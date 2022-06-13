/*
 * Copyright (C) Marcos Gabriel L from 2019 to Present
 * All rights Reserved
 */
package com.softsaj.egdvweb.Vendas.services;

import com.softsaj.egdvweb.Vendas.exception.UserNotFoundException;
import com.softsaj.egdvweb.Vendas.models.NotaNFE;
import com.softsaj.egdvweb.Vendas.repositories.NotaNFERepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcos
 */
 @Service
public class NotaNFEService {
      
      
      @Autowired
    private NotaNFERepository rp;
    
     public List<NotaNFE> findAll() {
        return rp.findAll();
    }
     
     public NotaNFE findCienfiloById(Integer id) {
        return rp.findNotaNFEById(id)
                .orElseThrow(() -> new UserNotFoundException("NotaNFE by id " + id + " was not found"));
    }
     
     public NotaNFE addNotaNFE(NotaNFE cinefilo) {
        return rp.save(cinefilo);
    }
     
      public NotaNFE updateNotaNFE(NotaNFE cinefilo) {
        return rp.save(cinefilo);
    }
      
      public void deleteNotaNFE(Integer id){
        try{
          rp.deleteNotaNFEById(id);  
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityViolationException(
                    "Não foi possivel deletar o NotaNFE");
        }
    }
      
  }
