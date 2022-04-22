/*
 * Copyright 2021 lin-mt@outlook.com
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

package com.gitee.quiet.doc.dubbo;

import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.system.service.QuietUserService;
import java.util.List;
import java.util.Set;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

/**
 * RPC 调用系统用户.
 *
 * @author @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Component
public class UserDubboService {

    @DubboReference
    private QuietUserService userService;

    public QuietUser getById(Long id) {
        return userService.findById(id);
    }

    public List<QuietUser> findByUserIds(Set<Long> userIds) {
        return userService.findByUserIds(userIds);
    }
}
