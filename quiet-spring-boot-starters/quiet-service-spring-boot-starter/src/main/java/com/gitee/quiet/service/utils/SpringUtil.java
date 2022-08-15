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

package com.gitee.quiet.service.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

/**
 * Spring 上下文工具.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class SpringUtil implements ApplicationContextAware {

  private static ApplicationContext applicationContext;

  public static <T> T getBean(Class<T> clazz) throws BeansException {
    return applicationContext.getBean(clazz);
  }

  @Override
  public void setApplicationContext(@NonNull ApplicationContext applicationContext)
      throws BeansException {
    SpringUtil.applicationContext = applicationContext;
  }
}
