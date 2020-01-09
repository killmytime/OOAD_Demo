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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * 期中难度二第一个用例
 * 进阶的任务发布，删除可见单元测试部分
 */
public class AdvancedTaskTest {
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
        int size = Global.AdminPool.getTasks().size();
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
}
