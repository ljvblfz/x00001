package com.founder.sipbus.fwk.groovy.groovytmpl.hrs;

/**
 * 公共变量
 * @author lu.zhen
 *
 */
public class EmployeeSalaryCalcVariable {
	//2013-02
	protected String 计算年月;

	public String get计算年月() {
		return 计算年月;
	}

	public void set计算年月(String 计算年月) {
		this.计算年月 = 计算年月;
	}
	
	public String 计算月份(){
		return 计算年月.substring(5, 7);
	}
	

 
	 
	
	
	 
	
	
	
}
