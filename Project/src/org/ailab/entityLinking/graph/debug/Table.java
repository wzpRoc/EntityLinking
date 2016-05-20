package org.ailab.entityLinking.graph.debug;

import org.ailab.wimfra.util.FileUtil;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 16-1-7
 * Time: 下午6:26
 */
public class Table extends ArrayList<TR> {
    public String toHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table>\n");

        for(TR tr:this) {
            sb.append(tr.toHTML());
        }

        sb.append("</table>\n");

        return sb.toString();
    }

    public void save(String path) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>\n");
        sb.append("<head>\n");
        sb.append("<link href=\"file://D:\\Projects\\EntityLinking\\Project\\web\\css\\matrix.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
        sb.append("</head>\n");
        sb.append("<body>\n");
        sb.append(this.toHTML());
        sb.append("</body>\n");
        sb.append("</html>\n");

        FileUtil.write(sb.toString(), path);
    }
}
