<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.album.Album" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page import="org.ailab.tem.wim.albumComment.AlbumComment" %>
<%@ page import="org.ailab.tem.wim.song.Song" %>
<%@ page import="org.ailab.tem.wim.songArtist.SongArtist" %>
<%@ page import="org.ailab.wimfra.util.StringUtil" %>
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
    Album album = (Album) request.getAttribute("dto");
    List<Song> songList = (List<Song>) request.getAttribute("songList");
    List<AlbumComment> albumCommentList = (List<AlbumComment>) request.getAttribute("albumCommentList");
    List<User> favorUserList = (List<User>) request.getAttribute("favorUserList");
    List<Album> otherAlbumList = (List<Album>) request.getAttribute("otherAlbumList");
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>专辑详情</title>
    <meta name="description" content="music" />
</head>
<body>

<div id="fullContent">
    <%@include file="../../header.jsp" %>
    <%@include file="../../leftBar.jsp" %>
        <div id="mainContent" class="Clearfix">
            <div class ="albumInfo">
                <div class="albumPic">
                    <img class="album_cover" src="<%=StringUtil.ifEmpty(album.getPhotoRelUrl(), "img/productPic_Default.jpg")%>" onerror="this.src='img/productPic_Default.jpg'"/>
                </div>
                <div class="albumParameter">
                    <h2><%=album.getTitle()%></h2>
                    <p>艺术家:
                        <%
                            if(album.getArtistName()!=null && album.getArtistId()!=0) {
                        %>
                        <a href="artistDetail?name=<%=album.getArtistId()%>"><%=album.getArtistName()%></a>
                        <%
                            }
                        %>
                    </p>
                    <p>序列号：<%=StringUtil.ifEmpty(album.getCdNo(), "不详")%> </p>
                    <p>语言：<%=StringUtil.ifEmpty(album.getLanguage(), "不详")%> </p>
                    <p>发行时间：<%=StringUtil.ifEmpty(album.getPublishDate(), "不详")%></p>
                    <p>唱片公司：<%=StringUtil.ifEmpty(album.getPublisherName(), "不详")%></p>
                </div>
                <div class="albumIntroduction">
                    <h2>专辑简介</h2>
                    <p><%=album.getDescription()%></p>
                </div>
            </div>
            <div class="songList">
                <h2>专辑曲目列表</h2>
                <ul>
                <%
                    if(songList!=null) {
                        int id = 0;
                        String songNo = "";
                        for(Song song: songList) {
                            id++;
                            if(id < 10)
                                songNo = "0"+id;
                            else
                                songNo = String.valueOf(id);
                %>
                    <li><%=songNo%>、<a href="songDetail?name=<%=song.getId()%>"><%=song.getTitle()%></a></li>
                <%
                        }
                    }
                %>
                </ul>
            </div>
            <div class="albumComment">
                <div class="subTitle">专辑评论</div>
                <div class="commentContent" id="comment">
                    <%
                        if(albumCommentList!=null) {
                            for (AlbumComment albumComment: albumCommentList) {
                    %>
                    <div class="item">
                        <a class="avatar"><img src="images/test/head_default.jpg" alt=""></a>
                        <h5><a href="http://t.qq.com/linlili8184" target="_blank"><%=albumComment.getNickName()%></a>：</h5>
                        <div class="msg_txt">
                            <%=albumComment.getContent()%>
                        </div>
                        <p>发布时间：<%=albumComment.getOpTime()%></p>
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
    <s:hidden name="dto.id"/>
</div>
</body>
</html>

<script language="javascript" src="js/tcal.js"></script>
<script language="javascript" src="js/string.js"></script>
<script language="javascript" src="js/time.js"></script>
<script language="javascript" src="jsp/common/album/album.js"></script>

<script language="javascript">
    var userPhotoUrl = "<%=user == null ? "" : "images/wzp.newsML.test/chen.jpg"%>";
    var userName =  "<%=user == null ? "" : user.getNicName()%>" ;
</script>


<%@ include file="/jsp/msg.jsp" %>
