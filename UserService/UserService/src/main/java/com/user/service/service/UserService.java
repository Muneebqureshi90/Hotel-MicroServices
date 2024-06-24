package com.user.service.service;

import com.user.service.entity.User;
import java.util.List;

public interface UserService {

    User createUser(User user);
    List<User> getAllUsers();
    User getUserById(String userId);
    User updateUser(String userId, User user);
    void deleteUser(String userId);
}
