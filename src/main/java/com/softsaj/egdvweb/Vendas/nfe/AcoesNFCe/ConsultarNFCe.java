/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe;

import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema_4.consSitNFe.TConsSitNFe;
import br.com.swconsultoria.nfe.schema_4.retConsSitNFe.TRetConsSitNFe;
import br.com.swconsultoria.nfe.util.ConstantesUtil;
import br.com.swconsultoria.nfe.util.XmlNfeUtil;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import javax.xml.bind.JAXBException;
/**
 *
 * @author Marcos
 */
public class ConsultarNFCe {
    
    public static String chave;
    public void recebechave(String chave1){
    
    chave = chave1;
    
        try {
            consulta();
        } catch (CertificadoException ex) {
            Logger.getLogger(ConsultarNFCe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ConsultarNFCe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void consulta() throws CertificadoException, Exception {

        try {  
      //Inicia As Configurações  
      ConfiguracoesNfe config = IniciaConfiguraçãoNFCE.iniciaConfigurações();  
  
      //Informe a chave a ser Consultada

            //Efetua a consulta
            TRetConsSitNFe retorno = Nfe.consultaXml(config, chave, DocumentoEnum.NFCE);

            //Resultado
            System.out.println();
            System.out.println("# Status: " + retorno.getCStat() + " - " + retorno.getXMotivo());
        //    NotasFiscais.Retornonfe1.setText("# Status: " + retorno.getCStat() + " - " + retorno.getXMotivo());
        } catch (Exception e) {
            System.err.println();
            System.err.println(e.getMessage());
        }

    }
    

}
