package com.founder.sipbus.fwk.groovy.groovytmpl.hrs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.founder.sipbus.fwk.groovy.annotation.Description;


/**
 * 薪酬计算公共函数
 * @author lu.zhen
 *
 */
public class EmployeeSalaryCalcFunction  extends KpiAndParamSupport{
 
	
	private Logger logger = LoggerFactory.getLogger(EmployeeSalaryCalcFunction.class);
	
	private void logPrint(String sp)
	{
		if (logger.isTraceEnabled()) {
			logger.trace(sp);
		}
		System.out.println(sp);
	}
	
	public final int LEGAL_WORKING_DAY=21;//取整的月法定工作天数
	
	
	
	@Description(desc="获取圈奖")
	protected BigDecimal 圈奖(String 员工工号){
		BigDecimal a = new BigDecimal(0);
		List 员工线路 = lnames(员工工号,"员工线路圈数");
		if(员工线路==null)
		{
			return a;
		}
		for(int i=0;i<员工线路.size();i++){
			String 员工线路圈数 =lkpi(员工工号,"员工线路圈数",员工线路.get(i).toString());
			a=a.add(toBigDecimal(员工线路圈数).multiply(toBigDecimal(lp(员工线路.get(i).toString(),"线路圈奖基数"))));
			logPrint("员工线路圈数:"+员工线路圈数+" 员工线路:"+员工线路.get(i)+" 线路圈奖基数:"+lp(员工线路.get(i).toString(),"线路圈奖基数"));
		}
		//员工线路.get(i).toString()
		return a;
	}
	
	
	@Description(desc="获取满圈(String 员工工号)")
	protected BigDecimal 满圈(String 员工工号){
		BigDecimal a = new BigDecimal(0);
		BigDecimal b = new BigDecimal(0);
		List 员工线路 = lnames(员工工号,"员工线路圈数");
		for(int i=0;i<员工线路.size();i++){
			String 员工加圈次数 =lkpi(员工工号,"员工加圈次数",员工线路.get(i).toString());
			String 员工故障停圈次数 =lkpi(员工工号,"员工故障停圈次数",员工线路.get(i).toString());
//			a+=toInteger(员工加圈次数);
//			b+=toInteger(员工故障停圈次数);
			a=a.add(toBigDecimal(员工加圈次数));
			b=b.add(toBigDecimal(员工故障停圈次数));
		}
		String 员工年休假天数= kpi(员工工号,"员工年休假天数");
		String 员工补足后出勤天数= kpi(员工工号,"员工补足后出勤天数");
//		if(toBigDecimal(员工年休假天数).add(toBigDecimal(员工出勤天数)).longValue()>25) {
//			return toBigDecimal(p("满圈奖基数"));
//		}
//		if(toInteger(员工年休假天数)+toInteger(员工出勤天数)==25&&a.compareTo(b)>=0){
//			return toBigDecimal(p("满圈奖基数"));
//		}
		if(toBigDecimal(员工补足后出勤天数).longValue()>25) {
			return toBigDecimal(p("满圈奖基数"));
		}
		if(toInteger(员工补足后出勤天数)==25&&a.compareTo(b)>=0){
			return toBigDecimal(p("满圈奖基数"));
		}
		return toBigDecimal("0");
	}
	
	@Description(desc="获取质量奖(String 员工工号)")
	protected BigDecimal 质量奖(String 员工工号){ 
		String 员工事故次数= kpi(员工工号,"员工事故次数");
		String 员工补足后出勤天数= kpi(员工工号,"员工补足后出勤天数");
		BigDecimal 质量奖系数 = toBigDecimal(p("质量奖系数"));
		BigDecimal 质量奖基数 = toBigDecimal(p("质量奖基数"));
		if(toInteger(员工事故次数)>0) {
			return new BigDecimal(0);
		}else{
			if(toInteger(员工补足后出勤天数)<this.LEGAL_WORKING_DAY){
				return new BigDecimal(0);
			}else{
				return 质量奖基数.add(质量奖系数.multiply((toBigDecimal(员工补足后出勤天数).subtract(new BigDecimal(25)))));
			}
		}
	}
	
