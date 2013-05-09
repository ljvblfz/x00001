package ${packageName};


import com.founder.sipbus.fwk.groovy.execution.*;

import com.founder.sipbus.fwk.groovy.groovytmpl.hrs.*;

${managedImports}


public class ${className}  extends EmployeeSalaryCalcFunction implements IScriptExecution{ 
 
	
	//业务函数 
	public void execute(ScriptExecutionContext scriptExecutionContext){
		 String 员工工号=scriptExecutionContext.getEmployeeJobNumber();
		 this.计算年月=scriptExecutionContext.getContext().get("CONTEXT_CALCYEARMONTH");
		 System.out.println("计算年月------->"+计算年月);
		 BigDecimal retValue=process(员工工号);
		 setReturnValue(scriptExecutionContext,retValue.toString());
	}
	
	
	public BigDecimal process(String 员工工号){
		${methodContent}
	}
	
	public void setReturnValue(ScriptExecutionContext scriptExecutionContext,String retValue){
		scriptExecutionContext.setReturnValue(retValue);
	}
	
}