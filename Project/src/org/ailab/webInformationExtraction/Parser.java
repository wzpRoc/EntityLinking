package org.ailab.webInformationExtraction;

//import org.ailab.irica.webInformationExtraction.*;
//import org.ailab.irica.webInformationExtraction.IsIdenticalFilter;
//import lutm.util.HtmlUtil;

import org.ailab.wimfra.util.FileUtil;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Tag;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.*;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Lu Timgming
 * Date: 2009-12-28
 * Time: 13:22:35
 * Desc: 解析器
 */
public class Parser extends org.htmlparser.Parser {
    // 被解析文件的路径
    public String path;

    /**
     * constructor
     */
    public Parser() {
    }

    /**
     * constructor
     */
    public Parser(String resource) throws ParserException {
        super();
        path = resource;
        this.setResource(resource);
        this.setEncoding("UTF-8");
    }

    /**
     * constructor
     */
    public Parser(String path, String content) throws ParserException {
        super();
        this.path = path;
        this.setResource(content);
        this.setEncoding("UTF-8");
    }

    public void setResource(String resource) throws org.htmlparser.util.ParserException {
        if (resource != null && resource.charAt(0) == 65279) {
            super.setResource(resource.substring(1));
        } else {
            super.setResource(resource);
        }
    }

    public void setPath(String path) throws Exception {
        this.path = path;
        String content = FileUtil.readFile(path);
        this.setResource(content);
        this.setEncoding("UTF-8");
    }

    public BodyTag getBodyTag() throws ParserException {
        this.reset();
        return (BodyTag) this.parse(new TagNameFilter("body")).elementAt(0);
    }

    public TitleTag getTitleTag() throws ParserException {
        this.reset();
        return (TitleTag) this.parse(new TagNameFilter("title")).elementAt(0);
    }


    /**
     * body中第一个table
     *
     * @return
     */
    public TableTag getFirstTable() throws ParserException {
        BodyTag body = getBodyTag();
        NodeList children = body.getChildren();

        for (int i = 0; i < children.size(); i++) {
            Node child = children.elementAt(i);
            if (child instanceof TableTag) {
                return (TableTag) child;
            }
        }

        return null;
    }

    /**
     * 返回表格中从表头以下，第colIdx列的内容列表
     */
    public List<String> getTableContentList(NodeFilter tableNodeFilter, String headerColNameRegex) throws Exception {
        NodeList tableTagList = find(new AndFilter(tableNodeFilter, SimpleTagNameFilter.tableFilter));
        return getTableContentList(tableTagList, headerColNameRegex);
    }

    /**
     * 返回表格中从表头以下，第colIdx列的内容列表
     */
    public List<String> getTableContentList(NodeList tableTagList, String headerColNameRegex) throws Exception {
        if (tableTagList == null) return null;

        List<String> result = new ArrayList<String>();
        for (int i = 0; i < tableTagList.size(); i++) {
            TableTag tableTag = (TableTag) tableTagList.elementAt(i);
            Tag headerCell = (Tag) findLastOffspring(
                    tableTag,
                    new CvNodeFilter().setTextRegex(headerColNameRegex).and(SimpleTagNameFilter.thtdFilter),
                    2, 2);

            if (headerCell == null) {
                continue;
            }
            final NodeList tableContentNodeList = getTableContentNodeList(headerCell);
            if (tableContentNodeList != null) {
                for (int i_node = tableContentNodeList.size() - 1; i_node >= 0; i_node--) {
                    final Tag tag = (Tag) tableContentNodeList.elementAt(i_node);
                    if (tag == null) {
                        tableContentNodeList.remove(i_node);
                        continue;
                    }
                    final String colspan = tag.getAttribute("colspan");
                    if (colspan != null && !"1".equals(colspan)) {
                        tableContentNodeList.remove(i_node);
                    }
                }
                final List<String> tempResult = nodeListToPlainTexts(tableContentNodeList);
                result.addAll(tempResult);
            }
        }

        return result;
    }


    /**
     * 返回表格中从表头以下，第colIdx列的内容列表
     */
    public List<String> getTableContentList(Tag headerCell) throws ParserException {
        return nodeListToPlainTexts(getTableContentNodeList(headerCell));
    }


    /**
     * 返回表格中从表头以下，第colIdx列的内容列表
     */
    public NodeList getTableContentNodeList(Tag headerCell) throws ParserException {
        final TableRow headerRow = (TableRow) headerCell.getParent();
        final TableTag tableTag = (TableTag) headerRow.getParent();

        int headerRowIdx = rowIndexOfRowInTable(headerRow);
        int colIdx = colIndexOfCellInRow(headerCell);

        NodeList result = new NodeList();
        for (int i = headerRowIdx + 1; i < tableTag.getRowCount(); i++) {
            result.add(getCells(tableTag.getRow(i)).elementAt(colIdx));
        }

        return result;
    }


    /**
     * 得到单元格在行中的索引
     */
    public int colIndexOfCellInRow(Tag cell) throws ParserException {
        final TableRow headerRow = (TableRow) cell.getParent();
        final NodeList children = getCells(headerRow);
        for (int i = 0; i < children.size(); i++) {
            if (children.elementAt(i) == cell) {
                return i;
            }
        }

        return -1;
    }


    public NodeList getCells(TableRow row) throws ParserException {
        return findChildIn(row, SimpleTagNameFilter.thtdFilter);
    }

    /**
     * 得到行在表格中的索引
     */
    public int rowIndexOfRowInTable(TableRow row) {
        final TableTag tableTag = (TableTag) row.getParent();
        final TableRow[] rows = tableTag.getRows();
        for (int i = 0; i < rows.length; i++) {
            if (rows[i] == row) {
                return i;
            }
        }

        return -1;
    }


    public LinkTag findFirstLinkInCell(TableTag table, int rowIdx, int colIdx) {
        return (LinkTag) findFirstTagInCell(table, rowIdx, colIdx, "a");
    }

    public ImageTag findFirstImageInCell(TableTag table, int rowIdx, int colIdx) {
        return (ImageTag) findFirstTagInCell(table, rowIdx, colIdx, "img");
    }

    public ParagraphTag findFirstPInCell(TableTag table, int rowIdx, int colIdx) {
        return (ParagraphTag) findFirstTagInCell(table, rowIdx, colIdx, "p");
    }

    public String getTextInCell(TableTag table, int rowIdx, int colIdx) {
        TableRow row = table.getRow(rowIdx);
        TableColumn col = row.getColumns()[colIdx];
        return col.toPlainTextString().trim();
    }

