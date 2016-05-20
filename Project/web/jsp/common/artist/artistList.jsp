<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.artist.Artist" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/list.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>
<link href="artist.css" rel="stylesheet" type="text/css"/>




<script language="javascript" src="artist.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>artistList</title>
</head>
<body>


<div id="fullContent">
    <%@include file="../../header_old.jsp"%>
    <%@include file="../left.jsp"%>
    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="get" action="artistList">
            <s:hidden name="clearSession"/>
            <s:hidden name="submitTag"/>
            <s:hidden name="dowhat"/>
            <input type="hidden" name="dowhatInList" value="<%=dowhat%>"/>


            <div id="titleArea">
                <h1>
                    艺人列表
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
                <div class="label">
                    艺人状态:
                </div>
                <div class="value">
                    <%--<w:select name="condition.propertyState" dataId="propertyState" onchange="document.getElementById('mainForm').submit()"/>--%>
                    <s:select name="condition.propertyState" list="propertyStateList" listKey="value" listValue="text" onchange="document.getElementById('mainForm').submit()" style="width:80px"/>
                </div>
                <div class="submitButton">
                    <button type="submit">查询</button>
                    <%
                        if(!"annotation".equals(request.getParameter("dowhat")) && !"check".equals(request.getParameter("dowhat"))) {
                    %>
                    <button type="button" onclick="window.location='artistDetail?dowhat=add'">新增</button>
                    <button type="button" onclick="deleteFromList()">删除</button>
                    <%
                        }
                    %>
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


                        <th width="40%">
                            标题
                        </th>
                        <th width="15%">
                            标注人
                        </th>
                        <th width="15%">
                            审核人
                        </th>
                        <th width="15%">
                            状态
                        </th>

                    <tr>

                        <%--表格内容--%>
                            <%
                    for (Object dto : list) {
                        Artist artist = (Artist) dto;
                        int id = artist.getId();

                %>
                    <tr class="tbody">
                        <td>
                            <input type="checkbox" value="<%=artist.getId()%>" id="id1" name="checkbox_id">
                        </td>
                        <%--
                                            <td>
                                                <a href="artistDetail?dowhat=edit&id=<%=id%>">
                                                    artist<%=artist.getId()%>
                                                </a>
                                            </td>
                        --%>
                        <td>
                            <%
                                String tempdowhat = request.getParameter("dowhat");
                                if(!"annotation".equals(tempdowhat) && !"check".equals(tempdowhat)) {
                            %>
                            <a href="artistDetail?dowhatInList=<%=dowhat%>&dowhat=edit&id=<%=id%>">
                                <%=artist.getName()%>
                            </a>

                            <%
                            } else {
                            %>
                            <a href="artistDetail?dowhatInList=<%=dowhat%>&dowhat=<%=tempdowhat%>&id=<%=id%>">
                                <%=artist.getName()%>
                            </a>
                            <%
                                }
                            %>
                        </td>
                        <td>
                            <%=artist.getPropertyEditorName()%>
                        </td>
                        <td>
                            <%=artist.getPropertyCheckerName()%>
                        </td>
                        <td>
                            <%=artist.getPropertyStateDesc()%>
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
