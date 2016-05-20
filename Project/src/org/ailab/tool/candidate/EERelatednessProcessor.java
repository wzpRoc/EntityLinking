package org.ailab.tool.candidate;

import org.ailab.entityLinking.wim.doc.Doc;
import org.ailab.entityLinking.wim.doc.DocBL;
import org.ailab.tool.EntityToEntityLink.EERelatednessMap;
import org.ailab.wimfra.database.DBConnect;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * User: lutingming
 * Date: 16-1-1
 * Time: 下午12:53
 * Desc: 候选实体对语义关联度计算器
 */
public class EERelatednessProcessor {
    public static void main(String[] args) throws SQLException {
        EERelatednessMap eeRelatednessMap = new EERelatednessMap();

        // 对每篇文章逐个处理
        DocBL docBL = new DocBL();
        List<Doc> docList = docBL.getList();
//        docList = docList.subList(0, 1);
        for(int i_doc=0; i_doc<docList.size(); i_doc++) {
            System.out.println("processing doc " + (i_doc + 1) + "/" + docList.size());
            Doc doc = docList.get(i_doc);
            (new DocEERelatednessProcessor(eeRelatednessMap, doc)).processDoc();
        }

        System.out.println();

        eeRelatednessMap.save();

        System.out.println();
    }



}
