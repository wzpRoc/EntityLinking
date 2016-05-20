/***********************************************************************************
 *名称：userSubscription.js
 *功能：用户空间的订阅设置页面若干交互功能
 *开发者：张强
 *时间：2013-07-01
 ***********************************************************************************/
$(document).ready(function(){
	//初始化“短信订阅”
	initSM();
	
	//初始化“邮件订阅”
	initMail();
});


/**
 * 处理“短信订阅”
 */
function initSM(){
	//1根据“是否接受短信”决定相关内容是否出现
	var $subscribedSM=$("#subscribedSM").attr("checked");
	if($subscribedSM){
		$("#subscribedSMContent").show();
	}else{
	  	$("#subscribedSMContent").hide();
	}
	
	//2为“是否接受短信”添加事件
	$("#subscribedSM").click(updateSubscribedSM);
	
	
	//3.1 设置“短信开始时间”下拉列表的宽度
	$("#s_msg_startTime").chosen();
	$("#s_msg_startTime_chzn").css("width","80px");
	$("#s_msg_startTime_chzn .chzn-drop").css("width","78px");
	
	//3.2 设置“短信结束时间”下拉框的宽度
	$("#s_msg_endTime").chosen();
	$("#s_msg_endTime_chzn").css("width","80px");
	$("#s_msg_endTime_chzn .chzn-drop").css("width","78px");

	//4 短信“添加时间”的事件
	$("#bt_msg_add").click(addMsgTime);
}

/**
 *"是否接受短信"事件
 */
function updateSubscribedSM(){
	var $subscribedSM=$("#subscribedSM");
	var subscribedSM=$(this).attr("checked");
	if(subscribedSM){
		$("#subscribedSMContent").slideDown();
		subscribedSM=true;
	}else{
	  	$("#subscribedSMContent").slideUp();
	  	subscribedSM=false;
	}
	AjaxInterface.updateUserSubscribedSM(userID,subscribedSM,function(blMessage){
		if(!blMessage.success){
			alert("抱歉，保存失败");
		}	
	});
}

/**
 * 短信添加时间操作
 */
function addMsgTime(){
	//开始时间和结束时间
	var s_startTime=$("#s_msg_startTime").val();
	if(s_startTime!=null){
		s_startTime=parseInt(s_startTime);
	}
	var s_endTime=$("#s_msg_endTime").val();
	if(s_endTime!=null){
		s_endTime=parseInt(s_endTime);
	}

	if(s_startTime>s_endTime){
		alert("结束时间不能早于开始时间！");
		return false;
	}else if(s_startTime==s_endTime){
		alert("结束时间不能等于开始时间！");
		return false;
	}
//	// 确认询问
//	if(!confirm("确认添加时间段：" + s_startTime + ":00-"+s_endTime+":00  吗？")){
//		return false;
//	}
	
	//将数据插入数据库
	AjaxInterface.addUserOrderTime(userID,s_startTime,s_endTime,1,0,function(blMessage){
		if(blMessage.success){
			var UserOrderTime=blMessage.data;
			//在列表中添加
			$("#msgMemberList").append(
				'<li id="'+UserOrderTime.id+ '">'  + 
				UserOrderTime.startSendingTime + ':00-' + UserOrderTime.endSendingTime+":00 " +
				'<a href="javascript:void(0);" onclick="removeTime(this,'+UserOrderTime.id+')"></a>' +
				'</li>'
				
			);
		}else{
			alert(blMessage.message);
		}
	});
}

/**
 * 删除时间
 */
function removeTime(obj, timeID){
	//得到父节点
	var $_li = $(obj).parent();
	var name = $_li[0].childNodes[0].textContent;
	
//	// 确认询问
//	if(!confirm("确认将" + name + "删除吗？")){
//		return false;
//	}
	
	AjaxInterface.deleteUserOrderTime(timeID, function(blMessage){
		if(blMessage.success){
			$_li.remove();
		}
		else {
			alert(blMessage.message);
		}
	});
}




/**
 * 处理“邮件订阅”
 */
function initMail(){
	//1  根据“是否接受邮件”决定相关内容是否出现
	var $subscribedMail=$("#subscribedMail").attr("checked");
	if($subscribedMail){
		$("#subscribedMailContent").show();
	}else{
	  	$("#subscribedMailContent").hide();
	}
	
	//2 为“是否接受邮件”添加事件
	$("#subscribedMail").click(updatesubscribedMail);
	
	
	//3.1 初始化“复选框”的颜色
	initCheckbox();
	
	//3.2 初始化"哪天接受邮件"复选框事件
	initWeekday();
	
	//3.2 “哪天接受邮件”的“保存时间”事件
	$("#bt_weekday_save").click(updateWeekday);
	
	
	//4.1 设置“邮件时间点”下拉框的宽度
	$("#s_mail_startTime").chosen();
	$("#s_mail_startTime_chzn").css("width","80px");
	$("#s_mail_startTime_chzn .chzn-drop").css("width","78px");
	
	//4.2  邮件“添加时间”的事件
	$("#bt_mail_add").click(addMailTime);
	
	//5.1 设置“邮件语种”下拉框的宽度
	$("#s_mail_language").chosen();
	$("#s_mail_language_chzn").css("width","90px");
	$("#s_mail_language_chzn .chzn-drop").css("width","88px");
	$("#s_mail_language_chzn .chzn-drop .chzn-search").hide();
	
	//5.2 为“添加语种”按钮添加事件
	$("#bt_language_add").click(addMailLanguage);
	
	//6 初始化"邮件主题"
	$.subject.init();
}

/**
 * "是否接受邮件"事件
 */
