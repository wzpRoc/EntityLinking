package org.ailab.entityLinking_old.corpus;

/**
 * User: Lu Tingming
 * Date: 15-5-17
 * Time: 下午6:03
 * Desc:
 */
public enum CorpusName {
    trainingCorpus("trainingCorpus"),
    wikiAbstCorpus("wikiAbstCorpus"),
    ;

    private String name;

    private CorpusName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
