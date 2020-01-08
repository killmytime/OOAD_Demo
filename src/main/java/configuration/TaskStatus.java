package configuration;

public enum TaskStatus {
    //分别对应离线状态，发布状态，任务进行状态，任务完成状态，任务放弃状态，任务删除状态
    //其中离线状态为保留状态
    // 任务完成状态和任务放弃状态基本不使用，但基于可扩展的理念有所涉及
    OFFLINE, POST, PROCESSING, FINISH, ABANDON,DELETE;
}
