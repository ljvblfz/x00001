var REG_NATURAL_NUM = /^[1-9]\d*$/;
var REG_NON_NEGATIVE_INTEGER=/^[0-9]+$/;
var REG_INT_NUM = /^-?\d+$/;
var REG_NUM = /^\-?\d+(.\d+)?$/;
var REG_NUM_YAO = /^[-\+]?\d+(\.\d+)?$/;
var REG_WITH_2_NUM= /^\d+(?:\.\d{1,2})?$/;
var REG_ONLY_2DECIMAL_NUM =/^\d+(\.\d{2})?$/;
var REG_ONLY_4DECIMAL_NUM =/^\d+(\.\d{1,4})?$/;
var REG_ONLY_6DECIMAL_NUM =/^\d+(\.\d{1,6})?$/;
var REG_2DECIMAL_NUM = /^\d+(\.\d{1,2})?$/;
var REG_1DECIMAL_NUM = /^\d+(\.\d{1})?$/;
var REG_8_12_DIGITS_NUM = /^\d{8,12}$/;
var REG_EMAIL = /^([a-zA-Z0-9_\-])([a-zA-Z0-9_\-\.]*)@(\[((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])\.){3}|((([a-zA-Z0-9\-]+)\.)+))([a-zA-Z]{2,}|(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])\])$/;
var REG_MUTI_EMAIL=/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+(;)*(( )*(;)+( )*[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+(;)*)*$/;
var REG_FILE_FOLDER=/^[^\\\/\?\*\&quot;\'\&gt;\&lt;\:\|\<\>]*$ /;
var REG_FILE_NAME=/^(([a-zA-Z]:)|(\\{2}\w+)\$?)(\\(\w[\w].*))+(.mp3|.xls|.doc|.pdf|.txt|.jpg|.gif)$/;
// hanyanwei modified start 2009-12-09 20:38
var REG_DATE_STRICT = /^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))$/;
//var REG_DATE_STRICT = /^((19)|(20))[0-9]{2}-((0?[1-9])|(1[0-2]))-((0?[1-9])|([12][0-9])|(3[0-1]))$/;
var REG_TELE_NUMBER = /^0\d{2,3}-[1-9]\d{6,7}$/;
// hanyanwei modified end
var REG_DATE_LOGBACKUP = /^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/;
var REG_YEAR = /^(19|20)\d\d+$/;
var REG_POSITIVE = /^\d+(\.\d+)?$/;
var LEAP_YEAR = /^\d{2}([02468][048]|[13579][26])?$/;
var POS_WHOLE_NUM = /^\d+$/;
//var REG_DATE = /^(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)\d\d+$/;
var TWELVE_HOUR_TIME = /^((0[1-9])|(1[0-2]))([0-5][0-9])$/;
var REG_TWENTY_FOUR_HOUR_TIME = /^(([0-1][0-9])|(2[0-3]))([0-5][0-9])$/;
var NATURE_NUM = /^[1-9]\d*$/;
var REG_FULL_DATE_TIME = /^((((((0?[1-9])|([1-2][0-9])|(3[01]))\/((0?[13578])|(1[02])))|(((0?[1-9])|([1-2][0-9])|(30))\/((0?[469])|(11)))|(((0?[1-9])|([1-2][0-9]))\/02))\/\d{2}(([02468][048])|([13579][26])))|(((((0[1-9])|([1-2][0-9])|(3[01]))\/((0[13578])|(1[02])))|(((0[1-9])|([1-2][0-9])|(30))\/((0[469])|(11)))|(((0[1-9])|(1[0-9])|(2[0-8]))\/02))\/\d{2}(([02468][1235679])|([13579][01345789]))))(\s(([0-1][0-9])|(2[0-3]))\:([0-5][0-9]))$/
var REG_NUM_ALPHA = /^[A-Za-z0-9]+$/;
var REG_FULL_TIME = /^((0[0-9])|([1-2][0-3])|([1][4-9]))\:([0-5][0-9])$/;
var FILE_TYPE = /^\.(JPG|IMG|JPEG|BMP|GIF|JAR|TXT|ZIP|RAR|DOC|HTML|PPS|PDF|CHM|MHT|JAVA|MP3|AVG|RMVB|AVI|XLS|3GP|wmv|MPEG|FLV|MP4)(\;(\s)?\.(JPG|IMG|JPEG|BMP|GIF|JAR|TXT|ZIP|RAR|DOC|HTML|PPS|PDF|CHM|MHT|JAVA|MP3|AVG|RMVB|AVI|XLS|3GP|wmv|MPEG|FLV|MP4))*$/;
var REG_MOBILE = /^\d{11,11}$/;
var URL_COM = /^(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/
var REG_DIR = /^\w+$/
var REG_POST_NUM = /^\d{6,6}$/;
var REG_POST_CODE =/^\d{6}$/;
var REG_NUM_ALPHA_UNDERSCORE = /^[A-Za-z0-9\_]+$/;
var REG_NUM_ALPHA_SCORE = /^[A-Za-z0-9\_\-]+$/;
var REG_INTEGER = /^-?\d+$/; //The regExp for integer format
var REG_FIXED_TELEPHONE=/0\d{2,3}-\d{5,9}|0\d{2,3}-\d{5,9}/;
var REG_CORPCODE=/\d-\d/;
var ONLY_VALIDATE_YEAR=/^(19|20)\d{2}$/;
var REG_DATE_YYYY_MM = /^(19|20)\d{2}-((0[1-9])|(1[0-2]))$/;
var ID_CARD_VALIDATE=/^[1-9][0-9]{14}$|^[1-9]{1}[0-9]{16}[0-9xX]{1}$/;
var REG_VALIDATE_PHONE=/^\d{8}$/;
var File_PATH_VALIDATE =/[a-zA-Z]:\\[^\/\\<>\*\?\:"\|]+$/;
var REG_LOGIN_NAME = /[\'\"\,\<\>\+\-\*\/\%\^\=\\\!\&\|\(\)\[\]\{\}\:\;\~\`\#\$]+/;
var REG_PHONE_MOBILE=/^[0]?\d{11}$/;
var REG_PHONE_LANDLINE=/^(\d{3,4}\-)?\d{5,9}(\-\d{3,5})?$/;
var REG_PHONE=/^(([0]?\d{11})|((0\d{2,3}\-)?\d{5,9}(\-\d{3,5})?))$/;
var REG_IPV4=/^(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])(\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])){3}$/;
/**
 * @requires jquery.validate.js
 * @author ZhangHuihua@msn.com
 */
