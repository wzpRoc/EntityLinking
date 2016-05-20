//评论
function doRating(domId,obj)
{
	var rating_num=obj.getAttribute("title");
	for(i=1;i<=5;i++)
	{
		if(i<=rating_num)
		{
			$("#"+domId+" .ratings_star[title="+i+"]").addClass("ratings_star_on");
			$("#"+domId+" .ratings_star[title="+i+"]").removeClass("ratings_star_off");
		}
		else
		{
			$("#"+domId+" .ratings_star[title="+i+"]").addClass("ratings_star_off");
			$("#"+domId+" .ratings_star[title="+i+"]").removeClass("ratings_star_on");
		}
	}
	$("#rating").val(rating_num);
}

// 要用ajaxForm了
function doLoadCommentFunc(){
	if($('#form_comment').size()==0){
		return;
	}
	
	var options = {
		beforeSubmit : function(formData){	// 提交之前
			// 检查评论内容是否为空
			var content = formData[1].value;
			if(content == ""){
				alert('评论内容不能为空！');
				return false;
			}
			
			return true;
		},
		success : function(result){
			// 将列表重置
			$('#commentContent').val("");
			$('#commentList').html(result);
		},
		error : function(){
			alert("添加评论失败！当前网络处于离线状态，请等待网络恢复后再执行该操作！");
		}
	};
	// 绑定Ajax事件
	$('#form_comment').ajaxForm(options);
	
	// 添加推荐的AjaxForm选项
	var options1 = {
			beforeSubmit : function(formData){	// 提交之前
				// 检查评论内容是否为空
				var content = formData[1].value;
				if(content == ""){
					alert('评论内容不能为空！');
					return false;
				}
				
				return true;
			},
			success : function(result){
				// 将列表重置
				$('#recommendContent').val("");
				$('#recommendList').html(result);
			},
			error : function(){
				alert("添加评论失败！当前网络处于离线状态，请等待网络恢复后再执行该操作！");
			}
	};
	// 绑定Ajax事件
	$('#form_recommend').ajaxForm(options1);
	
	// 推荐
	$('#s_user').chosen();
}
