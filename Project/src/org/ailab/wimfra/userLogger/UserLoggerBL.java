package org.ailab.wimfra.userLogger;


import org.ailab.wimfra.core.BLMessage;
import org.ailab.wimfra.core.BaseBL;
import org.apache.log4j.Logger;


import java.sql.SQLException;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2013-11-25
 * 功能描述：用户日志业务逻辑
 */
public class UserLoggerBL extends BaseBL {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(UserLoggerBL.class);


    /**
     * 构造函数
     */
    public UserLoggerBL(){
        dao = new UserLoggerDAO();
    }


}
