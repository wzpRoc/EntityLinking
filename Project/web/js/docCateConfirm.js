/**********************************************************************************************************************************************
 *名称：docCateConfirm.js
 *功能：处理批量对文档的事件类别和文档类别进行标注页面的各类操作
 *开发者：彭程
 *时间：2012-03-05
 **********************************************************************************************************************************************/

// 当前的聚焦的“添加”按钮元素
var currentAddDom = null;
// 当前勾选的的“添加”元素列表
var currentCheckedList = null;
// 用于保存各个文档的事件分类的数据结构
var idToEventType = {};
// 分类选择框的高度
var HEIGHT_EFRAME = 240;
var WIDTH_EFRAME = 200;
// 类别映射map
var event2Str = {
	"-1" : "不相关",
	"-2" : "重复",
	0 : "无分类",
	1001 : "国家/地区大选",
	1002 : "人事变动",
	1003 : "重要会议",
	1004 : "高端访问",
	1005 : "条约制定",
	2001 : "重要讲话",
	1008 : "军事演习",
	2002 : "阅兵",
	2003 : "军事行动",
	1009 : "武器研发",
	1010 : "武器交易",
	1006 : "社会冲突",
	1007 : "武装冲突",
	1011 : "恐怖活动",
	1012 : "反恐行动",
	1013 : "自然灾害"
}

/**
 * 显示选择框
 * @param {} n_top
 * @param {} n_left
 */
function showETypeList(n_top, n_left){
	n_top = n_top < 10 ? 10 : n_top;
	$('#eTypeListFrame').css({"top": n_top+"px", "left": n_left+"px"}).show();
}

// 隐藏选择框
function hideETypeList(){
	$('#eTypeListFrame').hide();
}

/**
 * 更新一篇文档的事件类别
 * @param {} typeId	新的事件类型的ID
 * @param {} typeStr	新的事件类型显示文本
 */
function updateEType(typeId, typeStr){
	// 在map数据结构中，添加一条id到eventType的映射
	idToEventType[currentAddDom.getAttribute('_docid')] = typeId;
	// 将当前聚焦的“添加”按钮边上的span.cateInst设置显示文本
	$(currentAddDom).siblings('.cateInst').html(typeStr).show();
	// 设置该按钮的显示文本为“删除”
	$(currentAddDom).html("删除");
	// 隐藏分类选择框
	$('#eTypeListFrame').hide();
}

// 删除一个事件分类
function removeEType(){
	idToEventType[currentAddDom.getAttribute('_docid')] = 0;
	$(currentAddDom).siblings('.cateInst').html('').hide();
	$(currentAddDom).html("添加");
}

// 批量保存列表内的事件分类
function updateETypeForDocs(){
	AjaxInterface.updateEventTypes(idToEventType, function(result){
		if(result == "true"){
			alert("保存成功，点击跳转！");
			document.form.submit();
		}
	});
}

// 批量保存文档的分类信息
function updateCatesForDocs(){
	// 组装各个文档的类别到一个数组当中
	var idToDocCates = {};
	$('.J_artical_item').each(function(){
		var self = this;
		// 获取文档的id，
		var docId = $(self).find('.docTitle').attr('_docid');
		// 获取分类id
		var $_cates = $(self).find('.cateList').children();
		var docCates = "";
		for(var i=0; i < $_cates.size(); i++){
			docCates += $_cates[i].id + ",";
		}
		idToDocCates[docId] = docCates;
	});
	// 提交分类结果
	AjaxInterface.updateDocCates(idToDocCates, function(result){
		if(result == "true"){
			alert("保存成功，点击跳转！");
			document.form.submit();
		}
	});
}

// 响应“批量分类”按钮
function doBatchCate(){
	// 找到当前勾选的ID
	var $_checkedList = $('table :checked');
	// 判断大小
	if($_checkedList.size()==0){
		alert('请勾选要批量分类的文章！');
		return;
	}
	// 将每个DOM元素放到currentCheckedList中
	currentCheckedList = [];
	$_checkedList.each(function(){
		currentCheckedList.push($(this).closest('tr').find('.addDocCate')[0]);
	});
	// 显示分类框
	$('#categoryFrame').show();
}


