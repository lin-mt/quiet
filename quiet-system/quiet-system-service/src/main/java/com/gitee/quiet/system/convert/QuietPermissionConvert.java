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

package com.gitee.quiet.system.convert;

import com.gitee.quiet.common.service.base.QuietConvert;
import com.gitee.quiet.system.dto.QuietPermissionDto;
import com.gitee.quiet.system.entity.QuietPermission;
import org.mapstruct.Mapper;

/**
 * 权限信息实体信息转换.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Mapper
public interface QuietPermissionConvert extends QuietConvert<QuietPermission, QuietPermissionDto> {
    
}
