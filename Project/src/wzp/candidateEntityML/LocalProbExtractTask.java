package wzp.candidateEntityML;

import nlp.stanford.Parameters;
import nlp.stanford.StanfordUtil;
import org.ailab.entityLinking.wim.candidateEntity.*;
import org.ailab.entityLinking.wim.doc.*;
import org.ailab.entityLinking.wim.docWordFreq.DocWordFreq;
import org.ailab.entityLinking.wim.docWordFreq.DocWordFreqDict;
import org.ailab.entityLinking.wim.entity.*;
import org.ailab.entityLinking.wim.mention.*;
import org.ailab.tool.wikiProcess.p3_abst.PageAbstCache;
import org.ailab.wimfra.util.CountTable;
import org.ailab.wimfra.util.GeometricUtil;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: WZP
 * Date: 15-12-29
 * Time: 下午8:04
 * 计算命名实体指称到候选实体的局部概率
 */

public class LocalProbExtractTask {
    private final int maxPageAbstCacheSize = 100000;
    private Logger logger = Logger.getLogger(LocalProbExtractTask.class.getName());

    public void extractProbOfMentionToEntity(boolean caseSensitive) throws Exception {
        PageAbstCache pageAbstCache = new PageAbstCache(maxPageAbstCacheSize);
        // 载入停用词表
        HashSet<String> stopWordSet = StanfordUtil.getStopWords(caseSensitive);
        // 载入mention的原始文档
        DocBL docBL = new DocBL();
        List<Doc> docList = docBL.getList();
        HashMap<Integer, Doc> docMap = new HashMap<Integer, Doc>();
        for (Doc doc : docList)
            docMap.put(doc.getId(), doc);
        // 载入mention表
        MentionBL mentionBL = new MentionBL();
        List<Mention> mentionList = mentionBL.getList();
        // mention_id到mention的映射表
        HashMap<Integer, Mention> mentionMap = new HashMap<Integer, Mention>();
        for (Mention mention : mentionList)
            mentionMap.put(mention.getId(), mention);
        // 获取待计算的候选实体列表
        CandidateEntityBL candidateEntityBL = new CandidateEntityBL();
        List<CandidateEntity> candidateEntityList = candidateEntityBL.getList();
        int index = 1, total = candidateEntityList.size();
        for (CandidateEntity candidateEntity : candidateEntityList) {
            logger.info("开始提取第"+index+"/"+total+"个mention的指称到候选实体的概率");
            int entityId = candidateEntity.getEntityId();
            int docId = candidateEntity.getDocId();
            int mentionId = candidateEntity.getMentionId();
            // 根据entity的id从数据库获取entity
            String pageAbst = pageAbstCache.get(entityId);
            if (pageAbst == null) {
                logger.error("没有提取到entityId为"+entityId+"的候选实体");
                index++;
                continue;
            }
            // 根据mention的docId从获取mention所处的原始文档
            Doc doc = docMap.get(docId);
            if (doc == null) {
                logger.error("没有提取到docId为"+docId+"的文档");
                index++;
                continue;
            }
            // 根据mention的id获取相应的mention
            Mention mention = mentionMap.get(mentionId);
            if (mention == null) {
                logger.error("没有提取到mentionId为"+mentionId+"的mention");
                index++;
                continue;
            }
            String[] docTokens = doc.getTokens().split("\n");
            List<String> docTokenList = new ArrayList<String>();
            for (String docToken : docTokens)
                docTokenList.add(docToken);
            List<String> entityTokenList = StanfordUtil.Tokenize(pageAbst);
            double localCapatibility = getLocalProbability(docTokenList, mention.getStartIdx(), mention.getEndIdx(), entityTokenList, caseSensitive);
            if (Double.isNaN(localCapatibility))
                localCapatibility = 0.0;
            candidateEntity.setProbOfMentionToEntity(localCapatibility);
            logger.info("完成提取第"+index+"/"+total+"个mention的指称到候选实体的概率");
            index++;
        }
        // 按照mentionId分组，对候选实体排序
        // 第一步：分组
        HashMap<Integer, List<CandidateEntity>> candidateEntityMap = new HashMap<Integer, List<CandidateEntity>>();
        for (CandidateEntity candidateEntity : candidateEntityList) {
            int mentionId = candidateEntity.getMentionId();
            if (!candidateEntityMap.containsKey(mentionId))
                candidateEntityMap.put(mentionId, new ArrayList<CandidateEntity>());
            candidateEntityMap.get(mentionId).add(candidateEntity);
        }
        // 第二步：排序
        candidateEntityList.clear();
        for (Map.Entry<Integer, List<CandidateEntity>> entry : candidateEntityMap.entrySet()) {
            List<CandidateEntity> candidateEntities = entry.getValue();
            try {
                Collections.sort(candidateEntities);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            for (int i=0;i<candidateEntities.size();i++) {
                candidateEntities.get(i).setRankByProbOfMentionToEntity(candidateEntities.size()-i-1);
                candidateEntityList.add(candidateEntities.get(i));
            }
        }
        candidateEntityBL.batchUpdate(candidateEntityList);
    }

    /**
     * 计算两个token序列的局部相似度
     * @param docTokenList mention所在上下文的token序列
     * @param startIdx mention处于token的开始位置
     * @param endIdx mention处于token的结束位置（mention最后一个token的下一个位置）
     * @param entityTokenList entity摘要的token序列
     * @return 局部相似度
     */
    public double getLocalProbability(List<String> docTokenList, int startIdx, int endIdx, List<String> entityTokenList, boolean caseSensitive) throws Exception{
        // 根据设定的窗口大小截取mention上下文的token序列
        int tokenGramSize = Parameters.tokenGramSize;
        int docWinStartIndex = Math.max(startIdx - tokenGramSize/2, 0);
        int endWinEndIndex = Math.min(endIdx + tokenGramSize / 2, docTokenList.size());
        docTokenList = docTokenList.subList(docWinStartIndex, endWinEndIndex);
        if (docTokenList.size() == 0 || entityTokenList.size() == 0)
            return 0.0;
        // 所有出现在mention上下文和entity上下文的非停用词token
        Set<String> tokens = new HashSet<String>();
        // 出现在mention上下文的token的次数
        CountTable docCountTable = new CountTable();
        // entity上下文的token的次数
        CountTable entityCountTable = new CountTable();
        // 遍历mention的token序列
        for (String docToken : docTokenList) {
            if (StanfordUtil.getStopWords(caseSensitive).contains(docToken))
                continue;
            tokens.add(caseSensitive?docToken:docToken.toUpperCase());
            docCountTable.add(caseSensitive?docToken:docToken.toUpperCase());
        }
        // 遍历entity的token序列
        for (String entityToken : entityTokenList) {
            if (StanfordUtil.getStopWords(caseSensitive).contains(entityToken))
                continue;
            tokens.add(caseSensitive?entityToken:entityToken.toUpperCase());
            entityCountTable.add(caseSensitive?entityToken:entityToken.toUpperCase());
        }
        List<Double> docTokenVector = new ArrayList<Double>();
        List<Double> entityTokenVector = new ArrayList<Double>();
        // 计算mention和entity上下文的词包
        for (String token : tokens) {
            double docTf = (docCountTable.getCount(token) * 1.0) / (docTokenList.size() * 1.0);
            double entityTf = (entityCountTable.getCount(token) * 1.0) / (entityTokenList.size() * 1.0);
            DocWordFreq docWordFreq = DocWordFreqDict.getNewInstance().getDocWordFreqByName(token);
            if (docWordFreq == null)
                continue;
            double idf = docWordFreq.getIdf();
            docTokenVector.add(docTf * idf);
            entityTokenVector.add(entityTf * idf);
        }
        // 计算局部相似度
        double localCompatibility = GeometricUtil.calcCosineDistance(docTokenVector, entityTokenVector);
        return localCompatibility;
    }
}
