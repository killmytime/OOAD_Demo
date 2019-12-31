package service.serviceImpl;

import configuration.UserProfile;
import entity.User;
import service.UserService;

public class UserServiceImpl implements UserService {
    public User login(String username, String password) {
        User user = new User();
        for (UserProfile userProfile : UserProfile.values()) {
            if (username.equals(userProfile.getUsername()) && password.equals(userProfile.getPassword())) {
                if (userProfile.equals(UserProfile.ADMIN))
                    user.setAuth("ADMIN");
            }
        }
        return null;
    }
}
