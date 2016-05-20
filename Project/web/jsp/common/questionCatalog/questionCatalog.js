/**
 * 点击一级菜单显示二级菜单
 * @param id
 * @param obj
 */
function showQuestionSecondMenu(id, name, obj) {
    if (id == null || name == null) {
        alert("获取一级菜单的信息出错");
        return false;
    }
    $("#questionMenuNav").empty().append(name);

    //1  根据一级菜单的id查询出对应的二级菜单
    AjaxInterface.fetchQuestionSecondMenus(id, function (blMessage) {
        if (blMessage.success) {
            //2   清除所有的二级菜单 加载新的二级菜单
            $(".secondMenu").remove();
            var $secondMenu = $(obj).children(".qMenu2");
            var secondMenus = blMessage.data;
            if (secondMenus.length > 0) {
                for (var i = 0; i < secondMenus.length; i++) {
                    var questionSecondMenu = secondMenus[i];
                    $secondMenu.append('<li class="secondMenu" onclick="showQuestions(' + questionSecondMenu.id + '); return false;">' +
                        '<a id="' + questionSecondMenu.id + '">' + questionSecondMenu.name + '</a></li>');
                }
            }
        } else {
            alert(blMessage.message);
        }
    });
}

/**
 * 根据问题类别的id获取对应的问题
 * @param catalogId
 * @return {boolean}
 */
function showQuestions(catalogId) {
    if (catalogId == null) {
        alert("获取问题二级目录信息出错");
        return false;
    }
    //1 根据问题的类别获取问题集
    AjaxInterface.fetchQuestions(catalogId, function (blMessage) {
        if (blMessage.success) {
            $("#questions").empty();
            //2 将问题追加到相应的区域
            var questionList = blMessage.data;
            if (questionList.length > 0) {
                for (var i = 0; i < questionList.length; i++) {
                    var question = questionList[i];
                    $("#questions").append('<div class="questions">' +
                        '<a class="question" onclick="toggleAnswer(this); return false;">' + question.question + '</a>' +
                        '<li class="answer">' + question.answer + '</li>' +
                        '</div>');
                }
            }
        } else {
            alert(blMessage.message);
        }
    });
}

function toggleAnswer(obj) {
    $(obj).siblings(".answer").slideToggle();
}

