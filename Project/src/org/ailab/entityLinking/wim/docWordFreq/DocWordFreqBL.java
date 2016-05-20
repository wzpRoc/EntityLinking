package org.ailab.entityLinking.wim.docWordFreq;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Vector;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-29
 * 功能描述：新闻数据中词出现的频次业务逻辑
 */
public class DocWordFreqBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(DocWordFreqBL.class);
    protected DocWordFreqDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<DocWordFreq> dtoList;
    public static Map<Integer, DocWordFreq> idToDtoMap;

    static {
        try {
            DocWordFreqBL projectBL = new DocWordFreqBL();
            docWordFreqBL.reloadCache();
            ValueAndLabelListCache.register("docWordFreq", docWordFreqBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static DocWordFreq getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = DocWordFreqBL.getByIdFromCache(DocWordFreqBL.class, id);
        if (dto != null) {
            return ((DocWordFreq) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public DocWordFreqBL(){
        this.dao = new DocWordFreqDAO();
        super.dao = this.dao;
    }


}