	@Description(desc="无投诉(String 员工工号)")
	protected BigDecimal 无投诉(String 员工工号){ 
		String 员工所在线路投诉排名是否倒数第一 = kpi(员工工号, "员工所在线路投诉排名是否倒数第一");//如果 员工所在线路投诉排名是否倒数第一 ==1 说明是倒数第一
		String 员工补足后出勤天数= kpi(员工工号,"员工补足后出勤天数");
		String 员工投诉次数= kpi(员工工号,"员工投诉次数");
		BigDecimal 无投诉基数 = toBigDecimal(p("无投诉基数"));
		if(toBigDecimal(员工投诉次数).longValue()>0||toInteger(员工补足后出勤天数)<this.LEGAL_WORKING_DAY) {
			return new BigDecimal(0);
		}else{
			if(toBoolean(员工所在线路投诉排名是否倒数第一,"1")==true){
				return new BigDecimal(0);
			}else{
				return 无投诉基数;
			}
		}  
	}
	
	@Description(desc="无事故(String 员工工号)")
	protected BigDecimal 无事故 (String 员工工号){ 
		String 员工所在线路总事故次数 = kpi(员工工号,"员工所在线路总事故次数");
		String 员工补足后出勤天数= kpi(员工工号,"员工补足后出勤天数");
		String 员工事故次数= kpi(员工工号,"员工事故次数");
		BigDecimal 无事故基数 = toBigDecimal(p("无事故基数"));
		if(toInteger(员工事故次数)>0||toInteger(员工补足后出勤天数)<this.LEGAL_WORKING_DAY) {
			return new BigDecimal(0);
		}else{
			if(toInteger(员工所在线路总事故次数)>0){
				return new BigDecimal(0);
			}else{
				return 无事故基数;
			}
		}  
	}
	
	@Description(desc="节日加班费(String 员工工号)")
	protected BigDecimal 节日加班费(String 员工工号){ 
		BigDecimal 员工日基本工资 = toBigDecimal(p("员工日基本工资"));
		String 员工节日加班天数= kpi(员工工号,"员工节日加班天数");
		BigDecimal 节日加班工资系数 = toBigDecimal(p("节日加班工资系数"));
		return 员工日基本工资.multiply(toBigDecimal(员工节日加班天数)).multiply(节日加班工资系数);
	}
	
	@Description(desc="升工加班费(String 员工工号)")
	protected BigDecimal 升工加班费(String 员工工号){ 
		BigDecimal 员工日基本工资 = toBigDecimal(p("员工日基本工资"));
		String 员工出勤天数= kpi(员工工号,"员工出勤天数");
		if(toBigDecimal(员工出勤天数).compareTo(new BigDecimal(21))>0)
		{
			//BigDecimal 员工法定月工作天数 = toBigDecimal(p("员工法定月工作天数"));
			BigDecimal 员工法定月工作天数 =new BigDecimal(this.LEGAL_WORKING_DAY);
			BigDecimal 升工加班工资系数 = toBigDecimal(p("升工加班工资系数"));
			//return toBigDecimal(员工基本工资).multiply(toBigDecimal(员工加班天数)).multiply(员工加班系数);
			return (toBigDecimal(员工出勤天数).subtract(员工法定月工作天数)).multiply(员工日基本工资).multiply(升工加班工资系数);
		}
		else
		{
			return new BigDecimal(0);
		}
	}
	

	@Description(desc="年休假带薪报酬(String 员工工号)")
	protected BigDecimal 年休假带薪报酬(String 员工工号){ 
		String 员工年休假天数= kpi(员工工号,"员工年休假天数");
		String 员工本年度剩余年休假天数= kpi(员工工号,"员工本年度剩余年休假天数");
		String 员工上一年度剩余年休假天数= kpi(员工工号,"员工上一年度剩余年休假天数");
		BigDecimal 年度剩余带薪休假工资系数 = toBigDecimal(p("年度剩余带薪休假工资系数"));
		BigDecimal 员工日基本工资 = toBigDecimal(p("员工日基本工资"));
		int calcsalarymonth=toInteger(计算月份());
		if(calcsalarymonth==12){
			return toBigDecimal(员工年休假天数).multiply(员工日基本工资).add(toBigDecimal(员工本年度剩余年休假天数).multiply(员工日基本工资).multiply(年度剩余带薪休假工资系数));
		}
		if(calcsalarymonth>=1&&calcsalarymonth<=11){
			return toBigDecimal(员工年休假天数).multiply(员工日基本工资);
		}
		return new BigDecimal(0);
	}
	
	
	@Description(desc="两头班补贴(String 员工工号)")
	protected BigDecimal 两头班补贴(String 员工工号){ 
		String 员工两头班出勤天数= kpi(员工工号,"员工两头班出勤天数");
		BigDecimal 两头班补贴系数 = toBigDecimal(p("两头班补贴系数"));
		return toBigDecimal(员工两头班出勤天数).multiply(两头班补贴系数);
	}
	
