/**
 * 高级用户页面的一些JS功能函数
 * 开发人：彭程
 * 开发日期：2011-10-26
 */


/**
 * 在新闻详细页的各个项目中“添加新项”的响应函数
 * 功能：添加一栏用于用户输入
 * @param obj 当前的锚点<a>元素，即"添加新项"
 */
function addNewItem(obj){
	// 先获取到tr父元素
	var $_tr = $(obj).parents('.J_add_item');
	// 然后复制第一个兄弟元素的内容
	var trElement = document.createElement('tr');
	trElement.innerHTML = $_tr.siblings().eq(1).html();
	// 这里必须要先添加元素，在去处理元素中子元素的样式。这样符合逻辑，不会出现样式问题。
	$(trElement).insertBefore($_tr);
	// 将该元素中的div元素清空
	// 先要获取td元素，还要防止清空掉"编辑""删除"所在的那个td
	var $_children = $(trElement).children();
	for(var i = 0; i < $_children.size(); i++){
		// 如果是最后一个元素，则不做处理
		if(i==$_children.size()-1){
			// 找到"编辑"按钮并修改样式
			var aElement = $_children.eq(i).children().children()[0];
			aElement.innerHTML = "确认";
			$(aElement).css('font-weight', 'bold');
			continue;
		}
		// 其余的节点，在div中添加input元素
		var divElement = $_children.children()[i];
		divElement.innerHTML = "";
		divElement.appendChild(getNewInputElement(divElement.parentNode, ""));
	}
	$('.submit_tip').show();
	return false;
}

/**
 * 现有的每项信息支持双击后变成输入框
 * @param {} obj 当前点击的锚点<a>元素，即"编辑"
 * @return {Boolean}
 */
function editOneRow(obj){	
	if(!obj)return false;
	// 首先找到当前节点的父<td>节点
	var cNode = obj;
	while(cNode.tagName.toLowerCase()!="td"){
		cNode = cNode.parentNode;
	}
	// 然后得到<td>节点的兄弟元素
	var $_siblings = $(cNode).siblings();
	
	// 判断是“编辑”还是“确认”
	if(obj.innerHTML == "确认"){	// 如果当前元素内容为"确认"，说明用户已经修改好，需要提交
		obj.innerHTML = "编辑";
		$(obj).css('font-weight', 'normal');
		// 除去输入框
		$_siblings.each(function(){
			// 获得子div元素
			var divElement = $(this).children()[0];
			var editedText = $(divElement).children().val();
			divElement.innerHTML = editedText;
		});
		
		// 提交修改
		return debugInfo();
	}
	
	// 当前元素内容为"编辑"，用户开始进行编辑
	// 将其中的div元素内容变成一个输入框
	$_siblings.each(function(){
		// 获得子div元素
		var divElement = $(this).children()[0];
		var origText = divElement.innerHTML;
		divElement.innerHTML = "";
		// 需要控制一下input元素的宽度
		var inputElement = getNewInputElement(divElement, origText);
		divElement.appendChild(inputElement);
	});
	
	// 将节点的名字改为确认
	obj.innerHTML = "确认";
	$(obj).css('font-weight', 'bold');
	
	$('.submit_tip').show();
	return false;
}

/**
 * 获取一个新的input元素
 * @param {} obj 所添加的input元素的父元素
 * @param {} text input元素的初始显示内容，即value指
 * @return {}
 */
function getNewInputElement(obj, text){
	var inputElement = document.createElement('input');
	inputElement.className = 'inputArea';
	inputElement.type = "text";
	inputElement.value = text;
	inputElement.style.width = ($(obj).width() - 10) + "px";
	
	return inputElement;
}

// 重写列表聚焦函数，处理不同列表的特殊要求
function specTitleProc(obj){
    if(!obj) {
        return;
    }
	// 专题列表的特殊处理
	if(obj.innerHTML.indexOf("专题")>-1){
		// 显示"添加新专题"元素
		$(obj).children().show();
	}
	else{
		$('#subjectTitle').children().hide();
	}

}

