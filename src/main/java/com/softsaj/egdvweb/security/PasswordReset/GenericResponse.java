/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.security.PasswordReset;

/**
 *
 * @author Marcos
 */
public class GenericResponse {
    private String message;
    private String error;
 
    public GenericResponse(String message) {
        super();
        this.message = message;
    }
 
    public GenericResponse(String message, String error) {
        super();
        this.message = message;
        this.error = error;
    }
}