	@Description(desc="超时费(String 员工工号)")
	protected BigDecimal 超时费(String 员工工号){ 
		String 员工超时小时数 = kpi(员工工号,"员工超时小时数");
		BigDecimal 员工日基本工资 = toBigDecimal(p("员工日基本工资"));
		BigDecimal 超时费系数 = toBigDecimal(p("超时费系数"));
		logPrint("员工工号:"+员工工号+" 员工超时小时数:"+员工超时小时数+"  员工小时基本工资:"+员工日基本工资.divide(new BigDecimal(8))+"  超时费系数:"+超时费系数+" 超时费:"+toBigDecimal(员工超时小时数).multiply(员工日基本工资.divide(new BigDecimal(8))).multiply(超时费系数));
		return toBigDecimal(员工超时小时数).multiply(员工日基本工资.divide(new BigDecimal(8))).multiply(超时费系数);
	}
	
	@Description(desc="高温补贴(String 员工工号)")
	protected BigDecimal 高温补贴(String 员工工号){ 
		int calcsalarymonth=toInteger(计算月份());
		if(calcsalarymonth==10){
		String 员工高温日出勤全年总天数 = kpi(员工工号,"员工高温日出勤全年总天数");
		BigDecimal 高温补贴系数 = toBigDecimal(p("高温补贴系数"));
		return toBigDecimal(员工高温日出勤全年总天数).multiply(高温补贴系数);
		}
		return new BigDecimal(0);
		}
	
	@Description(desc="信息员补贴(String 员工工号)")
	protected BigDecimal 信息员补贴(String 员工工号){ 
		String 员工是否信息员 = kpi(员工工号,"员工是否信息员");
		BigDecimal 信息员补贴基数 = toBigDecimal(p("信息员补贴基数"));
		if(toBoolean(员工是否信息员,"1")==true){
			return 信息员补贴基数;
		}
		return new BigDecimal(0);
	}
	
	@Description(desc="油耗节费(String 员工工号)")
	protected BigDecimal 油耗节费(String 员工工号){ 
		String 员工节费油公升数 = kpi(员工工号,"员工节费油公升数");
		BigDecimal 节费油补贴系数 = toBigDecimal(p("节费油补贴系数"));
		if((toBigDecimal(员工节费油公升数).multiply(节费油补贴系数)).compareTo(new BigDecimal(150))==1){
			return new BigDecimal(150);
		}else{
			if((toBigDecimal(员工节费油公升数).multiply(节费油补贴系数)).compareTo(new BigDecimal(-100))== -1){
				return new BigDecimal(-100);
			}else{
				return toBigDecimal(员工节费油公升数).multiply(节费油补贴系数);
			}
		}
	}
	
	@Description(desc="营收奖(String 员工工号)")
	protected BigDecimal 营收奖(String 员工工号){ 
		BigDecimal 员工超欠营收数 = new BigDecimal(0);
		List 员工线路 = lnames(员工工号,"员工线路圈数");
		if(员工线路==null)
		{
			return new BigDecimal(0);
		}
		for(int i=0;i<员工线路.size();i++){
			String 员工线路圈数 =lkpi(员工工号,"员工线路圈数",员工线路.get(i).toString());
			String 员工线路圈平均营收 = kpi(员工工号,"员工线路圈平均营收");
			String 线路月圈平均营收 = lkpi(员工工号,"员工线路圈数",员工线路.get(i).toString());
			员工超欠营收数 = 员工超欠营收数.add(toBigDecimal(员工线路圈数).multiply(toBigDecimal(员工线路圈平均营收).subtract(toBigDecimal(线路月圈平均营收))));
			logPrint("员工线路:"+员工线路.get(i).toString()+" 员工线路圈平均营收:"+员工线路圈平均营收+" 线路月圈平均营收:"+线路月圈平均营收+" 员工超欠营收数:"+员工超欠营收数);
		}
		BigDecimal 超欠营收补贴系数 = toBigDecimal(p("超欠营收补贴系数"));
		if((员工超欠营收数.multiply(超欠营收补贴系数)).compareTo(new BigDecimal(150))==1){
			return new BigDecimal(150);
		}else{
			if((员工超欠营收数.multiply(超欠营收补贴系数)).compareTo(new BigDecimal(-100))== -1){
				return new BigDecimal(-100);
			}else{
				return 员工超欠营收数.multiply(超欠营收补贴系数);
			}
		}
	}
	
