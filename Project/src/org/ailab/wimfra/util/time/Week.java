package org.ailab.wimfra.util.time;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 13-10-3
 * Time: 上午3:06
 */
public class Week {
    public String startDate;
    public String endDate;

    public Week(String startDate) {
        this.startDate = startDate;
        this.endDate = TimeUtil.getDateAfter(startDate, 6);
    }

    public List<String> getDateList() {
        List<String> list = new ArrayList<String>(7);
        list.add(startDate);
        for(int i=1; i<=5; i++) {
            list.add(TimeUtil.getDateAfter(startDate, i));
        }
        list.add(endDate);

        return list;
    }
}
