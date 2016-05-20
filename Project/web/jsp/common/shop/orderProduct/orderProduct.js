/**
 * 删除购物车中的商品
 * @param obj
 * @param id
 */
function removeOrderProduct(obj, id) {
    // 确认询问
    if (!confirm("确认将此商品删除吗？")) {
        return false;
    }
    AjaxInterface.removeOrderProduct(id, function (blMessage) {
        if (blMessage.success) {
            //删除成功
            $(obj).closest("tr").remove();
            //重新计算总价
            calTotalPrice();
        } else {
            alert("抱歉，商品删除失败" + blMessage.message);
        }
    });
}

/**
 * 计算购物车总价函数
 */
function calTotalPrice() {
    var totalPrice = 0;
    $("#dataTable .tbody").each(function () {
        var $row = $(this);
        if ($row.find("input").is(":checked")) {
            var price = $row.find(".productAmount").text().trim();
            totalPrice = (parseFloat(totalPrice) + parseFloat(price)).toFixed(2);
        }
    });
    $("#amountNum").text(totalPrice);
}

/**
 * “去结算”函数
 */
function payProducts(){
    //1 查询被所有被选择的orderProduct的id
    var orderProductIds=new Array();
    $("#dataTable .tbody").each(function () {
        var $input = $(this).find("input");
        if ($input.is(":checked")) {
            orderProductIds.push($input.val());
        }
    });
    if(orderProductIds.length==0){
        alert("请勾选需要购买的商品");
        return;
    }
    //2 根据多个orderProduct生成order  并返回orderId
    AjaxInterface.buyProducts(orderProductIds.join(","), function(blMessage){
       if(blMessage.success){
           //3 跳转到orderId页面
           var order=blMessage.data;
           window.location.href = "orderDetail?id=" + order.id;
       } else{
           alert(blMessage.message);
       }
    });
}
