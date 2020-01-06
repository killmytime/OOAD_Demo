package entity;

import configuration.TaskStatus;
import configuration.UserProfile;
import lombok.Data;

import java.util.UUID;

@Data
public class Task {
    private UUID taskID;//UUID简易搞一个唯一ID，反正也不用考虑持久化和后期维护的问题
    private int limit=1;//-1为无限，其他为限制次数,默认为1
    private String taskName="";
    private String taskDescription="";
    private TaskStatus taskStatus=TaskStatus.OFFLINE;//默认为OFFLine
    private float rewards=0;//默认为0

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
        task.setTaskID(this.taskID);
        task.setLimit(this.limit);
        task.setTaskName(this.taskName);
        task.setTaskDescription(this.taskDescription);
        task.setTaskStatus(this.taskStatus);
        task.setRewards(this.rewards);
        return task;
    }
}
