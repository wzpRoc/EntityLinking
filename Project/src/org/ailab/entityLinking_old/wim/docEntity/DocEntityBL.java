package org.ailab.entityLinking_old.wim.docEntity;

import org.ailab.entityLinking_old.wim.doc.Doc;
import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-07
 * 功能描述：文档实体业务逻辑
 */
public class DocEntityBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(DocEntityBL.class);
    protected DocEntityDAO dao;

    /**
     * 构造函数
     */
    public DocEntityBL(){
        this.dao = new DocEntityDAO();
        super.dao = this.dao;
    }

    public List<DocEntity> getListByDocId(int docId) {
        return dao.getListByDocId(docId);
    }

    public List<DocEntity> getListByDocIdList(Collection<Integer> docIdCollection) {
        List<DocEntity> list = new ArrayList<DocEntity>();
        for(int docId:docIdCollection) {
            List<DocEntity> listInOneDoc = getListByDocId(docId);
            list.addAll(listInOneDoc);
        }
        return list;
    }

    public void save(Doc doc, List<DocEntity> docEntityList) throws Exception {
        dao.deleteByDocId(doc.getId());

        if(docEntityList == null || docEntityList.size()==0) {
        } else {
            for(DocEntity docEntity : docEntityList) {
                docEntity.setDocId(doc.getId());
                docEntity.setPubdate(doc.getPubdate());
            }
            dao.batchInsert(docEntityList);
        }
    }

}
