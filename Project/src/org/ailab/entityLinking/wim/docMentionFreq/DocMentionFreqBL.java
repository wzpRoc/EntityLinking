package org.ailab.entityLinking.wim.docMentionFreq;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Vector;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-24
 * 功能描述：实体名称业务逻辑
 */
public class DocMentionFreqBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(DocMentionFreqBL.class);
    protected DocMentionFreqDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<DocMentionFreq> dtoList;
    public static Map<Integer, DocMentionFreq> idToDtoMap;

    static {
        try {
            DocMentionFreqBL projectBL = new DocMentionFreqBL();
            docMentionFreqBL.reloadCache();
            ValueAndLabelListCache.register("docMentionFreq", docMentionFreqBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static DocMentionFreq getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = DocMentionFreqBL.getByIdFromCache(DocMentionFreqBL.class, id);
        if (dto != null) {
            return ((DocMentionFreq) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public DocMentionFreqBL(){
        this.dao = new DocMentionFreqDAO();
        super.dao = this.dao;
    }


}
