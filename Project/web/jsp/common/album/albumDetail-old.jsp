<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.album.Album" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page import="org.ailab.tem.wim.albumComment.AlbumComment" %>
<%@ page import="org.ailab.tem.wim.song.Song" %>
<%@ page import="org.ailab.tem.wim.songArtist.SongArtist" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

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
    <%--<link href="../jsp/common/music/musicHome.css" rel="stylesheet" type="text/css">--%>
    <%--<link href="../jsp/common/music/global.css" rel="stylesheet" type="text/css">--%>
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
            <!-- 专辑信息 -->
            <div class="album_info">
                <div class="album_info">
                    <img class="album_cover" src="<%=StringUtil.ifEmpty(album.getPhotoRelUrl(), "img/productPic_Default.jpg")%>" onerror="this.src='img/productPic_Default.jpg'"/>
                    <div class="album_text">
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
                        <div class="album_assess" id="pUserGrade" style="display: block;">
                            <strong class="score">7.7</strong><p>1399评价</p>
                            <div class="favor">
                                <a onclick="favorAlbum()"><img src="images/heart-48.png"/></a>
                            </div>
                        </div>
                        <div class="mod_album_op" id="album_op" style="display: block;">
                            <div id="postComment" class="ratings inline_block" style="cursor: pointer;">
                                <div id="rating_title">
                                    <p>我来评分</p>
                                </div>
                                <div class="ratings_star ratings_star_off" onclick="javascript:doRating('postComment',this);" title="1"></div>
                                <div class="ratings_star ratings_star_off" onclick="javascript:doRating('postComment',this);" title="2"></div>
                                <div class="ratings_star ratings_star_off" onclick="javascript:doRating('postComment',this);" title="3"></div>
                                <div class="ratings_star ratings_star_off" onclick="javascript:doRating('postComment',this);" title="4"></div>
                                <div class="ratings_star ratings_star_off" onclick="javascript:doRating('postComment',this);" title="5"></div>
                                <input id="rating" type="hidden" value="1" name="comment.rating"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="album_detail">
                <h3>专辑简介</h3>
                <p><%=album.getDescription()%></p>
            </div>

             <!-- 专辑歌曲列表 -->
            <div class="album_song_list">
                <div class="song_list">
                    <h3>曲目列表</h3>
                </div>
                <div class="music_list class_songlist">
                    <table class= "table table-hover">
                        <tbody>
                        <%
                            for(Song song: songList) {
                        %>
                        <tr>
                            <td><a href="songDetail?name=<%=song.getId()%>"><%=song.getTitle()%></a></td>
                            <td>
                                <%
                                    if(song.getSingerList()!=null && song.getSingerList().size()!=0){
                                        for(SongArtist songArtist:song.getSingerList()) {
                                %>
                                <a href="artistDetail?name=<%=songArtist.getArtistId()%>"><%=songArtist.getArtistName()%></a>&nbsp;
                                <%
                                        }
                                    }
                                %>
                            </td>
                            <td><%=song.getPublisherName()%></td>
                            <td><%=song.getPublishDate()%></td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- 评论列表 -->
            <div class="commentContent">
                <h3>专辑评论</h3>
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
                    if(albumCommentList!=null) {
                %>
                <div class="comment_list" id="comment" style="display: block;">
                    <%
                        for(AlbumComment albumComment:albumCommentList) {
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
                    %>
                </div>
                <%
                    }
                %>
            </div>
        </div>

        <div class="side_container">
            <!--喜欢这张专辑的人-->
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

            <!--ta的其他专辑_S-->
            <div class="other_albums">
                <div class="title">
                    <h3>Ta的其他专辑</h3>
                </div>
                <%
                    if(otherAlbumList!=null && otherAlbumList.size()!=0) {
                %>
                <div class="other_albums_content">
                    <ul>
                        <%
                            for(Album otherAlbum: otherAlbumList) {
                        %>
                        <li>
                            <a class="album_pic" href="albumDetail?name=<%=otherAlbum.getId()%>"><img alt="" title="" width="100" height="100" src="<%=StringUtil.ifEmpty(otherAlbum.getPhotoRelUrl(), "img/productPic_Default.jpg")%>" onerror="this.src='img/productPic_Default.jpg'"></a>
                            <h5><a class="album_name" href="albumDetail?name=<%=otherAlbum.getId()%>" title="<%=otherAlbum.getTitle()%>"><%=otherAlbum.getTitle()%></a></h5>
                            <p><%=StringUtil.ifEmpty(otherAlbum.getPublishDate(),"发行日期不详")%></p>
                        </li>

                        <%
                            }
                        %>



                    </ul>
                </div>
                <%
                    }else {
                %>
                <div class="mod_warning small_icon">
                    <p class="warning_info" style="font-size: 12px;line-height: 27px;padding: 0 0 0 40px;">TA没有其他专辑</p>
                </div>
                <%
                    }
                %>
            </div>
            <!--ta的其他专辑_E-->
        </div>
    </div>
</div>


<%@include file="../../footer_old.jsp"%>
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
