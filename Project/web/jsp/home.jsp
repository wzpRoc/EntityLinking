<META http-equiv="X-UA-Compatible" content="IE=9">
</META>
<%@ page import="java.util.List" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page import="org.ailab.tem.wim.homeInfo.HomeInfo" %>
<%@ page import="org.ailab.wimfra.util.StringUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%--<%@include file="../../commonJS.jsp"%>--%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="css/common/header.css" rel="stylesheet" type="text/css"/>
<link href="css/home.css" rel="stylesheet" type="text/css"/>

<link href="css/bootstrap/bootstrap-new.css" rel="stylesheet" type="text/css"/>
<link href="css/bootstrap/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="css/globalFrame.css" rel="stylesheet" type="text/css"/>

<%
    List homeInfoList = (List) request.getAttribute("homeInfoList");
%>

<script language="javascript">
    var picList =[];
    <%
        int i = 0;
        for(Object object: homeInfoList) {
            HomeInfo homeInfo = (HomeInfo) object;
    %>
    picList[<%=i%>] = "<%=homeInfo.getPhotoRelUrl(true)%>";
    <%
            i++;
        }
    %>
</script>

<div id="fullContent">
    <%@include file="header.jsp" %>
    <%@include file="leftBar.jsp" %>

    <div id="mainContent" class="Clearfix">
        <div id="dataArea" class=" Clearfix">
            <div class="display">
                <div class="display-inner">
                    <div id="dataDisplay" class="dataDisplay">
                        <img src="<%=StringUtil.ifEmpty(((HomeInfo)homeInfoList.get(0)).getPhotoRelUrl(false), "img/productPic_Default.jpg")%>" onerror="this.src='img/productPic_Default.jpg'" />
                    </div>
                    <div id="moveleft" class="navigationPic"></div>
                    <div id="moveright" class="navigationPic"></div>
                    <%--<div class="clear"></div>--%>
                </div>
            </div>
            <div>
                <div class="dataPicture choosePicture">
                    <img id="0" src="<%=StringUtil.ifEmpty(((HomeInfo)homeInfoList.get(0)).getPhotoRelUrl(false), "img/productPic_Default.jpg")%>" onerror="this.src='img/productPic_Default.jpg'" />
                </div>
                <div class="dataPicture">
                    <img id="1" src="<%=StringUtil.ifEmpty(((HomeInfo)homeInfoList.get(1)).getPhotoRelUrl(false), "img/productPic_Default.jpg")%>" onerror="this.src='img/productPic_Default.jpg'" />
                </div>
                <div class="dataPicture">
                    <img id="2" src="<%=StringUtil.ifEmpty(((HomeInfo)homeInfoList.get(2)).getPhotoRelUrl(false), "img/productPic_Default.jpg")%>" onerror="this.src='img/productPic_Default.jpg'" />
                </div>
                <div class="dataPicture">
                    <img id="3" src="<%=StringUtil.ifEmpty(((HomeInfo)homeInfoList.get(3)).getPhotoRelUrl(false), "img/productPic_Default.jpg")%>" onerror="this.src='img/productPic_Default.jpg'" />
                </div>
                <div class="dataPicture">
                    <img id="4" src="<%=StringUtil.ifEmpty(((HomeInfo)homeInfoList.get(4)).getPhotoRelUrl(false), "img/productPic_Default.jpg")%>" onerror="this.src='img/productPic_Default.jpg'" />
                </div>
            </div>
        </div>
    </div>


 <%@include file="footer.jsp" %>
</div>
</body>
</html>



<script type="text/javascript" src="js/jquery-validate.min.js"></script>
<script type="text/javascript" src="js/home/home.js"></script>
<script type="text/javascript" src="js/bootstrap-switch.min.js"></script>

