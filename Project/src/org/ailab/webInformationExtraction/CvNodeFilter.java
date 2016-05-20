package org.ailab.webInformationExtraction;

//import org.ailab.irica.webInformationExtraction.IsIdenticalFilter;
//import org.ailab.irica.webInformationExtraction.Parser;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Tag;
import org.htmlparser.filters.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Lu Timgming
 * Date: 2009-12-28
 * Time: 13:22:35
 * Desc: 可扩展的“与”过滤器
 */
public class CvNodeFilter implements NodeFilter {
    Node parent;
    boolean recursive;
    String tagName;
    Class nodeClass;
    String textRegex;
    String text;
    ArrayList<NodeFilter> andFilterList;
    ArrayList<NodeFilter> notFilterList;
    HashMap<String, String> attributeRegexMap;
    HashMap<String, String> attributeMap;
    NodeFilter previousSiblingFilter;
    NodeFilter nextSiblingFilter;
    NodeFilter parentFilter;
    NodeFilter childFilter;
    NodeFilter grandChildFilter;

    /**
     * 是否接受指定节点
     *
     * @param node
     * @return
     */
    public boolean accept(Node node) {
        //System.out.println("accept:"+node.getClass().getSimpleName());
        if (parent != null) {
            if (!(new HasParentFilter(new IsIdenticalFilter(parent), recursive)).accept(node)) {
                return false;
            }
        }

        if (parentFilter != null) {
            if (!parentFilter.accept(node.getParent())) {
                return false;
            }
        }

        if (childFilter != null) {
            if (!hasChild(node, childFilter)) {
                return false;
            }
        }

        if (grandChildFilter != null) {
            if (!hasGrandChild(node, grandChildFilter)) {
                return false;
            }
        }

        if (previousSiblingFilter != null) {
            Node previousSibling = node.getPreviousSibling();
            if (previousSibling == null) {
                return false;
            }
            if (!previousSiblingFilter.accept(previousSibling)) {
                return false;
            }
        }

        if (nextSiblingFilter != null) {
            Node nextSibling = node.getNextSibling();
            if (nextSibling == null) {
                return false;
            }
            if (!nextSiblingFilter.accept(nextSibling)) {
                return false;
            }
        }

        if (tagName != null) {
            if (!(new TagNameFilter(tagName)).accept(node)) {
                return false;
            }
        }

        if (nodeClass != null) {
            if (!(new NodeClassFilter(nodeClass)).accept(node)) {
                return false;
            }
        }

        if (attributeRegexMap != null) {
            if (!(node instanceof Tag)) {
                return false;
            }
            Tag tag = (Tag) node;

            for (Map.Entry<String, String> entry : attributeRegexMap.entrySet()) {
                String aName = entry.getKey();
                String aValue = entry.getValue();
                if (aName != null) {
                    String realAValue = tag.getAttribute(aName);
                    if (realAValue == null) {
                        return false;
                    }
                    realAValue = realAValue.trim();
                    if (aValue != null) {
                        if (!realAValue.matches(aValue)) {
                            return false;
                        }
                    }
                }
            }
        }

        if (attributeMap != null) {
            if (!(node instanceof Tag)) {
                return false;
            }
            Tag tag = (Tag) node;

            for (Map.Entry<String, String> entry : attributeMap.entrySet()) {
                String aName = entry.getKey();
                String aValue = entry.getValue();
                if (aName != null) {
                    String realAValue = tag.getAttribute(aName);
                    if (realAValue == null) {
                        return false;
                    }
                    realAValue = realAValue.trim();
                    if (aValue != null) {
                        if (!realAValue.equals(aValue)) {
                            return false;
                        }
                    }
                }
            }
        }

        if (andFilterList != null) {
            for (NodeFilter nf : andFilterList) {
                if (!nf.accept(node)) {
                    return false;
                }
            }
        }

        if (notFilterList != null) {
            for (NodeFilter nf : notFilterList) {
                if (nf.accept(node)) {
                    return false;
                }
            }
        }

        if (text != null) {
            if (!Parser.nodeToPlainText(node).trim().equals(text)) {
                return false;
            }
        }

        // 正则匹配很耗性能，所以放在最后
        if (textRegex != null) {
            if (!Parser.nodeToPlainText(node).trim().matches(textRegex)) {
                return false;
            }
        }

        return true;
    }


