<%@ page import="java.util.List" %>
<%@ page import="org.ailab.entityLinking.wim.doc.Doc" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="jsp/doc/doc.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="jsp/doc/doc.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>docList</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="get" action="docList">
        <s:hidden name="clearSession"/>
        <s:hidden name="submitTag"/>
        <s:hidden name="dowhat"/>

        <!--%@ include file="../navigation.jsp" %-->
        <div id="titleArea">
            <h1>
                文档列表
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
                <button type="button" onclick="window.location='docDetail?dowhat=add'">新增</button>
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
                        标题
                    </th>
                    <th width="10%">
                        内容
                    </th>
                    <th width="10%">
                        token列表
                    </th>
                    <th width="10%">
                        URL
                    </th>
                    <th width="10%">
                        训练/测试标记
                    </th>
                    <th width="10%">
                        发布日期
                    </th>

                <tr>

                <%--表格内容--%>
                <%
                    for (Object dto : list) {
                        Doc doc = (Doc) dto;
                        int id = doc.getId();

                %>
                <tr class="tbody">
                    <td style="text-align: center">
                        <input type="checkbox" value="<%=doc.getId()%>" id="id1" name="checkbox_id">
                    </td>
<%--
                    <td>
                        <a href="docDetail?dowhat=edit&id=<%=id%>">
                            doc<%=doc.getId()%>
                        </a>
                    </td>
--%>
                    <td>
                        <%=doc.getId()%>
                    </td>
                    <td>
                        <%=doc.getTitle()%>
                    </td>
                    <td>
                        <%=doc.getContent()%>
                    </td>
                    <td>
                        <%=doc.getTokens()%>
                    </td>
                    <td>
                        <%=doc.getUrl()%>
                    </td>
                    <td>
                        <%=doc.getToeTag()%>
                    </td>
                    <td>
                        <%=doc.getPubDate()%>
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