	@Description(desc="月票奖(String 员工工号)")
	protected BigDecimal 月票奖(String 员工工号){ 
		String 学生卡次数 = kpi(员工工号,"学生卡次数");
		String 老人卡次数 = kpi(员工工号,"老人卡次数");
		BigDecimal 月票奖补贴系数 = toBigDecimal(p("月票奖补贴系数"));
		return (toBigDecimal(学生卡次数).add(toBigDecimal(老人卡次数))).divide(new BigDecimal(100)).multiply(月票奖补贴系数);
	}
	
	@Description(desc="行车里程补贴(String 员工工号)")
	protected BigDecimal 行车里程补贴(String 员工工号){ 
		String 员工行驶里程 = kpi(员工工号,"员工行驶里程");
		BigDecimal 行车里程补贴系数 = toBigDecimal(p("行车里程补贴系数"));
		return toBigDecimal(员工行驶里程).divide(new BigDecimal(100)).multiply(行车里程补贴系数);
	}
	
	@Description(desc="带徒(String 员工工号)")
	protected BigDecimal 带徒(String 员工工号){ 
		//员工带徒A3天数  员工带徒A1A2天数  先直接算两个KPI的值  加起来  如果超过200就以200计算 
		BigDecimal 员工带徒补贴=new BigDecimal(0);
		String 员工带徒A3天数 = kpi(员工工号,"员工带徒A3天数");
		String 员工带徒A1A2天数 = kpi(员工工号,"员工带徒A1A2天数");
		logPrint("员工带徒A1A2天数--->"+员工带徒A1A2天数+"  员工带徒A3天数--->"+员工带徒A3天数);
		BigDecimal 员工带徒A1A2系数 = toBigDecimal(p("员工带徒A1A2系数"));
		BigDecimal 员工带徒A3系数 = toBigDecimal(p("员工带徒A3系数"));
		if(this.toInteger(员工带徒A3天数)>=25)//如果A3大于25天  则直接
		{
			员工带徒补贴=员工带徒A3系数;	
			return 员工带徒补贴;
		}
		else if(this.toInteger(员工带徒A3天数)>0)
		{
			员工带徒补贴=this.toBigDecimal(员工带徒A3天数).divide(new BigDecimal(25), 2, BigDecimal.ROUND_HALF_UP).multiply(员工带徒A3系数);
		}
		if(this.toInteger(员工带徒A1A2天数)>0)
		{
			员工带徒补贴=员工带徒补贴.add(this.toBigDecimal(员工带徒A1A2天数).multiply(员工带徒A1A2系数));
		}
		if(员工带徒补贴.compareTo(员工带徒A3系数)>0)
		{
			员工带徒补贴=员工带徒A3系数;
			return 员工带徒补贴;
		}
		return 员工带徒补贴;
	}
	
	@Description(desc="表扬(String 员工工号)")
	protected BigDecimal 表扬(String 员工工号){ 
		String 员工表扬次数 = kpi(员工工号,"员工表扬次数");
		BigDecimal 表扬补贴系数 = toBigDecimal(p("表扬补贴系数"));
		logPrint("员工表扬次数:"+员工表扬次数+" 表扬补贴系数:"+表扬补贴系数);
		return toBigDecimal(员工表扬次数).multiply(表扬补贴系数);
	}
	
	@Description(desc="加圈奖(String 员工工号)")
	protected BigDecimal 加圈奖(String 员工工号){ 
		List 员工加圈线路 = lnames(员工工号,"员工加圈次数");
		BigDecimal a = new BigDecimal(0);
		if(员工加圈线路==null)
		{
			return a;
		}
		for(int i=0;i<员工加圈线路.size();i++){
			String 员工加圈次数 =lkpi(员工工号,"员工加圈次数",员工加圈线路.get(i).toString());
			logPrint("员工加圈次数:"+员工加圈次数+" 员工加圈线路:"+员工加圈线路.get(i)+" 线路加圈系数"+lp(员工加圈线路.get(i).toString(),"加圈奖系数"));
			a=a.add(toBigDecimal(员工加圈次数).multiply(toBigDecimal(lp(员工加圈线路.get(i).toString(),"加圈奖系数"))));
		}
		return a;
	}
	
