/***********************************************************************************
 *名称：userOrderTime.js
 *功能：负责用户订阅模块页面装载完成后执行的初始化动作管理
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


function myConfirm() {
    getOnlyElementByName("subject.confirmed").value = 1;
    document.getElementById("form0").submit();
}

function updateUserOrderTime(buttonId, id) {
    if(getElementById(buttonId).value == "已开启")
        AjaxInterface.updateUserOrderTime(id, 0,callback_updateUserOrderTime);
    else
        AjaxInterface.updateUserOrderTime(id, 1,callback_updateUserOrderTime);

    if(getElementById(buttonId).value == "已开启")
        getElementById(buttonId).value = "已关闭" ;
    else
        getElementById(buttonId).value = "已开启";

    showPopMessage("正在更新，请耐心等待……");
}

function callback_updateUserOrderTime(blMessage) {
    if (blMessage.success) {

        if (blMessage.message != null) {
            showPopMessage(blMessage.message);
        } else {
            showPopMessage("更新成功！");
        }
    } else {
        showPopMessage(blMessage.message);
    }
}

function updateSubjectListDoc(isToReprocess) {
    var info = getSelectedCheckboxListInfo();

    if (info.cnt == 0) {
        alert("请选择一条或多条记录");
        return;
    }

    AjaxInterface.updateSubjectListDoc(
        info.valueList,
        getElementById("batchProcessMode").value,
        getElementById("dateBegin").value,
        getElementById("dateEnd").value,
        getElementById("additionalCondition").value,
        callback_updateSubjectListDoc
    );

    showPopMessage("正在检索，可能需要花费较长时间，请耐心等待……");
}

function callback_updateSubjectListDoc(blMessage) {
    if (blMessage.success) {
        // getOnlyElementByName("subject.nrDoc").value = blMessage.data["nrDoc"];
        // getOnlyElementByName("subject.nrTankDoc").value = blMessage.data["nrTankDoc"];
        if (blMessage.message != "") {
            showPopMessage(blMessage.message);
        } else {
            showPopMessage("更新成功！");
        }
    } else {
        showPopMessage(blMessage.message);
    }
}

function addGrapDays() {
    var select_newGrapDays = getElementById("select_newGrapDays").value;
    if(select_newGrapDays == "")  {
        alert("时间设定有误，请检查！");
        select_newGrapDays.focus();
        return;
    }

    AjaxInterface.addUserGrapDays(
        userId,
        select_newGrapDays,
        callback_addUserGrapDays
    );

    // 显示等待消息
    showPopMessage("正在保存，请稍候……");
}

function callback_addUserGrapDays(blMessage)  {
    if (blMessage.success) {
        // getOnlyElementByName("subject.nrDoc").value = blMessage.data["nrDoc"];
        // getOnlyElementByName("subject.nrTankDoc").value = blMessage.data["nrTankDoc"];
        if (blMessage.message != null && blMessage.message != "") {
            showPopMessage(blMessage.message);
        } else {
            showPopMessage("更新间隔天数成功！");
        }
    } else {
        showPopMessage(blMessage.message);
    }
    save();
}

function addUserOrderTime() {
    // 得到要订阅的主题ID
    var orderTime1 = 0;
    var tmpDoWhat =  getOnlyElementByName("dowhat").value;
    if(tmpDoWhat == "messageTime")
         orderTime1 = getElementById("select_newOrderTime1").value;
    var orderTime2 = getElementById("select_newOrderTime2").value;
    if ((tmpDoWhat=="messageTime" && orderTime1 == "") || orderTime2 == "" ||
        (tmpDoWhat=="messageTime" && parseInt(orderTime1)>=parseInt(orderTime2))) {
        alert("时间设定有误，请检查！");
        select_newOrderTime.focus();
        return;
    }

    var type;
    if(tmpDoWhat == "messageTime")
        type = 0;
    else
        type = 1;
    // 调用AJAX保存
    AjaxInterface.addUserOrderTime(
        userId,
        orderTime1,
        orderTime2,
        1,
        type,
        callback_addUserSubject
    );

    // 显示等待消息
    showPopMessage("正在保存，请稍候……");
}

function callback_addUserSubject(blMessage) {
    if (blMessage.success) {
        if (blMessage.message != null && blMessage.message != "") {
            showPopMessage(blMessage.message);
        } else {
            showPopMessage("保存成功！");
        }
    } else {
        showPopMessage(blMessage.message);
    }
    save();
}
