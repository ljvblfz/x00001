
package com.xmeeting;

public class ToDoEntity {
    @SuppressWarnings("unused")
	private static final String TAG = ToDoEntity.class.getSimpleName();

    private String todoId;

    private String position;

    private String type;

    private String timeStr;

    public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}

	private boolean isChecked = true;

	public String getTodoId() {
		return todoId;
	}

	public void setTodoId(String todoId) {
		this.todoId = todoId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}



}
