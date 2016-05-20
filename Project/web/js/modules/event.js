/***********************************************************************************
 *名称：event.js
 *功能：负责事件模块页面DOM装载完成后执行的初始化动作管理
 *开发者：彭程
 *时间：2011-11-4
 ***********************************************************************************/
 
// 改进“分类” 和 “事件重要性” 相关的操作
$.namespace('$.cateEdit');
$.cateEdit = {
	// 原来的的文档ID和事件类型ID
	eventId : null,
	eventTitle : null,
	origType : null,
	origImportance : null,
	docId : null,
	tableHtml : null,
	
	// 打开分类框
	showForm : function(eventId, eventTitle, origType, origImportance, docId) {
		$.cateEdit.eventId = eventId;
		$.cateEdit.eventTitle = eventTitle;
		$.cateEdit.origType = origType;
		$.cateEdit.origImportance = origImportance;
		$.cateEdit.docId = docId;
		
//		if($.cateEdit.tableHtml != null){
//			// 打开对话框
//			$.cateEdit.box = artDialog( {
//				id : 'eventTypeSelectContainer',
//				title : '新闻编辑',
//				content : $.cateEdit.tableHtml
//			}); 
//			return;
//		}
		
		// 向后台请求主题列表
		AjaxInterface.getEventTypeList(function(blMessage){
			if(blMessage.success){
				var eventTypes = blMessage.data;
				// 构造HTML文本
				var $_type_select_box = $('<div><div id="group_subject_select_box" class="p10 mb10"><h5>请选择事件应属的类别：</h5><table id="subject_table" width="100%" cellspacing="1" cellpadding="3" border="0" align="center" class="tableborder"><thead><tr height="25" class="header"><td width="5%"><div align="center">序号</div></td><td width="20%"><div align="center">名称</div></td><td width="20%"><div align="center">类型号</div></td></thead><tbody id="J_eventType_list"></tbody></table>' +
						'<div id="importance_radio" class="p10 mb10"><h5>请选择事件的重要性：</h5><div id="importance_radio_content"></div></div>'+
						'<p class="mt10 tc"><input type="button" onclick="$.cateEdit.submit();" value="提交" name="combineSubmit"><input type="button" onclick="$.cateEdit.box.close();" value="取消"></p>' +
						'</div></div>');
				var $_type_list = $_type_select_box.find('#J_eventType_list');
				var count = 1;
				for(var i = 0; i < eventTypes.length; i++){
					var eventType = eventTypes[i];
					// 如果已经在已选的列表中了，就不在添加。
					if(eventType == origType) continue;
					
					// 构造tr元素的html文本
					var trHtmlText = '<tr id="J_type_' + eventType.id + '" height="25" bgcolor="ffffff" style="background-color: rgb(255, 255, 255);" onmouseout="this.style.backgroundColor=\'#ffffff\'" onmouseover="this.style.backgroundColor=\'#DBEAF5\'">' +
						'<td><div align="center"><input type="radio" name="checkbox_id" id="id_' + eventType.id + '" value="15">' +
						'<label>' + (count++) + '</lable>' +
						'</div></td>' +
						'<td><div align="left" class="J_type_name">' +
						eventType.name +
						'</div></td>' +
						'<td><div align="left" class="J_type_id">' +
						eventType.id +
						'</div></td>' +
						'</tr>';
					// 加入到list中
					$_type_list.append(trHtmlText);
				}
				//设置主题选择框的初始值
				$_type_list.find("tr").each(function(){
					if(parseInt($(this).find("td").find(".J_type_id").text()) == $.cateEdit.origType){
						$(this).find("input").attr("checked","checked");	
					}
				});
				
				
				
				
				//事件重要性选择框
				var $_importance_radio_content=$_type_select_box.find('#importance_radio_content');
				var radioHtmlText='<input type="radio" name="importance"    level="level1"	autocomplete="off"	value="100"><label>重&nbsp大	</label>'+
								  '<input type="radio" name="importance"	level="level2"	autocomplete="off"	value="75"><label>重&nbsp要	</label>'+
								  '<input type="radio"	name="importance"	level="level3" autocomplete="off"	value="45"><label>普&nbsp通	</label>'+
								  '<input type="radio" name="importance"	level="level4" autocomplete="off"	value="15"><label>不重要</label>';
				$_importance_radio_content.append(radioHtmlText);
				//为单选框设置初始值
				var origLevel;
				if (origImportance > 90) {
					origLevel = "level1";
				} else if (origImportance > 60) {
					origLevel = "level2";
				} else if (origImportance > 30) {
					origLevel = "level3";
				} else {
					origLevel = "level4";
				}
				$_importance_radio_content.find("input").each(function(){
					if($(this).attr("level") == origLevel){
						$(this).attr("checked","checked");
					}
				});
				
				
				// 保存HTML页面
				$.cateEdit.tableHtml = $_type_select_box.html();
				// 打开对话框
				$.cateEdit.box = artDialog( {
					id : 'eventTypeSelectContainer',
					title : '编辑事件',
					content : $_type_select_box.html()
				}); 
			}
		});
		
		return;
	},
	
	/**
	 * 提交操作
	 * 确认勾选记录，Ajax提交
	 */
	submit : function(){
		//弹出框的提示信息
		var confirmStr="您确定将\"" + $.cateEdit.eventTitle + "\"的";
		
		// 先读取用户选择的类别
		var $_select_tr = $("#J_eventType_list input[type=radio]:checked").closest('tr');
		//如果选择了新的事件类别
		var newType=parseInt($_select_tr.find(".J_type_id").html());
		if(newType != $.cateEdit.origType){
			// 获取新类别信息
			var newTypeName = $_select_tr.find(".J_type_name").html();
			
			var confirmStr=confirmStr+"\n事件的类别修改为\" " + newTypeName + "\" ";
		}
		
		//获取重要性
		var newImportance=$("#importance_radio_content input[type=radio]:checked").val();
		var newImportanceText=$("#importance_radio_content input[type=radio]:checked").next("label").text().trim();
		
		//如果重要性发生了变化
		if(newImportance!=$.cateEdit.origImportance){
			confirmStr=confirmStr+"\n事件的重要性改为 \" "+newImportanceText+"\" ";
		}
		confirmStr=confirmStr+"吗？" 
		
		//如果没有改变任何信息
		if(newType == $.cateEdit.origType && newImportance == $.cateEdit.origImportance){
			alert("请选择需要修改的信息");
			return;
		}
		// 询问
		if(!confirm(confirmStr)){
			return;
		}
		
		
		// ajax操作
		AjaxInterface.modifyEventTypeAndImportance($.cateEdit.eventId, $.cateEdit.docId, newType, newImportance, function(blMessage){
			if(blMessage.success){
				// 修改成功了就刷新页面吧~~~
				location.reload();
			}
			else {
				alert("修改失败！检查数据库操作！");
			}
		});
	}
}


