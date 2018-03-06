/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.auditoria.controller;


import com.sistema.auditoria.dao.impl.EstructuraAsignacionDaoImpl;
import com.sistema.auditoria.entity.AudEstructuraAsignacion;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sistema.auditoria.dao.EstructuraAsignacionDao;


/**
 * @author 
 */
@ManagedBean(name = "EstructuraAsignacionBean")
@ViewScoped
public class EstructuraAsignacionBean extends GenericBean {

    private static final long serialVersionUID = 1L;
    private final Logger LOG = LoggerFactory.getLogger(EstructuraAsignacionBean.class);
    private EstructuraAsignacionDao estructuraasignacionDAO = new EstructuraAsignacionDaoImpl();
    private List<AudEstructuraAsignacion> listaEstructuraAsignacion = new ArrayList<>();
    private AudEstructuraAsignacion estructuraasignacion = new AudEstructuraAsignacion();
    private Integer codigoEstado;

    /**
     * Creates a new instance of JsfManejadorUsuarioBean
     */
    public EstructuraAsignacionBean() {
        
            estructuraasignacion = new AudEstructuraAsignacion();
            cargarDependencias();
            
        
    }

    public void inicializar(ActionEvent actionEvent) {
        estructuraasignacion = new AudEstructuraAsignacion();
    }

    private void cargarDependencias() {
        try{
        listaEstructuraAsignacion = estructuraasignacionDAO.findAll();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    public void create(ActionEvent actionEvent) {
        try {
            if (estructuraasignacion.getEstrCodigo() == null) {
                if (!estructuraasignacionDAO.existePorCampo(estructuraasignacion.getEstrDescripcion())) {
                            int idEstructuraAsignacion = estructuraasignacionDAO.save(estructuraasignacion);
                            cargarDependencias();
                            saveMessageInfoDetail("EstructuraAsignacion", "EstructuraAsignacion " + estructuraasignacion.getEstrDescripcion() + " creada correctamente");
                            this.inicializar(actionEvent);
                } else {
                    saveMessageErrorDetail("EstructuraAsignacion", "EstructuraAsignacion " + estructuraasignacion.getEstrDescripcion()+ " ya existe");
                }
            } else if(estructuraasignacion.getEstrCodigo() != null){
                estructuraasignacionDAO.update(estructuraasignacion);
                cargarDependencias();
                this.inicializar(actionEvent);
            }
            
                cargarDependencias();
        } catch (SQLException e) {
            saveMessageErrorDetail("Usuario", e.getMessage());
            LOG.error(e.getMessage(), e);
        }

    }

    public void edit(ActionEvent event) {
        estructuraasignacion = new AudEstructuraAsignacion();
        try {
            estructuraasignacion = (AudEstructuraAsignacion) event.getComponent().getAttributes().get("objetoEditar");

        } catch (Exception e) {
        }

    }

    public void remove(ActionEvent event) {
        try {
            estructuraasignacion = (AudEstructuraAsignacion) event.getComponent().getAttributes().get("objetoEliminar");
            estructuraasignacionDAO.delete(estructuraasignacion.getEstrCodigo().intValue());
             cargarDependencias();
             this.inicializar(event);
        } catch (Exception e) {
        }
    }

    public EstructuraAsignacionDao getEstructuraAsignacionDAO() {
        return estructuraasignacionDAO;
    }

    public void setEstructuraAsignacionDAO(EstructuraAsignacionDao estructuraasignacionDAO) {
        this.estructuraasignacionDAO = estructuraasignacionDAO;
    }

    

    

    public Integer getEstrEstado() {
        return codigoEstado;
    }

    public void setEstrEstado(Integer EstrEstado) {
        this.codigoEstado = codigoEstado; 
    }


}
