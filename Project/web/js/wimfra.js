/**
 * 典型的信息管理系统涉及的JS
 * 开发人：鲁廷明
 * 开发日期：2011-12-30
 */

/**
 * 点击表头中的复选框，下面的复选框跟着改变
 */
function on_checkbox_id_head_clicked(checkbox_id_head) {
    var checkboxList = document.getElementsByName("checkbox_id");

    var checked = checkbox_id_head.checked;
    for (var i = 0; i < checkboxList.length; i++) {
        var checkbox = checkboxList[i];
        checkbox.checked = checked;
    }
}

function go(url) {
    window.location.href(url);
}

/**
 * 点击按钮，进入详细页
 * 只能选择一条记录，将ID放入URL中
 */
function on_clickButtonToDetail(urlPattern) {
    var checkboxList = document.getElementsByName("checkbox_id");
    var ids = "";
    var cnt = 0;

    for (var i = 0; i < checkboxList.length; i++) {
        var checkbox = checkboxList[i];
        if (checkbox.checked) {
            cnt++;
            if (cnt == 1) {
                ids = checkbox.value;
            } else {
                ids += "," + checkbox.value;
            }
        }
    }


    if (cnt != 1) {
        alert("请选择一条记录");
    } else {
        var url = urlPattern.replace("%id%", ids);
        window.location = url;
    }
}


/**
 * 只能选择一条记录
 */
function getSelectedOneRecordId() {
    var checkboxList = document.getElementsByName("checkbox_id");
    var ids = "";
    var cnt = 0;

    for (var i = 0; i < checkboxList.length; i++) {
        var checkbox = checkboxList[i];
        if (checkbox.checked) {
            cnt++;
            if (cnt == 1) {
                ids = checkbox.value;
            } else {
                ids += "," + checkbox.value;
            }
        }
    }

    if (cnt != 1) {
        alert("请选择一条记录");
        return null;
    } else {
        return ids;
    }
}


/**
 * 点击按钮，进入详细页
 * 可以选择一条或多条记录，将ID放入URL中
 */
function on_clickButtonToSubmit(btn, urlPattern) {
    var checkboxList = document.getElementsByName("checkbox_id");
    var ids = "";
    var cnt = 0;

    for (var i = 0; i < checkboxList.length; i++) {
        var checkbox = checkboxList[i];
        if (checkbox.checked) {
            cnt++;
            if (cnt == 1) {
                ids = checkbox.value;
            } else {
                ids += "," + checkbox.value;
            }
        }
    }


    if (cnt == 0) {
        alert("请选择记录");
    } else {
        // 得到表单
        var form = $(btn).closest('form')[0];
        var url = urlPattern.replace("%ids%", ids);
        form.action = url;
        form.submit();
    }
}


/**
 * 点击按钮，提交
 */
function on_submit(url) {
    // 得到表单
    var form = document.getElementsByName("form1")[0];
    form.action = url;
    form.submit();
}

/**
 * 验证页码是否正确
 * @param pageNo
 * @return
 */
function validateNum(pageNo) {
    var reg = new RegExp("^[1-9][0-9]*");
    //测试pageNo是不是数字
    var isInt = reg.test(pageNo);
    if (!isInt || pageNo <= 0) {
        pageNo = 20;
    }
    return pageNo;
}

/**
 * 翻页函数
 * @param pageNo
 */
function gotoPage(pageNo) {
    var currentPageNoObj = document.getElementsByName("page.currentPageNo")[0];
    if (currentPageNoObj.value == pageNo) {
        return;
    }

    currentPageNoObj.value = pageNo;
    if (document.form) {
        document.form.submit();
    } else if (document.getElementById("mainForm")) {
        document.getElementById("mainForm").submit();
    } else {
        document.getElementById("form1").submit();
    }
}


/**
 * 设置参数后提交
 * @param parameterList
 */
function setParametersAndSubmit(parameterList) {
    for (var i = 0; i < parameterList.length; i++) {
        var parameter = parameterList[i];
        // document.getElementsByName(parameter[0])[0].value = parameter[1];
        document.mainForm[parameter[0]].value = parameter[1];
    }

    document.mainForm.submit();
}


/**
 * 设置排序字段后提交
 */
