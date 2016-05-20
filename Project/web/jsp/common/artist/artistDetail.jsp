<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.album.Album" %>
<%@ page import="org.ailab.tem.wim.artist.Artist" %>
<%@ page import="org.ailab.tem.wim.song.Song" %>
<%@ page import="org.ailab.tem.wim.songArtist.SongArtist" %>
<%@ page import="org.ailab.tem.wim.artistComment.ArtistComment" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<%
    Artist artist = (Artist) request.getAttribute("dto");
    List<Song> songList = (List<Song>) request.getAttribute("songList");
    List<ArtistComment> artistCommentList = (List<ArtistComment>) request.getAttribute("artistCommentList");
    List<Album> albumList = (List<Album>) request.getAttribute("albumList");
    List<User> favorUserList = (List<User>) request.getAttribute("favorUserList");
    List<Artist> otherArtistList = (List<Artist>) request.getAttribute("otherArtistList");
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>艺人详情</title>
    <meta name="description" content="music" />
</head>
<body>

<%@include file="../../header_old.jsp"%>
<%@include file="../music/globalNav.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/detail.css" rel="stylesheet" type="text/css"/>
<link href="css/common/header.css" rel="stylesheet" type="text/css"/>
<link href="css/common/footer.css" rel="stylesheet" type="text/css"/>
<link href="css/common/globalNav.css" rel="stylesheet" type="text/css"/>
<link href="jsp/common/music/music.css" rel="stylesheet" type="text/css"/>

<s:hidden name="dto.id"/>


