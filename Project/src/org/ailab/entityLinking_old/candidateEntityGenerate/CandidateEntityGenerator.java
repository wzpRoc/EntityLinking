package org.ailab.entityLinking_old.candidateEntityGenerate;

import org.ailab.entityLinking_old.experiment.TrainingCorpusContext;
import org.ailab.entityLinking_old.wim.entity.Entity;
import org.ailab.wimfra.util.CollectionUtil;
import org.apache.log4j.Logger;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Lu Tingming
 * Date: 15-5-15
 * Time: 下午1:29
 * Desc:
 */
public class CandidateEntityGenerator {
    protected final Logger logger = Logger.getLogger(this.getClass());

    protected boolean includeNIL = true;
    private final EntityQuerier entityQuerier;
    protected TrainingCorpusContext trainingCorpusContext;
    protected Map<String, List<Entity>> cache;

    public CandidateEntityGenerator(TrainingCorpusContext trainingCorpusContext) {
        this.trainingCorpusContext = trainingCorpusContext;
        entityQuerier = new EntityQuerier();
        cache = new HashMap<String, List<Entity>>();
    }

    public List<Entity> generate(String mentionText) {
        List<Entity> resultEntityList;

        resultEntityList = cache.get(mentionText);
        if(resultEntityList != null) {
            return resultEntityList;
        }

        // query by name
        List<Entity> entityListByName = entityQuerier.queryByName(mentionText);
        logger.debug("query by name: "+entityListByName.size()+ " found");

        // query from training data
        List<Entity> entityListByTrainingCorpus;
        if(trainingCorpusContext!=null) {
            entityListByTrainingCorpus = trainingCorpusContext.getEntityList(mentionText);
        } else {
            entityListByTrainingCorpus = null;
        }

        resultEntityList = CollectionUtil.merge(entityListByName, entityListByTrainingCorpus);

        // todo
        if(includeNIL) {
            resultEntityList.add(Entity.NIL);
        }

        cache.put(mentionText, resultEntityList);

        return resultEntityList;
    }

    public boolean isIncludeNIL() {
        return includeNIL;
    }

    public void setIncludeNIL(boolean includeNIL) {
        this.includeNIL = includeNIL;
    }

    private void test(String mention) {
        System.out.println("mention="+mention);
        System.out.println("----------------------------------------------------------");
        List<Entity> entityList = generate(mention);
        for(Entity entity : entityList) {
            System.out.println(entity.toString());
        }
        System.out.println();
    }

    @Test
    public void test() {
        this.test("习近平");
        this.test("习大大");
        this.test("美");
        this.test("中");
        this.test("日");
    }
}