// 验证用户的输入
function validInput(){
	// 至少输入一个名字
	var hasName = false;
	$('.entityName').each(function(){
		var $_self = $(this);
		if($_self.val() != "")hasName = true;
		// 如果该名字在列表中不存在，则添加一个新名字
		if(!entityNameList[$_self.val()] && $_self.val() != ""){
			entityNameList[$_self.val()] = 1;
			addNewAliaseItem($_self.val(), $_self.attr('_langId'), null, true);
		}
	});
	// 如果没有输入任何名字，提示
	if(!hasName){
		alert("请至少输入一个名字！");
		return false;
	}
	// 如果有正在添加的名字
	var flag_addingName = checkHasAddingName();
	if(flag_addingName){
		$('#tip_addingName').show();
	}
	return !flag_addingName;
}

// 检查是否还有正在提交的名字
function checkHasAddingName(){
	// 检查是否有正在添加的名字
	var addingSize = $('#aliaseList .adding').size();
	if(addingSize > 0){
		return true;
	}
	return false;
}

// 整理国家列表
function initCountryList(){
	// 每个洲的国家列表
	var $_origList = $('#origList');
	$('.simpleCountryList').each(function(){
		var obj = this; // this为ul元素
		var idStr = obj.id;
		$(obj).children().remove();
		// 将class为idStr的li元素，加到当前列表下
		$_origList.children('.'+idStr).appendTo(this);
	});
	
	// 将原始列表移除
	$_origList.remove();
}

function justWidthOfItem(){
	// 调整li元素的宽度
	$('#countryFrame li').each(function(){
		if($(this).width()>70){
			$(this).width('146');
		}
	});
}

// 显示国家列表框
function showCountryFrame(){
	$('#countryFrame').show();
	justWidthOfItem();
}

// 关闭国家列表
function hideCountryFrame(){
	$('#countryFrame').hide();
}

// 设置国家
function setCountry(id, name){
	$('#countryId').val(id);
	$('#countryName').val(name);
	$('#countryFrame').hide();
}

// 重置国家
function resetCountry(){
	$('#countryId').val('');
	$('#countryName').val('');
	$('#countryFrame').hide();
}

// 显示查询实体框
function showQueryEntityFrame(obj){
	var top = f_getPosition(obj, 'Top') - 111;
	$('#queryEntityFrame').css('top' , top).show();
	var ancestorNames = $('#ancestorNames').val();
	if(ancestorNames != ""){
		$("#currentAncestors").show().find('#currentAncNames').html(ancestorNames);
	}
	else {
		$("#currentAncestors").hide();
	}
}

// 关闭查询实体框
function hideQueryEntityFrame(){
	$('#queryEntityFrame').hide();
}

// 设置上级地点
function setAnsestor(id, name, ancestorNames){
	$('#parentId').val(id);
	var ancestorNames = (ancestorNames == "null") ? name : (ancestorNames + " > " + name);
	$('#ancestorNames').val(ancestorNames);
//	$('#parentName').html("（" + name + "）");
	$('#queryEntityFrame').hide();
}

// 重置上级地点
function resetAncestor(){
	$('#parentId').val(0);
	$('#ancestorNames').val('');
//	$('#parentName').html('');
	$('#queryEntityFrame').hide();
}

