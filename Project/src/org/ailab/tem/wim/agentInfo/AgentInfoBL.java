package org.ailab.tem.wim.agentInfo;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;


/**
 * 作	者:  OneChen
 * 创建日期: 2014-05-04
 * 功能描述：代理商业务逻辑
 */
public class AgentInfoBL extends BaseBL {

	/** 日志工具 */
    private Logger logger = Logger.getLogger(AgentInfoBL.class);

   // 代理商数据库控制类
    protected AgentInfoDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<AgentInfo> dtoList;
    public static Map<Integer, AgentInfo> idToDtoMap;

    static {
        try {
            AgentInfoBL projectBL = new AgentInfoBL();
            agentInfoBL.reloadCache();
            ValueAndLabelListCache.register("agentInfo", agentInfoBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static AgentInfo getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = AgentInfoBL.getByIdFromCache(AgentInfoBL.class, id);
        if (dto != null) {
            return ((AgentInfo) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public AgentInfoBL(){
        this.dao = new AgentInfoDAO();
        super.dao = this.dao;
    }



}
