/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.auditoria.controller;

import com.sistema.auditoria.dao.CiudadDao;
import com.sistema.auditoria.dao.impl.CiudadDaoImpl;
import com.sistema.auditoria.entity.AudCiudad;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rene.travez
 */
@ManagedBean(name = "ciudadBean")
@ViewScoped
public class CiudadBean extends GenericBean {

    private static final long serialVersionUID = 1L;
    private final Logger LOG = LoggerFactory.getLogger(CiudadBean.class);
    private CiudadDao ciudadDAO = new CiudadDaoImpl();
    private List<AudCiudad> listaCiudades = new ArrayList<AudCiudad>();
    private AudCiudad ciudad = new AudCiudad();
    private Integer codigoEstado;

    /**
     * Creates a new instance of JsfManejadorUsuarioBean
     */
    public CiudadBean() {
        
            ciudad = new AudCiudad();
            cargarDependencias();
            
        
    }

    public void inicializar(ActionEvent actionEvent) {
        ciudad = new AudCiudad();
    }

    private void cargarDependencias() {
        try{
        listaCiudades = ciudadDAO.findAll();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    public void create(ActionEvent actionEvent) {
        try {
            if (ciudad.getCiuCodigo() == null) {
                if (!ciudadDAO.existePorCampo(ciudad.getCiuNombre())) {
                            int idCiudad = ciudadDAO.save(ciudad);
                            cargarDependencias();
                            saveMessageInfoDetail("Ciudad", "Ciudad " + ciudad.getCiuNombre() + " creada correctamente");
                            this.inicializar(actionEvent);
                } else {
                    saveMessageErrorDetail("Ciudad", "Ciudad " + ciudad.getCiuNombre()+ " ya existe");
                }
            } else if(ciudad.getCiuCodigo() != null){
                ciudadDAO.update(ciudad);
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
        ciudad = new AudCiudad();
        try {
            ciudad = (AudCiudad) event.getComponent().getAttributes().get("objetoEditar");

        } catch (Exception e) {
        }

    }

    public void remove(ActionEvent event) {
        try {
            ciudad = (AudCiudad) event.getComponent().getAttributes().get("objetoEliminar");
            ciudadDAO.delete(ciudad.getCiuCodigo().intValue());
             cargarDependencias();
             this.inicializar(event);
        } catch (Exception e) {
        }
    }

    public CiudadDao getCiudadDAO() {
        return ciudadDAO;
    }

    public void setCiudadDAO(CiudadDao ciudadDAO) {
        this.ciudadDAO = ciudadDAO;
    }

    public List<AudCiudad> getListaCiudades() {
        return listaCiudades;
    }

    public void setListaCiudades(List<AudCiudad> listaCiudades) {
        this.listaCiudades = listaCiudades;
    }

    public AudCiudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(AudCiudad ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(Integer codigoEstado) {
        this.codigoEstado = codigoEstado;
    }


}
