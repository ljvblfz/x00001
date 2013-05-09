	function cckcontentSearch(opt) {
		//序列化表单值，并扩展到syCckTypeArgs对象中
		$.extend(findName("cckcontentSearchForm").data("arg"), findName(
				'cckcontentSearchForm').serializeJson(), opt ? opt : {});
		//执行ajax查询方法
// 		var sctGuid = navTab._getParam("sctGuid");
// 		var masterId = navTab._getParam("masterId");
		var sctGuid = 	findName("cckcontentSearchForm").data("sctGuid" );
		var masterId = findName("cckcontentSearchForm").data("masterId" );
		var arg = findName("cckcontentSearchForm").data("arg");
		if(!arg.numPerPage){
			arg.numPerPage=20;
		}
 
		doGetAjax(getURL(CPATH.domain_1, "/rs/cck/dgrid/buildgrid/" + sctGuid+(masterId?("?masterId="+masterId):"")),
				arg, cckcontentList);
	}
	
	function resetForm_cckcontent() {
		findName("cckcontentSearchForm")[0].reset();
$(".selectToReset",findName("cckcontentSearchForm")).filter("a").text("所有");
// 		findClass("selectToReset").filter("a").text("所有");

	}
	//创建 searchform 
	function createSearcheFormForCckContent(data, sctGuid) {
		var searchs = data.searchs;
// 		var searchBar = findClass("searchBar");
// 		var subBar = findClass("subBar");
		var searchBar = $(".searchBar",findName("cckcontentSearchForm"));
		var subBar =$(".subBar",findName("cckcontentSearchForm"));
		var count = 1;
		var searchhtml = "<ul class=\"searchContent\">";
		changeSearchBarHeightForCckContent(searchs.length);
		for ( var i = 0; i < searchs.length; i++) {
			if (count == 4) {
				count = 1;
				searchhtml += "</ul><ul class=\"searchContent\">";

			}
			searchhtml += "<li  > ";
			searchhtml += createSearchInputForCckContent(searchs[i]);
			searchhtml += " </li>";
			count++;

		}
		searchhtml += "</ul>";
		searchBar.append(searchhtml);
		subBar.appendTo(searchBar);
	}
	//更改高度
	function changeSearchBarHeightForCckContent(length) {
		var th = 113 + (Math.ceil(parseInt(length) / 3) * 25);
// 		var ttt = $("[layouth]", navTab.getCurrentPanel());

// 	$("[layouth]", navTab.getCurrentPanel()).attr("layoutH", th);
		findName("cckcontenttable").attr("layouthcck", th);
//	$("[layouthcck]",findName("syCckContentPageContent")).attr("layouthcck", th);
	}
	//新建searchform 里的 input select 等；
	function createSearchInputForCckContent(s) {
		var html = " <label>" + s.fieldLabel + "</label>";
		var input = '';
		if (s.fieldType == 4) {//code
			input = "<select name=\""
					+ s.fieldColumn.toUpperCase()
					+ "\" seq= \"desc\" class= \"combox selectToReset\" valueCode= \"valueCode\" codeType=\""
					+ s.fieldTypeReference + "\" valueDesc=\"valueDesc\">";
			input += "<option value=\"\">所有</option> </select>";
		} else 
			if (s.fieldType == 5) {//code
				var url =  "/rs/syWidgetReferenceData/"+s.fieldTypeReference ;
				if(s.fieldTypeReferenceType==1)//autocomplete
				{
					input ="<input type=\"text\" name=\""+ s.fieldColumn.toUpperCase()+"_SHOW"
						+"\" id=\""+s.fieldColumn.toUpperCase()+new Date().getTime()+"\" datavalue=\"ID\" "
						+"itemvalue=\"NAME\" autocompletetype=\""+s.fieldAutocompleteType
						+"\" domain=\"1\"  dataurl=\""+url+"\"  class=\"autocompleteForCCK\"  >";
						input +="<input type=\"hidden\" name=\""+s.fieldColumn.toUpperCase() +"\"  />";
						
						
						 
				}else	if(s.fieldTypeReferenceType==2)//autocomplete
				{
					input ="<input type=\"text\" name=\""+ s.fieldColumn.toUpperCase()+""
						+"\" datavalue=\"ID\" "
						+"itemvalue=\"NAME\"  domain=\"1\"  dataurl=\""+url+"\"    >";
				}else{
					input = "<select name=\""
						+ s.fieldColumn.toUpperCase()
						+ "\" dataurl=\""+url+"\" domain=1  class= \"combox selectToReset\" valueCode= \"ID\"    valuePid=\"PID\" valueDesc=\"NAME\">";
						
				input += "<option value=\"\">所有</option> </select>";
				}
			
				
			} else if ((s.fieldType <=3 && s.fieldType >= 1) || s.fieldType > 5) {
			input = "<input type= \"text\" name = '"
					+ s.fieldColumn.toUpperCase() + "' class= '"
					+ (3 == s.fieldType ? 'date' : '') + "' />";
		}
		html += input;
		return html;
	}
	//创建表头
	function createTableColumnForCckContent(data, sctGuid) {
		var table = findClass("cckcontenttable");
		var trTop = findName("cckcontenttr");
		var trTemplate = findName("cckcontentTrTemplate");
		var fields = data.fields;
		if (!fields) {
			fields = [];
		}
		var ths = '';
		var tts = '';
		for ( var i = 0; i < fields.length; i++) {
			ths += createThForCckContent(fields[i]);
			tts += createTemplateForCckContent(fields[i]);
		}
		trTop.append(ths);
		trTemplate.append(tts);
		table.addClass("table").parent().initUI();
		if (!data) {
			data = {"jsonData" : {"list" : []}};
		} else if (!data.jsonData) {
			data["jsonData"] = {"list" : []};
		}
		cckcontentList(data);
	}

	function createThForCckContent(field) {
		var type = 'th';
		var html = "<"+type+(field.columnWidth?(" width=\""+field.columnWidth+"\""):"")+" >" + field.fieldLabel + "</"+type+">";
		return html;
	}

	function createTemplateForCckContent(field) {
		var type = 'td';
		var classtext=(field.fieldType=='16'?" class=\"ccklongtext\" ":"   ");
		var html = "<" + type + " name='" + field.fieldColumn.toUpperCase()	+((field.fieldType=='5'||field.fieldType=='4')?"__NAME":''  )+"' "+classtext+" ></"+type+">";
		return html;
	}
	function cckcontentList(data) {
		var sampleRow = findName('cckcontentTrTemplate');
		pageResponse2Table(sampleRow, data);
		printPageBarDiv(findName('cckcontentPageBar'),
				findName('cckcontentCountDiv'), data.jsonData);
		$(".ccklongtext", findName("syCckContentPageContent")).each(function() {
			var $this = $(this);
			$this.attr("title", $this.text());
		});
		if (!data.jsonData)
			return;
		var list = data.jsonData.list;
		var checkboxes = $("[name='sccGuids']:checkbox",
				findName("syCckContentPageContent"));
		var length = checkboxes.length;
	
		for ( var i = 0; i < list.length; i++) {
			if (i < length) {
				$(checkboxes[i + 1]).data("object", list[i]);
			}
		}
	}
	function cckcontent_add() {
		var sctGuid = findName("cckcontentSearchForm").data("sctGuid");
		var masterId = findName("cckcontentSearchForm").data("masterId");
		var pData = { "sctGuid" : sctGuid, "masterId" :masterId};
		cckcontent_showDialog("新增", pData);

	}
	function _get_sccGuid_ccktypefield_edit( ) {
 
		var ids = [];
		var checkboxs = findName("syCckContentPageContent").find(
				"input:checkbox:checked");
		for ( var i = 0; i < checkboxs.length; i++) {
			var t = $(checkboxs[i]);
			var val = t.val();
			if (val && val != "on") {
				ids[ids.length] = val;
			} 

		}
		return ids;

	}
	
	function cckcontent_children_tab() {
		var ids = _get_sccGuid_ccktypefield_children_tab();
		if (ids!= null) {
			var $this=$(this);
			var sctGuid = findName("cckcontentSearchForm").data("sctGuid");
//	 		var masterId = findName("cckcontentSearchForm").data("masterId");
			var title =$this.attr("childrenTitle");
			if(!title)
				title="子表信息";
			var tabid =   $this.attr("rel");
			var authid='';
			var csctGuid=$this.attr("childrenSctGuid");
			var isReadOnly=cckcontent_is_readOnly("cckcontentSearchForm");
			var masterId=ids.val();
			
			var masterIdColumn=$this.attr("masterIdColumn");
			if(masterIdColumn){
				var object=ids.data("object");
				if(object){
					var tm=object[masterIdColumn];
					if(tm){
						masterId=tm;
					}
				}
			}
			
			var url =getURL(CPATH.domain_1,"/rs/cckRedirector/gridOrTree/"+csctGuid+"?masterId="+masterId+"&authid="+authid+"&sctGuid="+csctGuid );
		 	if(isReadOnly){
		 		url+="&readOnly=readOnly";
		 	}
			var pData={rel:tabid,href:url,title:title, fresh:"true", external:"false"};
		 
			openNavTab(pData);
		} else {
			alertMsg.error("请选择一条记录!");
		}
	}

	function cckcontent_edit() {
		var ids = _get_sccGuid_ccktypefield_edit();
		if (ids.length == 1 && ids[0]) {
			var sctGuid = findName("cckcontentSearchForm").data("sctGuid");
			var masterId = findName("cckcontentSearchForm").data("masterId");
			var pData = {
				"sctGuid" : sctGuid,
				"sccGuid" : ids[0],
				"masterId" :masterId
			};
			cckcontent_showDialog("修改", pData);
		} else {
			alertMsg.error("请选择一条记录!");
		}
	}

	function cckcontent_remove() {

		var ids = _get_sccGuid_ccktypefield_edit();
		if (ids.length > 0) {
			var sctGuid = findName("cckcontentSearchForm").data("sctGuid");
			var masterId = findName("cckcontentSearchForm").data("masterId");
			var pData = { "sctGuid" : sctGuid, "sccGuids" : ids.toString() , "masterId" : masterId };
			alertMsg.confirm("确认删除？", {
				okCall : _doPost
			});
		} else {
			alertMsg.error("请选择一条记录!");
		}
		function _doPost() {

			doPostAjax(
					getURL(CPATH.domain_1, "/rs/cck/dform/formaction/delete"),
					pData, cckcontentSearch);
		}
	}

	function cckcontent_showDialog(operAction, pData) {
		//
		var title = operAction + " -- 数据";
		var rel = "cckcontentdialog";
		var options = {};
		options.width = 550;
		options.height = 500;
		options.max = false;
		options.mask = true;
		options.maxable = true;
		options.minable = false;
		options.fresh = true;
		options.resizable = true;
		options.drawable = true;
		options.close = "";
		options.param = pData;
		options.zIndex = "1000";
		var sctGuid = findName("cckcontentSearchForm").data("sctGuid");
		var url = CPATH.domain_1
				+ "/pages/system/cck/modify/cckContentModify.html?sctGuid="
				+ sctGuid;
		$.pdialog.open(url, rel, title, options);
	}//end of showLineChooserDialog

	//判断当前页面是否是readOnly
	function cckcontent_is_readOnly(name) {
			var isReadOnly = findName(name).data("isReadOnly");
	  		if(isReadOnly==true||isReadOnly=="true")
	  			return true;
	  		else return false;
	}
	
	function findNameByParentForCck(name,parent){
		return $("[name='"+name+"']",parent);
		
	}
	//Tree 查看子表
	function cckContentTree_children_tab() {
		
		var div=findNameWithParentNameInTabForCCK("cckContentTreeInfoTree","cckContentTree_maintain_content");
		var treeId=div.attr("id");
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		var nodes = zTree.getSelectedNodes();
		var treeNode = nodes[0];  
	  	var node=treeNode; 
	  	if(node){
	  	node.sctGuid=get_sctguid_for_cck_content_tree();
	  	var id = node.__ID__;
	  	var $this=$(this);
	  	var masterIdColumn=$this.attr("masterIdColumn");
	  	if(masterIdColumn){
	  		if(node[masterIdColumn]){
	  			id=node[masterIdColumn];
	  		}
	  	}
		if (id ) {
			var title =$this.attr("childrenTitle");
			if(!title)
				title="子表信息";
			var tabid =   $this.attr("rel");
			var authid='';
			var csctGuid=$this.attr("childrenSctGuid");
			var url =getURL(CPATH.domain_1,"/rs/cckRedirector/gridOrTree/"+csctGuid+"?masterId="+id +"&authid="+authid+"&sctGuid="+csctGuid );
			var isReadOnly=cckcontent_is_readOnly("cckContentTreeform");
		 	if(isReadOnly){
		 		url+="&readOnly=readOnly";
		 	}
			var pData={rel:tabid,href:url,title:title, fresh:"true", external:"false"};
		 
			openNavTab(pData);
		} else {
			alertMsg.error("请选择一个节点!");
		}
	   	}
	  	else{
	  		alertMsg.error("请选择一个节点!");
	  	}
		
	}
	
	function findNameWithParentNameInTabForCCK(name,pname){
		return	$("[name='"+name+"']",findName(pname));
		}
		function findClassWithParentNameInTabForCCK(classname,pname){
			return	$("."+classname,findName(pname));
			}



	function 	cckContentCreateButtons(list,pageContentName,type,isReadOnly){
			var html=	'';
			for(var i=0;i<list.length;i++){
				var button=list[i];
				var buttonType=button.buttonType;
			
				if(isReadOnly){
					if(buttonType!=3&&buttonType!=5&&buttonType!=10)
					continue;
				}
				if(type==0){
					if(buttonType==7||buttonType==8){
						continue;
					}	
				}else if(type==1){
					if(buttonType==1||buttonType==3){
						continue;
					}
				}
				var time=new Date().getTime()+''+i;
			 
				if(!button.buttonName) {
					button.buttonName="cckButton"+time ;
					}
				var buttonName= button.buttonName;
				var buttonLabel=button.buttonLabel;
				var buttonIcon=button.buttonIcon;
				if(!buttonLabel||!buttonIcon) {
					if(buttonType==1&&type==0){
						if(!buttonLabel)
							button.buttonLabel="新增";
						if(!buttonIcon)
							button.buttonIcon="add";
					}else 	if(buttonType==2){
						if(!buttonLabel)
							button.buttonLabel="修改";
						if(!buttonIcon)
							button.buttonIcon="edit";
					}else 	if(buttonType==4){
						if(!buttonLabel)
							button.buttonLabel="删除";
						if(!buttonIcon)
							button.buttonIcon="delete";
					}else 	if(buttonType==5){
						if(!buttonLabel)
							button.buttonLabel="查看子表";
						if(!buttonIcon)
							button.buttonIcon="add";
					}else 	if(buttonType==3){
						if(!buttonLabel)
							button.buttonLabel="查看";
						if(!buttonIcon)
							button.buttonIcon="view";
					}else 	if(buttonType==6){
						if(!buttonLabel)
							button.buttonLabel="自定义";
						if(!buttonIcon)
							button.buttonIcon="icon";
					}
					else 	if(buttonType==7&&type==1){
						if(!buttonLabel)
							button.buttonLabel="新增子节点";
						if(!buttonIcon)
							button.buttonIcon="add";
					}
					else 	if(buttonType==8&&type==1){
						if(!buttonLabel)
							button.buttonLabel="新增根节点";
						if(!buttonIcon)
							button.buttonIcon="add";
					}
					else 	if(buttonType==9){
						if(!buttonLabel)
							button.buttonLabel="方法";
						if(!buttonIcon)
							button.buttonIcon="icon";
					}
					else 	if(buttonType==10&&type==0){
						if(!buttonLabel)
							button.buttonLabel="导出";
						if(!buttonIcon)
							button.buttonIcon="export";
					}
				}

				  buttonLabel=button.buttonLabel;
				  buttonIcon=button.buttonIcon;
				
//		 		var buttonDetailTypeCode=button.buttonDetailTypeCode;
				var buttonLink=button.buttonLink;
				if(!button.buttonLinkNavtabId) {
					button.buttonLinkNavtabId="cckButtonRel"+time;
					}
				var buttonLinkNavtabId=button.buttonLinkNavtabId;
				var buttonLinkTarget=null;
				if(buttonType==6)
				var buttonLinkTarget=button.buttonLinkTarget;
				var buttonIsMultiple=button.buttonIsMultiple;
				var buttonIsReadonly=button.buttonIsReadonly;
			 
				var target= 'customTarget="'+buttonLinkTarget+'"';
				
				 html+=	'<li><a name="'+ buttonName+'" rel="'+
				 buttonLinkNavtabId+'" class="'+ buttonIcon+'"   ';
				 if(buttonType==5){
					 html+=" childrenSctGuid="+button.childrenSctGuid;
					 html+=" masterIdColumn="+(button.masterIdColumn?button.masterIdColumn:null);
				 }
				 
				 else  if(buttonType==6)
					{ html+=target;
						html+=" url=\""+buttonLink+"\"";
						html+=" rel=\""+button.buttonLinkNavtabId+"\"";
						html+=" title=\""+button.buttonLabel+"\"";
						html+=" isMultiple=\""+button.buttonIsMultiple+"\"";
					}  
				 else  if(buttonType==9)
					{ 
						html+=" func=\""+button.functionName+"\"";
						 
					} 
				 if(button.dialogWidth){
					 
						html+= " dialogWidth=\""+button.dialogWidth+"\" ";
					 }
				 if(button.dialogHeight){
					 
						html+= " dialogHeight=\""+button.dialogHeight+"\" ";
					 }
				 html +=" href=\"#\" ";
				 html+=' ><span ';
				 if('other'==button.buttonIcon){
					 
						html+= " style=\" background: url(\'"+button.iconUrl+"\') 0 50%  no-repeat ; \" ";
					 }
				
				html+= ' >'+ buttonLabel+'</span></a></li>';
			}
			var toolBar= $(".CckToolBar",findName(pageContentName));
			toolBar.html(html);
			toolBar.parent().parent().initUI();
			for(var i=0;i<list.length;i++){
				var button=list[i];
				var $button=findNameByParentForCck(button.buttonName,toolBar);	
				//添加click方法
				var clickfn;
				buttonType=button.buttonType;
				if(isReadOnly){
					if(buttonType!=3&&buttonType!=5)
					continue;
				}
				if(type==0){
					if(buttonType==7||buttonType==8){
						continue;
					}	
				}else if(type==1){
					if(buttonType==1||buttonType==3){
						continue;
					}
				}
			
				if(buttonType==1){
					clickfn=cckcontent_add; 
				}else 	if(buttonType==2){
					clickfn=type==1?cckContentTree_edit:cckcontent_edit;
				}else 	if(buttonType==3&&type==0){
					clickfn=cckcontent_view;
				}else 	if(buttonType==4){
					clickfn=type==1?cckContentTree_remove:cckcontent_remove;
				}else 	if(buttonType==5){
					clickfn=type==1?cckContentTree_children_tab:cckcontent_children_tab;
				}else 	if(buttonType==6){
		 			clickfn=type==1?cckContentTree_custom:cckContent_custom;
//		 			clickfn=cckContent_custom;
				}else 	if(buttonType==7){
					clickfn=cckContentTree_add;
				}else 	if(buttonType==8){
					clickfn=cckContentTree_add_root;
				}
				else 	if(buttonType==9){
					clickfn=cckContent_function;
				}
				else 	if(buttonType==10){
 				clickfn=cckContent_export;
				}
				$button.bind("click",clickfn);
			}
			
		}
		//定制 
		function cckContent_custom(){
			 cckContent_custom_detail(this,1);
			 return false;
		}
		function cckContentTree_custom(){
			 cckContent_custom_detail(this,2);
			 return false;
		}
		//  1list 2 tree
		function cckContent_custom_detail(obj,type){
			var $this=$(obj);
			var url=$this.attr("url");
			var customTarget=$this.attr("customTarget");
			var rel=$this.attr("rel");
			var title=$this.attr("title");
			var isMultiple=$this.attr("isMultiple");
			var dialogWidth=$this.attr("dialogWidth");
			var dialogHeight=$this.attr("dialogHeight");
			if(isMultiple!=2&&isMultiple!='2'){
				var ids = null;
				var sctGuid='';
				var masterId='';
				if(type==1){ids=_get_sccGuid_ccktypefield_edit();
			  sctGuid = findName("cckcontentSearchForm").data("sctGuid");
			
				  masterId = findName("cckcontentSearchForm").data("masterId");
				}
				else{
				  sctGuid=get_sctguid_for_cck_content_tree();
				  masterId = get_masterid_for_cck_content_tree();
					ids=_get_sccGuid_ccktypefield_edit_for_tree();
				}
				if(isMultiple==0||isMultiple=='0'){
					if (ids.length == 1 && ids[0]){}else {
						alertMsg.error("请选择一条记录!");
						return false;
					}
				}else{
					if (ids.length >=1 && ids[0]){}else {
						alertMsg.error("请选择记录!");
						return false;
					}
				}
					var urls=url.split("?");
					var id='';
				 if(isMultiple==1||isMultiple=='1'){
					 id=ids.toString();
				 }else{
					 id=ids[0];
				 }
				 url=	urls[0]+"?"+"sctGuid="+sctGuid+"&sccGuid="+id+"&masterId="+masterId;
					if(urls[1]){
						url+="&"+urls[1];
					}
				
			}

			if("dialog"==customTarget){
				var pData = {
						"sctGuid" : sctGuid,
						"sccGuid" : id,
						"masterId" :masterId ,
						"height":dialogHeight,
						"width":dialogWidth
					};
				cckcontent_showDialogCustom(title,rel,url, pData);
			}else 	if("resource"==customTarget){
				//TODO resuorce
			}else{
					openNavTab({rel:rel,tabid:rel,href:url,title: title,fresh:"true", external:"false"}); 
			}
			return false;

		}
		function cckcontent_showDialogCustom(title,rel,url, pData) {
			//
			var options = {};
			options.width = pData.width? pData.width:550;
			options.height = pData.height? pData.height: 500;
			options.max = false;
			options.mask = true;
			options.maxable = true;
			options.minable = false;
			options.fresh = true;
			options.resizable = true;
			options.drawable = true;
			options.close = "";
			options.param = pData;
			options.zIndex = "1000";
			$.pdialog.open(url, rel, title, options);

		}//end of showLineChooserDialog
		function cckContent_function(){
			var $this=$(this);
			var func=$this.attr("func");
			if(func&&window[func]){
				 window[func]();
			}
		}
		function cckcontent_view() {
			var ids = _get_sccGuid_ccktypefield_edit();
			if (ids.length == 1 && ids[0]) {
				var sctGuid = findName("cckcontentSearchForm").data("sctGuid");
				var masterId = findName("cckcontentSearchForm").data("masterId");
				var pData = {
					"sctGuid" : sctGuid,
					"sccGuid" : ids[0],
					"masterId" :masterId,
					"isView":true
				};
				cckcontent_showDialog("查看", pData);
			} else {
				alertMsg.error("请选择一条记录!");
			}
		}
		function _get_sccGuid_ccktypefield_children_tab( ) {
			 
			var ids = [];
			var checkboxs = findName("syCckContentPageContent").find(
					"input:checkbox:checked");
			var index=null;
			for ( var i = 0; i < checkboxs.length; i++) {
				var t = $(checkboxs[i]);
				var val = t.val();
			
				if (val && val != "on") {
					ids[ids.length] = val;
					index=i;
					if(ids.length>1)
						return null;
				} 

			}
			if(index!=null){
				return  $(checkboxs[index]);
			}else{
				return null;
			}
			

		}

		function cckContent_export( opt) {
			//序列化表单值，并扩展到syCckTypeArgs对象中
//	 		$.extend(findName("cckcontentSearchForm").data("arg"), findName(
//	 				'cckcontentSearchForm').serializeJson(), opt ? opt : {});
			//执行ajax查询方法
//	 		var sctGuid = navTab._getParam("sctGuid");
//	 		var masterId = navTab._getParam("masterId");

			var sctGuid = 	findName("cckcontentSearchForm").data("sctGuid" );
			var masterId = findName("cckcontentSearchForm").data("masterId" );
			var arg = findName("cckcontentSearchForm").data("arg");
			var tabid=navTab.getTabId();
//	 		var filename=$(".selected .cck-ajax span").text();
		var filename=	$("[name=cckName]", findName("cckcontentSearchForm")).val();
			if(!filename){
				filename=$("."+tabid).attr("title");
			}
			if(!filename){
				filename=sctGuid;
			}
			var url=getURL(CPATH.domain_1, "/rs/cck/dgrid/export/" + sctGuid+"/"+filename+"?"+(masterId?("masterId="+masterId):""));
			for(var n in arg){
				url+="&"+n+"="+arg[n];
			}
			findName("fileDownFrame").attr("src",url);
		}