<%@ page import="java.util.List" %>
<%@ page import="org.ailab.entityLinking_old.wim.doc.Doc" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page import="flexjson.JSONSerializer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../adminJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/detail.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>
<link href="../jsp/admin/doc/docAnno.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="../js/tcal.js"></script>
<script language="javascript" src="../js/string.js"></script>
<script language="javascript" src="../js/time.js"></script>
<script language="javascript" src="../js/businessCommon.js"></script>


<%
    Doc doc = (Doc) request.getAttribute("dto");
//    doc.setContent("01234567890123456789");
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
    <form name="mainForm" id="mainForm" method="post" action="docAnno">
        <input type="hidden" name="saveTag" value="1"/>
        <s:hidden name="dowhat"/>
        <s:hidden name="dto.id"/>
        <s:hidden name="dto.pubdate"/>

        <div id="titleArea">
            <h1>
                文档详情
            </h1>
        </div>

    <%--功能按钮区--%>
        <div id="topArea">
            &nbsp;
            <div id="buttonArea">
                <%
                    String updateInfo;
                    String updaterName = doc.getUpdaterName();
                    String updateTime = doc.getUpdateShortTime();
                    if(updaterName != null) {
                        if(updateTime != null) {
                            updateInfo = updaterName+"修改于"+updateTime;
                        } else {
                            updateInfo = updaterName+"修改";
                        }
                    } else {
                        updateInfo = "修改于"+updateTime;
                    }
                %>
                <span style="margin-right: 100px; font-style: italic"><%=updateInfo%></span>
                <button type="button" onclick="save()">保存</button>
                <button type="button" onclick="window.location='docList'">返回</button>
            </div>
        </div>

        <div id="div_annoText">
            <%
                int i;
                for(i = 0; i<doc.getContent().length();i++) {
                    char c = doc.getContent().charAt(i);
            %><div name="charDiv" id="charDiv_<%=i%>" idx="<%=i%>"
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
            <div class="div_annoListHeadElement" style="text-align: right; width: 50px;">开始</div>
            <div class="div_annoListHeadElement" style="text-align: right; width: 60px;">结束</div>
            <div class="div_annoListHeadElement" style="text-align: left; margin-left: 12px;">名称</div>
            <div class="div_annoListHeadElement" style="text-align: left; margin-left: 283px;">实体</div>
        </div>

        <div id="div_annoInfoList">

        </div>

    </form>
    </div>
    <%@include file="../bottom.jsp"%>
</div>
</body>
</html>

<%@ include file="/jsp/msg.jsp" %>
<%@ include file="annoInfoTemplet.jsp" %>
<%@ include file="../common/queryEntityFrameCommon.jsp" %>

<script language="javascript">
<%
    String docJSON = new JSONSerializer().exclude("class").deepSerialize(doc);
%>
    var doc = <%=docJSON%>;
    var content = doc.content;
    var docEntityList = doc.docEntityList;
</script>
<script language="javascript" src="../jsp/admin/doc/docAnno.js"></script>
