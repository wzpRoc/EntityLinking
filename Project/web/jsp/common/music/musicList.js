function search(queryWhat) {
    getOnlyElementByName('queryWhat').value=queryWhat;
    getOnlyElementByName('mainForm').submit();
}

/**
 * 音乐清单动画
 */
function toggleMusics() {
    $("#cartMusics").slideToggle();
}


/**
 * 加入音乐清单的动画
 */
function addMusicCart(albumId) {
//    alert(1);
//    // 验证用户是否已经登录
//    if (!isUserLogged()) {
//        return false;
//    }

    AjaxInterface.favorAlbum(albumId, function (blMessage) {
        if (blMessage.success) {
            //刷新音乐清单信息
            alert(blMessage.message);
            initMusicCart();
            $("#cartMusics").slideDown();
        } else {
            alert(blMessage.message);
        }
    });
    return false;
}



/**
 * 用户退出后刷新购物车
 */
function refreshMusicCart() {
    userId = "";
    initMusicCart();
}

/**
 * 初始化音乐清单的内容
 */
function initMusicCart() {
    if (userId != null && "" != userId) {
        AjaxInterface.getAlbumFavorList(userId, function (blMessage) {
            if (blMessage.success) {
                var albumFavorList = blMessage.data;
                $("#musicSum").text(albumFavorList.length);
                var $allMusics = $("#allMusics");
                $("#allMusics").empty();
                for (var i = 0; i < albumFavorList.length; i++) {
                    var albumFavor = albumFavorList[i];
                    $allMusics.append(
                        '<li class="cartMusic">' +
                            '<div class="cartImg"><img src=' + albumFavor.photoRelUrl+ '></div>' +
                            '<div class="cartName" >' + albumFavor.albumTitle + '</div>' +
//                            '<div class="cartPrice"> ¥' + orderProduct.currentPrice + '</div>' +
//                            '<div class="quantity"> ×' + orderProduct.quantity + '</div>' +
                            '<div class="deleteProduct" onclick="return removeMusicFaovr(this ,' + albumFavor.id + ')"><img src="./img/delete.png"></div>' +
                            '</li>'
                    );
                }
            }
        });
    } else {
        $("#allMusics").empty().hide();
        $("#cartMusics").slideUp();
        $("#musicCart").empty().html('<div onclick="showLoginFrame();return false;">音乐清单 请先登录</div>');
    }
}

/**
 * 根据id删除音乐清单中对应的音乐
 * @param obj
 * @param id
 */
function removeMusicFaovr(obj, id) {
    AjaxInterface.removeAlbumFavor(id, function (blMessage) {
        if (blMessage.success) {
            //刷新音乐清单
            initMusicCart();
            $("#cartMusic").slideDown();
        } else {
            alert("抱歉，删除失败" + blMessage.message);
        }

    });
    return false;
}


$(document).ready(function () {


    //初始化购物车
    initMusicCart();
    $("#cartMusics").hide();

    //用户退出时刷新购物车
    $("#logoutBut").click(function () {
        refreshMusicCart();
        return true;
    });
});