<div class="main_frame">
    <div class="main_frame_layout">
        <div class="main_container">
            <!-- 艺人信息 -->
            <div class="album_info">
                <div class="album_info">
                    <img class="album_cover" src="<%=StringUtil.ifEmpty(artist.getPhotoRelUrl(), "img/productPic_Default.jpg")%>" onerror="this.src='img/productPic_Default.jpg'"/>
                    <div class="album_text">
                        <h2><%=artist.getName()%></h2>
                        <p>别名：<%=StringUtil.ifEmpty(artist.getAlias(), "无")%></p>
                        <p>生日：<%=StringUtil.ifEmpty(artist.getBirthday(), "不详")%></p>
                        <p>地区：<%=StringUtil.ifEmpty(artist.getCountryName(), "不详")%></p>
                        <p>个人网站：<%=StringUtil.ifEmpty(artist.getPersonalWebsite(), "不详")%> </p>
                        <div class="album_assess" id="pUserGrade" style="display: block;">
                            <strong class="score"><%=artist.getRate()%></strong><p><%=artist.getCommentCount()%>评价</p>
                            <div class="favor">
                                <a onclick="favorArtist()"><img src="images/heart-48.png"/></a>
                            </div>
                        </div>
                        <%--<div class="mod_album_op" id="album_op" style="display: block;">--%>
                            <%--<div id="postComment" class="ratings inline_block" style="cursor: pointer;">--%>
                                <%--<div id="rating_title">--%>
                                    <%--<p>我来评分</p>--%>
                                <%--</div>--%>
                                <%--<div class="ratings_star ratings_star_off" onclick="javascript:doRating('postComment',this);" title="1"></div>--%>
                                <%--<div class="ratings_star ratings_star_off" onclick="javascript:doRating('postComment',this);" title="2"></div>--%>
                                <%--<div class="ratings_star ratings_star_off" onclick="javascript:doRating('postComment',this);" title="3"></div>--%>
                                <%--<div class="ratings_star ratings_star_off" onclick="javascript:doRating('postComment',this);" title="4"></div>--%>
                                <%--<div class="ratings_star ratings_star_off" onclick="javascript:doRating('postComment',this);" title="5"></div>--%>
                                <%--<input id="rating" type="hidden" value="1" name="comment.rating"/>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    </div>
                </div>
            </div>

            <div class="album_detail">
                <h3>艺人简介</h3>
                <p><%=artist.getDescription()%></p>
            </div>

            <!-- 艺人歌曲列表 -->
            <div class="album_song_list">
                <div class="song_list">
                    <h3>TA的歌曲</h3>
                </div>
                <div class="music_list class_songlist">
                    <table class= "table table-hover">
                        <tbody>
                        <%
                            for(Song song: songList) {
                        %>
                        <tr>
                            <td style="width: 30%"><a href="songDetail?name=<%=song.getId()%>"><%=song.getTitle()%></a></td>
                            <%--<td>--%>
                                <%--<%--%>
                                    <%--if(song.getSingerList().size()!=0 && song.getSingerList()!=null){--%>
                                        <%--for(SongArtist songArtist:song.getSingerList()) {--%>
                                <%--%>--%>
                                <%--<%=songArtist.getArtistName()%>&nbsp;--%>
                                <%--<%--%>
                                        <%--}--%>
                                    <%--}--%>
                                <%--%>--%>
                            <%--</td>--%>
                            <td><a href="albumDetail?name=<%=song.getAlbumId()%>"><%=song.getAlbumName()%></a></td>

                            <td style="width: 30%"><%=song.getPublishDate()%></td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- 专辑列表 -->
            <div class="albums">
                <div class="albums_title">
                    <h3>TA的专辑</h3>
                </div>
                <div class="album_list" id="albumList" style="display:'';">
                    <ul>

                        <%
                            for(Album album: albumList) {
                        %>
                        <li>
                            <a class="poster" href="albumDetail?name=<%=album.getId()%>"><img alt="<%=album.getTitle()%>" title="<%=album.getTitle()%>" src="<%=StringUtil.ifEmpty(album.getPhotoRelUrl(), "img/productPic_Default.jpg")%>" onerror="this.src='img/productPic_Default.jpg'" /></a>
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

            <!-- 评论列表 -->
            <div class="commentContent">
                <h3>艺人评论</h3>
                <%--<div class="more" style="display: block;" id="div_moremblog">--%>
                <%--<a href="javascript:;" onclick="g_mblogCmtMod.goMblogTopic('华丽的沉默');">去腾讯微博查看更多</a>--%>
                <%--</div>--%>
                <%--<div class="number">--%>
                <%--有<strong><span id="span_mblog_totalnum">6</span></strong>条微博在讨论--%>
                <%--<a href="javascript:;" onclick="g_mblogCmtMod.goMblogTopic('华丽的沉默');">#<span id="span_mblog_topic">华丽的沉默</span>#</a>--%>
                <%--</div>--%>
                <div class="add_comment">
                    <h4>我来说两句:<span id="span_mblog_addcmtnum">(0/120)</span></h4>
                    <textarea cols="120" rows="5" id="textarea_content" onkeyup="g_mblogCmtMod.getTxtNum();"></textarea>
                    <button type="button" title="发表" onclick="loadComment();" onmouseover="this.className='hover'" onmouseout="this.className=''">发表</button>
                </div>
                <%
                    if(artistCommentList!=null) {
                %>
                <div class="comment_list" id="comment" style="display: block;">
                    <%
                        for(ArtistComment artistComment:artistCommentList) {
                    %>
                    <div class="item">
                        <a class="avatar"><img src="images/test/head_default.jpg" alt=""></a>
                        <h5><a href="http://t.qq.com/linlili8184" target="_blank"><%=artistComment.getNicName()%></a>：</h5>
                        <div class="msg_txt">
                            <%=artistComment.getContent()%>
                        </div>
                        <p>发布时间：<%=artistComment.getOpTime()%></p>
                    </div>
                    <%
                        }
                    %>
                </div>
                <%
                    }
                %>
            </div>

        </div>

        <div class="side_container">
            <div class="favor_fans">
                <div class="prescNo">
                    <h3>TA的粉丝</h3>
                </div>
                <%
                    if(favorUserList!=null && favorUserList.size()!=0) {
                %>
                <div class="fans_content" id="fans">
                    <div class="fans_list">
                        <ul id="divfans">

                            <%
                                for(User favorUser: favorUserList){
                            %>
                            <li>
                                <a class="fans_pic" href="">
                                    <img src="images/test/head_default.jpg"/>
                                </a>
                                <p style="width: 100%"><%=favorUser.getNicName()%></p>
                            </li>

                            <%
                                }
                            %>
                        </ul>
                    </div>
                </div>
                <%
                    }  else {
                %>
                <div class="mod_warning small_icon" id="divnofans">
                    <p class="warning_info" style="font-size: 12px;line-height: 27px;padding: 0 0 0 40px;">没有粉丝收藏过他。</p>
                </div>
                <%
                    }
                %>
            </div>

            <div class="side_singers" style="display: block;">
                <div class="prescNo">
                    <h3>热门歌手</h3>
                </div>
                <div class="singers_content" style="display: block;">
                     <div class="singers_list">
                        <ul>
                            <%
                                for(Artist singer: otherArtistList) {
                             %>
                            <li>
                                <a class="singer_pic" href="">
                                    <img alt="<%=singer.getName()%>" title="<%=singer.getName()%>" src="<%=StringUtil.ifEmpty(singer.getPhotoUrl(), "img/productPic_Default.jpg")%>" onerror="this.src='img/productPic_Default.jpg'" width="50" height="50"/>
                                </a>
                                <a class="singer_name"><%=singer.getName()%></a>
                            </li>
                            <%
                                 }
                            %>
                        </ul>
                     </div>
                </div>
            </div>
        </div>

</div>


<%@include file="../../footer_old.jsp"%>

</body>
</html>




<script language="javascript" src="js/tcal.js"></script>
<script language="javascript" src="js/string.js"></script>
<script language="javascript" src="js/time.js"></script>


<script language="javascript" src="jsp/common/artist/artist.js"></script>

<script language="javascript">
    var userPhotoUrl = "<%=user == null ? "" : "images/wzp.newsML.test/chen.jpg"%>";
    var userName =  "<%=user == null ? "" : user.getNicName()%>" ;
</script>


<%@ include file="/jsp/msg.jsp" %>
