/**
 * User: ZhangQiang
 * Date: 14-3-18
 * Time: 下午4:33
 * Description: 首页的js
 */

/**
 * 为目录增加滑动效果
 */
function addSlideEffects() {
    $(".menuItem").each(function () {
        var $topMenu = $(this).closest(".topMenu");
        //一级菜单切换背景图片
        $(this).hover(function () {
            $(this).find(".normal").hide();
            $(this).find(".selected").show();

            $topMenu.siblings().find(".selected").hide();
            $topMenu.siblings().find(".normal").show();
        });

        //一级菜单点击效果
        $(this).click(function () {

            var $subMenu = $topMenu.find(".subMenu");
            if ($subMenu.length > 0) {
                // 如果有二级菜单则展开二级菜单
                $subMenu.slideDown();
                $topMenu.siblings().find(".subMenu").slideUp();
                return false;
            } else {
                //如果没有二级菜单则加上选中效果
                $(this).addClass("active");
            }
        });
    });
}

$(document).ready(function () {
    addSlideEffects();
});