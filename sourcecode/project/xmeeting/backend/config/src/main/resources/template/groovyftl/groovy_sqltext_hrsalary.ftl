package ${packageName};


import com.founder.sipbus.fwk.groovy.execution.*;

import com.founder.sipbus.fwk.groovy.groovytmpl.hrs.*;

${managedImports}


public class ${className}  extends SqlTextSupport implements IScriptExecution{ 
	 
	

	public void execute(final ScriptExecutionContext scriptExecutionContext){ 
	 	${methodContent}
		process(scriptExecutionContext);
	}
	
	
	
	
	
}