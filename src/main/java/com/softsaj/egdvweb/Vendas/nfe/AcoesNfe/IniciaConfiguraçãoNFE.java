/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.nfe.AcoesNfe;

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
import com.softsaj.egdvweb.Vendas.services.NotaNFCeService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Marcos
 */
public class IniciaConfiguraçãoNFE {
    
   public static Certificado certificado;
   
   @Autowired
   private static FiscalService fiscalservice;
   @Autowired
   private static NotaNFCeService notaNFCeService;
   
    public IniciaConfiguraçãoNFE(){
    
        try {
            iniciaConfigurações();
        } catch (NfeException ex) {
            Logger.getLogger(IniciaConfiguraçãoNFE.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificadoException ex) {
            Logger.getLogger(IniciaConfiguraçãoNFE.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
           Logger.getLogger(IniciaConfiguraçãoNFE.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    
    public static ConfiguracoesNfe iniciaConfigurações() throws NfeException, CertificadoException, Exception {
        
           // A1Pfx a = new A1Pfx();
           
            
            // Certificado Arquivo, Parametros: -Caminho Certificado, - Senha
            String path = new File(".\\resources\\Schemas").getAbsolutePath();
	 AiPfx();
         
         ConfiguracoesNfe config = null;
         
        
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
	int porta = 3128;
	String usuario = "";
	String senha = "";

	//config.setProxy(ip, porta, usuario , senha);
        
        boolean contigencia=false;
        
        if(contigencia){
            config.setContigenciaSVC(true);
        }

	return config;
            
        
}
    
    public static void AiPfx(){
        
        Fiscal f = new Fiscal();
        
        
        try{
             certificado = certifidoA1Pfx();
            System.out.println("Nome Certificado :" +certificado.getNome());
            System.out.println("Dias Restantes Certificado :" +certificado.getDiasRestantes());
            System.out.println("Validade Certificado :" +certificado.getVencimento());
            //atualiza data vencimento
            f.setCValidade(String.valueOf(certificado.getVencimento()));
            fiscalservice.updateFiscal(f);//updatevalidade(f);
            System.out.println("Token Certificado :" +certificado.getSerialToken());
             System.out.println("Protocolo Certificado :" +certificado.getSslProtocol());
            System.out.println("Tipo Certificado :" +certificado.getTipoCertificado()); 
            System.out.println("TllA3 :" +certificado.getDllA3()); 
            System.out.println("TllA3 :" +certificado.getMarcaA3()); 
            

            //PARA REGISTRAR O CERTIFICADO NA SESSAO, FAÇA SOMENTE EM PROJETOS EXTERNO
            //JAVA NFE, CTE E OUTRAS APIS MINHAS JA CONTEM ESTA INICIALIZAÇÃO
            //CertificadoService.inicializaCertificado(certificado, new FileInputStream(new File("caminhoCacert")));
            
        }catch (CertificadoException e){
            JOptionPane.showMessageDialog(null, "Erro ao ler certificado:"+e);
        } catch (FileNotFoundException ex) {
           Logger.getLogger(IniciaConfiguraçãoNFE.class.getName()).log(Level.SEVERE, null, ex);
       }
    
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
