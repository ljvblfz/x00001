<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<style type="text/css">
 
.textleft{
 	text-align:left;
}

.textcenter{
 	text-align:center;
}

</style>
<script type="text/javascript">

//查看
$(function(){
		findName("timetable_saveapplydate").attr("dataform","aduitTimeTableForm");	
		var bsguid ="<%=request.getParameter("bizKey")%>";
		doGetAjax(CPATH.domain_3 +"/rs/linebusschedule/" + bsguid + "/apply",null,setAduitTimeTableData);
		generateAduitVerticalTable(bsguid);
		initUI($("#timetableworkflow"));
});

function setAduitTimeTableData(data){
	var lineBusschedule = data.jsonData.lineBusschedule;
	var lineLineinfo = data.jsonData.lineLineinfo;
	var depart = data.jsonData.depart;
	var lineTimequantum = data.jsonData.lineTimequantum;
	var busScheDetailPlan = data.jsonData.busScheDetailPlan;

	var details = data.jsonData.detail;
	$("input[name='applyDate']").val(lineBusschedule.STARTDT);
	$("input[name='avgbcshift']").val(lineBusschedule.AVGBCSHIFT);
	$("td > span",$("#timetableworkflow")).each(function(){
	 	$this = $(this);
		var name = $this.attr("name");
		if(name && name.indexOf("span")>=0){
		 	var attrName = name.substring(4);
		 	if(attrName=="ORGNAME"){
		 		$this.html(depart[attrName]);
		 	}else if(attrName=="LINELINEINFO"){
		 		$this.html(createAduitLineInfoTable(lineLineinfo));
		 	} else if(attrName=="LINETIMEQUANTUM"){
		 		$this.html(createAduitTimeTable(lineTimequantum));
		 	} else if(attrName=="HINTERVAL"){
		 		$this.html(createAduitIntervalTable(lineLineinfo,"H"));
		 	} else if(attrName=="LINTERVAL"){
		 		$this.html(createAduitIntervalTable(lineLineinfo,"L"));
		 	} else if(attrName=="NINTERVAL"){
		 		$this.html(createAduitIntervalTable(lineLineinfo,"N"));
		 	}
		 	else {
		 		$this.html(lineBusschedule[attrName]);		 		
		 	}		 	
		}	
	});
	addAduitBusScheDetailPlan(busScheDetailPlan,lineBusschedule["BCSHIFT"]);
	createAduitShiftTimeTable(busScheDetailPlan);
	createAduitShiftDistanceTable(busScheDetailPlan);
	createTurnovertimeTable(lineBusschedule.BSGUID,lineBusschedule.FSNAME,lineBusschedule.LSNAME);
	createAduitWorkandrestTableForTimeTable(details);
}


function createAduitLineInfoTable(data){
	
	var html = "<table><tr height=\"20\"><td width=\"150\">线路方向</td>";
		html += "<td width=\"150\">起点站</td>";
		html += "<td width=\"100\">起点首班时间</td>";												
		html += "<td width=\"100\">终点末班时间</td>";												
		html += "<td >终点站</td></tr>";											
		$.each(data,function(){
			html += "<tr>";
			html += "<td>" + this.LDIRECTION + "</td>";
			html += "<td>" + this.LFSTDNAME + "</td>";
			html += "<td>" + this.LFSTDFTIME + "</td>";
			html += "<td>" + this.LFSTDETIME + "</td>";
			html += "<td>" + this.LESTDNAME+ "</td>";
			html += "</tr>";
			html += "<tr><td colspan=\"5\" height=\"10px\">&nbsp;</td></tr>";		
		});																									
		html += "</table>";
		return html;
}

