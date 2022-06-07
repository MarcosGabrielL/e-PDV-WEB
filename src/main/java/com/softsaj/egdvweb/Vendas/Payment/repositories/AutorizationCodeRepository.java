/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.Payment.repositories;

import com.softsaj.egdvweb.Vendas.Payment.models.AutorizationCode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marcos
 */
@Repository
public interface AutorizationCodeRepository extends JpaRepository<AutorizationCode,Long> {

	AutorizationCode findAutorizationCodeById(Long id);

	@Query("select n from AutorizationCode n where n.state = ?1")
	List<AutorizationCode> userAutorizationCode(String userId);

	 void deleteAutorizationCodeById(Long id);

}
