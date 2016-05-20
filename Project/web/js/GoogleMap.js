/**********************************************************************************************************************************************
 *名称：GoogleMap.js
 *功能：JS实现的googleMap
 *开发者：彭程
 *时间：2011-6-14
 **********************************************************************************************************************************************
 */

/**
 * 
 * @param mapId DOM中div元素的id
 */
function GoogleMap(mapId, isSave, zoomSize, latitude, longitude){
	
	// 地图所在DOM中div元素的id
	this.id = mapId;
	this.map = null;
	// 后台传入的JSON串，JSON串有地理信息数组组成
	this.json = null;
	// 解析JSON串得到的信息，让在这个array中
	this.array = null;
	// 地图Marker标记数组
	// 将标记放到数组中的目的是用来清除掉地图上现有的所有标记
	// 如果没有清除标记的操作，那么可以不用将标记保存到标记数组中
	this.markersArray = [];
	// 用来判定是否需要保存标记的标志位
	var mSaved = isSave ? isSave : true;
	
	// 为了保证相同位置的标记不重复出现，这里用一个markerSet来判断某个 坐标 的marker是否存在
	this.markerSet = {};
	
	this.nInstSet = {};
	
	// 当前正在显示的信息窗口，用于在地图上切换信息窗口
	var currentInfo = null;
	
	// 加载google map
	this.init = function(){
		// 以中国作为中心
		try{
			var myCenter = latitude ? new google.maps.LatLng(transNSCoord(latitude), transWECoord(longitude)) : new google.maps.LatLng(39.92, 110.46);
		    var myOptions = {
		      zoom: zoomSize || 2,
		      center: myCenter,
		      mapTypeId: google.maps.MapTypeId.ROADMAP
		    }
		    
			this.map = new google.maps.Map(document.getElementById(this.id), myOptions);
			
			// 加载地图上显示的特有图标
			this.initImages();
			
				
		}catch(e){
			alert("无法连接至Google服务器!");
			throw e;
		}
	}
	
	// 加载图标的方法，这个主要用于子类加载地图上显示的各种图表
	this.initImages = function(){
	}
	
	// 加载标记
	this.loadMarkers = function(){
		// 将数组中的每一个点加上一个坐标
		var arrays = this.array;
		
		for(var i = 0; i < arrays.length; i++){
			// 添加一个标记
			var marker = this.addNewMarker(arrays[i].latitude, arrays[i].longitude);
    		// 加载标记响应事件
			if(marker != null){
				this.addDetail(marker, eventArrays[i]);
			}
		}
	}
	
	// 添加一个标记
	// 从对象中获取标记的坐标，标题，图标等等
	this.addNewMarker = function(mkLatitude, mkLongitude, mkTitle, mkIcon, mkShape, nInst){
		// 判断坐标是否合法
		if(mkLatitude == null || mkLongitude == null || mkLatitude == "" || mkLongitude == "" || mkLatitude == "null" || mkLongitude == "null"){
			return null;
		}
		var isNew = false;
		// 首先判断该坐标的节点是否已近存在，（标签允许有重叠，下面这部分代码可以不要了2013-05-15）
//		if(this.nInstSet[mkLatitude + ',' + mkLongitude]){
//			//如果存在，判断其实例大小是否比当前的实例大小大
//			if(nInst <= this.nInstSet[mkLatitude + ',' + mkLongitude]){
//				return null;
//			}
//			// 设置当前的最大实例值
//			this.nInstSet[mkLatitude + ',' + mkLongitude] = nInst;
//		}
//		else{
//			// 如果不存在，则将实例大小放到markerSet中
//			this.nInstSet[mkLatitude + ',' + mkLongitude] = nInst;
//			// 
//			isNew = true;
//		}
		
		// 将南北东西坐标转化为正负坐标
		mkLatitude = transNSCoord(mkLatitude);
		mkLongitude = transWECoord(mkLongitude);
		
		if(mkLatitude == null || mkLongitude == null)return null;
		
		// 创建标记
		var loc = new google.maps.LatLng(mkLatitude, mkLongitude);
		var marker = new google.maps.Marker({
	        position: loc,
	        title: mkTitle,
	        icon: mkIcon,
	        mkShape: mkShape,
	        shadow: this.shadow,
	        map: this.map
   		});	
   		
//   		// 判断是不是新来的一个
//   		if(isNew){
//   			this.markerSet[mkLatitude + ',' + mkLongitude] = marker;
//   		}
//   		else{
//   			this.markerSet[mkLatitude + ',' + mkLongitude].setMap(null);
//   			this.markerSet[mkLatitude + ',' + mkLongitude] = marker;
//   		}
   		
   		return marker;
	}

	// 添加一个标记详细内容，这个方法一般用来重写
	this.addDetail = function(marker, eventObj) {
		// 标记的info内容
	}
	
	// 控制信息窗口的显示
	this.setInfoWindow = function(infowindow, marker){
		// 只有在确定要保存的情况下载将marker放到数组中
    	if(mSaved)this.markersArray.push(marker);
		// 将当前传入的信息窗口打开，并隐藏其他的信息窗口
		google.maps.event.addListener(marker, 'click', function() {
    		if(currentInfo){
    			currentInfo.close();
    		}
    		currentInfo = infowindow;
      		infowindow.open(this.map,marker);
    	});
	}

	// 清楚所有的标记
	this.clearMarkers = function(){
		if (this.markersArray) {
			for (var i in this.markersArray) {
				this.markersArray[i].setMap(null);
			}
			this.markersArray.length = 0;
			this.nInstSet = {};
		}
	}

	// 刷新map中的标记
	this.refresh = function(jsonStr){
		if(jsonStr){
			this.setJSON(jsonStr);
		}
		// 清除现有的所有标记		
		this.clearMarkers();

		this.loadMarkers();
	}
	
	this.setJSON = function(jsonStr){
		this.json = jsonStr;
		this.array = $.parseJSON(this.json);
	}

	this.setArray = function(array){
		this.array = array;
	}
	
	// 将南北东西坐标转化为正负坐标
	function transNSCoord(mkLatitude){
		// 将南北坐标转化为正负坐标
		var index = mkLatitude.indexOf('°');
		if(index < 0){
			index = mkLatitude.indexOf(' ');
		}
		if(index < 0){
			return null;
		}
		var latDir = mkLatitude.substring( index + 1);
		mkLatitude = mkLatitude.substring(0, index);		
		if(latDir == 'S'){
			mkLatitude = 0 - mkLatitude;
		}
		return mkLatitude;
	}
	
	function transWECoord(mkLongitude){
		var index = mkLongitude.indexOf('°');
		if(index < 0){
			index = mkLongitude.indexOf(' ');
		}
		if(index < 0){
			return null;
		}
		var lonDir = mkLongitude.substring( index + 1);
		mkLongitude = mkLongitude.substring(0, index);	
		if(lonDir == 'W'){
			mkLongitude = 0 - mkLongitude;
		}
		return mkLongitude;
	}
};

