/**********************************************************************************************************************************************
 *名称：common.js
 *功能：系统常用函数（暂时没用）
 *开发者：毛权
 *时间：2010-9-7
 *长度：300
 *********************************************************************************************************************************************
 */

//是否输出调试信息
var isDebug = false;
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
//注册.script.file
$.namespace("$.script.file");

$.extend({
    includePath: '',
    include: function(file)
    {
        var files = typeof file == "string" ? [file] : file;
        for (var i = 0; i < files.length; i++)
        {
            var name = files[i].replace(/^\s|\s$/g, "");
            var att = name.split('.');
            var ext = att[att.length - 1].toLowerCase();
            var isCSS = ext == "css";
            var tag = isCSS ? "link" : "script";
            var attr = isCSS ? " type='text/css' rel='stylesheet' " : " language='javascript' type='text/javascript' ";
            var link = (isCSS ? "href" : "src") + "='" + $.includePath + name + "'";
            if ($(tag + "[" + link + "]").length == 0) document.write("<" + tag + attr + link + "></" + tag + ">");
        }
    }
});

//$.script.file.server = {
//	AJAX_LOGIN : "dwr/interface/AjaxLogin.js",
//	AJAX_INTERFACE : "dwr/interface/AjaxInterface.js",
//	GOOGLE_SEARCH : 'js/googleSearch.min.js',
//	MD5 : 'js/md5.min.js'
//};

//作DEMO的研究机构ID列表
var DEMO_DISTRIBUTE_LIST = ['517','518','3','51'];

//封装请求参数，由服务器设置
var parameters = null;
//当前页面参数，用于保护在页面跳转之后，复原原来的请求参数
var orig_parameters = null;

//当前界面使用语言 如：中文简体、英文、中文繁体等,默认为中文简体
$.namespace('$.i18n.lang');
$.i18n.lang = 'zh_CN';

//登录表单
$.namespace('$.login.form');
$.login.form = null;

//IE下兼容console.log
if(typeof console == 'undefined') console = {};

if(!console.log)
{
	console.log = function(msg)
	{
		alert(msg + ' (弹出此信息通常情况下为开发结束后未删除相关调试语句！)');
	};
}
if(!isDebug) console.log = function(){};

//打印调试语句
function log(msg)
{
	console.log(msg);
}
////DWR的异常处理
//DWREngine.setErrorHandler(function(error){
//	log(error);
//});

//设置技术分布图FLASH文件
$.namespace('$.distribute');
$.distribute.flash = {
	atur : 'data/aturMap.swf',
	google : 'data/googleMap.swf'
}
/**
 * 切换查询类别，如由论文查询转到专利查询
 */
function toggleSearchBy(searchBy)
{
	//设置菜单样式
	$('#menu_'+searchBy).addClass('active').siblings().removeClass('active');
	// 更新首页提示
	updateIndexTip(searchBy)
	//设置高级查询按钮样式
	resetAdvancePart();
	//提交表单
	submitSearchForm();
}
/**
 * 更新首页的提示内容
 */
function updateIndexTip(searchBy){
	if(searchBy != 'partner'){
		$('#index_title').show();
		var tipContentList = lang['title_index_'+searchBy].split(';');
		$('#index_title').empty();
		for(var i = 0; i < tipContentList.length; i++){
			var item = document.createElement('p')
			item.innerHTML = tipContentList[i];
			$('#index_title').append(item);
		}
    }
	else{
		$('#index_title').hide();
	}
}
/**
 * 设置首页中输入栏下部的高级查询项的更新
 */
function resetAdvancePart(){
	if($('#advance_searchType').size() !== 0){
		var activeNodes = $('div.ggMenu > a.active');
		var searchBy = activeNodes[0].id;
	    searchBy = searchBy.substring(searchBy.indexOf("_")+1);	
	    
	    // 先重置单选框
	    // 默认选项为普通列表模式
		$('#searchType1').attr('checked', 'true');
	    
	    if(searchBy === enumVars.PARTNER){
	    	$('#advance_searchType').hide();
	    	return;
	    }
	    	
		// 然后设置查询方式是否可见，判断依据是现在是否处于技术分布查询阶段。
		// 如果是技术分布或者是研究人员，则设置为不可见，否则可见
		if(searchBy !== 'distribute' && searchBy !== 'faculty'){
			$('#advance_searchType').show();
		}
		else {
			$('#advance_searchType').hide();
		}
	}
}

