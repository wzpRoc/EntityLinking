package org.ailab.entityLinking.wim.mention;

import org.ailab.entityLinking.wim.candidateEntity.CandidateEntity;
import org.ailab.entityLinking.wim.candidateEntity.CandidateEntityBL;
import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-24
 * 功能描述：指称业务逻辑
 */
public class MentionBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(MentionBL.class);
    protected MentionDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<Mention> dtoList;
    public static Map<Integer, Mention> idToDtoMap;

    static {
        try {
            MentionBL projectBL = new MentionBL();
            mentionBL.reloadCache();
            ValueAndLabelListCache.register("mention", mentionBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static Mention getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = MentionBL.getByIdFromCache(MentionBL.class, id);
        if (dto != null) {
            return ((Mention) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public MentionBL(){
        this.dao = new MentionDAO();
        super.dao = this.dao;
    }


    /**
     * 根据docId获得mention列表
     */
    public List<Mention> getListByDocId(int docId) {
        return dao.getListByDocId(docId);
    }


    /**
     * 根据docId获得mention列表
     */
    public List<Mention> getListWithCandidatesByDocId(int docId) {
        return getListWithCandidatesByDocId(docId, 30);
    }


    /**
     * 根据docId获得mention列表
     */
    public List<Mention> getListWithCandidatesByDocId(int docId, int maxCandidates) {
        return getListWithCandidatesByDocId(docId, -1, maxCandidates);
    }
    /**
     * 根据docId获得mention列表
     */
    public List<Mention> getListWithCandidatesByDocId(int docId, int maxMentions, int maxCandidates) {
        List<Mention> mentionList = dao.getListByDocId(docId, maxMentions);

        CandidateEntityBL candidateEntityBL = new CandidateEntityBL();
        for(Mention mention : mentionList) {
            List<CandidateEntity> candidateList = candidateEntityBL.getListByMentionId(mention.getId(), maxCandidates);
            mention.setCandidateList(candidateList);
        }

        return mentionList;
    }


    /**
     * 根据mention.name去重
     */
    public List<Mention> getDistinctByNameListByDocId(int docId) {
        List<Mention> list = dao.getListByDocId(docId);
        List<Mention> result = new ArrayList<Mention>();

        Set<String> nameSet = new HashSet<String>();
        for(Mention mention : list) {
            if(nameSet.contains(mention.getName())) {
                continue;
            } else {
                nameSet.add(mention.getName());
                result.add(mention);
            }
        }
        return result;
    }




}
