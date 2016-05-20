<%@ page import="java.util.List" %>
<%@ page import="org.ailab.entityLinking_old.wim.pageAbst.PageAbst" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="jsp/pageAbst/pageAbst.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="jsp/pageAbst/pageAbst.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>pageAbstList</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="get" action="pageAbstList">
        <s:hidden name="clearSession"/>
        <s:hidden name="submitTag"/>
        <s:hidden name="dowhat"/>

        <!--%@ include file="../navigation.jsp" %-->
        <div id="titleArea">
            <h1>
                维基页面摘要列表
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
                <button type="button" onclick="window.location='pageAbstDetail?dowhat=add'">新增</button>
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

                    <th width="10%">
                        page_id
                    </th>
                    <th width="10%">
                        page_title
                    </th>
                    <th width="10%">
                        page_abst
                    </th>
                    <th width="10%">
                        page_abst_len
                    </th>

                <tr>

                <%--表格内容--%>
                <%
                    for (Object dto : list) {
                        PageAbst pageAbst = (PageAbst) dto;
                        int id = pageAbst.getPage_id();

                %>
                <tr class="tbody">
                    <td style="text-align: center">
                        <input type="checkbox" value="<%=pageAbst.getPage_id()%>" id="id1" name="checkbox_id">
                    </td>
<%--
                    <td>
                        <a href="pageAbstDetail?dowhat=edit&id=<%=id%>">
                            pageAbst<%=pageAbst.getPage_id()%>
                        </a>
                    </td>
--%>
                    <td>
                        <%=pageAbst.getPage_id()%>
                    </td>
                    <td>
                        <%=pageAbst.getPage_title()%>
                    </td>
                    <td>
                        <%=pageAbst.getPage_abst()%>
                    </td>
                    <td>
                        <%=pageAbst.getPage_abst_len()%>
                    </td>

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
