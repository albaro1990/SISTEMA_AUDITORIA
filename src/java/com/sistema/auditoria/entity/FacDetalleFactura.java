/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.auditoria.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author 
 */

public class FacDetalleFactura implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private BigDecimal detCodigo;
    
    private Double detCatidad;
    
    private BigDecimal detValorUnitario;
    
    private String detNombreArticulo;
    
    private String detUnidad;
    
    private BigDecimal detSubtotal;
    
    private Integer detAplicaIva;
    
    private FacArticulo artCodigo;
    
    private CitCita cabCodigo;

    public FacDetalleFactura() {
    }

    public FacDetalleFactura(BigDecimal detCodigo) {
        this.detCodigo = detCodigo;
    }

    public FacDetalleFactura(BigDecimal detCodigo, Double detCatidad, BigDecimal detValorUnitario, String detNombreArticulo, String detUnidad, BigDecimal detSubtotal, Integer detAplicaIva) {
        this.detCodigo = detCodigo;
        this.detCatidad = detCatidad;
        this.detValorUnitario = detValorUnitario;
        this.detNombreArticulo = detNombreArticulo;
        this.detUnidad = detUnidad;
        this.detSubtotal = detSubtotal;
        this.detAplicaIva = detAplicaIva;
    }

    public BigDecimal getDetCodigo() {
        return detCodigo;
    }

    public void setDetCodigo(BigDecimal detCodigo) {
        this.detCodigo = detCodigo;
    }

    public Double getDetCatidad() {
        return detCatidad;
    }

    public void setDetCatidad(Double detCatidad) {
        this.detCatidad = detCatidad;
    }

    public BigDecimal getDetValorUnitario() {
        return detValorUnitario;
    }

    public void setDetValorUnitario(BigDecimal detValorUnitario) {
        this.detValorUnitario = detValorUnitario;
    }

    public String getDetNombreArticulo() {
        return detNombreArticulo;
    }

    public void setDetNombreArticulo(String detNombreArticulo) {
        this.detNombreArticulo = detNombreArticulo;
    }

    public String getDetUnidad() {
        return detUnidad;
    }

    public void setDetUnidad(String detUnidad) {
        this.detUnidad = detUnidad;
    }

    public BigDecimal getDetSubtotal() {
        return detSubtotal;
    }

    public void setDetSubtotal(BigDecimal detSubtotal) {
        this.detSubtotal = detSubtotal;
    }

    public Integer getDetAplicaIva() {
        return detAplicaIva;
    }

    public void setDetAplicaIva(Integer detAplicaIva) {
        this.detAplicaIva = detAplicaIva;
    }

    public FacArticulo getArtCodigo() {
        return artCodigo;
    }

    public void setArtCodigo(FacArticulo artCodigo) {
        this.artCodigo = artCodigo;
    }

    public CitCita getCabCodigo() {
        return cabCodigo;
    }

    public void setCabCodigo(CitCita cabCodigo) {
        this.cabCodigo = cabCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detCodigo != null ? detCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacDetalleFactura)) {
            return false;
        }
        FacDetalleFactura other = (FacDetalleFactura) object;
        if ((this.detCodigo == null && other.detCodigo != null) || (this.detCodigo != null && !this.detCodigo.equals(other.detCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facturacion.entity.FacDetalleFactura[ detCodigo=" + detCodigo + " ]";
    }
    
}
