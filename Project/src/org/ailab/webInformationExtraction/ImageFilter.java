package org.ailab.webInformationExtraction;

import org.htmlparser.Node;

/**
 * 图片过滤器
 */
public class ImageFilter extends CvNodeFilter {
    private String srcRegex;

    public ImageFilter() {
        this.setTagName("img");
    }

    public ImageFilter(String linkRegex) {
        setSrcRegex(linkRegex);
        this.setTagName("img");
    }

    public boolean accept(Node node) {
        return super.accept(node);
    }

    public void setSrcRegex(String srcRegex) {
        this.srcRegex = srcRegex;
        if(this.srcRegex !=null){
            this.addAttributeRegex("src", this.srcRegex);
        }
    }
}