    public TableColumn getCell(TableTag table, int rowIdx, int colIdx) {
        TableRow row = table.getRow(rowIdx);
        if (row != null && colIdx < row.getColumnCount()) {
            return row.getColumns()[colIdx];
        } else {
            return null;
        }
    }

    public Tag findFirstTagInCell(TableTag table, int rowIdx, int colIdx, String tagName) {
        TableRow row = table.getRow(rowIdx);
        TableColumn col = row.getColumns()[colIdx];

        return findFirstTagIn(col, new TagNameFilter(tagName));
    }

    public Node getFirstChild(Node ancestor, NodeFilter nf) throws ParserException {
        return findChild(ancestor, nf, 0);
    }

    public TagNode getFirstChildTag(Node ancestor, String tagName) throws ParserException {
        return findChildTag(ancestor, tagName, 0);
    }

    public TagNode findChildTag(Node ancestor, String tagName, int seq) throws ParserException {
        return (TagNode) findChild(ancestor, SimpleTagNameFilter.get(tagName), seq);
    }

    /**
     * 从0计数
     *
     * @param ancestor
     * @param nf
     * @param seq
     * @return
     * @throws org.htmlparser.util.ParserException
     *
     */
    public Node findChild(Node ancestor, NodeFilter nf, int seq) throws ParserException {
        Node node = ancestor.getFirstChild();
        int foundCount = -1;

        while (true) {
            if (node == null) break;

            if (nf.accept(node)) {
                foundCount++;
                if (foundCount == seq) {
                    return node;
                }
            }

            node = node.getNextSibling();
        }

        return null;
    }

    public NodeList findChildIn(Node ancestor, NodeFilter nf) throws ParserException {
        return findNodeIn(ancestor, nf, false);
    }

    public NodeList findOffspringIn(Node ancestor, NodeFilter nf) throws ParserException {
        return findNodeIn(ancestor, nf, true);
    }

    public Node findOnlyChildIn(Node ancestor, NodeFilter nf) throws Exception {
        return findOnlyNodeIn(ancestor, nf, false);
    }

    public Node findOnlyOffspringIn(Node ancestor, NodeFilter nf) throws Exception {
        return findOnlyNodeIn(ancestor, nf, true);
    }

    public Tag findOnlyTagIn(Node ancestor, NodeFilter nf) throws Exception {
        return findOnlyTagIn(ancestor, nf, false);
    }

    public Tag findOnlyTagIn(Node ancestor, NodeFilter nf, boolean recursive) throws Exception {
        return (Tag) findOnlyNodeIn(ancestor, nf, recursive);
    }

    public Node findOnlyNodeIn(Node ancestor, NodeFilter nf, boolean recursive) throws Exception {
        return findOnlyNode(findNodeIn(ancestor, nf, recursive));
    }

    public Node findOnlySiblingBetween(Node nodeStart, Node nodeEnd, NodeFilter nf) throws Exception {
        return findOnlyNode(findSiblingBetween(nodeStart, nodeEnd, nf));
    }

    public Node findFirstSiblingBetween(Node nodeStart, Node nodeEnd, NodeFilter nf) throws Exception {
        final NodeList list = findSiblingBetween(nodeStart, nodeEnd, nf);
        if (list.size() == 0) {
            return null;
        } else {
            return list.elementAt(0);
        }
    }

    public NodeList findSiblingBetween(Node nodeStart, Node nodeEnd, NodeFilter nf) throws Exception {
        NodeList nl = new NodeList();
        Node currentNode = nodeStart.getNextSibling();
        while (true) {
            if (currentNode == null) {
                break;
            }
            if (currentNode == nodeEnd) {
                break;
            }
            if (nf.accept(currentNode)) {
                nl.add(currentNode);
            }
            currentNode = currentNode.getNextSibling();
        }
        return nl;
    }

    public NodeList findSiblingBetween(Node nodeStart, Node nodeEnd) throws Exception {
        NodeList nl = new NodeList();
        Node currentNode = nodeStart.getNextSibling();
        while (true) {
            if (currentNode == null) {
                break;
            }
            if (currentNode == nodeEnd) {
                break;
            }
            nl.add(currentNode);
            currentNode = currentNode.getNextSibling();
        }
        return nl;
    }

    public Node findOnlyOffspring(NodeList ancestorList, NodeFilter nf) throws Exception {
        return findOnlyNode(findOffspring(ancestorList, nf));
    }

    public Node findFirstOffspring(NodeList ancestorList, NodeFilter nf) throws Exception {
        return findFirstNode(findOffspring(ancestorList, nf));
    }

    public NodeList findOffspring(NodeList ancestorList, NodeFilter nf) throws Exception {
        NodeList resultList = new NodeList();
        for (int i = 0; i < ancestorList.size(); i++) {
            Node ancestor = ancestorList.elementAt(i);
            NodeList result = findNodeIn(ancestor, nf, true);
            if (result != null) {
                resultList.add(result);
            }
        }
        return resultList;
    }

    public Tag findOnlyTagIn(Node ancestor, String tagName) throws Exception {
        return findOnlyTagIn(ancestor, tagName, false);
    }

    public Tag findOnlyTagIn(Node ancestor, String tagName, boolean recursive) throws Exception {
        return (Tag) findOnlyNode(findTagIn(ancestor, tagName, recursive));
    }

    public NodeList findNodeIn(Node ancestor, NodeFilter nf, boolean recursive) throws ParserException {
        NodeList list = new NodeList();
        findNodeIn(ancestor, nf, list, recursive);
        //System.out.println(list);
        //System.out.println("list.size()=" + list.size());
        return list;
    }

    public NodeList findTagIn(Node ancestor, String tagName, boolean recursive) throws ParserException {
        NodeFilter nf = new TagNameFilter(tagName);
        return findNodeIn(ancestor, nf, recursive);
    }

    public NodeList findTagIn(Node ancestor, String tagName) throws ParserException {
        return findTagIn(ancestor, tagName, false);
    }

    /**
     */
    private NodeList findNodeIn(Node ancestor, NodeFilter nf, NodeList list, boolean recursive) throws ParserException {
        findNodeIn(
                ancestor, nf, list, 0,
                recursive ? -1 : 1,
                recursive ? -1 : 1
        );
        return list;
    }


    /**
     */
    private NodeList findNodeIn(Node ancestor, NodeFilter nf, int minDepth, int maxDepth) throws ParserException {
        NodeList list = new NodeList();
        findNodeIn(ancestor, nf, list, 0, minDepth, maxDepth);
        return list;
    }


