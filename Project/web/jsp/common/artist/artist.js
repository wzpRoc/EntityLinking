/**
 * 添加评论
 * @return {boolean}
 */
function loadComment() {
    var content = $('#textarea_content').val();
    var artistId = getOnlyElementByName("dto.id").value;
    if(content == "") {
        alert("评论不能为空！");
        return false;
    }else {
        AjaxInterface.addArtistComment(artistId, content, addComment_callback);
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
function favorArtist() {
    var artistId = getOnlyElementByName("dto.id").value;
    AjaxInterface.favorArtist(artistId, artistFavorCall_back);
}

/**
 * 收藏专辑的回调函数
 * @param blMessage
 */
function artistFavorCall_back(blMessage) {
    alert(blMessage.message);
}