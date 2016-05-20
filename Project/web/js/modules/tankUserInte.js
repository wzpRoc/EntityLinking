/***********************************************************************************
 *名称：tankUserInte.js
 *功能：智库人员的兴趣管理若干功能
 *开发者：彭程
 *时间：2012-08-05
 ***********************************************************************************/

// 添加标签
function addKeyword(){
	// 取得文本输入
	var tagText = $('#keywordText').val();
	if(!tagText)return;
	AjaxInteManage.addInteKeyword(tagText, function(blMessage){
		if(blMessage.success){
			// 构造新li元素
			var itemId = blMessage.data.id;
			var newTagItem = $('<li>' + tagText + '<span onclick="delKeyword(this,' + itemId + ')">X</span></li>');
			$('#inte_keyword_list').append(newTagItem).show();
			// 输入框置空
			$('#keywordText').val('');
		}
		else{
			alert('后台添加关键词操作失败！');
		}
	});
}

// 删除标签
function delKeyword(obj, kwdId){	
	// Ajax删除
	AjaxInteManage.deleteInteTerm(kwdId, function(blMessage){
		if(blMessage.success){
			$(obj).parent().remove();
			if($('#inte_keyword_list').children().size()==0){	// 如果分类被删光了，则隐藏分类列表和分类标题
				$('#inte_keyword_list').hide();
			}
		}
		else{
			alert('后台删除关键词操作失败！');
		}
	});
}

// 关闭查询实体框
function hideResultFrame(obj){
	$(obj).siblings('.resultFrame').hide();
	$(obj).hide();
}

// 添加实体
function addEntity(id, name, type, tip){
	// 取得实体信息
	AjaxInteManage.addInteEntity(id, name, type, function(blMessage){
		if(blMessage.success){
			// 构造新li元素
			var itemId = blMessage.data.id;
			var newTagItem = $('<li title="' + tip + '">' + name + '<span onclick="delEntity(this,' + itemId + ')">X</span></li>');
			$('#inte_entity_list').append(newTagItem).show();
			// 输入框置空
			$('#t_entity_name').val('').siblings('.resultFrame').hide().siblings('.toggle_result').hide();
		}
		else{
			alert('后台添加实体操作失败！');
		}
	});
}

// 删除实体
function delEntity(obj, eid){
	// Ajax删除
	AjaxInteManage.deleteInteTerm(eid, function(blMessage){
		if(blMessage.success){
			$(obj).parent().remove();
			if($('#inte_entity_list').children().size()==0){	// 如果分类被删光了，则隐藏分类列表和分类标题
				$('#inte_entity_list').hide();
			}
		}
		else{
			alert('后台删除实体操作失败！');
		}
	});
}

// 向后台提交查询
function queryCandidateByInput(fuzzy){
	// 取得用户输入文本
	var name = $('#t_entity_name').val();
	if(name == "")return;
	// 取得实体类型
	var type = $('#s_entity_type').val();
	var $_resultFrame = $('#t_entity_name').siblings('.resultFrame').show();
	$('#t_entity_name').siblings('.toggle_result').show();
	// 清空原来的结果
	var $_candList = $_resultFrame.children(".candsList").empty();
	// 设置候选列表的提示信息
	var $_infoNode = $_resultFrame.children('.queryTip').html("数据加载中，请稍候....");
	fuzzy = fuzzy || false;	// 保证fuzzy变量有值
	// Ajax查询
	AjaxInterface.getCandidateList(name, type, 1, fuzzy, function(result){
		// 解析得到的JSON串为js对象
		var cands = $.parseJSON(result);
		// 遍历cands数组中的元素，构造新的HTML文本，用于显示在消息提示框
		if(cands && cands.length > 0){
			$_infoNode.html("查询结束，请从下列实体中选择：");
			for(var i = 0; i < cands.length; i++){
				$_candList.append(genCandHtmlText('addEntity',cands[i].id, cands[i].name_zh || cands[i].name_en, genTipText(cands[i]), type));
			}
		}
		else{
			// 将提示节点设置为新的内容
			$_infoNode.html("很抱歉，查询不到相关实体！");
		}
	});
}

/**
 * 生成一个li元素的HTML文本，该元素对应一个候选项
 * @param {} clickFunc 点击的响应方法名称
 * @param {} id 该候选项的实体id
 * @param {} name 候选项名称
 * @param {} tip 候选项附属信息
 * @param {} type 实体类型
 * @return {}
 */
function genCandHtmlText(clickFunc, id, name, tip, type){
	var htmlText = "<li onclick='" + clickFunc +"(\"" + id + "\", \"" + name + "\", \"" + type +"\", \"" + tip.replace(/(<([^>])+>)/g, '') + "\");'" + " title='点击该项以进行确认！'>" +
			"<b>" + name + "</b>" +
			(tip == '' ? '' : ("<span>（" + tip + "）</span>")) +
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
	else{
		return "ID:" + candidate.id;
	}
	return '';
}

// 添加作者
function addAuthor(id, name, type, tip){
	// 取得实体信息
	AjaxInteManage.addInteEntity(id, name, type, function(blMessage){
		if(blMessage.success){
			// 构造新li元素
			var itemId = blMessage.data.id;
			var newTagItem = $('<li title="' + tip + '">' + name + '<span onclick="delEntity(this,' + itemId + ')">X</span></li>');
			$('#inte_author_list').append(newTagItem).show();
			// 输入框置空
			$('#t_author_name').val('').siblings('.resultFrame').hide().siblings('.toggle_result').hide();
		}
		else{
			alert('后台添加作者操作失败！');
		}
	});
}

// 删除作者
function delAuthor(obj, eid){
	// Ajax删除
	AjaxInteManage.deleteInteTerm(eid, function(blMessage){
		if(blMessage.success){
			$(obj).parent().remove();
			if($('#inte_author_list').children().size()==0){	// 如果分类被删光了，则隐藏分类列表和分类标题
				$('#inte_author_list').hide();
			}
		}
		else{
			alert('后台删除作者操作失败！');
		}
	});
}

// 查询作者
function queryAuthorByInput(fuzzy){
	// 取得用户输入文本
	var name = $('#t_author_name').val();
	var $_resultFrame = $('#t_author_name').siblings('.resultFrame').show();
	$('#t_author_name').siblings('.toggle_result').show();
	// 清空原来的结果
	var $_candList = $_resultFrame.children(".candsList").empty();
	// 设置候选列表的提示信息
	var $_infoNode = $_resultFrame.children('.queryTip').html("数据加载中，请稍候....");
	fuzzy = fuzzy || false;	// 保证fuzzy变量有值
	// Ajax查询
	AjaxInteManage.queryCandAuthors(name, fuzzy, function(blMessage){
		if(blMessage.success){
			// 解析得到的JSON串为js对象
			var cands = blMessage.data;
			// 遍历cands数组中的元素，构造新的HTML文本，用于显示在消息提示框
			if(cands && cands.length > 0){
				$_infoNode.html("查询结束，请从下列作者中选择：");
				for(var i = 0; i < cands.length; i++){
					$_candList.append(genCandHtmlText('addAuthor',cands[i].id, cands[i].name, genTipText(cands[i]), 'a'));
				}
			}
			else{
				// 将提示节点设置为新的内容
				$_infoNode.html("很抱歉，查询不到相关作者！");
			}
		}
	});
}