// 武装冲突世界地图，继承自默认的GoogleMap
function ArmedConflictGoogleMap(mapId, isSaved){
	// 先调用父类的构造方法
	ArmedConflictGoogleMap.superclass.constructor.call(this, mapId, isSaved);
	// 定义自己的变量
	this.image = null;
	this.bigImage = null;
	this.shape = null;
	this.bigShape = null;

	// 加载武装冲突图标的方法
	this.initImages = function(){
		// 来两个自己的图标
		this.image = new google.maps.MarkerImage('images/ac.png', new google.maps.Size(16, 16), new google.maps.Point(0,0), new google.maps.Point(8, 8), new google.maps.Size(16, 16));
		this.bigImage = new google.maps.MarkerImage('images/ac.png', new google.maps.Size(20, 25), new google.maps.Point(0,0), new google.maps.Point(15, 15), new google.maps.Size(20, 25));
		this.shape = {
				coord: [9, 9, 9],
				type: 'circ'
			};
		this.bigShape = {
				coord: [15, 15, 15],
				type: 'circ'
			};
	}
	
	// 加载标记
	this.loadMarkers = function(){
		// 将数组中的每一个点加上一个坐标
		var eventArrays = this.array;
		
		for(var i = 0; i < eventArrays.length; i++){
			// 地图标记中的各种数据
			var mkTitle = eventArrays[i].name;
			var mkIcon = (eventArrays[i].nInst>2)? this.bigImage :this.image;
			var mkShape = (eventArrays[i].nInst>2)? this.bigShape : this.shape;
			// 地图上加载一个新标记
			var marker = this.addNewMarker(eventArrays[i].latitude, eventArrays[i].longitude, mkTitle, mkIcon, mkShape);
    		// 加载标记响应事件
			if(marker!=null)this.addDetail(marker, eventArrays[i]);
		}
	}
	
	// 添加一个标记详细内容
	this.addDetail = function(marker, eventObj) {
		// 标记的info内容
		var contentString = '<div class="mapContent">'+
        	'<h2 class="firstHeading" onclick="return production(\'event!detail\',\'id\',' + eventObj.id + ')">' + eventObj.name + ", " + eventObj.locationName + '</h2>' +//'</div>';
        	'<div class="bodyContent">' +
        	'<ul>';
        
        // 添加各个数据，数据大于0才显示
        if(eventObj.slot && eventObj.slot.vicStr!="--"){
        	contentString += '<li><span>Death toll: </span><a>' + eventObj.slot.vicNum + '</a></li>';
        }
        if(eventObj.slot && eventObj.slot.kilStr!="--"){
        	contentString += '<li><span>Murder: </span><a>' + eventObj.slot.kilStr + '</a></li>';
        }
        if(eventObj.time){
        	contentString += '<li><span>Occur Time: </span><a>' + eventObj.time.substring(0,11) + '</a></li>';
        }
        if(eventObj.nInst && eventObj.nInst>0){
        	contentString += '<li><span>Reported times: </span><a>' + eventObj.nInst + '</a></li>';
        }
        
        contentString += '</ul>'+'</div>'+'</div>';
        
        var infowindow = new google.maps.InfoWindow({
        	content: contentString,
        	flat: true
    	});  
    	
    	this.setInfoWindow(infowindow, marker);
	}
}
// 让武装冲突世界地图继承自GoogleMap
extend(ArmedConflictGoogleMap, GoogleMap);

