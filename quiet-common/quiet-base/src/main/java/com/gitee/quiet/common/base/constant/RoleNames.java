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

package com.gitee.quiet.common.base.constant;

import org.springframework.lang.NonNull;

/**
 * 角色名称.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused"})
public interface RoleNames {
    
    String ROLE_PREFIX = "ROLE_";
    
    // 为了角色名称更加直观，没有采用大写+下划线的命名风格
    
    String SystemAdmin = buildRoleName("SystemAdmin");
    String Admin = buildRoleName("Admin");
    String ProductOwner = buildRoleName("ProductOwner");
    String ScrumMaster = buildRoleName("ScrumMaster");
    
    /**
     * 构建角色名称
     *
     * @param roleName 角色名称
     * @return 构建后的角色名称
     */
    private static String buildRoleName(@NonNull String roleName) {
        return ROLE_PREFIX + roleName;
    }
}
