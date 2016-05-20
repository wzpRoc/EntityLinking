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
	//关闭按钮事件
    $("#dialog_close").click(function(){
        $("#defintionTypeMaskForMessage").fadeOut();
    });

}

function save() {
    document.getElementById("form0").submit();
}


function myConfirm() {
    getOnlyElementByName("subject.confirmed").value = 1;
    document.getElementById("form0").submit();
}

function confirmOnline(smPostDocId, userId){
    var title = getElementById("titleForChange").value;
    var abst = getElementById("abstForChange").value;

    AjaxInterface.ConfirmSendMessageOnline(smPostDocId, userId, title, abst, callback_ConfirmSendMessageOnline);
    getElementById("defintionTypeMaskForMessage").style.display = "none";
}

function callback_ConfirmSendMessageOnline(blMessage) {
    if(blMessage.message != null)
        showPopMessage(blMessage.message);
    save();
}

var smPostDocId;
function showEditMessageBox(id) {
    $("#defintionTypeMaskForMessage").show();
    smPostDocId=id;
    AjaxInterface.getUsersOfSendingMessage(smPostDocId, callback_functionForMessage);
}

function callback_functionForMessage(blMessage) {
    var users = null;

    if(blMessage.success) {
        users = blMessage.data;
    }
    else{
        users = blMessage.message;
    }
    if(users == null)
        users = "";

    AjaxInterface.getSMPostDoc(smPostDocId, function(blMessage){
    	
    	if(blMessage.success){
    		var smPostDoc = blMessage.data;
    		
    		var title = smPostDoc.title;
    		var abst = smPostDoc.abst;
    		var confirmStatus = smPostDoc.confirmStatus;

    		var htmlText="<div style='position: relative; left:5%'><div align='left' > 标题：<input id='titleForChange' align='center' style='width:80%' value="+title+"> </div>" +
    				"<div align='left'><span style='float:left'>摘要：</span><textarea id='abstForChange' align='center' rows=6 style='width:80%;' >"+abst+"</textarea></div><br />" +
                    "<div align='left'><span style='float:left'>用户：</span><textarea id='usersForMessage' align='center' rows=10 style='width:80%;' >"+users+"</textarea></div></div>";

            if(confirmStatus == 0){
                htmlText +=  "<br/> <button type='button' onclick='confirmOnline("+smPostDocId+","+userId+")'> 确认发送</button>";
            }


    		$("#dialog_content_text").html("");
    		$("#dialog_content_text").append(htmlText);
    	}else{
    		alert(blMessage.message);
    	}
    });
    
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
