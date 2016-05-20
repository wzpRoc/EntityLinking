package org.ailab.entityLinking.graph;

import org.ailab.entityLinking.evaluation.DocResult;
import org.ailab.entityLinking.graph.debug.*;
import org.ailab.entityLinking.wim.candidateEntity.CandidateEntity;
import org.ailab.entityLinking.wim.doc.Doc;
import org.ailab.entityLinking.wim.mention.Mention;
import org.ailab.entityLinking.wim.mention.MentionBL;
import org.ailab.tool.EntityToEntityLink.EERelatednessMap;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * User: lutingming
 * Date: 16-1-5
 * Time: 上午12:09
 */
public class GraphConstructor {
    private final double DEFAULT_NORMALIZED_PROB = 0;
    private Logger logger = Logger.getLogger(GraphConstructor.class);
    public int maxMentions = -1;
    public int maxCandidates = 30;
    public int iterations = 100;
    public boolean printAnalysis = false;

    private Doc doc;
    private List<Mention> mentionList;
    private Map<Integer, Mention> idToMentionMap;
    private List<CandidateEntity> candidateList;
    private List<Node> nodeList;
    private Vector startVector;         // s
    private Vector evidenceVector;      // r
    private Matrix transferMatrix;      // T

    private double namita = 0.1;
    private Vector startVectorMultipliedByNamita;

    private int mentionTotal;
    private double sumOfMentionInitialWeight;
    private int candidateTotal;
    private int nodeTotal;
    private List<Vector> evidenceVectorList;
    private List<Vector> combinedEvidenceVectorList;
    private Set<Integer> mentionEndNodeIdxSet;
    private Set<Integer> mentionCorrectNodeIdxSet;


    public GraphConstructor(Doc doc) {
        this.doc = doc;
    }


    private void init() {
        if (doc.mentionList == null) {
            mentionList = (new MentionBL()).getListWithCandidatesByDocId(doc.getId(), maxMentions, maxCandidates);
        } else {
            mentionList = doc.mentionList;
        }
        mentionTotal = mentionList.size();

        idToMentionMap = new HashMap<Integer, Mention>(mentionTotal);
        sumOfMentionInitialWeight = 0;
        candidateList = new ArrayList<CandidateEntity>();
        mentionEndNodeIdxSet = new HashSet<Integer>();
        mentionCorrectNodeIdxSet = new HashSet<Integer>();
        for (Mention mention : mentionList) {
            idToMentionMap.put(mention.getId(), mention);
            sumOfMentionInitialWeight += mention.getInitialWeightInDoc();

            mention.beginNodeIdx = mentionTotal + candidateList.size();
            candidateList.addAll(mention.getCandidateList());
            mention.endNodeIdx = mentionTotal + candidateList.size();
            mentionEndNodeIdxSet.add(mention.endNodeIdx);
            mention.correctNodeIdx = -1;
            for (int i = 0; i < mention.getCandidateList().size(); i++) {
                if (mention.getEntityId() == mention.getCandidateList().get(i).getEntityId()) {
                    mention.correctNodeIdx = mention.beginNodeIdx + i;
                    mentionCorrectNodeIdxSet.add(mention.correctNodeIdx);
                }
            }
        }
        candidateTotal = candidateList.size();

        nodeTotal = mentionTotal + candidateTotal;
        nodeList = new ArrayList<Node>(nodeTotal);
        nodeList.addAll(mentionList);
        nodeList.addAll(candidateList);
    }


