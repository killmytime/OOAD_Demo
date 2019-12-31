package service.serviceImpl;

import entity.Task;
import entity.User;
import service.UserTaskService;

public class UserTaskServiceImpl implements UserTaskService {
    public boolean acceptTask(Task task, User user) {
        return false;
    }

    public boolean finishTask(Task task, User user) {
        return false;
    }

    public boolean abandonTask(Task task, User user) {
        return false;
    }
}