function setOrderByAndSubmit(orderBy) {
    if (document.mainForm["page.orderBy"].value == orderBy) {
        // 已经是按这个字段排序，那么变换排序方式
        if (document.mainForm["page.order"].value == "DESC") {
            document.mainForm["page.order"].value = "ASC";
        } else {
            document.mainForm["page.order"].value = "DESC";
        }
    } else {
        document.mainForm["page.orderBy"].value = orderBy;
        document.mainForm["page.order"].value = "ASC";
    }

    document.mainForm.submit();
}


/**
 * 从map删除键
 * @param key
 * @param map
 */
function removeKeyFromMap(key, map) {
    delete map[key];
}


/**
 得到map的键列表
 */
function keys(map) {
    var list = new Array();

    for (var key in map) {
        list.push(key);
    }

    return list;
}


/**
 得到map的值列表
 */
function values(map) {
    var list = new Array();

    for (var key in map) {
        list.push(map[key]);
    }

    return list;
}


/**
 * 根据ID得到对象
 * @param id
 */
function getElementById(id) {
    obj = document.getElementById(id);
    return obj;
}


/**
 * 根据ID得到对象，如果为空，那么alert
 * @param id
 */
function getElementById_alertIfNull(id) {
    obj = getElementById(id);
    if (obj == null) {
        alert("obj = null, id=" + id);
    }
    return obj;
}


function getOnlyElementByTagNameAndAttribute(tagName, attributeName, attributeValue) {
    return getOnlyElementByTagNameAndAttributes(tagName, {attributeName: attributeValue});
}

function getOnlyElementByTagNameAndAttributes(tagName, attributesMap) {
    var list = getElementsByTagNameAndAttributes(tagName, attributesMap);
    if (list) {
        if (list.length == 0) {
            return null;
        } else if (list.length == 1) {
            return list[0];
        } else {
            alert("找到满足条件的元素有多个(" + list.length + ")\n" +
                "tagName=" + tagName + "\n" +
                "attributesMap=" + objectToString(attributesMap)
            );
        }
        return null;
    } else {
        return null;
    }
}


function getOnlyElementByIdOrName(idOrName) {
    var obj = getElementById(idOrName);
    if (!obj) {
        obj = getOnlyElementByName(idOrName);
    }
    return obj;
}


function getOnlyElementByName(name) {
    var list = document.getElementsByName(name);
    if (list) {
        if (list.length == 0) {
            return null;
        } else if (list.length == 1) {
            return list[0];
        } else {
            alert("找到满足条件的元素有多个(" + list.length + ")\n" +
                "name=" + name
            );
        }
        return null;
    } else {
        return null;
    }
}

function getElementsByTagNameAndAttribute(tagName, filterAttributeName, filterAttributeValue) {
    var list = document.getElementsByTagName(tagName);
    var resultList = new Array();
    if (list) {
        for (var i = 0; i < list.length; i++) {
            var ele = list[i];
            var eleAttribute = ele.attributes[filterAttributeName];
            if (eleAttribute && eleAttribute.value == filterAttributeValue) {
                resultList.push(ele);
            }
        }
    }

    return resultList;
}

function getElementsByTagNameAndAttributes(tagName, filterAttributesMap) {
    var list = document.getElementsByTagName(tagName);
    var resultList = new Array();
    if (list) {
        for (var i = 0; i < list.length; i++) {
            var ele = list[i];
            var attributesMatchable = true;
            for (var attributeName in filterAttributesMap) {
                var eleAttribute = ele.attributes[attributeName];
                if (eleAttribute) {
                    var eleAttributeValue = eleAttribute.value;
                    var filterAttributeValue = filterAttributesMap[attributeName];
                    if (eleAttributeValue != filterAttributeValue) {
                        attributesMatchable = false;
                        break;
                    }
                } else {
                    attributesMatchable = false;
                    break;
                }
            }
            if (attributesMatchable) {
                resultList.push(ele);
            }
        }
    }

    return resultList;
}


function setSelectByValue(ctrl_select, value) {
    for (var i = 0; i < ctrl_select.options.length; i++) {
        var option = ctrl_select.options[i];
        if (option.value == value) {
            ctrl_select.selectedIndex = i;
            return;
        }
    }
}


