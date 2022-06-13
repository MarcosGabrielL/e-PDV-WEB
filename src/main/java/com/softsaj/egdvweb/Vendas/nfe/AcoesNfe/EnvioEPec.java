/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.nfe.AcoesNfe;


import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.Evento;
import br.com.swconsultoria.nfe.dom.EventoEpec;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import br.com.swconsultoria.nfe.schema.envEpec.TEnvEvento;
import br.com.swconsultoria.nfe.schema.envEpec.TRetEnvEvento;
import br.com.swconsultoria.nfe.util.EpecUtil;
import br.com.swconsultoria.nfe.util.RetornoUtil;
import com.softsaj.egdvweb.Vendas.Relatorios.DanfeNFE_Contigencia;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import  com.softsaj.egdvweb.Vendas.models.Adcionais;
import  com.softsaj.egdvweb.Vendas.models.Caixa;
import  com.softsaj.egdvweb.Vendas.models.DDConsumidor;
import  com.softsaj.egdvweb.Vendas.models.Imagens;
import  com.softsaj.egdvweb.Vendas.models.NotaNFE;
import  com.softsaj.egdvweb.Vendas.models.Produto;
import  com.softsaj.egdvweb.Vendas.models.Transportadora;
import  com.softsaj.egdvweb.Vendas.models.Vendas;
import com.softsaj.egdvweb.Vendas.services.FiscalService;
import com.softsaj.egdvweb.Vendas.services.NotaNFCeService;
import com.softsaj.egdvweb.Vendas.services.NotaNFEService;
import org.springframework.beans.factory.annotation.Autowired;
/**
 *
 * @author Marcos
 */
public class EnvioEPec {
    
       @Autowired
   private static FiscalService fiscalservice;
   @Autowired
   private static NotaNFCeService notaNFCeService;
   
   @Autowired
   private static NotaNFEService NotaNFEService;
    
    public static String Id,Versão,Uf,Cnf,NatOp,Serie,NNF,DhEmi,CMun,TpEmis,CDV,TpAmb,FinNFe,CNPJ,XNome,XFant,IdToken,CSC;
   public static String XLgr,Nro,Bairro,XMun,UF,CEP,Fone,IE,CRT,CPF,Nome,Email,CNPJconsu;
   public static List<Produto> produtos;
   public static int Quantidade,itens,IdLote;
   public static float Totalcompra, ACobrar;
   public static Adcionais add;
   public static DDConsumidor cliente;
   public static Vendas venda;
   public static NotaNFE nota;
   public static ConfiguracoesNfe configuraçãoNFE;
   public static float Icmstot,vbctot,icmsDtot,vbcst,vst,vprod,vpis,vcofins,impostotot;
   public static Caixa caixa;
   static DecimalFormat decimalFormat = new DecimalFormat( "0.00" );
   //public static Informações informações;
   public static Transportadora Trans;
   public static String Logo;
    
