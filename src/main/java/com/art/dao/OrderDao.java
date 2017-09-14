/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.art.dao;

import com.art.model.Orders;
import java.util.List;

/**
 *
 * @author SHWETA
 */
public interface OrderDao {
    
    public List<Orders> getOrderByUser(int user_id,int type);
    public List<Orders> getOrderByPainting(int painting_id);
    public Orders getOrderById(int order_id);
    public void addOrder(Orders order);
    public void deleteOrder(int order_id, int type);
    public void confirmOrder(List<Orders> orders);
}
