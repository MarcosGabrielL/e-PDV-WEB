/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe;

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
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.COFINS;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.ICMS;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.PIS;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Prod;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Total.ICMSTot;
import br.com.swconsultoria.nfe.schema_4.retConsReciNFe.TRetConsReciNFe;
import br.com.swconsultoria.nfe.util.ConstantesUtil;
import com.softsaj.egdvweb.Vendas.Relatorios.DanfeNFCe_Contigencia;
//import static gerenciador.AcoesNFCe.ConsultarNFCe.iniciaConfigurações;
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

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import java.util.Base64;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import com.softsaj.egdvweb.Vendas.models.Adcionais;
import com.softsaj.egdvweb.Vendas.models.Caixa;
import com.softsaj.egdvweb.Vendas.models.Fiscal;
import com.softsaj.egdvweb.Vendas.models.Produto;
import com.softsaj.egdvweb.Vendas.models.Vendas;
import com.softsaj.egdvweb.Vendas.models.NotaNFCe;
import com.softsaj.egdvweb.Vendas.services.FiscalService;
import com.softsaj.egdvweb.Vendas.services.NotaNFCeService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Marcos
 */
public class EnvioNFCeContigencia {
    
    public static String Id,Versão,Uf,Cnf,NatOp,Serie,NNF,DhEmi,CMun,TpEmis,CDV,TpAmb,FinNFe,CNPJ,XNome,XFant;
   public static String XLgr,Nro,Bairro,XMun,UF,CEP,Fone,IE,CRT,CPF,Nome,IdToken,CSC;
   public static List<Produto> produtos;
   public static int Quantidade,itens,IdLote;
   public static float Totalcompra, ACobrar;
   public static Adcionais add;
   public static Vendas venda;
   public static NotaNFCe nota;
   public static String xmlfinal;
   public static ConfiguracoesNfe configuraçãoNFCE;
    public static float Icmstot,vbctot,icmsDtot,vbcst,vst,vprod,vpis,vcofins,impostotot;
    public static Caixa caixa;
    static DecimalFormat decimalFormat = new DecimalFormat( "0.00" );
    
      @Autowired
   private static FiscalService fiscalservice;
   @Autowired
   private static NotaNFCeService notaNFCeService;
    
