package service;

import entity.Task;
import entity.User;

public interface UserTaskService {
    boolean acceptTask(Task task, User user);
    boolean finishTask(Task task,User user);
    boolean abandonTask(Task task,User user);
}
