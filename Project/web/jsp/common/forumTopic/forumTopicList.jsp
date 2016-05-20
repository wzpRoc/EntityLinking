<META http-equiv="X-UA-Compatible" content="IE=9">
</META>
<%@ page import="java.util.List" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page import="org.ailab.tem.wim.album.Album" %>
<%@ page import="org.ailab.wimfra.util.StringUtil" %>
<%@ page import="org.ailab.tem.wim.forumTopic.ForumTopic" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%--<%@include file="../../commonJS.jsp"%>--%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="css/common/header.css" rel="stylesheet" type="text/css"/>
<link href="css/bootstrap/bootstrap-new.css" rel="stylesheet" type="text/css"/>
<link href="jsp/common/forumTopic/forumTopic.css" rel="stylesheet" type="text/css"/>


<link href="css/bootstrap/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="css/globalFrame.css" rel="stylesheet" type="text/css"/>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();

%>

<html>
<head>
    <title>超毅论坛</title>
</head>
<body>


<div id="fullContent">
    <%@include file="../../header.jsp" %>
    <%@include file="../../leftBar.jsp" %>

    <script>
        var userName = "<%=user == null ? "" : user.getNicName()%>";
        var userId = "<%=user==null?"":user.getId() %>"
    </script>
    <div id="mainContent" class="Clearfix">

        <form name="mainForm" id="mainForm" method="post" action="forumTopicList">
            <s:hidden name="clearSession"/>
            <input type="hidden" name="submitTag" value="1"/>
            <s:hidden name="dowhat"/>

            <%--二级导航栏--%>
            <div id="subNav">
                <ol>
                    <li>热帖榜</li>
                    <li>音乐欣赏</li>
                    <li>数字播放技术</li>
                    <li>发烧论坛</li>
                    <li>服务热议</li>
                </ol>
            </div>


            <%--查询结果--%>
            <div id="dataArea" class=" Clearfix" style="padding: 10px">
                <%
                    for (Object dto : list) {
                        ForumTopic forumTopic = (ForumTopic) dto;
                        int id = forumTopic.getId();

                %>

                <div class = "topic">
                    <div class = "topicTitle">
                        <a href="forumTopicDetail?name=<%=id%>"><%=forumTopic.getTitle()%></a>
                    </div>
                    <div class = "topicContent">
                        <p><%=forumTopic.getContent()%></p>
                    </div>
                </div>

                <%
                    }
                %>
                <br/>
            </div>
            <%@include file="../../queryNavigation.jsp" %>
        </form>

        <div class= "myTopic">
            <div class="myTopicTitle">
                <label>标题：</label> <s:textfield id="topicTitle"></s:textfield>
            </div>
            <div class="myTopicContent">
                <textarea cols="120" rows="5" id="textarea_content"></textarea>
            </div>
        </div>
        <div class="addButton">
            <button type="button" class="btn btn-default" title="创建话题" onclick="createTopic();">创建话题</button>
        </div>
    </div>

    <%@include file="../../footer.jsp" %>
</div>
</body>
</html>


<%@include file="../../commonJS.jsp" %>
<script type="text/javascript" src="js/jquery-validate.min.js"></script>
<%--<script type="text/javascript" src="js/jquery-validate.min.js"></script>--%>
<script type="text/javascript" src="js/bootstrap-switch.min.js"></script>
<script type="text/javascript" src="jsp/common/forumTopic/forumTopic.js"></script>
