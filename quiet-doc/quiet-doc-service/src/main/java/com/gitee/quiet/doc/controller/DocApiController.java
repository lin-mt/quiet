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
import com.gitee.quiet.doc.service.DocApiGroupService;
import com.gitee.quiet.doc.service.DocApiInfoService;
import com.gitee.quiet.doc.service.DocApiService;
import com.gitee.quiet.doc.service.DocProjectService;
import com.gitee.quiet.doc.vo.DocApiDetailVO;
import com.gitee.quiet.doc.vo.DocApiVO;
import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.service.utils.CurrentUserUtil;
import com.gitee.quiet.service.utils.ObjectUtils;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
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
  private final DocApiGroupService apiGroupService;
  private final DocApiInfoService apiInfoService;
  private final DocProjectService docProjectService;
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
  public Result<DocApiDetailVO> getDetail(@PathVariable Long id) {
    DocApiVO docApi = apiConvert.entity2vo(apiService.getById(id));
    if (docApi.getApiGroupId() != null) {
      DocApiGroup apiGroup = apiGroupService.findById(docApi.getApiGroupId());
      if (apiGroup != null) {
        docApi.setApiGroup(apiGroupConvert.entity2vo(apiGroup));
      }
    }
    Set<Long> userIds = new HashSet<>();
    userIds.add(docApi.getAuthorId());
    userIds.add(docApi.getCreator());
    userIds.add(docApi.getUpdater());
    Map<Long, QuietUser> userId2Info =
        dubboUserService.findByUserIds(userIds).stream()
            .collect(Collectors.toMap(QuietUser::getId, user -> user));
    if (userId2Info.get(docApi.getCreator()) != null) {
      docApi.setCreatorFullName(userId2Info.get(docApi.getCreator()).getFullName());
    }
    if (userId2Info.get(docApi.getUpdater()) != null) {
      docApi.setUpdaterFullName(userId2Info.get(docApi.getUpdater()).getFullName());
    }
    if (userId2Info.get(docApi.getAuthorId()) != null) {
      docApi.setAuthorFullName(userId2Info.get(docApi.getAuthorId()).getFullName());
    }
    DocApiDetailVO.DocApiDetailVOBuilder builder = DocApiDetailVO.builder();
    builder.api(docApi);
    DocApiInfo apiInfo = apiInfoService.getByApiId(id);
    if (apiInfo != null) {
      builder.apiInfo(apiInfoConvert.entity2vo(apiInfo));
    }
    return Result.success(builder.build());
  }

  /**
   * 更新项目所有接口信息
   *
   * @param projectId 项目ID
   * @param apis 接口信息
   * @return 更新结果
   */
  @PostMapping("/all/{projectId}")
  public Result<Object> all(
      @PathVariable Long projectId, @RequestBody @Valid List<DocApiDTO> apis) {
    if (CollectionUtils.isEmpty(apis)) {
      return Result.createSuccess();
    }
    docProjectService.getById(projectId);
    List<DocApi> allApi = apiService.listAllByProjectId(projectId);
    String keyPattern = "%s:%s";
    Map<String, DocApiDTO> key2newInfo = new HashMap<>(apis.size());
    for (DocApiDTO api : apis) {
      api.setProjectId(projectId);
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
      List<QuietUser> usernames = dubboUserService.findByUsernames(authors);
      Map<String, Long> username2Id =
          usernames.stream().collect(Collectors.toMap(QuietUser::getUsername, QuietUser::getId));
      key2newInfo.forEach(
          (key, newApi) -> {
            newApi.setAuthorId(username2Id.getOrDefault(newApi.getAuthor(), 0L));
            DocApi save = apiService.save(apiConvert.dto2entity(newApi));
            DocApiInfo docApiInfo = apiInfoConvert.dto2entity(newApi.getApiInfo());
            docApiInfo.setApiId(save.getId());
            apiInfoService.save(docApiInfo);
          });
    }
    apiService.saveAll(key2oldInfo.values());
    apiInfoService.saveAll(apiId2Info.values());
    return Result.createSuccess();
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
    apiService.deleteById(id);
    return Result.deleteSuccess();
  }
}
