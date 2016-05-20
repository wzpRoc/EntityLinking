/**
 * 导入新闻/智库文章
 */
var lastUpdateStateDesc = document.getElementById("lastUpdateStateDesc");
if(lastUpdateStateDesc!=null){
	lastUpdateStateDesc.value = lastUpdateStateDesc.value.replace(new RegExp(/\n+/g), "\n");
}


function startUpdateScheduler() {
    AjaxInterface.startUpdateScheduler();

    showPopMessage("已发送消息到系统，刷新可以查看当前状态");
}


function stopUpdateScheduler() {
    AjaxInterface.stopUpdateScheduler();

    showPopMessage("已发送消息到系统，刷新可以查看当前状态");
}


function stopImport() {
    AjaxInterface.stopImport();
    showPopMessage("已发送消息到系统，刷新可以查看当前状态");
}


function saveAnalysisUpdateInterval() {
        AjaxInterface.saveAnalysisUpdateInterval(
                getElementById("updateInterval").value,
                callback_saveUpdateTime
                );

    showPopMessage("正在处理，可能需要花费较长时间，请耐心等待……");
}


function update(toUseCustomrizedConfig) {
    if (toUseCustomrizedConfig) {
        AjaxInterface.update(
                getElementById("dateBegin").value,
                getElementById("dateEnd").value,
                getElementById("additionalCondition").value,
                callback_extractUpdate
                );
    } else {
        AjaxInterface.update(
                null,
                null,
                null,
                callback_extractUpdate
                );
    }

    showPopMessage("正在处理，可能需要花费较长时间，请耐心等待……");
}



function importByDay() {
    AjaxInterface.importByDay(
        getElementById("dateBegin").value,
        getElementById("dateEnd").value,
        getElementById("additionalCondition").value,
        callback_extractUpdate
    );

    showPopMessage("正在处理，可能需要花费较长时间，请耐心等待……");
}


/**
 * 更新文章的回调函数
 */
function callback_saveUpdateTime(result) {
    showPopMessage("保存成功！");
}


/**
 * 更新文章的回调函数
 * @param blMessage
 */
function callback_extractUpdate(blMessage) {
    if (blMessage.success) {
        // getOnlyElementByName("subject.nrDoc").value = blMessage.data;
        if (blMessage.message != "") {
            var msgHtml = blMessage.message.replace(new RegExp(/\n/g), "<BR>");
            // alert(msgHtml);
            showPopMessage(msgHtml);
        } else {
            showPopMessage("处理结束！");
        }
    } else {
        showPopMessage(blMessage.message);
    }
}