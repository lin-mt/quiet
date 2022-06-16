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
import com.gitee.quiet.doc.service.DocApiService;
import com.gitee.quiet.service.exception.ServiceException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * api信息服务实现类.
 *
 * @author @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class DocApiInfoServiceImpl implements DocApiInfoService {

    private final DocApiInfoRepository repository;

    private final DocApiService apiService;

    public DocApiInfoServiceImpl(DocApiInfoRepository repository, @Lazy DocApiService apiService) {
        this.repository = repository;
        this.apiService = apiService;
    }

    @Override
    public DocApiInfo save(DocApiInfo save) {
        apiService.checkId(save.getApiId());
        if (repository.existsByApiId(save.getApiId())) {
            throw new ServiceException("apiInfo.apiId.exist", save.getApiId());
        }
        return repository.save(save);
    }

    @Override
    public DocApiInfo update(DocApiInfo update) {
        if (!repository.existsById(update.getId())) {
            throw new ServiceException("apiInfo.id.notExist", update.getId());
        }
        return repository.saveAndFlush(update);
    }

    @Override
    public DocApiInfo getByApiId(Long apiId) {
        return repository.getByApiId(apiId);
    }

    @Override
    public List<DocApiInfo> listByApiIds(Set<Long> apiIds) {
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
