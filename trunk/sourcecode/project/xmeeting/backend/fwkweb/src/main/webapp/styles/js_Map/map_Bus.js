/**
 * var mapbus = new Mapbus(); mapbus.init();
 * mapbus.buildMap(31.32226058755177,120.70816040039062,30, 110,35,
 * 125,8,16,14,"containercccc"); //addControler参数说明 1：表示添加平移控件 2：表示添加比例尺控件
 * mapbus.addControler(1); mapbus.addControler(2);
 * mapbus.drawBusInstance(127,31.26715088, 120.7396317,1,0);
 * mapbus.drawBusInstance(127,31.26951599, 120.7443314,1,0);
 * mapbus.drawBusInstance(148,31.27708244, 120.7403488,1,0); //path表示要画线的数组
 * mapbus.drawLine(path); mapbus.drawStation1(path); //drawCar参数说明 ： 1表示bus的起始站点
 * 2表示终了站点 path 表示bus的路线 最后一个url表示bus图标的Url
 * mapbus.drawCar(1,4,path,"http://www.sipbus.com:8080/syWeb/styles/js_Map/imgs/bus.jpg");
 * mapbus.drawCar(5,7,path,"http://www.sipbus.com:8080/syWeb/styles/js_Map/imgs/bus.jpg");
 * 
 */

function Mapbus() {
	var map = null;// gismap
	var google_layer = null;
	var soso_layer = null;
	var busTable;// hashtable
	var upStationTable;// 上行Bus的Hashtable
	var downStationTable;// 下行Bus的HashTable
	var mapUrl = null;
	var carUrl_00 = null;
	var carUrl_01 = null;
	var carUrl_10 = null;
	var carUrl_11 = null;
	var urlhead_00 = null;
	var urlhead_01 = null;
	var urlhead_10 = null;
	var urlhead_11 = null;
	var imgbus_East = null;
	var imgbus_EastSouth = null;
	var imgbus_north = null;
	var imgbus_northEast = null;
	var imgbus_Northwest = null;
	var imgbus_south = null;
	var imgbus_southEast = null;
	var imgbus_West = null;
	var isController = false;
	var southWestlng = null;
	var southWestlat = null;
	var northwestlng = null;
	var northwestlat = null;
	var minzoom = null;
	var maxzoom = null;
	var zoomlevel = null;
	var index = null;
	var a = null;
	var b = null;
	/*
	 * var marker1 = null; var icon = null; var anchor = null; var size = null;
	 */
}

