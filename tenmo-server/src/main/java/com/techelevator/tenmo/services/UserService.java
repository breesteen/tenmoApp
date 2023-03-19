package com.techelevator.tenmo.services;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    public int findIdByUsername(String username){return  userDao.findIdByUsername(username);}

    public List<User> findOtherUsers(Integer excludedId, String userName) {return userDao.findOtherUsers(excludedId);}

    public List<User> findAll(){return userDao.findAll();}
}
