/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.services;

import com.softsaj.egdvweb.exception.UserNotFoundException;
import com.softsaj.egdvweb.models.PerfilPagamento;
import com.softsaj.egdvweb.repositories.PerfilPagamentoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
/**
 *
 * @author Marcos
 */
  @Service
public class PerfilPagamentoService {
    
    @Autowired
    private PerfilPagamentoRepository rp;
    
     public List<PerfilPagamento> findAll() {
        return rp.findAll();
    }
     
     public PerfilPagamento findPerfilPagamentoById(Long id) {
        return rp.findPerfilPagamentoById(id)
                .orElseThrow(() -> new UserNotFoundException("PerfilPagamento by id " + id + " was not found"));
    }
     
     public PerfilPagamento findPerfilPagamentoByEmailUser(String id) {
        return rp.userPerfilPagamento(id)
                .orElseThrow(() -> new UserNotFoundException("PerfilPagamento by id " + id + " was not found"));
    }
     
     
     
     public PerfilPagamento addPerfilPagamento(PerfilPagamento cinefilo) {
        return rp.save(cinefilo);
    }
     
      public PerfilPagamento updatePerfilPagamento(PerfilPagamento cinefilo) {
        return rp.save(cinefilo);
    }
      
      public void deletePerfilPagamento(Long id){
        try{
          rp.deletePerfilPagamentoById(id);  
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityViolationException(
                    "Não foi possivel deletar o PerfilPagamento");
        }
    }
}
