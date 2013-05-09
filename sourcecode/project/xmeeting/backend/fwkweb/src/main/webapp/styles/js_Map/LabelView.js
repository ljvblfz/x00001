(function() {
	var h = __MapNSImpl__.Util,
	n = __MapNS__.Point,
	r = __MapNS__.Size,
	s = __MapNS__.Marker,
	t = h.getSize,
	j = __MapNS__.MVCObject.checkers,
	o = __MapNS__.MVCView,
	p = j.checkInterface(__MapNSImpl__.Interfaces.LatLng),
	u = h.setPosition,
	v = __MapNSImpl__.Class(o, {
		initialize: function(a) {
			o.apply(this);
			this.set("model_", a);
			this.bindsTo(["style", "target_", "position", "content", "offset", "labelDom_", "size", "anchor", "visible", "opacity", "zIndex", "map", "panes_", "destroy_", "constructed_", "draw_"], a)
		},
		constructed__changed: function() {
			if (this.get("constructed_")) {
				var a = this.get("panes_"),
				b = document.createElement("div");
				b.style.cssText = "position:absolute;white-space:nowrap"; (a = a.overlayMouseTarget) && b.parentNode !== a && a.appendChild(b);
				this.set("labelDom_", b);
				this.registMouseEvents(b, ["mouseover", "mouseout", "mousemove", "click", "dblclick", ",mousedown", "mouseup", "contextmenu"]);
				this.notify("content");
				this.notify("style");
				this.notify("visible");
				this.notify("opacity");
				this.notify("zIndex");
				this.draw()
			}
		},
		destroy__changed: function() {
			var a = this.get("panes_");
			if (a) {
				var b = this.get("labelDom_");
				b && a.overlayMouseTarget.removeChild(b)
			}
		},
		changed: function(a) {
			this.get(a);
			var b = this.get("labelDom_");
			switch (a) {
			case "content":
				if ((a = this.get("content")) && b) if (h.isHtmlControl(a)) {
					a.parentNode && a.parentNode.removeChild(a);
					b.innerHTML = "";
					b.appendChild(a)
				} else b.innerHTML = a;
				break;
			case "visible":
				if (b) b.style.display = this.get("visible") ? "": "none";
				break;
			case "zIndex":
				if (b) if (a = this.get("map")) {
					var c = this.get("zIndex");
					if (typeof c != "undefined" || c != null) c += 1E3;
					else {
						if ((c = this.get("position")) && !p(c)) c = c.getPosition();
						c = c ? a.fromLatLngToContainerPixel(c).getY() : ""
					}
					b.style.zIndex = c
				}
				break;
			case "style":
				b && h.setStyle(b, this.get("style"));
				break;
			case "opacity":
				b && h.setOpacity(b, this.get("opacity"));
				break;
			case "offset":
			case "altitude_":
			case "draggable":
			case "clickable":
				this.redraw()
			}
		},
		position_changed: function() {
			var a = this.get("position");
			if (a && !p(a)) if (a.getPosition) {
				this.bindTo("position", a);
				this.bindTo("altitude_", a);
				this.set("target_", a)
			}
			this.notify("zIndex");
			this.redraw()
		},
		draw: function() {
			var a = this.get("map"),
			b = this.get("labelDom_"),
			c = this.get("position"),
			f = this.get("panes_"),
			q = this.get("visible"),
			d = this.get("target_"),
			g = this.get("offset");
			if (a && c && f) {
				f = t(b);
				this.set("size", new r(f.width, f.height));
				if (!q) b.style.display = "none";
				var i,
				k,
				e,
				l,
				m;
				j.isInstanceOf(r)(g) && (m = g.getWidth(), l = g.getHeight());
				j.isInstanceOf(n)(g) && (m = g.getX(), l = g.getY());
				if (p(c)) {
					c = a.fromLatLngToPixel(c);
					e = new n(0, f.height / 2);
					i = c.getX() + m - e.getX();
					k = c.getY() + l - e.getY()
				}
				if (d) {
					c = d.getPosition();
					q = this.get("altitude_") || 0;
					if (j.isInstanceOf(s)(d)) {
						d = d.getIcon();
						if (c && d) {
							c = a.fromLatLngToPixel(c);
							e = d.get("anchor_");
							a = d.get("size_");
							i = c.getX() + m - e.getX() + a.getWidth();
							k = c.getY() + l - e.getY() + a.getHeight() / 2 - f.height / 2;
							if (q) k -= this.get("altitude_")
						}
					}
				}
				this.set("anchor", e);
				i = new n(i, k);
				u(b, i)
			}
		},
		draw__changed: function() {
			this.draw()
		}
	});
	o.setView("Label", v)
})();