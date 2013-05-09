/**
 * @author zhanghuihua@msn.com
 */
(function($){
	$.fn.navMenu = function(){
		return this.each(function(){
			var $box = $(this);
			$box.find("li>a").click(function(){
				var $a = $(this);
				$a = evaluteHrefAttr($a);
				showLoading=false;
				$.ajax({
					type:"GET",
					url:$a.attr("href"),
					dataType:"json",
					cache: false,
					data:{},
					success: function(data){
						
						var html = '<div class="accordion" fillSpace="sidebar">';
						if(data.sonMenu.length > 0) {
				    		$.each(data.sonMenu,function(sonIndex,son){
				    			html += '<div class="accordionHeader"><h2><span>Folder</span>' + son.menuName + '</h2></div>';
				    			html += '<div class="accordionContent">';
				    			html += '<ul class="tree treeFolder">';
				    			if(son.sonMenu.length > 0) {
			    					$.each(son.sonMenu,function(childIndex,child){
			    						html+=parseSonMenu(child);
			    					});
				    			}
				    		html += '</ul></div>';
				    		});
				    	}
				    	html += '</div>';
					    
					    $("#sidebar").find(".accordion").remove().end().append(html).initUI();
						$box.find("li").removeClass("selected");
						$a.parent().addClass("selected");
						//navTab.closeAllTab();
						showLoading=true;
				    },
					error: DWZ.ajaxError
				});
				return false;
			});
		});
	}
	
	

	 
	
	function parseSonMenu(menu){
		var htmlStr='';
		if(menu.sonMenu.length > 0) {
			htmlStr += '<li><a>'+ menu.menuName + '</a>';
			htmlStr += '<ul>';
			$.each(menu.sonMenu,function(childIndex,child){
				htmlStr += parseSonMenu(child);//'<li><a href="'+ getDomain(child.domainName) + child.menuAction + '" target="navTab" rel="' + getRef(child.menuAction)+ '">' + child.menuName + '</a></li>';
			});
			htmlStr += '</ul>';

			htmlStr += '</li>';
		}else{
			var external='';
			if(menu.menuType=='2'){
				external='external="true"';
			}
			var menuAction;
			if(menu.menuAction.startWith('http://')){
				menuAction=menu.menuAction;
			}else{
				
				//added by lu.zhen begin
				if(menu.menuAction==""){ 
					menuAction=getDomain(1)+"/pages/system/menu/view/auMenuDescPreview.html?menuId="+menu.menuId; 
				} else{//added by lu.zhen end 
					menuAction=getDomain(menu.domainName) +menu.menuAction ;
				}
				
			}
			htmlStr += '<li><a '+external+' class="dialogmenusupport" menuId="'+menu.menuId+'" ismenu="true" href="'+  menuAction + '" target="navTab" rel="' + menu.menuTitle+ '">' + menu.menuName + '</a></li>';
		}
		return htmlStr;
	}
	
	function getRef(data) {
	  	var ref = '';
        ref = data.substring(0,data.lastIndexOf(".html"));
        ref = ref.substring(ref.lastIndexOf("/") + 1);
        return ref;
	}
	
	$.fn.switchEnv = function(){
		var op = {cities$:">ul>li", boxTitle$:">a>span"};
		return this.each(function(){
			var $this = $(this);
			$this.click(function(){
				if ($this.hasClass("selected")){
					_hide($this);
				} else {
					_show($this);
				}
				return false;
			});
			
			$this.find(op.cities$).click(function(){
				var $li = $(this);

				$.post($li.find(">a").attr("href"), {}, function(html){
					_hide($this);
					$this.find(op.boxTitle$).html($li.find(">a").html());
					navTab.closeAllTab();
					$("#sidebar").find(".accordion").remove().end().append(html).initUI();
				});
				return false;
			});
		});
	}
	
	function _show($box){
		$box.addClass("selected");
		$(document).bind("click",{box:$box}, _handler);
	}
	function _hide($box){
		$box.removeClass("selected");
		$(document).unbind("click", _handler);
	}
	
	function _handler(event){
		_hide(event.data.box);
	}
})(jQuery);


