package entity;

import configuration.UserProfile;
public class User {
    private String userId;//唯一Id从简取个固定的序列好了并且不提供注册功能
    private String account;
    private String password;
    private String username;
    private int dailyLimit=1998;
    private float credits;
    private UserProfile Auth;
    private TaskPool taskPool=new TaskPool();
    private Transaction transaction;

    public void taskAccepted(){
        this.dailyLimit--;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(int dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public float getCredits() {
        return credits;
    }

    public void setCredits(float credits) {
        this.credits = credits;
    }

    public UserProfile getAuth() {
        return Auth;
    }

    public void setAuth(UserProfile auth) {
        Auth = auth;
    }

    public TaskPool getTaskPool() {
        return taskPool;
    }

    public void setTaskPool(TaskPool taskPool) {
        this.taskPool = taskPool;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
