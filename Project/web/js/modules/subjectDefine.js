var cnt_basicRule = 0;
var cnt_compositeRule = 0;
// id到规则的映射
var map_id_to_basicRule = new Object();
var map_id_to_compositeRule = new Object();
// id到规则扩展数据的映射
var map_id_to_ruleExtData = new Object();
// 顶层规则计数
var cnt_topRule = 0;


// 基本规则所占行数
var NR_ROWS_OF_BASIC_RULE = 3;

/**
 * 基本规则的构造函数
 */
function BasicRule(idInRuleSet, type, valueChar, valueInt, valueString) {
    this.idInRuleSet = idInRuleSet;
    this.type = type;
    this.valueChar = valueChar;
    this.valueInt = valueInt;
    this.valueString = valueString;
    this.checkBeforeSave = _checkBeforeSave;

    cnt_topRule++;
}


/**
 * 是否可保存
 */
function _checkBeforeSave() {
    if(this.type == undefined || this.type == "" || this.type == 0) {
        alert("请选择规则类别！");
        return false;
    }

    if(this.type == "languageId") {
        if(this.valueInt == undefined || this.valueInt == "" || this.valueInt == 0) {
            alert("请选择语种！");
            return false;
        }
    } else if(this.type == "entityTypeId") {
        if(this.valueInt == undefined || this.valueInt == "" || this.valueInt == 0) {
            alert("请选择实体！");
            return false;
        }
    } else if(this.type == "keyword") {
        if(this.valueString == undefined || this.valueString == "") {
            alert("请填写关键词！");
            return false;
        }
    }

    return true;
}


/**
 * 创建空的基本规则
 */
function createEmptyBasicRule(idInRuleSet) {
    return new BasicRule(idInRuleSet, "", " ", 0, "");
}


/**
 * 复合规则的构造函数
 */
function CompositeRule(idInRuleSet, operator, subRuleIdList) {
    this.idInRuleSet = idInRuleSet;
    this.operator = operator;
    this.subRuleIdList = subRuleIdList;

    cnt_topRule++;
    cnt_topRule-=subRuleIdList.length;
}


/**
 * 与
 */
function andRules() {
    combin('and');
}


/**
 * 或
 */
function orRules() {
    combin('or');
}


/**
 * 增加基本规则
 */
function addBasicRule() {
    // 分配ID
    var idInRuleSet = "br_" + cnt_basicRule;

    // 加入规则库
    map_id_to_basicRule[idInRuleSet] = createEmptyBasicRule(idInRuleSet);

    // 计算行数
    var rowIdxStart = cnt_basicRule * NR_ROWS_OF_BASIC_RULE + 1;

    // 规则id写入checkbox
    var checkbox = getBasicRuleCheckbox(rowIdxStart);
    checkbox.attributes["ruleIdInRuleSet"].value = idInRuleSet;

    // 规则id写入基本规则类别下拉框
    var select_basicRuleType= getSelect_basicRuleType(rowIdxStart);
    select_basicRuleType.attributes["ruleIdInRuleSet"].value = idInRuleSet;

    // 显示隐藏的表格行
    for (var rowIdx = rowIdxStart; rowIdx < rowIdxStart + NR_ROWS_OF_BASIC_RULE; rowIdx++) {
        var tr = getElementById("tr_" + rowIdx);
        tr.style.display = "";
        // tr.attributes["ruleIdInRuleSet"].value = ruleIdInRuleSet;
    }

    // 计数增加
    cnt_basicRule++;
}


/**
 * 删除多条规则
 */
function deleteRules() {
    // 找到所有的checkbox
    var inputList = document.getElementsByTagName("input");
    var selectedCheckboxList = new Array();

    // 找到选中的checkbox
    for (var i = 0; i < inputList.length; i++) {
        var input = inputList[i];
        if (input.type == "checkbox" && input.checked) {
            selectedCheckboxList[selectedCheckboxList.length] = input;
        }
    }

    // 检查数量
    if (selectedCheckboxList.length == 0) {
        alert("请选择一条或多条规则");
    }

    // 逐个删除
    for(var i = 0; i<selectedCheckboxList.length; i++) {
        var checkbox = selectedCheckboxList[i];
        deleteRule(checkbox);
    }
}