    public static void Envia() {
        
        try {
            Icmstot = 0;vbctot = 0;icmsDtot = 0;vbcst = 0;vst = 0;vprod = 0;vpis = 0;vcofins = 0;impostotot = 0;
            //ConfiguracoesIniciaisNfe config = configuraçãoNFCE;  
           
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
            String Dh = data;//Data e hora de emissão do Documento

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
            ide.setDhEmi(Dh);//Data e hora de emissão do Documento
            ide.setTpNF("1");//Tipo de Operação
            ide.setIdDest("1");//Identificador de local de destino da operação
            ide.setIndFinal("1");//Operação ocorrer com Consumidor Final,
            ide.setIndPres("1");// Indicador de Presença do Comprador
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
            ide.setXJust("Falta de Internet");//Justificativa da entrada em contingência
            ide.setDhCont(DhEmi);//data e hora de entrada em contingência
            ide.setTpEmis("9");
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
            //det   - Detalhamento de Produtos e Serviços
            Det det = new Det();
            int i = 1;
            //imposto   - Tributos incidentes no Produto ou Serviço
            Imposto imposto = new Imposto();
            //Prod prod = new Prod();
            Prod prod = new Prod();
            //icms  - Informações do ICMS da Operação própria e ST
            ICMS icms = new ICMS();
            //pis   - Grupo PIS
            PIS pis = new PIS();
            //COFINS cofins = new COFINS();
            COFINS cofins = new COFINS();
            //PIS ST
            PISST pisst = new PISST();
            //imposto.getContent().add(new ObjectFactory().createTNFeInfNFeDetImpostoVTotTrib("0.00")); 
            for(Produto p : produtos){
            det.setNItem(""+i);
            i++;
            
                    /*Produtos e Serviços da NF-e*/
                    //prod  - Detalhamento de Produtos e Serviços
                    //Prod prod = new Prod();
                    prod.setCProd(p.getCodigo());//Código do produto ou serviço
                   if(p.getCEANTrib().equals("SEM GTIN")){
                        prod.setCEAN(p.getCEANTrib()); //GTIN (Global Trade Item Number) do produto, antigo código EAN ou código de barras
                    }else{
                    prod.setCEAN(p.getCodigo()); //GTIN (Global Trade Item Number) do produto, antigo código EAN ou código de barras
                    }
                    if(TpAmb.equals("2") && i==2){
                    prod.setXProd("NOTA FISCAL EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL");
                    }else{prod.setXProd(p.getDescricao()); //Descrição do produto ou serviço
                    }
                  //  prod.setNCM(p.getNCM());//Código NCM com 8 dígitos
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

            //infAdic   - Grupo de Informações Adicionais
            InfAdic infAdic = new InfAdic();
            infAdic.setInfCpl("Envio de NFCe em contigencia offLine");//Informações Complementares de interesse
            infNFe.setInfAdic(infAdic);

            
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
            //Tela_Caixa.Tramite(1);
            
            //MANDA TODAS INFORMAÇOES PRA NFE
            nfe.setInfNFe(infNFe);

           
            
            // Monta EnviNfe
            
            TEnviNFe enviNFe = new TEnviNFe();
            enviNFe.setVersao(ConstantesUtil.VERSAO.NFE);
            enviNFe.setIdLote(String.valueOf(IdLote));
            enviNFe.setIndSinc("1");
            enviNFe.getNFe().add(nfe);
            
            
            
            //QRCODE
            String qrCodeContigencia = null;
            try{
                qrCodeContigencia = NFCeUtil.getCodeQRCodeContingencia(
                infNFe.getId().substring(3),
                config.getAmbiente().getCodigo(),
                ide.getDhEmi(),
                total.getICMSTot().getVNF(),
                Base64.getEncoder().encodeToString(enviNFe.getNFe().get(0).getSignature().getSignedInfo().getReference().getDigestValue()),
                IdToken,
                CSC,
                WebServiceUtil.getUrl(config, DocumentoEnum.NFCE, ServicosEnum.URL_QRCODE));
            }catch(Exception e){
                qrCodeContigencia = "http://hnfe.sefaz.ba.gov.br/servicos/nfce/modulos/geral/NFCEC_consulta_chave_acesso.aspx?p=29190306202307000151650010000000149953553201|2|2|20|7.50|674565515643325841514452434751776b50324976794d334f4b383d|1|B279737103AEBE8F851340F2DDE3FDE24C3DEA51";
            }
            TNFe.InfNFeSupl infNFeSupl = new TNFe.InfNFeSupl();
            infNFeSupl.setQrCode(qrCodeContigencia);
            infNFeSupl.setUrlChave(WebServiceUtil.getUrl(config, DocumentoEnum.NFCE, ServicosEnum.URL_CONSULTANFCE));
            enviNFe.getNFe().get(0).setInfNFeSupl(infNFeSupl);
            
            // Monta e Assina o XML
            enviNFe = Nfe.montaNfe(config, enviNFe, true);
            salvanfce();
            
            System.err.println("AQUI!!!!!!!");
            //Valida Retorno Sincrono
            xmlfinal = XmlNfeUtil.objectToXml(enviNFe);
            System.err.println("XML FINAL: "+xmlfinal);
            

            //Salva Xml e manda valores para imprimir DANFE
            SalvarXML(qrCodeContigencia,xmlfinal);

           
            
         } catch (NfeException e) {
           // JOptionPane.showMessageDialog(null, "Erro ao Enviar Nota em Contigencia:  "+e.getMessage());
            System.out.println("Erro ao Enviar Nota:  "+e);
            Logger.getLogger(EnvioNFCeContigencia.class.getName()).log(Level.SEVERE, null, e);
        }catch (Exception ex) {
           // JOptionPane.showMessageDialog(null, "Erro ao Enviar Notaem Contigencia:  "+ex.getMessage());
            System.out.println("Erro ao Enviar Nota em Contigencia:  "+ex);
             Logger.getLogger(EnvioNFCeContigencia.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            //Tela_Caixa.produtos.clear();
            //Tela_Caixa.Item = 0;
        }
        
        

    }
    
     public static void SalvarXML(final String Qr,final String Conteudo){
          new Thread() {
                @Override
                public void run() {
        //TramiteNota.Info.setText("Salvando Nota...Aguarde");
        //Tela_Caixa.Tramite(2);
            DanfeNFCe_Contigencia c = new DanfeNFCe_Contigencia();
            //Salvar XML na pasta urlxml
            //Com nome de Nota
            Locale locale = new Locale("pt","BR");
                GregorianCalendar calendar = new GregorianCalendar();
                SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-YYYY_hh-mm-ss");
                Date d = new Date();
                String data = formatador.format(d.getTime());
            String Nota = "nnF"+nota.getNumero()+"D"+data+"_CONTIGENCIA";
             String path  = System.getProperty("user.home") + "\\Desktop";
            String urlDir = path+"\\Notas Fiscais\\XML_Conti";
            String urlxml = path+"\\Notas Fiscais\\XML_Conti\\"+Nota+".xml";
                
                File diretorio = new File(urlDir);      
                if (!diretorio.exists()) {
                    diretorio.mkdirs();
            }      
         
        try {
            Writer file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(urlxml),"UTF-8"));
            file.write(Conteudo);      
            file.close(); 
        } catch (UnsupportedEncodingException ex) {
           // JOptionPane.showMessageDialog(null, "Erro ao Salvar Nota:  "+ex);
        } catch (IOException ex) {
           // JOptionPane.showMessageDialog(null, "Erro ao Salvar Nota:  "+ex);
        }
            
            //TramiteNota.Info.setText("Imprimindo Nota...Aguarde");
             //Tela_Caixa.Tramite(3);
            //Envia XMl e QrCode para impressão
            String Troco = String.valueOf(venda.getTroco());
            String QItem = String.valueOf(itens);
            
            c.recebedados(caixa.getNomeImpressora(),Troco,QItem,Qr, urlxml,Nota);
      
      }
        }.start();
    
    }
    

