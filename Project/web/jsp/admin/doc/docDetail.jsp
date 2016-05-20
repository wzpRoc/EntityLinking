<%@ page import="java.util.List" %>
<%@ page import="org.ailab.entityLinking_old.wim.doc.Doc" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../adminJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/detail.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>
<link href="../jsp/admin/doc/doc.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="../js/tcal.js"></script>
<script language="javascript" src="../js/string.js"></script>
<script language="javascript" src="../js/time.js"></script>


<%
    Doc doc = (Doc) request.getAttribute("dto");
    doc.setContent("01234567890123456789");
%>

<html>
<head>
    <title>文档详情</title>
</head>
<body>

<div id="fullContent">
    <%@include file="../header.jsp" %>
    <%@include file="../left.jsp" %>
    <div id="mainContent">
    <div id="contentInMainContent">
    <form name="mainForm" id="mainForm" method="post" action="docDetail">
        <input type="hidden" name="saveTag" value="1"/>
        <s:hidden name="dowhat"/>
        <s:hidden name="dto.id"/>

        <div id="titleArea">
            <h1>
                文档详情
            </h1>
        </div>

    <%--功能按钮区--%>
        <div id="topArea">
            &nbsp;
            <div id="buttonArea">
                <button type="submit">保存</button>
                <button type="button" onclick="window.location='docList'">返回</button>
            </div>
        </div>

        <%--数据表格区--%>
        <div id="dataArea">
            <table id="dataTable">
                <tr>
					<td class="label" style="width: 90px">ID:</td>
					<td class="value">
                        <s:textfield name="dto.id"/>
                    </td>
					<td class="value" style="text-align: right; color: #888888">
                        <span style="margin-left: 50px">
                            ${dto.pubDate} 发布
                        </span>
                        <span style="margin-left: 50px">
                            ${dto.updateTime} 修改
                        </span>
                    </td>
				</tr>
				<tr>
					<td class="label">标题:</td>
					<td class="value" colspan="2"><s:textfield name="dto.title" size="85"/></td>
				</tr>
				<tr>
					<td class="label">正文:</td>
					<td class="value" colspan="2"><s:textfield name="dto.content" size="85"/></td>
				</tr>
            </table>
        </div>

        <div id="div_annoText">
            <%
                int i;
                for(i = 0; i<doc.getContent().length();i++) {
                    char c = doc.getContent().charAt(i);
            %><div class="charDiv" id="charDiv_<%=i%>" idx="<%=i%>"
                   <%--onmousedown="charDivMouseDown(<%=i%>, this, 'charDiv_<%=i%>')"--%>
                   <%--onmouseup="charDivMouseUp(<%=i%>, this, 'charDiv_<%=i%>')"--%>
                ><%=c%></div><%
                }
            %>
        </div>
        <%--<div id="annoControlDiv">--%>
            <%--<input type="button" value="增加标注" onclick="addAnno()">--%>
        <%--</div>--%>
        <div id="div_annoListHead">
            <div class="div_annoListHeadElement" style="width: 50px;">开始</div>
            <div class="div_annoListHeadElement" style="width: 50px;">结束</div>
            <div class="div_annoListHeadElement" style="width: 300px;">名称</div>
            <div class="div_annoListHeadElement" style="width: 300px;">实体</div>
        </div>
        <div id="div_annoList">

        </div>
    </form>
    </div>
    <%@include file="../bottom.jsp"%>
    </div>
</div>
</body>
</html>

<%@ include file="/jsp/msg.jsp" %>
<%@ include file="annoInfoTemplet.jsp" %>

<script language="javascript" src="../jsp/admin/doc/doc.js"></script>
