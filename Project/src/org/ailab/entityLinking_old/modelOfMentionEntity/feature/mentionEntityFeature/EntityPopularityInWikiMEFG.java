package org.ailab.entityLinking_old.modelOfMentionEntity.feature.mentionEntityFeature;

import org.ailab.entityLinking_old.wim.doc.Doc;
import org.ailab.entityLinking_old.wim.docEntity.Mention;
import org.ailab.entityLinking_old.wim.entity.Entity;

/**
 * User: Lu Tingming
 * Date: 15-5-16
 * Time: 下午9:37
 * Desc:
 */
public class EntityPopularityInWikiMEFG extends MentionEntityFeatureGenerator {
    public static final int maxWikiPageLinkCount = 1000000; // 849200;
    @Override
    public double generate(Doc doc, Mention mention, Entity entity) {
        return (double) entity.getWikiPageLinkCount() /maxWikiPageLinkCount;
    }


    @Override
    public String getFeatureShortName() {
        return "Pop(e)inWiki";
    }
}
