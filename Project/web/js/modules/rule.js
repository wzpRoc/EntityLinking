// id到规则扩展数据的映射
var map_id_to_ruleExtData = new Object();
// 顶层规则计数
var map_id_to_topRule = new Object();
// 基本规则所占行数
var NR_ROWS_OF_BASIC_RULE = 3;

var countryIdNameOpinionListStr = "<option value='86'>中国</option><option value='886'>中国台湾</option><option value='852'>中国香港</option><option value='1'>美国</option><option value='7'>俄罗斯</option><option value='81'>日本</option><option value='44'>英国</option><option value='33'>法国</option><option value='49'>德国</option><option value='357'>賽普勒斯</option><option value='355'>阿尔巴尼亚</option><option value='213'>阿尔及利亚</option><option value='93'>阿富汗</option><option value='54'>阿根廷</option><option value='971'>阿拉伯联合酋长国</option><option value='297'>阿鲁巴</option><option value='968'>阿曼</option><option value='994'>阿塞拜疆</option><option value='20'>埃及</option><option value='251'>埃塞俄比亚</option><option value='353'>爱尔兰</option><option value='372'>爱沙尼亚</option><option value='376'>安道尔</option><option value='244'>安哥拉</option><option value='701'>安圭拉</option><option value='703'>安提瓜和巴布达</option><option value='43'>奥地利</option><option value='61'>澳大利亚</option><option value='742'>澳大利亚行政区划</option><option value='705'>巴巴多斯</option><option value='675'>巴布亚新几内亚</option><option value='704'>巴哈马</option><option value='92'>巴基斯坦</option><option value='595'>巴拉圭</option><option value='930'>巴勒斯坦</option><option value='973'>巴林</option><option value='507'>巴拿马</option><option value='55'>巴西</option><option value='375'>白俄罗斯</option><option value='706'>百慕大</option><option value='359'>保加利亚</option><option value='716'>北马里亚纳群岛</option><option value='229'>贝宁</option><option value='32'>比利时</option><option value='354'>冰岛</option><option value='591'>玻利維亚</option><option value='717'>波多黎各</option><option value='48'>波兰</option><option value='387'>波斯尼亚和黑塞哥维那</option><option value='267'>波札那</option><option value='501'>伯利兹</option><option value='975'>不丹</option><option value='226'>布吉纳法索</option><option value='850'>朝鲜</option><option value='240'>赤道几内亚</option><option value='45'>丹麦</option><option value='49'>德国</option><option value='670'>东帝汶</option><option value='676'>东加</option><option value='228'>多哥</option><option value='709'>多米尼克</option><option value='710'>多明尼加共和国</option><option value='7'>俄罗斯</option><option value='593'>厄瓜多尔</option><option value='291'>厄立特里亚</option><option value='33'>法国</option><option value='298'>法罗群岛</option><option value='689'>法属玻里尼西亚</option><option value='594'>法属圭亚那</option><option value='737'>法属南部领地</option><option value='63'>菲律宾</option><option value='358'>芬兰</option><option value='238'>佛得角</option><option value='500'>福克兰群岛</option><option value='220'>冈比亚</option><option value='242'>刚果共和国</option><option value='735'>刚果民主共和国</option><option value='57'>哥伦比亚</option><option value='506'>哥斯达黎加</option><option value='269'>葛摩</option><option value='711'>格林纳达</option><option value='299'>格陵兰</option><option value='995'>格鲁吉亚</option><option value='730'>根西岛</option><option value='53'>古巴</option><option value='724'>瓜德罗普</option><option value='712'>关岛</option><option value='592'>圭亚那</option><option value='714'>哈萨克斯坦</option><option value='509'>海地</option><option value='82'>韩国</option><option value='31'>荷兰</option><option value='599'>荷属安的列斯</option><option value='382'>黑山共和国</option><option value='504'>洪都拉斯</option><option value='686'>基里巴斯</option><option value='253'>吉布提</option><option value='331'>吉尔吉斯斯坦</option><option value='245'>几內亚比索</option><option value='224'>几内亚</option><option value='728'>加拿大</option><option value='740'>加纳</option><option value='241'>加蓬</option><option value='855'>柬埔寨</option><option value='420'>捷克</option><option value='263'>津巴布韦</option><option value='237'>喀麦隆</option><option value='974'>卡塔尔</option><option value='708'>开曼群岛</option><option value='381'>科索沃</option><option value='225'>科特迪瓦</option><option value='965'>科威特</option><option value='385'>克罗地亚</option><option value='254'>肯尼亚</option><option value='682'>库克群岛</option><option value='371'>拉脱维亚</option><option value='266'>莱索托</option><option value='856'>老挝</option><option value='961'>黎巴嫩</option><option value='231'>利比里亚</option><option value='218'>利比亚</option><option value='370'>立陶宛</option><option value='423'>列支敦斯登</option><option value='262'>留尼汪</option><option value='352'>卢森堡</option><option value='250'>卢旺达</option><option value='40'>罗马尼亚</option><option value='261'>马达加斯加</option><option value='356'>马耳他</option><option value='960'>马尔代夫</option><option value='265'>马拉维</option><option value='60'>马来西亚</option><option value='223'>马里共和国</option><option value='389'>马其顿</option><option value='692'>马绍尔群岛</option><option value='596'>马提尼克</option><option value='741'>马约特</option><option value='729'>曼島</option><option value='230'>毛里求斯</option><option value='222'>毛里塔尼亚</option><option value='1'>美国</option><option value='727'>美国本土外小岛屿</option><option value='700'>美属萨摩亚</option><option value='723'>美属维尔京群岛</option><option value='976'>蒙古国</option><option value='715'>蒙塞拉特岛</option><option value='880'>孟加拉国</option><option value='51'>秘鲁</option><option value='725'>密克罗尼西亚联邦</option><option value='95'>缅甸</option><option value='373'>摩尔多瓦</option><option value='212'>摩洛哥</option><option value='377'>摩纳哥</option><option value='258'>莫桑比克</option><option value='52'>墨西哥</option><option value='264'>纳米比亚</option><option value='27'>南非</option><option value='743'>南苏丹</option><option value='977'>尼泊尔</option><option value='505'>尼加拉瓜</option><option value='227'>尼日尔</option><option value='234'>尼日利亚</option><option value='683'>纽埃</option><option value='47'>挪威</option><option value='672'>诺福克岛</option><option value='680'>帕劳</option><option value='351'>葡萄牙</option><option value='257'>蒲隆地</option><option value='722'>千里达及托巴哥</option><option value='81'>日本</option><option value='46'>瑞典</option><option value='41'>瑞士</option><option value='503'>萨尔瓦多</option><option value='685'>萨摩亚</option><option value='736'>塞尔维亚</option><option value='232'>塞拉利昂</option><option value='221'>塞内加尔</option><option value='248'>塞舌尔</option><option value='966'>沙特阿拉伯</option><option value='239'>圣多美和普林西比</option><option value='290'>圣赫勒拿岛</option><option value='719'>圣基茨和尼维斯</option><option value='720'>圣卢西亚</option><option value='378'>圣马力诺</option><option value='508'>圣皮埃尔和密克隆群岛</option><option value='721'>圣文森特和格林纳丁斯</option><option value='94'>斯里兰卡</option><option value='421'>斯洛伐克</option><option value='386'>斯洛文尼亚</option><option value='726'>斯瓦尔巴特和扬马延岛</option><option value='268'>斯威士兰</option><option value='249'>苏丹</option><option value='597'>苏利南</option><option value='252'>索马里</option><option value='677'>所罗门群岛</option><option value='992'>塔吉克斯坦</option><option value='66'>泰国</option><option value='255'>坦桑尼亚</option><option value='649'>特克斯和凯科斯群岛</option><option value='216'>突尼斯</option><option value='688'>图瓦卢</option><option value='90'>土耳其</option><option value='993'>土库曼斯坦</option><option value='690'>托克劳</option><option value='681'>瓦利斯和富图纳群岛</option><option value='678'>瓦努阿图</option><option value='502'>危地马拉</option><option value='58'>委內瑞拉</option><option value='673'>文莱</option><option value='256'>乌干达</option><option value='380'>乌克兰</option><option value='598'>乌拉圭</option><option value='233'>乌兹别克斯坦</option><option value='34'>西班牙</option><option value='733'>西撒哈拉</option><option value='30'>希腊</option><option value='65'>新加坡</option><option value='687'>新喀里多尼亚</option><option value='64'>新西兰</option><option value='36'>匈牙利</option><option value='963'>叙利亚</option><option value='713'>牙买加</option><option value='374'>亚美尼亚</option><option value='967'>也门</option><option value='964'>伊拉克</option><option value='98'>伊朗</option><option value='972'>以色列</option><option value='39'>意大利</option><option value='91'>印度</option><option value='62'>印度尼西亚</option><option value='44'>英国</option><option value='731'>英国较小领土</option><option value='707'>英属维尔京群岛</option><option value='962'>约旦</option><option value='84'>越南</option><option value='260'>赞比亚</option><option value='732'>泽西岛</option><option value='235'>乍得</option><option value='350'>直布罗陀</option><option value='56'>智利</option><option value='734'>智利较小领土</option><option value='236'>中非共和国</option><option value='86'>中国</option><option value='886'>中国台湾</option><option value='887'>中国香港</option><option value='674'>瑙鲁</option><option value='379'>梵蒂冈</option><option value='679'>斐济</option>";

