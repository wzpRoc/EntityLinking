package org.ailab.entityLinking_old.modelOfMentionEntity.feature;

import org.ailab.entityLinking_old.model.Label;
import org.ailab.entityLinking_old.experiment.TrainingCorpusContext;
import org.ailab.entityLinking_old.modelOfMentionEntity.feature.mentionEntityFeature.*;
import org.ailab.entityLinking_old.wim.doc.Doc;
import org.ailab.entityLinking_old.wim.docEntity.Mention;
import org.ailab.entityLinking_old.wim.entity.Entity;
import weka.core.Instance;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-30
 * Time: 下午5:31
 * Desc:
 */
public class MentionEntityFeatureVectorGenerator {
    private static final int WEIGH = 1;
    protected List<IMentionEntityFeatureGenerator> featureGeneratorList;
    protected int featureCount;
          // 特征生成是与训练数据相关的
    protected TrainingCorpusContext trainingCorpusContext;

    public MentionEntityFeatureVectorGenerator(TrainingCorpusContext trainingCorpusContext, IMentionEntityFeatureGenerator... featureGenerators) {
        this.trainingCorpusContext = trainingCorpusContext;
        addFeatureGenerators(featureGenerators);
    }

    private void addFeatureGenerators(IMentionEntityFeatureGenerator... featureGenerators) {
        featureGeneratorList = new ArrayList<IMentionEntityFeatureGenerator>(featureGenerators.length);

        for(IMentionEntityFeatureGenerator featureGenerator : featureGenerators) {
            featureGeneratorList.add(featureGenerator);
            featureGenerator.setTrainingCorpusContext(trainingCorpusContext);
        }

        featureCount = featureGeneratorList.size();
    }


    public List<Instance> generate(Doc doc, Mention mention, Integer correctEntityId, List<Entity> candidateEntityList) {
        List<Instance> instanceList = new ArrayList<Instance>();
        for(Entity candidateEntity : candidateEntityList) {
            Instance instance = generate(doc, mention, correctEntityId, candidateEntity);
            instanceList.add(instance);
        }

        return instanceList;
    }

    public Instance generate(Doc doc, Mention mention, Integer correctEntityId, Entity candidateEntity) {
        Label label;
        if(correctEntityId == null) {
            label = Label.unknown;
        } else if(correctEntityId == candidateEntity.getId()) {
            label = Label.yes;
        } else {
            label = Label.no;
        }
        return generate(doc, mention, candidateEntity, label);
    }

    public Instance generate(Doc doc, Mention mention, Entity candidateEntity, Label label) {
        double[] values = new double[featureCount+1];
        values[0] = label.getValue();
        for(int i=0; i<featureGeneratorList.size(); i++) {
            IMentionEntityFeatureGenerator fg = featureGeneratorList.get(i);
            double featureValue = fg.generate(doc, mention, candidateEntity);
            values[i+1]= featureValue;
            //noinspection unchecked
        }

        return new Instance(WEIGH, values);
    }


    public String[] getFeatureNames() {
        String[] featureNames = new String[featureGeneratorList.size()];
        for(int i=0; i<featureGeneratorList.size(); i++) {
            featureNames[i] = featureGeneratorList.get(i).getFeatureName();
        }

        return featureNames;
    }
}
