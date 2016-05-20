package org.ailab.webInformationExtraction;

import org.htmlparser.Node;
import org.htmlparser.tags.LinkTag;


public class LinkFilter extends CvNodeFilter {
    private String hrefPrefix;

    public LinkFilter() {
    }

    public LinkFilter(String textRegex) {
        this.textRegex = textRegex;
    }

    public LinkFilter(String textRegex, String hrefRegex) {
        setHrefRegex(hrefRegex);
        this.textRegex = textRegex;
    }

    public LinkFilter setHrefRegex(String hrefRegex) {
        String hrefRegex1 = hrefRegex;
        if (hrefRegex1 != null) {
            this.addAttributeRegex("href", hrefRegex1);
        }

        return this;
    }

    public LinkFilter setHrefPrefix(String hrefPrefix) {
        this.hrefPrefix = hrefPrefix;

        return this;
    }

    public boolean accept(Node node) {
        if (node instanceof LinkTag) {
            LinkTag tag = (LinkTag) node;
            String href = tag.getAttribute("href");
            if (hrefPrefix != null) {
                if (href == null) {
                    return false;
                } else {
                    if(!href.startsWith(hrefPrefix)){
                        return false;
                    }
                }
            }
            return super.accept(node);
        }

        return false;
    }
}
