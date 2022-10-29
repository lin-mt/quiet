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
import com.gitee.quiet.scrum.entity.ScrumIteration;
import com.gitee.quiet.scrum.entity.ScrumVersion;
import com.gitee.quiet.scrum.repository.ScrumIterationRepository;
import com.gitee.quiet.scrum.repository.ScrumVersionRepository;
import com.gitee.quiet.scrum.service.ScrumDemandService;
import com.gitee.quiet.scrum.service.ScrumIterationService;
import com.gitee.quiet.scrum.service.ScrumVersionService;
import com.gitee.quiet.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class ScrumIterationManager {

  private final ScrumIterationRepository iterationRepository;
  private final ScrumVersionRepository versionRepository;
  private final ScrumIterationService iterationService;
  private final ScrumDemandService demandService;
  private final ScrumVersionService versionService;

  /**
   * 新建/更新迭代
   *
   * @param iteration 新建/更新的迭代信息
   * @return 新建/更新后的迭代信息
   */
  public ScrumIteration saveOrUpdate(ScrumIteration iteration) {
    ScrumIteration exist =
        iterationRepository.findByVersionIdAndName(iteration.getVersionId(), iteration.getName());
    if (exist != null && !exist.getId().equals(iteration.getId())) {
      throw new ServiceException(
          "iteration.versionId.name.exist", iteration.getVersionId(), iteration.getName());
    }
    if (iteration.getId() == null) {
      ScrumVersion version = versionService.getById(iteration.getVersionId());
      if (version.getEndTime() != null) {
        version.setEndTime(null);
        versionService.saveOrUpdate(version);
      }
    }
    return iterationRepository.saveAndFlush(iteration);
  }

  /**
   * 开始迭代
   *
   * @param id 迭代ID
   * @return 迭代信息
   */
  public ScrumIteration start(Long id) {
    ScrumIteration iteration = iterationService.getById(id);
    iteration.setStartTime(LocalDateTime.now());
    ScrumVersion version = versionService.getById(iteration.getVersionId());
    if (version.getStartTime() == null) {
      Long parentId = version.getParentId();
      while (parentId != null) {
        ScrumVersion parent = versionService.getById(parentId);
        parentId = parent.getParentId();
        if (parent.getStartTime() == null) {
          parent.setStartTime(LocalDateTime.now());
        }
        versionRepository.saveAndFlush(parent);
      }
      version.setStartTime(LocalDateTime.now());
      versionRepository.saveAndFlush(version);
    }
    return iterationRepository.saveAndFlush(iteration);
  }

  /**
   * 结束迭代
   *
   * @param id 结束迭代的迭代ID
   * @param nextId 需求移入的下一个迭代ID
   * @return 迭代信息
   */
  public ScrumIteration end(Long id, Long nextId) {
    ScrumIteration iteration = iterationService.getById(id);
    List<ScrumDemand> unfinished = demandService.findAllUnfinished(iteration.getId());
    if (CollectionUtils.isNotEmpty(unfinished)) {
      if (nextId == null) {
        throw new ServiceException("iteration.canNotFindNextIteration");
      }
      ScrumIteration next = iterationService.getById(nextId);
      if (next.getEndTime() != null) {
        throw new ServiceException("iteration.next.end");
      }
      unfinished.forEach(demand -> demand.setIterationId(nextId));
      demandService.saveAll(unfinished);
    }
    endVersion(id, iteration.getVersionId());
    iteration.setEndTime(LocalDateTime.now());
    return iterationRepository.saveAndFlush(iteration);
  }

  private void endVersion(Long iterationId, Long versionId) {
    List<ScrumIteration> unfinishedIterations =
        iterationRepository.findAllByVersionId(versionId).stream()
            .filter(i -> Objects.isNull(i.getEndTime()))
            .filter(i -> !i.getId().equals(iterationId))
            .collect(Collectors.toList());
    List<ScrumVersion> unfinishedVersions = versionService.listAllChildren(versionId).stream()
            .filter(v -> Objects.isNull(v.getEndTime()))
            .collect(Collectors.toList());
    if (CollectionUtils.isEmpty(unfinishedIterations) && CollectionUtils.isEmpty(unfinishedVersions)) {
      ScrumVersion version = versionService.getById(versionId);
      version.setEndTime(LocalDateTime.now());
      versionService.saveOrUpdate(version);
      if (version.getParentId() != null) {
        endVersion(null, version.getParentId());
      }
    }
  }

  /**
   * 根据ID删除迭代信息
   *
   * @param id 迭代ID
   */
  public void deleteById(Long id) {
    if (demandService.countByIterationId(id) > 0) {
      throw new ServiceException("iteration.hasDemand.canNotDelete", id);
    }
    iterationRepository.deleteById(id);
  }
}
