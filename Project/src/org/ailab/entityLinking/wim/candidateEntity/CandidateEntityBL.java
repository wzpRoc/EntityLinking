package org.ailab.entityLinking.wim.candidateEntity;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-29
 * 功能描述：指称的候选实体业务逻辑
 */
public class CandidateEntityBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(CandidateEntityBL.class);
    protected CandidateEntityDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<CandidateEntity> dtoList;
    public static Map<Integer, CandidateEntity> idToDtoMap;

    static {
        try {
            CandidateEntityBL projectBL = new CandidateEntityBL();
            candidateEntityBL.reloadCache();
            ValueAndLabelListCache.register("candidateEntity", candidateEntityBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static CandidateEntity getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = CandidateEntityBL.getByIdFromCache(CandidateEntityBL.class, id);
        if (dto != null) {
            return ((CandidateEntity) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public CandidateEntityBL(){
        this.dao = new CandidateEntityDAO();
        super.dao = this.dao;
    }


    /**
     * 根据mentionId获得候选实体
     */
    public List<CandidateEntity> getListByMentionId(int mentionId) {
        return dao.getListByMentionId(mentionId, 30);
    }


    /**
     * 根据mentionId获得候选实体
     */
    public List<CandidateEntity> getListByMentionId(int mentionId, int maxCandidates) {
        return dao.getListByMentionId(mentionId, maxCandidates);
    }


}
