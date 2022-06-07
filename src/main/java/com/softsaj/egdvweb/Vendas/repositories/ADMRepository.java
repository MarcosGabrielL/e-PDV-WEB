/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.repositories;


import com.softsaj.egdvweb.Vendas.models.ADM;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marcos
 */
@Repository
public interface ADMRepository extends JpaRepository<ADM, Long> {
    
     Optional<ADM> findADMById(Integer id);
     
     void deleteADMById(Integer id);
}