// 在调用其他方法前必须调用此init方法 这个方法内主要是 写定了一些地址
Mapbus.prototype.init = function(isController) {
	// mapURl是地图图源的地址 tilemaps 是存放图源的文件夹 之后三个参数是依据不同
	// 的地图API会有不同的选图方法
	this.mapUrl = "http://map.sipbus.com/mapserver/tilemaps/{z}/{y}/{x}";
	// carUrl 是初始bus的地址 00表示上行报警 01表示上行未报警 10表示下行报警 11表示下行未报警
	this.carUrl_00 = "/syWeb/styles/js_Map/imgs/bus_East.jpg";
	this.carUrl_01 = "/syWeb/styles/js_Map/imgs/bus_East.jpg";
	this.carUrl_10 = "/syWeb/styles/js_Map/imgs/bus_East.jpg";
	this.carUrl_11 = "/syWeb/styles/js_Map/imgs/bus_East.jpg";
	// 这四个urlhead分别表示存放各个情况的图库 两位数字的含义与carUrl相同
	this.urlhead_00 = "/syWeb/styles/js_Map/imgs/";
	this.urlhead_01 = "/syWeb/styles/js_Map/imgs/";
	this.urlhead_10 = "/syWeb/styles/js_Map/imgs/";
	this.urlhead_11 = "/syWeb/styles/js_Map/imgs/";
	// 以下八个参数就是头朝各个方向的图片的名字
	this.imgbus_East = "bus_East.jpg";
	this.imgbus_EastSouth = "bus_EastSouth.jpg";
	this.imgbus_north = "bus_north.jpg";
	this.imgbus_northEast = "bus_northEast.jpg";
	this.imgbus_Northwest = "bus_Northwest.jpg";
	this.imgbus_south = "bus_south.jpg";
	this.imgbus_southEast = "bus_southEast.jpg";
	this.imgbus_West = "bus_West.jpg";
	this.isController = isController;
	this.southWestlng = 30;
	this.southWestlat = 110;
	this.northwestlng = 35;
	this.northwestlat = 125;
	this.minzoom = 8;
	this.maxzoom = 16;
	this.zoomlevel = 14;
	// this.index = 0;
	/*
	 * this.anchor = new soso.maps.Point(6, 6); this.size = new
	 * soso.maps.Size(24, 24); this.icon = new
	 * soso.maps.MarkerImage(this.carUrl_00, this.size, this.anchor);
	 * this.marker1 = new soso.maps.Marker({ icon : this.icon, map : this.map,
	 * position : carpath[0] });
	 */

}
// 创建地图
Mapbus.prototype.buildMap = function(centerlng, centerLat, containerId) {

	busTable = new Hashtable();
	upStationTable = new Hashtable();
	downStationTable = new Hashtable();
	var myLatlng = new soso.maps.LatLng(centerlng, centerLat);

	// 精度到象素
	var southWest = new soso.maps.LatLng(this.southWestlng, this.southWestlat);
	var northEast = new soso.maps.LatLng(this.northwestlng, this.northwestlat);
	var boundary = new soso.maps.LatLngBounds(southWest, northEast);

	var myOptions = {
		center : myLatlng,
		minZoom : this.minzoom,
		maxZoom : this.maxzoom,
		zoomLevel : this.zoomlevel,
		boundary : boundary
	};

	// 创建地图
	this.map = new soso.maps.Map(document.getElementById(containerId),
			myOptions);

	// 实现谷歌地图图层类
	var GoogleLayer = function(options) {
	};
	// 继承TileLayer类
	GoogleLayer.prototype = new soso.maps.TileLayer();
	// 自定义图块地址
	GoogleLayer.prototype = new soso.maps.TileLayer({
		tileUrlTemplate : this.mapUrl,
		tileSubdomains : '/'
	});

	this.google_layer = new GoogleLayer();
	this.google_layer.setMap(this.map);
	// 移除SOSO地图国层
	this.soso_layer = this.map.layers.removeAt(0);
	// setTimeout(function() {
	// this.map.moveTo(this.path[3]);
	// }, 2000);
	// alert(this.map.getCenter());
	if (this.isController) {
		this.addControler(1);
		this.addControler(2);
	}

}

// 专门用于添加控件的方法
Mapbus.prototype.addControler = function(i) {

	switch (i) {
	case 1:
		// 添加地图导航平移控件
		var navControl = new soso.maps.NavigationControl({
			align : soso.maps.ALIGN.TOP_LEFT,
			margin : new soso.maps.Size(5, 15),
			map : this.map
		});
		break;
	case 2:
		// 比例尺控件
		var scaleControl = new soso.maps.ScaleControl({
			align : soso.maps.ALIGN.BOTTOM_RIGHT,
			margin : new soso.maps.Size(30, 15),
			map : this.map
		});
		break;
	}

}

// 画线(经校准后的画线方法)
Mapbus.prototype.drawLine = function(linepath, color) {
	// linepath=this.path;
	// 校准地图的坐标 通过校准的数据库获得
	for ( var i = 0; i < linepath.length; i++) {
		linepath[i].lat = linepath[i].lat + -0.002225;
		linepath[i].lng = linepath[i].lng + 0.004157;
	}
	// 画线
	var polyline = new soso.maps.Polyline({
		path : linepath,
		strokeColor : color,
		strokeWeight : 5,
		strokeOpacity : 1,
		editable : false,
		map : this.map
	});

}

// 未经校准后的画线方法
Mapbus.prototype.drawLineWithoutCorrection = function(linepath, color) {
	// 画线
	var polyline = new soso.maps.Polyline({
		path : linepath,
		strokeColor : color,
		strokeWeight : 5,
		strokeOpacity : 1,
		editable : true,
		map : this.map
	});

}

