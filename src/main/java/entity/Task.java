package entity;

import configuration.TaskStatus;
import configuration.UserProfile;
import lombok.Data;

import java.util.UUID;

@Data
public class Task {
    private UUID taskID;//UUID简易搞一个唯一ID，反正也不用考虑持久化和后期维护的问题
    private int limit;//-1为无限，其他为限制次数
    private String taskName;
    private String taskDescription;
    private int taskStatus;
    private float rewards;

//    public static void main(String[] args) {
//        System.out.println(TaskStatus.ABANDON.getValue());
//        for (UserProfile userProfile:UserProfile.values())
//            System.out.println(userProfile.getAccount());
//    }
}
