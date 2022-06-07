/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Marcos
 */
@RestController
@RequestMapping("/util")
public class UtilController {
    
    
    
    @GetMapping("/HoraServidor")
    public ResponseEntity<String> getTempoDecorrido () {
       
     return new ResponseEntity<>(HoraServidor.HoraServidor(), HttpStatus.OK);
    } 
    
      @GetMapping("/TempoDecorrido/{horacomentad}")
    public ResponseEntity<String> getTempoDecorrido (@PathVariable("horacomentad") String horacomentad) {
                     
        return new ResponseEntity<>(TempoDecorrido.TempoDecorrido(horacomentad), HttpStatus.OK);
    } 
    
}
