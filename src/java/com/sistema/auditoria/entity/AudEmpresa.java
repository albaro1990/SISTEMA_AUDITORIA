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
public class AudEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long empCodigo;
    
    private String empNombre;
    
    private String empRazon_Social;
    
    private String empTelefono;
    
    private String empDireccion;
    
    private String empCed_Ruc;
    
    private String empCorreo;
    
    private Integer empEstado;
    
    private AudCiudad codigoCiudad;
   
      

    public AudEmpresa() {
    }

    public AudEmpresa(Long empCodigo) {
        this.empCodigo = empCodigo;
    }

    public AudEmpresa(Long empCodigo, String empNombre, String empRazon_Social, String empTelefono, String empDireccion, 
            String empCed_Ruc, String empCorreo, Integer empEstado) {
        this.empCodigo = empCodigo;
        this.empNombre = empNombre;
        this.empRazon_Social = empRazon_Social;
        this.empTelefono = empTelefono;
        this.empDireccion = empDireccion;
        this.empCed_Ruc = empCed_Ruc;
        this.empCorreo = empCorreo;
        this.empEstado = empEstado;
    }

    public Long getEmpCodigo() {
        return empCodigo;
    }

    public void setEmpCodigo(Long empCodigo) {
        this.empCodigo = empCodigo;
    }

    public String getEmpCorreo() {
        return empCorreo;
    }

    public void setEmpCorreo(String empCorreo) {
        this.empCorreo = empCorreo;
    }

    public String getEmpRazon_Social() {
        return empRazon_Social;
    }

    public void setEmpCed_Ruc(String empCed_Ruc) {
        this.empCed_Ruc = empCed_Ruc;
    }

    public String getEmpTelefono() {
        return empTelefono;
    }

    public void setEmpEstado(Integer empEstado) {
        this.empEstado = empEstado;
    }

    public void setEmpRazon_Social(String empRazon_Social) {
        this.empRazon_Social = empRazon_Social;
    }

    public void setEmpTelefono(String empTelefono) {
        this.empTelefono = empTelefono;
    }
    public AudCiudad getCodigoCiudad() {
        return codigoCiudad;
    }

    public void setCodigoCiudad(AudCiudad codigoCiudad) {
        this.codigoCiudad = codigoCiudad;
    }
    
    public Integer getEmpEstado() {
        return empEstado;
    }

    public String getEmpNombre() {
        return empNombre;
    }

    public void setEmpNombre(String empNombre) {
        this.empNombre = empNombre;
    }

    public String getEmpCed_Ruc() {
        return empCed_Ruc;
    }

              

    public String getEmpDireccion() {
        return empDireccion;
    }

    public void setEmpDireccion(String empDireccion) {
        this.empDireccion = empDireccion;
    }

    


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empCodigo != null ? empCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AudEmpresa)) {
            return false;
        }
        AudEmpresa other = (AudEmpresa) object;
        if ((this.empCodigo == null && other.empCodigo != null) || (this.empCodigo != null && !this.empCodigo.equals(other.empCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facturacion.entity.FacCiudad[ ciuCodigo=" + empCodigo + " ]";
    }

    public String getCiuCodigo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
