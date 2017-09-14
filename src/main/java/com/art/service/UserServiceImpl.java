/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.art.service;

import com.art.dao.UserDao;
import com.art.model.Usr;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author SHWETA
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;

    @Override
    public List<Usr> getUser() {
        return userDao.getUser();
    }

    @Override
    public void addUser(Usr user) {
        userDao.addUser(user);
    }

    @Override
    public Usr findUser(String email, String password) {
        return userDao.findUser(email,password);
    }

    @Override
    public Usr getUserById(int user_id) {
        return userDao.getUserById(user_id);
    }

    @Override
    public Usr editArtist(int user_id,String description, String contact) {
        return userDao.editArtist(user_id,description,contact);
    }

    @Override
    public Usr editPic(int user_id, String picToSave) {
        return userDao.editPic(user_id,picToSave);
    }
    
}
