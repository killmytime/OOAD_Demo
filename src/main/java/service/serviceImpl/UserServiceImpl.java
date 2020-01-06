package service.serviceImpl;
import configuration.UserProfile;
import entity.Task;
import entity.TaskPool;
import entity.User;
import service.UserService;

import java.util.ArrayList;

import static configuration.Global.AdminPool;
import static configuration.Global.userList;

public class UserServiceImpl implements UserService {
    public User login(String username, String password) {
        User user = new User();
        for (UserProfile userProfile : UserProfile.values()) {
            if (username.equals(userProfile.getUsername()) && password.equals(userProfile.getPassword())) {
                user.setUsername(userProfile.getUsername());
                user.setAccount(userProfile.getAccount());
                user.setCredits(userProfile.getCredits());
                user.setPassword(userProfile.getPassword());
                user.setUserID(userProfile.getUserId());
                //因为用户信息也没有持久化，所以不需要考虑初次登陆的任务加载了
                TaskPool taskPool=new TaskPool();
                taskPool.setUserId(user.getUserID());
                ArrayList<Task> tasks = new ArrayList<>();
                for (Task task:AdminPool.getTasks())
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
}
