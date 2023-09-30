package cn.linmt.quiet.common.type;

import java.io.Serializable;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface Hierarchy<T extends Serializable> {

  T getParentId();
}
