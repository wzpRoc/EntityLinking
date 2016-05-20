/**
 * 系统管理员页面的一些JS功能函数
 * 开发人：彭程
 * 开发日期：2011-10-24
 */

function selAll(){
	$(':checkbox').attr('checked', 'checked');
	return false;
}

function noSelAll(){
	$('input:checked').click();
	return false;
}

function debugInfo(){
	alert('该功能正在开发，请您耐心等待...');
	return false;
}

/**
 * 小组添加验证
 */
function validateGroupAdd(){
	// 小组名称必须填写
	if($("#title").val() == ""){
		alert("请填写小组名称！");
		return;
	}
	
	$("#add-item-form")[0].submit();
}

// 删除小组操作
function deleteGroup(){
	// 询问是否确认删除
	if(confirm("确认删除该小组吗？")){
		return true;
	}
	return false;
}

/**
 * 用户添加验证
 */
function validateUserAdd(){
	// 小组名称必须填写
	if($("#username").val() == "" || $("#password").val() == ""){
		alert("请填写用户名和密码！");
		return;
	}
	
	$("#add-item-form")[0].submit();
}

// 删除用户操作
function deleteUser(){
	// 询问是否确认删除
	if(confirm("确认删除该用户吗？")){
		return true;
	}
	return false;
}

// 批量删除用户操作
function deleteUsers(obj){
	// 获取所有选中的input
	var $_checkedList = $("tbody input:checkbox:checked");
	if($_checkedList.size()<1){
		alert("未选择要删除的用户");
		return false;
	}
	
	var idArray = [];
	$_checkedList.each(function(){
		idArray.push(this.id);
	});
	
	var idsStr = idArray.join(',');
	
	// 询问是否确认删除
	if(!confirm("确认删除这些用户吗？")){
		return false;
	}
	
	obj.href = "user!deleteUsers?ids=" + idsStr;
	
	return true;
}

// 是否更改了训练文本的标志位
var flag_need_retrain = false;
function setRetrainFlag(){
	flag_need_retrain = true;
	
	$("#bt_retrain").removeClass("button_grey");
	$("#doc_test").addClass("button_grey");
	$("#retrain_tip").html("模型训练样例文本已经更改！需要重新训练聚类模型！").show();
}
function resetReatrinFlag(){
	flag_need_retrain = false;
	$("#bt_retrain").addClass("button_grey");
	$("#doc_test").removeClass("button_grey");
	$("#retrain_tip").html("模型训练完成！").show();
}

/**
 * 训练新的模型
 */
function trainNewModel(){
	// 检查是否不需要训练
	if($("#bt_retrain").hasClass('button_grey'))return;
	
	// 获取聚类ID 语种ID和聚类相似度阈值
	var scId = $("#scInfo_id").val();
	var langId = $("#scInfo_lang").val();
	var simiThreshold=$("#scInfo_simiThreshold").val();
	
	$("#retrain_tip").html("正在训练聚类模型，请稍后...").show();
	AjaxInterface.trainSCModel(scId, langId, simiThreshold, function(success){
		if(success){
			resetReatrinFlag();
		}
		else{
			$("#retrain_tip").html("模型训练失败，请检查后台信息！").show();
		}
	});
}

/**
 * 测试模型
 */
function testModel(){
	if(flag_need_retrain){
		return;
	}
	// 提取标题和正文
	var t_text = $("#doc_title").val();
	var c_text = $("#doc_content").val();
	// 验证
	if(t_text == ""){
		alert("请输入测试文档的标题");
		return;
	}
	
	AjaxInterface.testSCModel(t_text + ". " + c_text, $("#scInfo_id").val(), function(simi){
		alert("模型测试完毕，相似度为：" + simi);
	});
}

/**
 * 验证用户输入
 */
function validateSClusterAdd(){
	// 必须写聚类名称
	if($('#title').val()==""){
		alert("请填写聚类的名称！");
		return;
	}
	
	// 检查是否已经训练了模型
	if(flag_need_retrain){
		if(confirm("模型的训练样例文本已经被更改！模型必须重新训练，现在开始训练吗？")){
			trainNewModel();
		}
		return;
	}
	$("#add-item-form")[0].submit();
	return;
}

/**
 * 验证添加内容是否正确
 */
