package entity;

import configuration.TaskStatus;

import java.util.Date;
import java.util.UUID;

public class Task {
    private UUID taskId;//UUID简易搞一个唯一ID，反正也不用考虑持久化和后期维护的问题
    private int limit=1;//-1为无限，其他为限制次数,默认为1
    private String taskName="";
    private String taskDescription="";
    private TaskStatus taskStatus=TaskStatus.OFFLINE;//默认为OFFLine
    private float rewards=0;//默认为0
    private Date expired;//过期日期，可供扩展
    public boolean acceptTask(){
        if (this.limit==-1) return true;
        else if (this.limit>0){
            this.limit--;
            return true;
        }
        return false;
    }

    public Task copy(){
        Task task=new Task();
        task.setTaskId(this.taskId);
        task.setLimit(this.limit);
        task.setTaskName(this.taskName);
        task.setTaskDescription(this.taskDescription);
        task.setTaskStatus(this.taskStatus);
        task.setRewards(this.rewards);
        return task;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public float getRewards() {
        return rewards;
    }

    public void setRewards(float rewards) {
        this.rewards = rewards;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }
}