    /**
     */
    private void findNodeIn(Node ancestor, NodeFilter nf, NodeList list, int currentDepth, int minDepth, int maxDepth) throws ParserException {
        int childDepth = currentDepth + 1;
        if (maxDepth >= 0 && childDepth > maxDepth) {
            // 最大深度有效，且超过最大深度
            return;
        }

        NodeList children = ancestor.getChildren();
        if (children != null) {
            for (int i = 0; i < children.size(); i++) {
                Node child = children.elementAt(i);
                if (minDepth < 0 || childDepth >= minDepth) {
                    // 此处一定不会超过最大深度（如果最大深度有效）
                    // 最小深度无效，或者当前孩子深度合法
                    if (nf.accept(child)) {
                        list.add(child);
                    }
                }
                findNodeIn(child, nf, list, childDepth, minDepth, maxDepth);
            }
        }
    }


    public Tag findFirstTagIn(Node ancestor, NodeFilter nf) {
        NodeList children = ancestor.getChildren();

        if (children == null) {
            return null;
        }

        for (int i = 0; i < children.size(); i++) {
            Node child = children.elementAt(i);
            if (child instanceof Tag && nf.accept(child)) {
                return (Tag) child;
            } else {
                Tag result = findFirstTagIn(child, nf);
                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }


    public Tag findFirstTagIn(Node ancestor, String tagName) {
        return findFirstTagIn(ancestor, tagName, false);
    }

    public Tag findFirstTagIn(Node ancestor, String tagName, boolean recursive) {
        NodeList children = ancestor.getChildren();

        if (children == null) {
            return null;
        }

        for (int i = 0; i < children.size(); i++) {
            Node child = children.elementAt(i);
            if (child instanceof Tag && ((Tag) child).getTagName().equalsIgnoreCase(tagName)) {
                return (Tag) child;
            } else {
                if (recursive) {
                    Tag result = findFirstTagIn(child, tagName, recursive);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }

        return null;
    }


    public ArrayList<Tag> findTagByAttributeRegex(String tagName, String attributeName, String attributeValueRegex) throws ParserException {
        this.reset();

        ArrayList<Tag> tagList = new ArrayList<Tag>();
        NodeFilter nf_tag = new TagNameFilter(tagName);
        NodeList nodeList = this.parse(nf_tag);

        for (int i = 0; i < nodeList.size(); i++) {
            Tag tag = (Tag) nodeList.elementAt(i);
            String attributeValue = tag.getAttribute(attributeName);
            if (attributeValue != null && attributeValue.matches(attributeValueRegex)) {
                tagList.add(tag);
            }
        }

        return tagList;
    }

    public Tag findOnlyTagByAttributeRegex(String tagName, String attributeName, String attributeValueRegex, boolean nullIfNotExist) throws Exception {
        ArrayList<Tag> tagList = findTagByAttributeRegex(tagName, attributeName, attributeValueRegex);

        if (tagList == null || tagList.size() == 0) {
            if (nullIfNotExist) {
                return null;
            } else {
                System.out.println("tagName=" + tagName);
                System.out.println("attributeName=" + attributeName);
                System.out.println("attributeValueRegex=" + attributeValueRegex);
                throw new Exception("tagList==null or tagList.size()==0");
            }
        } else if (tagList.size() != 1) {
            System.out.println("tagName=" + tagName);
            System.out.println("attributeName=" + attributeName);
            System.out.println("attributeValueRegex=" + attributeValueRegex);
            throw new Exception("tagList.size(" + "=" + tagList.size() + ")!=1");
        } else {
            return tagList.get(0);
        }
    }

    public String findOnlyText(NodeFilter nf) throws Exception {
        return findOnlyText(nf, true);
    }

    public String findOnlyText(NodeFilter nf, boolean emptyIfNotExist) throws Exception {
        Node node = findOnlyNode(nf);
        if (node == null) {
            if (emptyIfNotExist) {
                return "";
            } else {
                throw new Exception("node == null");
            }
        } else {
            return node.toPlainTextString().trim();
        }
    }

    public Node findOnlyNode(NodeFilter nf) throws Exception {
        this.reset();
        NodeList list = this.parse(nf);

        return findOnlyNode(list);
    }

    private Node findOnlyNode(NodeList list) throws Exception {
        if (list == null || list.size() == 0) {
            return null;
        } else if (list.size() != 1) {
            throw new Exception("list.size(" + "=" + list.size() + ")!=1");
        } else {
            return list.elementAt(0);
        }
    }

    private Node findFirstNode(NodeList list) throws Exception {
        if (list == null || list.size() == 0) {
            return null;
        } else {
            return list.elementAt(0);
        }
    }


    public Tag findFirstTag(NodeFilter nf) throws Exception {
        this.reset();
        NodeList list = this.parse(nf);
        if (list != null && list.size() > 0) {
            return (Tag) list.elementAt(0);
        } else {
            return null;
        }
    }


    public Tag findOnlyTagById(String tagName, String id) throws Exception {
        return findOnlyTagById(tagName, id, true);
    }

    public Tag findOnlyTagById(String tagName, String id, boolean nullIfNotExist) throws Exception {
        ArrayList<Tag> tagList = findTagByAttributeRegex(tagName, "id", id);

        if (tagList == null || tagList.size() == 0) {
            if (nullIfNotExist) {
                return null;
            } else {
                System.out.println("tagName=" + tagName);
                System.out.println("id=" + id);
                throw new Exception("tagList==null or tagList.size()==0");
            }
        } else if (tagList.size() != 1) {
            System.out.println("tagName=" + tagName);
            System.out.println("id=" + id);
            throw new Exception("tagList.size(" + "=" + tagList.size() + ")!=1");
        } else {
            return tagList.get(0);
        }
    }

    public ImageTag findOnlyImageBySrcRegex(String srcRegex, boolean nullIfNotExist) throws Exception {
        return (ImageTag) findOnlyTagByAttributeRegex("img", "src", srcRegex, nullIfNotExist);
    }

    public ImageTag findOnlyImageBySrcRegex(String srcRegex) throws Exception {
        return findOnlyImageBySrcRegex(srcRegex, true);
    }


    public String findOnlyImageSrcBySrcRegex(String srcRegex, boolean nullIfNotExist) throws Exception {
        ImageTag tag = (ImageTag) findOnlyTagByAttributeRegex("img", "src", srcRegex, nullIfNotExist);
        if (tag == null) {
            if (nullIfNotExist) {
                return null;
            } else {
                throw new Exception("tag == null");
            }
        } else {
            return tag.getAttribute("src");
        }
    }

    public static String ulToString(BulletList ul, String separator) {
        StringBuffer sb = new StringBuffer();

        NodeList children = ul.getChildren();

        for (int i = 0; i < children.size(); i++) {
            Node child = children.elementAt(i);
            if (child instanceof Bullet) {
                sb.append(child.toPlainTextString().trim()).append(separator);
            }
        }

        if (sb.length() > 1) {
            // 去掉最后的分号
            return sb.substring(0, sb.length() - 1);
        } else {
            System.out.println("Can't find li!");
            return "";
        }
    }

    public LinkTag findOnlyLinkTagByTextRegex(String textRegex) throws Exception {
        return findOnlyLinkTagByRegex(textRegex, true);
    }

    public String findOnlyLinkByTextRegex(String textRegex, boolean nullIfNotExist) throws Exception {
        ArrayList<LinkTag> tagList = findLinkTagByTextRegex(textRegex);

        if (tagList == null || tagList.size() == 0) {
            if (nullIfNotExist) {
                return null;
            } else {
                System.out.println("text=" + textRegex);
                throw new Exception("tagList==null or tagList.size()==0");
            }
        } else if (tagList.size() != 1) {
            System.out.println("text=" + textRegex);
            throw new Exception("tagList.size(" + "=" + tagList.size() + ")!=1");
        } else {
            return tagList.get(0).getLink().trim();
        }
    }

    public LinkTag findOnlyLinkTagByRegex(String textRegex, boolean nullIfNotExist) throws Exception {
        ArrayList<LinkTag> tagList = findLinkTagByTextRegex(textRegex);

        if (tagList == null || tagList.size() == 0) {
            if (nullIfNotExist) {
                return null;
            } else {
                System.out.println("text=" + textRegex);
                throw new Exception("tagList==null or tagList.size()==0");
            }
        } else if (tagList.size() != 1) {
            System.out.println("text=" + textRegex);
            throw new Exception("tagList.size(" + "=" + tagList.size() + ")!=1");
        } else {
            return tagList.get(0);
        }
    }

    public ArrayList<LinkTag> findLinkTagByTextRegex(String textRegex) throws ParserException {
        ArrayList<LinkTag> tagList = new ArrayList<LinkTag>();
        this.reset();

        NodeFilter nf = new TagNameFilter("a");
        NodeList list = this.parse(nf);

        for (int i = 0; i < list.size(); i++) {
            LinkTag linkTag = (LinkTag) list.elementAt(i);
            if (linkTag.getLinkText().trim().matches(textRegex)) {
                tagList.add(linkTag);
            }
        }

        return tagList;
    }

    /**
     * 在当前层，当前节点前面的节点都符合条件
     *
     * @param anchorNode
     * @return
     */
    public String getTextBefore(Node anchorNode) {
        return nodeListToPlainText(getSiblingsBefore(anchorNode));
    }

    public String getTextBefore(Node parentNode, String anchorTagName) throws Exception {
        Node anchorNode = findFirstNodeIn(parentNode, anchorTagName);
        if (anchorNode == null) {
            return null;
        }
        return nodeListToPlainText(getSiblingsBefore(anchorNode));
    }

    public NodeList getSiblingsBefore(Node anchorNode) {
        NodeList list = new NodeList();

        for (Node node : anchorNode.getParent().getChildren().toNodeArray()) {
//            if (lutm.informationExtraction.IsIdenticalFilter.isIdentical(node, anchorNode)) {
            if (IsIdenticalFilter.isIdentical(node, anchorNode)) {
                break;
            }

            list.add(node);
        }

        return list;
    }

    public NodeList findSiblingsAfter(Node anchorNode) {
        NodeList list = new NodeList();

        Node nextSibling = anchorNode.getNextSibling();
        while (true) {
            if (nextSibling == null) break;
            list.add(nextSibling);
            nextSibling = nextSibling.getNextSibling();
        }

        return list;
    }

    public NodeList findSiblingsAfter(Node anchorNode, String tagName) {
        return findSiblingsAfter(anchorNode, SimpleTagNameFilter.get(tagName));
    }

    public NodeList findSiblingsAfter(Node anchorNode, NodeFilter nf) {
        NodeList list = findSiblingsAfter(anchorNode);
        NodeList matchList = new NodeList();

        for (int i = 0; i < list.size(); i++) {
            Node node = list.elementAt(i);
            if (node instanceof TagNode) {
                if (nf.accept(node)) {
                    matchList.add(node);
                }
            }
        }

        return matchList;
    }

    public List<String> getTextsBetweenSiblings(Node startNode, Node endNode) {
        return getTextsBetweenSiblings(startNode, endNode, false, false);
    }

    public String getTextBetweenSiblings(Node startNode, Node endNode) {
        return getTextBetweenSiblings(startNode, endNode, false, false);
    }

    public String getTextAfter(Node startNode) {
        return getTextAfter(startNode, false);
    }

    public String getTextAfter(Node startNode, boolean includingStartNode) {
        return getTextBetweenSiblings(startNode, startNode.getParent().getLastChild(), includingStartNode, true);
    }

    public List<String> getTextsBetweenSiblings(Node startNode, Node endNode, boolean includeStartNode, boolean includeEndNode) {
        NodeList list = getSiblingsBetween(startNode, endNode, includeStartNode, includeEndNode);
        return nodeListToPlainTexts(list);
    }

    public String getTextBetweenSiblings(Node startNode, Node endNode, boolean includeStartNode, boolean includeEndNode) {
        NodeList list = getSiblingsBetween(startNode, endNode, includeStartNode, includeEndNode);
        return nodeListToPlainText(list, "\n");
    }

    public List<String> getTextsBetweenSiblings(NodeFilter startNodeFilter, NodeFilter endNodeFilter, boolean includeStartNode, boolean includeEndNode) throws ParserException {
        NodeList list = getChildrenBetween(startNodeFilter, endNodeFilter, includeStartNode, includeEndNode);
        return nodeListToPlainTexts(list);
    }

    public List<String> getTextsBetween(Node parent, NodeFilter startNodeFilter, NodeFilter endNodeFilter, boolean includeStartNode, boolean includeEndNode) throws ParserException {
        NodeList list = getSiblingsBetween(parent, startNodeFilter, endNodeFilter, includeStartNode, includeEndNode);
        return nodeListToPlainTexts(list);
    }

    public List<String> getTextsBetween(Node parent, NodeFilter startNodeFilter, NodeFilter endNodeFilter) throws ParserException {
        NodeList list = getSiblingsBetween(parent, startNodeFilter, endNodeFilter, false, false);
        return nodeListToPlainTexts(list);
    }

    private ArrayList<String> nodeListToPlainTexts(ArrayList<Node> nodeList) {
        ArrayList<String> sList = new ArrayList<String>();

        for (int i = 0; i < nodeList.size(); i++) {
            String s = nodeList.get(i).toPlainTextString().trim();

            if (s == null || "".equals(s)) {
                //
            } else {
                String[] ss = s.split("\n");
                for (String si : ss) {
                    sList.add(si);
                }
            }
        }

        return sList;
    }

    public List<String> nodeListToPlainTexts(NodeList nodeList) {
        if (nodeList == null) {
            return null;
        }

        ArrayList<String> sList = new ArrayList<String>();

        for (int i = 0; i < nodeList.size(); i++) {
            final Node node = nodeList.elementAt(i);
            if (node == null) {
                continue;
            }
            String s = node.toPlainTextString().trim();

            if (s == null || "".equals(s)) {
                //
            } else {
                sList.add(s);
            }
        }

        return sList;
    }

    private String nodeListToPlainText(ArrayList<Node> nodeList, String separator) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < nodeList.size(); i++) {
            String s = nodeList.get(i).toPlainTextString().trim();

            if (s == null || "".equals(s)) {
                //
            } else {
                sb.append(s).append(separator);
            }
        }

        if (sb.length() >= 1) {
            return sb.substring(0, sb.length() - 1);
        } else {
            return sb.toString();
        }
    }

    private String nodeListToPlainText(NodeList nodeList, String separator) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < nodeList.size(); i++) {
            String s = nodeToPlainText(nodeList.elementAt(i));

            if (s == null || "".equals(s)) {
                //
            } else {
                sb.append(s).append(separator);
            }
        }

        if (sb.length() >= 1) {
            return sb.substring(0, sb.length() - 1);
        } else {
            return sb.toString();
        }
    }

