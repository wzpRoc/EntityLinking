<%--
  Created by IntelliJ IDEA.
  User: onechen
  Date: 13-12-10
  Time: 下午2:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<META http-equiv="X-UA-Compatible" content="IE=9" > </META>
<html>

<head>
    <meta charset="utf-8">
    <title>True-E</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="True-E">
    <meta name="author" content="OneChen">

    <!-- BootStrap 2.3.1 -->
    <link href="css/home/bootstrap.min.css" rel="stylesheet">

    <!-- True-E camera -->
    <link href="css/home/True-E.css" rel="stylesheet">

    <!-- 字体样式 -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,400,300,700' rel='stylesheet' type='text/css'>

    <!-- 效果样式 -->
    <link href="css/home/standard.css" rel="stylesheet">

    <link href="css/common/footer.css" rel="stylesheet">
    <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon">
    <link rel="icon" href="img/favicon.ico" type="image/x-icon">
</head>

<body>
<!-- 首页导航栏模块 -->
<div class="container-fluid sf_nav">
    <div class="navbar navbar-static-top">
        <div class="navbar-inner">
            <div class="container">
                <!-- True-E logo -->
                <a class="brand myBrand" href="index">
                    <img src="images/home/logo.jpg">
                </a>
                <!-- 子模块导航链接 -->
                <div class="nav-collapse">
                    <ul class="nav pull-right sf_navmenu navanimation sf-menu hidden-phone" id="sf-menu-responsive">
                        <li id="home" class="Lava selectedLava">
                            <a href="#">首页<span><span class="hidden-desktop"> - </span>home</span></a>
                        </li>

                        <li id="mall" class="Lava">
                            <a href="productList">商城 <span><span class="hidden-desktop"> - </span>mall</span></a>
                        </li>

                        <li id="product" class="Lava open-submenu">
                            <a href="#products">产品 <span><span class="hidden-desktop"> - </span>products</span></a>
<%--
                            <ul class="navsub noLava">
                                <li class="noLava">
                                    <a href="#">Version 1</a>
                                </li>
                                <li class="noLava">
                                    <a href="#">Version 2</a>
                                </li>
                            </ul>
--%>
                        </li>

                        <li id="Music" class="Lava open-submenu">
                            <a href="musicHall">音乐 <span><span class="hidden-desktop"> - </span>music</span></a>
<%--
                            <ul class="navsub noLava">
                                <li class="noLava">
                                    <a href="features.html#sf-typography">Typography</a>
                                </li>
                                <li class="noLava">
                                    <a href="features.html#sf-icons">Icons</a>
                                </li>
                                <li class="noLava">
                                    <a href="features.html#sf-form-validation">Form & Validation</a>
                                </li>
                                <li class="noLava">
                                    <a href="features.html#sf-buttons">Buttons</a>
                                </li>
                                <li class="noLava">
                                    <a href="features.html#sf-alerts">Alerts</a>
                                </li>
                                <li class="noLava">
                                    <a href="features.html#sf-tabs">Tabs</a>
                                </li>
                                <li class="noLava">
                                    <a href="features.html#sf-table">Table</a>
                                </li>
                                <li class="noLava">
                                    <a href="features.html#sf-tooltips">Tooltips</a>
                                </li>
                                <li class="noLava">
                                    <a href="features.html#sf-popover">Popover</a>
                                </li>
                            </ul>
--%>
                        </li>
                        <li id="forum" class="Lava">
                            <a href="#">论坛 <span><span class="hidden-desktop"> - </span>forum</span></a>
                        </li>
                        <li id="contacts" class="Lava">
                            <a href="#contact">联系 <span><span class="hidden-desktop"> - </span>mail us</span></a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- True-E公司照片展示 -->