/**
 * 删除多条规则
 */
function deleteRule(checkbox) {
    var isBasicRule = checkbox.attributes["isBasicRule"];

    if(isBasicRule) {
        deleteBasicRule(checkbox);
    } else {
        deleteCompositeRule(checkbox);
    }
}


/**
 * 删除基本规则
 */
function deleteBasicRule(checkbox) {
    // 不再勾选
    checkbox.checked = false;

    // 获得ID
    var ruleIdInRuleSet = checkbox.attributes["ruleIdInRuleSet"].value;

    // 从规则库中删除
    removeKeyFromMap(ruleIdInRuleSet, map_id_to_basicRule);
    removeKeyFromMap(ruleIdInRuleSet, map_id_to_ruleExtData);

    // 清空表格行
    var rowIdxStart = parseInt(checkbox.attributes["rowIdx"].value);
    var td_br_type = getElementById("td_br_type_" + rowIdxStart);
    td_br_type.innerHTML = "";
    var td_br_value = getElementById("td_br_value_" + rowIdxStart); 
    td_br_value.innerHTML = "";

    // 隐藏表格行
    for (var rowIdx = rowIdxStart; rowIdx < rowIdxStart + NR_ROWS_OF_BASIC_RULE; rowIdx++) {
        var tr = getElementById("tr_" + rowIdx);
        tr.style.display = "none";
        // tr.attributes["ruleIdInRuleSet"].value = "";
    }
}



/**
 * 组合条件
 * @param operator
 */
