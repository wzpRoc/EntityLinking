<%@ page import="java.util.List" %>
<%@ page import="org.ailab.entityLinking.wim.doc_lob.Doc_lob" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="jsp/doc_lob/doc_lob.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="jsp/doc_lob/doc_lob.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>doc_lobList</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="get" action="doc_lobList">
        <s:hidden name="clearSession"/>
        <s:hidden name="submitTag"/>
        <s:hidden name="dowhat"/>

        <!--%@ include file="../navigation.jsp" %-->
        <div id="titleArea">
            <h1>
                文档大对象列表
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
                <button type="button" onclick="window.location='doc_lobDetail?dowhat=add'">新增</button>
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
                        摘要
                    </th>
                    <th width="10%">
                        正文
                    </th>

                <tr>

                <%--表格内容--%>
                <%
                    for (Object dto : list) {
                        Doc_lob doc_lob = (Doc_lob) dto;
                        int id = doc_lob.getId();

                %>
                <tr class="tbody">
                    <td style="text-align: center">
                        <input type="checkbox" value="<%=doc_lob.getId()%>" id="id1" name="checkbox_id">
                    </td>
<%--
                    <td>
                        <a href="doc_lobDetail?dowhat=edit&id=<%=id%>">
                            doc_lob<%=doc_lob.getId()%>
                        </a>
                    </td>
--%>
                    <td>
                        <%=doc_lob.getId()%>
                    </td>
                    <td>
                        <%=doc_lob.getAbst()%>
                    </td>
                    <td>
                        <%=doc_lob.getContent()%>
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
