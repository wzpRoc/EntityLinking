/**********************************************************************************************************************************************
 *名称：load.js
 *功能：负责系统各模块页面DOM装载完成后执行的初始化动作管理
 *开发者：彭程
 *时间：2011-9-15
 **********************************************************************************************************************************************/

// 由数字星期对应到汉字星期
var WEEKSTRS = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
// Google链接错误提示信息
var GOOGLEMAP_ERROR_TIP = "无法打开Google地图，可能由于您未连接Internet！";
var GOOGLEMAP_LOADING_TIP = "正在加载地图数据...... 如果长时间内未加载成功，请您检查您是否连接了Internet！";

//命名空间管理,以圆点符号为分隔符，逐层建立命名空间
$.namespace = function() {
    var a=arguments, o=null, i, j, d;
    for (i=0; i<a.length; i=i+1) {
        d=a[i].split(".");
        o=window;
        for (j=0; j<d.length; j=j+1) {
            o[d[j]]=o[d[j]] || {};
            o=o[d[j]];
        }
    }
    return o;
};

/**
 * 使subClass继承自superClass
 */
function extend(subClass, superClass) {
  var F = function() {};
  F.prototype = superClass.prototype;
  subClass.prototype = new F();
  subClass.prototype.constructor = subClass;

  subClass.superclass = superClass.prototype;
  if(superClass.prototype.constructor == Object.prototype.constructor) {
    superClass.prototype.constructor = superClass;
  }
}

// 计算元素相对于body结点的偏移量
function f_getPosition (e_elemRef, s_coord) {
	var n_pos = 0, n_offset,
		e_elem = e_elemRef;

	while (e_elem) {
		n_offset = e_elem["offset" + s_coord];
		n_pos += n_offset;
		e_elem = e_elem.offsetParent;
	}

	e_elem = e_elemRef;
	while (e_elem != document.body) {
		n_offset = e_elem["scroll" + s_coord];
		if (n_offset && e_elem.style.overflow == 'scroll')
			n_pos -= n_offset;
		e_elem = e_elem.parentNode;
	}
	return n_pos;
}

//页面要高亮的关键词数组
$.namespace('$.highlighter');
$.highlighter.keywordList = [];
//高亮指定容器
$.highlighter.execute = function(selector){
	$(selector).highlight(
        $.map($.highlighter.keywordList,
        	function(o) {
        		return '(' + o + ')';
        	}).join('|'),
        { wordsOnly: true }
	);
};

/**
 * 由关键词列表对文档进行高亮
 * @param {} keywordsJSONStr  关键词列表组成的JSON串
 */
var highlighterKeywords = function(keywordsJSONStr) {
	// 如果关键词列表存在，则将JSON串转化为Array对象
	if(keywordsJSONStr)
		$.highlighter.keywordList = $.parseJSON(keywordsJSONStr);

	//关键词高亮
    if ($.highlighter.keywordList && $.highlighter.keywordList.length > 0 && $.isFunction($.highlight)) {
   		//添加一些变换形式
		var highlightLength = $.highlighter.keywordList.length;
		for(var i = 0;i<highlightLength;i++)
		{
			if($.highlighter.keywordList[i] == "") continue;
//		    $.highlighter.keywordList[i] = $.highlighter.keywordList[i].replace(/\./,'');
		    $.highlighter.keywordList.push($.highlighter.keywordList[i].replace(/\./,'').replace(/\s{2,}/,' '));
		}

		$.highlighter.execute('.cluster_box, .news_box_content');
    }
};

/**
 * 根据播放器ID获取播放器引用
 */
function getSWF(swfName)
{
	if (navigator.appName.indexOf("Microsoft") != -1)
	{
		return window[swfName];
	}
	else
	{
		return document[swfName];
	}
}

// 页面跳转时 显示等待信息
function showMessage(text){
	text = text || "页面跳转中，请稍候...";
	$('#dvLoadingMsg p').html(text);
	$('#dvLoadingMsg').show();
}

// 隐藏页面加载信息
function hideMessage(){
	$('#dvLoadingMsg').hide();
}

