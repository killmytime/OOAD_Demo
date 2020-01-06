package service.serviceImpl;

import configuration.TaskStatus;
import configuration.UserProfile;
import entity.Task;
import entity.TaskPool;
import entity.User;
import service.AdminTaskService;

import java.util.ArrayList;
import java.util.UUID;

import static configuration.Global.AdminPool;
import static configuration.Global.userList;

public class AdminTaskImpl implements AdminTaskService {
    public Task postTask(Task task, User user) {
        if (checkAdmin(user)) {
            if (task.getTaskName().equals("")) return null;
            ArrayList<Task> tasks = AdminPool.getTasks();
            task.setTaskStatus(TaskStatus.POST);
            task.setTaskID(UUID.randomUUID());
            tasks.add(task);
            AdminPool.setTasks(tasks);
            new AdminTaskImpl().taskReload(task);
            return task;
        }
        return null;
    }

    public Task deleteTask(UUID taskID, User user) {
        if (checkAdmin(user)) {
            ArrayList<Task> tasks = AdminPool.getTasks();
            for (Task task : tasks) {
                if (taskID.equals(task.getTaskID())) {
                    task.setTaskStatus(TaskStatus.DELETE);
                    task.setLimit(0);
                    AdminPool.setTasks(tasks);
                    new AdminTaskImpl().taskReload(task);
                    return task;
                }
            }
            return null;
        }
        return null;
    }

    //针对一个数据持久化之后可能的场景，将主任务更新到用户个人任务那里去同时不修改用户已经更改的数据，题目没有要求到修改任务，所以这里只考虑添加删除，粗糙的做个遍历好了
    public void taskReload(Task task) {
            if (task.getTaskStatus() == TaskStatus.DELETE)
                for (User userItem : userList) {
                    ArrayList<Task> taskArrayList = userItem.getTaskPool().getTasks();
                    for (Task taskItem : taskArrayList) {
                        if (taskItem.getTaskID().equals(task.getTaskID())) {
                            if (taskItem.getTaskStatus() == TaskStatus.POST)
                                taskItem.setTaskStatus(TaskStatus.DELETE);
                            taskItem.setLimit(task.getLimit());
                            TaskPool taskPool = userItem.getTaskPool();
                            taskPool.setTasks(taskArrayList);
                            userItem.setTaskPool(taskPool);
                            break;
                        }
                    }
                }
            else if (task.getTaskStatus() == TaskStatus.POST)
                for (User userItem : userList) {
                    ArrayList<Task> taskArrayList = userItem.getTaskPool().getTasks();
                    boolean flag = true;
                    for (Task taskItem : taskArrayList) {
                        if (taskItem.getTaskID().equals(task.getTaskID())) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        taskArrayList.add(task);
                        TaskPool taskPool = userItem.getTaskPool();
                        taskPool.setTasks(taskArrayList);
                        userItem.setTaskPool(taskPool);
                    }
                }
    }

    private boolean checkAdmin(User user) {
        return user!=null&&user.getAuth().equals(UserProfile.ADMIN) && user.getUsername().equals(UserProfile.ADMIN.getUsername()) && user.getPassword().equals(UserProfile.ADMIN.getPassword());
    }
}
