
package com.softsaj.egdvweb.models;

import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Marcos
 */
public class Notification {

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
	 @Column(nullable = false, unique = false, length = 30)
	private boolean isRead;
       

    public Notification() {
        super();
    }

    public Notification(Long id, String message, String info, String date, String cod, String level, String usuario, boolean isRead) {
        this.id = id;
        this.message = message;
        this.info = info;
        this.date = date;
        this.cod = cod;
        this.level = level;
        this.usuario = usuario;
        this.isRead = isRead;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

   
    
    }
        

