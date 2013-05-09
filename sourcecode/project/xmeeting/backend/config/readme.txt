	<bean id="sprTaskServiceHolder"
		class="com.hp.rccpp.spr.rt.api.internal.SPRServiceHolder"
		factory-method="getInstance">
		<property name="properties" ref="sprconfig"/>
		<property name="taskService" ref="sprTaskService"/>
		<property name="subscriberService" ref="subscriberService"/>
        <property name="subscriberCounterService" ref="subscriberCounterService"/>
        <property name="subscriptionService" ref="subscriptionService"/>
        <property name="serviceQuotaService" ref="servicequotaService"/>
        <property name="serviceUsageCounterService" ref="serviceusagecounterService"/>
        <property name="deviceIdentifierService" ref="deviceIdentifierService"/>
	</bean>
	
	
	
    public static SPRServiceHolder getInstance() {
        return singleton;
    }
	
	
    private static final SPRServiceHolder singleton = new SPRServiceHolder();
	
	
	
	
		<property name="sqlValue">
			<bean class="org.springframework.beans.factory.config.PropertiesFactoryBean">
				<property name="location" value="classpath:/springconfig/jcommon.springbean.labelvalue.JdbcLabelValueListImpl.properties"/>
			</bean>
		</property>
		
		
		
	private Map<String, String> sqlValue;

	public Map<String, String> getSqlValue() {
		return sqlValue;
	}

	public void setSqlValue(Map<String, String> sqlValue) {
		this.sqlValue = sqlValue;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
var iframe = document.createElement('iframe');
iframe.frameBorder = 1;
iframe.width = "200px";
iframe.height = "100px";
iframe.id = "xxxiframe";

iframe.onload = function()
{
    var doc = iframe.contentDocument || iframe.contentWindow.document;
    var form = doc.createElement("from");
	form.action="test.yupload";
	form.enctype="multipart/form-data";
	form.method="post";
	
	var file = doc.createElement("input");
	file.type="file"; 

	
	var button = doc.createElement("input");
	button.type="button";
	button.value = "upload"; 
	button.onclick=function(){
			alert("upload the file."+ $(e1).val());  
			$(form).ajaxForm(function(){
				alert("upload the file successfully.")
			});
	}
    doc.body.appendChild(form);
};

document.getElementById("div").appendChild(iframe); //this is a div  that I have in 
	
	
	20120621051307118hr_employee_guid4
	//////////////
	
								button.onclick=function(){
										//alert("upload the file."+ $(file).val()); 
										$(e0).val($(file).val()); 
										//alert(form.action);
										//alert(form.enctype);
										//alert(form.method);
										$(form).submit(function(){
											alert("upload the file successfully.");
											return false;
										});
								}
								
								//////////////////////
	
	
	
							        // var sampleFile = document.getElementById("sampleFile").files[0]; 
							        var sampleFile = e1;  
							        var xhr = new XMLHttpRequest();     
							        alert("2");
							        xhr.open("POST","test.yupload", true); 
							        alert("3");
							        xhr.send(sampleFile); 
							        alert("4");
							        xhr.onload = function(re) { 
							            if (this.status == 200) { 
							               alert(this.responseText); 
							            } 
							        };     