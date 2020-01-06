package configuration;

public enum UserProfile {
    ADMIN("0","ADMIN","ADMIN","^passwordisnotADMIN!",0),user1("123","killmytime","27382711","jadk1292d",10000),user2("124","yuyu","563452525","asdfaf134141a",7758258);
    private String userId;
    private String username;
    private String account;
    private String password;
    private float credits;
    UserProfile(String userId,String username,String account,String password,float credits){
        this.userId=userId;
        this.username=username;
        this.account=account;
        this.password=password;
        this.credits=credits;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public float getCredits() {
        return credits;
    }
}
