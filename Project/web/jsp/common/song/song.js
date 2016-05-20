/**
 * 为歌曲评分
 * @param domId
 * @param obj
 */
function doRating(domId,obj) {
    var rating_num=obj.getAttribute("title");
    for(var i=1;i<=5;i++)
    {
        if(i<=rating_num)
        {
            $("#"+domId+" .ratings_star[title="+i+"]").addClass("ratings_star_on");
            $("#"+domId+" .ratings_star[title="+i+"]").removeClass("ratings_star_off");
        }
        else
        {
            $("#"+domId+" .ratings_star[title="+i+"]").addClass("ratings_star_off");
            $("#"+domId+" .ratings_star[title="+i+"]").removeClass("ratings_star_on");
        }
    }
    $("#rating").val(rating_num);
    var songId = getOnlyElementByName("dto.id").value;
    var albumId = getOnlyElementByName("dto.albumId").value;
    AjaxInterface.rateForSong(songId, albumId, rating_num, rateForSong_callback);
}

/**
 * 评分的回调函数
 * @param blMessage
 */
function rateForSong_callback(blMessage) {
    if(blMessage.success) {
        // 成功
        alert(blMessage.message);

    } else {
        // 失败
        alert(blMessage.message);
    }

}

/**
 * 添加评论
 * @return {boolean}
 */
function loadComment() {
    var content = $('#textarea_content').val();
    var songId = getOnlyElementByName("dto.id").value;
    var albumId = getOnlyElementByName("dto.albumId").value;
    if(content == "") {
        alert("评论不能为空！");
        return false;
    }else {
        AjaxInterface.addSongComment(songId, albumId, content, addComment_callback);
        return true;
    }
}

/**
 * 评论歌曲的回调函数
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
 * 收藏歌曲
 */
function favorSong() {
    var songId = getOnlyElementByName("dto.id").value;
    var albumId = getOnlyElementByName("dto.albumId").value;
    AjaxInterface.favorSong(songId, albumId, songFavorCall_back);
}

/**
 * 收藏歌曲的回调函数
 * @param blMessage
 */
function songFavorCall_back(blMessage) {
    alert(blMessage.message);
}

// 要用ajaxForm了
function doLoadCommentFunc(){
    if($('#form_comment').size()==0){
        return;
    }

    var options = {
        beforeSubmit : function(formData){	// 提交之前
            // 检查评论内容是否为空
            var content = formData[1].value;
            if(content == ""){
                alert('评论内容不能为空！');
                return false;
            }

            return true;
        },
        success : function(result){
            // 将列表重置
            $('#commentContent').val("");
            $('#commentList').html(result);
        },
        error : function(){
            alert("添加评论失败！当前网络处于离线状态，请等待网络恢复后再执行该操作！");
        }
    };
    // 绑定Ajax事件
    $('#form_comment').ajaxForm(options);

    // 添加推荐的AjaxForm选项
    var options1 = {
        beforeSubmit : function(formData){	// 提交之前
            // 检查评论内容是否为空
            var content = formData[1].value;
            if(content == ""){
                alert('评论内容不能为空！');
                return false;
            }

            return true;
        },
        success : function(result){
            // 将列表重置
            $('#recommendContent').val("");
            $('#recommendList').html(result);
        },
        error : function(){
            alert("添加评论失败！当前网络处于离线状态，请等待网络恢复后再执行该操作！");
        }
    };
    // 绑定Ajax事件
    $('#form_recommend').ajaxForm(options1);

    // 推荐
    $('#s_user').chosen();
}
