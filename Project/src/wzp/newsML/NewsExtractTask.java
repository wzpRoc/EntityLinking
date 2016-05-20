package wzp.newsML;

import nlp.stanford.StanfordUtil;
import org.ailab.entityLinking.wim.docMentionFreq.DocMentionFreq;
import org.ailab.entityLinking.wim.docMentionFreq.DocMentionFreqBL;
import org.ailab.entityLinking.wim.docWordFreq.DocWordFreq;
import org.ailab.entityLinking.wim.docWordFreq.DocWordFreqBL;
import org.ailab.entityLinking.wim.mention.Mention;
import org.ailab.wimfra.util.CountTable;
import org.ailab.wimfra.util.FileUtil;
import org.ailab.wimfra.util.ObjectAndCount;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WZP
 * Date: 15-12-20
 * Time: 下午6:33
 * To change this template use File | Settings | File Templates.
 */
public class NewsExtractTask {
    private Logger logger = Logger.getLogger(NewsExtractTask.class.getName());

    /**
     * 从新闻文本中抽取mention出现的频次
     * @param folder
     * @param caseSensitive 是否区分大小写
     */
    public void ExtractNewsForMention(String folder, boolean caseSensitive) throws Exception {
        CountTable mention_link_count = new CountTable();
        List<String> file_list = FileUtil.getFilesRecursively(folder);
        List<String> lines = FileUtil.readLines("E:\\Entity_Linking\\doc\\lutm - distinct.txt");
//        MentionBL mention_bl = new MentionBL();
//        List<Mention> mention_list = mention_bl.getList();
        List<Mention> mention_list = new ArrayList<Mention>();
        for (String line : lines) {
            if (!"\t".equals(line)) {
                Mention mention = new Mention();
                mention.setName(caseSensitive?line:line.toUpperCase());
                mention_list.add(mention);
            }
        }
        int doc_index = 1;
        int doc_count = file_list.size();
        mention_link_count.clear();
        for (String file : file_list) {
            logger.info("--开始提取第"+doc_index+"/"+doc_count+"篇新闻-------------------------------------");
            try {
                NewsParser newsParser = new NewsParser(file);
                newsParser.parseText();
                News news = newsParser.getNews();
                String news_text = caseSensitive?news.getText():news.getText().toUpperCase();
                for (Mention mention : mention_list) {
                    String mention_name = mention.getName();
                    List<Integer> index_list = new ArrayList<Integer>();
                    int start_index = 0;
                    int index = news_text.indexOf(mention_name, start_index);
                    while(index >= 0) {
                        char pre_ch = ' ', next_ch = ' ';
                        if (index > 0)
                            pre_ch = news_text.charAt(index-1);
                        if (index + mention_name.length() < news_text.length())
                            next_ch = news_text.charAt(index + mention_name.length());
                        if ((pre_ch>='a'&&pre_ch<='z')||(pre_ch>='A'&&pre_ch<='Z')||(pre_ch>='0'&&pre_ch<='9')||
                                (next_ch>='a'&&next_ch<='z')||(next_ch>='A'&&next_ch<='Z')||(next_ch>='0'&&next_ch<='9')) {
                            start_index++;
                            index = news_text.indexOf(mention_name, start_index);
                        } else {
                            mention_link_count.add(mention_name);
                            break;
                        }
                    }

                }
            } catch (Exception e) {
                logger.info("--第"+doc_index+"/"+doc_count+"篇新闻提取出错"+e.getStackTrace());
            }
            logger.info("--第"+doc_index+"/"+doc_count+"篇新闻提取完成。------------------------------");
            doc_index++;
        }
        List<ObjectAndCount> object_and_count_list = mention_link_count.toList();
        DocMentionFreqBL docMentionFreqBL = new DocMentionFreqBL();
        docMentionFreqBL.deleteAll();
        List<DocMentionFreq> docMentionFreqList = new ArrayList<DocMentionFreq>();
        for (ObjectAndCount object_and_count : object_and_count_list) {
            DocMentionFreq docMentionFreq = new DocMentionFreq();
            docMentionFreq.setMentionName((String) object_and_count.object);
            docMentionFreq.setFreq(object_and_count.count);
            docMentionFreq.setIdf(Math.log10((doc_count*1.0)/(object_and_count.count*1.0)));
            docMentionFreqList.add(docMentionFreq);
        }
        docMentionFreqBL.batchInsert(docMentionFreqList);
    }

    /**
     * 从新闻文本中抽取word出现的频次
     * @param newsFolder 新闻文本所在的文件夹
     */
    public void ExtractNewsForWord(String newsFolder, boolean caseSensitive) throws Exception{
        // 读取停用词表
        HashSet<String> lines = StanfordUtil.getStopWords(caseSensitive);
        HashSet<String> stopWord_set = new HashSet<String>();
        for (String line : lines) {
            if (!"\t".equals(line)) {
                stopWord_set.add(caseSensitive?line:line.toUpperCase());
            }
        }
        //
        CountTable wordCountTable = new CountTable();
        // 获取所有新闻文件的路径
        List<String> file_list = FileUtil.getFilesRecursively(newsFolder);
        int doc_index = 1, doc_count = file_list.size();
        for (String file : file_list) {
            logger.info("--开始提取第"+doc_index+"/"+doc_count+"篇新闻-------------------------------------");
            try {
                NewsParser newsParser = new NewsParser(file);
                newsParser.parseText();
                News news = newsParser.getNews();
                String news_text = news.getText();
                List<String> word_list = StanfordUtil.Tokenize(news_text);
                HashSet<String> already_set = new HashSet<String>();
                for (String word : word_list){
                    if (!caseSensitive)
                        word = word.toUpperCase();
                    // 停用词或同一篇文章重复出现的词不再计数
                    if (already_set.contains(word) || stopWord_set.contains(word))
                        continue;
                    already_set.add(word);
                    wordCountTable.add(word);
                }
            } catch (Exception e) {
                logger.info("--第"+doc_index+"/"+doc_count+"篇新闻提取出错"+e.getStackTrace());
            }
            logger.info("--第"+doc_index+"/"+doc_count+"篇新闻提取完成。------------------------------");
            doc_index++;
        }
        // 删除词频表
        DocWordFreqBL docWordFreqBL = new DocWordFreqBL();
        docWordFreqBL.deleteAll();
        // 获得词频列表
        List<ObjectAndCount> object_and_count_list = wordCountTable.toList();
        // 将词频插入数据库
        List<DocWordFreq> docWordFreqList = new ArrayList<DocWordFreq>();
        for (ObjectAndCount object_and_count : object_and_count_list) {
            DocWordFreq docWordFreq = new DocWordFreq();
            docWordFreq.setWordName((String)object_and_count.object);
            docWordFreq.setFreq(object_and_count.count);
            docWordFreq.setIdf(Math.log10((doc_count*1.0)/(object_and_count.count*1.0)));
            docWordFreqList.add(docWordFreq);
        }
        docWordFreqBL.batchInsert(docWordFreqList);
    }
}
