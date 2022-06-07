/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.repositories;

import com.softsaj.egdvweb.models.Dominio;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marcos
 */
@Repository
public interface DominioRopository extends JpaRepository<Dominio, Long> {
    
     Optional<Dominio> findDominioById(Long id);
     
     void deleteDominioById(Long id);
     
     @Query("SELECT u FROM Dominio u WHERE u.vendedor = ?1")
      List<Dominio> findByEmail(String usuario);
}