// 当前聚焦的文档标题元素
var currentTitleDom = null;
// 存储文档信息的map对象
var idToDocMap = {};
// 显示的标志位
var showFlag = false;
var counter = 0;
// 显示文档信息框
function showDocInfoFrame(n_top, n_left){
	if(n_top && n_left)
		$('#docInfoFrame').css({"top": n_top+"px", "left": n_left+"px"});
		
	$('#docInfoFrame').show();
	showFlag = true;
}
// 隐藏文档信息框
function hideDocInfoFrame(){
	$('#docInfoFrame').hide();
	showFlag = false;
}

// 异步获取文档信息
function ajax_getDocInfo(docId){
	// 如果idToDocMap中已经有该篇文档，那么直接设置新的内容
	if(idToDocMap[docId]){
		setNewDocInfo(idToDocMap[docId]);
		return;
	}
	// 否则需要异步获取文档内容	
	var $_loading = $('#docInfoFrame #loading');
	$_loading.html("正在加载中，请稍后...");
	$_loading.show().siblings().hide();	// 显示加载信息的同时，隐藏文档内容
	AjaxInterface.getOneDocInfo(docId, function(result){
		if(!result){
			$_loading.html("获取文档信息失败！");
			return;
		}
		var doc = $.parseJSON(result);	// 解析JSON串
		idToDocMap[docId] = doc;	// 在map添加新的映射
		setNewDocInfo(doc);	// 设置新的文档内容
	});	
}

// 设置新的文档内容
function setNewDocInfo(doc){
	var $_docInfo = $('#docInfoFrame #docInfo');
	$_docInfo.children('#title').html(doc.title);
	$_docInfo.children('#abst').html(doc.abst);
	$_docInfo.children('#content').html(doc.content);
	$_docInfo.show().siblings().hide();	// 显示文档内容，隐藏加载信息
}

// 隐藏分类框
function hideCategoryFrame(){
	$('#categoryFrame').hide();
	// 清空currentCheckedList，确保在点击确认分类是，把勾选的也带上
	currentCheckedList = null;
}

/**
 * 添加一个分类，在"文档分类"单元格中，添加一个新的li元素
 * 分类的个数不能大于5
 * @param {} nodeId 分类的id
 * @param {} name 分类的名称
 */
function addDocCategory(nodeId, name){
	// 如果批量checked列表为空，则把当前的"添加"按钮加过去
	if(currentCheckedList == null){
		currentCheckedList = [];
		currentCheckedList.push(currentAddDom);
	}
	// 遍历数组，批量添加分类
	for(var i=0; i < currentCheckedList.length; i++){
		var node = currentCheckedList[i];
		addOneDocCate(node, nodeId, name);
	}
	// 隐藏分类框
	hideCategoryFrame();
}

// 在“添加”按钮边上，添加一个分类
function addOneDocCate(node, nodeId, name){
	// 当前分类列表
	var $_cateList = $(node).siblings('.cateList');
	// 先数数已经有多少个分类了
	if($_cateList.children().size()>4){
		alert('最多添加五个分类');
		return;
	}
	
	// 构造一个新元素
	var $_newElement = $('<li></li>').html(name).attr('id', nodeId).attr('title', name);
	// 向新元素中添加一个删除按钮
	$_newElement.append($('<span class="delDocCategory" onclick="delDocCategory(this)">X</span>'));
	$_cateList.append($_newElement).show();
}

/**
 * 删除分类，点击了分类li元素中的span.delDocCategory元素
 * @param {} obj 用户点击的span.delDocCategory元素
 */
function delDocCategory(obj){
	$(obj).parent().remove();
}