// 后台查询是否存在新闻图片链接
function loadEventPhotos(){
	$("#content_center .cluster_box").each(function(){
		var $_self = $(this);
		var docId = parseInt($_self.attr('_doc_id'));
		AjaxInterface.getDocPhotoUrl(docId, function(docRes){
			if(docRes){
				$_self.children('.articleAbst').prepend("<img height='100px' src=" + docRes.link + ">");
			}
		});
	});
}

// event页面加载完成时
$(document).ready(function(){
	// 禁用高级筛选选项
	if(methodType=="detail"){
		disableAdvancedOntion();
	}
//	else if($('.eInType').size()<1){	// 如果是事件列表页，则加载图片。用页面中是否含有.eInType元素来判断是首页还是列表页
//		loadEventPhotos();
//	}
});

// 左侧信息监测列表的的提交函数
// 传入参数为树节点的id


/**
 * 导入新闻/智库文章
 */
function extractEvent() {
    AjaxInterface.extractEvent(
        getElementById("batchProcessMode").value,
        getElementById("dateBegin").value,
        getElementById("dateEnd").value,
        getElementById("additionalCondition").value,
        callback_extractEvent
    );

    showPopMessage("正在处理，可能需要花费较长时间，请耐心等待……");
}


/**
 * 更新文章的回调函数
 * @param blMessage
 */
function callback_extractEvent(blMessage) {
    if (blMessage.success) {
        // getOnlyElementByName("subject.nrDoc").value = blMessage.data;
        if(blMessage.message!="") {
            showPopMessage(blMessage.message.replace(new RegExp(/\n/g), "<BR>"));
        } else {
            showPopMessage("处理结束！");
        }
    } else {
        showPopMessage(blMessage.message);
    }
}