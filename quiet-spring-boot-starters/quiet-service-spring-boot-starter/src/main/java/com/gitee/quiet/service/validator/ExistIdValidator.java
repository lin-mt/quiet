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

package com.gitee.quiet.service.validator;

import com.gitee.quiet.jpa.repository.QuietRepository;
import com.gitee.quiet.service.annotation.ExistId;
import com.gitee.quiet.service.utils.SpringUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ID 校验.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class ExistIdValidator implements ConstraintValidator<ExistId, Long> {

  private static final ConcurrentMap<Class<? extends QuietRepository<?>>, QuietRepository<?>>
      repositories = new ConcurrentHashMap<>();

  private Class<? extends QuietRepository<?>> repository;

  @Override
  public void initialize(ExistId constraintAnnotation) {
    repository = constraintAnnotation.repository();
  }

  @Override
  public boolean isValid(Long value, ConstraintValidatorContext context) {
    boolean isValid;
    if (value == null) {
      isValid = true;
    } else if (value <= 0) {
      isValid = false;
    } else {
      QuietRepository<?> quietRepository =
          repositories.computeIfAbsent(repository, SpringUtil::getBean);
      Object entity = quietRepository.findById(value).orElse(null);
      isValid = entity != null;
    }
    return isValid;
  }
}
