/**
 * 创建话题
 */
function createTopic() {
    var title = document.getElementById("topicTitle").value;
    var content = document.getElementById("textarea_content").value;
    var category = 1;
    if(title == "") {
        alert("话题标题不能为空！");
        return false;
    } else if(content == "") {
        alert("话题内容不能为空！");
        return false;
    }
    else {
        AjaxInterface.createTopic(category, title, content, creteTopic_callback);
        return true;
    }
}


/**
 * 创建话题回调函数
 * @param blMessage
 */
function creteTopic_callback(blMessage) {
    if(blMessage.success) {
        // 成功
        alert(blMessage.message);
    } else {
        // 失败
        alert(blMessage.message);
    }

}

/**
 * 回复话题
 */
function replyTopic() {
    var topicId = getOnlyElementByName("dto.id").value;
    var content = document.getElementById("textarea_content").value;
    if(content == "") {
        alert("内容不能为空！");
        return false;
    } else {
        AjaxInterface.replyTopic(topicId, content, replyTopic_callback);
        return true;
    }
}

/**
 * 回复话题回调函数
 * @param blMessage
 */
function replyTopic_callback(blMessage) {
    if(blMessage.success) {
        // 成功
        alert(blMessage.message);
    } else {
        // 失败
        alert(blMessage.message);
    }

}