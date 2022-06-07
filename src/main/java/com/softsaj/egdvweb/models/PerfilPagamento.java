/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.models;

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
@Table(name = "perfis")
public class PerfilPagamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     
    @Column(nullable = false, unique = true, length = 45)
    private String email;
     
    @Column(nullable = true, length = 64)
    private String password;
     
    @Column(name = "first_name", nullable = true, length = 20)
    private String firstName;
     
    @Column(name = "last_name", nullable = true, length = 64)
    private String lastName;
    
     private String banco;
      private String   tipoconta;
      private String   numeroconta;
      private String   agencia;
      private String   nomecompleto;
       private String  cpf;
       private String  numeroCartao1;
       private String  numeroCartao2;
       private String  numeroCartao3;
       private String  numeroCartao4;
       private String  titular;
        private String mesvencimento;
        private String anovencimento;
        private String cvv;

    public PerfilPagamento() {
        super();
    }

    public PerfilPagamento(Long id, String email, String password, String firstName, String lastName, String banco, String tipoconta, String numeroconta, String agencia, String nomecompleto, String cpf, String numeroCartao1, String numeroCartao2, String numeroCartao3, String numeroCartao4, String titular, String mesvencimento, String anovencimento, String cvv) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.banco = banco;
        this.tipoconta = tipoconta;
        this.numeroconta = numeroconta;
        this.agencia = agencia;
        this.nomecompleto = nomecompleto;
        this.cpf = cpf;
        this.numeroCartao1 = numeroCartao1;
        this.numeroCartao2 = numeroCartao2;
        this.numeroCartao3 = numeroCartao3;
        this.numeroCartao4 = numeroCartao4;
        this.titular = titular;
        this.mesvencimento = mesvencimento;
        this.anovencimento = anovencimento;
        this.cvv = cvv;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getTipoconta() {
        return tipoconta;
    }

    public void setTipoconta(String tipoconta) {
        this.tipoconta = tipoconta;
    }

    public String getNumeroconta() {
        return numeroconta;
    }

    public void setNumeroconta(String numeroconta) {
        this.numeroconta = numeroconta;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNomecompleto() {
        return nomecompleto;
    }

    public void setNomecompleto(String nomecompleto) {
        this.nomecompleto = nomecompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNumeroCartao1() {
        return numeroCartao1;
    }

    public void setNumeroCartao1(String numeroCartao1) {
        this.numeroCartao1 = numeroCartao1;
    }

    public String getNumeroCartao2() {
        return numeroCartao2;
    }

    public void setNumeroCartao2(String numeroCartao2) {
        this.numeroCartao2 = numeroCartao2;
    }

    public String getNumeroCartao3() {
        return numeroCartao3;
    }

    public void setNumeroCartao3(String numeroCartao3) {
        this.numeroCartao3 = numeroCartao3;
    }

    public String getNumeroCartao4() {
        return numeroCartao4;
    }

    public void setNumeroCartao4(String numeroCartao4) {
        this.numeroCartao4 = numeroCartao4;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getMesvencimento() {
        return mesvencimento;
    }

    public void setMesvencimento(String mesvencimento) {
        this.mesvencimento = mesvencimento;
    }

    public String getAnovencimento() {
        return anovencimento;
    }

    public void setAnovencimento(String anovencimento) {
        this.anovencimento = anovencimento;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
        
        
        
    
}
