/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.security.PasswordReset;

import com.softsaj.egdvweb.security.User;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author Marcos
 */
@Entity
public class PasswordResetToken {
 
   

    @Id
    @Column(length = 45)
    private String id;
    
    @Column(name = "token", nullable = true, unique = true, length = 200)
    private String resetPasswordToken;

    public PasswordResetToken() {
        super();
    }

    public PasswordResetToken(String id, String resetPasswordToken) {
        this.id = id;
        this.resetPasswordToken = resetPasswordToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

   
}
