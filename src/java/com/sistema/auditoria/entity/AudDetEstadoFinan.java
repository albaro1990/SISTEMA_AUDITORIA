/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.auditoria.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author
 */
public class AudDetEstadoFinan implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer codigo;
    
    private AudEstadoFinanciero estadoFinanciero;

    private BigDecimal anioAnterior;
    
    private BigDecimal debe;

    private BigDecimal haber;

    private BigDecimal saldoInicial;

    private BigDecimal saldoFinal;

    private List<AudPlanCuentas> planCuentas;

    

    public AudDetEstadoFinan() {
        super();
    }

    public AudDetEstadoFinan(Integer codigo) {
        this.codigo = codigo;
    }

    public AudDetEstadoFinan(Integer codigo, AudEstadoFinanciero estadoFinanciero, BigDecimal anioAnterior, BigDecimal debe, BigDecimal haber, BigDecimal saldoInicial, BigDecimal saldoFinal, List<AudPlanCuentas> planCuentas) {
        this.codigo = codigo;
        this.estadoFinanciero = estadoFinanciero;
        this.anioAnterior = anioAnterior;
        this.debe = debe;
        this.haber = haber;
        this.saldoInicial = saldoInicial;
        this.saldoFinal = saldoFinal;
        this.planCuentas = planCuentas;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public AudEstadoFinanciero getEstadoFinanciero() {
        return estadoFinanciero;
    }

    public void setEstadoFinanciero(AudEstadoFinanciero estadoFinanciero) {
        this.estadoFinanciero = estadoFinanciero;
    }

    public BigDecimal getAnioAnterior() {
        return anioAnterior;
    }

    public void setAnioAnterior(BigDecimal anioAnterior) {
        this.anioAnterior = anioAnterior;
    }

    public BigDecimal getDebe() {
        return debe;
    }

    public void setDebe(BigDecimal debe) {
        this.debe = debe;
    }

    public BigDecimal getHaber() {
        return haber;
    }

    public void setHaber(BigDecimal haber) {
        this.haber = haber;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public BigDecimal getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(BigDecimal saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public List<AudPlanCuentas> getPlanCuentas() {
        return planCuentas;
    }

    public void setPlanCuentas(List<AudPlanCuentas> planCuentas) {
        this.planCuentas = planCuentas;
    }

   

    


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AudDetEstadoFinan)) {
            return false;
        }
        AudDetEstadoFinan other = (AudDetEstadoFinan) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facturacion.entity.FacArticulo[ codigo=" + codigo + " ]";
    }

}
