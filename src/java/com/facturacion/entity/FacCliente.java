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

public class FacCliente implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private BigDecimal cliCodigo;
    
    private String cliNombres;
    
    private String cliRazonSocial;
    
    private String cliTelefono;
    
    private String cliDireccion;
    
    private String cliIdentificacin;
    
    private String cliCorreo;
    
    private Integer cliEstado;
   
    private String cliApellidos;
    
    private List<FacCabeceraFactura> facCabeceraFacturaList;

    public FacCliente() {
    }

    public FacCliente(BigDecimal cliCodigo) {
        this.cliCodigo = cliCodigo;
    }

    public FacCliente(BigDecimal cliCodigo, String cliNombres, String cliIdentificacin, Integer cliEstado, String cliApellidos) {
        this.cliCodigo = cliCodigo;
        this.cliNombres = cliNombres;
        this.cliIdentificacin = cliIdentificacin;
        this.cliEstado = cliEstado;
        this.cliApellidos = cliApellidos;
    }

    public BigDecimal getCliCodigo() {
        return cliCodigo;
    }

    public void setCliCodigo(BigDecimal cliCodigo) {
        this.cliCodigo = cliCodigo;
    }

    public String getCliNombres() {
        return cliNombres;
    }

    public void setCliNombres(String cliNombres) {
        this.cliNombres = cliNombres;
    }

    public String getCliRazonSocial() {
        return cliRazonSocial;
    }

    public void setCliRazonSocial(String cliRazonSocial) {
        this.cliRazonSocial = cliRazonSocial;
    }

    public String getCliTelefono() {
        return cliTelefono;
    }

    public void setCliTelefono(String cliTelefono) {
        this.cliTelefono = cliTelefono;
    }

    public String getCliDireccion() {
        return cliDireccion;
    }

    public void setCliDireccion(String cliDireccion) {
        this.cliDireccion = cliDireccion;
    }

    public String getCliIdentificacin() {
        return cliIdentificacin;
    }

    public void setCliIdentificacin(String cliIdentificacin) {
        this.cliIdentificacin = cliIdentificacin;
    }

    public String getCliCorreo() {
        return cliCorreo;
    }

    public void setCliCorreo(String cliCorreo) {
        this.cliCorreo = cliCorreo;
    }

    public Integer getCliEstado() {
        return cliEstado;
    }

    public void setCliEstado(Integer cliEstado) {
        this.cliEstado = cliEstado;
    }

    public String getCliApellidos() {
        return cliApellidos;
    }

    public void setCliApellidos(String cliApellidos) {
        this.cliApellidos = cliApellidos;
    }

    public List<FacCabeceraFactura> getFacCabeceraFacturaList() {
        return facCabeceraFacturaList;
    }

    public void setFacCabeceraFacturaList(List<FacCabeceraFactura> facCabeceraFacturaList) {
        this.facCabeceraFacturaList = facCabeceraFacturaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cliCodigo != null ? cliCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacCliente)) {
            return false;
        }
        FacCliente other = (FacCliente) object;
        if ((this.cliCodigo == null && other.cliCodigo != null) || (this.cliCodigo != null && !this.cliCodigo.equals(other.cliCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facturacion.entity.FacCliente[ cliCodigo=" + cliCodigo + " ]";
    }
    
}
