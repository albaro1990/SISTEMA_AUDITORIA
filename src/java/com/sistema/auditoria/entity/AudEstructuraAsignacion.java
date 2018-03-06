/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.auditoria.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author 
 */
public class AudEstructuraAsignacion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long estrCodigo;
    
    private String estrDescripcion;
     
    private Integer estrEstado;
    
    
      

    public AudEstructuraAsignacion() {
    }

    public AudEstructuraAsignacion(Long estrCodigo) {
        this.estrCodigo = estrCodigo;
    }

    public AudEstructuraAsignacion(Long estrCodigo, String estrDescripcion, Integer estrEstado) {
        this.estrCodigo = estrCodigo;
        this.estrDescripcion = estrDescripcion;
        this.estrEstado = estrEstado;
    }

    public Long getEstrCodigo() {
        return estrCodigo;
    }

    public void setEstrCodigo(Long estrCodigo) {
        this.estrCodigo = estrCodigo;
    }

    public String getEstrDescripcion() {
        return estrDescripcion;
    }

    public void setEstrDescripcion(String estrDescripcion) {
        this.estrDescripcion = estrDescripcion;
    }

    

    public void setEstrEstado(Integer empEstado) {
        this.estrEstado = empEstado;
    }

    
    public Integer getEstrEstado() {
        return estrEstado;
    }

  

    


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estrCodigo != null ? estrCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AudEstructuraAsignacion)) {
            return false;
        }
        AudEstructuraAsignacion other = (AudEstructuraAsignacion) object;
        if ((this.estrCodigo == null && other.estrCodigo != null) || (this.estrCodigo != null && !this.estrCodigo.equals(other.estrCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facturacion.entity.AudEstructuraAsignacion[ estrCodigo=" + estrCodigo + " ]";
    
 
    
}

    public void add(AudEstructuraAsignacion estructuraasignacion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
