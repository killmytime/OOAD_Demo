import configuration.CommodityInfo;
import configuration.Global;
import configuration.UserProfile;
import entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.AdminTaskService;
import service.UserService;
import service.UserTaskService;
import service.serviceImpl.AdminTaskServiceImpl;
import service.serviceImpl.UserServiceImpl;
import service.serviceImpl.UserTaskServiceImpl;

import java.util.Stack;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 期中用例难度级别1第三个
 * 查看当前积分和流水
 */
public class CreditTest {
    User admin,user1;
    AdminTaskService adminTaskService=new AdminTaskServiceImpl();
    UserService userService = new UserServiceImpl();
    UserTaskService userTaskService = new UserTaskServiceImpl();
    @BeforeEach
    void setUp() {
        //这里初始化商品列表从简处理，逻辑可依照发布任务处理
        admin=userService.login(UserProfile.ADMIN.getUsername(),UserProfile.ADMIN.getPassword());
        for (CommodityInfo commodityInfo : CommodityInfo.values()) {
            Commodity commodity = new Commodity(UUID.randomUUID(), commodityInfo.getName(), commodityInfo.getStorage(), commodityInfo.getCredits());
            Global.commodities.add(commodity);
        }
        //发布一个任务作测试
        Task task1 = new Task();
        task1.setTaskName("测试任务");
        task1.setRewards(6);
        task1.setLimit(-1);
        task1 = adminTaskService.postTask(task1, admin);
        assertTrue(Global.AdminPool.getTasks().contains(task1));
        user1 = userService.login(UserProfile.user1.getUsername(), UserProfile.user1.getPassword());
        user1.setCredits(0);
        userTaskService.acceptTask(task1,user1,UserProfile.user1);
        userTaskService.finishTask(task1,user1,UserProfile.user1);
        userService.exchange(user1,Global.commodities.get(1).getCommodityId(),UserProfile.user1);
    }

    @AfterEach
    void tearDown() {

    }
    @Test
    void Transaction(){
        Transaction transaction=user1.getTransaction();
        float total = transaction.getTotal();
        Stack<Flow> flows = transaction.getFlows();
        String userId = transaction.getUserId();
        assertEquals(UserProfile.user1.getUserId(),userId);
        //这里重置用户credit为0，实现比对流水
        assertEquals(0+6-5,total);
        for (Flow flow:flows){
            System.out.println(flow.getFlowId()+"***积分:"+flow.getAmount()+"***来源:"+flow.getCreditOrigin()+"***描述:"+flow.getDescription());
        }
    }
}
