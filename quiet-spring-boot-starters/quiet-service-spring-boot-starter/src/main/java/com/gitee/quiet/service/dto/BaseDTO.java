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

package com.gitee.quiet.service.dto;

import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.IdValid;
import com.gitee.quiet.validation.groups.OffsetLimitValid;
import com.gitee.quiet.validation.groups.PageValid;
import com.gitee.quiet.validation.groups.Update;
import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * 请求参数.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Data
public class BaseDTO {

    private static final String ASCEND = "ascend";

    private static final String DESCEND = "descend";

    /**
     * ID
     */
    @Null(groups = Create.class)
    @NotNull(groups = {IdValid.class, Update.class})
    private Long id;

    /**
     * 查询关键词
     */
    private String keyword;

    /**
     * 第几页
     */
    @NotNull(groups = PageValid.class)
    @Min(value = 1, groups = PageValid.class)
    private Integer current;

    /**
     * 分页大小
     */
    @NotNull(groups = PageValid.class)
    @Min(value = 1, groups = PageValid.class)
    private Integer pageSize;

    /**
     * 跳过几条数据
     */
    @Min(value = 0L, groups = OffsetLimitValid.class)
    @NotNull(groups = OffsetLimitValid.class)
    private Long offset;

    /**
     * 查询几条数据
     */
    @Range(max = 300L, groups = OffsetLimitValid.class)
    @NotNull(groups = OffsetLimitValid.class)
    private Long limit;

    private Long creator;

    private Long updater;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtUpdate;

    public Pageable page() {
        return PageRequest.of(getCurrent() - 1, this.getPageSize());
    }
}
