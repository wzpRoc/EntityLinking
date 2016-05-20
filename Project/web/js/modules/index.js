/**
 * 重建索引
 */
function rebuildIndex() {
    if(!confirm("确定需要重建索引吗？")) {
        return;
    }
    AjaxInterface.rebuildIndex(
        callback_rebuildIndex
    );

    showPopMessage("正在处理……");
}


/**
 * 重建索引目录的回调函数
 */
function callback_rebuildIndex(blMessage) {
    if (blMessage.success) {
        if(blMessage.message!="") {
            showPopMessage(blMessage.message.replace(new RegExp(/\n/g), "<BR>"));
        } else {
            showPopMessage("处理结束！");
        }
    } else {
        showPopMessage(blMessage.message);
    }
}

/**
 * 重建索引目录
 */
function buildIndexDir() {
    AjaxInterface.buildIndexDir(
        callback_buildIndexDir
    );

    showPopMessage("正在处理……");
}


/**
 * 重建索引目录的回调函数
 */
function callback_buildIndexDir(blMessage) {
    if (blMessage.success) {
        if(blMessage.message!="") {
            showPopMessage(blMessage.message.replace(new RegExp(/\n/g), "<BR>"));
        } else {
            showPopMessage("处理结束！");
        }
    } else {
        showPopMessage(blMessage.message);
    }
}

/**
 * 导入新闻/智库文章
 */
function extractIndex() {
    AjaxInterface.extractIndex(
        getElementById("batchProcessMode").value,
        getElementById("dateBegin").value,
        getElementById("dateEnd").value,
        getElementById("additionalCondition").value,
        callback_extractIndex
    );

    showPopMessage("正在处理，可能需要花费较长时间，请耐心等待……");
}


/**
 * 更新文章的回调函数
 * @param blMessage
 */
function callback_extractIndex(blMessage) {
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