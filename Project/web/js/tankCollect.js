 /**********************************************************************************************************************************************
 *功能：智库入口列表页面中点击“总文章数”或“错误数”的窗口弹出方法
 *开发者：张强
 *时间：2012/12/19
 **********************************************************************************************************************************************/

//初始化函数
$(document).ready(function() {
	//给错误的数字加上发光的效果
	$(".DocNum").mouseenter(function() {
				$(this).addClass("shining");
			});
	$(".DocNum").mouseleave(function() {
				$(this).removeClass("shining");
			});
	//弹出框的关闭函数 点击遮罩层中不是弹出框的区域时页面关闭
	$('.overlay').click(function() {
				if ($(this).is($("#showDocDetailTable"))) {
				} else {
					$("#showDocDetailTable").fadeOut("slow",function(){
						$('.overlay').fadeOut('fast');
						$('#detailTable').html(null);
					});
					
				}
			});
});
//点击“总文章数”数字时的动作
function showCollectDetail(id) {
	$("#showDocDetailTable").fadeOut();
	// 显示第一页内容  2表示去查询已经下载好的文章
	updateDocTable(id,1,2);
}

//点击“错误数”数字时的动作
function showWrongDetail(id){
	$("#showDocDetailTable").fadeOut();
	// 显示第一页内容  1表示去查询已经下载好的文章
	updateDocTable(id,1,1);
}


function updateDocTable(id,pageNo,collectState){
	var url = ctx + '/advanced/showDocDetail.action';
	$.post(url, {
				siteFeedId : id, currentPageNo:pageNo,collectState:collectState
			}, function(data) {
				// 获取根据siteFeedId查询的数据 并放到表格中去
				$("#showDocDetailTable").html(data);
				$("#showDocDetailTable .epages a").each(function(){
												var self = this;
												//找到页码
												var num = self.href.substring(self.href.lastIndexOf(',') + 1, self.href.lastIndexOf(')'));
												//更改默认的链接
												self.href = 'javascript:updateDocTable(' + id + ', ' + num + ', '+collectState+');';
											});
				$('.overlay').fadeIn('fast',function(){
					$("#showDocDetailTable").fadeIn("slow");
				});
			}, 'html');
}