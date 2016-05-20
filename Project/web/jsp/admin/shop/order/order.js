/**
 *  如果是首次添加订单则隐藏部分信息
 */
function hiddenPayInfo() {
    var pageType = $("#pageType").val();
    var state = $("#state").val();
    //如果订单状态是“拍下订单”则隐藏部分信息
    if (pageType == "detail") {
        if (state == "1") {
            $(".state2").hide();
            $(".state3").hide();
            $(".state4").hide();
        } else if (state == "2") {
            $(".state3").hide();
            $(".state4").hide();
        } else if (state == 3) {
            $(".state4").hide();
        }

    }
}

/**
 * 发货
 */
function sendGoods() {
    var id = $("#id").val();
    var logisticsCompanyName = $("#logisticsCompanyName").val();
    var logisticsId = $("#logisticsId").val();
    var deliverTime = $("#deliverTime").val();
    if (id == null || "" == id.trim()) {
        alert("获取订单Id失败");
    }

    AjaxInterface.sendGoods(id, logisticsCompanyName, logisticsId, deliverTime, function (blMessage) {
        if (blMessage.success) {
            alert("保存成功");
        } else {
            alert("抱歉，信息保存失败" + blMessage.message);
        }
    });

    return false;
}


$(document).ready(function () {
    //hiddenPayInfo();
})