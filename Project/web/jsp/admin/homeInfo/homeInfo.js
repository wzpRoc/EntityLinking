/**
 * 浏览文件触发的事件
 */
function uploadSmallImage() {
//    alert(1);
    $("#showImage_small").html("<img src='../img/loading.gif'>");
    <!--用于提交图片的表单 -->
//    t = setTimeout("submitForm(false)", 200);
    t = setTimeout("submitForm(false)", 200);
}

function uploadBigImage() {
    $("#showImage_big").html("<img src='../img/loading.gif'>");
    <!--用于提交图片的表单 -->
    t = setTimeout("submitForm(true)", 200);
}

function submitForm(isBigPic) {
    var options = {
        url: path + "/uploadFile.action",
        type: "POST",
        dataType: "text",
        success: function (msg) {
            var msg = msg.trim();
            var msgStruct = JSON.parse(msg);
            if (msgStruct.success) {
                var data = msgStruct.data.split("#");
//                $("#tipDiv").html(data[0]);
                if(isBigPic) {
                    $("#showImage_big").html("<img src=" + path + "/" + data[1] + ">");
                    $("#pic_big").val(data[0]);

                }else {
                    $("#showImage_small").html("<img src=" + path + "/" + data[1] + ">");
                    $("#pic_small").val(data[0]);
                }
            } else {
//                $("#tipDiv").html("<font color='red'>"+msgStruct.message+"</font>");
            }
        }
    };

    if(isBigPic)
        $("#pic_big_form").ajaxSubmit(options);
    else
        $("#pic_small_form").ajaxSubmit(options);
    return false;
}