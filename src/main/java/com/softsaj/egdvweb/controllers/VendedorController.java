/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.controllers;


import com.softsaj.egdvweb.models.Vendedor;
import com.softsaj.egdvweb.security.JwtUtil;
import com.softsaj.egdvweb.services.VendedorService;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;;

/**
 *
 * @author Marcos
 */

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/vendedores")
public class VendedorController {
    
     @Autowired
    private VendedorService vs;
     
      @Autowired
    private JwtUtil jwt;
    
    @GetMapping
    public ResponseEntity<List<Vendedor>> getAll() {
        List<Vendedor> movies =  vs.findAll();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
    
    //GEt Vendedor
     @GetMapping("/vendedor/{id}")
    public ResponseEntity<Vendedor> getCienfiloById (@PathVariable("id") Long id
             ,@RequestHeader("Authorization") String token) {
        
        if(jwt.IsValidToken(token, id.toString())){
        
        Vendedor vendedor = vs.findVendedorById(id);
        return new ResponseEntity<>(vendedor, HttpStatus.OK);
        }else{
            throw new IllegalStateException("token invalid");
        }
    }
    
     @GetMapping("/vendedor/usuario/{email}")
    public ResponseEntity<List<Vendedor>> findByEmail (@PathVariable("email") String email
             ,@RequestHeader("Authorization") String token) {
        
         if(jwt.IsValidToken(token, email)){
                List<Vendedor> movies = vs.findByEmail(email);
                return new ResponseEntity<>(movies, HttpStatus.OK);
        }else{
            throw new IllegalStateException("token invalid");
        }
    }
    
    
    @PostMapping("/vendedor/add")
    public ResponseEntity<Vendedor> addVendedor(@RequestBody Vendedor movie, @RequestHeader("Authorization") String token) {
         if(jwt.IsValidToken(token, movie.getEmail())){
        Vendedor newVendedor = vs.addVendedor(movie);
        URI uri = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/vendedor/{id}").buildAndExpand(movie.getId()).toUri();
        
        return new ResponseEntity<>(newVendedor, HttpStatus.CREATED);
        }else{
            throw new IllegalStateException("token invalid");
        }
    }
    
    //Update nome,telefone,idade,foto;
    @PutMapping("/vendedor/update/{id}")
    public ResponseEntity<Vendedor> updateVendedor(@PathVariable("id") Long id, @RequestBody Vendedor newvendedor
            ,@RequestHeader("Authorization") String token) {
        
         if(jwt.IsValidToken(token, newvendedor.getEmail())){
        
        Vendedor vendedor = vs.findVendedorById(id);
        //vendedor.setNome(newvendedor.getNome());
        //c//inefilo.setTelefone(newvendedor.getTelefone());
        //vendedor.setIdade(newvendedor.getIdade());
        //vendedor.setFoto(newvendedor.getFoto());
        Vendedor updateVendedor = vs.updateVendedor(vendedor);//s
        return new ResponseEntity<>(updateVendedor, HttpStatus.OK);
        }else{
            throw new IllegalStateException("token invalid");
        }
    }
    
    @PostMapping("/vendedor/update/escolheu/{id}")
    public ResponseEntity<Vendedor> updateVVendedor(@PathVariable("id") Long id, @RequestBody Vendedor newvendedor
            ,@RequestHeader("Authorization") String token){
          if(jwt.IsValidToken(token, newvendedor.getEmail())){
        Locale locale = new Locale("pt","BR");
                GregorianCalendar calendar = new GregorianCalendar();
                SimpleDateFormat formatador1 = new SimpleDateFormat("YYYY-MM-dd",locale);
                Date datehoje = new Date();
                String hoje = formatador1.format(datehoje.getTime());
                datehoje.setMonth((datehoje.getMonth() + 1) );
                String menos30dias = formatador1.format(datehoje.getTime());
               
                System.out.println("Hoje: "+hoje+" inicio primeiro mes: "+menos30dias );
        
      //  newvendedor.setAmbiente(ambiente);
      //  newvendedor.setSerie(serie);
        newvendedor.setDatainicio(hoje);
        newvendedor.setDatafim(menos30dias);
        
        
        
        Vendedor updateVendedor = vs.updateVendedor(newvendedor);//s
        return new ResponseEntity<>(updateVendedor, HttpStatus.OK);
        }else{
            throw new IllegalStateException("token invalid");
        }
    }
    
    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVendedor(@PathVariable("id") Long id
            ,@RequestHeader("Authorization") String token) {
        
        if(jwt.IsValidToken(token, id.toString())){
        
        vs.deleteVendedor(id);
        return new ResponseEntity<>(HttpStatus.OK);
        }else{
            throw new IllegalStateException("token invalid");
        }
    }
    
    
}