function combin(operator) {
    // 找到选中的checkbox
    var inputList = document.getElementsByTagName("input");
    var subCheckboxList = new Array();
    var subRuleIdList = new Array();

    for (var i = 0; i < inputList.length; i++) {
        var input = inputList[i];
        if (input.type == "checkbox" && input.checked) {
            subCheckboxList.push(input);
            subRuleIdList.push(input.attributes.ruleIdInRuleSet.value);
        }
    }

    // 检查数量
    if (subCheckboxList.length <= 1) {
        alert("请选择两条或多条规则");
        return;
    }

    // 分配ID
    var ruleIdInRuleSet = "cr_" + cnt_compositeRule;
    // 创建规则
    var rule = new CompositeRule(ruleIdInRuleSet, operator, subRuleIdList);
    map_id_to_compositeRule[ruleIdInRuleSet] = rule;
    // 保存规则扩展数据
    var ruleExtData = new Object();
    map_id_to_ruleExtData[ruleIdInRuleSet] = ruleExtData;
    ruleExtData.subCheckboxList = subCheckboxList;

    // 处理数据
    var maxSubOpDepth = 0;
    var minSubRowIdx = 1000000;
    var maxSubRowIdx = 0;
    for (var i = 0; i < subCheckboxList.length; i++) {
        var cb = subCheckboxList[i];
        var subRowIdx_i = parseInt(cb.attributes["rowIdx"].value);
        var subOpDepth_i = parseInt(cb.attributes["opDepth"].value);

        // 计算opDepth
        if (maxSubOpDepth < subOpDepth_i) {
            maxSubOpDepth = subOpDepth_i;
        }

        // 计算rowIdx
        if (maxSubRowIdx < subRowIdx_i) {
            maxSubRowIdx = subRowIdx_i;
        }
        if (minSubRowIdx > subRowIdx_i) {
            minSubRowIdx = subRowIdx_i;
        }
    }

    var parentRowIdx = parseInt((maxSubRowIdx + minSubRowIdx) / 2);
    var parentOpDepth = maxSubOpDepth + 1;

    // 线条涉及的单元格
    ruleExtData.lineTdList = new Array();
    for (var i = 0; i < subCheckboxList.length; i++) {
        var subCheckbox = subCheckboxList[i];
        var subRowIdx_i = parseInt(subCheckbox.attributes["rowIdx"].value);
        var subOpDepth_i = parseInt(subCheckbox.attributes["opDepth"].value);

        // 消除选中
        subCheckbox.checked = false;
        subCheckbox.style.display = "none";
        // 显示横分线
        var td = getElementById("td_op_subLine_" + subRowIdx_i + "_" + (subOpDepth_i + 1));
        ruleExtData.lineTdList.push(td);
        setLine(td, "bottom");
        // 显示穿越线
        for (var subOpDepth_i_j = subOpDepth_i + 1; subOpDepth_i_j < parentOpDepth; subOpDepth_i_j++) {
            td = "td_op_parentLine_" + subRowIdx_i + "_" + subOpDepth_i_j;
            ruleExtData.lineTdList.push(td);
            setLine(td, "bottom");

            td = "td_op_op_" + subRowIdx_i + "_" + subOpDepth_i_j;
            ruleExtData.lineTdList.push(td);
            setLine(td, "bottom");

            td = "td_op_checkbox_" + subRowIdx_i + "_" + subOpDepth_i_j;
            ruleExtData.lineTdList.push(td);
            setLine(td, "bottom");

            td = "td_op_subLine_" + subRowIdx_i + "_" + (subOpDepth_i_j + 1);
            ruleExtData.lineTdList.push(td);
            setLine(td, "bottom");
        }
    }

    // 创建新的组合条件
    // getElementById("td_op_leftLine_"+rowIdx+"_"+opDepth)
    // getElementById("td_op_leftLine_"+rowIdx+"_"+(opDepth+1))

    // 组合方式
    // 操作符合并单元格
    ruleExtData.td_op_op = getElementById("td_op_op_" + parentRowIdx + "_" + parentOpDepth);
    ruleExtData.td_op_op.rowSpan = 2;
    ruleExtData.td_op_op_under = getElementById("td_op_op_" + (parentRowIdx + 1) + "_" + parentOpDepth);
    ruleExtData.td_op_op_under.style.display = "none";
    // 画边框
    ruleExtData.td_op_op.className = "td_left_box";
    ruleExtData.td_op_op.innerHTML = getOperatorText(operator);
    // 线
    td = "td_op_parentLine_" + parentRowIdx + "_" + parentOpDepth;
    ruleExtData.lineTdList.push(td);
    setLine(td, "bottom");

    // 复选框
    // 合并单元格
    ruleExtData.td_op_cb = getElementById("td_op_checkbox_" + parentRowIdx + "_" + parentOpDepth);
    ruleExtData.td_op_cb.rowSpan = 2;
    ruleExtData.td_op_cb_under = getElementById("td_op_checkbox_" + (parentRowIdx + 1) + "_" + parentOpDepth);
    ruleExtData.td_op_cb_under.style.display = "none";
    // 画边框
    ruleExtData.td_op_cb.className = "td_right_box";
    // 写复选框HTML
    ruleExtData.td_op_cb.innerHTML = "<input class='checkbox' type='checkbox' id='cb_" + parentRowIdx + "_" + parentOpDepth + "' ruleIdInRuleSet='" + ruleIdInRuleSet  + "' rowIdx='" + parentRowIdx + "' opDepth='" + parentOpDepth + "' />";

    // 竖线
    for (var rowIdx_i = minSubRowIdx + 1; rowIdx_i <= maxSubRowIdx; rowIdx_i++) {
        var td_op_parentLine = getElementById("td_op_parentLine_" + rowIdx_i + "_" + parentOpDepth);
        ruleExtData.lineTdList.push(td_op_parentLine);
        setLine(td_op_parentLine, "left");
    }
    
    // 计数增加
    cnt_compositeRule++;
}


/**
 * 删除复合规则
 */
