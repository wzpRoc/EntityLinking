<%@ page import="java.util.List" %>
<%@ page import="org.ailab.common.config.Config" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page import="org.ailab.wimfra.userLogger.UserLogger" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../adminJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/list.css" rel="stylesheet" type="text/css"/>
<link href="../css/tcal.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>
<link href="../jsp/admin/userLogger/userLogger.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="../dwr/engine.js"></script>
<script type="text/javascript" src="../dwr/interface/AjaxInterface.js"></script>

<script language="javascript" src="../js/tcal.js"></script>
<script language="javascript" src="../js/string.js"></script>
<script language="javascript" src="../js/time.js"></script>


<script language="javascript" src="../jsp/admin/userLogger/userLogger.js"></script>

<%
    String pageTitle="用户操作日志查询";
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title><%=pageTitle%></title>
</head>
<body>

<div id="fullContent">
    <%@include file="../header.jsp"%>
    <%@include file="../left.jsp"%>

    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="get" action="userLoggerList">
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
                    开始日期
                </div>
                <div>
                    <input type="text" id="condition.dateBegin" name="condition.dateBegin" value="${condition.dateBegin}" rel="tcal" class="tcalInput"/>
                </div>
                <div class="label">
                    结束日期
                </div>
                <div>
                    <input type="text" id="condition.dateEnd" name="condition.dateEnd" value="${condition.dateEnd}" value="" rel="tcal" class="tcalInput"/>
                </div>
                <div class="label" width="40">
                    用户
                </div>
                <div>
                    <w:select name="condition.userId" dataId="user" addSelectAll="true"/>
                </div>
                <div class="label" width="25">
                    页面
                </div>
                <div>
                    <s:textfield name="condition.className" size="10"/>
                </div>
                <div class="label" width="25">
                    操作
                </div>
                <div>
                    <s:textfield name="condition.method" size="10"/>
                </div>
                <div class="submitButton">
                    <button type="submit">查询</button>
                </div>
            </div>

            <%--查询结果--%>
            <div id="dataArea">
                <table id="dataTable" cellSpacing="0">
                    <%--表头--%>
                    <tr class="thead">
                        <th class="td_checkbox">
                            <input type="checkbox" id="id_head" name="checkbox_id_head"
                                   onclick="on_checkbox_id_head_clicked(this)"/>
                        </th>
                        <th width="20%">
                            <div>页面</div>
                        </th>
                        <th width="10%">
                            <div>用户名</div>
                        </th>
                        <th width="20%">
                            <div>访问时间</div>
                        </th>
                        <th  width="10%">
                            <div>操作</div>
                        </th>
                        <th  width="20%">
                            <div>地址</div>
                        </th>
                    <tr>

                        <%--表格内容--%>
                            <%
                    for (Object obj : list) {
                        UserLogger dto = (UserLogger) obj;
                        int id = dto.getId();

                %>
                        <tr class="tbody">
                            <td class="td_checkbox">
                                <input type="checkbox" value="<%=dto.getId()%>" id="id1" name="checkbox_id">
                            </td>
                            <td>
                                <div><a href="<%=dto.getUrl()%>"> <%=dto.getOpName()%> </div>
                            </td>
                            <td>
                                <div><%=dto.getUserName()%></div>
                            </td>
                            <td>
                                <div><%=dto.getStartTime()%></div>
                            </td>
                            <td>
                                <div><%=dto.getMethod()%></div>
                            </td>
                            <td>
                                <div><%=dto.getIp()%></div>
                            </td>
                        </tr>
                    <%
                        }
                    %>
                </table>
                <br/>
            </div>
            <%@include file="../../pageNavigation.jsp"%>
        </form>
    </div>

    <%@include file="../bottom.jsp"%>
</div>

</body>
</html>

<%@ include file="../../msg.jsp" %>
