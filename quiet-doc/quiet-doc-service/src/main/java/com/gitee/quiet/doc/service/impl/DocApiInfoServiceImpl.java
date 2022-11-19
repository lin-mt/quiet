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

package com.gitee.quiet.doc.service.impl;

import com.gitee.quiet.doc.entity.DocApiInfo;
import com.gitee.quiet.doc.repository.DocApiInfoRepository;
import com.gitee.quiet.doc.service.DocApiInfoService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * api信息服务实现类.
 *
 * @author @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class DocApiInfoServiceImpl implements DocApiInfoService {

  private final DocApiInfoRepository repository;

  @Override
  public DocApiInfo saveOrUpdate(DocApiInfo apiInfo) {
    DocApiInfo exist = repository.findByApiId(apiInfo.getApiId());
    if (exist != null) {
      apiInfo.setId(exist.getId());
    }
    return repository.save(apiInfo);
  }

  @Override
  public DocApiInfo getByApiId(Long apiId) {
    return repository.getByApiId(apiId);
  }

  @Override
  public List<DocApiInfo> listByApiIds(Set<Long> apiIds) {
    if (CollectionUtils.isEmpty(apiIds)) {
      return new ArrayList<>();
    }
    return repository.findAllByApiIdIn(apiIds);
  }

  @Override
  public void saveAll(Collection<DocApiInfo> apiInfos) {
    if (CollectionUtils.isEmpty(apiInfos)) {
      return;
    }
    repository.saveAll(apiInfos);
  }
}
