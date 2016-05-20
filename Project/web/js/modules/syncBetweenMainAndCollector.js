
/***********************************************************************************
 *名称：syncBetweenMainAndCollector.js
 *功能：负责数据同步动作管理
 *开发者：贾治中
 *时间：2013-7-2
 ***********************************************************************************/
$(document).ready(function () {
// 禁用高级筛选选项
// disableAdvancedOntion();
    myInit();
});
function myInit() {
}
function save() {
    document.getElementById("form0").submit();
}

function run(){
    var RSSChecked = getOnlyElementByName("RSSBox").checked;
    var tankChecked = getOnlyElementByName("tankBox").checked;
    var crawlerChecked = getOnlyElementByName("crawlerBox").checked;

    if(!RSSChecked && !tankChecked && !crawlerChecked) {
        alert("未选择同步数据！");
        return;
    }
    // 调用AJAX保存
    AjaxInterface.SyncBetweenMainAndCollector(
        RSSChecked,
        tankChecked,
        crawlerChecked,
        callback_syncBetweenMainAndCollector
    );
// 显示等待消息
    showPopMessage("正在同步，请稍候……");
}

function callback_syncBetweenMainAndCollector(blMessage) {
    if (blMessage.success) {
        if (blMessage.message != null && blMessage.message != "") {
            showPopMessage(blMessage.message);
        } else {
            showPopMessage("同步数据成功！");
        }
    } else {
        showPopMessage(blMessage.message);
    }
}
