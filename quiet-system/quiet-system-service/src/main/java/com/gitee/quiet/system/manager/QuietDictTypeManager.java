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

package com.gitee.quiet.system.manager;

import com.gitee.quiet.system.entity.QuietDict;
import com.gitee.quiet.system.repository.QuietDictTypeRepository;
import com.gitee.quiet.system.service.QuietDictService;
import com.gitee.quiet.system.service.QuietDictTypeService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class QuietDictTypeManager {

  private final QuietDictTypeService dictTypeService;

  private final QuietDictTypeRepository dictTypeRepository;

  private final QuietDictService dictService;

  public void deleteById(Long id) {
    dictTypeService.getById(id);
    List<QuietDict> dictList = dictService.listByTypeId(id);
    if (CollectionUtils.isNotEmpty(dictList)) {
      throw new SecurityException("dictType.has.dict..cannot.delete");
    }
    dictTypeRepository.deleteById(id);
  }
}
