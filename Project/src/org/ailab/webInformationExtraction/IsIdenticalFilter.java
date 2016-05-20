package org.ailab.webInformationExtraction;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;

/**
 * 统一节点过滤器
 */
public class IsIdenticalFilter implements NodeFilter {
    public Node mNode;

    public IsIdenticalFilter(Node mNode) {
        this.mNode = mNode;
    }

    public boolean accept(Node node) {
        return isIdentical(node, mNode);
    }

    public static boolean isIdentical(Node n0, Node n1){
        return n0.getStartPosition() == n1.getStartPosition() && n0.getEndPosition() == n1.getEndPosition();
    }
}
