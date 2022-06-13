/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe;


import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.dom.enuns.ServicosEnum;
import br.com.swconsultoria.nfe.util.NFCeUtil;
import br.com.swconsultoria.nfe.util.WebServiceUtil;
import br.com.swconsultoria.nfe.util.XmlNfeUtil;
import br.com.swconsultoria.nfe.schema_4.enviNFe.*;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.*;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN101;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN102;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN201;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN202;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN500;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN900;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.PISST;
import br.com.swconsultoria.nfe.util.RetornoUtil;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.StatusEnum;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.COFINS;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.ICMS;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.PIS;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Prod;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Total.ICMSTot;
import br.com.swconsultoria.nfe.util.ConstantesUtil;
import com.softsaj.egdvweb.Vendas.Relatorios.DanfeNFCe;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.softsaj.egdvweb.Vendas.models.Produto;
import com.softsaj.egdvweb.Vendas.models.Adcionais;
import com.softsaj.egdvweb.Vendas.models.Caixa;
import com.softsaj.egdvweb.Vendas.models.Fiscal;
import com.softsaj.egdvweb.Vendas.models.Vendas;
import com.softsaj.egdvweb.Vendas.models.NotaNFCe;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.ACobrar;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.Bairro;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.CDV;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.CEP;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.CMun;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.CNPJ;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.CPF;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.CRT;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.CSC;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.Cnf;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.DhEmi;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.Fone;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.IE;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.Icmstot;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.Id;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.IdLote;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.IdToken;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.NNF;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.NatOp;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.Nome;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.Nro;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.SalvarXML;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.Serie;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.TpAmb;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.TpEmis;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.UF;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.Uf;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.Versão;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.XFant;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.XLgr;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.XMun;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.XNome;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.configuraçãoNFCE;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.decimalFormat;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.icmsDtot;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.impostotot;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.produtos;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.salvanfce;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.vbcst;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.vbctot;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.vcofins;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.venda;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.vpis;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.vprod;
import static com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe.EnvioNFCe.vst;
import com.softsaj.egdvweb.Vendas.services.FiscalService;
import com.softsaj.egdvweb.Vendas.services.NotaNFCeService;
import com.softsaj.egdvweb.Vendas.services.ProdutoService;
import java.io.FileNotFoundException;
import java.security.Provider;
import java.security.Security;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



/**
 *
 * @author Marcos
 */
public class ReenvioNFCe {
    
    public static String Id,Versão,Uf,Cnf,NatOp,Serie,NNF,DhEmi,CMun,TpEmis,CDV,TpAmb,FinNFe,CNPJ,XNome,XFant,IdToken,CSC;
   public static String XLgr,Nro,Bairro,XMun,UF,CEP,Fone,IE,CRT,CPF,Nome;
   public static List<Produto> produtos = new ArrayList<Produto>();
   public static int Quantidade,itens,IdLote;
   public static float Totalcompra, ACobrar;
   public static Adcionais add;
   public static Vendas venda = new Vendas();
   public static NotaNFCe nota;
   public static String XMLAntigo;
   public static ConfiguracoesNfe configuraçãoNFCE;
   public static float Icmstot,vbctot,icmsDtot,vbcst,vst,vprod,vpis,vcofins,impostotot;
   public static Caixa caixa;
   static DecimalFormat decimalFormat = new DecimalFormat( "0.00" );
   
     @Autowired
   private static FiscalService fiscalservice;
   @Autowired
   private static NotaNFCeService notaNFCeService;
    @Autowired
   private static ProdutoService produtoService;
   
    public static void iniciaConfigurações() throws NfeException, CertificadoException, IOException, Exception {
        
         
                Certificado certificado = certifidoA1Pfx();
                           
	configuraçãoNFCE = IniciaConfiguraçãoNFCE.iniciaConfigurações(); 
    }
    
