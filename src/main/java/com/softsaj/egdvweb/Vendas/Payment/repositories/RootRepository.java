/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.Payment.repositories;

import com.softsaj.egdvweb.Vendas.Payment.models.Root;
import com.softsaj.egdvweb.Vendas.models.*;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marcos
 */

@Repository
public interface RootRepository extends JpaRepository<Root,Integer> {

	Root findRootById(Long id);

	@Query("select n from Root n where n.vendedor_id = ?1")
	List<Root> userRoot(String userId);

	 void deleteRootById(Long id);
	
}
