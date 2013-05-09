function initEnv() {
	$("body").append(DWZ.frag["dwzFrag"]);

	if ( $.browser.msie && /6.0/.test(navigator.userAgent) ) {
		try {
			document.execCommand("BackgroundImageCache", false, true);
		}catch(e){}
	}
	//清理浏览器内存,只对IE起效
	if ($.browser.msie) {
		window.setInterval("CollectGarbage();", 10000);
	}

	$(window).resize(function(){
		initLayout();
		$(this).trigger("resizeGrid");
	});

	var ajaxbg = $("#background,#progressBar");
	ajaxbg.hide();
	
	$(document).ajaxStart(function(){
		if(showLoading)
			ajaxbg.show();
	}).ajaxStop(function(){
		ajaxbg.hide();
	});
	
	$("#leftside").jBar({minW:150, maxW:700});
	
	if ($.taskBar) $.taskBar.init();
	navTab.init();
	//if ($.fn.switchEnv) $("#switchEnvBox").switchEnv();
	//if ($.fn.navMenu) $("#navMenu").navMenu();
		
	setTimeout(function(){
		initLayout();
		initUI();
		
		// navTab styles
		var jTabsPH = $("div.tabsPageHeader");
		jTabsPH.find(".tabsLeft").hoverClass("tabsLeftHover");
		jTabsPH.find(".tabsRight").hoverClass("tabsRightHover");
		jTabsPH.find(".tabsMore").hoverClass("tabsMoreHover");
	
	}, 10);

}
function initLayout(){
	var iContentW = $(window).width() - (DWZ.ui.sbar ? $("#sidebar").width() + 10 : 34) - 5;
	var iContentH = $(window).height() - $("#header").height() - 34;

	$("#container").width(iContentW);
	$("#container .tabsPageContent").height(iContentH - 34).find("[layoutH]").layoutH();
	$("#sidebar, #sidebar_s .collapse, #splitBar, #splitBarProxy").height(iContentH - 5);
	$("#taskbar").css({top: iContentH + $("#header").height() + 5, width:$(window).width()});
}

function _resizeGrid(){
	$("div.j-resizeGrid").each(function(){
		var width = $(this).innerWidth();
		if (width){
			$("div.gridScroller", this).width(width+"px");
		}
	});
	try{
		if(typeof resizeMonitorMap != 'undefined' && resizeMonitorMap instanceof Function)
			resizeMonitorMap();
	}catch(e){
	}

	try{
		if(typeof resizeDispatchPage != 'undefined' && resizeDispatchPage instanceof Function)
			resizeDispatchPage();
	}catch(e){
	}
	
	//
	try{
		$(window).trigger("resizeLeftNavigation");
	}catch(e){
	}
}

