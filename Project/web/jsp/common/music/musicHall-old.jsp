<META http-equiv="X-UA-Compatible" content="IE=9" > </META>
<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.song.Song" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page import="org.ailab.tem.wim.album.Album" %>
<%@ page import="org.ailab.tem.wim.artist.Artist" %>
<%@ page import="org.ailab.tem.wim.songArtist.SongArtist" %>
<%@ page import="org.ailab.tem.wim.genre.Genre" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();

    List<Song> songList = (List<Song>) request.getAttribute("songList");
    List<Album> albumList = (List<Album>) request.getAttribute("albumList");
    List<Artist> artistList = (List<Artist>) request.getAttribute("artistList");
    List<Genre> genreList = (List<Genre>) request.getAttribute("genreList");
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>超毅音乐</title>
    <meta name="description" content="music" />
</head>
<body>


<%@include file="../../header_old.jsp"%>
<%@include file="../music/globalNav.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/common/header.css" rel="stylesheet" type="text/css"/>
<link href="css/common/footer.css" rel="stylesheet" type="text/css"/>
<link href="css/common/globalNav.css" rel="stylesheet" type="text/css"/>
<link href="jsp/common/music/music.css" rel="stylesheet" type="text/css"/>


<div class="main_frame">
    <div class="main_frame_layout">
        <div class="main_container">
            <div class ="carousel slide" id="myCarousel">
                <ol class="carousel-indicators" id="myIndicators">
                    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                    <li data-target="#myCarousel" data-slide-to="1" class=""></li>
                    <li data-target="#myCarousel" data-slide-to="2" class=""></li>
                    <li data-target="#myCarousel" data-slide-to="3" class=""></li>
                </ol>
                <div class="carousel-inner">
                    <div class="item active">
                        <img src="images/test/dariy.jpg" alt="吸血鬼日记"/>
                        <div class="carousel-caption">
                            <b><a href="###1" class="chos">吸血鬼日记第一季</a></b>
                        </div>
                    </div>
                    <div class="item">
                        <img src="images/test/singer.jpg" alt="我是歌手"/>
                        <div class="carousel-caption">
                            <b><a href="###2" class="chos">我是歌手</a></b>
                        </div>
                    </div>
                    <div class="item">
                        <img src="images/test/background.jpg" alt="精选MV"/>
                        <div class="carousel-caption">
                            <b><a href="###4" class="chos">精选集</a></b>
                        </div>
                    </div>
                    <div class="item">
                        <img src="images/test/beipiao.jpg" alt="北漂一族"/>
                        <div class="carousel-caption">
                            <b><a href="###5" class="last">北漂一族</a></b>
                        </div>
                    </div>
                </div>
                <div class="pic_prev"><a class="carousel-control left" href="#myCarousel" data-slide="prev" id="left_arrow"></a></div>
                <div class="pic_next"><a class="carousel-control right" href="#myCarousel" data-slide="next" id="right_arrow"></a></div>
            </div>

            <div class="hall_songlist">
                <div class="prescNo">
                    <h3>最热歌曲</h3>
                </div>
                <div class="songList">
                    <table id="songTable" class= "table table-striped">
                        <thead>
                            <tr>
                                <th class="tableHead">排名</th>
                                <th class="tableHead">歌曲</th>
                                <th class="tableHead">专辑</th>
                                <th class="tableHead">艺人</th>
                            </tr>
                        </thead>
                        <tbody>
                        <%
                            int num = 0;
                            for(int i=0; i < songList.size(); i++) {
                                num = i + 1;
                                Song song = songList.get(i);
                        %>
                        <tr id="song<%=i%>" onmouseover="mouseOn('song<%=i%>')" onmouseout="mouseOut('song<%=i%>')">
                            <td>
                                <%=num%>
                            </td>
                            <td>
                                <a href="songDetail?name=<%=song.getId()%>"><img src ="<%=StringUtil.ifEmpty(song.getPhotoUrl(), "img/productPic_Default.jpg")%>" onerror="this.src='img/productPic_Default.jpg'" style="width: 50px;height: 50px; display:none"/></a>
                                <a href="songDetail?name=<%=song.getId()%>">
                                    <%=song.getTitle()%>
                                </a>
                            </td>
                            <td><%=song.getAlbumName()%></td>
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
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </div>
            </div>

            <%
                if(albumList!=null && albumList.size()!=0) {
            %>
            <div class="hall_albumlist">
                <div class="prescNo">
                    <h3>最热专辑</h3>
                </div>
                <div class="albumContent">
                    <ul class="album_list_content">

                        <li class="album_cover">
                            <a href="albumDetail?name=<%=albumList.get(0).getId()%>" class="album_poster" target="_blank"><img width="270" height="270" alt="<%=albumList.get(0).getTitle()%>" title="<%=albumList.get(0).getTitle()%>" src="<%=StringUtil.ifEmpty(albumList.get(0).getPhotoRelUrl(), "img/productPic_Default.jpg")%>" onerror="this.src='img/productPic_Default.jpg'"></a>
                            <h5 onmouseover="this.className='hover'" onmouseout="this.className=''"><a href="albumDetail?name=<%=albumList.get(0).getId()%>" target="_blank" class="" title="<%=albumList.get(0).getTitle()%>"><%=albumList.get(0).getTitle()%></a></h5>
                            <p class="album_info"><%=albumList.get(0).getTitle()%></p>
                        </li>

                        <%
                            for(int i=1; i<albumList.size();i++) {
                                Album album = albumList.get(i);
                        %>
                        <li>
                            <a href="albumDetail?name=<%=album.getId()%>" target="_blank" class="small_album_poster"><img width="130" height="130" alt="<%=album.getTitle()%>" title="<%=album.getTitle()%>" src="<%=StringUtil.ifEmpty(album.getPhotoRelUrl(), "img/productPic_Default.jpg")%>" onerror="this.src='img/productPic_Default.jpg'"></a>
                            <h5 onmouseover="this.className='hover'" onmouseout="this.className=''"><a href="albumDetail?name=<%=album.getId()%>" target="_blank" class="" title="<%=album.getTitle()%>"><%=album.getTitle()%></a></h5>
                        </li>
                        <%
                            }
                        %>

                        <%--<li>--%>
                            <%--<a href="" target="_blank" class="small_album_poster"><img width="130" height="130" alt="穿上衣服唱歌给你听" title="穿上衣服唱歌给你听" src="images/wzp.newsML.test/albumCover.jpg"></a>--%>
                            <%--<h5 onmouseover="this.className='hover'" onmouseout="this.className=''"><a href="http://y.qq.com/y/static/mv/mv_play.html?cid=tbzz8wsnc7k8ubx&amp;pgv_ref=qqmusic.y.index.Choose.title3" target="_blank" class="" title="穿上衣服唱歌给你听">穿上衣服唱歌给你听</a></h5>--%>
                        <%--</li>--%>

                        <%--<li>--%>
                            <%--<a href="" target="_blank" class="small_album_poster"><img width="130" height="130" alt="妹妹就是我的Style" title="妹妹就是我的Style" src="images/wzp.newsML.test/albumCover.jpg"></a>--%>
                            <%--<h5 onmouseover="this.className='hover'" onmouseout="this.className=''" class=""><a href="http://y.qq.com/y/static/mv/mv_play.html?cid=vld25dyokkkvmt2&amp;pgv_ref=qqmusic.y.index.Choose.title4" target="_blank" class="" title="妹妹就是我的Style">妹妹就是我的Style</a></h5>--%>
                        <%--</li>--%>

                        <%--<li>--%>
                            <%--<a href="" target="_blank" class="small_album_poster"><img width="130" height="130" alt="不如跳舞" title="不如跳舞" src="images/wzp.newsML.test/albumCover.jpg"></a>--%>
                            <%--<h5 onmouseover="this.className='hover'" onmouseout="this.className=''"><a href="http://y.qq.com/y/static/mv/mv_play.html?cid=z4xjqwpzkup4dk6&amp;pgv_ref=qqmusic.y.index.Choose.title5" target="_blank" class="" title="不如跳舞">不如跳舞</a></h5>--%>
                        <%--</li>--%>

                        <%--<li>--%>
                            <%--<a href="" target="_blank" class="small_album_poster"><img width="130" height="130" alt="流动在光影里的灵魂之歌" title="流动在光影里的灵魂之歌" src="images/wzp.newsML.test/albumCover.jpg"></a>--%>
                            <%--<h5 onmouseover="this.className='hover'" onmouseout="this.className=''"><a href="http://y.qq.com/y/static/mv/mv_play.html?cid=46dy4dcvcgxixma&amp;pgv_ref=qqmusic.y.index.Choose.title6" target="_blank" class="" title="流动在光影里的灵魂之歌">流动在光影里的灵魂之歌</a></h5>--%>
                        <%--</li>--%>

                        <%--<li>--%>
                            <%--<a href="" target="_blank" class="small_album_poster"><img width="130" height="130" alt="只因为爱" title="只因为爱" src="images/wzp.newsML.test/albumCover.jpg"></a>--%>
                            <%--<h5 onmouseover="this.className='hover'" onmouseout="this.className=''"><a href="http://y.qq.com/y/static/mv/mv_play.html?cid=tclqxbh46qd8msp&amp;pgv_ref=qqmusic.y.index.Choose.title7" target="_blank" class="" title="只因为爱">只因为爱</a></h5>--%>
                        <%--</li>--%>

                    </ul>
                </div>
            </div>
            <%
                }
            %>
        </div>
        <div class="side_container">
            <div class ="ad" style="width:100%;height: 320px">
                <div>
                    <ul id="myTab" class="nav nav-tabs">
                        <li class="active"><a href="#home" data-toggle="tab">公告</a></li>
                        <li><a href="#home" data-toggle="tab">新闻</a></li>
                        <li><a href="#profile" data-toggle="tab">客服服务</a></li>
                    </ul>
                </div>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="home">
                        <div class="alert alert-success">
                            <a href="#" class="alert-link">超毅公司欢迎您</a>
                        </div>
                        <div class="alert alert-info">
                            <a href="#" class="alert-link">注册会员都有优惠哦</a>
                        </div>
                    </div>
                    <div class="tab-pane fade" id="profile">
                        <div class="alert alert-success">
                            <a href="#" class="alert-link">超毅公司欢迎您</a>
                        </div>
                        <div class="alert alert-info">
                            <a href="#" class="alert-link">注册会员都有优惠哦</a>
                        </div>
                        <div class="alert alert-warning">
                            <a href="#" class="alert-link">联系电话:888-88888</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="side_singers" id="hall_singers" style="display: block;margin: 30px 0 0 0 ">
                <div class="prescNo">
                    <h3>最热歌手</h3>
                </div>
                <div class="singers_content" style="display: block;">
                    <div class="singers_list">
                        <ul>
                            <%
                                for(int i=0;i<artistList.size();i++) {
                                    Artist artist = artistList.get(i);
                            %>
                            <li>
                                <a class="singer_pic" href="artistDetail?name=<%=artist.getId()%>">
                                    <img alt="<%=artist.getName()%>" title="<%=artist.getName()%>" src="<%=StringUtil.ifEmpty(artist.getPhotoRelUrl(), "img/productPic_Default.jpg")%>" onerror="this.src='img/productPic_Default.jpg'" width="50" height="50"/>
                                </a>
                                <a class="singer_name"><%=artist.getName()%></a>
                            </li>
                            <%
                                }
                            %>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="side_labels">
                <div class="prescNo">
                    <h3>最热标签</h3>
                </div>

                <div class="side_labels_part  Clearfix">
                    <div class="small_title">
                        <h4>流派</h4>
                    </div>
                    <ul class="genre_list">
                        <%
                            for(Genre genre: genreList) {
                        %>
                        <li><a href="musicList?condition.genreId=<%=genre.getId()%>"><%=genre.getName()%></a></li>
                        <%
                            }
                        %>

                    </ul>
                </div>



            </div>
        </div>

    </div>


    <%@include file="../../footer_old.jsp"%>

</body>
</html>


<script type="text/javascript">
    function mouseOn(id){
        $("#"+id+" img").show();
    }

    function mouseOut(id){
        $("#"+id+" img").hide();
    }

</script>


<script type="text/javascript" src="js/bootstrap-switch.min.js"></script>
