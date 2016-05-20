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
 * 将商品添加到购物车
 * @param commodity
 */
function addToCart(commodity) {
    addOrderCommodity(commodity, 2);
}

/**
 * 将商品添加到心愿
 * @param commodity
 */
function addToWish(commodity) {
    addOrderCommodity(commodity, 1);
}

/**
 * 将商品添加到购物车或心愿
 * type为1表示购物车 2表示心愿
 */
function addOrderCommodity(commodityId, type) {
    // 验证用户是否已经登录
    if (!isUserLogged()) {
        return false;
    }

    AjaxInterface.addOrderCommodity(commodityId, 1, userId, type, function (blMessage) {
        if (blMessage.success) {
            refreshCartWish(type);
        } else {
            alert(blMessage.message);
        }
    });
    return false;
}

/**
 * 刷新购物车或心愿
 */
function refreshCartWish(type) {
    if (userId != null && "" != userId) {
        AjaxInterface.fetchOrderCommodityList(userId, type, function (blMessage) {
            if (blMessage.success) {
                var commodityList = blMessage.data;
                if (type == 1) {
                    $("#cartProductsNum").text(commodityList.length);
                } else if (type == 2) {
                    $("#wishProductsNum").text(commodityList.length);
                }
            }
        });
    } else {
        $("#cartProductsNum").text("");
        $("#wishProductsNum").text("");
    }
}

/**
 * 追加商品追加到$obj中去
 * @param $obj
 * @param commodity
 */
function addCommodityHtml($obj, commodity) {
    $obj.append('<li class="comp_item" id="comp' + commodity.id + '">' +
        '<p class="item_img">  <img src="' + commodity.photoRelUrl + '"></p>' +
        '<p class="item_title"><a>' + commodity.name + '</a></p>' +
        '<p class="item_remove"><a onclick="removeCommodity(' + commodity.id + ', \'' + commodity.topCategoryId + '\', this)">清除</a></p>' +
        '</li>');

}
/**
 * 添加对比商品
 * @param commodityId
 */
function compareCommodity(commodityId, obj) {
    if (commodityId == null || "" == commodityId) {
        alert("获取商品id出错");
        return;
    }
    //1 查询出商品的相关信息
    AjaxInterface.fetchCommodity(commodityId, function (blMessage) {
        if (blMessage.success) {
            var commodity = blMessage.data;
            var commodityTopCategory = commodity.topCategoryId;
            var commodityName = commodity.name;
            // 2 根据商品类别判断session中是否有同类商品
            AjaxInterface.addCompareCommodity(commodityId, commodityTopCategory, function (message) {
                if (message.success) {
                    //将商品追加在对比栏目中
                    addCommodityHtml($("#comp_items"), commodity);
                    $("#comp_box").show();
                    //将“对比”变为“取消”
                    $(obj).text("取消");
                    $(obj).attr("onclick", 'removeCommodity(' + commodityId + ',"' + commodityTopCategory + '",this)');
                } else {
                    alert(message.message)
                }
            });
        } else {
            alert(blMessage.message);
        }
    });
}

/**
 * 删除对比栏中的商品 页面中的“取消”按钮
 * @param commodityTopCategory
 * @param commodityId
 */
function removeCommodity(commodityId, commodityTopCategory) {
    if (commodityId == null && commodityTopCategory == null) {
        commodityTopCategory = $("#topCategoryId").val();
    }

    AjaxInterface.removeCompareCommodity(commodityId, commodityTopCategory, function (blMessage) {
        // 将商品从对比栏目中删除
        if (blMessage.success) {
            //如果商品id不为空则只删除此商品
            if (commodityId != null) {
                //删除对比栏目上的物品
                $("#comp" + commodityId).remove();
                //将“取消”改为对比
                $("#product" + commodityId).find(".compare").text("对比").attr("onclick", 'compareCommodity(' + commodityId + ', this)');
            } else {  //商品id为空间此类别的商品全部删除
                $("#comp_items").empty();
                window.location.reload();
            }
            //如果商品全部删除了则隐藏对比栏
            if ($("#comp_item").find(".comp_item").length == 0) {
                $("#comp_item").hide();
            }
        } else {
            alert(blMessage.message);
        }
    });
}


/**
 * 初始化商品对比栏
 */
function initCommodityComparisonBar() {
    var topCategoryId = $("#topCategoryId").val();
    //没有商品类别
    if (topCategoryId == null || "" == topCategoryId) {
        return;
    }
    AjaxInterface.fetchCompareCommodity(topCategoryId, function (blMessage) {
        if (blMessage.success) {
            var commodityList = blMessage.data;
            if (commodityList.length > 0) {
                for (var i = 0; i < commodityList.length; i++) {
                    var commodity = commodityList[i];
                    addCommodityHtml($("#comp_items"), commodity);
                }
                $("#comp_box").show();
            }
        } else {
            alert(blMessage.message);
        }

    });

}

$(document).ready(function () {
    // 刷新购物车
    refreshCartWish(1);
    //刷新心愿
    refreshCartWish(2);
    //初始化商品对比栏
    initCommodityComparisonBar();

});