/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Marcos
 */
@Entity
@Table(name = "produtos")
public class Produto {
    
   
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     
    @Column(nullable = false, unique = false, length = 30)
    private String codigo;
    @Column(nullable = false, unique = false, length = 300)
    private String descricao;
    @Column(nullable = false, unique = false, length = 100)
    private String precoun;
    @Column(nullable = true, unique = false, length = 100)
    private String Ventrada;
    @Column(nullable = false, unique = false, length = 100)
    private float quantidade;
    @Column(nullable = false, unique = false, length = 100)
    private String tipo;
    @Column(nullable = true, unique = false, length = 100)
    private String Unidade;
    @Column(nullable = true, unique = false, length = 20)
    private String UnidadeTributavel;
    @Column(nullable = false, unique = false, length = 20)
    private String data;
    @Column(nullable = true, unique = false, length = 20)
    private String loja = "Sede";
    @Column(nullable = true, unique = false, length = 20)
    private float SubTotal;
    
    @Column(nullable = false, unique = false, length = 20)
    private String vendedor_id;
    @Column(nullable = true, unique = false, length = 20)
    private String CST;
    @Column(nullable = true, unique = false, length = 20)
    private String CFOP;
    @Column(nullable = true, unique = false, length = 20)
    private String BaseICMS;
    @Column(nullable = true, unique = false, length = 20)
    private String ICMS;
    @Column(nullable = true, unique = false, length = 20)
    private String AliquotaICMS;
    
    @Column(nullable = true, unique = false, length = 20)
    private String CEST;
    @Column(nullable = true, unique = false, length = 20)
    private String CEAN;
    @Column(nullable = true, unique = false, length = 20)
    private String CEANTrib;
    @Column(nullable = true, unique = false, length = 20)
    private String QTrib;
    @Column(nullable = true, unique = false, length = 20)
    private String VUnTrib;
    
@Column(nullable = true, unique = false, length = 20)
    private int Item;

    @Column(nullable = true, unique = false, length = 20)
    private String BCICMS;
    @Column(nullable = true, unique = false, length = 20)
    private String BCICMSST;
    @Column(nullable = true, unique = false, length = 20)
    private String PMVAST;
    @Column(nullable = true, unique = false, length = 20)
    private String PRedBCST;
    @Column(nullable = true, unique = false, length = 20)
    private String VBCST;
    @Column(nullable = true, unique = false, length = 20)
    private String PICMSST;//Valor
    @Column(nullable = true, unique = false, length = 20)
    private String VICMSST;//Percentual
    @Column(nullable = true, unique = false, length = 20)
    private String PRedBC;
    @Column(nullable = true, unique = false, length = 20)
    private String PDif;
    @Column(nullable = true, unique = false, length = 20)
    private String VICMSDif;
    @Column(nullable = true, unique = false, length = 20)
    private String VICMS;
    @Column(nullable = true, unique = false, length = 20)
    private String PCredSN;
    @Column(nullable = true, unique = false, length = 20)
    private String VCredICMSSN;
    
    @Column(nullable = true, unique = false, length = 20)
    private String CSTPIS;
    @Column(nullable = true, unique = false, length = 20)
    private String VBCPIS;
    @Column(nullable = true, unique = false, length = 20)
    private String PPIS;
    @Column(nullable = true, unique = false, length = 20)
    private String VPIS;
    
    @Column(nullable = true, unique = false, length = 20)
    private String CSTCOFINS;
    @Column(nullable = true, unique = false, length = 20)
    private String VBCCOFINS;
    @Column(nullable = true, unique = false, length = 20)
    private String PCOFINS;
    @Column(nullable = true, unique = false, length = 20)
    private String VCOFINS;
    
    @Column(nullable = true, unique = false, length = 20)
    private String AFederalN;
    @Column(nullable = true, unique = false, length = 20)
    private String AFederalI;
    @Column(nullable = true, unique = false, length = 20)
    private String AEstadual;
    @Column(nullable = true, unique = false, length = 20)
    private String AMunicipal;

    public Produto() {
        super();
    }