// 通过Latlng来画站
Mapbus.prototype.drawStationByLatlng = function(Latlng) {
	Latlng.lat = Latlng.lat + -0.002225;
	Latlng.lng = Latlng.lng + 0.004157;
	var marker = new soso.maps.Marker({
		position : Latlng,
		map : this.map
	});
	(function(marker) {
		soso.maps.Event.addListener(marker, 'click', function() {
			alert(111);
		});
	})(marker);

}
// 通过PATH画站（站点经纠偏）
Mapbus.prototype.drawStationWithCorrection = function(stationPath) {
	// stationPath=this.path;
	for ( var i = 0; i < stationPath.length; i++) {
		stationPath[i].lat = stationPath[i].lat + -0.002225;
		stationPath[i].lng = stationPath[i].lng + 0.004157;
	}

	for ( var i = 0; i < stationPath.length; i++) {
		var marker = new soso.maps.Marker({
			position : stationPath[i],
			map : this.map
		});

		var info = new soso.maps.InfoWindow({
			map : this.map
		});
		var decor = new soso.maps.MarkerDecoration({
			content : i + 1,
			margin : new soso.maps.Size(0, -4),
			align : soso.maps.ALIGN.CENTER,
			marker : marker
		});

		(function(marker) {
			soso.maps.Event
					.addListener(
							marker,
							'click',
							function() {

								/* */

								info
										.open(
												'<div class="tabs" currentIndex="2" eventType="click">'
														+ '<div class="tabsHeader">'
														+ '<div class="tabsHeaderContent">'
														+ '<ul>'
														+ '<li><a href="javascript:;"><span>标题1</span></a></li>'
														+ '<li><a href="javascript:;"><span>标题2</span></a></li>'
														+ '<li><a href="javascript:;" class="j-ajax"><span>标题3</span></a></li>'
														+ '</ul>'
														+ '</div>'
														+ '</div>'
														+ '<div class="tabsContent" style="height:150px;">'
														+ '<div>content1</div>'
														+ '<div>内容2</div>'
														+ '<div>content3</div>'
														+ '</div>' + '</div>',
												marker);
							});
		})(marker);
	}

}

//通过PATH画站（站点未经纠偏）
Mapbus.prototype.drawStationWithoutCorrection = function(stationPath) {
	// stationPath=this.path;

	for ( var i = 0; i < stationPath.length; i++) {
		var marker = new soso.maps.Marker({
			position : stationPath[i],
			map : this.map
		});

		var info = new soso.maps.InfoWindow({
			map : this.map
		});
		var decor = new soso.maps.MarkerDecoration({
			content : i + 1,
			margin : new soso.maps.Size(0, -4),
			align : soso.maps.ALIGN.CENTER,
			marker : marker
		});

		(function(marker) {
			soso.maps.Event
					.addListener(
							marker,
							'click',
							function() {

								/* */

								info
										.open(
												'<div class="tabs" currentIndex="2" eventType="click">'
														+ '<div class="tabsHeader">'
														+ '<div class="tabsHeaderContent">'
														+ '<ul>'
														+ '<li><a href="javascript:;"><span>标题1</span></a></li>'
														+ '<li><a href="javascript:;"><span>标题2</span></a></li>'
														+ '<li><a href="javascript:;" class="j-ajax"><span>标题3</span></a></li>'
														+ '</ul>'
														+ '</div>'
														+ '</div>'
														+ '<div class="tabsContent" style="height:150px;">'
														+ '<div>content1</div>'
														+ '<div>内容2</div>'
														+ '<div>content3</div>'
														+ '</div>' + '</div>',
												marker);
							});
		})(marker);
	}

}


// 画站（站点经纠偏同时具有callback方法）
Mapbus.prototype.drawStationWithCorrection_callback = function(stationPath,
		callback) {
	// stationPath=this.path;
	for ( var i = 0; i < stationPath.length; i++) {
		stationPath[i].lat = stationPath[i].lat + -0.002225;
		stationPath[i].lng = stationPath[i].lng + 0.004157;
	}

	for ( var i = 0; i < stationPath.length; i++) {
		var marker = new soso.maps.Marker({
			position : stationPath[i],
			map : this.map
		});

		var info = new soso.maps.InfoWindow({
			map : this.map
		});
		var decor = new soso.maps.MarkerDecoration({
			content : i + 1,
			margin : new soso.maps.Size(0, -4),
			align : soso.maps.ALIGN.CENTER,
			marker : marker
		});

		(function(marker, callback) {
			soso.maps.Event.addListener(marker, 'click', callback);
		})(marker, callback);
	}

}

