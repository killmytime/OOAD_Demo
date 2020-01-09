package service.serviceImpl;

import configuration.Global;
import configuration.UserProfile;
import entity.*;
import service.UserService;

import java.util.ArrayList;
import java.util.UUID;

import static configuration.Global.AdminPool;
import static configuration.Global.userList;
import static utils.Verification.commodityVerification;

public class UserServiceImpl implements UserService {
    public User login(String username, String password) {
        User user = new User();
        for (UserProfile userProfile : UserProfile.values()) {
            if (username.equals(userProfile.getUsername()) && password.equals(userProfile.getPassword())) {
                user.setUsername(userProfile.getUsername());
                user.setAccount(userProfile.getAccount());
                user.setCredits(userProfile.getCredits());
                user.setPassword(userProfile.getPassword());
                user.setUserId(userProfile.getUserId());
                Transaction transaction=new Transaction();
                transaction.setUserId(user.getUserId());
                //这里记账由于持久化问题，没有之前记录，所以将总额设置为0，在测试该功能时同时将用户总额也设置为0
                user.setTransaction(transaction);
                //因为用户信息也没有持久化，所以不需要考虑初次登陆的任务加载了
                TaskPool taskPool = new TaskPool();
                taskPool.setUserId(user.getUserId());
                ArrayList<Task> tasks = new ArrayList<>();
                for (Task task : AdminPool.getTasks())
                    tasks.add(task.copy());
                taskPool.setTasks(tasks);
                user.setTaskPool(taskPool);
                user.setAuth(userProfile);
                userList.add(user);
                return user;
            }
        }
        return null;
    }

    public boolean exchange(User user, UUID commodityId, UserProfile auth) {
        for (Commodity commodity : Global.commodities) {
            if (commodity.getCommodityId().equals(commodityId)) {
                if (commodityVerification(commodity, user, auth))
                    return new CreditServiceImpl().exchangeCalculation(commodity, user);
                return false;
            }
        }
        return false;
    }
}
