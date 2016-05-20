<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.user.User" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page import="org.ailab.wimfra.database.DBUtilInstance" %>
<%@ page import="org.ailab.tem.DBConfig" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../adminJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/list.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>
<link href="../jsp/admin/user/user.css" rel="stylesheet" type="text/css"/>
<script language="javascript" src="../jsp/admin/user/user.js"></script>

<%
//    Page listPage = (Page) request.getAttribute("page");
//    List list = listPage.getList();

    Page listPage = new Page();
    List<String[]> list = (new DBUtilInstance(DBConfig.adminDB.getName())).getStringsList(
            "select distinct batchName, batchStartTime " +
                    "from eldoc.linkResult " +
                    "order by batchStartTime desc");

%>

<html>
<head>
    <title>userList</title>
</head>
<body>
<div id="fullContent">
<%@include file="../header.jsp"%>
<%@include file="../left.jsp"%>
<div id="mainContent">
    <form name="mainForm" id="mainForm" method="get" action="userList">
        <s:hidden name="clearSession"/>
        <s:hidden name="submitTag"/>
        <s:hidden name="dowhat"/>

        <!--%@ include file="../navigation.jsp" %-->
        <div id="titleArea">
            <h1>
                实验批次列表
            </h1>
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

                    <th width="100%">
                        ID
                    </th>
                <tr>

                <%--表格内容--%>
                <%
                    for (String[] dto : list) {
                        String batchName = dto[0];
                        String batchStartTime = dto[1];
                %>
                <tr class="tbody">
                    <td>
                        <input type="checkbox" id="id1" name="checkbox_id">
                    </td>
                    <td>
                        <a href="batchStat.jsp?batchName=<%=batchName%>&batchStartTime=<%=batchStartTime%>">
                            <%=batchName+"  "+batchStartTime%>
                        </a>
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
