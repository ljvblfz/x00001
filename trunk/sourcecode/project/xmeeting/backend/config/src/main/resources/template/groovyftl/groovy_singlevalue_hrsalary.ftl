package ${packageName};


import com.founder.sipbus.fwk.groovy.execution.*;

import com.founder.sipbus.fwk.groovy.groovytmpl.hrs.*;

${managedImports}


public class ${className}  extends SingleValueSupport implements IScriptExecution{ 
	 
	

	public void execute(final ScriptExecutionContext scriptExecutionContext){  
	 	scriptExecutionContext.setReturnValue("${methodContent}");
		//process(scriptExecutionContext);
	}
	
	
	
	
	
}