// 画站（站点未经纠偏）
Mapbus.prototype.drawStation = function(stationPath, callback) {
	this
			.setCenter(new soso.maps.LatLng(stationPath[0].lat,
					stationPath[0].lng));
	for ( var i = 0; i < stationPath.length; i++) {
		var position = new soso.maps.LatLng(stationPath[i].lat,
				stationPath[i].lng);
		var marker = new soso.maps.Marker({
			position : position,
			map : this.map
		});

		var info = new soso.maps.InfoWindow({
			map : this.map
		});
		var decor = new soso.maps.MarkerDecoration({
			content : i + 1,
			margin : new soso.maps.Size(0, -4),
			align : soso.maps.ALIGN.CENTER,
			marker : marker
		});

		/*
		 * (function(marker,callback,sguid) { soso.maps.Event .addListener(
		 * marker, 'click', callback); })(marker,callback,stationPath[i].sguid);
		 */

		var sguid = stationPath[i].sguid;
		soso.maps.Event.addListener(marker, 'click', function() {
			callback(sguid);
		});

	}// end of for
}
// 画上行站点，同时存入hashtable中
Mapbus.prototype.drawUpStation = function(linename, stationPath, callback) {
	this
			.setCenter(new soso.maps.LatLng(stationPath[0].lat,
					stationPath[0].lng));
	for ( var i = 0; i < stationPath.length; i++) {
		var position = new soso.maps.LatLng(stationPath[i].lat,
				stationPath[i].lng);
		var marker = new soso.maps.Marker({
			position : position,
			map : this.map
		});
		upStationTable.put(linename, marker);
		var info = new soso.maps.InfoWindow({
			map : this.map
		});
		var decor = new soso.maps.MarkerDecoration({
			content : i + 1,
			margin : new soso.maps.Size(0, -4),
			align : soso.maps.ALIGN.CENTER,
			marker : marker
		});

		/*
		 * (function(marker,callback,sguid) { soso.maps.Event .addListener(
		 * marker, 'click', callback); })(marker,callback,stationPath[i].sguid);
		 */

		var sguid = stationPath[i].sguid;
		soso.maps.Event.addListener(marker, 'click', function() {
			callback(sguid);
		});

	}// end of for
}

//画下行站点，同时存入hashtable中
Mapbus.prototype.drawDownStation = function(linename, stationPath, callback) {
	this
			.setCenter(new soso.maps.LatLng(stationPath[0].lat,
					stationPath[0].lng));
	for ( var i = 0; i < stationPath.length; i++) {
		var position = new soso.maps.LatLng(stationPath[i].lat,
				stationPath[i].lng);
		var marker = new soso.maps.Marker({
			position : position,
			map : this.map
		});
		downStationTable.put(linename, marker);
		var info = new soso.maps.InfoWindow({
			map : this.map
		});
		var decor = new soso.maps.MarkerDecoration({
			content : i + 1,
			margin : new soso.maps.Size(0, -4),
			align : soso.maps.ALIGN.CENTER,
			marker : marker
		});

		/*
		 * (function(marker,callback,sguid) { soso.maps.Event .addListener(
		 * marker, 'click', callback); })(marker,callback,stationPath[i].sguid);
		 */

		var sguid = stationPath[i].sguid;
		soso.maps.Event.addListener(marker, 'click', function() {
			callback(sguid);
		});

	}// end of for
}

//画上下行线路
Mapbus.prototype.drawAllLineStation = function(linename, uppath, downpath,
		callback) {
	this.drawUpStation(linename, uppath, callback);
	this.drawDownStation(linename, downpath, callback);
}

