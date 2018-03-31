/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.auditoria.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author
 */

public class AudEstadoFinanciero implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    private Long codigo;
    
    private AudEmpresa empresa;
    
    private String nombreArchivo;
    
    private Date fechaSubida;
    
    private List<AudDetEstadoFinan> detalleFinanciero;
    
   

    public AudEstadoFinanciero() {
    }

    public AudEstadoFinanciero(Long codigo) {
        this.codigo = codigo;
    }

    public AudEstadoFinanciero(Long codigo, AudEmpresa empresa, String nombreArchivo, Date fechaSubida) {
        this.codigo = codigo;
        this.empresa = empresa;
        this.nombreArchivo = nombreArchivo;
        this.fechaSubida = fechaSubida;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public AudEmpresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(AudEmpresa empresa) {
        this.empresa = empresa;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Date getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(Date fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public List<AudDetEstadoFinan> getDetalleFinanciero() {
        return detalleFinanciero;
    }

    public void setDetalleFinanciero(List<AudDetEstadoFinan> detalleFinanciero) {
        this.detalleFinanciero = detalleFinanciero;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AudEstadoFinanciero)) {
            return false;
        }
        AudEstadoFinanciero other = (AudEstadoFinanciero) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facturacion.entity.FacCabeceraFactura[ codigo=" + codigo + " ]";
    }
    
}
