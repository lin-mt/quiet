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

package com.gitee.quiet.common.service.base;

import javax.annotation.Nullable;

/**
 * 可排序.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface Serial extends Comparable<Serial> {
    
    /**
     * 获取排序的序号
     *
     * @return 序号
     */
    int getSerialNumber();
    
    /**
     * 设置排序的序号
     *
     * @param serialNumber 序号
     */
    void setSerialNumber(int serialNumber);
    
    /**
     * 跟其他对象进行比较
     *
     * @param other 比较的对象
     * @return 比较结果
     */
    @Override
    default int compareTo(@Nullable Serial other) {
        if (other == null) {
            return 1;
        }
        return Integer.compare(getSerialNumber(), other.getSerialNumber());
    }
    
}