// 向后台提交查询
function queryCandidateByInput(fuzzy){
	// 取得用户输入文本
	var name = $('#queryEntityFrame #name').val();
	if(name == "")return;
	// 清空原来的结果
	var $_candList = $("#queryEntityFrame #candsList").empty();
	// 设置候选列表的提示信息
	var $_infoNode = $('#queryEntityFrame #queryTip').html("数据加载中，请稍候....");
	fuzzy = fuzzy || false;	// 保证fuzzy变量有值
	// Ajax查询
	AjaxInterface.getCandidateList(name, 'Location', 1, fuzzy, function(result){
		// 解析得到的JSON串为js对象
		var cands = $.parseJSON(result);
		// 遍历cands数组中的元素，构造新的HTML文本，用于显示在消息提示框
		if(cands && cands.length > 0){
			$_infoNode.html("查询结束，请从下列地点中选择：");
			for(var i = 0; i < cands.length; i++){
				$_candList.append(genCandHtmlText(cands[i].id, cands[i].name_zh || cands[i].name_en, cands[i].ancestorNames, genTipText(cands[i])));
			}
		}
		else{
			// 将提示节点设置为新的内容
			$_infoNode.html("很抱歉，查询不到相关地点！");
		}
	});
	
	/**
	 * 生成一个li元素的HTML文本，该元素对应一个候选项
	 * @param {} id 该候选项的实体id
	 * @param {} name 候选项名称
	 * @param {} tip 候选项附属信息
	 * @return {}
	 */
	function genCandHtmlText(id, name, ancestorNames, tip){
		var htmlText = "<li onclick='setAnsestor(\"" + id + "\", \"" + name +"\", \"" + ancestorNames + "\");'" + " title='点击该项以进行确认！'>" +
				"<b>" + name + "</b>" +
				"<span>（" + tip + "）</span>" +
				"</li>";
		return htmlText;
	}
	
	/**
	 * 由候选实体项和标注类型生成提示文本
	 * @param {} candidate 候选实体项
	 * @param {} annoType 标注类型
	 * @return {String}
	 */
	function genTipText(candidate, annoType){
		if(candidate.prompt){
			var tipArray = candidate.prompt.replace(/）/g, '').split('（');
			return tipArray[tipArray.length-1];
		}
		return '';
	}
}


// 一行别名的初始dom结构
var $_initialAliaseDom;

// 添加一行
function editAliaseRow(obj){
	// 如果是“添加”
	var $_tr = $(obj).closest('tr');
	if(obj.innerHTML == "添加"){
		// 读取别名文本
		var aliase_text = $_tr.find('input').val();
		if(aliase_text == ""){
			alert('名字文本不能为空');
			return false;
		}
		else if(entityNameList[aliase_text]){
			alert('该名字已经存在');
			return false;
		}
		// 读取语种
		var langId = $_tr.find('select').val();
		var langText = $_tr.find('option[value="' + langId + '"]').html();
		// 添加一个别名
		addNewAliaseItem(aliase_text, langId);
		// 清空原来的内容
		$_tr.find('input').val('');
		$_tr.find('select option')[0].selected = "selected";
	}
	return false;
}

// 添加一个别名项
function addNewAliaseItem(aliaseText, langId, old, doSubmit){
	// 要向别名列表中添加一行
	var $_new_tr = $_initialAliaseDom.clone();	// 克隆一行
	var langText = $_new_tr.find('option[value="' + langId + '"]').html();
	$_new_tr.find('input').replaceWith(aliaseText);	// 更换<input>元素，填充为别名名称
	$_new_tr.find('select').replaceWith('<span _langId="' + langId +'">' + langText + '</span>');	// 更换<select>元素，填充为语种名称，并设置id
	$_new_tr.find('.editAliase').hide();	// 更换文本
	$_new_tr.find('.deleteAliase').show();
	$_new_tr.find('.queryItem').show();
	$_initialAliaseDom.before($_new_tr);	// 执行插入
	if(!old){	//Ajax操作
		$_new_tr.find('.deleteAliase').addClass('adding');	// 显示"删除按钮"
		$_new_tr.find('.deleteAliase a').html("正在添加...");	// 显示"删除按钮"
		AjaxInterface.addNewEntityName(aliaseText, entityId, langId, entityType, function(blMessage){
			if(blMessage.success){
				entityNameList[aliaseText] = 1;
				$_new_tr.find('.deleteAliase').show().removeClass('adding');	// 显示"删除按钮"
				$_new_tr.find('.deleteAliase a').html("删除");	// 显示"删除按钮"
				if(doSubmit){	// 如果需要提交 则检查是否有正在添加的名字
					// 如果有名字，则不提交，否则接着验证
					if(!checkHasAddingName()){
						$('form')[0].submit(); 	// 提交表单
					}
				}
			}
		});
	}
}

// 重置别名项
function resetAliaseItem($_obj){
	var $_newInput = $_initialAliaseDom.find('input').clone();
	var $_newSelect = $_initialAliaseDom.find('select').clone();
	var aliaseText = $_obj.find('.J_aliaseText').html();
	var langId = $_obj.find('.J_lang span').attr('_langId');
	// 替换内容
	$_newInput.val(aliaseText);
	$_newSelect.find('option')[parseInt(langId)-1].selected = "selected";
	$_obj.find('.J_aliaseText').html('').append($_newInput);
	$_obj.find('.J_lang span').replaceWith($_newSelect);
	$_obj.find('.editAliase').show();	// 更换文本
}

