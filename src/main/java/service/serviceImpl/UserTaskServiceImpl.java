package service.serviceImpl;

import configuration.TaskStatus;
import configuration.UserProfile;
import entity.DailyTask;
import entity.Task;
import entity.User;
import service.UserTaskService;

import java.util.Date;

/**
 * 从简，service和entity的中间间就省略了，写到entity里了
 * 考虑到只存储在内存中，不设置计时器刷新每日任务和每日任务上限了
 */
public class UserTaskServiceImpl implements UserTaskService {
    //不结算积分
    public boolean acceptTask(Task task, User user, UserProfile auth) {
        //每日任务上限
        if (checkUser(user, auth)) {
            if (user.getDailyLimit() <= 0) return false;
            for (Task taskItem : user.getTaskPool().getTasks()) {
                //仅考虑发布状态的任务&&任务id校验&&对于不同任务的剩余次数的处理
                if (taskItem.getTaskStatus() == TaskStatus.POST && taskItem.getTaskID().equals(task.getTaskID()) && taskItem.acceptTask()) {
                    //dailyTask,简单的标识
                    if (taskItem instanceof DailyTask) {
                        if (((DailyTask) taskItem).getFinishDate() != null && ((DailyTask) taskItem).getFinishDate().before(((DailyTask) taskItem).getToday())) {
                            ((DailyTask) taskItem).setFinishDate(null);
                            ((DailyTask) taskItem).setToday(null);
                        } else return false;
                    }
                    taskItem.setTaskStatus(TaskStatus.PROCESSING);
                    user.taskAccepted();
                    return true;
                }
            }
        }
        return false;
    }

    //结算积分
    public boolean finishTask(Task task, User user, UserProfile auth) {
        if (checkUser(user, auth)) {
            for (Task taskItem : user.getTaskPool().getTasks()) {
                //仅考虑发布状态的任务
                if (taskItem.getTaskStatus() == TaskStatus.PROCESSING && taskItem.getTaskID().equals(task.getTaskID())) {
                    //dailyTask,确保当天的任务第二天才能刷新
                    if (taskItem instanceof DailyTask) {
                        ((DailyTask) taskItem).setToday(new Date());
                        ((DailyTask) taskItem).setFinishDate(new Date());
                        taskItem.setTaskStatus(TaskStatus.FINISH);
                    } else
                        taskItem.setTaskStatus(TaskStatus.POST);
                    return true;
                }
            }
        }
        return false;
    }

    //不结算积分
    public boolean abandonTask(Task task, User user, UserProfile auth) {
        if (checkUser(user, auth)) {
            for (Task taskItem : user.getTaskPool().getTasks()) {
                //仅考虑发布状态的任务
                if (taskItem.getTaskStatus() == TaskStatus.PROCESSING && taskItem.getTaskID().equals(task.getTaskID())) {
                    //dailyTask,每日任务放弃了第二天还是可以做==>推之任务都可以这么来，判断剩余次数即可
                    if (taskItem instanceof DailyTask) {
                        ((DailyTask) taskItem).setToday(new Date());
                        ((DailyTask) taskItem).setFinishDate(new Date());
                        taskItem.setTaskStatus(TaskStatus.ABANDON);
                    } else
                        taskItem.setTaskStatus(TaskStatus.POST);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkUser(User user, UserProfile auth) {
        return user != null && user.getAuth() != null && user.getAuth() == auth;
    }
}
