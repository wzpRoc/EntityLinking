/**
 * 计算相关性
 */
function computeRelativity() {
    AjaxInterface.computeRelativity(
        getElementById("ruleSetId").value,
        getElementById("tempDBName").value,
        getElementById("docTableName").value,
        getElementById("docLobTableName").value,
        callback_computeRelativity
    );

    showPopMessage("正在计算，可能需要花费较长时间，请耐心等待……");
}


/**
 * 更新文章的回调函数
 * @param blMessage
 */
function callback_computeRelativity(blMessage) {
    if (blMessage.success) {
        // getOnlyElementByName("subject.nrDoc").value = blMessage.data;
        if(blMessage.message!="") {
            showPopMessage(blMessage.message.replace(new RegExp(/\n/g), "<BR>"));
        } else {
            showPopMessage("计算结束！");
        }
    } else {
        showPopMessage(blMessage.message);
    }
}