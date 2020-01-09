//package service.serviceImpl;
//
//import configuration.Global;
//import configuration.UserProfile;
//import entity.User;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import service.UserService;
//
//import static org.junit.jupiter.api.Assertions.*;
///**
// * 这是部分单元测试，不是本次作业内容
// */
//class UserServiceImplTest {
//    private UserService userService = new UserServiceImpl();
//    @BeforeEach
//    void setUp() {
//
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    /**
//     * 由于后台存的字符串，通过看是否相等实现登陆，这里的测试只是做一个简单校验
//     */
//    @Test
//    void login() {
//        //用户名或密码为空
//        User user0 = userService.login("", null);
//        assertNull(user0);
//        //用户名或密码错误
//        User user1 = userService.login("hasdj", "jasdfhadk");
//        //用户名密码正确
//        User user = userService.login(UserProfile.user1.getUsername(), UserProfile.user1.getPassword());
//        assertEquals(UserProfile.user1,user.getAuth());
//        assertTrue(Global.userList.contains(user));
//    }
//}
