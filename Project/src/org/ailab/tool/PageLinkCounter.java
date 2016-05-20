package org.ailab.tool;

import org.ailab.entityLinking_old.wim.entity.Entity;
import org.ailab.entityLinking_old.wim.entity.EntityBL;
import org.ailab.entityLinking_old.wim.wikiDisambiguation.WikiDisambiguationDAO;
import org.ailab.wimfra.database.DBUtilInstance;
import org.ailab.wimfra.util.FileUtil;
import org.ailab.wimfra.util.KeyValue;
import org.apache.log4j.Logger;


import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-16
 * Time: 下午11:26
 * Desc:
 */
public class PageLinkCounter {
    private static final String filePath = "d:\\pageLinkCount.txt";
    protected static Logger logger = Logger.getLogger(PageLinkCounter.class);

    public static void main(String[] args) throws Exception {
        saveToDB();
    }

    private static void saveToDB() throws Exception {
        DBUtilInstance dbi = new DBUtilInstance();
        List<KeyValue> entityIdAndCountList = load();
        dbi.updateByKeyValueList("entity", "id", "wikiPageLinkCount", entityIdAndCountList);
    }

    private static List<KeyValue> load() throws Exception {
        List<String> lines = FileUtil.readLines(filePath);

        List<KeyValue> keyValueList = new ArrayList<KeyValue>(lines.size());
        for(String line : lines) {
            String[] as = line.split("\t");
            keyValueList.add(new KeyValue(as[0], as[1]));
        }

        return keyValueList;
    }

    private static void compute() {
        List<Entity> entityList = new EntityBL().getList();

        WikiDisambiguationDAO wikiDisambiguationDAO = new WikiDisambiguationDAO();

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<entityList.size(); i++) {
            Entity entity = entityList.get(i);
            int pageLinkCount = wikiDisambiguationDAO.getPageLinkCount(entity.getTitle());
            sb.append(entity.getId()).append("\t").append(pageLinkCount).append("\t").append(entity.getTitle()).append("\n");
            if(i%1000==0) {
                System.out.println("processed entity "+(i+1));
                FileUtil.append(sb.toString(), filePath);
                sb = new StringBuilder();
            }
        }

        FileUtil.append(sb.toString(), filePath);
    }
}
