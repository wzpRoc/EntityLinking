package org.ailab.entityLinking_old.experiment;

import org.ailab.entityLinking_old.util.DocInfo;
import org.ailab.entityLinking_old.wim.doc.Doc;
import org.ailab.entityLinking_old.wim.doc.DocBL;
import org.ailab.entityLinking_old.wim.docEntity.DocEntity;
import org.ailab.entityLinking_old.wim.docEntity.DocEntityBL;
import org.apache.log4j.Logger;


import java.util.*;

/**
 * User: Lu Tingming
 * Date: 15-5-17
 * Time: 下午7:50
 * Desc:
 */
public class CorpusContext {
    protected final Logger logger = Logger.getLogger(this.getClass());
    protected Set<Integer> docIdSet;
    protected Map<Integer, DocInfo> docIdToDocInfoMap;
    protected List<DocInfo> docInfoList;
    protected Map<Integer, String> entityIdToContextMap;

    public CorpusContext(Set<Integer> docIdSet) {
        this.docIdSet = docIdSet;

        DocBL docBL = new DocBL();
        DocEntityBL docEntityBL = new DocEntityBL();

        docInfoList = new ArrayList<DocInfo>();
        docIdToDocInfoMap = new HashMap<Integer, DocInfo>();
        entityIdToContextMap = new HashMap<Integer, String>();

        for(int docId : docIdSet) {
            Doc doc = (Doc) docBL.get(docId);
            List<DocEntity> docEntityList = docEntityBL.getListByDocId(docId);

            DocInfo docInfo = new DocInfo(doc, docEntityList);
            docInfoList.add(docInfo);
            docIdToDocInfoMap.put(docId, docInfo);

            for(DocEntity docEntity : docEntityList) {
                int entityId = docEntity.getEntityId();
                String docContext = entityIdToContextMap.get(entityId);
                if (docContext == null) {
                    docContext = "";
                }
                // todo
                docContext += doc.getContent();
                entityIdToContextMap.put(entityId, docContext);
            }
        }
        logger.info("doc list loaded, size=" + docInfoList.size());
    }

    public Set<Integer> getDocIdSet() {
        return docIdSet;
    }

    public Map<Integer, DocInfo> getDocIdToDocInfoMap() {
        return docIdToDocInfoMap;
    }

    public List<DocInfo> getDocInfoList() {
        return docInfoList;
    }

    public Map<Integer, String> getEntityIdToContextMap() {
        return entityIdToContextMap;
    }
}
