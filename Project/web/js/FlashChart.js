/**********************************************************************************************************************************************
 *名称：FlashChart.js
 *功能：系統相关的所有Flash图表类，基类：FlashChart，子类包括：曲线图、SNS、分布图等
 *开发者：毛权
 *时间：2010-7-18
 *时间：2010-8-11
 *长度：250
 *更新日志:增加了获取图表实例的单例模式
 **********************************************************************************************************************************************
 */
//注册.chart命名空间
$.namespace("$.chart.instance");

/**
 * FlashChart类，封装Flash图表的基本操作
 * 
 * @param playerId
 *            播放器ID
 * @param jsonStr
 *            JSON数据
 * @param ajaxMethod
 *            异步请求的服务器方法
 * @return
 */
function FlashChart(playerId, jsonStr, ajaxMethod)
{
	//播放器ID
	this.id = playerId;
	//JSON数据源
	this.json = jsonStr;
	//服务器方法
	this.ajaxMethod = ajaxMethod;
	//FLASH提供给客户端的刷新播放器自身的接口
	this.playerRefreshMethod = null;

	//刷新flash播放器
	this.refresh = function()
	{
		if (arguments.length == 1)
		{
			this.json = arguments[0];
		}
		this.getPlayer().refresh();
	};

	//获取JSON数据
	this.getJSONStr = function()
	{
		return this.json;
	};
	//设置JSON数据
	this.setJSONStr = function(jsonStr)
	{
		this.json = jsonStr;
	};
	//获取播放器引用
	this.getPlayer = function()
	{
		return getSWF(this.id);
	}

	//设置播放器数据源
	this.setPlayerJSONStr = function()
	{
		this.getPlayer().setChartJSON(this.getJSONStr());
	};

	//调用服务器方法，根据返回的数据刷新播放器
	//此类一般需子类覆盖
	this.change = function()
	{
		this.ajaxMethod(arguments, this.refresh.bind(this));
	}
}