function initUI(_box){
	var $p = $(_box || document);
	//tables
	$("table.table", $p).jTable();
	//$(window).unbind("resizeGrid").bind("resizeGrid", _resizeGrid);

	$(window).unbind("resizeGrid").bind("resizeGrid", _resizeGrid);
	// css tables
	$('table.list', $p).cssTable();

	//auto bind tabs
	$("div.tabs", $p).each(function(){
		var $this = $(this);
		var options = {};
		options.currentIndex = $this.attr("currentIndex") || 0;
		options.eventType = $this.attr("eventType") || "click";
		$this.tabs(options);
	});

	$("ul.tree", $p).jTree();
	$('div.accordion', $p).each(function(){
		var $this = $(this);
		$this.accordion({fillSpace:$this.attr("fillSpace"),alwaysOpen:true,active:0});
	});

	$(":button.checkboxCtrl, :checkbox.checkboxCtrl", $p).checkboxCtrl($p);
	
	if ($.fn.combox) $("select.combox",$p).combox();
	if ($.fn.combox) $("select.combox2",$p).combox2();
	
	
	if ($.fn.xheditor) {
		$("textarea.editor", $p).each(function(){
			var $this = $(this);
			var op = {html5Upload:false, skin: 'vista',tools: $this.attr("tools") || 'full'};
			var upAttrs = [
				["upLinkUrl","upLinkExt","zip,rar,txt"],
				["upImgUrl","upImgExt","jpg,jpeg,gif,png"],
				["upFlashUrl","upFlashExt","swf"],
				["upMediaUrl","upMediaExt","avi"]
			];
			
			$(upAttrs).each(function(i){
				var urlAttr = upAttrs[i][0];
				var extAttr = upAttrs[i][1];
				
				if ($this.attr(urlAttr)) {
					op[urlAttr] = $this.attr(urlAttr);
					op[extAttr] = $this.attr(extAttr) || upAttrs[i][2];
				}
			});
			
			$this.xheditor(op);
		});
	}
	
	if ($.fn.uploadify) {
		$(":file[uploader]", $p).each(function(){
			var $this = $(this);
			var options = {
				uploader: $this.attr("uploader"),
				script: $this.attr("script"),
				cancelImg: $this.attr("cancelImg"),
				queueID: $this.attr("fileQueue") || "fileQueue",
				fileDesc: $this.attr("fileDesc") || "*.jpg;*.jpeg;*.gif;*.png;*.pdf",
				fileExt : $this.attr("fileExt") || "*.jpg;*.jpeg;*.gif;*.png;*.pdf",
				folder	: $this.attr("folder"),
				auto: true,
				multi: true,
				onError:uploadifyError,
				onComplete: uploadifyComplete,
				onAllComplete: uploadifyAllComplete
			};
			if ($this.attr("onComplete")) {
				options.onComplete = DWZ.jsonEval($this.attr("onComplete"));
			}
			if ($this.attr("onAllComplete")) {
				options.onAllComplete = DWZ.jsonEval($this.attr("onAllComplete"));
			}
			if ($this.attr("scriptData")) {
				options.scriptData = DWZ.jsonEval($this.attr("scriptData"));
			}
			$this.uploadify(options);
		});
	}
	
	//contextmenu 
	if ($.fn.contextMenu) {
//		$(".dialogmenusupport",$p).contextMenu("dialogMenuSupport", {
//				bindings:{
//					description:function(t,m){
//						 logger.info("description support---->"+t.toSource());
//						 logger.info("description support---->"+m.toSource());
//					}, 
//					help:function(t,m){
//						 logger.info("help support---->"+t.toSource());
//						 logger.info("help support---->"+m.toSource());
//					}
//				} //end of bindings
//		});
//		
		var descdialogurl=getDomain(1)+"/pages/system/menu/view/auMenuDescPreviewDialog.html";
		var helpdialogurl=getDomain(1)+"/pages/system/menu/view/auMenuHelpPreviewDialog.html";
		$(".dialogmenusupport",$p).each(function(){ 
 					var $this = $(this);
					 var menuId=$this.attr("menuId");  
 					$this.contextMenu('dialogMenuSupport', {
 						bindings:{ 
 							description:function(t,m){ 
								showDialog({title:"功能描述",url:descdialogurl,param:{menuId:menuId}});
 							}, 
 							help:function(t,m){   
								showDialog({title:"功能帮助",url:helpdialogurl,param:{menuId:menuId}});
 							}
 						} 
 					});  
 		}); //end of each
	}//end of if
	//
	
	// init styles
	$("input[type=text], input[type=password], textarea", $p).addClass("textInput").focusClass("focus");

	$("input[readonly], textarea[readonly]", $p).addClass("readonly");
	$("input[disabled=true], textarea[disabled=true]", $p).addClass("disabled");

	$("input[type=text]", $p).not("div.tabs input[type=text]", $p).filter("[alt]").inputAlert();

	//Grid ToolBar
	$("div.panelBar li, div.panelBar", $p).hoverClass("hover");

	//Button
	$("div.button", $p).hoverClass("buttonHover");
	$("div.buttonActive", $p).hoverClass("buttonActiveHover");
	
	//tabsPageHeader
	$("div.tabsHeader li, div.tabsPageHeader li, div.accordionHeader, div.accordion", $p).hoverClass("hover");
	
	$("div.panel", $p).jPanel();

	//validate form
	$("form.required-validate", $p).each(function(){
	$(this).validate({
			focusInvalid: false,
			focusCleanup: true,
			onkeyup: function(element,event){ 
			if(!$(element).attr("validateRemote")){//重写validate的onkeyup方法
													//去除 ajax 校验的 onkeyup事件
			if ( element.name in this.submitted || element == this.lastElement ) {
				this.element(element);
			}
			 }},
			errorElement: "span",
			ignore:".ignore",
			invalidHandler: function(form, validator) {
				var errors = validator.numberOfInvalids();
				if (errors) {
					var message = DWZ.msg("validateFormError",[errors]);
					alertMsg.error(message);
				} 
			}
		});
 	});

	if ($.fn.datepicker){
		$('input.date', $p).each(function(){
			var $this = $(this);
			var opts = {};
			if ($this.attr("format")) opts.pattern = $this.attr("format");
			if ($this.attr("yearstart")) opts.yearstart = $this.attr("yearstart");
			if ($this.attr("yearend")) opts.yearend = $this.attr("yearend");
			$this.datepicker(opts);
		});
	}
	
	function evaluteHrefAttr($this) {
	 	if($this.attr('href').indexOf("javascript:") != -1){
			var fun = new Function('return ' + $this.attr('href').replaceAll("javascript:",""));
			var h = fun();
			$this.attr('href',h);
		}
		return $this;
	 }
	
	// navTab
	$("a[target=navTab]", $p).each(function(){
		$(this).click(function(event){
			var $this = $(this);
			$this = evaluteHrefAttr($this);
			var title = $this.attr("title") || $this.text();
			var tabid = $this.attr("rel") || "_blank";
			var fresh = eval($this.attr("fresh") || "true");
			var external = eval($this.attr("external") || "false");
			var url;
			if($this.attr("transfer")) {
				url = unescape($this.attr("href")).replaceTmByValue($this.attr("transfer"));
			}else if($this.attr("multiable") === "no") {
				var size = $(event.target).parents(".unitBox").find("input:checked").size();
				if(size != 1) {
					alertMsg.error($this.attr("warn") || DWZ.msg("alertSelectMsg"));
					return false;
				}
				
				$check = $(event.target).parents(".unitBox").find("input:checked");
				url = unescape($this.attr("href")).replaceTmByValue($check.val());
				//
				var callbackMethodName=$check.attr("callbackMethod");
				logger.info("[dwz.ui.js]callbackMethodName----->"+callbackMethodName);
				if(callbackMethodName){
					url=window[callbackMethodName]($check,url); 
				}
			}else{
				url = unescape($this.attr("href")).replaceTmById($(event.target).parents(".unitBox:first"));
			}
			
			DWZ.debug(url);
			if (!url.isFinishedTm()) {
				alertMsg.error($this.attr("warn") || DWZ.msg("alertSelectMsg"));
				return false;
			}
			
			//return;
			navTab.openTab(tabid, url,{title:title, fresh:fresh, external:external,ismenu:$this.attr("ismenu")});

			event.preventDefault();
		});
	});
	
	//dialogs
	$("a[target=dialog]", $p).each(function(){
		$(this).click(function(event){
			var $this = $(this);
			$this = evaluteHrefAttr($this);
			var title = $this.attr("title") || $this.text();
			var rel = $this.attr("rel") || "_blank";
			var options = {};
			var w = $this.attr("width");
			var h = $this.attr("height");
			if (w) options.width = w;
			if (h) options.height = h;
			options.max = eval($this.attr("max") || "false");
			options.mask = eval($this.attr("mask") || "false");
			options.maxable = eval($this.attr("maxable") || "true");
			options.minable = eval($this.attr("minable") || "true");
			options.fresh = eval($this.attr("fresh") || "true");
			options.resizable = eval($this.attr("resizable") || "true");
			options.drawable = eval($this.attr("drawable") || "true");
			options.close = eval($this.attr("close") || "");
			options.param = $this.attr("param") || "";
			options.zIndex = $this.attr("zIndex") || "1000";

			var url = unescape($this.attr("href")).replaceTmById($(event.target).parents(".unitBox:first"));
			DWZ.debug(url);
			if (!url.isFinishedTm()) {
				alertMsg.error($this.attr("warn") || DWZ.msg("alertSelectMsg"));
				return false;
			}
			$.pdialog.open(url, rel, title, options);
			
			return false;
		});
	});
	
	$("a[target=ajax]", $p).each(function(){
		$(this).click(function(event){
			var $this = $(this);
			$this = evaluteHrefAttr($this);
			var rel = $this.attr("rel");
			if (rel) {
				var $rel = $("#"+rel);
				$rel.attr("url",$this.attr("href"));
				$rel.loadUrl($this.attr("href"), {}, function(){
					$rel.find("[layoutH]").layoutH();
				});
			}

			event.preventDefault();
		});
	});
	
	$("div.pagination", $p).each(function(){
		var $this = $(this);
		$this.pagination({
			targetType:$this.attr("targetType"),
			rel:$this.attr("rel"),
			totalCount:$this.attr("totalCount"),
			numPerPage:$this.attr("numPerPage"),
			pageNumShown:$this.attr("pageNumShown"),
			currentPage:$this.attr("currentPage")
		});
	});

	if ($.fn.sortDrag) $("div.sortDrag", $p).sortDrag();

	// dwz.ajax.js
	if ($.fn.ajaxTodo) $("a[target=ajaxTodo]", $p).ajaxTodo();
	if ($.fn.dwzExport) $("a[target=dwzExport]", $p).dwzExport();

	if ($.fn.lookup) $("a[lookupGroup]", $p).lookup();
	if ($.fn.multLookup) $("[multLookup]:button", $p).multLookup();
	if ($.fn.suggest) $("input[suggestFields]", $p).suggest();
	if ($.fn.itemDetail) $("table.itemDetail", $p).itemDetail();
	if ($.fn.selectedTodo) $("a[target=selectedTodo]", $p).selectedTodo();
	if ($.fn.pagerForm) $("form[rel=pagerForm]", $p).pagerForm({parentBox:$p});

	// 这里放其他第三方jQuery插件...
	  
	if ($.fn.selectedTodoForTree) $("a[target=selectedTodoForTree]", $p).selectedTodoForTree();
	
	$(".bambooclose", $p).each(function(){
		var $this = $(this);
		$this.click(function(){$(this).parent().remove();});
	});
	
	if ($.fn.comboxcascade) $("input.comboxcascade", $p).comboxcascade();
	
	$(".autocomplete", $p).each(function(){
		var $this = $(this);
		var width=$this.width();
		var opts= $this.attr("opts");
		doGetAjax($this.attr("dataurl"),opts?opts:{},function(data){
			$this.autocomplete(data.jsonData,{
				max: 500,    //列表里的条目数
                minChars: 0,    //自动完成激活之前填入的最小字符
                width: width,     //提示的宽度，溢出隐藏
                scrollHeight: 300,   //提示的高度，溢出显示滚动条
                matchContains: true,    //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示
                autoFill: false,    //自动填充
                mustMatch:true,
                formatItem: function(row, i, max) {
                    return row[$this.attr("itemvalue")];
                },
                formatMatch: function(row, i, max) {
                    return row[$this.attr("itemvalue")];
                },
                formatResult: function(row) {
                    return row[$this.attr("itemvalue")];
                }
             }
			).result(function(event, row, formatted) {
				$this.next().val(row[$this.attr("datavalue")]);
				var $callback = $this.attr("callback");
				if (typeof ($callback) != "undefined") {
					window[$callback](row);
				}
            });
		});
		//$this.click(function(){$(this).parent().remove();});
	});
	if ($.fn.customAutocompleteForCCK)	$(".autocompleteForCCK", $p).customAutocompleteForCCK(); 
}

