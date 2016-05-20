<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.song.Song" %>
<%@ page import="org.ailab.tem.wim.songArtist.SongArtist" %>
<%@ page import="org.ailab.tem.wim.songComment.SongComment" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../commonJS.jsp"%>


<%
    Song song = (Song) request.getAttribute("dto");
    List<SongComment> songCommentList = (List<SongComment>) request.getAttribute("songCommentList");
    List<User> favorUserList = (List<User>) request.getAttribute("favorUserList");
    List<Song> songList = (List<Song>) request.getAttribute("songList");
%>

<html>
<head>
    <title><%=song.getTitle()%>(True-e)</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
<link href="jsp/common/music/music.css" rel="stylesheet" type="text/css"/>

<s:hidden name="dto.id"></s:hidden>
<s:hidden name="dto.albumId"></s:hidden>

<div class="main_frame">
    <div class="main_frame_layout">
        <div class="main_container">
            <!-- 专辑信息 -->
            <div class="album_info">
                <img class="album_cover" src="<%=StringUtil.ifEmpty(song.getPhotoUrl(), "img/productPic_Default.jpg")%>" onerror="this.src='img/productPic_Default.jpg'">
                <div class="album_text">
                    <h2><%=song.getTitle()%></h2>
                    <p>演唱者：
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
                        <%--<a href="http://y.qq.com/y/static/singer/r/9/001D1TkI14agr9.html" title="周展宏">周杰伦</a><a href="javascript:;" name="script_btn" class="icon_mblog" title="腾讯微博"></a>--%>
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
                    <div class="album_assess" id="pUserGrade" style="display: block;">
                        <strong class="score"><%=song.getRate()%></strong><p><%=song.getFavorCount()%></p>
                        <div class="favor">
                            <a onclick="favorSong()"><img src="images/heart-48.png"/></a>
                        </div>
                        <%--<div class="rate">--%>
                            <%--<p>--%>
                                <%--<i class="icon_good"></i>--%>
                                <%--<span class="bar"><span style="width:75%;"></span></span>--%>
                                <%--<span class="rate_count">75%</span>--%>
                            <%--</p>--%>
                            <%--<p>--%>
                                <%--<i class="icon_bad"></i><span class="bar"><span style="width:25%;"></span></span>--%>
                                <%--<span class="rate_count">25%</span>--%>
                            <%--</p>--%>
                        <%--</div>--%>
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


                        <%--<div class="op_content">--%>
                            <%--<a href="javascript:;" class="btn_fav" onclick="g_albumChn.collectAlbum();"><i class="icon_fav"></i><span>收藏</span></a>--%>
                            <%--<a href="javascript:;" class="btn_share" onclick="g_albumChn.shareAlbum();"><i class="icon_share"></i><span>分享</span></a>--%>
                            <%--<div id="pOpt" style="display: block;"><a href="javascript:;" onclick="g_scoreMod.vipStarClick(10);hideElement('score_pop');" class="btn_good"><i class="icon_good"></i><span>顶</span></a> <a href="javascript:;" onclick="g_scoreMod.vipStarClick(1);hideElement('score_pop');" class="btn_bad"><i class="icon_bad"></i><span>踩</span></a></div>--%>
                            <%--<a href="javascript:;" class="my_assess" id="pMyGrade" style="display: none;">--%>
                            <%--</a>--%>

                        <%--</div>--%>
                    </div>
                </div>
            </div>

            <div class="album_detail">
                <h3>歌曲简介</h3>
                <p><%=song.getNote()%></p>
            </div>


            <!-- 评论列表 -->
            <div class="commentContent">
                <h3>歌曲评论</h3>
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
                    if(songCommentList!=null) {
                %>
                <div class="comment_list" id="comment" style="display: block;">
                    <%
                        for(SongComment songComment:songCommentList) {
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

            <div class="other_songs">
                <div class="title">
                    <h3>热门歌曲</h3>
                </div>
                <table class="table table-hover">
                    <%
                        for(Song otherSong: songList) {
                    %>
                    <tr>
                        <td>
                            <a href="songDetail?name=<%=otherSong.getId()%>"><%=otherSong.getTitle()%></a>
                        </td>
                        <td><%=otherSong.getFavorCount()%></td>
                    </tr>
                    <%
                        }
                    %>
                </table>
            </div>

            <!--ta的其他专辑_S-->
            <%--<div class="other_albums">--%>
                <%--<div class="title">--%>
                    <%--<h3>Ta的其他专辑</h3>--%>
                <%--</div>--%>
                <%--<div class="other_albums_content">--%>
                    <%--<ul>--%>
                        <%--<li>--%>
                            <%--<a class="album_pic" href=""><img alt="" title="" width="100" height="100" src="images/wzp.newsML.test/albumCover.jpg"></a>--%>
                            <%--<h5><a class="album_name" href="/y/static/album/R/E/000G6nLh24C0RE.html" title="周展宏同名专辑">十二星座</a></h5>--%>
                            <%--<p>2011-08-02</p>--%>
                        <%--</li>--%>

                        <%--<li>--%>
                            <%--<a class="album_pic" href=""><img alt="" title="" width="100" height="100" src="images/wzp.newsML.test/albumCover.jpg"></a>--%>
                            <%--<h5><a class="album_name" href="/y/static/album/R/q/000esmPf4DwkRq.html" title="音恒情弦">十二星座</a></h5>--%>
                            <%--<p>2009-04-20</p>--%>
                        <%--</li>--%>

                    <%--</ul>--%>
                <%--</div>--%>
            </div>
        </div>
    </div>
</div>

<%--<div class="span9" style="width:950px;margin:0 auto;">--%>
    <%--<div id="content">--%>
        <%--<h4>超毅搜索：</h4>--%>
        <%--<div class="grid-16-8 clearfix">--%>
            <%--<div class="article">--%>
                <%--<div class="intent">--%>
                    <%--<div class="songInfo">--%>
                        <%--<div class="subject clearfix">--%>
                            <%--<div id="pic">--%>
                                <%--<a>--%>
                                    <%--<img src="../jsp/common/song/song.jpg" alt="song" class="img-thumbnail"/>--%>
                                <%--</a>--%>
                            <%--</div>--%>
                            <%--<div id="info">--%>
                                <%--<table>--%>
                                    <%--<tbody>--%>
                                    <%--<tr>--%>
                                        <%--<td class="item" width=64>曲名:</td>--%>
                                        <%--<td><%=song.getTitle()%></td>--%>
                                    <%--</tr>--%>
                                    <%--<tr>--%>
                                        <%--<td class="item">艺人:</td>--%>
                                        <%--<td><%=song.getAlias()%></td>--%>
                                    <%--</tr>--%>
                                    <%--<tr>--%>
                                        <%--<td class="item">艺人:</td>--%>
                                        <%--<td><%=song.getAlias()%></td>--%>
                                    <%--</tr>--%>
                                    <%--</tbody>--%>
                                <%--</table>--%>
                            <%--</div>--%>

                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>

                <%--<div id="postComment" class="ratings inline_block" style="cursor: pointer;">--%>
                    <%--<div class="ratings_star ratings_star_off" onclick="javascript:doRating('postComment',this);" title="1"></div>--%>
                    <%--<div class="ratings_star ratings_star_off" onclick="javascript:doRating('postComment',this);" title="2"></div>--%>
                    <%--<div class="ratings_star ratings_star_off" onclick="javascript:doRating('postComment',this);" title="3"></div>--%>
                    <%--<div class="ratings_star ratings_star_off" onclick="javascript:doRating('postComment',this);" title="4"></div>--%>
                    <%--<div class="ratings_star ratings_star_off" onclick="javascript:doRating('postComment',this);" title="5"></div>--%>
                    <%--<input id="rating" type="hidden" value="1" name="comment.rating"/>--%>
                <%--</div>--%>

                <%--<textarea id="commentContent" cols="65" rows="10" name="comment.content"></textarea>--%>
                <%--<input type="button" value="submit" onclick="loadComment()"/>--%>
            <%--</div>--%>
            <%--<div class ="aside">--%>
                <%--<div class ="ad">guanggao</div>--%>

            <%--</div>--%>
        <%--</div>--%>

    <%--</div>--%>



<%--</div>--%>


<%@include file="../../footer_old.jsp"%>

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
