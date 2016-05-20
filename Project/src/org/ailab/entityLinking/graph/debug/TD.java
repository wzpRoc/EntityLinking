package org.ailab.entityLinking.graph.debug;

import org.ailab.wimfra.util.StringUtil;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 16-1-7
 * Time: 下午6:27
 */
public class TD {
    public String className;
    private String style="";
    public String text;

    public TD() {
    }

    public TD(String text) {
        this.text = text;
    }

    public TD(String className, String text) {
        this.className = className;
        this.text = text;
    }

    public String toHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<td");
        if(className !=null) {
            sb.append(" class='").append(className).append("'");
        }

        if(style==null) {
            style = "";
        }
        if(text !=null && text.matches("0\\.0+")) {
            style+="color: grey; ";
        }
        if(StringUtil.notEmpty(style)) {
            sb.append(" style='").append(style).append("'");
        }

        sb.append(">");
        if(text !=null) {
            sb.append(text);
        }
        sb.append("</td>");

        return sb.toString();
    }

    public void addStyle(String style) {
        this.style+=style+"; ";
    }
}