$.fn.extend({
	customAutocomplete:function(){
			//findName("userStr").attr("opts",{deptallow:"yes"});
			//findName("userStr").attr("dataurl",CPATH.domain_3 + "/rs/staff/" + lbguid + "/apply?order=username_asc");
			var $this = $(this);
			var width=$this.width();
			var opts= $this.attr("opts");
			doGetAjax($this.attr("dataurl"),opts?opts:{},function(data){
				$this.autocomplete(data.jsonData,{
					max: 500,    //列表里的条目数
	                minChars: 0,    //自动完成激活之前填入的最小字符
	                width: width,     //提示的宽度，溢出隐藏
	                scrollHeight: 300,   //提示的高度，溢出显示滚动条
	                matchContains: true,    //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示
	                autoFill: false,    //自动填充
	                mustMatch:true,
	                formatItem: function(row, i, max) {
	                    return row[$this.attr("itemvalue")];
	                },
	                formatMatch: function(row, i, max) {
	                    return row[$this.attr("itemvalue")];
	                },
	                formatResult: function(row) {
	                    return row[$this.attr("itemvalue")];
	                }
	             }
				).result(function(event, row, formatted) {
					$this.next().val(row[$this.attr("datavalue")]);
	            });
			});
			//$this.click(function(){$(this).parent().remove();});
	}
	
});

