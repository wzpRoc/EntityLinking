package org.ailab.wikipedia.wim.infobox;

import org.ailab.wikipedia.wim.infoboxItem.InfoboxItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 13-12-28
 * Time: 下午2:11
 */
public class Infobox extends ArrayList<InfoboxItem> {
    protected Map<String, String> map = new HashMap<String, String>();

    public boolean add(InfoboxItem item) {
        super.add(item);
        map.put(item.getTitle(), item.getValue());
        return true;
    }

    public String get(String title) {
        return map.get(title);
    }
}