    public static void AssinaSSL(String caminhoCertificado, String senhaCertificado) {
        System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
        System.clearProperty("javax.net.ssl.keyStore");
        System.clearProperty("javax.net.ssl.keyStorePassword");
        System.clearProperty("javax.net.ssl.trustStore");
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
        System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
        System.setProperty("javax.net.ssl.keyStore", caminhoCertificado);
        System.setProperty("javax.net.ssl.keyStorePassword", senhaCertificado);
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
        System.setProperty("javax.net.ssl.trustStore", "cacerts");
        
       
        
    }
    
    
     private static Certificado certifidoA1Pfx() throws CertificadoException, FileNotFoundException {
         
         
         String caminhoCertificado = null;
         String senha = null;
         
         for(Fiscal f: fiscalservice.findAll()){
         caminhoCertificado = f.getCertificado();
          senha = f.getCSenha();
         }
        AssinaSSL(caminhoCertificado,senha);

        return CertificadoService.certificadoPfx(caminhoCertificado, senha);
    }
 
   
    public static void Envia() {
        
        try {
           Icmstot = 0;vbctot = 0;icmsDtot = 0;vbcst = 0;vst = 0;vprod = 0;vpis = 0;vcofins = 0;impostotot = 0;
           ConfiguracoesNfe config = configuraçãoNFCE;//IniciaConfiguraçãoNFCE.iniciaConfigurações();
             
           
            
            //TAG raiz da NF-e
            TNFe nfe = new TNFe();
            /*Dados da Nota Fiscal eletrônica
            infNFe   - Informações da NF-e  */
            InfNFe infNFe = new InfNFe();
            infNFe.setId(Id);//Identificador da TAG a ser assinada
            infNFe.setVersao(Versão);//Versão do leiaute

            Locale locale = new Locale("pt","BR");
                GregorianCalendar calendar = new GregorianCalendar();
                SimpleDateFormat formatador = new SimpleDateFormat("YYYY-MM-dd HH:mm:ssXXX",locale);
                Date d = new Date();
                String data = formatador.format(d.getTime());
                data = data.replaceAll(" ", "T");
            DhEmi = data;//Data e hora de emissão do Documento
            
             /*Identificação da Nota Fiscal eletrônica
            ide     - Informações de identificação da NF-e*/
            // Dados Nfe
            Ide ide = new Ide();
            ide.setCUF(Uf);//Código da UF do emitente do Documento Fiscal
            ide.setCNF(Cnf);//Código Numérico que compõe a Chave de Acesso
            ide.setNatOp(NatOp);//Descrição da Natureza da Operação
            ide.setMod("65");//Código do Modelo do Documento Fiscal
            ide.setSerie(String.valueOf(Integer.parseInt(Serie)));//Série do Documento Fiscal
            ide.setNNF(String.valueOf(Integer.parseInt(NNF)));//Número do Documento Fiscal
            ide.setDhEmi(DhEmi);//Data e hora de emissão do Documento
            ide.setTpNF("1");//Tipo de Operação
            ide.setIdDest("1");//Identificador de local de destino da operação
            ide.setIndFinal("1");//Operação ocorrer com Consumidor Final,
            //ide.setIndPres("1");// Indicador de Presença do Comprador
            ide.setCMunFG(CMun);//Código do Município de Ocorrência do Fato Gerador
            ide.setTpImp("4");//Formato de Impressão do DANFE
            ide.setTpEmis(TpEmis);//Tipo de Emissão da NF-e
            ide.setCDV(CDV);//Dígito Verificador da Chave de Acesso daNF-e
            ide.setTpAmb(TpAmb);//Identificação do Ambiente
            ide.setFinNFe("1");//Finalidade de emissão da NF-e
            ide.setIndFinal("1");//Indica operação com Consumidor final
            ide.setIndPres("1");//Indicador de presença do comprador no estabelecimento comercial no momento daoperação
            ide.setProcEmi("0");//Processo de emissão da NF-e
            ide.setVerProc("eGDV 1.0");//Versão do Processo de emissão da NF-e
            infNFe.setIde(ide);

            /*Identificação do Emitente da Nota Fiscal eletrônica*/
            //emit = Identificação do emitente da NF-e
            Emit emit = new Emit();
            emit.setCNPJ(CNPJ.replaceAll("[^0-9]", ""));//CNPJ do emitente
            emit.setXNome(XNome);//Razão Social ou Nome do emitente
            emit.setXFant(XFant);//Nome fantasia
            /*enderEmit - Endereço do emitente*/
            TEnderEmi enderEmit = new TEnderEmi();
            enderEmit.setXLgr(XLgr);//Logradouro
            enderEmit.setNro(Nro);//Número
            enderEmit.setXCpl("Casa");//Complemento
            enderEmit.setXBairro(Bairro);//Bairro
            enderEmit.setCMun(CMun);//Código do município
            enderEmit.setXMun(XMun);//Nome do município
            enderEmit.setUF(TUfEmi.valueOf(UF));//Sigla da UF
            enderEmit.setCEP(CEP.replaceAll("[^0-9]", ""));//Código do CEP
            enderEmit.setCPais("1058");//Código do País
            enderEmit.setXPais("BRASIL");//Nome do País
            enderEmit.setFone(Fone.replaceAll("[^0-9]", ""));//Telefone
            emit.setEnderEmit(enderEmit);
            emit.setIE(IE.replaceAll("[^0-9]", ""));//Inscrição Estadual do Emitente
            emit.setCRT(CRT);//Código de Regime Tributário
            infNFe.setEmit(emit);

            /* Identificação do Destinatário da Nota Fiscal eletrônica*/
            //dest  - Identificação do Destinatário da NF-e
            Dest dest = new Dest();
            if(CPF ==null ){
                
            }else{
                dest.setCPF(CPF);//CPF do destinatário
                dest.setXNome(Nome);//CPF do destinatário
                dest.setIndIEDest("9");//Indicador da IE do Destinatário
                infNFe.setDest(dest);
            }

            /*Detalhamento de Produtos e Serviços da NF-e*/
            List <Det> Dets = new ArrayList<>();
            int i = 1;
            
            for(Produto p : produtos){
            //det   - Detalhamento de Produtos e Serviços
            Det det = new Det();
            //Prod prod = new Prod();
            Prod prod = new Prod();
            det.setNItem(""+i);
            System.out.println("ITEM="+i);
            
                    /*Produtos e Serviços da NF-e*/
                    //prod  - Detalhamento de Produtos e Serviços
                    //Prod prod = new Prod();
                    prod.setCProd(p.getCodigo());//Código do produto ou serviço
                    if(p.getCEANTrib().equals("SEM GTIN")){
                        prod.setCEAN(p.getCEANTrib()); //GTIN (Global Trade Item Number) do produto, antigo código EAN ou código de barras
                    }else{
                    prod.setCEAN(p.getCodigo()); //GTIN (Global Trade Item Number) do produto, antigo código EAN ou código de barras
                    }
                    if(TpAmb.equals("2") && i==1){
                    prod.setXProd("NOTA FISCAL EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL");
                    }else{
                    prod.setXProd(p.getDescricao()); //Descrição do produto ou serviço
            System.err.println("!!!!!!!!"+prod.getXProd());
                    }
                    //prod.setNCM(p.getNCM);//Código NCM com 8 dígitos
                    prod.setCEST(p.getCEST());//Código CEST
                    prod.setIndEscala("S");//Produzido em escala relevante?
                    prod.setCFOP(p.getCFOP());//Código Fiscal de Operações e Prestações
                    prod.setUCom(p.getUnidade());//Unidade Comercial
                    prod.setQCom(String.valueOf(p.getQuantidade()));//Quantidade Comercial
                    prod.setVUnCom(p.getPrecoun());//Valor Unitário de Comercialização
                    prod.setVProd(decimalFormat.format(p.getSubTotal()).replaceAll(",", "."));//Valor Total Bruto dos Produtos ou Serviços
                    //System.err.println("Valor do Produto: "+String.valueOf(p.getSubTotal()));
                    prod.setCEANTrib(p.getCEANTrib());//GTIN (Global Trade Item Number) da unidade tributável, antigo código EAN ou código de barras 
                    prod.setUTrib(p.getUnidade());//Unidade Tributável
                    prod.setQTrib(String.valueOf(p.getQuantidade()));//Quantidade Tributável
                    prod.setVUnTrib(p.getPrecoun());//Valor Unitário de tributação
                    //prod.setVSeg("0.00");//Valor Total do Seguro
                    //prod.setVDesc("0.00");//Valor do Desconto
                    prod.setIndTot("1");//Indica se valor do Item (vProd) entra no valor total da NF
                    
                    det.setProd(prod);
                    det.setImposto(getImposto(p));//det   - Detalhamento de Produtos e Serviços
            infNFe.getDet().add(det);//infNFe   - Informações da NF-e
            i++;
            }
            
            
            /*Total da NF-e*/
            //total - Grupo Totais da NF-e
            Total total = new Total();
            //ICMSTot   - Grupo Totais referentes ao ICMS
            ICMSTot icmstot = new ICMSTot();
            
                BigDecimal vb = new BigDecimal(vbctot).setScale(2, RoundingMode.HALF_EVEN);
                if(String.valueOf(vb.doubleValue()).replaceAll(",", ".").length()<=3){
            icmstot.setVBC(decimalFormat.format(vb.doubleValue()).replaceAll(",", "."));//Base de Cálculo do ICMS
                }else{
            icmstot.setVBC(decimalFormat.format(vb.doubleValue()).replaceAll(",", "."));
                }
                BigDecimal ic = new BigDecimal(Icmstot).setScale(2, RoundingMode.HALF_EVEN);
                if(decimalFormat.format(ic.doubleValue()).replaceAll(",", ".").length()<=3){
            icmstot.setVICMS(decimalFormat.format(ic.doubleValue()).replaceAll(",", ".")+"0");//Valor Total do ICMS
                }else{
            icmstot.setVICMS(decimalFormat.format(ic.doubleValue()).replaceAll(",", "."));
                }
                BigDecimal Deson = new BigDecimal(icmsDtot).setScale(2, RoundingMode.HALF_EVEN);
                
                    if(icmsDtot<=0){
                        icmstot.setVICMSDeson("0.00");
                    }else{
                        if(decimalFormat.format(Deson.doubleValue()).replaceAll(",", ".").length()<=3){
                icmstot.setVICMSDeson(decimalFormat.format(Deson.doubleValue()).replaceAll(",", ".")+"0");//Valor Total do ICMS desonerado
                        }else{
                icmstot.setVICMSDeson(decimalFormat.format(Deson.doubleValue()).replaceAll(",", "."));
                            }
                    
                        }
            icmstot.setVFCP("0.00"); //Fundo de Combate à Pobreza
            icmstot.setVFCPST("0.00");
            icmstot.setVFCPSTRet("0.00");
            if(vbcst>0){
                BigDecimal vbc = new BigDecimal(vbcst).setScale(2, RoundingMode.HALF_EVEN);
            icmstot.setVBCST(decimalFormat.format(vbc.doubleValue()).replaceAll(",", "."));//Base de Cálculo do ICMS ST
            }else{ icmstot.setVBCST("0.00");//Base de Cálculo do ICMS ST
            }
            if(vst>0){
                BigDecimal vs = new BigDecimal(vst).setScale(2, RoundingMode.HALF_EVEN);
            icmstot.setVST(decimalFormat.format(vs.doubleValue()).replaceAll(",", "."));//Valor Total do ICMS ST
            }else{ icmstot.setVST("0.00");}
                BigDecimal pr = new BigDecimal(vprod).setScale(2, RoundingMode.HALF_EVEN);
                //System.err.println("Valor Total dos produtos: "+pr.doubleValue());
            icmstot.setVProd(decimalFormat.format(pr.doubleValue()).replaceAll(",", "."));//Valor Total dos produtos e serviços
            icmstot.setVFrete("0.00");//Valor Total do Frete
            icmstot.setVSeg("0.00");//Valor Total do Seguro
            icmstot.setVDesc("0.00");//Valor Total do Desconto
            icmstot.setVII("0.00");//Valor Total do Imposto de Importação II
            icmstot.setVIPI("0.00");//Valor Total do IPI - Imposto sobre Produtos Industrializados
            icmstot.setVIPIDevol("0.00");// IPI a ser devolvido
               BigDecimal vpi = new BigDecimal(vpis).setScale(2, RoundingMode.HALF_EVEN);
                    if(vpi.doubleValue() <=0){icmstot.setVPIS("0.00");}else{
                         //System.err.println("Valor Total PIS:"+vpi.doubleValue());
                         icmstot.setVPIS(decimalFormat.format(vpi.doubleValue()).replaceAll(",", "."));//Valor do PIS
                    }
               BigDecimal vco = new BigDecimal(vcofins).setScale(2, RoundingMode.HALF_EVEN);
                    if(vco.doubleValue() <=0){icmstot.setVCOFINS("0.00");}else{
                         //System.err.println("Valor Total COFINS:"+vco.doubleValue());
                         icmstot.setVCOFINS(decimalFormat.format(vco.doubleValue()).replaceAll(",", "."));//Valor da COFINS
                    }
            icmstot.setVOutro("0.00");//Outras Despesas acessórias
            BigDecimal aco = new BigDecimal(ACobrar).setScale(2, RoundingMode.HALF_EVEN);
            icmstot.setVNF(decimalFormat.format(aco.doubleValue()).replaceAll(",", "."));//Valor Total da NF-e
            BigDecimal bd = new BigDecimal(impostotot).setScale(2, RoundingMode.HALF_EVEN);
            if(decimalFormat.format(bd.doubleValue()).replaceAll(",", ".").length()<=3){
            icmstot.setVTotTrib(decimalFormat.format(bd.doubleValue()).replaceAll(",", ".") + "0");//Valor aproximado total de tributos federais
            }else{
            icmstot.setVTotTrib(decimalFormat.format(bd.doubleValue()).replaceAll(",", "."));//Valor aproximado total de tributos federais
            }total.setICMSTot(icmstot);
            infNFe.setTotal(total);

            /*Informações do Transporte da NF-e*/
            //transp    - Grupo Informações do Transporte
            Transp transp = new Transp();
            transp.setModFrete("9");//Modalidade do frete
            infNFe.setTransp(transp);


            
            /* Formas de Pagamento*/
            //pag   - Grupo de Formas de Pagamento
            Pag pag = new Pag();
            Pag.DetPag detPag = new Pag.DetPag();
            Pag.DetPag detPag1 = new Pag.DetPag();
            Pag.DetPag detPag2 = new Pag.DetPag();
            
                if(venda.getModopagamento1().equals("Dinheiro")){
                    //dinheiro
                    detPag.setTPag("01");//Forma de pagamento
                    //System.out,println("Forma de Pagamento 1");
                    BigDecimal aa = new BigDecimal(venda.getRecebido1()).setScale(2, RoundingMode.HALF_EVEN);
                    DecimalFormat decimalFormat1 = new DecimalFormat( "0.00" );
                    detPag.setVPag(String.valueOf(decimalFormat1.format(aa.doubleValue())).replaceAll(",", "."));//Valor do Pagamento
                    detPag.setIndPag("0");//ndicador da forma depagamento A vista ou a prazo
                     pag.getDetPag().add(detPag);
                     
                  //   pag.setVTroco(Troco);
                }
                if(venda.getModopagamento1().equals("Cartão Crédito")){
                    //Pagou Cartão Crédito
                    detPag.setTPag("99");//Forma de pagamento
                    //System.out,println("Forma de Pagamento 1");
                    //detPag1.getCard().setCNPJ("");//CNPJ da Credenciadora de cartão de
                    //detPag1.getCard().setTBand("");//Bandeira da operadora de cartão de
                   // detPag1.getCard().setCAut("");//Número de autorização da operação
                    BigDecimal aa = new BigDecimal(venda.getRecebido1()).setScale(2, RoundingMode.HALF_EVEN);
                    DecimalFormat decimalFormat1 = new DecimalFormat( "0.00" );
                    detPag.setVPag(String.valueOf(decimalFormat1.format(aa.doubleValue())).replaceAll(",", "."));//Valor do Pagamento
                    //detPag.setIndPag("2");
                     pag.getDetPag().add(detPag);
                }
                if(venda.getModopagamento1().equals("Cartão Debito")){
                    //Cartão Débito
                   detPag.setTPag("99");//Forma de pagamento
                   //System.out,println("Forma de Pagamento 1");
                   //detPag2.getCard().setCNPJ("");//CNPJ da Credenciadora de cartão de
                   //detPag2.getCard().setTBand("");//Bandeira da operadora de cartão de
                   //detPag2.getCard().setCAut("");//Número de autorização da operação
                    BigDecimal aa = new BigDecimal(venda.getRecebido1()).setScale(2, RoundingMode.HALF_EVEN);
                    DecimalFormat decimalFormat1 = new DecimalFormat( "0.00" );
                    detPag.setVPag(String.valueOf(decimalFormat1.format(aa.doubleValue())).replaceAll(",", "."));//Valor do Pagamento
                   //detPag.setIndPag("2");
                    pag.getDetPag().add(detPag);
                }
                
                //Forma de pagamento 2
                if(venda.getModopagamento2() != null){
                    if(venda.getModopagamento2().equals("Dinheiro")){
                    //dinheiro
                    detPag1.setTPag("01");//Forma de pagamento
                    
                    BigDecimal aa = new BigDecimal(venda.getRecebido2()).setScale(2, RoundingMode.HALF_EVEN);
                    DecimalFormat decimalFormat1 = new DecimalFormat( "0.00" );
                    detPag1.setVPag(String.valueOf(decimalFormat1.format(aa.doubleValue())).replaceAll(",", "."));//Valor do Pagamento
                    detPag1.setIndPag("0");
                    pag.getDetPag().add(detPag1);
                    
                    }
                    if(venda.getModopagamento2().equals("Cartão Crédito")){
                        //Pagou Cartão Crédito
                        detPag1.setTPag("99");//Forma de pagamento
                        BigDecimal aa = new BigDecimal(venda.getRecebido2()).setScale(2, RoundingMode.HALF_EVEN);
                        DecimalFormat decimalFormat1 = new DecimalFormat( "0.00" );
                        detPag1.setVPag(String.valueOf(decimalFormat1.format(aa.doubleValue())).replaceAll(",", "."));//Valor do Pagamento
                        //detPag1.setIndPag("2");
                        pag.getDetPag().add(detPag1);
                    }
                    if(venda.getModopagamento2().equals("Cartão Debito")){
                        //Cartão Débito
                       detPag1.setTPag("99");//Forma de pagamento
                       BigDecimal aa = new BigDecimal(venda.getRecebido2()).setScale(2, RoundingMode.HALF_EVEN);
                        DecimalFormat decimalFormat1 = new DecimalFormat( "0.00" );
                        detPag1.setVPag(String.valueOf(decimalFormat1.format(aa.doubleValue())).replaceAll(",", "."));//Valor do Pagamento
                       //detPag1.setIndPag("2");
                       pag.getDetPag().add(detPag1);
                    }
                }
                if(venda.getModopagamento3() != null){
                        if(venda.getModopagamento3().equals("Dinheiro")){
                        //dinheiro
                        detPag2.setTPag("01");//Forma de pagamento
                        
                         BigDecimal aa = new BigDecimal(venda.getRecebido3()).setScale(2, RoundingMode.HALF_EVEN);
                        DecimalFormat decimalFormat1 = new DecimalFormat( "0.00" );
                        detPag2.setVPag(String.valueOf(decimalFormat1.format(aa.doubleValue())).replaceAll(",", "."));//Valor do Pagamento
                        detPag2.setIndPag("0");
                        pag.getDetPag().add(detPag2);
                        }
                        if(venda.getModopagamento3().equals("Cartão Crédito")){
                            //Pagou Cartão Crédito
                            detPag2.setTPag("99");//Forma de pagamento
                            //detPag1.getCard().setCNPJ("");//CNPJ da Credenciadora de cartão de
                            //detPag1.getCard().setTBand("");//Bandeira da operadora de cartão de
                           // detPag1.getCard().setCAut("");//Número de autorização da operação
                            BigDecimal aa = new BigDecimal(venda.getRecebido3()).setScale(2, RoundingMode.HALF_EVEN);
                        DecimalFormat decimalFormat1 = new DecimalFormat( "0.00" );
                        detPag2.setVPag(String.valueOf(decimalFormat1.format(aa.doubleValue())).replaceAll(",", "."));//Valor do Pagamento
                            //detPag2.setIndPag("2");
                            pag.getDetPag().add(detPag2);
                        }
                        if(venda.getModopagamento3().equals("Cartão Debito")){
                            //Cartão Débito
                           detPag2.setTPag("99");//Forma de pagamento
                           //detPag2.getCard().setCNPJ("");//CNPJ da Credenciadora de cartão de
                           //detPag2.getCard().setTBand("");//Bandeira da operadora de cartão de
                           //detPag2.getCard().setCAut("");//Número de autorização da operação
                           BigDecimal aa = new BigDecimal(venda.getRecebido3()).setScale(2, RoundingMode.HALF_EVEN);
                        DecimalFormat decimalFormat1 = new DecimalFormat( "0.00" );
                        detPag2.setVPag(String.valueOf(decimalFormat1.format(aa.doubleValue())).replaceAll(",", "."));//Valor do Pagamento
                           //detPag2.setIndPag("2");
                           pag.getDetPag().add(detPag2);
                        }
                }
           
            BigDecimal tro = new BigDecimal(venda.getTroco()).setScale(2, RoundingMode.HALF_EVEN);
            pag.setVTroco(String.valueOf(decimalFormat.format(tro.doubleValue())).replaceAll(",", "."));
            infNFe.setPag(pag);
            
            //TramiteNota.Info.setText("Aguardando Retorno SEFAZ...AGUARDE!!");
            //Tela_Caixa.Info.setText("<html>Aguardando Retorno SEFAZ\n ...AGUARDE!!</html>");
            //MANDA TODAS INFORMAÇOES PRA NFE
            nfe.setInfNFe(infNFe);

            
            // Monta EnviNfe
            TEnviNFe enviNFe = new TEnviNFe();
            enviNFe.setVersao(ConstantesUtil.VERSAO.NFE);
            enviNFe.setIdLote(String.valueOf(IdLote));
            enviNFe.setIndSinc("1");
            enviNFe.getNFe().add(nfe);
            
            
            
            //QRCODE
            /*Parâmetros:
            String qrCode = NFCeUtil.getCodeQRCode(
				chave, 
				ambiente,
				idToken, 
				CSC, 
				urlCOnsulta);
        chave : Chave de Acesso da NFCe
        ambiente : Identificação do Ambiente (1 – Produção, 2 – Homologação)
        idToken : Identificador do CSC – Código de Segurança do Contribuinte no Banco de Dados da SEFAZ
        CSC : Código de Segurança do Contribuinte (antigo Token)
        urlConsulta : Url De Consulta da Nfc-e do Estado*/
            String qrCode = NFCeUtil.getCodeQRCode(
                infNFe.getId().substring(3),
                 config.getAmbiente().getCodigo(),
                IdToken,
                CSC,
                WebServiceUtil.getUrl(config,DocumentoEnum.NFCE, ServicosEnum.URL_QRCODE));
            
              
            TNFe.InfNFeSupl infNFeSupl = new TNFe.InfNFeSupl();
            infNFeSupl.setQrCode(qrCode);
            infNFeSupl.setUrlChave(WebServiceUtil.getUrl(config, DocumentoEnum.NFCE, ServicosEnum.URL_CONSULTANFCE));
            enviNFe.getNFe().get(0).setInfNFeSupl(infNFeSupl);
            
            // Monta e Assina o XML
            enviNFe = Nfe.montaNfe(config, enviNFe, true);
            salvanfce();
            System.out.println("QRCODE: "+qrCode);
            
            // Envia a Nfe para a Sefaz
            TRetEnviNFe retorno = Nfe.enviarNfe(config, enviNFe, DocumentoEnum.NFCE);
            
            //Salva Xml e manda valores para imprimir DANFE
            //SalvarXMLErro(qrCode);
            String xmlfinal = null;
            //Salva Xml e manda valores para imprimir DANFE
            //SalvarXMLErro(qrCode);
            //Valida se o Retorno é Assincrono
            
                //Se for else o Retorno é Sincrono

                //Valida Retorno Sincrono
                RetornoUtil.validaSincrono(retorno);
                System.out.println();
                System.out.println("# Status: " + retorno.getProtNFe().getInfProt().getCStat() + " - " + retorno.getProtNFe().getInfProt().getXMotivo());
                System.out.println("# Protocolo: " + retorno.getProtNFe().getInfProt().getNProt());
                System.out.println("# Xml Final :" + XmlNfeUtil.criaNfeProc(enviNFe, retorno.getProtNFe()));
            System.out.println("Xml Final :" + XmlNfeUtil.criaNfeProc(enviNFe, retorno.getProtNFe()));
            xmlfinal = XmlNfeUtil.criaNfeProc(enviNFe, retorno.getProtNFe());
            
            
            
            System.err.println("Aqui 2 !!!!!");
            //Salva Xml e manda valores para imprimir DANFE
            SalvarXML(qrCode,xmlfinal);
            
            
           // Tela_Caixa.Tramite.setVisible(false);
        } catch (NfeException | JAXBException e) {
            
            System.out.println("Erro ao Enviar Nota:  "+e);
           
        }catch (Exception ex) {
            
            System.out.println("Erro ao Enviar Nota 1:  "+ex);
             }finally{
            produtos.clear();
           //Item = 0;
        }
        

    }
    
    
    
