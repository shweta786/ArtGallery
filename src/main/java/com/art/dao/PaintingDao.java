/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.art.dao;

import com.art.model.Painting;
import java.util.List;

/**
 *
 * @author SHWETA
 */
public interface PaintingDao {
    
    public List<Painting> getPainting();
    public List<Painting> getPaintingByUser(int user_id);
    public List<Painting> getPaintingByType(String type);
    public Painting getPaintingByNameAndId(String name,int user_id);
    public Painting getPaintingById(int painting_id);
    public void addPainting(Painting painting);
    public void deletePainting(int Painting_id);
    public void changePopularity(int painting_id);
    public List<Painting> getPaintingByPage(int page);
}