function createAduitTimeTable(data){
		var html = "<table><tr height=\"20\"><td width=\"100\">高峰时间段</td><td width=\"100\">平峰时间段</td>";
			html += "<td width=\"100\">低峰时间段</td>";	
		html += "</tr>";
		if(data && data.length>0){
			var orderno = -1;
			var hhtml="";	
			var nhtml="";
			var lhtml="";

			for(var i=0;i<data.length;i++){				
				if(orderno != data[i].orderno){

					orderno = data[i].orderno;
					if(i!=0){
						html +="<tr>"+ hhtml+ nhtml + lhtml ;
						html +="</tr>";
					}
					hhtml ="";
					nhtml="";
					lhtml="";
					
				} 
				var tgtype = data[i].tgtype;
				if(tgtype=='H'){
					hhtml ="<td>" + data[i].starttm +"-"+ data[i].endtm + "</td>";
				}else if(tgtype=='L'){
					lhtml ="<td>" + data[i].starttm +"-"+ data[i].endtm + "</td>";
				}else{
					nhtml ="<td>" + data[i].starttm +"-"+ data[i].endtm + "</td>";
				}
				if(hhtml==""){
					hhtml ="<td>&nbsp;</td>";					
				}
				if(nhtml==""){
					nhtml ="<td>&nbsp;</td>";
				}
				if(lhtml==""){
					lhtml ="<td>&nbsp;</td>";
				}
			}
			html +="<tr>"+ hhtml+ nhtml + lhtml;
			html +="</tr>";
		}				
		html += "</table>";
		return html;									
}

function createAduitIntervalTable(data,level){

		var html = "<table><tr height=\"20\"><td width=\"200\">线路方向</td>";
		html += "<td width=\"200\">考核时间</td>";
		html += "<td width=\"200\">休息时间</td></tr>";												
									
		$.each(data,function(){
			html += "<tr>";
			
			if(level=="H"){	
				var hbrtime = this.HBRTIME;
				if(hbrtime==null){
					hbrtime="";
				}
				var hinteruptime = this.HINTERUPTTIME;
				if(hinteruptime==null){
					hinteruptime ="";
				}
				html += "<td>" + this.LDIRECTION + "</td>";
				html += "<td>" + hbrtime+ "</td>";
				html += "<td>" + hinteruptime + "</td>";			
			} else if(level=="L"){
				var lbrtime = this.LBRTIME;
				if(lbrtime==null){
					lbrtime="";
				}
				var linteruptime = this.LINTERUPTTIME;
				if(linteruptime==null){
					linteruptime ="";
				}
				html += "<td>" + this.LDIRECTION + "</td>";
				html += "<td>" + lbrtime + "</td>";
				html += "<td>" + linteruptime + "</td>";

			} else {
				var nbrtime = this.NBRTIME;
				if(nbrtime==null){
					nbrtime="";
				}
				var ninteruptime = this.NINTERUPTTIME;
				if(ninteruptime==null){
					ninteruptime ="";
				}
			
				html += "<td>" + this.LDIRECTION + "</td>";
				html += "<td>" + nbrtime + "</td>";
				html += "<td>" + ninteruptime + "</td>";
			}
			html += "</tr>";
			html += "<tr><td colspan=\"3\" height=\"10px\">&nbsp;</td></tr>";		
		});																									
		html += "</table>";
		return html;										
}


function addAduitBusScheDetailPlan(data,num){
 	$table  = $("table[name='BusScheDetailPlanTable']",$("#timetableworkflow"));
 	$table.html("");
 	var data1 = data.data01;//早班
 	var data2 = data.data23;//中班
 	var data3 = data.data45;//两头班
 	var data4 = data.data6;//夜班
	var data5 = data.data7;//机动班

	if(data1 && data1.length>0){
		var html ="<tr><td>早班</td>";
		$.each(data1,function(){
			html += "<td>";
			html += createAduitBusScheDetailPlanTable(this,num);
			html += "</td>";
		})
		html += "</tr>";
		$table.append(html);
	}
	if(data2 && data2.length>0){
		var html ="<tr><td>中班</td>";
		$.each(data2,function(){
			html += "<td>";
			html += createAduitBusScheDetailPlanTable(this,num);
			html += "</td>";
		})
		html += "</tr>";
		$table.append(html);
	}
	if(data3 && data3.length>0){
		var html ="<tr><td>两头班</td>";
		$.each(data3,function(){
			html += "<td>";
			html += createAduitBusScheDetailPlanTable(this,num);
			html += "</td>";
		})
		html += "</tr>";
		$table.append(html);
	}
	if(data4 && data4.length>0){
		var html ="<tr><td>夜班</td>";
		$.each(data4,function(){
			html += "<td>";
			html += createAduitBusScheDetailPlanTable(this,num);
			html += "</td>";
		})
		html += "</tr>";
		$table.append(html);
	}
	if(data5 && data5.length>0){
		var html ="<tr><td>机动班</td>";
		$.each(data5,function(){
			html += "<td valign=\"top\">";
			html += createAduitBusScheDetailPlanTable(this,num);
			html += "</td>";
		});
		html += "</tr>";
		$table.append(html);
	}
}

