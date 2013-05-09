package com.founder.sipbus.fwk.groovy.groovytmpl.hrs;

import java.math.BigDecimal;

import com.founder.sipbus.fwk.groovy.annotation.Description;


/**
 * 薪酬计算公共函数
 * @author lu.zhen
 *
 */
public class DriverEmpSalaryCalcFunction  extends KpiAndParamSupport{

	
//	@Description(desc="获取圈奖")
//	protected String 圈奖(){
//		System.out.println("圈奖");
//		return "20";
//	}
//	
//
//	@Description(desc="获取质量奖(String 线路名称)")
//	protected String 质量奖(String 线路名称){
//		System.out.println("获取质量奖");
//		return "20";
//	}
//	
//	
//	@Description(desc="获取节日加班费(String 线路名称,String 员工工号)")
//	protected String 节日加班费(String 线路名称,String 员工工号){
//		System.out.println("节日加班费");
//		return "20";
//	} 
	@Description(desc="获取圈奖")
	protected BigDecimal 圈奖(String 员工工号){  
		return BigDecimal.valueOf(20);
	}
	
	
	@Description(desc="获取满圈(String 员工工号)")
	protected BigDecimal 满圈(String 员工工号){ 
		String 员工年休假天数= kpi(员工工号,"员工年休假天数");
		String 员工出勤天数= kpi(员工工号,"员工出勤天数");
		if(toBigDecimal(员工年休假天数).add(toBigDecimal(员工出勤天数)).longValue()>25) {
			return toBigDecimal(p("满圈奖基数"));
		}  
		return toBigDecimal("0");
	}
	
	@Description(desc="获取质量奖(String 员工工号)")
	protected BigDecimal 质量奖(String 员工工号){ 
		String 员工事故次数= kpi(员工工号,"员工事故次数");
		String 员工出勤天数= kpi(员工工号,"员工出勤天数");
		BigDecimal 质量奖系数 = toBigDecimal(p("质量奖系数"));
		BigDecimal 质量奖基数 = toBigDecimal(p("质量奖基数"));
		if(toInteger(员工事故次数)>0) {
			return new BigDecimal(0);
		}else{
			if(toInteger(员工出勤天数)<21){
				return new BigDecimal(0);
			}else{
				return 质量奖基数.add(质量奖系数.multiply((toBigDecimal(员工出勤天数).subtract(new BigDecimal(25)))));
			}
		}
	}
	
	@Description(desc="无投诉(String 员工工号)")
	protected BigDecimal 无投诉(String 员工工号){ 
		boolean 员工所在线路投诉排名是否倒数第一 = kpi3(员工工号, "员工所在线路投诉排名是否倒数第一");
		String 员工投诉次数= kpi(员工工号,"员工投诉次数");
		BigDecimal 无投诉基数 = toBigDecimal(p("无投诉基数"));
		if(toBigDecimal(员工投诉次数).longValue()>0) {
			return new BigDecimal(0);
		}else{
			if(员工所在线路投诉排名是否倒数第一==true){
				return new BigDecimal(0);
			}else{
				return 无投诉基数;
			}
		}  
	}
	
	@Description(desc="无事故(String 员工工号)")
	protected BigDecimal 无事故 (String 员工工号){ 
		boolean 员工所在线路事故排名是否倒数第一 = kpi3(员工工号, "员工所在线路事故排名是否倒数第一");
		String 员工事故次数= kpi(员工工号,"员工事故次数");
		BigDecimal 无事故基数 = toBigDecimal(p("无事故基数"));
		if(toBigDecimal(员工事故次数).longValue()>0) {
			return new BigDecimal(0);
		}else{
			if(员工所在线路事故排名是否倒数第一==true){
				return new BigDecimal(0);
			}else{
				return 无事故基数;
			}
		}  
	}
	
	@Description(desc="节日加班费(String 员工工号)")
	protected BigDecimal 节日加班费(String 员工工号){ 
		String 员工基本工资= kpi(员工工号,"员工基本工资");
		String 员工加班天数= kpi(员工工号,"员工加班天数");
		BigDecimal 员工加班系数 = toBigDecimal(p("员工加班系数"));
		return toBigDecimal(员工基本工资).multiply(toBigDecimal(员工加班天数)).multiply(员工加班系数);
	}
	
	@Description(desc="升工加班费(String 员工工号)")
	protected BigDecimal 升工加班费(String 员工工号){ 
		String 员工基本工资= kpi(员工工号,"员工基本工资");
		String 员工加班天数= kpi(员工工号,"员工加班天数");
		BigDecimal 员工加班系数 = toBigDecimal(p("员工加班系数"));
		return toBigDecimal(员工基本工资).multiply(toBigDecimal(员工加班天数)).multiply(员工加班系数);
	}
	