    public NodeList getChildrenBetween(NodeFilter startNodeFilter, NodeFilter endNodeFilter, boolean includeStartNode, boolean includeEndNode) throws ParserException {
        this.reset();
        NodeList startNodeList = this.parse(startNodeFilter);

        this.reset();
        NodeList endNodeList = this.parse(endNodeFilter);

        return getSiblingsBetween(startNodeList.elementAt(0), endNodeList.elementAt(0), includeStartNode, includeEndNode);
    }

    public NodeList getSiblingsBetween(Node parent, NodeFilter startNodeFilter, NodeFilter endNodeFilter, boolean includeStartNode, boolean includeEndNode) throws ParserException {
        this.reset();
        NodeList startNodeList = findChildIn(parent, startNodeFilter);

        this.reset();
        NodeList endNodeList = findChildIn(parent, endNodeFilter);

        return getSiblingsBetween(startNodeList.elementAt(0), endNodeList.elementAt(0), includeStartNode, includeEndNode);
    }

    public String getSiblingsTextBetween(Node startNode, Node endNode) {
        return getSiblingsTextBetween(startNode, endNode, false, false);
    }

    public String getSiblingsTextBetween(Node startNode, Node endNode, boolean includeStartNode, boolean includeEndNode) {
        return nodeListToPlainText(getSiblingsBetween(startNode, endNode, includeStartNode, includeEndNode));
    }