//删除一条线路的上下行站点
Mapbus.prototype.removeAllLineStation = function(linename){
	var upMarker = new Array();
	var downMarker = new Array();
	upMarker = upStationTable.get(linename);
	downMarker = downStationTable.get(linename);
	for (i in upMarker) {
		upMarker[i].setMap(null);
	}
	for(j in downMarker){
		downMarker[j].setMap(null);
	}
}
/*
 * //画站（站点未经纠偏） Mapbus.prototype.drawStation = function(stationPath,callback) {
 * for ( var i = 0; i < stationPath.length; i++) { var marker = new
 * soso.maps.Marker({ position : stationPath[i], map : this.map });
 * 
 * var info = new soso.maps.InfoWindow({ map : this.map }); var decor = new
 * soso.maps.MarkerDecoration({ content : i + 1, margin : new soso.maps.Size(0,
 * -4), align : soso.maps.ALIGN.CENTER, marker : marker });
 * 
 * (function(marker) { soso.maps.Event .addListener( marker, 'click',
 * function(){ alert(111); }); })(marker); } }
 */

// 画一个无监听，无callback的Bus
Mapbus.prototype.createBusWithoutCallback = function(lng, lat) {
	this.setCenter(new soso.maps.LatLng(lat,lng));
	var carUrl = this.urlhead_00 + this.imgbus_West;
	var anchor = new soso.maps.Point(6, 6), size = new soso.maps.Size(24, 24), icon = new soso.maps.MarkerImage(
			carUrl, size, anchor);
	var location = new soso.maps.LatLng(lat, lng);
	var marker = new soso.maps.Marker({
		icon : icon,
		map : this.map,
		position : location
	});
}
// 用于创建车辆 callback是一个方法名 是在你点击车辆后调用的方法
Mapbus.prototype.createBus = function(busNo1, lng1, lat1, carUrl1, callback) {
	this.setCenter(new soso.maps.LatLng(lat1,lng1));
	var carUrl = carUrl1;
	var anchor = new soso.maps.Point(6, 6), size = new soso.maps.Size(24, 24), icon = new soso.maps.MarkerImage(
			carUrl, size, anchor);
	var location = new soso.maps.LatLng(lat1, lng1);
	var marker = new soso.maps.Marker({
		icon : icon,
		map : this.map,
		position : location
	});
	// 这段代码用于为车加上监听。同时点击后调用传入的方法
	/*(function(marker, callback) {
		soso.maps.Event.addListener(marker, 'click', callback);
	})(marker, callback);*/
	soso.maps.Event.addListener(marker, 'click', function() {
		callback(busNo1);
	});
	busTable.put(busNo1, marker);
}

