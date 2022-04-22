/*
 * Copyright 2021 lin-mt@outlook.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gitee.quiet.doc.vo;

import com.gitee.quiet.doc.entity.DocProject;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 登陆人的项目信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class MyDocProject {

    /**
     * 负责的项目
     */
    private List<DocProject> responsibleProjects = List.of();

    /**
     * 可访问的项目
     */
    private List<DocProject> accessibleProjects = List.of();

}
