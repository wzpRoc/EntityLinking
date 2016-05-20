/**********************************************************************************************************************************************
 *名称：userMessage.js
 *功能：用户消息
 *开发者：鲁廷明
 *时间：2012-07-23
 **********************************************************************************************************************************************/



/**
 * 开始更新用户消息
 */
function startUserMessageUpdater(){
    // 等待2秒后执行
    setTimeout(updateUserMessage, 2 * 1000);

    // 每隔一定时间执行
    setInterval(updateUserMessage, USER_MESSAGE_UPDATE_INTERVAL_SECOND * 1000);
    // 测试
    // var timer_userMessage = setInterval(updateUserMessage, 1000);
}


/**
 * 更新用户消息
 */
function updateUserMessage(){
    AjaxInterface.getNrUnreceivedUserMessage(showUserMessage);
}


/**
 * 显示用户消息
 **/
function showUserMessage(blMessage) {
    if(blMessage.success) {
        var a_userMessage = document.getElementById("userMessageInHead");
        if(a_userMessage) {
            var innerHtml;
            var nrMsg = blMessage.data;
            if(nrMsg == 0){
                innerHtml = "消息(0)"
            } else {
                innerHtml = "<span class='unreceivedUserMessage'>"+"新消息("+nrMsg+")</span>";
            }
            a_userMessage.innerHTML = innerHtml;
        }
    } else {
        alert("获取用户消息失败："+blMessage.message);
    }
}


/**
 * 接收用户消息后的回调函数
 **/
function afterCallReceiveUserMessage(blMessage) {
    if(blMessage.success) {
        // ok
        document.getElementById("queryForm").dowhat = "";
    } else {
        alert("更新用户消息状态失败："+blMessage.message);
    }
}


