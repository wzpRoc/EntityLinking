package org.ailab.webInformationExtraction;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;

/**
 * User: Lu Tingming
 * Date: 2012-12-17
 * Time: 21:56:39
 * Desc:
 */
public class AncestorNodeFilter implements NodeFilter {
    // 0表示不限制，1表示父节点，依此类推
    public int distance;
    // 过滤器
    public NodeFilter nf;


    public AncestorNodeFilter(int distance, NodeFilter nf) {
        this.distance = distance;
        this.nf = nf;
    }


    public boolean accept(Node node) {
        if (node == null) {
            return false;
        }

        if (distance > 0) {
            // 有距离限制
            // 寻找父节点
            Node parent = node.getParent();
            for (int i = 2; i <= distance; i++) {
                if (parent == null) {
                    return false;
                }
                parent = parent.getParent();
            }
            if (parent == null) {
                return false;
            }
            return nf.accept(parent);
        } else {
            // 没有距离限制
            Node parent = node.getParent();
            while(true) {
                if(parent == null) {
                    return false;
                }
                if(nf.accept(parent)) {
                    return true;
                } else {
                    parent = parent.getParent();
                }
            }
        }
    }
}
