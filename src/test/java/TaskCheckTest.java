import configuration.Global;
import configuration.TaskStatus;
import configuration.UserProfile;
import entity.DailyTask;
import entity.Task;
import entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.AdminTaskService;
import service.UserService;
import service.UserTaskService;
import service.serviceImpl.AdminTaskServiceImpl;
import service.serviceImpl.UserServiceImpl;
import service.serviceImpl.UserTaskServiceImpl;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 期中用例难度级别一第二个
 * App用户查看可做的任务和任务的状态
 */
public class TaskCheckTest {
    UserService userService = new UserServiceImpl();
    UserTaskService userTaskService = new UserTaskServiceImpl();
    AdminTaskService adminTaskService = new AdminTaskServiceImpl();
    User user1, admin;

    @BeforeEach
    void setUp() {
        admin = userService.login(UserProfile.ADMIN.getUsername(), UserProfile.ADMIN.getPassword());

        Task taskOnce = new Task();
        taskOnce.setTaskName("有剩余次数任务");
        taskOnce.setLimit(12);
        adminTaskService.postTask(taskOnce, admin);
        DailyTask taskDaily = new DailyTask();
        taskDaily.setTaskName("每日任务");
        taskDaily.setToday(new Date());
        adminTaskService.postTask(taskDaily, admin);
        Task taskUnLimit = new Task();
        taskUnLimit.setTaskName("无限任务");
        taskUnLimit.setLimit(-1);
        adminTaskService.postTask(taskUnLimit, admin);
        Task taskFinished = new Task();
        taskFinished.setTaskName("已完成（无剩余次数）任务");
        taskFinished.setLimit(0);
        adminTaskService.postTask(taskFinished, admin);
        Task taskUnProcessing = new Task();
        taskUnProcessing.setTaskName("未进行任务（保留）");
        taskUnProcessing.setLimit(100);
        adminTaskService.postTask(taskUnProcessing, admin);
        user1 = userService.login(UserProfile.user1.getUsername(), UserProfile.user1.getPassword());
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * 共有OFFLINE，POST，PROCESSING,FINISH,ABANDON,DELETE几种状态
     * 其中主要是POST和PROCESSING状态
     * 完成状态和放弃状态都是中间态再进行结算之后会切换成POST状态为了下一次可能的执行
     * 对于任务一方面是过期时间的校验，一方面是剩余次数的校验，其中对于每日任务还有当前日和上一次完成任务时间的校验
     * 故而这里的测试只测试获取状态，具体测试可见之前开发时预留的单元测试
     * test/java/service.serviceImpl/UserTaskServiceImplTest.java
     */
    @Test
    void taskCheckTest() {
        //检查不存在的任务,在这里没有测试必要了..因为是通过唯一id访问的
        //无权限操作
        assertNull(userTaskService.getTasks(TaskStatus.POST,user1,UserProfile.user2));
        //查看任务状态
        assertEquals(TaskStatus.POST,user1.getTaskPool().getTasks().get(0).getTaskStatus());
        //获取可做任务列表
        assertEquals(Global.AdminPool.getTasks().size()-1,userTaskService.getTasks(TaskStatus.POST,user1,UserProfile.user1).size());
    }

}
