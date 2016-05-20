/**********************************************************************************************************************************************
 *名称：businessCommon.js
 *功能：业务公共
 *开发者：鲁廷明
 *时间：2012-05-23
 **********************************************************************************************************************************************/


/**
 * 打开新闻详情页面
 * @param docId
 */
function openDocDetail(docId){
    window.open(ctx+"/doc!detail?id="+docId);
}

/**
 * 打开事件详情页面
 * @param eventId
 */
function openEventDetail(eventId){
    window.open(ctx+"/event!detail?id="+eventId);
}

/**
 * 打开实体详情页面
 * @param entityId
 */
function openEntityDetail(entityId){
    if(entityId <= 0) {
        return;
    }

    var url;

    if(url!=null) {
        window.open(url);
    }
}


/**
 * 打开实体分析页面
 * @param entityId
 */
function openEntityAnalysis(entityId){
    var url;

    window.open(url);
}


/**
 * 打开实体编辑页面
 * @param entityId
 */
function openEntityEdit(entityId){
    var url;

    window.open(url);
}


var qefc_positionCtrl;
var qefc_entityIdObj;
var qefc_entityTitleObj;
var qefc_callbackCommand;


/**
 * 显示查询实体框
 * @positionCtrl 定位控件
 * @ctrlIdOfEntityId 实体id控件id
 * @ctrlIdOfentityTitle 实体名称控件id
 */
function showQueryEntityFrameCommon(positionCtrl, mention, entityIdObj, entityTitleObj, callbackCommand){
    qefc_positionCtrl = positionCtrl;
    qefc_entityIdObj = entityIdObj;
    qefc_entityTitleObj = entityTitleObj;
    qefc_callbackCommand = callbackCommand;

	var top = f_getPosition(positionCtrl, 'Top') + 25;
	var left = f_getPosition(positionCtrl, 'Left');
    var $frame = $('#queryEntityFrameCommon');
    $frame.css('top' , top).css('left' , left).show(100);

    if(mention!="") {
        $frame.find('#name').val(mention);
        queryCandidateByInput(false);
    }
}

// 关闭查询实体框
function hideQueryEntityFrameCommon(){
	$('#queryEntityFrameCommon').toggle(100);
}


// 向后台提交查询
function queryCandidateByInput(fuzzy){
    var $frame = $("#queryEntityFrameCommon");
	// 取得用户输入文本
    var name = $frame.find('#name').val();
	if(name == "") return false;
	// 清空原来的结果
    var $_candList = $frame.find("#candsList").empty();
	// 设置候选列表的提示信息
	var $_infoNode = $frame.find('#queryTip').html("数据加载中，请稍候....");
	fuzzy = fuzzy || false;	// 保证fuzzy变量有值
	// Ajax查询
	AjaxInterface.getCandidateList(name, fuzzy, function(result){
		// 解析得到的JSON串为js对象
		var candidateEntityList = $.parseJSON(result);
		// 遍历candidateEntityList数组中的元素，构造新的HTML文本，用于显示在消息提示框
		if(candidateEntityList && candidateEntityList.length > 0){
			$_infoNode.html("查询结束，请从下列候选项中选择：");
			for(var i = 0; i < candidateEntityList.length; i++){
				$_candList.append(genCandHtmlText(candidateEntityList[i].id, candidateEntityList[i].title, genTipText(candidateEntityList[i])));
			}
		}
		else{
			// 将提示节点设置为新的内容
			$_infoNode.html("很抱歉，查询不到相关数据！");
		}
		$_candList.css('height','200px').css('overflow-y', 'scroll');
	});

    return false;

	/**
	 * 生成一个li元素的HTML文本，该元素对应一个候选项
	 * @param {} id 该候选项的实体id
	 * @param {} name 候选项名称
	 * @param {} tip 候选项附属信息
	 * @return {}
	 */
	function genCandHtmlText(id, name, tip){
		var htmlText = "<li style='cursor:pointer' onclick='setQEFCEntity(" + id + ", \"" + name +"\");'" + " title='请选择'>" +
				"<b>" + name + "</b>" +
				"<span>（" + tip.replace(/(')/g, "") + "）</span>" +
				"</li>";
		return htmlText;
	}

	/**
	 * 由候选实体项和标注类型生成提示文本
	 * @param {} candidate 候选实体项
	 * @return {String}
	 */
	function genTipText(candidate){
		if(candidate.abst){
			return candidate.abst.replace(/）/g, '');
		}
		return '';
	}
}


/**
 * 根据点击的项，设置值
 * @param entityId
 * @param entityTitle
 * @param callbackCommand 回调命令
 */
function setQEFCEntity(entityId, entityTitle){
    if(qefc_entityIdObj){
        qefc_entityIdObj.value = entityId;
    }

    if(qefc_entityTitleObj){
        qefc_entityTitleObj.value = entityTitle;
    }

    if(qefc_callbackCommand) {
        try{
            eval(qefc_callbackCommand);
        } catch(e) {
            alert("执行回调命令出错：\n"+e);
        }
    }

    hideQueryEntityFrameCommon();
}

function resetValueById(id1, id2){
    var obj1 = document.getElementById(id1);
    if(obj1){
        obj1.value = "";
    }

    var obj2 = document.getElementById(id2);
    if(obj2){
        obj2.value = "";
    }
}

function resetValueByIdOrName(id1, id2){
    var obj1 = getOnlyElementByIdOrName(id1);
    if(obj1){
        obj1.value = "";
    }

    var obj2 = getOnlyElementByIdOrName(id2);
    if(obj2){
        obj2.value = "";
    }
}


/**
 * 按照值选择下拉框
 * @param selectObj
 * @param value
 */
function selectByValue(selectObj, value){
    for(var i=0; i<selectObj.options.length; i++) {
        var option = selectObj.options[i];
        if(option.value == value){
            selectObj.selectedIndex = i;
            return;
        }
    }
}


/**
 * 获得下拉框选值
 * @param selectObj
 */
function getSelectedValue(selectObj){
    return selectObj.options[selectObj.selectedIndex].value;
}


/**
 * 获得下拉框选值
 * @param selectObj
 */
function getSelectedValueBySelectId(selectId){
    var selectObj = getElementById(selectId);
    if(selectObj) {
        return selectObj.options[selectObj.selectedIndex].value;
    } else {
        alert("Can not find the control by id: "+selectId);
        return null;
    }
}
