package org.ailab.wimfra.frontend.graph;

import org.ailab.wimfra.util.NumberUtil;

import java.util.ArrayList;

/**
 * User: Lu Tingming
 * Date: 2012-6-23
 * Time: 20:49:52
 * Desc:
 */
public class LineId extends ArrayList<String> {
    public LineId(int initialCapacity) {
        super(initialCapacity);
    }


    /**
     * 转换成javascript
     * @return
     */
    public String toJs() {
        StringBuffer sb = new StringBuffer();

        sb.append('[');

        boolean needComma = false;
        for (String id : this) {
            if (needComma) {
                sb.append(',');
            }
            if(id!=null && (NumberUtil.isDecimal(id) || id.startsWith("["))) {
                sb.append(id);
            } else {
                sb.append('\'').append(id).append('\'');
            }
            if (!needComma) {
                needComma = true;
            }
        }

        sb.append(']');

        return sb.toString();
    }

}