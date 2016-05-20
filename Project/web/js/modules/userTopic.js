/***********************************************************************************
 *名称：userTopic.js
 *功能：智库人员的小组发帖页面若干交互功能
 *开发者：彭程
 *时间：2012-09-25
 ***********************************************************************************/
 

// 上传的文书处理操作
function validateAttach(){
	// 先取到上传的文书文件
	if($('#fileToUpload').size()==0)return true;
	var myFile = $('#fileToUpload')[0].files.item(0);
	if(!myFile)return true;
	
	// 判断文件类型
	var exName = myFile.name.substring(myFile.name.lastIndexOf('.')+1);
	if(exName != 'txt' && exName != 'doc' && exName != "docx" && exName != "xml" && exName != "xls" && exName != "sml" && exName != "rar" && exName != "zip" && exName != "pdf"&&exName!="chm"
	 &&exName != 'TXT' && exName != 'DOC' && exName != "DOCX" && exName != "XML" && exName != "XLS" && exName != "SML" && exName != "RAR" && exName != "ZIP" && exName != "PDF"&&exName!="CHM"){
		alert("只能上传后缀名为doc、docx、xml、xls、sml、pdf、rar、zip、chm的文件！");
		return false;
	}
	
	// 判断文件大小不能大于10M
	var fileSize = myFile.size / 1024 / 1024;
	if(fileSize >= 10){
		alert("上传文档的大小不能超过10M！");
		return false;
	}
	if(fileSize==0){
		alert("上传文件内容为空！");
		return fasle;
	}
	
	// 填充文件名称
	$("#myFileName").val(myFile.name);
	return true;
}

// 提交验证
function validateUtopicInsert(){
	// 从上到下
	// 必须填文书名称
	var title = $("#ut_title").val();
	//删去空格
	if(title!=null){
		title=title.replace(/(^\s*)|(\s*$)/g, "");
	}
	if(title==""){
		alert("请您填写标题！");
		return;
	}
	// 上传文件验证
	if(!validateAttach()){
		return;
	}
	// 把tagMap中的文本组装成字符串
	buildTagsStr();
	
	// 提交操作
	$("#add-item-form")[0].submit();
}

// 把tagMap中的文本组装成字符串
function buildTagsStr(){
	var tagsStr = "";
	for(var x in tagMap){
		tagsStr += x + ",";
	}
	if(tagsStr != ""){
		tagsStr = tagsStr.substring(0, tagsStr.length-1);
	}
	$('#tagsStr').val(tagsStr);
}

// 验证回复
function validateReply(){
	// 回复内容不能为空
	var content = $('#replyToBody').val();
	if(content == ""){
		alert("评论内容不能为空！");
		return false;
	}
	
	return true;
}

// 标签操作
// 标签映射表，用于判重
var tagMap = {};

// 点击添加按钮后，新加一个项的操作，用jQuery绑定事件
function addNewTag(){
	// this为当前li元素
	var $_li = $(this);
	// 构造一串新HTML文本
	var $_newLi = $('<li class="j_tag input"><input type="text" size="10"/></li>');
	var $_input = $_newLi.find('input');
	// TODO 为$_newLi添加事件响应函数
	bindListenerForInput($_input);
	
	// 加到列表尾部
	$_newLi.insertBefore($_li);
	$_li.hide();
	$_input.focus();
}

/**
 * 标签输入框的各类事件响应操作
 * @param {} $_input
 */
function bindListenerForInput($_input){
	// 散焦操作
	$_input.blur(function(){
		saveTag(this);
	});
	
	// 回车事件
	$_input.keydown(function(e){
		if(e.keyCode == 13){
			saveTag(this);
			return false;	// 避免提交表单操作
		}
	});
}

/**
 * 保存标签文本的操作
 * @param {} obj 当前的input元素
 */
function saveTag(obj){
	var text = $(obj).val();
	// 文本为空，或者已经存在，则移除当前li元素，并显示添加按钮
	if(text == "" || tagMap[text]){
		$(obj).closest('li').remove();
		$('#tag-add-bt').show();
		return false;
	}
	
	// 否则将input换成span，并切换父li元素的class属性
	var $_li = $(obj).closest('li').removeClass('input');
	$(obj).replaceWith("<span>" + text + "</span>");
	$_li.append('<a onclick="removeTag(this)" href="javascript:;"></a>');
	
	// 将text放到tagMap中
	tagMap[text] = true;
	$('#tag-add-bt').show();
}

/**
 * 删除标签操作
 * @param {} obj 当前被点击的a元素
 */
function removeTag(obj){
	var text = $(obj).siblings().html();
	delete tagMap[text];
	$(obj).parent().remove();
}

// 加载tag
function loadTag(){
	$('.topic-tag-list li.j_tag').each(function(){
		var tagText = $(this).children('span').html();
		tagMap[tagText] = true;
	});
}

$(document).ready(function(){
	$('#tag-add-bt').click(addNewTag);
	loadTag();
});