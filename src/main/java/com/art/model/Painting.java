/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.art.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Comparator;

/**
 *
 * @author SHWETA
 */
@Entity
@Table(name= "painting")
public class Painting implements Serializable{
    
    @Id
    @GeneratedValue
    private int painting_id;
    private int user_id;
    private String painting_add;
    private String thumbnail_add;
    private String name;
    private String type;
    private String price;
    private int popularity;
    private String sze;
    private String dt;
    private int status;
    
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getPainting_id() {
        return painting_id;
    }

    public void setPainting_id(int painting_id) {
        this.painting_id = painting_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPainting_add() {
        return painting_add;
    }

    public void setPainting_add(String painting_add) {
        this.painting_add = painting_add;
    }

    public String getThumbnail_add() {
        return thumbnail_add;
    }

    public void setThumbnail_add(String thumbnail_add) {
        this.thumbnail_add = thumbnail_add;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSze() {
        return sze;
    }

    public void setSze(String sze) {
        this.sze = sze;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }    

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public static Comparator<Painting> PaintingPrice = new Comparator<Painting>() {

        @Override
	public int compare(Painting s1, Painting s2) {

	   int price1 = Integer.parseInt(s1.getPrice());
	   int price2 = Integer.parseInt(s2.getPrice());

	   /*For ascending order*/
	   return price1-price2;

   }};
    
    public static Comparator<Painting> PaintingPopularity = new Comparator<Painting>() {

        @Override
	public int compare(Painting s1, Painting s2) {

	   int popular1 = s1.getPopularity();
	   int popular2 = s2.getPopularity();

	   /*For descending order*/
	   return popular2-popular1;

   }};
    
    public static Comparator<Painting> paintingDate = new Comparator<Painting>() {
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        @Override
	public int compare(Painting s1, Painting s2) {
	   String date1 = s1.getDt();
	   String date2 = s2.getDt();
             try {
                 return f.parse(date1).compareTo(f.parse(date2));
             } catch (ParseException ex) {
                throw new IllegalArgumentException(ex);
             }
    }};
    
}
