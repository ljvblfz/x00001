(function(p) {
	function r(a, b, c) {
		a % b === 0 && c--;
		return Math.floor(a / b) + c
	}
	var q = __MapNSImpl__.Util,
	u = __MapNSImpl__.Config,
	x = p.Event,
	y = p.Point,
	s = p.MVCView,
	v = p.MVCObject.checkers,
	w = u.domain + "imgs/transparent.gif";
	p = __MapNSImpl__.Class(s, {
		initialize: function(a) {
			s.apply(this);
			this.set("model_", a);
			this.bindsTo(["tileSize_", "opacity", "isPng_", "layerContainer_", "domsClone_", "baseLeft_", "baseTop_", "isBase", "map"], a);
			this.bindTo("exchange_", a, null, true);
			a.set("view_", this)
		},
		map_changed: function() {
			var a = this.get("map");
			this.destroy();
			if (a) {
				this.get("isBase") && this.bindTo("layerClone_", a);
				this.bindTo("viewWidth_", a);
				this.bindTo("viewHeight_", a);
				this.bindTo("center", a);
				this.bindTo("zoomLevel", a)
			}
		},
		changed: function(a) {
			var b = this.get("tileSize_");
			switch (a) {
			case "viewWidth_":
				this.set("columns_", r(this.get("viewWidth_"), b.getWidth(), 2));
				this.construct();
				break;
			case "viewHeight_":
				this.set("rows_", r(this.get("viewHeight_"), b.getHeight(), 2));
				this.construct();
				break;
			case "zoomLevel":
			case "center":
				this.resetCenterPosition(this.get("lastZoom_") !== this.get("zoomLevel"));
				this.resetTiles()
			}
		},
		exchange__changed: function() {
			var a = this.get("layerContainer_"),
			b = this.get("layerClone_");
			if (a && b) {
				for (var c = this.get("domsClone_"), d = this.get("tileSize_"), g = 0, e = c.length; g < e; g++) for (var h = 0, i = c[g].length; h < i; h++) {
					var f = c[g][h].dom;
					f.style.width = d.getWidth() + "px";
					f.style.height = d.getHeight() + "px";
					c[g][h].z = null
				}
				this.set("domsClone_", this.get("doms_"));
				this.set("doms_", c);
				this.set("layerContainer_", b);
				this.set("layerClone_", a);
				b.style.display = ""
			}
		},
		resetTiles: function() {
			var a = this.get("zoomLevel");
			if (a && this.get("center")) {
				var b = this.get("lastZoom_"),
				c = this.get("lastBaseX_"),
				d = this.get("lastBaseY_"),
				g = this.get("baseY_"),
				e = this.get("baseX_"),
				h = this.get("rows_"),
				i = this.get("columns_"),
				f = h,
				k = i;
				if (typeof c === "number") {
					f = e - c;
					k = g - d
				}
				if (b === a) if (Math.abs(f) >= 0 && Math.abs(f) < i && Math.abs(k) >= 0 && Math.abs(k) < h) if (Math.abs(f) !== 0 || Math.abs(k) !== 0) this.shiftTiles(f, k);
				this.reloadTiles();
				this.set("lastZoom_", a);
				this.set("lastBaseX_", e);
				this.set("lastBaseY_", g)
			}
		},
		shiftTiles: function(a, b) {
			for (var c = this.get("rows_"), d = this.get("columns_"), g = this.get("doms_"), e = [], h = 0; h < c; h++) for (var i = 0; i < d; i++) {
				var f = h - b;
				f < 0 && (f += c);
				f >= c && (f -= c);
				var k = i - a;
				k < 0 && (k += d);
				k >= d && (k -= d);
				e[f] || (e[f] = []);
				e[f][k] = g[h][i]
			}
			this.set("doms_", e)
		},
		reloadTiles: function() {
			for (var arows = this.get("rows_"), 
					bcolumns = this.get("columns_"), 
					ccenter = this.get("center"), 
					dzoomlevel = this.get("zoomLevel"), 
					gdoms = this.get("doms_"), 
					ebaseleft = this.get("baseLeft_"), 
					hbaseTop = this.get("baseTop_"), 
					itilesize = this.get("tileSize_"), 
					fopa = this.get("opacity"), 
					kmodel = this.get("model_"), 
					lbasex = this.get("baseX_"), 
					jbasey = this.get("baseY_"), 
					moribasex = this.get("oriBaseX_"),
					noribasey = this.get("oriBaseY_"), 
					o = 0; o < arows; o++) 
				for (var t = 0; t < bcolumns; t++) 
					this.loadTile(o, t, ccenter, dzoomlevel, ebaseleft, gdoms, hbaseTop, itilesize, fopa, kmodel, lbasex, jbasey, moribasex, noribasey)
		},
		//                (a, b, c,       d,           g,         e,     h,       i, f, k, l, j, m, n)
		//loadTile555: function(a, b, c, d,                g,         e,   h, i, f, k, l, j, m, n)
		loadTile: function(a, b, ccenter, dzoomlevel, ebaseleft, gdoms, hbaseTop, itilesize, fopa, kmodel, lbasex, jbasey, moribasex, noribasey) {
			if (ccenter && dzoomlevel) {
				ccenter = lbasex + b; //视图中心x
				jbasey  = jbasey + a; //四通中心y
				scope  = u.scope; 
				var o = dzoomlevel * 4;
				if (gdoms) {
					a = gdoms[a][b]; //指向的IMG对象数组
					b = a.dom;       //IMG对象
					if (fopa !== 1) {
						b.style.filter = "alpha(opacity=" + fopa * 100 + ")";
						b.style.opacity = fopa
					} else {
						b.style.filter = "";
						b.style.opacity = ""
					}
					if (a.x !== ccenter || a.y !== jbasey || a.z !== dzoomlevel) {
						if (a.x !== ccenter || a.y !== jbasey || a.z !== null) {
							a.x = ccenter;
							a.y = jbasey;
							f = scope[o++];
							e = scope[o++];
							l = ccenter;
							if (ccenter > e) l = f + (ccenter - e) - 1;
							if (ccenter < f) l = e - (f - ccenter) +1;
							if (!q.Browser().ie && !q.Browser().chrome) {
								b.src = "";
								b.src = w
							}
							b.src = kmodel.getTileImageUrl ? kmodel.getTileImageUrl(l, jbasey, dzoomlevel) : kmodel.getTileUrl(new y(l, jbasey), dzoomlevel)
						}
						a.z = dzoomlevel;
						d = b.style;
						d.left = ebaseleft + (ccenter - moribasex) * itilesize.getWidth() + "px";
						d.top = hbaseTop + (jbasey - noribasey) * itilesize.getHeight() + "px"
					}
				}
			}
		},

		//原来的loadTile xq
		loadTile2: function(a, b, c, d, g, e, h, i, f, k, l, j, m, n) {
			if (c && d) {
				c = l + b;
				j = j + a;
				l = u.scope;
				var o = d * 4;
				if (e) {
					a = e[a][b];
					b = a.dom;
					if (f !== 1) {
						b.style.filter = "alpha(opacity=" + f * 100 + ")";
						b.style.opacity = f
					} else {
						b.style.filter = "";
						b.style.opacity = ""
					}
					if (a.x !== c || a.y !== j || a.z !== d) {
						if (a.x !== c || a.y !== j || a.z !== null) {
							a.x = c;
							a.y = j;
							f = l[o++];
							e = l[o++];
							l = c;
							if (c > e) l = f + (c - e) - 1;
							if (c < f) l = e - (f - c) +
							1;
							if (!q.Browser().ie && !q.Browser().chrome) {
								b.src = "";
								b.src = w
							}
							b.src = k.getTileImageUrl ? k.getTileImageUrl(l, j, d) : k.getTileUrl(new y(l, j), d)
						}
						a.z = d;
						d = b.style;
						d.left = g + (c - m) * i.getWidth() + "px";
						d.top = h + (j - n) * i.getHeight() + "px"
					}
				}
			}
		},
		constructCells: function(a, b) {
			var c = this.get("rows_"),
			d = this.get("columns_");
			if (v.isNumber(c) && v.isNumber(d)) {
				this.get("model_");
				var g = this.get("tileSize_"),
				e = this.get("doms_") || [];
				b && (e = this.get("domsClone_") || []);
				this.get("baseLeft_");
				this.get("baseTop_");
				var h = e.length,
				i = 0;
				if (h !== 0) i = e[0].length;
				var f = Math.max(h, c),
				k = Math.max(i, d),
				l = [];
				g.getWidth();
				g.getHeight();
				for (var j = 0; j < f; j++) {
					j < c && (l[j] = []);
					for (var m = 0; m < k; m++) if (j >= c || m >= d) a.removeChild(e[j][m].dom);
					else if (j >= h || m >= i) {
						var n = null,
						o = ["width:", g.getWidth(), "px;height:", g.getHeight(), "px;position:absolute;-moz-user-select:none;-khtml-user-select:none;"].join("");
						n = document.createElement("img");
						n.src = w;
						n.style.cssText = o + "border:none;";
						n.galleryImg = "no";
						q.setUnSelectable(n);
						x.bindDom(n, "error", this, this.tileOnError_);
						a.appendChild(n);
						l[j][m] = {
							x: null,
							y: null,
							z: null,
							dom: n
						}
					} else l[j][m] = e[j][m]
				}
				if (b) this.set("domsClone_", l);
				else {
					this.set("doms_", l);
					this.reloadTiles()
				}
			}
		},
		construct: function() {
			var a = this.get("map");
			if (a && a.get("tilePane_")) {
				var b = this.get("layerContainer_");
				if (!b) {
					b = document.createElement("div");
					this.get("opacity");
					b.style.cssText = "position:absolute;left:0;top:0;;overflow:visible;z-index:100;";
					if (this.get("isBase")) b.style.zIndex = "-1";
					a.get("tilePane_").appendChild(b);
					q.setUnSelectable(b);
					this.set("layerContainer_", b)
				}
				this.constructCells(b); (a = this.get("layerClone_")) && this.constructCells(a, true)
			}
		},
		layerClone__changed: function() {
			var a = this.get("layerClone_");
			a && !this.get("domsClone_") && this.constructCells(a, true)
		},
		//重定位中心点
		resetCenterPosition: function(a) {
			var b = this.get("center"),
			c = this.get("zoomLevel");
			if (b && c) {
				//获取瓦片索引数字
				b = q.getSeedTileIndex(b, c);
				this.set("centerX_", b[0]);
				this.set("centerY_", b[1]);
				this.caculateLayerBase(a)
			}
		},
		destroy: function() {
			var a = this.get("layerContainer_"),
			b = this.get("domsClone_");
			a && a.parentNode.removeChild(a);
			if (b) {
				a = 0;
				for (var c = b.length; a < c; a++) for (var d = 0, g = b[a].length; d < g; d++) {
					var e = b[a][d].dom;
					e.parentNode.removeChild(e)
				}
			}
			this.unbind("viewWidth_");
			this.unbind("viewHeight_");
			this.unbind("center");
			this.unbind("zoomLevel");
			this.unbind("layerClone_");
			this.set("doms_", null);
			this.set("domsClone_", null);
			this.set("layerContainer_", null);
			this.set("layerClone_", null);
			this.set("lastZoom_", null);
			this.set("lastBaseX_", null);
			this.set("lastBaseY_", null)
		},
		//计算层基准信息
		caculateLayerBase: function(a) {
			var b = this.get("centerX_"),
			c = this.get("centerY_"),
			d = this.get("viewWidth_"),
			g = this.get("viewHeight_"),
			e = this.get("tileSize_"),
			h = b > 0 ? Math.floor(b / 256) : Math.ceil(b / 256),
			i = c > 0 ? Math.floor(c / 256) : Math.ceil(c / 256);
			b = b % e.getWidth();
			c = c % e.getHeight();
			d = d / 2 - b;
			b = g / 2 - c;
			g = r(b, e.getHeight(), 1);
			c = r(d, e.getWidth(), 1);
			this.set("baseX_", h - c);
			this.set("baseY_", i - g);
			if (a) {
				a = this.get("map").get("originPosition_") || [0, 0];
				d = d - c * e.getWidth() + a[0];
				e = b - g * e.getHeight() + a[1];
				this.set("baseLeft_", d);
				this.set("baseTop_", e);
				this.set("oriBaseX_", h - c);
				this.set("oriBaseY_", i - g)
			}
		},
		tileOnLoad_: function() {},
		tileOnError_: function(a, b) {
			var c = this.get("model_");
			b.src = c.get("errorUrl")
		}
	});
	s.setView("TileLayer", p)
})(__MapNS__);