function createAduitBusScheDetailPlanTable(data,num){

	var detail = data.detail;
	var planlist = data.plan;
	var isMain = detail.ISMAIN;
	var isRoute =  detail.ISROUTE;

	if(isRoute!=1){
		if(isMain==0){
			num += 1;
		}	
	}

	var html =	"<table  class=\"list\" style=\"width:400px;margin:10px 0 0 20px; float:left\" name=\"" + detail.BSDGUID + "\" >";
	  	html += "<tr><th colspan = \"4\" class=\"textcenter\">" + detail.SHIFTNAME + "</th></tr>";
		html += "<tr><td width=\"45px\">&nbsp;</td><td width=\"150px\" class=\"textcenter\">主站</td><td width=\"40px\" class=\"textcenter\">=&gt;</td><td width=\"150px\" class=\"textcenter\">副站</td></tr>";				
		html +=	"<tr><td>序号</td><td class=\"textcenter\">" + detail.FSNAME+ "</td>";
		html += "<td class=\"textcenter\">=&gt;</td> <td class=\"textcenter\">" + detail.LSNAME+ "</td></tr>";						
	var i = 1;

	var index = 0;
	for(;i<=num ;i++){			
		var starttm="";
		var starttm2 ="";
		var isShuttle =false;
		var fsname="";
		var lsname="";
		if(planlist &&index < planlist.length){
			starttm =  planlist[index].pdeparture;
			if(planlist[index].isshuttle==1){
				isShuttle = true;
				fsname = planlist[index].fsname;
				lsname = planlist[index].lsname;
			}
			index+=1;
		}
		if(planlist &&index < planlist.length){
			starttm2 =  planlist[index].pdeparture;
			if(planlist[index].isshuttle==1){
				isShuttle = true;
				fsname = planlist[index].fsname;
				lsname = planlist[index].lsname;
			}
			index+=1;
		}

		html += "<tr><td>"+i+"</td>";
		html += "<td class=\"textcenter\">";
		if(isShuttle){
			html += "<p>" + fsname + "</p>";
		}
		
		html += "<input type=\"text\" size=\"4\" maxlength=\"4\" value=\""+ starttm +"\"  size=\"4\" name=\"" + detail.BSDGUID + "_starttm" + i + "\"";
		

		html += " readonly=\"readonly\" ";

		html += " /></td><td class=\"textcenter\">=&gt;</td>";

		html += "<td class=\"textcenter\">";
		if(isShuttle){
			html += "<p>" + lsname + "</p>"
		}
		html += "<input type=\"text\" size=\"4\" maxlength=\"4\" value=\""+ starttm2 +"\" size=\"4\" name=\"" + detail.BSDGUID + "_starttm2" + i + "\"";				
		html += " readonly=\"readonly\" ";				
		html += " /></td></tr>";
	}

							
	html += "</table>";		
	return html;											
}


