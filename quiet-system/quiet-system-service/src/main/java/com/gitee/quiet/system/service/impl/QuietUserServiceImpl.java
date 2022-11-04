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

package com.gitee.quiet.system.service.impl;

import com.gitee.quiet.jpa.utils.SelectBooleanBuilder;
import com.gitee.quiet.jpa.utils.SelectBuilder;
import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.system.repository.QuietUserRepository;
import com.gitee.quiet.system.service.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

import static com.gitee.quiet.system.entity.QQuietTeamUser.quietTeamUser;
import static com.gitee.quiet.system.entity.QQuietUser.quietUser;

/**
 * 用户 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@DubboService
@AllArgsConstructor
public class QuietUserServiceImpl implements QuietUserService {

  private final JPAQueryFactory jpaQueryFactory;
  private final PasswordEncoder passwordEncoder;
  private final QuietUserRepository userRepository;

  @Override
  public QuietUser save(@NotNull QuietUser quietUser) {
    if (userRepository.getByUsername(quietUser.getUsername()) != null) {
      throw new ServiceException("user.username.exist", quietUser.getUsername());
    }
    quietUser.setSecretCode(passwordEncoder.encode(quietUser.getSecretCode()));
    return userRepository.saveAndFlush(quietUser);
  }

  @Override
  public QuietUser update(@NotNull QuietUser user) {
    QuietUser exist = userRepository.getByUsername(user.getUsername());
    if (exist != null && !exist.getId().equals(user.getId())) {
      throw new ServiceException("user.username.exist", user.getUsername());
    }
    user.setSecretCode(passwordEncoder.encode(user.getPassword()));
    return userRepository.saveAndFlush(user);
  }

  @Override
  public Page<QuietUser> page(QuietUser params, @NotNull Pageable page) {
    BooleanBuilder predicate = SelectBuilder.booleanBuilder(params).getPredicate();
    return userRepository.findAll(predicate, page);
  }

  @Override
  public boolean existsById(@NotNull Long userId) {
    return userRepository.existsById(userId);
  }

  @Override
  public List<QuietUser> findByUserIds(@NotNull @NotEmpty Set<Long> userIds) {
    return jpaQueryFactory
        .select(
            Projections.bean(
                QuietUser.class,
                quietUser.id,
                quietUser.fullName,
                quietUser.username,
                quietUser.avatar))
        .from(quietUser)
        .where(quietUser.id.in(userIds))
        .fetch();
  }

  @Override
  public List<QuietUser> listUsers(String name, Set<Long> userIds, int limit) {
    BooleanBuilder where =
        SelectBuilder.booleanBuilder()
            .notEmptyIn(userIds, quietUser.id)
            .with(
                builder -> {
                  if (StringUtils.isNotBlank(name)) {
                    builder.and(
                        quietUser.username.contains(name).or(quietUser.fullName.contains(name)));
                  }
                })
            .getPredicate();
    JPAQuery<QuietUser> query = jpaQueryFactory.selectFrom(quietUser).where(where);
    if (limit > 0) {
      query.limit(limit);
    }
    return query.fetch();
  }

  @Override
  public QuietUser findById(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new ServiceException("user.id.not.exist", id));
  }

  @Override
  public List<QuietUser> listTeamUser(Long id) {
    BooleanBuilder predicate =
        SelectBooleanBuilder.booleanBuilder().isIdEq(id, quietTeamUser.teamId).getPredicate();
    return jpaQueryFactory
        .selectFrom(quietUser)
        .leftJoin(quietTeamUser)
        .on(quietUser.id.eq(quietTeamUser.userId))
        .where(predicate)
        .fetch();
  }

  @Override
  public List<QuietUser> findByUsernames(Set<String> usernames) {
    return userRepository.findByUsernameIsIn(usernames);
  }
}
