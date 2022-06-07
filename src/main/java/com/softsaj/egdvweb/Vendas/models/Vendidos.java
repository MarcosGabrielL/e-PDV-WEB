/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Marcos
 */
@Entity
@Table(name = "produtos_vendidos")
public class Vendidos {
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     
     @Column(nullable = false, unique = false, length = 20)
    private String Vendedor_ID;
     
    @Column(nullable = false, unique = false, length = 30)
    private String codigo;
    @Column(nullable = false, unique = false, length = 300)
    private String descrição;
    @Column(nullable = false, unique = false, length = 100)
    private String Tipo;
    @Column(nullable = true, unique = false, length = 30)
    private String Loja;
    private String Caixa;
    @Column(nullable = false, unique = false, length = 30)
    private String datasaida;
    @Column(nullable = false, unique = false, length = 10)
    private int IdVenda;
    @Column(nullable = false, unique = false, length = 10)
    private float quantidade;

    public Vendidos() {
        super();
    }

    public Vendidos(Long id, String Vendedor_ID, String codigo, String descrição, String Tipo, String Loja, String Caixa, String datasaida, int IdVenda, float quantidade) {
        this.id = id;
        this.Vendedor_ID = Vendedor_ID;
        this.codigo = codigo;
        this.descrição = descrição;
        this.Tipo = Tipo;
        this.Loja = Loja;
        this.Caixa = Caixa;
        this.datasaida = datasaida;
        this.IdVenda = IdVenda;
        this.quantidade = quantidade;
    }

    public String getVendedor_ID() {
        return Vendedor_ID;
    }

    public void setVendedor_ID(String Vendedor_ID) {
        this.Vendedor_ID = Vendedor_ID;
    }

   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescrição() {
        return descrição;
    }

    public void setDescrição(String descrição) {
        this.descrição = descrição;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public String getLoja() {
        return Loja;
    }

    public void setLoja(String Loja) {
        this.Loja = Loja;
    }

    public String getCaixa() {
        return Caixa;
    }

    public void setCaixa(String Caixa) {
        this.Caixa = Caixa;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
    }


    public String getDataSaida() {
        return datasaida;
    }

    public void setDataSaida(String datasaida) {
        this.datasaida = datasaida;
    }
   
    public int getIdVenda() {
        return IdVenda;
    }

    public void setIdVenda(int IdVenda) {
        this.IdVenda = IdVenda;
    }

    
    

}
