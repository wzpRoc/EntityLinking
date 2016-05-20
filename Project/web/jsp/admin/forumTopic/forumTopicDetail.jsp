<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.forumTopic.ForumTopic" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page import="org.ailab.tem.wim.forumReply.ForumReply" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../adminJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/detail.css" rel="stylesheet" type="text/css"/>
<link href="../css/tcal.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>
<link href="../jsp/admin/forumTopic/forumTopic.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="../js/tcal.js"></script>
<script language="javascript" src="../js/string.js"></script>
<script language="javascript" src="../js/time.js"></script>
<script language="javascript" src="../js/uploadImage.js"></script>
<script language="javascript" src="../js/jquery.form.js"></script>
<script language="javascript" src="../jsp/admin/forumTopic/forumTopic.js"></script>

<%
    ForumTopic forumTopic = (ForumTopic) request.getAttribute("dto");
    List topicReplysList = (List) request.getAttribute("forumReplyList");

%>

<html>
<head>
    <title>论坛话题详情</title>
</head>
<body>

<div id="fullContent">
    <%@include file="../header.jsp"%>
    <%@include file="../left.jsp"%>
    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="post" action="forumTopicDetail">
            <input type="hidden" name="saveTag" value="1"/>
            <s:hidden name="dowhat"/>
            <s:hidden name="dto.id"/>
            <s:hidden name="dto.userId"/>
            <s:hidden name="dto.userName"/>
            <s:hidden name="dto.pubdate"/>
            <s:hidden name="dto.pubTime"/>
            <s:hidden name="dto.lastReplyDate"/>
            <s:hidden name="dto.lastReplyTime"/>
            <s:hidden name="dto.votingScore"/>
            <div id="titleArea">
                <h1>
                    论坛话题详情
                </h1>
            </div>

            <%--功能按钮区--%>
            <div id="topArea">
                &nbsp;
                <div id="buttonArea">
                    <button type="submit">保存</button>
                    <button type="button" onclick="window.location='forumTopicList'">返回</button>
                </div>
            </div>

            <%--数据表格区--%>
            <div id="dataArea">
                <%
                    if("add".equals(request.getParameter("dowhat"))) {
                %>

                <table id="dataTable">
                    <tr>
                        <td class="label">话题类别:</td>
                        <td class="value"><w:select name="dto.categoryId" addPleaseSelect="true" dataId="TopicCategory"/></td>
                    </tr>
                    <tr>
                        <td class="label">标题:</td>
                        <td class="value"><s:textfield name="dto.title"/></td>
                    </tr>
                    <tr>
                        <td class="label">正文:</td>
                        <td class="value"><s:textarea name="dto.content" cols="40" rows="8"/></td>
                    </tr>
                    <tr>
                        <td class="label">总回复数:</td>
                        <td class="value"><s:textfield name="dto.totalReplyNum"/></td>
                    </tr>
                    <tr>
                        <td class="label">重要性:</td>
                        <td class="value"><s:textfield name="dto.importance"/></td>
                    </tr>
                    <tr>
                        <td class="label">审核结果:</td>
                        <td class="value"><w:select name="dto.isVisable" addPleaseSelect="true" dataId="ForumCheckType"/></td>
                    </tr>
                </table>
                <%
                    } else {
                %>
                <div id ="topicContent">
                    <div class="subTitle" onclick="toggleSubData(this)">话题内容</div>
                    <div class="subData">
                        <table id="dataTable">
                            <tr>
                                <td class="label">话题类别:</td>
                                <td class="value"><w:select name="dto.categoryId" addPleaseSelect="true" dataId="TopicCategory"/></td>
                            </tr>
                            <tr>
                                <td class="label">标题:</td>
                                <td class="value"><s:textfield name="dto.title"/></td>
                            </tr>
                            <tr>
                                <td class="label">正文:</td>
                                <td class="value"><s:textarea name="dto.content" cols="40" rows="8"/></td>
                            </tr>
                            <tr>
                                <td class="label">总回复数:</td>
                                <td class="value"><s:textfield name="dto.totalReplyNum"/></td>
                            </tr>
                            <tr>
                                <td class="label">重要性:</td>
                                <td class="value"><s:textfield name="dto.importance"/></td>
                            </tr>
                            <tr>
                                <td class="label">审核结果:</td>
                                <td class="value"><w:select name="dto.isVisable" addPleaseSelect="true" dataId="ForumCheckType"/></td>
                            </tr>
                        </table>
                    </div>
                </div>

                <div id ="topicReplyList">
                    <div class="subTitle" onclick="toggleSubData(this)">话题回复</div>
                    <div class="subData">
                        <ul class="replyList">
                            <%
                                for(Object obj: topicReplysList) {
                                    ForumReply forumReply  = (ForumReply) obj;
                            %>
                            <li>
                                <div class="commentUser">
                                    <%=forumReply.getFloor()%>楼
                                    <span class="deleteComment" onclick="deleteProductComment(this, 1)">删除此评论</span>
                                </div>
                                <div class="commentContent">
                                    <%=forumReply.getUserName()%>:<%=forumReply.getContent()%>
                                </div>
                                <div class="commentTime"><%=forumReply.getReplyTime()%></div>
                                <div class="commentCommit">
                                    <%
                                        if(forumReply.getIsVisable() == 0){
                                    %>
                                    <button type="button" onclick="passCheck(<%=forumReply.getId()%>)">审核通过</button>
                                    <button type="button" onclick="failCheck(<%=forumReply.getId()%>)">审核不通过</button>
                                    <%
                                        }else {
                                    %>
                                    <span class = "commentStatus">当前状态：<%=forumReply.getCheckResult()%></span>

                                    <%
                                        }
                                    %>
                                </div>

                            </li>
                            <%
                                }
                            %>
                        </ul>
                    </div>
                </div>


                <%
                    }
                %>
            </div>
        </form>
    </div>

    <%@include file="../bottom.jsp"%>

</div>

</body>
</html>

<%@ include file="../../msg.jsp" %>
