package org.ailab.tem.tool.wikipedia;

import org.ailab.wimfra.database.DBUtil;
import org.ailab.wimfra.database.DBUtilInstance;
import org.ailab.wimfra.util.FileUtil;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 13-12-26
 * Time: 下午9:40
 */
public class TitleReader {
    public static void main(String[] args) throws Exception {
//          FileUtil.print("D:\\Wikipedia\\zhwiki-20131221-pages-articles.xml", 13000);
//          FileUtil.print("D:\\Wikipedia\\zhwiki-20131221-stub-meta-current.xml", 13000);
          FileUtil.print("D:\\Wikipedia\\zhwiki-20131221-stub-articles.xml", 13000);

//        List<String> lines = FileUtil.readLines("zhwiki-20131221-pages-articles.xml");
//        DBUtil.batchExecuteWithValueCollection("insert into zhwp.title values(?)", lines);
    }
}
