/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facturacion.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author 
 */
public class FacCiudad implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private BigDecimal ciuCodigo;
    
    private String ciuNombre;
    
    private Integer ciuEstado;
   
    private List<FacProveedor> facProveedorList;

    public FacCiudad() {
    }

    public FacCiudad(BigDecimal ciuCodigo) {
        this.ciuCodigo = ciuCodigo;
    }

    public FacCiudad(BigDecimal ciuCodigo, String ciuNombre, Integer ciuEstado) {
        this.ciuCodigo = ciuCodigo;
        this.ciuNombre = ciuNombre;
        this.ciuEstado = ciuEstado;
    }

    public BigDecimal getCiuCodigo() {
        return ciuCodigo;
    }

    public void setCiuCodigo(BigDecimal ciuCodigo) {
        this.ciuCodigo = ciuCodigo;
    }

    public String getCiuNombre() {
        return ciuNombre;
    }

    public void setCiuNombre(String ciuNombre) {
        this.ciuNombre = ciuNombre;
    }

    public Integer getCiuEstado() {
        return ciuEstado;
    }

    public void setCiuEstado(Integer ciuEstado) {
        this.ciuEstado = ciuEstado;
    }

    public List<FacProveedor> getFacProveedorList() {
        return facProveedorList;
    }

    public void setFacProveedorList(List<FacProveedor> facProveedorList) {
        this.facProveedorList = facProveedorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ciuCodigo != null ? ciuCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacCiudad)) {
            return false;
        }
        FacCiudad other = (FacCiudad) object;
        if ((this.ciuCodigo == null && other.ciuCodigo != null) || (this.ciuCodigo != null && !this.ciuCodigo.equals(other.ciuCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facturacion.entity.FacCiudad[ ciuCodigo=" + ciuCodigo + " ]";
    }
    
}