// 初始化
function init() {
    // 根据后台传来的数据操作
    showRuleSet(ruleSet);
}


/**
 * 基本规则的构造函数
 */
function BasicRule(idInRuleSet, seqInRuleSet, type, valueChar, valueInt, valueString, docFields) {
    this.idInRuleSet = idInRuleSet;
    this.seqInRuleSet = seqInRuleSet;
    this.type = type;
    this.valueChar = valueChar;
    this.valueInt = valueInt;
    this.valueString = valueString;
    this.docFields = docFields;
}


function showRuleSet(ruleSet) {
    // 操作基本规则
    if (ruleSet.basicRuleList) {
        for (var i = 0; i < ruleSet.basicRuleList.length; i++) {
            var rule = ruleSet.basicRuleList[i];
            showBasicRule(rule);
        }
    }

    // 操作组合规则
    if (ruleSet.compositeRuleList) {
        for (var i = 0; i < ruleSet.compositeRuleList.length; i++) {
            var rule = ruleSet.compositeRuleList[i];
            showCompositeRule(rule);
        }
    }
}

/**
 * 是否可保存
 */
function checkRuleBeforeSave(rule) {
    if (rule.type == undefined || rule.type == "" || rule.type == 0) {
        alert("请选择规则类别！");
        return false;
    }

    if (rule.type == "languageId") {
        if (rule.valueInt == undefined || rule.valueInt == "" || rule.valueInt == 0) {
            alert("请选择语种！");
            return false;
        }
    } else if (rule.type == "category") {
        if (rule.valueInt == undefined || rule.valueInt == "" || rule.valueInt == 0) {
            alert("请选择分类！");
            return false;
        }
    } else if (rule.type == "countryId") {
        if (rule.valueInt == undefined || rule.valueInt == "" || rule.valueInt == 0) {
            alert("请选择来源国家！");
            return false;
        }
    } else if (rule.type == "entityTypeId") {
        if (rule.valueInt == undefined || rule.valueInt == "" || rule.valueInt == 0) {
            alert("请选择实体！");
            return false;
        }
    } else if (rule.type == "keyword") {
        if (rule.valueString == undefined || rule.valueString == "") {
            alert("请填写关键词！");
            return false;
        }
    } else if (rule.type == "importance") {
        if (rule.valueInt == undefined || rule.valueInt == "" || rule.valueInt < 0 || rule.valueInt > 100) {
            alert("请填写重要性阈值（0-100）！");
            return false;
        }
    }

    return true;
}


/**
 * 创建空的基本规则
 */
function createEmptyBasicRule(idInRuleSet, seqInRuleSet) {
    return new BasicRule(idInRuleSet, seqInRuleSet, "", " ", 0, "");
}


/**
 * 复合规则的构造函数
 */
function CompositeRule(idInRuleSet, seqInRuleSet, operator, subRuleIdList) {
    this.idInRuleSet = idInRuleSet;
    this.seqInRuleSet = seqInRuleSet;
    this.operator = operator;
    this.subRuleIdList = subRuleIdList;
}


/**
 * 与
 */
function andRules() {
    var rule = createCompositeRuleBySelectedCheckbox('and');
    showCompositeRule(rule);
}


/**
 * 或
 */
function orRules() {
    var rule = createCompositeRuleBySelectedCheckbox('or');
    showCompositeRule(rule);
}


/**
 * 非
 */
function notRules() {
    var rule = createCompositeRuleBySelectedCheckbox('not');
    showCompositeRule(rule);
}


/**
 * 满足
 */
function satisfyRules() {
    var rule = createCompositeRuleBySelectedCheckbox('satisfy');
    showCompositeRule(rule);
}


function addBasicRule() {
    var rule = createBasicRule();
    showBasicRule(rule);
}

/**
 * 创建基本规则
 */
function createBasicRule() {
    // 分配ID
    var ruleSeqInRuleSet = getMaxBasicRuleSeq() + 1;
    var ruleIdInRuleSet = getNextBasicRuleId();

    // 创建规则
    var newBasicRule = createEmptyBasicRule(ruleIdInRuleSet, ruleSeqInRuleSet);

    // 加入规则库
    ruleSet.map_id_to_basicRule[ruleIdInRuleSet] = newBasicRule;

    return newBasicRule;
}


