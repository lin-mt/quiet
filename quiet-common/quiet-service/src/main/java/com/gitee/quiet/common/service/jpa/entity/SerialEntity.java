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

package com.gitee.quiet.common.service.jpa.entity;

import com.gitee.quiet.common.service.base.Serial;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 具有排序字段的实体.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@MappedSuperclass
public class SerialEntity extends BaseEntity implements Serial {
    
    /**
     * 序号
     */
    @ColumnDefault("0")
    @Column(name = "serial_number", nullable = false)
    private int serialNumber;
    
    @Override
    public int getSerialNumber() {
        return serialNumber;
    }
    
    @Override
    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }
    
}
