/***********************************************************************************
 *名称：subject.js
 *功能：负责专题模块页面DOM装载完成后执行的初始化动作管理
 *开发者：张强
 *时间：2013-07-15
 ***********************************************************************************/


$(document).ready(function () {
	//判断页面类型
    if (dowhat == "add") {
        // 初始化“添加主题”页面
    	initAddPage();
    }else{
    	// 初始化“编辑主题”页面
    	initEditPage();
    }
    //初始化页面
	initPage();
    
});

/**
 * 初始化“添加主题页面”
 */
function initAddPage(){
	// 默认设置为按规则
	$("#definitionType option:selected").val(1);
	$("#tr_ruleSet").show();
	// 规则名称为空隐藏
	$("#subject_ruleSetName").hide();
	
	// 将“按聚类”的按钮隐藏
	$("#tr_cluster").hide();
	$("#subject_clusterName").hide();
	
	// 隐藏"检索文章"
	$("#updateSubjectDoc").hide();
}

/**
 * 初始化“编辑主题”页面
 * @return
 */
function initEditPage(){
	
	//根据主题的定义方式隐藏相应控件	
	var $definitionType= $("#definitionType option:selected").val();
	
	if($definitionType == 1){ //按规则
		$("#tr_cluster").hide();
		$("#tr_ruleSet").show();
	}else{ //按聚类
		$("#tr_ruleSet").hide();
		$("#tr_cluster").show();
	}
	
	
}

/**
 * 初始化页面
 */
function initPage(){
	//主题是否已经“审核”了
	if($("#subject_confirmed").val() == 1){
		$("#btn_confirm").hide();
	}
	
	//弹出框关闭按钮事件
	$("#dialog_close").click(function(){
		popBoxHide();
	});
	
	//初始化“选择聚类”弹出框
    initSeclectClusterBox();
    
    //初始化“选择规则”弹出框
    initSeclectRuleSetBox();
}



/**
 * 初始化“选择聚类”弹出框
 */
function initSeclectClusterBox(){
	
	//为通过相似文章聚类中的”选择“按钮添加事件
	$("#a_editCluster").click(function(){
		//1 修改弹出框的标题等
		$("#dialog_close").show();
		$("#dialog_title").text("选择主题");
		$("#dialog_content_title").text("请从下面的主题中选择一个");
		$("#dialog_content_buttons").hide();
		
		//2 将弹出框的内容改为聚类列表
		var subjectID=$("#entityId").val();
		AjaxInterface.fetchSimpleClusters(subjectID,function(blMessage){
			if(blMessage.success){
				
				$("#dialog_content_title").append('<table class="cluster-list" width="100%"><tbody id="clusterBody"></tbody><table>');
				
				//表头
				$("#clusterBody").append('<tr class="header"><td width="60%" height="25">聚类名称</td><td width="40%" height="25">最后更新时间</td></tr>');
				
				//逐一的遍历每个simpleCluster
				var clustersList=blMessage.data;
				//表格中的内容
				for(var i=0;i<clustersList.length;i++){
					var simpleCluster=clustersList[i];
					if(simpleCluster.updateTime==null){
						simpleCluster.updateTime="";
					}
					$("#clusterBody").append('<tr>' +
							'<td id="everyCluster" onclick="everyClusterClick(this)"><a style="cursor:pointer;" clusterID="'+simpleCluster.id+'" clusterName="'+simpleCluster.name+'">'+simpleCluster.name+'</a></td>' +
							'<td>'+simpleCluster.updateTime+'</td>' +
							'</tr>');
				}
			}
		});
		//4 显示弹出框	
		popBoxShow();
	});
}

//3 为每个聚类的名字添加事件
function everyClusterClick(obj){
	//取聚类的名称
	var clusterName=$(obj).find("a").attr("clusterName");	
	//取得聚类的ID
	var clusterID=$(obj).find("a").attr("clusterID");	
			
	$("#subject_clusterName").val(clusterName).show();
	
	//如果主题的名称为空 将聚类名称赋给主题名称
	var $subjectName=$("#entityTitle");
	if(($subjectName)&&($subjectName.val())&&($subjectName.val().length==0)){
		$subjectName.val(clusterName);
	}
	
	//为页面的变量赋值
	getOnlyElementByName("subject.clusterId").value=clusterID;
	getOnlyElementByName("subject.clusterName").value=clusterName;
	
	//关闭弹出框
	popBoxHide();
}

/**
 * 初始化“选择规则”弹出框
 * @return
 */
