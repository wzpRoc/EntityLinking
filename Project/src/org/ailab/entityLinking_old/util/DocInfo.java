package org.ailab.entityLinking_old.util;

import org.ailab.entityLinking_old.wim.doc.Doc;
import org.ailab.entityLinking_old.wim.docEntity.DocEntity;
import org.ailab.nlp.tfidf.IContent;

import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-17
 * Time: 下午4:29
 * Desc:
 */
public class DocInfo implements IContent {
    public Doc doc;
    public List<DocEntity> docEntityList;

    public double score;

    public DocInfo(Doc doc) {
        this.doc = doc;
    }

    public DocInfo(Doc doc, List<DocEntity> docEntityList) {
        this.doc = doc;
        this.docEntityList = docEntityList;
    }

    @Override
    public String getContent() {
        return doc.getContent();
    }
}
