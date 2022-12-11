/*
 *     Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
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

package com.gitee.quiet.service.config;

import com.gitee.quiet.service.security.properties.QuietSecurityProperties;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@AllArgsConstructor
@EnableConfigurationProperties(QuietSecurityProperties.class)
public class QuietSecurityConfig extends WebSecurityConfigurerAdapter {

  private final QuietSecurityProperties quietSecurityProperties;

  @Override
  public void configure(WebSecurity web) {
    WebSecurity.IgnoredRequestConfigurer configurer = web.ignoring();
    if (CollectionUtils.isNotEmpty(quietSecurityProperties.getIgnoreUrls())) {
      for (String ignoreUrl : quietSecurityProperties.getIgnoreUrls()) {
        configurer.mvcMatchers(ignoreUrl);
      }
    }
  }
}
