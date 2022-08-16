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

package com.gitee.quiet.scrum;

import com.gitee.quiet.scrum.repository.Scrum;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 启动类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@EnableJpaRepositories(basePackageClasses = Scrum.class)
@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class QuietScrumServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuietScrumServiceApplication.class, args);
  }
}
