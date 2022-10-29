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

package com.gitee.quiet.doc.dubbo;

import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.system.service.QuietUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * RPC 调用系统用户.
 *
 * @author @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Component
public class DubboUserService {

  @DubboReference private QuietUserService userService;

  public QuietUser getById(Long id) {
    return userService.findById(id);
  }

  public List<QuietUser> findByUserIds(Set<Long> userIds) {
    return userService.findByUserIds(userIds);
  }

  public List<QuietUser> findByUsernames(Set<String> usernames) {
    if (CollectionUtils.isEmpty(usernames)) {
      return new ArrayList<>();
    }
    return userService.findByUsernames(usernames);
  }
}