function validateTrainDocAdd(){
	// 提取标题和正文
	var t_text = $("#doc_title").val()
	var c_text = $("#doc_content").val()
	// 验证
	if(t_text == ""){
		alert("请输入训练文档的标题");
		return	
	}
	
	// 提取聚类的信息
	var scTrainDoc = {
		"scId" : $("#scInfo_id").val(),
		"title" : t_text,
		"content" : c_text
	}
	
	// 发送Ajax请求
	AjaxInterface.insertClusterDoc(scTrainDoc, function(blm){
		if(!blm.success){
			alert("添加失败，错误信息：" + blm.message);
			return;
		}
		// 在列表中添加一行
		var trainDoc = blm.data
		var $_tr = $("#tr_hidden").clone().removeClass('none');
		$_tr.attr('id', 'train_tr_' + trainDoc.id);
		$_tr.find('.checkbox').attr('id', trainDoc.id);
		$_tr.find('.label').attr('id', trainDoc.id).html($("#train_doc_list").children().size()-1);
		$_tr.find('.doc_title').html(trainDoc.title);
		$_tr.find('.opt_bt').attr("_doc_id", trainDoc.id);
		// 控制显示长度
		var c_text = trainDoc.content.length > 80 ? trainDoc.content.substring(0,80) + "..." : trainDoc.content;
		$_tr.find('.doc_content').html(c_text).attr('title', trainDoc.content.replace(/"/g, '\\"'));
		$("#train_doc_list").append($_tr);
		// 清空输入框
		$("#doc_title").val("");
		$("#doc_content").val("");
		
		setRetrainFlag();
	});
}

/**
 * 编辑某个训练样本
 * @param {} obj
 */
function editTrainDoc(obj){
	var $_tr = $(obj).closest('tr');
	var trainDoc = {
		'id' : $(obj).attr('_doc_id'),
		'scId' : $("#scInfo_id").val(),
		'title' : $_tr.find('.doc_title').html(),
		'content' : $_tr.find('.doc_content').attr('title').replace(/\\"/g, '"')
	}
	// 将当前文本的标题和正文放到输入框中
	$("#doc_title").val(trainDoc.title);
	$("#doc_content").val(trainDoc.content);
	// 高亮当前行
	$_tr.addClass('cur_edit').siblings().removeClass('cur_edit');
	// 添加按钮改为编辑按钮
	$("#doc_update").show().siblings("#doc_insert").hide();
}

/**
 * 清空现在的文本输入框，同时清除编辑状态
 */
function clearTrainDoc(){
	$("#doc_title").val("");
	$("#doc_content").val("");
	$("#doc_insert").show().siblings("#doc_update").hide();
	$("#train_doc_list").children().removeClass('cur_edit');
}

/**
 * 验证提交请求
 */
function validateTrainDocUpdate(){
	// 提取标题和正文
	var t_text = $("#doc_title").val()
	var c_text = $("#doc_content").val()
	// 验证
	if(t_text == ""){
		alert("请输入训练文档的标题");
		return	
	}
	var $_tr = $("#train_doc_list .cur_edit");
	
	var scTrainDoc = {
		"id" : $_tr.attr('id').substring(9),
		"scId" : $("#scInfo_id").val(),
		"title" : t_text,
		"content" : c_text
	}
	
	// 发送Ajax请求
	AjaxInterface.updateClusterDoc(scTrainDoc, function(blm){
		if(!blm.success){
			alert("更新失败，错误信息：" + blm.message);
			return;
		}
		$_tr.find('.doc_title').html(scTrainDoc.title);
		// 控制显示长度
		var c_text = scTrainDoc.content.length > 80 ? scTrainDoc.content.substring(0,80) + "..." : scTrainDoc.content;
		$_tr.find('.doc_content').html(c_text).attr('title', scTrainDoc.content.replace(/"/g, '\\"'));
		
		// 进行清空复位操作
		clearTrainDoc();
		
		setRetrainFlag();
	});
}

/**
 * 删除某个训练文本
 * @param {} obj a元素
 */
function delTrainDoc(obj){
	// 确认删除询问
	if(!confirm("确认删除该篇训练文本么？")){
		return false;
	}
	var t_id = $(obj).attr('_doc_id');
	
	// Ajax请求
	AjaxInterface.deleteClusterDoc(t_id, function(blm){
		if(!blm.success){
			alert("删除失败，错误信息：" + blm.message);
			return;
		}
		$("#train_tr_" + t_id).remove();
		
		setRetrainFlag();
	});
	
	return false;
}