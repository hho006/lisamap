package com.openshift.springmvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.openshift.springmvc.dao.UserDao;
import com.openshift.springmvc.model.User;

@Service
public class UserManager {
    
    private final UserDao dao = new UserDao();
    
    public void createUser(final User user) {
        dao.save(user);
    }
    
    public void deleteUser(final User user) {
        dao.delete(user);
    }
    
    public void updateUser(final User user) {
        dao.update(user);
    }
    
    /**
     * Authenticate user. Return null if authentication fails.
     * @param username
     * @param password
     * @return authenticated user
     */
    public final User authenticateUser(final String username, final String password) {
        final Map<String, Object> critMap = new HashMap<>();
        critMap.put("username", username);
        critMap.put("password", password);
        final List<User> userList = dao.getByCriteria(critMap);
        if(userList != null && userList.size() == 1) {
            return userList.get(0);
        } else {
            return null;
        }
    }
    
    public final User getUserByUsernameEmail(final String username, final String email) {
        final Map<String, Object> critMap = new HashMap<>();
        critMap.put("username", username);
        critMap.put("email", email);
        final List<User> userList = dao.getByCriteria(critMap);
        if(userList != null && userList.size() == 1) {
            return userList.get(0);
        } else {
            return null;
        }
    }
    
    public final User getUserById(final int userId) {
        return dao.getById(userId);
    }
}
