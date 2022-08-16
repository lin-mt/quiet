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

package com.gitee.quiet.doc.vo;

import com.gitee.quiet.doc.entity.DocApi;
import com.gitee.quiet.doc.entity.DocApiGroup;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目接口分组信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
public class ProjectApiInfo {

  /** 未分组的接口 */
  private final List<DocApi> ungroup = new ArrayList<>();

  /** 已分组的接口 */
  private final Map<Long, List<DocApi>> grouped = new HashMap<>();

  /** 项目接口分组 */
  private final List<DocApiGroup> apiGroups = new ArrayList<>();
}
