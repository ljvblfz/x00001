/*SOSO Map API beta 1.0.120307.1 @Tencent Research*/
(function(o){function v(b){var a=b.get("anchor_");if(!a){b=b.get("size_");a=new r(b.getWidth()/2,b.getHeight()/2)}return a}function w(b,a,c){var e=null;if(b)e=b.firstChild;else{b=document.createElement("div");e=document.createElement("div");b.appendChild(e);e.style.cssText="width:100%;height:100%;overflow:hidden;"}c&&b.parentNode!==c&&c.appendChild(b);c=a.get("origin_");c=[c.getX(),c.getY()];b.style.position="absolute";n.setCssSprite(e,a.get("url_"),c,false,a.get("scaledSize"));x(b,a.get("size_"));
return b}var n=__MapNSImpl__.Util,z=__MapNSImpl__.Config,k=o.Event,r=o.Point,y=o.MVCView,u=n.setPosition,p=o.Marker.Animation,x=n.setSize,A=0,C=__MapNSImpl__.Class(y,{initialize:function(b){y.apply(this);this.set("model_",b);this.bindsTo(["icon","shadow","shape","dragTargetIcon","iconDom_","constructed_","position","cursor","animationType_","title","clickable","flat","draggable","visible","altitude_","zIndex","map","panes_","destroy_","draw_","iconReady_","shadowReady_","dragTargetIconReady_","dragEvent_",
"dragAnimation"],b);this.set("altitude_",0)},dragEvent__changed:function(){if(this.get("dragEvent_")&&this.get("dragCallbacks_")&&this.get("draggable")){var b=this.get("dragEvent_"),a=this.getMouseContainerPixel(b);a=new r(a.getX(),a.getY());this.set("position",this.get("map").fromContainerPixelToLatLng(a));b.__startdrag__=true;n.startDrag(this.get("dragTarget_"),document.body.parentNode||document.body,this.get("dragCallbacks_"),b);this.set("dragEvent_",null)}else if(this.get("dragEvent_")===false){n.stopDrag(document.body.parentNode||
document.body,this.get("dragTarget_"));this.set("dragEvent_",null)}},constructed__changed:function(){if(this.get("constructed_")){this.set("map_",this.get("map"));this.drawShadow();this.drawIcon()}},map_changed:function(){this.get("map")&&this.set("map_",this.get("map"))},destroy__changed:function(){var b=this.get("shadowDom_"),a=this.get("panes_");b&&a.overlayShadow.removeChild(b);(b=this.get("iconDom_"))&&a.overlayImage.removeChild(b);(b=this.get("area_"))&&a.overlayMouseTarget.removeChild(b);(b=
this.get("target_"))&&b.parentNode==a.overlayImage&&a.overlayImage.removeChild(b);if(a=this.get("animation_")){a&&k.clearListeners(a,"end");a.stop();this.set("jumpDestination_",null)}n.stopDrag(document.body.parentNode||document.body,this.get("dragTarget_"));if((a=this.get("mask_"))&&a.parentNode){a.parentNode.removeChild(a);if(a=this.get("map_")){if(a.get("autoPan_")){a.set("autoPan_",false);(a=this.get("centerListener_"))&&k.removeListener(a)}this.set("map_",null)}}},shadow_changed:function(){if(this.get("shadow")===
null&&this.get("shadowDom_")){this.get("panes_").overlayShadow.removeChild(this.get("shadowDom_"));this.set("shadowDom_",null)}},drawShadow:function(){var b=this.get("shadowDom_"),a=this.get("shadow"),c=this.get("panes_");if(this.get("shadowReady_")&&c){b=w(b,a,c.overlayShadow);this.set("shadowDom_",b);this.redraw()}},shadowReady__changed:function(){this.drawShadow()},drawIcon:function(){var b=this.get("iconDom_"),a=this.get("icon"),c=this.get("panes_");if(this.get("iconReady_")&&c){var e=!b;b=w(b,
a,c.overlayImage);this.set("iconDom_",b);this.notify("shape");b=this.get("map").getBounds();if(e&&this.get("animationType_")===o.Animation.DROP&&b.contains(this.get("position"))){this.set("altitude_",1E3);this.drop()}}},iconReady__changed:function(){this.drawIcon()},dragTargetIconReady__changed:function(){var b=this.get("target_"),a=this.get("dragTargetIcon"),c=this.get("panes_");if(this.get("dragTargetIconReady_")&&c){b=w(b,a,c.overlayImage);b.style.display="none";this.set("target_",b);this.notify("isUpping_")}},
changed:function(b){var a=this.get("iconDom_");if(a){var c=this.get("shadowDom_"),e=this.get("map"),d=this.get("visible"),g=this.get("position"),h=this.get("area_");switch(b){case "title":case "cursor":if(h){h=h.firstChild.nextSibling.firstChild;h.style.cursor=this.get("cursor");h.title=this.get("title")}break;case "zIndex":d=this.get("zIndex");if(typeof d!="undefined"||d!=null)d+=1E3;else d=e.fromLatLngToContainerPixel(g).getY();if(this.get("isUpping_"))d=1E5;h.style.zIndex=d;a.style.zIndex=d;break;
case "draggable":case "clickable":case "flat":case "visible":e=this.get("flat");a.style.display=d?"":"none";if(c)c.style.display=e||!d?"none":"";a=this.get("draggable");c=this.get("clickable");h.style.display=!d||!c&&!a?"none":"";break;case "altitude_":this.draw();break;case "panes_":this.notify("dragTargetIconReady_")}}},shape_changed:function(){var b=this.get("panes_");if(this.get("iconDom_")&&b){var a=this.get("area_"),c,e,d,g=this.get("icon"),h=g.get("url_");if(a){e=a.firstChild;d=e.nextSibling;
c=d.firstChild}else{a=document.createElement("div");c="";if(n.Browser().ie&&n.Browser().ie<=8)if(h.toUpperCase().indexOf("PNG")>-1)c="filter:alpha(opacity=1)";a.style.cssText="position:absolute;overflow:hidden;line-height:0;opacity:0.01;"+c+";display:none;font-size:0;";e=document.createElement("img");e.style.cssText="positiion:absolute;-moz-user-select:none;-khtml-user-select:none;border:none;";e.galleryImg="no";n.setUnSelectable(e);a.appendChild(e);c="imgmap"+A++;e.setAttribute("usemap","#"+c);if(n.Browser().ie&&
n.Browser().ie<9){e.setAttribute("useMap","#"+c);d=document.createElement('<map name="'+c+'"></map>')}else{d=document.createElement("map");d.setAttribute("name",c)}a.appendChild(d);c=document.createElement("area");c.href="javascript:void(0)";if(n.Browser().ie)d.parentNode.style.cursor=this.get("cursor");else c.style.cursor=this.get("cursor");d.appendChild(c);this.set("area_",a);this.registMouseEvents(c,["mouseover","mouseout","mousemove","click","dblclick","mousedown","mouseup","contextmenu"]);this.enableDrag(c);
this.notify("dragEvent_")}a.parentNode!==b.overlayMouseTarget&&b.overlayMouseTarget.appendChild(a);e.src=h;b=g.get("size_");x(e,b);x(a,b);c.title=this.get("title");e=this.get("shape");a="";g=[];if(e){a=e.get("type_");g=e.get("coord_")}else{a="poly";e=b.getWidth();b=b.getHeight();g=[0,0,0,b,e,b,e,0]}c.setAttribute("shape",a);c.setAttribute("coords",g.join(","));this.redraw()}},isUpping__changed:function(){var b=this.get("dragAnimation");if(b===p.DROP||b===p.STICK){b=this.get("target_");if(this.get("isUpping_")){if(b)b.style.display=
""}else if(b)b.style.display="none"}},enableDrag:function(b){var a=this,c=this.get("map"),e=null,d=null,g=null,h=this.get("model_"),q=false,f=null,l=null,i={dragstart:function(m){if(f==null){f=document.createElement("div");f.style.cssText=["width:100%;height:100%;opacity:0.001;filter:alpha(opacity=0.1);z-index:0;position:absolute;top:0;left:0;background:black;cursor:url(",z.domain,"imgs/grabbing.cur), move"].join("");a.set("mask_",f);k.addDomListener(f,"contextmenu",function(j){k.stopEvent(j)})}b.style.cursor=
"move";g=false;a.set("isUpping_",false);if(a.get("dragEvent_"))q=true;var s=c.fromLatLngToContainerPixel(a.get("position")),t=a.getMouseContainerPixel(m);e=[t.getX()-s.getX(),t.getY()-s.getY()];m.__startdrag__||(l=a.get("position"))},dragging:function(m,s,t){if(a.get("draggable")){if(!g){var j=a.get("panes_").overlayImage.parentNode;j.nextSibling?j.parentNode.insertBefore(f,j.nextSibling):j.parentNode.appendChild(f)}if(!a.get("isUpping_")&&!t){j=a.get("dragAnimation");if(j===p.DROP||j===p.STICK)if(q){a.bounce(15,
0,null,true);q=false}else a.bounce(15,0.25,null,true);a.set("isUpping_",true);a.get("map").set("autoPan_",c.get("autoPan"));d&&k.removeListener(d);var B=arguments.callee;d=k.addListener(c,"center_changed",function(){B.apply(this,[d.pixel])});a.set("centerListener_",d)}if(!t&&!g){k.trigger(h,"dragstart");a.get("panes_")}if(!t||g){j=m.getX?m:a.getMouseContainerPixel(m);d&&(d.pixel=j);j=new r(j.getX()-e[0],j.getY()-e[1]);j=c.fromContainerPixelToLatLng(j);a.set("position",j);k.trigger(h,"dragging")}g||
(g=true)}},dragend:function(m){if(a.get("isUpping_")){var s=a.get("dragAnimation");if(s===p.DROP)a.bounce(15,0.25,function(){a.bounce(8,0.15,function(){k.trigger(h,"idle")})});else s===p.STICK&&a.bounce(30,0.2,function(){k.trigger(h,"idle")});a.set("isUpping_",false);e=null;a.get("map").set("autoPan_",false);d&&k.removeListener(d);d=null;m.button!=2&&k.trigger(h,"dragend");q=false}if(m&&m.button==2){a.get("map").set("autoPan_",false);d&&k.removeListener(d);d=null;if(l){o.Event.stopEvent(m);a.set("position",
l)}else h.setMap(null);k.trigger(h,"dragcancel");setTimeout(function(){f.parentNode&&f.parentNode.removeChild(f)},100)}else f.parentNode&&f.parentNode.removeChild(f);n.setCursorStyle(b,"pointer");l=null}};this.set("dragCallbacks_",i);this.set("dragTarget_",b);n.enableDrag(b,document.body.parentNode||document.body,i)},jump:function(b,a,c){b=[b.getLng(),b.getLat()];a=[a.getLng(),a.getLat()];var e=this.get("jumpAnimation_"),d=this;if(!e){e=new o.Fx({fps:40,callback:function(g){g=new o.LatLng(g[1],g[0]);
d.set("position",g)},method:o.Fx.easeOutQuad});this.set("jumpAnimation_",e)}e.set("duration",c);e.set("begins",b);e.set("ends",a);e.start()},bounce:function(b,a,c,e){if(b){var d=this.get("animation_"),g=this;if(!d){d=new o.Fx({fps:40,callback:function(f){g.set("altitude_",f[0]);if(typeof f[1]==="number"){f=new o.LatLng(f[2],f[1]);g.set("position",f)}}});this.set("animation_",d)}var h=this.get("altitude_"),q=o.Fx.easeOutQuad;if(h>=b){b=0;q=o.Fx.easeInQuad}d.set("duration",a);d.set("method",q);d.set("begins",
[h]);d.set("ends",[b]);e&&(b=0);k.addListenerOnce(d,"end",function(){setTimeout(function(){g.bounce(b,a,c)},b==0?5:0)});d.start()}else c&&c()},drop:function(){var b=this;this.bounce(1E3,0.5,function(){b.bounce(15,0.1)})},animationType__changed:function(){var b=this.get("animationType_"),a=this.get("animation_");if(a){a&&k.clearListeners(a,"end");a.stop();this.set("jumpDestination_",null)}this.set("altitude_",0);if(b===p.BOUNCE)if(this.get("iconDom_")){var c=this;(function(){c.get("animationType_")===
p.BOUNCE&&c.bounce(20,0.3,arguments.callee)})()}},jumpDestination__changed:function(){var b=this.get("jumpDestination_");if(b!==null){var a=this;this.jump(b[0],b[1],0.6);this.bounce(40,0.3,function(){a.set("jumpDestination_",null)})}},position_changed:function(){this.get("map");var b=this.get("position");if(this.get("animationType_")===p.JUMP&&!this.get("jumpDestination_")&&!this.get("draggable")){var a=this.get("lastPosition_");if(a!==b){this.set("jumpDestination_",[a,b]);this.set("position",this.get("lastPosition_"));
return}}this.redraw()},draw:function(){var b=this.get("map"),a=this.get("iconDom_"),c=this.get("visible"),e=this.get("position");if(a&&b&&e){var d=this.get("altitude_");a.style.display="none";var g=this.get("clickable"),h=this.get("draggable"),q=this.get("flat"),f=b.fromLatLngToPixel(e),l=this.get("target_"),i=this.get("dragTargetIcon");if(l){i=v(i);i=new r(f.getX()-i.getX(),f.getY()-i.getY());u(l,i)}i=v(this.get("icon"));i=new r(f.getX()-i.getX(),f.getY()-i.getY()-d);var m=this.get("area_");u(m,
i);u(a,i);l=this.get("zIndex");if(typeof l!="undefined"||l!=null)l+=1E3;else l=b.fromLatLngToContainerPixel(e).getY();if(this.get("isUpping_"))l=1E5;m.style.zIndex=l;a.style.zIndex=l;m.style.display=!c||!g&&!h?"none":"";if((b=this.get("shadowDom_"))&&this.get("shadow")){i=v(this.get("shadow"));d=d/1.414;i=new r(f.getX()-i.getX()+d,f.getY()-i.getY()-d);u(b,i);b.style.display=q||!c?"none":"";b.style.zIndex=l}a.style.display=c?"":"none"}this.set("lastPosition_",this.get("position"))},draw__changed:function(){this.draw()}});
y.setView("Marker",C)})(__MapNS__);
