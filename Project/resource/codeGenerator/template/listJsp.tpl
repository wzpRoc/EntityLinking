<%@ page import="java.util.List" %>
<%@ page import="%packagePrefix%.%tableName%.%tableName_upInitial%" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="jsp/%tableName%/%tableName%.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="jsp/%tableName%/%tableName%.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>%tableName%List</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="get" action="%tableName%List">
        <s:hidden name="clearSession"/>
        <s:hidden name="submitTag"/>
        <s:hidden name="dowhat"/>

        <!--%@ include file="../navigation.jsp" %-->
        <div id="titleArea">
            <h1>
                %tableComment%列表
            </h1>
        </div>

        <%--查询条件--%>
        <div id="conditionArea">
            <div class="label">
                姓名：
            </div>
            <div class="value">
                <s:textfield name="condition.name" style="width:80px"/>
            </div>
            <div class="submitButton">
                <button type="submit">查询</button>
                <button type="button" onclick="window.location='%tableName%Detail?dowhat=add'">新增</button>
                <button type="button" onclick="deleteFromList()">删除</button>
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

%tableHeadCells%
                <tr>

                <%--表格内容--%>
                <%
                    for (Object dto : list) {
                        %tableName_upInitial% %tableName% = (%tableName_upInitial%) dto;
                        int id = %tableName%.%getKey%;

                %>
                <tr class="tbody">
                    <td style="text-align: center">
                        <input type="checkbox" value="<%=%tableName%.%getKey%%>" id="id1" name="checkbox_id">
                    </td>
<%--
                    <td>
                        <a href="%tableName%Detail?dowhat=edit&id=<%=id%>">
                            %tableName%<%=%tableName%.%getKey%%>
                        </a>
                    </td>
--%>
%tableDataCells%
                </tr>
                 <%
                     }
                 %>
            </table>
            <br/>
        </div>
    </form>
</div>

</body>
</html>