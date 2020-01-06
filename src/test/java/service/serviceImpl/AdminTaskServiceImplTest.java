package service.serviceImpl;

import configuration.Global;
import configuration.TaskStatus;
import configuration.UserProfile;
import entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.AdminTaskService;
import service.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AdminTaskServiceImplTest {
    private static User admin;
    private UserService userService = new UserServiceImpl();
    private AdminTaskService adminTaskService = new AdminTaskServiceImpl();

    @BeforeEach
    void setUp() {
        admin = userService.login("ADMIN", "^passwordisnotADMIN!");
        assertEquals(UserProfile.ADMIN, admin.getAuth());
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * 重复任务不做考虑，唯一生成id，考虑很多任务的可重复性，季节性周期发布等等
     */
    @Test
    void postTask() {
        //缺失任务名字的为null
        Task task0 = new Task();
        assertNull(adminTaskService.postTask(task0, admin));
        //缺失其他信息的取默认值
        int size = Global.AdminPool.getTasks().size();
        Task task1 = new Task();
        task1.setTaskName("test1");
        task1 = adminTaskService.postTask(task1, admin);
        assertEquals(++size, Global.AdminPool.getTasks().size());
        assertTrue(Global.AdminPool.getTasks().contains(task1));
        //无权限操作
        Task task2 = new Task();
        task2.setTaskName("test2");
        task2 = adminTaskService.postTask(task2, userService.login("aaa", "adad"));
        assertNull(task2);
        assertEquals(size, Global.AdminPool.getTasks().size());
        //设置任务次数测试
        Task task3 = new Task();
        task3.setTaskName("test3");
        task3.setLimit(555);
        task3 = adminTaskService.postTask(task3, admin);
        assertEquals(++size, Global.AdminPool.getTasks().size());
        assertEquals(555, task3.getLimit());
        //每日任务
        DailyTask task4 = new DailyTask();
        Date date = new Date();
        task4.setTaskName("task4");
        task4.setToday(date);
        task4 = (DailyTask) adminTaskService.postTask(task4, admin);
        assertEquals(++size, Global.AdminPool.getTasks().size());
        assertEquals(date, task4.getToday());
        assertNull(task4.getFinishDate());


    }

    @Test
    void deleteTask() {
        int size = Global.AdminPool.getTasks().size();
        Task task0 = new Task();
        task0.setTaskName("delete_test");
        task0.setLimit(11);
        task0 = adminTaskService.postTask(task0, admin);
        assertEquals(++size, Global.AdminPool.getTasks().size());
        //无权限操作
        assertNull(adminTaskService.deleteTask(task0.getTaskID(),new User()));
        //删除任务测试
        task0 = adminTaskService.deleteTask(task0.getTaskID(), admin);
        assertEquals(0, task0.getLimit());
        assertEquals(TaskStatus.DELETE, task0.getTaskStatus());
        assertEquals(size, Global.AdminPool.getTasks().size());
    }

    @Test
    void taskReload() {
        //普通用户登陆
        User user1 = userService.login(UserProfile.user1.getUsername(), UserProfile.user1.getPassword());
        User user2 = userService.login(UserProfile.user2.getUsername(), UserProfile.user2.getPassword());
        //添加时用户测试
        int user1size=user1.getTaskPool().getTasks().size();
        Task task_add=new Task();
        task_add.setTaskName("task_add");
        adminTaskService.postTask(task_add,admin);
        assertEquals(++user1size,user1.getTaskPool().getTasks().size());

        //删除测试,通过模拟正在进行和未进行的任务来测试
        ArrayList<Task> tasks = new ArrayList<>();
        Task task1 = new Task();
        task1.setTaskID(UUID.randomUUID());
        task1.setTaskName("test1");
        task1.setLimit(-1);
        task1.setTaskStatus(TaskStatus.PROCESSING);
        Task task2 = new Task();
        task2.setTaskID(UUID.randomUUID());
        task2.setTaskName("test2");
        task2.setLimit(2020);
        task2.setTaskStatus(TaskStatus.POST);
        tasks.add(task1.copy());
        tasks.add(task2.copy());
        TaskPool taskPool1 = new TaskPool();
        taskPool1.setUserId(user1.getUserID());
        taskPool1.setTasks(tasks);
        user1.setTaskPool(taskPool1);
        TaskPool taskPool2 = new TaskPool();
        taskPool2.setUserId(user2.getUserID());
        taskPool2.setTasks(tasks);
        user2.setTaskPool(taskPool2);

        task1.setTaskStatus(TaskStatus.DELETE);
        task1.setLimit(0);
        task2.setTaskStatus(TaskStatus.DELETE);
        task2.setLimit(0);
        adminTaskService.taskReload(task1);
        adminTaskService.taskReload(task2);
        assertEquals(TaskStatus.PROCESSING,user1.getTaskPool().getTasks().get(0).getTaskStatus());
        assertEquals(0,user1.getTaskPool().getTasks().get(0).getLimit());
        assertEquals(TaskStatus.DELETE,user2.getTaskPool().getTasks().get(1).getTaskStatus());
        assertEquals(0,user2.getTaskPool().getTasks().get(1).getLimit());
    }
}
