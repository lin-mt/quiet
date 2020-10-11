package com.gitee.quite.common.service.id;

import com.gitee.quite.common.service.util.ApplicationUtil;
import com.gitee.quite.common.service.util.SnowFlakeIdWorker;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * Id 生成器.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class SnowFlakeIdGenerator implements IdentifierGenerator {
    
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return ApplicationUtil.getBean(SnowFlakeIdWorker.class).nextId();
    }
    
}