    public static void Envia() {

        try {

            // Inicia As Configurações
            ConfiguracoesNfe config = configuraçãoNFE; 

            String chave = Id;
            String cnpj = CNPJ.replaceAll("[^0-9]", "");
            
            //Agora o evento pode aceitar uma lista de cancelaemntos para envio em Lote.
            //Para isso Foi criado o Objeto Epec
            Evento epec = new Evento();
            //Informe a chave da Epec
            epec.setChave(chave);
            //Informe o CNPJ do emitente
            epec.setCnpj(cnpj);
            //Informe a data do EPEC
            epec.setDataEvento(LocalDateTime.now());
            //Preenche os Dados do Evento EPEC
            EventoEpec eventoEpec = new EventoEpec();
            if(CPF == null){ 
            eventoEpec.setCnpjDestinatario(cliente.getCNPJ());
            }else{
              eventoEpec.setCnpjDestinatario(cliente.getCPF());
            }
            BigDecimal ic = new BigDecimal(Icmstot).setScale(2, RoundingMode.HALF_EVEN);
            BigDecimal vs = new BigDecimal(vst).setScale(2, RoundingMode.HALF_EVEN);
            BigDecimal aco = new BigDecimal(ACobrar).setScale(2, RoundingMode.HALF_EVEN);
            
            if(decimalFormat.format(vs.doubleValue()).replaceAll(",", ".").length()<=3){
                    eventoEpec.setvST(decimalFormat.format(vs.doubleValue()).replaceAll(",", ".")+"0");
                    }else{
                    eventoEpec.setvST(decimalFormat.format(vs.doubleValue()).replaceAll(",", "."));
                    }
            
            eventoEpec.setvNF(decimalFormat.format(aco.doubleValue()).replaceAll(",", "."));
            eventoEpec.setvICMS(decimalFormat.format(ic.doubleValue()).replaceAll(",", "."));
            eventoEpec.setTipoNF("1");
            eventoEpec.setIeEmitente(IE.replaceAll("[^0-9]", ""));
            eventoEpec.setIeDestinatario(cliente.getIE());
            eventoEpec.setEstadoDestinatario(EstadosEnum.GO);
            epec.setEventoEpec(eventoEpec);

            //Monta o Evento de Cancelamento
            TEnvEvento enviEvento = EpecUtil.montaEpec(epec,config);

            //Envia Evento EPEC
            TRetEnvEvento retorno = Nfe.enviarEpec(config, enviEvento, true);

            //Valida o Retorno do Cancelamento
            RetornoUtil.validaEpec(retorno);

            //Resultado
            System.out.println();
            retorno.getRetEvento().forEach( resultado -> {
                System.out.println("# Chave: " + resultado.getInfEvento().getChNFe());
                System.out.println("# Status: " + resultado.getInfEvento().getCStat() + " - " + resultado.getInfEvento().getXMotivo());
                System.out.println("# Protocolo: " + resultado.getInfEvento().getNProt());
            });
            //Cria ProcEvento de Cacnelamento
            String proc = EpecUtil.criaProcEventoEpec(config, enviEvento, retorno);
            System.out.println();
            System.out.println("# ProcEvento : " + proc);

            //System.out.println(XmlUtil.objectToXml(procEvento));
            //SalvarXML(XmlUtil.objectToXml(procEvento));
        } catch (Exception e) {
            System.err.println();
            System.err.println("# Erro: "+e.getMessage());
        }
        
        

    }
    