    public static void SalvarXML(String Qr,String Conteudo){
    
        //TramiteNota.Info.setText("Salvando Nota...Aguarde");
       // Tela_Caixa.Tramite(2);
           // DanfeNFCe c = new DanfeNFCe();
            //Salvar XML na pasta urlxml
            //Com nome de Nota
            Locale locale = new Locale("pt","BR");
                GregorianCalendar calendar = new GregorianCalendar();
                SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-YYYY_hh-mm-ss");
                Date d = new Date();
                String data = formatador.format(d.getTime());
            String Nota = "nnF"+nota.getNumero()+"D"+data;
            String urlDir = "C:\\Users\\Marcos\\Desktop\\Notas Fiscais\\XML";
            String urlxml = "C:\\Users\\Marcos\\Desktop\\Notas Fiscais\\XML\\"+Nota+".xml";
                
                File diretorio = new File(urlDir);      
                if (!diretorio.exists()) {
                    diretorio.mkdirs();
            }      
         
        try {
            Writer file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(urlxml),"UTF-8"));
            file.write(Conteudo);      
            file.close(); 
        } catch (UnsupportedEncodingException ex) {
            //JOptionPane.showMessageDialog(null, "Erro ao Salvar Nota:  "+ex);
        } catch (IOException ex) {
            //JOptionPane.showMessageDialog(null, "Erro ao Salvar Nota:  "+ex);
        }catch (Exception ex) {
            //JOptionPane.showMessageDialog(null, "Erro ao Salvar Nota:  "+ex);
        }
            
            //TramiteNota.Info.setText("Imprimindo Nota...Aguarde");
            // Tela_Caixa.Tramite(3);
            //Envia XMl e QrCode para impressão
            String Troco = String.valueOf(venda.getTroco());
            String QItem = String.valueOf(itens);
            
           // c.recebedados(caixa.getNomeImpressora(),Troco,QItem,Qr, urlxml);
      
      ApagaXML(XMLAntigo);
      System.err.println("Apagou?");
    
    }
    
    public static void ApagaXML(String CaminhoXMLAntigo){
        
        File file = new File( CaminhoXMLAntigo );
        file.delete();
    
    
    }

    public static void PegaValores(String CaminhoXMLCOntigencia){
       
        try{
            
            XMLAntigo = CaminhoXMLCOntigencia;
            
            
            for(Fiscal f : fiscalservice.findAll()){
            IdToken = f.getIdToken();
            CSC = f.getCSC();
            }
            
            
             //Pega o XML completo da nota gerada
            File inputFile = new File(CaminhoXMLCOntigencia);
            //File inputFile = new File("C:\\Users\\Marcos\\Desktop\\Notas Fiscais\\XML\\a.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document xml = builder.parse(inputFile);
            xml.getDocumentElement().normalize();
            
            //Id e versao
            NodeList infList = xml.getElementsByTagName("infNFe");
            Node infNode = infList.item(0);
            Element infElement = (Element) infNode;
            Id = infElement.getAttribute("Id");
            Versão = infElement.getAttribute("versao");
            
            // Dados do emitente vindos do xml
            NodeList emitList = xml.getElementsByTagName("emit");
            Node emitNode = emitList.item(0);
            Element emitElement = (Element) emitNode;
            CNPJ = emitElement.getElementsByTagName("CNPJ").item(0).getTextContent();//CNPJ do emitente
            XNome = emitElement.getElementsByTagName("xNome").item(0).getTextContent();//Razão Social ou Nome do emitente
            XFant = emitElement.getElementsByTagName("xFant").item(0).getTextContent();//Nome fantasia 
            XLgr = emitElement.getElementsByTagName("xLgr").item(0).getTextContent();//Logradouro
            Nro = emitElement.getElementsByTagName("nro").item(0).getTextContent();//Número
            Bairro = emitElement.getElementsByTagName("xBairro").item(0).getTextContent();//Bairro
            CMun = emitElement.getElementsByTagName("cMun").item(0).getTextContent();//Código do município
            XMun = emitElement.getElementsByTagName("xMun").item(0).getTextContent();//Nome do município
            UF = emitElement.getElementsByTagName("UF").item(0).getTextContent();//Sigla da UF
            CEP = emitElement.getElementsByTagName("CEP").item(0).getTextContent().replaceAll("[^0-9]", "");//Código do CEP
            Fone = emitElement.getElementsByTagName("fone").item(0).getTextContent().replaceAll("[^0-9]", "");//Telefone
            IE = emitElement.getElementsByTagName("IE").item(0).getTextContent().replaceAll("[^0-9]", "");//Inscrição Estadual do Emitente
            CRT = emitElement.getElementsByTagName("CRT").item(0).getTextContent();//Código de Regime Tributário
            
            
            
            // Dados do Destinatário vindo do xml
            NodeList destList = xml.getElementsByTagName("dest");
            if(destList.getLength() > 0){
                Node destNode = destList.item(0);
                Element destElement = (Element) destNode;
                CPF = destElement.getElementsByTagName("CPF").item(0).getTextContent();//CPF do destinatário
                Nome = destElement.getElementsByTagName("xNome").item(0).getTextContent();
                
                       
            }       

            
            // Dados dos Itens vindo do xml
            
            NodeList itensList = xml.getElementsByTagName("det");
            for(int i = 0; i < itensList.getLength(); i++){
                Node itensNode = itensList.item(i);
                Element itensElement = (Element) itensNode;
                Produto prod = new Produto();
                prod.setCodigo(itensElement.getElementsByTagName("cProd").item(0).getTextContent());//Código do produto ou serviçoprod.setCEAN(itensElement.getElementsByTagName("cEAN").item(0).getTextContent()); //GTIN (Global Trade Item Number) do produto, antigo código EAN ou código de barras
                prod.setDescricao(itensElement.getElementsByTagName("xProd").item(0).getTextContent()); //Descrição do produto ou serviço
               // prod.setNCM(itensElement.getElementsByTagName("NCM").item(0).getTextContent());//Código NCM com 8 dígitos
                prod.setCEST(itensElement.getElementsByTagName("CEST").item(0).getTextContent());//Código CEST
                prod.setCFOP(itensElement.getElementsByTagName("CFOP").item(0).getTextContent());//Código Fiscal de Operações e Prestações
                prod.setUnidade(itensElement.getElementsByTagName("uCom").item(0).getTextContent());//Unidade Comercial
                prod.setQuantidade(Float.parseFloat(itensElement.getElementsByTagName("qCom").item(0).getTextContent()));//Quantidade Comercial
                prod.setPrecoun(itensElement.getElementsByTagName("vUnCom").item(0).getTextContent());//Valor Unitário de Comercialização
                prod.setSubTotal(Float.parseFloat(itensElement.getElementsByTagName("vProd").item(0).getTextContent()));//Valor Total Bruto dos Produtos ou Serviços
                prod.setCEANTrib(itensElement.getElementsByTagName("cEANTrib").item(0).getTextContent());//GTIN (Global Trade Item Number) da unidade tributável, antigo código EAN ou código de barras 
                prod.setUnidade(itensElement.getElementsByTagName("uTrib").item(0).getTextContent());//Unidade Tributável
                //prod.setQuantidade(itensElement.getElementsByTagName("qTrib").item(0).getTextContent());//Quantidade Tributável
                prod.setPrecoun(itensElement.getElementsByTagName("vUnTrib").item(0).getTextContent());//Valor Unitário de tributação
                
                
                prod = produtoService.findProdutoById(new Long(prod.getCodigo()));
                   
                
                
                produtos.add(prod);
            }
             
             
            // Dados da nota vindos do XML
            // Informações de Totais
            NodeList totList = xml.getElementsByTagName("ICMSTot");
            Node totNode = totList.item(0);
            Element totElement = (Element) totNode;
            vbctot = Float.parseFloat(totElement.getElementsByTagName("vBC").item(0).getTextContent());
            Icmstot = Float.parseFloat(totElement.getElementsByTagName("vICMS").item(0).getTextContent());
            icmsDtot = Float.parseFloat(totElement.getElementsByTagName("vICMSDeson").item(0).getTextContent());
            vbcst  = Float.parseFloat(totElement.getElementsByTagName("vBCST").item(0).getTextContent());//Base de Cálculo do ICMS ST
            vst = Float.parseFloat(totElement.getElementsByTagName("vST").item(0).getTextContent());//Valor Total do ICMS ST
            vprod = Float.parseFloat(totElement.getElementsByTagName("vProd").item(0).getTextContent());//Valor Total dos produtos e serviços
            vpis = Float.parseFloat(totElement.getElementsByTagName("vPIS").item(0).getTextContent());//Valor do PIS
            vcofins = Float.parseFloat(totElement.getElementsByTagName("vCOFINS").item(0).getTextContent()); //Valor da COFINS
            ACobrar = Float.parseFloat(totElement.getElementsByTagName("vNF").item(0).getTextContent());//Valor Total da NF-e
            impostotot = Float.parseFloat(totElement.getElementsByTagName("vTotTrib").item(0).getTextContent());//Valor aproximado total de tributos federais
            
                    
            
            // Numero, Serie, Emissao ...etc
            NodeList ideList = xml.getElementsByTagName("ide");
            Node ideNode = ideList.item(0);
            Element ideElement = (Element) ideNode;
            //dataEmissao
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");   
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String data = f.format(date); 
            Uf = ideElement.getElementsByTagName("cUF").item(0).getTextContent();//Código da UF do emitente do Documento Fiscal
            Cnf = ideElement.getElementsByTagName("cNF").item(0).getTextContent();//Código Numérico que compõe a Chave de Acesso
            NatOp = ideElement.getElementsByTagName("natOp").item(0).getTextContent();//Descrição da Natureza da Operação
            Serie = ideElement.getElementsByTagName("serie").item(0).getTextContent(); //SErie
            NNF = ideElement.getElementsByTagName("nNF").item(0).getTextContent();//Número do Documento Fiscal
            DhEmi = ideElement.getElementsByTagName("dhEmi").item(0).getTextContent();//Data e hora de emissão do Documento
            CMun = ideElement.getElementsByTagName("cMunFG").item(0).getTextContent();//Código do Município de Ocorrência do Fato Gerador
            TpEmis = ideElement.getElementsByTagName("tpEmis").item(0).getTextContent();//Tipo de Emissão da NF-e
            CDV = ideElement.getElementsByTagName("cDV").item(0).getTextContent();//Dígito Verificador da Chave de Acesso daNF-e
            TpAmb = ideElement.getElementsByTagName("tpAmb").item(0).getTextContent();//Identificação do Ambiente
            
            
            // Chave, Protocolo, Data/Hora Autorização
            NodeList protList = xml.getElementsByTagName("enviNFe");
            Node protNode = protList.item(0);
            Element protElement = (Element) protNode;
            try{
                IdLote = Integer.parseInt(protElement.getElementsByTagName("idLote").item(0).getTextContent());
            }catch(Exception e){
                IdLote = 1;
            }
           
            
            //Dados de Pagamento vindo do XML
            NodeList pagList = xml.getElementsByTagName("pag");
            for(int i = 0; i < pagList.getLength(); i++){
                Node pagNode = pagList.item(i);
                Element pagElement = (Element) pagNode;
            try {
                //Dinheiro = 01 - Credito = 99 - Debito = 99
                if(i==0){
                    if(pagElement.getElementsByTagName("tPag").item(0).getTextContent().equals("01")){
                        venda.setModopagamento1("Dinheiro");
                    }
                    if(pagElement.getElementsByTagName("tPag").item(0).getTextContent().equals("99")){
                    venda.setModopagamento1("Cartão Crédito");
                    }
            System.out.println("Modo Pagamento 1: "+venda.getModopagamento1());
            venda.setRecebido1(Float.parseFloat(pagElement.getElementsByTagName("vPag").item(0).getTextContent()));
            System.out.println("Recebido 1: "+venda.getRecebido1());
            }
                 if(i==1){
            if(pagElement.getElementsByTagName("tPag").item(0).getTextContent().equals("01")){
                        venda.setModopagamento2("Dinheiro");
                    }
                    if(pagElement.getElementsByTagName("tPag").item(0).getTextContent().equals("99")){
                    venda.setModopagamento2("Cartão Crédito");
                    }
            venda.setRecebido2(Float.parseFloat(pagElement.getElementsByTagName("vPag").item(0).getTextContent()));
            }
                  if(i==2){
            if(pagElement.getElementsByTagName("tPag").item(0).getTextContent().equals("01")){
                        venda.setModopagamento3("Dinheiro");
                    }
                    if(pagElement.getElementsByTagName("tPag").item(0).getTextContent().equals("99")){
                    venda.setModopagamento3("Cartão Crédito");
                    }
            venda.setRecebido3(Float.parseFloat(pagElement.getElementsByTagName("vPag").item(0).getTextContent()));
            }
           
            } catch (Exception e) {
                System.out.println("Grupo obrigatório para a NFC-e, a critério da UF. Não informar para a NF-e: " + e);
                Logger.getLogger(EnvioNFCe.class.getName()).log(Level.SEVERE, null, e);
            }
            
            }
            
           
            
           Envia();
               
               
        } catch (SAXException ex) {
                //JOptionPane.showMessageDialog(null, "Erro ao Ler XML:  "+ex);
                Logger.getLogger(EnvioNFCe.class.getName()).log(Level.SEVERE, null, ex);
           // System.out.println("Erro ao Ler XML:  "+ex);
        } catch (IOException ex) {
                //JOptionPane.showMessageDialog(null, "Erro ao Ler XML:  "+ex);
                Logger.getLogger(EnvioNFCe.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("Erro ao Ler XML:  "+ex);
        } catch (ParserConfigurationException ex) {
           //JOptionPane.showMessageDialog(null, "Erro ao Ler XML:  "+ex);
           Logger.getLogger(EnvioNFCe.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("Erro ao Ler XML:  "+ex);
        }
       
        
    }
    
     //Informações dos Grupos do ICMS Para Regime Normal
   public static TNFe.InfNFe.Det.Imposto.ICMS.ICMS00 getICMS00(Produto p){
   
       System.err.println("ICMS 00");
       
       TNFe.InfNFe.Det.Imposto.ICMS.ICMS00 icms00 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMS00();
            icms00.setOrig(p.getCST().substring(0, 1)); //Origem da mercadoria (0)
            icms00.setCST(p.getCST().substring(1,3)); //Tributação do ICMS
            icms00.setModBC(p.getBCICMS());//Modalidade de determinação da BC do ICMS
            if(p.getBaseICMS().length()<=3){
            icms00.setVBC(p.getBaseICMS()+"0");//Valor da Base de Calculo do ICMS
            }else{
            icms00.setVBC(p.getBaseICMS());
            }
            icms00.setPICMS(p.getAliquotaICMS());//Alíquota do imposto
            icms00.setVICMS(p.getICMS());//Valor do ICMS
            
            Icmstot  = Icmstot + Float.parseFloat(p.getICMS());
            vbctot = vbctot + Float.parseFloat(p.getBaseICMS());
            icmsDtot = icmsDtot + 0;
            vbcst = vbcst + 0;
            vst = vst + 0;
            vprod = vprod + Float.valueOf(p.getPrecoun());
            vpis = vpis + Float.parseFloat(p.getVPIS());
             vcofins = vcofins +Float.valueOf(p.getVCOFINS());
            
      
             
   return icms00;
   }
   public static TNFe.InfNFe.Det.Imposto.ICMS.ICMS10 getICMS10(Produto p){
   
       System.err.println("ICMS 10");
       TNFe.InfNFe.Det.Imposto.ICMS.ICMS10 icms10 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMS10();
            icms10.setOrig(p.getCST().substring(0, 1)); //Origem da mercadoria (0)
            icms10.setCST(p.getCST().substring(1,3)); //Tributação do ICMS
            icms10.setModBC(p.getBCICMS());//Modalidade de determinação da BC do ICMS
            if(p.getBaseICMS().length()<=3){
            icms10.setVBC(p.getBaseICMS()+"0");//Valor da Base de Calculo do ICMS
            }else{
            icms10.setVBC(p.getBaseICMS());
            }
            icms10.setPICMS(p.getAliquotaICMS());//Alíquota do imposto
            icms10.setVICMS(p.getICMS());//Valor do ICMS
            icms10.setModBCST(p.getBCICMSST());//Modalidade de determinação da BC do ICMS ST
            icms10.setPMVAST(p.getPMVAST());//Percentual da margem de valor Adicionado do ICMS ST
            icms10.setPRedBCST(p.getPRedBCST());//Percentual da Redução de BC do ICMS ST
            icms10.setVBCST(p.getVBCST());//Valor da BC do ICMS ST
            icms10.setVICMSST(p.getPICMSST());//Valor do ICMS ST
            icms10.setPICMSST(p.getVICMSST());//Aliquota
       
            Icmstot  = Icmstot + Float.parseFloat(p.getICMS());
            vbctot = vbctot + Float.parseFloat(p.getBaseICMS());
            icmsDtot = icmsDtot +0;
            vbcst = vbcst + Float.parseFloat(p.getVBCST());
            vst = vst + Float.parseFloat(p.getPICMSST());
             vprod = vprod + Float.valueOf(p.getPrecoun());
             vpis = vpis + Float.parseFloat(p.getVPIS());
              vcofins = vcofins +Float.valueOf(p.getVCOFINS());
            
   return icms10;
   }
   public static TNFe.InfNFe.Det.Imposto.ICMS.ICMS20 getICMS20(Produto p){
   
       System.err.println("ICMS 20");
       
       TNFe.InfNFe.Det.Imposto.ICMS.ICMS20 icms20 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMS20();
            icms20.setOrig(p.getCST().substring(0, 1)); //Origem da mercadoria (0)
            icms20.setCST(p.getCST().substring(1,3)); //Tributação do ICMS
            icms20.setModBC(p.getBCICMS());//Modalidade de determinação da BC do ICMS
            icms20.setPRedBC(p.getPRedBC());//Percentual da Redução de BC
            if(p.getBaseICMS().length()<=3){
            icms20.setVBC(p.getBaseICMS()+"0");//Valor da Base de Calculo do ICMS
            }else{
            icms20.setVBC(p.getBaseICMS());
            }
            icms20.setPICMS(p.getAliquotaICMS());//Alíquota do imposto
            icms20.setVICMS(p.getICMS());//Valor do ICMS
            //icms20.setVICMSDeson("");//Valor do ICMS desonerado
            //icms20.setMotDesICMS("");//Motivo da desoneração do ICMS
            
            
            
            Icmstot  = Icmstot + Float.parseFloat(p.getICMS());
            vbctot = vbctot + Float.parseFloat(p.getBaseICMS());
            icmsDtot = icmsDtot + 0;
            vbcst = vbcst + 0;
             vprod = vprod + Float.valueOf(p.getPrecoun());
             vpis = vpis + Float.parseFloat(p.getVPIS());
              vcofins = vcofins +Float.valueOf(p.getVCOFINS());
              
            
   return icms20;
   }
   public static TNFe.InfNFe.Det.Imposto.ICMS.ICMS30 getICMS30(Produto p){
   
       System.err.println("ICMS 30");
       
       TNFe.InfNFe.Det.Imposto.ICMS.ICMS30 icms30 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMS30();
            icms30.setOrig(p.getCST().substring(0, 1)); //Origem da mercadoria (0)
            icms30.setCST(p.getCST().substring(1,3)); //Tributação do ICMS
            icms30.setModBCST(p.getBCICMSST());//Modalidade de determinação da BC do ICMS
            icms30.setPMVAST(p.getPMVAST());//Percentual da margem de valor Adicionado
            icms30.setPRedBCST(p.getPRedBCST());//Percentual da Redução de BC do ICMS ST
            icms30.setVBCST(p.getVBCST());//Valor da BC do ICMS ST
            icms30.setPICMSST(p.getVICMSST());//Alíquota do imposto do ICMS ST
            icms30.setVICMSST(p.getPICMSST());//Valor do ICMS ST
            //icms30.setVICMSDeson("");//Valor do ICMS desonerado
            //icms30.setMotDesICMS("");//Motivo da desoneração do ICMS
            
            //icmstot  = icmstot + 0;
            vbctot = vbctot + 0;
            vbcst = vbcst + Float.parseFloat(p.getVBCST());
            vst = vst + Float.parseFloat(p.getPICMSST());
             vprod = vprod + Float.valueOf(p.getPrecoun());
             vpis = vpis + Float.parseFloat(p.getVPIS());
             vcofins = vcofins +Float.valueOf(p.getVCOFINS());
             
   return icms30;
   }
   public static TNFe.InfNFe.Det.Imposto.ICMS.ICMS40 getICMS404150(Produto p){
   
       System.err.println("ICMS 40");
       
       TNFe.InfNFe.Det.Imposto.ICMS.ICMS40 icms40 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMS40();
            icms40.setOrig(p.getCST().substring(0, 1)); //Origem da mercadoria (0)
            icms40.setCST(p.getCST().substring(1,3)); //Tributação do ICMS
           // icms40.setVICMSDeson("");//Valor do ICMS
            //icms40.setMotDesICMS("");//Motivo da desoneração do ICMS
            
            if(p.getCST().substring(1,3).equals("50")){
            icmsDtot = icmsDtot + 0;
            
            }
            
             vprod = vprod + Float.valueOf(p.getPrecoun());
             vpis = vpis + Float.parseFloat(p.getVPIS());
              vcofins = vcofins +Float.valueOf(p.getVCOFINS());
            
   return icms40;
   }
   public static TNFe.InfNFe.Det.Imposto.ICMS.ICMS51 getICMS51(Produto p){
   
       System.err.println("ICMS 51");
       
       TNFe.InfNFe.Det.Imposto.ICMS.ICMS51 icms51 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMS51();
            icms51.setOrig(p.getCST().substring(0, 1)); //Origem da mercadoria (0)
            icms51.setCST(p.getCST().substring(1,3)); //Tributação do ICMS
            icms51.setModBC(p.getBCICMS());//Modalidade de determinação da BC do
            icms51.setPRedBC(p.getPRedBC());//Percentual da Redução de BC
            if(p.getBaseICMS().length()<=3){
            icms51.setVBC(p.getBaseICMS()+"0");//Valor da Base de Calculo do ICMS
            }else{
            icms51.setVBC(p.getBaseICMS());
            }
            icms51.setPICMS(p.getAliquotaICMS());//Alíquota do imposto
            icms51.setVICMSOp(p.getICMS());//Valor do ICMS da Operação - Cmo se não tivesse Diferimento
            icms51.setPDif(p.getPDif());//Percentual do diferimento
            icms51.setVICMSDif(p.getVICMSDif());//Valor do ICMS diferido
            icms51.setVICMS(p.getVICMS());//Valor do ICMS
            
            Icmstot  = Icmstot + Float.parseFloat(p.getVICMS());
            vbctot = vbctot + Float.parseFloat(p.getBaseICMS());
            icmsDtot = icmsDtot + 0;
            vbcst = vbcst + 0;
             vprod = vprod + Float.valueOf(p.getPrecoun());
            
            vpis = vpis + Float.parseFloat(p.getVPIS());
             vcofins = vcofins +Float.valueOf(p.getVCOFINS());
            
   return icms51;
   }
   public static TNFe.InfNFe.Det.Imposto.ICMS.ICMS60 getICMS60(Produto p){
    
       System.err.println("ICMS 60");
        
            TNFe.InfNFe.Det.Imposto.ICMS.ICMS60 icms60 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMS60();
            icms60.setOrig(p.getCST().substring(0, 1)); //Origem da mercadoria (0)
            icms60.setCST(p.getCST().substring(1,3)); //Tributação do ICMS
            //icms60.setVBCSTRet("xxx"); //Valor da BC do ICMS ST retido
            //icms60.setVICMSSTRet("xxx");//Valor do ICMS ST retido
     //icmstot  = icmstot + 0;
     vbctot = vbctot + 0;
     vbcst = vbcst + Float.parseFloat(p.getVBCST());
     vst = vst + Float.parseFloat(p.getPICMSST());
      vprod = vprod + Float.valueOf(p.getPrecoun());
      vpis = vpis + Float.parseFloat(p.getVPIS());
       vcofins = vcofins +Float.valueOf(p.getVCOFINS());
    
    return icms60;
    
    }
   public static TNFe.InfNFe.Det.Imposto.ICMS.ICMS70 getICMS70(Produto p){
    
       System.err.println("ICMS 70");
        
            TNFe.InfNFe.Det.Imposto.ICMS.ICMS70 icms70 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMS70();
            icms70.setOrig(p.getCST().substring(0, 1)); //Origem da mercadoria (0)
            icms70.setCST(p.getCST().substring(1,3)); //Tributação do ICMS
            icms70.setModBC(p.getBCICMS());//Modalidade de determinação da BC do
            icms70.setPRedBC(p.getPRedBC());//Percentual da Redução de BC
            if(p.getBaseICMS().length()<=3){
            icms70.setVBC(p.getBaseICMS()+"0");//Valor da Base de Calculo do ICMS
            }else{
            icms70.setVBC(p.getBaseICMS());
            }
            icms70.setPICMS(p.getAliquotaICMS());//Alíquota do imposto
            icms70.setVICMS(p.getICMS());//Valor do ICMS
            icms70.setModBCST(p.getBCICMSST());//Modalidade de determinação da BC do ICMS
            icms70.setPMVAST(p.getPMVAST());//Percentual da margem de valor Adicionado
            icms70.setVBCST(p.getVBCST());//Valor da BC do ICMS ST
            icms70.setPICMSST(p.getVICMSST());//Alíquota do imposto do ICMS ST
            icms70.setVICMSST(p.getPICMSST());//Valor do ICMS ST
            //icms70.setVICMSDeson("");//Valor do ICMS desonerado
            //icms70.setMotDesICMS("");//Motivo da desoneração do ICMS
            
     Icmstot  = Icmstot + Float.parseFloat(p.getICMS());
     vbctot = vbctot + Float.parseFloat(p.getBaseICMS());
     icmsDtot = icmsDtot + 0;
     vbcst = vbcst + Float.parseFloat(p.getVBCST());
     vst = vst + Float.parseFloat(p.getPICMSST());
      vprod = vprod + Float.valueOf(p.getPrecoun());
      vpis = vpis + Float.parseFloat(p.getVPIS());
       vcofins = vcofins +Float.valueOf(p.getVCOFINS());
    
    return icms70;
    
    }
   public static TNFe.InfNFe.Det.Imposto.ICMS.ICMS90 getICMS90(Produto p){
    
       System.err.println("ICMS 90");
        
            TNFe.InfNFe.Det.Imposto.ICMS.ICMS90 icms90 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMS90();
            icms90.setOrig(p.getCST().substring(0, 1)); //Origem da mercadoria (0)
            icms90.setCST(p.getCST().substring(1,3)); //Tributação do ICMS
            icms90.setModBC(p.getBCICMS());//Modalidade de determinação da BC do
            icms90.setPRedBC(p.getPRedBC());//Percentual da Redução de BC
            if(p.getBaseICMS().length()<=3){
            icms90.setVBC(p.getBaseICMS()+"0");//Valor da Base de Calculo do ICMS
            }else{
            icms90.setVBC(p.getBaseICMS());
            }
            icms90.setPICMS(p.getAliquotaICMS());//Alíquota do imposto
            icms90.setVICMS(p.getICMS());//Valor do ICMS
            icms90.setModBCST(p.getBCICMSST());//Modalidade de determinação da BC do ICMS
            icms90.setPMVAST(p.getPMVAST());//Percentual da margem de valor Adicionado
            icms90.setPRedBCST(p.getPRedBCST());//Percentual da Redução de BC do ICMS ST
            icms90.setVBCST(p.getVBCST());//Valor da BC do ICMS ST
            icms90.setPICMSST(p.getVICMSST());//Alíquota do imposto do ICMS ST
            icms90.setVICMSST(p.getPICMSST());//Valor do ICMS ST
           // icms90.setVICMSDeson("");//Valor do ICMS desonerado
            //icms90.setMotDesICMS("");//Motivo da desoneração do ICMS
            
        if(p.getICMS()!=null){
            Icmstot  = Icmstot + Float.parseFloat(p.getICMS());
        vbctot = vbctot + Float.parseFloat(p.getBaseICMS());}
        icmsDtot = icmsDtot + 0;
        vbcst = vbcst + Float.parseFloat(p.getVBCST());
        vst = vst + Float.parseFloat(p.getPICMSST());
         vprod = vprod + Float.valueOf(p.getPrecoun());
         vpis = vpis + Float.parseFloat(p.getVPIS());
          vcofins = vcofins +Float.valueOf(p.getVCOFINS());
    
    return icms90;
    
    }
   
    //Informações dos Grupos do ICMS para Simples Nacional
    public static ICMSSN101 getICMSSN101(Produto p){
        
        System.err.println("ICMS 101");
        
        ICMSSN101 icmssn101 = new ICMSSN101(); 
        icmssn101.setOrig(p.getCST().substring(0, 1));//Origem da Mercadoria
        icmssn101.setCSOSN(p.getCST().substring(1,4));//Código de Situação da Operação – Simples Nacional
        icmssn101.setPCredSN(p.getPCredSN());//Aliquota Aplicavel de calculo do credito
        icmssn101.setVCredICMSSN(p.getVCredICMSSN());//Valor crédito do ICMS que pode ser aproveitado

        Icmstot = Icmstot + 0;
        vbctot = vbctot + 0;
         vprod = vprod + Float.valueOf(p.getPrecoun());
         vpis = vpis + Float.parseFloat(p.getVPIS());
          vcofins = vcofins +Float.valueOf(p.getVCOFINS());
         
        return icmssn101;
    }
    public static ICMSSN102 getICMSSN102(Produto p){
        
        System.err.println("ICMS 102");
        
        ICMSSN102 icmssn102 = new ICMSSN102(); 
        icmssn102.setOrig(p.getCST().substring(0, 1));//Origem da Mercadoria
        icmssn102.setCSOSN(p.getCST().substring(1,4));//Código de Situação da Operação – Simples Nacional
       
        if(p.getCST().substring(1,4).equals("102")){
            Icmstot = Icmstot + Float.parseFloat(p.getICMS());
            vbctot = vbctot + Float.parseFloat(p.getBaseICMS());
            }
        if(p.getCST().substring(1,4).equals("103")){Icmstot = Icmstot + 0;}
        if(p.getCST().substring(1,4).equals("300")){Icmstot = Icmstot + 0;}
        if(p.getCST().substring(1,4).equals("400")){Icmstot = Icmstot + 0;}
        
         vprod = vprod + Float.valueOf(p.getPrecoun());
         vpis = vpis + Float.parseFloat(p.getVPIS());
          vcofins = vcofins +Float.valueOf(p.getVCOFINS());
          
         //System.out.println("CST: "+p.getCST().substring(1,4));
        return icmssn102;
    }
    public static ICMSSN201 getICMSSN201(Produto p){
        
        System.err.println("ICMS 201");
        
        ICMSSN201 icmssn201 = new ICMSSN201(); 
        icmssn201.setOrig(p.getCST().substring(0, 1));//Origem da Mercadoria
        icmssn201.setCSOSN(p.getCST().substring(1,4));//Código de Situação da Operação – Simples Nacional
        icmssn201.setModBCST(p.getBCICMS());//Modalidade de determinação da BC
        icmssn201.setPMVAST(p.getPMVAST());//Percentual da margem de valor Adicionado
        icmssn201.setPRedBCST(p.getPRedBCST());//Percentual da Redução de BC do ICMS ST
        icmssn201.setVBCST(p.getVBCST());//Valor da BC do ICMS ST
        icmssn201.setPICMSST(p.getVICMSST());//Alíquota do imposto do ICMS ST
        icmssn201.setVICMSST(p.getPICMSST());//Valor do ICMS ST
        icmssn201.setPCredSN(p.getPCredSN());//Aliquota Aplicavel de calculo do credito
        icmssn201.setVCredICMSSN(p.getVCredICMSSN());//Valor crédito do ICMS que pode ser aproveitado

        Icmstot = Icmstot + Float.parseFloat(p.getICMS());
        vbctot = vbctot + Float.parseFloat(p.getBaseICMS());
        vbcst = vbcst + Float.parseFloat(p.getVBCST());
        vst = vst + Float.parseFloat(p.getPICMSST());
         vprod = vprod + Float.valueOf(p.getPrecoun());
        vpis = vpis + Float.parseFloat(p.getVPIS());
         vcofins = vcofins +Float.valueOf(p.getVCOFINS());
        
        return icmssn201;
    }
    public static ICMSSN202 getICMSSN202(Produto p){
      
        System.err.println("ICMS 202");
        
        ICMSSN202 icmssn202 = new ICMSSN202();
        icmssn202.setOrig(p.getCST().substring(0, 1));//Origem da Mercadoria
        icmssn202.setCSOSN(p.getCST().substring(1,4));//Código de Situação da Operação – Simples Nacional
        icmssn202.setModBCST(p.getBCICMS());//Modalidade de determinação da BC
        icmssn202.setPMVAST(p.getPMVAST());//Percentual da margem de valor Adicionado
        icmssn202.setPRedBCST(p.getPRedBCST());//Percentual da Redução de BC do ICMS ST
        icmssn202.setVBCST(p.getVBCST());//Valor da BC do ICMS ST
        icmssn202.setPICMSST(p.getVICMSST());//Alíquota do imposto do ICMS ST
        icmssn202.setVICMSST(p.getPICMSST());//Valor do ICMS ST
        
        if(p.getCST().substring(1,4).equals("202")){
            Icmstot = Icmstot + Float.parseFloat(p.getICMS());
            vbctot = vbctot + Float.parseFloat(p.getBaseICMS());
            }
        vbcst = vbcst + Float.parseFloat(p.getVBCST());
        vst = vst + Float.parseFloat(p.getPICMSST());
         vprod = vprod + Float.valueOf(p.getPrecoun());
         vpis = vpis + Float.parseFloat(p.getVPIS());
          vcofins = vcofins +Float.valueOf(p.getVCOFINS());
        
        return icmssn202;
    }
    public static ICMSSN500 getICMSSN500(Produto p){
        
        System.err.println("ICMS 500");
        
        ICMSSN500 icmssn500 = new ICMSSN500();  
        icmssn500.setOrig(p.getCST().substring(0, 1));//Origem da Mercadoria
        icmssn500.setCSOSN(p.getCST().substring(1,4));//Código de Situação da Operação – Simples Nacional
        //icmssn500.setVBCSTRet();//Valor da BC do ICMS ST retido
        //icmssn500.setVICMSSTRet();//Valor do ICMS ST retido
        
        Icmstot = Icmstot + 0;
        vbctot = vbctot + 0;
        vbcst = vbcst + Float.parseFloat(p.getVBCST());
        vst = vst + Float.parseFloat(p.getPICMSST());
         vprod = vprod + Float.valueOf(p.getPrecoun());
         vpis = vpis + Float.parseFloat(p.getVPIS());
          vcofins = vcofins +Float.valueOf(p.getVCOFINS());
        return icmssn500;
    }
    public static ICMSSN900 getICMSSN900(Produto p){
        
        System.err.println("ICMS 900");
        
        ICMSSN900 icmssn900 = new ICMSSN900(); 
        icmssn900.setOrig(p.getCST().substring(0, 1));//Origem da Mercadoria
        icmssn900.setCSOSN(p.getCST().substring(1,4));//Código de Situação da Operação – Simples Nacional
        icmssn900.setModBC(p.getBCICMS());//Modalidade de determinação da BC
        if(p.getBaseICMS().length()<=3){
            icmssn900.setVBC(p.getBaseICMS()+"0");//Valor da Base de Calculo do ICMS
            }else{
            icmssn900.setVBC(p.getBaseICMS());
            }
        icmssn900.setPRedBC(p.getPRedBC());//Percentual da Redução de BC
        icmssn900.setPICMS(p.getAliquotaICMS());//Alíquota do imposto
        icmssn900.setVICMS(p.getICMS());//Valor do ICMS
        icmssn900.setModBCST(p.getPRedBCST());//Modalidade de determinação da BC do ICMS ST
        icmssn900.setPMVAST(p.getPMVAST());//Percentual da margem de valor Adicionado
        icmssn900.setPRedBCST(p.getPRedBCST());//Percentual da Redução de BC do ICMS ST
        icmssn900.setVBCST(p.getVBCST());//Valor da BC do ICMS ST
        icmssn900.setPICMSST(p.getVICMSST());//Aliquota do imposto do ICMS ST
        icmssn900.setVICMSST(p.getPICMSST());//Valor do ICMS ST
        icmssn900.setPCredSN(p.getPCredSN());//Aliquota aplicavel de calculo do crédito (SImples Nacional)
        icmssn900.setVCredICMSSN(p.getVCredICMSSN());//Valor crédito do ICMS que pode ser aproveitado nos termos do art. 23 da LC 123/2006 (Simples Nacional)
        
        if(p.getICMS()!=null){
            Icmstot  = Icmstot + Float.parseFloat(p.getICMS());
            vbctot = vbctot + Float.parseFloat(p.getBaseICMS());
        }
        vbcst = vbcst + Float.parseFloat(p.getVBCST());
        vst = vst + Float.parseFloat(p.getPICMSST());
        
         vprod = vprod + Float.valueOf(p.getPrecoun());
         vpis = vpis + Float.parseFloat(p.getVPIS());
         vcofins = vcofins +Float.valueOf(p.getVCOFINS());
         
        return icmssn900;
    }
   
   //Informações dos Grupos do PIS
   public static TNFe.InfNFe.Det.Imposto.PIS.PISAliq pisAli(Produto p){
   
   
       TNFe.InfNFe.Det.Imposto.PIS.PISAliq pisAliq = new TNFe.InfNFe.Det.Imposto.PIS.PISAliq();
            pisAliq.setCST(p.getCSTPIS());//Código de Situação Tributária do PIS
            if(p.getBaseICMS().length()<=3){
            pisAliq.setVBC(p.getBaseICMS()+"0");//Valor da Base de Calculo do ICMS
            }else{
            pisAliq.setVBC(p.getBaseICMS());
            }
            pisAliq.setPPIS(p.getPPIS());//Alíquota do PIS (em percentual)
            pisAliq.setVPIS(p.getVPIS());//Valor do PIS
   
   
   
   return pisAliq;
   }
   public static TNFe.InfNFe.Det.Imposto.PIS.PISQtde pisQtde(Produto p){
   
   
       TNFe.InfNFe.Det.Imposto.PIS.PISQtde pisqtde = new TNFe.InfNFe.Det.Imposto.PIS.PISQtde();
            pisqtde.setCST(p.getCSTPIS());//Código de Situação Tributária do PIS
            pisqtde.setQBCProd(String.valueOf(p.getQuantidade()));//Quantidade Vendida
            pisqtde.setVAliqProd(p.getPPIS());//Alíquota do PIS (em reais)
            pisqtde.setVPIS(p.getVPIS());//Valor do PIS
   
   
   
   return pisqtde;
   }
   public static TNFe.InfNFe.Det.Imposto.PIS.PISNT PISNT(Produto p){
   
   
       TNFe.InfNFe.Det.Imposto.PIS.PISNT naot = new TNFe.InfNFe.Det.Imposto.PIS.PISNT();
            naot.setCST(p.getCSTPIS());//Código de Situação Tributária do PIS
            
   
   return naot;
   }
   public static TNFe.InfNFe.Det.Imposto.PIS.PISOutr PISOutr(Produto p){
   
   
       TNFe.InfNFe.Det.Imposto.PIS.PISOutr pisoutr = new TNFe.InfNFe.Det.Imposto.PIS.PISOutr();
            pisoutr.setCST(p.getCSTPIS());//Código de Situação Tributária do PIS
            pisoutr.setPPIS(p.getPPIS());//Alíquota do PIS (em percentual)
            if(p.getBaseICMS().length()<=3){
            pisoutr.setVBC(p.getBaseICMS()+"0");//Valor da Base de Calculo do ICMS
            }else{
            pisoutr.setVBC(p.getBaseICMS());
            }
            pisoutr.setQBCProd(String.valueOf(p.getQuantidade()));//Quantidade Vendida
            pisoutr.setVAliqProd(p.getVPIS());//Alíquota do PIS (em reais)
            pisoutr.setVPIS(p.getVPIS());//Valor do PIS
   
   
   
   return pisoutr;
   }
   public static TNFe.InfNFe.Det.Imposto.PISST pisST(Produto p){
   
   
       TNFe.InfNFe.Det.Imposto.PISST pisoutr = new TNFe.InfNFe.Det.Imposto.PISST();
            
            pisoutr.setPPIS(p.getPPIS());//Alíquota do PIS (em percentual)
             if(p.getBaseICMS().length()<=3){
            pisoutr.setVBC(p.getBaseICMS()+"0");//Valor da Base de Calculo do ICMS
            }else{
            pisoutr.setVBC(p.getBaseICMS());
            }
            pisoutr.setQBCProd(String.valueOf(p.getQuantidade()));//Quantidade Vendida
            pisoutr.setVAliqProd(p.getPPIS());//Alíquota do PIS (em reais)
            pisoutr.setVPIS(p.getVPIS());//Valor do PIS
   
   
   
   return pisoutr;
   }   
   //Informações dos Grupos do COFINS
   public static TNFe.InfNFe.Det.Imposto.COFINS.COFINSAliq cofinsAliq(Produto p){
       
        TNFe.InfNFe.Det.Imposto.COFINS.COFINSAliq cofinsAliq = new TNFe.InfNFe.Det.Imposto.COFINS.COFINSAliq();
            cofinsAliq.setCST(p.getCSTCOFINS());//Código de Situação Tributária da COFINS
             if(p.getBaseICMS().length()<=3){
            cofinsAliq.setVBC(p.getBaseICMS()+"0");//Valor da Base de Calculo do ICMS
            }else{
            cofinsAliq.setVBC(p.getBaseICMS());
            }
            cofinsAliq.setPCOFINS(p.getPCOFINS());//Alíquota da COFINS (em percentual)
            cofinsAliq.setVCOFINS(p.getVCOFINS());//Valor da COFINS
   
   return cofinsAliq;
   }
   public static TNFe.InfNFe.Det.Imposto.COFINS.COFINSQtde cofinsQtde(Produto p){
      
        TNFe.InfNFe.Det.Imposto.COFINS.COFINSQtde cofinsQtde = new TNFe.InfNFe.Det.Imposto.COFINS.COFINSQtde();
            cofinsQtde.setCST(p.getCSTCOFINS());//Código de Situação Tributária da COFINS
            cofinsQtde.setQBCProd(String.valueOf(p.getQuantidade()));//Quantidade Vendida
            cofinsQtde.setVAliqProd(p.getPCOFINS());//Alíquota da COFINS (em reais)
            cofinsQtde.setVCOFINS(p.getVCOFINS());//Valor da COFINS
   
   return cofinsQtde;
   }
   public static TNFe.InfNFe.Det.Imposto.COFINS.COFINSNT cofinsNt(Produto p){
       
        TNFe.InfNFe.Det.Imposto.COFINS.COFINSNT cofinsNt = new TNFe.InfNFe.Det.Imposto.COFINS.COFINSNT();
            cofinsNt.setCST(p.getCSTCOFINS());//Código de Situação Tributária da COFINS
            
   
   return cofinsNt;
   }
   public static TNFe.InfNFe.Det.Imposto.COFINS.COFINSOutr cofinsOutr(Produto p){
       
        TNFe.InfNFe.Det.Imposto.COFINS.COFINSOutr cofinsOutr = new TNFe.InfNFe.Det.Imposto.COFINS.COFINSOutr();
            cofinsOutr.setCST(p.getCSTCOFINS());//Código de Situação Tributária da COFINS
             if(p.getBaseICMS().length()<=3){
            cofinsOutr.setVBC(p.getBaseICMS()+"0");//Valor da Base de Calculo do ICMS
            }else{
            cofinsOutr.setVBC(p.getBaseICMS());
            }
            cofinsOutr.setPCOFINS(p.getPCOFINS());//Alíquota da COFINS (em percentual)
            cofinsOutr.setQBCProd(String.valueOf(p.getQuantidade()));//Quantidade Vendida
            cofinsOutr.setVAliqProd(p.getPCOFINS());//Alíquota da COFINS (em reais)
            cofinsOutr.setVCOFINS(p.getVCOFINS());//Valor da COFINS
   
   return cofinsOutr;
   }
   
   //Calcula impostos federais, estaduais e municipais totais de cada produto e total
   public static String TotaisImpostos(Produto p){
   
       String totalImpostoProduto = null;
       float federalm = Float.parseFloat(p.getAFederalN());
       System.out.println("IMPOSTO FEDERAL N: "+federalm);
       float federali = Float.parseFloat(p.getAFederalI());
       System.out.println("IMPOSTO FEDERAL I: "+federali);
       float estadial = Float.parseFloat(p.getAEstadual());
       System.out.println("IMPOSTO Estadual: "+estadial);
       float municipal =Float.parseFloat(p.getAMunicipal());
       System.out.println("IMPOSTO MUNICIPAL: "+municipal);
                                       
       float totalimpostos = federalm + federali + estadial + municipal;
       
       BigDecimal bd = new BigDecimal(totalimpostos).setScale(3, RoundingMode.HALF_EVEN);
       totalImpostoProduto = String.valueOf(bd.doubleValue()).replaceAll(",", ".");
       
       if(totalImpostoProduto.length()<=3){
       totalImpostoProduto = totalImpostoProduto + "0";}
       System.err.println("Total Imposto Produto: "+totalImpostoProduto);
       
       impostotot = impostotot + totalimpostos;
       
       System.err.println("Total Imposto Compra: "+impostotot);
   
       return totalImpostoProduto;
   }
   //Salva a nfce vmitida no banco de dados
   
   private static Imposto getImposto(Produto p){  
       
            //imposto   - Tributos incidentes no Produto ou Serviço
            Imposto imposto = new Imposto();  
            //icms  - Informações do ICMS da Operação própria e ST
            ICMS icms = new ICMS();
            //pis   - Grupo PIS
            PIS pis = new PIS();
            //COFINS cofins = new COFINS();
            COFINS cofins = new COFINS();
            //PIS ST
            PISST pisst = new PISST();
        /*Tributos incidentes no Produto ou Serviço*/
            //imposto   - Tributos incidentes no Produto ou Serviço
            //icms  - Informações do ICMS da Operação própria e ST
            //Imposto imposto = new Imposto();
            //ICMS icms = new ICMS();
            if(CRT.equals("1")){ 
                //É Simples Nacional
                
                //Se PArtilha entre estados icms.setICMSPart(value);
                //Repasse via Substituto Tributário.icms.setICMSST(value);
                if(p.getCST().substring(1,4).equals("101")){icms.setICMSSN101(getICMSSN101(p));}
                if(p.getCST().substring(1,4).equals("102") || p.getCST().substring(1,4).equals("103") || p.getCST().substring(1,4).equals("300") || p.getCST().substring(1,4).equals("400")){icms.setICMSSN102(getICMSSN102(p));}
                if(p.getCST().substring(1,4).equals("201")){icms.setICMSSN201(getICMSSN201(p));}
                if(p.getCST().substring(1,4).equals("202") || p.getCST().equals("203")){icms.setICMSSN202(getICMSSN202(p));}
                if(p.getCST().substring(1,4).equals("500")){icms.setICMSSN500(getICMSSN500(p));}
                if(p.getCST().substring(1,4).equals("900")){icms.setICMSSN900(getICMSSN900(p));}
                  
            }else{ 
             //Não é Simples Nacional
                //Se PArtilha entre estados icms.setICMSPart(value);
                //Repasse via Substituto Tributário.icms.setICMSST(value);
                
                if(p.getCST().substring(1,3).equals("00")){icms.setICMS00(getICMS00(p));}
                if(p.getCST().substring(1,3).equals("10")){icms.setICMS10(getICMS10(p));}
                if(p.getCST().substring(1,3).equals("20")){icms.setICMS20(getICMS20(p));}
                if(p.getCST().substring(1,3).equals("30")){icms.setICMS30(getICMS30(p));}
                if(p.getCST().substring(1,3).equals("40") || p.getCST().substring(1,3).equals("41") || p.getCST().substring(1,3).equals("50")){
                    icms.setICMS40(getICMS404150(p));
                }
                if(p.getCST().substring(1,3).equals("51")){icms.setICMS51(getICMS51(p));}
                if(p.getCST().substring(1,3).equals("60")){icms.setICMS60(getICMS60(p));}
                if(p.getCST().substring(1,3).equals("70")){icms.setICMS70(getICMS70(p));}
                if(p.getCST().substring(1,3).equals("90")){icms.setICMS90(getICMS90(p));}
                
            }
            
           /*pis   - Grupo PIS
            PIS pis = new PIS();
           confins   - Grupo COFINS
           COFINS cofins = new COFINS();*/
           if(p.getCSTPIS().equals("01") || p.getCSTPIS().equals("02")){
                    pis.setPISAliq(pisAli(p));//Pega todos os dados do grupo tributado pela aliquota
                    cofins.setCOFINSAliq(cofinsAliq(p)); //pega dadps do Grupo COFINS tributado pela alíquota
           }if(p.getCSTPIS().equals("03")){
                    pis.setPISQtde(pisQtde(p));//Pega todos os dados do Grupo PIS tributado por Qtde
                    cofins.setCOFINSQtde(cofinsQtde(p)); //pega dadps do Grupo de COFINS tributado por Qtde
           }if(p.getCSTPIS().equals("04") || p.getCSTPIS().equals("05") || p.getCSTPIS().equals("06")
            || p.getCSTPIS().equals("07") || p.getCSTPIS().equals("08") || p.getCSTPIS().equals("09")){
                    pis.setPISNT(PISNT(p));//Pega todos os dados do Grupo PIS não tributado
                    cofins.setCOFINSNT(cofinsNt(p)); //pega dadps do Grupo COFINS não tributado
           }if(p.getCSTPIS().equals("01")==false && p.getCSTPIS().equals("02")==false && p.getCSTPIS().equals("04")==false 
            && p.getCSTPIS().equals("05")==false && p.getCSTPIS().equals("06")==false && p.getCSTPIS().equals("07")==false
            && p.getCSTPIS().equals("03")==false && p.getCSTPIS().equals("08")==false && p.getCSTPIS().equals("09")==false){
                    pis.setPISOutr(PISOutr(p));//Pega todos os dados do Grupo PIS Outras Operações
                    cofins.setCOFINSOutr(cofinsOutr(p)); //pega dadps do Grupo COFINS Outras Operações
           }/*if(SubsPIS){
             pisst = pisST(p);
           }*/
           
            //imposto.getContent().add(new ObjectFactory().createTNFeInfNFeDetImpostoVTotTrib(TotaisImpostos(p))); 
           
            //JAXBElement<PISST> pisstElement = new JAXBElement<PISST>(new QName("PISST"), PISST.class, pisst);
            //imposto.getContent().add(pisstElement);
            
            JAXBElement<ICMS> icmsElement = new JAXBElement<ICMS>(new QName("ICMS"), ICMS.class, icms);
            imposto.getContent().add(icmsElement);

            JAXBElement<PIS> pisElement = new JAXBElement<PIS>(new QName("PIS"), PIS.class, pis);
            imposto.getContent().add(pisElement);

            JAXBElement<COFINS> cofinsElement = new JAXBElement<COFINS>(new QName("COFINS"), COFINS.class, cofins);
            imposto.getContent().add(cofinsElement);
            
       return imposto;  
}  
   
   /*public static void salvanfce(){
   
       NotaNFCe n = new NotaNFCe();
       NotasNFCeDAO ndao = new NotasNFCeDAO();
       
       Locale locale = new Locale("pt","BR");
                GregorianCalendar calendar = new GregorianCalendar();
                SimpleDateFormat formatador = new SimpleDateFormat("YYYY-MM-dd hh:mm:ssXXX",locale);
                SimpleDateFormat formatador1 = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
                Date d = new Date();
                String data = formatador.format(d.getTime());
                String date = formatador1.format(d.getTime());
                data = data.replaceAll(" ", "T");
            DhEmi = data;//Data e hora de emissão do Documento
       
            n.setNumero(nota.getNumero());
            n.setCaixa(nota.getCaixa());
            n.setData(date);
            n.setForma(nota.getForma());
            n.setLoja(nota.getLoja());
            n.setSerie(nota.getSerie());
            n.setValor(String.valueOf(ACobrar).replaceAll(",", "."));
            n.setPagamento(venda.getModoPagamento1());
            ndao.ArmazenarNota(n);
            
            IdLote = ndao.Id();
   
   }*/
   
}
