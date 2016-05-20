package org.ailab.webInformationExtraction;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;

/**
 * User: Lu Timgming
 * Date: 2009-12-28
 * Time: 13:22:35
 * Desc: 具有兄弟节点（跳过空白文本节点）
 */
public class HasSiblingIgnoreBTFilter implements NodeFilter {
    NodeFilter nf;

    public HasSiblingIgnoreBTFilter(NodeFilter nf) {
        this.nf = nf;
    }

    public boolean accept(Node node) {
        Node sibling = Parser.getNextSiblingIgnoreBT(node);
        if(sibling==null){
            return false;
        }
        return nf.accept(sibling);
    }
}