// 国家形象世界地图
function CountryImageGoogleMap(mapId, isSaved, zoomSize, latitude, longitude){
	// 先调用父类的构造方法
	CountryImageGoogleMap.superclass.constructor.call(this, mapId, isSaved, zoomSize, latitude, longitude);
	this.images = [];
	this.bigImages = [];
	this.shape = null;
	this.bigShape = null;
	
	// 加载支持反对图标的方法
	this.initImages = function(){
		var i = 0;
		for(i = 0; i < 11; i++){
			this.images[i] = new google.maps.MarkerImage('images/circle'+i+'_70.png', new google.maps.Size(70, 42), new google.maps.Point(0,0), new google.maps.Point(25, 15), new google.maps.Size(50, 30));
			this.bigImages[i] = new google.maps.MarkerImage('images/circle'+i+'_70.png', new google.maps.Size(70, 42), new google.maps.Point(0,0), new google.maps.Point(35, 21), new google.maps.Size(70, 42));
		}
		// 加载小形状，用于确定图标焦点
		this.shape = {
				coord: [1, 1, 1, 28, 50, 28, 50, 1],
				type: 'poly'
			};
		// 设置大形状
		this.bigShape = {
				coord: [1, 1, 1, 40, 70, 40, 70, 1],
				type: 'poly'
			};
	}
	
	// 加载标记
	this.loadMarkers = function(){
		// 话题数组
		var opinionList = this.array;
		
		// 遍历话题数组，添加地图标记
		for(var i = 0; i < opinionList.length; i++){
			var opinion = opinionList[i];
			var country = opinion.holderCountry;			
			// 创建标记
			var mkTitle = country.name_zh;
			var mkIcon = ((opinion.nrPositive + opinion.nrNegtive)>19)? this.bigImages[this.calIndex(opinion)] : this.images[this.calIndex(opinion)];
			var mkShape = ((opinion.nrPositive + opinion.nrNegtive)>19)? this.bigShape : this.shape;
			// 添加标记
			var marker = this.addNewMarker(country.latitude, country.longitude, mkTitle, mkIcon, mkShape);
    		// 加载标记响应事件
			if(marker!=null)this.addDetail(marker, opinion);
		}
	}
	// 由正反比计算索引值
	this.calIndex = function(opinion){
		if(opinion.nrNegtive==0)
			return 10;
		else{
			return Math.round(10*opinion.nrPositive / (opinion.nrPositive + opinion.nrNegtive));
		}
	}
	
	// 添加一个标记详细内容
	this.addDetail = function(marker, opinion) {
		// 标记的info内容
		var contentString = '<div class="mapContent">'+
        	'<h2 class="firstHeading">' + opinion.holderCountry.name_zh + '</h2>' +//'</div>';
        	'<div class="bodyContent">' +
        	'<ul>';
        
        // 添加各个数据，数据大于0才显示
        if(opinion.nrPositive && opinion.nrPositive>0){
        	contentString += '<li onclick="production(\'opinion!detail\', \'opinion\', \'true\', \'country_zh\', \'' + opinion.holderCountry.name_zh + '\', \'docSen\', \'' + opinion.positiveList + '\')"><span>正面报道: </span><a>' + opinion.nrPositive + '</a></li>';
        }
        if(opinion.nrNegtive && opinion.nrNegtive>0){
        	contentString += '<li onclick="production(\'opinion!detail\', \'opinion\', \'false\', \'country_zh\', \'' + opinion.holderCountry.name_zh + '\', \'docSen\', \'' + opinion.negtiveList + '\')"><span>负面报道: </span><a>' + opinion.nrNegtive + '</a></li>';
        }
        
        contentString += '</ul>'+'</div>'+'</div>';
        
        var infowindow = new google.maps.InfoWindow({
        	content: contentString,
        	flat: true
    	});  
    	
    	this.setInfoWindow(infowindow, marker);
	}
	
}
extend(CountryImageGoogleMap, GoogleMap);

