package org.ailab.wimfra.core.idFactory;

import java.util.Calendar;

/**
 * User: Lu Tingming
 * Date: 2011-6-6
 * Time: 12:22:53
 * Desc:
 */
public class TimeSeqIdFactory {
    private static long lastTimeSeq = 0;
    // private static String str_lastTime = TimeUtil.getDateTime();

    public synchronized static long getId() {
        long resultTimeSeq = Calendar.getInstance().getTimeInMillis();
        if (resultTimeSeq <= lastTimeSeq) {
            // 重复时间
            resultTimeSeq++;
            while (resultTimeSeq <= lastTimeSeq) {
                resultTimeSeq++;
            }
        }

        // resultTimeSeq > lastTimeSeq

        lastTimeSeq = resultTimeSeq;

        return resultTimeSeq;
    }
}
