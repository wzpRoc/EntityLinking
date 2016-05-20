function toggleSubData(e) {
    $(e).siblings(".subData").slideToggle();
}

function toggleHideData(e) {
    $("#picForm").slideToggle();
    $(e).siblings(".subData").slideToggle();
}

function passCheck(replyId) {
    AjaxInterface.passReplyCheck(replyId, passReplyCheck_callback);
}

function failCheck(replyId) {
    AjaxInterface.failReplyCheck(replyId, failReplyCheck_callback);
}

/**
 * 审核通过话题回调函数
 * @param blMessage
 */
function passReplyCheck_callback(blMessage) {
    if(blMessage.success) {
        // 成功
        alert(blMessage.message);
        document.getElementById("mainForm").submit();
    } else {
        // 失败
        alert(blMessage.message);
    }

}

/**
 * 审核不通过话题回调函数
 * @param blMessage
 */
function failReplyCheck_callback(blMessage) {
    if(blMessage.success) {
        // 成功
        alert(blMessage.message);
        document.getElementById("mainForm").submit();
    } else {
        // 失败
        alert(blMessage.message);
    }

}