/**
 * 增加基本规则
 */
function showBasicRule(basicRule) {
    // 计算行数
    var rowIdxStart = basicRule.seqInRuleSet * NR_ROWS_OF_BASIC_RULE + 1;

    // 设置单元格的线条
    setLine("td_br_type_" + rowIdxStart, "left|top|bottom");
    setLine("td_br_value_" + rowIdxStart, "top|bottom");
    setLine("td_br_checkbox_" + rowIdxStart, "right|top|bottom");

    // 规则id写入checkbox
    var checkbox = getBasicRuleCheckbox(rowIdxStart);
    checkbox.style.display = "";
    checkbox.attributes["ruleIdInRuleSet"].value = basicRule.idInRuleSet;

    // 规则id写入基本规则类别下拉框
    var select_basicRuleType = getSelect_basicRuleType(rowIdxStart);
    select_basicRuleType.style.display = "";
    select_basicRuleType.attributes["ruleIdInRuleSet"].value = basicRule.idInRuleSet;
    // 写入值
    if (basicRule) {
        selectByValue(select_basicRuleType, basicRule.type);
        onchange_basicRuleType(select_basicRuleType, basicRule);
    }

    // 显示隐藏的表格行
    for (var rowIdx = rowIdxStart; rowIdx < rowIdxStart + NR_ROWS_OF_BASIC_RULE; rowIdx++) {
        var tr = getElementById_alertIfNull("tr_" + rowIdx);
        tr.style.display = "";
        // tr.attributes["ruleIdInRuleSet"].value = ruleIdInRuleSet;
    }

    map_id_to_topRule[basicRule.idInRuleSet] = basicRule;
}


/**
 * 删除多条规则
 */
function deleteRules() {
    // 找到所有的checkbox
    var inputList = getElementsByTagNameAndAttribute("input", "isRuleCheckbox", "true");
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
    for (var i = 0; i < selectedCheckboxList.length; i++) {
        var checkbox = selectedCheckboxList[i];
        deleteRule(checkbox);
    }
}


/**
 * 删除多条规则
 */
