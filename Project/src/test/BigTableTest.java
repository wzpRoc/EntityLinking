package test;

import org.ailab.wimfra.database.DBUtil;
import org.ailab.wimfra.util.Stopwatch;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 16-1-14
 * Time: 下午4:30
 * 实验结果：
 table			records		time			seconds
 ----------------------------------------------------
 pageWikiText	5003472		15:22.954		922.954
 entity			5003442		02:16.729		136.729
 pageWikiText	5003472		15:33.222		933.222
 entity			5003442		02:16.733		136.733
 */
public class BigTableTest {
    public static void main(String[] args) throws SQLException {
        Stopwatch stopwatch = new Stopwatch();
        DBUtil.getIntToStringMap("select id, CONVERT(wikiTitle USING utf8) from entity");
//        DBUtil.getIntToStringMap("select id, CONVERT(title USING utf8) from wiki.pageWikiText");
        // 927seconds
        System.out.println(stopwatch.stopAndGetFormattedTime());
    }
}
