package service.serviceImpl;

import configuration.CreditOrigin;
import configuration.Global;
import configuration.UserProfile;
import entity.*;
import service.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;
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
                user.setTransaction(new Transaction());
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
                if (commodityVerification(commodity, user, auth) && commodity.exchangeCommodity())
                    return new CreditServiceImpl().exchangeCalculation(commodity, user);
                return false;
            }
            return false;
        }
        return false;
    }
}