function initSeclectRuleSetBox(){
	//为通过相似文章聚类中的”选择“按钮添加事件
	$("#a_editRuleSet").click(function(){
		//1 修改弹出框的标题等
		$("#dialog_close").show();
		$("#dialog_title").text("选择规则");
		$("#dialog_content_title").text("请从下面的规则中选择一个");
		$("#dialog_content_buttons").hide();
		
		//2 将弹出框的内容改为聚类列表
		var subjectID=$("#entityId").val();
		AjaxInterface.fetchRuleSets(subjectID,function(blMessage){
			if(blMessage.success){
				
				$("#dialog_content_title").append('<table class="cluster-list" width="100%"><tbody id="clusterBody"></tbody><table>');
				
				//表头
				$("#clusterBody").append('<tr class="header"><td width="60%" height="25">规则名称</td><td width="40%" height="25">规则描述</td></tr>');
				
				//逐一的遍历每个ruleSet
				var rulesList=blMessage.data;
				//表格中的内容
				for(var i=0;i<rulesList.length;i++){
					var ruleSet=rulesList[i];
					//将null替换为空字符串
					if(ruleSet.description==null){
						ruleSet.description="";
					}
					$("#clusterBody").append('<tr>' +
							'<td id="everyRule" onclick="everyRuleClick(this)"><a style="cursor:pointer;" ruleSetID="'+ruleSet.id+'" ruleSetName="'+ruleSet.name+'">'+ruleSet.name+'</a></td>' +
							'<td>'+ruleSet.description+'</td>' +
							'</tr>');
				}
			}
		});
		//4 显示弹出框	
		popBoxShow();
	});
}

//3 为每个规则的名字添加事件
function everyRuleClick(obj){
	//取规则的名称
	var ruleName=$(obj).find("a").attr("ruleSetName");	
	//取得规则的ID
	var ruleID=$(obj).find("a").attr("ruleSetID");	
			
	$("#subject_ruleSetName").val(ruleName).show();
	
	//如果主题的名称为空 将聚类名称赋给主题名称
	var $subjectName=$("#entityTitle");
	if(($subjectName)&&($subjectName.val())&&($subjectName.val().length==0)){
		$subjectName.val(ruleName);
	}
	
	//为页面的变量赋值
	getOnlyElementByName("subject.ruleSetId").value=ruleID;
	getOnlyElementByName("subject.ruleSetName").value=ruleName;
	
	//关闭弹出框
	popBoxHide();
}




//显示“选择主题”弹出框
function popBoxShow(){
	$("#maskLayer").fadeIn(200,function(){
		$("#defintionTypeMask").fadeIn(100);
	});
}

//关闭“选择主题”弹出框
function popBoxHide(){
	$("#defintionTypeMask").fadeOut(100,function(){
		$("#maskLayer").fadeOut(200);
	});
}

/**
 * 创建主题下拉框的事件 
 */
function changeDefType(){
	//根据主题的定义方式隐藏相应控件	
	var $definitionType= $("#definitionType option:selected").val();
	
	if($definitionType == 0){ //默认设置为按规则
		$("#definitionType option:selected").val(1);
		//将“按聚类”的按钮隐藏
		$("#tr_cluster").hide();
		$("#tr_ruleSet").show();
	}else if($definitionType ==1){ //按规则
		//将“按聚类”的按钮隐藏
		$("#tr_cluster").hide();
		$("#tr_ruleSet").show();
	}else{                        //按聚类  
		//将“按规则”的按钮隐藏
		$("#tr_ruleSet").hide();
		$("#tr_cluster").show();
	}
}

function save() {
	if($("#entityTitle").val().length == 0){
		alert("请填写主题名称");
		return ;
	}
	
	// 判断主题的信息是否完整
	var $subject_definitionType=$("#definitionType option:selected").val();
	if($subject_definitionType == 1 ) { // 主题定义方式为规则
		if($("#subject_ruleSetName").val().length == 0){
			alert("请为主题选择规则");
			return;
		}
	}else if($subject_definitionType ==2 ){ // 主题定义方式为聚类
		if($("#subject_clusterName").val().length == 0){
			alert("请为主题选择聚类");
			return;
		}
	}else{  //主题定义方式不正确
		alert("对不起，主题定义失败，请重新定义！");
		return ;
	}
	
    document.getElementById("form0").submit();
    $("#updateSubjectDoc").hide("fast",function(){
    	$(this).fadeIn("slow");
    });
    
}


function myConfirm() {
    getOnlyElementByName("subject.confirmed").value = 1;
    document.getElementById("form0").submit();
}


function gotoRuleSetPage(dowhat) {
    var url = ctx + "/advanced/ruleSetDetail!get" +
        "?dowhat=" + dowhat +
        "&id=" + getOnlyElementByName("subject.ruleSetId").value +
        "&moduleName=subject" +
        "&idInModule=" + getOnlyElementByName("subject.id").value +
        "&name=" + getOnlyElementByName("subject.name").value;
    var ruleSetWindow = window.open(url, "_blank", "width=1100, height=700, scrollbars=yes, modal=yes");
}


function onRuleSetSaved(ruleSet) {
    // 返填ID
    getOnlyElementByName("subject.ruleSetId").value = ruleSet.id;
    // 隐藏创建规则的链接
    document.getElementById("a_createRuleSet").style.display = "none";
    // 显示编辑规则的链接
    document.getElementById("a_editRuleSet").style.display = "";
}


