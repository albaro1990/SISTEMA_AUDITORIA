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

public class FacUsuarioAplicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    private BigDecimal uapCodigo;
    
    private Date uapFechaActualizacion;
    
    private Integer uapEstado;
    
    private Date uapFechaCreacion;
    
    private FacRol rolCodigo;
    
    private AudUsuario usuCodigo;
    
    private List<CitCita> facCabeceraFacturaList;

    public FacUsuarioAplicacion() {
    }

    public FacUsuarioAplicacion(BigDecimal uapCodigo) {
        this.uapCodigo = uapCodigo;
    }

    public FacUsuarioAplicacion(BigDecimal uapCodigo, Integer uapEstado) {
        this.uapCodigo = uapCodigo;
        this.uapEstado = uapEstado;
    }

    public BigDecimal getUapCodigo() {
        return uapCodigo;
    }

    public void setUapCodigo(BigDecimal uapCodigo) {
        this.uapCodigo = uapCodigo;
    }

    public Date getUapFechaActualizacion() {
        return uapFechaActualizacion;
    }

    public void setUapFechaActualizacion(Date uapFechaActualizacion) {
        this.uapFechaActualizacion = uapFechaActualizacion;
    }

    public Integer getUapEstado() {
        return uapEstado;
    }

    public void setUapEstado(Integer uapEstado) {
        this.uapEstado = uapEstado;
    }

    public Date getUapFechaCreacion() {
        return uapFechaCreacion;
    }

    public void setUapFechaCreacion(Date uapFechaCreacion) {
        this.uapFechaCreacion = uapFechaCreacion;
    }

    public FacRol getRolCodigo() {
        return rolCodigo;
    }

    public void setRolCodigo(FacRol rolCodigo) {
        this.rolCodigo = rolCodigo;
    }

    public AudUsuario getUsuCodigo() {
        return usuCodigo;
    }

    public void setUsuCodigo(AudUsuario usuCodigo) {
        this.usuCodigo = usuCodigo;
    }

    public List<CitCita> getFacCabeceraFacturaList() {
        return facCabeceraFacturaList;
    }

    public void setFacCabeceraFacturaList(List<CitCita> facCabeceraFacturaList) {
        this.facCabeceraFacturaList = facCabeceraFacturaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uapCodigo != null ? uapCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacUsuarioAplicacion)) {
            return false;
        }
        FacUsuarioAplicacion other = (FacUsuarioAplicacion) object;
        if ((this.uapCodigo == null && other.uapCodigo != null) || (this.uapCodigo != null && !this.uapCodigo.equals(other.uapCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facturacion.entity.FacUsuarioAplicacion[ uapCodigo=" + uapCodigo + " ]";
    }
    
}
