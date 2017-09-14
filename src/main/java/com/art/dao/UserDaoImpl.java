/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.art.dao;

import com.art.model.Usr;
import com.art.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 *
 * @author SHWETA
 */
@Repository
public class UserDaoImpl implements UserDao {

    private static Session getSess() {        
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session;
    }
    
    @Override
    public List<Usr> getUser() {
        Session session = getSess();
        session.beginTransaction();
        String hql = "from Usr usr where (type=:type)";
        Query query = session.createQuery(hql);
        query.setParameter("type", 1);
        List<Usr> usr = query.list();
        session.getTransaction().commit();
        return usr;
    }

    @Override
    public void addUser(Usr usr) {
        Session session = getSess();                   //getting session for transaction
        session.beginTransaction();
        session.save(usr);
        session.getTransaction().commit();           //commiting transaction
        session.flush();
        session.close();
    }

    @Override
    public Usr findUser(String email, String password) {
        Session session = getSess();                   //getting session for transaction
        session.beginTransaction();
        String hql = "from Usr usr where (email=:email)";
        Query query = session.createQuery(hql);
        query.setParameter("email", email);
        Usr usr = (Usr)query.uniqueResult();
        session.getTransaction().commit();
        return usr;
    }

    @Override
    public Usr getUserById(int user_id) {
        Session session = getSess();                   
        session.beginTransaction();
        String hql = "from Usr usr where user_id =:id";
        Query query = session.createQuery(hql);
        query.setParameter("id", user_id);
        Usr usr = (Usr) query.uniqueResult();
        session.getTransaction().commit();
        return usr;
    }

    @Override
    public Usr editArtist(int user_id, String description, String contact) {
        Session session = getSess();                   //getting session for transaction
        session.beginTransaction();
        String hql = "update Usr usr set contact=:con, description=:des where user_id=:id";
        Query query = session.createQuery(hql);
        query.setParameter("des", description);
        query.setParameter("con", contact);
        query.setParameter("id", user_id);
        query.executeUpdate();
        session.getTransaction().commit();
        return getUserById(user_id);
    }

    @Override
    public Usr editPic(int user_id, String picToSave) {
        Session session = getSess();                   //getting session for transaction
        session.beginTransaction();
        String hql = "update Usr usr set pic=:picToSave where user_id=:id";
        Query query = session.createQuery(hql);
        query.setParameter("picToSave", picToSave);
        query.setParameter("id", user_id);
        query.executeUpdate();
        session.getTransaction().commit();
        return getUserById(user_id);
    }
    
}
