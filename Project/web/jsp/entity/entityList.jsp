<%@ page import="java.util.List" %>
<%@ page import="org.ailab.entityLinking.wim.entity.Entity" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="jsp/entity/entity.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="jsp/entity/entity.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>entityList</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="get" action="entityList">
        <s:hidden name="clearSession"/>
        <s:hidden name="submitTag"/>
        <s:hidden name="dowhat"/>

        <!--%@ include file="../navigation.jsp" %-->
        <div id="titleArea">
            <h1>
                实体列表
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
                <button type="button" onclick="window.location='entityDetail?dowhat=add'">新增</button>
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
                        维基百科页面ID
                    </th>
                    <th width="10%">
                        维基百科标题
                    </th>
                    <th width="10%">
                        摘要
                    </th>
                    <th width="10%">
                        出度
                    </th>
                    <th width="10%">
                        入度
                    </th>
                    <th width="10%">
                        流行度
                    </th>

                <tr>

                <%--表格内容--%>
                <%
                    for (Object dto : list) {
                        Entity entity = (Entity) dto;
                        int id = entity.getId();

                %>
                <tr class="tbody">
                    <td style="text-align: center">
                        <input type="checkbox" value="<%=entity.getId()%>" id="id1" name="checkbox_id">
                    </td>
<%--
                    <td>
                        <a href="entityDetail?dowhat=edit&id=<%=id%>">
                            entity<%=entity.getId()%>
                        </a>
                    </td>
--%>
                    <td>
                        <%=entity.getId()%>
                    </td>
                    <td>
                        <%=entity.getWikiPageId()%>
                    </td>
                    <td>
                        <%=entity.getWikiTitle()%>
                    </td>
                    <td>
                        <%=entity.getAbst()%>
                    </td>
                    <td>
                        <%=entity.getOutlinkCount()%>
                    </td>
                    <td>
                        <%=entity.getInlinkCount()%>
                    </td>
                    <td>
                        <%=entity.getPopularity()%>
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
