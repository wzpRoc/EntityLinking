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

function rebuildAlbumIndex() {
    if(!confirm("确定需要重建索引吗？")) {
        return;
    }
    AjaxInterface.rebuildAlbumIndex(
        callback_rebuildIndex
    );

    showPopMessage("正在处理……");
}

function rebuildArtistIndex() {
    if(!confirm("确定需要重建索引吗？")) {
        return;
    }
    AjaxInterface.rebuildArtistIndex(
        callback_rebuildIndex
    );

    showPopMessage("正在处理……");
}
