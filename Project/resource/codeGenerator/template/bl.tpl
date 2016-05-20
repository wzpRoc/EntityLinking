package %packagePrefix%.%tableName%;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Vector;


/**
 * 作	者: %user%
 * 创建日期: %date%
 * 功能描述：%tableComment%业务逻辑
 */
public class %tableName_upInitial%BL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(%tableName_upInitial%BL.class);
    protected %tableName_upInitial%DAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<%tableName_upInitial%> dtoList;
    public static Map<Integer, %tableName_upInitial%> idToDtoMap;

    static {
        try {
            %tableName_upInitial%BL projectBL = new %tableName_upInitial%BL();
            %tableName%BL.reloadCache();
            ValueAndLabelListCache.register("%tableName%", %tableName%BL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static %tableName_upInitial% getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = %tableName_upInitial%BL.getByIdFromCache(%tableName_upInitial%BL.class, id);
        if (dto != null) {
            return ((%tableName_upInitial%) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public %tableName_upInitial%BL(){
        this.dao = new %tableName_upInitial%DAO();
        super.dao = this.dao;
    }


}
