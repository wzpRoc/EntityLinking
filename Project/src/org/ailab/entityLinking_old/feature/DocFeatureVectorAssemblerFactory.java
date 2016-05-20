package org.ailab.entityLinking_old.feature;

import org.ailab.entityLinking_old.candidateEntityGenerate.CandidateEntityGenerator;
import org.ailab.entityLinking_old.model.LinkModelType;
import org.ailab.entityLinking_old.modelOfDocGlobal.DocGlobalFeatureVectorAssembler;
import org.ailab.entityLinking_old.modelOfDocGlobal.feature.DocGlobalFeatureVectorGenerator;
import org.ailab.entityLinking_old.modelOfDocGlobal.feature.docGlobalFeature.IDocFeatureGenerator;
import org.ailab.entityLinking_old.modelOfMentionEntity.MentionEntityFeatureVectorAssembler;
import org.ailab.entityLinking_old.experiment.TrainingCorpusContext;
import org.ailab.entityLinking_old.modelOfMentionEntity.feature.MentionEntityFeatureVectorGenerator;
import org.ailab.entityLinking_old.modelOfMentionEntity.feature.mentionEntityFeature.IMentionEntityFeatureGenerator;

/**
 * User: Lu Tingming
 * Date: 15-5-18
 * Time: 上午12:53
 * Desc:
 */
public class DocFeatureVectorAssemblerFactory {
    public static DocFeatureVectorAssembler create(LinkModelType linkModelType, TrainingCorpusContext trainingCorpusContext, Object featureGenerators) {
        DocFeatureVectorAssembler docFeatureVectorAssembler;
        CandidateEntityGenerator candidateEntityGenerator = new CandidateEntityGenerator(trainingCorpusContext);
        if(linkModelType == LinkModelType.mentionEntity) {
            MentionEntityFeatureVectorGenerator featureVectorGenerator = new MentionEntityFeatureVectorGenerator(trainingCorpusContext, (IMentionEntityFeatureGenerator[]) featureGenerators);
            docFeatureVectorAssembler = new MentionEntityFeatureVectorAssembler(candidateEntityGenerator, featureVectorGenerator);
        } else {
            DocGlobalFeatureVectorGenerator featureVectorGenerator = new DocGlobalFeatureVectorGenerator(trainingCorpusContext, (IDocFeatureGenerator[]) featureGenerators);
            docFeatureVectorAssembler = new DocGlobalFeatureVectorAssembler(candidateEntityGenerator, featureVectorGenerator);
        }
        return docFeatureVectorAssembler;
    }
}
