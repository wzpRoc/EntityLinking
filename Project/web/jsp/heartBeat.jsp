<%@ page import="org.ailab.wimfra.util.time.TimeUtil" %>
<%@ page import="org.ailab.tem.wim.user.User" %>
<%--
该页面显示心跳消息返回的页面
--%>
<%
    User user = (User) request.getAttribute("user");
    int userId;
    if(user == null) {
        userId = 0;
    } else {
        userId = user.getId();
    }
%>
<%=userId%> <%=TimeUtil.getDate_time_ms()%>