    public Produto(Long id, String codigo, String descricao, String precoun, String Ventrada, float quantidade, String tipo, String Unidade, String UnidadeTributavel, String data, float SubTotal, String vendedor_id, String CST, String CFOP, String BaseICMS, String ICMS, String AliquotaICMS, String CEST, String CEAN, String CEANTrib, String QTrib, String VUnTrib, int Item, String BCICMS, String BCICMSST, String PMVAST, String PRedBCST, String VBCST, String PICMSST, String VICMSST, String PRedBC, String PDif, String VICMSDif, String VICMS, String PCredSN, String VCredICMSSN, String CSTPIS, String VBCPIS, String PPIS, String VPIS, String CSTCOFINS, String VBCCOFINS, String PCOFINS, String VCOFINS, String AFederalN, String AFederalI, String AEstadual, String AMunicipal) {
        this.id = id;
        this.codigo = codigo;
        this.descricao = descricao;
        this.precoun = precoun;
        this.Ventrada = Ventrada;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.Unidade = Unidade;
        this.UnidadeTributavel = UnidadeTributavel;
        this.data = data;
        this.SubTotal = SubTotal;
        this.vendedor_id = vendedor_id;
        this.CST = CST;
        this.CFOP = CFOP;
        this.BaseICMS = BaseICMS;
        this.ICMS = ICMS;
        this.AliquotaICMS = AliquotaICMS;
        this.CEST = CEST;
        this.CEAN = CEAN;
        this.CEANTrib = CEANTrib;
        this.QTrib = QTrib;
        this.VUnTrib = VUnTrib;
        this.Item = Item;
        this.BCICMS = BCICMS;
        this.BCICMSST = BCICMSST;
        this.PMVAST = PMVAST;
        this.PRedBCST = PRedBCST;
        this.VBCST = VBCST;
        this.PICMSST = PICMSST;
        this.VICMSST = VICMSST;
        this.PRedBC = PRedBC;
        this.PDif = PDif;
        this.VICMSDif = VICMSDif;
        this.VICMS = VICMS;
        this.PCredSN = PCredSN;
        this.VCredICMSSN = VCredICMSSN;
        this.CSTPIS = CSTPIS;
        this.VBCPIS = VBCPIS;
        this.PPIS = PPIS;
        this.VPIS = VPIS;
        this.CSTCOFINS = CSTCOFINS;
        this.VBCCOFINS = VBCCOFINS;
        this.PCOFINS = PCOFINS;
        this.VCOFINS = VCOFINS;
        this.AFederalN = AFederalN;
        this.AFederalI = AFederalI;
        this.AEstadual = AEstadual;
        this.AMunicipal = AMunicipal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPrecoun() {
        return precoun;
    }

    public void setPrecoun(String precoun) {
        this.precoun = precoun;
    }

    public String getVentrada() {
        return Ventrada;
    }

    public void setVentrada(String Ventrada) {
        this.Ventrada = Ventrada;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUnidade() {
        return Unidade;
    }

    public void setUnidade(String Unidade) {
        this.Unidade = Unidade;
    }

    public String getUnidadeTributavel() {
        return UnidadeTributavel;
    }

    public void setUnidadeTributavel(String UnidadeTributavel) {
        this.UnidadeTributavel = UnidadeTributavel;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public float getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(float SubTotal) {
        this.SubTotal = SubTotal;
    }

    public String getVendedor_id() {
        return vendedor_id;
    }

    public void setVendedor_id(String vendedor_id) {
        this.vendedor_id = vendedor_id;
    }

    public String getCST() {
        return CST;
    }

    public void setCST(String CST) {
        this.CST = CST;
    }

    public String getCFOP() {
        return CFOP;
    }

    public void setCFOP(String CFOP) {
        this.CFOP = CFOP;
    }

    public String getBaseICMS() {
        return BaseICMS;
    }

    public void setBaseICMS(String BaseICMS) {
        this.BaseICMS = BaseICMS;
    }

    public String getICMS() {
        return ICMS;
    }

    public void setICMS(String ICMS) {
        this.ICMS = ICMS;
    }

    public String getAliquotaICMS() {
        return AliquotaICMS;
    }

    public void setAliquotaICMS(String AliquotaICMS) {
        this.AliquotaICMS = AliquotaICMS;
    }

    public String getCEST() {
        return CEST;
    }

    public void setCEST(String CEST) {
        this.CEST = CEST;
    }

    public String getCEAN() {
        return CEAN;
    }

    public void setCEAN(String CEAN) {
        this.CEAN = CEAN;
    }

    public String getCEANTrib() {
        return CEANTrib;
    }

    public void setCEANTrib(String CEANTrib) {
        this.CEANTrib = CEANTrib;
    }

    public String getQTrib() {
        return QTrib;
    }

    public void setQTrib(String QTrib) {
        this.QTrib = QTrib;
    }

    public String getVUnTrib() {
        return VUnTrib;
    }

    public void setVUnTrib(String VUnTrib) {
        this.VUnTrib = VUnTrib;
    }

    public int getItem() {
        return Item;
    }

    public void setItem(int Item) {
        this.Item = Item;
    }

    public String getBCICMS() {
        return BCICMS;
    }

    public void setBCICMS(String BCICMS) {
        this.BCICMS = BCICMS;
    }

    public String getBCICMSST() {
        return BCICMSST;
    }

    public void setBCICMSST(String BCICMSST) {
        this.BCICMSST = BCICMSST;
    }

    public String getPMVAST() {
        return PMVAST;
    }

    public void setPMVAST(String PMVAST) {
        this.PMVAST = PMVAST;
    }

    public String getPRedBCST() {
        return PRedBCST;
    }

    public void setPRedBCST(String PRedBCST) {
        this.PRedBCST = PRedBCST;
    }

    public String getVBCST() {
        return VBCST;
    }

    public void setVBCST(String VBCST) {
        this.VBCST = VBCST;
    }

    public String getPICMSST() {
        return PICMSST;
    }

    public void setPICMSST(String PICMSST) {
        this.PICMSST = PICMSST;
    }

    public String getVICMSST() {
        return VICMSST;
    }

    public void setVICMSST(String VICMSST) {
        this.VICMSST = VICMSST;
    }

    public String getPRedBC() {
        return PRedBC;
    }

    public void setPRedBC(String PRedBC) {
        this.PRedBC = PRedBC;
    }

    public String getPDif() {
        return PDif;
    }

    public void setPDif(String PDif) {
        this.PDif = PDif;
    }

    public String getVICMSDif() {
        return VICMSDif;
    }

    public void setVICMSDif(String VICMSDif) {
        this.VICMSDif = VICMSDif;
    }

    public String getVICMS() {
        return VICMS;
    }

    public void setVICMS(String VICMS) {
        this.VICMS = VICMS;
    }

    public String getPCredSN() {
        return PCredSN;
    }

    public void setPCredSN(String PCredSN) {
        this.PCredSN = PCredSN;
    }

    public String getVCredICMSSN() {
        return VCredICMSSN;
    }

    public void setVCredICMSSN(String VCredICMSSN) {
        this.VCredICMSSN = VCredICMSSN;
    }

    public String getCSTPIS() {
        return CSTPIS;
    }

    public void setCSTPIS(String CSTPIS) {
        this.CSTPIS = CSTPIS;
    }

    public String getVBCPIS() {
        return VBCPIS;
    }

    public void setVBCPIS(String VBCPIS) {
        this.VBCPIS = VBCPIS;
    }

    public String getPPIS() {
        return PPIS;
    }

    public void setPPIS(String PPIS) {
        this.PPIS = PPIS;
    }

    public String getVPIS() {
        return VPIS;
    }

    public void setVPIS(String VPIS) {
        this.VPIS = VPIS;
    }

    public String getCSTCOFINS() {
        return CSTCOFINS;
    }

    public void setCSTCOFINS(String CSTCOFINS) {
        this.CSTCOFINS = CSTCOFINS;
    }

    public String getVBCCOFINS() {
        return VBCCOFINS;
    }

    public void setVBCCOFINS(String VBCCOFINS) {
        this.VBCCOFINS = VBCCOFINS;
    }

    public String getPCOFINS() {
        return PCOFINS;
    }

    public void setPCOFINS(String PCOFINS) {
        this.PCOFINS = PCOFINS;
    }

    public String getVCOFINS() {
        return VCOFINS;
    }

    public void setVCOFINS(String VCOFINS) {
        this.VCOFINS = VCOFINS;
    }

    public String getAFederalN() {
        return AFederalN;
    }

    public void setAFederalN(String AFederalN) {
        this.AFederalN = AFederalN;
    }

    public String getAFederalI() {
        return AFederalI;
    }

    public void setAFederalI(String AFederalI) {
        this.AFederalI = AFederalI;
    }

    public String getAEstadual() {
        return AEstadual;
    }

    public void setAEstadual(String AEstadual) {
        this.AEstadual = AEstadual;
    }

    public String getAMunicipal() {
        return AMunicipal;
    }

    public void setAMunicipal(String AMunicipal) {
        this.AMunicipal = AMunicipal;
    }

    
    
    
}
