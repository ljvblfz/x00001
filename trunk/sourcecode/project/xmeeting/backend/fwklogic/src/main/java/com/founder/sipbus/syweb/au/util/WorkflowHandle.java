package com.founder.sipbus.syweb.au.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.founder.fix.bpmn2extensions.coreconfig.UserInfo;
import com.founder.fix.bpmn2extensions.coreconfig.impl.UserInfoImpl;
import com.founder.fix.fixflow.core.ProcessEngine;
import com.founder.fix.fixflow.core.TaskService;
import com.founder.fix.fixflow.core.impl.Context;
import com.founder.fix.fixflow.core.impl.bpmn.behavior.TaskCommand;
import com.founder.fix.fixflow.core.impl.command.ExpandTaskCommand;
import com.founder.fix.fixflow.core.impl.command.StartProcessInstanceCommand;
import com.founder.fix.fixflow.core.runtime.ProcessInstance;
import com.founder.fix.fixflow.core.task.TaskInstance;
import com.founder.fix.fixflow.expand.config.FlowConst;
import com.founder.sipbus.common.util.ApplicationContextUtil;
import com.founder.sipbus.syweb.au.po.SysUser;

public class WorkflowHandle {

	private ProcessEngine processEngine;
	
	private SysUser currentUser;
	
	
	public SysUser getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(SysUser currentUser) {
		this.currentUser = currentUser;
	}

