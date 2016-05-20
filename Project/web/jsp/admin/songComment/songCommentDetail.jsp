<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.songComment.SongComment" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../adminJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/detail.css" rel="stylesheet" type="text/css"/>
<link href="jsp/songComment/songComment.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="js/tcal.js"></script>
<script language="javascript" src="js/string.js"></script>
<script language="javascript" src="js/time.js"></script>

<script language="javascript" src="jsp/songComment/songComment.js"></script>

<%
    SongComment songComment = (SongComment) request.getAttribute("dto");
%>

<html>
<head>
    <title>歌曲评论详情</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="post" action="songCommentDetail">
        <input type="hidden" name="saveTag" value="1"/>
        <s:hidden name="dowhat"/>
        <s:hidden name="dto.id"/>

        <div id="titleArea">
            <h1>
                歌曲评论详情
            </h1>
        </div>

    <%--功能按钮区--%>
        <div id="topArea">
            &nbsp;
            <div id="buttonArea">
                <button type="submit">保存</button>
                <button type="button" onclick="window.location='songCommentList'">返回</button>
            </div>
        </div>

        <%--数据表格区--%>
        <div id="dataArea">
            <table id="dataTable">
                <tr>
					<td class="label">ID:</td>
					<td class="value"><s:textfield name="dto.id"/></td>
				</tr>
				<tr>
					<td class="label">上级ID:</td>
					<td class="value"><s:textfield name="dto.parentId"/></td>
				</tr>
				<tr>
					<td class="label">歌曲ID:</td>
					<td class="value"><s:textfield name="dto.songId"/></td>
				</tr>
				<tr>
					<td class="label">艺人ID:</td>
					<td class="value"><s:textfield name="dto.userId"/></td>
				</tr>
				<tr>
					<td class="label">专辑ID:</td>
					<td class="value"><s:textfield name="dto.albumId"/></td>
				</tr>
				<tr>
					<td class="label">内容:</td>
					<td class="value"><s:textfield name="dto.content"/></td>
				</tr>
				<tr>
					<td class="label">操作日期:</td>
					<td class="value"><s:textfield name="dto.opDate"/></td>
				</tr>
				<tr>
					<td class="label">操作时间:</td>
					<td class="value"><s:textfield name="dto.opTime"/></td>
				</tr>

            </table>
        </div>
    </form>
</div>

</body>
</html>

<%@ include file="../msg.jsp" %>