// 删除一个别名项
function removeAliaseItem(obj){
	var $_tr = $(obj).closest('tr');
	var aliaseText = $_tr.find('.J_aliaseText').html();
	//Ajax操作
	AjaxInterface.deletEntityName(aliaseText, entityId, entityType, function(blMessage){
		if(blMessage.success){
			entityNameList[aliaseText] = null;
		}
	});
	
	$_tr.remove();
	return false;
}

/**
 * 按名字查询别名对应的文档列表
 * @param {} obj 当前被点击的<a>元素
 */
function queryDocsWithAliase(obj){
	// 先找到实体名称
	var $_tr = $(obj).closest('tr');
	var aliaseText = $_tr.find('.J_aliaseText').html();
	if(!aliaseText || aliaseText=="")return false;
	// 由实体id和实体名称构造<a>元素的链接文本
	obj.href = "docConfirm!list?enableSession=0&condition.entityType=" + entityType.charAt(0) + "&condition.entityId=" + entityId + "&condition.entityName=" + aliaseText + "&condition.confirmUserId=&condition.confirmState=";
	return true;
}

// DTO名字表
var DTONameList = ["", "", "", "", ""];
// 所有名字列表
var entityNameList = {};

$(document).ready(function(){
	$_initialAliaseDom = $('#aliaseList').children().last();	// 当前添加行的一个拷贝
	// 添加dto名称
//	$('.entityName').each(function(){
//		var $_self = $(this);
//		// 添加一行
//		if($_self.val() != ""){
//			var langId = parseInt($_self.attr('_langId'));
//			addNewAliaseItem($_self.val(), langId, $_self.attr('id'));
//			DTONameList[langId] = $_self.val();
//			entityNameList[$_self.val()] = 1;
//		}
//	});
	// 为每个.entityName对象，添加聚焦和散焦操作
//	$('.entityName').keyup(function(){
//		var $_self = $(this);
//		// 对应的别名项也要更新
//		if($('#tr_'+ $_self.attr('id')).size()==0){
//			addNewAliaseItem($_self.val(), $_self.attr('_langId'), $_self.attr('id'));
//		}
//		var $_tr = $('#tr_'+ $_self.attr('id'))
//		if($_self.val() == ""){
//			$_tr.find('.deleteAliase a').click();
//		}
//		else{
//			// 设置新的文本
//			$_tr.find('.J_aliaseText').html($_self.val());
//		}
//	});

	$('.entityName').blur(function(){
		var $_self = $(this);
		// 如果该名字不存在，则添加一行
		if(!entityNameList[$_self.val()] && $_self.val() != ""){
			entityNameList[$_self.val()] = 1;
			addNewAliaseItem($_self.val(), $_self.attr('_langId'));
		}
	});
	
	// 加载名字列表
	if(window.entityId){	// 如果实体id存在
		AjaxInterface.loadEntityNameList(entityId, entityType, function(blMessage){
			if(blMessage.success){
				// 取得名字列表
				var nameList = blMessage.data;
				for(var i = 0; i < nameList.length; i++){
					var eName = nameList[i];
					addNewAliaseItem(eName.name, eName.languageId, true);
					entityNameList[eName.name] = 1;
				}
			}
		});
		// 加载该实体对应的国家
		// 检查countryId
		var countryId = $('#countryId').val();
		if(countryId>0){
			var countryName = $('#countryFrame').find('#'+countryId).click();
		}
	}
});

/**
 * 通知添加验证
 */
function validateNoticeAdd(){
	// 小组名称必须填写
	if($("#title").val() == ""){
		alert("请填写通知标题！");
		return;
	}
	
	$("#add-item-form")[0].submit();
}

function deleteSimpleCluster(){
	return confirm("确认删除该聚类吗？");
}

// 删除通知操作
function deleteNotice(){
	// 询问是否确认删除
	if(confirm("确认删除该通知吗？")){
		return true;
	}
	return false;
}

function debugInfo(){
	alert('该功能正在开发，请您耐心等待...');
	return false;
}