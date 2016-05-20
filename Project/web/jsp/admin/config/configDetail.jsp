<%@ page import="java.util.List" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page import="org.ailab.common.config.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../adminJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/detail.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>
<link href="jsp/config/config.css" rel="stylesheet" type="text/css"/>

<script language="javascript" src="../js/tcal.js"></script>
<script language="javascript" src="../js/string.js"></script>
<script language="javascript" src="../js/time.js"></script>
<script language="javascript" src="../js/businessCommon.js"></script>
<script language="javascript" src="../jsp/admin/config/config.js"></script>

<%
    Config dto = (Config) request.getAttribute("dto");
%>

<html>
<head>
    <title>配置详情</title>
</head>
<body>

<div id="fullContent">
    <%@include file="../header.jsp" %>
    <%@include file="../left.jsp" %>
    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="post" action="configDetail">
            <input type="hidden" name="saveTag" value="1"/>
            <s:hidden name="dowhat"/>
            <s:hidden name="dto.id"/>

            <div id="titleArea">
                <h1>
                    配置详情
                </h1>
            </div>

            <%--功能按钮区--%>
            <div id="topArea">
                &nbsp;
                <div id="buttonArea">
                    <input type="button" onclick="save()" value="保存" style="cursor: pointer">
                    <input type="button" onclick="window.location='configList'" value="返回" style="cursor: pointer">
                </div>
            </div>

            <%--数据表格区--%>
            <div id="dataArea">
                <table id="dataTable">
                    <tr>
                        <td class="label">模块:</td>
                        <td class="value"><s:textfield name="dto.module"/></td>
                    </tr>
                    <tr>
                        <td class="label">名称:</td>
                        <td class="value"><s:textfield name="dto.name"/></td>
                    </tr>
                    <tr>
                        <td class="label">值:</td>
                        <td class="value"><s:textfield name="dto.value"/></td>
                    </tr>
                    <tr>
                        <td class="label">描述:</td>
                        <td class="value"><s:textarea name="dto.description" cols="100" rows="5"/></td>
                    </tr>

                </table>
            </div>
        </form>
    </div>
</div>

</body>
</html>

<%@ include file="../../msg.jsp" %>
