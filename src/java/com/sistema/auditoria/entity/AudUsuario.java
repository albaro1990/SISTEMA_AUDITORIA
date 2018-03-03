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

public class AudUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long usuCodigo;
    
    private String usuLogin;
    
    private String usuClave;
    
    private String usuNombres;
    
    private String usuApellidos;
    
    private String usuCorreo;
    
    private String usuIdentificacion;
    
    private String usuDireccion;
    
    private String usuTelefono;
    
    private Integer usuEstado;
    
    private AudEmpresa citEspecialidad;
    
    private Date fechaCreacion;
    
    private Date fechaModificacion;
    
    private List<FacUsuarioAplicacion> facUsuarioAplicacionList;

    public AudUsuario() {
    }

    public AudUsuario(Long usuCodigo) {
        this.usuCodigo = usuCodigo;
    }

    public AudUsuario(Long usuCodigo, String usuLogin, String usuClave, String usuNombres, String usuApellidos, String usuIdentificacion, Integer usuEstado) {
        this.usuCodigo = usuCodigo;
        this.usuLogin = usuLogin;
        this.usuClave = usuClave;
        this.usuNombres = usuNombres;
        this.usuApellidos = usuApellidos;
        this.usuIdentificacion = usuIdentificacion;
        this.usuEstado = usuEstado;
    }

    public Long getUsuCodigo() {
        return usuCodigo;
    }

    public void setUsuCodigo(Long usuCodigo) {
        this.usuCodigo = usuCodigo;
    }

    public String getUsuLogin() {
        return usuLogin;
    }

    public void setUsuLogin(String usuLogin) {
        this.usuLogin = usuLogin;
    }

    public String getUsuClave() {
        return usuClave;
    }

    public void setUsuClave(String usuClave) {
        this.usuClave = usuClave;
    }

    public String getUsuNombres() {
        return usuNombres;
    }

    public void setUsuNombres(String usuNombres) {
        this.usuNombres = usuNombres;
    }

    public String getUsuApellidos() {
        return usuApellidos;
    }

    public void setUsuApellidos(String usuApellidos) {
        this.usuApellidos = usuApellidos;
    }

    public String getUsuCorreo() {
        return usuCorreo;
    }

    public void setUsuCorreo(String usuCorreo) {
        this.usuCorreo = usuCorreo;
    }

    public String getUsuIdentificacion() {
        return usuIdentificacion;
    }

    public void setUsuIdentificacion(String usuIdentificacion) {
        this.usuIdentificacion = usuIdentificacion;
    }

    public String getUsuDireccion() {
        return usuDireccion;
    }

    public void setUsuDireccion(String usuDireccion) {
        this.usuDireccion = usuDireccion;
    }

    public String getUsuTelefono() {
        return usuTelefono;
    }

    public void setUsuTelefono(String usuTelefono) {
        this.usuTelefono = usuTelefono;
    }

    public Integer getUsuEstado() {
        return usuEstado;
    }

    public void setUsuEstado(Integer usuEstado) {
        this.usuEstado = usuEstado;
    }

    public List<FacUsuarioAplicacion> getFacUsuarioAplicacionList() {
        return facUsuarioAplicacionList;
    }

    public void setFacUsuarioAplicacionList(List<FacUsuarioAplicacion> facUsuarioAplicacionList) {
        this.facUsuarioAplicacionList = facUsuarioAplicacionList;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public AudEmpresa getCitEspecialidad() {
        return citEspecialidad;
    }

    public void setCitEspecialidad(AudEmpresa citEspecialidad) {
        this.citEspecialidad = citEspecialidad;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuCodigo != null ? usuCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AudUsuario)) {
            return false;
        }
        AudUsuario other = (AudUsuario) object;
        if ((this.usuCodigo == null && other.usuCodigo != null) || (this.usuCodigo != null && !this.usuCodigo.equals(other.usuCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facturacion.entity.AudUsuario[ usuCodigo=" + usuCodigo + " ]";
    }

    
    
    
}
