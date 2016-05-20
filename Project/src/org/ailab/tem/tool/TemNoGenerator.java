package org.ailab.tem.tool;

import org.ailab.wimfra.database.DBUtil;
import org.ailab.wimfra.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 14-3-3
 * Time: 下午7:30
 * 超毅音乐编号生成器
 * 从数据库中读取记录，依次分配序号
 */
public class TemNoGenerator {
    public static void main(String[] args) throws Exception {
        String filterCondition = "1=1";
        int start = 1;

        List<Integer> idList = DBUtil.getIntegerList("select id from song where "+filterCondition+" order by id");
        Map<Integer, String> idToTemNoMap = new HashMap<Integer, String>(idList.size());
        for(int i=0; i<idList.size();i++) {
            String temNo = StringUtil.leftFill(start+i, 16, '0');
            idToTemNoMap.put(idList.get(i), temNo);
        }

        DBUtil.batchUpdateByKeyValueMap("song", "id", "temNo", idToTemNoMap);
    }
}