	//?????calcsalarymonth??????
	@Description(desc="年休假带薪报酬(String 员工工号)")
	protected BigDecimal 年休假带薪报酬(String 员工工号){ 
		String 员工年休假天数= kpi(员工工号,"员工年休假天数");
		String 员工本年度剩余年休假天数= kpi(员工工号,"员工本年度剩余年休假天数");
		String 员工上一年度剩余年休假天数= kpi(员工工号,"员工上一年度剩余年休假天数");
		BigDecimal 年度剩余带薪休假工资系数 = toBigDecimal(p("年度剩余带薪休假工资系数"));
		BigDecimal 员工日基本工资 = toBigDecimal(p("员工日基本工资"));
		int calcsalarymonth=0;
		if(calcsalarymonth==12){
			return toBigDecimal(员工年休假天数).multiply(员工日基本工资).add(toBigDecimal(员工本年度剩余年休假天数).multiply(员工日基本工资).multiply(年度剩余带薪休假工资系数));
		}
		if(calcsalarymonth==11){
			return toBigDecimal(员工年休假天数).multiply(员工日基本工资);
		}
		return new BigDecimal(0);
	}
	
	
	@Description(desc="两头班补贴(String 员工工号)")
	protected BigDecimal 两头班补贴(String 员工工号){ 
		String 员工基本工资= kpi(员工工号,"员工基本工资");
		String 员工加班天数= kpi(员工工号,"员工加班天数");
		BigDecimal 员工加班系数 = toBigDecimal(p("员工加班系数"));
		return toBigDecimal(员工基本工资).multiply(toBigDecimal(员工加班天数)).multiply(员工加班系数);
	}
	
	@Description(desc="超时费(String 员工工号)")
	protected BigDecimal 超时费(String 员工工号){ 
		String 员工超时小时数 = kpi(员工工号,"员工超时小时数 ");
		BigDecimal 员工日基本工资 = toBigDecimal(p("员工日基本工资"));
		BigDecimal 超时费系数 = toBigDecimal(p("超时费系数"));
		return toBigDecimal(员工超时小时数).multiply(员工日基本工资.divide(new BigDecimal(8))).multiply(超时费系数);
	}
	
	@Description(desc="高温补贴(String 员工工号)")
	protected BigDecimal 高温补贴(String 员工工号){ 
		String 员工高温日出勤全年总天数 = kpi(员工工号,"员工高温日出勤全年总天数 ");
		BigDecimal 高温补贴基数 = toBigDecimal(p("高温补贴基数"));
		return toBigDecimal(员工高温日出勤全年总天数).multiply(高温补贴基数);
	}
	
	@Description(desc="信息员补贴(String 员工工号)")
	protected BigDecimal 信息员补贴(String 员工工号){ 
		boolean 员工是否信息员 = kpi3(员工工号,"员工是否信息员");
		BigDecimal 信息员补贴基数 = toBigDecimal(p("信息员补贴基数"));
		if(员工是否信息员==true){
			return 信息员补贴基数;
		}
		return new BigDecimal(0);
	}
	
	@Description(desc="油耗节费(String 员工工号)")
	protected BigDecimal 油耗节费(String 员工工号){ 
		String 员工节费油公升数 = kpi(员工工号,"员工节费油公升数 ");
		BigDecimal 节费油补贴基数 = toBigDecimal(p("节费油补贴基数"));
		if((toBigDecimal(员工节费油公升数).multiply(节费油补贴基数)).compareTo(new BigDecimal(150))==1){
			return new BigDecimal(150);
		}else{
			if((toBigDecimal(员工节费油公升数).multiply(节费油补贴基数)).compareTo(new BigDecimal(-100))== -1){
				return new BigDecimal(-100);
			}else{
				return toBigDecimal(员工节费油公升数).multiply(节费油补贴基数);
			}
		}
	}
	
	@Description(desc="营收奖(String 员工工号)")
	protected BigDecimal 营收奖(String 员工工号){ 
		String 员工超欠营收数 = kpi(员工工号,"员工超欠营收数 ");
		BigDecimal 超欠营收补贴系数 = toBigDecimal(p("超欠营收补贴系数"));
		if((toBigDecimal(员工超欠营收数).multiply(超欠营收补贴系数)).compareTo(new BigDecimal(150))==1){
			return new BigDecimal(150);
		}else{
			if((toBigDecimal(员工超欠营收数).multiply(超欠营收补贴系数)).compareTo(new BigDecimal(-100))== -1){
				return new BigDecimal(-100);
			}else{
				return toBigDecimal(员工超欠营收数).multiply(超欠营收补贴系数);
			}
		}
	}
	
	@Description(desc="月票奖(String 员工工号)")
	protected BigDecimal 月票奖(String 员工工号){ 
		String 员工学生卡次数 = kpi(员工工号,"员工学生卡次数 ");
		String 员工老人卡次数 = kpi(员工工号,"员工老人卡次数 ");
		BigDecimal 月票奖补贴基数 = toBigDecimal(p("月票奖补贴基数"));
		return (toBigDecimal(员工学生卡次数).add(toBigDecimal(员工老人卡次数))).divide(new BigDecimal(100)).multiply(月票奖补贴基数);
	}
	