// 话题世界分布图
function TopicWorldGoogleMap(mapId, isSaved){
	// 先调用父类的构造方法
	TopicWorldGoogleMap.superclass.constructor.call(this, mapId, isSaved);
	// 定义自己的图标
	this.image = null;
	this.bigImage = null;
	this.shape = null;
	this.bigShape = null;

	// 加载武装冲突图标的方法
	this.initImages = function(){
		// 来两个自己的图标
		this.image = new google.maps.MarkerImage('images/orange_button.png', new google.maps.Size(50, 50), new google.maps.Point(0,0), new google.maps.Point(15, 15), new google.maps.Size(20, 25));
		this.bigImage = new google.maps.MarkerImage('images/orange_button.png', new google.maps.Size(50, 50), new google.maps.Point(0,0), new google.maps.Point(25, 25), new google.maps.Size(50, 50));
		this.shape = {
				coord: [1, 1, 1, 30, 28, 30, 28 , 1],
				type: 'poly'
			};
		this.bigShape = {
				coord: [1, 1, 1, 50, 48, 50, 48 , 1],
				type: 'poly'
			};
	}
	
	// 加载标记
	this.loadMarkers = function(){
		// 话题数组
		var slTopicList = this.array;
		
		// 遍历话题数组，添加地图标记
		for(var i = 0; i < slTopicList.length; i++){
			var slTopic = slTopicList[i];
			
			// 创建标记
			var mkTitle = slTopic.title;
			var mkIcon = (slTopic.nrDoc>5)? this.bigImage :this.image;
			var mkShape = (slTopic.nrDoc>5)? this.bigShape : this.shape;
			var marker = this.addNewMarker(slTopic.latitude, slTopic.longitude, mkTitle, mkIcon, mkShape);;
    		// 加载标记响应事件
			if(marker!=null)this.addDetail(marker, slTopic);
		}
	}
	
	// 添加一个标记详细内容
	this.addDetail = function(marker, slTopic) {
		// 标记的info内容
		var contentString = '<div class="mapContent">'+
        	'<h2 class="firstHeading">' + slTopic.title + '</h2>' +//'</div>';
        	'<div class="bodyContent">' +
        	'<ul>';
        
        // 添加各个数据，数据大于0才显示
        if(slTopic.nrDoc && slTopic.nrDoc>0){
        	contentString += '<li><span>相关新闻数: </span><a>' + slTopic.nrDoc + '</a></li>';
        }
        
        contentString += '</ul>'+'</div>'+'</div>';
        
        var infowindow = new google.maps.InfoWindow({
        	content: contentString,
        	flat: true
    	});  
    	
    	this.setInfoWindow(infowindow, marker);
	}
	
}
extend(TopicWorldGoogleMap, GoogleMap);


/**
 * added by lutm at 20130904
 * 创建事件图片
 */
function createEventImage(typeId, importance, name) {
    if(isNaN(importance)) {
        importance = 0;
    }
    // 放大因子
    // var ampFactor = (importance * 10 / 100) / 10.0;
    var ampFactor = 0;
//    if(importance <= 30) {
//        ampFactor = 0;
//    } else if(importance <= 60) {
//        ampFactor = 0.5;
//    } else {
//        ampFactor = 1;
//    }
    if(name == "test_icon") {
        var width = parseInt(40 * (1+ampFactor));
        var height = parseInt(40 * (1+ampFactor));
        return new google.maps.MarkerImage('images/test_icon.png', new google.maps.Size(width, height), new google.maps.Point(0,0), new google.maps.Point(20, 40), new google.maps.Size(width, height));
    }
    var width = parseInt(15 * (1+ampFactor));
    var height = parseInt(19 * (1+ampFactor));
    return new google.maps.MarkerImage('images/'+typeId+'.png', new google.maps.Size(width, height), new google.maps.Point(0,0), new google.maps.Point(6, 19), new google.maps.Size(width, height));
}