function deleteCompositeRule(checkbox) {
    // 不再勾选
    checkbox.checked = false;

    // 获得ID
    var ruleIdInRuleSet = checkbox.attributes["ruleIdInRuleSet"].value;
    // 获得规则
    var rule = map_id_to_compositeRule[ruleIdInRuleSet];
    var ruleExtData = map_id_to_ruleExtData[ruleIdInRuleSet];

    // 从规则库中删除
    removeKeyFromMap(ruleIdInRuleSet, map_id_to_compositeRule);
    removeKeyFromMap(ruleIdInRuleSet, map_id_to_ruleExtData);

    // 恢复显示子规则复选框
    for(var i=0; i<ruleExtData.subCheckboxList.length; i++){
        ruleExtData.subCheckboxList[i].style.display = "";
    }
    
    // 操作符
    // 拆分单元格
    ruleExtData.td_op_op.rowSpan = 1;
    // 恢复显示下方单元格
    ruleExtData.td_op_op_under.style.display = "";
    // 清空组合规则表格
    ruleExtData.td_op_op.className = "";
    ruleExtData.td_op_op.innerHTML = "";

    // 复选框
    // 拆分单元格
    ruleExtData.td_op_cb.rowSpan = 1;
    // 恢复显示下方单元格
    ruleExtData.td_op_cb_under.style.display = "";
    // 清空组合规则表格
    ruleExtData.td_op_cb.className = "";
    ruleExtData.td_op_cb.innerHTML = "";

    // 清空各种线
    for(var i=0; i<ruleExtData.lineTdList.length; i++) {
        var lineTd = ruleExtData.lineTdList[i];
        clearLine(lineTd);
    }
}


/**
 * 基本规则类别变化后，要修改基本基本规则值
 * @param select_basicRuleType
 */
function onchange_basicRuleType(select_basicRuleType) {
    var rowIdx = select_basicRuleType.attributes["rowIdx"].value;
    var basicRuleType = getSelectedValue(select_basicRuleType);
    var td_basicRuleValue = getElementById("td_br_value_"+rowIdx);
    var tr = td_basicRuleValue.parentNode;
    var ruleIdInRuleSet = select_basicRuleType.attributes["ruleIdInRuleSet"].value;
    var rule = map_id_to_basicRule[ruleIdInRuleSet];

    if (basicRuleType == "") {
        // 清空
        rule.type = "";
        td_basicRuleValue.innerHTML = "";
    } else {
        // 设置基本规则类别
        rule.type = basicRuleType;

        if (basicRuleType == "languageId") {
            // 语种
            td_basicRuleValue.innerHTML =
            "<select onchange=\"onchange_basicRuleValue('"+ruleIdInRuleSet+"', ' ', getSelectedValue(this), '')\">" +
            "   <option value=''>请选择</option>" +
            "   <option value='1'>中简</option>" +
            "   <option value='2'>中繁</option>" +
            "   <option value='3'>英</option>" +
            "   <option value='4'>俄</option>" +
            "   <option value='5'>日</option>" +
            "</select>";
        } else if (basicRuleType == "entityTypeId") {
            // 实体
            var ctrlIdOfEntityType = ruleIdInRuleSet+"_entityType";
            var ctrlIdOfEntityId = ruleIdInRuleSet+"_entityId";
            var ctrlIdOfEntityName = ruleIdInRuleSet+"_entityName";
            td_basicRuleValue.innerHTML =
                "<input type='hidden' id='"+ctrlIdOfEntityType+"'/>" +
                "<input type='hidden' id='"+ctrlIdOfEntityId+"'/>" +
                "<input " +
                "   readonly" +
                "   id='"+ctrlIdOfEntityName+"'" +
                "   style='cursor: pointer'" +
                "   title='请点击选择'" +
                "   onclick=\"showQueryEntityFrameCommon(" +
                "       this, " +
                "       '', " +
                "       '"+ctrlIdOfEntityType+"', " +
                "       '"+ctrlIdOfEntityId+"', " +
                "       '"+ctrlIdOfEntityName+"', " +
                "       'onchange_basicRuleValue(\\'"+ruleIdInRuleSet+"\\', entityType, entityId, entityName)'" +
                "   )\" " +
                "/>";
        } else if (basicRuleType == "keyword") {
            // 关键词
            td_basicRuleValue.innerHTML =
            "<input onchange=\"onchange_basicRuleValue('"+ruleIdInRuleSet+"', ' ', 0, this.value)\"/>";
        } else {
            alert("Unknown basicRuleType:" + basicRuleType);
            return;
        }
    }
}