function updateSubjectDoc() {
    var ruleSetId = getOnlyElementByName("subject.ruleSetId").value;
    if (ruleSetId == 0) {
        alert("请先创建规则");
        return;
    }

    var subjectId = getOnlyElementByName("subject.id").value;
    var tankArticleOnly = getOnlyElementByName("subject.tankArticleOnly").value;

    AjaxInterface.updateSubjectDoc(subjectId, tankArticleOnly, ruleSetId, callback_updateSubjectDoc);

    showPopMessage("正在更新，可能需要花费较长时间，请耐心等待……");
}

function callback_updateSubjectDoc(blMessage) {
    if (blMessage.success) {
        getOnlyElementByName("subject.nrDoc").value = blMessage.data["nrDoc"];
        getOnlyElementByName("subject.nrTankDoc").value = blMessage.data["nrTankDoc"];
        if (blMessage.message != "") {
            showPopMessage(blMessage.message);
        } else {
            showPopMessage("更新成功！");
        }
    } else {
        showPopMessage(blMessage.message);
    }
}

/**
 *  列表页中的检索文章方法 
 */
function updateSubDocFromListPage(){
	var info = getSelectedCheckboxListInfo();
	if (info.cnt == 0) {
        alert("请选择一条或多条记录");
        return;
    }
	updateSubjectListDoc(info.valueList);
}


/**
 * 详细页中检索文章的方法
 */
function updateSubDocFromDetailPage(){
	var $subjectID=$("#entityId").val();
	if(typeof($subjectID)== undefined || $subjectID.length == 0){
		alert("请先定义主题，再检索文章");
		return;
	}
	var subjectIDs = new Array();
	subjectIDs.push(parseInt($subjectID));
	updateSubjectListDoc(subjectIDs);
	
	
}

var clock;

/**
 * 检索主题文章的方法
 * @param subjectIDs
 */
function updateSubjectListDoc(subjectIDs) {
    var dateBegin = getElementById("dateBegin").value;
    var dateEnd = getElementById("dateEnd").value;
    var days = getDays(dateBegin, dateEnd);
    if (days > MAX_SEARCH_DAYS) {
        alert("您选择的日期范围超过" + MAX_SEARCH_DAYS + "天，为了降低系统压力，请重新选择。")
        return;
    }

    if (!confirm("检索文章开销较大，最好在系统空闲时执行，您确定现在执行吗？")) {
        return;
    }

    $("#maskLayer").fadeIn(100,function(){
    	showPopMessage("开始检索，可能需要花费较长时间，请耐心等待……");
	});
    
    
    AjaxInterface.updateSubjectListDoc(
    	subjectIDs,
        getElementById("batchProcessMode").value,
        dateBegin,
        dateEnd,
        getElementById("additionalCondition").value,
        callback_updateSubjectListDoc
    );

    //每一秒中调用一次
    clock=setInterval("fetchProState()",1000);
}

function fetchProState() {
    //只要没有结束就不停的查看状态
    AjaxInterface.fetchBatchDocProcessorStateObject(function (blMessage) {
        if (blMessage.success) {
            var batchDocProcessorStateObject = blMessage.data;

            if (batchDocProcessorStateObject != null) {
                var processState = batchDocProcessorStateObject.state;

                showPopMessage(batchDocProcessorStateObject.desc);

                // 结束
                if (processState == 2) {
                    clearInterval(clock);
                }
            } else {
                // 检索结束
                clearInterval(clock);
            }
        } else {
            showPopMessage(blMessage.message);
            clearInterval(clock);
        }
    });
}

function callback_updateSubjectListDoc(blMessage) {
	var msg;
    if (blMessage.success) {
        if (blMessage.message != "") {
            msg = blMessage.message;
        } else {
            msg = "更新成功！";
        }
    } else {
        msg = blMessage.message;
    }
    showPopMessage(msg);
    
    // 更新主题的文章数
	var isDetailPage = $("#detailPage").val();
	if ("true" == isDetailPage) {
		var subjectID = $("#entityId").val();
		AjaxInterface.fetchSubject(subjectID, function(blMessage) {
			if (blMessage.success) {
				var subject = blMessage.data;
				$("#subject_nrDoc").val(subject.nrDoc);
				$("#subject_nrTankDoc").val(subject.nrTankDoc);
			}
		});
	}
}

/*
 function on_eventSub_clicked(eventSub){
 var checked = eventSub.checked;
 var tmp = 0;
 if(checked)
 tmp = 1;
 // 调用AJAX保存
 AjaxInterface.updateReceiveMessage(
 userId,
 tmp,
 callback_updateReceiveMeaasge
 );

 // 显示等待消息
 showPopMessage("正在保存，请稍候……");
 }

 function callback_updateReceiveMeaasge(blMessage) {
 if (blMessage.success) {
 if (blMessage.message != null && blMessage.message != "") {
 showPopMessage(blMessage.message);
 } else {
 showPopMessage("设为重大事件类型成功！");
 }
 } else {
 showPopMessage(blMessage.message);
 }
 }*/
