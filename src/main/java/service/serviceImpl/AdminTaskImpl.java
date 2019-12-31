package service.serviceImpl;

import entity.Task;
import entity.TaskPool;
import service.AdminTaskService;

import java.util.UUID;

public class AdminTaskImpl implements AdminTaskService {
    public Task postTask(Task task) {
        return null;
    }

    public boolean deleteTask(UUID taskID) {
        return false;
    }

    public void TaskInitial(TaskPool taskPool) {

    }
}
