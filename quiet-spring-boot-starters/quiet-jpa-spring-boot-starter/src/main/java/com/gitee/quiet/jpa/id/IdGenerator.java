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

package com.gitee.quiet.jpa.id;

import com.gitee.quiet.jpa.utils.IdWorker;
import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

/**
 * Id 生成器.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@SuppressWarnings("unused")
public class IdGenerator implements IdentifierGenerator {

    private static final Logger logger = LoggerFactory.getLogger(IdGenerator.class);
    private static IdWorker idWorker;

    public static void setIdWorker(@NonNull IdWorker idWorker) {
        IdGenerator.idWorker = idWorker;
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        if (idWorker == null) {
            throw new IllegalStateException("IdWorker 不能为 null");
        }
        long id = idWorker.nextId();
        logger.info("GeneratorId {}", id);
        return id;
    }

}