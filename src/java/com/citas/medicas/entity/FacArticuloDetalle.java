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
public class FacArticuloDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer artCodigoDetalle;

    private BigDecimal artCantidadIgresada;
    private BigDecimal artValorUnitarioIngresado;

    private BigDecimal artCantidadIgresadaInicio;

    private BigDecimal artValorUnitarioInicio;

    private BigDecimal artValorTotalInicio;

    private BigDecimal artSaldo;   
    
    private String artAutorizacion;

    private BigDecimal artValorUnitarioSaliente;

    private BigDecimal artValorTotalSaliente;

    private BigDecimal artCantidadSaliente;

    private Date artFechatransacccion;

    private BigDecimal artValorUnitario;

    private BigDecimal artValorTotal;

    private String detalle;

    private FacArticulo facArticulo;

    public FacArticuloDetalle() {
        super();
    }

    public FacArticuloDetalle(Integer artCodigoDetalle) {
        this.artCodigoDetalle = artCodigoDetalle;
    }

    public FacArticuloDetalle(Integer artCodigoDetalle, BigDecimal artCantidadIgresada, BigDecimal artCantidadSaliente,
            Date artFechatransacccion, BigDecimal artValorUnitario) {
        this.artCodigoDetalle = artCodigoDetalle;
        this.artCantidadIgresada = artCantidadIgresada;
        this.artCantidadSaliente = artCantidadSaliente;
        this.artFechatransacccion = artFechatransacccion;
        this.artValorUnitario = artValorUnitario;

    }

    public Integer getArtCodigoDetalle() {
        return artCodigoDetalle;
    }

    public void setArtCodigoDetalle(Integer artCodigoDetalle) {
        this.artCodigoDetalle = artCodigoDetalle;
    }

    public BigDecimal getArtCantidadIgresada() {
        return artCantidadIgresada;
    }

    public void setArtCantidadIgresada(BigDecimal artCantidadIgresada) {
        this.artCantidadIgresada = artCantidadIgresada;
    }

    public BigDecimal getArtCantidadSaliente() {
        return artCantidadSaliente;
    }

    public void setArtCantidadSaliente(BigDecimal artCantidadSaliente) {
        this.artCantidadSaliente = artCantidadSaliente;
    }

    public Date getArtFechatransacccion() {
        return artFechatransacccion;
    }

    public void setArtFechatransacccion(Date artFechatransacccion) {
        this.artFechatransacccion = artFechatransacccion;
    }

    public FacArticulo getFacArticulo() {
        return facArticulo;
    }

    public void setFacArticulo(FacArticulo facArticulo) {
        this.facArticulo = facArticulo;
    }

    public BigDecimal getArtValorUnitario() {
        return artValorUnitario;
    }

    public void setArtValorUnitario(BigDecimal artValorUnitario) {
        this.artValorUnitario = artValorUnitario;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public BigDecimal getArtValorTotal() {
        return artValorTotal;
    }

    public void setArtValorTotal(BigDecimal artValorTotal) {
        this.artValorTotal = artValorTotal;
    }

    public BigDecimal getArtCantidadIgresadaInicio() {
        return artCantidadIgresadaInicio;
    }

    public void setArtCantidadIgresadaInicio(BigDecimal artCantidadIgresadaInicio) {
        this.artCantidadIgresadaInicio = artCantidadIgresadaInicio;
    }

    public BigDecimal getArtValorUnitarioInicio() {
        return artValorUnitarioInicio;
    }

    public void setArtValorUnitarioInicio(BigDecimal artValorUnitarioInicio) {
        this.artValorUnitarioInicio = artValorUnitarioInicio;
    }

    public BigDecimal getArtValorTotalInicio() {
        return artValorTotalInicio;
    }

    public void setArtValorTotalInicio(BigDecimal artValorTotalInicio) {
        this.artValorTotalInicio = artValorTotalInicio;
    }

    public BigDecimal getArtValorUnitarioSaliente() {
        return artValorUnitarioSaliente;
    }

    public void setArtValorUnitarioSaliente(BigDecimal artValorUnitarioSaliente) {
        this.artValorUnitarioSaliente = artValorUnitarioSaliente;
    }

    public BigDecimal getArtValorTotalSaliente() {
        return artValorTotalSaliente;
    }

    public void setArtValorTotalSaliente(BigDecimal artValorTotalSaliente) {
        this.artValorTotalSaliente = artValorTotalSaliente;
    }

    public BigDecimal getArtValorUnitarioIngresado() {
        return artValorUnitarioIngresado;
    }

    public void setArtValorUnitarioIngresado(BigDecimal artValorUnitarioIngresado) {
        this.artValorUnitarioIngresado = artValorUnitarioIngresado;
    }
     public BigDecimal getArtSaldo() {
        return artSaldo;
    }

    public void setArtSaldo(BigDecimal artSaldo) {
        this.artSaldo = artSaldo;
    }

    public String getArtAutorizacion() {
        return artAutorizacion;
    }

    public void setArtAutorizacion(String artAutorizacion) {
        this.artAutorizacion = artAutorizacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (artCodigoDetalle != null ? artCodigoDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacArticuloDetalle)) {
            return false;
        }
        FacArticuloDetalle other = (FacArticuloDetalle) object;
        if ((this.artCodigoDetalle == null && other.artCodigoDetalle != null) || (this.artCodigoDetalle != null && !this.artCodigoDetalle.equals(other.artCodigoDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facturacion.entity.FacArticulo[ artCodigo=" + artCodigoDetalle + " ]";
    }

}
