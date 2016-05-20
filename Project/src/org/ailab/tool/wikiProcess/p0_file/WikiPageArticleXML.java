package org.ailab.tool.wikiProcess.p0_file;

import java.util.ArrayList;
import java.util.List;

/**
 * User: lutingming
 * Date: 16-1-4
 * Time: 下午2:00
 */
public class WikiPageArticleXML {
    public List<String> lines;
    public String title;
    public boolean isRedirect;
    public int namespace;

    public WikiPageArticleXML() {
        lines = new ArrayList<String>();
        isRedirect=false;
        namespace=-1;
    }

    public void addLine(String line) {
        lines.add(line);
    }
}
