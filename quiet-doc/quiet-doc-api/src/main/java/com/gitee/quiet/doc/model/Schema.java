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

package com.gitee.quiet.doc.model;

import lombok.Data;

import java.util.Map;
import java.util.Set;

/**
 * Schema.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Data
public class Schema {

  private String title;

  private String type;

  private Map<String, Schema> properties;

  private Set<String> required;

  private String description;

  private Long minLength;

  private Long maxLength;

  private Long minimum;

  private Long maximum;

  private Boolean exclusiveMinimum;

  private Boolean exclusiveMaximum;

  private Schema items;

  private Long minItems;

  private Long maxItems;
}
