package us.jameschan.supervisor.dto;

import java.beans.JavaBean;

@JavaBean
public class TaskUpdateDto {
    private Integer taskAction;

    public Integer getTaskAction() {
        return taskAction;
    }

    public void setTaskAction(Integer taskAction) {
        this.taskAction = taskAction;
    }
}
