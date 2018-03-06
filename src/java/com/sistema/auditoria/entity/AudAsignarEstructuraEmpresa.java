    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.auditoria.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author
 */
public class AudAsignarEstructuraEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer codigo;
    
    private List<AudPlanCuentas> planCuentas;

    private List<AudEstructuraAsignacion> estructuraAsig;
    
    private Long codigoEmpresa;

    private String descCta;

   

    public AudAsignarEstructuraEmpresa() {
        super();
    }

    public AudAsignarEstructuraEmpresa(Integer codigo) {
        this.codigo = codigo;
    }

    public AudAsignarEstructuraEmpresa(Integer codigo, List<AudPlanCuentas> planCuentas, List<AudEstructuraAsignacion> estructuraAsig, Long codigoEmpresa, String descCta) {
        this.codigo = codigo;
        this.planCuentas = planCuentas;
        this.estructuraAsig = estructuraAsig;
        this.codigoEmpresa = codigoEmpresa;
        this.descCta = descCta;
    }

   

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public List<AudPlanCuentas> getPlanCuentas() {
        return planCuentas;
    }

    public void setPlanCuentas(List<AudPlanCuentas> planCuentas) {
        this.planCuentas = planCuentas;
    }

    public List<AudEstructuraAsignacion> getEstructuraAsig() {
        return estructuraAsig;
    }

    public void setEstructuraAsig(List<AudEstructuraAsignacion> estructuraAsig) {
        this.estructuraAsig = estructuraAsig;
    }

    public Long getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(Long codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getDescCta() {
        return descCta;
    }

    public void setDescCta(String descCta) {
        this.descCta = descCta;
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
        if (!(object instanceof AudAsignarEstructuraEmpresa)) {
            return false;
        }
        AudAsignarEstructuraEmpresa other = (AudAsignarEstructuraEmpresa) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facturacion.entity.FacArticulo[ codigo=" + codigo + " ]";
    }

}
