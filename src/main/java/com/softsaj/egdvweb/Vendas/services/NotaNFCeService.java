/*
 * Copyright (C) Marcos Gabriel L from 2019 to Present
 * All rights Reserved
 */
package com.softsaj.egdvweb.Vendas.services;

import com.softsaj.egdvweb.Vendas.exception.UserNotFoundException;
import com.softsaj.egdvweb.Vendas.models.NotaNFCe;
import com.softsaj.egdvweb.Vendas.repositories.NotaNFCeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcos
 */
  @Service
public class NotaNFCeService {
      
      
      @Autowired
    private NotaNFCeRepository rp;
    
     public List<NotaNFCe> findAll() {
        return rp.findAll();
    }
     
     public NotaNFCe findCienfiloById(Integer id) {
        return rp.findNotaNFCeById(id)
                .orElseThrow(() -> new UserNotFoundException("NotaNFCe by id " + id + " was not found"));
    }
     
     public NotaNFCe addNotaNFCe(NotaNFCe cinefilo) {
        return rp.save(cinefilo);
    }
     
      public NotaNFCe updateNotaNFCe(NotaNFCe cinefilo) {
        return rp.save(cinefilo);
    }
      
      public void deleteNotaNFCe(Integer id){
        try{
          rp.deleteNotaNFCeById(id);  
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityViolationException(
                    "Não foi possivel deletar o NotaNFCe");
        }
    }
      
  }

