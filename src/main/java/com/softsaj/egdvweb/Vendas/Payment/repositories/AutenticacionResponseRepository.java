/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.Payment.repositories;

/**
 *
 * @author Marcos
 */
import com.softsaj.egdvweb.Vendas.Payment.models.AutenticacionResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marcos
 */
@Repository
public interface AutenticacionResponseRepository extends JpaRepository<AutenticacionResponse,Long> {

	AutenticacionResponse findAutenticacionResponseById(Long id);

	@Query("select n from AutenticacionResponse n where n.id = ?1")
	List<AutenticacionResponse> userAutenticacionResponse(String userId);

	 void deleteAutenticacionResponseById(Long id);

}
