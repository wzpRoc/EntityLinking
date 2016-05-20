<%@ page import="java.util.List" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page import="org.ailab.tem.wim.aboutUs.AboutUs" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../adminJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/list.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="jsp/aboutUs/aboutUs.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>关于我们</title>
</head>
<body>

<div id="fullContent">
    <%@include file="../header.jsp"%>
    <%@include file="../left.jsp"%>
    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="get" action="aboutUsList">
            <s:hidden name="clearSession"/>
            <s:hidden name="submitTag"/>
            <s:hidden name="dowhat"/>

            <!--%@ include file="../navigation.jsp" %-->
            <div id="titleArea">
                <h1>
                    关于我们列表
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
                    <button type="button" onclick="window.location='aboutUsDetail?dowhat=add'">新增</button>
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
                            ID
                        </th>
                        <th width="10%">
                            类别ID
                        </th>
                        <th width="10%">
                            类别名称
                        </th>
                        <th width="10%">
                            内容
                        </th>
                        <th width="10%">
                            描述
                        </th>

                    <tr>

                        <%--表格内容--%>
                            <%
                    for (Object dto : list) {
                        AboutUs aboutUs = (AboutUs) dto;
                        int id = aboutUs.getId();

                %>
                    <tr class="tbody">
                        <td style="text-align: center">
                            <input type="checkbox" value="<%=aboutUs.getId()%>" id="id1" name="checkbox_id">
                        </td>
                        <%--
                                            <td>
                                                <a href="aboutUsDetail?dowhat=edit&id=<%=id%>">
                                                    aboutUs<%=aboutUs.getId()%>
                                                </a>
                                            </td>
                        --%>
                        <td>
                            <%=aboutUs.getId()%>
                        </td>
                        <td>
                            <%=aboutUs.getCategoryId()%>
                        </td>
                        <td>
                            <%=aboutUs.getCategoryName()%>
                        </td>
                        <td>
                            <%=aboutUs.getContent()%>
                        </td>
                        <td>
                            <%=aboutUs.getDescription()%>
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
    <%@include file="../bottom.jsp"%>
</div>


</body>
</html>
