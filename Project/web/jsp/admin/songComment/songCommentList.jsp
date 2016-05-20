<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.songComment.SongComment" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../adminJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="jsp/songComment/songComment.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="jsp/songComment/songComment.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>songCommentList</title>
</head>
<body>


<div id="fullContent">
    <%@include file="../header.jsp"%>
    <%@include file="../left.jsp"%>
    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="get" action="artistList">
            <s:hidden name="clearSession"/>
            <s:hidden name="submitTag"/>
            <s:hidden name="dowhat"/>
            <input type="hidden" name="dowhatInList" value="<%=dowhat%>"/>


            <div id="titleArea">
                <h1>
                    歌曲评论列表
                </h1>
            </div>

            <%--查询条件--%>
            <div id="conditionArea">
                <div class="label">
                    歌曲：
                </div>
                <div class="value">
                    <s:textfield name="condition.name" style="width:80px"/>
                </div>

                <div class="submitButton">
                    <button type="submit">查询</button>
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
                            歌曲
                        </th>
                        <th width="15%">
                            评论用户
                        </th>
                        <th width="15%">
                            评论时间
                        </th>
                        <th width="15%">
                            评论内容
                        </th>

                    <tr>
                     <%--表格内容--%>
                <%
                      for (Object dto : list) {
                        SongComment songComment = (SongComment) dto;
                        int id = songComment.getId();
                %>
                    <tr class="tbody">
                        <td>
                            <input type="checkbox" value="<%=songComment.getId()%>" id="id1" name="checkbox_id">
                        </td>

                        <td>
                            <%=songComment.getSongId()%>
                        </td>
                        <td>
                            <%=songComment.getUserId()%>
                        </td>
                        <td>
                            <%=songComment.getContent()%>
                        </td>
                        <td>
                            <%=songComment.getOpTime()%>
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
