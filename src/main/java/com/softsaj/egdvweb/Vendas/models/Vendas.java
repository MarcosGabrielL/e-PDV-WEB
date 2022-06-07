/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.models;

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
@Table(name = "vendas")
public class Vendas {
    
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
     
   private String diavenda; 
   private int idvendas;
   private String caixa;
   private String loja;
   @Column(nullable = false, unique = false, length = 30)
   private String datavenda;
   private String datacancelamento;
   @Column(nullable = false, unique = false, length = 20)
   private String valor;
   @Column(nullable = false, unique = false)
   private float recebido1; 
   private float recebido2; 
   private float recebido3; 
   private float troco;
   @Column(nullable = false, unique = false, length = 20)
   private String modopagamento1;
   private String modopagamento2;
   private String modopagamento3;
   
   @Column(nullable = false, unique = false, length = 20)
    private String vendedor_id;
   
   @Column(nullable = false, unique = false, length = 20)
    private String comprador_id;
   
   
   /*1-Carrinho
    0 - pedido
    2-Pago
    3-Pronto
    4-Despachado
    5-Em Caminho
    6-Entregue
    7-Cancelado
    8-Extraviado
    9-Danificado*/
   @Column(nullable = false, unique = false, length = 20)
    private String Status;

    public Vendas() {
        super();
    }

    public Vendas(Long id, String diavenda, int idvendas, String caixa, String loja, String datavenda, String datacancelamento, String valor, float recebido1, float recebido2, float recebido3, float troco, String modopagamento1, String modopagamento2, String modopagamento3, String vendedor_id, String comprador_id, String Status) {
        this.id = id;
        this.diavenda = diavenda;
        this.idvendas = idvendas;
        this.caixa = caixa;
        this.loja = loja;
        this.datavenda = datavenda;
        this.datacancelamento = datacancelamento;
        this.valor = valor;
        this.recebido1 = recebido1;
        this.recebido2 = recebido2;
        this.recebido3 = recebido3;
        this.troco = troco;
        this.modopagamento1 = modopagamento1;
        this.modopagamento2 = modopagamento2;
        this.modopagamento3 = modopagamento3;
        this.vendedor_id = vendedor_id;
        this.comprador_id = comprador_id;
        this.Status = Status;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiavenda() {
        return diavenda;
    }

    public void setDiavenda(String diavenda) {
        this.diavenda = diavenda;
    }

    public int getIdvendas() {
        return idvendas;
    }

    public void setIdvendas(int idvendas) {
        this.idvendas = idvendas;
    }

    public String getCaixa() {
        return caixa;
    }

    public void setCaixa(String caixa) {
        this.caixa = caixa;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public String getDatavenda() {
        return datavenda;
    }

    public void setDatavenda(String datavenda) {
        this.datavenda = datavenda;
    }

    public String getDatacancelamento() {
        return datacancelamento;
    }

    public void setDatacancelamento(String datacancelamento) {
        this.datacancelamento = datacancelamento;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public float getRecebido1() {
        return recebido1;
    }

    public void setRecebido1(float recebido1) {
        this.recebido1 = recebido1;
    }

    public float getRecebido2() {
        return recebido2;
    }

    public void setRecebido2(float recebido2) {
        this.recebido2 = recebido2;
    }

    public float getRecebido3() {
        return recebido3;
    }

    public void setRecebido3(float recebido3) {
        this.recebido3 = recebido3;
    }

    public float getTroco() {
        return troco;
    }

    public void setTroco(float troco) {
        this.troco = troco;
    }

    public String getModopagamento1() {
        return modopagamento1;
    }

    public void setModopagamento1(String modopagamento1) {
        this.modopagamento1 = modopagamento1;
    }

    public String getModopagamento2() {
        return modopagamento2;
    }

    public void setModopagamento2(String modopagamento2) {
        this.modopagamento2 = modopagamento2;
    }

    public String getModopagamento3() {
        return modopagamento3;
    }

    public void setModopagamento3(String modopagamento3) {
        this.modopagamento3 = modopagamento3;
    }

    public String getVendedor_id() {
        return vendedor_id;
    }

    public void setVendedor_id(String vendedor_id) {
        this.vendedor_id = vendedor_id;
    }

    public String getComprador_id() {
        return comprador_id;
    }

    public void setComprador_id(String comprador_id) {
        this.comprador_id = comprador_id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

   

  

    
}