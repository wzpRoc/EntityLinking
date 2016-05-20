/**
 * 作者：张强
 * 功能：商城的动画js
 * 时间：2013/12/20
 */

/**
 * 给div将hover效果
 */
function addDivHover() {
    $(".product").hover(
        function () {
            $(this).css("border-color", "#ECECEC");
        },
        function () {
            $(this).css("border-color", "#FFFFFF");
        }
    )
}


/**
 * 购买数量减少
 */
function decreaseQuantity() {
    var $quantity = $("#quantity");
    var value = parseInt($quantity.val());
    if (value > 1) {
        $quantity.val(value - 1);
    }
}

/**
 * 购买数量增加
 */
function increaseQuantity() {
    var $quantity = $("#quantity");
    var value = parseInt($quantity.val());
    var inventory = $("#inventory").val();
    if (value < inventory) {
        $quantity.val(parseInt(value) + 1);
        $("#quantityWarning").hide();
    }else{
        $("#quantityWarning").show();
    }
}

/**
 * 购物车动画
 */
function toggleShoppingProducts() {
    $("#cartProducts").slideToggle();
}


/**
 * 验证用户是否已经登录
 * @return {boolean}
 */
function isUserLogged() {
    if (userId == null || userId == 0) {
        showLoginFrame();
        return false;
    } else {
        return true;
    }
}

/**
 * 加入购物车的动画
 */
function addOrderProduct() {
    // 验证用户是否已经登录
    if (!isUserLogged()) {
        return false;
    }

    var productId = $("#productId").val();
    var quantity = $("#quantity").val();
    AjaxInterface.addOrderProduct(productId, quantity, userId, function (blMessage) {
        if (blMessage.success) {
            //刷新购物车信息
            initShoppingCart();
            $("#cartProducts").slideDown();
        } else {
            alert("抱歉，商品添加失败");
        }
    });
    return false;
}


function buyProduct() {
    //验证用户是否已经登录
    if (!isUserLogged()) {
        return false;
    }

    var productId = $("#productId").val();
    var quantity = $("#quantity").val();
    //1 生成orderProduct 生成order
    AjaxInterface.buyProduct(userId, productId, quantity, function (blMessage) {
        if (blMessage.success) {
            var order = blMessage.data;
            //2 跳转到order的详细页面
            window.location.href = "orderDetail?id=" + order.id;
        } else {
            alert(blMessage.message);
        }
    });
}

/**
 * 根据id删除购物车中对应的商品
 * @param obj
 * @param id
 */
function removeOrderProduct(obj, id) {
    AjaxInterface.removeOrderProduct(id, function (blMessage) {
        if (blMessage.success) {
            //刷新购物车信息
            initShoppingCart();
            $("#cartProducts").slideDown();
        } else {
            alert("抱歉，商品删除失败" + blMessage.message);
        }

    });
    return false;
}

/**
 * 初始化购物车的内容
 */
function initShoppingCart() {
    if (userId != null && "" != userId) {
        AjaxInterface.getOrderProductList(userId, function (blMessage) {
            if (blMessage.success) {
                var orderProductList = blMessage.data;
                $("#productSum").text(orderProductList.length);
                var $allCartProducts = $("#allCartProducts");
                $("#allCartProducts").empty();
                for (i = 0; i < orderProductList.length; i++) {
                    var orderProduct = orderProductList[i];
                    $allCartProducts.append(
                        '<li class="cartProduct">' +
                            '<div class="cartImg"><img src=' + orderProduct.photoRelUrl + '></div>' +
                            '<div class="cartName" >' + orderProduct.productName + '</div>' +
                            '<div class="cartPrice"> ¥' + orderProduct.currentPrice + '</div>' +
                            '<div class="quantity"> ×' + orderProduct.quantity + '</div>' +
                            '<div class="deleteProduct" onclick="return removeOrderProduct(this ,' + orderProduct.id + ')"><img src="./img/delete.png"></div>' +
                            '</li>'
                    );
                }
            }
        });
    } else {
        $("#allCartProducts").empty().hide();
        $("#cartProducts").slideUp();
        $("#shoppingCart").empty().html('<div onclick="showLoginFrame();return false;">购物车 请先登录</div>');
    }
}


/**
 * 用户退出后刷新购物车
 */
function refreshShoppingCart() {
    userId = "";
    initShoppingCart();
}

/**
 * 提交评论触发的函数
 */
function submitConsult() {
    var comment = $("#consultArea").val();
    if (comment.length == 0) {
        alert("请您输入需要咨询的问题");
        return false;
    }

    var productId = $("#productId").val();
    AjaxInterface.addProductConsult(productId, userId, comment, function (blMessage) {
        if (blMessage.success) {
            var productComment = blMessage.data;
            $("#consultList").append(
                '<li>' +
                    '<div class="commentUser">' + productComment.userName + '</div>' +
                    '<div class="commentContent">' + productComment.comment + '</div>' +
                    '<div class="commentTime">' + productComment.opTime + '</div>' +
                    '</li>'
            );
            $("#consultArea").val('');
        }
    });
    //取消提交按钮的默认提交事件
    return false;
}

$(document).ready(function () {
    //首页商品div上加hover效果
//    addDivHover();

    //初始化购物车
    initShoppingCart();
    $("#cartProducts").hide();

    //用户退出时刷新购物车
    $("#logoutBut").click(function () {
        refreshShoppingCart();
        return true;
    });
});

