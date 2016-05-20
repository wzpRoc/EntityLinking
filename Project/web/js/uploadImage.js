/*
 功能：上传图片
 作者：张强
 日期：20131230
 */

/**
 * 浏览文件触发的事件
 */
function uploadImage() {
    $("#showImage").html("<img src='../img/loading.gif'>");
    <!--用于提交图片的表单 -->
    t = setTimeout("submitForm()", 200);
}

function submitForm() {
    var options = {
        url: path + "/uploadFile.action",
        type: "POST",
        dataType: "text",
        success: function (msg) {
            var msg = msg.trim();
            var msgStruct = JSON.parse(msg);
            if (msgStruct.success) {
                var data = msgStruct.data.split("#");
                $("#tipDiv").html(data[0]);
                $("#showImage").html("<img src=" + path + "/" + data[1] + ">");
                $("#photoUrl").val(data[0]);
            } else {
                $("#tipDiv").html("<font color='red'>"+msgStruct.message+"</font>");
            }
        }
    };
    $("#picForm").ajaxSubmit(options);
    return false;
}

