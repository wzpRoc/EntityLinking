package org.ailab.webInformationExtraction;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;

/**
 * User: Lu Timgming
 * Date: 2009-12-28
 * Time: 13:22:35
 * Desc: 具有兄弟节点
 */
public class HasSiblingFilter implements NodeFilter {
    public NodeFilter siblingFilter;

    public HasSiblingFilter(NodeFilter siblingFilter) {
        this.siblingFilter = siblingFilter;
    }

    public boolean accept(Node node) {
        Node sibling = node.getNextSibling();
        if(sibling==null){
            return false;
        } else {
            return siblingFilter.accept(sibling);
        }
    }
}