<div class="container offset-top-xl">
    <div class="camera_wrap camera_white_skin" id="camera_wrap_1">
        <div data-thumb="images/slides/thumbs/leaf.jpg" data-srcFilePath="images/home/slides/speaker.jpg">
            <div class="camera_caption fadeFromBottom">
                It uses a light version of jQuery mobile, <em>navigate the slides by swiping with your fingers</em>
            </div>
        </div>
        <div data-thumb="images/home/slides/thumbs/bridge.jpg" data-srcFilePath="images/home/slides/bridge.jpg">
            <div class="camera_caption fadeFromBottom">
                Camera is a responsive/adaptive slideshow. <em>Try to resize the browser window</em>
            </div>
        </div>
        <div data-thumb="images/slides/thumbs/road.jpg" data-srcFilePath="images/home/slides/road.jpg">
            <div class="camera_caption fadeFromBottom">
                <em>It's completely free</em> (even if a donation is appreciated)
            </div>
        </div>
        <div data-thumb="images/slides/thumbs/sea.jpg" data-srcFilePath="images/home/slides/sea.jpg">
            <div class="camera_caption fadeFromBottom">
                Camera slideshow provides many options <em>to customize your project</em> as more as possible
            </div>
        </div>
        <div data-thumb="images/slides/thumbs/shelter.jpg" data-srcFilePath="images/home/slides/shelter.jpg">
            <div class="camera_caption fadeFromBottom">
                It supports captions, HTML elements and videos and <em>it's validated in HTML5</em> (<a href="http://validator.w3.org/check?uri=http%3A%2F%2Fwww.pixedelic.com%2Fplugins%2Fcamera%2F&amp;charset=%28detect+automatically%29&amp;doctype=Inline&amp;group=0&amp;user-agent=W3C_Validator%2F1.2" target="_blank">have a look</a>)
            </div>
        </div>
        <div data-thumb="images/slides/thumbs/tree.jpg" data-srcFilePath="images/home/slides/tree.jpg">
            <div class="camera_caption fadeFromBottom">
                Different color skins and layouts available, <em>fullscreen ready too</em>
            </div>
        </div>
    </div>
</div>

<!-- True-E内容 -->
<div class="container">
    <!-- True-E介绍 -->
    <div class="row-fluid" align="center" style="margin-bottom: -10;">
        <hr>
        <h4>公司简介</h4>
        <p style="text-align: left;">
            　　超毅数字技术有限公司，主要服务对象是生产音响、功放、多媒体等厂家。我公司拥有一批专业、资深的软硬件设计工程师，有部分工程师已从事了近十年家庭影院设计和加工工作。他们用丰富的工作经验和前卫的创造思维，为许多厂家开发和设计出了一件件优良的高新科技产品。我公司可以根据客户的各种要求，设计出客户满意的产品。有空请您多关注我们的网站，我司研发出的每一件新产品，都会及时放在网站上展示。
        </p>
        <p style="text-align: left;">
            　　我们相信努力不会落空，有实力才能创造事业。多年来，我们一直关注市场的瞬息万变，深入了解电子产品的日新月异，根据市场的变化和需求开发、设计产品，并不断推出新产品。我公司的主要产品AC3/DTS解码板，经过不断的生产和总结经验，功能齐全、质量保证，超毅数字技术是一家开发、设计、高档数字音响产品的电子工程公司。
        </p>
        <p style="text-align: left;">
            　　主要经营：AC3/DTS解码板 多声道智能音频解码器。开发设计：中高档AC3/DTS多声道功放机（可过DOLBY、DTS认证）;组合音响（无损音源数字解码器）+卡座+收音机（RDS）;汽车音响（CD、MP3解码、TUNER、RDS）;点阵VFD显示、点阵LCD显示;其它电子产品开发和设计等。
        </p>
        <hr>
    </div>

    <!-- True-E产品信息 -->
    <div class="row-fluid" align="center" id="products">
        <%--<hr>--%>
        <h4>产品简介</h4>
        <div class="row-fluid offset-both">
            <div class="span3">
                <div class="offset-bottom">
                    <img class="productPic" src="images/home/products/product1.jpg"/>
                </div>
                <p>
                    FA1200专业汽车功放
                </p>
            </div>
            <div class="span3">
                <div class="offset-bottom">
                    <img class="productPic" src="images/home/products/product2.jpg"/>
                </div>
                <p>
                    FA12S 汽车超低音扬声器
                </p>
            </div>
            <div class="span3">
                <div class="offset-bottom">
                    <img class="productPic" src="images/home/products/product3.jpg"/>
                </div>
                <p>
                    音频转换器HTL4.6
                </p>
            </div>
            <div class="span3">
                <div class="offset-bottom">
                    <img class="productPic" src="images/home/products/product4.jpg"/>
                </div>
                <p>
                    S30 汽车环绕扬声器
                </p>
            </div>
        </div>
    </div>

    <div class="row-fluid offset-both">
        <!-- 业内动态 -->
        <div class="span6">
            <h4>业内动态</h4>
            <hr>
            <blockquote>
                <em>2012年深圳国际汽车电子产品展览会</em>
                <small><b>Peter Salt</b> <cite title="Source Title">Sept 16th, 2012 at 4:20 pm</cite></small>
            </blockquote>
            <blockquote>
                <em>广州车展11月21日开幕 车企转向精</em>
                <small><b>Peter Salt</b> <cite title="Source Title">Sept 16th, 2012 at 4:20 pm</cite></small>
            </blockquote>
            <blockquote>
                <em>汽车影音市场“三国鼎立”几时到</em>
                <small><b>Peter Salt</b> <cite title="Source Title">Sept 16th, 2012 at 4:20 pm</cite></small>
            </blockquote>
        </div>
        <!-- 公司新闻 -->
        <div class="span6" id="contact">
            <h4>联系方式</h4>
            <hr>
            <div>
                <div>
                    <blockquote>电话：0755-86026681</blockquote>
                    <blockquote>传真：0755-86026680</blockquote>
                    <blockquote>电邮：wu.yh@true-e.com.cn</blockquote>
                    <blockquote>邮编：518129</blockquote>
                    <blockquote>地址：广东省深圳市龙岗区坂田坂雪岗大道中兴路睿达科技园B栋六楼（万科第五园附近）</blockquote>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- footer -->
