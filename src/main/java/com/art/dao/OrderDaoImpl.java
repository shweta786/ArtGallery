/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.art.dao;

import com.art.model.Orders;
import com.art.util.HibernateUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 *
 * @author SHWETA
 */
@Repository
public class OrderDaoImpl implements OrderDao{

    private static Session getSess() {        
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session;
    }
    
    @Override
    public List<Orders> getOrderByUser(int id,int typ) {
        Session session = getSess();
        session.beginTransaction();
        String hql = "from Orders orders where (user_id=:user_id) and (type=:type)";
        Query query = session.createQuery(hql);
        query.setParameter("user_id",id);
        query.setParameter("type", typ);
        List<Orders> orders = query.list();
        session.getTransaction().commit();
        session.close();
        return orders;
    }

    @Override
    public List<Orders> getOrderByPainting(int painting_id) {
        Session session = getSess();
        session.beginTransaction();
        String hql = "from Order order where order.painitng_id=:id";
        Query query = session.createQuery(hql);
        query.setParameter("id",painting_id);
        List<Orders> orders = query.list();
        session.getTransaction().commit();
        session.close();
        return orders;
    }

    @Override
    public Orders getOrderById(int order_id) {
        Session session = getSess();
        session.beginTransaction();
        String hql = "from Order order where order.order_id=:id";
        Query query = session.createQuery(hql);
        query.setParameter("id",order_id);
        Orders order = (Orders)query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return order;
    }

    @Override
    public void addOrder(Orders order) {
        Session session = getSess();                   
        session.beginTransaction();
        session.save(order);         
        session.getTransaction().commit();    
        session.flush();
        session.close();
    }

    @Override
    public void deleteOrder(int order_id, int type) {
        Session session = getSess();
        session.beginTransaction();
        String hql = "delete from Orders orders where order_id= :id";
        Query query = session.createQuery(hql);
        query.setParameter("id",order_id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void confirmOrder(List<Orders> orders) {
        
        for(Orders o:orders) {
            Session session = getSess();
            session.beginTransaction();
            String hql = "update Orders o set type=:type, order_date=:dt where order_id= :id";
            Query query = session.createQuery(hql);
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            String dt = dateFormat.format(date);
            
            query.setParameter("type", 1);
            query.setParameter("dt", dt);
            query.setParameter("id", o.getOrder_id());
            query.executeUpdate();
            session.getTransaction().commit();
            session.close();
            
            session = getSess();
            session.beginTransaction();
            hql = "select popularity from Painting where painting_id= :id";
            query = session.createQuery(hql);
            query.setParameter("id",o.getPainting_id());
            int popular = (int) query.uniqueResult();
            session.getTransaction().commit();
            session.close();
            
            session = getSess();
            session.beginTransaction();
            hql = "update Painting set popularity =:p where painting_id= :id";
            query = session.createQuery(hql);
            query.setParameter("p", popular+1);
            query.setParameter("id",o.getPainting_id());
            query.executeUpdate();
            session.getTransaction().commit();
            session.close();
        }        
        
    }
    
}