function deleteRule(checkbox) {
    var isBasicRule = checkbox.attributes["isBasicRule"];

    if (isBasicRule) {
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
    // 隐藏
    checkbox.style.display = "none";

    // 获得ID
    var ruleIdInRuleSet = checkbox.attributes["ruleIdInRuleSet"].value;
    // 从checkbox中删除该ID
    delete checkbox.attributes["ruleIdInRuleSet"];

    // 从规则库中删除
    removeKeyFromMap(ruleIdInRuleSet, ruleSet.map_id_to_basicRule);
    removeKeyFromMap(ruleIdInRuleSet, map_id_to_ruleExtData);

    // 清空表格行
    var rowIdxStart = parseInt(checkbox.attributes["rowIdx"].value);

    // 基本规则类别
    var select_basicRuleType = getElementById_alertIfNull("basicRuleType_" + rowIdxStart);
    // 隐藏
    select_basicRuleType.style.display = "none";
    // 清空基本规则类别值
    setSelectByValue(select_basicRuleType, "");
    // 清空ruleIdInRuleSet，因为对应的规则已经删除
    delete select_basicRuleType.attributes["ruleIdInRuleSet"];
    // 清空基本规则值
    var td_br_value = getElementById_alertIfNull("td_br_value_" + rowIdxStart);
    td_br_value.innerHTML = "";

    // 清除单元格的线条
    clearLine("td_br_type_" + rowIdxStart);
    clearLine("td_br_value_" + rowIdxStart);
    clearLine("td_br_checkbox_" + rowIdxStart);

    // 更新顶层规则
    delete map_id_to_topRule[ruleIdInRuleSet];
}

function addCompositeRule(compositeRule) {
    combinByCompositeRule(compositeRule);
}

/**
 * 按照已有的复合规则组合
 * @param compositeRule
 */
function getSubCheckboxList(compositeRule) {
    var subCheckboxList = new Array();
    for (var i = 0; i < compositeRule.subRuleIdList.length; i++) {
        var subRuleId = compositeRule.subRuleIdList[i];
        var checkbox = getRuleCheckboxByRuleIdInRuleSet(subRuleId);
        if (!checkbox) {
            alert("checkbox is null, subRuleId=" + subRuleId);
        }
        subCheckboxList.push(checkbox);
    }

    return subCheckboxList;
}


/**
 * 显示组合条件
 */
function showCompositeRule(compositeRule) {
    // 保存规则扩展数据
    var ruleExtData = new Object();
    ruleExtData.subCheckboxList = getSubCheckboxList(compositeRule);
    map_id_to_ruleExtData[compositeRule.idInRuleSet] = ruleExtData;

    // 处理数据
    var maxSubOpDepth = 0;
    var minSubRowIdx = 1000000;
    var maxSubRowIdx = 0;
    // 对所有子规则循环
    for (var i = 0; i < ruleExtData.subCheckboxList.length; i++) {
        var cb = ruleExtData.subCheckboxList[i];
        var subRowIdx_i = parseInt(cb.attributes["rowIdx"].value);
        var subOpDepth_i = parseInt(cb.attributes["opDepth"].value);
        // 计算最大opDepth
        if (maxSubOpDepth < subOpDepth_i) {
            maxSubOpDepth = subOpDepth_i;
        }
        // 计算最小和最大rowIdx，已求得中间的rowIdx，用于显示父规则
        if (maxSubRowIdx < subRowIdx_i) {
            maxSubRowIdx = subRowIdx_i;
        }
        if (minSubRowIdx > subRowIdx_i) {
            minSubRowIdx = subRowIdx_i;
        }
    }

    // 将最小到最大rowIdx之间的单元格都显示出来
    for (var rowIdx = minSubRowIdx; rowIdx <= maxSubRowIdx; rowIdx++) {
        var tr = getElementById_alertIfNull("tr_" + rowIdx);
        tr.style.display = "";
    }

    // 父规则的行
    var parentRowIdx = parseInt((maxSubRowIdx + minSubRowIdx) / 2);
    // 父规则的列/深度
    var parentOpDepth = maxSubOpDepth + 1;

    // 线条涉及的单元格
    ruleExtData.lineTdList = new Array();
    for (var i = 0; i < ruleExtData.subCheckboxList.length; i++) {
        var subCheckbox = ruleExtData.subCheckboxList[i];
        var subRowIdx_i = parseInt(subCheckbox.attributes["rowIdx"].value);
        var subOpDepth_i = parseInt(subCheckbox.attributes["opDepth"].value);

        // 将选框消除选中
        subCheckbox.checked = false;
        // 将选框隐藏
        subCheckbox.style.display = "none";
        // 显示子规则后面的横线
        var td = getElementById_alertIfNull("td_op_subLine_" + subRowIdx_i + "_" + (subOpDepth_i + 1));
        ruleExtData.lineTdList.push(td);
        // 当前规则占两行，这儿的td是上面一行，所以显示下面的线条
        setLine(td, "bottom");
        // 有的子规则深度较小的，有的子规则深度较大
        // 显示深度较小的子规则后面的横线的横线
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

    // 组合方式
    // 操作符合并单元格
    ruleExtData.td_op_op = getElementById_alertIfNull("td_op_op_" + parentRowIdx + "_" + parentOpDepth);
    ruleExtData.td_op_op.rowSpan = 2;
    ruleExtData.td_op_op_under = getElementById_alertIfNull("td_op_op_" + (parentRowIdx + 1) + "_" + parentOpDepth);
    ruleExtData.td_op_op_under.style.display = "none";
    // 画边框
    setLine(ruleExtData.td_op_op, "top|left|bottom");

    if (compositeRule.operator == "satisfy") {
        var id_select_compositeRule_nrSatisfiedSubRule = "select_compositeRule_nrSatisfiedSubRule_" + compositeRule.idInRuleSet;
        // 暂时只支持“至少”
        compositeRule.comparisonOperator = ">=";
        var optionHTML =
            "至少&nbsp;" +
                "<SELECT " +
                "   id=\"" + id_select_compositeRule_nrSatisfiedSubRule + "\"" +
                "   onchange=\"onchange_compositeRule('" + compositeRule.idInRuleSet + "', '>=', this.value)\">" +
                "   <OPTION value='2'>2</OPTION>" +
                "   <OPTION value='3'>3</OPTION>" +
                "   <OPTION value='4'>4</OPTION>" +
                "   <OPTION value='5'>5</OPTION>" +
                "   <OPTION value='6'>6</OPTION>" +
                "   <OPTION value='7'>7</OPTION>" +
                "   <OPTION value='8'>8</OPTION>" +
                "   <OPTION value='9'>9</OPTION>" +
                "   <OPTION value='10'>10</OPTION>" +
                "</SELECT>" +
                "&nbsp;项";
        ruleExtData.td_op_op.innerHTML = getOperatorText(compositeRule.operator) + optionHTML;
        if (compositeRule.nrSatisfiedSubRule) {
            getElementById(id_select_compositeRule_nrSatisfiedSubRule).value = compositeRule.nrSatisfiedSubRule;
        } else {
            // 默认值
            compositeRule.nrSatisfiedSubRule = 2;
        }
    } else {
        ruleExtData.td_op_op.innerHTML = getOperatorText(compositeRule.operator);
    }

    // 线
    td = "td_op_parentLine_" + parentRowIdx + "_" + parentOpDepth;
    ruleExtData.lineTdList.push(td);
    setLine(td, "bottom");

    // 复选框
    // 合并单元格
    ruleExtData.td_op_cb = getElementById_alertIfNull("td_op_checkbox_" + parentRowIdx + "_" + parentOpDepth);
    ruleExtData.td_op_cb.rowSpan = 2;
    ruleExtData.td_op_cb_under = getElementById_alertIfNull("td_op_checkbox_" + (parentRowIdx + 1) + "_" + parentOpDepth);
    ruleExtData.td_op_cb_under.style.display = "none";
    // 画边框
    setLine(ruleExtData.td_op_cb, "top|right|bottom");
    // 写复选框HTML
    ruleExtData.td_op_cb.innerHTML =
        "<input " +
            "   class='checkbox'" +
            "   type='checkbox'" +
            "   id='cb_" + parentRowIdx + "_" + parentOpDepth + "'" +
            "   ruleIdInRuleSet='" + compositeRule.idInRuleSet + "'" +
            "   rowIdx='" + parentRowIdx + "'" +
            "   opDepth='" + parentOpDepth + "'" +
            "   isRuleCheckbox='true'" +
            "/>";

    // 竖线
    for (var rowIdx_i = minSubRowIdx + 1; rowIdx_i <= maxSubRowIdx; rowIdx_i++) {
        var td_op_parentLine = getElementById_alertIfNull("td_op_parentLine_" + rowIdx_i + "_" + parentOpDepth);
        ruleExtData.lineTdList.push(td_op_parentLine);
        setLine(td_op_parentLine, "left");
    }

    // 处理顶层规则
    map_id_to_topRule[compositeRule.idInRuleSet] = compositeRule;
    for (id in compositeRule.subRuleIdList) {
        delete map_id_to_topRule[compositeRule.subRuleIdList[id]];
    }
}


/**
 * 组合条件
 * @param operator
 */
function createCompositeRuleBySelectedCheckbox(operator) {
    // 找到选中的checkbox
    var inputList = getElementsByTagNameAndAttribute("input", "isRuleCheckbox", "true");
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
    if (operator == "and" || operator == "or") {
        if (subCheckboxList.length <= 1) {
            alert("请选择两条或多条规则");
            return null;
        }
    } else if (operator == "not") {
        if (subCheckboxList.length != 1) {
            alert("请选择一条规则");
            return null;
        }
    } else if (operator == "satisfy") {
        if (subCheckboxList.length == 0) {
            alert("请选择一条或多条规则");
            return null;
        }
    } else {
        alert("未知操作符:" + operator);
        return null;
    }

    // 分配ID
    var seqInRuleSet = (getMaxCompositeRuleSeq() + 1);
    var idInRuleSet = getNextCompositeRuleId();

    // 创建规则
    var rule = new CompositeRule(idInRuleSet, seqInRuleSet, operator, subRuleIdList);
    // 加入到规则库
    ruleSet.map_id_to_compositeRule[idInRuleSet] = rule;

    return rule;
}


/**
 * 删除复合规则
 */
function deleteCompositeRule(checkbox) {
    // 获得ID
    var ruleIdInRuleSet = checkbox.attributes["ruleIdInRuleSet"].value;
    // 获得规则
    var compositeRule = getRuleById(ruleIdInRuleSet);

    // 更新顶层规则
    delete map_id_to_topRule[compositeRule.idInRuleSet];
    for (i in compositeRule.subRuleIdList) {
        var id = compositeRule.subRuleIdList[i];
        map_id_to_topRule[id] = getRuleById(id);
    }

    // 不再勾选
    checkbox.checked = false;

    // 获得规则
    var rule = ruleSet.map_id_to_compositeRule[ruleIdInRuleSet];
    var ruleExtData = map_id_to_ruleExtData[ruleIdInRuleSet];

    // 从规则库中删除
    removeKeyFromMap(ruleIdInRuleSet, ruleSet.map_id_to_compositeRule);
    removeKeyFromMap(ruleIdInRuleSet, map_id_to_ruleExtData);

    // 恢复显示子规则复选框
    for (var i = 0; i < ruleExtData.subCheckboxList.length; i++) {
        ruleExtData.subCheckboxList[i].style.display = "";
    }

    // 操作符
    // 拆分单元格
    ruleExtData.td_op_op.rowSpan = 1;
    // 恢复显示下方单元格
    ruleExtData.td_op_op_under.style.display = "";
    // 清空组合规则表格
    clearLine(ruleExtData.td_op_op);
    ruleExtData.td_op_op.innerHTML = "";

    // 复选框
    // 拆分单元格
    ruleExtData.td_op_cb.rowSpan = 1;
    // 恢复显示下方单元格
    ruleExtData.td_op_cb_under.style.display = "";
    // 清空组合规则表格
    clearLine(ruleExtData.td_op_cb);
    ruleExtData.td_op_cb.innerHTML = "";

    // 清空各种线
    for (var i = 0; i < ruleExtData.lineTdList.length; i++) {
        var lineTd = ruleExtData.lineTdList[i];
        clearLine(lineTd);
    }
}


/**
 * 基本规则类别变化后，要修改基本基本规则值
 * @param select_basicRuleType
 */
function onchange_basicRuleType(select_basicRuleType, basicRule) {
    var rowIdx = select_basicRuleType.attributes["rowIdx"].value;
    var basicRuleType = getSelectedValue(select_basicRuleType);
    var td_basicRuleValue = getElementById_alertIfNull("td_br_value_" + rowIdx);
    var tr = td_basicRuleValue.parentNode;
    var ruleIdInRuleSet = select_basicRuleType.attributes["ruleIdInRuleSet"].value;
    var rule = ruleSet.map_id_to_basicRule[ruleIdInRuleSet];

    if (basicRuleType == "") {
        // 清空
        rule.type = "";
        td_basicRuleValue.innerHTML = "";
    } else {
        // 设置基本规则类别
        rule.type = basicRuleType;

        if (basicRuleType == "languageId") {
            // 语种
            var id = "languageId_" + ruleIdInRuleSet;
            td_basicRuleValue.innerHTML =
                "<select " +
                    "   id=\"" + id + "\"" +
                    "   onchange=\"onchange_basicRuleValue('" + ruleIdInRuleSet + "', false, ' ', getSelectedValue(this), '')\"" +
                    "   ruleIdInRuleSet=\"" + ruleIdInRuleSet + "\"" +
                    ">" +
                    "   <option value=''>请选择</option>" +
                    "   <option value='1'>中简</option>" +
                    "   <option value='2'>中繁</option>" +
                    "   <option value='3'>英</option>" +
                    "   <option value='4'>俄</option>" +
                    "   <option value='5'>日</option>" +
                    "</select>";
            if (basicRule && basicRule.valueInt) {
                var ctrl = getElementById(id);
                setSelectByValue(ctrl, basicRule.valueInt);
            }
        } else if (basicRuleType == "rule") {
            //规则
            var id_rule = "ruleId_" + ruleIdInRuleSet;

            var html_rule = getElementById("div_rule").innerHTML;
            html_rule = html_rule.replace("id_rule", id_rule);
            html_rule = html_rule.replace("ruleIdInRuleSet", ruleIdInRuleSet);

            td_basicRuleValue.innerHTML = html_rule;

            if (basicRule && basicRule.valueInt) {
                // 规则ID
                var ctrl = getElementById(id_rule);
                selectByValue(ctrl, basicRule.valueInt);
            }

        } else if (basicRuleType == "category") {
            // 分类
            var id = "category_" + ruleIdInRuleSet;
            td_basicRuleValue.innerHTML =
                "<select " +
                    "   id=\"" + id + "\"" +
                    "   onchange=\"onchange_basicRuleValue('" + ruleIdInRuleSet + "', false, ' ', getSelectedValue(this), '')\"" +
                    "   ruleIdInRuleSet=\"" + ruleIdInRuleSet + "\"" +
                    ">" +
                    "   <option value='1001'>国家/地区大选</option>" +
                    "   <option value='1002'>人事变动</option>" +
                    "   <option value='1003'>重要会议</option>" +
                    "   <option value='1004'>高端访问</option>" +
                    "   <option value='1005'>条约制定</option>" +
                    "   <option value='2001'>重要讲话</option>" +
                    "   <option value='1008'>军事演习</option>" +
                    "   <option value='2002'>阅兵</option>" +
                    "   <option value='2003'>军事行动</option>" +
                    "   <option value='1009'>武器研发</option>" +
                    "   <option value='1010'>武器交易</option>" +
                    "   <option value='1006'>社会冲突</option>" +
                    "   <option value='1007'>武装冲突</option>" +
                    "   <option value='1011'>恐怖活动</option>" +
                    "   <option value='1012'>反恐行动</option>" +
                    "   <option value='1013'>自然灾害</option>" +
                    "   <option value='1014'>领土争端</option>" +
                    "   <option value='1'>其他</option>" +
                    "</select>";
            if (basicRule && basicRule.valueInt) {
                var ctrl = getElementById(id);
                setSelectByValue(ctrl, basicRule.valueInt);
            }
        } else if (basicRuleType == "countryId") {
            // 语种
            var id = "countryId_" + ruleIdInRuleSet;
            td_basicRuleValue.innerHTML =
                "<select " +
                    "   id=\"" + id + "\"" +
                    "   onchange=\"onchange_basicRuleValue('" + ruleIdInRuleSet + "', false, ' ', getSelectedValue(this), '')\"" +
                    "   ruleIdInRuleSet=\"" + ruleIdInRuleSet + "\"" +
                    ">" +
                    "   <option value=''>请选择</option>" +
                    countryIdNameOpinionListStr +
                    "</select>";
            if (basicRule && basicRule.valueInt) {
                var ctrl = getElementById(id);
                setSelectByValue(ctrl, basicRule.valueInt);
            }
        } else if (basicRuleType == "entityTypeId") {
            // 实体
            var ctrlIdOfEntityType = ruleIdInRuleSet + "_entityType";
            var ctrlIdOfEntityId = ruleIdInRuleSet + "_entityId";
            var ctrlIdOfEntityName = ruleIdInRuleSet + "_entityName";
            td_basicRuleValue.innerHTML =
                "<input type='hidden' id='" + ctrlIdOfEntityType + "'/>" +
                    "<input type='hidden' id='" + ctrlIdOfEntityId + "'/>" +
                    "<input " +
                    "   readonly" +
                    "   id='" + ctrlIdOfEntityName + "'" +
                    "   style='cursor: pointer'" +
                    "   title='请点击选择'" +
                    "   onclick=\"showQueryEntityFrameCommon(" +
                    "       this, " +
                    "       '', " +
                    "       '" + ctrlIdOfEntityType + "', " +
                    "       '" + ctrlIdOfEntityId + "', " +
                    "       '" + ctrlIdOfEntityName + "', " +
                    "       'onchange_basicRuleValue(\\'" + ruleIdInRuleSet + "\\', false, entityType, entityId, entityName)'" +
                    "   )\" " +
                    "/>";
            if (basicRule && basicRule.valueString) {
                var ctrl = getElementById(ctrlIdOfEntityName);
                ctrl.value = basicRule.valueString;
            }
        } else if (basicRuleType == "keyword") {
            // 关键词
            var id_keyword = "keyword_" + ruleIdInRuleSet;
            var id_caseSensitive = "caseSensitive_" + ruleIdInRuleSet;
            var id_keywordField = "keywordField_" + ruleIdInRuleSet;
            var id_keywordOperator = "keywordOperator_" + ruleIdInRuleSet;
            td_basicRuleValue.innerHTML =
                "<INPUT " +
                    "   id='" + id_keyword + "'" +
                    "   onchange=\"onchange_keyword('" + ruleIdInRuleSet + "')\"" +
                    "/>" +
                    "&nbsp;" +
                    "<SELECT " +
                    "   id='" + id_keywordOperator + "'" +
                    "   onchange=\"onchange_keyword('" + ruleIdInRuleSet + "')\"" +
                    "/>" +
                    "   <OPTION value='or'>之一</OPTION>" +
                    "   <OPTION value='and'>全部</OPTION>" +
                    "</SELECT>" +
                    "&nbsp;于&nbsp;" +
                    "<SELECT " +
                    "   id='" + id_keywordField + "'" +
                    "   onchange=\"onchange_keyword('" + ruleIdInRuleSet + "')\"" +
                    "/>" +
                    "   <OPTION value=' '>全部</OPTION>" +
                    "   <OPTION value='t'>标题</OPTION>" +
                    "   <OPTION value='a'>摘要</OPTION>" +
                    "   <OPTION value='ta'>标题或摘要</OPTION>" +
                    "   <OPTION value='c'>正文</OPTION>" +
                    "</SELECT>" +
                    "&nbsp;" +
                    "<INPUT " +
                    "   id='" + id_caseSensitive + "'" +
                    "   type='checkbox'" +
                    "   class='checkbox'" +
                    "   onchange=\"onchange_keyword('" + ruleIdInRuleSet + "')\"" +
                    "/>&nbsp;区分大小写&nbsp;&nbsp;";
            if (basicRule && basicRule.valueBool) {
                var ctrl = getElementById(id_caseSensitive);
                ctrl.checked = basicRule.valueBool;
            }
            if (basicRule && basicRule.docFields) {
                var ctrl = getElementById(id_keywordField);
                selectByValue(ctrl, basicRule.docFields);
            }
            if (basicRule && basicRule.valueString) {
                var ctrl = getElementById(id_keyword);
                ctrl.value = basicRule.valueString;
            }
            if (basicRule && basicRule.operator) {
                var ctrl = getElementById(id_keywordOperator);
                selectByValue(ctrl, basicRule.operator);
            }
        } else if (basicRuleType == "contentLength") {
            // 文本长度
            var id_contentLength = "contentLength_" + ruleIdInRuleSet;
            var id_contentLengthOperator = "contentLengthOperator_" + ruleIdInRuleSet;
            var id_contentLengthThreshold = "contentLengthThreshold_" + ruleIdInRuleSet;
            td_basicRuleValue.innerHTML =
                "<SELECT " +
                    "   id='" + id_contentLengthOperator + "'" +
                    "   onchange=\"onchange_contentLength('" + ruleIdInRuleSet + "')\"" +
                    "/>" +
                    "   <OPTION value='>'>大于</OPTION>" +
                    "   <OPTION value='='>等于</OPTION>" +
                    "   <OPTION value='<'>小于</OPTION>" +
                    "</SELECT>" +
                    "&nbsp;" +
                    "<INPUT " +
                    "   id='" + id_contentLengthThreshold + "'" +
                    "   onchange=\"onchange_contentLength('" + ruleIdInRuleSet + "')\"" +
                    "/>";
            if (basicRule && basicRule.operator) {
                var ctrl = getElementById(id_contentLengthOperator);
                selectByValue(ctrl, basicRule.operator);
            }
            if (basicRule && basicRule.valueInt) {
                var ctrl = getElementById(id_contentLengthThreshold);
                ctrl.value = basicRule.valueInt;
            }
        } else if (basicRuleType == "importance") {
            // 重要性
            var id_importance = "importance_" + ruleIdInRuleSet;
            var id_importanceOperator = "importanceOperator_" + ruleIdInRuleSet;
            var id_importanceThreshold = "importanceThreshold_" + ruleIdInRuleSet;
            td_basicRuleValue.innerHTML =
                "<SELECT " +
                    "   id='" + id_importanceOperator + "'" +
                    "   onchange=\"onchange_importance('" + ruleIdInRuleSet + "')\"" +
                    "/>" +
                    "   <OPTION value='>'>大于</OPTION>" +
                    "   <OPTION value='='>等于</OPTION>" +
                    "   <OPTION value='<'>小于</OPTION>" +
                    "</SELECT>" +
                    "&nbsp;" +
                    "<INPUT " +
                    "   id='" + id_importanceThreshold + "'" +
                    "   onchange=\"onchange_importance('" + ruleIdInRuleSet + "')\"" +
                    "/>";
            if (basicRule && basicRule.operator) {
                var ctrl = getElementById(id_importanceOperator);
                selectByValue(ctrl, basicRule.operator);
            }
            if (basicRule && basicRule.valueInt) {
                var ctrl = getElementById(id_importanceThreshold);
                ctrl.value = basicRule.valueInt;
            }
        } else if (basicRuleType == "gazetteer") {
            //词表
            var id_gazetteer = "gazetteer_" + ruleIdInRuleSet;
            var id_caseSensitive = "caseSensitive_" + ruleIdInRuleSet;
            var id_gazetteerField = "gazetteerField_" + ruleIdInRuleSet;
            var id_gazetteerOperator = "gazetteerOperator_" + ruleIdInRuleSet;
            var html_gazetteer = getElementById("div_gazetteer").innerHTML;
            html_gazetteer = html_gazetteer.replace("id_gazetteer", id_gazetteer);
            html_gazetteer = html_gazetteer.replace("ruleIdInRuleSet", ruleIdInRuleSet);
            td_basicRuleValue.innerHTML =
                html_gazetteer +
                    "&nbsp;" +
                    "<SELECT " +
                    "   id='" + id_gazetteerOperator + "'" +
                    "   onchange=\"onchange_gazetteer('" + ruleIdInRuleSet + "')\"" +
                    "/>" +
                    "   <OPTION value='or'>之一</OPTION>" +
                    "   <OPTION value='and'>全部</OPTION>" +
                    "</SELECT>" +
                    "&nbsp;于&nbsp;" +
                    "<SELECT " +
                    "   id='" + id_gazetteerField + "'" +
                    "   onchange=\"onchange_gazetteer('" + ruleIdInRuleSet + "')\"" +
                    "/>" +
                    "   <OPTION value=' '>全部</OPTION>" +
                    "   <OPTION value='t'>标题</OPTION>" +
                    "   <OPTION value='a'>摘要</OPTION>" +
                    "   <OPTION value='ta'>标题或摘要</OPTION>" +
                    "   <OPTION value='c'>正文</OPTION>" +
                    "</SELECT>" +
                    "&nbsp;" +
                    "<INPUT " +
                    "   id='" + id_caseSensitive + "'" +
                    "   type='checkbox'" +
                    "   class='checkbox'" +
                    "   onchange=\"onchange_gazetteer('" + ruleIdInRuleSet + "')\"" +
                    "/>&nbsp;区分大小写&nbsp;&nbsp;";
            if (basicRule && basicRule.valueBool) {
                // 区分大小写
                var ctrl = getElementById(id_caseSensitive);
                ctrl.checked = basicRule.valueBool;
            }
            if (basicRule && basicRule.docFields) {
                // 文章域
                var ctrl = getElementById(id_gazetteerField);
                selectByValue(ctrl, basicRule.docFields);
            }
            if (basicRule && basicRule.valueInt) {
                // 词表ID
                var ctrl = getElementById(id_gazetteer);
                selectByValue(ctrl, basicRule.valueInt);
            }
            if (basicRule && basicRule.operator) {
                // 之一/全部
                var ctrl = getElementById(id_gazetteerOperator);
                selectByValue(ctrl, basicRule.operator);
            }
        } else {
            alert("Unknown basicRuleType:" + basicRuleType);
            return;
        }
    }
}


/**
 * 关键词变化事件处理函数
 */
function onchange_keyword(ruleIdInRuleSet) {
    var id_keyword = "keyword_" + ruleIdInRuleSet;
    var id_keywordField = "keywordField_" + ruleIdInRuleSet;
    var id_keywordOperator = "keywordOperator_" + ruleIdInRuleSet;
    var id_caseSensitive = "caseSensitive_" + ruleIdInRuleSet;

    var keyword = getElementById(id_keyword).value;
    var keywordField = getSelectedValueBySelectId(id_keywordField);
    var keywordOperator = getSelectedValueBySelectId(id_keywordOperator);
    var caseSensitive = getElementById(id_caseSensitive).checked;

    onchange_basicRuleValue(ruleIdInRuleSet, caseSensitive, ' ', 0, keyword, keywordOperator, keywordField);
}


/**
 * 关键词变化事件处理函数
 */
function onchange_gazetteer(ruleIdInRuleSet) {
    var id_gazetteer = "gazetteer_" + ruleIdInRuleSet;
    var id_gazetteerField = "gazetteerField_" + ruleIdInRuleSet;
    var id_gazetteerOperator = "gazetteerOperator_" + ruleIdInRuleSet;
    var id_caseSensitive = "caseSensitive_" + ruleIdInRuleSet;

    var gazetteer = getSelectedValueBySelectId(id_gazetteer);
    var gazetteerField = getSelectedValueBySelectId(id_gazetteerField);
    var gazetteerOperator = getSelectedValueBySelectId(id_gazetteerOperator);
    var caseSensitive = getElementById(id_caseSensitive).checked;

    onchange_basicRuleValue(ruleIdInRuleSet, caseSensitive, ' ', gazetteer, '', gazetteerOperator, gazetteerField);
}

/**
 * 包含规则变化事件处理函数
 * @param ruleIdInRuleSet
 * @return
 */
function onchange_rule(ruleIdInRuleSet) {
    var id_rule = "ruleId_" + ruleIdInRuleSet;

    var rule = getSelectedValueBySelectId(id_rule);

    onchange_basicRuleValue(ruleIdInRuleSet, true, ' ', rule, '', '', '');

}


/**
 * 内容长度变化事件处理函数
 */
function onchange_contentLength(ruleIdInRuleSet) {
    var id_contentLengthOperator = "contentLengthOperator_" + ruleIdInRuleSet;
    var id_contentLengthThreshold = "contentLengthThreshold_" + ruleIdInRuleSet;

    var contentLengthOperator = getSelectedValueBySelectId(id_contentLengthOperator);
    var contentLengthThreshold = getElementById(id_contentLengthThreshold).value;

    onchange_basicRuleValue(ruleIdInRuleSet, true, ' ', contentLengthThreshold, '', contentLengthOperator);
}


/**
 * 重要性变化事件处理函数
 */
function onchange_importance(ruleIdInRuleSet) {
    var id_importanceOperator = "importanceOperator_" + ruleIdInRuleSet;
    var id_importanceThreshold = "importanceThreshold_" + ruleIdInRuleSet;

    var importanceOperator = getSelectedValueBySelectId(id_importanceOperator);
    var importanceThreshold = getElementById(id_importanceThreshold).value;

    onchange_basicRuleValue(ruleIdInRuleSet, true, ' ', importanceThreshold, '', importanceOperator);
}


/**
 * 基本规则值发生变化后，要更新规则数据
 * @param ruleIdInRuleSet
 */
function onchange_basicRuleValue(ruleIdInRuleSet, valueBool, valueChar, valueInt, valueString, operator, docFields) {
    var rule = ruleSet.map_id_to_basicRule[ruleIdInRuleSet];
    // var td = ctrl.getParent();
    // var tr = td.getParent();

    rule.valueBool = valueBool;
    rule.valueChar = valueChar;
    rule.valueInt = valueInt;
    rule.valueString = valueString;
    rule.operator = operator;
    rule.docFields = docFields;
}


function getOperatorText(operator) {
    if (operator == "and") {
        return "与";
    } else if (operator == "or") {
        return "或";
    } else if (operator == "not") {
        return "非";
    } else if (operator == "satisfy") {
        return "满足";
    } else {
        return operator;
    }
}


/**
 * 处理复合规则变化事件，将控件中的值填入到值对象
 * @param idInRuleSet
 * @param comparisonOperator
 * @param nrSatisfiedSubRule
 */
function onchange_compositeRule(idInRuleSet, comparisonOperator, nrSatisfiedSubRule) {
    var compositeRule = ruleSet.map_id_to_compositeRule[idInRuleSet];
    compositeRule.comparisonOperator = comparisonOperator;
    compositeRule.nrSatisfiedSubRule = nrSatisfiedSubRule;
}


function setLine(idOrObj, positionTag) {
    var obj;
    if (typeof(idOrObj) == "string") {
        obj = getElementById_alertIfNull(idOrObj);
        if (obj == null) {
            alert("obj = null, id=" + idOrObj);
        }
    } else {
        obj = idOrObj;
    }
    var cssText = "1px solid #0077ee";
    if (positionTag.indexOf("bottom") >= 0) {
        obj.style.borderBottom = cssText;
        obj.style.borderBottom = cssText;
    }
    if (positionTag.indexOf("top") >= 0) {
        obj.style.borderTop = cssText;
    }
    if (positionTag.indexOf("left") >= 0) {
        obj.style.borderLeft = cssText;
    }
    if (positionTag.indexOf("right") >= 0) {
        obj.style.borderRight = cssText;
    }
}

function clearLine(idOrObj, position) {
    var obj;
    if (typeof(idOrObj) == "string") {
        obj = getElementById_alertIfNull(idOrObj);
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
 * 通过ID得到规则
 * @param id
 */
function getRuleById(id) {
    var basicRule = ruleSet.map_id_to_basicRule[id];
    if (basicRule) {
        return basicRule;
    } else {
        return ruleSet.map_id_to_compositeRule[id];
    }
}

/**
 * 根据行索引得到基本规则的checkbox
 * @param rowIdx
 */
function getBasicRuleCheckbox(rowIdx) {
    return getElementById_alertIfNull("checkbox_" + rowIdx + "_0");
}


function getRuleCheckboxByRuleIdInRuleSet(ruleIdInRuleSet) {
    return getOnlyElementByTagNameAndAttributes("input", {"type": "checkbox", "ruleIdInRuleSet": ruleIdInRuleSet});
}

/**
 * 根据行索引得到基本规则的checkbox
 * @param rowIdx
 */
function getSelect_basicRuleType(rowIdx) {
    return getElementById_alertIfNull("basicRuleType_" + rowIdx);
}


function getMaxBasicRuleSeq() {
    var maxBasicRuleSeq = 0;

    for (var ruleIdInRuleSet in ruleSet.map_id_to_basicRule) {
        // 序号
        var seqInRuleSet = ruleSet.map_id_to_basicRule[ruleIdInRuleSet].seqInRuleSet;
        if (seqInRuleSet > maxBasicRuleSeq) {
            maxBasicRuleSeq = seqInRuleSet;
        }
    }

    return maxBasicRuleSeq;
}


function getNextBasicRuleId() {
    var maxBasicRuleIdNum = 0;

    for (var ruleIdInRuleSet in ruleSet.map_id_to_basicRule) {
        // 序号
        var basicRuleIdNum = parseInt(ruleSet.map_id_to_basicRule[ruleIdInRuleSet].idInRuleSet.split("_")[1]);
        if (basicRuleIdNum > maxBasicRuleIdNum) {
            maxBasicRuleIdNum = basicRuleIdNum;
        }
    }

    return "br_" + (parseInt(maxBasicRuleIdNum) + 1);
}


function getNextCompositeRuleId() {
    var maxCompositeRuleIdNum = 0;

    for (var ruleIdInRuleSet in ruleSet.map_id_to_compositeRule) {
        // 序号
        var compositeRuleIdNum = parseInt(ruleSet.map_id_to_compositeRule[ruleIdInRuleSet].idInRuleSet.split("_")[1]);
        if (compositeRuleIdNum > maxCompositeRuleIdNum) {
            maxCompositeRuleIdNum = compositeRuleIdNum;
        }
    }

    return "cr_" + (parseInt(maxCompositeRuleIdNum) + 1);
}


function getRuleSeq(ruleIdInRuleSet) {
    return ruleIdInRuleSet.split("_")[1];
}


function getMaxCompositeRuleSeq() {
    var maxCompositeRuleSeq = 0;

    for (var ruleIdInRuleSet in ruleSet.map_id_to_compositeRule) {
        // 序号
        var seqInRuleSet = ruleSet.map_id_to_compositeRule[ruleIdInRuleSet].seqInRuleSet;
        if (seqInRuleSet > maxCompositeRuleSeq) {
            maxCompositeRuleSeq = seqInRuleSet;
        }
    }

    return maxCompositeRuleSeq;
}


/**
 * 保存之前先检查
 */
function checkBeforeSave() {
    if (ruleSet.expression && ruleSet.expression != "") {
        // 使用表达式
    } else {
        // 使用控件
        // 检查是否有没有合并的规则
        var nrTopRule = size(map_id_to_topRule);
        if (nrTopRule < 0) {
            alert("内部错误：map_id_to_topRule.length = " + map_id_to_topRule.length);
            return false;
        } else if (nrTopRule == 0) {
            alert("请创建规则后保存");
            return false;
        } else if (nrTopRule > 1) {
            if (!confirm("尚有未合并的规则，是否确认保存？")) {
                return false;
            }
        }

        // 检查基本规则是否都已经有值
        for (var ruleIdInRuleSet in ruleSet.map_id_to_basicRule) {
            var rule = ruleSet.map_id_to_basicRule[ruleIdInRuleSet];
            if (!checkRuleBeforeSave(rule)) {
                return false;
            }
        }
    }

    return true;
}


/**
 * 保存规则
 */
function saveRules() {
    // 检查
    if (!checkBeforeSave()) {
        return;
    }

    if (ruleSet.expression && ruleSet.expression != "") {
        // 使用表达式
    } else {
        // 使用控件
        // 设置topRule
        for (id in map_id_to_topRule) {
            ruleSet.topRuleIdInRuleSet = id;
            break;
        }
        // 将map转为list
        ruleSet.basicRuleList = values(ruleSet.map_id_to_basicRule);
        ruleSet.compositeRuleList = values(ruleSet.map_id_to_compositeRule);
    }

    // 保存
    if (dowhat == "add") {
        AjaxInterface.addRuleSet(ruleSet, callback_saveRules);
    } else {
        AjaxInterface.editRuleSet(ruleSet, callback_saveRules);
    }
}


/**
 * 保存规则后的回调函数
 */
function callback_saveRules(blMessage) {
    if (blMessage.success) {
        if (dowhat == "add") {
            dowhat = "edit";
            ruleSet.id = blMessage.data;
        }
        alert("保存成功！");

        if (window.opener) {
            if (window.opener.onRuleSetSaved) {
                window.opener.onRuleSetSaved(ruleSet);
            }
        }
    } else {
        alert(blMessage.message);
    }
}


/**
 * 选中所有顶层规则
 */
function selectAllUnselectedCheckbox() {
    for (ruleIdInRuleSet in map_id_to_topRule) {
        var ruleCheckbox = getRuleCheckboxByRuleIdInRuleSet(ruleIdInRuleSet);
        if (!ruleCheckbox.checked) {
            ruleCheckbox.checked = true;
        }
    }
}