    public static void EnviaValores(Caixa c, String token, String csc,NotaNFCe n,Vendas v, ConfiguracoesNfe Config, List<Produto> produtos1, int quantidade, int Item, Float SubTotalGeral, Adcionais a, Float ACobrar1, String Id1, String Versão1, String Uf1, String Cnf1, String NatOp1, String Serie1, String NNF1, String DhEmi1, String CMun1, String TpEmis1, String CDV1, String TpAmb1, String FinNFe1, String CNPJ1, String XNome1, String XFant1, String XLgr1, String Nro1, String Bairro1, String XMun1, String UF1, String CEP1, String Fone1, String IE1, String CRT1, String CPF1, String Nome1) {
       
        caixa = c;
        System.err.println("Nome Impressora: "+caixa.getNomeImpressora());
        
        
        IdToken = token;
        CSC = csc;
        
        nota = n;
        //System.out.println("Caixa - Nota: "+nota.getCaixa());
        //System.out.println("Data - Nota: "+nota.getData());
        //System.out.println("Forma - Nota: "+nota.getForma());
        //System.out.println("Loja - Nota: "+nota.getLoja());
        //System.out.println("Numero - Nota: "+nota.getNumero());
       // System.out.println("Serie - Nota: "+nota.getSerie());
        //System.out.println("Valor - Nota: "+nota.getValor());
        
         //Configurações da NotaFiscal
        configuraçãoNFCE = Config;
        //System.out.println("AMbiente Config: "+configuraçãoNFCE.getAmbiente());
        //System.out.println("Certificado Config: "+configuraçãoNFCE.getCertificado());
        //System.out.println("Estado Config: "+configuraçãoNFCE.getEstado());
        //System.out.println("Proxy Config: "+configuraçãoNFCE.getProxy());
        //System.out.println("PastaSchemas Config: "+configuraçãoNFCE.getPastaSchemas());
        //System.out.println("Versão Config: "+configuraçãoNFCE.getVersaoNfe());
        
        //Venda
        venda = v;
        //System.out.println("Venda - Caixa: "+venda.getCaixa());
        //System.out.println("Venda - Data: "+venda.getDataVenda());
        //System.out.println("Venda - Dia: "+venda.getDiaVenda());
        //System.out.println("Venda - Id: "+venda.getIdVendas());
        //System.out.println("Venda - Loja: "+venda.getLoja());
        //System.err.println("Venda - Pg1: "+venda.getModopagamento1());
       // System.err.println("Venda - Pg2: "+venda.getModopagamento2());
        //System.err.println("Venda - Pg3: "+venda.getModopagamento3());
        //System.err.println("Venda - Rc1: "+venda.getRecebido1());
        //System.err.println("Venda - Rc2: "+venda.getRecebido2());
        //System.err.println("Venda - Rc3: "+venda.getRecebido3());
       // System.out.println("Venda - Troco: "+venda.getTroco());
       // System.out.println("Venda - Valor: "+venda.getValor());
        
        //Lista de Produtos
        produtos = produtos1;
        if(produtos.isEmpty()){
        System.err.println("Vazio");
        }
        for(Produto p:produtos){
            System.err.println("Aliquota Estadual - Produto: "+p.getAEstadual());
            //System.err.println("Aliquota federal Importado - Produto: "+p.getAFederalI());
           // System.err.println("Aliquota Federal Nacional - Produto: "+p.getAFederalN());
           // System.err.println("Aliquota Municipal - Produto: "+p.getAMunicipal());
            //System.err.println("Aliquota Icms - Produto: "+p.getAliquotaICMS());
            //System.err.println("Base de calculo ICMS - Produto: "+p.getBCICMS());
            //System.err.println("Base de calculo ICMS ST - Produto: "+p.getBCICMSST());
            //System.err.println("Base do ICMS próprio - Produto: "+p.getBaseICMS());
            //System.err.println("CEAN - Produto: "+p.getCEAN());
            //System.err.println("CEANTrib: "+p.getCEANTrib());
            //System.err.println("CEST - Produto: "+p.getCEST());
            //System.err.println("CFOP - Produto: "+p.getCFOP());
            //System.err.println("CST - Produto: "+p.getCST());
            //System.err.println("CSTCOFINS - Produto: "+p.getCSTCOFINS());
            //System.err.println("CSTPIS - Produto: "+p.getCSTPIS());
            //System.err.println("Codigo - Produto: "+p.getCodigo());
            //System.err.println("Data - Produto: "+p.getData());
            //System.err.println("Descrição - Produto: "+p.getDescrição());
            //System.err.println("ICMS - Produto: "+p.getICMS());
            //System.err.println("Item - Produto: "+p.getItem());
            //System.err.println("Loja - Produto: "+p.getLoja());
            //System.err.println("NCM - Produto: "+p.getNCM());
            //System.err.println("PCOFINS - Produto: "+p.getPCOFINS());
           // System.err.println("PCredSN - Produto: "+p.getPCredSN());
           // System.err.println("PDif - Produto: "+p.getPDif());
           // System.err.println("PICMSST - Produto: "+p.getPICMSST());
           // System.err.println("PMVAST - Produto: "+p.getPMVAST());
           // System.err.println("PPIS - Produto: "+p.getPPIS());
           // System.err.println("PRedBC - Produto: "+p.getPRedBC());
           // System.err.println("PRedBCST - Produto: "+p.getPRedBCST());
           // System.err.println("PreçoUn - Produto: "+p.getPrecoun());
           // System.err.println("QTrib - Produto: "+p.getQTrib());
           // System.err.println("Quantidade - Produto: "+p.getQuantidade());
           // System.err.println("SubTotal - Produto: "+p.getSubTotal());
           // System.err.println("Tipo - Produto: "+p.getTipo());
           // System.err.println("Unidade - Produto: "+p.getUnidade());
           // System.err.println("UnidadeTributavel - Produto: "+p.getUnidadeTributavel());
           // System.err.println("VBCCOFINS - Produto: "+p.getVBCCOFINS());
           // System.err.println("VBCPIS - Produto: "+p.getVBCPIS());
           // System.err.println("VCOFINS - Produto: "+p.getVCOFINS());
           // System.err.println("VCredICMSSN - Produto: "+p.getVCredICMSSN());
           // System.err.println("tVICMS - Produto: "+p.getVICMS());
           // System.err.println("VICMSDif - Produto: "+p.getVICMSDif());
           // System.err.println("VICMSST - Produto: "+p.getVICMSST());
           // System.err.println("VPIS - Produto: "+p.getVPIS());
           // System.err.println("VUnTrib - Produto: "+p.getVUnTrib());
           // System.err.println("Validade - Produto: "+p.getValidade());
           // System.err.println("Ventrada - Produto: "+p.getVentrada());
       
        }
        //Quantidade de formas de pagamnto diferentes
        Quantidade = quantidade;
        //System.out.println("Quantidade de formas de pagamento: "+Quantidade);
        //Quantidade de itens
        itens = Item;
        //System.out.println("Quantidade de itens vendidos: "+itens);
        //Total dos produtos
        Totalcompra = SubTotalGeral;
        //System.out.println("Total dos produtos: "+Totalcompra);
        //Adicionais da compra
        add = a;
        //System.out.println("Adicionais de compra" + add);
        //Valor Total da compra com adicionais
        ACobrar = ACobrar1;
       // System.out.println("Valor Total da compra com adicionais"+ACobrar);
        //Valores da nota
        Id = Id1; //Identificador da TAG a ser assinada
        //System.out.println("Identificador da TAG a ser assinada: "+Id);
        //Versão do leiaute
        Versão = Versão1;
        //System.out.println("Versão do Leiaute: "+Versão);
        //Código da UF do emitente do Documento Fiscal
        Uf = Uf1;
        //System.out.println("Código da Uf: "+Uf);
        //Código Numérico que compõe a Chave de Acesso
        Cnf = Cnf1;
        //System.out.println("Código Numerico que compõe a Chave de Acesso: "+ Cnf);
        //Descrição da Natureza da Operação
        NatOp = NatOp1;
        //System.out.println("Natureza da Operação: "+NatOp);
        //Série do Documento Fiscal
        Serie = Serie1;
        //System.out.println("Série do Docuemnto Fiscal: "+Serie);
        //Número do Documento Fiscal
        NNF = NNF1;
        //System.out.println("Numero do Documento Fiscal: "+NNF);
        //Data e hora de emissão do Documento
        DhEmi = DhEmi1;
        System.out.println("Data e Hora Emissão: "+DhEmi);
        //Código do Município de Ocorrência do Fato Gerador
        CMun = CMun1;
        //System.out.println("Código do Municipio de Ocorrencia: "+CMun);
        //Tipo de Emissão da NF-e
        TpEmis = TpEmis1;
        //System.out.println("Tipo de Emissão:"+TpEmis);
        //Dígito Verificador da Chave de Acesso daNF-e
        CDV = CDV1; 
        //System.out.println("Digito Verificador: "+CDV);
        //Identificação do Ambiente
        TpAmb = TpAmb1;
        //System.out.println("Identificação do Ambiente: "+TpAmb);
        //Finalidade de emissão da NF-e
        FinNFe = FinNFe1;
        //System.out.println("Finalidade de Emissão: "+FinNFe);
        //CNPJ do emitente
        CNPJ = CNPJ1;
        //System.out.println("CNPJ do Emitente: "+CNPJ);
        //Razão Social ou Nome do emitente
        XNome = XNome1;
        //System.out.println("Razão Social do Emitente: "+XNome);
        //Nome fantasia
        XFant = XFant1;
        //System.out.println("Nome Fantasia: "+XFant);
        //Logradouro
        XLgr = XLgr1;
        //System.out.println("Logradouro: "+XLgr);
        //Número
        Nro = Nro1;
        //System.out.println("Numero: "+Nro);
        //Bairro
        Bairro = Bairro1;
        //System.out.println("Bairro: "+Bairro);
        //Nome do município
        XMun = XMun1;
        //System.out.println("XMun: "+XMun);
        //Sigla da UF
        UF = UF1;
        //System.out.println("Sigra da Uf: "+UF);
        //Código do CEP
        CEP = CEP1;
        //System.out.println("Código CEP: "+CEP);
        //Telefone
        Fone = Fone1;
        //System.out.println("Fone: "+Fone);
        //Inscrição Estadual do Emitente
        IE = IE1;
        //System.out.println("Inscrição Estadual: "+IE);
        //Código de Regime Tributário
        CRT = CRT1;
        //System.out.println("Código do Regime tributario: "+CRT);
        //CPF do destinatário
        CPF = CPF1;
        //System.out.println("CPF do destinatario: "+CPF);
        //Nome do Destinatario
        Nome = Nome1;
        //System.out.println("Nome do Destinatario: "+Nome);
         
        // Envia();
        
        
    }
    