      public static void EnviaValores(String image,Transportadora transportadora,Caixa c, String token, String csc, NotaNFE n,Vendas v, ConfiguracoesNfe Config, List<Produto> produtos1, int quantidade, int Item, Float SubTotalGeral, Adcionais a, Float ACobrar1, String Id1, String Versão1, String Uf1, String Cnf1, String NatOp1, String Serie1, String NNF1, String DhEmi1, String CMun1, String TpEmis1, String CDV1, String TpAmb1, String FinNFe1, String CNPJ1, String XNome1, String XFant1, String XLgr1, String Nro1, String Bairro1, String XMun1, String UF1, String CEP1, String Fone1, String IE1, String CRT1,DDConsumidor consumidor) {
       
        caixa = c;
       // informações = info;
        if(TpAmb1.equals("2")){
        IdToken = "1234567890";
        CSC = "000001";
        }else{
        IdToken = token;
        CSC = csc;
        }
        
        Trans = transportadora;
        cliente = consumidor;
        Logo = image;
        
        nota = n;
        //System.out.println("Caixa - Nota: "+nota.getCaixa());
        //System.out.println("Data - Nota: "+nota.getData());
        //System.out.println("Forma - Nota: "+nota.getForma());
        //System.out.println("Loja - Nota: "+nota.getLoja());
        //System.out.println("Numero - Nota: "+nota.getNumero());
        //System.out.println("Serie - Nota: "+nota.getSerie());
        //System.out.println("Valor - Nota: "+nota.getValor());
        
         //Configurações da NotaFiscal
        configuraçãoNFE = Config;
        //System.out.println("AMbiente Config: "+configuraçãoNFE.getAmbiente());
        //System.out.println("Certificado Config: "+configuraçãoNFE.getCertificado());
        //System.out.println("Estado Config: "+configuraçãoNFE.getEstado());
        //System.out.println("Proxy Config: "+configuraçãoNFE.getProxy());
        //System.out.println("PastaSchemas Config: "+configuraçãoNFE.getPastaSchemas());
        //System.out.println("Versão Config: "+configuraçãoNFE.getVersaoNfe());
        
        //Venda
        venda = v;
        //System.out.println("Venda - Caixa: "+venda.getCaixa());
        //System.out.println("Venda - Data: "+venda.getDataVenda());
        //System.out.println("Venda - Dia: "+venda.getDiaVenda());
        //System.out.println("Venda - Id: "+venda.getIdVendas());
        //System.out.println("Venda - Loja: "+venda.getLoja());
        //System.out.println("Venda - Pg1: "+venda.getModoPagamento1());
        //System.out.println("Venda - Pg2: "+venda.getModoPagamento2());
        //System.out.println("Venda - Pg3: "+venda.getModoPagamento3());
        //System.out.println("Venda - Rc1: "+venda.getRecebido1());
        //System.out.println("Venda - Rc2: "+venda.getRecebido2());
        //System.out.println("Venda - Rc3: "+venda.getRecebido3());
        //System.out.println("Venda - Troco: "+venda.getTroco());
        //System.out.println("Venda - Valor: "+venda.getValor());
        
        //Lista de Produtos
        produtos = produtos1;
        if(produtos.isEmpty()){ System.err.println("Lista de Produtos Vazia");}
       /* for(Produto p:produtos){
            System.err.println("Aliquota Estadual - Produto: "+p.getAEstadual());
            System.err.println("Aliquota federal Importado - Produto: "+p.getAFederalI());
            System.err.println("Aliquota Federal Nacional - Produto: "+p.getAFederalN());
            System.err.println("Aliquota Municipal - Produto: "+p.getAMunicipal());
            System.err.println("Aliquota Icms - Produto: "+p.getAliquotaICMS());
            System.err.println("Base de calculo ICMS - Produto: "+p.getBCICMS());
            System.err.println("Base de calculo ICMS ST - Produto: "+p.getBCICMSST());
            System.err.println("Base do ICMS próprio - Produto: "+p.getBaseICMS());
            System.err.println("CEAN - Produto: "+p.getCEAN());
            System.err.println("CEANTrib: "+p.getCEANTrib());
            System.err.println("CEST - Produto: "+p.getCEST());
            System.err.println("CFOP - Produto: "+p.getCFOP());
            System.err.println("CST - Produto: "+p.getCST());
            System.err.println("CSTCOFINS - Produto: "+p.getCSTCOFINS());
            System.err.println("CSTPIS - Produto: "+p.getCSTPIS());
            System.err.println("Codigo - Produto: "+p.getCodigo());
            System.err.println("Data - Produto: "+p.getData());
            System.err.println("Descrição - Produto: "+p.getDescrição());
            System.err.println("ICMS - Produto: "+p.getICMS());
            System.err.println("Item - Produto: "+p.getItem());
            System.err.println("Loja - Produto: "+p.getLoja());
            System.err.println("NCM - Produto: "+p.getNCM());
            System.err.println("PCOFINS - Produto: "+p.getPCOFINS());
            System.err.println("PCredSN - Produto: "+p.getPCredSN());
            System.err.println("PDif - Produto: "+p.getPDif());
            System.err.println("PICMSST - Produto: "+p.getPICMSST());
            System.err.println("PMVAST - Produto: "+p.getPMVAST());
            System.err.println("PPIS - Produto: "+p.getPPIS());
            System.err.println("PRedBC - Produto: "+p.getPRedBC());
            System.err.println("PRedBCST - Produto: "+p.getPRedBCST());
            System.err.println("PreçoUn - Produto: "+p.getPreçoUn());
            System.err.println("QTrib - Produto: "+p.getQTrib());
            System.err.println("Quantidade - Produto: "+p.getQuantidade());
            System.err.println("SubTotal - Produto: "+p.getSubTotal());
            System.err.println("Tipo - Produto: "+p.getTipo());
            System.err.println("Unidade - Produto: "+p.getUnidade());
            System.err.println("UnidadeTributavel - Produto: "+p.getUnidadeTributavel());
            System.err.println("VBCCOFINS - Produto: "+p.getVBCCOFINS());
            System.err.println("VBCPIS - Produto: "+p.getVBCPIS());
            System.err.println("VCOFINS - Produto: "+p.getVCOFINS());
            System.err.println("VCredICMSSN - Produto: "+p.getVCredICMSSN());
            System.err.println("tVICMS - Produto: "+p.getVICMS());
            System.err.println("VICMSDif - Produto: "+p.getVICMSDif());
            System.err.println("VICMSST - Produto: "+p.getVICMSST());
            System.err.println("VPIS - Produto: "+p.getVPIS());
            System.err.println("VUnTrib - Produto: "+p.getVUnTrib());
            System.err.println("Validade - Produto: "+p.getValidade());
            System.err.println("Ventrada - Produto: "+p.getVentrada());
       
        }*/
        //Quantidade de formas de pagamnto diferentes
        Quantidade = quantidade;
       //  System.out.println("Quantidade de formas de pagamento: "+Quantidade);
        //Quantidade de itens
        itens = Item;
        //System.out.println("Quantidade de itens vendidos: "+itens);
        //Total dos produtos
        Totalcompra = SubTotalGeral;
      //  System.out.println("Total dos produtos: "+Totalcompra);
        //Adicionais da compra
        add = a;
     //   System.out.println("Adicionais de compra" + add);
        //Valor Total da compra com adicionais
        ACobrar = ACobrar1;
      //  System.out.println("Valor Total da compra com adicionais"+ACobrar);
        //Valores da nota
        Id = Id1; //Identificador da TAG a ser assinada
     //   System.out.println("Identificador da TAG a ser assinada: "+Id);
        //Versão do leiaute
        Versão = Versão1;
    //    System.out.println("Versão do Leiaute: "+Versão);
        //Código da UF do emitente do Documento Fiscal
        Uf = Uf1;
    //    System.out.println("Código da Uf: "+Uf);
        //Código Numérico que compõe a Chave de Acesso
        Cnf = Cnf1;
    //    System.out.println("Código Numerico que compõe a Chave de Acesso: "+ Cnf);
        //Descrição da Natureza da Operação
        NatOp = NatOp1;
    //    System.out.println("Natureza da Operação: "+NatOp);
        //Série do Documento Fiscal
        Serie = Serie1;
    //    System.out.println("Série do Docuemnto Fiscal: "+Serie);
        //Número do Documento Fiscal
        NNF = NNF1;
    //    System.out.println("Numero do Documento Fiscal: "+NNF);
        //Data e hora de emissão do Documento
        DhEmi = DhEmi1;
    //    System.out.println("Data e Hora Emissão: "+DhEmi);
        //Código do Município de Ocorrência do Fato Gerador
        CMun = CMun1;
    //    System.out.println("Código do Municipio de Ocorrencia: "+CMun);
        //Tipo de Emissão da NF-e
        TpEmis = TpEmis1;
    //    System.out.println("Tipo de Emissão:"+TpEmis);
        //Dígito Verificador da Chave de Acesso daNF-e
        CDV = CDV1; 
    //    System.out.println("Digito Verificador: "+CDV);
        //Identificação do Ambiente
        TpAmb = TpAmb1;
    //    System.out.println("Identificação do Ambiente: "+TpAmb);
        //Finalidade de emissão da NF-e
        FinNFe = FinNFe1;
    //    System.out.println("Finalidade de Emissão: "+FinNFe);
        //CNPJ do emitente
        CNPJ = CNPJ1;
    //    System.out.println("CNPJ do Emitente: "+CNPJ);
        //Razão Social ou Nome do emitente
        XNome = XNome1;
    //    System.out.println("Razão Social do Emitente: "+XNome);
        //Nome fantasia
        XFant = XFant1;
    //    System.out.println("Nome Fantasia: "+XFant);
        //Logradouro
        XLgr = XLgr1;
    //    System.out.println("Logradouro: "+XLgr);
        //Número
        Nro = Nro1;
    //    System.out.println("Numero: "+Nro);
        //Bairro
        Bairro = Bairro1;
    //    System.out.println("Bairro: "+Bairro);
        //Nome do município
        XMun = XMun1;
    //    System.out.println("XMun: "+XMun);
        //Sigla da UF
        UF = UF1;
    //    System.out.println("Sigra da Uf: "+UF);
        //Código do CEP
        CEP = CEP1;
    //    System.out.println("Código CEP: "+CEP);
        //Telefone
        Fone = Fone1;
    //    System.out.println("Fone: "+Fone);
        //Inscrição Estadual do Emitente
        IE = IE1;
    //    System.out.println("Inscrição Estadual: "+IE);
        //Código de Regime Tributário
        CRT = CRT1;
    //    System.out.println("Código do Regime tributario: "+CRT);
        //CPF do destinatário
        CPF = consumidor.getCPF();
    //    System.out.println("CPF do destinatario: "+CPF);
        //CNPJ do destinatário
        CNPJconsu = consumidor.getCNPJ();
    //    System.out.println("CNPJ do destinatario: "+CNPJconsu);
        //Nome do Destinatario
        Nome = consumidor.getNome();
    //    System.out.println("Nome do Destinatario: "+Nome);
        //Email do Destinatario
        Email = consumidor.getEmail();
    //    System.out.println("Email do Destinatario: "+Email); 
        
        Envia();
        
    }
   
