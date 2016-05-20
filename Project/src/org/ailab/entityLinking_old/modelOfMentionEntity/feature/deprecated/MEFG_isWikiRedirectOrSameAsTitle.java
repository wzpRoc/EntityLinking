package org.ailab.entityLinking_old.modelOfMentionEntity.feature.deprecated;

import org.ailab.entityLinking_old.cache.WikiRedirectCache;
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
public class MEFG_isWikiRedirectOrSameAsTitle extends MentionEntityFeatureGenerator {
    @Override
    public double generate(Doc doc, Mention mention, Entity entity) {
        return mention.getMention().equals(entity.getTitleNormalized()) || WikiRedirectCache.isWikiRedirect(mention.getMention(), entity.getTitle()) ? 1 : 0;
    }
}
