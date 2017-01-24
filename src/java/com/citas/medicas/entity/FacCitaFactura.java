/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citas.medicas.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author
 */

public class FacCitaFactura implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private BigDecimal cabCodigo;
    
    private Date cabFechaCreacion;
    
    private Integer cabEstado;
  
    private String cabAutorizacion;
    
    private String cabIdentificacion;
   
    private BigDecimal cabTotal;
    
    private BigDecimal cabIva;
    
    private BigDecimal cabSubtotal;
    
    private List<FacDetalleFactura> facDetalleFacturaList;
    
    private CitPaciente cliCodigo;
    
    private FacUsuarioAplicacion uapCodigo;

    public FacCitaFactura() {
    }

    public FacCitaFactura(BigDecimal cabCodigo) {
        this.cabCodigo = cabCodigo;
    }

    public FacCitaFactura(BigDecimal cabCodigo, Date cabFechaCreacion, Integer cabEstado, BigDecimal cabTotal, BigDecimal cabIva, BigDecimal cabSubtotal) {
        this.cabCodigo = cabCodigo;
        this.cabFechaCreacion = cabFechaCreacion;
        this.cabEstado = cabEstado;
        this.cabTotal = cabTotal;
        this.cabIva = cabIva;
        this.cabSubtotal = cabSubtotal;
    }

    public BigDecimal getCabCodigo() {
        return cabCodigo;
    }

    public void setCabCodigo(BigDecimal cabCodigo) {
        this.cabCodigo = cabCodigo;
    }

    public Date getCabFechaCreacion() {
        return cabFechaCreacion;
    }

    public void setCabFechaCreacion(Date cabFechaCreacion) {
        this.cabFechaCreacion = cabFechaCreacion;
    }

    public Integer getCabEstado() {
        return cabEstado;
    }

    public void setCabEstado(Integer cabEstado) {
        this.cabEstado = cabEstado;
    }

    public String getCabAutorizacion() {
        return cabAutorizacion;
    }

    public void setCabAutorizacion(String cabAutorizacion) {
        this.cabAutorizacion = cabAutorizacion;
    }

    public String getCabIdentificacion() {
        return cabIdentificacion;
    }

    public void setCabIdentificacion(String cabIdentificacion) {
        this.cabIdentificacion = cabIdentificacion;
    }

    public BigDecimal getCabTotal() {
        return cabTotal;
    }

    public void setCabTotal(BigDecimal cabTotal) {
        this.cabTotal = cabTotal;
    }

    public BigDecimal getCabIva() {
        return cabIva;
    }

    public void setCabIva(BigDecimal cabIva) {
        this.cabIva = cabIva;
    }

    public BigDecimal getCabSubtotal() {
        return cabSubtotal;
    }

    public void setCabSubtotal(BigDecimal cabSubtotal) {
        this.cabSubtotal = cabSubtotal;
    }

    public List<FacDetalleFactura> getFacDetalleFacturaList() {
        return facDetalleFacturaList;
    }

    public void setFacDetalleFacturaList(List<FacDetalleFactura> facDetalleFacturaList) {
        this.facDetalleFacturaList = facDetalleFacturaList;
    }

    public CitPaciente getCliCodigo() {
        return cliCodigo;
    }

    public void setCliCodigo(CitPaciente cliCodigo) {
        this.cliCodigo = cliCodigo;
    }

    public FacUsuarioAplicacion getUapCodigo() {
        return uapCodigo;
    }

    public void setUapCodigo(FacUsuarioAplicacion uapCodigo) {
        this.uapCodigo = uapCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cabCodigo != null ? cabCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacCitaFactura)) {
            return false;
        }
        FacCitaFactura other = (FacCitaFactura) object;
        if ((this.cabCodigo == null && other.cabCodigo != null) || (this.cabCodigo != null && !this.cabCodigo.equals(other.cabCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facturacion.entity.FacCabeceraFactura[ cabCodigo=" + cabCodigo + " ]";
    }
    
}
