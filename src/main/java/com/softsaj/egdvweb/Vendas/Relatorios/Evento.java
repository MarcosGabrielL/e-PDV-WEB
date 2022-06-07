/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.Relatorios;

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
@Table(name = "logs")
public class Evento {
    
    /*SEVERE (highest)
        WARNING
        INFO
        CONFIG
        FINE
        FINER
        FINEST*/
    
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
    @Column(nullable = false, unique = false, length = 30)
    private String message;
    @Column(nullable = false, unique = false, length = 30)
    private String info;
    @Column(nullable = false, unique = false, length = 30)
    private String date;
    @Column(nullable = false, unique = false, length = 30)
    private String cod;
    @Column(nullable = false, unique = false, length = 30)
    private String level;
    //Email
     @Column(nullable = false, unique = false, length = 30)
    private String usuario;

    public Evento(Long id, String message, String info, String date, String cod, String level, String usuario) {
        this.id = id;
        this.message = message;
        this.info = info;
        this.date = date;
        this.cod = cod;
        this.level = level;
        this.usuario = usuario;
    }

    

    public Evento() {
        super();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    
    
}
