<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.user.User" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>
<%@ taglib prefix="c" uri="/struts-tags" %>

<%@include file="../adminJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/detail.css" rel="stylesheet" type="text/css"/>
<link href="../jsp/admin/user/user.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>

<script language="javascript" src="../js/tcal.js"></script>
<script language="javascript" src="../js/string.js"></script>
<script language="javascript" src="../js/time.js"></script>

<%
    User  user = (User) request.getAttribute("dto");

%>

<html>
<head>
    <title>用户详情</title>
</head>
<body>

<div id="fullContent">
    <%@include file="../header.jsp"%>
    <%@include file="../left.jsp"%>
<div id="mainContent">
    <form name="mainForm" id="mainForm" method="post" action="userDetail">
        <input type="hidden" name="saveTag" value="1"/>
        <s:hidden name="dowhatInList" value="adminUpdate"/>
        <s:hidden name="dowhat"/>
        <s:hidden name="dto.id"/>


        <div id="titleArea">
            <h1>
                用户详情
            </h1>
        </div>

    <%--功能按钮区--%>
        <div id="topArea">
            &nbsp;
            <div id="buttonArea">
                <button type="submit">保存</button>
                <button type="button" onclick="window.location='userList'">返回</button>
            </div>
        </div>

        <%--数据表格区--%>
        <div id="dataArea">
            <table id="dataTable">
				<tr>
					<td class="label">登录用户名:</td>
					<td class="value"><s:textfield name="dto.username"/></td>
				</tr>
				<tr>
					<td class="label">密码:</td>
					<td class="value"><s:textfield name="dto.password"/></td>
				</tr>
				<tr>
					<td class="label">昵称:</td>
					<td class="value"><s:textfield name="dto.nicName"/></td>
				</tr>
				<tr>
					<td class="label">真实姓名:</td>
					<td class="value"><s:textfield name="dto.realName"/></td>
				</tr>
				<tr>
					<td class="label">角色:</td>
					<td class="value"><w:select name="dto.role" dataId="userRole"/></td>
				</tr>
				<tr>
					<td class="label">客户类别:</td>
					<td class="value"><w:select name="dto.customerType" dataId="customerType"/></td>
				</tr>
				<tr>
					<td class="label">积分余额:</td>
					<td class="value"><s:textfield name="dto.points"/></td>
				</tr>
				<tr>
					<td class="label">等级:</td>
					<td class="value"><w:select name="dto.grade" dataId="userGrade"/></td>
				</tr>
				<tr>
					<td class="label">等级分数:</td>
					<td class="value"><s:textfield name="dto.gradePoint"/></td>
				</tr>
				<tr>
					<td class="label">注册时间:</td>
					<td class="value"><s:textfield name="dto.registerTime"/></td>
				</tr>
				<tr>
					<td class="label">最近登录时间:</td>
					<td class="value"><s:textfield name="dto.lastLoginTime"/></td>
				</tr>
				<tr>
					<td class="label">邮箱:</td>
					<td class="value"><s:textfield name="dto.email"/></td>
				</tr>
				<tr>
					<td class="label">移动电话:</td>
					<td class="value"><s:textfield name="dto.mobilephone"/></td>
				</tr>
				<tr>
					<td class="label">固定电话:</td>
					<td class="value"><s:textfield name="dto.telephone"/></td>
				</tr>
				<tr>
					<td class="label">头像URL:</td>
					<td class="value"><s:textfield name="dto.photoUrl"/></td>
				</tr>
				<tr>
					<td class="label">性别:</td>
					<td class="value"><w:select name="dto.sex" dataId="sex"/></td>
				</tr>
				<tr>
					<td class="label">生日:</td>
					<td class="value"><s:textfield name="dto.birthday"/></td>
				</tr>
				<tr>
					<td class="label">通信地址:</td>
					<td class="value"><s:textfield name="dto.address"/></td>
				</tr>
				<tr>
					<td class="label">爱好:</td>
					<td class="value"><s:textfield name="dto.hobby"/></td>
				</tr>
				<tr>
					<td class="label">车型:</td>
					<td class="value"><s:textfield name="dto.carType"/></td>
				</tr>
				<tr>
					<td class="label">购车日期:</td>
					<td class="value"><s:textfield name="dto.carPurchaseDate"/></td>
				</tr>
				<tr>
					<td class="label">学历:</td>
					<td class="value"><w:select name="dto.degree" dataId="degree"/></td>
				</tr>
				<tr>
					<td class="label">证件类别:</td>
					<td class="value"><w:select name="dto.certificateType" dataId="certificateType"/></td>
				</tr>
				<tr>
					<td class="label">证件号码:</td>
					<td class="value"><s:textfield name="dto.certificateNo"/></td>
				</tr>
				<tr>
					<td class="label">设备标志:</td>
					<td class="value"><w:select name="dto.deviceTag" dataId="deviceTag"/><a href="deviceList?condition.userId=<%=user.getId()%>">(绑定设备详情)</a></td>

				</tr>
				<tr>
					<td class="label">状态:</td>
					<td class="value"><w:select name="dto.state" dataId="state"/></td>
				</tr>
				<tr>
					<td class="label">发送验证时间:</td>
					<td class="value"><s:textfield name="dto.sendValidationTime"/></td>
				</tr>
				<tr>
					<td class="label">验证码:</td>
					<td class="value"><s:textfield name="dto.authenticode"/></td>
				</tr>

            </table>
        </div>
    </form>
</div>
<%@include file="../bottom.jsp"%>
</div>
</body>
</html>

<%@ include file="/jsp/msg.jsp" %>