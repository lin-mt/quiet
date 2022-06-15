/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.common.core.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

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

    class Utils {

        public static <T> void sortSerial(List<T> value) {
            if (CollectionUtils.isNotEmpty(value)) {
                Map<Integer, T> indexToValue = new HashMap<>(value.size());
                for (int i = 0; i < value.size(); i++) {
                    T t = value.get(i);
                    if (t instanceof Serial) {
                        indexToValue.put(i, t);
                    }
                }
                if (MapUtils.isNotEmpty(indexToValue)) {
                    List<T> sort = indexToValue.values().stream().sorted().collect(Collectors.toList());
                    int index = 0;
                    for (Map.Entry<Integer, T> entry : indexToValue.entrySet()) {
                        value.set(entry.getKey(), sort.get(index));
                        index++;
                    }
                }
            }
        }
    }

}