    public NodeList getSiblingsBetween(Node startNode, Node endNode) {
        return getSiblingsBetween(startNode, endNode, false, false);
    }

    public NodeList getSiblingsBetween(Node startNode, Node endNode, boolean includeStartNode, boolean includeEndNode) {
        NodeList list = new NodeList();

        if (includeStartNode) {
            list.add(startNode);
        }

        Node node = startNode;
        while (true) {
            node = node.getNextSibling();
            if (node == null) {
                break;
            } else if (!node.equals(endNode)) {
                list.add(node);
            } else {
                if (includeEndNode) {
                    list.add(endNode);
                }
                break;
            }
        }

        return list;
    }

    public String getTextBetween(Node startNode, Node endNode) throws Exception {
        return getTextBetween(startNode, endNode, false, false);
    }

    public String getTextBetween(Node startNode, Node endNode, boolean includeStartNode, boolean includeEndNode) throws Exception {
        return getTextBetween(startNode, new IsIdenticalFilter(endNode), includeStartNode, includeEndNode);
    }

    public String getTextBetween(Node startNode, NodeFilter endNodeFilter, boolean includeStartNode, boolean includeEndNode) throws Exception {
        return nodeListToPlainText(getLeavesBetween(startNode, endNodeFilter, includeStartNode, includeEndNode));
    }

    /**
     * 得到开始节点和结束节点之间的所有叶节点
     *
     * @param startNode
     * @param endNodeFilter
     * @return
     */
    public NodeList getLeavesBetween(Node startNode, NodeFilter endNodeFilter, boolean includeStartNode, boolean includeEndNode) throws Exception {
        NodeList list = new NodeList();

        if (includeStartNode) {
            addLeaves(startNode, endNodeFilter, includeEndNode, list);
        } else {
            Node nextNode = getNextNode(startNode);
            if (nextNode == null) {
                throw new Exception("The startNode is the last node!");
            } else {
                addLeaves(nextNode, endNodeFilter, includeEndNode, list);
            }
        }

        return list;
    }

