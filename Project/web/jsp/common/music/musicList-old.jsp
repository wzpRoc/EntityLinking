<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.song.Song" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page import="org.ailab.tem.wim.album.Album" %>
<%@ page import="org.ailab.tem.wim.artist.Artist" %>
<%@ page import="org.ailab.wimfra.util.StringUtil" %>
<%@ page import="org.ailab.tem.wim.songArtist.SongArtist" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<%
    String queryWhat = (String) request.getAttribute("queryWhat");
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();

    List<Song> songList = (List<Song>) request.getAttribute("songList");
    List<Album> albumList = (List<Album>) request.getAttribute("albumList");
    List<Artist> artistList = (List<Artist>) request.getAttribute("artistList");
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>超毅音乐</title>
    <meta name="description" content="music" />
</head>
<body>


<%@include file="../../header_old.jsp"%>
<%@include file="../music/globalNavHeader.jsp"%>
<%@include file="../music/globalNavItem.jsp"%>
<s:hidden name="queryWhat"/>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="jsp/common/music/music.css" rel="stylesheet" type="text/css"/>
<link href="css/common/header.css" rel="stylesheet" type="text/css"/>
<link href="css/common/footer.css" rel="stylesheet" type="text/css"/>


<div class="main_frame">
    <div class="main_frame_layout">
        <div class="main_container">
            <%--<div class="song_list_frame">--%>

            <%
                if(songList!=null && songList.size()!=0) {
            %>
            <div class="hall_songlist">
                <div class="prescNo">
                    <h3>单曲</h3>
                    <h5><a href="javascript: search('song')">+</a></h5>
                </div>
                <div class="songTable">
                    <table class= "table table-hover">
                        <tbody>
                        <%
                            for(Song song : songList) {
                        %>
                        <tr>
                            <td>
                                <a href="songDetail?name=<%=song.getId()%>">
                                    <%=song.getTitle()%>
                                </a>
                            </td>
                            <td>
                                <%
                                    if(song.getSingerList()!=null && song.getSingerList().size()!=0) {
                                        for(SongArtist songArtist:song.getSingerList()) {
                                %>
                                <a href="artistDetail?name=<%=songArtist.getArtistId()%>"><%=songArtist.getArtistName()%></a> &nbsp;
                                <%
                                    }
                                }else {
                                %>
                                不详
                                <%
                                    }
                                %>
                            </td>
                            <td><%=song.getAlbumName()%></td>
                            <td><%=song.getPublishYear()%></td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </div>
            </div>
            <%
                }
            %>

            <%
                if(albumList!=null && albumList.size()!=0) {
            %>
            <div class="album_list_frame">
                <div class="prescNo">
                    <h3>专辑</h3>
                    <h5><a href="javascript: search('album')">+</a></h5>
                </div>
                <div class="album_list" id="albumList" style="display:'';">
                    <ul>

                        <%
                            for(Album album: albumList) {
                        %>
                        <li>
                            <%--<a class="poster" href="albumDetail?id=<%=album.getId()%>"><img alt="十二新作" title="十二星座" src="../images/wzp.newsML.test/albumCover.jpg"/></a>--%>
                            <a class="poster" href="albumDetail?name=<%=album.getId()%>"><img alt="<%=album.getTitle()%>" title="<%=album.getTitle()%>" src="<%=StringUtil.ifEmpty(album.getPhotoRelUrl(), "img/productPic_Default.jpg")%>" onerror="this.src='img/productPic_Default.jpg'"/></a>
                            <p class="album_name"><a href="albumDetail?name=<%=album.getId()%>"><%=album.getTitle()%></a></p>
                            <p>艺术家:<%=album.getArtistName()%></p>
                            <p>发行时间:<%=album.getPublishDate()%></p>
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

            <%
                if(artistList!=null && artistList.size()!=0) {
            %>
            <div class="artist_list_frame">
                <div class="prescNo">
                    <h3>艺人</h3>
                    <h5><a href="javascript: search('artist')">+</a></h5>
                </div>
                <div class="artist_list" id="artistList" style="display:'';">
                    <ul>
                        <%
                            for(Artist artist: artistList) {
                        %>
                        <li>
                            <%--<a class="poster" href="albumDetail?id=<%=artist.getId()%>"><img alt="周杰伦" title="周杰伦" src="images/wzp.newsML.test/jay.jpg"/></a>--%>
                            <a class="poster" href="artistDetail?name=<%=artist.getId()%>"><img alt="<%=artist.getName()%>" title="<%=artist.getName()%>" src="<%=StringUtil.ifEmpty(artist.getPhotoRelUrl(), "img/productPic_Default.jpg")%>" onerror="this.src='img/productPic_Default.jpg'"/></a>
                            <p class="artist_name"><a href="artistDetail?name=<%=artist.getId()%>"><%=artist.getName()%></a></p>
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
            <%
                if(queryWhat != null && !"".equals(queryWhat)) {
            %>
                <%@include file="../../queryNavigation.jsp"%>
            <%
                }
            %>
        <div class="side_container"></div>
    </div>
</div>

<%@include file="globalNavFooter.jsp"%>

<%@include file="../../footer_old.jsp"%>

</body>
</html>



<script type="text/javascript" src="js/jquery-validate.min.js"></script>
<script type="text/javascript" src="js/bootstrap-switch.min.js"></script>
<script type="text/javascript" src="jsp/common/music/musicList.js"></script>