     //Informações dos Grupos do ICMS Para Regime Normal
   public static TNFe.InfNFe.Det.Imposto.ICMS.ICMS00 getICMS00(Produto p){
   
       System.err.println("ICMS 00");
       
       TNFe.InfNFe.Det.Imposto.ICMS.ICMS00 icms00 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMS00();
            icms00.setOrig(p.getCST().substring(0, 1)); //Origem da mercadoria (0)
            icms00.setCST(p.getCST().substring(1,3)); //Tributação do ICMS
            icms00.setModBC(p.getBCICMS());//Modalidade de determinação da BC do ICMS
            icms00.setVBC(p.getBaseICMS());//Valor da Base de Calculo do ICMS
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
            icms10.setVBC(p.getBaseICMS());//Valor da BC do ICMS
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
            icms20.setVBC(p.getBaseICMS());//Valor da BC do ICMS
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
            icms51.setVBC(p.getBaseICMS());//Valor da BC do ICMS
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
            icms70.setVBC(p.getBaseICMS());//Valor da BC do ICMS
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
            icms90.setVBC(p.getBaseICMS());//Valor da BC do ICMS
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
       
        /*if(p.getCST().substring(1,4).equals("102")){
          Icmstot = Icmstot + 0;
            }
        if(p.getCST().substring(1,4).equals("103")){Icmstot = Icmstot + 0;}
        if(p.getCST().substring(1,4).equals("300")){Icmstot = Icmstot + 0;}
        if(p.getCST().substring(1,4).equals("400")){Icmstot = Icmstot + 0;}*/
        
            vbctot = vbctot + 0;
            Icmstot = Icmstot + 0;
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
        icmssn201.setModBCST(p.getBCICMSST());//Modalidade de determinação da BC do ICMS ST
        icmssn201.setPMVAST(p.getPMVAST());//Percentual da margem de valor Adicionado
        icmssn201.setPRedBCST(p.getPRedBCST());//Percentual da Redução de BC do ICMS ST
        icmssn201.setVBCST(p.getVBCST());//Valor da BC do ICMS ST
        icmssn201.setPICMSST(p.getVICMSST());//Alíquota do imposto do ICMS ST
        icmssn201.setVICMSST(p.getPICMSST());//Valor do ICMS ST
        icmssn201.setPCredSN(p.getPCredSN());//Aliquota Aplicavel de calculo do credito
        icmssn201.setVCredICMSSN(p.getVCredICMSSN());//Valor crédito do ICMS que pode ser aproveitado

        //Icmstot = Icmstot + Float.parseFloat(p.getICMS());
        //vbctot = vbctot + Float.parseFloat(p.getBaseICMS());
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
           // Icmstot = Icmstot + Float.parseFloat(p.getICMS());
            }
        //vbcst = vbcst + Float.parseFloat(p.getVBCST());
        vst = vst + Float.parseFloat(p.getPICMSST());
         vprod = vprod + Float.valueOf(p.getPrecoun());
         vpis = vpis + Float.parseFloat(p.getVPIS());
          vcofins = vcofins +Float.valueOf(p.getVCOFINS());
          
