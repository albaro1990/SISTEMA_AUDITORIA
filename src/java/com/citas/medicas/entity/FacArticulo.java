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
public class FacArticulo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer artCodigo;

    private String artCodigoUnico;

    private String artDescripcion;

    private BigDecimal artCantidadIngresada;

    private Integer artAplicaIvaInt;

    private Date artFechaIngreso;

    private Integer artEstado;

    private String artUnidad;

    private Date artFechaActualizacion;

    private BigDecimal artValorUnitario;

    private boolean artAplicaIva;

    private Integer artPorcentajeIva;

    private List<FacProveedorArticulo> facProveedorArticuloList;

    private List<FacDetalleFactura> facDetalleFacturaList;

    public FacArticulo() {
        super();
    }

    public FacArticulo(Integer artCodigo) {
        this.artCodigo = artCodigo;
    }

    public FacArticulo(Integer artCodigo, String artCodigoUnico, String artDescripcion, BigDecimal artCantidadIngresada, Integer artAplicaIvaInt, Date artFechaIngreso, Integer artEstado, String artUnidad, Date artFechaActualizacion, BigDecimal artValorUnitario, boolean artAplicaIva, Integer artPorcentajeIva) {
        this.artCodigo = artCodigo;
        this.artCodigoUnico = artCodigoUnico;
        this.artDescripcion = artDescripcion;
        this.artCantidadIngresada = artCantidadIngresada;
        this.artAplicaIvaInt = artAplicaIvaInt;
        this.artFechaIngreso = artFechaIngreso;
        this.artEstado = artEstado;
        this.artUnidad = artUnidad;
        this.artFechaActualizacion = artFechaActualizacion;
        this.artValorUnitario = artValorUnitario;
        this.artAplicaIva = artAplicaIva;
        this.artPorcentajeIva = artPorcentajeIva;
    }

    public Integer getArtCodigo() {
        return artCodigo;
    }

    public void setArtCodigo(Integer artCodigo) {
        this.artCodigo = artCodigo;
    }

    public String getArtDescripcion() {
        return artDescripcion;
    }

    public void setArtDescripcion(String artDescripcion) {
        this.artDescripcion = artDescripcion;
    }

    public BigDecimal getArtCantidadIngresada() {
        return artCantidadIngresada;
    }

    public void setArtCantidadIngresada(BigDecimal artCantidadIngresada) {
        this.artCantidadIngresada = artCantidadIngresada;
    }

    public Integer getArtAplicaIvaInt() {
        return artAplicaIvaInt;
    }

    public void setArtAplicaIvaInt(Integer artAplicaIvaInt) {
        this.artAplicaIvaInt = artAplicaIvaInt;
    }

    
    public Date getArtFechaIngreso() {
        return artFechaIngreso;
    }

    public void setArtFechaIngreso(Date artFechaIngreso) {
        this.artFechaIngreso = artFechaIngreso;
    }

    public Integer getArtEstado() {
        return artEstado;
    }

    public void setArtEstado(Integer artEstado) {
        this.artEstado = artEstado;
    }

    public String getArtUnidad() {
        return artUnidad;
    }

    public void setArtUnidad(String artUnidad) {
        this.artUnidad = artUnidad;
    }

    public Date getArtFechaActualizacion() {
        return artFechaActualizacion;
    }

    public void setArtFechaActualizacion(Date artFechaActualizacion) {
        this.artFechaActualizacion = artFechaActualizacion;
    }

    public BigDecimal getArtValorUnitario() {
        return artValorUnitario;
    }

    public void setArtValorUnitario(BigDecimal artValorUnitario) {
        this.artValorUnitario = artValorUnitario;
    }

    public boolean getArtAplicaIva() {
        return artAplicaIva;
    }

    public void setArtAplicaIva(boolean artAplicaIva) {
        this.artAplicaIva = artAplicaIva;
    }

    public Integer getArtPorcentajeIva() {
        return artPorcentajeIva;
    }

    public void setArtPorcentajeIva(Integer artPorcentajeIva) {
        this.artPorcentajeIva = artPorcentajeIva;
    }

    public List<FacProveedorArticulo> getFacProveedorArticuloList() {
        return facProveedorArticuloList;
    }

    public void setFacProveedorArticuloList(List<FacProveedorArticulo> facProveedorArticuloList) {
        this.facProveedorArticuloList = facProveedorArticuloList;
    }

    public List<FacDetalleFactura> getFacDetalleFacturaList() {
        return facDetalleFacturaList;
    }

    public void setFacDetalleFacturaList(List<FacDetalleFactura> facDetalleFacturaList) {
        this.facDetalleFacturaList = facDetalleFacturaList;
    }

    public String getArtCodigoUnico() {
        return artCodigoUnico;
    }

    public void setArtCodigoUnico(String artCodigoUnico) {
        this.artCodigoUnico = artCodigoUnico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (artCodigo != null ? artCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacArticulo)) {
            return false;
        }
        FacArticulo other = (FacArticulo) object;
        if ((this.artCodigo == null && other.artCodigo != null) || (this.artCodigo != null && !this.artCodigo.equals(other.artCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facturacion.entity.FacArticulo[ artCodigo=" + artCodigo + " ]";
    }

}