<%--<div class="container_fluid sf_footer">
    <div class="container">
        <div class="row-fluid">
            <div class="span4">
                <h4>Recent Posts</h4>
                <a href="#">Macbook Pro for $1</a>
                <hr>
                <a href="#">Awesome iPad givaway</a>
                <hr>
                <a href="#">Sneak preview of iOS 7</a>
                <hr>
                <a href="#">Awesome portfolio web design</a>
            </div>
            <div class="span4">
                <h4>Recent Tweets</h4>

                <ul class="unstyled" id="twitter-ticker">

                    <div id="tweet-container">
                        Loading..
                    </div>

                </ul>

            </div>
            <div class="span4">
                <h4>Newsletter</h4>
                <p>
                    Subscribe to our newsletter to receive emails about new blog posts.
                </p>
                <form id="newsletter" action='' method="POST">
                    <div class="row no-margin">
                        <div class="span12">
                            <input type="text" id="name" name="name" placeholder="Name" class="span12 required">
                        </div>
                    </div>
                    <div class="row no-margin">
                        <div class="span12">
                            <input type="text" id="email" name="email" placeholder="email" class="span12 required email">
                        </div>
                    </div>

                    <button class="btn">
                        Subscribe
                    </button>

                </form>
            </div>
        </div>
    </div>
</div>--%>
<!-- end footer -->

<!-- scripts -->
<script src="js/home/jquery-1.8.0.min.js"></script>
<script src="js/home/jquery-ui-1.9.0.custom.min.js"></script>
<script src="js/home/bootstrap.min.js"></script>
<script src="js/home/selectnav.min.js"></script>
<script src="js/home/jquery-easing.js"></script>
<script src="js/home/jquery.lavalamp.min.js"></script>
<script src="js/home/jquery.mobile.customized.min.js"></script>
<script src="js/home/camera.min.js"></script>
<script src="js/home/hoverIntent.js"></script>
<script src="js/home/jquery.isotope.min.js"></script>
<script src="js/home/jquery.animate-shadow-min.js"></script>
<script src="js/home/bootstrap-datepicker.js"></script>
<script src="js/home/jquery.validate.min.js"></script>
<script src="js/home/additional-methods.min.js"></script>
<script src="js/home/jquery.colorbox-min.js"></script>
<script src="js/home/jquery.bxslider.min.js"></script>
<script src="js/home/modernizr.custom.97074.js"></script>
<script src="js/home/jquery.hoverdir.js"></script>
<script src="js/home/standard.js"></script>

<%--<script type="text/javascript">--%>
    <%--// The twitter accounts that will be included in the ticker--%>
    <%--var tweetUsers = ['speckyboy'];--%>
<%--</script>--%>

<%--<script src="js/home/jquery-twitter.js"></script>--%>

<%--<!-- end scripts -->--%>


<div class="footer" style="text-align: center; border-top: 1px solid #ccc; margin-top: 30px; padding-top: 30px;  background-color: rgb(25, 24, 24)">
    <div class="container">
        <p>True-E, Inc.</p>

        <p>MIT Licence</p>

        <p>Designed by YaTan | Powered by <a name="foot" href="http://twitter.github.io/bootstrap">Bootstrap</a>
        </p>
    </div>
</div>
</body>

</html>