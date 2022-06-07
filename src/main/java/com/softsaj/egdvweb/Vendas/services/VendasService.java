/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.services;

import com.softsaj.egdvweb.Vendas.exception.UserNotFoundException;
import com.softsaj.egdvweb.Vendas.models.Vendas;
import com.softsaj.egdvweb.Vendas.repositories.VendasRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcos
 */
 @Service
public class VendasService {
  
    @Autowired
    private VendasRepository rp;
    
     public List<Vendas> findAll() {
        return rp.findAll();
    }
     
     public Vendas findVendasById(Long id) {
        return rp.findVendasById(id)
                .orElseThrow(() -> new UserNotFoundException("Vendas by id " + id + " was not found"));
    }
     
      public List<Vendas> findAllByData(String datavenda, String idvendedor) {
        return rp.findByData(datavenda, idvendedor);
    }
      
      public List<Vendas> findVendasByComprador(String idcomprador) {
        return rp.findVendasByComprador(idcomprador);
    }
      
     public List<Vendas> findAllByMes(String datainicio, String datafinal, String idvendedor) {
        return rp.findAllByMes(datainicio,datafinal, idvendedor );
    }
     
     public Vendas addVendas(Vendas cinefilo) {
        return rp.save(cinefilo);
    }
     
      public Vendas updateVendas(Vendas cinefilo) {
        return rp.save(cinefilo);
    }
      
      public void deleteVendas(Long id){
        try{
          rp.deleteVendasById(id);  
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityViolationException(
                    "Não foi possivel deletar o Vendas");
        }
    }
    
}