	@Description(desc="行车里程补贴(String 员工工号)")
	protected BigDecimal 行车里程补贴(String 员工工号){ 
		String 员工行驶里程 = kpi(员工工号,"员工行驶里程 ");
		BigDecimal 行车里程补贴基数 = toBigDecimal(p("行车里程补贴基数"));
		return toBigDecimal(员工行驶里程).divide(new BigDecimal(100)).multiply(行车里程补贴基数);
	}
	
	@Description(desc="带徒(String 员工工号)")
	protected BigDecimal 带徒(String 员工工号){ 
		String 员工带徒天数 = kpi(员工工号,"员工带徒天数 ");
		BigDecimal 带徒补贴基数 = toBigDecimal(p("带徒补贴基数"));
		return toBigDecimal(员工带徒天数).multiply(带徒补贴基数);
	}
	
	@Description(desc="表扬(String 员工工号)")
	protected BigDecimal 表扬(String 员工工号){ 
		String 员工表扬次数 = kpi(员工工号,"员工表扬次数 ");
		BigDecimal 表扬补贴基数 = toBigDecimal(p("表扬补贴基数"));
		return toBigDecimal(员工表扬次数).multiply(表扬补贴基数);
	}
	
	@Description(desc="加圈奖(String 员工工号)")
	protected BigDecimal 加圈奖(String 员工工号){ 
		String 员工加圈次数 = kpi(员工工号,"员工加圈次数 ");
		BigDecimal 加圈奖基数 = toBigDecimal(p("加圈奖基数"));
		return toBigDecimal(员工加圈次数).multiply(加圈奖基数);
	}
	
	@Description(desc="送保养车(String 员工工号)")
	protected BigDecimal 送保养车(String 员工工号){ 
		String 员工送保次数 = kpi(员工工号,"员工送保次数 ");
		BigDecimal 加圈奖基数 = toBigDecimal(p("加圈奖基数"));
		return toBigDecimal(员工送保次数).multiply(加圈奖基数);
	}
	
	@Description(desc="病假(String 员工工号)")
	protected BigDecimal 病假(String 员工工号){ 
		String 员工病假圈数 = kpi(员工工号,"员工病假圈数 ");
		BigDecimal 病假扣款基数 = toBigDecimal(p("病假扣款基数"));
		return toBigDecimal(员工病假圈数).multiply(病假扣款基数);
	}
	
	@Description(desc="脱事脱休(String 员工工号)")
	protected BigDecimal 脱事脱休(String 员工工号){ 
		String 员工脱事脱休天数 = kpi(员工工号,"员工脱事脱休天数  ");
		BigDecimal 脱事脱休扣款基数 = toBigDecimal(p("脱事脱休扣款基数"));
		return toBigDecimal(员工脱事脱休天数).multiply(脱事脱休扣款基数);
	}
	
	
	@Description(desc="准点(String 员工工号)")
	protected BigDecimal 准点(String 员工工号){ 
		String 员工早退次数 = kpi(员工工号,"员工早退次数  ");
		String 员工晚到次数 = kpi(员工工号,"员工晚到次数  ");
		BigDecimal 早退扣款基数 = toBigDecimal(p("早退扣款基数"));
		BigDecimal 晚到扣款基数 = toBigDecimal(p("晚到扣款基数"));
		return (toBigDecimal(员工早退次数).multiply(早退扣款基数)).add(toBigDecimal(员工晚到次数).multiply(晚到扣款基数));
	}
	
	@Description(desc="机务(String 员工工号)")
	protected BigDecimal 机务(String 员工工号){ 
		String 员工脱事脱休天数 = kpi(员工工号,"员工脱事脱休天数  ");
		BigDecimal 脱事脱休扣款基数 = toBigDecimal(p("脱事脱休扣款基数"));
		return toBigDecimal(员工脱事脱休天数).multiply(脱事脱休扣款基数);
	}
	
	@Description(desc="自停(String 员工工号)")
	protected BigDecimal 自停(String 员工工号){ 
		String 员工自停次数 = kpi(员工工号,"员工自停次数  ");
		BigDecimal 自停扣款基数 = toBigDecimal(p("自停扣款基数"));
		return toBigDecimal(员工自停次数).multiply(自停扣款基数);
	}
	
	@Description(desc="上班迟到(String 员工工号)")
	protected BigDecimal 上班迟到(String 员工工号){ 
		String 员工自停次数 = kpi(员工工号,"员工自停次数  ");
		BigDecimal 自停扣款基数 = toBigDecimal(p("自停扣款基数"));
		return toBigDecimal(员工自停次数).multiply(自停扣款基数);
	}
	
	@Description(desc="非空调补贴(String 员工工号)")
	protected BigDecimal 非空调补贴(String 员工工号){ 
		String []a = kpi2(员工工号, "员工线路天数");
		BigDecimal 圈奖系数 = toBigDecimal(p("圈奖系数"));
		BigDecimal sum = new BigDecimal(0);
		for(int i=0;i<a.length;i++){
			BigDecimal b = toBigDecimal(a[i]).multiply(圈奖系数);
			sum = sum.add(b);
		}
		return sum;
	}
	
	
	
	
	
}
