package org.ailab.entityLinking.wim.docContent;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Vector;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-21
 * 功能描述：实体名称业务逻辑
 */
public class DocContentBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(DocContentBL.class);
    protected DocContentDAO dao;

    /**
     * 构造函数
     */
    public DocContentBL(){
        this.dao = new DocContentDAO();
        super.dao = this.dao;
    }

    /**
     * 插入一条记录
     * @param docContent
     * @return
     */
    public int insert(DocContent docContent) {
        return dao.insert(docContent);
    }

    /**
     * 删除所有记录
     * @return
     */
    public int delete() {
        return dao.deleteAll();
    }
}
