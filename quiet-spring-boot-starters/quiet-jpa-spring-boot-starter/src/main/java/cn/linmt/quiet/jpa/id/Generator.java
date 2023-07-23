package cn.linmt.quiet.jpa.id;

import cn.linmt.quiet.jpa.utils.IdWorker;
import java.io.Serializable;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.lang.NonNull;

/**
 * Id 生成器.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Slf4j
@SuppressWarnings("unused")
public class Generator implements IdentifierGenerator {

  private static IdWorker idWorker;

  public static void setIdWorker(@NonNull IdWorker idWorker) {
    Generator.idWorker = idWorker;
  }

  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object object)
      throws HibernateException {
    if (idWorker == null) {
      throw new IllegalStateException("IdWorker 不能为 null");
    }
    long id = idWorker.nextId();
    log.info("GeneratorId {}", id);
    return id;
  }
}
