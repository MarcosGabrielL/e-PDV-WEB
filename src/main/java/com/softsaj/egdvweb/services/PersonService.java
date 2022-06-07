/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.services;

import com.softsaj.egdvweb.exception.UserNotFoundException;
import com.softsaj.egdvweb.models.Person;
import com.softsaj.egdvweb.repositories.PersonRepository;
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
public class PersonService {
    
    @Autowired
    private PersonRepository rp;
    
     public List<Person> findAll() {
        return rp.findAll();
    }
     
     public Person findCienfiloById(Integer id) {
        return rp.findPersonById(id)
                .orElseThrow(() -> new UserNotFoundException("Person by id " + id + " was not found"));
    }
     
     public Person addPerson(Person cinefilo) {
        return rp.save(cinefilo);
    }
     
      public Person updatePerson(Person cinefilo) {
        return rp.save(cinefilo);
    }
      
      public void deletePerson(Integer id){
        try{
          rp.deletePersonById(id);  
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityViolationException(
                    "Não foi possivel deletar o Person");
        }
    }
}
