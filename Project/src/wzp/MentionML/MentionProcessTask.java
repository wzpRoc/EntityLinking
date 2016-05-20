package wzp.MentionML;

import org.ailab.entityLinking.wim.doc.Doc;
import org.ailab.entityLinking.wim.doc.DocBL;
import org.ailab.entityLinking.wim.docMentionFreq.DocMentionFreq;
import org.ailab.entityLinking.wim.docMentionFreq.DocMentionFreqBL;
import org.ailab.entityLinking.wim.mention.Mention;
import org.ailab.entityLinking.wim.mention.MentionBL;
import org.ailab.wimfra.util.CountTable;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WZP
 * Date: 15-12-24
 * Time: 下午9:29
 * 提取mention表中的tf,idf,tfidf
 */
public class MentionProcessTask {
    private Logger logger = Logger.getLogger(MentionProcessTask.class.getName());

    public void mentionProcess(boolean caseSensitive) throws Exception {
        MentionBL mentionBL = new MentionBL();
        // 数据集中所有mention的列表
        List<Mention> mentionList = mentionBL.getList();
        // 数据集中每个mention在文档中出现的次数
        CountTable mentionCountTable = new CountTable();
        // 键的方式为 docId@mentionName
        for (Mention mention : mentionList) {
            mentionCountTable.add(mention.getDocId()+"@"+(caseSensitive?mention.getName():mention.getName().toUpperCase()));
        }
        DocMentionFreqBL docMentionFreqBL = new DocMentionFreqBL();
        // 新闻文本中mention的信息（idf）列表
        List<DocMentionFreq> docMentionFreqList = docMentionFreqBL.getList();
        HashMap<String, DocMentionFreq> mention_freq_map = new HashMap<String, DocMentionFreq>();
        for (DocMentionFreq docMentionFreq : docMentionFreqList)
            mention_freq_map.put(docMentionFreq.getMentionName(), docMentionFreq);
        DocBL docBL = new DocBL();
        List<Doc> docList = docBL.getList();
        HashMap<Integer, Doc> doc_map = new HashMap<Integer, Doc>();
        for (Doc doc : docList)
            doc_map.put(doc.getId(), doc);
        int index = 1;
        int total = mentionList.size();
        for (Mention mention : mentionList) {
            logger.info("开始提取第"+index+"/"+total+"个mention的TFIDF...");
            DocMentionFreq docMentionFreq = mention_freq_map.get(caseSensitive?mention.getName():mention.getName().toUpperCase());
            if (docMentionFreq == null)
                continue;
            mention.setIdf(docMentionFreq.getIdf());
            int doc_id = mention.getDocId();
            Doc doc = doc_map.get(doc_id);
            if (doc == null)
                continue;
            String []tokens = doc.getTokens().split("\n");
            int length = tokens.length;
            int count = mentionCountTable.getCount(mention.getDocId()+"@"+(caseSensitive?mention.getName():mention.getName().toUpperCase()));
            mention.setTf((count*1.0)/(length*1.0));
            mention.setTfidf(mention.getTf()*mention.getIdf());
            logger.info("完成提取第"+index+"/"+total+"个mention的TFIDF.");
            index++;
        }
        mentionBL.batchUpdate(mentionList);
    }
}
