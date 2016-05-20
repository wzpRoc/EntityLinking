/**
 * 展示或隐藏标题下的内容
 * @param obj
 */
function toggleHideData(obj) {
    $(obj).siblings(".subData").slideToggle();
}


/**
 * 更新用户组中的人员
 * @return {boolean}
 */
function updateUserGroupUser() {
    var userGroupId = $("#userGroupId").val();
    var userIds = "";
    $("#userList").find("li input").each(function () {
        if ($(this).is(":checked")) {
            userIds = userIds + $(this).attr("id") + ",";
        }
    });
    if (userIds == "") {
        if (!confirm("您确认取消所有人员吗？")) {
            return false;
        }
    }
    AjaxInterface.updateUserGroupUser(userGroupId, userIds, function (blMessage) {
        if (blMessage.success) {
            // 1 修改用户组人员总数
            $("#userCount").val(blMessage.data);
            alert(blMessage.message);
            window.location.reload();
        } else {
            alert(blMessage.message);
        }
    });
    return false;
}

/**
 * 更新用户组所拥有的菜单
 */
function updateUserGroupMenu() {
    var userGroupId = $("#userGroupId").val();
    var menuIds = "";
    $("#userGroupMenuInfo").find("input[type='checkbox']").each(function () {
        if ($(this).is(":checked")) {
            menuIds = menuIds + $(this).attr("id") + ",";
        }
    });
    if (menuIds == "") {
        if (!confirm("您确认将取消所有菜单吗？")) {
            return false;
        }
    }
    AjaxInterface.updateUserGroupMenu(userGroupId, menuIds, function (blMessage) {
        if (blMessage.success) {
            alert(blMessage.message);
            window.location.reload();
        } else {
            alert(blMessage.message);
        }
    });
    return false;

}

/**
 * 三级菜单的点击事件
 */
function thirdMenuClick(obj) {
    if ($(obj).is(":checked")) {
        //处理父节点
        $(obj).closest("ul").siblings("div").find("input").prop("checked", true);  // 直接父节点
        $(obj).closest(".secondLevel").closest("ul").siblings("div").find("input").prop("checked", true);  // 祖先节点
    }
    else {
        //处理父节点 如果三级菜单全部取消则取消二级菜单
        if (!$(obj).closest("ul").find("input").is(":checked")) {
            $(obj).closest("ul").siblings("div").find("input").prop("checked", false);
        }
        // 如果二级菜单全部取消则取消一级菜单
        if (!$(obj).closest(".secondLevel").siblings().find("input").is(":checked")) {
            $(obj).closest(".secondLevel").closest("ul").siblings("div").find("input").prop("checked", false);
        }
    }
}

/**
 * 二级菜单的点击事件
 * @param obj
 */
function secondMenuClick(obj) {
    if ($(obj).is(":checked")) {
        // 1处理孩子节点
        $(obj).closest(".secondLevel").find("input").prop("checked", true);
        // 2 处理父节点
        $(obj).closest("ul").siblings(".firstLevelName").find("input").prop("checked", true);
    } else {
        // 1处理孩子节点
        $(obj).closest(".secondLevel").find("input").prop("checked", false);
        // 2 处理父节点
        if (!$(obj).closest("ul").find("input").is(":checked")) {
            $(obj).closest("ul").siblings("div").find("input").prop("checked", false);
        }
    }
}

/**
 * 一级菜单的点击事件
 * @param obj
 */
function firstMenuClick(obj) {
    if ($(obj).is(":checked")) {
        $(obj).closest(".firstLevel").find("input").prop("checked", true);
    } else {
        $(obj).closest(".firstLevel").find("input").prop("checked", false);
    }
}

$(document).ready(function () {
    var $dowhat = $("#dowhat").val();
    if ("add" == $dowhat) {
        $("#userGroupUserInfo").hide();
        $("#userGroupMenuInfo").hide();
    }
});




