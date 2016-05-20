function deleteDocByIds() {
    var docIds = getElementById("docIds").value;
    if(docIds == "") {
        alert("请填写文章ID！");
        return;
    }

    if(!confirm("删除后不可恢复，确认删除吗？")) {
        return;
    }

    var needResetImportStateAfterCancel = getElementById("cb_needResetImportStateAfterCancel").checked;

    AjaxInterface.deleteDocByIds(
        docIds,
        needResetImportStateAfterCancel,
        callback_common
    );

    showPopMessage("正在处理，可能需要花费较长时间，请耐心等待……");
}


function deleteDocByCondition() {
    var additionalCondition = getElementById("additionalCondition").value;
    if(additionalCondition == "") {
        alert("请填写附加条件！");
        return;
    }

    if(!confirm("删除后不可恢复，确认删除吗？")) {
        return;
    }

    var needResetImportStateAfterCancel = getElementById("cb_needResetImportStateAfterCancel").checked;

    AjaxInterface.deleteDocByCondition(
        additionalCondition,
        needResetImportStateAfterCancel,
        callback_common
    );

    showPopMessage("正在处理，可能需要花费较长时间，请耐心等待……");
}


function viewDoc() {
    var docIds = getElementById("docIds").value;
    if(docIds == "") {
        alert("请填写文章ID！");
        return;
    }

    var docIdArray = docIds.split(/[ ,]+/);
    for(var i=0; i<docIdArray.length; i++) {
        var docId = docIdArray[i];
        window.open(ctx+"/doc!detail?id=" + docId, "_blank");
    }
}


/**
 * 回调函数
 */
function callback_common(blMessage) {
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