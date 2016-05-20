/**
 * 添加评论
 * @return {boolean}
 */
function loadComment() {
    var content = $('#textarea_content').val();
    var albumId = getOnlyElementByName("dto.id").value;
    if(content == "") {
        alert("评论不能为空！");
        return false;
    }else {
        AjaxInterface.addAlbumComment(albumId, content, addComment_callback);
        return true;
    }
}

/**
 * 评论专辑的回调函数
 * @param blMessage
 */
function addComment_callback(blMessage) {
    if(blMessage.success) {
        // 成功
        alert(blMessage.message);
        $('#comment').append("<div class='item'><a class='avatar'><img src="+userPhotoUrl+" alt=''></a><h5><a>"+userName+"</a>：</h5><div class='msg_txt'>"+blMessage.data.content+"</div><p>发布时间："+blMessage.data.opTime+"</p> </div>");

    } else {
        // 失败
        alert(blMessage.message);
    }

}

/**
 * 收藏专辑
 */
function favorAlbum() {
    var albumId = getOnlyElementByName("dto.id").value;
    AjaxInterface.favorAlbum(albumId, albumFavorCall_back);
}

/**
 * 收藏专辑的回调函数
 * @param blMessage
 */
function albumFavorCall_back(blMessage) {
    alert(blMessage.message);
}