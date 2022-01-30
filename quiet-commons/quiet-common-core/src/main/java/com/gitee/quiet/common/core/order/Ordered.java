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

package com.gitee.quiet.common.core.order;

import javax.annotation.Nullable;

/**
 * 可排序
 */
public interface Ordered extends Comparable<Ordered> {
    
    default int getOrder() {
        return 0;
    }
    
    default int compareTo(@Nullable Ordered other) {
        if (other == null) {
            return 1;
        }
        return Integer.compare(getOrder(), other.getOrder());
    }
}