// trim函数
function trim(ostr){
	return ostr.replace(/^\s+|\s+$/g, "");
}

/**
 * 页面左侧树状节点绑定事件的处理逻辑
 * @param obj 当前用户选择了的聚焦节点
 */
function toggleSubList(){
	// 点击的元素实际上是a.J_hasSubList元素
	var obj = this;
	// 需要显示和隐藏的是a元素的兄弟节点ul
	var subListObj = $(obj).siblings('.subList');
	// 如果前一状态为隐藏，则下一状态设置显示
	if(subListObj.css('display')=="none"){
		// 之前的平辈聚焦节点点击后隐藏
		// 先获取平辈节点
		var $_sibling = $(obj).parent().siblings('.focus_now');
		// 如果平辈中有聚焦节，那么需要隐藏
		if($_sibling.size()>0){
			//$_sibling.children('a').click();
			var $_obj = $_sibling.children('a');
			$_obj.siblings('.subList').hide();
			$_obj.removeClass("focus_sub");
			$_obj.removeClass("focus");
			$_sibling.removeClass('focus_now');
		}
		// 当前选择的聚焦列表显示
		//subListObj.show();
		$(obj).addClass("focus_sub");
		$(obj).parent().addClass("focus_now");

		return true;
	}
	else{	//否则设置为隐藏
//		subListObj.hide();
//		$(obj).removeClass("focus_sub");
//		$(obj).removeClass("focus");
//		$(obj).parent().removeClass('focus_now');
	}
	return false;
}

/**
 * 左侧列表切换
 * 显示当前选择列表，并隐藏其他列表（保留其他列表的标题）
 * @param obj 当前用户选择了的h2节点
 */
function toggleTitleList(obj, moduleName, actionName){
	// 聚焦列表的特殊处理，先做特殊处理，在做聚焦处理，聚焦处理可能包含中断处理操作
	if(specTitleProc(obj, 1))return true;
	// 获得h2节点的兄弟节点，即div.cbside
	var $_sibling = $(obj).siblings().eq(0);
	// 加载聚焦显示样式，只有在当前选择节点未聚焦的情况下才需要处理
	if($(obj).hasClass('title_focus') && $_sibling.css('display')!='none'){
		return true;
	}

	// 先隐藏所有的cbside，用each()函数而不直接用$('.cbside').hide()的目的在于：当用户点击的元素就是聚焦元素的时候，不至于使其也隐藏
	$('.cbside').each(function(){
		if(this !== $_sibling[0])$(this).hide(500);
	});

	// 再显示当前obj元素的兄弟元素
	$_sibling.show(500);

	// 移除原来聚焦节点的样式
	$('.title_focus').removeClass('title_focus');
	// 当前节点加载聚焦样式
	$(obj).addClass('title_focus');

	return true;
}

/**
 * 加载当前页面的聚焦列表
 * 并可以聚焦子列表 2011-10-30添加
 */
function loadCurrentLeftTitle(){
	// 找到当前的聚焦选项的DOM元素
	var cFocusSubNode = $('.focus')[0];
	// 判断聚焦元素是否存在，如果不存在，则不做处理
//	if(!cFocusSubNode){
//		return;
//	}
	// 从该元素开始向上查找父节点，知道找到class为cbside的节点为止
	var cParent = cFocusSubNode;
	var stop = cFocusSubNode ? false : true;
	while(!stop){
		// 首先判断是否已经是目标元素（目标元素的class中有cbside类）
		if(cParent.className.indexOf('cbside')>-1){
			// 如果是，则循环结束
			stop = true;
		}
		else{
			// 如果找到一个结点（ul元素）拥有.subList样式，则也是其兄弟结点（a元素）聚焦
			if(cParent.className.indexOf('subList')>-1){
				$(cParent).siblings('.J_hasSubList').addClass('focus_sub');
				$(cParent).show();
				$(cParent).parent().addClass('focus_now');
			}
			// 如果当前结点(a元素)聚焦，且包含子节点时
			if($(cParent).hasClass('J_hasSubList')){
				// 显示其兄弟节点节点
				$(cParent).addClass('focus_sub');
				$(cParent).siblings().show();
				$(cParent).parent().addClass('focus_now');
			}

			// 否则向上查找
			cParent = cParent.parentNode;
		}
	}
	// 对找到的元素，查找其兄弟节点，即h2元素
	var $_cH2Element = $(cParent).siblings().eq(0);
	// 添加样式
	$_cH2Element.addClass('title_focus');

	//隐藏其他列表
	$('.cbside').hide();
	$(cParent).show();	//显示当前列表

	// 聚焦列表的特殊处理
	if($_cH2Element.size()==0){
		$_cH2Element = $('.title_focus');
	}
	specTitleProc($_cH2Element[0]);
}