    /**
     * @param currentNode
     * @param endNodeFilter
     * @param includeEndNode
     * @param list
     * @return
     */
    public boolean addLeaves(Node currentNode, NodeFilter endNodeFilter, boolean includeEndNode, NodeList list) {
        // 终止条件
        // 在此不管是不是叶节点
        if (endNodeFilter.accept(currentNode)) {
            if (includeEndNode) {
                list.add(currentNode);
            }
            return true;
        }

        NodeList children = currentNode.getChildren();
        if (children != null && children.size() > 0) {
            // 如果有孩子，那么只看第一个孩子
            // 其他的孩子，子节点的最后一个孩子会先向上再向后遍历
            if (addLeaves(children.elementAt(0), endNodeFilter, includeEndNode, list)) {
                return true;
            }
        } else {
            // 叶子节点
            list.add(currentNode);
            Node sibling = currentNode.getNextSibling();
            if (sibling != null) {
                // 如果有兄弟，那么找兄弟
                if (addLeaves(sibling, endNodeFilter, includeEndNode, list)) {
                    return true;
                }
            } else {
                // 没有兄弟，当前节点是当前层最后一个叶子节点
                // 寻找父亲的弟弟，或者爷爷的弟弟，……
                Node nextNode = getNextNode(currentNode);
                if (nextNode == null) {
                    // 已到最顶层
                    return false;
                } else {
                    // 父亲/爷爷/…… 有弟弟
                    if (addLeaves(nextNode, endNodeFilter, includeEndNode, list)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String getTextMatchingIn(Node ancestor, String regex) {
        return getTextMatching(nodeToPlainText(ancestor), regex);
    }

    public static String getTextMatchingIn(NodeList nodeList, String regex) {
        return getTextMatching(nodeListToPlainText(nodeList), regex);
    }

    public static String getTextMatching(String text, String regex) {
        String[] a_text = text.split("\n");
        Pattern pattern = Pattern.compile(regex);
        for (String s : a_text) {
            Matcher matcher = pattern.matcher(s);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }

        return null;
    }

    public static String nodeListToPlainText(NodeList list) {
        StringBuffer sb = new StringBuffer();
        for (Node node : list.toNodeArray()) {
            sb.append(nodeToPlainText(node));
        }

        return sb.toString().trim();
    }

    public static String nodeToPlainText(BulletList ul, String separator) {
        StringBuffer sb = new StringBuffer();
        NodeList liList = ul.getChildren().extractAllNodesThatMatch(new TagNameFilter("li"));
        for (int i = 0; i < liList.size(); i++) {
            Node li = liList.elementAt(i);
            sb.append(HtmlUtil.htmlToText(li.toPlainTextString()));
            if (i < liList.size() - 1) {
                sb.append(separator);
            }
        }

        return sb.toString();
    }

    public static String nodeToPlainText(BulletList ul) {
        return nodeToPlainText(ul, "\n");
    }

    public static String ntpt(Node node) {
        return nodeToPlainText(node);
    }

    public static String nodeToPlainText(Node node) {
        NodeList children = node.getChildren();
        if (children != null && children.size() > 0) {
            StringBuffer rsb = new StringBuffer();
            for (int i = 0; i < children.size(); i++) {
                Node child = children.elementAt(i);
                String compactText = nodeToPlainText(child);
                String plainText = child.toPlainTextString();
                if (plainText.startsWith(" ") && rsb.length() > 0 && rsb.charAt(rsb.length() - 1) != ' ') {
                    rsb.append(' ');
                }
                rsb.append(compactText);
                if (plainText.endsWith(" ") && i < children.size() - 1) {
                    rsb.append(' ');
                }
            }
            if (node instanceof ParagraphTag
                    || node instanceof Div
                    || node instanceof TableRow) {
                rsb.append("\n");
            }
            return rsb.toString();
        } else {
            if (node instanceof Tag) {
                Tag tag = (Tag) node;
                if ("br".equalsIgnoreCase(tag.getTagName())) {
                    return "\n";
                }
            }
            return HtmlUtil.htmlToText(node.toPlainTextString());
        }
    }


    /**
     * 寻找当前节点的下一个节点
     * （只能向后，或者向上再向后，不能向下搜索）
     * 如果当前节点有兄弟，那么返回兄弟
     * 如果当前节点没有兄弟
     * 如果当前节点有父亲（不是顶层）
     * 如果父亲有兄弟，返回父亲的兄弟
     * 找爷爷的兄弟……
     * 否则，返回空
     *
     * @param currentNode
     * @return
     */
    public static Node getNextNode(Node currentNode) {
        Node sibling = currentNode.getNextSibling();
        if (sibling != null) {
            // 有兄弟
            return sibling;
        } else {
            // 没有兄弟，当前节点是当前层最后一个节点
            // 寻找父亲的弟弟，或者爷爷的弟弟，……
            Node ancestor = currentNode;
            while (true) {
                ancestor = ancestor.getParent();
                if (ancestor == null) {
                    // 已到最顶层
                    return null;
                } else {
                    Node ancestorSibling = ancestor.getNextSibling();
                    if (ancestorSibling != null) {
                        // 父亲/爷爷/…… 有弟弟
                        return ancestorSibling;
                    }
                }
            }
        }
    }

    public static Node getNextSiblingIgnoreBT(Node node) {
        Node sibling;
        sibling = node;
        while (true) {
            sibling = sibling.getNextSibling();
            if (sibling == null) {
                return null;
            }
            if (sibling instanceof TextNode
                    && sibling.toPlainTextString().trim().length() == 0) {
                continue;
            }

            return sibling;
        }
    }

    public static String parseEmail(String s) {
        return s.replaceAll(" \\[&#97;t\\] ", "@").replaceAll(" \\[&#100;&#111;t\\] ", ".");
    }


    public Node findFirstNode(NodeFilter nf) throws Exception {
        this.reset();
        NodeList list = this.parse(nf);
        if (list != null && list.size() > 0) {
            return list.elementAt(0);
        } else {
            return null;
        }
    }

    /**
     * 默认不递归，可以为空
     *
     * @param nf
     * @param node
     * @return
     * @throws Exception
     */
    public static Node findFirstChildIn(Node node, NodeFilter nf) throws Exception {
        return findFirstNodeIn(node, nf, false, true);
    }

    public static Node findFirstNodeIn(Node node, NodeFilter nf, boolean recursive) throws Exception {
        return findFirstNodeIn(node, nf, recursive, true);
    }

    public static Node findFirstOffspringIn(Node node, NodeFilter nf) throws Exception {
        return findFirstNodeIn(node, nf, true, true);
    }

    public static Tag findFirstOffspringTagIn(Node node, String tagName) throws Exception {
        return (Tag) findFirstNodeIn(node, SimpleTagNameFilter.get(tagName), true, true);
    }

    public ImageTag findFirstImageIn(Node node) throws Exception {
        return (ImageTag) findFirstNodeIn(node, "img");
    }

    public static Node findFirstNodeIn(Node node, String tagName) throws Exception {
        //return findFirstNodeIn(node, new TagNameFilter(tagName));
        return findFirstChildIn(node, SimpleTagNameFilter.get(tagName));
    }

    public static Node findFirstNodeIn(Node node, String tagName, boolean recursive, boolean nullIfNotExist) throws Exception {
        return findFirstNodeIn(node, new TagNameFilter(tagName), recursive, nullIfNotExist);
    }

    public static Node findFirstNodeIn(Node node, NodeFilter nf, boolean recursive, boolean nullIfNotExist) throws Exception {
        NodeList children = node.getChildren();
        if (children != null) {
            for (int i = 0; i < children.size(); i++) {
                Node child = children.elementAt(i);
                if (nf.accept(child)) {
                    return child;
                } else if (recursive) {
                    Node ret = findFirstNodeIn(child, nf, recursive, nullIfNotExist);
                    if (ret != null) {
                        return ret;
                    }
                }
            }
        }

        if (nullIfNotExist) {
            return null;
        } else {
            throw new Exception("Can't find node!");
        }
    }

    /**
     * 不管在哪一层，都是从前向后寻找
     *
     * @param anchorNode
     * @param nf
     * @param recursive
     * @return
     * @throws Exception
     */
    public Node findFirstNodeBefore(Node anchorNode, NodeFilter nf, boolean recursive) throws Exception {
        Node node = anchorNode.getParent().getFirstChild();
        while (true) {
            if (node == null) {
                // 到达最后一个节点
                throw new Exception("Unexpected end of children!");
            } else if (IsIdenticalFilter.isIdentical(anchorNode, node)) {
                // 到达anchorNode
                return null;
            } else {
                if (nf.accept(node)) {
                    return node;
                }
                if (recursive) {
                    Node ret = findFirstNodeIn(node, nf, recursive);
                    if (ret != null) {
                        return ret;
                    }
                }
            }
            node = node.getNextSibling();
        }
    }

    public Node findNearestSiblingBefore(Node anchorNode, NodeFilter nf) throws Exception {
        Node node;
        node = anchorNode;
        while (true) {
            node = node.getPreviousSibling();
            if (node == null) {
                return null;
            } else {
                if (nf.accept(node)) {
                    return node;
                }
            }
        }
    }

    public Node findNearestSiblingTagBefore(Node anchorNode, String tagName) throws Exception {
        return findNearestSiblingBefore(anchorNode, new TagNameFilter(tagName));
    }

    /**
     * 从后往前，查找指定父节点的子节点，返回匹配指定条件的第一个节点
     */
    public Node findLastOffspring(Node ancestor, NodeFilter nf, int minDepth, int maxDepth) throws ParserException {
        NodeList offspringList = findNodeIn(ancestor, nf, minDepth, maxDepth);
        if (offspringList == null || offspringList.size() == 0) {
            return null;
        } else {
            return offspringList.elementAt(offspringList.size() - 1);
        }
    }

    /**
     * 从后往前，查找指定父节点的子节点，返回匹配指定条件的第一个节点
     */
    public static Node findLastChild(Node parent, NodeFilter nf) {
        NodeList children = parent.getChildren();
        for (int i = children.size() - 1; i >= 0; i--) {
            Node child = children.elementAt(i);
            if (nf.accept(child)) {
                return child;
            }
        }

        return null;
    }

    /**
     * 在指定节点的后面开始寻找，只会在同一级往后搜索
     * 如果recursive=true，那么在往后搜索过程中，可以往下搜索
     *
     * @param anchorNode
     * @param nf
     * @param recursive
     * @return
     * @throws Exception
     */
    public Node findFirstNodeAfter(Node anchorNode, NodeFilter nf, boolean recursive) throws Exception {
        Node node = anchorNode;
        while (true) {
            node = node.getNextSibling();
            if (node == null) {
                break;
            } else {
                if (nf.accept(node)) {
                    return node;
                }
                if (recursive) {
                    Node ret = findFirstNodeIn(node, nf, recursive);
                    if (ret != null) {
                        return ret;
                    }
                }
            }
        }

        return null;
    }

    public Tag getNearbyTagAfter(Node anchorNode, String tagName) throws Exception {
        Tag tag = getNearbyTagAfter(anchorNode);
        if (tag != null && tag.getTagName().equalsIgnoreCase(tagName)) {
            return tag;
        }

        return null;
    }

    public Tag getNearbyTagAfter(Node anchorNode) throws Exception {
        Node sibling = anchorNode.getNextSibling();
        while (true) {
            if (sibling == null) {
                return null;
            }

            if (isEmptyTextNode(sibling)) {
                //
            } else {
                return (Tag) sibling;
            }
        }
    }

    public TagNode findNextSiblingTagSkipBlank(Node anchorNode, String tagName) {
        return findNextSiblingTagSkipBlank(anchorNode, tagName, 0);
    }

    public TagNode findNextSiblingTagSkipBlank(Node anchorNode, String tagName, int maxNonBlankTagCount) {
         return findNextSiblingTagSkipBlank(anchorNode, SimpleTagNameFilter.get(tagName), maxNonBlankTagCount);
    }

    public TagNode findNextSiblingTagSkipBlank(Node anchorNode) {
        return findNextSiblingTagSkipBlank(anchorNode, (NodeFilter) null, 0);
    }

    public TagNode findNextSiblingTagSkipBlank(Node anchorNode, NodeFilter nf, int maxNonBlankTagCount) {
        Node currentNode = anchorNode.getNextSibling();
        int nonBlankTagCount = 0;
        while (true) {
            if (currentNode == null) {
                return null;
            }
            if (currentNode instanceof TextNode) {
                if ("".equals(currentNode.toPlainTextString().trim())) {
                    // skip
                    currentNode = currentNode.getNextSibling();
                    continue;
                }
            } else if (currentNode instanceof TagNode) {
                if (nf == null || nf.accept(currentNode)) {
                    // ok
                    return (TagNode) currentNode;
                }
            }
            nonBlankTagCount++;
            if(nonBlankTagCount>maxNonBlankTagCount) {
                return null;
            }
            currentNode = currentNode.getNextSibling();
        }
    }

    public Node findFirstNodeAfter(Node anchorNode, NodeFilter nf) throws Exception {
        return findFirstNodeAfter(anchorNode, nf, false);
    }

    public TagNode findFirstTagAfter(Node anchorNode, String tagName) throws Exception {
        return (TagNode) findFirstNodeAfter(anchorNode, SimpleTagNameFilter.get(tagName));
    }

    public Tag findFirstTagAfter(Node anchorNode, String tagName, boolean recursive) throws Exception {
        return (Tag) findFirstNodeAfter(anchorNode, SimpleTagNameFilter.get(tagName), recursive);
    }

    public String findFirstTagTextAfter(Node anchorNode, String tagName) throws Exception {
        return ((Tag) findFirstNodeAfter(anchorNode, SimpleTagNameFilter.get(tagName))).toPlainTextString().trim();
    }

    public ImageTag findFirstImageAfter(Node anchorNode) throws Exception {
        return (ImageTag) findFirstNodeAfter(anchorNode, SimpleTagNameFilter.imgFilter);
    }

    public NodeList find(NodeFilter nf) throws ParserException {
        this.reset();
        return parse(nf);
    }


    public static TableRow getNextRow(TableRow row) {
        Node sibling = row;
        while (true) {
            sibling = sibling.getNextSibling();
            if (sibling != null) {
                if (sibling instanceof TableRow) {
                    return (TableRow) sibling;
                }
            } else {
                return null;
            }
        }
    }

    public static TableColumn getNextCell(TableColumn cell) {
        Node sibling = cell;
        while (true) {
            sibling = sibling.getNextSibling();
            if (sibling != null) {
                if (sibling instanceof TableColumn) {
                    return (TableColumn) sibling;
                }
            } else {
                return null;
            }
        }
    }

    /**
     * 寻找当前节点的子孙节点中，最后一个叶节点
     *
     * @param ancestor
     * @return
     */
    public static Node getLastLeaf(Node ancestor) {
        Node lastChild = ancestor.getLastChild();
        if (lastChild == null) {
            return null;
        }
        while (true) {
            Node lastGrandson = lastChild.getLastChild();
            if (lastGrandson == null) {
                return lastChild;
            }

            lastChild = lastGrandson;
        }
    }

    public static Object[] subArray(Object[] oriArray, int fromIdx) {
        Object[] newArray = new Object[oriArray.length - fromIdx];

        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = oriArray[i + fromIdx];
        }

        return newArray;
    }

    public static String preProcessEmail(String s) {
        return s
                .replaceFirst(":", "")
                .replaceFirst("(E|e)mail ", "")
                .replaceAll(" dot ", ".")
                .replaceFirst("\\(at\\)", "@")
                .replaceFirst(" at ", "@")
                .replaceAll(" ", "");
    }

    /**
     * 去除空白文本节点
     *
     * @param srcList
     * @return
     */
    public static NodeList clean(NodeList srcList) {
        NodeList destList = new NodeList();
        for (int i = 0; i < srcList.size(); i++) {
            Node node = srcList.elementAt(i);
            if (!isEmptyTextNode(node)) {
                destList.add(node);
            }
        }

        return destList;
    }

    public static NodeList cleanBr(NodeList srcList) {
        NodeList destList = new NodeList();
        for (int i = 0; i < srcList.size(); i++) {
            Node node = srcList.elementAt(i);
            if (node instanceof Tag && "br".equalsIgnoreCase(((Tag) node).getTagName())) {
                destList.add(node);
            }
        }

        return destList;
    }

    public static ArrayList<NodeList> split(NodeList nodeList, NodeFilter filter) {
        ArrayList<NodeFilter> filterList = new ArrayList<NodeFilter>();
        filterList.add(filter);
        return split(nodeList, filterList, true, true, true);
    }

    public static ArrayList<NodeList> split(NodeList nodeList, ArrayList<NodeFilter> filterList) {
        return split(nodeList, filterList, true, true, true);
    }

    public static ArrayList<NodeList> split(NodeList nodeList, ArrayList<NodeFilter> filterList, boolean trimHead, boolean trimTail, boolean clearBrTag) {
        nodeList = clean(nodeList);

        ArrayList<NodeList> nodeListList = new ArrayList<NodeList>();
        ArrayList<Integer> idxList = new ArrayList<Integer>();

        // 找到匹配为分隔的位置
        for (int i_node = 0; i_node < nodeList.size() - filterList.size() + 1; ) {
            boolean match = true;
            for (int i_filter = 0; i_filter < filterList.size(); i_filter++) {
                Node node = nodeList.elementAt(i_node + i_filter);
                if (!filterList.get(i_filter).accept(node)) {
                    match = false;
                    break;
                }
            }

            if (match) {
                idxList.add(i_node);
                i_node += filterList.size();
            } else {
                i_node++;
            }
        }

        // 根据位置列表，得到数组
        // 如果以分隔开始，那么第一个元素是空的NodeList
        // 如果以分隔结束，那么最后一个元素是空的NodeList
        int lastIdx = -1;
        for (int idx : idxList) {
            NodeList nodeListSeg = new NodeList();
            for (int i_node = lastIdx + 1; i_node < idx; i_node++) {
                Node node = nodeList.elementAt(i_node);
                if (!isEmptyTextNode(node)) {
                    if (clearBrTag && node instanceof Tag && "br".equalsIgnoreCase(((Tag) node).getTagName())) {
                        // do nothing
                    } else {
                        nodeListSeg.add(node);
                    }
                }
            }
            nodeListList.add(nodeListSeg);
            lastIdx = idx;
        }

        if (nodeListList.size() > 0) {
            if (trimHead && nodeListList.get(0).size() == 0) {
                nodeListList.remove(0);
            }
            if (trimTail && nodeListList.get(nodeListList.size() - 1).size() == 0) {
                nodeListList.remove(nodeListList.size() - 1);
            }
        }

        return nodeListList;
    }

    public static String erasePersonTitle(String s) {
        return s.replaceFirst("^(Professor|Prof|Dr|Mr|Ms)\\s+", "");
    }

    public static boolean isEmptyTextNode(Node sibling) {
        return sibling instanceof TextNode && "".equals(sibling.toPlainTextString().trim());
    }

    public static NodeList extractRow(TableTag table, TableRowFilter f) {
        NodeList list = new NodeList();
        for (int i = 0; i < table.getRowCount(); i++) {
            TableRow row = table.getRow(i);
            if (f.accept(row)) {
                list.add(row);
            }
        }

        return list;
    }


    /**
     * @param list
     * @param headEleFilter
     * @return
     */
    public static ArrayList<NodeList> segListByHeadElementFilter(NodeList list, NodeFilter headEleFilter) {
        ArrayList<NodeList> result = new ArrayList<NodeList>();
        for (int i = 0; i < list.size(); i++) {
            Node child = list.elementAt(i);
            if (headEleFilter.accept(child)) {
                // have found a head element
                // create a new segment
                NodeList segment = new NodeList();
                segment.add(child);
                result.add(segment);
            } else {
                if (result.size() == 0) {
                    // have not found a head element
                } else {
                    // add to last segment
                    result.get(result.size() - 1).add(child);
                }
            }
        }

        return result;
    }


    /**
     * @param parent
     * @param headEleFilter
     * @return
     */
    public static ArrayList<NodeList> segChildrenByHeadElementFilter(Node parent, NodeFilter headEleFilter) {
        NodeList list = parent.getChildren();
        return segListByHeadElementFilter(list, headEleFilter);
    }

    /**
     * 表格的行转换成NodeList
     *
     * @param rows
     * @return
     */
    public static NodeList toNodeList(TableRow[] rows) {
        NodeList list = new NodeList();
        for (TableRow row : rows) {
            list.add(row);
        }

        return list;
    }


}
