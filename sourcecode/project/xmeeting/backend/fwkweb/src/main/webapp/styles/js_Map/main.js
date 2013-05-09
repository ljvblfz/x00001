__MapNSImpl__ = {};
__MapNS__ = {};
__MapNSImpl__.Config = {
	DEFAULT_LNG: 116.39712896958922,//天安门坐标
	DEFAULT_LAT: 39.9165275426627,
	DEFAULT_ZOOMLEVEL: 9,
	DEFAULT_TILE_SIZE: 256,
	EARTH_RADIUS: 6378137, //赤道半径为6378137米
	zoomMin: 4,
	zoomMax: 18,
	//估计是分辨率
	resolution: [156543.03, 78271.516, 39135.758, 19567.879, 9783.939, 4891.9697, 2445.9849, 1222.9924, 611.4962, 305.7481, 152.87405, 76.43703, 38.218513, 19.109257, 9.554628, 4.777314, 2.388657, 1.1943285, 0.597164283477939],
	//默认各层级的四个顶点图片编号
	scope: [0, 0, 0, 0, 0, 3, 0, 3, 0, 3, 0, 3, 0, 7, 0, 7, 0, 15, 0, 15, 0, 31, 0, 31, 0, 63, 4, 59, 0, 127, 12, 115, 0, 225, 28, 227, 356, 455, 150, 259, 720, 899, 320, 469, 1440, 1799, 650, 929, 2880, 3589, 1200, 2069, 5760, 7179, 2550, 3709, 11520, 14349, 5100, 7999, 23060, 28689, 10710, 15429, 46120, 57369, 20290, 29849, 89990, 124729, 41430, 60689, 184228, 229827, 84169, 128886],
	tileDomains: ["http://p0.map.soso.com/maptiles/", "http://p1.map.soso.com/maptiles/", "http://p2.map.soso.com/maptiles/", "http://p3.map.soso.com/maptiles/"],
	//tileDomains: ["maptiles/", "maptiles/"],
	QueryType: {
		POI: "poi",	
		SYN: "syn",
		RN: "rn",
		BUSLS: "busls",
		BUS: "bus",
		DT: "dt",
		DTS: "dts",
		GEOC: "geoc",
		RGEOC: "rgeoc",
		GC: "gc",
		NAV: "nav",
		WALK: "walk",
		POS: "pos",
		SG: "sg"
	},
	//SERVICE_URL: "http://api.map.qq.com/"
	SERVICE_URL: "js/"
}; (function() {
	var d = null;
	try {
		throw Error("Get Script Url");
	} catch(r) {
		d = r.fileName || r.sourceURL;
		if (!d && r.stack) d = !window.opera ? (r.stack.match(/at\s+(.*):\d+/) || [null, null])[1] : (r.stack.match(/@(.*):\d+/) || [null, null])[1];
		if (!d) {
			var o = document.getElementsByTagName("script"),
			j = [],
			p,
			b;
			p = 0;
			for (b = o.length; p < b; p++) {
				var g = o[p].src;
				if (g.indexOf("Config.js") != -1 || g.indexOf("main.js") != -1) j.push(g)
			}
			if (j.length === 1) d = j[0];
			else {
				p = 0;
				for (b = j.length; p < b; p++) if (j[p].indexOf("map.soso.com") != -1) {
					d = j[p];
					break
				}
			}
		}
	}
	o = "";
	if (d) {
		o = d.substring(0, d.lastIndexOf("/"));
		if (o.indexOf("common") !== -1) o = d.substring(0, o.lastIndexOf("/"));
		if (/MSIE (\d+(\.\d+)?)/.test(navigator.userAgent)) if (parseFloat(RegExp.$1) === 6 && o.indexOf("localhost") > 0) o = "http://" + document.domain + "/" + o
	}
	__MapNSImpl__.Config.domain = o + "/"
})(); (function() {
	__MapNSImpl__.Interfaces = {
		registInterface: function(d, r) {
			for (var o = {},
			j = 0, p = r.length; j < p; j++) o[r[j]] = null;
			__MapNSImpl__.Interfaces[d] = o
		}
	};
	__MapNSImpl__.Class = function() {
		for (var d = null, r = {},
		o, j = 0, p = arguments.length; j < p; ++j) {
			o = arguments[j];
			if (typeof o == "function") o = o.prototype;
			else {
				o = o;
				o.initialize && (d = o.initialize, delete o.initialize)
			}
			__MapNSImpl__.Synchronize.copy(r, o)
		}
		d.prototype = r;
		return d
	};
	__MapNSImpl__.Synchronize = {
		fill: function(d, r) {
			if (d) {
				for (var o in r) if (o !== "prototype" && r.hasOwnProperty(o) && d[o] === undefined) d[o] = r[o];
				return d
			} else return r
		},
		copy: function(d, r) {
			if (!r || !d) throw Error("You must provide an object to copy from and to");
			for (var o in r) d[o] = r[o];
			if (r.hasOwnProperty && r.hasOwnProperty("toString")) d.toString = r.toString
		}
	}
})(); (function(d) {
	function r(a, f, m, v) {
		if (f < m) {
			var s;
			s = v(a[m]);
			for (var u = f - 1, y = f; y < m; y++) if (v(a[y]) <= s) {
				u++;
				var t = a[y];
				a[y] = a[u];
				a[u] = t
			}
			t = a[u + 1];
			a[u + 1] = a[m];
			a[m] = t;
			s = u + 1;
			r(a, f, s - 1, v);
			r(a, s + 1, m, v)
		}
	}
	function o(a) {
		a = a || window.event;
		d.Event.stopEvent(a);
		return false
	} ! window.console && (window.console = {},
	console.log = function() {});
	var j = {};
	__MapNSImpl__.Util = j;
	j.Browser = function() {
		var a = navigator.userAgent,
		f = 0,
		m = 0,
		v = 0,
		s = 0,
		u = 0,
		y = 0,
		t = 0;
		if (/opera\/(\d+\.\d)/i.test(a)) m = +RegExp.$1;
		else if (/msie (\d+\.\d)/i.test(a)) f = document.documentMode || +RegExp.$1;
		else if (/firefox\/(\d+\.\d)/i.test(a)) s = +RegExp.$1;
		else if (/netscape(\s|\/)(\d+(\.\d+)?)/i.test(a)) y = +RegExp.$1;
		else if (/(\d+\.\d)?(?:\.\d)?\s+safari\/?(\d+\.\d+)?/i.test(a) && !/chrome/i.test(a) && !/android/i.test(a)) v = +(RegExp.$1 || RegExp.$2);
		else if (/chrome\/(\d+\.\d)/i.test(a)) u = +RegExp.$1;
		else if (/android\s(\d+\.\d)/i.test(a)) t = +RegExp.$1;
		return {
			ie: f,
			firefox: s,
			netscape: y,
			opera: m,
			safari: v,
			chrome: u,
			android: t,
			gecko: /gecko/i.test(a) && !/like gecko/i.test(a),
			webkit: /webkit/i.test(a)
		}
	};
	j.sortByGetter = function(a, f) {
		return r(a, 0, a.length - 1, f)
	};
	var p = null;
	j.removeNode = function(a) {
		if (j.Browser().ie) {
			d.Event.clearListeners(a);
			p || (p = document.createElement("div"));
			p.appendChild(a);
			p.innerHTML = ""
		} else a && a.parentNode && a.parentNode.removeChild(a)
	};
	var b = [],
	g = [];
	j.fetchImage = function(a, f) {
		var m,
		v,
		s = false;
		m = 0;
		for (v = g.length; m < v; m++) if (g[m].url === a) {
			g[m].callbacks.push(f);
			s = true
		}
		s || g.push({
			url: a,
			callbacks: [f]
		});
		var u = new Image;
		m = 0;
		for (v = b.length; m < v; ++m) if (b[m].url === a) {
			f(b[m].width, b[m].height, a, b[m].image);
			return b[m].image
		}
		var y = function(t, n) {
			for (var q = 0; q < g.length; q++) if (g[q].url === n) {
				for (var w = g[q].callbacks, x = 0; x < w.length; x++) w[x](t.width, t.height, n, t);
				g.splice(q, 1);
				break
			}
		};
		m = d.Event;
		m.addDomListenerOnce(u, "load",
		function() {
			b.push({
				url: a,
				width: u.width,
				height: u.height,
				image: u
			});
			y(u, a);
			u = null
		});
		m.addDomListenerOnce(u, "error",
		function() {
			b.push({
				url: a,
				width: 0,
				height: 0,
				image: u
			});
			f(0, 0, a);
			y(u, a);
			u = null
		});
		a.indexOf("?");
		u.src = a;
		return u
	};
	j.setSize = function(a, f) {
		if (f.getWidth) {
			a.style.width = f.getWidth() + "px";
			a.style.height = f.getHeight() + "px"
		} else {
			a.style.width = f[0] + "px";
			a.style.height = f[1] + "px"
		}
	};
	j.setPosition = function(a, f) {
		if (f.getX) {
			a.style.left = Math.floor(f.getX()) + "px";
			a.style.top = Math.floor(f.getY()) + "px"
		} else {
			a.style.left = Math.floor(f[0]) + "px";
			a.style.top = Math.floor(f[1]) + "px"
		}
	};
	j.setDomCoord = function(a, f) {
		var m = a.style; (f[0] || f[0] === 0) && (m.left = f[0] + "px"); (f[1] || f[1] === 0) && (m.top = f[1] + "px"); (f[2] || f[2] === 0) && (m.width = f[2] + "px"); (f[3] || f[3] === 0) && (m.height = f[3] + "px")
	};
	j.getChildNodesByTag = function(a, f) {
		var m = [];
		for (a = a.firstChild; a;) {
			a.tagName.toLowerCase() === f && m.push(a);
			a = a.nextSibling
		}
		return m
	};
	j.getCssSpriteImage = function(a, f, m, v, s) {
		var u = function(y, t, n, q) {
			y.width = n;
			y.height = q;
			if (j.Browser().ie && j.Browser().ie <= 6) {
				if (t.toUpperCase().indexOf(".PNG") > 0) y.style.filter = 'progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src="' + t + '"'
			} else y.src = t;
			y.style.display = ""
		};
		if (!m) {
			m = document.createElement("img");
			if (!v) {
				m.style.cssText = "-moz-user-select:none;-khtml-user-select:none;";
				m.unselectable = "on";
				v = d.Event;
				v.addDomListener(m, "selectstart", o);
				v.addDomListener(m, "dragstart", o)
			}
			m.style.position = "absolute";
			m.src = __MapNSImpl__.Config.domain + "imgs/transparent.gif"
		} (function(y, t, n) {
			y.className = "csssprite";
			y.style.display = "none";
			if (n) {
				var q = n.getWidth();
				n = n.getHeight();
				u(y, t, q, n)
			} else j.fetchImage(t,
			function(w, x) {
				u(y, t, w, x)
			})
		})(m, a, s);
		m.style.left = -f[0] + "px";
		m.style.top = -f[1] + "px";
		return m
	};
	j.setCssSprite = function(a, f, m, v, s) {
		if (j.Browser().ie && j.Browser().ie <= 6) {
			if (a.style.position != "relative" && a.style.position != "absolute") a.style.position = "relative";
			a.style.background = "";
			a.style.overflow = "hidden";
			for (var u = a.childNodes, y = null, t = 0, n = u.length; t < n; ++t) if (u[t].className == "csssprite") {
				y = u[t];
				break
			}
			y = j.getCssSpriteImage(f, m, y, v, s);
			if (!y.parentNode) {
				a.innerHTML = "";
				a.appendChild(y)
			}
		} else {
			f && (a.style.backgroundImage = "url(" + f + ")");
			a.style.backgroundPosition = -m[0] + "px " + -m[1] + "px"
		}
	};
	j.getMouseXY = function(a) {
		var f = [];
		if (j.Browser().ie) f = [a.clientX + (document.documentElement.scrollLeft || document.body.scrollLeft), a.clientY + (document.documentElement.scrollTop || document.body.scrollTop)];
		else if (a.touches) {
			f = null;
			if (a.targetTouches.length > 0) f = a.targetTouches[0];
			else if (a.changedTouches.length > 0) f = a.changedTouches[0];
			f = [f.pageX, f.pageY]
		} else f = [a.pageX, a.pageY];
		return f
	};
	j.getDomCoordinate = function(a) {
		for (var f = a.offsetWidth, m = a.offsetHeight, v = 0, s = 0; a;) {
			v += a.offsetLeft;
			s += a.offsetTop;
			a = a.offsetParent
		}
		return [v, s, f, m]
	};
	j.getStyle = function(a, f, m) {
		if (!a) return null;
		if (document.defaultView && m) {
			f = f.replace(/[A-Z]/g,
			function(s) {
				return "-" + s.toLowerCase()
			});
			try {
				return document.defaultView.getComputedStyle(a, null).getPropertyValue(f)
			} catch(v) {
				return null
			}
		}
		f = f.replace(/-(\D)/g,
		function(s, u) {
			return u.toUpperCase()
		});
		if (f == "float") f = j.Browser().ie ? "styleFloat": "cssFloat";
		if (a.currentStyle && m) return a.currentStyle[f];
		return a.style ? a.style[f] : undefined
	};
	j.getSize = function(a) {
		var f,
		m;
		f = j.getStyle(a, "width");
		m = j.getStyle(a, "height");
		if (f.indexOf("px") === -1) f = 0;
		if (m.indexOf("px") === -1) m = 0;
		return {
			width: parseInt(f) || a.offsetWidth || a.clientWidth,
			height: parseInt(m) || a.offsetHeight || a.clientHeight
		}
	};
	j.toCamelCase = function() {
		var a = {};
		return function(f) {
			if (a[f]) return a[f];
			else {
				f = f.replace(/([A-Z]+)/g,
				function(m, v) {
					return v.substr(0, 1).toUpperCase() + v.toLowerCase().substr(1, v.length)
				}).replace(/[\-_\s](.)/g,
				function(m, v) {
					return v.toUpperCase()
				});
				return a[f] = f
			}
		}
	} ();
	j.setStyle = function(a, f, m) {
		var v;
		a = typeof a === "string" ? document.getElementById(a) : a;
		if (typeof f === "string") if (m) a.style.cssText = f;
		else {
			m = f.split(";");
			for (v = 0; v < m.length; v++) {
				var s = j.toCamelCase(m[v]);
				a.style[s] = f[u]
			}
		} else {
			if (m) a.style.cssText = "";
			for (var u in f) if (f.hasOwnProperty(u)) if (m) v += u + ":" + f[u] + ";";
			else {
				s = j.toCamelCase(u);
				a.style[s] = f[u]
			}
			if (m) a.style.cssText = v
		}
		return a
	};
	j.getMouseCoordinate = function(a) {
		var f = [];
		if (j.Browser().ie) f = [a.clientX + (document.documentElement.scrollLeft || document.body.scrollLeft), a.clientY + (document.documentElement.scrollTop || document.body.scrollTop)];
		else if (a.touches) {
			f = null;
			if (a.targetTouches.length > 0) f = a.targetTouches[0];
			else if (a.changedTouches.length > 0) f = a.changedTouches[0];
			f = [f.pageX, f.pageY]
		} else f = [a.pageX, a.pageY];
		return f
	};
	j.topLeft = function(a) {
		a = a instanceof String ? document.getElementById(a) : a;
		if (!a) throw "You have not provided a valid element to topLeft()";
		return {
			x: a.style.left ? parseInt(a.style.left, 10) : 0,
			y: a.style.top ? parseInt(a.style.top, 10) : 0
		}
	};
	j.centerChangedFixDisplayBBox = function(a, f) {
		if (f !== undefined && f !== null) {
			if (a.lng < f[0]) a.lng = f[0];
			else if (a.lng > f[2]) a.lng = f[2];
			if (a.lat < f[1]) a.lat = f[1];
			else if (a.lat > f[3]) a.lat = f[3]
		}
	};
	j.isCenterInDisplayBBox = function(a, f, m) {
		var v = false;
		if (m !== undefined && m !== null) {
			if (a > m[0] && f > m[1] && a < m[2] && f < m[3]) v = true
		} else v = true;
		return v
	};
	j.setPositionByAlign = function(a, f, m, v) {
		if (a) {
			var s = a.style,
			u = m.getWidth();
			m = m.getHeight();
			var y = f % 3;
			f = Math.floor(f / 3);
			var t = Math.floor(a.offsetWidth / 2);
			a = Math.floor(a.offsetHeight / 2);
			if (y === 1) {
				s.left = v ? Math.floor(v.offsetWidth / 2) + "px": "50%";
				s.marginLeft = u - t + "px"
			}
			if (f === 1) {
				s.top = v ? Math.floor(v.offsetHeight / 2) + "px": "50%";
				s.marginTop = m - a + "px"
			}
			if (y === 2) {
				s.right = u + "px";
				s.left = ""
			}
			if (y === 0) {
				s.left = u + "px";
				s.right = ""
			}
			if (f === 0) {
				s.top = m + "px";
				s.bottom = ""
			}
			if (f === 2) {
				s.bottom = m + "px";
				s.top = ""
			}
		}
	};
	var k = {
		TOP_LEFT: 0,
		TOP: 1,
		TOP_RIGHT: 2,
		LEFT: 3,
		CENTER: 4,
		RIGHT: 5,
		BOTTOM_LEFT: 6,
		BOTTOM: 7,
		BOTTOM_RIGHT: 8
	};
	k.isTop = function(a) {
		return a < 3
	};
	k.isMiddle = function(a) {
		return a > 2 && a < 6
	};
	k.isBottom = function(a) {
		return a > 5
	};
	k.isLeft = function(a) {
		return a % 3 == 0
	};
	k.isCenter = function(a) {
		return a % 3 == 1
	};
	k.isRight = function(a) {
		return a % 3 == 2
	};
	j.forEach = function(a, f, m) {
		if (a) if (a.length) {
			m = 0;
			for (var v = a.length; m < v; ++m) f(m, a[m])
		} else for (v in a) if (m || !a.hasOwnProperty || a.hasOwnProperty(v)) f(v, a[v])
	};
	j.callback = function(a, f, m) {
		m = m || [];
		return function() {
			for (var v = m.concat(), s = 0; s < arguments.length; s++) v.push(arguments[s]);
			f && f.apply(a, v)
		}
	};
	j.copyObject = function(a, f, m) {
		j.forEach(a,
		function(v) {
			f[v] = a[v]
		},
		m)
	};
	var l = {};
	j.capitalInitial = function(a) {
		return l[a] || (l[a] = a.substr(0, 1).toUpperCase() + a.substr(1))
	};
	var i,
	c = (new Date).getTime();
	__MapNSImpl__.debug = {
		write: function() {
			for (var a = [], f = 0, m = arguments.length; f < m; f++) if (arguments[f] && arguments[f].length) a = a.concat(arguments[f]);
			else a.push(arguments[f]);
			a.join(", ");
			f = document.createElement("div");
			f.style.cssText = "border-bottom:1px dashed #e0ecff;line-height:12px;padding:2px 0;height:12px;";
			f.innerHTML = '<xmp style="margin:0 80px 0 0;float:left;">' + a + "</xmp>";
			a = document.createElement("div");
			a.style.cssText = "float:right;";
			i = (new Date).getTime();
			a.innerHTML = i - c + " ms";
			c = i;
			f.appendChild(a);
			var v = document.getElementById("__debug"),
			s = document.getElementById("__debug_content");
			if (!v) {
				v = document.createElement("div");
				v.id = "__debug";
				v.style.cssText = "position:fixed;*position:absolute;bottom:0;left:2%;width:96%;height:100px;overflow:hidden;z-index:100000;background:#99c661;font-size:11px;";
				document.body.appendChild(v);
				a = document.createElement("div");
				a.style.cssText = "height:16px;line-height:16px;background:#88b752;position:relative;";
				a.innerHTML = '<span style="margin-left:5px;color:white;font-weight:bold;">Logs</span>';
				m = document.createElement("span");
				m.style.cssText = "color:white;cursor:pointer;position:absolute;right:45px;text-decoration:underline;";
				m.innerHTML = "Clear";
				var u = d.Event;
				u.addDomListener(m, "click",
				function() {
					s.innerHTML = ""
				});
				var y = document.createElement("span");
				y.style.cssText = "color:white;cursor:pointer;position:absolute;right:45px;text-decoration:underline;";
				y.style.right = "10px";
				y.innerHTML = "Hide";
				u.addDomListener(y, "click",
				function() {
					if (y.innerHTML == "Hide") {
						y.innerHTML = "Show";
						v.style.height = "16px"
					} else {
						y.innerHTML = "Hide";
						v.style.height = "100px"
					}
				});
				a.appendChild(m);
				a.appendChild(y);
				s = document.createElement("div");
				s.id = "__debug_content";
				s.style.cssText = "height:80px;overflow:auto;background:#99c661;margin:2px 5px;color:#333333;";
				v.appendChild(a);
				v.appendChild(s)
			}
			v.style.display = "";
			s.appendChild(f);
			s.scrollTop = s.scrollHeight
		},
		clear: function() {
			document.getElementById("__debug_content").innerHTML = ""
		}
	};
	j.lngFrom4326ToProjection = function(a) {
		return a * 111319.49077777778
	};
	j.latFrom4326ToProjection = function(a) {
		a = Math.log(Math.tan((90 + a) * 0.008726646259971648)) / 0.017453292519943295;
		a *= 111319.49077777778;
		return a
	};
	j.lngFromProjectionTo4326 = function(a) {
		return a / 111319.49077777778
	};
	j.latFromProjectionTo4326 = function(a) {
		a = a / 111319.49077777778;
		return a = Math.atan(Math.exp(a * 0.017453292519943295)) * 114.59155902616465 - 90
	};
	j.wrapXY = function(a, f) {
		var m;
		if (f < 0) {
			m = f % a;
			f = m !== 0 ? a + m: 0
		} else f = f >= a ? f % a: f;
		return f
	};
	j.getOptimalZoomLevel = function(a, f, m, v) {
		var s = a.getSouthWest(),
		u = a.getNorthEast();
		a = j.lngFrom4326ToProjection(u.getLng()) - j.lngFrom4326ToProjection(s.getLng());
		s = j.latFrom4326ToProjection(u.getLat()) - j.latFrom4326ToProjection(s.getLat());
		for (u = __MapNSImpl__.Config.zoomMax; u >= 0; u--) {
			var y = v.getResolution(u);
			if (a / y.x <= f + 1 && s / y.y <= m + 1) break
		}
		u = Math.max(u, __MapNSImpl__.Config.zoomMin);
		return u = Math.min(u, __MapNSImpl__.Config.zoomMax)
	};
	
	
	//获取瓦片索引位置（坐标，层级）
	j.getSeedTileIndex = function(a, f) {
		var m;
		m = a.getLng();
		var v = a.getLat(),
		s = Math.pow(2, f + 7),
		u = s * 2;
		v = Math.min(Math.max(Math.sin(v * (Math.PI / 180)), -0.9999), 0.9999);
		m = [s + m * (u / 360), s - 0.5 * Math.log((1 + v) / (1 - v)) * (u / (2 * Math.PI))];
		return [m[0], m[1]]
	};
	var e = [],
	h = [];
	j.startDrag = function(a, f, m, v) {
		var s = j.getMouseCoordinate(v);
		e.push([f, m, s, a]);
		v.mousePageX = s[0];
		v.mousePageY = s[1];
		m.dragstart && m.dragstart(v, a)
	};
	j.stopDrag = function(a, f, m) {
		for (var v = 0, s; s = e[v]; v++) if (s[0] === a && s[3] === f) {
			m && s[1].dragend(m);
			e.splice(v, 1);
			v--
		}
	};
	j.enableDrag = function(a, f, m, v) {
		var s,
		u = d.Event,
		y = function(z) {
			if (f.setCapture) {
				f.setCapture();
				s = u.addDomListener(f, "losecapture", x)
			}
			if (z.button !== 2) if (z.touches && z.touches.length > 1) {
				z = 0;
				for (var D; D = e[z]; z++) if (D[0] == f) {
					e.splice(z, 1);
					z--
				}
			} else {
				D = j.getMouseCoordinate(z);
				u.stopEvent(z);
				e.push([f, m, D, a]);
				z.mousePageX = D[0];
				z.mousePageY = D[1];
				m.dragstart && m.dragstart(z)
			}
		};
		a.length || (a = [a]);
		for (var t = 0; t < a.length; t++) {
			u.addDomListener(a[t], "mousedown", y);
			u.addDomListener(a[t], "selectstart",
			function(z) {
				u.stopEvent(z)
			});
			u.addDomListener(a[t], "touchstart", y)
		}
		y = false;
		t = 0;
		for (var n = h.length; t < n; t++) if (h[t] == f) y = true;
		if (!y) {
			h.push(f);
			var q = null,
			w = function(z, D) {
				z = z || window.event;
				var B = j.getMouseCoordinate(z);
				z.mousePageX = B[0];
				z.mousePageY = B[1];
				for (var A = 0, C; C = e[A]; A++) if (C[0] == f) {
					var E = [B[0] - C[2][0], B[1] - C[2][1]];
					if (D) {
						C[1].dragging && C[1].dragging(z, E, true);
						C[1].dragend && C[1].dragend(z, E);
						e.splice(A, 1);
						A--
					} else C[1].dragging && C[1].dragging(z, E)
				}
				q = null
			};
			v = v || 0;
			y = function(z) {
				if (! (q && v != 0)) {
					w(z);
					v != 0 && (q = window.setTimeout(function() {
						q = null
					},
					v))
				}
			};
			var x = function(z) {
				if (f.releaseCapture) {
					f.releaseCapture();
					u.removeListener(s)
				}
				w(z, true)
			};
			u.addDomListener(f, "mousemove", y);
			u.addDomListener(f, "mouseup", x);
			u.addDomListener(f, "touchmove", y);
			u.addDomListener(f, "touchend", x)
		}
	};
	j.RPCRequest = function(a, f) {
		var m = document.createElement("script");
		m.type = "text/javascript";
		m.charset = "utf-8";
		if (j.Browser().ie && j.Browser().ie < 9) m.onreadystatechange = function() {
			if (m.readyState === "loaded" || m.readyState === "complete") f && f()
		};
		else m.onload = function() {
			f && f()
		};
		var v = document.getElementsByTagName("head")[0];
		if (!v) v = document.body;
		m.src = a;
		v.appendChild(m)
	};
	j.adapter = function(a, f) {
		return function(m) {
			f.call(a, m, this)
		}
	};
	j.setCursorStyle = function(a, f, m) {
		if (f.indexOf(",") > 0) {
			f = f.split(",");
			for (m = 0; m < f.length; m++) if (j.setCursorStyle(a, f[m])) return true;
			return false
		}
		try {
			m = m || "auto";
			if (f.toLowerCase().indexOf(".cur") > 0) f = "url(" + f + ")," + m;
			f = f.toLowerCase();
			if (f == "hand" && !j.Browser().ie) f = "pointer";
			a.style.cursor = f;
			return true
		} catch(v) {
			return false
		}
	};
	j.setUnSelectable = function(a) {
		if (j.Browser().ie || window.opera) {
			var f = d.Event;
			a.unselectable = "on";
			f.addDomListener(a, "selectstart", j.falseFunction);
			f.addDomListener(a, "dragstart", j.falseFunction)
		} else {
			a.style.MozUserSelect = "none";
			a.style.WebkitUserSelect = "none"
		}
	};
	j.falseFunction = function(a) {
		d.Event.stopEvent(a);
		return false
	};
	j.setOpacity = function(a, f) {
		if (a) {
			a.style.filter = "progid:DXImageTransform.Microsoft.Alpha(opacity=" + parseInt(f * 100) + ")";
			a.style.MozOpacity = f;
			a.style.opacity = f
		}
	};
	j.isHtmlControl = function(a) {
		var f = document.createElement("div");
		if (typeof a === "string" || typeof a === "number") return false;
		try {
			f.appendChild(a.cloneNode(true));
			return a.nodeType == 1 ? true: false
		} catch(m) {
			return a == window || a == document
		}
	};
	j.parseCoords = function(a) {
		a = a.split(",");
		for (var f = a.length, m = 2; m < f; m++) a[m] = (parseFloat(a[m - 2]) + parseFloat(a[m]) / 1E6).toFixed(6);
		a[0] = parseFloat(a[0], 10).toFixed(6);
		a[1] = parseFloat(a[1], 10).toFixed(6);
		f = [];
		for (m = 0; m < a.length; m += 2) f.push(new d.LatLng(a[m + 1], a[m]));
		return f
	};
	j.getNumberInRange = function(a, f, m) {
		if (f != null) a = Math.max(a, f);
		if (m != null) a = Math.min(a, m);
		return a
	};
	j.getLoopNumber = function(a, f, m) {
		if (a == Number.POSITIVE_INFINITY || a == Number.NEGATIVE_INFINITY) return f;
		if (a >= f && a <= m) return a;
		m -= f;
		return (a % m + m - f) % m + f
	};
	j.radianToDegree = function(a) {
		return a / (Math.PI / 180)
	};
	j.degreeToRadian = function(a) {
		return a * (Math.PI / 180)
	};
	j.isEquals = function(a, f) {
		return Math.abs(a -
		f) <= 1.0E-9
	};
	j.isSupportCanvas = function() {
		return !! document.createElement("canvas").getContext
	};
	j.isSupportSVG = function() {
		return !! (document.createElementNS && document.createElementNS("http://www.w3.org/2000/svg", "svg").createSVGRect)
	};
	j.domReady = function(a, f) {
		function m() {
			if (!u) {
				u = true;
				a()
			}
		}
		var v = {
			enableMozDOMReady: true
		};
		if (f) for (var s in f) v[s] = f[s];
		var u = false;
		if (j.Browser().ie) { (function() {
				if (!u) {
					try {
						document.documentElement.doScroll("left")
					} catch(y) {
						setTimeout(arguments.callee, 20);
						return
					}
					m()
				}
			})();
			window.attachEvent("onload", m)
		} else {
			if (j.Browser().webkit && j.Browser().webkit < 525)(function() {
				u || (/loaded|complete/.test(document.readyState) ? m() : setTimeout(arguments.callee, 0))
			})();
			else if (!j.Browser().firefox || j.Browser().firefox != 2 || v.enableMozDOMReady) document.addEventListener("DOMContentLoaded",
			function() {
				document.removeEventListener("DOMContentLoaded", arguments.callee, false);
				m()
			},
			false);
			window.addEventListener("load", m, false)
		}
	};
	j.isInDocument = function(a) {
		return a && a.parentNode && a.parentNode.nodeType != 11
	};
	d.ALIGN = k;
	window.log_ = __MapNSImpl__.debug.write
})(__MapNS__);
__MapNSImpl__.RC = {};
__MapNSImpl__.RC.NavControl = {
	zoomIn: "\u653e\u5927",
	zoomOut: "\u7f29\u5c0f",
	left: "\u5411\u5de6\u5e73\u79fb",
	right: "\u5411\u53f3\u5e73\u79fb",
	up: "\u5411\u4e0a\u5e73\u79fb",
	down: "\u5411\u4e0b\u5e73\u79fb",
	ruler: "\u5355\u51fb\u7f29\u653e",
	slide: "\u62d6\u52a8\u7f29\u653e"
};
__MapNSImpl__.RC.Control = {};
__MapNSImpl__.RC.Control.TencentText = "";
__MapNSImpl__.RC.Control.DataText = "";
__MapNSImpl__.RC.Control.GotoHome = "\u5230SOSO\u5730\u56fe\u9996\u9875";
__MapNSImpl__.RC.Control.ScaleText = ["1000\u516c\u91cc", "1000\u516c\u91cc", "1000\u516c\u91cc", "1000\u516c\u91cc", "500\u516c\u91cc", "200\u516c\u91cc", "200\u516c\u91cc", "100\u516c\u91cc", "50\u516c\u91cc", "20\u516c\u91cc", "10\u516c\u91cc", "5\u516c\u91cc", "2\u516c\u91cc", "1\u516c\u91cc", "500\u7c73", "200\u7c73", "200\u7c73", "100\u7c73", "50\u7c73"];
__MapNSImpl__.RC.PolyEditLabel = {
	start: "\u8d77\u70b9",
	end: "\u5171",
	add: "\u5355\u51fb\u9f20\u6807\u6dfb\u52a0\u65b0\u70b9",
	remove: "\u79fb\u9664\u8be5\u70b9",
	destroyPolyline: "\u79fb\u9664\u8be5\u6298\u7ebf",
	destroyPolygon: "\u79fb\u9664\u8be5\u591a\u8fb9\u5f62",
	drag: "\u62d6\u62fd\u79fb\u52a8\u8be5\u70b9",
	dragNew: "\u62d6\u62fd\u6dfb\u52a0\u4e00\u4e2a\u65b0\u70b9",
	drawStart: "\u5355\u51fb\u9009\u62e9\u8d77\u70b9",
	drawCurrent: "\u5f53\u524d",
	drawing: "\u5355\u51fb\u5de6\u952e\u7ee7\u7eed\uff0c\u53cc\u51fb\u6216\u53f3\u952e\u7ed3\u675f"
};
__MapNSImpl__.RC.Units = {
	m: "\u7c73",
	km: "\u516c\u91cc",
	msec: "\u6beb\u79d2",
	sec: "\u79d2",
	min: "\u5206\u949f",
	hour: "\u5c0f\u65f6"
};
__MapNSImpl__.RC.Direction = ["\u4e1c", "\u4e1c\u5317", "\u5317", "\u897f\u5317", "\u897f", "\u897f\u5357", "\u5357", "\u4e1c\u5357"];
__MapNSImpl__.RC.Transfer = ["\u4e58\u5750", "\u7ecf\u8fc7", "\u7ad9", "\u5230\u8fbe", "\u7ec8\u70b9"];
__MapNSImpl__.RC.MapType = {
	ROADMAP: "\u5730\u56fe",
	SATELLITE: "\u536b\u661f",
	HYBRID: "\u536b\u661f"
};
__MapNSImpl__.RC.LatLngOutOfRange = "\u8bf7\u6c42\u7684\u7ecf\u7eac\u5ea6\u4e0d\u5728\u670d\u52a1\u5668\u652f\u6301\u7684\u8303\u56f4\u5185\uff0c\u8bf7\u91cd\u8bd5\uff01"; (function(d) {
	function r(e, h) {
		var a;
		if (g.Browser().ie) a = o(e).__events_;
		else {
			e.__events_ || (e.__events_ = {});
			a = e.__events_
		}
		a[h] || (a[h] = {});
		return a[h]
	}
	function o(e) {
		var h = i.eventObjects[e.__oid_];
		if (!h) {
			e.__oid_ = l++;
			h = {
				__events_: {}
			};
			i.eventObjects[e.__oid_] = h
		}
		return h
	}
	function j(e, h) {
		var a,
		f = {};
		if (g.Browser().ie) {
			if (a = o(e)) f = a.__events_
		} else f = e.__events_ || {};
		if (h) a = f[h] || {};
		else {
			a = {};
			for (var m in f) g.copyObject(f[m], a)
		}
		return a
	}
	function p(e, h) {
		h = h || [];
		if (Object.prototype.toString.apply(h) !== "[object Array]") h = [h];
		if (e.target) return e.handler.apply(e.target, h);
		return null
	}
	function b(e) {
		e.bindHandler = function(h) {
			if ((h = h || window.event) && !h.target) try {
				h.target = h.srcElement
			} catch(a) {}
			var f = p(e, [h]);
			if (h && h.type === "click") if ((h = h.srcElement) && h.tagName && h.tagName === "A" && h.href === "javascript:void(0)") return false;
			return f
		};
		return e.bindHandler
	}
	var g = __MapNSImpl__.Util,
	k = 0,
	l = 0,
	i = {};
	i.listeners = {};
	i.eventObjects = {};
	var c = __MapNSImpl__.Class({
		className: "EventListener",
		initialize: function(e, h, a, f) {
			this.target = e;
			this.eventName = h;
			this.handler = a;
			this.type = f;
			this.bindHandler = null;
			this.id = k++;
			r(e, h)[this.id] = this;
			if (g.Browser().ie) i.listeners[this.id] = this
		},
		remove: function() {
			if (this.target) {
				switch (this.type) {
				case 1:
					this.target.removeEventListener(this.eventName, this.handler, false);
					break;
				case 4:
					this.target.removeEventListener(this.eventName, this.handler, true);
					break;
				case 2:
					this.target.detachEvent("on" + this.eventName, this.bindHandler);
					break;
				case 3:
					this.target["on" + this.eventName] = null
				}
				delete r(this.target, this.eventName)[this.id];
				delete i.listeners[this.id];
				this.target = this.handler = null
			}
		}
	});
	i.addListener = function(e, h, a, f) {
		a = g.callback(e, a, f);
		return new c(e, h, a, 0)
	};
	i.addListenerOnce = function(e, h, a, f) {
		var m = null;
		return m = i.addListener(e, h,
		function() {
			a.apply(e, arguments);
			m && m.remove();
			m = null
		},
		f)
	};
	i.removeListener = function(e) {
		e && e.remove()
	};
	i.clearListeners = function(e, h) {
		g.forEach(j(e, h),
		function(a, f) {
			f && f.remove()
		})
	};
	i.trigger = function(e, h, a) {
		g.forEach(j(e, h),
		function(f, m) {
			m && p(m, a)
		})
	};
	i.addDomListener = function(e, h, a, f) {
		a = g.callback(e, a, f);
		if (e.addEventListener) {
			f = false;
			if (h === "focusin") {
				h = "focus";
				f = true
			} else if (h === "focusout") {
				h = "blur";
				f = true
			}
			var m = f ? 4: 1;
			e.addEventListener(h, a, f);
			a = new c(e, h, a, m)
		} else if (e.attachEvent) {
			if (h === "mouseover" && (!e.tagName || e.tagName.toLowerCase() !== "area")) h = "mouseenter";
			else if (h === "mouseout" && (!e.tagName || e.tagName.toLowerCase() !== "area")) h = "mouseleave";
			a = new c(e, h, a, 2);
			e.attachEvent("on" + h, b(a))
		} else {
			e["on" + h] = a;
			a = new c(e, h, a, 3)
		}
		return a
	};
	i.addDomListenerOnce = function(e, h, a, f) {
		var m = null;
		return m = i.addDomListener(e, h,
		function() {
			a.apply(e, arguments);
			m && m.remove();
			m = null
		},
		f)
	};
	i.stopEvent = function(e) {
		i.preventDefault(e);
		i.stopPropagation(e)
	};
	i.preventDefault = function(e) {
		e = e || window.event;
		e.returnValue = false;
		e.preventDefault && e.preventDefault()
	};
	i.stopPropagation = function(e) {
		e = e || window.event;
		e.cancelBubble = true;
		e.stopPropagation && e.stopPropagation()
	};
	i.bindDom = function(e, h, a, f) {
		f = g.adapter(a, f);
		return i.addDomListener(e, h, f)
	};
	i.bind = function(e, h, a, f) {
		return i.addListener(e, h, g.adapter(a, f))
	};
	i.unload = function() {
		var e = i.listeners,
		h;
		for (h in e) e[h].remove();
		i.listeners = null;
		i.eventObjects = null; (e = window.CollectGarbage) && e()
	};
	d.Event = i
})(__MapNS__); (function(d) {
	function r(i, c, e, h, a) {
		o(i)[c] = {
			target: e,
			key: h
		};
		a || a || p(i, c)
	}
	function o(i) { ! i.hasOwnProperty("accessors_") && (i.accessors_ = {});
		return i.accessors_
	}
	function j(i) { ! i.hasOwnProperty("bindings_") && (i.bindings_ = {});
		return i.bindings_
	}
	function p(i, c) {
		var e = c + k;
		i[e] ? i[e]() : i.changed(c);
		g.trigger(i, c.toLowerCase() + k)
	}
	var b = __MapNSImpl__.Util,
	g = d.Event,
	k = "_changed",
	l = __MapNSImpl__.Class({
		initialize: function() {
			var i = {};
			this.set = function(c, e) {
				var h = o(this)[c];
				if (h) {
					var a = h.target;
					h = h.key;
					var f = "set" +
					b.capitalInitial(h);
					a[f] ? a[f](e) : a.set(h, e)
				} else {
					a = i[c];
					i[c] = e;
					p(this, c, a)
				}
			};
			this.get = function(c) {
				var e = o(this)[c];
				if (e) {
					c = e.target;
					e = e.key;
					var h = "get" + b.capitalInitial(e);
					return c[h] ? c[h]() : c.get(e)
				} else return i[c]
			}
		},
		setValues: function(i) {
			for (var c in i) {
				var e = i[c],
				h = "set" + o(c);
				this[h] ? this[h](e) : this.set(c, e)
			}
		},
		notify: function(i) {
			var c = o(this);
			if (c.hasOwnProperty(i)) {
				i = c[i];
				i.target.notify(i.key)
			} else p(this, i)
		},
		bindTo: function(i, c, e, h) {
			e = e || i;
			this.__checker__ = this.__checker__ || {};
			if (this.__checker__[i] !== this.__checker__[e]) throw Error("Can not bind properties of different type.");
			this.unbind(i);
			var a = e.toLowerCase() + k,
			f = this;
			j(this)[i] = g.addListener(c, a,
			function() {
				p(f, i)
			});
			r(this, i, c, e, h)
		},
		bindsTo: function(i, c, e, h) {
			e = e || [];
			for (var a = 0, f = i.length; a < f; a++) this.bindTo(i[a], c, e[a] || null, h)
		},
		unbind: function(i) {
			var c = j(this)[i];
			if (c) {
				delete j(this)[i];
				g.removeListener(c);
				c = this.get(i);
				delete o(this)[i];
				this[i] = c
			}
		},
		unbinds: function(i) {
			for (var c = 0, e = i.length; c < e; c++) this.unbind(i[c])
		},
		changed: function() {}
	});
	l.publicProperties = function(i, c) {
		b.forEach(c,
		function(e) {
			var h = "get" + b.capitalInitial(e);
			i.prototype[h] = function() {
				return this.get(e)
			};
			h = "set" + b.capitalInitial(e);
			i.prototype[h] = function(a) {
				var f = c[e];
				if (f && !f(a)) throw Error("Invalid value or type for property <" + (e + (">: " + a)));
				this.set(e, a)
			}
		})
	};
	l.prototype.setOptions = l.prototype.setValues;
	l.union = function() {
		var i = arguments,
		c = i.length;
		return function() {
			for (var e = 0; e < c; ++e) if (i[e].apply(this, arguments)) return true;
			return false
		}
	};
	l.checkers = {
		isNull: function(i) {
			return i === null || i === undefined
		},
		isBoolean: function(i) {
			return i === !!i
		},
		isArray: function(i) {
			return Object.prototype.toString.apply(i) === "[object Array]"
		},
		isObject: function(i) {
			return Object.prototype.toString.apply(i) === "[object Object]"
		},
		isString: function(i) {
			return typeof i === "string"
		},
		isHtmlControl: function(i) {
			return b.isHtmlControl(i)
		},
		isNumber: function(i) {
			return typeof i === "number" && (i || i === 0)
		},
		checkInterface: function(i) {
			return function(c) {
				var e = true,
				h;
				for (h in i) if (i.hasOwnProperty(h) && typeof c[h] !== "function") e = false;
				return e
			}
		},
		isInstanceOf: function(i) {
			return function(c) {
				return c instanceof i
			}
		}
	};
	l.checkers.isBoolOrNull = l.union(l.checkers.isBoolean, l.checkers.isNull);
	l.checkers.isStringOrNull = l.union(l.checkers.isString, l.checkers.isNull);
	l.checkers.isNumberOrNull = l.union(l.checkers.isNumber, l.checkers.isNull);
	l.checkers.checkInterfaceOrNull = l.union(l.checkers.checkInterfaces, l.checkers.isNull);
	l.checkers.isInstanceOfOrNull = l.union(l.checkers.isInstanceOf, l.checkers.isNull);
	d.MVCObject = l
})(__MapNS__); (function() {
	function d(a) {
		for (var f = 0, m; m = g[f]; f++) if (a === m[0]) return m;
		return null
	}
	var r = __MapNS__.Event,
	o = __MapNSImpl__.Config,
	j = __MapNS__.MVCObject,
	p = __MapNSImpl__.Synchronize,
	b = __MapNSImpl__.Util,
	g = [],
	k = false,
	l = __MapNSImpl__.Class(j, {
		initialize: function() {
			j.apply(this);
			this.renderTime_ = null
		},
		redraw: function() {
			var a = (new Date).getTime(),
			f = false;
			if (this.renderTime_) {
				if (a - this.renderTime > 100) {
					this.renderTime = a;
					f = true
				}
			} else {
				this.renderTime = a;
				f = true
			}
			var m = this;
			if (f) m.render ? m.render() : m.draw();
			else if (!this.renderTimer_) this.renderTimer_ = setTimeout(function() {
				m.render ? m.render() : m.draw();
				delete m.renderTimer_
			},
			0)
		},
		triggerMouseEvent: function(a, f) {
			f.latLng = this.getMouseLatLng(f);
			f.qLatLng = this.getMouseLatLng(f);
			var m = this.get("model_");
			m && r.trigger(m, a, [f])
		},
		registMouseEvents: function(a, f) {
			for (var m = this.get("model_"), v = 0, s = f.length; v < s; v++) {
				var u = function(y, t, n) {
					return function(q) {
						q.latLng = t.getMouseLatLng(q);
						q.qLatLng = t.getMouseLatLng(q);
						q.qLngLat = t.getMouseLatLng(q);
						if (n == "click") {
							var w = {};
							p.copy(w, q);
							if (!y.clickTimer_) y.clickTimer_ = setTimeout(function() {
								clearTimeout(y.clickTimer_);
								y.clickTimer_ = null;
								y && r.trigger(y, n, [w])
							},
							200)
						} else {
							if (n == "dblclick") if (y.clickTimer_) {
								clearTimeout(y.clickTimer_);
								y.clickTimer_ = null
							}
							y && r.trigger(y, n, [q])
						}
					}
				};
				if (f[v] === "click") {
					d(m) || g.push([m, false, null, false, this]);
					r.addDomListener(a, "mousedown",
					function(y) {
						return function(t) {
							var n = b.getMouseCoordinate(t),
							q = d(y);
							q[1] = false;
							q[2] = n;
							q[3] = t.button === 2;
							r.stopPropagation(t);
							u(q[0], q[4], "mousedown")(t)
						}
					} (m));
					if (!k) {
						k = true;
						r.addDomListener(document.body.parentNode || document.body, "mousemove",
						function(y) {
							for (var t = 0, n; n = g[t]; t++) if (n[2] && !n[3]) {
								var q = b.getMouseCoordinate(y);
								if (n[2][0] !== q[0] || n[2][1] !== q[1]) n[1] = true
							}
						});
						r.addDomListener(document.body.parentNode || document.body, "mouseup",
						function(y) {
							for (var t = 0, n; n = g[t]; t++) {
								var q = n[0].get("clickable") !== false;
								if (n[2] && !n[1] && q) if (y.button === 2) u(n[0], n[4], "rightclick")(y);
								else m.clickTimer_ || u(n[0], n[4], "click")(y);
								n[2] = null
							}
						})
					}
				} else r.addDomListener(a, f[v], u(m, this, f[v]))
			}
		},
		getMouseLatLng: function(a) {
			var f = this.get("map_") || this.get("map");
			if (f) return f.fromContainerPixelToLatLng(this.getMouseContainerPixel(a));
			return null
		},
		getMouseContainerPixel: function(a) {
			var f = this.get("map_") || this.get("map");
			if (f) {
				a = b.getMouseCoordinate(a);
				f = f.get("mapContainer_");
				f = b.getDomCoordinate(f);
				return new __MapNS__.Point(a[0] - f[0], a[1] - f[1])
			}
			return null
		},
		draw: function() {}
	}),
	i = {},
	c = [],
	e = {},
	h = !!document.createElement("canvas").getContext;
	l.loadView = function(a, f) {
		i[a] || (i[a] = {
			status: 0,
			models: [],
			view: null
		});
		switch (i[a].status) {
		case 2:
			new i[a].view(f);
			break;
		case 0:
			i[a].status = 1;
			i[a].models.push(f);
			var m = "";
			if (o.loader) m = "modules/" + a + "/";
			c.push(a);
			document.createElement("canvas");
			var v = a;
			if (h) v = e[a] || a;
			b.RPCRequest(o.domain + m + v + "View.js",
			function() {
				var s = 0,
				u = 0,
				y = false;
				s = 0;
				for (u = c.length; s < u; s++) {
					var t = null,
					n = i[c[s]];
					if (c[s] === a) {
						t = i[a];
						y = true
					} else if (y && n.view && n.status === 3) t = n;
					if (t) {
						t.status = 2;
						n = 0;
						for (var q = t.models.length; n < q; n++) new t.view(t.models[n])
					}
					if (!y && !i[c[s]].view) {
						i[a].status = 3;
						break
					}
				}
			});
			break;
		case 1:
			i[a].models.push(f)
		}
	};
	l.setView = function(a, f) {
		if(!i[a]) return;
		i[a].view = f;
		if (i[a].status !== 2) i[a].status = 2
	};
	l.getView = function(a) {
		return !! i[a].view
	};
	__MapNS__.MVCView = l
})(); (function(d) {
	var r = d.Event,
	o = __MapNSImpl__.Class(d.MVCObject, {
		initialize: function(j) {
			d.MVCObject.apply(this);
			this.elems = j || [];
			this.set("length", this.elems.length)
		},
		getAt: function(j) {
			return this.elems[j]
		},
		forEach: function(j) {
			for (var p = 0, b = this.length; p < b; ++p) j(this.elems[p], p)
		},
		setAt: function(j, p) {
			this.elems.splice(j, 0, p);
			this.set();
			this.set("length", this.elems.length);
			r.trigger(this, "insert_at", [j, p])
		},
		insertAt: function(j, p) {
			this.elems.splice(j, 0, p);
			this.set("length", this.elems.length);
			r.trigger(this, "insert_at", [j, p])
		},
		removeAt: function(j) {
			var p = this.elems[j];
			this.elems.splice(j, 1);
			this.set("length", this.elems.length);
			r.trigger(this, "remove_at", [j, p]);
			return p
		},
		push: function(j) {
			this.insertAt(this.elems.length, j);
			return this.elems.length
		},
		pop: function(j) {
			if (j) for (var p = 0; p < this.elems.length; p++) if (j == this.elems[p]) return this.removeAt(p);
			return this.removeAt(this.elems.length - 1)
		},
		exists: function(j) {
			if (j) for (var p = 0; p < this.elems.length; p++) if (j == this.elems[p]) return true;
			return false
		},
		getArray: function() {
			return this.elems
		}
	});
	d.MVCArray = o;
	d.MVCObject.publicProperties(o, {
		length: 0
	})
})(__MapNS__); (function(d) {
	function r() {
		for (var b = (new Date).getTime(), g = 0, k = j.length; g < k; g++) {
			var l = j[g];
			if (l && l.get("time_")) {
				var i = l.get("current");
				if (i < 0) break;
				var c = l.get("duration");
				l.get("fps");
				i > c && (i = c);
				l.frame(i);
				if (i >= c) {
					l.stop();
					break
				}
				l.set("current", (b - l.get("time_")) / 1E3)
			}
		}
	}
	var o = d.Event,
	j = [],
	p = null;
	d.Animation = __MapNSImpl__.Class(d.MVCObject, {
		initialize: function(b) {
			d.MVCObject.apply(this);
			b = b || {};
			b.duration = b.duration || 0;
			b.fps = 40;
			this.setValues(b)
		},
		prepare: function() {
			return true
		},
		start: function(b) {
			if (this.prepare()) {
				this.stop(true);
				var g = this.get("fps");
				this.set("current", 0.1 / g); ! b && o.trigger(this, "start");
				this.set("status", 1);
				j.push(this);
				this.set("time_", (new Date).getTime());
				p || (p = window.setInterval(r, 1E3 / g));
				r()
			}
		},
		stop: function(b) {
			this.set("time_", null);
			for (var g = 0, k = j.length; g < k; g++) if (j[g] === this) {
				j.splice(g, 1);
				break
			}
			if (j.length === 0) {
				p && window.clearInterval(p);
				p = null
			}
			this.set("status", 0);
			this.set("current", -1); ! b && o.trigger(this, "end")
		},
		getStatus: function() {
			return this.get("status")
		},
		frame: function() {}
	});
	d.Animation.BOUNCE = 0;
	d.Animation.DROP = 1;
	d.Animation.JUMP = 2;
	d.Animation.POP = 3;
	d.Fx = __MapNSImpl__.Class(d.Animation, {
		initialize: function(b) {
			b = b || {};
			b.begins = b.begins || [];
			b.ends = b.ends || [];
			b.duration = b.duration || 0;
			b.callback = b.callback ||
			function() {};
			b.fps = b.fps || 60;
			b.method = b.method || d.Fx.linear;
			d.MVCObject.apply(this);
			this.setValues(b)
		},
		isBeginEqualsEnd: function() {
			for (var b = this.get("begins"), g = this.get("ends"), k = true, l = 0, i = b.length; l < i; l++) if (b[l] !== g[l]) {
				k = false;
				break
			}
			return k
		},
		prepare: function() {
			return ! this.isBeginEqualsEnd()
		},
		getValues: function(b) {
			var g = this.get("begins"),
			k = this.get("ends"),
			l = this.get("method"),
			i = this.get("duration"),
			c = [],
			e = [],
			h,
			a;
			h = 0;
			for (a = g.length; h < a; ++h) {
				var f = k[h] - g[h],
				m;
				if (i != 0) {
					m = l(b, g[h], f, i);
					var v = b - 1 / this.get("fps");
					v < 0 && (v = 0);
					f = m - l(v, g[h], f, i)
				} else {
					m = k[h];
					f = f
				}
				c.push(m);
				e.push(f)
			}
			return {
				values: c,
				steps: e
			}
		},
		frame: function(b) {
			var g = this.getValues(b);
			b = g.steps;
			g = g.values;
			for (var k = true, l = 0, i = b.length; l < i; l++) if (b[l] !== 0) {
				k = false;
				break
			}
			k || this.qFxFrame(g, b)
		},
		qFxFrame: function(b, g) {
			var k = this.get("callback");
			k && k(b, g)
		}
	});
	d.CssFx = __MapNSImpl__.Class(d.Fx, {
		initialize: function(b) {
			b = b || {};
			b.items = b.items || [];
			b.duration = b.duration || 0;
			b.fps = b.fps || 60;
			b.method = b.method || d.Fx.linear;
			d.MVCObject.apply(this);
			this.setValues(b)
		},
		prepare: function() {
			for (var b = this.get("items"), g = [], k = [], l = 0, i = b.length; l < i; ++l) {
				var c = b[l];
				g = g.concat(c.begins);
				k = k.concat(c.ends)
			}
			this.set("begins", g);
			this.set("ends", k);
			return ! this.isBeginEqualsEnd()
		},
		qFxFrame: function(b) {
			for (var g = this.get("items"), k = 0, l = 0, i = g.length; l < i; ++l) {
				var c = g[l],
				e = c.styles,
				h = c.units;
				c = c.element;
				for (var a = 0, f = e.length; a < f; ++a) {
					c.style[e[a]] = parseInt(b[k], 10) + h[a];
					k++
				}
			}
		}
	});
	d.Fx.linear = function(b, g, k, l) {
		return k * b / l + g
	};
	d.Fx.easeInQuad = function(b, g, k, l) {
		return k * (b /= l) * b + g
	};
	d.Fx.easeOutQuad = function(b, g, k, l) {
		return - k * (b /= l) * (b - 2) + g
	};
	d.Fx.easeInOutQuad = function(b, g, k, l) {
		return (b /= l / 2) < 1 ? k / 2 * b * b + g: -k / 2 * (--b * (b - 2) - 1) + g
	};
	d.Fx.easeInCubic = function(b, g, k, l) {
		return k * Math.pow(b / l, 3) + g
	};
	d.Fx.easeOutCubic = function(b, g, k, l) {
		return k * (Math.pow(b / l - 1, 3) + 1) + g
	};
	d.Fx.easeInOutCubic = function(b, g, k, l) {
		return (b /= l / 2) < 1 ? k / 2 * Math.pow(b, 3) + g: k / 2 * (Math.pow(b - 2, 3) + 2) + g
	};
	d.Fx.easeInSine = function(b, g, k, l) {
		return k * (1 - Math.cos(b / l * (Math.PI / 2))) + g
	};
	d.Fx.easeOutSine = function(b, g, k, l) {
		return k * Math.sin(b / l * (Math.PI / 2)) + g
	};
	d.Fx.easeInOutSine = function(b, g, k, l) {
		return k / 2 * (1 - Math.cos(Math.PI * b / l)) + g
	};
	d.Fx.easeInExpo = function(b, g, k, l) {
		return k * Math.pow(2, 10 * (b / l - 1)) + g
	};
	d.Fx.easeOutExpo = function(b, g, k, l) {
		return k * ( - Math.pow(2, -10 * b / l) + 1) + g
	};
	d.Fx.easeInOutExpo = function(b, g, k, l) {
		return (b /= l / 2) < 1 ? k / 2 * Math.pow(2, 10 * (b - 1)) + g: k / 2 * ( - Math.pow(2, -10 * --b) + 2) + g
	};
	d.Fx.easeInCirc = function(b, g, k, l) {
		return k * (1 - Math.sqrt(1 - (b /= l) * b)) + g
	};
	d.Fx.easeOutCirc = function(b, g, k, l) {
		return k * Math.sqrt(1 - (b /= l - 1) * b) + g
	};
	d.Fx.easeInOutCirc = function(b, g, k, l) {
		return (b /= l / 2) < 1 ? k / 2 * (1 - Math.sqrt(1 - b * b)) + g: k / 2 * (Math.sqrt(1 - (b -= 2) * b) + 1) + g
	};
	d.Fx.easeInOutBack = function(b, g, k, l, i) {
		if (i == undefined) i = 2;
		if ((b /= l / 2) < 1) return k / 2 * b * b * (((i *= 1.525) + 1) * b - i) + g;
		return k / 2 * ((b -= 2) * b * (((i *= 1.525) + 1) * b + i) + 2) + g
	};
	d.Fx.easeInBack = function(b, g, k, l, i) {
		if (i == undefined) i = 20;
		return k * (b /= l) * b * ((i + 1) * b - i) + g
	};
	d.Fx.easeOutBack = function(b, g, k, l, i) {
		if (i == undefined) i = 20;
		return k * ((b = b / l - 1) * b * ((i + 1) * b + i) + 1) + g
	}
})(__MapNS__); (function(d) {
	function r(c, e) {
		var h = c[0] !== e[0] ? Math.atan((c[1] - e[1]) / (c[0] - e[0])) / Math.PI * 180: 0;
		h += 360;
		if (e[0] - c[0] > 0) h += 180;
		h >= 360 && (h -= 360);
		return h
	}
	function o(c, e, h) {
		c = r(c, e);
		c = c / Math.PI * 180;
		return [p(e, h, c + 90), p(e, h, c - 90)]
	}
	function j(c, e, h, a) {
		c = r(c, e);
		h = r(e, h);
		h = c / Math.PI * 180 + (180 - c / Math.PI * 180 + h / Math.PI * 180) / 2;
		return [p(e, a, h), p(e, a, h + 180)]
	}
	function p(c, e, h) {
		h < 0 && (h += 360);
		h > 360 && (h -= 360);
		h = h / 180 * Math.PI;
		return [c[0] - e * Math.cos(h), c[1] - e * Math.sin(h)]
	}
	function b(c, e) {
		return c[0] >= e[0] && c[0] <= e[0] +
		e[2] && c[1] >= e[1] && c[1] <= e[1] + e[3]
	}
	function g(c) {
		if ((typeof c).toLowerCase() === "string") if (c === "") c = [[0, 0]];
		else {
			var e = c.split(","),
			h = e.length;
			if (h % 2 != 0) throw Error("Points string is not valid.");
			c = [];
			for (var a = 0; a < h; a += 2) c.push([parseInt(e[a]), parseInt(e[a + 1])])
		}
		return c || []
	}
	var k = __MapNSImpl__.Util.Browser().ie && __MapNSImpl__.Util.Browser().ie < 9,
	l = d.MVCObject,
	i = __MapNSImpl__.Synchronize;
	d.Canvas = __MapNSImpl__.Class(l, {
		initialize: function(c) {
			l.apply(this);
			if (k) {
				var e = this; (function() {
					try {
						document.namespaces.v || document.namespaces.add("v", "urn:schemas-microsoft-com:vml", "#default#VML");
						e.set("ready_", true)
					} catch(s) {
						setTimeout(arguments.callee, 0)
					}
				})();
				this.set("container", c)
			} else {
				var h = document.createElementNS("http://www.w3.org/2000/svg", "svg");
				h.style.position = "absolute";
				h.setAttribute("version", "1.1");
				h.setAttribute("baseProfile", "full");
				c.appendChild(h);
				this.set("container", h);
				this.set("ready_", true)
			}
			this.set("shapes", []);
			this.offsetCoordSVG_ = null;
			this.set("viewport", null);
			d.Event.addDomListener(c, "mouseout",
			function(s) {
				for (var u = s.relatedTarget || s.toElement; u;) {
					if (u == c) return;
					u = u.parentNode
				}
				u = 0;
				for (var y; y = a[u]; u++) if (y.mouseIn) {
					d.Event.trigger(y, "mouseout", [s]);
					y.mouseIn = false
				}
			});
			var a = this.get("shapes");
			d.Event.addDomListener(c, "mousemove",
			function(s) {
				var u = __MapNSImpl__.Util.getMouseCoordinate(s),
				y = __MapNSImpl__.Util.getDomCoordinate(c);
				u = [u[0] - y[0], u[1] - y[1]];
				y = false;
				for (var t = 0, n; n = a[t]; t++) if (n.type == "polyline") {
					if (!y) {
						var q = n.get("strokeWeight") / 2;
						q = Math.max(q, 5);
						var w;
						a: {
							w = g(n.get("points"));
							var x = u;
							q = q;
							for (var z = 0, D = w.length; z < D - 1; z++) {
								var B,
								A = w[z];
								B = w[z + 1];
								var C = x,
								E = q,
								F = void 0,
								G = void 0,
								K = void 0,
								M = void 0,
								N = [A, B];
								M = A[0] > B[0] ? 0: 1;
								F = N[M][0] + E;
								G = N[1 - M][0] - E;
								M = A[1] > B[1] ? 0: 1;
								K = N[M][1] + E;
								M = N[1 - M][1] - E;
								N = null;
								if (b(C, [G, M, F - G, K - M])) if (! (A[0] === B[0] && A[1] === B[1])) if (A[0] === B[0]) {
									if (Math.abs(C[0] - B[0]) <= E) N = [A[0], C[1]]
								} else if (A[1] === B[1]) {
									if (Math.abs(C[1] - B[1]) <= E) N = [C[0], A[1]]
								} else {
									F = (A[0] - B[0]) / (B[1] - A[1]);
									G = F * F;
									if (Math.abs((C[0] + (F * C[1] + (B[1] * A[0] - A[1] * B[0]) / (A[1] - B[1]))) / Math.sqrt(1 + G)) <= E) {
										A = (B[0] - F * (C[1] - B[1]) + G * C[0]) / (1 + G);
										N = [A, (B[0] - A) / F + B[1]]
									}
								}
								B = N;
								if (B != null) {
									B.push(z);
									w = B;
									break a
								}
							}
							w = null
						}
						if (w != null) {
							if (!n.mouseIn) {
								n.mouseIn = true;
								d.Event.trigger(n, "mouseover", [s, w])
							}
							d.Event.trigger(n, "mousemove", [s, w]);
							n.linePoint = w;
							y = true;
							c.style.cursor = n.get("cursor");
							break
						}
					}
					if (n.mouseIn) {
						d.Event.trigger(n, "mouseout", [s]);
						n.mouseIn = false
					}
				}
				if (!y && c.style.cursor != "") c.style.cursor = ""
			});
			h = ["click", "dblclick", "mousedown", "mouseup"];
			for (var f = 0, m = h.length; f < m; f++) {
				var v = h[f];
				d.Event.addDomListener(c, v,
				function(s) {
					return function(u) {
						for (var y = 0, t; t = a[y]; y++) if (t.mouseIn) {
							d.Event.trigger(t, s, [u, t.linePoint]);
							s == "mouseup" && u.button == 2 && d.Event.trigger(t, "contextmenu", [u, t.linePoint])
						}
					}
				} (v))
			}
		},
		draw: function(c) {
			for (var e = false, h = this.get("shapes"), a = 0, f = h.length; a < f; a++) if (c === h[a]) {
				e = true;
				break
			}
			if (!e) {
				h.push(c);
				c.bindTo("ready_", this);
				c.set("canvas", this)
			}
		},
		erase: function(c) {
			if (c && c.shape_) {
				for (var e = false, h = this.get("shapes"), a = 0, f = h.length; a < f; a++) if (c === h[a]) {
					e = true;
					h.splice(a, 1);
					break
				}
				if (e) {
					this.get("container").removeChild(c.shape_);
					c.shape_ = null;
					c.set("canvas", null);
					this.resetOffsetCoordSVG_(true)
				}
			}
		},
		resizeSVG_: function() {
			if (!k) {
				var c = this.get("container");
				c.style.left = this.offsetCoordSVG_[0] + "px";
				c.style.width = this.offsetCoordSVG_[2] - this.offsetCoordSVG_[0] + "px";
				c.style.top = this.offsetCoordSVG_[1] + "px";
				c.style.height = this.offsetCoordSVG_[3] - this.offsetCoordSVG_[1] + "px";
				c = this.get("shapes");
				for (var e = 0, h; h = c[e]; e++) h.render_()
			}
		},
		resetOffsetCoordSVG_: function(c, e) {
			if (!k) {
				var h = false;
				if (c) {
					this.offsetCoordSVG_ = null;
					h = true
				}
				var a = e ? [e] : this.get("shapes"),
				f;
				for (f = 0; e = a[f]; f++) if (this.offsetCoordSVG_) {
					if (this.offsetCoordSVG_[0] > e.offsetCoordSVG_[0]) {
						this.offsetCoordSVG_[0] = e.offsetCoordSVG_[0];
						h = true
					}
					if (this.offsetCoordSVG_[1] > e.offsetCoordSVG_[1]) {
						this.offsetCoordSVG_[1] = e.offsetCoordSVG_[1];
						h = true
					}
					if (this.offsetCoordSVG_[2] < e.offsetCoordSVG_[2]) {
						this.offsetCoordSVG_[2] = e.offsetCoordSVG_[2];
						h = true
					}
					if (this.offsetCoordSVG_[3] < e.offsetCoordSVG_[3]) {
						this.offsetCoordSVG_[3] = e.offsetCoordSVG_[3];
						h = true
					}
				} else {
					this.offsetCoordSVG_ = e.offsetCoordSVG_.concat([]);
					h = true
				}
				h && this.offsetCoordSVG_ && this.resizeSVG_()
			}
		},
		sortShapesSVG_: function() {
			if (!k) {
				var c = this.get("shapes"),
				e,
				h,
				a = this.get("container");
				for (e = 0; h = c[e]; e++) a.removeChild(h.shape_);
				__MapNSImpl__.Util.sortByGetter(c,
				function(f) {
					return f.get("zIndex")
				});
				for (e = 0; h = c[e]; e++) a.appendChild(h.shape_)
			}
		}
	});
	d.CanvasShape = __MapNSImpl__.Class(l, {
		initialize: function() {
			l.apply(this)
		},
		getPath: function() {
			return this.path_.join(" ")
		},
		getShape: function() {
			return this.shape_
		},
		setDefaultOptions_: function(c) {
			c = i.fill(c, {
				strokeColor: "#1791fc",
				strokeWeight: 2,
				strokeDashStyle: "solid",
				fillColor: "",
				fillOpacity: 0.2,
				visible: true,
				zIndex: 0,
				cursor: ""
			});
			this.setOptions(c)
		},
		render_: function() {
			if (!this.donotdraw_) {
				var c = this.get("canvas");
				if (c && this.get("ready_")) {
					this.path_ = [];
					this.draw_();
					if (this.stopSVG_) {
						this.stopSVG_ = false;
						c.resetOffsetCoordSVG_(false, this)
					}
					this.createShape_();
					this.setShapeOptions_()
				}
			}
		},
		createShape_: function() {
			var c = this.shape_,
			e = this.get("canvas").get("container");
			if (!c && this.path_ && this.path_.length > 0) {
				if (k) {
					e.insertAdjacentHTML("beforeEnd", '<v:shape style="width:1px;height:1px;position:absolute;display:inline-block;"><v:stroke endcap="round"/><v:fill /></v:shape>');
					c = e.lastChild;
					c.coordsize = "1,1"
				} else {
					c = document.createElementNS("http://www.w3.org/2000/svg", "path");
					c.setAttribute("stroke-linejoin", "round");
					c.setAttribute("stroke-linecap", "round");
					e.appendChild(c)
				}
				this.shape_ = c
			}
		},
		setShapeOptions_: function() {
			var c = this.shape_;
			if (c) {
				var e = this.get("strokeDashStyle"),
				h = this.get("fillColor"),
				a = parseInt(this.get("strokeWeight"), 10);
				if (k) {
					if (e === "dash") e = "1,1";
					var f = c.firstChild,
					m = c.lastChild;
					if (h) {
						c.filled = true;
						c.fillcolor = h;
						m.opacity = this.get("fillOpacity")
					} else c.filled = false;
					f.dashstyle = e;
					c.strokeColor = this.get("strokeColor");
					c.strokeweight = a + "px";
					f.opacity = a === 0 ? 0: this.get("strokeOpacity");
					c.style.cursor = this.get("cursor");
					c.path = this.getPath()
				} else {
					if (e === "dash") e = a + "," + a * 2;
					c.setAttribute("stroke", this.get("strokeColor"));
					c.setAttribute("stroke-width", a + "px");
					c.setAttribute("stroke-dasharray", e);
					a === 0 ? c.setAttribute("stroke-opacity", 0) : c.setAttribute("stroke-opacity", this.get("strokeOpacity"));
					if (h) {
						c.setAttribute("fill", h);
						c.setAttribute("fill-opacity", this.get("fillOpacity"))
					} else c.setAttribute("fill", "none");
					c.setAttribute("cursor", this.get("cursor"));
					c.setAttribute("d", this.getPath())
				}
				c.style.zIndex = this.get("zIndex");
				c.style.display = this.get("visible") ? "": "none"
			}
		},
		draw_: function() {},
		moveTo_: function() {
			var c = 0,
			e = 0;
			if (arguments.length === 1) {
				c = parseFloat(arguments[0][0]);
				e = parseFloat(arguments[0][1])
			} else {
				c = parseFloat(arguments[0]);
				e = parseFloat(arguments[1])
			}
			this.checkCoordsSVG_([c, e]);
			var h = "";
			this.currentPoint_ = [c, e];
			if (k) {
				c = Math.round(c);
				e = Math.round(e);
				h = ["m ", c, ",", e].join("")
			} else {
				if (h = this.get("canvas").offsetCoordSVG_) {
					c -= h[0];
					e -= h[1]
				}
				h = ["M ", c, ",", e].join("")
			}
			this.path_.push(h)
		},
		linesTo_: function(c) {
			for (var e = 0, h = c.length; e < h; e++) this.lineTo_(c[e])
		},
		lineTo_: function() {
			this.currentPoint_ || this.moveTo_(0, 0);
			var c = 0,
			e = 0;
			if (arguments.length === 1) {
				c = parseFloat(arguments[0][0]);
				e = parseFloat(arguments[0][1])
			} else {
				c = parseFloat(arguments[0]);
				e = parseFloat(arguments[1])
			}
			this.checkCoordsSVG_([c, e]);
			c = Math.round(c);
			e = Math.round(e);
			var h = "";
			this.currentPoint_ = [c, e];
			if (k) {
				c = Math.round(c);
				e = Math.round(e);
				h = ["l ", c, ",", e].join("")
			} else {
				if (h = this.get("canvas").offsetCoordSVG_) {
					c -= h[0];
					e -= h[1]
				}
				h = ["L ", c, ",", e].join("")
			}
			this.path_.push(h)
		},
		arcTo_: function(c, e, h) {
			this.currentPoint_ || this.moveTo_(0, 0);
			var a = 0,
			f = 0;
			if (arguments.length === 4) {
				a = parseFloat(arguments[3][0]);
				f = parseFloat(arguments[3][1])
			} else {
				a = parseFloat(arguments[3]);
				f = parseFloat(arguments[4])
			}
			var m = "";
			m = h === 0 ? "at ": "wr ";
			var v = this.currentPoint_,
			s = [(v[0] + a) / 2, (v[1] + f) / 2],
			u = (a - v[0]) / (f - v[1]);
			u = Math.atan(u) / Math.PI * 180;
			var y = h;
			u > 0 && (y = 1 - h);
			u = Math.abs(u);
			e === y && (u = -u);
			y = v[0] - s[0];
			var t = v[1] - s[1];
			y = Math.sqrt(y * y + t * t);
			y = Math.sqrt(Math.abs(c * c - y * y));
			u = p(s, y, u);
			this.currentPoint_ = [a, f];
			if (k) {
				s = Math.ceil(u[0]);
				u = Math.ceil(u[1]);
				c = Math.ceil(c);
				a = Math.ceil(a);
				f = Math.ceil(f);
				m = [m, s - c, ",", u - c, ",", s + c, ",", u + c, ",", Math.ceil(v[0]), ",", Math.ceil(v[1]), ",", a, ",", f].join("")
			} else {
				this.checkCoordsSVG_([u[0] - c, u[1] - c], [u[0] + c, u[1] + c]);
				if (m = this.get("canvas").offsetCoordSVG_) {
					a -= m[0];
					f -= m[1]
				}
				m = ["A ", c, ",", c, ",0,", e, ",", h, ",", a, ",", f].join("")
			}
			this.path_.push(m)
		},
		curveTo_: function() {
			this.currentPoint_ || this.moveTo_(0, 0);
			var c = this.currentPoint_,
			e = null,
			h = null,
			a = null;
			if (arguments.length === 2) {
				h = arguments[0].concat([]);
				e = arguments[1].concat([])
			} else if (arguments.length === 4) {
				h = [arguments[0], arguments[1]];
				e = [arguments[2], arguments[3]]
			} else if (arguments.length === 3) {
				h = arguments[0].concat([]);
				a = arguments[1].concat([]);
				e = arguments[2].concat([])
			} else if (arguments.length === 6) {
				h = [arguments[0], arguments[1]];
				a = [arguments[2], arguments[3]];
				e = [arguments[4], arguments[5]]
			}
			this.checkCoordsSVG_(h, a, e);
			var f = "";
			if (k) {
				e[0] = Math.round(e[0]);
				e[1] = Math.round(e[1]);
				if (a === null) {
					h = h;
					h = [c[0] + 2 / 3 * (h[0] - c[0]), c[1] + 2 / 3 * (h[1] - c[1])];
					a = [h[0] + (e[0] - c[0]) / 3, h[1] + (e[1] - c[1]) / 3]
				}
				h[0] = Math.round(h[0]);
				h[1] = Math.round(h[1]);
				a[0] = Math.round(a[0]);
				a[1] = Math.round(a[1]);
				f = ["c ", h[0], ",", h[1], ",", a[0], ",", a[1], ",", e[0], ",", e[1]].join("")
			} else {
				if (c = this.get("canvas").offsetCoordSVG_) {
					h[0] -= c[0];
					h[1] -= c[1];
					e[0] -= c[0];
					e[1] -= c[1]
				}
				if (a !== null) {
					if (c) {
						a[0] -= c[0];
						a[1] -= c[1]
					}
					f = ["C ", h[0], ",", h[1], ",", a[0], ",", a[1], ",", e[0], ",", e[1]].join("")
				} else f = ["Q ", h[0], ",", h[1], ",", e[0], ",", e[1]].join("")
			}
			this.currentPoint_ = e.concat([]);
			this.path_.push(f)
		},
		checkCoordsSVG_: function() {
			if (! (k || !this.get("canvas"))) for (var c = 0; c < arguments.length; c++) if (arguments[c]) {
				var e = arguments[c][0],
				h = arguments[c][1],
				a = parseInt(this.get("strokeWeight"), 10);
				if (this.offsetCoordSVG_) {
					if (e -
					a / 2 < this.offsetCoordSVG_[0]) {
						this.offsetCoordSVG_[0] = e - a / 2;
						this.stopSVG_ = true
					}
					if (e + a / 2 > this.offsetCoordSVG_[2]) {
						this.offsetCoordSVG_[2] = e + a / 2;
						this.stopSVG_ = true
					}
					if (h - a / 2 < this.offsetCoordSVG_[1]) {
						this.offsetCoordSVG_[1] = h - a / 2;
						this.stopSVG_ = true
					}
					if (h + a / 2 > this.offsetCoordSVG_[3]) {
						this.offsetCoordSVG_[3] = h + a / 2;
						this.stopSVG_ = true
					}
				} else {
					this.offsetCoordSVG_ = [e - a / 2, h - a / 2, e + a / 2, h + a / 2];
					this.stopSVG_ = true
				}
			}
		},
		stopDraw: function() {
			this.donotdraw_ = true
		},
		forceDraw: function() {
			this.donotdraw_ = false;
			this.render_()
		},
		changed: function(c) {
			if (c === "zIndex" && !k) {
				this.get("zIndex") !== this.lastZIndex_ && this.get("canvas") && this.get("canvas").sortShapesSVG_();
				this.lastZIndex_ = this.get("zIndex")
			} else this.render_()
		}
	});
	d.CanvasPolyline = __MapNSImpl__.Class(d.CanvasShape, {
		initialize: function(c, e) {
			d.CanvasShape.apply(this);
			e = e || [];
			e.points = c;
			this.setDefaultOptions_(e);
			this.type = "polyline";
			this.mouseIn = false;
			this.linePoint = null
		},
		draw_: function() {
			var c = g(this.get("points")),
			e = this.get("canvas").get("viewport");
			this.moveTo_(0, 0);
			if (c.length > 0) if (e && !this.get("fillColor")) for (var h = c[0], a = "move", f = 1, m = c.length; f < m; f++) {
				var v = c[f],
				s;
				s = h;
				var u = v,
				y = e;
				s[0] = parseInt(s[0], 10);
				u[0] = parseInt(u[0], 10);
				s[1] = parseInt(s[1], 10);
				u[1] = parseInt(u[1], 10);
				var t = y[0],
				n = y[1],
				q = y[0] + y[2],
				w = y[1] + y[3],
				x = [];
				if (u[1] === s[1]) {
					if (u[1] >= n && u[1] <= w) { (t - u[0]) * (t - s[0]) < 0 && x.push([t, s[1]]); (q - u[0]) * (q - s[0]) < 0 && x.push([q, s[1]])
					}
				} else if (u[0] == s[0]) {
					if (u[0] >= t && u[0] <= q) { (n - u[1]) * (n - s[1]) < 0 && x.push([s[0], n]); (w - u[1]) * (w - s[1]) < 0 && x.push([s[0], w])
					}
				} else {
					var z = (s[0] - u[0]) / (u[1] - s[1]),
					D = s[0] + z * s[1],
					B = D - z * n,
					A = D - z * w;
					B >= t && B <= q && (B - s[0]) * (B - u[0]) <= 0 && x.push([B, n]);
					A >= t && A <= q && (A - s[0]) * (A - u[0]) <= 0 && x.push([A, w]);
					B = (D - t) / z;
					z = (D - q) / z;
					B >= n && B <= w && (B - s[1]) * (B - u[1]) <= 0 && x.push([t, B]);
					z >= n && z <= w && (z - s[1]) * (z - u[1]) <= 0 && x.push([q, z])
				}
				t = [];
				if (b(s, y)) {
					t.push(s);
					x.length == 1 ? t.push(x[0]) : t.push(u)
				} else if (x.length == 1) {
					t.push(x[0]);
					t.push(u)
				} else if (x.length == 2) t = x;
				s = t;
				if (s.length == 2) {
					if (s[0] != h) this.moveTo_(s[0]);
					else a == "move" && this.moveTo_(h);
					s[1] != v ? this.lineTo_(s[1]) : this.lineTo_(v);
					a = "line"
				}
				h = v
			} else {
				e = c.shift();
				this.moveTo_(e);
				this.linesTo_(c);
				this.get("fillColor") && this.lineTo_(e)
			}
		}
	});
	d.CanvasCircle = __MapNSImpl__.Class(d.CanvasShape, {
		initialize: function(c, e, h) {
			d.CanvasShape.apply(this);
			h = h || [];
			h.center = c || [0, 0];
			h.radius = e || 0;
			this.set("center", c);
			this.set("radius", e);
			this.setDefaultOptions_(h)
		},
		draw_: function() {
			var c = this.get("center"),
			e = this.get("radius");
			if (!c || !e) this.moveTo_(0, 0);
			else {
				var h = c[0] + e,
				a = c[0] - e;
				this.moveTo_(a, c[1]);
				this.arcTo_(e, 0, 0, [h, c[1]]);
				this.arcTo_(e, 0, 0, [a, c[1]])
			}
		}
	});
	d.CanvasSimpleArrow = __MapNSImpl__.Class(d.CanvasShape, {
		initialize: function(c, e, h, a, f, m) {
			m = m || [];
			m.strokeWeight = m.strokeWeight || 0;
			m.fillColor = m.fillColor || "#1791fc";
			m.fillOpacity = m.fillOpacity || 0.8;
			m.peak = c;
			m.direction = e || 0;
			m.weight = h || 4;
			m.height = a || 8;
			m.angle = f || 90;
			this.setDefaultOptions_(m)
		},
		draw_: function() {
			var c = this.get("peak");
			if (c) {
				var e = this.get("direction"),
				h = this.get("weight"),
				a = this.get("height"),
				f = this.get("angle") / 2,
				m = Math.abs(a / Math.cos(f / 180 * Math.PI)),
				v = e - f + 180;
				a = 180 + e;
				e = p(c, m, e + f + 180);
				f = p(e, h, a);
				var s = p(c, h, a);
				m = p(c, m, v);
				h = p(m, h, a);
				this.moveTo_(c);
				this.linesTo_([e, f, s, h, m])
			}
		}
	});
	d.CanvasArrow = __MapNSImpl__.Class(d.CanvasShape, {
		initialize: function() {
			var c = null,
			e = null,
			h = null,
			a = null;
			if (arguments.length === 5) {
				e = arguments[1];
				h = arguments[2];
				c = arguments[3];
				a = arguments[4]
			} else if (arguments.length === 4) {
				h = arguments[1];
				c = arguments[2];
				a = arguments[3]
			} else throw Error("Parameters are illegal to create a arrow.");
			a = a || [];
			a.start = arguments[0];
			a.cpS = e;
			a.cpE = h;
			a.end = c;
			a.weight = a.weight || 10;
			a.height = a.height || 6;
			this.setDefaultOptions_(a)
		},
		draw_: function() {
			var c = this.get("cpS"),
			e = this.get("cpE"),
			h = this.get("end"),
			a = this.get("weight"),
			f = this.get("start");
			o(e, h, a);
			var m = r(e, h);
			m = m / Math.PI * 180;
			var v = p(h, 20, m);
			v = o(h, v, a * 2);
			m = p(h, 15, m);
			m = o(h, m, a);
			var s = null,
			u = null;
			if (c) {
				s = j(f, c, e, a / 3);
				u = j(c, e, h, a * 2 / 3)
			} else u = j(f, e, h, a / 2);
			this.moveTo_(f);
			if (c) {
				this.curveTo_(s[0], u[0], m[0]);
				this.lineTo_(v[0]);
				this.lineTo_(h);
				this.lineTo_(v[1]);
				this.lineTo_(m[1]);
				this.curveTo_(u[1], s[1], f)
			} else {
				this.curveTo_(u[0], m[0]);
				this.lineTo_(v[0]);
				this.lineTo_(h);
				this.lineTo_(v[1]);
				this.lineTo_(m[1]);
				this.curveTo_(u[1], f)
			}
		}
	})
})(__MapNS__); (function(d) {
	var r = d.Event;
	d.Copyright = function(o, j, p) {
		this.text = o;
		this.bounds = j;
		this.minZoom = p
	};
	d.CopyrightCollection = __MapNSImpl__.Class({
		initialize: function(o) {
			this.cpcol = [];
			this.prefix = o
		},
		addCopyright: function(o) {
			this.cpcol.push(o);
			r.trigger(this, "newcopyright", [o])
		},
		getCopyrights: function(o, j) {
			for (var p = [], b, g = 0; g < this.cpcol.length; g++) {
				b = this.cpcol[g];
				j >= b.minZoom && b.bounds.intersects(o) && p.push(this.cpcol[g].text)
			}
			return p
		},
		getCopyrightNotice: function(o, j) {
			var p = this.getCopyrights(o, j);
			if (p.length === 0) return null;
			return (typeof this.prefix === "string" ? this.prefix + " ": "") + p.join(", ")
		}
	})
})(__MapNS__); (function(d) {
	var r = d.MVCObject.checkers;
	__MapNSImpl__.Interfaces.registInterface("Point", ["getX", "getY", "equals"]);
	d.Point = __MapNSImpl__.Class({
		initialize: function(o, j) {
			o = parseFloat(o, 10);
			j = parseFloat(j, 10);
			if (r.isNumber(o) && r.isNumber(j)) {
				this.x = o;
				this.y = j
			} else throw Error("Point need numbers to be initialized.");
		},
		getX: function() {
			return this.x
		},
		getY: function() {
			return this.y
		},
		equals: function(o) {
			return this.getX() === o.getX() && this.getY() === o.getY()
		},
		add: function(o, j) {
			this.x += o;
			this.y += j
		},
		toString: function() {
			return this.getX() + ", " + this.getY()
		}
	})
})(__MapNS__); (function(d) {
	var r = d.MVCObject.checkers;
	__MapNSImpl__.Interfaces.registInterface("Size", ["getWidth", "getHeight", "equals"]);
	d.Size = __MapNSImpl__.Class({
		initialize: function(o, j) {
			o = parseFloat(o, 10);
			j = parseFloat(j, 10);
			if (r.isNumber(o) && r.isNumber(j)) {
				this.width = o;
				this.height = j
			} else throw Error("Size need numbers to be initialized.");
		},
		getWidth: function() {
			return this.width
		},
		getHeight: function() {
			return this.height
		},
		equals: function(o) {
			return this.getWidth() === o.getWidth() && this.getHeight() === o.getHeight()
		},
		toString: function() {
			return this.getWidth() + ", " + this.getHeight()
		}
	})
})(__MapNS__); (function(d) {
	var r = d.MVCObject.checkers,
	o = __MapNSImpl__.Util;
	__MapNSImpl__.Interfaces.registInterface("LatLng", ["getLng", "getLat", "equals", "toUrlValue"]);
	d.LatLng = __MapNSImpl__.Class({
		initialize: function(j, p, b) {
			p = parseFloat(p, 10);
			j = parseFloat(j, 10);
			if (r.isNumber(p) && r.isNumber(j)) {
				if (!b) {
					j = o.getNumberInRange(j, -90, 90);
					p = o.getLoopNumber(p, -180, 180)
				}
				this.lng = p;
				this.lat = j
			}
		},
		getLng: function() {
			return this.lng
		},
		getLat: function() {
			return this.lat
		},
		equals: function(j) {
			if (!j) return false;
			return o.isEquals(this.getLat(), j.getLat()) && o.isEquals(this.getLng(), j.getLng())
		},
		getDistanceTo: function(j) {
			var p = function(k) {
				return Math.PI / 180 * k
			},
			b = p(j.getLat() - this.getLat()),
			g = p(j.getLng() - this.getLng());
			j = Math.sin(b / 2) * Math.sin(b / 2) + Math.cos(p(this.getLat())) * Math.cos(p(j.getLat())) * Math.sin(g / 2) * Math.sin(g / 2);
			return 6378137 * 2 * Math.asin(Math.min(1, Math.sqrt(j)))
		},
		toUrlValue: function(j) {
			j = j || 6;
			return this.getLat().toFixed(j) + "," + this.getLng().toFixed(j)
		},
		toString: function() {
			return this.getLat() + ", " + this.getLng()
		}
	})
})(__MapNS__); (function(d) {
	var r = d.LatLng;
	d.LatLngBounds = __MapNSImpl__.Class({
		initialize: function(o, j) {
			this.ne = this.sw = null;
			o && this.extend(o);
			j && this.extend(j)
		},
		getCenter: function() {
			var o = null;
			if (!this.isEmpty()) {
				var j = this.getNorthEast(),
				p = this.getSouthWest();
				o = (j.getLat() + p.getLat()) / 2;
				j = (j.getLng() + p.getLng()) / 2;
				o = new r(o, j)
			}
			return o
		},
		getNorthEast: function() {
			return this.ne
		},
		getSouthWest: function() {
			return this.sw
		},
		intersects: function(o) {
			if (this.isEmpty() || o.isEmpty()) return null;
			var j = o.getSouthWest(),
			p = o.getNorthEast(),
			b = this.getSouthWest(),
			g = this.getNorthEast();
			if (b.getLat() > p.getLat() || g.getLat() < j.getLat() || b.getLng() > p.getLng() || g.getLng() < j.getLng()) return null;
			o = Math.max(b.getLng(), j.getLng());
			var k = Math.min(g.getLng(), p.getLng());
			j = Math.max(b.getLat(), j.getLat());
			p = Math.min(g.getLat(), p.getLat());
			return new d.LatLngBounds(new r(j, o), new r(p, k))
		},
		union: function(o) {
			if (!o.isEmpty()) {
				this.extend(o.getNorthEast());
				this.extend(o.getSouthWest())
			}
		},
		equals: function(o) {
			return this.getSouthWest().equals(o.getSouthWest()) && this.getNorthEast().equals(o.getNorthEast())
		},
		contains: function(o) {
			if (this.isEmpty()) return false;
			var j = o.getLng();
			o = o.getLat();
			var p = this.getSouthWest(),
			b = this.getNorthEast();
			return j >= p.getLng() && j <= b.getLng() && o >= p.getLat() && o <= b.getLat()
		},
		isEmpty: function() {
			return ! this.getNorthEast() && !this.getSouthWest()
		},
		extend: function(o) {
			if (this.isEmpty()) this.ne = this.sw = o;
			else {
				var j = this.sw,
				p = j.getLat();
				j = j.getLng();
				var b = this.ne,
				g = b.getLat();
				b = b.getLng();
				var k = o.getLat();
				o = o.getLng();
				k < p && (p = k);
				o < j && (j = o);
				k > g && (g = k);
				o > b && (b = o);
				this.sw = new r(p, j);
				this.ne = new r(g, b)
			}
		},
		toString: function() {
			return this.getSouthWest() + ", " + this.getNorthEast()
		}
	})
})(__MapNS__); (function(d) {
	d.LatLngCircles = __MapNSImpl__.Class({
		initialize: function(r, o) {
			this.private_ = {
				center: null,
				radius: null
			};
			this.private_.center = r;
			this.private_.radius = o
		},
		getCenter: function() {
			return this.private_.center
		},
		getRadius: function() {
			return this.private_.radius
		}
	})
})(__MapNS__); (function(d) {
	var r = d.LatLng,
	o = d.Point;
	d.Projection = __MapNSImpl__.Class({
		initialize: function() {},
		fromLatLngToPoint: function(j) {
			return new o(Util.lngFrom4326ToProjection(j.getLng()), Util.latFrom4326ToProjection(j.getLat()))
		},
		fromPointToLatLng: function(j, p) {
			return new r(Util.latFromProjectionTo4326(j.getY()), Util.lngFromProjectionTo4326(j.getX()), p)
		}
	})
})(__MapNS__); (function(d) {
	function r(k, l, i) {
		var c = k.get("projection"),
		e = k.get("width") / 2,
		h = k.get("height") / 2,
		a = i || k.get("center");
		i = k.get("zoom");
		e = l.getX() - e;
		l = l.getY() - h;
		h = c.fromLatLngToPoint(a);
		k = k.getResolution(i);
		i = e * k.x + h.getX();
		k = -l * k.y + h.getY();
		return c.fromPointToLatLng(new p(i, k))
	}
	function o(k, l, i) {
		var c = k.get("projection"),
		e = k.get("width") / 2,
		h = k.get("height") / 2,
		a = i || k.get("center");
		i = k.get("zoom");
		l = c.fromLatLngToPoint(l);
		c = c.fromLatLngToPoint(a);
		i = k.getResolution(i);
		k = (l.getX() - c.getX()) / i.x;
		l = -(l.getY() -
		c.getY()) / i.y;
		return new p(e + k, h + l)
	}
	var j = d.MVCObject.checkers,
	p = d.Point,
	b = d.LatLng,
	g = __MapNSImpl__.Config;
	d.MapCanvasProjection = __MapNSImpl__.Class(d.MVCObject, {
		initialize: function() {
			d.MVCObject.apply(this)
		},
		fromContainerPixelToLatLng: function(k) {
			return r(this, k)
		},
		fromLatLngToContainerPixel: function(k) {
			return o(this, k)
		},
		fromDivPixelToLatLng: function(k) {
			var l = this.get("origin");
			return r(this, k, l)
		},
		fromLatLngToDivPixel: function(k) {
			var l = this.get("origin");
			return o(this, k, l)
		},
		getWorldWidth: function() {
			var k = this.get("projection"),
			l = this.get("zoom");
			if (k && j.isNumber(l)) {
				var i = k.fromLatLngToPoint(new b(0, 179.999999));
				k = k.fromLatLngToPoint(new b(0, -179.999999));
				l = this.getResolution(l);
				i = new p((i.getX() - k.getX()) / l.x, (i.getY() - k.getY()) / l.y);
				return Math.round(Math.sqrt(i.getX() * i.getX() + i.getY() * i.getY()))
			}
		},
		getResolution: function(k) {
			var l = this.get("mapType"),
			i = l.tileSize;
			l = l.radius * Math.PI * 2;
			k = this.getWorldTileDimension(k);
			return {
				x: l / i.getWidth() / k,
				y: l / i.getHeight() / k
			}
		},
		getWorldTileDimension: function(k) {
			var l = {},
			i;
			for (i = g.zoomMax + 1; i--;) l[i] = 1 << i;
			return k ? l[k] : l
		}
	})
})(__MapNS__); (function(d) {
	var r = __MapNSImpl__.Synchronize,
	o = __MapNSImpl__.Config,
	j = d.Size;
	d.MapType = __MapNSImpl__.Class({
		initialize: function(p) {
			var b = {
				name: "",
				tileLayers: null,
				projection: new d.Projection,
				alt: "",
				maxZoom: o.zoomMax,
				minZoom: o.zoomMin,
				radius: 6378137,
				tileSize: new j(256, 256)
			};
			p = r.fill(p, b);
			this.tileLayers = p.tileLayers;
			this.projection = p.projection;
			this.name = p.name;
			this.alt = p.alt;
			this.minZoom = p.minZoom;
			this.maxZoom = p.maxZoom;
			this.tileSize = p.tileSize;
			this.radius = p.radius
		},
		getCopyrights: function(p, b) {
			var g = this.tileLayers,
			k = "",
			l,
			i;
			for (i = g.length; i--;) if ((l = g[i].getCopyright(p, b)) && k.indexOf(l) === -1) k += k === "" ? l: " " + l;
			return k
		}
	});
	d.MapTypeId = {
		ROADMAP: "ROADMAP",
		HYBRID: "HYBRID",
		SATELLITE: "SATELLITE"
	}
})(__MapNS__); (function(d) {
	d.Overlay = __MapNSImpl__.Class(d.MVCView, {
		initialize: function() {
			d.MVCObject.apply(this)
		},
		getPanes: function() {
			return this.get("mapPane_") ? {
				mapPane: this.get("mapPane_"),
				overlayLayer: this.get("overlayLayer_"),
				overlayShadow: this.get("overlayShadow_"),
				overlayImage: this.get("overlayImage_"),
				floatShadow: this.get("floatShadow_"),
				overlayMouseTarget: this.get("overlayMouseTarget_"),
				floatPane: this.get("floatPane_"),
				canvas: this.get("canvas_")
			}: null
		},
		draw: function() {},
		setMap: function(r) {
			this.set("map", r)
		},
		getMap: function() {
			return this.get("map")
		},
		getProjection: function() {
			return this.get("mapCanvasProjection_")
		},
		construct: function() {},
		destroy: function() {},
		map_changed: function() {
			var r = this.get("map"),
			o = ["originPosition_", "mapPane_", "overlayLayer_", "overlayShadow_", "canvas_", "overlayImage_", "floatShadow_", "overlayMouseTarget_", "floatPane_", "overlayRedraw_", "mapCanvasProjection_"];
			if (r) this.bindsTo(o, r);
			else {
				this.destroy();
				this.set("constructed_", false);
				this.unbinds(o)
			}
		},
		overlayRedraw__changed: function() {
			if (this.get("originPosition_")) {
				if (!this.get("constructed_")) {
					this.construct();
					this.set("constructed_", true)
				}
				this.draw()
			}
		}
	})
})(__MapNS__); (function(d) {
	var r = d.MVCObject.checkers,
	o = __MapNSImpl__.Interfaces,
	j = __MapNSImpl__.Util,
	p = __MapNSImpl__.Synchronize;
	d.Control = __MapNSImpl__.Class(d.MVCObject, {
		initialize: function(b) {
			d.MVCObject.apply(this);
			var g = {
				content: "",
				margin: new d.Size(0, 0),
				align: 0,
				visible: true,
				zIndex: 2
			};
			b = p.fill(b, g);
			g = b.map;
			b.map = null;
			this.setOptions(b);
			g && this.set("map", g)
		},
		controlContainer__changed: function() {
			var b = this.get("controlContainer_");
			if (b) {
				var g = document.createElement("div");
				g.style.cssText = "position:absolute;z-index:" +
				this.get("zIndex") + ";";
				b.appendChild(g);
				this.set("container_", g)
			}
		},
		changed: function(b) {
			if (b === "align" || b === "margin" || b === "style" || b === "viewWidth_" || b === "viewHeight_" || b === "content") this.redraw();
			if (b === "visible" && this.get("container_")) if (this.get(b)) this.get("container_").style.display = "";
			else this.get("container_").style.display = "none"
		},
		redraw: function() {
			var b = this.get("container_");
			j.setPositionByAlign(b, this.get("align"), this.get("margin"));
			this.draw && this.draw()
		},
		content_changed: function() {
			this.notify("container_")
		},
		container__changed: function() {
			var b = this.get("content"),
			g = this.get("container_");
			if (b && g) {
				if (typeof b === "string") g.innerHTML = b;
				else if (b.parentNode != g) {
					g.innerHTML = "";
					g.appendChild(b)
				}
				this.construct && this.construct();
				this.redraw()
			}
			g && this.notify("visible")
		},
		map_changed: function() {
			var b = this.get("map");
			if (b) {
				this.bindTo("controlContainer_", b, "mapContainer_");
				this.bindTo("viewWidth_", b);
				this.bindTo("viewHeight_", b)
			} else {
				b = this.get("controlContainer_");
				var g = this.get("container_");
				b && g && b.removeChild(g);
				this.destroy && this.destroy();
				this.unbind("controlContainer_");
				this.unbind("viewWidth_");
				this.unbind("viewHeight_")
			}
		}
	});
	r = {
		content: null,
		margin: r.checkInterface(o.Size),
		align: r.isNumber,
		zIndex: r.isNumber,
		visible: r.isBoolean,
		map: null
	};
	d.MVCObject.publicProperties(d.Control, r)
})(__MapNS__); (function(d) {
	var r = __MapNSImpl__.Util,
	o = __MapNSImpl__.Config,
	j = d.LatLng,
	p = d.LatLngBounds;
	d.BaseService = __MapNSImpl__.Class({
		initialize: function() {
			this.queryType = ""
		},
		send: function(b, g) {
			var k = o.SERVICE_URL;
			if (this.queryType === "geoc" || this.queryType === "rgeoc") k = k + this.queryType + "/?" + b.join("&") + "&output=jsonp&fr=webapi";
			else {
				b.push("qt=" + this.queryType);
				k = k + "?" + b.join("&") + "&output=jsonp&fr=webapi"
			}
			var l = d.ObjectLoader.get();
			d.Event.bind(l, "loaded", this,
			function() {
				return function(i) {
					this.onResult({
						object: i,
						request: g.request,
						callback: g.callback
					})
				}
			}.call(this));
			l.request(k)
		},
		fromProjectionToLatLng: function(b, g) {
			var k = r.latFromProjectionTo4326(g),
			l = r.lngFromProjectionTo4326(b);
			return new j(k, l)
		},
		fromSegmentToPath: function(b) {
			b = b.split(";");
			for (var g = [], k = new p, l = 0, i = b.length; l < i; l++) {
				var c = b[l].split(",");
				c = new j(c[1], c[0]);
				g.push(c);
				k.extend(c)
			}
			return {
				path: g,
				bounds: k
			}
		},
		fromDestToPoi: function(b) {
			var g = new d.Poi;
			g.id = b.uid;
			g.name = b.query || b.name;
			g.latLng = new j(b.pointy, b.pointx);
			g.type = b.poitype;
			g.address = b.addr || "";
			return g
		},
		fromDestToString: function(b) {
			var g = [];
			if (!checkers.isNull(b)) {
				checkers.isInstanceOf(j)(b) && (g.push(1), g.push(""), g.push(b.getLng() + "," + b.getLat()), g.push(""));
				checkers.isString(b) && (g.push(2), g.push(""), g.push(""), g.push(encodeURIComponent(b)));
				checkers.isInstanceOf(d.Poi)(b) && (g.push(1), g.push(""), g.push(b.latLng.getLng() + "," + b.latLng.getLat()), g.push(encodeURIComponent(b.name)))
			}
			return g.join("$$")
		},
		fromDistToString: function(b) {
			var g = "";
			if (b > 0 && b <= 10) g = b + __MapNSImpl__.RC.Units.m;
			else if (b > 10) g = b < 1E3 ? (b / 10).toFixed(1) * 10 + __MapNSImpl__.RC.Units.m: (b / 1E3).toFixed(1) + __MapNSImpl__.RC.Units.km;
			return g
		},
		fromTimeToString: function(b) {
			var g = "";
			if (b > 0 && b < 60) g = b + __MapNSImpl__.RC.Units.min;
			else if (b > 60) g = Math.floor(b / 60) + __MapNSImpl__.RC.Units.hour + this.fromTimeToString(b - Math.floor(b / 60) * 60);
			return g
		}
	});
	d.Poi = function() {};
	d.Distance = function() {
		this.text = "";
		this.value = 0
	};
	d.Duration = function() {
		this.text = "";
		this.value = 0
	};
	d.Turning = function() {
		this.text = "";
		this.latLng = null
	};
	d.PoiType = {
		NORMAL: 0,
		BUS_STATION: 1,
		SUBWAY_STATION: 2,
		BUS_LINE: 3,
		DISTRICT: 4,
		DOCK: "DOCK"
	}
})(__MapNS__); (function(d) {
	function r() {
		this.HEADER = document.getElementsByTagName("head")[0]
	}
	var o = __MapNSImpl__.Util,
	j = d.Event,
	p = __MapNSImpl__.Synchronize;
	r.prototype.request = function(b, g) {
		g = p.fill(g, {
			jsonp: true,
			objName: "OLR",
			charset: "gbk"
		});
		this.objName = g.objName;
		this.charset = g.charset;
		if (g.jsonp) {
			this.callback = "cb" + (new Date).getTime().toString(36);
			b += "&cb=soso.maps.ObjectLoader." + this.callback;
			var k = this;
			d.ObjectLoader[this.callback] = function(l) {
				window[k.callback] = l
			}
		}
		b += "&t=" + (new Date).getTime();
		if (!this.jsFile) {
			this.jsFile = document.createElement("script");
			this.jsFile.setAttribute("type", "text/javascript");
			this.jsFile.setAttribute("charset", this.charset);
			this.jsFile.src = b;
			this.HEADER.appendChild(this.jsFile);
			if (o.Browser().ie && o.Browser().ie < 9) {
				j.bindDom(this.jsFile, "readystatechange", this, o.callback(this, this.onReadyStateChange, [this.callback]));
				this.fixIeOnError(b)
			} else {
				j.bindDom(this.jsFile, "load", this, o.callback(this, this.onLoad, [this.callback]));
				j.bindDom(this.jsFile, "error", this, this.onError)
			}
			window.opera && this.fixOperaOnError(b)
		}
		this.running = true
	};
	r.prototype.onLoad = function(b) {
		var g;
		if (window[b]) g = window[b];
		if (window[this.objName]) g = window[this.objName];
		g ? j.trigger(this, "loaded", [g]) : j.trigger(this, "error", []);
		setTimeout(function() {
			try {
				window[b] && delete window[b];
				window[this.objName] && delete window[this.objName];
				delete d.ObjectLoader[b]
			} catch(k) {}
		},
		1E3);
		this.remove()
	};
	r.prototype.onError = function() {
		j.trigger(this, "error", []);
		this.remove()
	};
	r.prototype.onReadyStateChange = function(b) {
		this.jsFile && /loaded|complete/.test(this.jsFile.readyState) && this.onLoad(b)
	};
	r.get = function() {
		var b,
		g;
		if (!r.objects) r.objects = [];
		g = r.objects;
		for (var k = 0, l = g.length; k < l; k++) if (g[k].running === false) {
			b = g[k];
			j.clearListeners(b);
			g.splice(k, l - k);
			break
		}
		if (!b) {
			b = new r;
			r.objects.push(b);
			return b
		}
		b.running = true;
		return b
	};
	r.prototype.remove = function() {
		if (this.jsFile) {
			this.jsFile.removeAttribute("src");
			this.jsFile.parentNode.removeChild(this.jsFile);
			this.running = false;
			delete this.jsFile
		}
	};
	r.prototype.fixOperaOnError = function(b) {
		b = '<script src="' + b + '" ' + (document.dispatchEvent ? "onload": "onreadystatechange") + '="this.ownerDocument.z = 1"><\/script>';
		var g = document.createElement("iframe");
		g.style.display = "none";
		this.HEADER.appendChild(g);
		var k = g.contentDocument,
		l = this;
		g.onload = function() {
			k.z != 1 && l.onError();
			g.onload = null;
			this.HEADER.removeChild(this)
		};
		try {
			k.write(b);
			k.close()
		} catch(i) {}
	};
	r.prototype.fixIeOnError = function() {
		var b = this;
		setTimeout(function() {
			b.onError()
		},
		3E3)
	};
	d.ObjectLoader = r
})(__MapNS__); (function(d) {
	var r = __MapNSImpl__.Config,
	o = d.MVCObject,
	j = d.MVCView,
	p = d.Size,
	b = __MapNSImpl__.Synchronize;
	o.checkers.isTileGridLayer = function(g) {
		if (typeof g.getTileImageUrl === "function" || typeof g.getTileUrl === "function") return true;
		return false
	};
	d.TileLayer = __MapNSImpl__.Class(o, {
		initialize: function(g) {
			o.apply(this);
			var k = {
				//tileUrlTemplate: "http://{s}.map.soso.com/maptiles/{z}/{x}/{y}.png",
				//google 是Z/Y/X
				tileUrlTemplate: "maptiles/{z}/{y}/{x}",
				//http://mt1.google.cn/vt/lyrs=m@170000000&hl=zh-CN&gl=cn&x=213&y=104&z=8&s=Galileo
				//tileSubdomains: "p0,p1,p2,p3",
				tileSubdomains: "",
				copyrights: null,
				isPng: true,
				tileSize: new p(256, 256),
				opacity: 1,
				minZoom: r.zoomMin,
				maxZoom: r.zoomMax,
				errorUrl: r.domain + "imgs/404.png"
			};
			g = b.fill(g, k);
			this.setOptions(g);
			this.set("tileSize_", g.tileSize);
			g.map && this.setMap(g.map);
			j.loadView("TileLayer", this)
		},
		setMap: function(g) {
			this.set("map", g)
		},
		getMap: function() {
			return this.get("map")
		},
		getCopyright: function(g, k) {
			return this.get("copyrights_").getCopyrightNotice(g, k)
		},
		changed: function(g) {
			if (g == "map") if (g = this.get("map")) {
				var k = g.layers;
				this.set("map_", g);
				k.exists(this) || k.push(this);
				var l = k.getLength();
				l == 1 && this.set("isBase", true);
				if (this.get("isBase")) {
					for (var i = 0; i < l; i++) {
						var c = k.getAt(i);
						c != this && c.set("isBase", false)
					}
					g.set("baseLayer", this)
				}
			} else if (g = this.get("map_")) {
				k = g.layers;
				k.exists(this) && this.get("map_").layers.pop(this)
			}
		},
		//非ROADMAP　类型执行此方法
		getTileUrl: function(g, k) {
			var l = g.getX(),
			i = g.getY(),
			c = r.domain + "imgs/transparent.gif",
			e = this.get("tileUrlTemplate"),
			h = this.get("tileSubdomains");
			if (e) {
				c = e.replace(/\{x\}/, l);
				c = c.replace(/\{y\}/, i);
				c = c.replace(/\{z\}/, k+1)
			}
			if (h) {
				e = h.split(",");
				if (e.length) c = c.replace(/\{s\}/, e[Math.abs(l + i) % e.length])
			}
			return c
		}
	});
	d.RoadMapLayer = __MapNSImpl__.Class(d.TileLayer, {

		initialize: function(g) {
			g = b.fill(g, {
				isBase: true
			});
			d.TileLayer.apply(this, [g])
		},
		//SOSO 图源采用此方法
		getTileImageUrl: function(g, k, l) {
			var i = r.tileDomains,
			c = r.domain + "imgs/transparent.gif",
			e = r.scope,
			h = l * 4,
			a = e[h++],
			f = e[h++],
			m = e[h++];
			e = e[h];
			if (g >= a && g <= f && k >= m && k <= e) {
				c = i.length;
				k = Math.pow(2, l) - 1 - k;
				a = k.toString();
				c = a.charAt(a.length - 1) % c;
				strTileServer = i[c];
				c = [strTileServer, l, "/", Math.floor(g / 16), "/", Math.floor(k / 16), "/", g, "_", k, ".png"].join("")
			}
			return c
		}

/*
		initialize: function(g) {
			g = b.fill(g, {
				tileUrlTemplate: "http://{s}.google.com/kh/v=95&x={x}&y={y}&z={z}&s=G",
				tileSubdomains: "khm0,khm1"
			});
			d.TileLayer.apply(this, [g])
		}
*/
	});
	d.SatelliteLayer = __MapNSImpl__.Class(d.TileLayer, {
		initialize: function(g) {
			g = b.fill(g, {
				tileUrlTemplate: "http://{s}.google.com/kh/v=95&x={x}&y={y}&z={z}&s=G",
				tileSubdomains: "khm0,khm1"
			});
			d.TileLayer.apply(this, [g])
		}
	});
	d.HybridLayer = __MapNSImpl__.Class(d.TileLayer, {
		initialize: function(g) {
			g = b.fill(g, {
				//http://mt1.google.cn/vt/lyrs=m@170000000&hl=zh-CN&gl=cn&x=213&y=104&z=8&s=Galileo
				tileUrlTemplate: "http://{s}.google.com/vt/lyrs=h@162000000&hl=zh-CN&x={x}&y={y}&z={z}&s=Gali",
				tileSubdomains: "mt0,mt1"
			});
			d.TileLayer.apply(this, [g])
		}
	});
	d.TileGridLayer = d.TileLayer
})(__MapNS__); (function(d) {
	var r = __MapNSImpl__.Util,
	mconfig = __MapNSImpl__.Config,
	j = __MapNSImpl__.RC.MapType,
	p = __MapNSImpl__.Interfaces,
	b = d.MVCObject.checkers,
	g = b.checkInterface(p.LatLng),
	k = d.Event,
	l = d.MapType,
	i = d.MapTypeId,
	c = d.Size,
	e = d.Point,
	h = d.LatLng,
	a = d.LatLngBounds,
	f = d.MVCArray,
	m = d.MVCObject,
	v = d.MVCView,
	s = __MapNSImpl__.Synchronize,
	u = r.Browser().firefox ? "-moz-grab": mconfig.domain + "imgs/grab.cur",
	y = r.Browser().firefox ? "-moz-grabbing": mconfig.domain + "imgs/grabbing.cur",
	t = __MapNSImpl__.Class(m, {
		initialize: function(n, q) {
			if (r.Browser().ie) {
				k.addDomListener(window, "unload", k.unload);
				try {
					document.execCommand("BackgroundImageCache", false, true)
				} catch(w) {}
			}
			m.apply(this);
			this.set("container_", n);
			var x = {
				__hideLogo__: true, //隐藏logo
				center: new h(mconfig.DEFAULT_LAT, mconfig.DEFAULT_LNG),
				zoomLevel: mconfig.DEFAULT_ZOOMLEVEL,
				maxZoom: mconfig.zoomMax,
				minZoom: mconfig.zoomMin,
				backgroundColor: "",
				cursor: u,
				draggingCursor: y,
				draggable: true,
				scrollWheel: true,
				zoomInByDblClick: true,
				keyBoard: true,
				animation: true,
				mapTypeId: i.ROADMAP,
				boundary: null,
				autoPan: false
			};  //地图Option
			q = s.fill(q, x);
			this.setOptions(q);
			x = Math.max(q.zoomLevel, q.minZoom);
			x = Math.min(x, q.maxZoom);
			this.set("zoomLevel", x);
			x = new d.MapCanvasProjection;
			x.bindTo("center", this, "center");
			x.bindTo("width", this, "viewWidth_");
			x.bindTo("height", this, "viewHeight_");
			x.bindTo("zoom", this, "zoomLevel");
			x.bindTo("origin", this, "origin_");
			x.bindTo("mapType", this);
			x.bindTo("map", this);
			this.set("mapCanvasProjection_", x);
			this.layers = new f;
			this.controls = new f;
			var z = this;
			k.addListener(z.layers, "remove_at",
			function(C, E) {
				E && E.get("map") && E.set("map", null);
				if (z.layers.getLength() == 1) {
					var F = z.layers.getAt(0);
					F.set("isBase", true);
					F.set("map", z)
				}
			});
			k.addListener(this.layers, "insert_at",
			function(C, E) {
				E.get("map") || E.set("map", z)
			});
			this.fromPixelToLngLat = this.fromPixelToLatLng;
			this.fromLngLatToPixel = this.fromLatLngToPixel;
			this.fromContainerPixelToLngLat = this.fromContainerPixelToLatLng;
			this.fromLngLatToContainerPixel = this.fromLatLngToContainerPixel;
			this.set("draggable", q.draggable);
			this.notifyResize();
			v.loadView("Map", this);
			var D = new a(new h( - 90, -180), new h(90, 180)),
			B = new d.CopyrightCollection("");
			B.addCopyright(new d.Copyright("", D, 1));
			D = new d.RoadMapLayer({
				copyrights: B
			});
			B = new d.SatelliteLayer({
				copyrights: B
			});
			var A = new d.HybridLayer;
			this.ROADMAP = new l({
				name: "roadmap",
				alt: j.ROADMAP,
				tileLayers: [D]
			});
			this.SATELLITE = new l({
				name: "satellite",
				alt: j.SATELLITE,
				tileLayers: [B, A]
			});
			D = this[this.get("mapTypeId")];
			this.set("projection", D.projection);
			this.set("mapType", D);
			B = 0;
			for (A = D.tileLayers.length; B < A; B++) D.tileLayers[B].set("map", this);
			x.bindTo("projection", this)
		},
		mapTypeId_changed: function() {
			var n = this.get("mapType"),
			q = this[this.get("mapTypeId")];
			if (! (q && q == n)) {
				if (n) for (var w = 0, x = n.tileLayers.length; w < x; w++) {
					var z = n.tileLayers[w];
					z.set("map", null)
				}
				if (q) {
					this.set("projection", q.projection);
					this.set("mapType", q);
					w = 0;
					for (x = q.tileLayers.length; w < x; w++) {
						z = q.tileLayers[w];
						z.set("map", this)
					}
				}
			}
		},
		getViewSize: function() {
			return new c(this.get("viewWidth_"), this.get("viewHeight_"))
		},
		getBounds: function() {
			if (this.get("projection")) {
				var n = this.get("viewWidth_"),
				q = this.get("viewHeight_");
				q = this.fromContainerPixelToLatLng(new e(0, q));
				n = this.fromContainerPixelToLatLng(new e(n, 0));
				return new a(q, n)
			}
		},
		moveTo: function(n, q) {
			if (g(n)) {
				var w = this.get("boundary");
				if (w) {
					var x = this.get("projection"),
					z = this.get("mapCanvasProjection_"),
					D = this.get("zoomLevel"),
					B = this.get("viewWidth_"),
					A = this.get("viewHeight_"),
					C = x.fromLatLngToPoint(w.getNorthEast());
					w = x.fromLatLngToPoint(w.getSouthWest());
					var E = x.fromLatLngToPoint(n),
					F = z.getResolution(D).x;
					D = z.getResolution(D).y;
					z = E.getX();
					E = E.getY();
					var G = C.getX() - B / 2 * F;
					C = C.getY() - A / 2 * D;
					B = w.getX() + B / 2 * F;
					A = w.getY() + A / 2 * D;
					if (z > G && E > C && z < B && E < A) return;
					n = x.fromPointToLatLng(new e(Math.max(Math.min(z, G), B), Math.max(Math.min(E, C), A)))
				}
				x = this.get("center");
				A = true;
				if (!x || !this.get("animation") || q || !v.getView("Map")) A = false;
				else {
					A = this.fromLatLngToContainerPixel(x);
					B = this.fromLatLngToContainerPixel(n);
					x = A.getX() - B.getX();
					A = A.getY() - B.getY();
					A = !(Math.abs(x) > this.get("viewWidth_") || Math.abs(A) > this.get("viewHeight_"))
				}
				if (A) this.set("animation_", {
					type: "move",
					center: n
				});
				else {
					this.set("animation_", null);
					this.set("center", n)
				}
			} else throw Error("Map center should be a LatLng.");
		},
		moveBy: function(n, q) {
			if (b.checkInterface(p.Size)(n)) {
				if (n.getWidth() !== 0 || n.getHeight() !== 0) {
					var w = this.get("center");
					w = this.fromLatLngToContainerPixel(w);
					var x = w.getX() - n.getWidth();
					w = w.getY() - n.getHeight();
					this.moveTo(this.fromContainerPixelToLatLng(new e(x, w)), q)
				}
			} else throw Error("Move by new a Size type offset.");
		},
		fitBounds: function(n, q, w) {
			q = q || {
				left: 0,
				right: 0,
				top: 0,
				bottom: 0
			};
			var x = parseInt(q.left) || 0 + parseInt(q.right) || 0;
			q = parseInt(q.top) || 0 + parseInt(q.bottom) || 0;
			x = this.get("viewWidth_") - x;
			q = this.get("viewHeight_") - q;
			var z = this.get("mapCanvasProjection_");
			x = r.getOptimalZoomLevel(n, x, q, z);
			this.set("center", n.getCenter());
			this.set("zoomLevel", w || x)
		},
		getFitBoundsZoomLevel: function(n, q) {
			var w = this.get("viewWidth_"),
			x = this.get("viewHeight_"),
			z = this.get("mapCanvasProjection_");
			if (q) {
				w = q.getWidth();
				x = q.getHeight()
			}
			return r.getOptimalZoomLevel(n, w, x, z)
		},
		zoomIn: function(n, q, w) {
			this.zoomTo(this.getZoomLevel() + 1, n, q, w)
		},
		zoomOut: function(n, q, w) {
			this.zoomTo(this.getZoomLevel() -
			1, n, q, w)
		},
		zoomTo: function(n, q, w, x) {
			n = Math.round(n);
			n = Math.max(n, this.get("minZoom"));
			n = Math.min(n, this.get("maxZoom"));
			if (n !== this.get("zoomLevel")) {
				var z = n - this.getZoomLevel();
				x = this.get("animation") || !x;
				var D = this.fromLatLngToContainerPixel(q || this.get("center")),
				B = this.getBaseLayer();
				B.get("baseLeft_");
				B.get("baseTop_");
				var A = this.get("zoomAnimation_");
				if ((!A || A.getStatus() !== 1) && x) B.notify("exchange_");
				B = D.getX();
				A = D.getY();
				if (B < 0 || B > this.get("viewWidth_") || A < 0 || A > this.get("viewHeight_")) x = false;
				B = this.get("originPosition_");
				this.set("zoomLevel", n);
				if (q) {
					if (w) {
						q = this.fromLatLngToContainerPixel(q);
						A = this.getCenter();
						A = this.fromLatLngToContainerPixel(A);
						n = A.getX() + q.getX() - D.getX();
						q = A.getY() + q.getY() - D.getY();
						q = this.fromContainerPixelToLatLng(new e(n, q))
					}
					this.set("center", q)
				}
				x && B || v.getView("Map") ? this.set("animation_", {
					type: "zoom",
					delta: z,
					focusPixel: D,
					originPosition: B,
					isInPlace: w
				}) : this.set("animation_", null)
			}
		},
		notifyResize: function() {
			var n = this.get("container_"),
			q = n.offsetWidth,
			w = n.offsetHeight,
			x = this.get("center") && this.get("viewWidth_") && this.get("viewHeight_");
			n = null;
			if (x) {
				var z = this.fromContainerPixelToLatLng(new e(q / 2, w / 2));
				n = [q - this.get("viewWidth_"), w - this.get("viewHeight_")]
			}
			this.set("viewWidth_", q);
			this.set("viewHeight_", w);
			if (x) {
				if (q = this.get("origin_")) {
					q = this.fromLatLngToContainerPixel(q);
					this.get("originPosition_");
					q = new e(q.getX() + n[0] / 2, q.getY() + n[1] / 2);
					this.set("origin_", this.fromContainerPixelToLatLng(q))
				}
				this.set("center", z)
			}
		},
		getDistance: function(n, q) {
			if (!g(n) || !g(q)) throw Error("Start and end must be __MapNS__.LatLng object.");
			var w = function(D) {
				return Math.PI / 180 * D
			},
			x = w(n.getLat() - q.getLat()),
			z = w(n.getLng() - q.getLng());
			w = Math.sin(x / 2) * Math.sin(x / 2) + Math.cos(w(q.getLat())) * Math.cos(w(n.getLat())) * Math.sin(z / 2) * Math.sin(z / 2);
			return 6378137 * 2 * Math.asin(Math.min(1, Math.sqrt(w)))
		},
		fromLatLngToPixel: function(n) {
			return this.get("mapCanvasProjection_").fromLatLngToDivPixel(n)
		},
		fromPixelToLatLng: function(n) {
			return this.get("mapCanvasProjection_").fromDivPixelToLatLng(n)
		},
		fromLatLngToContainerPixel: function(n) {
			return this.get("mapCanvasProjection_").fromLatLngToContainerPixel(n)
		},
		fromContainerPixelToLatLng: function(n) {
			return this.get("mapCanvasProjection_").fromContainerPixelToLatLng(n)
		},
		getCenter: function() {
			return this.get("center")
		},
		setCenter: function(n) {
			this.moveTo(n, true)
		},
		getZoomLevel: function() {
			return this.get("zoomLevel")
		},
		setZoomLevel: function(n) {
			this.zoomTo(n, null, null, true)
		},
		getBaseLayer: function() {
			return this.get("baseLayer")
		}
	});
	m.publicProperties(t, {
		backgroundColor: b.isString,
		cursor: b.isString,
		draggingCursor: b.isString,
		scrollWheel: b.isBoolean,
		zoomInByDblClick: b.isBoolean,
		keyBoard: b.isBoolean,
		animation: b.isBoolean,
		draggable: b.isBoolean,
		autoPan: b.isBoolean,
		mapTypeId: b.isString
	});
	d.Map = t
})(__MapNS__); (function(d) {
	var r = d.MVCObject.checkers,
	o = __MapNSImpl__.Config,
	j = __MapNSImpl__.Synchronize,
	p = d.MVCView,
	b = d.Overlay;
	d.InfoWindow = __MapNSImpl__.Class(b, {
		initialize: function(g) {
			b.apply(this);
			g = j.fill(g, {
				visible: true,
				content: "",
				style: {
					url: o.domain + "imgs/infowindow_default.png",
					top_left: [0, 0, 10, 10],
					bottom_right: [600, 430, 10, 10],
					margin: [20, 10],
					width_range: [200, 600],
					height_range: [80, 500],
					offset: [ - 3, 0],
					stem: {
						x: [65, 100, 43],
						y: 484,
						align: d.ALIGN.CENTER,
						offset: 0
					},
					close: {
						url: o.domain + "imgs/infowindow_close_default.png",
						coordinate: [0, 0, 13, 13],
						align: d.ALIGN.TOP_RIGHT,
						margin: [8, 8]
					},
					shadow: {
						url: o.domain + "imgs/infowindow_shadow_default.png",
						top_left: [343, 142],
						bottom_right: [641, 452],
						stem: {
							x: [97, 127, 44],
							y: 484,
							offset: [ - 6, -2],
							blur: 5
						}
					}
				},
				zIndex: 0,
				position: null,
				target: null,
				autoMove: true
			});
			this.setOptions(g);
			p.loadView("InfoWindow", this)
		},
		open: function(g, k) {
			this.set("position", k);
			this.set("content", g);
			this.set("visible", true)
		},
		close: function() {
			this.set("visible", false)
		},
		reset: function() {
			this.notify("content")
		},
		construct: function() {
			this.set("panes_", this.getPanes())
		},
		destroy: function() {
			this.notify("destroy_");
			this.set("panes_", null)
		},
		draw: function() {
			this.notify("draw_")
		}
	});
	d.MVCObject.publicProperties(d.InfoWindow, {
		visible: r.isBoolean,
		content: null,
		zIndex: r.isNumber,
		autoMove: r.isBoolean,
		style: null,
		position: null,
		target: null,
		animation: null
	});
	d.InfoWindow.Animation = {
		POP: 0
	}
})(__MapNS__); (function() {
	var d = __MapNS__.MVCObject.checkers,
	r = __MapNS__.Overlay,
	o = __MapNS__.MVCView,
	j = __MapNS__.MVCObject,
	p = __MapNSImpl__.Synchronize;
	__MapNS__.Polyline = __MapNSImpl__.Class(r, {
		initialize: function(b) {
			r.apply(this);
			b = p.fill(b, {
				path: [],
				strokeColor: "#1791fc",
				strokeWeight: 4,
				fillOpacity: 0.2,
				strokeOpacity: 0.8,
				strokeDashStyle: "solid",
				editable: false,
				hasLabel: false,
				zIndex: 0,
				cursor: "",
				visible: true
			});
			this.setValues(b);
			o.loadView("Polygon", this)
		},
		construct: function() {
			this.set("panes_", this.getPanes());
			this.notify("construct_")
		},
		draw: function() {
			this.notify("draw_")
		},
		destroy: function() {
			this.notify("destroy_");
			this.set("panes_", null)
		},
		getBounds: function() {
			var b = this.get("path"),
			g = null;
			if (b && b.length !== 0) {
				var k = b[0].getLng(),
				l = b[0].getLat();
				g = k;
				for (var i = l, c = 1, e = b.length; c < e; c++) {
					var h = b[c].getLng(),
					a = b[c].getLat();
					h > g && (g = h);
					a > i && (i = a);
					h < k && (k = h);
					a < l && (l = a)
				}
				b = new __MapNS__.LatLng(l, k);
				g = new __MapNS__.LatLng(i, g);
				g = new __MapNS__.LatLngBounds(b, g)
			}
			return g
		}
	});
	j.publicProperties(__MapNS__.Polyline, {
		path: null,
		strokeColor: d.isString,
		strokeWeight: d.isNumber,
		strokeOpacity: d.isNumber,
		strokeDashStyle: d.isString,
		cursor: d.isString,
		editable: d.isBoolean,
		hasLabel: d.isBoolean,
		zIndex: d.isNumber,
		visible: d.isBoolean
	})
})(); (function(d) {
	var r = d.MVCObject.checkers,
	o = d.Polyline,
	j = d.MVCObject,
	p = __MapNSImpl__.Synchronize;
	d.Polygon = __MapNSImpl__.Class(o, {
		initialize: function(b) {
			b = p.fill(b, {
				strokeWeight: 2
			});
			o.apply(this, [b]);
			this.set("fillColor", b.fillColor || "#1791fc")
		}
	});
	j.publicProperties(d.Polygon, {
		path: null,
		strokeColor: r.isString,
		strokeWeight: r.isNumber,
		strokeOpacity: r.isNumber,
		strokeDashStyle: r.isString,
		cursor: r.isString,
		zIndex: r.isNumber,
		visible: r.isBoolean,
		fillColor: r.isString,
		fillOpacity: r.isNumber,
		editable: r.isBoolean
	})
})(__MapNS__); (function(d) {
	var r = d.MVCObject.checkers,
	o = __MapNSImpl__.Interfaces,
	j = d.MVCView,
	p = d.MVCObject,
	b = d.Overlay;
	o = __MapNSImpl__.Interfaces;
	var g = __MapNSImpl__.Synchronize;
	d.Circle = __MapNSImpl__.Class(b, {
		initialize: function(k) {
			b.apply(this);
			var l = {
				center: new d.LatLng(0, 0),
				radius: 0,
				strokeColor: "#1791fc",
				strokeWeight: 1,
				fillColor: "#1791fc",
				fillOpacity: 0.2,
				strokeOpacity: 0.8,
				strokeDashStyle: "solid",
				zIndex: 0,
				cursor: "",
				visible: true
			};
			k = g.fill(k, l);
			this.setValues(k);
			j.loadView("Circle", this)
		},
		construct: function() {
			this.set("panes_", this.getPanes());
			this.notify("construct_")
		},
		draw: function() {
			this.notify("draw_")
		},
		destroy: function() {
			this.notify("destroy_");
			this.set("panes_", null)
		},
		getBounds: function() {
			var k = this.get("center"),
			l = this.get("radius"),
			i = null,
			c = k.getLat();
			if (k) if (l <= 0) i = new d.LatLngBounds(new d.LatLng(k.getLat(), k.getLng()), new d.LatLng(k.getLat(), k.getLng()));
			else {
				var e = l / 6378137,
				h = e * 180 / Math.PI;
				l = c + h;
				i = c - h;
				h = Math.asin(e / 2 / Math.cos(c * Math.PI / 180)) * 360 / Math.PI;
				c = k.getLng() + h;
				k = k.getLng() - h;
				i = new d.LatLngBounds(new d.LatLng(i, k), new d.LatLng(l, c))
			}
			return i
		}
	});
	r = {
		center: p.union(r.checkInterface(o.LatLng), r.isNull),
		radius: r.isNumber,
		strokeColor: r.isString,
		fillColor: r.isString,
		strokeWeight: r.isNumber,
		strokeOpacity: r.isNumber,
		fillOpacity: r.isNumber,
		strokeDashStyle: r.isString,
		cursor: r.isString,
		zIndex: r.isNumber,
		visible: r.isBoolean
	};
	p.publicProperties(d.Circle, r)
})(__MapNS__); (function(d) {
	var r = __MapNSImpl__.Util,
	o = d.Point,
	j = d.Size,
	p = d.MVCObject,
	b = __MapNSImpl__.Interfaces,
	g = d.MVCObject.checkers;
	d.MarkerImage = __MapNSImpl__.Class(p, {
		initialize: function(i, c, e, h, a) {
			p.apply(this);
			this.set("origin_", h || new o(0, 0));
			this.set("size_", c || a);
			this.set("anchor_", e);
			this.set("scaledSize_", a);
			this.set("url_", i);
			new l(this)
		}
	});
	var k = p.union(g.checkInterface(b.Size), g.isNull);
	b = p.union(g.checkInterface(b.Point), g.isNull);
	d.MVCObject.publicProperties(d.MarkerImage, {
		url_: g.isString,
		size_: k,
		origin_: b,
		anchor_: b,
		scaledSize_: k
	});
	var l = __MapNSImpl__.Class(p, {
		initialize: function(i) {
			p.apply(this);
			this.bindsTo(["origin_", "size_", "anchor_", "scaledSize_", "ready_", "url_"], i)
		},
		url__changed: function() {
			var i = this,
			c = this.get("url_");
			r.fetchImage(c,
			function(e, h) {
				if (e !== 0 && h !== 0) {
					i.get("scaledSize_") || i.set("scaledSize_", new j(e, h));
					i.get("size_") || i.set("size_", i.get("scaledSize_"));
					i.set("ready_", true)
				}
			})
		}
	});
	d.MarkerShape = __MapNSImpl__.Class(p, {
		initialize: function(i, c) {
			p.apply(this);
			this.set("coord_", i);
			this.set("type_", c)
		}
	})
})(__MapNS__); (function(d) {
	var r = d.MVCObject.checkers,
	o = __MapNSImpl__.Interfaces,
	j = __MapNSImpl__.Util,
	p = d.Size,
	b = d.ALIGN,
	g = d.MVCObject,
	k = __MapNSImpl__.Synchronize;
	d.MarkerDecoration = __MapNSImpl__.Class(g, {
		initialize: function(c) {
			g.apply(this);
			var e = {
				align: b.CENTER,
				zIndex: 0,
				margin: new p(0, 0)
			};
			c = k.fill(c, e);
			this.setOptions(c);
			new i(this)
		}
	});
	var l = g.union(r.isString, r.isNull, r.isNumber, r.isHtmlControl);
	g.publicProperties(d.MarkerDecoration, {
		content: l,
		margin: r.checkInterface(o.Size),
		marker: null
	});
	var i = __MapNSImpl__.Class(d.MVCObject, {
		initialize: function(c) {
			g.apply(this);
			this.set("model_", c);
			this.bindsTo(["content", "align", "margin", "marker"], c)
		},
		iconDom__changed: function() {
			var c = this.get("container_"),
			e = this.get("iconDom_");
			if (c && c.parentNode) try {
				c.parentNode.removeChild(c)
			} catch(h) {}
			if (!c) {
				c = document.createElement("div");
				c.style.cssText = "position:absolute;";
				this.set("container_", c)
			}
			if (e) {
				e.appendChild(c);
				this.notify("content");
				this.draw()
			}
		},
		marker_changed: function() {
			var c = this.get("marker");
			this.unbind("iconDom_");
			c ? this.bindTo("iconDom_", c) : this.notify("iconDom_")
		},
		content_changed: function() {
			var c = this.get("container_"),
			e = this.get("content");
			if (c && e) {
				c.innerHTML = "";
				if (j.isHtmlControl(e)) c.appendChild(e);
				else c.innerHTML = e;
				this.draw()
			}
		},
		changed: function(c) {
			if (c === "align" || c === "margin") this.draw()
		},
		draw: function() {
			this.get("container_") && j.setPositionByAlign(this.get("container_"), this.get("align"), this.get("margin"), this.get("iconDom_"))
		}
	})
})(__MapNS__); (function(d) {
	function r() {
		return m = m || new e(p.domain + "imgs/marker.png", new g(22, 34), new k(11, 34), new k(0, 0))
	}
	function o() {
		return v = v || new e(p.domain + "imgs/marker.png", new g(51, 39), new k(18, 34), new k(22, 0))
	}
	function j() {
		return s = s || new d.MarkerShape([7, 0, 13, 0, 17, 1, 22, 7, 22, 8, 22, 14, 21, 16, 20, 18, 18, 19, 15, 22, 13, 25, 12, 26, 12, 28, 11, 31, 11, 33, 10, 33, 10, 29, 9, 28, 9, 26, 8, 25, 0, 15, 0, 7], "poly")
	}
	var p = __MapNSImpl__.Config,
	b = d.Overlay,
	g = d.Size,
	k = d.Point,
	l = d.MVCObject,
	i = d.MVCView,
	c = d.Animation,
	e = d.MarkerImage,
	h = __MapNSImpl__.Interfaces,
	a = d.MVCObject.checkers,
	f = __MapNSImpl__.Synchronize,
	m = null,
	v = null,
	s = null,
	u = null;
	d.Marker = __MapNSImpl__.Class(b, {
		initialize: function(q) {
			b.apply(this);
			q = f.fill(q, {
				icon: null,
				shadow: null,
				shape: null,
				dragTargetIcon: null,
				title: "",
				cursor: "pointer",
				clickable: true,
				visible: true,
				zIndex: 0,
				flat: false,
				dragAnimation: 1,
				altitude_: 0,
				draggable: false
			}); ! q.icon && (q.icon = r(), q.shadow && (q.shadow = o()), q.shape = j());
			var w = q,
			x;
			if (! (x = q.dragTargetIcon)) x = u = u || new e(p.domain + "imgs/target.png", new g(16, 16), new k(8, 8), new k(0, 0));
			w.dragTargetIcon = x;
			this.setOptions(q);
			i.loadView("Marker", this)
		},
		construct: function() {
			this.set("panes_", this.getPanes())
		},
		destroy: function() {
			if (this.get("panes_")) {
				this.set("dragEvent_", false);
				this.notify("destroy_");
				this.set("panes_", null)
			}
		},
		draw: function() {
			this.notify("draw_")
		},
		startDrag: function(q) {
			this.set("dragEvent_", q)
		},
		stopDrag: function() {
			this.set("dragEvent_", false)
		},
		changed: function(q) {
			var w = this.get(q);
			switch (q) {
			case "animation":
				this.set("animationType_", w);
				break;
			case "shadow":
			case "icon":
			case "dragTargetIcon":
				if (q === "icon") if (this.get("icon") === null) {
					this.unbind(q + "Ready_");
					this.set("icon", r());
					this.set("shadow", o());
					break
				} else if (this.get("icon") !== r()) {
					this.get("shadow") === o() && this.set("shadow", null);
					this.get("shape") === j() && this.set("shape", null)
				}
				this.unbind(q + "Ready_");
				w && this.bindTo(q + "Ready_", w, "ready_")
			}
		}
	});
	d.Marker.Animation = {
		BOUNCE: 0,
		DROP: 1,
		JUMP: 2,
		STICK: 3
	};
	var y = l.union(function(q) {
		return q === c.BOUNCE || q === c.DROP || q === c.JUMP || q === c.STICK
	},
	a.isNull),
	t = l.union(a.isInstanceOf(e), a.isNull),
	n = l.union(a.isInstanceOf(d.MarkerShape), a.isNull);
	h = {
		position: l.union(a.checkInterface(h.LatLng), a.isNull),
		icon: t,
		title: a.isStringOrNull,
		shadow: t,
		dragTargetIcon: t,
		shape: n,
		cursor: a.isString,
		zIndex: a.isNumber,
		visible: a.isBoolean,
		clickable: a.isBoolean,
		flat: a.isBoolean,
		draggable: a.isBoolean,
		animation: y
	};
	l.publicProperties(d.Marker, h)
})(__MapNS__); (function(d) {
	var r = d.Point,
	o = __MapNSImpl__.Interfaces,
	j = d.MVCObject,
	p = d.MVCView,
	b = j.checkers,
	g = d.Overlay,
	k = __MapNSImpl__.Synchronize;
	d.Label = __MapNSImpl__.Class(g, {
		initialize: function(e) {
			g.apply(this);
			var h = {
				offset: new r(0, 0),
				style: {
					border: "solid 1px #999",
					fontSize: "12px",
					padding: "2px",
					backgroundColor: "#fff"
				},
				visible: true,
				opacity: 1,
				content: ""
			};
			e = k.fill(e, h);
			this.setOptions(e);
			p.loadView("Label", this)
		},
		construct: function() {
			this.set("panes_", this.getPanes())
		},
		destroy: function() {
			this.notify("destroy_");
			this.set("panes_", null)
		},
		draw: function() {
			this.notify("draw_")
		},
		changed: function(e) {
			this.get(e)
		}
	});
	var l = j.union(b.checkInterface(o.LatLng), b.isInstanceOf(d.Marker), b.isNull),
	i = j.union(b.isString, b.isNull, b.isNumber, b.isHtmlControl),
	c = j.union(b.isObject, b.isString, b.isNull);
	o = j.union(b.checkInterface(o.Point), b.checkInterface(o.Size), b.isNull);
	j.publicProperties(d.Label, {
		style: c,
		position: l,
		offset: o,
		content: i,
		zIndex: b.isNumberOrNull,
		opacity: b.isNumber,
		visible: b.isBoolean
	})
})(__MapNS__); (function(d) {
	var r = d.LatLngBounds,
	o = d.MVCObject.checkers,
	j = __MapNSImpl__.Synchronize,
	p = d.MVCView,
	b = d.Overlay;
	d.GroundOverlay = __MapNSImpl__.Class(b, {
		initialize: function(g) {
			b.apply(this);
			g = j.fill(g, {
				visible: true,
				opacity: 1
			});
			this.setOptions(g);
			p.loadView("GroundOverlay", this)
		},
		construct: function() {
			this.set("panes_", this.getPanes())
		},
		destroy: function() {
			this.notify("destroy_");
			this.set("panes_", null)
		},
		draw: function() {
			this.notify("draw_")
		}
	});
	r = d.MVCObject.union(o.checkInterface(r), o.isNull);
	d.MVCObject.publicProperties(d.GroundOverlay, {
		zIndex: o.isNumberOrNull,
		opacity: o.isNumber,
		visible: o.isBoolean,
		imageUrl: o.isStringOrNull,
		bounds: r
	})
})(__MapNS__); (function(d) {
	function r(t, n, q) {
		var w = document.createElement("div");
		w.style.cssText = "position:absolute;overflow:hidden;z-index:1;opacity:0.01;filter:alpha(opacity=1);";
		var x = document.createElement("img");
		x.style.cssText = "positiion:absolute;-moz-user-select:none;-khtml-user-select:none;border:none;";
		x.unselectable = "on";
		var z = function(D) {
			g.stopEvent(D);
			return false
		};
		g.addDomListener(x, "selectstart", z);
		g.addDomListener(x, "dragstart", z);
		w.appendChild(x);
		x.src = n;
		n = "qimgmapnavcontrol" + a++;
		z = null;
		x.setAttribute("usemap", "#" + n);
		b.setSize(x, q);
		b.setSize(w, q);
		if (b.Browser().ie && b.Browser().ie < 9) {
			x.setAttribute("useMap", "#" + n);
			z = document.createElement('<map name="' + n + '"></map>')
		} else {
			z = document.createElement("map");
			z.setAttribute("name", n)
		}
		w.appendChild(z);
		t.appendChild(w);
		return z
	}
	function o(t, n, q) {
		var w = document.createElement("area");
		w.href = "javascript:void(0)";
		t.appendChild(w);
		w.setAttribute("shape", "poly");
		w.setAttribute("coords", n.join(","));
		w.style.cursor = "pointer";
		q && (w.title = q);
		return w
	}
	function j(t, n, q, w) {
		var x = function(z) {
			return function() {
				c(t, q, w[z])
			}
		};
		g.addDomListener(n, "mouseover", x(1));
		g.addDomListener(n, "mouseout", x(0));
		g.addDomListener(n, "mouseup", x(1));
		g.addDomListener(n, "mousedown", x(2))
	}
	function p(t, n, q) {
		var w = q.get("map"),
		x = w.get("minZoom");
		w = w.get("maxZoom");
		var z = {
			17: "street",
			11: "city",
			8: "prov",
			4: "country"
		},
		D;
		for (D in z) {
			var B = parseInt(D);
			if (B >= x && B <= w) {
				var A = f[z[D]][n],
				C = document.createElement("div");
				C.style.position = "absolute";
				b.setSize(C, [A[2], A[3]]);
				c(C, f.url, A);
				n === "right" ? C.style.left = "34px": C.style.left = "-16px";
				C.style.bottom = 9 * (B - x) - 3 + "px";
				t.appendChild(C);
				g.addDomListener(C, "click",
				function(E) {
					return function() {
						q.get("map").zoomTo(E)
					}
				} (D))
			}
		}
	}
	var b = __MapNSImpl__.Util,
	g = d.Event,
	k = __MapNSImpl__.Interfaces,
	l = d.MVCObject.checkers,
	i = __MapNSImpl__.Synchronize,
	c = b.setCssSprite,
	e = b.setDomCoord,
	h = __MapNSImpl__.RC.NavControl,
	a = 0,
	f = {
		url: __MapNSImpl__.Config.domain + "imgs/ctrl.png",
		background: [0, 121, 44, 44],
		downhover: [44, 121, 44, 44],
		lefthover: [88, 121, 44, 44],
		uphover: [132, 121, 44, 44],
		righthover: [176, 121, 44, 44],
		downpress: [220, 121, 44, 44],
		leftpress: [264, 121, 44, 44],
		uppress: [308, 121, 44, 44],
		rightpress: [352, 121, 44, 44],
		inback: [0, 69, 23, 25],
		outback: [0, 95, 23, 25],
		inhover: [26, 69, 23, 25],
		outhover: [26, 95, 23, 25],
		inpress: [53, 69, 23, 25],
		outpress: [53, 95, 23, 25],
		slideback: [170, 76, 24, 13],
		slidehover: [170, 92, 24, 13],
		slidepress: [170, 107, 24, 13],
		ruler: [84, 0, 8, 9],
		heat: [92, 0, 8, 9],
		street: {
			right: [139, 53, 26, 16],
			left: [112, 53, 26, 16]
		},
		city: {
			right: [139, 70, 26, 16],
			left: [112, 70, 26, 16]
		},
		prov: {
			right: [139, 87, 26, 16],
			left: [112, 87, 26, 16]
		},
		country: {
			right: [139, 104, 26, 16],
			left: [112, 104, 26, 16]
		}
	},
	m = [22, 22, 8, 8, 2, 16, 0, 26, 2, 36, 8, 44],
	v = [22, 22, 44, 8, 50, 16, 52, 26, 50, 36, 44, 44],
	s = [22, 22, 8, 8, 16, 2, 26, 0, 36, 2, 44, 8],
	u = [22, 22, 8, 44, 16, 50, 26, 52, 36, 50, 44, 44],
	y = __MapNSImpl__.Class(d.Control, {
		initialize: function(t) {
			t = i.fill(t, {
				style: d.NavigationControlStyle.DEFAULT
			});
			d.Control.apply(this, [t]);
			this.set("content", this.getControlDom())
		},
		getControlDom: function() {
			var t = this.get("map");
			if (t) {
				var n = t.get("minZoom"),
				q = t.get("maxZoom");
				t = document.createElement("div");
				var w = document.createElement("div");
				w.style.cssText = "position:relative;margin-bottom:10px;";
				var x = document.createElement("div");
				x.style.cssText = "position:absolute;z-index:0;";
				var z = f.background;
				b.setSize(w, [z[2], z[3]]);
				b.setSize(x, [z[2], z[3]]);
				c(x, f.url, z);
				w.appendChild(x);
				t.appendChild(w);
				var D = r(w, f.url, [z[2], z[3]]);
				w = o(D, m, h.left);
				j(x, w, f.url, [f.background, f.lefthover, f.leftpress]);
				z = o(D, v, h.right);
				j(x, z, f.url, [f.background, f.righthover, f.rightpress]);
				var B = o(D, s, h.up);
				j(x, B, f.url, [f.background, f.uphover, f.uppress]);
				D = o(D, u, h.down);
				j(x, D, f.url, [f.background, f.downhover, f.downpress]);
				x = document.createElement("div");
				x.style.cssText = "position:relative;margin-left:11px;cursor:pointer;";
				x.title = h.zoomIn;
				var A = document.createElement("div");
				A.style.cssText = "position:relative;margin-left:11px;cursor:pointer;";
				A.title = h.zoomOut;
				var C = f.inback,
				E = f.outback;
				b.setSize(x, [C[2], C[3]]);
				b.setSize(A, [E[2], E[3]]);
				c(x, f.url, C);
				c(A, f.url, E);
				C = [f.outback, f.outhover, f.outpress];
				j(x, x, f.url, [f.inback, f.inhover, f.inpress]);
				j(A, A, f.url, C);
				t.appendChild(x);
				E = document.createElement("div");
				E.style.cssText = "position:relative;cursor:pointer;";
				var F = f.ruler,
				G = f.heat;
				b.setSize(E, [0, (q - n + 1) * F[3] + 1]);
				var K = document.createElement("div");
				K.style.position = "absolute";
				K.title = h.ruler;
				for (var M = -2; M < q - n; M++) {
					var N = document.createElement("div");
					b.setSize(N, [F[2], F[3]]);
					c(N, f.url, F);
					K.appendChild(N)
				}
				C = document.createElement("div");
				C.style.position = "absolute";
				C.title = h.ruler;
				for (M = -2; M < q - n; M++) {
					N = document.createElement("div");
					b.setSize(N, [G[2], G[3]]);
					c(N, f.url, G);
					C.appendChild(N)
				}
				b.setSize(K, [F[2], (q - n + 1) * F[3]]);
				e(K, [17, -2]);
				b.setSize(C, [G[2], (q - n + 1) * G[3]]);
				e(C, [17, -2]);
				var S = document.createElement("div");
				S.style.position = "absolute";
				S.style.margin = "auto 0px";
				S.title = h.slide;
				G = f.slideback;
				b.setSize(S, [G[2], G[3]]);
				c(S, f.url, G);
				e(S, [10, F[3]]);
				j(S, S, f.url, [f.slideback, f.slidehover, f.slidepress]);
				var Q = document.createElement("div");
				Q.style.display = "none";
				t.appendChild(E);
				E.appendChild(K);
				E.appendChild(C);
				E.appendChild(S);
				E.appendChild(Q);
				t.appendChild(A);
				var R = this;
				F = function(T) {
					return function() {
						var I = R.get("map");
						if (I) {
							var H = R.get("viewHeight_") / 3,
							J = R.get("viewWidth_") / 3,
							O = null;
							switch (T) {
							case 4:
								I.zoomIn();
								break;
							case 5:
								I.zoomOut();
								break;
							case 0:
								O = new d.Size(0, H);
								break;
							case 1:
								O = new d.Size(0, -H);
								break;
							case 2:
								O = new d.Size(J, 0);
								break;
							case 3:
								O = new d.Size( - J, 0);
								break;
							case 6:
								R.resume()
							}
							O && I.moveBy(O)
						}
					}
				};
				var L;
				R = this;
				var P = function(T, I) {
					return function() {
						if (L) {
							window.clearTimeout(L);
							L = null
						}
						if (R.getCurrentStyle().style === 3) if (I === "none") L = setTimeout(function() {
							T.style.display = I;
							L = null
						},
						1E3);
						else {
							T.style.display = I;
							L = null
						}
					}
				};
				g.addDomListener(B, "click", F(0));
				g.addDomListener(D, "click", F(1));
				g.addDomListener(w, "click", F(2));
				g.addDomListener(z, "click", F(3));
				g.addDomListener(x, "click", F(4));
				g.addDomListener(A, "click", F(5));
				g.addDomListener(E, "mouseover", P(Q, ""));
				g.addDomListener(x, "mouseover", P(Q, ""));
				g.addDomListener(A, "mouseover", P(Q, ""));
				g.addDomListener(Q, "mouseover", P(Q, ""));
				g.addDomListener(E, "mouseout", P(Q, "none"));
				g.addDomListener(x, "mouseout", P(Q, "none"));
				g.addDomListener(A, "mouseout", P(Q, "none"));
				g.addDomListener(Q, "mouseout", P(Q, "none"));
				g.addListener(this, "zoomlevel_changed",
				function() {
					P(Q, "")();
					P(Q, "none")()
				});
				w = function(T, I) {
					if (!I || I[0] !== 0 || I[1] !== 0) {
						var H = b.getMouseXY(T),
						J = b.getDomCoordinate(K);
						H = (1 - (H[1] - J[1]) / (J[3] - 9)) * (q - n) + n + 1;
						R.get("map").zoomTo(Math.floor(H));
						R.zoomLevel_changed()
					}
				};
				g.addDomListener(K, "click", w);
				g.addDomListener(C, "click", w);
				b.enableDrag(S, document.body.parent || document.body, {
					dragging: function(T, I) {
						if (I[0] !== 0 || I[1] !== 0) {
							var H = b.getMouseXY(T),
							J = b.getDomCoordinate(K),
							O = R.get("slideStart_"),
							U = R.get("slideEnd_");
							H = H[1] - J[1] + O - 11;
							H < -2 && (H = -2);
							H >= U - 9 && (H = U - 9);
							S.style.top = H + "px";
							R.setHeat(H, J[3])
						}
					},
					dragend: w
				});
				return t
			}
		},
		setHeat: function(t) {
			var n = this.get("slideStart_") - 2;
			t = (t - n) / 9;
			t = t > 0 ? t: 0;
			n = this.get("heat_").getElementsByTagName("div");
			for (var q = 0; q < n.length; q++) if (q <= t) {
				if (n[q]) n[q].style.visibility = "hidden"
			} else if (n[q]) n[q].style.visibility = "visible"
		},
		getCurrentStyle: function() {
			var t = this.get("style"),
			n = this.get("content"),
			q,
			w,
			x = 0,
			z = this.get("map"),
			D = z.get("maxZoom") - z.get("minZoom");
			z = f.background[3] + f.inback[3] + f.outback[3] + 12;
			D = f.background[3] + f.inback[3] + f.outback[3] + D * f.ruler[3] + 12;
			var B = f.background[3] + 16;
			if (n.parentNode && n.parentNode.parentNode && n.parentNode.style) {
				w = b.getDomCoordinate(n);
				n = b.getDomCoordinate(n.parentNode.parentNode);
				q = w[1] - n[1];
				w = w[0] - n[0]
			}
			if (t === 0) {
				t = this.get("viewHeight_") - q;
				t = t < z ? 1: t < D ? 2: 3;
				t !== this.get("currentStyle") && this.set("currentStyle", t)
			}
			if (this.get("viewWidth_") -
			w < B) x = 1;
			return {
				style: t,
				type: x
			}
		},
		draw: function() {
			var t = this.get("content"),
			n = this.get("map");
			if (t && n) {
				t = this.getCurrentStyle();
				var q = t.style,
				w = this.get("content").firstChild,
				x = w.nextSibling.nextSibling,
				z = x.firstChild;
				n = z.nextSibling;
				var D = n.nextSibling;
				tips = D.nextSibling;
				if (q == 1) {
					w.style.display = "none";
					x.style.display = "none"
				} else w.style.display = "";
				if (q == 2) x.style.display = "none";
				if (q == 3) {
					x.style.display = "";
					this.set("slideStart_", 0);
					q = z.offsetHeight;
					if (b.Browser().ie && b.Browser().ie <= 6) q -= 9;
					this.set("slideEnd_", q);
					this.set("slide_", D);
					this.set("heat_", n)
				}
				t = t.type;
				if (tips.childNodes.length) tips.innerHTML = "";
				t == 1 ? p(tips, "left", this) : p(tips, "right", this);
				this.zoomLevel_changed()
			}
		},
		zoomLevel_changed: function() {
			var t = this.get("zoomLevel"),
			n = this.get("slideEnd_"),
			q = this.get("map"),
			w = q.get("minZoom"),
			x = q.get("maxZoom");
			q = this.get("slideStart_") - 2;
			if (typeof t !== "undefined" && typeof n !== "undefined") {
				w = (t - w) / (x - w + 1);
				t = this.get("slide_");
				n = n - Math.round(w * (n - q) + 9);
				t.style.top = n + "px";
				this.setHeat(n)
			}
		},
		save: function() {
			var t = this.getMap();
			if (t) {
				this.set("center_", t.getCenter());
				this.set("zoomLevel_", t.getZoomLevel())
			}
		},
		resume: function() {
			var t = this.getMap(),
			n = this.get("center_");
			t && n && t.zoomTo(this.get("zoomLevel_"), n)
		},
		construct: function() {
			this.bindTo("zoomLevel", this.getMap());
			this.save()
		},
		destroy: function() {
			this.unbind("zoomLevel", this.getMap());
			this.set("center_", null)
		}
	});
	d.NavigationControlStyle = {
		DEFAULT: 0,
		SMALL: 1,
		NORMAL: 2,
		LARGE: 3
	};
	k = {
		content: null,
		margin: l.checkInterface(k.Size),
		align: l.isNumber,
		style: l.isNumber,
		map: null
	};
	d.NavigationControl = y;
	d.MVCObject.publicProperties(y, k)
})(__MapNS__); (function(d) {
	var r = __MapNSImpl__.Synchronize,
	o = d.Control;
	d.CopyrightControl = __MapNSImpl__.Class(o, {
		initialize: function(j) {
			var p = document.createElement("span");
			p.style.cssText = "background:transparent;font:11px arial,simsun;color:#333333;white-space:nowrap;";
			p.innerHTML = __MapNSImpl__.RC.Control.TencentText;
			j = r.fill(j, {
				content: p,
				zIndex: 10
			});
			o.apply(this, [j])
		},
		draw: function() {
			var j = this.get("content"),
			p = this.get("map"),
			b = p.layers;
			if (b) {
				var g = [];
				p = p.getBounds();
				for (var k = this.get("zoomLevel_"), l = 0; l < b.getLength(); l++) {
					var i = b.getAt(l).get("copyrights");
					i && g.push(i.getCopyrightNotice(p, k))
				}
				b = this.get("copySpan");
				if (!b) {
					b = document.createElement("span");
					j.appendChild(b);
					this.set("copySpan", b)
				}
				b.innerHTML = g.join(",")
			}
		},
		save: function() {
			var j = this.getMap();
			if (j) {
				this.set("center_", j.getCenter());
				this.set("zoomLevel_", j.getZoomLevel())
			}
		},
		changed: function(j) {
			switch (j) {
			case "zoomLevel":
			case "center":
			case "baseLayer":
				this.redraw()
			}
		},
		construct: function() {
			var j = this.get("map");
			this.bindTo("zoomLevel", j);
			this.bindTo("center", j);
			this.bindTo("baseLayer", j);
			this.save()
		},
		destroy: function() {
			var j = this.get("map");
			this.unbind("zoomLevel", j);
			this.unbind("center_", j);
			this.unbind("baseLayer", j)
		}
	})
})(__MapNS__); (function(d) {
	var r = d.MVCObject.checkers,
	o = d.Control;
	MVCObject = d.MVCObject;
	Synchronize = __MapNSImpl__.Synchronize;
	d.ScaleControl = __MapNSImpl__.Class(o, {
		initialize: function(j) {
			j = Synchronize.fill(j, {
				textColor: "#333333",
				barColor: "#333333",
				printable: true
			});
			var p = document.createElement("div");
			p.style.width = "100px";
			var b = document.createElement("div"),
			g = document.createElement("div");
			b.style.cssText = "font:11px arial,simsun;color:" + j.textColor + ";text-align:center;";
			g.style.cssText = "position:relative;height:8px;width:84px;";
			var k = document.createElement("div");
			k.style.cssText = "position:absolute;border:1px solid #ffffff;width:1px;height:6px;background:" + j.barColor + ";font-size:0;line-height:0;";
			k.style.left = 0;
			var l = document.createElement("div");
			l.style.cssText = k.style.cssText;
			l.style.left = "81px";
			var i = document.createElement("div");
			i.style.cssText = k.style.cssText;
			i.style.left = "2px";
			i.style.width = "80px";
			i.style.height = "2px";
			i.style.top = "2px";
			i.style.borderLeft = "none";
			i.style.borderRight = "none";
			g.appendChild(k);
			g.appendChild(l);
			g.appendChild(i);
			p.appendChild(b);
			p.appendChild(g);
			j.content = p;
			o.apply(this, [j]);
			this.set("text", b);
			this.set("rl", k);
			this.set("rr", l);
			this.set("rm", i)
		},
		construct: function() {
			var j = this.get("map");
			this.bindTo("center", j);
			this.bindTo("zoomLevel", j)
		},
		destroy: function() {
			this.unbind("center");
			this.unbind("zoomLevel")
		},
		changed: function(j) {
			var p = this.get("rr"),
			b = this.get("rl"),
			g = this.get("rm"),
			k = this.get("text");
			switch (j) {
			case "zoomLevel":
			case "center":
				if (!this.get("zoomLevel") || !this.get("center")) return;
				b = this.get("center").getLat();
				j = this.get("zoomLevel");
				if (k) k.innerHTML = __MapNSImpl__.RC.Control.ScaleText[j];
				b = [52, 52, 52, 52, 52, 41, 82, 82, 82, 65, 65, 65, 52, 52, 52, 41, 82, 82, 82][j] / Math.cos(b / 180);
				if (k) {
					k.style.width = b + 4 + "px";
					g.style.width = b + "px";
					p.style.left = b + 1 + "px"
				}
				break;
			case "textColor":
				if (k) {
					j = this.get("textColor");
					k.style.color = j
				}
				break;
			case "barColor":
				if (p && g && b) {
					j = this.get("barColor");
					p.style.backgroundColor = j;
					b.style.backgroundColor = j;
					g.style.backgroundColor = j
				}
			}
			this.redraw()
		}
	});
	MVCObject.publicProperties(d.ScaleControl, {
		textColor: r.isString,
		barColor: r.isString
	})
})(__MapNS__); (function(d) {
	var r = __MapNSImpl__.Util,
	o = __MapNSImpl__.Synchronize,
	j = d.Event,
	p = [];
	d.ContextMenuControl = __MapNSImpl__.Class(d.Control, {
		initialize: function(b) {
			b = o.fill(b, {
				map: null,
				zIndex: 100
			});
			var g = document.createElement("div"),
			k = document.createElement("div");
			k.style.cssText = "position:absolute;z-index:1;border:1px solid #999999;background:#FFFFFF;display:none;padding:4px 8px;-moz-box-shadow:2px 2px 8px #999999;-webkit-box-shadow:2px 2px 8px #999999;";
			var l = document.createElement("div");
			l.style.cssText = "position:absolute;background:black;opacity:0.2;filter:alpha(opacity=20);border:1px solid #999999;display:none;";
			var i = document.createElement("table"),
			c = document.createElement("tbody");
			i.appendChild(c);
			k.appendChild(i);
			g.appendChild(k);
			g.appendChild(l);
			b.content = g;
			d.Control.apply(this, [b]);
			this.set("tbody_", c);
			this.set("menu_", k);
			this.set("shadow_", l);
			this.set("targets_", []);
			this.set("items_", []);
			this.set("itemIndex_", 0);
			var e = this;
			b = function() {
				e.hide()
			};
			j.addDomListener(document.body.parentNode || document.body, "click", b);
			j.addDomListener(document.body.parentNode || document.body, "contextmenu", b);
			j.addDomListener(k, "click",
			function(h) {
				j.stopEvent(h)
			})
		},
		construct: function() {
			this.destroy();
			var b = this.get("map");
			if (b) {
				var g = this;
				this.set("listener_", j.addListener(b, "mousedown",
				function() {
					g.hide()
				}));
				p.push(this)
			}
		},
		destroy: function() {
			for (var b = 0, g; g = p[b]; b++) if (g === this) {
				p.splice(b, 1);
				break
			}
			this.get("listener_") && j.removeListener(this.get("listener_"))
		},
		hide: function() {
			this.get("menu_").style.display = "none";
			this.get("shadow_").style.display = "none"
		},
		show: function(b) {
			var g = this.get("map");
			if (g) {
				this.set("position", b.qLatLng || null);
				var k = g.get("mapContainer_");
				k = r.getDomCoordinate(k);
				b = r.getMouseCoordinate(b);
				var l = new d.Point(b[0] - k[0], b[1] - k[1]),
				i = this.get("menu_");
				b = i.style;
				k = this.get("shadow_").style;
				b.display = "";
				var c = i.offsetWidth;
				i = i.offsetHeight;
				g = g.getViewSize();
				var e = l.getX();
				l = l.getY();
				if (e - c >= 0 && e + c > g.getWidth()) e -= c;
				if (l - i >= 0 && l + i > g.getHeight()) l -= i;
				b.left = e + "px";
				b.top = l + "px";
				if (r.Browser().ie !== 0) {
					k.left = e + 1 + "px";
					k.top = l + 1 + "px";
					k.width = c + "px";
					k.height = i + "px";
					k.display = ""
				}
				for (g = 0; b = p[g]; g++) b !== this && b.hide()
			}
		},
		addTarget: function(b) {
			for (var g = this.get("targets_"), k = 0, l; l = g[k]; k++) if (l.target === b) return;
			k = {
				target: b,
				listener: null
			};
			var i = this;
			k.listener = j.addListener(b, "contextmenu",
			function(c) {
				i.show(c);
				j.stopEvent(c);
				return false
			});
			g.push(k)
		},
		removeTarget: function(b) {
			for (var g = this.get("targets_"), k = 0, l; l = g[k]; k++) if (l.target === b) {
				j.removeListener(l.listener);
				g.splice(k, 1);
				break
			}
		},
		addSeparator: function() {
			this.get("items_").push({
				content: "separator",
				type: 1
			});
			var b = this.get("itemIndex_") + 1;
			this.set("itemIndex_", b);
			this.renderItems();
			return b - 1
		},
		addItem: function(b, g) {
			this.get("items_").push({
				content: b,
				handler: g,
				type: 0
			});
			var k = this.get("itemIndex_") + 1;
			this.set("itemIndex_", k);
			this.renderItems();
			return k - 1
		},
		removeItem: function(b) {
			var g = this.get("items_");
			g[b] && (g[b].content = null);
			this.renderItems()
		},
		disableItem: function(b) {
			var g = this.get("items_");
			g[b] && (g[b].disabled = true);
			this.renderItems()
		},
		enableItem: function() {
			var b = this.get("items_");
			b[index] && (b[index].disabled = false);
			this.renderItems()
		},
		renderItems: function() {
			for (var b, g, k = this, l = this.get("items_"), i = l.length - 1; i >= 0; --i) if (l[i].content) {
				if (l[i].dom) g = l[i].dom;
				else {
					b = document.createElement("tr");
					g = document.createElement("td");
					b.appendChild(g);
					g.style.cssText = "white-space:nowrap;font-size:12px;color:#56695B;";
					l[i].dom = g;
					this.get("tbody_").appendChild(b)
				}
				j.clearListeners(g);
				if (l[i].type == 0) {
					g.innerHTML = l[i].content;
					if (l[i].disabled) {
						g.style.color = "#999999";
						g.style.cursor = "default"
					} else {
						g.style.color = "#56695b";
						g.style.cursor = "pointer";
						j.addDomListener(g, "mouseover",
						function() {
							this.style.color = "#76a045"
						});
						j.addDomListener(g, "mouseout",
						function() {
							this.style.color = "#56695b"
						});
						j.addDomListener(g, "click",
						function(c) {
							return function(e) {
								k.hide();
								c && c(e, k.get("position"));
								j.stopEvent(e)
							}
						} (l[i].handler))
					}
				} else g.innerHTML = "<div style='border-top:1px solid #cccccc;height: 1px;overflow:hidden'></div>"
			} else { (b = l[i].dom) && __MapNSImpl__.Util.removeNode(b.parentNode);
				l[i] = null
			}
			l = this.get("shadow_").style;
			i = this.get("menu_");
			l.width = i.offsetWidth + "px";
			l.height = i.offsetHeight + "px"
		}
	})
})(__MapNS__); (function(d) {
	d.SearchMode = {
		NORMAL: "NORMAL",
		BUS: "BUS"
	}
})(__MapNS__); (function(d) {
	d.SearchResult = function() {
		this.pois = [];
		this.totalNum = 0;
		this.pageCapacity = 10;
		this.pageIndex = 0;
		this.cityList = null
	}
})(__MapNS__); (function(d) {
	d.SearchStatus = {
		ERROR: "ERROR",
		INVALID_REQUEST: "INVALID_REQUEST",
		OK: "OK",
		UNKNOWN_ERROR: "UNKNOWN_ERROR",
		NO_RESULTS: "NO_RESULTS",
		CITIES: "CITIES"
	}
})(__MapNS__); (function(d) {
	var r = d.BaseService,
	o = d.MVCObject,
	j = d.LatLng,
	p = d.LatLngCircles,
	b = d.LatLngBounds,
	g = d.SearchResult,
	k = d.Poi,
	l = d.SearchMode,
	i = d.SearchStatus,
	c = __MapNSImpl__.Config;
	d.SearchService = __MapNSImpl__.Class(r, {
		initialize: function() {
			r.apply(this);
			this.defaultCapacity = 10;
			this.defaultPage = 0;
			this.queryType = c.QueryType.POI
		},
		search: function(e, h) {
			e && this.request({
				request: e,
				callback: h
			})
		},
		request: function(e) {
			var h = o.checkers,
			a = e.request,
			f = a.dataProvider,
			m = a.keyword,
			v = a.region,
			s = a.searchMode,
			u = [],
			y = "";
			if (!h.isNull(f)) if (h.isString(f)) {
				var t = f.split(";"),
				n = t.length;
				if (n > 1) for (f = 0; f < n; f++) y += "||" + encodeURIComponent(t[f]) + "||";
				else y = "||" + encodeURIComponent(f) + "||"
			}
			if (h.isNull(v)) u.push("c=1");
			else {
				h.isString(v) ? u.push("c=" + encodeURIComponent(v)) : u.push("c=1&l=11");
				this.queryType = c.QueryType.POI;
				if (!s || s === l.NORMAL) {
					if (h.isInstanceOf(b)(v)) {
						this.queryType = c.QueryType.SYN;
						f = v.getSouthWest();
						n = v.getNorthEast();
						t = f.getLng();
						f = f.getLat();
						var q = n.getLng();
						n = n.getLat();
						u.push("b=" + t + "," + f + "," + q + "," + n)
					}
					if (h.isInstanceOf(p)(v)) {
						this.queryType = c.QueryType.RN;
						t = v.getCenter();
						v = v.getRadius();
						n = t.getLng();
						t = t.getLat();
						u.push("px=" + n);
						u.push("py=" + t);
						u.push("r=" + v)
					}
				}
			}
			if (h.isString(m)) {
				h = encodeURIComponent(m);
				if (y) {
					u.push("sp=1");
					h = m + y
				}
				u.push("wd=" + h)
			}
			m = a.pageIndex || this.defaultPage;
			a = a.pageCapacity || this.defaultCapacity;
			if (s === l.BUS) {
				this.queryType = "busls";
				u.push("tp=0")
			}
			u.push("pn=" + m);
			u.push("rn=" + a);
			this.send(u, e)
		},
		onResult: function(e) {
			var h = e.callback,
			a = e.request,
			f = e.object;
			e = new g;
			if (f) {
				var m = f.info;
				f = f.detail;
				var v;
				switch (m.type) {
				case 6:
				case 11:
				case 16:
				case 17:
				case 49:
					var s = f.pois;
					v = f.city;
					var u = s.length,
					y = [];
					if (u) {
						for (f = 0; f < u; f++) {
							var t = new k,
							n = s[f];
							t.id = n.uid;
							t.name = n.name;
							t.latLng = new j(n.pointy, n.pointx);
							if (!a.searchMode || a.searchMode === l.NORMAL) n.addr && (t.address = n.addr); ! v && (v = {
								cname: a.region
							});
							v.cname && (t.city = v.cname);
							n.phone && (t.phone = n.phone);
							n.zip && (t.postcode = n.zip);
							n.classes && (t.category = n.classes);
							t.type = n.poitype;
							y.push(t)
						}
						e.pois = y;
						e.pageIndex = a.pageIndex || this.defaultPage;
						e.pageCapacity = a.pageCapacity || this.defaultCapacity;
						e.totalNum = m.total;
						e.cityList = [{
							city: v.cname,
							resultNums: m.total
						}];
						v = i.OK
					} else v = i.NO_RESULTS;
					break;
				case 8:
					a = f.result;
					m = [];
					for (f = 0; f < a.length; f++) {
						v = a[f];
						s = v.cities;
						if (s.length) for (u = 0; u < s.length; u++) {
							v = s[u];
							m.push({
								city: v.cname,
								resultNums: v.cnum
							})
						} else m.push({
							city: v.cname,
							resultNums: v.cnum
						})
					}
					e.cityList = m;
					v = i.CITIES;
					break;
				case 1E4:
					v = i.ERROR;
					break;
				case 10004:
					v = i.INVALID_REQUEST;
					break;
				default:
					v = i.UNKNOWN_ERROR
				}
			}
			h && h(e, v);
			delete h
		}
	})
})(__MapNS__); (function(d) {
	d.LineStatus = {
		ERROR: "ERROR",
		INVALID_REQUEST: "INVALID_REQUEST",
		OK: "OK",
		UNKNOWN_ERROR: "UNKNOWN_ERROR",
		NO_RESULTS: "NO_RESULTS"
	}
})(__MapNS__); (function(d) {
	d.LineType = {
		SUBWAY: 2,
		BUS: 1,
		FERRY: "FERRY"
	}
})(__MapNS__); (function(d) {
	d.LineResult = function() {
		this.type = this.name = this.id = "";
		this.distance = 0;
		this.price = this.etime = this.stime = "";
		this.time = this.stationNum = 0;
		this.to = this.from = "";
		this.path = null;
		this.stations = []
	}
})(__MapNS__); (function(d) {
	var r = d.BaseService,
	o = d.LatLng,
	j = d.LatLngBounds,
	p = d.LineResult,
	b = d.Poi,
	g = d.LineStatus,
	k = d.MVCObject.checkers;
	d.LineService = __MapNSImpl__.Class(r, {
		initialize: function() {
			r.apply(this);
			this.queryType = Config.QueryType.DT
		},
		detail: function(l, i) {
			l && this.request({
				request: l,
				callback: i
			})
		},
		request: function(l) {
			var i = l.request.lineId,
			c = [];
			k.isString(i) && c.push("uid=" + i);
			c.push("tp=3");
			this.send(c, l)
		},
		onResult: function(l) {
			var i = l.callback,
			c = l.object;
			l = new p;
			if (c) {
				var e = c.detail;
				switch (c.info.type) {
				case 18:
					c = e.poi;
					bounds = new j;
					if (c) {
						l.id = c.uid;
						l.name = c.name;
						l.type = c.poitype;
						l.distance = c.dist;
						l.stime = c.stime;
						l.etime = c.etime;
						l.price = c.price;
						l.stationNum = c.snum;
						l.from = c.from;
						l.to = c.to;
						e = this.fromSegmentToPath(c.points);
						l.path = e.path;
						l.bounds = e.bounds;
						e = 0;
						for (var h = c.stations, a = h.length; e < a; e++) {
							c = new b;
							var f = h[e];
							c.id = f.uid;
							c.type = f.poitype;
							c.name = f.name;
							c.latLng = new o(f.pointy, f.pointx);
							l.stations.push(c)
						}
						e = g.OK
					} else e = g.NO_RESULTS;
					break;
				case 1E4:
					e = g.ERROR;
					break;
				case 10004:
					e = g.INVALID_REQUEST;
					break;
				default:
					e = g.UNKNOWN_ERROR
				}
			}
			i && i(l, e);
			delete i
		}
	})
})(__MapNS__); (function(d) {
	d.StationStatus = {
		ERROR: "ERROR",
		INVALID_REQUEST: "INVALID_REQUEST",
		OK: "OK",
		UNKNOWN_ERROR: "UNKNOWN_ERROR",
		NO_RESULTS: "NO_RESULTS"
	}
})(__MapNS__); (function(d) {
	d.StationResult = function() {
		this.type = this.name = this.id = "";
		this.latLng = null;
		this.lines = []
	}
})(__MapNS__); (function(d) {
	var r = d.BaseService,
	o = d.LatLng,
	j = d.StationResult,
	p = d.LineResult,
	b = d.StationStatus;
	checkers = d.MVCObject.checkers;
	Util = __MapNSImpl__.Util;
	d.StationService = __MapNSImpl__.Class(r, {
		initialize: function() {
			r.apply(this);
			this.queryType = Config.QueryType.DT
		},
		detail: function(g, k) {
			g && this.request({
				request: g,
				callback: k
			})
		},
		request: function(g) {
			var k = g.request.stationId,
			l = [];
			checkers.isString(k) && l.push("uid=" + k);
			l.push("tp=1");
			this.send(l, g)
		},
		onResult: function(g) {
			var k = g.callback,
			l = g.object;
			g = new j;
			if (l) {
				var i = l.detail;
				switch (l.info.type) {
				case 19:
					l = i.poi;
					i = l.lines;
					var c = i.length;
					if (c) {
						for (var e = 0; e < c; e++) {
							var h = new p,
							a = i[e];
							h.id = a.uid;
							h.name = a.name;
							h.stime = a.stime;
							h.etime = a.etime;
							h.from = a.from;
							h.to = a.to;
							g.lines.push(h)
						}
						g.id = l.uid;
						g.name = l.name;
						g.type = l.poitype;
						g.latLng = new o(l.pointy, l.pointx);
						i = b.OK
					} else i = b.NO_RESULTS;
					break;
				case 1E4:
					i = b.ERROR;
					break;
				case 10004:
					i = b.INVALID_REQUEST;
					break;
				default:
					i = b.UNKNOWN_ERROR
				}
			}
			k && k(g, i);
			delete k
		}
	})
})(__MapNS__); (function(d) {
	d.DirectionsPolicy = {
		LEAST_TIME: 0,
		AVOID_HIGHWAYS: 1,
		LEAST_DISTANCE: 2,
		REAL_TRAFFIC: 3,
		PREDICT_TRAFFIC: 4
	}
})(__MapNS__); (function(d) {
	d.DirectionsTravelMode = {
		DRIVING: "DRIVING",
		WALKING: "WALKING"
	}
})(__MapNS__); (function(d) {
	d.DirectionsResult = function() {
		this.routes = [];
		this.policy = 0;
		this.end = this.start = null
	}
})(__MapNS__); (function(d) {
	d.DirectionsRoute = function() {
		this.bounds = null;
		this.legs = [];
		this.distance = this.duration = null
	}
})(__MapNS__); (function(d) {
	d.DirectionsLeg = function() {
		this.duration = this.distance = null;
		this.steps = [];
		this.path = [];
		this.start_address = "";
		this.start_location = null;
		this.end_address = "";
		this.end_location = null
	}
})(__MapNS__); (function(d) {
	d.DirectionsStep = function() {
		this.duration = this.distance = null;
		this.path = [];
		this.roadname = "";
		this.end_location = this.start_location = null;
		this.instructions = "";
		this.placemarks = [];
		this.cities = [];
		this.turning = null
	}
})(__MapNS__); (function(d) {
	d.DirectionsStatus = {
		ERROR: "ERROR",
		INVALID_REQUEST: "INVALID_REQUEST",
		OK: "OK",
		UNKNOWN_ERROR: "UNKNOWN_ERROR",
		NO_RESULTS: "NO_RESULTS",
		MULTI_DESTINATION: "MULTI_DESTINATION"
	}
})(__MapNS__); (function(d) {
	var r = d.BaseService,
	o = d.DirectionsResult,
	j = d.DirectionsStatus,
	p = d.DirectionsPolicy,
	b = d.DirectionsTravelMode,
	g = d.DirectionsRoute,
	k = d.DirectionsLeg,
	l = d.DirectionsStep,
	i = d.LatLng,
	c = d.Distance,
	e = d.Duration,
	h = d.Turning,
	a = d.Poi,
	f = d.LatLngBounds,
	m = __MapNSImpl__.Config,
	v = __MapNSImpl__.Util;
	d.DirectionsService = __MapNSImpl__.Class(d.BaseService, {
		initialize: function() {
			r.apply(this);
			this.routeIndex = 0;
			this.queryType = m.QueryType.NAV
		},
		route: function(s, u) {
			s && this.request({
				request: s,
				callback: u
			})
		},
		request: function(s) {
			var u = s.request,
			y = u.region,
			t = [];
			checkers.isString(y) && t.push("c=" + encodeURIComponent(y));
			y = u.end;
			var n = u.time,
			q = u.time,
			w = u.policy;
			t.push("start=" + this.fromDestToString(u.start));
			t.push("dest=" + this.fromDestToString(y)); ! q && (q = u.travelMode = b.DRIVING);
			switch (q) {
			case "DRIVING":
				this.queryType = m.QueryType.NAV;
				break;
			case "WALKING":
				this.queryType = m.QueryType.WALK
			} ! w && (w = u.policy = p.LEAST_TIME);
			if (n && (w === p.REAL_TRAFFIC || w === p.PREDICT_TRAFFIC)) t.push("time=" + n);
			t.push("cond=" + w);
			this.send(t, s)
		},
		onResult: function(s) {
			var u = s.callback,
			y = s.request,
			t = s.object;
			s = new o;
			if (t) {
				var n = t.info;
				switch (n.type) {
				case 44:
					var q = v.parseCoords(t.coors),
					w = t.distance,
					x = t.segmentList,
					z = t.bounds,
					D = x && x.length,
					B = new g,
					A = z.split(",");
					z = new i(A[1], A[0]);
					A = new i(A[3], A[2]);
					z = new f(z, A);
					A = new k;
					if (D) {
						var C = new c;
						C.text = this.fromDistToString(t.distance);
						C.value = w;
						w = new e;
						w.text = this.fromTimeToString(n.time);
						w.value = n.time;
						A.distance = C;
						A.duration = w;
						A.path = q;
						for (t = 0; t < D; t++) {
							var E = x[t],
							F = new l,
							G = E.coorStart;
							G = q.slice(G, G + E.coorNum);
							if (t == 0) {
								A.start_address = E.roadName;
								A.start_location = G[0]
							}
							if (t == D - 1) {
								A.end_address = E.roadName;
								A.end_location = G[G.length - 1]
							}
							var K = new c;
							K.text = this.fromDistToString(E.roadLength);
							K.value = E.roadLength;
							F.distance = K;
							K = new e;
							K.text = this.fromTimeToString(parseInt(E.driveTime));
							K.value = parseInt(E.driveTime);
							F.duration = K;
							K = new h;
							K.text = E.action;
							K.latLng = G[G.length - 1];
							for (var M = E.kp, N = [], S = 0, Q = M.length; S < Q; S++) {
								var R = new a,
								L = M[S];
								R.name = L.name;
								R.latLng = new i(L.pointy, L.pointx);
								N.push(R)
							}
							F.roadname = E.roadName;
							F.start_location = G[0];
							F.end_location = G[G.length - 1];
							F.instructions = E.textInfo;
							F.path = G;
							F.turning = K;
							F.cities = E.cities;
							F.placemarks = N;
							A.steps.push(F)
						}
						B.bounds = z;
						B.distance = C;
						B.duration = w;
						B.legs.push(A);
						s.start = this.fromDestToPoi(n.start);
						s.end = this.fromDestToPoi(n.dest);
						s.traffic = !!n.navtraffic;
						s.policy = y.policy;
						s.routes.push(B);
						n = j.OK
					} else n = j.NO_RESULTS;
					break;
				case 21:
					n = t.detail;
					y = n.start;
					n = n.dest;
					s.start = [];
					t = 0;
					for (q = y ? y.length: 0; t < q; t++) {
						x = y[t];
						s.start.push(this.fromDestToPoi(x))
					}
					s.end = [];
					t = 0;
					for (q = n ? n.length: 0; t < q; t++) {
						x = n[t];
						s.end.push(this.fromDestToPoi(x))
					}
					n = j.MULTI_DESTINATION;
					break;
				case 1E4:
					n = j.ERROR;
					break;
				case 10004:
					n = j.INVALID_REQUEST;
					break;
				default:
					n = j.UNKNOWN_ERROR
				}
			}
			u && u(s, n);
			delete u
		}
	})
})(__MapNS__); (function(d) {
	d.ActionType = {
		BUS: "BUS",
		SUBWAY: "SUBWAY",
		WALK: "WALK"
	}
})(__MapNS__); (function(d) {
	d.TransferAction = function() {}
})(__MapNS__); (function(d) {
	d.TransferResult = function() {
		this.plans = [];
		this.policy = 0;
		this.city = "";
		this.end = this.start = null
	}
})(__MapNS__); (function(d) {
	d.TransferPlan = function() {
		this.actions = [];
		this.bounds = {};
		this.lines = [];
		this.walks = [];
		this.stations = [];
		this.distance = this.duration = null
	}
})(__MapNS__); (function(d) {
	d.TransferPolicy = {
		LEAST_TIME: 0,
		LEAST_TRANSFER: 1,
		LEAST_WALKING: 2,
		MOST_ONE: 3,
		NO_SUBWAY: 4
	}
})(__MapNS__); (function(d) {
	d.TransferStatus = {
		ERROR: "ERROR",
		INVALID_REQUEST: "INVALID_REQUEST",
		OK: "OK",
		UNKNOWN_ERROR: "UNKNOWN_ERROR",
		NO_RESULTS: "NO_RESULTS",
		NO_SUPPORT: "NO_SUPPORT",
		MULTI_DESTINATION: "MULTI_DESTINATION"
	}
})(__MapNS__); (function(d) {
	var r = d.BaseService,
	o = d.LatLng,
	j = d.LatLngBounds,
	p = d.Poi,
	b = d.LineResult,
	g = d.TransferResult,
	k = d.TransferPlan,
	l = d.TransferStatus,
	i = d.TransferPolicy,
	c = d.TransferAction,
	e = d.ActionType,
	h = d.LineType,
	a = d.DirectionsLeg,
	f = d.DirectionsStep,
	m = d.Distance,
	v = d.Duration,
	s = d.Turning,
	u = d.MVCObject.checkers,
	y = __MapNSImpl__.RC.Transfer,
	t = __MapNSImpl__.Config;
	d.TransferService = __MapNSImpl__.Class(r, {
		initialize: function() {
			r.call(this);
			this.queryType = t.QueryType.BUS
		},
		plan: function(n, q) {
			n && this.request({
				request: n,
				callback: q
			})
		},
		request: function(n) {
			var q = n.request,
			w = q.region,
			x = [];
			u.isString(w) && x.push("c=" + encodeURIComponent(w));
			w = q.end;
			var z = q.policy;
			x.push("start=" + this.fromDestToString(q.start));
			x.push("dest=" + this.fromDestToString(w)); ! z && (z = q.policy = i.LEAST_TIME);
			u.isNull(z) || (z === i.NO_SUBWAY ? x.push("nosub=" + z) : x.push("cond=" + z));
			this.send(x, n)
		},
		onResult: function(n) {
			var q = n.callback,
			w = n.request,
			x = n.object;
			n = new g;
			if (x) {
				var z = x.info;
				x = x.detail;
				n.city = w.region;
				n.policy = w.policy;
				switch (z.type) {
				case 15:
					var D = x.length,
					B = [];
					if (D) {
						for (w = 0; w < D; w++) {
							var A = new k,
							C = x[w],
							E = C.intervals,
							F = C.trans,
							G = [],
							K = [],
							M = [],
							N = [],
							S = new j,
							Q = 0,
							R = 0,
							L = 0;
							for (C = F.length; L < C; L++) {
								var P = new c,
								T = [],
								I = null;
								if (L > 0) {
									var H = E[L - 1][0];
									I = new b;
									I.id = H.uid;
									I.name = H.name;
									I.type = H.type;
									T.push(y[0] + I.name);
									var J = new m;
									J.value = H.distance;
									J.text = this.fromDistToString(H.distance);
									I.distance = J;
									I.stationNum = H.station_num;
									T.push(y[1] + I.stationNum + y[2]);
									J = new v;
									J.value = H.time;
									J.text = this.fromTimeToString(H.time);
									I.duration = J;
									J = this.fromSegmentToPath(H.segment);
									I.path = J.path;
									I.bounds = J.bounds;
									S.union(J.bounds);
									G.push(I);
									Q += H.distance;
									R += H.time;
									P.data = I
								}
								J = F[L];
								var O,
								U = this.fromTranToLeg_(J);
								if (L == 0) {
									O = this.fromTranToStation_(J.geton);
									H = E[L][0];
									O.type = H.type;
									M.push(O)
								}
								if (L > 0 && L < C - 1) {
									O = this.fromTranToStation_(J.getoff);
									H = E[L - 1][0];
									O.type = H.type;
									M.push(O);
									O = this.fromTranToStation_(J.geton);
									H = E[L][0];
									O.type = H.type;
									M.push(O);
									T.push(y[3] + J.getoff.name)
								}
								if (L == C - 1) {
									O = this.fromTranToStation_(J.getoff);
									H = E[L - 1][0];
									O.type = H.type;
									M.push(O);
									T.push(y[3] + J.getoff.name)
								}
								if (I) {
									I.from = F[L - 1].geton.name;
									I.to = F[L].getoff.name;
									P.instructions = T.join("\uff0c");
									P.type = e.BUS;
									if (I.type == h.SUBWAY) P.type = e.SUBWAY;
									N.push(P)
								}
								if (U.distance.value) {
									P = new c;
									T = [];
									T.push(U.steps[0].instructions);
									T.push(y[3] + (J.geton.name ? J.geton.name: y[4]));
									P.instructions = T.join("\uff0c");
									P.data = U;
									P.type = e.WALK;
									N.push(P)
								}
								K.push(U)
							}
							A.actions = N;
							A.lines = G;
							A.bounds = S;
							A.walks = K;
							A.stations = M;
							C = new m;
							C.value = Q;
							C.text = this.fromDistToString(Q);
							A.distance = C;
							C = new v;
							C.value = R;
							C.text = this.fromTimeToString(R);
							A.duration = C;
							B.push(A);
							n.plans = B;
							n.start = this.fromDestToPoi(z.start);
							n.end = this.fromDestToPoi(z.dest)
						}
						z = l.OK
					} else z = l.NO_RESULTS;
					break;
				case 14:
					z = x.start;
					x = x.dest;
					n.start = [];
					w = 0;
					for (C = z ? z.length: 0; w < C; w++) {
						D = z[w];
						n.start.push(this.fromDestToPoi(D))
					}
					n.end = [];
					w = 0;
					for (C = x ? x.length: 0; w < C; w++) {
						D = x[w];
						n.end.push(this.fromDestToPoi(D))
					}
					z = l.MULTI_DESTINATION;
					break;
				case 1E4:
					z = l.ERROR;
					break;
				case 10004:
					z = l.INVALID_REQUEST;
					break;
				case 10006:
					z = l.NO_SUPPORT;
					break;
				default:
					z = l.UNKNOWN_ERROR
				}
			}
			q && q(n, z);
			delete q
		},
		fromTranToStation_: function(n) {
			var q = new p;
			q.id = n.uid;
			q.name = n.name;
			q.latLng = new o(n.pointy, n.pointx);
			return q
		},
		fromTranToLeg_: function(n) {
			var q = new a,
			w = new v,
			x = new m,
			z = n.walk;
			w.value = z.time;
			w.text = this.fromTimeToString(z.time);
			q.duration = w;
			x.value = z.distance;
			x.text = this.fromDistToString(z.distance);
			q.distance = x;
			var D = this.fromSegmentToPath(z.segment);
			q.path = D.path;
			var B = n.getoff;
			q.start_address = B.name;
			B = new o(B.pointy, B.pointx);
			q.start_location = B;
			n = n.geton;
			q.end_address = n.name;
			var A = new o(n.pointy, n.pointx);
			q.end_location = A;
			n = new f;
			n.distance = x;
			n.duration = w;
			n.start_location = B;
			n.end_location = A;
			w = new s;
			w.text = __MapNSImpl__.RC.Direction[z.direction];
			w.latLng = B;
			n.turning = w;
			n.instructions = x.value ? "\u5411" + w.text + "\u6b65\u884c\u7ea6" + n.distance.text: "";
			n.path = D.path;
			q.steps.push(n);
			return q
		}
	})
})(__MapNS__); (function(d) {
	d.LocationMode = {
		CLIENT_IP: "CLIENT_IP",
		AREA_CODE: "AREA_CODE",
		LATLNG: "LATLNG",
		W3C_LOCATION: "W3C_LOCATION"
	}
})(__MapNS__); (function(d) {
	d.GeolocationStatus = {
		ERROR: "ERROR",
		INVALID_REQUEST: "INVALID_REQUEST",
		OK: "OK",
		UNKNOWN_ERROR: "UNKNOWN_ERROR",
		NO_RESULTS: "NO_RESULTS"
	}
})(__MapNS__); (function(d) {
	d.GeolocationResult = function() {
		this.name = "";
		this.detail = this.latLng = null
	}
})(__MapNS__); (function(d) {
	var r = d.BaseService,
	o = d.LatLng,
	j = d.LocationMode,
	p = d.GeolocationResult,
	b = d.GeolocationStatus,
	g = d.MVCObject.checkers,
	k = __MapNSImpl__.Config;
	d.Geolocation = __MapNSImpl__.Class(r, {
		initialize: function() {
			r.apply(this);
			this.queryType = k.QueryType.POS
		},
		position: function(l, i) {
			l && this.request({
				request: l,
				callback: i
			})
		},
		request: function(l) {
			var i = l.request,
			c = i.keyword;
			i = i.locationMode;
			var e = [],
			h = "";
			if (c !== "" && !g.isNull(c)) {
				if (g.isString(c)) if (i) if (i === j.CLIENT_IP) h = "ip";
				else if (i === j.AREA_CODE) h = "acode";
				else {
					if (i === j.LATLNG) h = "lonlat"
				} else h = this.checkIP_(c) ? "ip": "acode";
				else if (g.isInstanceOf(o)(c)) {
					c = c.getLng() + "," + c.getLat();
					h = "lonlat"
				}
				e.push("wd=" + c);
				e.push("tp=" + h)
			} else this.queryType = "gc";
			this.send(e, l)
		},
		onResult: function(l) {
			var i = l.callback,
			c = l.object;
			l = new p;
			if (c) {
				var e = c.detail;
				switch (c.info.type) {
				case 4:
				case 31:
					c = e.cname;
					var h = e.path;
					if (c) {
						l.name = c;
						l.latLng = new o(e.pointy, e.pointx);
						if (h) {
							h.length == 4 && (e = [h[3] ? h[3].cname: "", h[2] ? h[2].cname: "", h[1] ? h[1].cname: "", h[0] ? h[0].cname: ""]);
							h.length == 3 && (e = [h[2] ? h[2].cname: "", h[1] ? h[1].cname: "", h[0] ? h[0].cname: ""]);
							h.length == 2 && (e = [h[1] ? h[1].cname: "", h[0] ? h[0].cname: ""]);
							h.length == 1 && (e = [h[0] ? h[0].cname: ""]);
							l.detail = e.join(",")
						}
						e = b.OK
					} else e = b.NO_RESULTS;
					break;
				case 1E4:
					e = b.ERROR;
					break;
				case 10004:
					e = b.INVALID_REQUEST;
					break;
				default:
					e = b.UNKNOWN_ERROR
				}
			}
			i && i(l, e);
			delete i
		},
		checkIP_: function(l) {
			return /^(([01]?[\d]{1,2})|(2[0-4][\d])|(25[0-5]))(\.(([01]?[\d]{1,2})|(2[0-4][\d])|(25[0-5]))){3}$/.test(l)
		}
	})
})(__MapNS__); (function(d) {
	d.SuggestionMode = {
		NORMAL: "NORMAL",
		BUS: "BUS",
		DISTRICT: "DISTRICT"
	}
})(__MapNS__); (function(d) {
	d.SuggestionStatus = {
		ERROR: "ERROR",
		INVALID_REQUEST: "INVALID_REQUEST",
		OK: "OK",
		UNKNOWN_ERROR: "UNKNOWN_ERROR",
		NO_RESULTS: "NO_RESULTS"
	}
})(__MapNS__); (function(d) {
	d.SuggestionResult = function() {}
})(__MapNS__); (function(d) {
	var r = d.BaseService,
	o = d.SuggestionResult,
	j = d.SuggestionStatus,
	p = __MapNSImpl__.Config,
	b = d.SuggestionMode;
	d.Suggestion = __MapNSImpl__.Class(r, {
		initialize: function() {
			r.apply(this);
			this.queryType = p.QueryType.SG
		},
		complete: function(g, k) {
			g && this.request({
				request: g,
				callback: k
			})
		},
		request: function(g) {
			var k = g.request,
			l = k.suggestionMode,
			i = k.keyword;
			k = k.region;
			var c = [];
			if (checkers.isNull(k)) c.push("c=1");
			else checkers.isString(k) ? c.push("c=" + encodeURIComponent(k)) : c.push("c=1");
			i !== "" && !checkers.isNull(i) && c.push("wd=" + encodeURIComponent(i));
			if (checkers.isNull(l)) c.push("tp=0");
			else {
				l === b.NORMAL && c.push("tp=0");
				l === b.BUS && c.push("tp=1");
				l === b.DISTRICT && c.push("tp=2")
			}
			this.send(c, g)
		},
		onResult: function(g) {
			var k = g.callback,
			l = g.object;
			g = [];
			if (l) {
				var i = l.detail;
				switch (l.info.type) {
				case 5:
					if (l = i && i.length) {
						for (var c = 0; c < l; c++) {
							var e = i[c],
							h = new o,
							a = e.name,
							f = e.place,
							m = e.show,
							v = e.type,
							s = e.city.path;
							h.id = e.uid;
							h.text = a;
							h.region = f;
							h.highlight_text = m;
							h.poi_type = v;
							var u;
							if (s) {
								s.length == 4 && (u = [s[3] ? s[3].cname: "", s[2] ? s[2].cname: "", s[1] ? s[1].cname: "", s[0] ? s[0].cname: ""]);
								s.length == 3 && (u = [s[2] ? s[2].cname: "", s[1] ? s[1].cname: "", s[0] ? s[0].cname: ""]);
								s.length == 2 && (u = [s[1] ? s[1].cname: "", s[0] ? s[0].cname: ""]);
								s.length == 1 && (u = [s[0] ? s[0].cname: ""]);
								h.district = u.join(",")
							}
							g.push(h)
						}
						i = j.OK
					} else i = j.NO_RESULTS;
					break;
				case 1E4:
					i = j.ERROR;
					break;
				case 10004:
					i = j.INVALID_REQUEST;
					break;
				default:
					i = j.UNKNOWN_ERROR
				}
			}
			k && k(g, i);
			delete k
		}
	})
})(__MapNS__); (function(d) {
	d.GeocoderResult = function() {
		this.location = null;
		this.address = "";
		this.addressComponents = {
			streetNumber: "",
			street: "",
			district: "",
			city: "",
			province: ""
		};
		this.similarResults = null
	}
})(__MapNS__); (function(d) {
	d.GeocoderStatus = {
		ERROR: "ERROR",
		INVALID_REQUEST: "INVALID_REQUEST",
		OK: "OK",
		UNKNOWN_ERROR: "UNKNOWN_ERROR",
		NO_RESULTS: "NO_RESULTS",
		OVER_QUERY_LIMIT: "OVER_QUERY_LIMIT"
	}
})(__MapNS__); (function(d) {
	var r = d.BaseService,
	o = d.GeocoderResult,
	j = d.LatLng,
	p = d.GeocoderStatus;
	d.Geocoder = __MapNSImpl__.Class(r, {
		initialize: function() {
			r.apply(this);
			this.queryType = "geoc"
		},
		geocode: function(b, g) {
			b && this.request({
				request: b,
				callback: g
			})
		},
		request: function(b) {
			var g = d.MVCObject.checkers,
			k = b.request,
			l = [],
			i = k.address;
			k = k.location;
			if (!g.isNull(i) && !g.isNull(k)) {
				if (g.isString(i)) {
					this.queryType = "geoc";
					l.push("addr=" + encodeURIComponent(i))
				}
			} else {
				if (g.isString(i)) {
					this.queryType = "geoc";
					l.push("addr=" + encodeURIComponent(i))
				}
				if (g.isInstanceOf(j)(k) || g.isInstanceOf(j)(i)) {
					k = k || i;
					this.queryType = "rgeoc";
					g = k.getLng();
					i = k.getLat();
					l.push("lnglat=" + g + "," + i)
				}
			}
			this.send(l, b)
		},
		onResult: function(b) {
			var g = b.callback,
			k = b.request,
			l = b.object;
			b = new o;
			if (l) {
				var i = l.info;
				l = l.detail;
				var c;
				c = i.error;
				switch (i.type) {
				case 45:
					if (c == -2) c = p.NO_RESULTS;
					else if (c == 0) {
						var e = l.name || "",
						h = l.city || "",
						a = l.province || "",
						f = l.district || "";
						b.address = a + h + f + e;
						b.location = new j(l.pointy, l.pointx);
						b.similarity = l.similarity;
						b.pcd_conflict_flag = l.pcd_conflict_flag;
						b.gps_type = l.gps_type;
						b.addressComponents = {
							street: e,
							district: f,
							city: h,
							province: a,
							country: l.country || "\u4e2d\u56fd"
						};
						b.similarResults = [];
						c = p.OK
					} else if (c == -1) c = p.ERROR;
					else if (c == -3) c = p.OVER_QUERY_LIMIT;
					break;
				case 46:
					i = l.results;
					l = i.length;
					if (c == -2) c = p.NO_RESULTS;
					else if (l) {
						var m = "";
						for (var v = c = "", s = "", u = "", y = 0; y < l; y++) {
							var t = i[y];
							switch (t.dtype) {
							case "AD":
								m = t.name;
								break;
							case "ST":
								v = t.name;
								break;
							case "ST_NO":
								c = t.name;
								break;
							case "TOWN":
								s = t.name;
								break;
							case "VILLAGE":
								u = t.name
							}
						}
						b.address = m.replace(/,/g, "") + (c ? c: v ? v: s + u);
						b.location = k.location;
						k = m.split(",");
						if (k.length == 4) {
							e = k[0];
							a = k[1];
							h = k[2];
							f = k[3]
						}
						if (k.length == 3) {
							e = k[0];
							a = k[1];
							h = k[1];
							f = k[2]
						}
						b.addressComponents = {
							village: u,
							town: s,
							streetNumber: c,
							street: v,
							district: f,
							city: h,
							province: a,
							country: e
						};
						c = p.OK
					} else c = p.NO_RESULTS;
					break;
				case 1E4:
					c = p.ERROR;
					break;
				case 10004:
					c = p.INVALID_REQUEST;
					break;
				default:
					c = p.UNKNOWN_ERROR
				}
			}
			g && g(b, c);
			delete g
		}
	})
})(__MapNS__); (function(d) {
	d.PoiStatus = {
		ERROR: "ERROR",
		INVALID_REQUEST: "INVALID_REQUEST",
		OK: "OK",
		UNKNOWN_ERROR: "UNKNOWN_ERROR",
		NO_RESULTS: "NO_RESULTS"
	}
})(__MapNS__); (function(d) {
	var r = d.BaseService,
	o = d.LatLng,
	j = d.Poi,
	p = d.PoiStatus;
	checkers = d.MVCObject.checkers;
	Config = __MapNSImpl__.Config;
	Util = __MapNSImpl__.Util;
	d.PoiService = __MapNSImpl__.Class(r, {
		initialize: function() {
			r.apply(this);
			this.queryType = Config.QueryType.DTS
		},
		detail: function(b, g) {
			b && this.request({
				request: b,
				callback: g
			})
		},
		request: function(b) {
			var g = b.request.poiId,
			k = [];
			checkers.isString(g) && k.push("uids=" + g);
			k.push("tp=0");
			k.push("num=10");
			this.send(k, b)
		},
		onResult: function(b) {
			var g = b.callback,
			k = b.object;
			b = [];
			if (k) {
				var l = k.detail;
				switch (k.info.type) {
				case 60:
					k = l.pois;
					if (l = k.length) {
						for (var i = 0; i < l; i++) {
							var c = new j,
							e = k[i];
							c.id = e.uid;
							c.name = e.name;
							c.latLng = new o(e.pointy, e.pointx);
							e.addr && (c.address = e.addr);
							e.phone && (c.phone = e.phone);
							c.type = e.poitype;
							e.zip && (c.postcode = e.zip);
							e.classes && (c.category = e.classes);
							e.average_price && (c.average_price = e.average_price);
							e.comment_num && (c.comment_num = e.comment_num);
							e.comment_level && (c.comment_level = e.comment_level);
							e.special_rec && (c.special_rec = e.special_rec);
							e.src && (c.source = e.src);
							b.push(c)
						}
						l = p.OK
					} else l = p.NO_RESULTS;
					break;
				case 1E4:
					l = p.ERROR;
					break;
				case 10004:
					l = p.INVALID_REQUEST;
					break;
				default:
					l = p.UNKNOWN_ERROR
				}
			}
			g && g(b, l);
			delete g
		}
	})
})(__MapNS__); (function() {
	QQMap = __MapNS__;
	QQMapImpl = __MapNSImpl__; (function(d, r) {
		if (typeof d == "string") {
			for (var o = d.split("."), j = window, p = 0; p < o.length; p++) {
				var b = o[p];
				j[b] || (j[b] = {});
				j = j[b]
			}
			for (; o = r.pop();) {
				j[o] = __MapNS__[o];
				QQMap["Q" + o] = __MapNS__[o]
			}
		}
	})("soso.maps", ["Point", "Size", "ALIGN", "LatLng", "LatLngBounds", "LatLngCircles", "Fx", "CssFx", "Animation", "ObjectLoader", "MVCObject", "Event", "MVCArray", "Overlay", "Control", "TileLayer", "TileGridLayer", "InfoWindow", "Polygon", "Polyline", "Circle", "MarkerImage", "MarkerShape", "MarkerDecoration", "Marker", "Label", "GroundOverlay", "ContextMenuControl", "Copyright", "CopyrightCollection", "CopyrightControl", "NavigationControl", "NavigationControlStyle", "ScaleControl", "Map", "Poi", "Distance", "Duration", "Turning", "PoiType", "SearchMode", "SearchStatus", "SearchResult", "SearchService", "PoiStatus", "PoiResult", "PoiService", "LineType", "LineStatus", "LineResult", "LineService", "StationStatus", "StationResult", "StationService", "ActionType", "TransferAction", "TransferStatus", "TransferPolicy", "TransferPlan", "TransferResult", "TransferService", "DirectionsTravelMode", "DirectionsPolicy", "DirectionsStatus", "DirectionsLeg", "DirectionsRoute", "DirectionsResult", "DirectionsService", "DirectionsStep", "Geocoder", "GeocoderResult", "GeocoderStatus", "LocationMode", "GeolocationStatus", "GeolocationResult", "Geolocation", "SuggestionMode", "SuggestionStatus", "SuggestionResult", "Suggestion"]);
	Util.domReady(function() {
		var d = (new Date).getTime(),
		r = window[d] = new Image;
		r.onload = r.onerror = function() {
			window[d] = null
		};
		r.src = "./";
		r = null
	})
})();