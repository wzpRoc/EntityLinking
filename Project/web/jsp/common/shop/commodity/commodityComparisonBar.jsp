<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    * {
        margin: 0px;
    }

    .item_remove a {
        color: #ffffff;
        text-decoration: none;
    }

    .item_remove a:hover {
        color: #000000;
    }

    #comp_box {
        position: fixed;
        top: 100px;
        right: 0;
        z-index: 100;
        display: none;
        width: 123px;
        text-align: center;
    }

    #comp_box p, #comp_box span {
        cursor: pointer;
        overflow: hidden;

    }

    #comp_top {
        height: 30px;
        padding: 5px;
        background: url("img/front/compareTop.png");
        color: #FFFFFF;
        position: relative;
    }

    #closeBtn {
        position: absolute;
        right: 10px;
    }

    #closeBtn:hover {
        color: #FFE566;
    }

    #comp_items .comp_item {
        display: block;
        height: 160px;
        border-bottom: 1px solid #5F6772;
        background-color: #CECFD1;
        padding: 10px 11.5px;
    }

    #comp_items p {
        width: 100px;
    }

    #comp_items .item_img {
        height: 100px;
    }

    #comp_items img {
        width: 100px;
        height: 100px;
    }

    #comp_items .item_title {
        height: 20px;
    }

    #comp_box .item_remove {
        width: 50px;
        height: 20px;
        background-color: #3E454D;
        margin: 5px auto;
        color: #ffffff;
        border-radius: 10px;
    }

    #comp_box .item_remove:hover {
        background-color: #FFE566;
        color: #000000;
    }

    #comp_footer {
        height: 84px;
        background: url("img/front/compareFooter.png");
        margin-top: -1px;
        padding: 10px;
    }

    #comp_footer #compBtn {
        width: 100px;
        margin: 10px auto;
    }
</style>

<div id="comp_box">
    <div id="comp_top">
        <span>对比栏</span>
        <span id="closeBtn" onclick="closeCompareBar()">X</span>
    </div>
    <ul id="comp_items">
        <%--
                <li class="comp_item">
                    <p class="item_img">
                        <img src="">
                    </p>
                    <p class="item_title">
                        <a>商品的标题</a>
                    </p>
                    <p class="item_remove">
                        <a onclick="removeCommodity(<%=commodity.getId()%>, <%=commodity.getTopCategoryId()%>, this)">清除</a>
                    </p>
                </li>
        --%>
    </ul>
    <div id="comp_footer">
        <p id="compBtn" class="item_remove"><a
                href="commodityCompare?topCategoryId=<%=request.getAttribute("topCategoryId")%>"
                target="_blank">对比</a></p>

        <p class="item_remove" onclick="removeCommodity()"><a>清空</a></p>
    </div>
</div>
<script>
    function closeCompareBar() {
        $("#comp_box").hide();
    }
</script>
