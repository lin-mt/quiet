package cn.linmt.quiet.jpa.properties;

import cn.linmt.quiet.jpa.id.Generator;
import cn.linmt.quiet.jpa.utils.IdWorker;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Data
@ConfigurationProperties(prefix = "quiet.jpa")
public class JpaProperties implements InitializingBean {

  private IdGenerator idGenerator = new IdGenerator();

  @Override
  public void afterPropertiesSet() {
    if (idGenerator.getDataCenterId() > idGenerator.getMaxServerNumber()) {
      throw new IllegalStateException("dataCenterId 不能大于 maxServerNumber");
    }
    // machine ID 0 ~ 1023
    long machineId = idGenerator.getMachineId();
    if (machineId < 0 || machineId > 1023) {
      throw new IllegalArgumentException("machineId 需要在 0 ～ 1023 之间");
    }
    Generator.setIdWorker(new IdWorker(machineId));
  }
}
