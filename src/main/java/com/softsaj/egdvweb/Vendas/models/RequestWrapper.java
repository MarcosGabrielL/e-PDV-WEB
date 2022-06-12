/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.models;

import java.util.List;

/**
 *
 * @author Marcos
 */
public class RequestWrapper {

    private List<Produto> produtos;
    private Vendas vendas;

    public RequestWrapper(List<Produto> produtos, Vendas vendas) {
        this.produtos = produtos;
        this.vendas = vendas;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public RequestWrapper() {
        super();
    }
    
    

    
}
