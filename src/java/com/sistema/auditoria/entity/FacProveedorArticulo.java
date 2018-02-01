/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.auditoria.entity;

import java.math.BigDecimal;

/**
 *
 * @author
 */
public class FacProveedorArticulo {

    private Integer parCodigo;
    private FacArticulo facArticulo;
    private FacProveedor facProveedor;

    public FacProveedorArticulo() {
        super();
    }

    public FacProveedorArticulo(Integer parCodigo, FacArticulo facArticulo, FacProveedor facProveedor) {
        this.parCodigo = parCodigo;
        this.facArticulo = facArticulo;
        this.facProveedor = facProveedor;
    }

    public FacArticulo getFacArticulo() {
        return facArticulo;
    }

    public void setFacArticulo(FacArticulo facArticulo) {
        this.facArticulo = facArticulo;
    }

    public FacProveedor getFacProveedor() {
        return facProveedor;
    }

    public void setFacProveedor(FacProveedor facProveedor) {
        this.facProveedor = facProveedor;
    }

    public Integer getParCodigo() {
        return parCodigo;
    }

    public void setParCodigo(Integer parCodigo) {
        this.parCodigo = parCodigo;
    }
}
