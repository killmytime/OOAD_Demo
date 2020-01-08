package entity;


import java.util.Date;

public class DailyTask extends Task {
    private Date finishDate;
    private Date today;

    public DailyTask copy(){
        DailyTask task=new DailyTask();
        task.setTaskId(this.getTaskId());
        task.setLimit(this.getLimit());
        task.setTaskName(this.getTaskName());
        task.setTaskDescription(this.getTaskDescription());
        task.setTaskStatus(this.getTaskStatus());
        task.setRewards(this.getRewards());
        task.setFinishDate(this.finishDate);
        task.setToday(this.today);
        return task;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }
}
