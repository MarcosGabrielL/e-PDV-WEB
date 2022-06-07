/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Marcos
 */
@Entity
public class Frete {
    
     @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
     
   private String fretefixo; 
   private String frete10k;
   private boolean cobrafrete;
   private String vendedorid;

    public Frete() {
        super();
    }

    public Frete(Long id, String fretefixo, String frete10k, boolean cobrafrete, String vendedorid) {
        this.id = id;
        this.fretefixo = fretefixo;
        this.frete10k = frete10k;
        this.cobrafrete = cobrafrete;
        this.vendedorid = vendedorid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFretefixo() {
        return fretefixo;
    }

    public void setFretefixo(String fretefixo) {
        this.fretefixo = fretefixo;
    }

    public String getFrete10k() {
        return frete10k;
    }

    public void setFrete10k(String frete10k) {
        this.frete10k = frete10k;
    }

    public boolean isCobrafrete() {
        return cobrafrete;
    }

    public void setCobrafrete(boolean cobrafrete) {
        this.cobrafrete = cobrafrete;
    }

    public String getVendedorid() {
        return vendedorid;
    }

    public void setVendedorid(String vendedorid) {
        this.vendedorid = vendedorid;
    }
   
   
   
   
    
    
}
