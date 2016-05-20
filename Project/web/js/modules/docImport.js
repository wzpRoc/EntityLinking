/**
 * 导入新闻/智库文章
 */
function importDoc() {
    AjaxInterface.importDoc(
        getElementById("tankTag").value,
        getElementById("toUseLastCollectTime").checked,
        getElementById("additionalCondition").value,
//        getElementById("tempDBName").value,
//        getElementById("docTableName").value,
//        getElementById("docLobTableName").value,
//        getElementById("docAuthorTableName").value,
        callback_importDoc
    );

    showPopMessage("正在处理，可能需要花费较长时间，请耐心等待……");
}


/**
 * 更新文章的回调函数
 * @param blMessage
 */
function callback_importDoc(blMessage) {
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