	private String currentUserId;
	
	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}

	public WorkflowHandle(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	
	public ProcessInstance getProcessInstance(String instId){
		return processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(instId).singleResult();
	}
	
	
	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	/**
	 * 
	 * @author founder 2012-6-25
	 * @param processKey
	 *            :process_1
	 * @param objGuid
	 *            : BUSINESS001
	 * @param author
	 *            : system
	 * @return
	 */
	public void startAndSubmit(String processKey, String objGuid,
			String author) {
//		StartProcessInstanceCommand startProcessInstanceCommand = new StartProcessInstanceCommand();
//		startProcessInstanceCommand.setProcessDefinitionKey(processKey);
//		startProcessInstanceCommand.setBusinessKey(objGuid);
//		if (StringUtils.isBlank(author)) {
//			author = // processEnginegetLoginUser().getUserid();
//			Authentication.getAuthenticatedUserId();
//		}
//		startProcessInstanceCommand.setStartAuthor(author);
//		ProcessInstance pi = processEngine.getRuntimeService()
//				.noneStartProcessInstance(startProcessInstanceCommand);
		
		processTask(null, null,
				"startandsubmit", "启动并提交", null,processKey,objGuid, author);
		
//		return null;
	}
	
	public void processTask(String taskId, String commandId,
			String commandType, String processDesc, Map<String, Object> map,String authId){
		//领取commandType - claim
		claimTask(taskId,authId);
		
		//处理任务
		processTask(taskId, commandId, commandType, processDesc, map , null,null, authId );
	}
	
	
//	public ProcessInstance startAndSubmit(String processKey, String objGuid,String author){
//		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
//		expandTaskCommand.setProcessDefinitionKey(processKey);
//		expandTaskCommand.setBusinessKey(objGuid);
//		
//		processTask(ti.getId(), "Advance_1", "startandsubmit", "submit", null);
//		return null;
//	}

	
	private void claimTask(String taskId, String authId){
		processTask(taskId,null,"claim",null,null,null,null,authId);
	}
	
	
	// commandType : rollBackTaskByTaskId
	// processDesc :"我把这个任务退回给我选中的一个任务了！"
	// commandId : Advance_4
	private void processTask(String taskId, String commandId,
			String commandType, String processDesc, Map<String, Object> map,String processKey,String businessKey,String authId) {
		TaskService ts = processEngine.getTaskService();
		// TaskInstance ti =
		// ts.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		
//		processEngine.

		// 用户自己创建的处理命令都以这种方式来执行,首先先创建一个扩展命令处理参数对象
		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setProcessDefinitionKey(processKey);
		expandTaskCommand.setBusinessKey(businessKey);
		//设置处理人
		expandTaskCommand.setInitiator(authId);

		Map m= new HashMap();
		if(null!=map)
			m.put("formInfo", map);
		m.put("appContext",ApplicationContextUtil.getCurrentWebApplicationContext());
		m.put("userInfo", currentUser);

		expandTaskCommand.setTransientVariables(m);
		
		
//		ts.getVariable(taskId, variableName);
		
		//按钮参数  如回退节点等信息
		//expandTaskCommand.setParamMap(paramMap)
		//非持久化数据，生命周期当前任务后半段至 下个任务的前半段
//		expandTaskCommand.setTransientVariables(transientVariables)
		//持久化数据，生命周期为当前流程实例
//		expandTaskCommand.setVariables()
		
		// 设置需要处理的任务号
		expandTaskCommand.setTaskId(taskId);
		// 设置用户点击的处理命令号
		expandTaskCommand.setUserCommandId(commandId);
		// 处理意见
		expandTaskCommand.setTaskComment(processDesc);
		// 处理命令类型(这里的类型请在流程配置里的处理命令扩展里查看那)
		expandTaskCommand.setCommandType(commandType);
		
		
		//expandTaskCommand.setBusinessKey("BUSINESS001");

		//设置处理人
//		expandTaskCommand.setInitiator(currentUserId);

		
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("rollBackTaskId", oneTaskInstance);
		// 创建一个RollBackTaskByTaskIdCommand所需要的参数
		expandTaskCommand.setParamMap(map);
		// 执行处理命令
		ts.expandTaskComplete(expandTaskCommand, null);

		//内存清理
//		processEngine.contextClose();
	}
	
	public TaskService getTaskService(){
		return processEngine.getTaskService();
	}

	public List<Map<String, Object>> getProcessCommandByTaskId(
			String taskInstanceId) {

		TaskService taskService = processEngine.getTaskService();

		TaskInstance taskInstance = taskService.createTaskQuery()
				.taskId(taskInstanceId).singleResult();

		String processDefinitionId = taskInstance.getProcessDefinitionId();
		String nodeId = taskInstance.getNodeId();
		String type;

		if (taskInstance.getEndTime() == null) {
			// 代办、共享
//			if (taskInstance.getAssignee() != null) {
//				// 代办
//				type = FlowConst.ProcessCommandType.TO_DO_TASKS;
//			} else {
//				// 共享
//				type = FlowConst.ProcessCommandType.SHARED_TASKS;
//			}

			type = FlowConst.ProcessCommandType.TO_DO_TASKS;

		} else {
			// 已办、追踪
			type = FlowConst.ProcessCommandType.FINISH_TASKS;

		}

		List<Map<String, Object>> listMap = getProcessCommand(
				processDefinitionId, nodeId, type);

		
		//内存清理
//		processEngine.contextClose();
		
		return listMap;
	}

	private List<Map<String, Object>> getProcessCommand(
			String processDefinitionId, String nodeId, String type) {

		TaskService taskService = processEngine.getTaskService();

		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();

		List<TaskCommand> taskCommands = taskService.getTaskCommandById(
				processDefinitionId, nodeId);

		for (TaskCommand taskCommand : taskCommands) {

			Map<String, Map<String, String>> map = Context
					.getProcessEngineConfiguration().getTaskCommandMap();
			String typeString = taskCommand.getTaskCommandType();
			Map<String, String> mapObjMap = map.get(typeString);
			if (mapObjMap != null) {
				if (mapObjMap.get("type").equals(type)
						|| mapObjMap.get("type").equals(
								FlowConst.ProcessCommandType.ALL)) {
					Map<String, Object> mapObj = taskCommand
							.getPersistentState();
					listMap.add(mapObj);
				}
			}

		}

		return listMap;
	}

}