// 热点事件的GoogleMap分布图，
// 现在的实现方法是加载event列表和topic列表。两者分开加载
function HotEventGoogleMap(mapId, isSaved, zoomSize, latitude, longitude){
	// 先调用父类的构造方法
	HotEventGoogleMap.superclass.constructor.call(this, mapId, isSaved, zoomSize, latitude, longitude);
	// 原来父类的变量json和array都用来保存event对象信息。
	// topicJson和topicArray用来保存topic信息
	this.topicJson = null;
	this.topicArray = null;
	
	// 图标看有多少种事件，就有多少种图标，于是将this.images定义为一个集合
	this.images = {};
	// 阴影图片
	this.shadow = {};
	
	// 事件槽类型的字符串对应
	this.slotTypeStr = {
		"conferrenceName" : "会议名称",
		"parti_Person" : "参与者",
		"disasterType" : "灾害类型",
		"speechContent" : "讲话主题",
		"treatyName" : "条约协议名称"
	}
	
	// 自动点击信息窗的索引数组
	this.autoIndexArray = [];
	this.curIndex = 0;
	
	// 自动点击信息窗的loopId
	this.loopId = 0;
	this.timeoutId = 0;
	// 自动点击信息窗的标志位，用于开关控制
	this.flag_autoTrigger = true;
	
	// 事件类别过滤器
	this.typeFilter = {};
	
	// 首先初始化图标，重写父类方法
	this.initImages = function(){
		// 各类事件
		this.images['1001'] = new google.maps.MarkerImage('images/1001.png', new google.maps.Size(15, 19), new google.maps.Point(0,0), new google.maps.Point(6, 19), new google.maps.Size(15, 19));
		this.images['big1001'] = new google.maps.MarkerImage('images/1001.png', new google.maps.Size(20, 25), new google.maps.Point(0,0), new google.maps.Point(15, 15), new google.maps.Size(20, 25));
		this.images['1002'] = new google.maps.MarkerImage('images/1002.png', new google.maps.Size(15, 19), new google.maps.Point(0,0), new google.maps.Point(6, 19), new google.maps.Size(15, 19));
		this.images['big1002'] = new google.maps.MarkerImage('images/1002.png', new google.maps.Size(20, 25), new google.maps.Point(0,0), new google.maps.Point(15, 15), new google.maps.Size(20, 25));
		this.images['1003'] = new google.maps.MarkerImage('images/1003.png', new google.maps.Size(15, 19), new google.maps.Point(0,0), new google.maps.Point(6, 19), new google.maps.Size(15, 19));
		this.images['big1003'] = new google.maps.MarkerImage('images/1003.png', new google.maps.Size(20, 25), new google.maps.Point(0,0), new google.maps.Point(15, 15), new google.maps.Size(20, 25));
		this.images['1004'] = new google.maps.MarkerImage('images/1004.png', new google.maps.Size(15, 19), new google.maps.Point(0,0), new google.maps.Point(6, 19), new google.maps.Size(15, 19));
		this.images['big1004'] = new google.maps.MarkerImage('images/1004.png', new google.maps.Size(20, 25), new google.maps.Point(0,0), new google.maps.Point(15, 15), new google.maps.Size(20, 25));
		this.images['1005'] = new google.maps.MarkerImage('images/1005.png', new google.maps.Size(15, 19), new google.maps.Point(0,0), new google.maps.Point(6, 19), new google.maps.Size(15, 19));
		this.images['big1005'] = new google.maps.MarkerImage('images/1005.png', new google.maps.Size(20, 25), new google.maps.Point(0,0), new google.maps.Point(15, 15), new google.maps.Size(20, 25));
		this.images['2001'] = new google.maps.MarkerImage('images/2001.png', new google.maps.Size(15, 19), new google.maps.Point(0,0), new google.maps.Point(6, 19), new google.maps.Size(15, 19));
		this.images['big2001'] = new google.maps.MarkerImage('images/2001.png', new google.maps.Size(20, 25), new google.maps.Point(0,0), new google.maps.Point(15, 15), new google.maps.Size(20, 25));
		this.images['1008'] = new google.maps.MarkerImage('images/1008.png', new google.maps.Size(15, 19), new google.maps.Point(0,0), new google.maps.Point(6, 19), new google.maps.Size(15, 19));
		this.images['big1008'] = new google.maps.MarkerImage('images/1008.png', new google.maps.Size(20, 25), new google.maps.Point(0,0), new google.maps.Point(15, 15), new google.maps.Size(20, 25));
		this.images['2002'] = new google.maps.MarkerImage('images/2002.png', new google.maps.Size(15, 19), new google.maps.Point(0,0), new google.maps.Point(6, 19), new google.maps.Size(15, 19));
		this.images['big2002'] = new google.maps.MarkerImage('images/2002.png', new google.maps.Size(20, 25), new google.maps.Point(0,0), new google.maps.Point(15, 15), new google.maps.Size(20, 25));
		this.images['2003'] = new google.maps.MarkerImage('images/2003.png', new google.maps.Size(15, 19), new google.maps.Point(0,0), new google.maps.Point(6, 19), new google.maps.Size(15, 19));
		this.images['big2003'] = new google.maps.MarkerImage('images/2003.png', new google.maps.Size(20, 25), new google.maps.Point(0,0), new google.maps.Point(15, 15), new google.maps.Size(20, 25));
		this.images['1009'] = new google.maps.MarkerImage('images/1009.png', new google.maps.Size(15, 19), new google.maps.Point(0,0), new google.maps.Point(6, 19), new google.maps.Size(15, 19));
		this.images['big1009'] = new google.maps.MarkerImage('images/1009.png', new google.maps.Size(20, 25), new google.maps.Point(0,0), new google.maps.Point(15, 15), new google.maps.Size(20, 25));
		this.images['1010'] = new google.maps.MarkerImage('images/1010.png', new google.maps.Size(15, 19), new google.maps.Point(0,0), new google.maps.Point(6, 19), new google.maps.Size(15, 19));
		this.images['big1010'] = new google.maps.MarkerImage('images/1010.png', new google.maps.Size(20, 25), new google.maps.Point(0,0), new google.maps.Point(15, 15), new google.maps.Size(20, 25));
		this.images['1006'] = new google.maps.MarkerImage('images/1006.png', new google.maps.Size(15, 19), new google.maps.Point(0,0), new google.maps.Point(6, 19), new google.maps.Size(15, 19));
		this.images['big1006'] = new google.maps.MarkerImage('images/1006.png', new google.maps.Size(20, 25), new google.maps.Point(0,0), new google.maps.Point(15, 15), new google.maps.Size(20, 25));
		this.images['1007'] = new google.maps.MarkerImage('images/1007.png', new google.maps.Size(15, 19), new google.maps.Point(0,0), new google.maps.Point(6, 19), new google.maps.Size(15, 19));
		this.images['big1007'] = new google.maps.MarkerImage('images/1007.png', new google.maps.Size(20, 25), new google.maps.Point(0,0), new google.maps.Point(15, 15), new google.maps.Size(20, 25));
		this.images['1011'] = new google.maps.MarkerImage('images/1011.png', new google.maps.Size(15, 19), new google.maps.Point(0,0), new google.maps.Point(6, 19), new google.maps.Size(15, 19));
		this.images['big1011'] = new google.maps.MarkerImage('images/1011.png', new google.maps.Size(20, 25), new google.maps.Point(0,0), new google.maps.Point(15, 15), new google.maps.Size(20, 25));
		this.images['1012'] = new google.maps.MarkerImage('images/1012.png', new google.maps.Size(15, 19), new google.maps.Point(0,0), new google.maps.Point(6, 19), new google.maps.Size(15, 19));
		this.images['big1012'] = new google.maps.MarkerImage('images/1012.png', new google.maps.Size(20, 25), new google.maps.Point(0,0), new google.maps.Point(15, 15), new google.maps.Size(20, 25));
		this.images['1013'] = new google.maps.MarkerImage('images/1013.png', new google.maps.Size(15, 19), new google.maps.Point(0,0), new google.maps.Point(6, 19), new google.maps.Size(15, 19));
		this.images['big1013'] = new google.maps.MarkerImage('images/1013.png', new google.maps.Size(20, 25), new google.maps.Point(0,0), new google.maps.Point(15, 15), new google.maps.Size(20, 25));	
		this.images['1014'] = new google.maps.MarkerImage('images/1014.png', new google.maps.Size(15, 19), new google.maps.Point(0,0), new google.maps.Point(6, 19), new google.maps.Size(15, 19));
		this.images['big1014'] = new google.maps.MarkerImage('images/1014.png', new google.maps.Size(20, 25), new google.maps.Point(0,0), new google.maps.Point(15, 15), new google.maps.Size(20, 25));	
		
		// 阴影
		this.shadow = new google.maps.MarkerImage('images/marker_bg.png', new google.maps.Size(15, 9), new google.maps.Point(0,0), new google.maps.Point(0, 9), new google.maps.Size(15, 9));
	}
	
	/**
	 * 初始化地图右侧筛选项
	 * @param {} ulElem 初始化各个li元素的点击事件
	 */
	this.initTypeFilter = function(ulElem){
		var typeFilterStr = $.cookie("typeFilter");
		if(typeFilterStr && typeFilterStr !== ""){
			this.typeFilter = $.parseJSON(typeFilterStr);
		}
		
		// 为每个已经选中的类别加上阴影
		for(var key in this.typeFilter){
			$("#" + key, ulElem).addClass('active');
		}
		
		this.refreshEventInTypes();
		
		this.attachEventToTypeTip(ulElem);
	}
	
	// 刷新事件聚类框
	this.refreshEventInTypes = function(){
		if($.isEmptyObject(this.typeFilter)){
			$(".eInType").removeClass('none');
		}
		else {
			$(".eInType").addClass('none');
			for(var key in this.typeFilter){
				$("#J_" + key).removeClass('none');
			}
		}
	}
	
	// 加载标记，分加载event标记和加载topic标记
	this.loadMarkers = function(){
		this.loadEventMarkers();
	}
	
	// 加载event标记
	this.loadEventMarkers = function(){
		// 将数组中的每一个点加上一个坐标
		var eventArrays = this.array;
		
		if(!eventArrays)return;
		for(var i = 0; i < eventArrays.length; i++){
			if(eventArrays[i].typeId<1000)continue;
			// 注意类别过滤
			if(!$.isEmptyObject(this.typeFilter) && !this.typeFilter[eventArrays[i].typeId])continue;
			// 地图标记中的各种数据
			var mkTitle = eventArrays[i].name;
			// var mkIcon = (eventArrays[i].nInst>2)? this.images['big'+eventArrays[i].typeId] :this.images[eventArrays[i].typeId];
			var mkIcon = createEventImage(eventArrays[i].typeId, eventArrays[i].importance, eventArrays[i].name);
			// 地图上加载一个新标记
			var marker = this.addNewMarker(eventArrays[i].latitude, eventArrays[i].longitude, mkTitle, mkIcon, null, eventArrays[i].nInst);
    		// 加载标记点击响应事件
			// 这里根据事件类型不同，区别对待
			if(marker!=null){
				var infowindow;
			
				/*if(eventArrays[i].typeId==1){
					infowindow = this.getInfoOfAcEvent(eventArrays[i]);
				}
				else if(eventArrays[i].typeId==2){
					infowindow = this.getInfoOfMtEvent(eventArrays[i]);
				}
				else if(eventArrays[i].typeId>2){
					infowindow = this.getInfoOfOtherEvent(eventArrays[i]);
				}*/
				 if(eventArrays[i].typeId>1000){
					infowindow = this.getInfoOfOtherEvent(eventArrays[i]);
				}
				
				this.setInfoWindow(infowindow, marker);
			}
		}
	}
	
	// 武装冲突的标记点击信息窗
	this.getInfoOfAcEvent = function(eventObj){
		var contentString = '<div class="mapContent">'+
        	'<h2 class="firstHeading" onclick="return production(\'event!detail\',\'id\',' + eventObj.id + ')">' + eventObj.name + ", " + eventObj.locationName + '</h2>' +//'</div>';
        	'<div class="bodyContent">' +
        	'<ul>';
        
        // 添加各个数据，数据大于0才显示
        if(eventObj.slot && eventObj.slot['101']!="--"){
        	contentString += '<li><span>死亡情况: </span><a>' + eventObj.slot['101'] + '</a></li>';
        }
        if(eventObj.time){
        	contentString += '<li><span>报道时间: </span><a>' + eventObj.time + '</a></li>';
        }
        if(eventObj.nInst && eventObj.nInst>1){
        	contentString += '<li><span>报道次数: </span><a>' + eventObj.nInst + '</a></li>';
        }
        
        contentString += '</ul>'+'</div>'+'</div>';
        
        var infowindow = new google.maps.InfoWindow({
        	content: contentString,
        	flat: true
    	});  
        
        return infowindow;
	}
	
	// 会见时间的标记点击信息窗
	this.getInfoOfMtEvent = function(eventObj){
		var contentString = '<div class="mapContent">'+
        	'<h2 class="firstHeading" onclick="return production(\'event!detail\',\'id\',' + eventObj.id + ')">' + eventObj.name + ", " + eventObj.locationName + '</h2>' +//'</div>';
        	'<div class="bodyContent">' +
        	'<ul>';
        
        // 添加各个数据，数据大于0才显示
        if(eventObj.slot && eventObj.slot['201']!="--"){
        	contentString += '<li><span>主方: </span><a>' + eventObj.slot['201'] + '</a></li>';
        }
        if(eventObj.slot && eventObj.slot['203']!="--"){
        	contentString += '<li><span>宾方: </span><a>' + eventObj.slot['203'] + '</a></li>';
        }
        if(eventObj.time){
        	contentString += '<li><span>报道时间: </span><a>' + eventObj.time + '</a></li>';
        }
        if(eventObj.nInst && eventObj.nInst>1){
        	contentString += '<li><span>报道次数: </span><a>' + eventObj.nInst + '</a></li>';
        }
        
        contentString += '</ul>'+'</div>'+'</div>';
        
        var infowindow = new google.maps.InfoWindow({
        	content: contentString,
        	flat: true
    	});  
        
        return infowindow;
	};
	
	// 清除&nbsp;的正则表达式
	this.reg_nbsp = /&nbsp;/ig;
	
	// 其他事件的信息窗
	this.getInfoOfOtherEvent = function(eventObj){
		var contentString = '<div class="mapContent">'+
        	'<h2 class="firstHeading" onclick="return production(\'event!detail\',\'id\',' + eventObj.id + ')">' + eventObj.name.replace(this.reg_nbsp, ' ')+ '</h2>' +//'</div>';
        	'<div class="bodyContent">' +
        	'<ul>';
        
        // 添加各个数据，数据大于0才显示
        if(eventObj.time && eventObj.time != 'null'){
        	contentString += '<li><span>报道时间: </span><a>' + eventObj.time + '</a></li>';
        }
        if(eventObj.locationName && eventObj.locationName != 'null'){
        	contentString += '<li><span>发生地点: </span><a>' + eventObj.locationName + '</a></li>';
        }
        if(eventObj.nInst && eventObj.nInst>1){
            contentString += '<li><span>报道次数: </span><a>' + eventObj.nInst + '</a></li>';
        }
//        // 获取槽内的信息
//        var slotList = eventObj.slot;
//        for(var key in slotList){
//        	if(this.slotTypeStr[key]){
//	        	contentString += '<li><span>' + this.slotTypeStr[key] + ': </span><a>' + slotList[key] + '</a></li>';
//	        }
//        }
//        if(eventObj.nInst && eventObj.nInst>0){
//        	contentString += '<li><span>报道次数: </span><a>' + eventObj.nInst + '</a></li>';
//        }

        contentString += '</ul>'+'</div>'+'</div>';
        
        var infowindow = new google.maps.InfoWindow({
        	content: contentString,
        	flat: true
    	});  
        
        return infowindow;
	}
	
	// 自动触发标记Marker的点击操作
	this.autoTriggerInfoWindow = function(){
		var m_size;
		var r_index;
		// 一定要加这行，因为setInterval会是this变成window;
		var self = this;
		if(this.markersArray && this.markersArray.length>0){
			// 取随机数
			m_size = this.markersArray.length;

			this.loopId = setInterval(function(){
				// r_index = self.getNextMarkerIndex();
                // 改成按顺序
				r_index = self.getNextMarkerIndexInOrder();
				google.maps.event.trigger(self.markersArray[r_index], 'click');
			}, 3000)
		}
	}
	
	// 监听地图的鼠标单击事件，如果触发了点击事件，则停止Marker的自动点击
	this.loadMouseClickHandler = function(){
		var self = this;
		$('#'+this.id).mousedown(function(){
			// 鼠标按下，停止跳转
			clearInterval(self.loopId);
			
			// 如果，跳转开关是开的状态，则10秒以后再来
			if(self.flag_autoTrigger){
				clearTimeout(self.timeoutId);
				self.timeoutId = setTimeout(function(){
					self.autoTriggerInfoWindow();
				}, 10000);
			}
		});
	}
	
	/**
	 * 获取下一个自动触发的Marker的index
	 * 策略：1、将markersArray的index索引值随机打乱一下，存储在autoIndexArray数组中
	 * 		 2、逐一从autoIndexArray数组中读取index值，如果index值大于等于数组的size，则回头重读。
	 * 这样保证每个marker都能点到，避免完全随机时偶尔出现的来回点的情况。
	 * @return {}
	 */
	this.getNextMarkerIndex = function(){
		var index = 0, i = 0, shuff_num, ai, bi;
		var m_size = this.markersArray.length
		if(this.autoIndexArray.length == 0){
			// 初始化
			while(index < m_size){
				this.autoIndexArray.push(index++);	
			}
			// shuff the array
			shuff_num = Math.floor(m_size / 2);
			while(i++ < shuff_num){
				ai = Math.floor(Math.random() * m_size);
				bi = Math.floor(Math.random() * m_size);
				swap(this.autoIndexArray, ai, bi);
			}
		}
		// 取下一个索引值
		if(this.curIndex >= m_size)this.curIndex = 0;
		return this.autoIndexArray[this.curIndex++];
		
		// 工具函数
		function swap(array, ai, bi){
			var temp = array[ai];
			array[ai] = array[bi];
			array[bi] = temp;
		}
	}

    this.currentMarkerIndex = -1;
	this.getNextMarkerIndexInOrder = function(){
        this.currentMarkerIndex++;
        if(this.currentMarkerIndex == this.markersArray.length) {
            this.currentMarkerIndex = 0;
        }
        return this.currentMarkerIndex;
	}

	/**
	 * 重新触发自动跳转操作
	 */
	this.reAutoTrigger = function(){
		var self = this;
		// 不论开关，先都把之前的循环给去掉
		clearTimeout(self.timeoutId);
		clearInterval(self.loopId);
		if(self.flag_autoTrigger){
			this.autoIndexArray = [];
			self.autoTriggerInfoWindow();
		}
	}
	
	/**
	 * 为某个checkbox元素添加开关事件
	 * @param {} elem
	 */
	this.attachEventToSwitch = function(elem){
		var self = this;
		
		$(elem).change(function(){
			var selfElem = this;
			// 不论开关，先都把之前的循环给去掉
			clearTimeout(self.timeoutId);
			clearInterval(self.loopId);
			// 如果是开，则自动点击
			if(selfElem.checked){	
				self.autoTriggerInfoWindow();
				self.flag_autoTrigger = true;
			}
			else {
				self.flag_autoTrigger = false;	
			}
		});
	}
	
	/**
	 * 为地图右侧列表添加事件响应函数
	 * @param {} elem
	 */
	this.attachEventToTypeTip = function(elem){
		var map_self = this;
		
		$("li", elem).click(function(){
			var self = this;
			if($(self).hasClass('active')){	// 取消类别
				$(self).removeClass('active');
				delete map_self.typeFilter[self.id];
			}
			else {	// 添加类别
				$(self).addClass('active');
				map_self.typeFilter[self.id] = true;
			}
			// 看是不是为空了
			if($.isEmptyObject(this.typeFilter)){
				$(".eInType").removeClass('none');
			}
			map_self.refreshEventInTypes();
			map_self.refresh();
			map_self.reAutoTrigger();
			$.cookie('typeFilter', JSON.stringify(map_self.typeFilter), {expires: 7});
		});
	}
}
extend(HotEventGoogleMap, GoogleMap);