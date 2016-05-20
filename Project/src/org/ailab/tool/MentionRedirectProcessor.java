package org.ailab.tool;

import org.ailab.entityLinking.wim.mention.Mention;
import org.ailab.entityLinking.wim.mention.MentionBL;
import org.ailab.wimfra.database.DBUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * User: lutingming
 * Date: 15-12-23
 * Time: 下午10:42
 * 将mention的entityId更新为重定向目标页ID，如果存在重定向的话
 */
public class MentionRedirectProcessor {
    public static void main(String[] args) throws Exception {
        List<Mention> mentionList = (new MentionBL()).getList();
        List<Vector> valueVectorList = new ArrayList<Vector>(mentionList.size());
        for(Mention mention : mentionList) {
            int wikiPageId = WikiTitleAndPageIdMap.getPageId(mention.getWikiTitle());
            int entityId;
            int wikiRedirectId = RedirectMap.getDestPageId(wikiPageId);
            if(wikiRedirectId == 0) {
                entityId = wikiPageId;
            } else {
                entityId = wikiRedirectId;
            }

            Vector vector = new Vector();
            vector.add(entityId);
            vector.add(mention.getId());
            valueVectorList.add(vector);
        }

        DBUtil.batchUpdate(
                "update mention set " +
                        "entityId=? " +
                        "where id=?",
                valueVectorList
        );
    }

}
