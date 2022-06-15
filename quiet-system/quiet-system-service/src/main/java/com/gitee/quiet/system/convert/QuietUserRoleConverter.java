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

package com.gitee.quiet.system.convert;

import com.gitee.quiet.service.dto.QuietConvert;
import com.gitee.quiet.system.dto.QuietUserRoleDTO;
import com.gitee.quiet.system.entity.QuietUserRole;
import com.gitee.quiet.system.vo.QuietUserRoleVO;
import org.mapstruct.Mapper;

/**
 * 用户角色信息转换器.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Mapper
public interface QuietUserRoleConverter extends QuietConvert<QuietUserRole, QuietUserRoleDTO, QuietUserRoleVO> {

}
