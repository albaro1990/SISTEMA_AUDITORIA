/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.auditoria.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author
 */

public class AudEstructuraAsignacion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    private Long estCodigo;
        
   
      
    private String estDescripcion;
    
    private Integer estEstado;
    
    
    public AudEstructuraAsignacion() {
    }

    public AudEstructuraAsignacion(Long estCodigo) {
        this.estCodigo = estCodigo;
    }

    public AudEstructuraAsignacion(Long estCodigo, String estDescripcion, Integer estEstado) {
        this.estCodigo = estCodigo;
        this.estDescripcion = estDescripcion;
        this.estEstado = estEstado;
    }

    public Long getEstCodigo() {
        return estCodigo;
    }

    public void setEstCodigo(Long estCodigo) {
        this.estCodigo = estCodigo;
    }

    public String getEstDescripcion() {
        return estDescripcion;
    }

    public void setEstDescripcion(String estDescripcion) {
        this.estDescripcion = estDescripcion;
    }

    public Integer getEstEstado() {
        return estEstado;
    }

    public void setEstEstado(Integer estEstado) {
        this.estEstado = estEstado;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estCodigo != null ? estCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AudEstructuraAsignacion)) {
            return false;
        }
        AudEstructuraAsignacion other = (AudEstructuraAsignacion) object;
        if ((this.estCodigo == null && other.estCodigo != null) || (this.estCodigo != null && !this.estCodigo.equals(other.estCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facturacion.entity.FacCabeceraFactura[ cabCodigo=" + estCodigo + " ]";
    }
    
}
