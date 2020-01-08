package service;

import configuration.UserProfile;
import entity.User;

import java.util.UUID;

public interface UserService {
    User login(String username,String password);
    boolean exchange(User user, UUID commodityId, UserProfile auth);
}
