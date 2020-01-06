package service;

import configuration.UserProfile;
import entity.Task;
import entity.User;

public interface UserTaskService {
    boolean acceptTask(Task task, User user, UserProfile auth);
    boolean finishTask(Task task, User user, UserProfile auth);
    boolean abandonTask(Task task, User user, UserProfile auth);
}
