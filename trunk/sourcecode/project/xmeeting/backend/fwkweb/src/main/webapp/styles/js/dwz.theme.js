/**
 * Theme Plugins
 * @author ZhangHuihua@msn.com
 */
var changeStyleNeedReflush=false;
(function($){
	$.fn.extend({
		theme: function(options){
			var op = $.extend({themeBase:"themes"}, options);
			var _themeHref = op.themeBase + "/#theme#/style.css";
			return this.each(function(){
				var jThemeLi = $(this).find(">li[theme]");
				var setTheme = function(themeName){
					//$("head").find("link[href$='style.css']").attr("href", _themeHref.replace("#theme#", themeName));
					jThemeLi.find(">div").removeClass("selected");
					jThemeLi.filter("[theme="+themeName+"]").find(">div").addClass("selected");
					
					if ($.isFunction($.cookie)) $.cookie("dwz_theme", themeName);
				}
				
				jThemeLi.each(function(index){
					var $this = $(this);
					var themeName = $this.attr("theme");
					$this.addClass(themeName).click(function(){
						if(changeStyleNeedReflush==true){
							if(confirm('更改样式需要重载页面，请确认！')){
								setTheme(themeName);
								window.top.location.reload();
							}
						}else{
							$("head").find("link[href$='style.css']").attr("href", _themeHref.replace("#theme#", themeName));
							setTheme(themeName);
						}
					});
				});
					
				if ($.isFunction($.cookie)){
					var themeName = $.cookie("dwz_theme");
					if (themeName) {
						$("head").find("link[href$='style.css']").attr("href", _themeHref.replace("#theme#", themeName));
						setTheme(themeName);
					}
				}
				
			});
		}
	});
})(jQuery);