/**
 * 每个聚焦列表的一些特殊处理，这个函数在系统管理员页面或者高级用户页面可能会被重写
 * @param {} obj 当前聚焦的h2节点
 * @param {boolean} newAction 判断是否需要跳转到新页面
 */
function specTitleProc(obj, newAction){
	if(!obj)return;
	// 如果是国家
	if(obj.innerHTML.indexOf('国家')>-1){
		// 判断是否需要跳转
		if(newAction){// 跳转
			location.href = "country.action";
			return true;
		}//否则不做处理
	}
	else if(obj.innerHTML.indexOf('工作组')>-1){
		if(newAction){// 跳转
			location.href = "tankUserGroup!allGroup";
			return true;
		}
	}
	else if(obj.innerHTML.indexOf('全部事件')>-1){
		if(newAction){// 跳转
			location.href = "event?eventType=0";
			return true;
		}
		$(obj).siblings().show();
	}
	else if(obj.innerHTML.indexOf('人物分析')>-1){
		if(newAction){// 跳转
			location.href = "entityStatAnalysis?entityType=p";
			return true;
		}
		$(obj).siblings().show();
	}
	else if(obj.innerHTML.indexOf('机构分析')>-1){
		if(newAction){// 跳转
			location.href = "entityStatAnalysis?entityType=o";
			return true;
		}
		$(obj).siblings().show();
	}
	return false;
}


// 右侧列表的toggle函数
/**
 *
 * @param {} obj 当前点击的<a>元素
 */
function rightMore(obj){
	// 找到元素父亲节点的兄弟节点
	var $_toggleItem = $(obj).parent().siblings('ul').children('.toggle_list');
	// 如果是隐藏则改为显示，并切换成滚轴式
	if($_toggleItem.css('display')=="none"){
		var $_ul = $_toggleItem.parent();
		var height = $_ul.height();
		$_ul.css('height', height);
		$_ul.css('overflow-y', 'scroll');
		$_toggleItem.show();
		$(obj).html("<<隐藏");
	}
	else{
		var $_ul = $_toggleItem.parent();
		$_ul.css('overflow-y', 'auto');
		$_toggleItem.hide();
		$(obj).html("更多>>")
	}

	return false;
}


/**
 * 加载Google地图的JS代码，用$.getScript()来做。
 * 开发人：彭程
 * 开发日期：2011-10-21
 */
function loadGoogleMap(){
	// 加载GoogleJS，并调用回调函数
	if(!window.google){
		$.getScript("http://maps.google.com/maps/api/js?sensor=false&callback=map_init");
	}
}

/**
 * 加载当前页面的Google地图，当加载完Google API JS代码后的回调函数
 * 开发人：彭程
 * 开发日期：2011-10-21
 */
function map_init(){
	if(loadGoogleMapForCurpage)loadGoogleMapForCurpage();
}

/**
 * 点击日期，相应日期的切换
 * 暂用于事件页面
 * 开发人：彭程
 */
function refreshDate(dateToday)
{
	// 获得语种选择信息
	var langStr = genLangSelectStr();
	addFormParaThenSubmit('startDate', dateToday, 'endDate', "", 'dateMode', "oneDay", 'lang', langStr);
}
/**
 * 获取今天的日期 例如：20130605
 * 开发人：张强
 * 开发日期：20130605
 * @return
 */
