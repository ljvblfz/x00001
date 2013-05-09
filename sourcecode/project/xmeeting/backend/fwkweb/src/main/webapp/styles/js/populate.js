
/**
*给所有匹配元素集合赋值，跟据元素的propertyName从entity中取值
*@param{}entity
* 传递过来的java实体Bean对应的js对象
*@param{}[attrName]
* 可选参数；
* 元素的属性名称（例如id，name等）；
* 默认为name属性；
* 元素的该属性值要和entity中的变量名对应
*/
(function($){
	$.fn.extend({
		populate:function(entity, attrName){
		if (this.length > 0) { //判断是否有匹配的元素
		//迭代匹配的元素集合，为每个元素赋值
			for (var i = 0; i < this.length; i++) {
				var tObj = $(this[i]);
				if (attrName == null) { //判断是否传递了第二个参数
					attrName = "name"; //如果没有默认为name属性
				}
			//获取元素attrName属性的属性值
				var propertyVal = tObj.attr(attrName);
			//如果实体对象entity中包含名称为propertyVal的变量
				if (entity[propertyVal] != undefined) {
				//获取实体对象entity名称为propertyVal的变量的值
					var enValue = entity[propertyVal];
				//如果元素类型是radio或者是checkbox
					if ("radio" == tObj.attr("type") || "checkbox" == tObj.attr("type")) {
					//如果元素值等于enValue
						if (enValue == tObj.val()) {
						//设置元素被选中
							tObj.attr("checked", "checked");
						}
					}else if(tObj.is('select')){
						tObj.attr("currentVal",enValue);
						//tObj.setCurrentVal(enValue);
						//$('[name="numPerPage"]', navTab.getCurrentPanel()).setCurrentVal('100');
					}else {//其他类型的元素（input：text，hidden，password；textarea等）
						tObj.val(enValue);//设置元素的值
					}
				}
			}
		}//end if
		if ($.fn.combox) {$("select.combox",navTab.getCurrentPanel()).removeClass("sync");$("select.combox",navTab.getCurrentPanel()).combox();}
	}//end function
	});
})(jQuery);

