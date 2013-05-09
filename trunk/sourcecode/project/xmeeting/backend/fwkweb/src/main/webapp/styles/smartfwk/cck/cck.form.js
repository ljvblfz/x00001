
var cckform = {
		buildDForm : function(urlx,divname,callback) {
			var dtd = $.ajax({
				type : "GET",
				url : getURL(CPATH.domain_1, urlx),
				dataType : "json", 
				cache : false,
				global : false
			});
			dtd.done(function(respJson) {   
 
				var $form=findDialogName(divname);
				$form.dform(respJson.jsonData );
				 //call function from dwz.ui.js 
				$form.data("validator",null);//删除之前的validator
				$form.parent().initUI();
				$form.find(".pageFormContent").layoutH($form.parent().parent());
//					var $form = $form;
//					$form.find("[name]").populate({"FIELD_DATA6":"dasd"} );
				 $(":button.close",$form).click(function(){
						$.pdialog.close($form.parent().parent().parent());
						return false;
					});
				 if(callback)
				 { callback();}
				 
			}); 
			dtd.fail(function(respJson) {  
				//logger.info("fail..."+respJson);
			});
			
		}
};

function changeCallBackMethod(fromTree){
	if(fromTree){
	findDialogName("callBackMethod").val("cckContentTree_refresh_tree");
	findDialogName("navTabId").val("cckcontenttreenavTab");}
	
}
function customAutocompleteForCCK2(obj){
	//findName("userStr").attr("opts",{deptallow:"yes"});
	//findName("userStr").attr("dataurl",CPATH.domain_3 + "/rs/staff/" + lbguid + "/apply?order=username_asc");
	 
	var $this = $(obj);

	
	var opts= $this.attr("opts");
	var autocompleteType= $this.attr("autocompleteType");
	if(1==autocompleteType){
		createAutocompleteForCCK($this,$this.attr("dataurl"));
	}
	else{
		doGetAjax($this.attr("dataurl"),opts?opts:{},function(data){
			if(data&&data.jsonData){createAutocompleteForCCK($this,data.jsonData);} 
			
		});
	}
	//$this.click(function(){$(this).parent().remove();});
}
function createAutocompleteForCCK2($this,data){
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