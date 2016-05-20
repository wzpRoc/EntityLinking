package org.ailab.wikipedia.wim.infoboxItem;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-01-02
 * 功能描述：信息框条目业务逻辑
 */
public class InfoboxItemBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(InfoboxItemBL.class);
    protected InfoboxItemDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<InfoboxItem> dtoList;
    public static Map<Integer, InfoboxItem> idToDtoMap;

    static {
        try {
            InfoboxItemBL projectBL = new InfoboxItemBL();
            infoboxItemBL.reloadCache();
            ValueAndLabelListCache.register("infoboxItem", infoboxItemBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static InfoboxItem getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = InfoboxItemBL.getByIdFromCache(InfoboxItemBL.class, id);
        if (dto != null) {
            return ((InfoboxItem) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public InfoboxItemBL(){
        this.dao = new InfoboxItemDAO();
        super.dao = this.dao;
    }


    public int deleteByArticleId(int articleId) throws SQLException {
        return dao.deleteByCondition("articleId=?", articleId);
    }
}
