<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.pointsLog.PointsLog" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../adminJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/list.css" rel="stylesheet" type="text/css"/>
<link href="../css/tcal.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="../js/tcal.js"></script>
<script language="javascript" src="../js/string.js"></script>
<script language="javascript" src="../js/time.js"></script>
<script language="javascript" src="jsp/pointsLog/pointsLog.js"></script>

<%
    String pageTitle="积分操作日志查询";
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title><%=pageTitle%></title>
</head>
<body>

<div id="fullContent">
    <%@include file="../header.jsp"%>
    <%@include file="../left.jsp"%>
    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="get" action="pointsLogList">
            <s:hidden name="clearSession"/>
            <s:hidden name="submitTag"/>
            <s:hidden name="dowhat"/>

            <!--%@ include file="../navigation.jsp" %-->
            <div id="titleArea">
                <h1>
                    <%=pageTitle%>
                </h1>
            </div>

            <%--查询条件--%>
            <div id="conditionArea">
                <div class="label">
                    开始日期
                </div>
                <div>
                    <input type="text" id="condition.dateBegin" name="condition.dateBegin" value="${condition.dateBegin}" rel="tcal" class="tcalInput" style="width:110px"/>
                </div>
                <div class="label">
                    结束日期
                </div>
                <div>
                    <input type="text" id="condition.dateEnd" name="condition.dateEnd" value="${condition.dateEnd}" value="" rel="tcal" class="tcalInput" style="width:110px"/>
                </div>
                <div class="label" width="40">
                    用户
                </div>
                <div>
                    <w:select name="condition.userId" dataId="user" addSelectAll="true"/>
                </div>
                <div class="label" width="25">
                    类型
                </div>
                <div>
                    <w:select name="condition.type" dataId="pointsType" addSelectAll="true"/>
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
                        <th width="10%">
                            用户
                        </th>
                        <th width="10%">
                            交易类型
                        </th>
                        <th width="10%">
                            数量
                        </th>
                        <th width="20%">
                            操作时间
                        </th>
                        <th width="10%">
                            请求ip
                        </th>


                    <tr>

                        <%--表格内容--%>
                            <%
                    for (Object dto : list) {
                        PointsLog pointsLog = (PointsLog) dto;
                        int id = pointsLog.getId();

                %>
                    <tr class="tbody">
                        <td class="td_checkbox">
                            <input type="checkbox" value="<%=pointsLog.getId()%>" id="id1" name="checkbox_id">
                        </td>
                        <%--
                                            <td>
                                                <a href="pointsLogDetail?dowhat=edit&id=<%=id%>">
                                                    pointsLog<%=pointsLog.getId()%>
                                                </a>
                                            </td>
                        --%>
                        <td>
                            <%=pointsLog.getUserName()%>
                        </td>
                        <td>
                            <%=pointsLog.getTypeName()%>
                        </td>
                        <td>
                            <%=pointsLog.getAmount()%>
                        </td>
                        <td>
                            <%=pointsLog.getOpTime()%>
                        </td>
                        <td>
                            <%=pointsLog.getIp()%>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </table>
                <br/>
            </div>
            <%@include file="../../pageNavigation.jsp"%>
        </form>
    </div>
    <%@include file="../bottom.jsp"%>
</div>



</body>
</html>
