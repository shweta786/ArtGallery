/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.art.service;

import com.art.dao.OrderDao;
import com.art.model.Orders;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author SHWETA
 */
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderDao orderDao;
    
    @Override
    public List<Orders> getOrderByUser(int user_id,int type) {
        return orderDao.getOrderByUser(user_id,type);
    }

    @Override
    public List<Orders> getOrderByPainting(int painting_id) {
        return orderDao.getOrderByPainting(painting_id);
    }

    @Override
    public Orders getOrderById(int order_id) {
        return orderDao.getOrderById(order_id);
    }

    @Override
    public void addOrder(Orders order) {
        orderDao.addOrder(order);
    }

    @Override
    public void deleteOrder(int order_id,int type) {
        orderDao.deleteOrder(order_id,type);
    }

    @Override
    public void confirmOrder(List<Orders> orders) {
        orderDao.confirmOrder(orders);
    }
    
}
