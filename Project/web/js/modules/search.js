/***********************************************************************************
 *名称：search.js
 *功能：负责搜索模块页面DOM装载完成后执行的初始化动作管理
 *开发者：彭程
 *时间：2011-11-6
 ***********************************************************************************/
 
 
function exportExl(totalRecordNum, exportLimit){
	
	if(totalRecordNum> exportLimit){
		alert("查询记录数超过"+exportLimit+"条，请缩小查询条件后再导出");
		return false;
	}
	
	// 获取当前搜索页的表单
	var searchForm = $('#form0')[0];

	// 获取原来的action名和target名
	var orig_exportTag = searchForm.exportTag.value;
	var orig_target = searchForm.target;

	// 提交新的action
	searchForm.exportTag.value = '1';
	searchForm.target = "_blank";
	searchForm.submit();
	
	// 信息修改回来
	searchForm.exportTag.value = orig_exportTag;
	searchForm.target = orig_target;

	return false;
}


/**
 * 开始查询
 */
function query() {
    var form = getElementById("form0");

    // 如果是按区段选择，那么读取开始日期和结束日期
    var selectBySection = getElementById("tcal_ss_b");
    var classOfSelectBySection = selectBySection.getAttribute("class");
    if(classOfSelectBySection == "tds_focus") {
        form.startDate.value = getElementById("rightStartDate").value;
        form.endDate.value = getElementById("rightEndDate").value;
    }

    form.submit();
}