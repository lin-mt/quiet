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

package com.gitee.quiet.system.service.impl;

import com.gitee.quiet.system.entity.QuietTeam;
import com.gitee.quiet.system.repository.QuietTeamRepository;
import com.gitee.quiet.system.service.QuietTeamService;
import com.gitee.quiet.common.service.exception.ServiceException;
import com.gitee.quiet.common.service.util.Where;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.gitee.quiet.system.entity.QQuietTeam.quietTeam;


/**
 * 团队 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietTeamServiceImpl implements QuietTeamService {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    private final QuietTeamRepository teamRepository;
    
    public QuietTeamServiceImpl(JPAQueryFactory jpaQueryFactory, QuietTeamRepository teamRepository) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.teamRepository = teamRepository;
    }
    
    @Override
    public QueryResults<QuietTeam> page(QuietTeam params, Pageable page) {
        BooleanBuilder builder = new BooleanBuilder();
        Where.NotNullEq(params.getId(), quietTeam.id, builder);
        Where.NotBlankContains(params.getTeamName(), quietTeam.teamName, builder);
        Where.NotBlankContains(params.getSlogan(), quietTeam.slogan, builder);
        return jpaQueryFactory.selectFrom(quietTeam).where(builder).offset(page.getOffset()).limit(page.getPageSize())
                .fetchResults();
    }
    
    @Override
    public QuietTeam saveOrUpdate(QuietTeam team) {
        QuietTeam exist = teamRepository.getByTeamName(team.getTeamName());
        if (exist != null && !exist.getId().equals(team.getId())) {
            throw new ServiceException("team.teamName.exist", team.getTeamName());
        }
        return teamRepository.saveAndFlush(team);
    }
    
    @Override
    public void deleteTeam(Long deleteId) {
        // TODO 删除团队中的成员信息
        teamRepository.deleteById(deleteId);
    }
}
