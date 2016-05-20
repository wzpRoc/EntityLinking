package org.ailab.entityLinking_old.model;

import org.ailab.entityLinking_old.experiment.Experiment;
import org.ailab.entityLinking_old.wim.doc.Doc;
import org.ailab.entityLinking_old.wim.linkResult.LinkResult;

import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-18
 * Time: 上午1:20
 * Desc: 一篇文档的链接结果
 */
public abstract class DocLinkResult {
    protected Experiment experiment;
    public Doc doc;
    public double probability0;
    public double probability1;
    public double probabilityDiff;
    public double maxProbabilityRatio;

    public DocLinkResult(Doc doc) {
        this.doc = doc;
    }

    public abstract void processAfterClassification();

    public abstract List<LinkResult> toLinkResult(Experiment experiment);

}