function generateAduitVerticalTable(bsguid){
	doGetAjax(CPATH.domain_3 +"/rs/VerticalTable/" + bsguid + "/apply",null,function(data){		
		var list = data.jsonData;
		if(list && list.length>0){
			$.each(list,function(){
				var html ="<table style=\"float:left;margin-left:20px\" class=\"list\">";
				$.each(this,function(i){
					if(i==0){
						html += "<tr><th class=\"textcenter\">序号</th><th class=\"textcenter\">班次</th><th class=\"textcenter\" colspan=\"2\">"+this.LDIRECTION+"</th><th>趟次间隔（分钟）</th></tr>";
					}
					html += "<tr>";
					html += "<td class=\"textcenter\">" + (i+1) + "</td>";
					html += "<td class=\"textcenter\">" + this.SHIFTNAME + "</td>";
					html += "<td class=\"textcenter\">" + this.PDEPARTURE + "</td>";
					html += "<td class=\"textcenter\">" + this.ARRIVALTM + "</td>";
					html += "<td class=\"textcenter\">" + this.INTERVALTIME + "</td>";
					html += "</tr>";				
				});
				html += "</table>";
				$("div[name='VerticalTable']",$("#timetableworkflow")).append(html);
				$("div[name='VerticalTable']",$("#timetableworkflow")).find("tr").pagnationStyle();
			});		
		}
	});
}

function createAduitShiftTimeTable(data){
	var data1 = data.data01;//早班
 	var data2 = data.data23;//中班
 	var data3 = data.data45;//两头班
 	var data4 = data.data6;//夜班
	var data5 = data.data7;//机动班
	var detail = data.detail;
	var html = "<thead><tr><th width=\"100\">班次</th><th width=\"100\">出场用时</th><th width=\"100\">进场用时</th>";
		html += "<th width=\"100\">交接班用时</th><th width=\"100\">班次用时</th>";
		html += "<th width=\"100\">超时</th></tr></thead>";					
							
	if(data1 && data1.length>0){
		$.each(data1,function(){
			html += createAduitShiftTimeTr(this);
		});
	}
	if(data2 && data2.length>0){
		$.each(data2,function(){
			html += createAduitShiftTimeTr(this);
		});
	}
	if(data3 && data3.length>0){
		$.each(data3,function(){
			html += createAduitShiftTimeTr(this);
		});
	}
	if(data4 && data4.length>0){
		$.each(data4,function(){
			html += createAduitShiftTimeTr(this);
		});
	}
	if(data5 && data5.length>0){
		$.each(data5,function(){
			html += createAduitShiftTimeTr(this);
		});
	}
	$("table[name='shiftTimeTable']",$("#timetableworkflow")).html("");
	$("table[name='shiftTimeTable']",$("#timetableworkflow")).append(html);
	$("table[name='shiftTimeTable']",$("#timetableworkflow")).find("tr").pagnationStyle();
}
function createTurnovertimeTable(bsguid,fsname,lsname){

	doGetAjax(CPATH.domain_3 +"/rs/lineBusScheduleTurnovertime/" + bsguid,null,function(data){
		var html = "<table style=\"float:left;margin-left:20px\" class=\"list\">";
			
		var result = data.jsonData;
		if(result){
			$.each(result,function(){
				var shiftname = this.shiftname;	
				html += "<tr><th colspan=\"5\" class=\"textcenter\">班次："+shiftname+"</th></tr>";
				html +="<tr><th>"+fsname+"</th><th>"+lsname+"</th><th>下行</th><th>上行</th><th>总时间</th></tr>";			
				var list = this.list;				
				if(list){
					$.each(list,function(){
						html += "<tr>";
						html += "<td>" + this.downPdeparture+ "</td>";
						html += "<td>" + this.upPdeparture+"</td>";
						html += "<td>" + this.downtime + "</td>";
						html += "<td>" + this.uptime + "</td>";
						html += "<td>" + this.totaltime+ "</td>";
						html += "</tr>";				
					});
				}							
			});
		}		
		html += "</table>";
		$("div[name='turnovertimeTable']",$("#timetableworkflow")).html(html);
		$("div[name='turnovertimeTable']",$("#timetableworkflow")).find("table").find("tr").pagnationStyle();
	});
}
function createAduitShiftTimeTr(data,isNew){
	var detail = data.detail;
	var html = "<tr>";
	html += "<td>" + detail.SHIFTNAME + "</td>";
	html += "<td>" + detail.OTIME + "小时</td>";
	html += "<td>" + detail.ITIME + "小时</td>";
	html += "<td>" + detail.JJTIME + "小时</td>";
	html += "<td>" + detail.SHIFTTIME + "小时</td>";
	html += "<td>" + detail.OUTTIME + "小时</td>";
	html += "</tr>";
	return html;
}

