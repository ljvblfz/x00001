(function(t) {
	var u = __MapNSImpl__.Util,
	D = __MapNSImpl__.Config,
	J = t.MVCView,
	v = t.Size,
	m = t.Event,
	Q = __MapNSImpl__.Class(J, {
		initialize: function(a) {
			J.apply(this);
			this.set("model_", a);
			this.set("map_", a);
			for (var b = ["viewWidth_", "viewHeight_", "projection", "mapCanvasProjection_", "autoPan_", "autoPanPixel", "draggable", "draggingCursor", "cursor", "backgroundColor", "scrollWheel", "animation", "zoomInByDblClick", "keyBoard", "center", "zoomLevel", "layerClone_", "overlayRedraw_", "origin_", "originPosition_", "mapContainer_", "canvas_", "container_", "baseLeft_", "baseTop_", "animation_", "zoomAnimation_", "moveAnimation_", "status_"], d = 0, e = b.length; d < e; d++) this.bindTo(b[d], a)
		},
		changed: function(a) {
			var b = this.get(a),
			d = this.get("mapContainer_");
			switch (a) {
			case "backgroundColor":
				d && (d.style.background = b || "url(" + D.domain + "imgs/tile_loading.png)");
				break;
			case "cursor":
				d && u.setCursorStyle(this.get("paneContainer_"), b, "default")
			}
		},
		setOriginPosition: function(a) {
			var b = this.get("paneContainer_");
			b.style.left = -a[0] + "px";
			b.style.top = -a[1] + "px";
			this.set("originPosition_", a)
		},
		center_changed: function() {
			var a = this.get("origin_"),
			b = this.get("center"),
			d = this.get("zoomLevel");
			if (this.get("paneContainer_")) if (a) {
				a = u.getSeedTileIndex(a, d);
				b = u.getSeedTileIndex(b, d);
				this.setOriginPosition([Math.floor(b[0] - a[0]), Math.floor(b[1] - a[1])])
			} else {
				this.set("origin_", this.get("center"));
				this.setOriginPosition([0, 0])
			}
			this.set("status_", true)
		},
		status__changed: function() {
			var a = this.get("status_");
			if (a) {
				this.set("statusTime_", (new Date).getTime());
				var b = this.get("timer_");
				if (!b) {
					var d = this;
					b = setInterval(function() {
						if ((new Date).getTime() - d.get("statusTime_") > 200) {
							clearInterval(b);
							d.set("timer_", 0);
							d.set("status_", false)
						}
					},
					50);
					this.set("timer_", b)
				}
			} else a === false && m.trigger(this.get("map_"), "idle", [])
		},
		zoomLevel_changed: function() {
			var a = this.get("lZoom_"),
			b = this.get("zoomLevel");
			if (this.get("paneContainer_") && a !== b) {
				this.set("origin_", this.get("center"));
				this.setOriginPosition([0, 0]);
				this.notify("overlayRedraw_")
			}
			this.set("lZoom_", b);
			this.set("status_", true)
		},
		paneContainer__changed: function() {
			this.setOriginPosition([0, 0]);
			this.notify("overlayRedraw_")
		},
		container__changed: function() {
			var a = this.get("container_"),
			b = document.createElement("div"),
			d,
			e;
			b.style.cssText = "position:relative;height:100%;width:100%;overflow:hidden;z-index:0;";
			var c = this.get("backgroundColor");
			b.style.background = c || "url(" + D.domain + "imgs/tile_loading.png)";
			c = document.createElement("div");
			u.Browser().firefox ? this.get("cursor") : this.get("cursor");
			c.style.cssText = "left:0;top:0;position:absolute;z-index:0;";
			u.setCursorStyle(c, this.get("cursor"));
			var h = ["tilePane", "mapPane", "overlayLayer", "overlayShadow", "overlayImage", "floatShadow", "overlayMouseTarget", "floatPane"],
			g = this.get("model_");
			this.get("origin_");
			d = 0;
			for (e = h.length; d < e; d++) {
				var i = h[d] + "_";
				this.bindTo(i, g);
				var f = 100 + d,
				l = document.createElement("div");
				l.style.cssText = "left:0;top:0;position:absolute;z-index:" + f + ";";
				if (i === "floatPane_" || i === "overlayMouseTarget_") l.style.cursor = "default";
				c.appendChild(l);
				this.set(i, l)
			}
			this.get("floatPane_");
			if (!u.Browser().android) {
				d = new t.Canvas(this.get("overlayLayer_"));
				this.bindTo("canvas_", g);
				this.set("canvas_", d)
			}
			b.appendChild(c);
			a.appendChild(b);
			this.set("paneContainer_", c);
			this.set("mapContainer_", b);
			c = c.firstChild;
			this.set("tilePane_", c);
			g = document.createElement("input");
			g.style.cssText = "position:absolute;border:none;margin:0;padding:0;z-index:-100;opacity:0.01;";
			if (u.Browser().ie) g.type = "button";
			b.appendChild(g);
			this.set("focusElement_", g);
			g = document.createElement("div");
			g.style.cssText = "left:0;top:0;position:absolute;";
			c.appendChild(g);
			this.set("layerClone_", g);
			this.registEvents();
			this.notify("viewWidth_");
			this.notify("center");
			if (!this.get("model_").get("__hideLogo__")) {
				c = document.createElement("div");
				c.innerHTML = '<a href="http://map.soso.com" title="' + __MapNSImpl__.RC.Control.GotoHome + '" target="_blank"><img src="' + D.domain + 'imgs/logo.png" style="border:none;"></img></a>';
				c.style.cssText = "position:absolute;z-index:10;left:5px;bottom:-4px;height:20px;";
				b.appendChild(c);
				new t.CopyrightControl({
					align: t.ALIGN.BOTTOM_LEFT,
					margin: new v(85, 0),
					map: this.get("model_")
				})
			}
			m.addDomListener(a, "contextmenu", m.preventDefault)
		},
		projection_changed: function() {
			var a = this.get("mapCanvasProjection_");
			a && a.set("projection", this.get("projection"))
		},
		registEvents: function() {
			this.registDragEvent();
			this.registDblClickEvent();
			this.registMouseWheelEvent();
			this.registKeyEvent();
			for (var a = [this.get("tilePane_"), this.get("mapPane_"), this.get("overlayShadow_"), this.get("floatShadow_"), this.get("overlayLayer_"), this.get("overlayImage_")], b = ["mousedown", "mouseup", "click", "dblclick", "mouseover", "mousemove", "mouseout", "contextmenu"], d = 0, e = a.length; d < e; d++) this.registMouseEvents(a[d], b);
			this.registTouchEvent()
		},
		autoPan: function(a) {
			var b = this.get("autoPanSpeed_") || [0, 0];
			b = [b[0], b[1]];
			if (! (b[0] === 0 && b[1] === 0 && a[0] === 0 && a[1] === 0)) {
				var d = this,
				e = function(c, h, g) {
					if (g === 0) c[h] = 0;
					if (c[h] / g <= 0) c[h] = g
				};
				e(b, 0, a[0]);
				e(b, 1, a[1]);
				if (b[0] === 0 && b[1] === 0) {
					if (this.get("autoPanTimer_")) {
						clearTimeout(d.get("autoPanTimer_"));
						d.set("autoPanTimer_", null)
					}
					a = this.get("autoPanSpeed_");
					a = new v(a[0] * 6, a[1] * 6)
				}
				this.set("autoPanSpeed_", b);
				this.get("autoPanTimer_") || this.set("autoPanTimer_", setInterval(function() {
					var c = d.get("autoPanSpeed_");
					if (c[0] === 0 && c[1] === 0) {
						clearTimeout(d.get("autoPanTimer_"));
						d.set("autoPanTimer_", null)
					} else {
						var h = c[0] !== 0 ? Math.abs(c[0]) / c[0] : 0,
						g = c[1] !== 0 ? Math.abs(c[1]) / c[1] : 0;
						c[0] += h * 0.5;
						c[1] += g * 0.5;
						Math.abs(c[0]) > 15 && (c[0] = h * 15);
						Math.abs(c[1]) > 15 && (c[1] = g * 15)
					}
					d.set("autoPanSpeed_", c);
					c = new v(c[0], c[1]);
					d.get("model_").moveBy(c, true);
					if (c.getWidth() !== 0 || c.getHeight() !== 0) d.set("autoPanPixel", c)
				},
				20))
			}
		},
		autoPan__changed: function() {
			var a = this.get("autoPanListener_");
			if (this.get("autoPan_")) {
				if (!a) {
					var b = this;
					a = m.addDomListener(document.body.parentNode || document.body, "mousemove",
					function(d) {
						if (b.get("autoPan_")) {
							var e = [0, 0],
							c = b.getMouseContainerPixel(d);
							d = b.get("viewWidth_");
							var h = b.get("viewHeight_"),
							g = c.getX();
							c = c.getY();
							g < 30 && (e[0] = 1);
							g > d - 30 && (e[0] = -1);
							c < 30 && (e[1] = 1);
							c > h - 30 && (e[1] = -1);
							b.autoPan(e)
						}
					})
				}
			} else {
				a && m.removeListener(a);
				this.set("autoPanListener_", null);
				if (this.get("autoPanTimer_")) {
					clearTimeout(this.get("autoPanTimer_"));
					this.set("autoPanTimer_", null)
				}
			}
		},
		registKeyEvent: function() {
			for (var a = this, b = [0, 0], d = [this.get("tilePane_"), this.get("mapPane_"), this.get("overlayShadow_"), this.get("floatShadow_"), this.get("overlayLayer_"), this.get("overlayImage_")], e = 0; e < d.length; e++) m.addDomListener(d[e], "click",
			function() {
				c.focus()
			});
			var c = this.get("focusElement_");
			m.addDomListener(c, "keydown",
			function(h) {
				if (a.get("keyBoard")) {
					var g = a.get("model_"),
					i = a.get("viewHeight_") / 2,
					f = [b[0], b[1]];
					switch (h.keyCode || h.which || h.charCode) {
					case 33:
						g.moveBy(new v(0, i));
						break;
					case 34:
						g.moveBy(new v(0, -i));
						break;
					case 37:
						f[0] = 1;
						break;
					case 38:
						f[1] = 1;
						break;
					case 39:
						f[0] = -1;
						break;
					case 40:
						f[1] = -1
					}
					if (f[0] !== b[0] || f[1] !== b[1]) {
						a.autoPan(f);
						b = f
					}
				}
			});
			m.addDomListener(c, "keyup",
			function(h) {
				if (a.get("keyBoard")) {
					var g = [b[0], b[1]];
					switch (h.keyCode || h.which || h.charCode) {
					case 38:
					case 40:
						g[1] = 0;
						break;
					case 37:
					case 39:
						g[0] = 0
					}
					if (g[0] !== b[0] || g[1] !== b[1]) {
						a.autoPan(g);
						b = g
					}
				}
			});
			m.addDomListener(c, "blur",
			function() {
				a.autoPan([0, 0])
			})
		},
		registTouchEvent: function() {
			for (var a = null, b = null, d = null, e = this, c = function(n) {
				if (n.touches.length > 1) {
					if (n.touches.length === 2) {
						a = [n.touches[0], n.touches[1]];
						b = [(a[0].pageX + a[1].pageX) / 2, (a[1].pageY + a[0].pageY) / 2];
						d = [a[0].pageX - a[1].pageX, a[0].pageY - a[1].pageY];
						d = Math.sqrt(d[0] * d[0] + d[1] * d[1])
					}
				} else i()
			},
			h = function() {
				var n = [(a[0].pageX + a[1].pageX) / 2, (a[1].pageY + a[0].pageY) / 2],
				j = [a[0].pageX - a[1].pageX, a[0].pageY - a[1].pageY];
				return {
					s: Math.sqrt(j[0] * j[0] + j[1] * j[1]) / d,
					o: [n[0] -
					b[0], n[1] - b[1]],
					m: n
				}
			},
			g = function(n) {
				if (a !== null) {
					var j = h(),
					k = e.get("model_").getBaseLayer().get("layerContainer_"),
					w = "translate(" + j.o[0] * j.s + "px," + j.o[1] * j.s + "px) scale(" + j.s + ")",
					s = u.getDomCoordinate(k);
					k.style.webkitTransformOrigin = j.m[0] - s[0] + "px " + (j.m[1] - s[1]) + "px";
					k.style.webkitTransform = w;
					m.stopEvent(n)
				}
			},
			i = function() {
				if (a !== null) {
					var n = h(),
					j = e.get("model_").getBaseLayer().get("layerContainer_");
					j.style.webkitTransform = "";
					j.style.webkitTransformOrigin = "";
					j = e.get("model_");
					var k = Math.round(Math.log(n.s) / Math.log(2));
					j.moveBy(new v(n.o[0], n.o[1]), true);
					n = j.get("zoomLevel") + k;
					j.zoomTo(n, null, null, true);
					a = null
				}
			},
			f = [this.get("tilePane_"), this.get("mapPane_"), this.get("overlayShadow_"), this.get("floatShadow_"), this.get("overlayLayer_"), this.get("overlayImage_")], l = 0, o = f.length; l < o; l++) {
				m.addDomListener(f[l], "touchstart", c);
				m.addDomListener(f[l], "touchmove", g);
				m.addDomListener(f[l], "touchend", i)
			}
		},
		registOrientationEvent: function() {
			m.addDomListener(window, "orientationchange",
			function() {
				this.get("model_".changeViewSize())
			})
		},
		registDragEvent: function() {
			var a = null,
			b = this,
			d = null,
			e = null,
			c = null,
			h = false,
			g = this.get("map_");
			u.enableDrag([this.get("tilePane_"), this.get("mapPane_"), this.get("overlayShadow_"), this.get("floatShadow_"), this.get("overlayLayer_"), this.get("overlayImage_")], document || document.body, {
				dragstart: function(i) {
					if (b.get("draggable")) if (! (i.touches && i.touches.length !== 1)) {
						a = [0, 0];
						c = [0, 0];
						d = (new Date).getTime();
						m.trigger(g, "dragstart");
						u.setCursorStyle(b.get("paneContainer_"), b.get("draggingCursor"), "move")
					}
				},
				dragging: function(i, f) {
					if (b.get("draggable")) {
						var l = b.get("model_"),
						o = [f[0] - a[0], f[1] - a[1]];
						l.moveBy(new v(o[0], o[1]), true);
						a = f;
						l = (new Date).getTime();
						var n = l - d;
						if (! (n < 10)) {
							d = l;
							if (o[0] === 0 && o[1] === 0) o = c;
							e = [o[0] / n, o[1] / n];
							c = o;
							h = true;
							m.trigger(g, "dragging")
						}
					}
				},
				dragend: function() {
					if (b.get("draggable")) {
						if (b.get("animation") && h) {
							var i = e[0] * 80,
							f = e[1] * 80,
							l = b.get("viewWidth_"),
							o = b.get("viewHeight_");
							if (Math.abs(i) >= l) i = i > 0 ? l - 1: -l + 1;
							if (Math.abs(f) >= o) f = f > 0 ? o - 1: -o + 1;
							b.get("model_").moveBy(new v(i, f));
							m.trigger(g, "dragend")
						} else b.get("animation");
						u.setCursorStyle(b.get("paneContainer_"), b.get("cursor"))
					}
				}
			},
			0)
		},
		registDblClickEvent: function() {
			for (var a = this, b = function(h) {
				a.get("zoomInByDblClick") && a.get("model_").zoomIn(a.getMouseLatLng(h))
			},
			d = [this.get("tilePane_"), this.get("mapPane_"), this.get("overlayShadow_"), this.get("floatShadow_"), this.get("overlayLayer_"), this.get("overlayImage_")], e = 0, c = d.length; e < c; e++) m.addDomListener(d[e], "dblclick", b)
		},
		registMouseWheelEvent: function() {
			var a = this,
			b = this.get("mapContainer_"),
			d = this.get("model_"),
			e = null,
			c = function(h) {
				if (a.get("scrollWheel")) {
					m.stopEvent(h);
					var g = a.get("zoomFocus_"),
					i = a.get("zoomAnimation_");
					if (i && i.getStatus() === 1) g = a.get("zoomingFocus_");
					if (!g) {
						g = a.getMouseLatLng(h);
						a.set("zoomFocus_", g);
						a.set("zoomingFocus_", g)
					}
					var f = null;
					f = h.wheelDelta <= 0 || h.detail > 0 ? -1: 1;
					e && clearTimeout(e);
					e = setTimeout(function() {
						var l = a.get("zoomLevel") + f;
						d.zoomTo(l, g, true)
					},
					40)
				}
			};
			m.addDomListener(b, "mousewheel", c);
			m.addDomListener(b, "DOMMouseScroll", c);
			m.addDomListener(b, "mousemove",
			function() {
				a.get("zoomFocus_") && a.set("zoomFocus_", null)
			})
		},
		animation__changed: function() {
			var a = this.get("animation_"),
			b = this.get("moveAnimation_"),
			d = this.get("zoomAnimation_");
			b && b.stop(true);
			if (a && a.type === "move") {
				d && d.getStatus() == 1 && d.stop();
				this.moveToAnimation(a)
			}
			a && a.type === "zoom" && this.zoomToAnimation(a)
		},
		moveToAnimation: function(a) {
			a = a.center;
			var b = this.get("moveAnimation_");
			if (!b) {
				var d = this;
				b = new t.Fx({
					duration: 0.4,
					fpts: 40,
					method: t.Fx.easeOutQuad,
					callback: function(c) {
						c = new t.LatLng(c[1], c[0]);
						d.get("model_").set("center", c)
					}
				});
				this.set("moveAnimation_", b);
				m.addListener(b, "end",
				function() {})
			}
			var e = this.get("center");
			b.set("begins", [e.getLng(), e.getLat()]);
			b.set("ends", [a.getLng(), a.getLat()]);
			b.start()
		},
		zoomToAnimation: function(a) {
			var b = this.get("zoomAnimation_"),
			d = a.delta,
			e = this.get("zoomMultiple_") || 1;
			e *= Math.pow(2, d);
			this.set("zoomMultiple_", e);
			var c = this.get("map_").getBaseLayer(),
			h = this.get("layerClone_"),
			g = c.get("tileSize_"),
			i = c.get("domsClone_"),
			f = a.originPosition;
			if (f && i) {
				c = i && typeof i.left != "undefined";
				var l = this.get("originPosition_"),
				o = this.get("viewWidth_"),
				n = this.get("viewHeight_"),
				j = h.style,
				k = h.firstChild && h.firstChild.style;
				if (b && b.getStatus() === 1) {
					j.left = l[0] + "px";
					j.top = l[1] + "px";
					if (c) {
						var w = this.get("realOffset_");
						f = "scale(" + e + "," + e + ") translate(" + w[0] + "%," + w[1] + "%)";
						k.MozTransition = "-moz-transform 0.3s ease-in-out 0s,opacity 0.3s ease-out 0.3s";
						k.MozTransformOrigin = q + "% " + r + "%";
						k.MozTransform = f;
						k.webkitTransition = "-webkit-transform 0.3s ease-in-out 0s,opacity 0.3s ease-out 0.3s";
						k.webkitTransformOrigin = q + "% " + r + "%";
						k.webkitTransform = f;
						k.OTransition = "-o-transform 0.3s ease-in-out 0s,opacity 0.3s ease-out 0.3s";
						k.OTransformOrigin = q + "% " + r + "%";
						k.OTransform = f;
						k.opacity = 0.3
					} else {
						c = [this.get("realtimeMultiple_")];
						q = b.get("ends");
						q[0] = e;
						if (!w) if (e = this.get("realtimeOffset_")) {
							c.push(e[0]);
							c.push(e[1])
						}
						b.set("begins", c);
						b.set("ends", q)
					}
					b.start()
				} else {
					j.zIndex = 1E4;
					j.display = "";
					j.background = this.get("backgroundColor") || "url(" + D.domain + "imgs/tile_loading.png)";
					j.width = o + "px";
					j.height = n + "px";
					if (c) {
						j.left = l[0] + "px";
						j.top = l[1] + "px";
						k.left = Math.floor(i.left - f[0]) + "px";
						k.top = Math.floor(i.top - f[1]) + "px"
					} else {
						j.left = l[0] + "px";
						j.top = l[1] + "px"
					}
					this.get("tilePane_").style.zIndex = 200;
					e = Math.pow(2, d);
					w = a.isInPlace;
					var s = this,
					E = g.getWidth(),
					F = g.getHeight();
					this.get("model_");
					var x = a.focusPixel;
					d = a = 0;
					if (!w) {
						a = o / 2 - x.getX();
						d = n / 2 - x.getY()
					}
					if (!b && !c) {
						b = new t.Fx({
							duration: 0.4,
							fps: 40,
							method: t.Fx.easeOutSine
						});
						this.set("zoomAnimation_", b);
						var G = function() {
							var p = s.get("layerClone_").style;
							p.zIndex = 0;
							s.set("zoomMultiple_", 1);
							p.width = "";
							p.height = "";
							p.left = "";
							p.top = "";
							p.background = "";
							p.display = "none";
							s.get("tilePane_").style.zIndex = 100
						};
						m.addListener(b, "end", G)
					}
					var y,
					A;
					if (c) {
						r = i.left;
						q = i.top;
						y = [x.getX() + f[0] - r, x.getY() + f[1] - q];
						G = function() {
							s.set("zoomMultiple_", 1);
							k.opacity = 1;
							j.zIndex = 0;
							s.get("tilePane_").style.zIndex = 100;
							this.set("status_", true);
							s.set("canvasTimer_", null);
							A.setStatus(0)
						};
						A = {
							setStatus: function(p) {
								this.status = p
							},
							getStatus: function() {
								return typeof this.status == "undefined" ? 1: this.status
							},
							stop: function(p) {
								if (this.timer) {
									clearTimeout(this.timer);
									p || G()
								}
								this.setStatus(0)
							},
							start: function() {
								A.stop(true);
								this.setStatus(1);
								var p = this;
								this.timer = setTimeout(function() {
									j.background = "";
									p.timer_ = setTimeout(G, 300)
								},
								250)
							}
						};
						this.set("zoomAnimation_", A)
					} else {
						r = i[0][0].dom.style;
						q = parseInt(r.top, 10);
						r = parseInt(r.left, 10);
						y = [x.getX() + f[0] - r, x.getY() + f[1] - q]
					}
					if (c) {
						o = E * i.columns;
						f = F * i.rows;
						var q = y[0] * 100 / o,
						r = y[1] * 100 / f;
						o = a * 50 / o;
						f = d * 50 / f;
						this.set("realOffset_", [o, f]);
						f = "scale(" + e + "," + e + ") translate(" +
						o + "%," + f + "%)";
						k.MozTransition = "-moz-transform 0.3s ease-in-out 0s,opacity 0.3s ease-out 0.3s";
						k.MozTransformOrigin = q + "% " + r + "%";
						k.MozTransform = f;
						k.webkitTransition = "-webkit-transform 0.3s ease-in-out 0s,opacity 0.3s ease-out 0.3s";
						k.webkitTransformOrigin = q + "% " + r + "%";
						k.webkitTransform = f;
						k.OTransition = "-o-transform 0.3s ease-in-out 0s,opacity 0.3s ease-out 0.3s";
						k.OTransformOrigin = q + "% " + r + "%";
						k.OTransform = f;
						k.opacity = "0.3";
						A.start()
					}
					q = function(p) {
						s.set("status_", true);
						var B = p[0],
						C = [0, 0];
						if (p.length !== 1) {
							C[0] = Math.floor(p[1]);
							C[1] = Math.floor(p[2]);
							s.set("realOffset_", C)
						}
						p = Math.floor(E * B);
						var K = Math.floor(F * B),
						L = Math.round(y[0] % E * B) + p * Math.floor(y[0] / E),
						M = Math.round(y[1] % F * B) + K * Math.floor(y[1] / F);
						L = L || 0;
						M = M || 0;
						for (var H = 0, O = i.length; H < O; H++) {
							for (var N = i[H], I = 0, P = N.length; I < P; I++) {
								z = N[I].dom;
								z = z.style;
								z.left = x.getX() - L + I * p + C[0] + "px";
								z.top = x.getY() - M + H * K + C[1] + "px";
								z.width = p + "px";
								z.height = K + "px"
							}
							var z = N
						}
						s.set("realtimeMultiple_", B)
					};
					this.set("realtimeMultiple_", 1);
					if (!c) {
						this.set("realOffset_", null);
						b.set("callback", q);
						c = [1];
						q = [e];
						if (!w) {
							c.push(0);
							c.push(0);
							q.push(a);
							q.push(d)
						}
						b.set("begins", c);
						b.set("ends", q);
						b.start()
					}
				}
			}
		}
	});
	J.setView("Map", Q)
})(__MapNS__);