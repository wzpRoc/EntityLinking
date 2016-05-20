package org.ailab.webInformationExtraction;

import org.htmlparser.Node;
import org.htmlparser.util.NodeList;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 14-1-5
 * Time: 下午8:55
 */
public class CvNodeList extends NodeList {
    /**
     * 仅增加当前列表中没有的元素
     */
    public void addNew(NodeList list) {
        if(list == null) {
            return;
        }
        for(Node node : list.toNodeArray()) {
            addNew(node);
        }
    }

    /**
     * 仅增加当前列表中没有的元素
     */
    public void addNew(Node node) {
        if(this.contains(node)) {
            return;
        } else {
            this.add(node);
        }
    }
}
