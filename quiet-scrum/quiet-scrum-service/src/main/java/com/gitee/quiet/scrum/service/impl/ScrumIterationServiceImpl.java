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

package com.gitee.quiet.scrum.service.impl;

import com.gitee.quiet.scrum.entity.ScrumDemand;
import com.gitee.quiet.scrum.entity.ScrumIteration;
import com.gitee.quiet.scrum.entity.ScrumVersion;
import com.gitee.quiet.scrum.repository.ScrumIterationRepository;
import com.gitee.quiet.scrum.service.ScrumDemandService;
import com.gitee.quiet.scrum.service.ScrumIterationService;
import com.gitee.quiet.scrum.service.ScrumVersionService;
import com.gitee.quiet.service.exception.ServiceException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 迭代信息service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class ScrumIterationServiceImpl implements ScrumIterationService {

  private final ScrumIterationRepository iterationRepository;

  private final ScrumVersionService versionService;

  private final ScrumDemandService demandService;

  public ScrumIterationServiceImpl(
      ScrumIterationRepository iterationRepository,
      @Lazy ScrumVersionService versionService,
      ScrumDemandService demandService) {
    this.iterationRepository = iterationRepository;
    this.versionService = versionService;
    this.demandService = demandService;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteByVersionIds(Set<Long> versionIds) {
    if (CollectionUtils.isNotEmpty(versionIds)) {
      iterationRepository.deleteAllByVersionIdIn(versionIds);
    }
  }

  @Override
  public List<ScrumIteration> findAllByVersionIds(Set<Long> versionIds) {
    if (CollectionUtils.isNotEmpty(versionIds)) {
      return iterationRepository.findAllByVersionIdIn(versionIds);
    }
    return List.of();
  }

  @Override
  public ScrumIteration save(ScrumIteration save) {
    checkInfo(save);
    return iterationRepository.save(save);
  }

  @Override
  public ScrumIteration update(ScrumIteration update) {
    checkInfo(update);
    return iterationRepository.saveAndFlush(update);
  }

  @Override
  public void deleteById(Long id) {
    if (demandService.countByIterationId(id) > 0) {
      throw new ServiceException("iteration.hasDemand.canNotDelete", id);
    }
    checkIdExist(id);
    iterationRepository.deleteById(id);
  }

  @Override
  public long countByVersionId(Long versionId) {
    return iterationRepository.countByVersionId(versionId);
  }

  @Override
  public void checkIdExist(Long id) {
    if (!iterationRepository.existsById(id)) {
      throw new ServiceException("iteration.id.not.exist", id);
    }
  }

  @Override
  public ScrumIteration start(Long id) {
    ScrumIteration iteration =
        iterationRepository
            .findById(id)
            .orElseThrow(() -> new ServiceException("iteration.id.not.exist", id));
    iteration.setStartTime(LocalDateTime.now());
    return iterationRepository.saveAndFlush(iteration);
  }

  @Override
  public ScrumIteration end(Long id) {
    ScrumIteration iteration =
        iterationRepository
            .findById(id)
            .orElseThrow(() -> new ServiceException("iteration.id.not.exist", id));
    List<ScrumDemand> unfinished = demandService.findAllUnfinished(iteration.getId());
    if (CollectionUtils.isNotEmpty(unfinished)) {
      ScrumIteration nextIteration =
          iterationRepository.findByVersionIdAndPlanStartDateAfter(
              iteration.getVersionId(), iteration.getPlanStartDate());
      if (nextIteration == null) {
        ScrumVersion nextVersion = versionService.nextVersion(iteration.getVersionId());
        while (nextVersion != null && nextIteration == null) {
          nextIteration =
              iterationRepository.findFirstByVersionIdOrderByPlanStartDateAsc(nextVersion.getId());
          if (nextIteration == null) {
            nextVersion = versionService.nextVersion(nextVersion.getId());
          }
        }
      }
      if (nextIteration == null) {
        throw new ServiceException("iteration.canNotFindNextIteration");
      } else {
        final Long nextIterationId = nextIteration.getId();
        unfinished.forEach(demand -> demand.setIterationId(nextIterationId));
        demandService.saveAll(unfinished);
      }
    }
    iteration.setEndTime(LocalDateTime.now());
    return iterationRepository.saveAndFlush(iteration);
  }

  private void checkInfo(@NotNull ScrumIteration iteration) {
    if (Objects.nonNull(iteration.getId())) {
      checkIdExist(iteration.getId());
    }
    versionService.checkIdExist(iteration.getVersionId());
    ScrumIteration exist =
        iterationRepository.findByVersionIdAndName(iteration.getVersionId(), iteration.getName());
    if (exist != null && !exist.getId().equals(iteration.getId())) {
      throw new ServiceException(
          "iteration.versionId.name.exist", iteration.getVersionId(), iteration.getName());
    }
  }
}
