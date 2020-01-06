package service.serviceImpl;

import configuration.TaskStatus;
import entity.DailyTask;
import entity.Task;
import entity.User;
import service.UserTaskService;

import java.util.Date;

/**
 * 从简，service和entity的中间间就省略了，写到entity里了
 */
public class UserTaskServiceImpl implements UserTaskService {
    //不结算积分
    public boolean acceptTask(Task task, User user) {
        //每日任务上限
        if (user.getDailyLimit() <= 0) return false;
        for (Task taskItem : user.getTaskPool().getTasks()) {
            //仅考虑发布状态的任务
            if (taskItem.getTaskStatus() == TaskStatus.POST && taskItem.getTaskID().equals(task.getTaskID())) {
                //dailyTask,防止冲突的简陋做法，
                if (taskItem instanceof DailyTask) {
                    ((DailyTask) taskItem).setFinishDate(null);
                    ((DailyTask) taskItem).setToday(null);
                }
                if (taskItem.acceptTask()) {
                    taskItem.setTaskStatus(TaskStatus.PROCESSING);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    //结算积分
    public boolean finishTask(Task task, User user) {
        for (Task taskItem : user.getTaskPool().getTasks()) {
            //仅考虑发布状态的任务
            if (taskItem.getTaskStatus() == TaskStatus.PROCESSING && taskItem.getTaskID().equals(task.getTaskID())) {
                //dailyTask,确保当天的任务第二天才能刷新
                if (taskItem instanceof DailyTask) {
                    ((DailyTask) taskItem).setToday(new Date());
                    ((DailyTask) taskItem).setFinishDate(new Date());
                }
                taskItem.setTaskStatus(TaskStatus.POST);
                return true;
            }
        }
        return false;
    }

    //不结算积分
    public boolean abandonTask(Task task, User user) {
        for (Task taskItem : user.getTaskPool().getTasks()) {
            //仅考虑发布状态的任务
            if (taskItem.getTaskStatus() == TaskStatus.PROCESSING && taskItem.getTaskID().equals(task.getTaskID())) {
                //dailyTask,每日任务放弃了第二天还是可以做==>推之任务都可以这么来，判断剩余次数即可
                if (taskItem instanceof DailyTask) {
                    ((DailyTask) taskItem).setToday(new Date());
                    ((DailyTask) taskItem).setFinishDate(new Date());
                }
                taskItem.setTaskStatus(TaskStatus.POST);
                return true;
            }
        }
        return false;
    }
}
