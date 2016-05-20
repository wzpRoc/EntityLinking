package org.ailab.webInformationExtraction;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Tag;
import org.htmlparser.filters.OrFilter;

import java.util.HashMap;

/**
 * User: Lu Timgming
 * Date: 2009-12-28
 * Time: 13:22:35
 * Desc: 标签名过滤器，不管开始节点还是结束节点都算
 */
public class SimpleTagNameFilter implements NodeFilter {
    public static final SimpleTagNameFilter titleFilter = new SimpleTagNameFilter("title");
    public static final SimpleTagNameFilter spanFilter = new SimpleTagNameFilter("span");
    public static final SimpleTagNameFilter strongFilter = new SimpleTagNameFilter("strong");
    public static final SimpleTagNameFilter imgFilter = new SimpleTagNameFilter("img");
    public static final SimpleTagNameFilter linkFilter = new SimpleTagNameFilter("a");
    public static final SimpleTagNameFilter olFilter = new SimpleTagNameFilter("ol");
    public static final SimpleTagNameFilter divFilter = new SimpleTagNameFilter("div");
    public static final SimpleTagNameFilter tableFilter = new SimpleTagNameFilter("table");
    public static final SimpleTagNameFilter pFilter = new SimpleTagNameFilter("p");
    public static final SimpleTagNameFilter supFilter = new SimpleTagNameFilter("sup");
    public static final SimpleTagNameFilter brFilter = new SimpleTagNameFilter("br");
    public static final SimpleTagNameFilter h1Filter = new SimpleTagNameFilter("h1");
    public static final SimpleTagNameFilter h2Filter = new SimpleTagNameFilter("h2");
    public static final SimpleTagNameFilter h3Filter = new SimpleTagNameFilter("h3");
    public static final SimpleTagNameFilter h4Filter = new SimpleTagNameFilter("h4");
    public static final SimpleTagNameFilter ulFilter = new SimpleTagNameFilter("ul");
    public static final SimpleTagNameFilter liFilter = new SimpleTagNameFilter("li");
    public static final SimpleTagNameFilter trFilter = new SimpleTagNameFilter("tr");
    public static final SimpleTagNameFilter tdFilter = new SimpleTagNameFilter("td");
    public static final SimpleTagNameFilter thFilter = new SimpleTagNameFilter("th");
    public static final NodeFilter thtdFilter = new OrFilter(tdFilter, thFilter);

    private static HashMap<String, SimpleTagNameFilter> map = new HashMap<String, SimpleTagNameFilter>();
    static{
        map.put("img", imgFilter);
        map.put("a", linkFilter);
        map.put("div", divFilter);
        map.put("table", tableFilter);
        map.put("p", pFilter);
        map.put("br", brFilter);
        map.put("h2", h2Filter);
        map.put("ul", ulFilter);
        map.put("li", liFilter);
    }

    private String tagName;

    public SimpleTagNameFilter(String tagName) {
        this.tagName = tagName;
    }

    public boolean accept(Node node) {
        return ((node instanceof Tag)
                && ((Tag)node).getTagName ().equalsIgnoreCase (tagName));

    }

    public static SimpleTagNameFilter get(String tagName){
        SimpleTagNameFilter f = map.get(tagName);
        if(f==null){
            f = new SimpleTagNameFilter(tagName);
            map.put(tagName, f);
        }

        return f;
    }
}