function getToday(){
	var myDate=new Date();
	var today;
	//得到年份2013
	var year=myDate.getFullYear();
	today=year;
	//得到月份6
	var month=myDate.getMonth()+1;
	//如果月份小于10则在前面加‘0’ 得到06
	if(month<10){
		today=today+'0';
	}
	today=today+month;
	//得到日期5
	var day=myDate.getDate();
	//如果日期小于10则在前面加‘0’ 得到05
	if(day<10){
		today=today+'0';
	}
	today=today+day;
	return today;
}

// 由日期区段筛选新闻或者事件
function refreshSecDate(){
	// 获取开始日期和结束日期
	var startDate = $('#right_startDate input').val();
	var endDate = $('#right_endDate input').val();
    startDate = startDate.replace(/-/g, "");
    endDate = endDate.replace(/-/g, "");
	if(startDate!="" && startDate.length==8){
		if(endDate){
			if(endDate < startDate){
				alert("结束日期必须大于等于开始日期！");
				return false;
			}
//			var today=getToday();
//			if(endDate>today){
//				alert("结束日期不能超过今天！");
//				return false;
//			}
		}
		
		// 获得语种选择信息
		var langStr = genLangSelectStr();
		addFormParaThenSubmit('startDate', startDate, 'endDate', endDate, 'dateMode', "section", 'lang', langStr);
	}
	else{
		alert("请填写合法的时间！");
	}
	return false;
}

// 在URL中添加一个参数，
function addFormParaThenSubmit(){
	if(arguments.length>0){

		var url = location.href;
		var paraObj = getParasFromUrl(url, 'currentPageNo');
		// 根据传入的参数添加参数
		if (arguments.length > 0 && arguments.length % 2 == 0)
		{
			for ( var i = 0; i < arguments.length; i = i + 2)
			{
				paraObj[arguments[i]] = arguments[i + 1] ;
			}
		}
		var curForm = document.getElementById("form0") || document.form0 || document.form1;
		if(document.form0){
			resetPartFormParas(paraObj, curForm);
			curForm.submit();
		}
		else{
			refreshFormParas(paraObj, curForm);	// 刷新表单
	        curForm.submit();
		}
	}
}

// 翻页函数
function page(pageName, curPage, actionMethod){
	var url = decodeURI(location.href);
	// 在URL后面加上'?'或者'&'，前者表示之前url无参数，后者表示之前url有参数
	url = (url.indexOf('?')<0) ? (url+'?') : (url + '&');
	var newUrl = url + pageName + ".currentPageNo=" + curPage;

	var paraObj = getParasFromUrl(newUrl)
	// 将URL中的关键词参数放到新的参数列表中，主要是为了避免中文转化为百分号字符的情况
	if(window.search_keywords){
		for(prop in paraObj){
			if(prop.indexOf('strQueryWord')>0){
				paraObj[prop] = window.search_keywords;
				break;
			}
		}
	}
	var curForm = document.form0 || document.form1;
	if(document.form0){
		resetPartFormParas(paraObj, curForm);
	}
	else{
		refreshFormParas(paraObj, curForm);	// 刷新表单
	}

	if(actionMethod && actionMethod!="" && actionMethod!="null"){
		var actionStr = curForm.action + "";
		var aIndex = actionStr.indexOf('.action');
		if(aIndex>0){
			curForm.action = actionStr.subString(0, aIndex) + "!" + actionMethod + ".action"
		}
		else{
			curForm.action = document.form1.action + "!" + actionMethod;
		}
	}
	curForm.submit();
}

// 由URL参数刷新页面表单
function refreshFormParas(paraObj, formDom){
	formDom = formDom || $("form[name=form1]");
	//将parameters对象封装的参数转成HTML表单
	$(formDom).find("input:hidden").remove();
	//添加其他QueryString
	for (para in paraObj)
	{
		$(formDom).append(
				'<input type=hidden name=' + para + ' value="'
						+ paraObj[para] + '" />');
	}
}

