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
public class CitEspecialidad implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private BigDecimal espCodigo;
    
    private String espDescripcion;
    
    private Integer espEstado;
   
    private List<FacUsuario> facUsuarioList;

    public CitEspecialidad() {
    }

    public CitEspecialidad(BigDecimal espCodigo) {
        this.espCodigo = espCodigo;
    }

    public CitEspecialidad(BigDecimal espCodigo, String espDescripcion, Integer espEstado) {
        this.espCodigo = espCodigo;
        this.espDescripcion = espDescripcion;
        this.espEstado = espEstado;
    }

    public BigDecimal getEspCodigo() {
        return espCodigo;
    }

    public void setEspCodigo(BigDecimal espCodigo) {
        this.espCodigo = espCodigo;
    }

    public String getEspDescripcion() {
        return espDescripcion;
    }

    public void setEspDescripcion(String espDescripcion) {
        this.espDescripcion = espDescripcion;
    }

    public Integer getEspEstado() {
        return espEstado;
    }

    public void setEspEstado(Integer espEstado) {
        this.espEstado = espEstado;
    }

    public List<FacUsuario> getFacUsuarioList() {
        return facUsuarioList;
    }

    public void setFacUsuarioList(List<FacUsuario> facUsuarioList) {
        this.facUsuarioList = facUsuarioList;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (espCodigo != null ? espCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CitEspecialidad)) {
            return false;
        }
        CitEspecialidad other = (CitEspecialidad) object;
        if ((this.espCodigo == null && other.espCodigo != null) || (this.espCodigo != null && !this.espCodigo.equals(other.espCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facturacion.entity.FacCiudad[ ciuCodigo=" + espCodigo + " ]";
    }
    
}
