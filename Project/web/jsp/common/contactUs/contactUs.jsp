<%@ page import="org.ailab.tem.wim.contactComment.ContactComment" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sun.corba.se.spi.ior.ObjectKey" %>
<%@ page import="org.ailab.tem.wim.agentInfo.AgentInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%--<%@include file="../../commonJS.jsp"%>--%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="css/common/header.css" rel="stylesheet" type="text/css"/>
<link href="css/bootstrap/bootstrap-new.css" rel="stylesheet" type="text/css"/>
<link href="jsp/common/contactUs/contactUs.css" rel="stylesheet" type="text/css"/>
<link href="css/globalFrame.css" rel="stylesheet" type="text/css"/>


<%
    ContactComment contactComment = (ContactComment) request.getAttribute("dto");
    HashMap map = (HashMap) request.getAttribute("map");
    List agentInfoList = (List) request.getAttribute("agentInfoList");
%>

<html>
<head>
    <title>联系我们</title>
</head>
<body>


<div id="fullContent">
    <%@include file="../../header.jsp" %>
    <%@include file="../../leftBar.jsp" %>

    <script>
        var userName = "<%=user == null ? "" : user.getNicName()%>";
        var userId = "<%=user==null?"":user.getId() %>"
    </script>

    <s:hidden name="dowhat"/>
    <div id="mainContent" class="Clearfix">
        <div class="contactContent">
            <form id="mainForm" method="get" action="contact">
                <div class="queryContent">
                    <ul>
                        <li><span>姓</span><s:textfield name="dto.firstName"/></li>
                        <li><span>名</span><s:textfield name="dto.secondName"/></li>
                    </ul>
                    <ul>
                        <li><span>电话</span><s:textfield name="dto.telephone"/></li>
                        <li><span>邮箱</span><s:textfield name="dto.email"/></li>
                    </ul>
                    <p>问题与评论</p>
                    <s:textarea name="dto.content" cols="79" rows="8" id="textarea_content" style="border-style:none;width:420px "/>
                    <div class="contactCommit">
                        <button type="button" onclick="saveComment()">提&nbsp;&nbsp;交</button>
                    </div>
                </div>
            </form>

            <div class="companyInfo">
                <h1><%=map.get("ch_coName")%></h1>
                <h2><%=map.get("en_coName")%></h2>
                <ul>
                    <li><p>地址：<%=map.get("co_address")%></p></li>
                    <li><p>邮编：<%=map.get("co_postcode")%></p></li>
                    <li><p>电话：<%=map.get("co_phoneNo")%></p></li>
                    <li><p>传真：<%=map.get("co_fax")%></p></li>
                    <li><p>网址：<%=map.get("co_website")%></p></li>
                    <li><p>邮箱：<%=map.get("co_mail")%></p></li>
                </ul>
            </div>
        </div>

        <div class="splitSide"></div>

        <div class="sideinfo">
            <h1>代理商:</h1>
            <ul>
                <%
                    int id = 0;
                    String agencyNo = "";
                    for(Object object: agentInfoList) {
                        id++;
                        AgentInfo agentInfo = (AgentInfo) object;
                        if(id < 10)
                            agencyNo = "0"+id;
                        else
                            agencyNo = String.valueOf(id);
                %>
                <li><%=agencyNo%>、<%=agentInfo.getName()%></li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>

    <%@include file="../../footer.jsp" %>
</div>
</body>
</html>


<%@include file="../../commonJS.jsp" %>
<script type="text/javascript" src="js/jquery-validate.min.js"></script>
<script type="text/javascript" src="jsp/common/contactUs/contactUs.js"></script>
<script type="text/javascript" src="js/bootstrap-switch.min.js"></script>

