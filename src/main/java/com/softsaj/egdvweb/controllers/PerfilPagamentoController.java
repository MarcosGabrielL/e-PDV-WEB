/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.controllers;


import com.softsaj.egdvweb.models.PerfilPagamento;
import com.softsaj.egdvweb.services.PerfilPagamentoService;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Marcos
 */
@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/perfispagamento")
public class PerfilPagamentoController {
    
     @Autowired
    private PerfilPagamentoService vs;
    
    @GetMapping
    public ResponseEntity<List<PerfilPagamento>> getAll() {
        List<PerfilPagamento> movies =  vs.findAll();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
    
    //GEt PerfilPagamento
     @GetMapping("/perfil/{id}")
    public ResponseEntity<PerfilPagamento> getCienfiloById (@PathVariable("id") Long id
             ,@RequestParam("token") String token) {
        
       
        
        PerfilPagamento perfil = vs.findPerfilPagamentoById(id);
        return new ResponseEntity<>(perfil, HttpStatus.OK);
    }
    
    
    @GetMapping("/perfil/user/{email}")
    public ResponseEntity<PerfilPagamento> getCienfiloByUser (@PathVariable("email") String email
             ,@RequestParam("token") String token) {
        
       
        
        PerfilPagamento perfil = vs.findPerfilPagamentoByEmailUser(email);
        return new ResponseEntity<>(perfil, HttpStatus.OK);
    }
    
    
    @PostMapping("/perfil/add")
    public ResponseEntity<PerfilPagamento> addPerfilPagamento(@RequestBody PerfilPagamento movie,@RequestParam("token") String token) {
        
        
        
        PerfilPagamento newPerfilPagamento = vs.addPerfilPagamento(movie);
        URI uri = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/perfil/{id}").buildAndExpand(movie.getId()).toUri();
        
        return new ResponseEntity<>(newPerfilPagamento, HttpStatus.CREATED);
    }
    
    
    @PutMapping("/perfil/update/{id}")
    public ResponseEntity<PerfilPagamento> updatePerfilPagamento(@PathVariable("id") Long id, @RequestBody PerfilPagamento newperfil
            ,@RequestParam("token") String token) {
        
        
        
        PerfilPagamento perfil = vs.findPerfilPagamentoById(id);
        //c//inefilo.setTelefone(newperfil.getTelefone());
        //perfil.setIdade(newperfil.getIdade());
        //perfil.setFoto(newperfil.getFoto());
        PerfilPagamento updatePerfilPagamento = vs.updatePerfilPagamento(newperfil);//s
        return new ResponseEntity<>(updatePerfilPagamento, HttpStatus.OK);
    }
    
    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePerfilPagamento(@PathVariable("id") Long id
            ,@RequestParam("token") String token) {
        
      
        
        vs.deletePerfilPagamento(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