/**
 * 过滤查询无效字符的正则表达式
 */
var reg = /\+|,|\'|\"|\*|~|!|\@|\$|%|\^|\&|\{|\}|<|>|\?|\|/g;// '
/**
 * 后台返回的搜索状态
 */
var searchState = null;

/**
 * 展开/收缩论文（专利/项目）摘要
 */

function toggleAllSummary()
{
	if($('#summary_showHideAllMenu').html().indexOf(lang.show_all_abstract)>-1)
	{
		$('#summary_showHideAllMenu').html(lang.hide_all_abstract);
		$('.hover-item div[id^=summary_]').show();
	}
	else if($('#summary_showHideAllMenu').html().indexOf(lang.hide_all_abstract)>-1)
	{
		$('#summary_showHideAllMenu').html(lang.show_all_abstract);
		$('.hover-item div[id^=summary_]').hide();
	}
	
}

/**
 * 展开/收缩论文（专利/项目）摘要
 */
function toggleSummary(summaryIndex)
{
	$('#summary_' + summaryIndex).toggle(500);
}

 /**
  * 关闭候选研究人员列表
  */
function hideCandidateFacultyList()
{
	$('#candidateFacultyList').hide(500);
}

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

/**
 * 切换Locale
 */
function changeLocale(localeStr)
{
	$.i18n.lang = localeStr;
	if(typeof AjaxInterface == 'undefined')
	{
		$.getScript($.script.file.server.AJAX_INTERFACE,function(){
			AjaxInterface.i18n(localeStr,function(){
				location.reload();
			});
		});
	}
	else
	{
		AjaxInterface.i18n(localeStr,function(){
			location.reload();
		});
	}
	
}

/**
 * 用于页面顶部提示信息的显示和隐藏
 * @param msg
 * @return
 */
function showMessage(msg)
{
	if(msg==null)
	{
		msg = lang.system_wait;
	}
	$('#spnLoadingMsg').html(msg);
	$('#dvLoadingMsg').show();
}
function hideMessage()
{
	$('#dvLoadingMsg').hide();
}

/**
 * 复制一个对象
 * @param object
 * @return
 */
function clone(object)
{
	var F = function(){};
	F.prototype = object;
	return new F;
}

/**
 * 为所有的回调函数添加bind事件，用于解决在某些情况下作用域改变问题
 * 
 * @param obj
 * @return
 */
Function.prototype.bind = function(obj) {   
	var method = this;
	return function() {
		return method.apply(obj, arguments); 
	};
}

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

/**
 * 解析URL以判断当前检索类型
 */

function getParamByName(name)
{
	if(name.indexOf('parameters.')!=0)
		name = 'parameters.' + name;
	var start = location.search.indexOf(name)+name.length+1;
	return location.search.substring(
				start,
				location.search.indexOf('&',start)
			);
}

/**
 * 正则表达式保留字转义
 * @param pattern
 * @return
 */
function rgExpEscape(pattern)
{
	return pattern.replace(/\^\$\.\*\+\?\=\!\:\|\\\/\(\)\[\]\{\}/,'');
}

/**
 * 获取盒模型的真实宽
 * @param jqueryObject
 * @return
 */
function getJWidth(jqueryObject)
{
	if(jqueryObject.size()==0) return;
	var borderLeftHeight = parseInt(jqueryObject.css('border-left-width').replace(/[^0-9]+/,''));
	borderLeftHeight = isNaN(borderLeftHeight)?0:borderLeftHeight;
	var borderRightHeight = parseInt(jqueryObject.css('border-right-width').replace(/[^0-9]+/,''));
	borderRightHeight = isNaN(borderRightHeight)?0:borderRightHeight;
	return jqueryObject.width() 
		+ parseInt(jqueryObject.css('margin-left').replace(/[^0-9]+/,'')) 
		+ parseInt(jqueryObject.css('margin-right').replace(/[^0-9]+/,'')) 
		+ parseInt(jqueryObject.css('padding-left').replace(/[^0-9]+/,'')) 
		+ parseInt(jqueryObject.css('padding-right').replace(/[^0-9]+/,''))
		+ borderLeftHeight
		+ borderRightHeight;
}
/**
 * 获取盒模型的真实高度
 * @param jqueryObject
 * @return
 */
