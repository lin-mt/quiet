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

package com.gitee.quiet.scrum.manager;

import com.gitee.quiet.scrum.entity.ScrumDemand;
import com.gitee.quiet.scrum.entity.ScrumTask;
import com.gitee.quiet.scrum.repository.ScrumDemandRepository;
import com.gitee.quiet.scrum.service.ScrumDemandService;
import com.gitee.quiet.scrum.service.ScrumTaskService;
import com.gitee.quiet.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class ScrumDemandManager {

  private final ScrumDemandService demandService;
  private final ScrumDemandRepository demandRepository;
  private final ScrumTaskService taskService;

  public void deleteById(Long id) {
    ScrumDemand delete = demandService.getById(id);
    if (delete.getIterationId() != null) {
      throw new ServiceException("demand.iterationId.notNull.canNotDelete", id);
    }
    List<ScrumTask> tasks = taskService.listAllByDemandId(id);
    if (CollectionUtils.isNotEmpty(tasks)) {
      throw new ServiceException("demand.hasTasks.canNotDelete");
    }
    demandRepository.deleteById(id);
  }
}
