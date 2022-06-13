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
import br.com.swconsultoria.nfe.schema_4.retConsStatServ.TRetConsStatServ;
import br.com.swconsultoria.nfe.util.ConstantesUtil;
import br.com.swconsultoria.nfe.util.XmlNfeUtil;
import com.softsaj.egdvweb.Vendas.models.Fiscal;
import com.softsaj.egdvweb.Vendas.services.FiscalService;
import com.softsaj.egdvweb.Vendas.services.NotaNFCeService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.springframework.beans.factory.annotation.Autowired;
/**
 *
 * @author Marcos
 */
public class ConsultaCadastro {
    
    //Função para Consultar o Cadastro do Contribuinte na Sefaz.
    public static String cnpj = null;
    
     @Autowired
   private static FiscalService fiscalservice;
   @Autowired
   private static NotaNFCeService notaNFCeService;
    
    public ConsultaCadastro() {
        
        StringBuilder sb = new StringBuilder();
        
        
        try {

            // Inicia As Configurações - ver https://github.com/Samuel-Oliveira/Java_NFe/wiki/1-:-Configuracoes
            ConfiguracoesNfe config =  IniciaConfiguraçãoNFE.iniciaConfigurações();  

            
            for(Fiscal f: fiscalservice.findAll()){
         cnpj = f.getCNPJ();
         }
            cnpj = cnpj.replaceAll("[^0-9]", "");
            
            
            TRetConsStatServ retorno = Nfe.statusServico(config, DocumentoEnum.NFE);

            //Resultado
            System.out.println();
            System.out.println("# Status: " + retorno.getCStat() + " - " + retorno.getXMotivo());
       
            
             JOptionPane.showMessageDialog(null, "# Status: " + retorno.getCStat() + " - " + retorno.getXMotivo());
            // NotasFiscais.jButton2.setBackground(new java.awt.Color(150,0,20));

        } catch (NfeException | CertificadoException e) {
            System.out.println(e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(ConsultaCadastro.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
