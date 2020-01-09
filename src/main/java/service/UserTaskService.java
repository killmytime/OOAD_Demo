package service;

import configuration.TaskStatus;
import configuration.UserProfile;
import entity.Task;
import entity.User;

import java.util.ArrayList;

public interface UserTaskService {
    boolean acceptTask(Task task, User user, UserProfile auth);
    boolean finishTask(Task task, User user, UserProfile auth);
    boolean abandonTask(Task task, User user, UserProfile auth);
    ArrayList<Task> getTasks(TaskStatus taskStatus,User user, UserProfile auth);
}