// 用于根据车辆的x 与 y坐标的正负 来判断车辆的方向 之后画车
Mapbus.prototype.drawSingleBus = function(busNo2, lng2, lat2, UrlHead) {
	// 下面的几个url是头向各个方向的图片
	var start_point = this.map.fromLatLngToContainerPixel(busTable.get(busNo2)
			.getPosition()); 
	var location = new soso.maps.LatLng(lat2,lng2 );
	var end_point = this.map.fromLatLngToContainerPixel(location);
	var distance_y = end_point.getY() - start_point.getY();
	var distance_x = end_point.getX() - start_point.getX(); 
	if(distance_x==0&&distance_y==0){
		return;
	}
	
	var imgbus_East = this.imgbus_East;
	var imgbus_EastSouth = this.imgbus_EastSouth;
	var imgbus_north = this.imgbus_north;
	var imgbus_northEast = this.imgbus_northEast;
	var imgbus_Northwest = this.imgbus_Northwest;
	var imgbus_south = this.imgbus_south;
	var imgbus_southEast = this.imgbus_southEast;
	var imgbus_West = this.imgbus_West;
	var bus_EastUrl = UrlHead + imgbus_East;
	var bus_EastSouthUrl = UrlHead + imgbus_EastSouth;
	var bus_northUrl = UrlHead + imgbus_north;
	var bus_northEastUrl = UrlHead + imgbus_northEast;
	var bus_NorthwestUrl = UrlHead + imgbus_Northwest;
	var bus_southUrl = UrlHead + imgbus_south;
	var bus_southEastUrl = UrlHead + imgbus_southEast;
	var bus_WestUrl = UrlHead + imgbus_West;
	var marker = busTable.get(busNo2);

	// 这里通过判断distance_x和distance_y的正负来选取图片
 
	if (distance_x == 0 && distance_y > 0) {
		marker.getIcon().setUrl_(bus_southUrl);
	}
	if (distance_x == 0 && distance_y < 0) {
		marker.getIcon().setUrl_(bus_northUrl);
	}
	if (distance_x > 0 && distance_y == 0) {
		marker.getIcon().setUrl_(bus_EastUrl);
	}
	if (distance_x < 0 && distance_y == 0) {
		marker.getIcon().setUrl_(bus_WestUrl);
	}
	if (distance_x > 0 && distance_y < 0) {
		marker.getIcon().setUrl_(bus_northEastUrl);
	}
	if (distance_x < 0 && distance_y < 0) {
		marker.getIcon().setUrl_(bus_NorthwestUrl);
	}
	if (distance_x < 0 && distance_y > 0) {
		marker.getIcon().setUrl_(bus_southEastUrl);
	}
	if (distance_x > 0 && distance_y > 0) {
		marker.getIcon().setUrl_(bus_EastSouthUrl);
	}
	
 
	
	
	
	// var
	// tanValue=(end_point.getY()-start_point.getY())/(end_point.getX()-start_point.getX());
	// tanValue 算出來的只是弧度值，并不是角度值 弧度和角度的換算需要乘以57.2957
	// var angle=Math.atan(tanValue)*57.2957;
	// alert(angle);
	// marker.getIcon().setUrl_(carUrl1); 可以用來設置icon的Url
	logger.info("bus position is: "+location);
	marker.setPosition(location);
	busTable.put(busNo2, marker); 
}

// 创建车的实例 alert 0表示报警 1表示未报警 upOrdown 0表示上行 1表示下行
Mapbus.prototype.drawBusInstance = function(busNo, lng, lat, alert, upOrdown,
		callback) {
	// lng,lat Car的初始位置 busNO表示车的number
	if (busTable.get(busNo) == null) {
		if (upOrdown == 0 && alert == 0) {
			var carUrl = this.carUrl_00;
			this.createBus(busNo, lng, lat, carUrl, callback);
		}
		if (upOrdown == 0 && alert == 1) {
			var carUrl = this.carUrl_01;
			this.createBus(busNo, lng, lat, carUrl, callback);

		}
		if (upOrdown == 1 && alert == 0) {
			var carUrl = this.carUrl_10;
			this.createBus(busNo, lng, lat, carUrl, callback);
		}
		if (upOrdown == 1 && alert == 1) {
			var carUrl = this.carUrl_11;
			this.createBus(busNo, lng, lat, carUrl, callback);
		}
		// alert(busTable.get(busNo).getPosition());
		// alert(marker.getIcon().getUrl_()); 這個方法可以獲得icon的Url
	} else {
		if (upOrdown == 0 && alert == 0) {
			var urlhead_00 = this.urlhead_00;
			this.drawSingleBus(busNo, lng, lat, urlhead_00);
		}
		if (upOrdown == 0 && alert == 1) {
			var urlhead_01 = this.urlhead_01;
			this.drawSingleBus(busNo, lng, lat, urlhead_01);
		}
		if (upOrdown == 1 && alert == 0) {
			var urlhead_10 = this.urlhead_10;
			this.drawSingleBus(busNo, lng, lat, urlhead_10);
		}
		if (upOrdown == 1 && alert == 1) {
			var urlhead_11 = this.urlhead_11;
			this.drawSingleBus(busNo, lng, lat, urlhead_11);
		}
	}

	// $("img[src='http://www.sipbus.com:8080/syWeb/styles/js_Map/imgs/bus.jpg']").rotateRight(90);
}

