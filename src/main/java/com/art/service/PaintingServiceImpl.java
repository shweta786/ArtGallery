/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.art.service;

import com.art.dao.PaintingDao;
import com.art.model.Painting;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author SHWETA
 */
@Service
public class PaintingServiceImpl implements PaintingService{

    @Autowired
    PaintingDao paintingDao;
    
    @Override
    public List<Painting> getPainting() {
        return paintingDao.getPainting();
    }

    @Override
    public List<Painting> getPaintingByUser(int user_id) {
        return paintingDao.getPaintingByUser(user_id);
    }

    @Override
    public Painting getPaintingByNameAndId(String name, int user_id) {
        return paintingDao.getPaintingByNameAndId(name, user_id);
    }

    @Override
    public Painting getPaintingById(int painting_id) {
        return paintingDao.getPaintingById(painting_id);
    }

    @Override
    public void addPainting(Painting painting) {
        paintingDao.addPainting(painting);
    }

    @Override
    public void deletePainting(int Painting_id) {
        paintingDao.deletePainting(Painting_id);
    }

    @Override
    public List<Painting> getPaintingByType(String type) {
        return paintingDao.getPaintingByType(type);
    }

    @Override
    public void changePopularity(int painting_id) {
        paintingDao.changePopularity(painting_id);
    }

    @Override
    public List<Painting> getPaintingByPage(int page) {
        return paintingDao.getPaintingByPage(page);
    }

    @Override
    public void refreshAllProducts() {
        paintingDao.refreshAllProducts();
    }
    
}
