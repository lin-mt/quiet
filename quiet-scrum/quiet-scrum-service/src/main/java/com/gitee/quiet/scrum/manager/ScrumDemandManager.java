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
import com.gitee.quiet.scrum.repository.ScrumDemandRepository;
import com.gitee.quiet.scrum.service.ScrumIterationService;
import com.gitee.quiet.scrum.service.ScrumTaskService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class ScrumDemandManager {

  private final ScrumDemandRepository demandRepository;
  private final ScrumTaskService taskService;
  private final ScrumIterationService iterationService;

  /**
   * 根据项目信息删除项目下的需求信息
   *
   * @param projectId 要删除需求的项目ID
   */
  public void deleteAllByProjectId(@NotNull Long projectId) {
    List<ScrumDemand> demands = demandRepository.findAllByProjectId(projectId);
    if (CollectionUtils.isNotEmpty(demands)) {
      Set<Long> demandIds = demands.stream().map(ScrumDemand::getId).collect(Collectors.toSet());
      taskService.deleteAllByDemandIds(demandIds);
    }
  }
}
