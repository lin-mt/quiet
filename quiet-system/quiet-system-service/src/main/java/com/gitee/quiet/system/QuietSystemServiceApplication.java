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

package com.gitee.quiet.system;

import com.gitee.quiet.system.repository.Quiet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 启动类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = Quiet.class)
public class QuietSystemServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuietSystemServiceApplication.class, args);
  }
}