// 重置部分表单的项
function resetPartFormParas(paraObj, formDom){
	for (para in paraObj)
	{
		var $_input = $(formDom).find('input[name="' + para + '"]');
		/*if($_input.size()==0){
			$(formDom).append(
				'<input type=hidden name=' + para + ' value="'
						+ paraObj[para] + '" />');
		}
		else*/ if($_input.attr('type') == "hidden")
			$_input.val(paraObj[para]);
	}
}

/**
 * 将URL参数组装成一个JS对象,
 * @param excl 表示将在返回对象中不包括key值为excl的元素。
 * @return paraObj url参数键值对组成的对象
 * 开发人：彭程
 * 最后修改日期：2011-10-19
 */
function getParasFromUrl(url, excl){
	if(!url)
		return null;
	// 获取URL中的参数字段，保存为一个数组
	var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");
	var paraObj = {} ;
	// 如果参数字段为空，则返回元素为空的对象。
	if(url.indexOf("?")<0){
		return paraObj;
	}

	// 遍历参数数组，除了元素名为excl的参数，其余全部装入paraObj对象中。
	var i,j;
	for (i=0; j=paraString[i]; i++){
		if(excl && j.indexOf(excl)>0)
			continue;
		paraObj[j.substring(0,j.indexOf("="))] = decodeURI(j.substring(j.indexOf("=")+1,j.length));
	}

	return paraObj;
}


// 查询框的数据校验
function validSearchForm(){
	// 关键词输入出错
	if($('#search_strQueryWord').val()==""){
		alert("请输入查询关键字！");
		return false;
	}
	// 输入日期不符合格式
	var sDate = $('input[name="parameters.commonParams.startDate"]').val();
	var eDate = $('input[name="parameters.commonParams.endDate"]').val();
	if(sDate!="" || eDate!=""){
		if(sDate!="" && sDate.length!=8){
			alert("请输入有效日期！");
			return false;
		}
		if(eDate!="" && eDate.length!=8){
			alert("请输入有效日期！");
			return false;
		}
	}
	return true;
}


// 加载系统演示日期
function loadResultDate(){
	// 获取开始和结束时间
	var startDate = $('#rightStartDate').val();
	if(!startDate)return;
    if(startDate.length == 10) {
        startDate = startDate.replace('-', '').replace('-', '');
    }

	var endDate = $('#rightEndDate').val();
    if(endDate.length == 10) {
        endDate = endDate.replace('-', '').replace('-', '');
    }

    var dateMode;
    if(myDateMode) {
        dateMode = myDateMode;
    } else {
        dateMode = $('#dateMode').val() || 'oneDay';
    }

	if(onlySection && (onlySection=="true" || onlySection=="1")) {
		//切换到按区段选择模式
		$('#tcal_ss_b').click();
		//删掉切换模式的按钮
		$('#tcalDateSelect').remove();
	}

	// dateMode = startDate == endDate ? "oneDay" : dateMode;

	// 如果有开始和结束日期则换显示方式
	if(dateMode == "section"){
		$('#tcal_ss_b').click();
	}
	else if(dateMode == "" && startDate != '' && endDate != ''){
		$('#tcal_ss_b').click();
	}
	else if(dateMode == "oneDay" || (startDate !='' && endDate == '')){	// 否则是 只选择了一天作为日期条件
		var myDate = new Date(startDate.substring(0,4) + '-' + startDate.substring(4,6) + '-' + startDate.substring(6,8));
		myDate.setHours(12);

		$('.tcalSelected').removeClass('tcalSelected');
		$('td[onclick="f_tcalUpdate('+myDate.valueOf()+')"]').addClass('tcalSelected');
	}//如果是只保留按区段选择的功能
}

// 加载当天日期
function loadTodayDate(){
	// 为了统一各浏览器的日期格式，自己提取日期的年月日和星期属性。
	var myDate = new Date();
	var yearNum = myDate.getFullYear();
	var monthNum = myDate.getMonth()+1;
	var dateNum = myDate.getDate();
	var dateStr = yearNum+"年"+monthNum+"月"+dateNum+"日";
	var weekStr = WEEKSTRS[myDate.getDay()];
	// 设置这些值
	$('#irica_today span').eq(0).html(dateStr).siblings().html(weekStr);
}

