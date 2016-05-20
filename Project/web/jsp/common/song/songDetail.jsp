<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.album.Album" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page import="org.ailab.tem.wim.albumComment.AlbumComment" %>
<%@ page import="org.ailab.tem.wim.song.Song" %>
<%@ page import="org.ailab.tem.wim.songArtist.SongArtist" %>
<%@ page import="org.ailab.wimfra.util.StringUtil" %>
<%@ page import="org.ailab.tem.wim.songComment.SongComment" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/detail.css" rel="stylesheet" type="text/css"/>
<link href="css/common/header.css" rel="stylesheet" type="text/css"/>
<link href="jsp/common/album/albumDetail.css" rel="stylesheet" type="text/css"/>

<link href="css/bootstrap/bootstrap-new.css" rel="stylesheet" type="text/css"/>
<link href="css/bootstrap/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="css/globalFrame.css" rel="stylesheet" type="text/css"/>

<%@include file="../../commonJS.jsp"%>


<%
    Song song = (Song) request.getAttribute("dto");
    List<SongComment> songCommentList = (List<SongComment>) request.getAttribute("songCommentList");

    List<User> favorUserList = (List<User>) request.getAttribute("favorUserList");

    //同一专辑的其他曲目
    List<Song> songList = (List<Song>) request.getAttribute("songList");

%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>歌曲详情</title>
    <meta name="description" content="music" />
</head>
<body>


<s:hidden name="dto.id"></s:hidden>
<s:hidden name="dto.albumId"></s:hidden>

<div id="fullContent">
    <%@include file="../../header.jsp" %>
    <%@include file="../../leftBar.jsp" %>
    <div id="mainContent" class="Clearfix">
        <div class ="albumInfo">
            <div class="songPic">
                <img class="song_cover" src="<%=StringUtil.ifEmpty(song.getPhotoUrl(), "img/productPic_Default.jpg")%>" onerror="this.src='img/productPic_Default.jpg'"/>
                <div class="songTitle">
                    <p class = "songLabel">曲目名称:</p>
                    <p class = "songValue"><%=song.getTitle()%></p>
                </div>
            </div>
            <div class="albumParameter">
                <h2><%=song.getTitle()%></h2>
                <p>艺术家:
                    <%
                        if(song.getSingerList()!=null) {
                            int length = song.getSingerList().size();
                            for(int i = 0; i < length; i++) {
                                SongArtist songArtist = song.getSingerList().get(i);
                    %>
                    <a href="artistDetail?name=<%=songArtist.getArtistId()%>"><%=songArtist.getArtistName()%></a>&nbsp;
                    <%
                        }
                    }else {
                    %>
                    不详
                    <%
                        }
                    %>
                </p>
                <p>作曲者：
                    <%
                        if(song.getComposerList()!=null) {
                            int length = song.getComposerList().size();
                            for(int i = 0; i < length; i++) {
                                SongArtist songArtist = song.getComposerList().get(i);
                    %>
                    <a href="artistDetail?name=<%=songArtist.getArtistId()%>"><%=songArtist.getArtistName()%></a>&nbsp;
                    <%
                        }
                    }else {
                    %>
                    不详
                    <%
                        }
                    %>
                </p>
                <p>作词者：
                    <%
                        if(song.getLyricistList()!=null) {
                            int length = song.getLyricistList().size();
                            for(int i = 0; i < length; i++) {
                                SongArtist songArtist = song.getLyricistList().get(i);
                    %>
                    <a href="artistDetail?name=<%=songArtist.getArtistId()%>"><%=songArtist.getArtistName()%></a>&nbsp;
                    <%
                        }
                    }else {
                    %>
                    不详
                    <%
                        }
                    %>
                </p>
                <p>语言：<%=StringUtil.ifEmpty(song.getLanguage(), "不详")%> </p>
                <p>发行时间：<%=StringUtil.ifEmpty(song.getPublishDate(), "不详")%></p>
                <p>唱片公司：<%=StringUtil.ifEmpty(song.getPublisherName(), "不详")%></p>
            </div>

            <div class="albumIntroduction">
                <h2>曲目简介</h2>
                <p><%=song.getNote()%></p>
            </div>
        </div>
        <div class="songList">
            <h3>同一专辑的其他曲目</h3>
            <ul>
                <%
                    if(songList!=null) {
                        int id = 0;
                        String songNo = "";
                        for(Song s: songList) {
                            id++;
                            if(id < 10)
                                songNo = "0"+id;
                            else
                                songNo = String.valueOf(id);
                %>
                <li><a href="songDetail?name=<%=s.getId()%>"><%=songNo%>、<%=s.getTitle()%></a></li>
                <%
                        }
                    }
                %>
            </ul>
        </div>
        <div class="albumComment">
            <div class="subTitle">曲目评论</div>
            <div class="commentContent" id="comment">
                <%
                    if(songCommentList!=null) {
                        for (SongComment songComment: songCommentList) {
                %>
                <div class="item">
                    <a class="avatar"><img src="images/test/head_default.jpg" alt=""></a>
                    <h5><a href="http://t.qq.com/linlili8184" target="_blank"><%=songComment.getNickName()%></a>：</h5>
                    <div class="msg_txt">
                        <%=songComment.getContent()%>
                    </div>
                    <p>发布时间：<%=songComment.getOpTime()%></p>
                </div>

                <%
                        }
                    }
                %>
            </div>
        </div>
        <div class="myComment">
            <div class="subTitle">我的说说</div>
            <div class="addComment">
                <textarea cols="120" rows="5" id="textarea_content"></textarea>
            </div>
            <div class="addButton">
                <button type="button"  class="btn btn-default" title="发表" onclick="loadComment();">发表</button>
            </div>
        </div>
        <div class="otherInfo"></div>
    </div>
    <%@include file="../../footer.jsp" %>

</div>

</body>
</html>

<script language="javascript" src="js/tcal.js"></script>
<script language="javascript" src="js/string.js"></script>
<script language="javascript" src="js/time.js"></script>

<script language="javascript" src="jsp/common/song/song.js"></script>
<script language="javascript">
    var userPhotoUrl = "<%=user == null ? "" : "images/wzp.newsML.test/chen.jpg"%>";
    var userName =  "<%=user == null ? "" : user.getNicName()%>" ;
</script>
