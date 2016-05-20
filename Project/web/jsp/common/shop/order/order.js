/**
 *  如果是首次添加订单则隐藏部分信息
 */
function hiddenPayInfo() {
    var pageType = $("#pageType").val();
    var state = $("#state").val();
    //如果订单状态是“拍下订单”则隐藏部分信息
    if (pageType == "detail") {
        if (state == "1") {
            $(".state2, .state3, .state4").hide();
        } else if (state == "2") {
            $(".state3, .state4").hide();
        } else if (state == 3) {
            $(".state4").hide();
        }
    }
}

/**
 * 提交订单的事件
 */
function submitOrder() {
    //1 保存收货信息
    var orderId = $("#orderId").val();
    var consignee = $("#consignee").val();
    var mobilephone = $("#mobilephone").val()
    var address = $("#address").val();
    var zipCode=$("#zipCode").val();
    if (orderId == null) {
        alert("订单提交失败");
        return false;
    }

    //2 提交订单
    AjaxInterface.updateOrderAddress(orderId, consignee, mobilephone, address,zipCode, function (blMessage) {
        if (blMessage.success) {
            $("#mainForm").submit();
            return false;
        } else {
            alert(blMessage.message);
            return false;
        }
    });
}

/**
 * 删除订单
 * @param id
 * @param obj
 * @return {boolean}
 */
function removeOrder(id, obj){
    if(!confirm("您确定删除此订单吗？")){
        return false;
    }
    if(id == null){
        return false;
    }
    AjaxInterface.removeOrder(id, function(blMessage){
      if(blMessage.success){
           $("tr[orderId='"+id+"']").slideUp("slow");
      }else{
          alert(blMessage.message);
      }
    });
}

/**
 * 确认收货
 * @param id
 */
function confirmReceipt(id){
    if(!confirm("您确认收货吗？")){
        return false;
    }
    if(id == null || "" == id){
        alert("抱歉，订单id获取失败");
        return false;
    }
    AjaxInterface.confirmReceipt(id, function(blMessage){
        if(blMessage.success){
            //刷新页面
            window.location.reload();
        }else{
            alert(blMessage.message);
        }
    });
}

