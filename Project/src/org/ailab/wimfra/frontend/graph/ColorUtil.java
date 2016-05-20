package org.ailab.wimfra.frontend.graph;

import org.ailab.wimfra.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 2012-10-9
 * Time: 15:41:31
 * Desc:
 */
public class ColorUtil {
    public static List<String> colorList;
    public static String colorListJs;

    static {
        colorList = new ArrayList<String>();
        colorList.add("#ff0000");
        colorList.add("#00ff00");
        colorList.add("#0000ff");
        colorList.add("#FF00ff");
        colorList.add("#ffff00");
        colorList.add("#00ffff");
        colorList.add("#218868");
        colorList.add("#B23AEE");
        colorList.add("#8B8B00");
        colorList.add("#555555");
        colorList.add("");
        colorList.add("");

        colorListJs = "['" + StringUtil.collectionToString(colorList, "','") + "']";
    }
}
