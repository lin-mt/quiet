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

/**
 * 角色名称.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused"})
public final class RoleNames {
    
    private RoleNames() {
    }
    
    public static final String ROLE_PREFIX = "ROLE_";
    
    // 为了角色名称更加直观，没有采用大写+下划线的命名风格
    
    public static final String SystemAdmin = "ROLE_SystemAdmin";
    
    public static final String Admin = "ROLE_Admin";
    
    public static final String ProductOwner = "ROLE_ProductOwner";
    
    public static final String ScrumMaster = "ROLE_ScrumMaster";
    
}
