/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Marcos
 */
@Entity
public class Dominio {
    
     @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
     
     private String vendedor;
     private String urlfree;
     private String urlpersonal;
     private String appurl;

    public Dominio() {
        super();
    }

    public Dominio(Long id, String vendedor, String urlfree, String urlpersonal, String appurl) {
        this.id = id;
        this.vendedor = vendedor;
        this.urlfree = urlfree;
        this.urlpersonal = urlpersonal;
        this.appurl = appurl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getUrlfree() {
        return urlfree;
    }

    public void setUrlfree(String urlfree) {
        this.urlfree = urlfree;
    }

    public String getUrlpersonal() {
        return urlpersonal;
    }

    public void setUrlpersonal(String urlpersonal) {
        this.urlpersonal = urlpersonal;
    }

    public String getAppurl() {
        return appurl;
    }

    public void setAppurl(String appurl) {
        this.appurl = appurl;
    }
    
    
     
     
}
