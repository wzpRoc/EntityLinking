<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="pageNavigationArea">
    <%
        // 上一页 1 2 3 4 5 ... 10 11 12 13 14 ... 16 17 18 19 20 下一页
        //               end1  start2      end2   start3
        final int totalPageNum = listPage.getTotalPageNum();
        final int currentPageNo = listPage.getCurrentPageNo();
        final int segmentSize = 5;
    %>
    <s:hidden name="page.currentPageNo"/>
    <%
        if(currentPageNo==1) {
    %>
    <a class="gray" style="margin-right: 10px">上一页</a>
    <%
    } else {
    %>
    <a title="转到上一页" href="javascript:gotoPage(<%=currentPageNo-1%>)" style="margin-right: 10px">上一页</a>
    <%
        }
    %>
    <%
        int last_printed_i_page = 0;
        for (int i_page=1; i_page<=totalPageNum; i_page++) {
            if(i_page<=segmentSize || i_page>=totalPageNum-segmentSize+1) {
                // 头部或尾部，输出
            } else if(i_page>=currentPageNo-2 && i_page<=currentPageNo+2) {
                // 当前页前后，输出
            } else {
                // 不输出
                continue;
            }
            if(i_page > last_printed_i_page + 1) {
                // 不连续，输出省略号
    %>
    …
    <%
        }
    %>
    <a href="javascript:gotoPage(<%=i_page%>)" <%=i_page==currentPageNo ? "class='currentPageNo'" : ""%>><span><%=i_page%></span></a>
    <%
            last_printed_i_page = i_page;
        }
    %>

    <%
        if(currentPageNo==totalPageNum) {
    %>
    <a class="gray" style="margin-right: 10px">下一页</a>
    <%
    } else {
    %>
    <A title="转到下一页" href="javascript:gotoPage(<%=currentPageNo+1%>)" style="margin-left: 10px; margin-right: 13px;">下一页</A>
    <%
        }
    %>

    共<%=listPage.getTotalRecordNum()%>条记录，
    <s:textfield id="numPerPage" size="2" style="text-align:right" name="page.recordNumPerPage" onchange="validateNum()"/>条记录/页
</div>