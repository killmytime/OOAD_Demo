package entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class DailyTask extends Task {
    private Date finishDate;
    private Date today;

    public DailyTask copy(){
        DailyTask task=new DailyTask();
        task.setTaskID(this.getTaskID());
        task.setLimit(this.getLimit());
        task.setTaskName(this.getTaskName());
        task.setTaskDescription(this.getTaskDescription());
        task.setTaskStatus(this.getTaskStatus());
        task.setRewards(this.getRewards());
        task.setFinishDate(this.finishDate);
        task.setToday(this.today);
        return task;
    }
}
