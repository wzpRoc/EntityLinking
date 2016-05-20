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
    //替换导航栏
    $("#questionMenuNav").empty().append(name);
    $("#questions").empty();
    $("#addQuestion").empty();

    //1  根据一级菜单的id查询出对应的二级菜单
    AjaxInterface.fetchQuestionSecondMenus(id, function (blMessage) {
        if (blMessage.success) {
            //2   清除所有的二级菜单 加载新的二级菜单
            $(".secondMenu").remove();
            $(".addSecondMenu").remove();
            var $secondMenu = $(obj).children(".qMenu2");
            var secondMenus = blMessage.data;
            if (secondMenus.length > 0) {
                for (var i = 0; i < secondMenus.length; i++) {
                    var questionSecondMenu = secondMenus[i];
                    $secondMenu.append('<li class="secondMenu" onclick="showQuestions(' + questionSecondMenu.id + '); return false;">' +
                        '<a id="' + questionSecondMenu.id + '">' + questionSecondMenu.name + '</a>' +
                        '<span class="deleteMenu"><img onclick="deleteSecondQuestionMenu(' + questionSecondMenu.id + ', this); return false;" src="../img/delete.png"></span>' +
                        '</li>');
                }
            }
            $secondMenu.after('<div class="addSecondMenu" onclick="addSecondQuestionMenu(this);"><a style="font-style:oblique">增加二级菜单</a></div>');
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
    $("#catalogId").val(catalogId);

    $("#questions").empty();
    $("#addQuestion").remove();
    //1 根据问题的类别获取问题集
    AjaxInterface.fetchQuestions(catalogId, function (blMessage) {
        if (blMessage.success) {
            //2 将问题追加到相应的区域
            var questionList = blMessage.data;
            if (questionList.length > 0) {
                for (var i = 0; i < questionList.length; i++) {
                    var question = questionList[i];
                    $("#questions").append('<div class="questions">' +
                        '<a class="question" onclick="toggleAnswer(this); return false;">' + question.question + '</a>' +
                        '<span class="deleteQuestion" onclick="deleteQuestion(this, ' + question.id + ')">删除此问题</span>' +
                        '<li class="answer oldAnswer" onclick="editAnswer(this)">' + question.answer + '</li>' +
                        '<div class="editAnswer">' +
                        '<textarea class="newAnswer">' + question.answer + '</textarea>' +
                        '<button onclick="updateAnswer(this, ' + question.id + '); return false;">保存</button>' +
                        '</div>' +
                        '</div>');
                }
            }
            // 新增问题
            $("#questions").after('<div id="addQuestion">' +
                '<textarea class="newQuestionArea" rows="3"></textarea> ' +
                '<button id="addQuestionBtn" onclick="addQuestion(this); return false;" >新增问题</button>' +
                '</div>');
            $("#addQuestion > .newQuestionArea").hide();
        } else {
            alert(blMessage.message);
        }
    });
}

/**
 * 增加新的常见问题
 * @return {boolean}
 */
function addQuestion(obj) {
    // 1获取对应的目录Id
    var catalogId = $("#catalogId").val();
    if (catalogId == null || catalogId == "") {
        alert("获取目录的id出错");
        return false;
    }

    var $newQuestionArea = $(obj).siblings(".newQuestionArea");
    if ($newQuestionArea.is(":hidden")) {
        $newQuestionArea.slideDown("fast");
        $(obj).text("提交问题");
        return false;
    }
    // 2获取问题的内容
    var questionContent = $newQuestionArea.val();
    if (questionContent == null || questionContent.trim() == "") {
        alert("请您先输入问题的内容");
        return false;
    }

    // 3 将问题插入数据库 刷新界面
    AjaxInterface.addQuestion(catalogId, questionContent, function (blMessage) {
        if (blMessage.success) {
            // 将问题追加页面
            var question = blMessage.data;
            appendQuestionToObj($("#questions"), question);
            $newQuestionArea.val("");
        } else {
            alert(blMessage.message);
        }
    });
}

/**
 * 将问题追加到节点$obj后面
 * @param obj
 * @param question
 */
function appendQuestionToObj($obj, question) {
    $obj.append('<div class="questions">' +
        '<a class="question" onclick="toggleAnswer(this); return false;">' + question.question + '</a>' +
        '<span class="deleteQuestion" onclick="deleteQuestion(this, ' + question.id + ')">删除此问题</span>' +
        '<li class="answer oldAnswer" onclick="editAnswer(this)">' + question.answer + '</li>' +
        '<div class="editAnswer">' +
        '<textarea class="newAnswer">' + question.answer + '</textarea>' +
        '<button onclick="updateAnswer(this, ' + question.id + '); return false;">保存</button>' +
        '</div>' +
        '</div>');
}

/**
 * 显示或展开编辑答案的窗口
 * @param obj
 */
function editAnswer(obj) {
    $(obj).siblings(".editAnswer").slideToggle();
}

function updateAnswer(obj, questionId) {
    if (questionId == null || questionId == "") {
        alert("抱歉，获取问题的Id出错");
        return false;
    }

    var newAnswer = $(obj).siblings(".newAnswer").val();
    AjaxInterface.updateAnswer(questionId, newAnswer, function (blMessage) {
        if (blMessage.success) {
            $(obj).closest(".editAnswer").siblings(".oldAnswer").empty().html(newAnswer);
        } else {
            alert(blMessage.message);
        }
    });
    return false;

}

/**
 * 删除问题
 * @param obj
 * @param questionId
 */
function deleteQuestion(obj, questionId) {
    if (questionId == null || questionId == "") {
        alert("获取问题Id出错");
        return false;
    }
    if (!confirm("您确定删除此问题吗？")) {
        return false;
    }

    AjaxInterface.deleteQuestion(questionId, function (blMessage) {
        if (blMessage.success) {
            $(obj).closest(".questions").remove();
        } else {
            alert(blMessage.message);
        }
    })
}

/**
 * 问题答案以藏与展现
 * @param obj
 */
function toggleAnswer(obj) {
    $(obj).siblings(".answer").slideToggle();
    $(obj).siblings(".editAnswer").hide();
}

/**
 * 删除一级目录
 * @param catalogId
 * @param obj
 * @return {boolean}
 */
function deleteQuestionMenu(catalogId, obj) {
    if (!confirm("删除此一级目录时，其所有二级子目录也会全部删除,你确定删除吗？")) {
        return false;
    }
    //1 删除数据中对应的记录以及其孩子节点
    AjaxInterface.deleteMenu(catalogId, function (blMessage) {
        if (blMessage.success) {
            //2 刷新界面
            $(obj).closest("li").remove();
            $("#questionMenuNav").empty().append("问题分类");
            $("#questions").empty();
        } else {
            alert(blMessage.message);
        }
    });
}

function deleteSecondQuestionMenu(catalogId, obj) {
    if (!confirm("删除此目录，目录下对应的所有问题也将全部删除，您确定删除吗？")) {
        return false;
    }
    //1 删除数据中对应的记录以及其孩子节点
    AjaxInterface.deleteSecondMenu(catalogId, function (blMessage) {
        if (blMessage.success) {
            //2 刷新界面
            $(obj).closest("li").remove();
            $("#questions").empty();
        } else {
            alert(blMessage.message);
        }
    });
}

/**
 * 增加一级菜单
 * @param obj
 */
function addQuestionMenu(obj) {
    $('<li>' +
        '<input class="name">' +
        '<a style="font-style:oblique"  onclick="saveQuestionMenu(this); return false;">保存</a>' +
        '</li>').insertBefore($(obj));
}

/**
 * 保存新增的菜单
 * @param obj
 */
function saveQuestionMenu(obj) {
    var menuName = $(obj).siblings(".name").val();
    if (menuName == null || menuName == "") {
        alert("请先输入菜单名称，然后保存");
    }
    //1 保存到数据库
    AjaxInterface.saveQuestionMenu(menuName, 1, 0, function (blMessage) {
        if (blMessage.success) {
            var questionSecondMenu = blMessage.data;
            // 2 将新生成的菜单追加菜单栏中
            var $addMenuLi = $(obj).closest("li");
            $('<li class="firstMenu" onclick="showQuestionSecondMenu(' + questionSecondMenu.id + ' , ' + questionSecondMenu.name + ',this); return false;">' +
                '<a id=' + questionSecondMenu.id + '>' + questionSecondMenu.name + ' </a>' +
                '<span class="deleteMenu"><img src="../img/delete.png" onclick="deleteQuestionMenu(' + questionSecondMenu.id + ', this); return false;"></span>' +
                '<ul class="qMenu2"></ul>' +
                '</li>').insertBefore($addMenuLi);
            $addMenuLi.remove();
        } else {
            alert(blMessage.message);
        }
    });
}

/**
 * 新增二级目录
 * @param obj
 */
function addSecondQuestionMenu(obj) {
    $('<div class="saveSecondMenu">' +
        '<input class="name">' +
        '<a onclick="saveSecondQuestionMenu(this); return false;">保存</a>' +
        '</div>').insertBefore($(obj));
}

function saveSecondQuestionMenu(obj) {
    var menuName = $(obj).siblings(".name").val();
    if (menuName == null || menuName == "") {
        alert("请先输入菜单名称，然后保存");
        return false;
    }
    var parentId = $(obj).closest(".saveSecondMenu").siblings("a").attr("id");
    if (parentId == null || parentId == "") {
        alert("获取父节点的id出错");
        return false;
    } else {
        parentId = parseInt(parentId);
    }

    //1 保存到数据库
    AjaxInterface.saveQuestionMenu(menuName, 2, parentId, function (blMessage) {
        if (blMessage.success) {
            var secondMenu = blMessage.data;
            // 2 将新生成的菜单追加菜单栏中
            var $secondMenuUl = $(obj).closest(".saveSecondMenu").siblings(".qMenu2");
            $secondMenuUl.append($('<li class="secondMenu" onclick="showQuestions(' + secondMenu.id + '); return false;">' +
                '<a id="' + secondMenu.id + '">' + secondMenu.name + '</a>' +
                '<span class="deleteMenu"><img src="../img/delete.png" onclick="deleteSecondQuestionMenu(' + secondMenu.id + ', this); return false;"></span>' +
                ' </li>'));
            $(obj).closest(".saveSecondMenu").remove();
        } else {
            alert(blMessage.message);
        }
    });
}



