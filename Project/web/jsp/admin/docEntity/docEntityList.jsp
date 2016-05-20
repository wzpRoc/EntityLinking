<%@ page import="java.util.List" %>
<%@ page import="org.ailab.entityLinking_old.wim.docEntity.DocEntity" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="jsp/docEntity/docEntity.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="jsp/docEntity/docEntity.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>docEntityList</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="get" action="mentionList">
        <s:hidden name="clearSession"/>
        <s:hidden name="submitTag"/>
        <s:hidden name="dowhat"/>

        <!--%@ include file="../navigation.jsp" %-->
        <div id="titleArea">
            <h1>
                文档实体列表
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
                <button type="button" onclick="window.location='docEntityDetail?dowhat=add'">新增</button>
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
                        文档ID
                    </th>
                    <th width="10%">
                        日期
                    </th>
                    <th width="10%">
                        实体类型
                    </th>
                    <th width="10%">
                        实体ID
                    </th>
                    <th width="10%">
                        实体名称
                    </th>
                    <th width="10%">
                        实体值
                    </th>
                    <th width="10%">
                        字段
                    </th>
                    <th width="10%">
                        开始位置
                    </th>
                    <th width="10%">
                        结束位置
                    </th>
                    <th width="10%">
                        国家ID
                    </th>
                    <th width="10%">
                        更新时间
                    </th>
                    <th width="10%">
                        更新者
                    </th>

                <tr>

                <%--表格内容--%>
                <%
                    for (Object dto : list) {
                        DocEntity docEntity = (DocEntity) dto;
                        int id = docEntity.getnull();

                %>
                <tr class="tbody">
                    <td style="text-align: center">
                        <input type="checkbox" value="<%=docEntity.getnull()%>" id="id1" name="checkbox_id">
                    </td>
<%--
                    <td>
                        <a href="docEntityDetail?dowhat=edit&id=<%=id%>">
                            docEntity<%=docEntity.getnull()%>
                        </a>
                    </td>
--%>
                    <td>
                        <%=docEntity.getDocId()%>
                    </td>
                    <td>
                        <%=docEntity.getPubdate()%>
                    </td>
                    <td>
                        <%=docEntity.getEntityType()%>
                    </td>
                    <td>
                        <%=docEntity.getEntityId()%>
                    </td>
                    <td>
                        <%=docEntity.getMention()%>
                    </td>
                    <td>
                        <%=docEntity.getEntityTitle()%>
                    </td>
                    <td>
                        <%=docEntity.getField()%>
                    </td>
                    <td>
                        <%=docEntity.getStartIdx()%>
                    </td>
                    <td>
                        <%=docEntity.getEndIdx()%>
                    </td>
                    <td>
                        <%=docEntity.getCountryId()%>
                    </td>
                    <td>
                        <%=docEntity.getUpdateTime()%>
                    </td>
                    <td>
                        <%=docEntity.getUpdaterName()%>
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
