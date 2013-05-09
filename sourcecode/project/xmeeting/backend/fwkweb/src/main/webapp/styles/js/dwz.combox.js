/**
 * @author Roger Wu
 */

function evaluteRefUrlAttr($this) {
	 	if($this.attr('refUrl').indexOf("javascript:") != -1){
			var fun = new Function('return ' + $this.attr('refUrl').replaceAll("javascript:",""));
			var h = fun();
			$this.attr('refUrl',h);
		}
		return $this;
	 }

(function($){
	var allSelectBox = [];
	var killAllBox = function(bid){
		$.each(allSelectBox, function(i){
			if (allSelectBox[i] != bid) {
				if (!$("#" + allSelectBox[i])[0]) {
					$("#op_" + allSelectBox[i]).remove();
					//allSelectBox.splice(i,1);
				} else {
					$("#op_" + allSelectBox[i]).css({ height: "", width: "" }).hide();
				}
				$(document).unbind("click", killAllBox);
			}
		});
	};
	
	$.extend($.fn, {
		comboxSelect: function(options){
			var op = $.extend({ selector: ">a" }, options);
			
			return this.each(function(){
				var box = $(this);
				var selector = $(op.selector, box);

				allSelectBox.push(box.attr("id"));
				$(op.selector, box).click(function(){
					var options = $("#op_"+box.attr("id"));
					if (options.is(":hidden")) {
						if(options.height() > 300) {
							options.css({height:"300px",overflow:"scroll"});
						}
						var top = box.offset().top+box[0].offsetHeight-50;
						if(top + options.height() > $(window).height() - 20) {
							top =  $(window).height() - 20 - options.height();
						}
						options.css({top:top,left:box.offset().left}).show();
						killAllBox(box.attr("id"));
						$(document).click(killAllBox);
					} else {
						$(document).unbind("click", killAllBox);
						killAllBox();
					}
					return false;
				});
				$("#op_"+box.attr("id")).find(">li").comboxOption(selector, box);		
			});
		},setCurrentVal: function(currentVal){
			
			var $this=$(this);
			$this.val(currentVal);
			$("[value='"+currentVal+"']",$("#op_"+$this.parent().attr("id"))).click();
			
			
//			var $cloneSelect=$($this.clone());
//			$cloneSelect.appendTo($this.parent().parent().parent());
//			$this.parent().parent().remove();
//			$cloneSelect.val(currentVal);
//			$cloneSelect.combox();
			//var $this=$(this);
			//$this.val(currentVal);
			//$this.combox();
		},
		comboxOption: function(selector, box){
			return this.each(function(){
				$(">a", this).click(function(){
					var $this = $(this);
					$this.parent().parent().find(".selected").removeClass("selected");
					$this.addClass("selected");
					selector.text($this.text());
					
					var $input = $("select", box);
					if ($input.val() != $this.attr("value")) {
						$("select", box).val($this.attr("value")).trigger("refChange").trigger("change");
					}
				});
			});
		},
		combox:function(){
			if($(this).hasClass("sync"))
				return;
			/* 清理下拉层 */
			var _selectBox = [];
			$.each(allSelectBox, function(i){ 
				if ($("#" + allSelectBox[i])[0]) {
					_selectBox.push(allSelectBox[i]);
				} else {
					$("#op_" + allSelectBox[i]).remove();
				}
			});
			allSelectBox = _selectBox;
			
			return this.each(function(i){
				var $this = $(this).removeClass("combox");
				
				var currentVal=$this.attr("currentVal");
				
				if($this.attr("defaultSelectTitle")){
					$("<option value=\""+$this.attr("defaultSelectValue")+"\">"+$this.attr("defaultSelectTitle")+"</option>").appendTo($this);
				}

				var include=$this.attr("include");
				var includeValues;
				if(include){
					includeValues = include.split(',');
				}
				var dataurl=$this.attr("dataurl");
				var domain=$this.attr("domain");
				var code=$this.attr("refcode");
				if(code){
					dataurl="/rs/syWidgetReferenceData/"+code;
					domain="1";
				}
				if(dataurl){
					var codeUrl=dataurl;
					var key=$this.attr("valueCode");
					var value=$this.attr("valueDesc");
					var pidname=$this.attr("valuePid");
			 
					if(!pidname){
						pidname="PID";
					}
					if(domain){
						codeUrl=getDomain(domain)+codeUrl;
					}
					showLoading=false;
					doGetAjax(codeUrl,{},function(data){
						 if(!includeValues&&data.jsonData[0]&&data.jsonData[0][pidname]){//tree 形式 的combox
								var list =data.jsonData;
								for(var i=0;i<list.length;i++){
									 list[i].selected =true;
								}	
							 var nodes2={};
								 structureNodes(list,"0",nodes2,key,pidname);
								 initComboxTree([nodes2],$this,"0",key,value);
							}else{
						$.each(data.jsonData, function(index, json) {
							//$("<option "+(currentVal==json[key]?"selected='selected'":"")+" value=\""+json[key]+  "\">"+json[value]+"</option>").appendTo($this);
							if(includeValues ){
								if(includeValues.in_array(json[key])){
									if(!key&&!value)
										$("<option value=\""+json+  "\">"+json+"</option>").appendTo($this);
									else
										$("<option value=\""+json[key]+  "\">"+json[value]+"</option>").appendTo($this);
								}else{
									if(!key&&!value)
										$("<option value=\""+json+  "\">"+json+"</option>").appendTo($this);
									else
										$("<option value=\""+json[key]+  "\">"+json[value]+"</option>").appendTo($this);
								}
						 }else{
						
							 if(!key&&!value)
									$("<option value=\""+json+  "\">"+json+"</option>").appendTo($this);
								else
									$("<option value=\""+json[key]+  "\">"+json[value]+"</option>").appendTo($this);
								}
							 
						});
							}
						$this.changeStyle();
					});
				}else if($this.attr("codeType")){
					var codeUrl=CPATH.domain_1+"/rs/syNameCode/"+$this.attr("codeType");
					showLoading=false;
					doGetAjax(codeUrl,{},function(data){
						$.each(data.jsonData, function(index, json) {
							if(includeValues ){
								if(includeValues.in_array(json["valueCode"]))
									$("<option value=\""+json["valueCode"]+"\">"+json["valueName"]+"</option>").appendTo($this);
							}else{
								$("<option value=\""+json["valueCode"]+"\">"+json["valueName"]+"</option>").appendTo($this);
							}
					     });
						$this.changeStyle();
					});
				}else{
					$this.changeStyle();
				}
				
				
			});
		 
		},comboxcascade:function(){
	 
			
			return this.each(function(i){
				if($(this).hasClass("syncComboxCascade"))
					return;
 				var $this = $(this).removeClass("comboxcascade");
				
				var currentVal=$this.attr("currentVal");
				var id=$this.val();
				var dataurl=$this.attr("dataurl");
				if($this.attr("defaultSelectTitle")){
					var choose=$this.attr("defaultSelectTitle");
				 			}
				var domain=$this.attr("domain");
				var code=$this.attr("refcode");
				if(code){
					dataurl="/rs/syWidgetReferenceData/"+code;
					domain="1";
				}
				var cckrefcode=$this.attr("cckrefcode");
				if(cckrefcode){
					dataurl="/rs/cck/cckContentData/"+cckrefcode;
					domain="1"; 
				}
				if(dataurl){

					var codeUrl=dataurl;
					var key=$this.attr("valueCode");
					var value=$this.attr("valueDesc");
					var pidname=$this.attr("valuePid");
					
					var inputName=$this.attr("name");
					if(!pidname){
						pidname="PID";
					}
					if(domain){
						codeUrl=getDomain(domain)+codeUrl;
					}
					showLoading=false;
				var comboxcascadedata=	$this.data("comboxcascadedata");
					if(comboxcascadedata){
						 var preselect ={};
						 if(id){
										 var array=[];
										 _getpreselect(comboxcascadedata, array,id) ;
										 preselect[inputName]= array  ;
						 }
						 var op  = {select_class: 'comboxcascadeselect',indexed: false,preselect_only_once: false, preselect:preselect,empty_value: "", choose: choose?choose:'请选择...'};
						 $this.optionTree(comboxcascadedata,op);
					}
					else{
						doGetAjax(codeUrl,{},function(data){
							 if(  data.jsonData ){//tree 形式 的combox
//									var nodes2={};
//									 structureNodes(data.jsonData,0,nodes2,key,pidname);
//									 initComboxTree([nodes2],$this,0,key,value);
//								 var preselect =getpreselect();
								 var nodes2={};
								var list =data.jsonData;
								for(var i=0;i<list.length;i++){
									 list[i].selected =true;
								}
								 structureNodesForCascade(list,0,nodes2,key,pidname,value,{});
								 var preselect ={};
	 
								 if(id){
//									 var list =data.list;
//									 for(var i=0;i<list.length;i++){
//											var object=list[i];
//											 if(object[key]==id)
//											 {	
												 var array=[];
												 _getpreselect(nodes2, array,id) ;
												 preselect[inputName]= array  ;
//						 
//												break; 
//											 }
//										 }
								 }
								 var op  = {select_class: 'comboxcascadeselect',indexed: false,preselect_only_once: false, preselect:preselect,empty_value: "", choose: choose?choose:'请选择...'};
								 $this.data("comboxcascadedata",nodes2);
								 $this.optionTree(nodes2,op);
								} 
					 
						});
					}
				
				} 
				
			});
			function _getpreselect(data,array,id ){
				 for(var a in data) { 
					var ndata=data[a];
					if(typeof(ndata)=='object'){
								array.push(a);
				 			 if(_getpreselect(ndata,array,id)){
				 			 	return true;
				 			 	}else{
				 			 		 array.pop();
				 			 		 }
						}else{
							if(ndata==id) { 
								array.push(a);
								return true;
								}
							}
				 }
			  return false;
			}
 
		}, combox2:function(){
			if($(this).hasClass("sync"))
				return;
			/* 清理下拉层 */
			var _selectBox = [];
			$.each(allSelectBox, function(i){ 
				if ($("#" + allSelectBox[i])[0]) {
					_selectBox.push(allSelectBox[i]);
				} else {
					$("#op_" + allSelectBox[i]).remove();
				}
			});
			allSelectBox = _selectBox;
			
			return this.each(function(i){
				var $this = $(this);
				var currentVal=$this.attr("currentVal");
				if($this.attr("defaultSelectTitle")){
					$("<option value=\""+$this.attr("defaultSelectValue")+"\">"+$this.attr("defaultSelectTitle")+"</option>").appendTo($this);
				}

				var include=$this.attr("include");
				var includeValues;
				if(include){
					includeValues = include.split(',');
				}
				if($this.attr("dataurl")){
					var codeUrl=$this.attr("dataurl");
					var key=$this.attr("valueCode");
					var value=$this.attr("valueDesc");
					showLoading=false;
					doGetAjax(codeUrl,{},function(data){
						$.each(data.jsonData, function(index, json) {
							//$("<option "+(currentVal==json[key]?"selected='selected'":"")+" value=\""+json[key]+  "\">"+json[value]+"</option>").appendTo($this);
							if(includeValues ){
								if(includeValues.in_array(json[key]))
									$("<option value=\""+json[key]+  "\">"+json[value]+"</option>").appendTo($this);
							}else{
								$("<option value=\""+json[key]+  "\">"+json[value]+"</option>").appendTo($this);
							}
						
						});
						 
					});
				}else if($this.attr("codeType")){
					var codeUrl=CPATH.domain_1+"/rs/syNameCode/"+$this.attr("codeType");
					showLoading=false;
					doGetAjax(codeUrl,{},function(data){
						$.each(data.jsonData, function(index, json) {
							if(includeValues ){
								if(includeValues.in_array(json["valueCode"]))
									$("<option value=\""+json["valueCode"]+"\">"+json["valueName"]+"</option>").appendTo($this);
							}else{
								$("<option value=\""+json["valueCode"]+"\">"+json["valueName"]+"</option>").appendTo($this);
							}
					     }); 
					});
				} 

			});
		},changeStyle:function(){
			var $this=$(this);
			var name = $this.attr("name");
			var value= $this.attr("currentVal");
			if(!value)value=$this.attr("value");
			var label = $("option[value=" + value + "]",$this).text();
			var ref = $this.attr("ref");
			var refSec = $this.attr("refSec");
			var refUrl = $this.attr("refUrl") || "";
			var cid = $this.attr("id") || Math.round(Math.random()*10000000);
			var select = '<div class="combox"><div id="combox_'+ cid +'" class="select"' + (ref?' ref="' + ref + '"' : '') + '>';
			select += '<a href="javascript:" class="'+$this.attr("class")+'" name="' + name +'" value="' + value + '">' + label +'</a></div></div>';
			var options = '<ul class="comboxop" id="op_combox_'+ cid +'">';
			$("option", $this).each(function(){
				var option = $(this);
				options +="<li><a class=\""+ (value==option[0].value?"selected":"") +"\" href=\"#\" value=\"" + option[0].value + "\">" + option[0].text + "</a></li>";
			});
			options +="</ul>";
			var secSeq=$this.attr("secSeq");
			var seq=$this.attr("seq");
			$("body").append(options);
			$this.after(select);
			$("div.select", $this.next()).comboxSelect().append($this);
			$this.val(value);
			if(!value){
				if($this.attr("defaultSelect")){
					$this.setCurrentVal($this.attr("defaultSelect"));
				}
			}
			if (ref && refUrl) {
				
				$this.unbind("refChange").bind("refChange", function(event){
					$this = evaluteRefUrlAttr($this);
					var $ref = $("#"+ref);
					var $refSec;
					var key=$ref.attr("valueCode");
					var value=$ref.attr("valueDesc");
					
					if ($this.attr("nullchangeref")=='n' && $this.attr("value")=='') return false;
					if ($ref.size() == 0) return false;
					$.ajax({
						type:'GET', dataType:"json", url:$this.attr("refUrl").replace("{value}", $this.attr("value")), cache: false,
						data:{},
						success: function(json){
							if (!json) return;
							var html = '';
							var seqHtml='';
							/*
							$.each(json, function(i){
								if (json[i] && json[i].length > 1){
									html += '<option value="'+json[i][0]+'">' + json[i][1] + '</option>';
								}
							});
							*/
							$.each(json.jsonData, function(index, data) {
								if(secSeq=='desc'){
									seqHtml = '<option value="'+data[key]+'">' + data[value] + '</option>'+seqHtml;
								}else{
									seqHtml += '<option value="'+data[key]+'">' + data[value] + '</option>';
								}
								
								if(seq=='desc'){
									 html = '<option value="'+data[key]+'">' + data[value] + '</option>'+html;
									}else{
									 html += '<option value="'+data[key]+'">' + data[value] + '</option>';
									}
						     });
							
							var $refCombox = $ref.parents("div.combox:first");
							$ref.html(html).insertAfter($refCombox);
							$refCombox.remove();
							$ref.trigger("refChange").trigger("change").combox();
							
							if(refSec){
								$refSec = $("#"+refSec);
								var $refSecCombox = $refSec.parents("div.combox:first");
								$refSec.html(seqHtml).insertAfter($refSecCombox);
								$refSecCombox.remove();
								$refSec.trigger("refChange").trigger("change").combox();
							}
							
						},
						error: DWZ.ajaxError
					});
				});
			}
		}
	});
	function structureNodes(nodes,pId,pNode,idname,pidName){  
		var children=getNodesByPid(nodes,pId,pidName,idname); 
		if(pId||pId==0){
			if(children.length==0){ 
				return;
			}
			
			if(pNode){
				pNode.children=children;
			}
			for(var i=0;i<children.length;i++){
				var node=children[i];  
				structureNodes(nodes,node[idname],node,idname,pidName);
			}
		}else return;
		
	}
	function structureNodesForCascade(nodes,pId,pNode,idname,pidName,nameName,ppNode){
		var children=getNodesByPid(nodes,pId,pidName,idname); 
		if(pId||pId==0){
			if(children.length==0){ 
				if(pNode[nameName]||pNode[nameName]==0)
				{ppNode[pNode[nameName]]=pNode[idname];
				delete ppNode[nameName];
				delete ppNode[idname];
				delete ppNode[pidName];
				}
				return;
			}
			delete  pNode[nameName];
			delete  pNode[idname];
			delete  pNode[pidName];
//			if(pNode){
				//pNode.children=children;
//			}
			for(var i=0;i<children.length;i++){
				var node=children[i];  
				var nodenamename=node[nameName];
				var nodeid=node[idname];
				if(pNode){
//					var newnode={}
					pNode[nodenamename]=node;
				}
				
				structureNodesForCascade(nodes,nodeid,node,idname,pidName,nameName,pNode);
			}
		}else return;
	}
	function getNodesByPid(nodes,pId,pidName,idName){
		var xnodes=[];
		for(var i=0;i<nodes.length;i++){
			var node=nodes[i];
			if(node.selected&&node[pidName]==pId){

				xnodes.push(node);
				delete node.selected ;
				
			}
		}
		return xnodes;
		
	}
	
	function initComboxTree(nodes,comboxtreeselect,level,idname,namename){ 
		var blank="";
		var counter=level;
		while(counter>0){ 
			blank+="&nbsp;&nbsp;&nbsp;";
			counter--;
		}
		for(var i=0;i<nodes.length;i++){
			var node=nodes[i]; 
			if(node[idname]){
				comboxtreeselect.append("<option value='"+node[idname]+"'    >"+blank+node[namename]+"</option>");
			}else{
				level=level-1;
			}
			if(node.children&&node.children.length>0){
				initComboxTree(node.children,comboxtreeselect,level+1,idname,namename);
			}
		}//end of for
	}
})(jQuery);
