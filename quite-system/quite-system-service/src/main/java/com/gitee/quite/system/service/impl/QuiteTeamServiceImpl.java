/*
 * Copyright 2020 lin-mt@outlook.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gitee.quite.system.service.impl;

import com.gitee.quite.common.service.util.Wus;
import com.gitee.quite.system.entity.QuiteTeam;
import com.gitee.quite.system.repository.QuiteTeamRepository;
import com.gitee.quite.system.service.QuiteTeamService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.gitee.quite.system.entity.QQuiteTeam.quiteTeam;


/**
 * 团队 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuiteTeamServiceImpl implements QuiteTeamService {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    private final QuiteTeamRepository teamRepository;
    
    public QuiteTeamServiceImpl(JPAQueryFactory jpaQueryFactory, QuiteTeamRepository teamRepository) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.teamRepository = teamRepository;
    }
    
    @Override
    public QueryResults<QuiteTeam> page(QuiteTeam params, Pageable page) {
        BooleanBuilder builder = new BooleanBuilder();
        Wus.NotBlankContains(params.getTeamName(), quiteTeam.teamName, builder);
        Wus.NotBlankContains(params.getSlogan(), quiteTeam.slogan, builder);
        return jpaQueryFactory.selectFrom(quiteTeam).where(builder).offset(page.getOffset()).limit(page.getPageSize())
                .fetchResults();
    }
    
    @Override
    public QuiteTeam saveOrUpdate(QuiteTeam team) {
        return null;
    }
    
    @Override
    public void deleteTeam(Long deleteId) {
    
    }
}
