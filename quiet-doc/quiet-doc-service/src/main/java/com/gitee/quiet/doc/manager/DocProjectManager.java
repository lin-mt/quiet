/*
 *     Copyright (C) 2022  lin-mt@outlook.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.doc.manager;

import com.gitee.quiet.common.util.JsonUtils;
import com.gitee.quiet.doc.entity.DocApi;
import com.gitee.quiet.doc.entity.DocApiGroup;
import com.gitee.quiet.doc.entity.DocApiInfo;
import com.gitee.quiet.doc.entity.DocProject;
import com.gitee.quiet.doc.enums.ApiState;
import com.gitee.quiet.doc.enums.HttpMethod;
import com.gitee.quiet.doc.enums.QueryParamType;
import com.gitee.quiet.doc.model.Header;
import com.gitee.quiet.doc.model.PathParam;
import com.gitee.quiet.doc.model.QueryParam;
import com.gitee.quiet.doc.model.Schema;
import com.gitee.quiet.doc.repository.DocProjectRepository;
import com.gitee.quiet.doc.service.DocApiGroupService;
import com.gitee.quiet.doc.service.DocApiInfoService;
import com.gitee.quiet.doc.service.DocApiService;
import com.gitee.quiet.doc.service.DocProjectService;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Slf4j
@Service
@AllArgsConstructor
public class DocProjectManager implements ApplicationRunner {

  private static final ConcurrentHashMap<Long, ScheduledFuture<?>> PROJECT_ID_2_SCHEDULED_FUTURE =
      new ConcurrentHashMap<>();

  private final ThreadPoolTaskScheduler taskScheduler;
  private final DocProjectRepository projectRepository;
  private final DocProjectService projectService;
  private final DocApiService apiService;
  private final DocApiGroupService apiGroupService;
  private final DocApiInfoService apiInfoService;

  /**
   * 更新 swagger 信息
   *
   * @param id 项目ID
   * @param enabled 是否启用
   * @param url swagger 地址
   * @param cron 定时表达式
   * @return 项目信息
   */
  public DocProject updateSwagger(Long id, Boolean enabled, String url, String cron) {
    DocProject project = projectService.getById(id);
    ScheduledFuture<?> scheduledFuture = PROJECT_ID_2_SCHEDULED_FUTURE.get(id);
    if (scheduledFuture != null) {
      scheduledFuture.cancel(true);
    }
    project.setSwaggerEnabled(enabled);
    project.setSwaggerUrl(url);
    project.setSwaggerCron(cron);
    DocProject update = projectService.saveOrUpdate(project);
    if (BooleanUtils.toBoolean(enabled)) {
      buildSwaggerTask(project);
    }
    return update;
  }

  private void buildSwaggerTask(DocProject project) {
    SwaggerSyncTask task =
        new SwaggerSyncTask(
            project.getId(), project.getSwaggerUrl(), apiService, apiGroupService, apiInfoService);
    ScheduledFuture<?> future =
        taskScheduler.schedule(task, new CronTrigger(project.getSwaggerCron()));
    if (future != null) {
      PROJECT_ID_2_SCHEDULED_FUTURE.put(project.getId(), future);
    }
  }

  @Override
  public void run(ApplicationArguments args) {
    List<DocProject> enabled = projectRepository.findAllBySwaggerEnabled(true);
    if (CollectionUtils.isEmpty(enabled)) {
      return;
    }
    for (DocProject project : enabled) {
      buildSwaggerTask(project);
    }
  }

  @AllArgsConstructor
  static class SwaggerSyncTask implements Runnable {

    private final Long projectId;
    private final String url;
    private final DocApiService apiService;
    private final DocApiGroupService apiGroupService;
    private final DocApiInfoService apiInfoService;

    @Override
    public void run() {
      StopWatch stopWatch = new StopWatch("swagger-sync-" + projectId);
      stopWatch.start("get swagger api info.");
      ParseOptions parseOptions = new ParseOptions();
      parseOptions.setFlatten(false);
      parseOptions.setResolveFully(true);
      SwaggerParseResult result = new OpenAPIParser().readLocation(url, null, parseOptions);
      stopWatch.stop();
      OpenAPI openAPI = result.getOpenAPI();
      if (result.getMessages() != null) {
        result.getMessages().forEach(log::error);
      }
      if (openAPI != null) {
        stopWatch.start("update project api info.");
        coverDocApi(projectId, openAPI.getPaths());
        stopWatch.stop();
      }
      log.info(stopWatch.prettyPrint());
    }

    private void coverDocApi(Long projectId, Paths paths) {
      Map<String, Long> groupName2Id =
          apiGroupService.listByProjectId(projectId).stream()
              .collect(Collectors.toMap(DocApiGroup::getName, DocApiGroup::getId));
      String keyPattern = "%s:%s";
      Map<String, Long> key2Id =
          apiService.listAllByProjectId(projectId).stream()
              .collect(
                  Collectors.toMap(
                      api -> String.format(keyPattern, api.getPath(), api.getMethod()),
                      DocApi::getId));
      Map<Long, Long> apiId2apiInfoId =
          apiInfoService.listByApiIds(new HashSet<>(key2Id.values())).stream()
              .collect(Collectors.toMap(DocApiInfo::getApiId, DocApiInfo::getId));
      paths.forEach(
          (path, pathItem) -> {
            List<DocApiDetail> apis = buildDocApis(path, pathItem, groupName2Id);
            if (CollectionUtils.isNotEmpty(apis)) {
              for (DocApiDetail apiDetail : apis) {
                DocApi api = apiDetail.getApi();
                DocApiInfo apiInfo = apiDetail.getApiInfo();
                String key = String.format(keyPattern, api.getPath(), api.getMethod());
                Long apiId = key2Id.get(key);
                if (apiId != null) {
                  api.setId(apiId);
                }
                DocApi docApi = apiService.save(api);
                apiInfo.setApiId(docApi.getId());
                Long apiInfoId = apiId2apiInfoId.get(docApi.getId());
                if (apiInfoId != null) {
                  apiInfo.setId(apiInfoId);
                }
                apiInfoService.saveOrUpdate(apiInfo);
              }
            }
          });
    }

    private List<DocApiDetail> buildDocApis(
        String path, PathItem pathItem, Map<String, Long> groupName2Id) {
      List<DocApiDetail> apiDetails = new ArrayList<>();
      Operation get = pathItem.getGet();
      if (get != null) {
        DocApi docApi = buildDocApiFromOperation(projectId, groupName2Id, path, get);
        docApi.setMethod(HttpMethod.GET);
        DocApiInfo docApiInfo = buildDocApiInfoFromOperation(get);
        apiDetails.add(DocApiDetail.builder().api(docApi).apiInfo(docApiInfo).build());
      }
      Operation post = pathItem.getPost();
      if (post != null) {
        DocApi docApi = buildDocApiFromOperation(projectId, groupName2Id, path, post);
        docApi.setMethod(HttpMethod.POST);
        DocApiInfo docApiInfo = buildDocApiInfoFromOperation(post);
        apiDetails.add(DocApiDetail.builder().api(docApi).apiInfo(docApiInfo).build());
      }
      Operation head = pathItem.getHead();
      if (head != null) {
        DocApi docApi = buildDocApiFromOperation(projectId, groupName2Id, path, head);
        docApi.setMethod(HttpMethod.HEAD);
        DocApiInfo docApiInfo = buildDocApiInfoFromOperation(head);
        apiDetails.add(DocApiDetail.builder().api(docApi).apiInfo(docApiInfo).build());
      }
      Operation put = pathItem.getPut();
      if (put != null) {
        DocApi docApi = buildDocApiFromOperation(projectId, groupName2Id, path, put);
        docApi.setMethod(HttpMethod.PUT);
        DocApiInfo docApiInfo = buildDocApiInfoFromOperation(put);
        apiDetails.add(DocApiDetail.builder().api(docApi).apiInfo(docApiInfo).build());
      }
      Operation delete = pathItem.getDelete();
      if (delete != null) {
        DocApi docApi = buildDocApiFromOperation(projectId, groupName2Id, path, delete);
        docApi.setMethod(HttpMethod.DELETE);
        DocApiInfo docApiInfo = buildDocApiInfoFromOperation(delete);
        apiDetails.add(DocApiDetail.builder().api(docApi).apiInfo(docApiInfo).build());
      }
      Operation options = pathItem.getOptions();
      if (options != null) {
        DocApi docApi = buildDocApiFromOperation(projectId, groupName2Id, path, options);
        docApi.setMethod(HttpMethod.OPTIONS);
        DocApiInfo docApiInfo = buildDocApiInfoFromOperation(options);
        apiDetails.add(DocApiDetail.builder().api(docApi).apiInfo(docApiInfo).build());
      }
      Operation patch = pathItem.getPatch();
      if (patch != null) {
        DocApi docApi = buildDocApiFromOperation(projectId, groupName2Id, path, patch);
        docApi.setMethod(HttpMethod.PATCH);
        DocApiInfo docApiInfo = buildDocApiInfoFromOperation(patch);
        apiDetails.add(DocApiDetail.builder().api(docApi).apiInfo(docApiInfo).build());
      }
      return apiDetails;
    }

    private DocApiInfo buildDocApiInfoFromOperation(Operation operation) {
      DocApiInfo docApiInfo = new DocApiInfo();
      List<QueryParam> reqQuery = new ArrayList<>();
      List<PathParam> pathParam = new ArrayList<>();
      List<Header> headers = new ArrayList<>();
      List<Parameter> parameters = operation.getParameters();
      if (CollectionUtils.isNotEmpty(parameters)) {
        parameters.forEach(
            parameter -> {
              String name = parameter.getName();
              boolean required = BooleanUtils.toBoolean(parameter.getRequired());
              String description = parameter.getDescription();
              String type = parameter.getSchema().getType();
              if ("query".equals(parameter.getIn())) {
                QueryParam param = new QueryParam();
                param.setName(name);
                param.setRequired(required);
                param.setRemark(description);
                param.setType(QueryParamType.valueOf(type.toUpperCase()));
                reqQuery.add(param);
              }
              if ("path".equals(parameter.getIn())) {
                PathParam param = new PathParam();
                param.setName(name);
                param.setRemark(description);
                pathParam.add(param);
              }
              if ("header".equals(parameter.getIn())) {
                Header param = new Header();
                param.setName(name);
                param.setRequired(required);
                param.setRemark(description);
                headers.add(param);
              }
            });
        if (CollectionUtils.isNotEmpty(reqQuery)) {
          docApiInfo.setReqQuery(reqQuery);
        }
        if (CollectionUtils.isNotEmpty(pathParam)) {
          docApiInfo.setPathParam(pathParam);
        }
        if (CollectionUtils.isNotEmpty(headers)) {
          docApiInfo.setHeaders(headers);
        }
      }
      RequestBody requestBody = operation.getRequestBody();
      if (requestBody != null) {
        MediaType applicationJson = requestBody.getContent().get("application/json");
        if (applicationJson != null) {
          Schema schema = new Schema();
          BeanUtils.copyProperties(applicationJson.getSchema(), schema);
          docApiInfo.setReqJsonBody(schema);
        }
      }
      ApiResponses responses = operation.getResponses();
      if (responses != null) {
        ApiResponse successResponse = responses.get("200");
        Content content = successResponse.getContent();
        MediaType response = content.get("application/json");
        if (response == null) {
          response = content.get("*/*");
        }
        if (response != null) {
          Schema schema = new Schema();
          BeanUtils.copyProperties(response.getSchema(), schema);
          docApiInfo.setRespJsonBody(schema);
        } else {
          docApiInfo.setRespRaw(JsonUtils.toJsonString(content));
        }
      }
      return docApiInfo;
    }

    private DocApi buildDocApiFromOperation(
        Long projectId, Map<String, Long> groupName2Id, String path, Operation operation) {
      DocApi docApi = new DocApi();
      docApi.setPath(path);
      docApi.setCreator(0L);
      docApi.setAuthorId(0L);
      docApi.setProjectId(projectId);
      String summary = operation.getSummary();
      if (summary == null) {
        summary = path.length() > 30 ? path.substring(0, 30) : path;
      }
      docApi.setName(summary);
      String description = operation.getDescription();
      if (description != null && description.length() > 300) {
        description = description.substring(0, 300);
      }
      docApi.setRemark(description);
      docApi.setApiState(ApiState.FINISHED);
      List<String> tags = operation.getTags();
      String groupName = "SwaggerGroup";
      if (CollectionUtils.isNotEmpty(tags) && StringUtils.isNotBlank(tags.get(0))) {
        groupName = tags.get(0);
      }
      Long groupId = groupName2Id.get(groupName);
      if (groupId == null) {
        DocApiGroup apiGroup = new DocApiGroup();
        apiGroup.setName(groupName);
        apiGroup.setProjectId(projectId);
        apiGroup.setRemark("From Swagger.");
        apiGroup.setCreator(0L);
        DocApiGroup newGroup = apiGroupService.save(apiGroup);
        groupId = newGroup.getId();
        groupName2Id.put(groupName, groupId);
      }
      docApi.setApiGroupId(groupId);
      return docApi;
    }

    @Data
    @Builder
    static class DocApiDetail {
      private DocApi api;
      private DocApiInfo apiInfo;
    }
  }
}