function updatesubscribedMail(){
	var $subscribedMail=$("#subscribedMail");
	var subscribedMail=$(this).attr("checked");
	if(subscribedMail){
		$("#subscribedMailContent").slideDown();
		subscribedMail=true;
	}else{
	  	$("#subscribedMailContent").slideUp();
	  	subscribedMail=false;
	}
	AjaxInterface.updateUserSubscribedMail(userID,subscribedMail,function(blMessage){
		if(!blMessage.success){
			alert("抱歉，保存失败");
		}	
	});
	
}



/**
 * 初始化复选框的颜色
 */
function initCheckbox(){
	$("#weekdaysOfMail").find('input').each(function(){
		if($(this).attr("checked")==null){
			$(this).parent().removeClass("checked").addClass("unchecked");
		}else{
			$(this).parent().removeClass("unchecked").addClass("checked");
		}
	});
}

/**
 * "哪天接受邮件"复选框事件
 */
function initWeekday(){
	//levelOne复选框的点击事件
	$(".levelOne").find('input:first').click(function(){
		if($(this).attr("checked")=="checked"){
			$(this).parent().find(".levelTwo").find("input").attr("checked","checked");
		}else{
			$(this).parent().find(".levelTwo").find("input").attr("checked",false);
		};
		initCheckbox();
	});
	
	//levelTwo复选框的点击事件
	$(".levelTwo").find('input').click(function(){
		if($(this).attr("checked")==null){
			$(this).parent().parent().parent().find('input')[0].checked = false;
		}
		initCheckbox();
	});
}

/**
 * "哪天接受邮件"的“保存时间”事件
 */
function updateWeekday(){
	var weekdaysOfMail='';
	var $daysInputs=$('#weekdaysOfMail').find(".levelTwo").find("input");
	$daysInputs.each(function(){
		if($(this).attr("checked")){
			weekdaysOfMail=weekdaysOfMail+'1';
		}else{
			weekdaysOfMail=weekdaysOfMail+'0';
		}
	})
	//alert(weekdaysOfMail);
	AjaxInterface.updateUserWeekdaysOfMail(userID,weekdaysOfMail,function(blMessage){
		if(blMessage.success){
			alert("保存成功");
		}else{
			alert("抱歉，保存失败");
		}	
	});
}

/**
 * 邮件添加时间操作
 */ 
function addMailTime(){
	//开始时间
	var s_startTime=$("#s_mail_startTime").val();
	if(s_startTime!=null){
		s_startTime=parseInt(s_startTime);
	}
	var s_endTime=s_startTime;

//	// 确认询问
//	if(!confirm("确认添加时间点：" + s_startTime + ":00  吗？")){
//		return false;
//	}
	
	//将数据插入数据库
	AjaxInterface.addUserOrderTime(userID,s_startTime,s_endTime,1,1,function(blMessage){
		if(blMessage.success){
			var UserOrderTime=blMessage.data;
			//在列表中添加
			$("#mailMemberList").append(
				'<li id="'+UserOrderTime.id+ '">'  + 
				UserOrderTime.startSendingTime + ':00' +
				'<a href="javascript:void(0);" onclick="removeTime(this,'+UserOrderTime.id+')"></a>' +
				'</li>'
				
			);
		}else{
			alert(blMessage.message);
		}
	});	
}

/**
 * 邮件语种“添加语种”的操作
 * @return
 */
function addMailLanguage(){
	//语种
	var languageName=$("#s_mail_language_chzn span").text();
	
//	//确认询问
//	if(!confirm("确认添加：" + languageName + " 语种吗？")){
//		return false;
//	}
	
	//将数据插入数据库
	AjaxInterface.addMailLanguage(userID,languageName,function(blMessage){
		if(blMessage.success){
			//在列表中添加
			var language=blMessage.data;
			$("#mailLanguagesList").append(
				'<li languageName="'+language.id+ '">'  + 
				language.name_zh +
				'<a href="javascript:void(0);" onclick="removeMailLanguage(this,'+language.id+')"></a>' +
				'</li>'
				
			);
		}else{
			alert(blMessage.message);
		}
	});
	
}

/**
 * 删除邮件语种
 */
function removeMailLanguage(obj, languageId){
	//得到父节点
	var $_li = $(obj).parent();
	var name = $_li[0].childNodes[0].textContent;
	
//	// 确认询问
//	if(!confirm("确认将 " + name + " 语种删除吗？")){
//		return false;
//	}
	
	AjaxInterface.deleteMailLanguage(userID ,languageId, function(blMessage){
		if(blMessage.success){
			$_li.remove();
		}else {
			alert(blMessage.message);
		}
	});
}



/**
 * 将已经选择的语种从下拉框中去掉
 * @return
 */
function removeLanguageOptions(){
    //已经选择的语种
	var $mailLanguages=$("#mailLanguagesList li");
	
	//下拉框中的值
	$("#s_mail_language option").each(function(){
		var optionText=$(this).text();
		for(var i=0;i< $mailLanguages.length; i++){
			if(optionText == $mailLanguages.eq(i).text()){
				$(this).remove();
			}
		}
	});
	$("#s_mail_language").chosen();
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
		
//		// 确认询问
//		if(!confirm("确认将邮件主题  " + name + " 删除吗？")){
//			return false;
//		}
		
		AjaxInterface.deleteUserSubject(userID, sid, function(blMessage){
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
		
		// 异步向后台添加记录
		AjaxInterface.userAddSubject(userID, subject_ids, 1, function(blMessage) {
		if (!blMessage.success) {
			alert('添加失败！请检查数据库操作问题！');
			return;
		}
		// 逐个添加主题
			for ( var i = 0; i < subject_ids.length; i++) {
				$.subject.addOneSubject(subject_ids[i]);
			}
			// 关闭添加框
			$.subject.box.close();
		});
	}
}








