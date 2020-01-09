import configuration.CommodityInfo;
import configuration.Global;
import configuration.UserProfile;
import entity.Commodity;
import entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UserService;
import service.serviceImpl.UserServiceImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 期中难度二第二个用例
 * 商品兑换
 */
public class ExchangeTest {
    User user1;
    UserService userService = new UserServiceImpl();

    @BeforeEach
    void setUp() {
        //这里初始化商品列表从简处理，逻辑可依照发布任务处理
        //User admin=userService.login(UserProfile.ADMIN.getUsername(),UserProfile.ADMIN.getPassword());
        for (CommodityInfo commodityInfo : CommodityInfo.values()) {
            Commodity commodity = new Commodity(UUID.randomUUID(), commodityInfo.getName(), commodityInfo.getStorage(), commodityInfo.getCredits());
            Global.commodities.add(commodity);
        }
        user1 = userService.login(UserProfile.user1.getUsername(), UserProfile.user1.getPassword());
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void exchangeTest() {
        float credits = user1.getCredits();
        int storage0 = 0;
        assertEquals(UserProfile.user1.getCredits(), credits);
        //无权限操作，以user1使用user2权限为例
        storage0 = Global.commodities.get(0).getStorage();
        assertFalse(userService.exchange(user1, Global.commodities.get(0).getCommodityId(), UserProfile.user2));
        assertEquals(credits, user1.getCredits());
        assertEquals(storage0, Global.commodities.get(0).getStorage());
        //商品不存在
        assertFalse(userService.exchange(user1, UUID.randomUUID(), UserProfile.user1));
        assertEquals(credits, user1.getCredits());
        //商品库存不足,链表第三个Bag设置库存为0应当无法兑换
        assertEquals(0, Global.commodities.get(2).getStorage());
        assertFalse(userService.exchange(user1, Global.commodities.get(2).getCommodityId(), UserProfile.user1));
        assertEquals(credits, user1.getCredits());
        //用户积分不足,不安全的快速实现，手动更改用户的积分值
        credits = 9;
        user1.setCredits(9);
        assertFalse(userService.exchange(user1, Global.commodities.get(0).getCommodityId(), UserProfile.user1));
        assertEquals(credits, user1.getCredits());
        assertEquals(storage0,Global.commodities.get(0).getStorage());
        //成功兑换,第二个积分为5
        int storage1 = Global.commodities.get(1).getStorage();
        System.out.println(Global.commodities.get(1).getCredits());
        assertTrue(userService.exchange(user1,Global.commodities.get(1).getCommodityId(),UserProfile.user1));
        assertEquals(credits - Global.commodities.get(1).getCredits(),user1.getCredits());
        credits=credits-Global.commodities.get(1).getCredits();
        assertEquals(storage1-1,Global.commodities.get(1).getStorage());
        storage1=storage1-1;
    }

}
