package org.ailab.entityLinking_old.modelOfMentionEntity.feature.mentionEntityFeature;

import org.ailab.entityLinking_old.cache.EntityCache;
import org.ailab.entityLinking_old.wim.doc.Doc;
import org.ailab.entityLinking_old.wim.doc.DocBL;
import org.ailab.entityLinking_old.wim.docEntity.DocEntity;
import org.ailab.entityLinking_old.wim.entity.Entity;
import org.ailab.nlp.tfidf.IDFDict;
import org.ailab.nlp.tfidf.IDFDictFactory;
import org.ailab.nlp.tfidf.TFIDFProcessor;
import org.ailab.nlp.tfidf.TFProcessor;

/**
 * User: Lu Tingming
 * Date: 15-5-16
 * Time: 下午9:37
 * Desc:
 */
public class SimilarityByWikiAbstMEFG extends SimilarityMEFG {
    private static IDFDict idfDict;

    @Override
    protected String getEntityContext(Entity entity) {
        return entity.getAbst();
    }

    @Override
    protected void initTFIDFProcessor() {
        if(idfDict == null) {
            idfDict = IDFDictFactory.wikiIDFDict;
        }
        this.tfidfProcessor = new TFIDFProcessor(idfDict, TFProcessor.getInstance());
    }


    @Override
    public String getFeatureShortName() {
        return "Sim(d,e.wikiAbst)";
    }


    public static void main(String[] args) {
        Doc doc = (Doc) (new DocBL()).get(1012623600);

        DocEntity mention = new DocEntity();
        mention.setMention("匈牙利");

        Entity entity0 = EntityCache.getByTitle("匈牙利");
        Entity entity1 = EntityCache.getByTitle("匈牙利王国_(1920–1946)");

        SimilarityByWikiAbstMEFG similarityByWikiAbstMEFG = new SimilarityByWikiAbstMEFG();

        double sim0 = similarityByWikiAbstMEFG.generate(doc, mention, entity0);
        double sim1 = similarityByWikiAbstMEFG.generate(doc, mention, entity1);

        sim0 = similarityByWikiAbstMEFG.generate(doc, mention, entity0);
        sim1 = similarityByWikiAbstMEFG.generate(doc, mention, entity1);

        System.out.println();
    }
}
