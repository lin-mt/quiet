/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.doc.controller;

import com.gitee.quiet.doc.converter.DocApiConvert;
import com.gitee.quiet.doc.converter.DocApiGroupConvert;
import com.gitee.quiet.doc.converter.DocApiInfoConvert;
import com.gitee.quiet.doc.dto.DocApiDTO;
import com.gitee.quiet.doc.dubbo.DubboUserService;
import com.gitee.quiet.doc.entity.DocApi;
import com.gitee.quiet.doc.entity.DocApiGroup;
import com.gitee.quiet.doc.entity.DocApiInfo;
import com.gitee.quiet.doc.manager.DocApiManager;
import com.gitee.quiet.doc.repository.DocProjectRepository;
import com.gitee.quiet.doc.service.DocApiGroupService;
import com.gitee.quiet.doc.service.DocApiInfoService;
import com.gitee.quiet.doc.service.DocApiService;
import com.gitee.quiet.doc.vo.DocApiVO;
import com.gitee.quiet.service.annotation.ExistId;
import com.gitee.quiet.service.result.BatchResult;
import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.service.utils.CurrentUserUtil;
import com.gitee.quiet.service.utils.ObjectUtils;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 接口信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class DocApiController {

  private final DocApiService apiService;
  private final DocApiManager apiManager;
  private final DocApiGroupService apiGroupService;
  private final DocApiInfoService apiInfoService;
  private final DocApiConvert apiConvert;
  private final DocApiInfoConvert apiInfoConvert;
  private final DocApiGroupConvert apiGroupConvert;
  private final DubboUserService dubboUserService;

  /**
   * 根据项目ID和接口名称模糊查询接口信息
   *
   * @param projectId 项目ID
   * @param name 接口名称
   * @param limit 限制查询条数，小于等于0或者不传则查询所有
   * @return 接口信息
   */
  @GetMapping("/list")
  public Result<List<DocApiVO>> list(
      @RequestParam Long projectId,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Long limit) {
    List<DocApi> docApis = apiService.listByProjectIdAndName(projectId, name, limit);
    return Result.success(apiConvert.entities2vos(docApis));
  }

  /**
   * 分页查询接口，api_group_id 传0时会加上 api_group_id is null 过滤条件
   *
   * @param dto 分页参数
   * @return 查询结果
   */
  @GetMapping("/page")
  public Result<Page<DocApiVO>> page(DocApiDTO dto) {
    Page<DocApi> page = apiService.page(apiConvert.dto2entity(dto), dto.page());
    return Result.success(apiConvert.page2page(page));
  }

  /**
   * 查询接口详细信息
   *
   * @param id 接口ID
   * @return 接口详细信息
   */
  @GetMapping("/detail/{id}")
  public Result<DocApiVO> getDetail(@PathVariable Long id) {
    DocApiVO docApiVO = apiConvert.entity2vo(apiService.getById(id));
    if (docApiVO.getApiGroupId() != null) {
      DocApiGroup apiGroup = apiGroupService.findById(docApiVO.getApiGroupId());
      if (apiGroup != null) {
        docApiVO.setApiGroup(apiGroupConvert.entity2vo(apiGroup));
      }
    }
    Set<Long> userIds = new HashSet<>();
    userIds.add(docApiVO.getAuthorId());
    userIds.add(docApiVO.getCreator());
    userIds.add(docApiVO.getUpdater());
    Map<Long, QuietUser> userId2Info =
        dubboUserService.findByUserIds(userIds).stream()
            .collect(Collectors.toMap(QuietUser::getId, user -> user));
    if (userId2Info.get(docApiVO.getCreator()) != null) {
      docApiVO.setCreatorFullName(userId2Info.get(docApiVO.getCreator()).getFullName());
    }
    if (userId2Info.get(docApiVO.getUpdater()) != null) {
      docApiVO.setUpdaterFullName(userId2Info.get(docApiVO.getUpdater()).getFullName());
    }
    if (userId2Info.get(docApiVO.getAuthorId()) != null) {
      docApiVO.setAuthorFullName(userId2Info.get(docApiVO.getAuthorId()).getFullName());
    }
    DocApiInfo apiInfo = apiInfoService.getByApiId(id);
    if (apiInfo != null) {
      docApiVO.setApiInfo(apiInfoConvert.entity2vo(apiInfo));
    }
    return Result.success(docApiVO);
  }

  /**
   * 更新项目所有接口信息
   *
   * @param projectId 项目ID
   * @param apis 接口信息
   * @return 更新结果
   */
  @Valid
  @PostMapping("/batch/{projectId}")
  public Result<BatchResult> batch(
      @PathVariable
          @ExistId(repository = DocProjectRepository.class, message = "{project.id.not.exist}")
          Long projectId,
      @RequestBody List<DocApiDTO> apis) {
    BatchResult result = new BatchResult();
    if (CollectionUtils.isEmpty(apis)) {
      return Result.success(result);
    }
    List<DocApi> allApi = apiService.listAllByProjectId(projectId);
    Map<String, Long> groupName2Id =
        apiGroupService.listByProjectId(projectId).stream()
            .collect(Collectors.toMap(DocApiGroup::getName, DocApiGroup::getId));
    // 根据 ${请求路径}:${请求方法} 判断是否同一个 api
    String keyPattern = "%s:%s";
    Map<String, DocApiDTO> key2newInfo = new HashMap<>(apis.size());
    for (DocApiDTO api : apis) {
      api.setProjectId(projectId);
      String groupName = api.getGroupName();
      Long groupId = groupName2Id.get(groupName);
      if (StringUtils.isNotBlank(groupName) && groupId == null) {
        DocApiGroup apiGroup = new DocApiGroup();
        apiGroup.setProjectId(projectId);
        String newGroupName = StringUtils.substring(groupName, 0, 30);
        apiGroup.setName(newGroupName);
        DocApiGroup save = apiGroupService.save(apiGroup);
        groupId = save.getId();
        groupName2Id.put(newGroupName, groupId);
      }
      api.setApiGroupId(groupId);
      key2newInfo.put(String.format(keyPattern, api.getPath(), api.getMethod()), api);
    }
    Set<Long> apiIds = new HashSet<>(allApi.size());
    Map<String, DocApi> key2oldInfo = new HashMap<>(allApi.size());
    allApi.forEach(
        api -> {
          apiIds.add(api.getId());
          key2oldInfo.put(String.format(keyPattern, api.getPath(), api.getMethod()), api);
        });
    Map<Long, DocApiInfo> apiId2Info = new HashMap<>();
    if (CollectionUtils.isNotEmpty(apiIds)) {
      apiId2Info =
          apiInfoService.listByApiIds(apiIds).stream()
              .collect(Collectors.toMap(DocApiInfo::getApiId, info -> info));
    }
    Iterator<Map.Entry<String, DocApiDTO>> iterator = key2newInfo.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, DocApiDTO> entry = iterator.next();
      DocApiDTO newDocApi = entry.getValue();
      DocApi docApi = key2oldInfo.get(entry.getKey());
      if (null == docApi) {
        continue;
      }
      ObjectUtils.copyPropertiesIgnoreNull(newDocApi, docApi);
      DocApiInfo apiInfo = apiId2Info.get(docApi.getId());
      if (apiInfo == null) {
        continue;
      }
      ObjectUtils.copyPropertiesIgnoreNull(newDocApi.getApiInfo(), apiInfo);
      apiInfo.setReqFile(newDocApi.getApiInfo().getReqFile());
      apiInfo.setReqForm(newDocApi.getApiInfo().getReqForm());
      apiInfo.setReqJsonBody(newDocApi.getApiInfo().getReqJsonBody());
      apiInfo.setReqQuery(newDocApi.getApiInfo().getReqQuery());
      apiInfo.setRespRaw(newDocApi.getApiInfo().getRespRaw());
      apiInfo.setRespJsonBody(newDocApi.getApiInfo().getRespJsonBody());
      newDocApi.setApiInfo(null);
      iterator.remove();
    }
    if (MapUtils.isNotEmpty(key2newInfo)) {
      Set<String> authors =
          key2newInfo.values().stream().map(DocApiDTO::getAuthor).collect(Collectors.toSet());
      List<QuietUser> users = dubboUserService.findByUsernames(authors);
      Map<String, Long> username2Id =
          users.stream().collect(Collectors.toMap(QuietUser::getUsername, QuietUser::getId));
      key2newInfo.forEach(
          (key, newApi) -> {
            newApi.setAuthorId(username2Id.getOrDefault(newApi.getAuthor(), 0L));
            DocApi save = apiService.save(apiConvert.dto2entity(newApi));
            DocApiInfo docApiInfo = apiInfoConvert.dto2entity(newApi.getApiInfo());
            docApiInfo.setApiId(save.getId());
            apiInfoService.saveOrUpdate(docApiInfo);
          });
      result.setAddNum(key2newInfo.size());
    }
    apiService.saveAll(key2oldInfo.values());
    result.setUpdateNum(key2oldInfo.size());
    apiInfoService.saveAll(apiId2Info.values());
    return Result.success(result);
  }

  /**
   * 新建接口
   *
   * @param dto 新建的接口信息
   * @return 新增的接口信息
   */
  @PostMapping
  public Result<DocApiVO> save(@RequestBody @Validated(Create.class) DocApiDTO dto) {
    dto.setAuthorId(CurrentUserUtil.getId());
    DocApi save = apiService.save(apiConvert.dto2entity(dto));
    return Result.createSuccess(apiConvert.entity2vo(save));
  }

  /**
   * 更新接口信息
   *
   * @param dto 更新的接口信息
   * @return 更新后的接口信息
   */
  @PutMapping
  public Result<DocApiVO> update(@RequestBody @Validated(Update.class) DocApiDTO dto) {
    DocApi update = apiService.update(apiConvert.dto2entity(dto));
    return Result.updateSuccess(apiConvert.entity2vo(update));
  }

  /**
   * 根据接口ID删除接口信息
   *
   * @param id 要删除的接口ID
   * @return 删除结果
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    apiManager.deleteById(id);
    return Result.deleteSuccess();
  }
}
