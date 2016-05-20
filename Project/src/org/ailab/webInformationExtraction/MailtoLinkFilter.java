package org.ailab.webInformationExtraction;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Tag;

/**
 * mailto节点过滤器
 */
public class MailtoLinkFilter implements NodeFilter {
    public static final MailtoLinkFilter inst = new MailtoLinkFilter();

    public boolean accept(Node node) {
        if (node instanceof Tag) {
            Tag tag = (Tag) node;
            if (tag.getTagName().equalsIgnoreCase("a")) {
                String href = tag.getAttribute("href");
                if(href !=null){
                    if(href.startsWith("mailto:")){
                        return true;
                    }
                }
            }
        }

        return false;
    }
}