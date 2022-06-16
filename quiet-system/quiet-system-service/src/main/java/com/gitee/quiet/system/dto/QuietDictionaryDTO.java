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

package com.gitee.quiet.system.dto;

import com.gitee.quiet.service.dto.ParentAndSerialDTO;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 数据字典.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class QuietDictionaryDTO extends ParentAndSerialDTO<QuietDictionaryDTO> {

    /**
     * 数据字典类型
     */
    @Length(max = 30)
    private String type;

    /**
     * 数据字典的key，同数据字典类型下的key不能重复，这个要在业务代码中进行限制
     */
    @Length(max = 30)
    private String key;

    /**
     * 数据字典显示的值，前端找不到国际化值的时候使用的默认值
     */
    @NotBlank
    @Length(max = 30)
    private String label;

    /**
     * 备注
     */
    @Length(max = 100)
    private String remark;

}