Array.prototype.S = String.fromCharCode(2);
Array.prototype.in_array = function(e) {
	var r = new RegExp(this.S + e + this.S);
	return (r.test(this.S + this.join(this.S) + this.S));
};

$.fn.extend({
	customAutocompleteForCCK:function(){
		$(this).each(function(){ 
			var $this = $(this);
			var autocompleteType= $this.attr("autocompleteType");
			var url=$this.attr("dataurl");
			var domain=$this.attr("domain");
			var code=$this.attr("refcode");
			if(code){
				 url="/rs/syWidgetReferenceData/"+code;
				domain="1";
			}
			
			 
			if(domain)
				{url=getDomain(domain)+url;}
			if(1==autocompleteType){
				createAutocompleteForCCK($this,url);
			}
			else{
				doGetAjax(url, {},function(data){
					if(data&&data.jsonData){createAutocompleteForCCK($this,data.jsonData);} 
					
				});
			}
			
		});
	

	}
	
});
 
function createAutocompleteForCCK($this,data){
	var width=$this.width();
	var itemvalue=$this.attr("itemvalue");
	var datavalue=$this.attr("datavalue");
	$this.autocomplete(data,{
		max: 30,    //列表里的条目数
        minChars: 0,    //自动完成激活之前填入的最小字符
        width: width,     //提示的宽度，溢出隐藏
        scrollHeight: 300,   //提示的高度，溢出显示滚动条
        matchContains: false,    //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示
        autoFill: false,    //自动填充
        mustMatch:true,
			parse : function(data) {// 
			var parsed = [];
			var rows = data.jsonData ;
			for ( var i = 0; i < rows.length; i++) {
				var row =  rows[i] ;
				if (row) {
					 
					parsed[parsed.length] = {
						data : row,
						value : row[itemvalue],
						result : row[itemvalue]
					};
				}
			}
			return parsed;
		},
        formatItem: function(row, i, max) {
            return row[itemvalue];
        },
        formatMatch: function(row, i, max) {
            return row[itemvalue];
        },
        formatResult: function(row) {
            return row[itemvalue];
        }
     }
	).result(function(event, row, formatted) {
		if(!row)
			return;
		$this.next().val(row[datavalue]);
		$("p [name]",$this.parent().parent()).each(function (){
			var t=$(this);
			var name=t.attr("name");
			var value=row[name];
			if(value){
				if(t.hasClass("date")){
					var   d   =   new   Date(Date.parse(value.replace(/-/g,   "/")));  
					value= d.formatDate("yyyy-MM-dd");
				}
				t.val(value);
			 }
		});
    }); 
}


