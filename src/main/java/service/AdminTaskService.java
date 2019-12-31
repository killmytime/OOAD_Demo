package service;

import entity.Task;
import entity.TaskPool;

import java.util.UUID;
//Admin用户认证从简处理，字符串
public interface AdminTaskService {
    public Task postTask(Task task);
    public boolean deleteTask(UUID taskID);//其他用户的指定任务状态为发布的变为删除
    public void TaskInitial(TaskPool taskPool);//这里更新admin用户的taskpool，其他用户的taskpool从这里更新，然后分情况对任务limit，date，finish等状态更新

}
