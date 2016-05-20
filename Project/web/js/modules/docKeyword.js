/**
 * 导入新闻/智库文章
 */
function extractDocKeyword() {
    AjaxInterface.extractDocKeyword(
        getElementById("batchProcessMode").value,
        getElementById("dateBegin").value,
        getElementById("dateEnd").value,
        getElementById("additionalCondition").value,
        callback_extractDocKeyword
    );

    showPopMessage("正在处理，可能需要花费较长时间，请耐心等待……");
}


/**
 * 更新文章的回调函数
 * @param blMessage
 */
function callback_extractDocKeyword(blMessage) {
    if (blMessage.success) {
        // getOnlyElementByName("subject.nrDoc").value = blMessage.data;
        if(blMessage.message!="") {
            showPopMessage(blMessage.message.replace(new RegExp(/\n/g), "<BR>"));
        } else {
            showPopMessage("处理结束！");
        }
    } else {
        showPopMessage(blMessage.message);
    }
}