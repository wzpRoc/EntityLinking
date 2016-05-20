<%@ page import="java.util.List" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page import="org.ailab.wimfra.webServer.AccessCountInterceptor" %>
<%@ page import="org.ailab.wimfra.webServer.Counter" %>
<%@ page import="org.ailab.wimfra.webServer.DailyAccessCounter" %>
<%@ page import="org.ailab.wimfra.util.ObjectAndCount" %>
<%@ page import="org.ailab.tem.wim.user.UserBL" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@include file="../adminJS.jsp" %>
<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/list.css" rel="stylesheet" type="text/css"/>
<link href="../css/tcal.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>
<link href="../jsp/admin/accessCounter/accessCounter.css" rel="stylesheet" type="text/css"/>

<script language="javascript" src="../js/tcal.js"></script>
<script language="javascript" src="../js/string.js"></script>
<script language="javascript" src="../js/time.js"></script>
<script language="javascript" src="../jsp/admin/accessCounter/accessCounter.js"></script>

<%
    String pageTitle = "访问次数查询";
    String currentDate;
    List<DailyAccessCounter> accessCounterList = AccessCountInterceptor.getAccessCounterList();
    if (accessCounterList == null || accessCounterList.size() == 0) {
        currentDate = "异常：accessCounterList没有初始化";
    } else {
        currentDate = accessCounterList.get(0).getCurrentDate();
    }
%>

<html>
<head>
    <title><%=pageTitle%>
    </title>
</head>
<body>

<div id="fullContent">
    <%@include file="../header.jsp" %>
    <%@include file="../left.jsp" %>

    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="get" action="accessCounterList">
            <s:hidden name="clearSession"/>
            <s:hidden name="submitTag"/>
            <s:hidden name="dowhat"/>

            <!--%@ include file="../navigation.jsp" %-->
            <div id="titleArea">
                <h1>
                    <%=pageTitle%>
                </h1>
            </div>

            <%--查询条件--%>
            <div id="conditionArea">
                <div class="label">
                    当前日期
                </div>
                <div>
                    <%=currentDate%>
                </div>
                <div class="submitButton">
                    <button type="submit">刷新</button>
                </div>
            </div>

            <%
                if (accessCounterList != null) {
                    for (DailyAccessCounter accessCounter : accessCounterList) {
            %>
            <%--查询结果--%>
            <div id="dataArea">
                <%--<div><%=accessCounter.getName()%></div>--%>
                <table id="dataTable" cellSpacing="0">
                    <%--表头--%>
                    <tr class="thead">
                        <th width="70%">
                            <%=accessCounter.getName()%>
                        </th>
                        <th width="30%">
                            访问次数
                        </th>
                    <tr>
                    <%
                        for (ObjectAndCount objectAndCount : accessCounter.getCounter().toDescOrderList(-1)) {
                    %>
                    <tr class="tbody">
                        <td>
                            <div>
                                <%="IP".equals(accessCounter.getName())
                                        ?objectAndCount.object
                                        :UserBL.getUserNameFromCache(Integer.parseInt((String)objectAndCount.object))
                                %>
                            </div>
                        </td>
                        <td style="text-align: right">
                            <div><%=objectAndCount.count%>
                            </div>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </table>
                <br/>
            </div>
            <%
                    }
                }
            %>

            <%--<%@include file="../../pageNavigation.jsp"%>--%>
        </form>
    </div>

    <%@include file="../bottom.jsp" %>
</div>

</body>
</html>

<%@ include file="../../msg.jsp" %>
