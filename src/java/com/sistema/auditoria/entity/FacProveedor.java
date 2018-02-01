/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.auditoria.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 *
 * @author
 */
public class FacProveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private BigDecimal proCodigo;
    
    private String proNombre;
    
    private String proIdentificaion;
   
    private Date proFechaCreacion;
   
    private String proRazonSocial;
    
    private Date proFechaActualizacion;
    
    private Integer proEstado;
    
    private String proDireccion;
    
    private String proTelefono;
    
    private String proCorreo;
    
    private List<FacProveedorArticulo> facProveedorArticuloList;
    
    private AudCiudad ciuCodigo;

    public FacProveedor() {
    }

    public FacProveedor(BigDecimal proCodigo) {
        this.proCodigo = proCodigo;
    }

    public FacProveedor(BigDecimal proCodigo, String proNombre, String proIdentificaion, Date proFechaCreacion, Integer proEstado) {
        this.proCodigo = proCodigo;
        this.proNombre = proNombre;
        this.proIdentificaion = proIdentificaion;
        this.proFechaCreacion = proFechaCreacion;
        this.proEstado = proEstado;
    }

    public BigDecimal getProCodigo() {
        return proCodigo;
    }

    public void setProCodigo(BigDecimal proCodigo) {
        this.proCodigo = proCodigo;
    }

    public String getProNombre() {
        return proNombre;
    }

    public void setProNombre(String proNombre) {
        this.proNombre = proNombre;
    }

    public String getProIdentificaion() {
        return proIdentificaion;
    }

    public void setProIdentificaion(String proIdentificaion) {
        this.proIdentificaion = proIdentificaion;
    }

    public Date getProFechaCreacion() {
        return proFechaCreacion;
    }

    public void setProFechaCreacion(Date proFechaCreacion) {
        this.proFechaCreacion = proFechaCreacion;
    }

    public String getProRazonSocial() {
        return proRazonSocial;
    }

    public void setProRazonSocial(String proRazonSocial) {
        this.proRazonSocial = proRazonSocial;
    }

    public Date getProFechaActualizacion() {
        return proFechaActualizacion;
    }

    public void setProFechaActualizacion(Date proFechaActualizacion) {
        this.proFechaActualizacion = proFechaActualizacion;
    }

    public Integer getProEstado() {
        return proEstado;
    }

    public void setProEstado(Integer proEstado) {
        this.proEstado = proEstado;
    }

    public String getProDireccion() {
        return proDireccion;
    }

    public void setProDireccion(String proDireccion) {
        this.proDireccion = proDireccion;
    }

    public String getProTelefono() {
        return proTelefono;
    }

    public void setProTelefono(String proTelefono) {
        this.proTelefono = proTelefono;
    }   

    public List<FacProveedorArticulo> getFacProveedorArticuloList() {
        return facProveedorArticuloList;
    }

    public void setFacProveedorArticuloList(List<FacProveedorArticulo> facProveedorArticuloList) {
        this.facProveedorArticuloList = facProveedorArticuloList;
    }

    public AudCiudad getCiuCodigo() {
        return ciuCodigo;
    }

    public void setCiuCodigo(AudCiudad ciuCodigo) {
        this.ciuCodigo = ciuCodigo;
    }

    public String getProCorreo() {
        return proCorreo;
    }

    public void setProCorreo(String proCorreo) {
        this.proCorreo = proCorreo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proCodigo != null ? proCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacProveedor)) {
            return false;
        }
        FacProveedor other = (FacProveedor) object;
        if ((this.proCodigo == null && other.proCodigo != null) || (this.proCodigo != null && !this.proCodigo.equals(other.proCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facturacion.entity.FacProveedor[ proCodigo=" + proCodigo + " ]";
    }
    
}
