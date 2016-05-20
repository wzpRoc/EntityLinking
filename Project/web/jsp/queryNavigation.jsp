<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="pageNavigationArea" style="margin: 0 auto;text-align: center">
    <ul class="pagination">

    <%
        // 上一页 1 2 3 4 5 ... 10 11 12 13 14 ... 16 17 18 19 20 下一页
        //               end1  start2      end2   start3
        final int totalPageNum = listPage.getTotalPageNum();
        final int currentPageNo = listPage.getCurrentPageNo();
        final int segmentSize = 2;
    %>
    <s:hidden name="page.currentPageNo"/>
    <%
        if(currentPageNo==1) {
    %>
        <li class="disabled" style="cursor: pointer">
            <a class="gray">&laquo;</a>
        </li>
    <%
    } else {
    %>
        <li class="disabled">
            <a title="转到上一页" href="javascript:gotoPage(<%=currentPageNo-1%>)" style="cursor: pointer">&laquo;</a>
        </li>
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
        <li class="disabled" style="cursor: pointer">
            <span>...</span>
        </li>


    <%
            }
        if(i_page == currentPageNo){
    %>
        <li class="active">
            <a href="javascript:gotoPage(<%=i_page%>)" style="background: #FFE047;"><span><%=i_page%></span></a>
        </li>
    <%
        } else{
    %>

        <li class="disabled">
            <a href="javascript:gotoPage(<%=i_page%>)" style="cursor: pointer"><span><%=i_page%></span></a>
        </li>

    <%
            }
            last_printed_i_page = i_page;
        }
    %>

    <%
        if(currentPageNo==totalPageNum) {
    %>
        <li class="disabled">
            <a class="gray">&raquo;</a>
        </li>
    <%
    } else {
    %>
        <li class="disabled">
            <a title="转到下一页" href="javascript:gotoPage(<%=currentPageNo+1%>)" style="cursor: pointer">&raquo;</a>
        </li>
    <%
        }
    %>
    </ul>
    <%--<div>--%>
        <%--<span>共<%=listPage.getTotalRecordNum()%>条记录，</span>--%>
        <%--<s:textfield id="numPerPage" size="2" style="width: 40px;height: 20px;margin: 1px" name="page.recordNumPerPage" onchange="validateNum()"/>条记录/页--%>
    <%--</div>--%>


</div>