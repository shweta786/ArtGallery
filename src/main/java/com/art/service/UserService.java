/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.art.service;

import com.art.model.Usr;
import java.util.List;

/**
 *
 * @author SHWETA
 */
public interface UserService {
    public List<Usr> getUser();
    public void addUser(Usr user);
    public Usr findUser(String email, String password);
    public Usr getUserById(int user_id);
    public Usr editArtist(int user_id,String description, String contact);
    public Usr editPic(int user_id,String picToSave);
    
}
