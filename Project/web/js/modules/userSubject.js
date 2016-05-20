/***********************************************************************************
 *名称：userSubject.js
 *功能：负责专题模块页面DOM装载完成后执行的初始化动作管理
 *开发者：彭程
 *时间：2011-11-6
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


function gotoRuleSetPage(dowhat) {
    var url = ctx + "/advanced/ruleSetDetail!get" +
        "?dowhat=" + dowhat +
        "&id=" + getOnlyElementByName("subject.ruleSetId").value +
        "&moduleName=subject" +
        "&idInModule=" + getOnlyElementByName("subject.id").value +
        "&name=" + getOnlyElementByName("subject.name").value;
    var ruleSetWindow = window.open(url, "_blank", "width=1100, height=700, scrollbars=yes, modal=yes");
}


function onRuleSetSaved(ruleSet) {
    // 返填ID
    getOnlyElementByName("subject.ruleSetId").value = ruleSet.id;
    // 隐藏创建规则的链接
    document.getElementById("a_createRuleSet").style.display = "none";
    // 显示编辑规则的链接
    document.getElementById("a_editRuleSet").style.display = "";
}


function updateSubjectDoc() {
    var ruleSetId = getOnlyElementByName("subject.ruleSetId").value;
    if (ruleSetId == 0) {
        alert("请先创建规则");
        return;
    }

    var subjectId = getOnlyElementByName("subject.id").value;
    var tankArticleOnly = getOnlyElementByName("subject.tankArticleOnly").value;

    AjaxInterface.updateSubjectDoc(subjectId, tankArticleOnly, ruleSetId, callback_updateSubjectDoc);

    showPopMessage("正在更新，可能需要花费较长时间，请耐心等待……");
}

function callback_updateSubjectDoc(blMessage) {
    if (blMessage.success) {
        getOnlyElementByName("subject.nrDoc").value = blMessage.data["nrDoc"];
        getOnlyElementByName("subject.nrTankDoc").value = blMessage.data["nrTankDoc"];
        if (blMessage.message != "") {
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


function addUserSubject() {
    // 得到要订阅的主题ID
    var select_newSubject = getElementById("select_newSubject");
    var subjectId = getSelectedValue(select_newSubject);
    if (subjectId == "") {
        alert("请选择要订阅的主题");
        select_newSubject.focus();
        return;
    }
    var tmpType = 0;
    if(getOnlyElementByName("dowhat").value == "mailContent")
        tmpType = 1;
    // 调用AJAX保存
    AjaxInterface.addUserSubject(
        userId,
        subjectId,
        tmpType,
        callback_addUserSubject
    );

    // 显示等待消息
    showPopMessage("正在保存，请稍候……");
}

function deleteUserSubject() {
    // 得到要订阅的主题ID
    var select_newSubject = getElementById("select_newSubject");
    var subjectId = getSelectedValue(select_newSubject);
    if (subjectId == "") {
        alert("请选择要订阅的主题");
        select_newSubject.focus();
        return;
    }

    // 调用AJAX保存
    AjaxInterface.deleteUserSubject(
        subjectId,
        callback_addUserSubject
    );

    // 显示等待消息
    showPopMessage("正在取消订阅，请稍候……");
}

function callback_addUserSubject(blMessage) {
    if (blMessage.success) {
        if (blMessage.message != null && blMessage.message != "") {
            showPopMessage(blMessage.message);
        } else {
            showPopMessage("增加订阅成功！");
        }
    } else {
        showPopMessage(blMessage.message);
    }
    save();
}

function callback_deleteUserSubject(blMessage) {
    if (blMessage.success) {
        if (blMessage.message != null && blMessage.message != "") {
            showPopMessage(blMessage.message);
        } else {
            showPopMessage("取消订阅成功！");
        }
    } else {
        showPopMessage(blMessage.message);
    }
}
