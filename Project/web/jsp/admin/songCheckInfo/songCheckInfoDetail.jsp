<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.songCheckInfo.SongCheckInfo" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/detail.css" rel="stylesheet" type="text/css"/>
<link href="jsp/songCheckInfo/songCheckInfo.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="js/tcal.js"></script>
<script language="javascript" src="js/string.js"></script>
<script language="javascript" src="js/time.js"></script>

<script language="javascript" src="jsp/songCheckInfo/songCheckInfo.js"></script>

<%
    SongCheckInfo songCheckInfo = (SongCheckInfo) request.getAttribute("dto");
%>

<html>
<head>
    <title>歌曲审核信息详情</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="post" action="songCheckInfoDetail">
        <input type="hidden" name="saveTag" value="1"/>
        <s:hidden name="dowhat"/>
        <s:hidden name="dto.id"/>

        <div id="titleArea">
            <h1>
                歌曲审核信息详情
            </h1>
        </div>

    <%--功能按钮区--%>
        <div id="topArea">
            &nbsp;
            <div id="buttonArea">
                <button type="submit">保存</button>
                <button type="button" onclick="window.location='songCheckInfoList'">返回</button>
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
					<td class="label">歌曲ID:</td>
					<td class="value"><s:textfield name="dto.song_id"/></td>
				</tr>
				<tr>
					<td class="label">类型:</td>
					<td class="value"><s:textfield name="dto.type"/></td>
				</tr>
				<tr>
					<td class="label">分配者ID:</td>
					<td class="value"><s:textfield name="dto.assigner_id"/></td>
				</tr>
				<tr>
					<td class="label">分配时间:</td>
					<td class="value"><s:textfield name="dto.assign_time"/></td>
				</tr>
				<tr>
					<td class="label">编辑者ID:</td>
					<td class="value"><s:textfield name="dto.editor_id"/></td>
				</tr>
				<tr>
					<td class="label">审核者ID:</td>
					<td class="value"><s:textfield name="dto.checker_id"/></td>
				</tr>
				<tr>
					<td class="label">提交时间:</td>
					<td class="value"><s:textfield name="dto.submit_time"/></td>
				</tr>
				<tr>
					<td class="label">当前状态:</td>
					<td class="value"><s:textfield name="dto.current_status"/></td>
				</tr>

            </table>
        </div>
    </form>
</div>

</body>
</html>

<%@ include file="../msg.jsp" %>
