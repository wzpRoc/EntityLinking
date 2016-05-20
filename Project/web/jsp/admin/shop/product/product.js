function toggleSubData(e) {
    $(e).siblings(".subData").slideToggle();
}

function toggleHideData(e) {
    $("#picForm").slideToggle();
    $(e).siblings(".subData").slideToggle();
}

/**
 * 删除商品参数
 * @param id
 * @param obj
 */
function deleteProductParam(id, obj) {
    if (id == null) {
        alert("删除失败");
        return;
    }
    if(!confirm("您确定要删除此参数吗？")){
        return;
    }
    AjaxInterface.deleteProductParam(id, function (blMessage) {
        if (blMessage.success) {
            $(obj).closest("li").remove();
        } else {
            alert("抱歉，参数删除失败" + blMessage.message);
        }
    });
}

/**
 * 新增参数的事件
 */
function addProductParam() {
    $("#productParam").append(
        '<li>' +
            '<input class="name">：' +
            '<input class="value">' +
            '<span class="saveParam" onclick="saveProductParam(this)">保存</span>' +
            '</li>');

}

/**
 * 保存商品参数
 * @param obj
 */
function saveProductParam(obj) {
    var productId = $("#productId").val();
    var name = $(obj).siblings("input").filter(".name").val();
    var value = $(obj).siblings("input").filter(".value").val();
    AjaxInterface.saveProductParam(productId, name, value, function (blMessage) {
        if (blMessage.success) {
            var param = blMessage.data;
            $(obj).closest("li").remove();
            $("#productParam").append(
                '<li id="' + param.id + '">' +
                    param.name + ':' +
                    param.value +
                    '<span class="deleteParam" onclick="deleteProductParam(' + param.id + ', this)">删除</span>' +
                    '</li>');
        } else {
            alert(blMessage.message);
        }
    });
}

/**
 * 删除商品评论
 * @param obj
 * @param id
 */
function deleteProductComment(obj, id) {
    if (id == null) {
        alert("删除失败，id为空");
        return;
    }
    AjaxInterface.deleteProductComment(id, function (blMessage) {
        if (blMessage.success) {
            $(obj).closest("li").remove();
        } else {
            alert(blMessage.message);
        }
    });
}

/**
 * 更新商品的回复
 * @param obj
 * @param id
 */
function updateProductReply(obj, id) {
    if (id == null) {
        alert("删除失败，id为空");
        return;
    }
    var newReply = $(obj).siblings(".newReply").val();
    AjaxInterface.updateProductReply(id, newReply, function (blMessage) {
        if (blMessage.success) {
            $(obj).closest("div").siblings(".commentReply").children(".oldReply").empty().html(newReply);
        } else {
            alert(blMessage.message);
        }
    });

}

/**
 * 增加商品评论
 */
function addProductComment() {
    var comment = $("#commentArea").val();
    if (comment.length == 0) {
        alert("请您输入评论");
        return false;
    }

    var productId = $("#productId").val();
    //todo 后台添加评论和咨询人员身份默认为匿名用户
    var userId = 0;
    AjaxInterface.addProductComment(productId, userId, comment, function (blMessage) {
        if (blMessage.success) {
            var productComment = blMessage.data;
            $("#commentList").append(
                '<li>' +
                    '<div class="commentUser">' + productComment.userName +
                    '<span class="deleteComment" onclick="deleteProductComment(this, ' +productComment.id+ ')">删除此问题</span></div>' +
                    '<div class="commentContent" >' + productComment.comment + '</div>' +
                    '<div class="commentReply"  onclick="editReply(this)"><span class="oldReply">' + productComment.reply + '</span></div>' +
                    '<div class="commentReply editReply" >' +
                    '<textarea class="newReply">' + productComment.reply + '</textarea>' +
                    '<button onclick="updateProductReply(this ,' + productComment.id + ')">保存</button></div>' +
                    '<div class="commentTime">' + productComment.opTime + '</div>' +
                    '</li>'
            );
            $("#commentArea").val('');
        }
    });
    //取消提交按钮的默认提交事件
    return false;
}

/**
 * 增加商品咨询
 */
function addProductConsult() {
    var comment = $("#consultArea").val();
    if (comment.length == 0) {
        alert("请您输入需要咨询的问题");
        return false;
    }

    var productId = $("#productId").val();
    //todo 后台添加评论和咨询人员身份默认为匿名用户
    var userId = 0;
    AjaxInterface.addProductConsult(productId, userId, comment, function (blMessage) {
        if (blMessage.success) {
            var productComment = blMessage.data;
            $("#consultList").append(
                '<li>' +
                    '<div class="commentUser">' + productComment.userName +
                    '<span class="deleteComment" onclick="deleteProductComment(this, ' +productComment.id+ ')">删除此问题</span></div>' +
                    '<div class="commentContent">' + productComment.comment + '</div>' +
                    '<div class="commentReply"  onclick="editReply(this)"><span class="oldReply">' + productComment.reply + '</span></div>' +
                    '<div class="commentReply editReply">' +
                    '<textarea class="newReply">' + productComment.reply + '</textarea>' +
                    '<button onclick="updateProductReply(this ,' + productComment.id + ')">保存</button></div>' +
                    '<div class="commentTime">' + productComment.opTime + '</div>' +
                    '</li>'
            );
            $("#consultArea").val('');
        }
    });
    //取消提交按钮的默认提交事件
    return false;
}

/**
 * 编辑回复
 * @param obj
 */
function editReply(obj){
    $(obj).siblings(".commentReply").slideToggle();
}




