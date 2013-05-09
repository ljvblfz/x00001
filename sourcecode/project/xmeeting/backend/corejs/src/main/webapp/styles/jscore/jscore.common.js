var isneedCNName = "true";

var REG_NUM = /^\-?\d+(.\d+)?$/;

//var routeLsn={"10","",""};

function batchSetShiftCnName(objList){
	
	
}

function setShiftCnName(obj){
	if(true){
		
	}
}

function comboxExchangeShiftCnName(combox){
	$.each(combox.find("option"), function(index) {
		$(this).text(exchageShiftCnName($(this).text()));
	});
}


function exchageShiftCnName(numname){
	var x=numname;
	if(isneedCNName=="true"){
		if(numname.length>3 && REG_NUM.test(numname.substring(numname.length-3,numname.length))){
			var part1 = numname.substring(0,numname.length-3);
			var part2 = parseInt(numname.substring(numname.length-3,numname.length-2),10);
			var part3 = parseInt(numname.substring(numname.length-2,numname.length),10);
			var upTimes=19;
			var cnType;
//			if(jQuery.inArray( part1, routeLsn )!=-1){
//				//环线班次中文名
//				if(part3<20) 
//					cnType='早班';
//				else if (20<part3 && part3<40){
//					cnType='中班';
//					part3=part3-20;
//				}else if (40<part3 && part3<60){
//					cnType='两头班';
//					part3=part3-40;
//				}else if (60<part3 && part3<70){
//					upTimes=9;
//					cnType='夜班';
//					part3=part3-60;
//				}else if (70<part3 && part3<80){
//					cnType='机动班';
//					part3=part3-70;
//				}else if (80<part3 && part3<90){
//					cnType='加班';
//					part3=part3-80;
//				}
//				numname= part1+cnType+(part2*upTimes+part3);
//			}else{
				//班次循环数量 除了夜班为9 其余为19
				if(part3<20) 
					cnType='早';
				else if (20<part3 && part3<40){
					cnType='中';
					part3=part3-20;
				}else if (40<part3 && part3<60){
					cnType='两头';
					part3=part3-40;
				}else if (60<part3 && part3<70){
					upTimes=9;
					cnType='夜';
					part3=part3-60;
				}else if (70<part3 && part3<80){
					upTimes=9;
					cnType='机动';
					part3=part3-70;
				}else if (80<part3 && part3<90){
					upTimes=9;
					cnType='加';
					part3=part3-80;
				}
				numname= part1+cnType+(part2*upTimes+part3);
//			}
		}
	}
	return numname;
}



