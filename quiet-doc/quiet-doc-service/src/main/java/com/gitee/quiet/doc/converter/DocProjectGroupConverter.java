/*
 *     Copyright (C) 2022  lin-mt@outlook.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.doc.converter;

import com.gitee.quiet.doc.dto.DocProjectGroupDTO;
import com.gitee.quiet.doc.entity.DocProjectGroup;
import com.gitee.quiet.doc.vo.DocProjectGroupVO;
import com.gitee.quiet.service.dto.QuietConvert;
import org.mapstruct.Mapper;

/**
 * 项目分组.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Mapper
public interface DocProjectGroupConverter
    extends QuietConvert<DocProjectGroup, DocProjectGroupDTO, DocProjectGroupVO> {}
