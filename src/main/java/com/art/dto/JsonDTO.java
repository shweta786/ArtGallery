/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.art.dto;

import com.art.model.Orders;
import com.art.model.Painting;
import com.art.model.Usr;
import java.util.List;

/**
 *
 * @author SHWETA
 */
public class JsonDTO {
    
    String status;
    List<Painting> paintings;
    List<String> names;
    List<Orders> orders;
    Usr usr;
    List<Usr> usrs;
    String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Painting> getPaintings() {
        return paintings;
    }

    public void setPaintings(List<Painting> paintings) {
        this.paintings = paintings;
    }
    
    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public Usr getUsr() {
        return usr;
    }

    public void setUsr(Usr usr) {
        this.usr = usr;
    }
    
    public List<Usr> getUsrs() {
        return usrs;
    }

    public void setUsrs(List<Usr> usrs) {
        this.usrs = usrs;
    }
    
    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    
    
}
