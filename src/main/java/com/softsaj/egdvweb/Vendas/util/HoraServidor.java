/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.util;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
/**
 *
 * @author Marcos
 */
public class HoraServidor {

    public static String HoraServidor() {
        // Pega data/hora atual
        LocalDateTime agora = LocalDateTime.now();
        // formata a data
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dataFormatada = formatterData.format(agora);
        
        return dataFormatada;
        
    }
    
}