/**
 * 基本规则值发生变化后，要更新规则数据
 * @param ruleIdInRuleSet
 */
function onchange_basicRuleValue(ruleIdInRuleSet, valueChar, valueInt, valueString) {
    var rule = map_id_to_basicRule[ruleIdInRuleSet];
    // var td = ctrl.getParent();
    // var tr = td.getParent();

    rule.valueChar = valueChar;
    rule.valueInt = valueInt;
    rule.valueString = valueString;
}


function getOperatorText(operator) {
    if (operator == "and") {
        return "与";
    } else if (operator == "or") {
        return "或";
    } else {
        return operator;
    }
}


function getElementById(id) {
    obj = document.getElementById(id);
    if (obj == null) {
        alert("obj = null, id=" + id);
    }
    return obj;
}

function setLine(idOrObj, position) {
    var obj;
    if (typeof(idOrObj) == "string") {
        obj = getElementById(idOrObj);
        if (obj == null) {
            alert("obj = null, id=" + idOrObj);
        }
    } else {
        obj = idOrObj;
    }
    var cssText = "1px solid #aaccff";
    if (position == "bottom") {
        obj.style.borderBottom = cssText;
    } else if (position == "top") {
        obj.style.borderTop = cssText;
    } else if (position == "left") {
        obj.style.borderLeft = cssText;
    } else if (position == "right") {
        obj.style.borderRight = cssText;
    } else {
        obj.style.border = cssText;
    }
}

function clearLine(idOrObj, position) {
    var obj;
    if (typeof(idOrObj) == "string") {
        obj = getElementById(idOrObj);
        if (obj == null) {
            alert("obj = null, id=" + idOrObj);
        }
    } else {
        obj = idOrObj;
    }
    var cssText = "0";
    if (position == "bottom") {
        obj.style.borderBottom = cssText;
    } else if (position == "top") {
        obj.style.borderTop = cssText;
    } else if (position == "left") {
        obj.style.borderLeft = cssText;
    } else if (position == "right") {
        obj.style.borderRight = cssText;
    } else {
        obj.style.border = cssText;
    }
}


/**
 * 根据行索引得到基本规则的checkbox
 * @param rowIdx
 */
function getBasicRuleCheckbox(rowIdx) {
    return getElementById("checkbox_" + rowIdx + "_0");
}


/**
 * 根据行索引得到基本规则的checkbox
 * @param rowIdx
 */
function getSelect_basicRuleType(rowIdx) {
    return getElementById("basicRuleType_" + rowIdx);
}


/**
 * 保存之前先检查
 */
function checkBeforeSave() {
    // 检查是否有没有合并的规则
    if (cnt_topRule == 0) {
        alert("请创建规则后保存");
        return false;
    } else if (cnt_topRule > 1) {
        alert("尚有未合并的规则");
        return false;
    }

    // 检查基本规则是否都已经有值
    for(var ruleIdInRuleSet in map_id_to_basicRule) {
        var rule = map_id_to_basicRule[ruleIdInRuleSet];
        if(!rule.checkBeforeSave()) {
            return false;
        }
    }
    
    return true;
}


/**
 * 保存规则
 */
function saveRules() {
    // 检查
    if(!checkBeforeSave()){
        return;
    }

    // 保存
    AjaxInterface.saveRules(map_id_to_basicRule, map_id_to_compositeRule, callback_saveRules)
}


/**
 * 保存规则后的回调函数
 */
function callback_saveRules(blMessage) {
    if(blMessage.success) {
        alert("保存成功！");
    } else {
        alert(blMessage.message);
    }
}