package cn.linmt.quiet.common.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface Sortable extends Comparable<Sortable> {

  int getSerialNum();

  void setSerialNum(int serialNum);

  default boolean autoSort() {
    return true;
  }

  @Override
  default int compareTo(@Nullable Sortable other) {
    if (other == null) {
      return 1;
    }
    return Integer.compare(getSerialNum(), other.getSerialNum());
  }

  class Utils {

    public static <T> List<T> sort(List<T> value) {
      if (CollectionUtils.isNotEmpty(value)) {
        List<T> sorted = new ArrayList<>(value);
        Map<Integer, T> indexToValue = new HashMap<>(sorted.size());
        for (int i = 0; i < sorted.size(); i++) {
          T t = sorted.get(i);
          if (t instanceof Sortable) {
            if (((Sortable) t).autoSort()) {
              indexToValue.put(i, t);
            }
          }
        }
        if (MapUtils.isNotEmpty(indexToValue)) {
          List<T> sort = indexToValue.values().stream().sorted().toList();
          int index = 0;
          for (Map.Entry<Integer, T> entry : indexToValue.entrySet()) {
            sorted.set(entry.getKey(), sort.get(index));
            index++;
          }
        }
        return sorted;
      } else {
        return value;
      }
    }
  }
}
