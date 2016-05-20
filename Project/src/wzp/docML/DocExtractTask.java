package wzp.docML;

import org.ailab.entityLinking.wim.doc.Doc;
import org.ailab.entityLinking.wim.doc.DocBL;
import org.ailab.entityLinking.wim.docContent.DocContent;
import org.ailab.entityLinking.wim.docContent.DocContentBL;
import org.ailab.wimfra.util.FileUtil;
import org.apache.log4j.Logger;
import wzp.newsML.NewsParser;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WZP
 * Date: 15-12-21
 * Time: 下午4:33
 * 从新闻文本中抽取出训练数据对应的原始文本
 */
public class DocExtractTask {
    private Logger logger = Logger.getLogger(DocExtractTask.class.getName());
    private int match_length = 40;

    /**
     * 从新闻文本中抽取训练集中的原文
     * @param folder
     */
    public void ExtractDoc(String folder) {
        DocContentBL docContentBL = new DocContentBL();
        docContentBL.delete();
        DocBL docBL = new DocBL();
        List<Doc> doc_list = docBL.getList();
        List<String> newsFile_list = FileUtil.getFilesRecursively(folder);
        for (int i=1; i<=newsFile_list.size();i++) {
            logger.info("开始抽取第"+i+"/"+newsFile_list.size()+"篇文章");
            String newFile = newsFile_list.get(i-1);
            NewsParser newsParser = new NewsParser(newFile);
            newsParser.parseText();
            String newsText = newsParser.getNews().getText();
            for (int j=1;j<=doc_list.size();j++) {
                boolean match_flag = true;
                Doc doc = doc_list.get(j-1);
                String []tokens = doc.getTokens().split("\n");
                for (int k=0; k<tokens.length;k++) {
                    if (newsText.indexOf(tokens[k])<0) {
                        match_flag = false;
                        break;
                    } else {
                        match_flag &= true;
                    }
                }
                if (match_flag) {
                    DocContent docContent = new DocContent();
                    docContent.setDocId(doc.getId());
                    docContent.setContent(newsText);
                    docContentBL.insert(docContent);
                }
            }
            logger.info("完成抽取"+i+"/"+newsFile_list.size()+"篇文章");
        }
    }
}
