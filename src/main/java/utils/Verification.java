package utils;

import configuration.TaskStatus;
import configuration.UserProfile;
import entity.Commodity;
import entity.Task;
import entity.User;

import java.util.Date;

public class Verification {
    //管理员身份验证
    public static boolean adminVerification(User user) {
        //用户不为空，用户权限为管理员
        return user != null && user.getAuth() != null && user.getAuth().equals(UserProfile.ADMIN);
    }

    public static boolean userVerification(User user, UserProfile auth) {
        //用户不为空，用户权限为当前用户（只是证明是登记的用户
        return user != null && user.getAuth() != null && user.getAuth() == auth;
    }

    public static boolean taskVerification(Task task, TaskStatus taskStatus) {
        //task校验，task名字不为空，task剩余次数符合预期，task未过期，task状态符合预期
        return task != null && !task.getTaskName().equals("") && task.getLimit() >= -1 && (task.getExpired() == null || task.getExpired().after(new Date())) && task.getTaskStatus().equals(taskStatus);
    }

    public static boolean commodityVerification(Commodity commodity, User user, UserProfile auth) {
        //商品不为空，商品库存大于0，用户身份验证，用户积分大于商品单价（默认兑换单个）
        return commodity != null && commodity.getStorage() > 0 && userVerification(user, auth) && user.getCredits() >= commodity.getCredits();
    }
}
