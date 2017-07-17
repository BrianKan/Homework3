package com.sargent.mark.todolist.data;

/**
 * Created by mark on 7/4/17.
 */

public class ToDoItem {
    private String description;
    private String dueDate;
    //TODO added fields and getters for type and is Done to ToDoitem
    private String type;

    private boolean isDone;

    public ToDoItem(String description, String dueDate,String type,boolean isDone) {
        this.description = description;
        this.dueDate = dueDate;
        this.type=type;
        this.isDone=isDone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getType() {
        return type;
    }

    public boolean isDone() {
        return isDone;
    }
}
