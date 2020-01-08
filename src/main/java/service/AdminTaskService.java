package service;

import entity.Task;
import entity.TaskPool;
import entity.User;

import java.util.UUID;

//Admin用户认证从简处理，字符串
public interface AdminTaskService {
    public Task postTask(Task task, User user);

    public Task deleteTask(UUID taskId, User user);//其他用户的指定任务状态为发布的变为删除

    public void taskReload(Task task);//其他用户的taskpool从这里更新，然后分情况对任务limit，date，finish等状态更新,无需校验身份

}