function showDialog(opts){   
	//
	var url = opts.url;
	var title = opts.title; 
	var rel = "dialog";
	var options = {}; 
	options.width = 600;
	options.height = 500;
	options.max =false;
	options.mask = true;
	options.maxable = true;
	options.minable = false;
	options.fresh = true;
	options.resizable = false;
	options.drawable = true;
	options.close = "";
	options.param =opts.param;
	options.zIndex = "1000"; 

	$.pdialog.open(url, rel, title, options);
	
}//end of showDialog
 

function openDialog(opts){
	//var $this = $(this);
	//$this = evaluteHrefAttr($this);
	var title = opts.title||opts.text; 
	var rel = opts.rel || "_blank";
	var options = {};
	var w = opts.width;
	var h = opts.height;
	if (w) options.width = w;
	if (h) options.height = h;
	options.max = eval(opts.max || "false");
	options.mask = eval(opts.mask || "false");
	options.maxable = eval(opts.maxable || "true");
	options.minable = eval(opts.minable || "true");
	options.fresh = eval(opts.fresh || "true");
	options.resizable = eval(opts.resizable || "true");
	options.drawable = eval(opts.drawable || "true");
	options.close = eval(opts.close || "");
	options.param = opts.param || "";
	options.zIndex = opts.zIndex || "1000";
	options.showloading = opts.showloading;

	var url = unescape(opts.href);//.replaceTmById($(event.target).parents(".unitBox:first"));  
	DWZ.debug(url);
	if (!url.isFinishedTm()) {
		alertMsg.error(opts.warn || DWZ.msg("alertSelectMsg"));
		return false;
	}
	$.pdialog.open(url, rel, title, options); 
	return false;
}
function openNavTab(opts){
//	var $this = $(this);
//	$this = evaluteHrefAttr($this);
	var title = opts.title|| $this.text ;
	var tabid = opts.rel|| "_blank";
	var fresh = eval(opts.fresh|| "true");
	var external = eval(opts.external|| "false"); 
	var url;
	url = unescape(opts.href );  
//	if(opts.transfer ) {
//		url = unescape(opts.href );//.replaceTmByValue(opts.transfer );
//	}else if(opts.multiable=== "no"){
//		var size = $(event.target).parents(".unitBox").find("input:checked").size();
//		if(size != 1) {
//			alertMsg.error(opts.warn|| DWZ.msg("alertSelectMsg"));
//			return false;
//		}
//		
//		$check = $(event.target).parents(".unitBox").find("input:checked");
//		url = unescape(opts.href ).replaceTmByValue($check.val());
//	}else{
//		url = unescape(opts.href ).replaceTmById($(event.target).parents(".unitBox:first"));
//	}
	
	DWZ.debug(url);
	if (!url.isFinishedTm()) {
		alertMsg.error(opts.warn|| DWZ.msg("alertSelectMsg"));
		return false;
	}
	
	//return;
	navTab.openTab(tabid, url,{title:title, fresh:fresh, external:external});

	return false;
}


