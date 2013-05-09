package com.founder.sipbus.fwk.groovy.groovytmpl.hrs;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.founder.sipbus.fwk.groovy.execution.ScriptExecutionContext;
import com.founder.sipbus.syweb.script.dao.SyScriptDecisiontableDaoImpl;
import com.founder.sipbus.syweb.script.po.SyScriptDecisiontable;


/**
 * 薪酬计算公共函数
 * @author lu.zhen
 *
 */
@Component
public class DecisionTableSupport extends KpiAndParamSupport  {

	

	
	
	//
	
	public List<SyScriptDecisiontable> findDecisionTableByScriptName(String scriptName){
		Assert.notNull(syScriptDecisiontableDao); 
		List<SyScriptDecisiontable>  listOf=syScriptDecisiontableDao.findByScriptName(scriptName);
		return listOf;
	}


	
	
	public void processWithMinAndMax(final ScriptExecutionContext scriptExecutionContext,String kpiName){
		String scriptName="";
		String conditionValue="";
		String actionValue="Not Found Matched Value.";
		if(scriptExecutionContext!=null&&scriptExecutionContext.getScriptName()!=null)
		{
			scriptName=scriptExecutionContext.getScriptName();
			String jobnumber=scriptExecutionContext.getEmployeeJobNumber();
			conditionValue=cacheKpiAndParamSupport.getKpiValue(jobnumber, kpiName);
			if(conditionValue!=null)
			{
				List<SyScriptDecisiontable>  listOfDecisiontable=findDecisionTableByScriptName(scriptName);
				for(SyScriptDecisiontable decisionTable:listOfDecisiontable){
					if(Double.valueOf(conditionValue)>=Double.valueOf(decisionTable.getMinValue())&&Double.valueOf(conditionValue)<Double.valueOf(decisionTable.getMaxValue())){
						actionValue=decisionTable.getActionValue();
						break;
					}
				}
			}
		}
		if(null!=actionValue){ 
			scriptExecutionContext.setReturnValue(actionValue);
		}
		else
		{
			scriptExecutionContext.setReturnValue("0");
		}



	
	}//end of processWithMinAndMax
	
	

	public void processWithSingleMin(final ScriptExecutionContext scriptExecutionContext){
		String scriptName="";
		String conditionValue="";
		String actionValue="Not Found Matched Value.";
		List<SyScriptDecisiontable>  listOfDecisiontable=findDecisionTableByScriptName(scriptName);
		for(SyScriptDecisiontable decisionTable:listOfDecisiontable){
			if(conditionValue.equals(decisionTable.getMinValue())){
				actionValue=decisionTable.getActionValue();
				break;
			}
		}
		if(null!=actionValue){ 
			scriptExecutionContext.setReturnValue(actionValue);
		}
	
	}//end of processWithMinAndMax
	 
	
	//============================IOC=================================
	@Resource(name="syScriptDecisiontableDao")
	private SyScriptDecisiontableDaoImpl syScriptDecisiontableDao;

	public void setSyScriptDecisiontableDao(
			SyScriptDecisiontableDaoImpl syScriptDecisiontableDao) { 
		this.syScriptDecisiontableDao = syScriptDecisiontableDao;
	}
	 
	@Resource(name="cacheKpiAndParamSupportDummyImpl")
	private ICacheKpiAndParamSupport cacheKpiAndParamSupport;  
	public void setCacheKpiAndParamSupport(
			ICacheKpiAndParamSupport cacheKpiAndParamSupport) {
		this.cacheKpiAndParamSupport = cacheKpiAndParamSupport;
	} 
	
	
}
