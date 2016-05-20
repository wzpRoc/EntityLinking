<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.forumTopic.ForumTopic" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page import="org.ailab.tem.wim.forumReply.ForumReply" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/detail.css" rel="stylesheet" type="text/css"/>
<link href="css/common/header.css" rel="stylesheet" type="text/css"/>
<link href="jsp/common/forumTopic/forumTopic.css" rel="stylesheet" type="text/css"/>


<link href="css/bootstrap/bootstrap-new.css" rel="stylesheet" type="text/css"/>
<link href="css/bootstrap/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="css/globalFrame.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="jsp/common/forumTopic/forumTopic.js"></script>
<%@include file="../../commonJS.jsp"%>



<%
    List topicReplysList = (List) request.getAttribute("forumReplyList");
    ForumTopic forumTopic = (ForumTopic) request.getAttribute("dto");

%>

<html>
<head>
    <title>论坛话题详情</title>
</head>
<body>

<div id="fullContent">
    <%@include file="../../header.jsp"%>
    <%@include file="../../leftBar.jsp" %>

    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="post" action="forumTopicDetail">
            <s:hidden name= "dto.id"></s:hidden>
            <div class = "topic">
                <div class = "topicTitle">
                    <h2><%=forumTopic.getTitle()%></h2>
                </div>
                <div class = "topicContent">
                    <p><%=forumTopic.getContent()%></p>
                </div>
            </div>
        </form>

        <div class="topicReply">
                <div class="replyList" id="comment">
                <%
                    if(topicReplysList!=null) {
                        for(Object obj: topicReplysList) {
                            ForumReply forumReply = (ForumReply) obj;
                %>
                        <div class="item">
                            <a class="avatar"><img src="images/test/head_default.jpg" alt=""></a>
                            <h5><a href="http://t.qq.com/linlili8184" target="_blank"><%=forumReply.getUserName()%></a>：</h5>
                            <div class="msg_txt">
                                <%=forumReply.getContent()%>
                            </div>
                            <p>发布时间：<%=forumReply.getReplyTime()%></p>
                        </div>
                <%
                        }
                    }
                %>
                </div>
            </div>
        <div class="myReply">
            <h3>我要回复</h3>
            <div class="myReplyContent">
                <textarea cols="120" rows="5" id="textarea_content"></textarea>
            </div>
        </div>
        <div class="addButton">
            <button type="button" class="btn btn-default" title="发表" onclick="replyTopic();">发表</button>
        </div>


    <%@include file="../../footer.jsp" %>
</div>

</body>
</html>

<%@ include file="../../msg.jsp" %>
