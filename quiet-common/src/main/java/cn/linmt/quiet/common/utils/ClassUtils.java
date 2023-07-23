package cn.linmt.quiet.common.utils;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class ClassUtils {

  /**
   * 获取 main 方法所在的类.
   *
   * @return main 方法所在的类，或者 null
   */
  public static Class<?> getMainClass() {
    try {
      StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
      for (StackTraceElement stackTraceElement : stackTrace) {
        if ("main".equals(stackTraceElement.getMethodName())) {
          return Class.forName(stackTraceElement.getClassName());
        }
      }
    } catch (ClassNotFoundException ex) {
      // Swallow and continue
    }
    return null;
  }
}
