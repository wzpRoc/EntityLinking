package org.ailab.webInformationExtraction;


/**
 * 文本节点过滤器
 */
public class TextFilter extends CvNodeFilter {
    private void init() {
        this.setTagName("a");
    }

    public TextFilter() {
        init();
    }

    public TextFilter(String textRegex) {
        this.textRegex = textRegex;
        init();
    }

    public TextFilter(String prevTagName, String textRegex, String nextTagName) {
        this.previousSiblingFilter = new SimpleTagNameFilter(prevTagName);
        this.textRegex = textRegex;
        this.nextSiblingFilter = new SimpleTagNameFilter(nextTagName);
        init();
    }


}