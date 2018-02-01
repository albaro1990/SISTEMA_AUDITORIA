/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.auditoria.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author
 */

public class CitCita implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    private Long citCodigo;
    
    private AudUsuario usuario;
    
    private CitPaciente cliCodigo;
    
    private Date citFechaCita;
    
    private Date horaCita;
    
    private Integer citEstado;
      
    private String citMotivo;
    
    private FacUsuarioAplicacion uapCodigo;

    public CitCita() {
    }

    public CitCita(Long cabCodigo) {
        this.citCodigo = citCodigo;
    }

    public CitCita(Long citCodigo, Date citFechaCita, Integer citEstado, String citMotivo) {
        this.citCodigo = citCodigo;
        this.citFechaCita = citFechaCita;
        this.citEstado = citEstado;
        this.citMotivo = citMotivo;

    }

    public Long getCitCodigo() {
        return citCodigo;
    }

    public void setCitCodigo(Long citCodigo) {
        this.citCodigo = citCodigo;
    }

    public AudUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(AudUsuario usuario) {
        this.usuario = usuario;
    }

    public Date getCitFechaCita() {
        return citFechaCita;
    }

    public void setCitFechaCita(Date citFechaCita) {
        this.citFechaCita = citFechaCita;
    }

    public Date getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(Date horaCita) {
        this.horaCita = horaCita;
    }

    public Integer getCitEstado() {
        return citEstado;
    }

    public void setCitEstado(Integer citEstado) {
        this.citEstado = citEstado;
    }

    public String getCitMotivo() {
        return citMotivo;
    }

    public void setCitMotivo(String citMotivo) {
        this.citMotivo = citMotivo;
    }


    public CitPaciente getCliCodigo() {
        return cliCodigo;
    }

    public void setCliCodigo(CitPaciente cliCodigo) {
        this.cliCodigo = cliCodigo;
    }

    public FacUsuarioAplicacion getUapCodigo() {
        return uapCodigo;
    }

    public void setUapCodigo(FacUsuarioAplicacion uapCodigo) {
        this.uapCodigo = uapCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (citCodigo != null ? citCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CitCita)) {
            return false;
        }
        CitCita other = (CitCita) object;
        if ((this.citCodigo == null && other.citCodigo != null) || (this.citCodigo != null && !this.citCodigo.equals(other.citCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facturacion.entity.FacCabeceraFactura[ cabCodigo=" + citCodigo + " ]";
    }
    
}