   public static void SalvarXML(String Conteudo){
    
        
       // EmitirNFE.Tramite("Salvando Nota...","...AGUARDE!!");
            DanfeNFE_Contigencia c = new DanfeNFE_Contigencia();
            //Salvar XML na pasta urlxml
            //Com nome de Nota
            Locale locale = new Locale("pt","BR");
                GregorianCalendar calendar = new GregorianCalendar();
                SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-YYYY_hh-mm-ss");
                Date d = new Date();
                String data = formatador.format(d.getTime());
            String Nota = "nnF"+nota.getNumero()+"D"+data;
            String path  = System.getProperty("user.home") + "\\Desktop";
            String urlDir = path+"\\Notas Fiscais\\XML_NFE_Conti";
            String urlxml = path+"\\Notas Fiscais\\XML_NFE_Conti\\"+Nota+".xml";
                
                File diretorio = new File(urlDir);      
                if (!diretorio.exists()) {
                    diretorio.mkdirs();
            }      
         
        try {
            Writer file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(urlxml),"UTF-8"));
            file.write(Conteudo);      
            file.close(); 
        } catch (UnsupportedEncodingException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Salvar Nota:  "+ex);
             System.out.println("Erro ao Salvar Nota:  "+ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Salvar Nota:  "+ex);
            System.out.println("Erro ao Salvar Nota:  "+ex);
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Salvar Nota:  "+ex);
            System.out.println("Erro ao Salvar Nota:  "+ex);
        }
            
            //TramiteNota.Info.setText("Imprimindo Nota...Aguarde");
            //  EmitirNFE.Tramite("Imprimindo Nota","...Aguarde!!");
            //Envia XMl e QrCode para impressão
            String Troco = String.valueOf(venda.getTroco());
            String QItem = String.valueOf(itens);
            
            c.recebedados(caixa.getNomeImpressora(),Troco,QItem,urlxml,Id.substring(3),Nota,cliente,Trans,Logo);
      
      
    
    }
    
   public static void SalvarXMLErro(){
    new Thread() {
                @Override
                public void run() {
        //TramiteNota.Info.setText("Salvando Nota...Aguarde");
       // EmitirNFE.Tramite("Salvando Nota...","...AGUARDE!!");
        String Conteudo = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><enviNFe xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"4.00\"><idLote>0</idLote><indSinc>1</indSinc><NFe><infNFe Id=\"NFe29181111951317000120650010000001721805847804\" versao=\"4.00\"><ide><cUF>29</cUF><cNF>80584780</cNF><natOp>Venda</natOp><mod>65</mod><serie>1</serie><nNF>172</nNF><dhEmi>2018-11-06T03:51:21-03:00</dhEmi><tpNF>1</tpNF><idDest>1</idDest><cMunFG>2928703</cMunFG><tpImp>4</tpImp><tpEmis>1</tpEmis><cDV>4</cDV><tpAmb>2</tpAmb><finNFe>1</finNFe><indFinal>1</indFinal><indPres>1</indPres><procEmi>0</procEmi><verProc>eGDV 1.0</verProc></ide><emit><CNPJ>11951317000120</CNPJ><xNome>G D S SUPERMERCADO LTDA - ME</xNome><xFant>SUPERMERCADO GERMAN</xFant><enderEmit><xLgr>Cosme e Damião</xLgr><nro>440</nro><xCpl>Casa</xCpl><xBairro>Andaia</xBairro><cMun>2928703</cMun><xMun>Santo Antônio de Jesus</xMun><UF>BA</UF><CEP>44572420</CEP><cPais>1058</cPais><xPais>BRASIL</xPais><fone>75988047867</fone></enderEmit><IE>087790081</IE><CRT>1</CRT></emit><det nItem=\"1\"><prod><cProd>7894900011517</cProd><cEAN>7894900011517</cEAN><xProd>REFRIGERANTE COCA COLA 2L</xProd><NCM>22021000</NCM><CEST>0301000</CEST><indEscala>S</indEscala><CFOP>5102</CFOP><uCom>UN</uCom><qCom>1.0</qCom><vUnCom>6.50</vUnCom><vProd>6.50</vProd><cEANTrib>7894900011517</cEANTrib><uTrib>UN</uTrib><qTrib>1.0</qTrib><vUnTrib>6.50</vUnTrib><indTot>1</indTot></prod><imposto><ICMS><ICMSSN102><orig>0</orig><CSOSN>102</CSOSN></ICMSSN102></ICMS><PIS><PISNT><CST>04</CST></PISNT></PIS><COFINS><COFINSNT><CST>04</CST></COFINSNT></COFINS></imposto></det><det nItem=\"2\"><prod><cProd>7894900011609</cProd><cEAN>7894900011609</cEAN><xProd>REFRIGERANTE COCA COLA 600ML</xProd><NCM>22021000</NCM><CEST>0301000</CEST><indEscala>S</indEscala><CFOP>5102</CFOP><uCom>UN</uCom><qCom>1.0</qCom><vUnCom>6.50</vUnCom><vProd>6.50</vProd><cEANTrib>7894900011609</cEANTrib><uTrib>UN</uTrib><qTrib>1.0</qTrib><vUnTrib>6.50</vUnTrib><indTot>1</indTot></prod><imposto><ICMS><ICMSSN102><orig>0</orig><CSOSN>102</CSOSN></ICMSSN102></ICMS><PIS><PISNT><CST>04</CST></PISNT></PIS><COFINS><COFINSNT><CST>04</CST></COFINSNT></COFINS></imposto></det><det nItem=\"3\"><prod><cProd>7891991001342</cProd><cEAN>7891991001342</cEAN><xProd>REFRIGERANTE GUARANÁ ANTARCTICA 2 LITROS</xProd><NCM>22021000</NCM><CEST>0301000</CEST><indEscala>S</indEscala><CFOP>5102</CFOP><uCom>UN</uCom><qCom>1.0</qCom><vUnCom>6.70</vUnCom><vProd>6.70</vProd><cEANTrib>7891991001342</cEANTrib><uTrib>UN</uTrib><qTrib>1.0</qTrib><vUnTrib>6.70</vUnTrib><indTot>1</indTot></prod><imposto><ICMS><ICMSSN102><orig>0</orig><CSOSN>102</CSOSN></ICMSSN102></ICMS><PIS><PISNT><CST>04</CST></PISNT></PIS><COFINS><COFINSNT><CST>04</CST></COFINSNT></COFINS></imposto></det><det nItem=\"4\"><prod><cProd>7896918801234</cProd><cEAN>7896918801234</cEAN><xProd>CERA LIMPADORA AUTOMOTIVA RADIEX</xProd><NCM>34059000</NCM><CEST>2806300</CEST><indEscala>S</indEscala><CFOP>5102</CFOP><uCom>UN</uCom><qCom>1.0</qCom><vUnCom>12.99</vUnCom><vProd>12.99</vProd><cEANTrib>7896918801234</cEANTrib><uTrib>UN</uTrib><qTrib>1.0</qTrib><vUnTrib>12.99</vUnTrib><indTot>1</indTot></prod><imposto><ICMS><ICMSSN102><orig>0</orig><CSOSN>102</CSOSN></ICMSSN102></ICMS><PIS><PISNT><CST>04</CST></PISNT></PIS><COFINS><COFINSNT><CST>04</CST></COFINSNT></COFINS></imposto></det><det nItem=\"5\"><prod><cProd>7898215152002</cProd><cEAN>7898215152002</cEAN><xProd>LEITE CONDENSADO PIRACANJUBA 395G</xProd><NCM>04029900</NCM><CEST>1702000</CEST><indEscala>S</indEscala><CFOP>5102</CFOP><uCom>UN</uCom><qCom>1.0</qCom><vUnCom>3.99</vUnCom><vProd>3.99</vProd><cEANTrib>7898215152002</cEANTrib><uTrib>UN</uTrib><qTrib>1.0</qTrib><vUnTrib>3.99</vUnTrib><indTot>1</indTot></prod><imposto><ICMS><ICMSSN102><orig>0</orig><CSOSN>102</CSOSN></ICMSSN102></ICMS><PIS><PISNT><CST>04</CST></PISNT></PIS><COFINS><COFINSNT><CST>04</CST></COFINSNT></COFINS></imposto></det><det nItem=\"6\"><prod><cProd>7891164027339</cProd><cEAN>7891164027339</cEAN><xProd>LEITE DAMARE INTEGRAL 1L</xProd><NCM>04015010</NCM><CEST>1701700</CEST><indEscala>S</indEscala><CFOP>5102</CFOP><uCom>UN</uCom><qCom>1.0</qCom><vUnCom>4.59</vUnCom><vProd>4.59</vProd><cEANTrib>7891164027339</cEANTrib><uTrib>UN</uTrib><qTrib>1.0</qTrib><vUnTrib>4.59</vUnTrib><indTot>1</indTot></prod><imposto><ICMS><ICMSSN102><orig>0</orig><CSOSN>102</CSOSN></ICMSSN102></ICMS><PIS><PISNT><CST>04</CST></PISNT></PIS><COFINS><COFINSNT><CST>04</CST></COFINSNT></COFINS></imposto></det><det nItem=\"7\"><prod><cProd>0000000000002</cProd><cEAN>0000000000002</cEAN><xProd>PÃO FRANCÊS</xProd><NCM>19052090</NCM><CEST>1705000</CEST><indEscala>S</indEscala><CFOP>5102</CFOP><uCom>Kg</uCom><qCom>1.161</qCom><vUnCom>6.99</vUnCom><vProd>0.00</vProd><cEANTrib>SEM GTIN</cEANTrib><uTrib>Kg</uTrib><qTrib>1.161</qTrib><vUnTrib>6.99</vUnTrib><indTot>1</indTot></prod><imposto><ICMS><ICMSSN102><orig>0</orig><CSOSN>102</CSOSN></ICMSSN102></ICMS><PIS><PISNT><CST>04</CST></PISNT></PIS><COFINS><COFINSNT><CST>04</CST></COFINSNT></COFINS></imposto></det><total><ICMSTot><vBC>36.10</vBC><vICMS>6.50</vICMS><vICMSDeson>0.00</vICMSDeson><vFCP>0.00</vFCP><vBCST>0.00</vBCST><vST>0.00</vST><vFCPST>0.00</vFCPST><vFCPSTRet>0.00</vFCPSTRet><vProd>48.26</vProd><vFrete>0.00</vFrete><vSeg>0.00</vSeg><vDesc>0.00</vDesc><vII>0.00</vII><vIPI>0.00</vIPI><vIPIDevol>0.00</vIPIDevol><vPIS>0.00</vPIS><vCOFINS>0.00</vCOFINS><vOutro>0.00</vOutro><vNF>49.39</vNF><vTotTrib>0.00</vTotTrib></ICMSTot></total><transp><modFrete>9</modFrete></transp><pag><detPag><indPag>0</indPag><tPag>01</tPag><vPag>50.00</vPag></detPag></pag><infAdic><infCpl>Envio de NFCe</infCpl></infAdic></infNFe><Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\"><SignedInfo><CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/><SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/><Reference URI=\"#NFe29181111951317000120650010000001721805847804\"><Transforms><Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/><Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/></Transforms><DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/><DigestValue>0FIpaNdSfPnoWFHQTq0mZB9PuZE=</DigestValue></Reference></SignedInfo><SignatureValue>R8jD1ZspgWqJy65UIN6eUVuTn1bqdkLg+ZpKAfhnJ5JvkwnzaMHGw6KYPwRrHoptF1e32+14vpxKsFOuA9WDttdsts5nm367751FWJ2JDkqB6DcBqULo+jnF0CTXJH4LavE/auTlBTK5mRp/owP6VoCp2PkLxgy6xeGa1tl5djx3kK1N3gB3dsMP+DSwtL7DqTquTSHzRFlEVR8/N2d+FxtuSnkoZZckfrPcoDGEG2bbZK4z1TvfB6ZlV1fNdNjwVuD5PNWSjI8c4RyL7UakbifzwNh0L1+z3Z5AVHxoSuTlc9UBb8C6AaUwDxLSkeYvUnm9QigBUKRvnAaF2Ut4Zg==</SignatureValue><KeyInfo><X509Data><X509Certificate>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</X509Certificate></X509Data></KeyInfo></Signature></NFe></enviNFe>";
                DanfeNFE_Contigencia c = new DanfeNFE_Contigencia();
            //Salvar XML na pasta urlxml
            //Com nome de Nota
            Locale locale = new Locale("pt","BR");
                GregorianCalendar calendar = new GregorianCalendar();
                SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-YYYY_hh-mm-ss");
                Date d = new Date();
                String data = formatador.format(d.getTime());
            String Nota = "nnF"+nota.getNumero()+"D"+data;
            String path  = System.getProperty("user.home") + "\\Desktop";
            String urlDir = path+"\\Notas Fiscais\\XML_NFE_Conti";
            String urlxml = path+"\\Notas Fiscais\\XML_NFE_Conti\\"+Nota+".xml";
                
                File diretorio = new File(urlDir);      
    if (!diretorio.exists())      
        diretorio.mkdirs();      
      
    Writer file;      
        try {
            file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(urlxml),"UTF-8"));
            file.write(Conteudo);      
            file.close(); 
        } catch (UnsupportedEncodingException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Salvar Nota:  "+ex);
            System.out.println("Erro ao Salvar Nota:  "+ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Salvar Nota:  "+ex);
            System.out.println("Erro ao Salvar Nota:  "+ex);
        }
         
 
            
          //TramiteNota.Info.setText("Imprimindo Nota...Aguarde");
            // EmitirNFE.Tramite("Imprimindo Nota","...Aguarde!!");
             
            //Envia XMl e QrCode para impressão
            String Troco = String.valueOf(venda.getTroco());
            String QItem = String.valueOf(itens);
            
            c.recebedados(caixa.getNomeImpressora(),Troco,QItem,urlxml,Id.substring(3),Nota,cliente,Trans,Logo);
      
       }
                    }.start();
    
    }
   
   public static void salvanfce(){
   
       NotaNFE n = new NotaNFE();
       Locale locale = new Locale("pt","BR");
                GregorianCalendar calendar = new GregorianCalendar();
                SimpleDateFormat formatador = new SimpleDateFormat("YYYY-MM-dd HH:mm:ssXXX",locale);
                SimpleDateFormat formatador1 = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
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
            n.setPagamento(venda.getModopagamento1());
            NotaNFEService.addNotaNFE(n);//.ArmazenarNota(n);
            
        //    IdLote = ndao.Id();
   
   }
   
    
}