function objectToString(obj) {
    var s = "";
    for (var key in obj) {
        if (s != "") {
            s += ", ";
        }
        s += key + ": " + obj[key];
    }

    return s;
}


function size(obj) {
    var size = 0;
    for (var key in obj) {
        size++;
    }

    return size;
}


/**
 * 得到选中的复选框，值包括
 * cnt: 数量
 * values: 用逗号隔开的字符串
 * valueList: 值的列表
 * checkboxList: 复选框的列表
 */
function getSelectedCheckboxListInfo() {
    var allCheckboxList = document.getElementsByName("checkbox_id");
    var checkboxList = new Array();
    var cnt = 0;
    var values = "";
    var valueList = new Array();

    for (var i = 0; i < allCheckboxList.length; i++) {
        var checkbox = allCheckboxList[i];
        if (checkbox.checked) {
            checkboxList.push(checkbox);
            valueList.push(checkbox.value);
            cnt++;
            if (cnt == 1) {
                values = checkbox.value;
            } else {
                values += "," + checkbox.value;
            }
        }
    }
    return {
        cnt: cnt,
        values: values,
        valueList: valueList,
        checkboxList: checkboxList
    };
}


function deleteFromList() {
    var __ret = getSelectedCheckboxListInfo();
    var ids = __ret.values;
    var cnt = __ret.cnt;

    if (cnt == 0) {
        alert("请选择记录");
    } else {
        if (!confirm("确认删除选中的记录吗？")) {
            return;
        }
        var form = document.getElementById("mainForm");

        //设置tempDowhat
        var ctrl_tempDowhat = getOnlyElementByName("tempDowhat");
        if(ctrl_tempDowhat){
            ctrl_tempDowhat.value = "deleteFromList";
        }else{
            $(form).append('<input id="tempDowhat" type="hidden" value="deleteFromList" name="tempDowhat">');
        }

        // 设置ids
        var ctrl_ids = getOnlyElementByName("ids");
        if(ctrl_ids){
            ctrl_ids.value = ids;
        }else{
            $(form).append('<input id="ids" type="hidden" value="" name="ids">');
            $("#ids").val(ids);
        }

        // 提价表单
        form.submit();
    }

}


function closePopMessage() {
    document.getElementById("div_popMessage").style.display = "none";

    //如果有遮罩层 则去掉遮罩层
    if (document.getElementById("maskLayer")) {
        $("#maskLayer").fadeOut();
    }

}


function showPopMessage(contentHtml) {
    document.getElementById("td_content").innerHTML = contentHtml;
    document.getElementById("div_popMessage").style.display = "";
}


/**
 * 从字符串创建日期
 */
function createDate(YYYYMMDD) {
    YYYYMMDD = YYYYMMDD.replace("-", "");
    var year = parseInt(YYYYMMDD.substring(0, 4));
    var month = parseInt(YYYYMMDD.substring(4, 6));
    var day = parseInt(YYYYMMDD.substring(6, 8));

    return new Date(year, month, day);
}


/**
 * 由两个日期字符串计算相差天数
 */
function getDays(dateBeginStr, dateEndStr) {
    var dateBegin = createDate(dateBeginStr);
    var dateEnd = createDate(dateEndStr);
    // 将毫秒转换为天
    return (dateEnd.getTime() - dateBegin.getTime()) / (24 * 3600 * 1000);

}

/**
 * 增加类似记录
 */
function addSimilar() {
    var f = document.getElementById("mainForm");
    document.getElementsByName("dowhat")[0].value = "add";
    document.getElementsByName("saveTag")[0].value = "";
    document.getElementsByName("dto.id")[0].value = "0";
    f.submit();
}


/**
 * 清除字段值
 */
function clearFieldValue(name) {
    var objects = document.getElementsByName(name);
    if (objects) {
        for (var i = 0; i < objects.length; i++) {
            var obj = objects[i];
            obj.value = "";

            var evt = document.createEvent('HTMLEvents');
            evt.initEvent('change', true, true);
            obj.dispatchEvent(evt);
        }
    }
}


/**
 * 从详细页返回到列表页
 */
function returnToList() {
    var s = moduleName + "List?enableSession=1";
    if (dowhatInList != "") {
        s += "&dowhat=" + dowhatInList;
    }
    window.location = s;
}
