<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.relatedLinks.RelatedLinks" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../adminJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/list.css" rel="stylesheet" type="text/css"/>
<link href="jsp/album/album.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="jsp/relatedLinks/relatedLinks.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>相关链接</title>
</head>
<body>

<div id="fullContent">
    <%@include file="../header.jsp"%>
    <%@include file="../left.jsp"%>
    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="get" action="relatedLinksList">
            <s:hidden name="clearSession"/>
            <s:hidden name="submitTag"/>
            <s:hidden name="dowhat"/>

            <!--%@ include file="../navigation.jsp" %-->
            <div id="titleArea">
                <h1>
                    网站链接列表
                </h1>
            </div>

            <%--查询条件--%>
            <div id="conditionArea">
                <div class="submitButton">
                    <button type="submit">查询</button>
                    <button type="button" onclick="window.location='relatedLinksDetail?dowhat=add'">新增</button>
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
                            网站名称
                        </th>
                        <th width="10%">
                            网站链接
                        </th>

                    <tr>

                        <%--表格内容--%>
                            <%
                    for (Object dto : list) {
                        RelatedLinks relatedLinks = (RelatedLinks) dto;
                        int id = relatedLinks.getId();

                %>
                    <tr class="tbody">
                        <td>
                            <input type="checkbox" value="<%=relatedLinks.getId()%>" id="id1" name="checkbox_id">
                        </td>
                        <td>
                            <a href="relatedLinksDetail?dowhat=edit&name=<%=id%>">
                                <%=relatedLinks.getSiteName()%>
                            </a>
                        </td>
                        <td>
                            <%=relatedLinks.getSiteUrl()%>
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
