package cn.linmt.quiet.jpa.properties;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Slf4j
public class IdGenerator {

  private long workerId = 0L;

  private long dataCenterId = 0L;

  private int maxServerNumber = 10;

  public long getMachineId() {
    return workerId * maxServerNumber + dataCenterId;
  }

  public long getWorkerId() {
    return workerId;
  }

  public void setWorkerId(long workerId) {
    if (workerId < 0) {
      log.warn("workerId 不能小于 0，当前值：0");
    } else {
      this.workerId = workerId;
    }
  }

  public long getDataCenterId() {
    return dataCenterId;
  }

  public void setDataCenterId(long dataCenterId) {
    if (dataCenterId < 0) {
      log.warn("dataCenterId 不能小于 0，当前值：0");
    } else {
      this.dataCenterId = dataCenterId;
    }
  }

  public int getMaxServerNumber() {
    return maxServerNumber;
  }

  public void setMaxServerNumber(int maxServerNumber) {
    if (maxServerNumber < 1) {
      log.warn("maxServerNumber 不能小于 1，当前值：10");
    } else {
      this.maxServerNumber = maxServerNumber;
    }
  }
}
