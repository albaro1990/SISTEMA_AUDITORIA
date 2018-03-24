/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.auditoria.dto;

import com.sistema.auditoria.entity.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author
 */

public class AudEstadoFinancieroDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    private Long hisCodigo;
    
    private AudEmpresa empresa;
    
    private String numeroCuenta;
    
    private String descCuenta;
    
    private String estado;
    
    private Double saldoInicial;
    
    private Double debe;
    
    private Double haber;
    
    private Double saldoFinal;
    

    public AudEstadoFinancieroDTO() {
    }

    public AudEstadoFinancieroDTO(Long hisCodigo, AudEmpresa empresa, String numeroCuenta, String descCuenta, String estado, Double saldoInicial, Double debe, Double haber, Double saldoFinal) {
        this.hisCodigo = hisCodigo;
        this.empresa = empresa;
        this.numeroCuenta = numeroCuenta;
        this.descCuenta = descCuenta;
        this.estado = estado;
        this.saldoInicial = saldoInicial;
        this.debe = debe;
        this.haber = haber;
        this.saldoFinal = saldoFinal;
    }

   

   

    public Long getHisCodigo() {
        return hisCodigo;
    }

    public void setHisCodigo(Long hisCodigo) {
        this.hisCodigo = hisCodigo;
    }

    public AudEmpresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(AudEmpresa empresa) {
        this.empresa = empresa;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getDescCuenta() {
        return descCuenta;
    }

    public void setDescCuenta(String descCuenta) {
        this.descCuenta = descCuenta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public Double getDebe() {
        return debe;
    }

    public void setDebe(Double debe) {
        this.debe = debe;
    }

    public Double getHaber() {
        return haber;
    }

    public void setHaber(Double haber) {
        this.haber = haber;
    }

    public Double getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(Double saldoFinal) {
        this.saldoFinal = saldoFinal;
    }    


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hisCodigo != null ? hisCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AudEstadoFinancieroDTO)) {
            return false;
        }
        AudEstadoFinancieroDTO other = (AudEstadoFinancieroDTO) object;
        if ((this.hisCodigo == null && other.hisCodigo != null) || (this.hisCodigo != null && !this.hisCodigo.equals(other.hisCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facturacion.entity.FacCabeceraFactura[ cabCodigo=" + hisCodigo + " ]";
    }
    
}
