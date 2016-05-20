<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.homeInfo.HomeInfo" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../adminJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/list.css" rel="stylesheet" type="text/css"/>
<%--<link href="jsp/album/album.css" rel="stylesheet" type="text/css"/>--%>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>

<script language="javascript" src="jsp/homeInfo/homeInfo.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>首页信息列表</title>
</head>
<body>
<div id="fullContent">
    <%@include file="../header.jsp"%>
    <%@include file="../left.jsp"%>
    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="get" action="homeInfoList">
            <s:hidden name="clearSession"/>
            <s:hidden name="submitTag"/>
            <s:hidden name="dowhat"/>

            <!--%@ include file="../navigation.jsp" %-->
            <div id="titleArea">
                <h1>
                    首页信息列表
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
                    <button type="button" onclick="window.location='homeInfoDetail?dowhat=add'">新增</button>
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
                            小图
                        </th>
                        <th width="10%">
                            大图
                        </th>

                    <tr>

                        <%--表格内容--%>
                            <%
                    for (Object dto : list) {
                        HomeInfo homeInfo = (HomeInfo) dto;
                        int id = homeInfo.getId();

                %>
                    <tr class="tbody">
                        <td>
                            <input type="checkbox" value="<%=homeInfo.getId()%>" id="id1" name="checkbox_id">
                        </td>
                        <%--
                                            <td>
                                                <a href="homeInfoDetail?dowhat=edit&id=<%=id%>">
                                                    homeInfo<%=homeInfo.getId()%>
                                                </a>
                                            </td>
                        --%>
                        <td>
                            <a href="homeInfoDetail?dowhat=edit&name=<%=id%>">
                                图片<%=homeInfo.getId()%>
                            </a>
                        </td>
                        <td>
                            <%=homeInfo.getPic()%>
                        </td>
                        <td>
                            <%=homeInfo.getPic_big()%>
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
