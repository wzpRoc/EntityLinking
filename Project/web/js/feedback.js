/**********************************************************************************************************************************************
 *名称：feedback.js
 *功能：管理用户反馈的操作
 *开发者：彭程
 *时间：2012-03-06
 **********************************************************************************************************************************************/

// 定义反馈部分的命名空间
$.namespace('$.feedback');
$.feedback = {
	form : null,
	// 加载编辑框相关的JS和CSS文件
	init: function(){
		$.getScript(ctx+'/dwr/interface/AjaxInterface.js', function(){
			$.getScript(ctx+'/js/wysiwyg/wysiwyg.js', $.feedback.showForm);	// 加载
		});
		
	},
	
	showForm : function(){
		
		if(!$.isFunction($.fn.wysiwyg))	// 判断编辑框JS是否已经加载
		{
			$.feedback.init();
			$('#iframeContent').focus();
			return false;
		}
		else if($('#feedbackContainer').size()>0){
			return false;
		}
		var url = ctx+'/feedback.action';
		$.post(url, {}, function(data){
				// 初始化对话框
				$.feedback.form = artDialog( {
					id : 'feedbackContainer',
					title : '用户反馈',
					content : data
				});
				// 初始化编辑框
				$('#wysiwyg').wysiwyg();
			}, 'html');
		
		return false;
	},
	
	submit : function(){
		// 获取反馈内容
		var fb_title = document.getElementById("feedback.title").value;
		var fb_content = $('body', $('#wysiwygIFrame').document()).html();

		if(fb_title == '<br/>' || fb_title == '')
		{
			$('#feedbackMsg').html('反馈标题不能为空！');
			$('#feedbackMsg').show();
		}
		else
		{
			var feedbackObject = {
					url : location.href,
					title : fb_title,
					content : fb_content,
					clientInfo : navigator.userAgent
			};
			AjaxInterface.submitFeedback(feedbackObject,function(msg){
				if(msg.success)
				{
					$('#feedbackContent').val('');
					setTimeout(function(){
						$.feedback.form.close();
					}, 1000);
					$('#feedbackMsg').html(msg.message + '  本对话框将于1秒后自动关闭。');
				}
				else{
					$('#feedbackMsg').html(msg.message + '  提交失败！');
				}
				
				$('#feedbackMsg').show();
			});
		}
	}
}
