/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.File;

/**
 *
 * @author Marcos
 */

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface FileDBRepository extends JpaRepository<FileDB, Long> {
    
     @Query("SELECT u FROM FileDB u WHERE u.idpost = ?1")
     public  List<FileDB> findByIdProduto(String idpost);
     
     @Query("SELECT u FROM FileDB u WHERE u.idvendedor = ?1")
     public  List<FileDB> findByIdVendedor(String idvendedor);

}

