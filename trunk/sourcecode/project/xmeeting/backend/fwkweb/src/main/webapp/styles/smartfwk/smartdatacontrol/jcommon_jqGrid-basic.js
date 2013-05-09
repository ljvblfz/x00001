//jqGrod refine and wrapper 
(function($) {
	// Attach this new method to jQuery
	$.fn.extend({

		// This is where you write your plugin's name
		broadsoft_jgGrid : function(options) {

			var defaults = {
				editCaptionPrefix : "修改-",
				addCaptionPrefix : "新增-",
				deleteCaptionPrefix : "删除-",
				viewCaptionPrefix : "查看-",
				searchCaptionPrefix : "查找-",
				columnChooserCaptionPrefix : "请选择列-",
				formCancelButton:"关闭",
				formSaveButton:"提交"
			};

			var options = $.extend(defaults, options);
			// Iterate over the current set of matched elements
			return this.each(function() {
				var grid=$(this);
				$(this).jqGrid({

					treeGrid: options.treeGrid,
                	treeGridModel: options.treeGridModel,
                	treedatatype: 'json',
                	ExpandColumn: options.ExpandColumn,  
                	ExpandColClick: true,
					treeReader: options.treeReader, 
					url : options.getdataurl,
					editurl : options.editurl,   
					colNames : options.colNames,
					colModel : options.colModel,
					//tree grid setting begin
					
					rowNum : 10,
					rowList : [ 10, 20, 30 ],
					rownumbers: options.rownumbers, //it should be false for the tree grid
					viewrecords : true,
					pager : options.pager,
					sortname : options.sortname,
					sortorder :  options.sortorder, 
					caption : options.title,
					width:options.width,
					height:options.height,
					multiselect: options.multiselect,
					//tree grid setting end
					onSelectRow : function(id) {  
						if(options.detailgridid&&null!=options.detailgridid&&""!=options.detailgridid){
							$("#"+options.detailgridid+"_list").jqGrid('setGridParam',{url:"fwk/smartdatacontrol/sale_order_detail.query?q=1&dataControlId=sale_order_detail&qparam_sale_order_guid="+id,page:1});
							//var capation=$("#"+options.detailgridid+"_list").jqGrid('getCaption');
					        var capation = $("#" + options.detailgridid+"_list").jqGrid('getGridParam', 'caption');   
					        if(capation.indexOf(":")>0){
					        	capation=capation.substring(0,capation.indexOf(":"));
					        }
					        
							$("#"+options.detailgridid+"_list").jqGrid('setCaption',capation+": "+id).jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid'); 

							//alert(id+" reload done.");
						} 
					}, 
					//datatype : "json",
					datatype : options.getdatatype,
					jsonReader:options.jsonReader 
				});
				 

				// Navigation for the edit/add/delete/search/view
				$(this).navGrid(
						'#' + options.pager,
						{
							edit : true,
							add : true,
							del : true,
							view : true,
							search : true,
							refresh : true
						},
						{ 
							width:500,
							recreateForm: true,
							modal:true,
							url : options.formaction,
							editCaption : options.editCaptionPrefix
									+ options.title,
							bSubmit : options.formSaveButton,
							bCancel : options.formCancelButton, 
							afterSubmit : function(response, postdata) { 
								if (response.responseText == "Success") {
									jQuery("#success").show();
									jQuery("#success").html(
											"修改成功！");
									jQuery("#success").fadeOut(6000);
									return [ true, response.responseText ]
								} else {
									return [ false, response.responseText ]
								}
							}, 
							afterShowForm: function(formid){
								//xheditor richtext enabled
								if(options.jrichtext_fieldid){ 
									if(options.jrichtext_opts){ 
										$("#"+options.jrichtext_fieldid,formid).xheditor(options.jrichtext_opts);
									}else{ 
										$("#"+options.jrichtext_fieldid,formid).xheditor({tools:'mini'});
									}
								}
								//Adjacency tree view
								if(options.treeReader&&options.treeReader.parent_id_field){
								    // change the selector appropriately  
									var selRowData;
									var rowid = grid.jqGrid('getGridParam', 'selrow');     
									if (rowid&&rowid !== null) {
									    selRowData= grid.jqGrid('getRowData', rowid); 
									    var parent=selRowData[options.treeReader.parent_id_field]; 
									    $('#'+options.treeReader.parent_id_field).val(parent);
									    $('#'+options.treeReader.parent_id_field).attr("readonly","true");
									}else{
										alert("请选择一行数据.");
										return ;
									}
								}
							},
							onInitializeForm: function(formid){
								//alert("onInitializeForm for edit");
							}
						},// edit
						{
							width:500,
							recreateForm: true,
							modal:true,
							url : options.formaction,
							addCaption :  options.addCaptionPrefix + options.title,
							bSubmit : options.formSaveButton,
							bCancel : options.formCancelButton, 
							afterSubmit : function(response, postdata) { 
								if (response.responseText == "Success") {
									jQuery("#success").show();
									jQuery("#success").html(
											"新增成功！");
									jQuery("#success").fadeOut(6000);
									return [ true, response.responseText ]
								} else {
									return [ false, response.responseText ]
								}
							}, 
							afterShowForm: function(formid){  
								//xheditor richtext enabled
								if(options.jrichtext_fieldid){ 
									if(options.jrichtext_opts){ 
										$("#"+options.jrichtext_fieldid,formid).xheditor(options.jrichtext_opts);
									}else{ 
										$("#"+options.jrichtext_fieldid,formid).xheditor({tools:'mini'});
									}
								}
								//Adjacency tree view  
								if(options.treeReader&&options.treeReader.parent_id_field){
								    // change the selector appropriately  
									var selRowData;
									var rowid = grid.jqGrid('getGridParam', 'selrow');      
									if (rowid&&rowid !== null) {
									    selRowData= grid.jqGrid('getRowData', rowid); 
									    var parent=selRowData[options.jsonReader.id]; 
									    $('#'+options.treeReader.parent_id_field).val(parent);
									    $('#'+options.treeReader.parent_id_field).attr("readonly","true");
									}else{
										alert("请选择一行数据.");
										return ;
									}
								}
							},
							onInitializeForm: function(formid){
								//alert("onInitializeForm for add");
							}
						},// add
						{
							modal:true,
							url : options.formaction,
							caption :  options.deleteCaptionPrefix + options.title,
							msg : "删除所选记录?",
							bSubmit : options.formSaveButton,
							bCancel : options.formCancelButton, 
							afterSubmit : function(response, postdata) {
								alert(response.responseText);
								if (response.responseText == "Success") {
									jQuery("#success").show();
									jQuery("#success").html(
											"删除成功！");
									jQuery("#success").fadeOut(6000);
									return [ true, response.responseText ]
								} else {
									return [ false, response.responseText ]
								}
							}
						},// delete
						{

							modal:true,
							url : options.formaction,
							caption :  options.searchCaptionPrefix + options.title,
							Find : "Find",
							Reset : "Reset",
							multipleSearch : true,
							afterSubmit : function(response, postdata) {
								//alert(response.responseText);
								if (response.responseText == "Success") {
									jQuery("#success").show();
									jQuery("#success").html(
											"查找成功！");
									jQuery("#success").fadeOut(6000);
									return [ true, response.responseText ]
								} else {
									alert("else");
									return [ false, response.responseText ]
								}
							}

						},// search
						{
							modal:true,
							caption :  options.viewCaptionPrefix + options.title,
							bClose : "关闭"

						}// view
				); // end of navGrid
				
				//this is depending on the jquery.ui and multiple.select plugin 
				$(this).navButtonAdd('#' + options.pager, {
					modal:true,
					caption : "",
					title : options.columnChooserCaptionPrefix+  options.title, 
					onClickButton : function() {  
						$(this).columnChooser();  
					}
				});//end of column chooser
				//filterToolbar integration
				$(this).filterToolbar( { stringResult: true, searchOnEnter: true, defaultSearch: 'cn' });
				$(this)[0].toggleToolbar();
				$(this).navButtonAdd('#' + options.pager, {
					modal:true,
					caption : "",  
					title : "高级查找",  
					buttonicon : 'ui-icon-pin-s',  
					onClickButton : function() {  
						//$(this).filterToolbar().toggleToolbar();  
						$(this)[0].toggleToolbar();
					}  
				});//end of enable search toolbar 

			});
		}
	});// end of plugin
	// 
})(jQuery);