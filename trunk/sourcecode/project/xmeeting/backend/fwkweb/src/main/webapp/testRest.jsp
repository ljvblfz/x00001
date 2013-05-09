<script type="text/javascript">

/* 增加用户信息 **/
function addAccount(){
	var xmlHttp = ajaxObjHolder();
	//注：此处参数要和addUser方法中的参数名一一对应，这样数值才能自动存入其中。
	var params = "{'bizTypeId':'021','canJt':'1','bizTypeName':'阿斯顿发'}";
	xmlHttp.onreadystatechange=function(){
		if(xmlHttp.readyState==4){
			alert(xmlHttp.status);
			if(xmlHttp.status == 200){
				alert("results: "+xmlHttp.responseText);
			}
		}
	};
	
	// 注意此URL的写法,
	//  ws为 web.xml中配置的路径，
	// user 为restletContext.xml文件中的attachments的key值，
	// addUser为请求操作方法名
	xmlHttp.open("POST","/syWeb/rs/bizFeeType",true);
	xmlHttp.setRequestHeader("Content-Type", "application/json");
	xmlHttp.setRequestHeader("charset", "UTF-8");
	xmlHttp.send(params);    
}

/** obtain ajax object */
function ajaxObjHolder(){
	var xmlHttp;
	try{
		// Firefox, Opera 8.0+, Safari
		xmlHttp=new XMLHttpRequest();
		//netscape.security.PrivilegeManager.enablePrivilege("UniversalBrowserRead"); 
	}catch (e){
		// Internet Explorer
		xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
	}
	return xmlHttp;
}

</script>

<input type="button" name="添加帐号" value="添加帐号" onclick="javascript:addAccount();" />

