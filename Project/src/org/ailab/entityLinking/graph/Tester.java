package org.ailab.entityLinking.graph;

import org.ailab.entityLinking.evaluation.DatasetResult;
import org.ailab.entityLinking.evaluation.DocResult;
import org.ailab.entityLinking.graph.debug.EvidenceVectorPrinter;
import org.ailab.entityLinking.graph.debug.MatrixPrinter;
import org.ailab.entityLinking.wim.doc.Doc;
import org.ailab.entityLinking.wim.doc.DocBL;
import org.ailab.entityLinking.wim.mention.Mention;
import org.ailab.entityLinking.wim.mention.MentionBL;

import java.util.List;

/**
 * User: lutingming
 * Date: 16-1-7
 * Time: 上午12:53
 */
public class Tester {
    public static void main(String[] args) {
        testAll();
//        testDoc(21);
//        testIteration();
//        debugDoc();
    }

    private static void testAll() {
        List<Doc> docList = (new DocBL()).getList();
        MentionBL mentionBL = new MentionBL();
        for(Doc doc : docList) {
            List<Mention> mentionList = mentionBL.getListWithCandidatesByDocId(doc.getId());
            doc.mentionList = mentionList;
        }

        DatasetResult datasetResult_first = new DatasetResult();
        DatasetResult datasetResult_final = new DatasetResult();
        for(Doc doc : docList) {
            if(doc.mentionList!=null && doc.mentionList.size()>0) {
                DocResult[] docResults = testDoc(doc);
                datasetResult_first.addDocResult(docResults[0]);
                datasetResult_final.addDocResult(docResults[1]);
                datasetResult_first.print("------------------- dataset base\t");
                datasetResult_final.print("------------------- dataset final\t");
            }
        }
    }

    private static DocResult[] testDoc(int docId) {
        Doc doc = (Doc) (new DocBL()).get(docId);
        return testDoc(doc);
    }

    private static DocResult[] testIteration() {
        Doc doc = (Doc) (new DocBL()).get(349);
        GraphConstructor graphConstructor = new GraphConstructor(doc);
        graphConstructor.iterations=10000;
        graphConstructor.printAnalysis=true;
        DocResult[] docResults = graphConstructor.process();
        docResults[0].print("base\t");
        docResults[1].print("final\t");
        return docResults;
    }

    private static DocResult[] debugDoc() {
        int docId = 22;
        Doc doc = (Doc) (new DocBL()).get(docId);

        GraphConstructor graphConstructor = new GraphConstructor(doc);
/*
        graphConstructor.maxMentions=2;
        graphConstructor.maxCandidates=5;
        graphConstructor.iterations=1000;
*/

        DocResult[] docResults = graphConstructor.process();
        docResults[0].print("base\t");
        docResults[1].print("final\t");

        graphConstructor.saveMatrix();
//        graphConstructor.saveEvidenceVectors();
        graphConstructor.saveCombinedEvidenceVectors();

        return docResults;
    }

    private static DocResult[] testDoc(Doc doc) {
        DocResult[] docResults = new GraphConstructor(doc).process();
        docResults[0].print("base\t");
        docResults[1].print("final\t");
        return docResults;
    }
}