            vbctot = vbctot + Float.parseFloat(p.getBaseICMS());
        
        return icmssn202;
    }
    public static ICMSSN500 getICMSSN500(Produto p){
        
        System.err.println("ICMS 500");
        
        ICMSSN500 icmssn500 = new ICMSSN500();  
        icmssn500.setOrig(p.getCST().substring(0, 1));//Origem da Mercadoria
        icmssn500.setCSOSN(p.getCST().substring(1,4));//Código de Situação da Operação – Simples Nacional
       // icmssn500.setVBCSTRet(p.getVBCST());//Valor da BC do ICMS ST retido
       // icmssn500.setVICMSSTRet(p.setVICMSST());//Valor do ICMS ST retido
       // icmssn500.setPST(p.getPICMSST());
        
        Icmstot = Icmstot + 0;
        vbctot = vbctot + 0;
        //vbcst = vbcst + Float.parseFloat(p.getVBCST());
       // vst = vst + Float.parseFloat(p.getPICMSST());
         vprod = vprod + Float.valueOf(p.getPrecoun());
        // vpis = vpis + Float.parseFloat(p.getVPIS());
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
        }
        vbcst = vbcst + Float.parseFloat(p.getVBCST());
        vst = vst + Float.parseFloat(p.getPICMSST());
        
         vprod = vprod + Float.valueOf(p.getPrecoun());
         vpis = vpis + Float.parseFloat(p.getVPIS());
         vcofins = vcofins +Float.valueOf(p.getVCOFINS());
         
            vbctot = vbctot + Float.parseFloat(p.getBaseICMS());
         
        return icmssn900;
    }
   
   //Informações dos Grupos do PIS
   public static TNFe.InfNFe.Det.Imposto.PIS.PISAliq pisAli(Produto p){
   
   
       TNFe.InfNFe.Det.Imposto.PIS.PISAliq pisAliq = new TNFe.InfNFe.Det.Imposto.PIS.PISAliq();
            pisAliq.setCST(p.getCSTPIS());//Código de Situação Tributária do PIS
            pisAliq.setVBC(p.getVBCPIS());//Valor da Base de Cálculo do PIS
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
            pisoutr.setVBC(p.getVBCPIS());//Valor da Base de Cálculo do PIS
            pisoutr.setQBCProd(String.valueOf(p.getQuantidade()));//Quantidade Vendida
            pisoutr.setVAliqProd(p.getVPIS());//Alíquota do PIS (em reais)
            pisoutr.setVPIS(p.getVPIS());//Valor do PIS
   
   
   
   return pisoutr;
   }
   public static TNFe.InfNFe.Det.Imposto.PISST pisST(Produto p){
   
   
       TNFe.InfNFe.Det.Imposto.PISST pisoutr = new TNFe.InfNFe.Det.Imposto.PISST();
            
            pisoutr.setPPIS(p.getPPIS());//Alíquota do PIS (em percentual)
            pisoutr.setVBC(p.getVBCPIS());//Valor da Base de Cálculo do PIS
            pisoutr.setQBCProd(String.valueOf(p.getQuantidade()));//Quantidade Vendida
            pisoutr.setVAliqProd(p.getPPIS());//Alíquota do PIS (em reais)
            pisoutr.setVPIS(p.getVPIS());//Valor do PIS
   
   
   
   return pisoutr;
   }   
   //Informações dos Grupos do COFINS
   public static TNFe.InfNFe.Det.Imposto.COFINS.COFINSAliq cofinsAliq(Produto p){
       
        TNFe.InfNFe.Det.Imposto.COFINS.COFINSAliq cofinsAliq = new TNFe.InfNFe.Det.Imposto.COFINS.COFINSAliq();
            cofinsAliq.setCST(p.getCSTCOFINS());//Código de Situação Tributária da COFINS
            cofinsAliq.setVBC(p.getVBCCOFINS());//Valor da Base de Cálculo da COFINS
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
            cofinsOutr.setVBC(p.getVBCCOFINS());//Valor da Base de Cálculo da COFINS
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
       //System.out.println("IMPOSTO FEDERAL N: "+federalm);
       float federali = Float.parseFloat(p.getAFederalI());
       //System.out.println("IMPOSTO FEDERAL I: "+federali);
       float estadial = Float.parseFloat(p.getAEstadual());
       //System.out.println("IMPOSTO Estadual: "+estadial);
       float municipal =Float.parseFloat(p.getAMunicipal());
       //System.out.println("IMPOSTO MUNICIPAL: "+municipal);
                                       
       float totalimpostos = federalm + federali + estadial + municipal;
       
       BigDecimal bd = new BigDecimal(totalimpostos).setScale(3, RoundingMode.HALF_EVEN);
       totalImpostoProduto = String.valueOf(bd.doubleValue()).replaceAll(",", ".");
       //System.err.println("Total Imposto Produto: "+totalImpostoProduto);
       
       impostotot = impostotot + totalimpostos;
       //System.err.println("Total Imposto Compra: "+impostotot);
   
       return totalImpostoProduto;
   }
   //Salva a nfce vmitida no banco de dados
   
   public static void salvanfce(){
   
       NotaNFCe n = new NotaNFCe();
       
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
            //n.setPagamento(venda.getModopagamento1());
            notaNFCeService.addNotaNFCe(n);
            
            //IdLote = ndao.Id();
   
   }
   
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

    public static String getXmlfinal() {
        return xmlfinal;
    }

    public static void setXmlfinal(String xmlfinal) {
        EnvioNFCeContigencia.xmlfinal = xmlfinal;
    }
   
    
}
