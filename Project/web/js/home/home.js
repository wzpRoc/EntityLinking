/**
 * 图片幻灯片动画
 */
function addSlideShow() {
//    var firstId = $(".dataPicture:first").find("img").attr('id');
//    var lastId = $(".dataPicture:last").find("img").attr('id');
//    var firstPicSrc = $(".dataPicture:first").find("img").attr('src');
//    var lastPicSrc = $(".dataPicture:last").find("img").attr('src');

    //1 点击切换图片
    $(".dataPicture").bind("click", function () {
//        var img = $(this).find('img').attr('src');
        var id = $(this).find('img').attr('id');
        $(".choosePicture").removeClass("choosePicture");
        $(this).addClass("choosePicture");
        $("#dataDisplay").find('img').attr('src', picList[id]);
    });

    //2 上一张
    $("#moveleft").click(function () {
        var $current = $(".choosePicture");
        $current.removeClass("choosePicture");

        var $prevPic = $current.prev();
        if ($prevPic.length == 0) {
            $prevPic = $(".dataPicture:last");
        }
        $prevPic.addClass("choosePicture");
//        var img = $prevPic.find('img').attr('src');
        var id =  $prevPic.find('img').attr('id');
        $("#dataDisplay").find('img').attr('src', picList[id]);
    });

    //3 下一张
    $("#moveright").click(function () {
        var $current = $(".choosePicture");
        $current.removeClass("choosePicture");

        var $nextPic = $current.next();
        if ($nextPic.length == 0) {
            $nextPic = $(".dataPicture:first");
        }
        $nextPic.addClass("choosePicture");
//        var img = $nextPic.find('img').attr('src');
        var id = $nextPic.find('img').attr('id');
        $("#dataDisplay").find('img').attr('src', picList[id]);
    });

}

$(document).ready(function () {
    //1 添加首页的图片幻灯片动画
    addSlideShow();
});

