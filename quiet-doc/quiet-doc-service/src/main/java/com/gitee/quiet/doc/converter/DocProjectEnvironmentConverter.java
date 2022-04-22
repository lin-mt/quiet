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
 *
 */

package com.gitee.quiet.doc.converter;

import com.gitee.quiet.doc.dto.DocProjectEnvironmentDTO;
import com.gitee.quiet.doc.entity.DocProjectEnvironment;
import com.gitee.quiet.doc.vo.DocProjectEnvironmentVO;
import com.gitee.quiet.service.dto.QuietConvert;
import org.mapstruct.Mapper;

/**
 * 项目环境数据转换.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Mapper
public interface DocProjectEnvironmentConverter
    extends QuietConvert<DocProjectEnvironment, DocProjectEnvironmentDTO, DocProjectEnvironmentVO> {

}
