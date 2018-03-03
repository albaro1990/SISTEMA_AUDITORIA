/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.auditoria.controller;

import com.sistema.auditoria.dao.CiudadDao;
import com.sistema.auditoria.dao.impl.CiudadDaoImpl;
import com.sistema.auditoria.dao.impl.EmpresaDaoImpl;
import com.sistema.auditoria.entity.AudEmpresa;
import com.sistema.auditoria.entity.AudCiudad;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sistema.auditoria.dao.EmpresaDao;
import com.sistema.auditoria.utilitarios.ValidadorCedulaRuc;

/**
 * @author 
 */
@ManagedBean(name = "empresaBean")
@ViewScoped
public class EmpresaBean extends GenericBean {

    private static final long serialVersionUID = 1L;
    private final Logger LOG = LoggerFactory.getLogger(EmpresaBean.class);
    private EmpresaDao empresaDAO = new EmpresaDaoImpl();
    private List<AudEmpresa> listaEmpresa = new ArrayList<AudEmpresa>();
    private List<AudCiudad> ciudades = new ArrayList<AudCiudad>();
    private AudEmpresa empresa = new AudEmpresa();
    private Integer codigoEstado;
    private CiudadDao ciudadDAO = new CiudadDaoImpl();
    private AudCiudad ciudad;
    private Integer codigoCiudad;

    
    

    /**
     * Creates a new instance of JsfManejadorUsuarioBean
     */
    public EmpresaBean() {
        
            empresa = new AudEmpresa();
            cargarDependencias();
            
        
    }

    public void inicializar(ActionEvent actionEvent) {
        empresa = new AudEmpresa();
    }

    private void cargarDependencias() {
        try{
            ciudades = ciudadDAO.findAll();
        listaEmpresa = empresaDAO.findAll();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    public void create(ActionEvent actionEvent) { 
        try {
            if (empresa.getEmpCodigo() == null) {
                if (!empresaDAO.existePorCampo(empresa.getEmpCed_Ruc())) {
                    if (ValidadorCedulaRuc.isRucCedulaValido(empresa.getEmpCed_Ruc())) {
                        empresa.setEmpEstado(1);
                        empresa.setCodigoCiudad(ciudadDAO.find(codigoCiudad));
                            int idempresa = empresaDAO.save(empresa);
                            cargarDependencias();
                            saveMessageInfoDetail("Cedula/Ruc", "Cedula/Ruc " + empresa.getEmpCed_Ruc()+ " creada correctamente");
                            this.inicializar(actionEvent);
                } else {
                    saveMessageErrorDetail("Cedula/Ruc", "Cedula/Ruc " + empresa.getEmpCed_Ruc()+ " ya existe");
                }
                } else {
                    saveMessageErrorDetail("Cedula/Ruc", "Cedula/Ruc " + empresa.getEmpCed_Ruc()+ " ya existe");
                }
            } else if(empresa.getEmpCodigo() != null){
                empresaDAO.update(empresa);
                cargarDependencias();
                this.inicializar(actionEvent);
            }
            
                cargarDependencias();
        } catch (SQLException e) {
            saveMessageErrorDetail("Empresa", e.getMessage());
            LOG.error(e.getMessage(), e);
        }

    }

    public void edit(ActionEvent event) {
        empresa = new AudEmpresa();
        try {
            empresa = (AudEmpresa) event.getComponent().getAttributes().get("objetoEditar");

        } catch (Exception e) {
        }

    }

    public void remove(ActionEvent event) {
        try {
            empresa = (AudEmpresa) event.getComponent().getAttributes().get("objetoEliminar");
            empresaDAO.delete(empresa.getEmpCodigo().intValue());
             cargarDependencias();
             this.inicializar(event);
        } catch (Exception e) {
        }
    }

    public EmpresaDao getEmpresaDAO() {
        return empresaDAO;
    }

    public void setEmpresaDAO(EmpresaDao empresaDAO) {
        this.empresaDAO = empresaDAO;
    }

    public List<AudEmpresa> getListaEmpresa() {
        return listaEmpresa;
    }

    public void setListaEmpresa(List<AudEmpresa> listaEmpresa) {
        this.listaEmpresa = listaEmpresa;
    }
    public List<AudCiudad> getCiudades() {
        return ciudades;
    }

    public AudEmpresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(AudEmpresa empresa) {
        this.empresa = empresa;
    }
   

    public Integer getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(Integer codigoEstado) {
        this.codigoEstado = codigoEstado;
    }
    
    public Integer getCodigoCiudad() {
        return codigoCiudad;
    }

    public void setCodigoCiudad(Integer codigoCiudad) {
        this.codigoCiudad = codigoCiudad;
    }
}