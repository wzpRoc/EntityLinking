package org.ailab.entityLinking_old.wim.doc;

import org.ailab.entityLinking_old.wim.AnnoState;
import org.ailab.entityLinking_old.wim.docEntity.DocEntity;
import org.ailab.entityLinking_old.wim.docEntity.DocEntityBL;
import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;
import org.ailab.wimfra.util.time.TimeUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-07
 * 功能描述：文章业务逻辑
 */
public class DocBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(DocBL.class);
    protected DocDAO dao;


    /**
     * 构造函数
     */
    public DocBL(){
        this.dao = new DocDAO();
        super.dao = this.dao;
    }


    public Doc getWithAnno(int id) throws SQLException {
        Doc doc = (Doc) this.get(id);
        if(doc.getAnnoState()!= AnnoState.notAnnotated.getValue()) {
            List<DocEntity> docEntityList = new DocEntityBL().getListByDocId(id);
            doc.setDocEntityList(docEntityList);
        }

        return doc;
    }


    public void saveAnno(Doc doc, List<DocEntity> docEntityList, int updaterId, int annoState) throws Exception {
        String now = TimeUtil.getYyyy_mm_dd_hh_mm_ss();

        doc.setAnnoState(annoState);
        doc.setUpdaterId(updaterId);
        doc.setUpdateTime(now);
        dao.updateAnno(doc);

        DocEntityBL docEntityBL = new DocEntityBL();
        docEntityBL.save(doc, docEntityList);
    }

    public Set<Integer> getDocIdSetByAnnoState(String annoState) {
        return dao.getDocIdSetByAnnoState(annoState);
    }
}