// 画车 主要用于轨迹回放时用到的画车
Mapbus.prototype.drawCar = function(k, l, carpath, carUrl) {
	var latlngs = new Array();
	for ( var i = k - 1; i < l - 1; i++) {
		var distance = this.map.getDistance(carpath[i], carpath[i + 1]);
		var speed = 20; // 每秒走10米
		var n = distance / speed;
		var start_point = this.map.fromLatLngToContainerPixel(carpath[i]);
		var end_point = this.map.fromLatLngToContainerPixel(carpath[i + 1]);
		var dis_X = end_point.getX() - start_point.getX();
		var dis_Y = end_point.getY() - start_point.getY();

		for ( var j = 0; j < n; j++) {
			var x = start_point.getX() + Math.floor(dis_X / n) * j;
			var y = start_point.getY() + Math.floor(dis_Y / n) * j;
			var point = new soso.maps.Point(x, y);
			var latlng = this.map.fromContainerPixelToLatLng(point);
			latlngs.push(latlng);
		}
	}
	// alert(this.latlngs.length);
	var info = new soso.maps.InfoWindow({
		map : this.map
	});
	var anchor = new soso.maps.Point(6, 6), size = new soso.maps.Size(24, 24), icon = new soso.maps.MarkerImage(
			carUrl, size, anchor);
	var marker = new soso.maps.Marker({
		icon : icon,
		map : this.map,
		position : carpath[0]
	});
	soso.maps.Event.addListener(marker, 'click', function() {
		info.open('<div>information</div>', marker);
	});
	var index = 0;
	setInterval(function() {
		if (index > latlngs.length - 1) {
			index = 0;
		}
		marker.setPosition(latlngs[index]);
		index++;
		// map.moveTo(latlngs[index]);

	}, 300);

}

// 设置地图中心
Mapbus.prototype.setCenter = function(latLng) {
	map = this.map;
	map.setCenter(latLng);
}

// 移动地图中心(按照轨迹进行移动 传入的参数是一个数组) 参数k为center改变开始点
Mapbus.prototype.changeCenterByPath = function(k, lpath, second) {
	var i = k;
	map = this.map;
	// path=this.path;
	this.b = setInterval(function() {

		map.moveTo(lpath[i]);
		i++;
	}, second*190);
	// map.getViewSize().setWidth()("1025px");
	// alert(map.getViewSize().getWidth());
}

// 移动地图中心(按照经纬度进行移动)
Mapbus.prototype.changeCenterByLatlng = function(Latlng, second) {
	map = this.map;
	map.moveTo(Latlng);
}

// 回放轨迹 k表示轨迹回放开始站点 l表示轨迹回放结束站点 second表示间距时间
Mapbus.prototype.trackPlayback = function(k, l, carpath,second) {
	var carUrl = "/syWeb/styles/js_Map/imgs/bus.jpg";
	var latlngs = new Array();
	for ( var i = k - 1; i < l - 1; i++) {
		var distance = this.map.getDistance(carpath[i], carpath[i + 1]);
		//var speed = 200; //每秒走10米
		//var n = distance/speed;
		var n = second;
		var start_point = this.map.fromLatLngToContainerPixel(carpath[i]);
		var end_point = this.map.fromLatLngToContainerPixel(carpath[i + 1]);
		var dis_X = end_point.getX() - start_point.getX();
		var dis_Y = end_point.getY() - start_point.getY();

		for ( var j = 0; j < n; j++) {
			var x = start_point.getX() + Math.floor(dis_X / n) * j;
			var y = start_point.getY() + Math.floor(dis_Y / n) * j;
			var point = new soso.maps.Point(x, y);
			var latlng = this.map.fromContainerPixelToLatLng(point);
			latlngs.push(latlng);
		}
	}

	var anchor = new soso.maps.Point(6, 6), size = new soso.maps.Size(24, 24), icon = new soso.maps.MarkerImage(
			carUrl, size, anchor);
	var marker = new soso.maps.Marker({
		icon : icon,
		map : this.map,
		position : carpath[0]
	});

	var index = 0;
	this.a = setInterval(function() {
		if (index > latlngs.length - 1) {
			marker.setMap(null);
		}
		marker.setPosition(latlngs[index]);
		this.map.setCenter(latlngs[index]);
		index++;

	}, second);
	// marker.setMap(null);
	// clearInterval(a1);
}
Mapbus.prototype.clearInterval = function() {
	// alert(this.a);
	clearInterval(this.b);
}