/**
 * 响应新闻文档详细页的翻译语言选择框的函数
 * 开发人：彭程
 * 开发日期：2011-10-19
 */
function translateDoc(obj){
	// 提取文档id
	// obj就是select 元素
	var lang = obj.value;
	addFormParaThenSubmit('idLang', lang);
}

// 支持各类更换action后的点击操作
// 比如 地图中点击信息窗信息
function subProduct(actionName){
	// 保存当前页面的action名
	var orig_action = document.form1.action;
	document.form1.action = actionName || arguments[0];
	// 组装新的查询参数
	var paraObj = {};
	if (arguments.length > 1 && arguments.length % 2 == 1)
	{
		for ( var i = 1; i < arguments.length; i = i + 2)
		{
			paraObj[arguments[i]] = arguments[i + 1] ;
		}
	}
	// 更新新的查询参数
	refreshFormParas(paraObj);
	document.form1.submit();

	// 将action切换为原来的action
	document.form1.action = orig_action;
}

// 弹出新页面
function production(actionName){

	var orig_target = document.form1.target;
	// 在新的页面打开
	document.form1.target = "_blank";

	subProduct.apply(this, arguments);
	document.form1.target = orig_target;
}

// 保持自己页面
function productionSelf(actionName){
	subProduct.apply(this, arguments);
}

/**
 * 添加一个新的选择项
 * 这个函数有点偷懒了，以后可以根据具体情况改
 */
function addNewSubItem(obj){
	// 创建一个新的div元素
	var divElement = document.createElement('div');
	divElement.className = "subject_item";
	divElement.innerHTML = '<div class="item_left"><select><option title="" selected value="search_paper_count">包含关键字</option><option title="" value="lcited">地点在</option><option title="" value="lHuaweiScore">包含人物</option><option title="" value="gcited">时间</option></select></div><div class="item_right"><input type="text" value="" size="30"/></div><div class="item_delete">[<a href="#1" onclick="return deleteSubjectItem(this);">删除</a>]</div><div class="cb"></div>';
	// 添加到div.J_add_item元素之前
	$(divElement).insertBefore($(obj).parents('.J_add_item'));
	return false;
}

// 显示/隐藏用户兴趣定制列表
function toggleUserCatorgoryList(obj){
	if($(obj).html()=="添加新兴趣"){
		$(obj).html("隐藏分类列表");
		$('#J_category').show(500);
	}
	else{
		$(obj).html("添加新兴趣");
		$('#J_category').hide(500);
	}
	return false;
}

// 用户兴趣定制页面，响应用户的分类点击
function addNewInterest(){
	// 绑定事件后this为当前被点击的元素
	// 取得当前选中的分类内容
	var intTitle = this.innerHTML;
	// 如果已经选择过，就停止
	if(hasSelected(intTitle)){
		alert('您已经选择了“' + intTitle + '”作为您的兴趣之一！');
		return false;
	}
	// jQuery复制新的兴趣dom节点
	var $_newIntElement = $('#J_empty_subject_item').clone();
	// 修改新元素的内容
	$_newIntElement.removeAttr('id');
	$_newIntElement.children('h2').html(intTitle);
	$_newIntElement.show();
	// 将该元素插入
	$_newIntElement.insertAfter($('#J_empty_subject_item'));
	// 分类框隐藏
	$('#categoryToggle').html("添加新兴趣");
	$('#J_category').hide(500);
}

// 删除用户定制选项
function deleteSubjectItem(obj){
	$(obj).parents('.subject_item').hide();
	return false;
}

// 判断当前用户是否已经选择了某个分类
function hasSelected(intTitle){
	var $_hList = $('.contentTitle_box').children('h2');
	for(var i=0; i < $_hList.length; i++){
		if($_hList[i].innerHTML == intTitle){
			return true;
		}
	}
	return false;
}

