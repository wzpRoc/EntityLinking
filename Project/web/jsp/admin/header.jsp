<%@ page import="org.ailab.wimfra.util.time.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<META http-equiv="X-UA-Compatible" content="IE=9" > </META>

<%
    Boolean needOuterPart = (Boolean) request.getAttribute("needOuterPart");
    if(needOuterPart!=null && needOuterPart) {
%>
<div id="header">
    <div id="logo">
        <img src="../images/logo.jpg" onclick="window.location='../index'"/>
    </div>
    <h1 id="admin_title">
        Named Entity Recognition & Linking
    </h1>

    <div id="user_info">
        <%--<a href="../">普通用户页面</a>&nbsp;--%>
        <%--当前用户：--%>
        <a href='../userInfo'>${user.realName}</a>
        <%--<a href="#" onclick="return $.feedback.showForm()">用户反馈</a>--%>
        <%--<a id="userMessageInHead" href="${ctx}/userMessageList?dowhat=viewUnreceived&condition.receiverId=${user.id}&condition.received=0" target="_blank"></a>--%>
        <span><%=TimeUtil.getYyyy_mm_dd()%></span> <span id="irica_week">星期<%=TimeUtil.getDayOfWeekInChinese()%></span>
        <span id="user_logout" onclick="adminLogout()">退出</span>
    </div>
</div>
<%
    }
%>

<script language="javascript">
    var userAgent = "<%=request.getHeader("User-Agent")%>";
    var userId = "${userId}"
</script>