// HTML加载完毕的JS动作
$(document).ready(function(){
	// 为所有“添加”按钮添加鼠标点击事件响应函数
	$(".addEventType").click(function(e){
		// this是点击的“添加/删除”按钮DOM元素
		var self = this;
		// 先设置当前聚焦的“添加/删除”按钮元素
		currentAddDom = self;
		if(currentAddDom.innerHTML == "删除"){
			removeEType();
			return false;
		}
		// 获取按钮的屏幕相对位置
		var n_top = f_getPosition(self, "Top") - HEIGHT_EFRAME/3, n_left = f_getPosition(self, "Left") - WIDTH_EFRAME;
		// 获取屏幕的高度，和当前鼠标所处的高度
		var clientHeight = $('html')[0].clientHeight, eHeight = e.clientY;
		// 如果鼠标位置比较小，那么选择框的位置就要提高
		if(clientHeight - eHeight < HEIGHT_EFRAME){
			n_top -= HEIGHT_EFRAME/3;
		}
		showETypeList(n_top, n_left);
		// 停止事件冒泡
		return false;
	});
	
	// 为所有文档分类页面的“添加”按钮添加鼠标点击事件响应函数
	$(".addDocCate").click(function(e){
		// this是点击的“添加/删除”按钮DOM元素
		var self = this;
		// 先设置当前聚焦按钮元素
		currentAddDom = self;
		// 显示分类框
		$('#categoryFrame').show();
		// 停止事件冒泡
		return false;
	});
	
	// 为所有的标题连接添加鼠标over事件响应函数
	$('.docTitle').mouseover(function(e){
		// this为当天的标题DOM元素
		var self = this;
		counter++;
		if(currentTitleDom == self && showFlag){
			return false;
		}
		currentTitleDom = self;
		// 获取文档信息
		ajax_getDocInfo(self.getAttribute('_docid'));
		// 获取self的偏移量
		var n_top = f_getPosition(self, "Top") + self.offsetHeight+5, n_left = f_getPosition(self, "Left") + 100;
		// 显示文档信息框
		showDocInfoFrame(n_top, n_left);
	});
	// 文档信息框的mouseover事件，鼠标移到信息框后，只需要显示，不需要异步获取文档内容了
	$('#docInfoFrame').mouseover(function(e){
		showDocInfoFrame();
		return false;
	});
	// 还需要添加移除事件
	$('.docTitle, #docInfoFrame').mouseout(function(e){
		showFlag = false;
		setTimeout(function(){
			if(!showFlag)
				hideDocInfoFrame();
		}, 100);
	});
	// 将类别的编号改为中文字符串
	$('.cateInst').each(function(){
		var eventType = this.innerHTML;	// 获取事件类型号
		// 当前的状态，如果是“事件已分类”就显示“无分类”否则不显示
		if($('#state').val() == 8 || eventType != 0){ // 如果类型号不为0，则将this的html值进行相应修改
			currentAddDom = this.nextSibling;
			updateEType(eventType, event2Str[eventType])
		}
		else{	// 如果为0，则去掉“0”这个值
			$(this).html("");
		}
	});
	
	// 将文档分类的编号，改为中文字符串
	$('.cateList').each(function(){
		var self = this;
		// 取得当前的类别文本
		var catesStr = self.innerHTML;
		// 清空文本
		$(self).html("");
		// 分割字符串，找出类别号不为0的类别，
		currentAddDom = $(self).siblings()[0];
		var cates = catesStr.split(',');
		for(var i = 0; i < cates.length; i++){
			var cateId = parseInt(cates[i]);
			if(cateId > 0){
				var cateName = $("#treeNode_" + cateId).html();
				addOneDocCate(currentAddDom, cateId, cateName);
			}
		}
	});
	
	// 添加分类框的点击事件操作，用户点击空白处则隐藏分类框
	$('#categoryFrame').click(function( event )
    {
    	// 获取事件元素
        var element =  event.target ? event.target : event.srcElement;
        if(element == this){	// 如果事件元素就是分类框（也就是说，没有点击到分类框的子元素）
        	hideCategoryFrame();	// 隐藏分类框
        }
    });
});