function createAduitShiftDistanceTable(data){
	var data1 = data.data01;//早班
 	var data2 = data.data23;//中班
 	var data3 = data.data45;//两头班
 	var data4 = data.data6;//夜班
	var data5 = data.data7;//机动班
	var detail = data.detail;
	var html = "<thead><tr><th width=\"100\">班次</th><th width=\"150\">起点站</th><th width=\"150\">终点站</th>";
		html += "<th width=\"200\">停车场</th><th width=\"150\">进场里程</th><th width=\"150\">出场里程</th></tr></thead>";
	if(data1 && data1.length>0){
		$.each(data1,function(){
			html += createAduitShiftDistanceTr(this);
		});
	}
	if(data2 && data2.length>0){
		$.each(data2,function(){
			html += createAduitShiftDistanceTr(this);
		});
	}
	if(data3 && data3.length>0){
		$.each(data3,function(){
			html += createAduitShiftDistanceTr(this);
		});
	}
	if(data4 && data4.length>0){
		$.each(data4,function(){
			html += createAduitShiftDistanceTr(this);
		});
	}
	if(data5 && data5.length>0){
		$.each(data5,function(){
			html += createAduitShiftDistanceTr(this);
		});
	}
	$("table[name='shiftDistanceTable']",$("#timetableworkflow")).html("");
	$("table[name='shiftDistanceTable']",$("#timetableworkflow")).append(html);
	$("table[name='shiftDistanceTable']",$("#timetableworkflow")).find("tr").pagnationStyle();
}

function createAduitShiftDistanceTr(data){
	var detail = data.detail;
	var parkname = detail.PARKNAME;
	var sstationName =  detail.SSTATIONNAME;
	var estationName =  detail.ESTATIONNAME;
	if(!parkname){
		parkname="";
	}
	if(!sstationName){
		sstationName ="";
	}
	if(!estationName){
		estationName ="";
	}
	var html = "<tr>";

	html += "<td>" + detail.SHIFTNAME + "</td>";
	html += "<td>" + sstationName + "</td>";
	html += "<td>" + estationName + "</td>";
	html += "<td>" + parkname + "</td>";
	html += "<td>" + detail.INDISTANCE + "千米</td>";
	html += "<td>" + detail.OUTDISTANCE + "千米</td>";
	html += "</tr>";
	return html;
}
function createAduitWorkandrestTableForTimeTable(detail){
	var html = "<thead><tr><th width=\"100\">班次</th><th width=\"150\">作息方式</th><th width=\"150\">选择作息组</th>";
		html += "<th width=\"150\">序号</th><th width=\"150\">作息时间</th></thead>";
	$.each(detail,function(){
		html += "<tr>";
		html += "<td>" + this.shiftname + "</td>";

		html += "<td>" + this.workandrestDesc + "</td>";
		html += "<td>" + this.wrgroupDesc + "</td>";
		html += "<td>" + this.wrno + "</td>";
		html += "<td>" + this.wrtime + "</td>";		
		html += "</tr>";
	});	
	$("table[name='workandrestTable']",$("#timetableworkflow")).html(html);
	$("table[name='workandrestTable']",$("#timetableworkflow")).find("tr").pagnationStyle();	

}
findName("applyDate").datepicker({});

