<%@ page import="org.ailab.tem.wim.music.MusicListCondition" %>
<%@ page import="org.ailab.wimfra.util.StringUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="css/common/globalNav.css" rel="stylesheet" type="text/css"/>
<%
    String keyword = null;
    MusicListCondition musicListCondition = (MusicListCondition) request.getAttribute("condition");
    if(musicListCondition != null) {
        keyword = musicListCondition.getKeyword();
    }
%>
<form name="mainForm" id="mainForm" method="get" action="musicList">
<div id="music-nav" class="headNav">
    <div class="nav-content">
        <div class="nav-main">
            <div class="nav-logo">
                <a href="musicHall"><img src = "images/test/logo.png"/></a>
            </div>
            <div class="nav-search">
                <s:hidden name="clearSession"/>
                <s:hidden name="submitTag"/>
                <s:hidden name="dowhat"/>
                <input type="hidden" name="fuzzyMatch" value="true"/>
                <fieldset>
                    <legend>搜索：</legend>
                    <%--<label for="inp-query">唱片名、表演者、条码、ISRC</label>--%>
                    <div class="inp"><input id="inp-query" name="condition.keyword" size="22" maxlength="60" value="<%=StringUtil.ifEmpty(keyword, "")%>"></div>
                    <div class="inp-btn"><input type="submit"></div>
                </fieldset>
            </div>
        </div>
    </div>
</div>