    public DocResult[] process() {
        init();

        // 1. weight(m) & p(ce|m) -> startVector
        createStartVector();
        startVectorMultipliedByNamita = startVector.multiply(namita);
        // 2. p(ce|m) & sr(ce_i, ce_j) -> transferMatrix
        createTransferMatrix();

        // 3. 根据evidenceVector求精度
        // 对每个mention，打印正确答案的排序
        Vector evidenceVector = startVector.clone();
        evidenceVectorList = new ArrayList<Vector>();
        evidenceVectorList.add(evidenceVector);

        combinedEvidenceVectorList = new ArrayList<Vector>();
        Vector combinedEvidenceVector = createCombinedEvidenceVector(evidenceVector);
        combinedEvidenceVectorList.add(combinedEvidenceVector);

        DocResult docResult_first = analysis(0, combinedEvidenceVector);

        for (int i = 1; i <= iterations; i++) {
            evidenceVector = calculate(evidenceVector);
            if (printAnalysis) {
                analysis(i, combinedEvidenceVector);
            }
            evidenceVectorList.add(evidenceVector);
            combinedEvidenceVector = createCombinedEvidenceVector(evidenceVector);
            combinedEvidenceVectorList.add(combinedEvidenceVector);
        }
        DocResult docResult_final = analysis(iterations, combinedEvidenceVector);

//        analysisMention(1);
//        MatrixPrinter.print(mentionList, mentionCorrectNodeIdxSet, mentionEndNodeIdxSet, nodeList, transferMatrix);
//        EvidenceVectorPrinter.print(mentionList, mentionCorrectNodeIdxSet, mentionEndNodeIdxSet, nodeList, evidenceVectorList);

        return new DocResult[]{docResult_first, docResult_final};
    }


    protected void analysisMention(int mentionIdx) {
        Mention mention = mentionList.get(mentionIdx);
        System.out.println("---------------------------------------------" + mention.toString());
        // 打印候选实体
        System.out.print("#\t");
        for (CandidateEntity ce : mention.getCandidateList()) {
            System.out.print(ce.getWikiTitle() + "\t");
        }
        System.out.println();

        // 打印候选实体的评分
        for (int i_calc = 0; i_calc < evidenceVectorList.size(); i_calc++) {
            System.out.print(i_calc + "\t");
            Vector evidenceVector = evidenceVectorList.get(i_calc);
            for (int i = mention.beginNodeIdx; i < mention.endNodeIdx; i++) {
                System.out.print(evidenceVector.get(i) + "\t");
            }
            System.out.println();
        }
    }


