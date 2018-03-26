/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.auditoria.entity;

import java.io.Serializable;

/**
 *
 * @author
 */

public class AudPlanCuentas implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    private Long codPlanCta;
    
    private AudEmpresa empresa;
    
    private String numeroCta;
    
    private String descCta;
    
    private Integer estado;
    
  
    

    public AudPlanCuentas() {
    }

    public AudPlanCuentas(Long codPlanCta) {
        this.codPlanCta = codPlanCta;
    }

    public Long getCodPlanCta() {
        return codPlanCta;
    }

    public void setCodPlanCta(Long codPlanCta) {
        this.codPlanCta = codPlanCta;
    }

    public AudEmpresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(AudEmpresa empresa) {
        this.empresa = empresa;
    }

    public String getNumeroCta() {
        return numeroCta;
    }

    public void setNumeroCta(String numeroCta) {
        this.numeroCta = numeroCta;
    }

    public String getDescCta() {
        return descCta;
    }

    public void setDescCta(String descCta) {
        this.descCta = descCta;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codPlanCta != null ? codPlanCta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AudPlanCuentas)) {
            return false;
        }
        AudPlanCuentas other = (AudPlanCuentas) object;
        if ((this.codPlanCta == null && other.codPlanCta != null) || (this.codPlanCta != null && !this.codPlanCta.equals(other.codPlanCta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facturacion.entity.FacCabeceraFactura[ codPlanCta=" + codPlanCta + " ]";
    }
    
}
