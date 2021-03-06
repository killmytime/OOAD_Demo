//package service.serviceImpl;
//
//import configuration.TaskStatus;
//import configuration.UserProfile;
//import entity.DailyTask;
//import entity.Task;
//import entity.User;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import service.AdminTaskService;
//import service.UserService;
//import service.UserTaskService;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.Date;
//
//
///**
// * 注意：该测试是一整套流程，完成任务依赖于接受任务，执行测试请三个都运行
// *  * 这是部分单元测试，不是本次作业内容
// */
//class UserTaskServiceImplTest {
//    UserService userService = new UserServiceImpl();
//    UserTaskService userTaskService = new UserTaskServiceImpl();
//    AdminTaskService adminTaskService = new AdminTaskServiceImpl();
//    User user1, user2,admin;
//
//    @BeforeEach
//    void setUp() {
//        admin=userService.login(UserProfile.ADMIN.getUsername(), UserProfile.ADMIN.getPassword());
//
//        Task taskOnce = new Task();
//        taskOnce.setTaskName("有剩余次数任务");
//        taskOnce.setLimit(12);
//        adminTaskService.postTask(taskOnce, admin);
//        DailyTask taskDaily = new DailyTask();
//        taskDaily.setTaskName("每日任务");
//        taskDaily.setToday(new Date());
//        adminTaskService.postTask(taskDaily, admin);
//        Task taskUnLimit = new Task();
//        taskUnLimit.setTaskName("无限任务");
//        taskUnLimit.setLimit(-1);
//        adminTaskService.postTask(taskUnLimit, admin);
//        Task taskFinished = new Task();
//        taskFinished.setTaskName("已完成（无剩余次数）任务");
//        taskFinished.setLimit(0);
//        adminTaskService.postTask(taskFinished, admin);
//        Task taskUnProcessing =new Task();
//        taskUnProcessing.setTaskName("未进行任务（保留）");
//        taskUnProcessing.setLimit(100);
//        adminTaskService.postTask(taskUnProcessing,admin);
//        user1 = userService.login(UserProfile.user1.getUsername(), UserProfile.user1.getPassword());
//        user2 = userService.login(UserProfile.user2.getUsername(), UserProfile.user2.getPassword());
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//    //Todo 接受日期不对的每日任务
//    @Order(1)
//    @Test
//    void acceptTask() {
//        int limit1 = user1.getDailyLimit();
//        //无权限操作
//        assertFalse(userTaskService.acceptTask(user1.getTaskPool().getTasks().get(0), user2, UserProfile.user1));
//        assertFalse(userTaskService.acceptTask(user1.getTaskPool().getTasks().get(0), new User(), UserProfile.user1));
//        //接受不存在的任务
//        assertFalse(userTaskService.acceptTask(new Task(), user1, UserProfile.user1));
//        assertEquals(limit1,user1.getDailyLimit());
//        //无剩余次数接任务
//        user1.setDailyLimit(0);
//        assertFalse(userTaskService.acceptTask(user1.getTaskPool().getTasks().get(0),user1,UserProfile.user1));
//        user1.setDailyLimit(limit1);
//        //接受有剩余次数任务
//        Task task = user1.getTaskPool().getTasks().get(0);
//        int limit_task = task.getLimit();
//        assertTrue(userTaskService.acceptTask(task, user1, UserProfile.user1));
//        assertEquals(--limit_task, task.getLimit());
//        assertEquals(TaskStatus.PROCESSING, task.getTaskStatus());
//        assertEquals(--limit1, user1.getDailyLimit());
//        //接受正在进行的任务
//        assertFalse(userTaskService.acceptTask(task, user1, UserProfile.user1));
//        assertEquals(limit1,user1.getDailyLimit());
//        //接受每日任务
//        DailyTask taskDaily = (DailyTask) user1.getTaskPool().getTasks().get(1);
//        int limit_taskDaily = taskDaily.getLimit();
//        assertTrue(userTaskService.acceptTask(taskDaily, user1, UserProfile.user1));
//        assertEquals(limit_taskDaily, taskDaily.getLimit());
//        assertEquals(TaskStatus.PROCESSING, taskDaily.getTaskStatus());
//        assertNull(taskDaily.getToday());
//        assertEquals(--limit1,user1.getDailyLimit());
//        //接受无限任务
//        Task taskUnLimit = user1.getTaskPool().getTasks().get(2);
//        int unLimit = taskUnLimit.getLimit();
//        assertTrue(userTaskService.acceptTask(taskUnLimit, user1, UserProfile.user1));
//        assertEquals(unLimit, taskUnLimit.getLimit());
//        assertEquals(TaskStatus.PROCESSING, taskUnLimit.getTaskStatus());
//        assertEquals(--limit1,user1.getDailyLimit());
//        //接受无剩余次数任务
//        Task taskNon = user1.getTaskPool().getTasks().get(3);
//        int non =taskNon.getLimit();
//        assertFalse(userTaskService.acceptTask(taskNon,user1,UserProfile.user1));
//        assertEquals(non,taskNon.getLimit());
//        assertEquals(limit1,user1.getDailyLimit());
//
//    }
//    //未对积分进行操作，故不在此测试积分是否变动
//    @Order(2)
//    @Test
//    void abandonTask() {
//        //初始化
//        userTaskService.acceptTask(user1.getTaskPool().getTasks().get(0), user1, UserProfile.user1);
//        userTaskService.acceptTask(user1.getTaskPool().getTasks().get(1), user1, UserProfile.user1);
//        userTaskService.acceptTask(user1.getTaskPool().getTasks().get(2), user1, UserProfile.user1);
//        //无权限操作
//        assertFalse(userTaskService.abandonTask(user1.getTaskPool().getTasks().get(0), user2, UserProfile.user1));
//        assertFalse(userTaskService.abandonTask(user1.getTaskPool().getTasks().get(0), new User(), UserProfile.user1));
//        //放弃不存在的任务
//        assertFalse(userTaskService.acceptTask(new Task(), user1, UserProfile.user1));
//        //放弃有剩余次数任务
//        Task task = user1.getTaskPool().getTasks().get(0);
//        System.out.println(task.getLimit());
//        assertTrue(userTaskService.abandonTask(task, user1, UserProfile.user1));
//        assertEquals(TaskStatus.POST, task.getTaskStatus());
//        //放弃未进行的任务
//        Task taskUnProcessing = user1.getTaskPool().getTasks().get(4);
//        assertFalse(userTaskService.abandonTask(taskUnProcessing,user1,UserProfile.user1));
//        //放弃每日任务
//        DailyTask taskDaily = (DailyTask) user1.getTaskPool().getTasks().get(1);
//        assertTrue(userTaskService.abandonTask(taskDaily,user1,UserProfile.user1));
//        assertEquals(TaskStatus.POST,taskDaily.getTaskStatus());
//        //放弃无限任务
//        Task taskUnLimit = user1.getTaskPool().getTasks().get(2);
//        assertTrue(userTaskService.abandonTask(taskUnLimit,user1,UserProfile.user1));
//        assertEquals(TaskStatus.POST,taskUnLimit.getTaskStatus());
//    }
//
//    //Todo 财务结算待补充
//    //逻辑与abandonTask大致相同，除结算部分之外,故这里仅针对一项任务做测试
//    @Order(3)
//    @Test
//    void finishTask() {
//        //数据初始化--接受无限任务
//        assertTrue(user1.getDailyLimit()>0);
//        Task taskUnLimit = user1.getTaskPool().getTasks().get(2);
//        int unLimit = taskUnLimit.getLimit();
//        assertTrue(userTaskService.acceptTask(taskUnLimit, user1, UserProfile.user1));
//        assertEquals(unLimit, taskUnLimit.getLimit());
//        assertEquals(TaskStatus.PROCESSING, taskUnLimit.getTaskStatus());
//        //无权限操作
//        assertFalse(userTaskService.finishTask(user1.getTaskPool().getTasks().get(0), user2, UserProfile.user1));
//        assertFalse(userTaskService.finishTask(user1.getTaskPool().getTasks().get(0), new User(), UserProfile.user1));
//        //完成不存在的任务
//        assertFalse(userTaskService.acceptTask(new Task(), user1, UserProfile.user1));
//        //完成无限任务--完成+结算etc.
//        assertTrue(userTaskService.finishTask(taskUnLimit,user1,UserProfile.user1));
//        //Todo 结算的校验
//    }
//
//
//}
