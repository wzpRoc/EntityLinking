/***********************************************************************************
 *名称：tankUserGroup.js
 *功能：智库人员的小组页面若干交互功能
 *开发者：彭程
 *时间：2012-09-20
 ***********************************************************************************/


/**
 * 重置表单
 */
function resetForm(){
	$("#add-item-form")[0].reset();
}

// 点击“申请加入后的操作”
function onApplyGroup(obj, userId, groupId){
	// 向小组长发申请消息
	AjaxInterface.applyGroupMember(groupId, userId);
	
	alert("已向小组组长发送申请！");
	// 提示
	$(obj).html("已申请");
}

var memberMap = {};
// 成员管理的初始化工作
function doLoadMemberManage(){
	
	$("#s_user").chosen();
	
	// 将当前用户列表初始化
	$("#memberList li").each(function(){
		memberMap[$(this).attr('id')] = true;
	});
	
	$.subject.init();
	
	$("#bt_add").click(addMember);
}

// 添加成员操作
function addMember(){
	// 获取选择的ID
	var s_uid = $("#s_user").val();
	var s_name = $("#s_user :selected").html();
	if(memberMap[s_uid]){
		alert("该用户已经是本小组成员！");
		return false;
	}
	// 确认询问
	if(!confirm("确认将" + s_name + "添加到本小组中吗？")){
		return false;
	}
	
	AjaxInterface.groupAddMember(userGroupId, s_uid, function(blMessage){
		if(blMessage.success){
			var member = blMessage.data;
			// 在列表中添加	
			$("#memberList").append(
				'<li>'  + 
				member.username + '（' + member.name + '）' +
				'<a href="javascript:void(0);" onclick="removeMember(this,' + member.id + ')"></span>' +
				'</li>'
			);
			memberMap[member.id] = true;
		}
		else {
			alert(blMessage.message);
		}
	});
}

/**
 * 
 * @param {} obj
 * @param {} uid
 */
function removeMember(obj, uid){
	var $_li = $(obj).parent();
	var name = $_li[0].childNodes[0].textContent;
	
	// 确认询问
	if(!confirm("确认将" + name + "从本小组中删除吗？")){
		return false;
	}
	
	AjaxInterface.groupRemoveMember(userGroupId, uid, function(blMessage){
		if(blMessage.success){
			$_li.remove();
			delete memberMap[uid];
		}
		else {
			alert(blMessage.message);
		}
	});
}