(function($){
	if ($.validator) {
		$.validator.addMethod("alphanumeric", function(value, element) {
			return this.optional(element) || /^\w+$/i.test(value);
		}, "Letters, numbers or underscores only please");
		
		$.validator.addMethod("lettersonly", function(value, element) {
			return this.optional(element) || /^[a-z]+$/i.test(value);
		}, "Letters only please"); 
		
		$.validator.addMethod("phone", function(value, element) {
			return this.optional(element) || /^[0-9 \(\)]{7,30}$/.test(value);
		}, "Please specify a valid phone number");
		
		$.validator.addMethod("postcode", function(value, element) {
			return this.optional(element) || /^[0-9 A-Za-z]{5,20}$/.test(value);
		}, "Please specify a valid postcode");
 		 
		$.validator.addMethod("validatetime", function(value, element) {
			value = value.replace(/\s+/g, "");
			if (String.prototype.parseDate){
				var $input = $(element);
				var pattern = $input.attr('format') || 'yyyy-MM-dd';
	
				return !$input.val() || $input.val().parseDate(pattern);
			} else {
				return this.optional(element) || value.match(/^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/);
			}
		}, "请输入正确的时间");
		$.validator.addMethod("date", function(value, element) {
			value = value.replace(/\s+/g, "");
			if (String.prototype.parseDate){
				var $input = $(element);
				var pattern = $input.attr('format') || 'yyyy-MM-dd';
	
				return !$input.val() || $input.val().parseDate(pattern);
			} else {
				return this.optional(element) || value.match(/^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/);
			}
		}, "Please enter a valid date.");
		
		jQuery.validator.addMethod("validateAddShiftname", function(value, element) {   
			 return this.optional(element) || (/^.+9{1}.{1}$/.test(value));   
		}, "加班班次名称长度不足，并且倒数第二位应该是9");
		
		jQuery.validator.addMethod("validateAddCirclename", function(value, element) {   
			 return this.optional(element) || (/^.+[7]{1}.{1}$/.test(value));   
		}, "班次名称长度不足，并且倒数第二位应该是7");
		
		jQuery.validator.addMethod("validatePositiveNumber", function(value, element) {   
			 return this.optional(element) || (/^\d+(\.\d+)?$/.test(value));   
		}, "请输入正数");
		jQuery.validator.addMethod("email", function(value, element) {   
			 return this.optional(element) || (REG_EMAIL.test(value));   
		}, "邮箱格式不正确");
		
		jQuery.validator.addMethod("mobile", function(value, element) {   
			 return this.optional(element) || (REG_PHONE_MOBILE.test(value));   
		}, "手机号码不正确");
		
		jQuery.validator.addMethod("officephone", function(value, element) {   
			 return this.optional(element) || (REG_VALIDATE_PHONE.test(value));   
		}, "办公号码不正确");
		
		// 验证两次输入值是否不相同
		  jQuery.validator.addMethod("notEqualTo", function(value, element, param) {
			  var fun = new Function('return ' + param);
			  var h = fun();
			  return value != h.val();
		  }, $.validator.format("两次输入不能相同!"));

//		var dwzjqueryRemoteValidateErrorMsg='';
		$.validator.addMethod("validateRemote", function(value, element) {
//			boolean
//			dwzjqueryRemoteValidateErrorMsg='';
				var param=$(element).attr("validateRemote");
				 
				var domainn=$(element).attr("domain");
				var domain= getDomain(domainn);
				param=getURL(domain?domain:'',param);
			

		if ( this.optional(element) )
				return "dependency-mismatch";

			var previous = this.previousValue(element);
			if (!this.settings.messages[element.name] )
				this.settings.messages[element.name] = {};
			previous.originalMessage = this.settings.messages[element.name].validateRemote;
			this.settings.messages[element.name].validateRemote = previous.message;

			

			if ( this.pending[element.name] ) {
				return "pending";
			}
//			if ( previous.old === value ) {
//				return previous.valid;
//			}

			previous.old = value;
			var validator = this;
			this.startRequest(element);
			var data = {};
			if($(element).attr("formData")){
			data=	$(element.form).serializeArray();
			ename={name:'elementname',value:element.name};
			data.push(ename);
			}else
			{data[element.name] = value;
			data.elementname=element.name;
			}
			
			
			 if(!param.charAt(param.length-1)=='/'){
 				param+="/";
 				};
			param+=value;
			
			$.ajax($.extend(true, {
				previous:previous,
				url: param,
				mode: "abort",
				port: "validate" + element.name,
				dataType: "json",
				data: data,
				success: function(response) {
					validator.settings.messages[element.name].validateRemote = previous.originalMessage;
					var valid = response.result === true;
					
					if ( valid ) {
						var submitted = validator.formSubmitted;
						validator.prepareElement(element);
						validator.formSubmitted = submitted;
						validator.successList.push(element);
						validator.showErrors();
					} else {
						 
						var errors = {};
						var message = response || validator.defaultMessage( element, "validateRemote" );
						errors[element.name] = previous.message =response.message?response.message:($.isFunction(message) ? message(value) : message);
						validator.showErrors(errors);
						//dwzjqueryRemoteValidateErrorMsg=response.message?response.message:($.isFunction(message) ? message(value) : message);
					}
					previous.valid = valid;
					validator.stopRequest(element, valid);
				},error: function(){
					validator.stopRequest(element, true);
					previous.valid = false;
					var errors = {};
					errors[element.name] = '通讯错误!';
					validator.showErrors(errors);
				}
			}, param,previous));
			return previous.valid;
		}, "Please enter a valid date.");
		jQuery.validator.addMethod("biggerThan",
				function(value, element, param) {
					var target = $(param).unbind(".validate-equalTo").bind(
							"blur.validate-equalTo", function() {
								$(element).valid();
							});
					return this.optional(element) || value - target.val() > 0;
				}, "数字太小");
		jQuery.validator.addMethod("litterThan",
				function(value, element, param) {
					var target = $(param).unbind(".validate-equalTo").bind(
							"blur.validate-equalTo", function() {
								$(element).valid();
							});
					return this.optional(element) || value - target.val() < 0;
				}, "数字太大"); 
		jQuery.validator.addMethod("biggerThanByName",
				function(value, element, param) {
					var target = findName(param).unbind(".validate-equalTo").bind(
							"blur.validate-equalTo", function() {
								$(element).valid();
							});
					return this.optional(element) || value - target.val() > 0;
				}, "数字太小");
		jQuery.validator.addMethod("bigger1ByName",
				function(value, element, param) {
					var target = findName(param).unbind(".validate-equalTo").bind(
							"blur.validate-equalTo", function() {
								$(element).valid();
							});
					return this.optional(element) || value - target.val()==1;
				}, "数字必须连续");
		jQuery.validator.addMethod("litterThanByName",
				function(value, element, param) {
		 
					var target = findName(param).unbind(".validate-equalTo").bind(
							"blur.validate-equalTo", function() {
								$(element).valid();
							});
					return this.optional(element) || value - target.val() < 0;
				}, "数字太大"); 
		jQuery.validator.addMethod("yearlitterThanByName",
				function(value, element, param) {
					
					var target = findName(param).unbind(".validate-equalTo").bind(
							"blur.validate-equalTo", function() {
								$(element).valid();
							});
					value = value.replace(/-/g,"/");
					targetValue = target.val();
					targetValue = targetValue.replace(/-/g,"/");
					var dt1 = new Date(Date.parse(value));
					var dt2 = new Date(Date.parse(targetValue));
					var flag = dt2>dt1;
					return this.optional(element) || flag==false;
				}, "领回失物时间必须晚于失物时间");
		jQuery.validator.addMethod("ipv4",
				function(value, element, param) {
			 return this.optional(element) || (REG_IPV4.test(value));   
				}, "请输入正确的ip地址");
		jQuery.validator.addMethod("maxlengthChinese", function(value, element,param) {
			return this.optional(element) ||  value.replace(/[^\x00-\xff]/g,"**").length-param<=0;
		},  "请输入少于{0}个字符");
		$.validator.addClassRules({
			date: {date: true},
			alphanumeric: { alphanumeric: true },
			lettersonly: { lettersonly: true },
			phone: { phone: true },
			postcode: {postcode: true},
			validateRemote: {validateRemote: true},
			ipv4:{ipv4:true},
			maxlengthChinese:{maxlengthChinese:true}
			 
		});
		$.validator.setDefaults({errorElement:"span"});
		$.validator.autoCreateRanges = true;
		
	}

})(jQuery);