	@Description(desc="送保养车(String 员工工号)")
	protected BigDecimal 送保养车(String 员工工号){ 
		String 员工送保次数 = kpi(员工工号,"员工送保次数");
		BigDecimal 送保补贴系数 = toBigDecimal(p("送保补贴系数"));
		logPrint("员工送保次数:"+员工送保次数+" 送保补贴系数:"+送保补贴系数);
		return toBigDecimal(员工送保次数).multiply(送保补贴系数);
	}
	
	@Description(desc="病假(String 员工工号)")
	protected BigDecimal 病假(String 员工工号){ 
		String 员工病假圈数 = kpi(员工工号,"员工病假圈数");
		BigDecimal 病假扣款系数 = toBigDecimal(p("病假扣款系数"));
		return toBigDecimal(员工病假圈数).multiply(病假扣款系数);
	}
	
	@Description(desc="脱事脱休(String 员工工号)")
	protected BigDecimal 脱事脱休(String 员工工号){ 
		String 员工脱事脱休天数 = kpi(员工工号,"员工脱事脱休天数");
		BigDecimal 脱事脱休扣款系数 = toBigDecimal(p("脱事脱休扣款系数"));
		return toBigDecimal(员工脱事脱休天数).multiply(脱事脱休扣款系数);
	}
	
	
	@Description(desc="准点(String 员工工号)")
	protected BigDecimal 准点(String 员工工号){ 
		return new BigDecimal(20);
	}
	
	@Description(desc="机务(String 员工工号)")
	protected BigDecimal 机务(String 员工工号){ 
		String 员工故障停圈次数 = kpi(员工工号,"员工故障停圈次数");
		BigDecimal 故障停圈扣款系数 = toBigDecimal(p("故障停圈扣款系数"));
		return toBigDecimal(员工故障停圈次数).multiply(故障停圈扣款系数);
	}
	
	@Description(desc="自停(String 员工工号)")
	protected BigDecimal 自停(String 员工工号){ 
		String 员工自停次数 = kpi(员工工号,"员工自停次数");
		BigDecimal 自停扣款系数 = toBigDecimal(p("自停扣款系数"));
		return toBigDecimal(员工自停次数).multiply(自停扣款系数);
	}
	
	@Description(desc="上班迟到(String 员工工号)")
	protected BigDecimal 上班迟到(String 员工工号){ 
		return new BigDecimal(20);
	}
	
	@Description(desc="非空调车补贴(String 员工工号)")
	protected BigDecimal 非空调车补贴(String 员工工号){ 
		List<String> NO_CONDITION_SUBSIDY=new ArrayList<String>();//非空调车补贴线路
		NO_CONDITION_SUBSIDY.add("148");
		NO_CONDITION_SUBSIDY.add("18");
		BigDecimal 非空调车补贴基数= toBigDecimal(p("非空调车补贴基数"));
		BigDecimal 员工非空调线路圈数=new BigDecimal(0);
		BigDecimal subsidy=new BigDecimal(0);
		List 员工线路 = lnames(员工工号,"员工线路圈数");
		if(员工线路==null)
		{
			return subsidy;
		}		
		for(int i=0;i<员工线路.size();i++){
			String 员工线路圈数 =lkpi(员工工号,"员工线路圈数",员工线路.get(i).toString());
			if(NO_CONDITION_SUBSIDY.indexOf(员工线路.get(i).toString())>=0)
			{
				员工非空调线路圈数=员工非空调线路圈数.add(this.toBigDecimal(员工线路圈数));
			}
			logPrint("员工非空调线路圈数:"+员工非空调线路圈数+" 员工线路:"+员工线路.get(i)+" 非空调车补贴基数:"+非空调车补贴基数);
		}
		subsidy=员工非空调线路圈数.multiply(非空调车补贴基数);
//		List 员工线路 = lnames(员工工号,"员工线路圈数");
//		if(员工线路==null)
//		{
//			return a;
//		}
//		for(int i=0;i<员工线路.size();i++){
//			String 员工线路圈数 =lkpi(员工工号,"员工线路圈数",员工线路.get(i).toString());
//			a=a.add(toBigDecimal(员工线路圈数).multiply(toBigDecimal(lp(员工线路.get(i).toString(),"线路圈奖基数"))));
//			logPrint("员工线路圈数:"+员工线路圈数+" 员工线路:"+员工线路.get(i)+" 线路圈奖基数:"+lp(员工线路.get(i).toString(),"线路圈奖基数"));
//		}
		return subsidy;
	}
	
