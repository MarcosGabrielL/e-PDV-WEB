/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.models;

/**
 *
 * @author Marcos
 */
public class ResponseVendas {
    
    private String total;
    private String percentual;

    public ResponseVendas(String total, String percentual) {
        this.total = total;
        this.percentual = percentual;
    }

    public ResponseVendas() {
        super();
    }
    
    

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPercentual() {
        return percentual;
    }

    public void setPercentual(String percentual) {
        this.percentual = percentual;
    }
    
    
    
}
