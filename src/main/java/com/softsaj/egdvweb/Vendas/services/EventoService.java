/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.services;

import com.softsaj.egdvweb.Vendas.Relatorios.Evento;
import com.softsaj.egdvweb.Vendas.exception.UserNotFoundException;
import com.softsaj.egdvweb.Vendas.repositories.EventoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcos
 */
@Service
public class EventoService {
    
    @Autowired
    private EventoRepository rp;
    
     public List<Evento> findAll() {
        return rp.findAll();
    }
     
     public Evento findEventoById(Long id) {
        return rp.findEventoById(id)
                .orElseThrow(() -> new UserNotFoundException("Evento by id " + id + " was not found"));
    }
     public  List<Evento> findByEmail(String email) {
        return rp.findByEmail(email);
    }
     
     public Evento addEvento(Evento cinefilo) {
        return rp.save(cinefilo);
    }
     
      public Evento updateEvento(Evento cinefilo) {
        return rp.save(cinefilo);
    }
      
      public void deleteEvento(Long id){
        try{
          rp.deleteEventoById(id);  
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityViolationException(
                    "Não foi possivel deletar o Evento");
        }
    }
    
}