	@Description(desc="出勤天数(String 员工工号)")
	protected BigDecimal 出勤天数(String 员工工号){ 
		String 员工出勤天数= kpi(员工工号,"员工出勤天数");
		return toBigDecimal(员工出勤天数);
	}
	
	//岗位补贴
	@Description(desc="岗位补贴(String 员工工号)")
	protected BigDecimal 岗位补贴(String 员工工号){ 
		String 员工出勤天数= kpi(员工工号,"员工出勤天数");
		BigDecimal 岗位补贴基数= toBigDecimal(p("岗位补贴基数"));
		if(this.toInteger(员工出勤天数)>=this.LEGAL_WORKING_DAY){
			return 岗位补贴基数;
		}
		else {
			BigDecimal bdLwd = new BigDecimal(this.LEGAL_WORKING_DAY);
			BigDecimal bdEmployeeWorkDay = this.toBigDecimal(员工出勤天数);
			BigDecimal tempResult = 岗位补贴基数.multiply(bdEmployeeWorkDay);
			BigDecimal result = tempResult.divide(bdLwd,2, BigDecimal.ROUND_HALF_UP);//进行四舍五入,保留2位
			return result;
		}
		//return toBigDecimal(员工出勤天数);
	}

	//卫保贴
	@Description(desc="卫保贴(String 员工工号)")
	protected BigDecimal 卫保贴(String 员工工号){ 
		String 员工卫保贴参数= kpi(员工工号,"员工卫保贴参数");
		BigDecimal 卫保贴基数= toBigDecimal(p("卫保贴基数"));
		if(this.toInteger(员工卫保贴参数)==1){
			return 卫保贴基数;
		}
		else {
			return new BigDecimal(0);
		}
	}

	//加区间
	@Description(desc="加区间(String 员工工号)")
	protected BigDecimal 加区间(String 员工工号){ 
		List 员工加区间线路 = lnames(员工工号,"员工加区间次数");
		BigDecimal a = new BigDecimal(0);
		if(员工加区间线路==null)
		{
			return a;
		}
		for(int i=0;i<员工加区间线路.size();i++){
			String 员工加区间次数 =lkpi(员工工号,"员工加区间次数",员工加区间线路.get(i).toString());
			logPrint("员工加区间次数:"+员工加区间次数+" 员工加区间线路:"+员工加区间线路.get(i)+" 线路加区间系数"+lp(员工加区间线路.get(i).toString(),"加区间系数"));
			a=a.add(toBigDecimal(员工加区间次数).multiply(toBigDecimal(lp(员工加区间线路.get(i).toString(),"加区间系数"))));
		}
		return a;
	}
	
	//星级驾驶员嘉奖
	@Description(desc="星级驾驶员嘉奖(String 员工工号)")
	protected BigDecimal 星级驾驶员嘉奖(String 员工工号){ 
		String 星级驾驶员嘉奖数= kpi(员工工号,"星级驾驶员嘉奖");
		return this.toBigDecimal(星级驾驶员嘉奖数);
	}
	
	//加区间 员工补足后出勤天数
	@Description(desc="文明线路奖(String 员工工号)")
	protected BigDecimal 文明线路奖(String 员工工号){ 
		String 文明线路奖数= kpi(员工工号,"文明线路奖");
		return this.toBigDecimal(文明线路奖数);
	}
	
	//安全奖   员工补足后出勤天数  满21天才给
	@Description(desc="安全奖(String 员工工号)")
	protected BigDecimal 安全奖(String 员工工号){ 
//		String 文明线路奖数= kpi(员工工号,"文明线路奖");
		return this.toBigDecimal("0");
	}
	
	//违章扣款
	@Description(desc="违章(String 员工工号)")
	protected BigDecimal 违章(String 员工工号){ 
//		String 文明线路奖数= kpi(员工工号,"文明线路奖");
		return this.toBigDecimal("0");
	}

	//技能补贴
	@Description(desc="技能补贴(String 员工工号)")
	protected BigDecimal 技能补贴(String 员工工号){ 
//		String 文明线路奖数= kpi(员工工号,"文明线路奖");
		return this.toBigDecimal("0");
	}
	
	
}
