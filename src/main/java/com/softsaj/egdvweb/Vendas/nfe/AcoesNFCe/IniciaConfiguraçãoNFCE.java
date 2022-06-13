/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe;

import gerenciador.AcoesNfe.*;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.util.ConstantesUtil;
import com.softsaj.egdvweb.Vendas.models.Fiscal;
import com.softsaj.egdvweb.Vendas.services.FiscalService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 * @author Marcos
 */
public class IniciaConfiguraçãoNFCE {
    
    @Autowired
   private static FiscalService fiscalservice;
    
  
   public static ConfiguracoesNfe iniciaConfigurações() throws NfeException, CertificadoException, IOException, Exception {
        
         String path = new File(".\\resources\\Schemas").getAbsolutePath();
                Certificado certificado = certifidoA1Pfx();
                
                ConfiguracoesNfe config = null ;
                
      
        String Ambiente = null;
         for(Fiscal f: fiscalservice.findAll()){
             if(f.getAmbiente() ==1){
             //Produção
                    config = ConfiguracoesNfe.criarConfiguracoes(EstadosEnum.BA , 
                AmbienteEnum.PRODUCAO,
                certificado,
                path);
                    
                    System.err.println("Produção");
             }
             if(f.getAmbiente() == 2){
                 //Homologação
                 config = ConfiguracoesNfe.criarConfiguracoes(EstadosEnum.BA , 
                AmbienteEnum.HOMOLOGACAO,
                certificado,
                path);
                 
             System.err.println("Homologação");
             }
              
         }
         
	/*ConfiguracoesIniciaisNfe config = ConfiguracoesIniciaisNfe.iniciaConfiguracoes(Estados.BA , 
                ConstantesUtil.AMBIENTE.HOMOLOGACAO,
                certificado,
                path);*/

	String ip = "192.168.0.1";
	String porta = "3128";
	String usuario = "";
	String senha = "";

	//config.setProxy(ip, porta, usuario , senha);
        
        boolean contigencia=false;
        
        if(contigencia){
            config.setContigenciaSVC(true);
        }

	return config;
            
        
}
    
    private static Certificado certifidoA1Pfx() throws CertificadoException, FileNotFoundException {
         
         
         String caminhoCertificado = null;
         String senha = null;
         
         for(Fiscal f: fiscalservice.findAll()){
         caminhoCertificado = f.getCertificado();
          senha = f.getCSenha();
         }
        

        return CertificadoService.certificadoPfx(caminhoCertificado, senha);
    }
   
}