// 获得语种选择的参数
function genLangSelectStr(){
	// 先提出所有选中的语言选项
	var $_checked = $('.langSel:checked');
	if($_checked.length<1)return "";
	// 拼接语言选项字符串
	var checked = [];
	for(var i=0; i< $_checked.length; i++){
		checked[i] = $_checked[i].value;
	}
	return checked.join(',');
}

// 语种选择绑定的处理函数
function langSelect(){
	var langStr = genLangSelectStr();
	// 页面开始跳转，提示加载信息
	showMessage();
	addFormParaThenSubmit('lang', langStr);
}

// 日期选择绑定的处理函数，用户选择“按日选择”或者“按区段选择”
function tcalSelect(){
	// 当前的this元素为tcal_ds_b或者tcal_ss_b
	if($(this).hasClass('tds_focus'))return;
	// 修改聚焦样式
	$(this).addClass('tds_focus').siblings().removeClass('tds_focus');
	// 显示相应区域元素
	var displayId = '#' + this.id.substring(0, this.id.lastIndexOf('_'));
	$(displayId).show().siblings().eq(1).hide();
}

// 在某些页面日期选择和语种选择得不到响应
function disableAdvancedOntion(){

	var disableTip = "当前页面无法使用该功能...";
	// 禁用日期选择框
	// 先隐藏日期选择方式栏
	$('#tcalDateSelect').hide();
	// 再禁用日历上的onclick事件
	$('#tcal_fixed td').removeAttr('onclick').css('cursor', 'default');
	// 日历上提供提示信息
	$('#tcal_fixed').attr('title', disableTip);

	// 禁用语种选择框和国家选择框
	$('.box_right .search_advanceOption').children().attr('disabled', 'true');
	$('.box_right .search_advanceOption').parent().attr('title', disableTip);
}

// 异步加载右侧关键词列表
function doLoadRightKeywordList(){
	if($("#right_keyword_box").size()==0)return;

	// 用Ajax请求
	var curHref = location.href;
	// 区分tank页面和智库文章查询页面
	curHref = actionType == 'tank' ? curHref.replace(/tank/, "docListForKeyword?tankTag=1") : curHref.replace(/docList/, "docListForKeyword");
	$.post(curHref, function(data){
		$("#right_keyword_box").replaceWith(data);
	});
}

// 切换新闻列表页面的查询选项框
function toggleTopOptions(obj){
	var text = obj.innerHTML;
	var $_top = $('.top_options:first');
	if(text.indexOf("展开")>-1){
		$_top.slideDown();
		obj.innerHTML = "收起查询选项";
	}
	else {
		$_top.slideUp();
		obj.innerHTML = "展开查询选项";
	}
	return false;
}

/**
 * DROPDOWN
 * 导航栏的下拉效果，就这么点代码哈...
 */
function dropdownMenu() {
	$("ul.navigation ul ").css({
		display: "none"
	});
	$("ul.navigation li").each(function() {
		var $dropdownMenu = $(this).find('ul:first');
		$(this).hover(function() {
			$dropdownMenu.stop().css({
				overflow: "hidden",
				height: "auto",
				display: "none"
			}).slideDown(400, function() {
				$(this).css({
					overflow: "visible",
					height: "auto"
				});
			});
		}, function() {
			$dropdownMenu.stop().slideUp(400, function() {
				$(this).css({
					overflow: "hidden",
					display: "none"
				});
			});
		});
	});
}

// 给标签添加事件响应函数
function addListenerForTab(){
	$(".search_result_tags li a").click(function(){
		var $_self = $(this);
		var tabId = $_self.attr('href');
		// 切换显示
		$(tabId).removeClass('none').siblings('.tab_box').addClass('none');
		// 标签切换
		$_self.parent().addClass('selectTag').siblings().removeClass('selectTag');

		return false;
	});
}