// 显示主题列表
$.namespace('$.subject');
$.subject = {
	// 以有的主题ID
	subIdMap : {},
	
	/**
	 * 显示主题列表框
	 */
	showBox : function(){
		// 向后台请求主题列表
		AjaxInterface.getSubjectList(function(blMessage){
			if(blMessage.success){
				var subjects = blMessage.data;
				// 构造HTML文本
				var $_subject_select_box = $('<div><div id="group_subject_select_box" class="p10 mb10"><h5>请从列表中选择小组关注的主题：</h5><table id="subject_table" width="100%" cellspacing="1" cellpadding="3" border="0" align="center" class="tableborder"><thead><tr height="25" class="header"><td width="5%"><div align="center">序号</div></td><td width="20%"><div align="center">名称</div></td><td width="20%"><div align="center">备注</div></td><td width="8%"><div align="center">是否纳入<br>智库监测</div></td><td width="8%"><div align="center">仅&#12288;&#12288;限<br>智库文章</div></td><tr></thead><tbody id="J_subject_list"></tbody></table>' +
						'<p class="mt10 tc"><input type="button" onclick="$.subject.submit();" value="提交" name="combineSubmit"><input type="button" onclick="$.subject.box.close();" value="取消"><input type="button" onclick="$.subject.jump2Subject();" value="主题管理"></p>' +
						'</div></div>');
				var $_subject_list = $_subject_select_box.find('#J_subject_list');
				var count = 1;
				for(var i = 0; i < subjects.length; i++){
					var subject = subjects[i];
					// 如果已经在已选的列表中了，就不在添加。
					if($.subject.subIdMap[subject.id])continue;
					// 构造tr元素的html文本
					var trHtmlText = '<tr id="J_sub_' + subject.id + '" height="25" bgcolor="ffffff" style="background-color: rgb(255, 255, 255);" onmouseout="this.style.backgroundColor=\'#ffffff\'" onmouseover="this.style.backgroundColor=\'#DBEAF5\'">' +
						'<td><div align="center"><input type="checkbox" name="checkbox_id" id="id_' + subject.id + '" value="15">' +
						'<label>' + (count++) + '</lable>' +
						'</div></td>' +
						'<td><div align="left" class="J_subject_name">' +
						subject.name +
						'</div></td>' +
						'<td><div align="left">' +
						subject.description +
						'</div></td>' +
						'<td><div align="center">' +
						(subject.isTankSubject ? '是' : '否') +
						'</div></td>' +
						'<td><div align="center">' +
						(subject.tankArticleOnly ? '是' : '否') +
						'</div></td>' +
						'</tr>';
					// 加入到list中
					$_subject_list.append(trHtmlText);
				}
				// 打开对话框
				$.subject.box = artDialog( {
					id : 'subjectSelectContainer',
					title : '主题选择',
					content : $_subject_select_box.html()
				}); 
			}
		});
		
		return;
	},
	
	// 添加新主题
	jump2Subject : function (){
		window.open("advanced/subjectList", "_blank");
		$.subject.box.close();
	},
	
	/**
	 * 添加一个主题
	 * @param {} id
	 */
	addOneSubject : function (sid) {
		// 取得当前的主题名称
		var subject_name = $("#J_sub_" + sid).find(".J_subject_name").html();
		// 在主题列表中添加一个元素
		$("#subjectList").append(
			'<li>'  + 
			subject_name +
			' <a href="javascript:void(0);" onclick="$.subject.removeSubject(this,' + sid + ')"></span>' +
			'</li>'
		);
		
		$.subject.subIdMap[sid] = true;
	},
	
	/**
	 * 删除组关联
	 * @param {} obj
	 * @param {} sid
	 * @return {Boolean}
	 */
	removeSubject : function (obj, sid) {
		var $_li = $(obj).parent();
		var name = $_li[0].childNodes[0].textContent;
		
		// 确认询问
		if(!confirm("确认将主题" + name + "从本小组中删除吗？")){
			return false;
		}
		
		AjaxInterface.groupDeleteSubject(userGroupId, sid, function(blMessage){
			if(blMessage.success){
				$_li.remove();
				delete $.subject.subIdMap[sid];
			}
		});
	},
	
	/**
	 * 初始化 
	 */
	init : function() {
		$("#subjectList li").each(function(){
			$.subject.subIdMap[$(this).attr('id')] = true;
		});
	},
	
	// 提交
	submit : function (){
		var subject_names = [];
		var subject_ids = [];
		// 先取得各个主题的名称
		$('#J_subject_list input[type=checkbox]:checked').each(function(){
			var $_tr = $(this).closest('tr');
			var subName = $_tr.find('.J_subject_name').html();
			var subId = $_tr.attr('id');
			subId = subId.substring(subId.lastIndexOf('_') + 1);
			subject_names.push(subName);
			subject_ids.push(subId);
		});
		// 如果没选，则无响应
		if(subject_ids.length == 0){
			return;
		}
		// 合并数组
		var namesStr = subject_names.join('、');
		// 询问确认
		if(confirm("确认添加：" + namesStr + "，这" + subject_names.length + "个主题吗？")){
			// 异步向后台添加记录
			AjaxInterface.groupAddSubject(userGroupId, subject_ids, function(blMessage){
				if(!blMessage.success){
					alert('添加失败！请检查数据库操作问题！');
					return;
				}
				// 逐个添加主题
				for(var i = 0; i < subject_ids.length; i++){
					$.subject.addOneSubject(subject_ids[i]);
				}
				// 关闭添加框
				$.subject.box.close();
			});
		}
	}
}

   

$(document).ready(function(){
	if(methodType && methodType == "manage"){
		doLoadMemberManage();
	}
	$('#content_center').timeago();
});


