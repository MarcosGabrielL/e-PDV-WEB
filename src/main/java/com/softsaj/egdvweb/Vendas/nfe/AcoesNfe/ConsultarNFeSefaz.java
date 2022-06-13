/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.nfe.AcoesNfe;
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
import com.softsaj.egdvweb.Vendas.services.FiscalService;
import com.softsaj.egdvweb.Vendas.services.NotaNFCeService;
import javax.swing.JOptionPane;

import javax.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
/**
 *
 * @author Marcos
 */
public class ConsultarNFeSefaz {
    
    public static String chave;
     @Autowired
   private static FiscalService fiscalservice;
   @Autowired
   private static NotaNFCeService notaNFCeService;
    
    public void recebechave(String chave1) throws CertificadoException{
    
    chave = chave1;
    
    consulta();
    
    DownloadNfeDistDfe a = new DownloadNfeDistDfe();
    a.chave(chave);
    }
    
    public static void consulta() throws CertificadoException {

        try {  
      //Inicia As Configurações  
      //Para Mais informações: https://github.com/Samuel-Oliveira/Java_NFe/wiki/Configura%C3%A7%C3%B5es-Nfe  
      ConfiguracoesNfe config =  IniciaConfiguraçãoNFE.iniciaConfigurações();  
  
      //Efetua a consulta
            TRetConsSitNFe retorno = Nfe.consultaXml(config, chave, DocumentoEnum.NFCE);

            //Resultado
            System.out.println();
            System.out.println("# Status: " + retorno.getCStat() + " - " + retorno.getXMotivo());
             //NotasFiscais.Retornonfe.setText("# Status: " + retorno.getCStat() + " - " + retorno.getXMotivo());
        } catch (Exception e) {
            System.err.println();
            System.err.println(e.getMessage());
        }

    }
    
    

}
