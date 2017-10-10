/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.art.dao;

import com.art.model.Painting;
import com.art.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 *
 * @author SHWETA
 */
@Repository
public class PaintingDaoImpl implements PaintingDao{

    private static Session getSess() {        
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session;
    }
    
    @Override
    @Cacheable("products")
    public List<Painting> getPainting() {
    
        Session session = getSess();
        session.beginTransaction();
        String hql = "from Painting where status =:st";
        Query query = session.createQuery(hql);
        query.setParameter("st", 0);
        List<Painting> paintings = query.list();
        session.getTransaction().commit();
        session.flush();
        session.close();
        return paintings;
    }
    
    @Override
    @CacheEvict(value = "products", allEntries = true)
    public void refreshAllProducts() {
        //This method will remove all 'paintings' from cache, say as a result of flush-all API.
    }

    @Override
    public List<Painting> getPaintingByType(String type) {
        Session session = getSess();
        session.beginTransaction();
        String hql = "from Painting where type =:type and status =:st";
        Query query = session.createQuery(hql);
        query.setParameter("type", type);
        query.setParameter("st", 0);
        List<Painting> paintings = query.list();
        session.getTransaction().commit();
        session.flush();
        session.close();
        return paintings;
    }
    
    @Override
    public List<Painting> getPaintingByUser(int user_id) {
        Session session = getSess();
        session.beginTransaction();
        String hql = "from Painting painting where (user_id=:id) and status=:st";
        Query query = session.createQuery(hql);
        query.setParameter("id", user_id);
        query.setParameter("st", 0);
        List<Painting> paintings = query.list();
        session.getTransaction().commit();
        session.flush();
        session.close();
        return paintings;
    }

    @Override
    public Painting getPaintingByNameAndId(String name, int user_id) { return null;
    }

    @Override
    public Painting getPaintingById(int painting_id) {
        Session session = getSess();
        session.beginTransaction();
        String hql = "from Painting painting where (painting_id=:id)";
        Query query = session.createQuery(hql);
        query.setParameter("id", painting_id);
        //query.setParameter("st", 0);
        session.flush();
        Painting p = (Painting)query.uniqueResult();
        session.close();
        return p;
    }

    @Override
    public void addPainting(Painting painting) {
        Session session = getSess();                   
        session.beginTransaction();
        session.save(painting);         
        session.getTransaction().commit();    
        session.flush();
        session.close();
    }

    @Override
    public void deletePainting(int painting_id) {
        Session session = getSess();
        session.beginTransaction();
        String hql = "update Painting set status=:st where painting_id= :id";
        Query query = session.createQuery(hql);
        query.setParameter("st",1);
        query.setParameter("id",painting_id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.flush();
        session.close();
    }

    @Override
    public void changePopularity(int painting_id) {
        Session session = getSess();
        session.beginTransaction();
        String hql = "select popularity from Painting where painting_id= :id";
        Query query = session.createQuery(hql);
        query.setParameter("id",painting_id);
        int p = (int) query.uniqueResult();
        session.getTransaction().commit();
        session.flush();
        session.close();
        
        session = getSess();
        session.beginTransaction();
        hql = "update Painting set popularity=:pl where painting_id= :id";
        query = session.createQuery(hql);
        query.setParameter("pl", p+1);
        query.setParameter("id",painting_id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.flush();
        session.close();
    }

    @Override
    public List<Painting> getPaintingByPage(int page) {
        int pageSize = 6;
        Session ss =getSess();
        ss.beginTransaction();
        Query query = ss.createQuery("from Painting");
        query = query.setFirstResult(pageSize * (page - 1));
        query.setMaxResults(pageSize);
        List<Painting> paintings = query.list();
        ss.getTransaction().commit();
        ss.flush();
        ss.close();
        return paintings;
    }
    
}
