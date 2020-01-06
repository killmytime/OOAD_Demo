package service.serviceImpl;

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

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;


/**
 * 注意：该测试是一整套流程，完成任务依赖于接受任务，执行测试请三个都运行
 */
class UserTaskServiceImplTest {
    UserService userService = new UserServiceImpl();
    UserTaskService userTaskService = new UserTaskServiceImpl();
    User user1, user2;

    @BeforeEach
    void setUp() {
        User admin = userService.login(UserProfile.ADMIN.getUsername(), UserProfile.ADMIN.getPassword());
        AdminTaskService adminTaskService = new AdminTaskServiceImpl();
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
        user1 = userService.login(UserProfile.user1.getUsername(), UserProfile.user1.getPassword());
        user2 = userService.login(UserProfile.user2.getUsername(), UserProfile.user2.getPassword());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void acceptTask1() {
        int limit1=user1.getDailyLimit();
        //无权限操作
        assertFalse(userTaskService.acceptTask(user1.getTaskPool().getTasks().get(0), user2, UserProfile.user1));
        assertFalse(userTaskService.acceptTask(user1.getTaskPool().getTasks().get(0), new User(), UserProfile.user1));
        //接受不存在的任务
        assertFalse(userTaskService.acceptTask(new Task(), user1, UserProfile.user2));
        //接受有剩余次数任务
        int taskLimit=user1.getTaskPool().getTasks().get(0).getLimit();
        assertTrue(userTaskService.acceptTask(user1.getTaskPool().getTasks().get(0), user1, UserProfile.user1));
        assertEquals(--taskLimit,user1.getTaskPool().getTasks().get(0).getLimit());
        assertEquals(TaskStatus.PROCESSING,user1.getTaskPool().getTasks().get(0).getTaskStatus());
        //接受正在进行的任务
        //接受每日任务
        //接受无限任务
        //接受无剩余次数任务

    }

    @Test
    void abandonTask2() {
        //无权限操作
        //放弃不存在的任务
        //放弃有剩余次数任务
        //放弃未进行的任务
        //放弃每日任务
        //放弃无限任务
        //放弃无剩余次数任务
    }

    //逻辑与abandonTask大致相同，除结算部分之外,故这里仅针对一项任务做测试
    @Test
    void finishTask3() {
        //数据初始化--无限任务
        //无权限操作
        //完成不存在的任务
        //完成无限任务--完成+结算etc.
    }


}
