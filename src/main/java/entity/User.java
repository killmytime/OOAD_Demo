package entity;

import configuration.UserProfile;
import lombok.Data;

@Data
public class User {
    private String userID;//唯一Id从简取个固定的序列好了并且不提供注册功能
    private String account;
    private String password;
    private String username;
    private int dailyLimit=10;
    private float credits;
    private UserProfile Auth;
    private TaskPool taskPool=new TaskPool();//ToDo 用户的任务池，全局发布或删除的任务会更新到每一个用户（全局操作稍微注意一下
    //Todo 添加流水，流水ID可以考虑字符串拼接+自增
    public void TaskAccepted(){
        this.dailyLimit--;
    }
}
