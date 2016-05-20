package org.ailab.webInformationExtraction;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Tag;

/**
 * User: Lu Timgming
 * Date: 2009-12-28
 * Time: 13:22:35
 * Desc: 结束标签的过滤器
 */
public class EndTagNameFilter implements NodeFilter {
    String tagName;

    public EndTagNameFilter(String tagName) {
        this.tagName = tagName;
    }

    public boolean accept(Node node) {
        return ((node instanceof Tag)
                && ((Tag)node).isEndTag ()
                && ((Tag)node).getTagName ().equalsIgnoreCase (tagName));

    }
}
