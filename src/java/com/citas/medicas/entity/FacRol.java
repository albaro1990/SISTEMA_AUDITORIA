/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citas.medicas.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author
 */

public class FacRol implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private BigDecimal rolCodigo;
    
    private String rolNombre;
    
    private Integer rolEstado;
    
    private List<FacUsuarioAplicacion> facUsuarioAplicacionList;

    public FacRol() {
    }

    public FacRol(BigDecimal rolCodigo) {
        this.rolCodigo = rolCodigo;
    }

    public FacRol(BigDecimal rolCodigo, String rolNombre, Integer rolEstado) {
        this.rolCodigo = rolCodigo;
        this.rolNombre = rolNombre;
        this.rolEstado = rolEstado;
    }

    public BigDecimal getRolCodigo() {
        return rolCodigo;
    }

    public void setRolCodigo(BigDecimal rolCodigo) {
        this.rolCodigo = rolCodigo;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public Integer getRolEstado() {
        return rolEstado;
    }

    public void setRolEstado(Integer rolEstado) {
        this.rolEstado = rolEstado;
    }

    public List<FacUsuarioAplicacion> getFacUsuarioAplicacionList() {
        return facUsuarioAplicacionList;
    }

    public void setFacUsuarioAplicacionList(List<FacUsuarioAplicacion> facUsuarioAplicacionList) {
        this.facUsuarioAplicacionList = facUsuarioAplicacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rolCodigo != null ? rolCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacRol)) {
            return false;
        }
        FacRol other = (FacRol) object;
        if ((this.rolCodigo == null && other.rolCodigo != null) || (this.rolCodigo != null && !this.rolCodigo.equals(other.rolCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facturacion.entity.FacRol[ rolCodigo=" + rolCodigo + " ]";
    }
    
}
