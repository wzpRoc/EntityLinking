package org.ailab.wikipedia.wim.article;

import org.ailab.wikipedia.wim.infoboxItem.InfoboxItem;
import org.ailab.wikipedia.wim.infoboxItem.InfoboxItemBL;
import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.util.List;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-01-02
 * 功能描述：文章业务逻辑
 */
public class ArticleBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(ArticleBL.class);
    protected ArticleDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<Article> dtoList;
    public static Map<Integer, Article> idToDtoMap;

    static {
        try {
            ArticleBL projectBL = new ArticleBL();
            articleBL.reloadCache();
            ValueAndLabelListCache.register("article", articleBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static Article getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = ArticleBL.getByIdFromCache(ArticleBL.class, id);
        if (dto != null) {
            return ((Article) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public ArticleBL(){
        this.dao = new ArticleDAO();
        super.dao = this.dao;
    }


    public int updateList(List<Article> articleList) throws Exception {
        int affectedRowCount = 0;
        for(Article article : articleList) {
            affectedRowCount += update(article);
        }
        return affectedRowCount;
    }


    public int update(Article article) throws Exception {
        final int affectedRowCount = dao.update(article);

        InfoboxItemBL infoboxItemBL = new InfoboxItemBL();
        infoboxItemBL.deleteByArticleId(article.getId());
        if(article.getInfobox()!=null && article.getInfobox().size()>0) {
            for(InfoboxItem item : article.getInfobox()) {
                item.setArticleId(article.getId());
            }
            infoboxItemBL.insert(article.getInfobox());
        }

        return affectedRowCount;
    }
}