function exportAduitTimeTable(){
	var bsguid ="<%=request.getParameter("bizKey")%>";
	ajaxDownload(CPATH.domain_3+"/rs/linebusschedule/"+bsguid+"/export");
}
</script>

<div class="pageFormContent" style="position: relative; display: inline;">	
	<div class="panelBar" style="margin-bottom: 2px;" >	
		<ul class="toolBar">
			<li><a class="icon" href="javascript:;" onclick="exportAduitTimeTable()"><span style="background: url('/syWeb/img/export.png') no-repeat center left;">导出</span></a></li>
		</ul>
	</div>
	<div class="pageFormContent" layoutH="97">	
		<form action="" name="aduitTimeTableForm">
			<p>
			<lable>平均班日圈:</label>
			<input type="text" class="validatePositiveNumber required" name="avgbcshift" >
			</p>
			<p>
			<lable>生效日期:</label>
			<input type="text" class="date required" name="applyDate" >
			</p>
		</form>
	</div>
	<div id="timetableworkflow" style="margin-top:20px"  style="display: inline;">			
		<div class="tabs" currentIndex="0" eventType="click" name="detailDiv"  style="display: inline;">
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li><a href="javascript:;"><span>基本信息</span></a></li>
						<li><a href="javascript:;"><span>时刻表</span></a></li>
						<li><a href="javascript:;"><span>竖表</span></a></li>
						<li><a href="javascript:;"><span>周转时间</span></a></li>
						<li><a href="javascript:;"><span>班次时间表</span></a></li>
						<li><a href="javascript:;"><span>班次进出场里程</span></a></li>
						<li><a href="javascript:;"><span>班次作息时间</span></a></li>
					</ul>
				</div>
			</div>
			<div class="tabsContent"  style="display: inline;">
				<div style="width:100%;height:700px;overflow-y:scroll"> 
					<!-- begin基本信息 --> 
					基本信息
					<hr/> 
						<div class="tabs" currentIndex="0" eventType="click" >
								<div class="tabsHeader">
									<div class="tabsHeaderContent">
										<ul>
											<li><a href="javascript:;"><span>线路信息</span></a></li> 
											<li><a href="javascript:;"><span>发车间隔设定</span></a></li>  
										</ul>
									</div>
								</div>
								<div class="tabsContent">
									<div style="height:100%">
										<table width="100%">
											<tr height="20">
												<td width="100" >线路番号:</td>
												<td width="100" class="textleft"><span name="spanLNAME">&nbsp;</span></td>
												<td width="100">时刻表类型:</td>
												<td class="textleft" width="100"><span name="spanBSCNAME">&nbsp;</span></td>
												<td width="100">编号:</td> 
												<td class="textleft" width="100"><span name="spanBSCODE">&nbsp;</span></td>
												<td width="100">版本:</td>
												<td class="textleft" width="100"><span name="spanVERNAME">&nbsp;</span></td>
											</tr>
											<tr height="20">
												<td>自编码:</td>
												<td class="textleft"><span name="spanCODE">&nbsp;</span></td>
												<td>最大班日圈:</td>
												<td class="textleft"><span name="spanBCSHIFT">&nbsp;</span></td>
												<td>运营趟次:</td>
												<td class="textleft"><span name="spanRUNSHIFTNUM">&nbsp;</span></td>
												<td>部门:</td>
												<td class="textleft"><span name="spanORGNAME">&nbsp;</span></td>
											</tr>
											<tr height="20">
												<td>执行时间:</td>
												<td class="textleft"><span name="spanSTARTDT">&nbsp;</span></td>
												<td>班日平均圈:</td>
												<td><span name="spanAVGBCSHIFT">&nbsp;</span></td>
												
												<!-- 
												<td>班日平均里程:</td>
												<td class="textleft"><span name="spanDISTANCE">&nbsp;</span></td>
												<td colspan="3"></td>
												
											</tr>
											<tr height="20">
											 -->
												<td >主站:</td>
												<td class="textleft"><span name="spanFSNAME">&nbsp;</span></td>
												<td>副站:</td>
												<td class="textleft"><span name="spanLSNAME">&nbsp;</span></td>
												<!-- <td colspan="5"></td> -->
											</tr>
											<tr height="20">
												<td>出车数:</td>
												<td class="textleft"><span name="spanBUSQTY">&nbsp;</span></td>
												<td>出班数:</td>
												<td class="textleft"><span name="spanSHIFTCOUNT">&nbsp;</span></td>
												<td>圈数:</td>
												<td class="textleft"><span name="spanPLANCOUNT">&nbsp;</span></td>
												<td colspan="2"></td>
											</tr>
											<tr height="20">
												<td>日运营里程:</td>
												<td class="textleft"><span name="spanBUSINESSDISTANCE">&nbsp;</span></td>
												<td>日非运营里程:</td>
												<td class="textleft"><span name="spanNOBUSINESSDISTANCE">&nbsp;</span></td>
												<td>日总里程:</td>
												<td class="textleft"><span name="spanTOTALDISTANCE">&nbsp;</span></td>
												<td>平均班日里程:</td>
												<td class="textleft"><span name="spanAVGDISTANCE">&nbsp;</span></td>
											</tr>
											<tr height="20">
												<td colspan="8" height="20"></td>  
											</tr>
											<tr height="20">
												<td >首末班时间</td> 
												<td colspan="7">
													<span name="spanLINELINEINFO" width="100%">&nbsp;</span>												
												</td>
											</tr>
											
											<tr height="20">
												<td colspan="8" height="20"></td>  
											</tr>
											<tr height="20">
												<td>时间段设定 </td> 
												<td colspan="7"> 
													<span name="spanLINETIMEQUANTUM">&nbsp;</span>
												</td>
											</tr>
											
											<tr height="20">
												<td colspan="8" height="20"></td>  
											</tr>
											<tr height="20">
												<td>发车数设定 </td> 
												<td colspan="7"> 
													<!-- 发车数设定 -->
													<table>
														<tr height="20">
															<td width="100">&nbsp;</td>
															<td width="100">首站/东南发车数</td>
															<td width="100">末站/西北发车数</td> 
															<td width="180">首末班次顺序(0首站1末站)<td>
														</tr>
														<tr height="20"> 
															<td>早班</td>
															<td><span name="spanLRUNBUSNUMS01">&nbsp;</span></td>
															<td><span name="spanLRUNBUSNUME01">&nbsp;</span></td> 
															<td><span name="spanORDER01">&nbsp;</span></td>
														</tr>
														<tr height="20">
															<td>中班</td>
															<td><span name="spanLRUNBUSNUMS23">&nbsp;</span></td>
															<td><span name="spanLRUNBUSNUME23">&nbsp;</span></td> 
															<td><span name="spanORDER23">&nbsp;</span></td>
														</tr> 
														<tr height="20">
															<td>两头班</td>
															<td><span name="spanLRUNBUSNUMS45">&nbsp;</span></td>
															<td><span name="spanLRUNBUSNUME45">&nbsp;</span></td> 
															<td><span name="spanORDER45">&nbsp;</span></td>
														</tr> 
														<tr height="20">
															<td>夜班</td>
															<td><span name="spanLRUNBUSNUMS6">&nbsp;</span></td>
															<td><span name="spanLRUNBUSNUME6">&nbsp;</span></td> 
															<td><span name="spanORDER6">&nbsp;</span></td>
														</tr> 
														<tr height="20">
															<td>机动班</td>
															<td><span name="spanLRUNBUSNUMS7">&nbsp;</span></td>
															<td><span name="spanLRUNBUSNUME7">&nbsp;</span></td> 
															<td><span name="spanORDER7">&nbsp;</span></td>
														</tr> 
													</table>
												</td>
											</tr>
										</table>
									</div> 
									<div> 
										<!-- 发车间隔设定 --> 
										<table border="1">
											<tr>
												<td colspan="4"  height="20" width="100%"> 时间间隔单位为：分 </td> 
											</tr>
											<!-- 高峰 -->
											<tr height="20">
												<td style="color:red;font-size:18;">高峰</td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
											</tr>
											<tr height="20">
												<td width="200">时间间隔</td>
												<td width="200">平均间隔：<span name="spanLINTERVALH"></span></td>
												<td width="200">最短间隔：<span name="spanLINTERVALHMIN"></span></td>
												<td width="200">最长间隔：<span name="spanLINTERVALHMAX"></span></td>
											</tr> 
											<tr height="20">
												<td>周转时间</td>
												<td colspan="3">
												<span name="spanHINTERVAL">&nbsp;</span>									
												</td>
											</tr>
											<!-- 平峰 -->
											<tr>
												<td colspan="4"  height="20" width="100%">  </td> 
											</tr>
											<tr height="20">
												<td style="color:red;font-size:18;">平峰 </td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
											</tr>
											<tr height="20">
												<td width="200">时间间隔</td>
												<td width="200">平均间隔：<span name="spanLINTERVALN"></span></td>
												<td width="200">最短间隔：<span name="spanLINTERVALNMIN"></span></td>
												<td width="200">最长间隔：<span name="spanLINTERVALNMAX"></span></td>
											</tr> 
											<tr height="20">
												<td>周转时间</td>
												<td colspan="3">
												<span name="spanNINTERVAL">&nbsp;</span>									
												</td>
											</tr>
											<!-- 低峰 -->
											<tr height="20">
												<td colspan="4" height="20" width="100%">  </td> 
											</tr>
											<tr height="20">
												<td style="color:red;font-size:18;">低峰 </td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
											</tr>
											<tr height="20">
												<td width="200">时间间隔</td>
												<td width="200">平均间隔：<span name="spanLINTERVALL"></span></td>
												<td width="200">最短间隔：<span name="spanLINTERVALLMIN"></span></td>
												<td width="200">最长间隔：<span name="spanLINTERVALLMAX"></span></td>
											</tr height="20"> 
											<tr height="20">
												<td>周转时间</td>
												<td colspan="3">
												<span name="spanLINTERVAL">&nbsp;</span>									
												</td>
											</tr>
										</table>										
									</div> 
								</div>
								<div class="tabsFooter">
									<div class="tabsFooterContent">
										
									</div>
								</div>						
						</div> 					
					<!-- end基本信息 -->
				</div>
				<div style="width:100%;height:700px;display: inline;"> 
					<!-- begin时刻表 -->  
					
						<div name="BusScheDetailPlanDiv"  style="display: inline;">							
							<table name="BusScheDetailPlanTable">
		
							</table>		
						</div>

					
					<!-- end时刻表-->
				</div>
				<div style="width:100%;overflow:scroll;height:700px">
					<!-- begin竖表 --> 
					<div name="VerticalTable">
						
					</div>	
					<!-- end竖表 -->
				</div>
				<div style="width:100%;overflow:scroll;height:700px">
					<!-- begin周转时间 --> 
					<div name="turnovertimeTable">
						
					</div>	
					<!-- end周转时间 -->
				</div>
				<div style="width:100%;overflow:scroll;height:430px">
				<!-- begin班次时间表 --> 
				</br>
				<table name="shiftTimeTable" class="list">

				</table>	
				<!-- end班次时间表 -->
				</div>
				<div style="width:100%;overflow:scroll;height:430px">
				<!-- begin班次时间表 --> 
				</br>
				<table name="shiftDistanceTable" class="list">

				</table>	
				<!-- end班次时间表 -->
				</div>
				<div style="width:100%;overflow:scroll;height:430px">
				<!-- begin班次作息表 --> 
				</br>
				<table name="workandrestTable" class="list">

				</table>
				<!-- end班次作息表 -->
			</div>
				
			</div>
			<div class="tabsFooter">
				<div class="tabsFooterContent">
										
				</div>
			</div>	
		</div>
	</div>	
</div>
