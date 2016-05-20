package org.ailab.entityLinking.graph.debug;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 16-1-7
 * Time: 下午6:27
 */
public class TR extends ArrayList<TD> {
    public String className;

    public String toHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<tr");
        if(className !=null) {
            sb.append(" class='").append(className).append("'");
        }
        sb.append(">");

        for(TD td:this) {
            sb.append(td.toHTML());
        }

        sb.append("</tr>\n");

        return sb.toString();
    }
}