    /**
     * 分析实验结果
     *
     * @param calcIdx        计算轮次
     * @param evidenceVector 证据向量
     */
    protected DocResult analysis(int calcIdx, Vector evidenceVector) {
        int errorCount = 0;
        int errorScore = 0;
        int[] correctCandidateRanks = new int[mentionTotal];

        for (int i_mention = 0; i_mention < mentionList.size(); i_mention++) {
            Mention mention = mentionList.get(i_mention);
            int correctCandidateRank;
            if (mention.correctNodeIdx == -1) {
                // 不存在正确的候选实体
                correctCandidateRank = -1;
                errorCount++;
            } else {
                double eviScoreOfCorrectCandidate = evidenceVector.get(mention.correctNodeIdx);
                correctCandidateRank = -1;
                for (int i = mention.beginNodeIdx; i < mention.endNodeIdx; i++) {
                    if (evidenceVector.get(i) >= eviScoreOfCorrectCandidate) {
                        correctCandidateRank++;
                    }
                }
                if (correctCandidateRank == -1) {
                    throw new RuntimeException("Can not find correct candidate for mention " + i_mention + ": " + mention);
                } else if (correctCandidateRank == 0) {
                    // ok
                } else {
                    errorCount++;
                    errorScore += correctCandidateRank;
                }
            }

            correctCandidateRanks[i_mention] = correctCandidateRank;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(calcIdx).append(":\t");
        for (int rank : correctCandidateRanks) {
            sb.append(rank).append('\t');
        }
        sb.append(errorCount).append('\t');
        sb.append(errorScore);

        System.out.println(sb.toString());

        // 记录评估结果
        DocResult docResult = new DocResult(doc.getId());
        docResult.mentionTotal = mentionTotal;
        docResult.mentionCorrect = mentionTotal - errorCount;

        return docResult;
    }

    protected Vector calculate(Vector evidenceVector) {
        Vector newEvidenceVector = transferMatrix.multiply(evidenceVector);
        newEvidenceVector.multiply(1 - namita);
        newEvidenceVector.plusWithSelf(startVectorMultipliedByNamita);

        return newEvidenceVector;
    }

    private void createStartVector() {
        logger.info("createStartVector");

        startVector = new Vector(nodeTotal);
        // 1. mention weight
        for (Mention mention : mentionList) {
            double normalizedWeight = mention.getInitialWeightInDoc() / sumOfMentionInitialWeight;
            startVector.add(normalizedWeight);
        }
        // 2. mention->candidate
        for (Mention mention : mentionList) {
            for (CandidateEntity candidate : mention.getCandidateList()) {
                double normalizedProb;
                if (mention.getSumProbOfMentionToEntity() == 0) {
                    normalizedProb = DEFAULT_NORMALIZED_PROB;
                } else {
                    normalizedProb = candidate.getProbOfMentionToEntity() / mention.getSumProbOfMentionToEntity();
                }
                startVector.add(normalizedProb);
            }
        }
    }

    private void createTransferMatrix() {
        logger.info("createTransferMatrix");

        transferMatrix = new Matrix(nodeTotal, nodeTotal);

        // 矩阵上面 mentionTotal 行全为0
        for (int rowIdx = 0; rowIdx < nodeTotal; rowIdx++) {
//            logger.debug("createTransferMatrix rowIdx=" + rowIdx);
            Node rowNode = nodeList.get(rowIdx);
            Vector matrixRow = transferMatrix.getRow(rowIdx);
            for (int colIdx = 0; colIdx < nodeTotal; colIdx++) {
                Node colNode = nodeList.get(colIdx);
                double probFromColToRow;
                if (rowNode instanceof Mention) {
                    probFromColToRow = 0;
                } else {
                    // colNode -> rowNode
                    // rowNode is a candidate
                    CandidateEntity rowCandidate = (CandidateEntity) rowNode;
                    if (colNode instanceof Mention) {
                        // mention -> candidate
                        Mention colMention = (Mention) colNode;
                        if (colMention.getId() == rowCandidate.getMentionId()) {
                            probFromColToRow = rowCandidate.getProbOfMentionToEntity();
                        } else {
                            probFromColToRow = 0;
                        }
                    } else {
                        // candidate -> candidate
                        CandidateEntity colCandidate = (CandidateEntity) colNode;
                        if (colCandidate.getMentionId() == rowCandidate.getMentionId()) {
                            probFromColToRow = 0;
                        } else {
                            probFromColToRow = EERelatednessMap.getInstance().get(colCandidate.getEntityId(), rowCandidate.getEntityId());
                            if (Double.isNaN(probFromColToRow)) {
                                // 没有查询到
                                probFromColToRow = 0;
                            }
                        }
                    }
                }

                matrixRow.add(probFromColToRow);
            }
        }

        transferMatrix.normalizeByRow();
    }


    /**
     * 迭代计算
     */
    public void inference() {

    }


    public void saveMatrix() {
        MatrixPrinter.print(mentionList, mentionCorrectNodeIdxSet, mentionEndNodeIdxSet, nodeList, transferMatrix);

    }

    public void saveEvidenceVectors() {
        EvidenceVectorPrinter.print(mentionList, idToMentionMap, mentionCorrectNodeIdxSet, mentionEndNodeIdxSet, nodeList, evidenceVectorList);
    }

    public void saveCombinedEvidenceVectors() {
        EvidenceVectorPrinter.print(mentionList, idToMentionMap, mentionCorrectNodeIdxSet, mentionEndNodeIdxSet, nodeList, combinedEvidenceVectorList);
    }

    private Vector createCombinedEvidenceVector(Vector evidenceVector) {
        Vector combinedEvidenceVector = new Vector(evidenceVector.size());
        for (int rowIdx = 0; rowIdx < evidenceVector.size(); rowIdx++) {
            double evidence = evidenceVector.get(rowIdx);
            double combinedEvidence;
            if (rowIdx < mentionTotal) {
                combinedEvidence = evidence;
            } else {
                CandidateEntity candidate = (CandidateEntity) nodeList.get(rowIdx);
                combinedEvidence = evidence * candidate.getProbOfMentionToEntity();
            }
            combinedEvidenceVector.add(combinedEvidence);
        }

        return combinedEvidenceVector;
    }
}
