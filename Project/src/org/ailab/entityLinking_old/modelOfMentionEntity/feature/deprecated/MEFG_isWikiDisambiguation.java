package org.ailab.entityLinking_old.modelOfMentionEntity.feature.deprecated;

import org.ailab.entityLinking_old.cache.WikiDisambiguationCache;
import org.ailab.entityLinking_old.modelOfMentionEntity.feature.mentionEntityFeature.MentionEntityFeatureGenerator;
import org.ailab.entityLinking_old.wim.doc.Doc;
import org.ailab.entityLinking_old.wim.docEntity.Mention;
import org.ailab.entityLinking_old.wim.entity.Entity;

/**
 * User: Lu Tingming
 * Date: 15-5-16
 * Time: 下午9:37
 * Desc:
 */
public class MEFG_isWikiDisambiguation extends MentionEntityFeatureGenerator {
    @Override
    public double generate(Doc doc, Mention mention, Entity entity) {
        return WikiDisambiguationCache.isWikiDisambiguation(mention.getMention(), entity.getTitle()) ? 1 : 0;
    }
}
