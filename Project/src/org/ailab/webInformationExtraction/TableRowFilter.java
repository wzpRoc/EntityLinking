package org.ailab.webInformationExtraction;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.tags.TableRow;

/**
 * 表格行过滤器
 */
public class TableRowFilter implements NodeFilter {
    private int columnCount;

    public TableRowFilter(int columnCount) {
        this.columnCount = columnCount;
    }

    public boolean accept(Node node) {
        if (node instanceof TableRow) {
            TableRow row = (TableRow) node;
            if (row.getColumnCount() == columnCount) {
                return true;
            }
        }

        return false;
    }
}