function getJHeight(jqueryObject)
{
	if(jqueryObject.size()==0) return;
	var borderTopHeight = parseInt(jqueryObject.css('border-top-width').replace(/[^0-9]+/,''));
	borderTopHeight = isNaN(borderTopHeight)?0:borderTopHeight;
	var borderBottomHeight = parseInt(jqueryObject.css('border-bottom-width').replace(/[^0-9]+/,''));
	borderBottomHeight = isNaN(borderBottomHeight)?0:borderBottomHeight;
	
	return jqueryObject.height() 
		+ parseInt(jqueryObject.css('margin-top').replace(/[^0-9]+/,'')) 
		+ parseInt(jqueryObject.css('margin-bottom').replace(/[^0-9]+/,'')) 
		+ parseInt(jqueryObject.css('padding-top').replace(/[^0-9]+/,'')) 
		+ parseInt(jqueryObject.css('padding-bottom').replace(/[^0-9]+/,''))
		+ borderTopHeight
		+ borderBottomHeight;
}
/**
 * JSONP请求类，用于异步加载JS,跨域发送异步请求等
 * @param fullUrl
 * @return
 */
function JSONscriptRequest(fullUrl)
{
    this.fullUrl=fullUrl;
    this.noCacheIE='&noCacheIE='+(new Date()).getTime();
    this.headLoc=document.getElementsByTagName("head").item(0);
    this.scriptId='JscriptId'+JSONscriptRequest.scriptCounter++;
};

JSONscriptRequest.scriptCounter=1;
JSONscriptRequest.prototype.buildScriptTag=function(){
    this.scriptObj=document.createElement("script");
    this.scriptObj.setAttribute("type","text/javascript");
    this.scriptObj.setAttribute("src",this.fullUrl);
};
JSONscriptRequest.prototype.removeScriptTag=function(){
    this.headLoc.removeChild(this.scriptObj);
};
JSONscriptRequest.prototype.addScriptTag=function(){
    this.headLoc.appendChild(this.scriptObj);
};

//添加到收藏
function add_favorite()
{
    var url = location.protocol + '://' + location.host + location.pathname;
	if (document.all)  
	{
		$("#homepage")[0].setHomePage(url);
	}
	else if (window.sidebar)
	{
		window.sidebar.addPanel(lang.webSiteName, url, "");
	}
}


/**
 * 获得一个月的最后一天
 * @param year 年
 * @param month 月
 * @return Date类型的日期
 */
function getLastDateOfMonth(year, month) {
	var next_yyyy_mm;
	if(month == 12) {
		next_yyyy_mm = (year + 1) + "-01";
	} else {
		var nextMonth = month + 1;
		var nextMM;
		if(nextMonth >= 10) {
			nextMM = nextMonth;
		} else {
			nextMM = "0" + nextMonth;
		}
		next_yyyy_mm = year + "-" + nextMM;
	}

	var firstDayOfNextMonth = new Date(next_yyyy_mm+"-01");
	var lastDayOfThisMonth = new Date(firstDayOfNextMonth - 24 * 3600 * 1000);

	return lastDayOfThisMonth;
}


/**
 * 返回YYYY_MM_DD形式的日期字符串
 * @param date
 */
function getYYYY_MM_DD(date) {
    if(!date) {
        // 如果参数为空，那么取当天
        date = new Date();
    }
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var dayOfMonth = date.getDate();

    return year
        + "-" + (month<=9 ? ("0"+month) : month.toString())
        + "-" + (dayOfMonth<=9 ? ("0"+dayOfMonth) : dayOfMonth.toString());
}