    private boolean hasChild(Node node, NodeFilter nf) {
        if (node.getChildren() != null && node.getChildren().size() > 0) {
            for (Node child : node.getChildren().toNodeArray()) {
                if (nf.accept(child)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean hasGrandChild(Node node, NodeFilter nf) {
        if (node.getChildren() != null && node.getChildren().size() > 0) {
            for (Node child : node.getChildren().toNodeArray()) {
                if(child.getChildren() !=null && child.getChildren().size()>0) {
                    for (Node grandChild : child.getChildren().toNodeArray()) {
                        if (nf.accept(grandChild)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * constructor
     */
    public CvNodeFilter() {
    }

    /**
     * constructor
     */
    public CvNodeFilter(NodeFilter nf) {
        this.and(nf);
    }

    /**
     * 具有指定的父节点
     */
    public CvNodeFilter(Node parent) {
        this.parent = parent;
        this.recursive = false;
    }

    /**
     * 标签名
     */
    public CvNodeFilter(String tagName) {
        this.tagName = tagName;
        this.recursive = false;
    }

    /**
     * constructor
     */
    public CvNodeFilter(String tagName, String attributeName, String attributeValue) {
        this.tagName = tagName;
        this.addAttribute(attributeName, attributeValue);
        this.recursive = false;
    }

    /**
     * constructor
     */
    public CvNodeFilter(Class nodeClass) {
        this.nodeClass = nodeClass;
        this.recursive = false;
    }

    /**
     * constructor
     */
    public CvNodeFilter(Node parent, String tagName) {
        this.parent = parent;
        this.tagName = tagName;
    }

    public CvNodeFilter(Node parent, Class nodeClass) {
        this.parent = parent;
        this.nodeClass = nodeClass;
    }

    public CvNodeFilter(Node parent, boolean recursive) {
        this.parent = parent;
        this.recursive = recursive;
    }

    public CvNodeFilter(Node parent, boolean recursive, String tagName) {
        this.parent = parent;
        this.recursive = recursive;
        this.tagName = tagName;
    }

    public CvNodeFilter(Node parent, boolean recursive, Class nodeClass) {
        this.parent = parent;
        this.recursive = recursive;
        this.nodeClass = nodeClass;
    }

    public static CvNodeFilter get(Node parent, String tagName, String textRegex) {
        CvNodeFilter inst = new CvNodeFilter();
        inst.parent = parent;
        inst.recursive = false;
        inst.tagName = tagName;
        inst.textRegex = textRegex;

        return inst;
    }

    public static CvNodeFilter get(String tagName, String textRegex) {
        CvNodeFilter inst = new CvNodeFilter();
        inst.tagName = tagName;
        inst.textRegex = textRegex;

        return inst;
    }

    public static CvNodeFilter getByTagName(String tagName) {
        CvNodeFilter inst = new CvNodeFilter();
        inst.tagName = tagName;

        return inst;
    }

    public CvNodeFilter setParent(Node parent, boolean recursive) {
        this.parent = parent;
        this.recursive = recursive;
        return this;
    }

    public CvNodeFilter setParent(Node parent) {
        this.parent = parent;
        this.recursive = false;
        return this;
    }

    public CvNodeFilter setTagName(String tagName) {
        this.tagName = tagName;
        return this;
    }

    public CvNodeFilter setTextRegex(String textRegex) {
        this.textRegex = textRegex;
        return this;
    }

    public CvNodeFilter setText(String text) {
        this.text = text;
        return this;
    }

    public CvNodeFilter addAttribute(String name) {
        return addAttributeRegex(name, null);
    }

    public CvNodeFilter addAttributeRegex(String name, String valueRegex) {
        if (attributeRegexMap == null) {
            attributeRegexMap = new HashMap<String, String>();
        }
        attributeRegexMap.put(name, valueRegex);

        return this;
    }

    public CvNodeFilter addAttribute(String name, String value) {
        if (attributeMap == null) {
            attributeMap = new HashMap<String, String>();
        }
        attributeMap.put(name, value);

        return this;
    }

    public CvNodeFilter hasId(String value) {
        addAttribute("id", value);
        return this;
    }

    public CvNodeFilter and(NodeFilter nf) {
        if (andFilterList == null) {
            andFilterList = new ArrayList<NodeFilter>();
        }
        andFilterList.add(nf);

        return this;
    }

    public CvNodeFilter andNot(NodeFilter nf) {
        if (notFilterList == null) {
            notFilterList = new ArrayList<NodeFilter>();
        }
        notFilterList.add(nf);

        return this;
    }

    public CvNodeFilter andIsNot(Node node) {
        if (notFilterList == null) {
            notFilterList = new ArrayList<NodeFilter>();
        }
        notFilterList.add(new IsIdenticalFilter(node));

        return this;
    }


    public CvNodeFilter andHasParentFilter(NodeFilter nf) {
        this.parentFilter = nf;

        return this;
    }


    public CvNodeFilter andHasChildFilter(NodeFilter nf) {
        this.childFilter = nf;

        return this;
    }


    public CvNodeFilter andHasGrandChildFilter(NodeFilter nf) {
        this.grandChildFilter = nf;

        return this;
    }


    public CvNodeFilter andHasPreviousSiblingFilter(NodeFilter nf) {
        this.previousSiblingFilter = nf;

        return this;
    }

    public CvNodeFilter andHasNextSiblingFilter(NodeFilter nf) {
        this.nextSiblingFilter = nf;

        return this;
    }


    public static void main(String[] args) {
        CvNodeFilter result;

        result = (new CvNodeFilter("a"));

        String id = null;
        if (id != null && !"".equals(id)) {
            result.hasId(id);
        }

        result.and(new HasParentFilter(new CvNodeFilter("div", "id", "main"), true));

        NodeFilter nf = new TagNameFilter("a");
        NodeFilter nfAnd = new AndFilter();

    }
}