// 加载热点信息列表
function loadHotList(){
	var $_loading = $("#loading_hotList");
	if($_loading.size()>0){
		// 取得当前的日期语种参数
		var urlParams = getParasFromUrl(location.href);
		var params = {};
		params['dateMode'] = urlParams['dateMode'];
		params['startDate'] = urlParams['startDate'];
		params['endDate'] = urlParams['endDate'];
		params['lang'] = urlParams['lang'];

        // added by lutm at 20130513
        // 得到主form
        var form = document.getElementById("form0");
        if(params['startDate'] == null || params['startDate'] == '') {
            // url中的参数没有，尝试使用form中的
            if (form) {
                // 主form存在
                // 赋值
                if (form['dateMode']) {
                    params['dateMode'] = form['dateMode'].value;
                }
                if (form['startDate']) {
                    params['startDate'] = form['startDate'].value;
                }
                if (form['endDate']) {
                    params['endDate'] = form['endDate'].value;
                }
                if (form['lang']) {
                    params['lang'] = form['lang'].value;
                }
            }
        }

        if(form) {
            if (form['entityTypeForHotList']) {
                params['entityTypeForHotList'] = form['entityTypeForHotList'].value;
            }
            if (form['entityIdForHotList']) {
                params['entityIdForHotList'] = form['entityIdForHotList'].value;
            }
        }

        // 组装Ajax方法
		var options = {
			url : "hotlist",
			data: params,
			success : function(data){
				$_loading.replaceWith(data);
			},
			error: function(){
				$_loading.html("加载失败，请检查网络是否正常！")
			}
		};
		$.ajax(options);
		return false;
	}
}

//显示 “密码修改框”
function showPwdBox(){
	$("#pwd2").val($("#pwd1").val());
	$('table .pwdBox').show();
	$('#btn_modify').hide();
}

// 验证用户修改信息
function checkUserDetail(){
	// 检查两个密码是否相同
	var pwd1 = $("#pwd1").val();
	var pwd2 = $("#pwd2").val();
	

	if(pwd1 == "" && pwd2 == ""){
		$("#newPwd").val("");
		$("#userDetialAction")[0].submit();
	}
	else if(pwd1 == pwd2){
		$("#newPwd").val(pwd1);
		$("#userDetialAction")[0].submit();
	}
	else{
		alert("两次输入的密码不一致！");
	}
}

function resetForm(){
	$("#add-item-form")[0].reset();
}


/**
 * 增加方法到“onload”事件上
 * @param f_func
 */
function addOnload(f_func) {
    if (document.addEventListener) {
        window.addEventListener('load', f_func, false);
    }
    else if (window.attachEvent) {
        window.attachEvent('onload', f_func);
    }
    else {
        var f_onLoad = window.onload;
        if (typeof window.onload != 'function') {
            window.onload = f_func;
        }
        else {
            window.onload = function() {
                f_onLoad();
                f_func();
            }
        }
    }
}

// 页面加载完毕后执行的操作
$(document).ready(function(){
 	//为表单中的checkbox元素添加响应事件
//	$('#form_search input[type="checkbox"]').click(function(){
//		this.value='false';
//		if(this.checked)this.value='true';
//	});

	// 页面指定日期
	loadResultDate();
	// 加载当天日期
	loadTodayDate();
	// 加载当前左侧列表的聚焦列表
	loadCurrentLeftTitle();
	// 导航栏的下拉效果
	dropdownMenu();
	// 加载右侧热点信息列表
	loadHotList();
	// 设置日期输入框为readonly的
	$('.tcalInput').attr('readonly', 'true');

	// 搜索页面如果没有结果就不显示 搜索结果框
	if($('#search_strQueryWord').size()>0 && $('#search_strQueryWord').val()==""){
		$('.contentTitle_box').eq(1).hide();
	}
	// 国家列表控制宽度
	$('.countryList li').each(function(){
    	if($(this).width()>145){
        	$(this).width(295);
    	}
	});

	//TODO 用户登出按钮相应事件

    // 开始用户消息更新器
    if(window.startUserMessageUpdater)startUserMessageUpdater();

    // 加载评论
    try {
        doLoadCommentFunc();
    } catch (e){
        // alert(e);
    }

    // 加载右侧关键词
    doLoadRightKeywordList();

    $('.eInType.none').removeClass('none');
});