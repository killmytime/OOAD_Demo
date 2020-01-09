import configuration.Global;
import configuration.UserProfile;
import entity.DailyTask;
import entity.Task;
import entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.AdminTaskService;
import service.UserService;
import service.serviceImpl.AdminTaskServiceImpl;
import service.serviceImpl.UserServiceImpl;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * 期中用例难度级别一第一个
 *  运营方在系统中发布一个任务。第一个难度级别只需要考虑任务的类型是不限次数的
 */
public class PostTest {
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

    @Test
    void postTask() {
        //缺失任务名字的为null
        Task task0 = new Task();
        assertNull(adminTaskService.postTask(task0, admin));
        //缺失其他信息的取默认值任务次数为0
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
        task3.setLimit(-1);
        task3 = adminTaskService.postTask(task3, admin);
        assertEquals(++size, Global.AdminPool.getTasks().size());
        assertEquals(-1, task3.getLimit());
    }
}
