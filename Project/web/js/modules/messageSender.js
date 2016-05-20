
/***********************************************************************************
 *名称：messageSender.js
 *功能：负责信息推送服务模块页面装载完成后执行的初始化动作管理
 *开发者：贾治中
 *时间：2013-3-12
 ***********************************************************************************/
// subject页面加载完成时
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
function on_shortMessageSender_clicked(select){
    var checked = select.checked;
// 调用AJAX保存
    AjaxInterface.ChangeShortMessageSender(
        checked,
        callback_updateChangeShortMessageSender
    );
// 显示等待消息
    showPopMessage("正在更新，请稍候……");
}
function callback_updateChangeShortMessageSender(blMessage) {
    if (blMessage.success) {
        if (blMessage.message != null && blMessage.message != "") {
            showPopMessage(blMessage.message);
        } else {
            showPopMessage("变动短信服务成功！");
        }
    } else {
        showPopMessage(blMessage.message);
    }
}
function on_mailSender_clicked(select){
    var checked = select.checked;
// 调用AJAX保存
    AjaxInterface.ChangeMailSender(
        checked,
        callback_updateChangeMailSender
    );
// 显示等待消息
    showPopMessage("正在更新，请稍候……");
}
function callback_updateChangeMailSender(blMessage) {
    if (blMessage.success) {
        if (blMessage.message != null && blMessage.message != "") {
            showPopMessage(blMessage.message);
        } else {
            showPopMessage("变动邮件服务成功！");
        }
    } else {
        showPopMessage(blMessage.message);
    }
}
function on_antishield_clicked(select){
    var checked = select.checked;
// 调用AJAX保存
    AjaxInterface.ChangeAntishield(
        checked,
        callback_updateChangeAntishield
    );
// 显示等待消息
    showPopMessage("正在更新，请稍候……");
}
function callback_updateChangeAntishield(blMessage) {
    if (blMessage.success) {
        if (blMessage.message != null && blMessage.message != "") {
            showPopMessage(blMessage.message);
        } else {
            showPopMessage("变动防屏蔽设置成功！");
        }
    } else {
        showPopMessage(blMessage.message);
    }
}
function addAntiShieldStr(){
    var antiShieldStr = getOnlyElementByName("antiShieldStr").value;

    if(antiShieldStr=="") {
        alert("无内容修改！");
        select_newOrderTime.focus();
        return;
    }
    // 调用AJAX保存
    AjaxInterface.AddAntiShieldStr(
        antiShieldStr,
        callback_updateAddAntiShieldStr
    );
// 显示等待消息
    showPopMessage("正在更新，请稍候……");
}

function callback_updateAddAntiShieldStr(blMessage) {
    if (blMessage.success) {
        if (blMessage.message != null && blMessage.message != "") {
            showPopMessage(blMessage.message);
        } else {
            showPopMessage("更改防屏蔽字符串成功！");
        }
    } else {
        showPopMessage(blMessage.message);
    }
}

function addNewSet(){
    var checkingTime = getElementById("select_newCheckingTime").value;
    var numOfNewsForMail = getElementById("select_newNumOfNewsForMail").value;
    var maxNumOfMails =  getElementById("select_newMaxNumOfMails").value;

    if(checkingTime=="" || checkingTime==0
        || numOfNewsForMail=="" || numOfNewsForMail==0
        || maxNumOfMails=="" || maxNumOfMails==0) {
        alert("系统检测设定有误！");
        select_newOrderTime.focus();
        return;
    }

// 调用AJAX保存
    AjaxInterface.AddNewSet(
        checkingTime,
        numOfNewsForMail,
        maxNumOfMails,
        callback_updateAddNewSet
    );
// 显示等待消息
    showPopMessage("正在更新，请稍候……");
}
function callback_updateAddNewSet(blMessage) {
    if (blMessage.success) {
        if (blMessage.message != null && blMessage.message != "") {
            showPopMessage(blMessage.message);
        } else {
            showPopMessage("更改系统设定成功！");
        }
    } else {
        showPopMessage(blMessage.message);
    }
}

function send(){
    var messageChecked = getElementById("messageType").checked;
    var sendNumber = getOnlyElementByName("numberStr").value;
    var sendContent = getOnlyElementByName("contentStr").value;
    var isEnglish = getOnlyElementByName("checkEnglishBox").checked;
    if(sendNumber=="" || sendContent=="") {
        alert("号码/地址缺失或无内容，无法正常发送！");
        return;
    }

    // 调用AJAX保存
    AjaxInterface.Send(
        sendNumber, sendContent, isEnglish, messageChecked,
        callback_send
    );
// 显示等待消息
    showPopMessage("正在发送，请稍候……");
}

function callback_send(blMessage) {
    if (blMessage.success) {
        if (blMessage.message != null && blMessage.message != "") {
            showPopMessage(blMessage.message);
        } else {
            showPopMessage("发送成功！");
        }
    } else {
        showPopMessage(blMessage.message);
    }
}