/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.auditoria.controller;

import com.sistema.auditoria.dao.CiudadDao;
import com.sistema.auditoria.dao.ProveedorDao;
import com.sistema.auditoria.dao.impl.CiudadDaoImpl;
import com.sistema.auditoria.dao.impl.ProveedorDaoImpl;
import com.sistema.auditoria.entity.AudCiudad;
import com.sistema.auditoria.entity.FacProveedor;
import com.sistema.auditoria.utilitarios.ValidadorCedulaRuc;
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
@ManagedBean(name = "proveedorBean")
@ViewScoped
public class ProveedorBean extends GenericBean {

    private static final long serialVersionUID = 1L;
    private final Logger LOG = LoggerFactory.getLogger(UsuarioBean.class);
    private ProveedorDao proveedorDAO = new ProveedorDaoImpl();
    private CiudadDao ciudadDAO = new CiudadDaoImpl();
    private List<FacProveedor> listaProveedores = new ArrayList<FacProveedor>();
    private List<AudCiudad> ciudades = new ArrayList<AudCiudad>();
    private FacProveedor proveedor;
    private AudCiudad ciudad;
    private Integer codigoEstado;
    private Integer codigoCiudad;

    /**
     * Creates a new instance of JsfManejadorUsuarioBean
     */
    public ProveedorBean() {
        proveedor = new FacProveedor();
        cargarCombos();
        cargarDependencias();

    }

    public void inicializar(ActionEvent actionEvent) {
        proveedor = new FacProveedor();
        codigoCiudad=null;
        codigoEstado=null;
    }

    private void cargarCombos() {
        
    }

    private void cargarDependencias() {
        try {
            ciudades = ciudadDAO.findAll();
            listaProveedores = proveedorDAO.findAll();
            for (FacProveedor item : listaProveedores) {
                item.setCiuCodigo(ciudadDAO.find(item.getCiuCodigo().getCiuCodigo().intValue()));
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    public void create(ActionEvent actionEvent) {
        try {
            if (proveedor.getProCodigo()== null) {
                if (!proveedorDAO.existePorCampo(proveedor.getProIdentificaion())) {
                    if (ValidadorCedulaRuc.isRucCedulaValido(proveedor.getProIdentificaion())) {
                        proveedor.setCiuCodigo(ciudadDAO.find(codigoCiudad));
                        int idProveedor = proveedorDAO.save(proveedor);
                            cargarDependencias();
                            saveMessageInfoDetail("Proveedor", "Proveedor " + proveedor.getProNombre() + " creado correctamente");
                            this.inicializar(actionEvent);
                    } else {
                        saveMessageErrorDetail("Proveedor", "La cedula es incorrecta");
                    }
                } else {
                    saveMessageErrorDetail("Proveedor", "Proveedor " + proveedor.getProNombre() + " ya existe");
                }
            } else if (proveedor.getProCodigo() != null) {
                proveedor.setCiuCodigo(ciudadDAO.find(codigoCiudad));
                proveedorDAO.update(proveedor);
                
                cargarDependencias();
                saveMessageInfoDetail("Proveedor", "Proveedor " + proveedor.getProNombre() + " modificado correctamente");
                this.inicializar(actionEvent);
            }
        } catch (SQLException e) {
            saveMessageErrorDetail("Proveedor", e.getMessage());
            LOG.error(e.getMessage(), e);
        }

    }

    public void edit(ActionEvent event) {
        proveedor = new FacProveedor();
        ciudad = new AudCiudad();
        try {
            proveedor = (FacProveedor) event.getComponent().getAttributes().get("objetoEditar");
            ciudad = proveedor.getCiuCodigo();
            codigoCiudad = proveedor.getCiuCodigo().getCiuCodigo().intValue();

        } catch (Exception e) {
        }

    }

    public void remove(ActionEvent event) {
        
        try {
            proveedor = (FacProveedor) event.getComponent().getAttributes().get("objetoEliminar");
            proveedorDAO.delete(proveedor.getProCodigo().intValue());
            cargarDependencias();
            this.inicializar(event);

        } catch (Exception e) {
        }

    }

    public ProveedorDao getProveedorDAO() {
        return proveedorDAO;
    }

    public void setProveedorDAO(ProveedorDao proveedorDAO) {
        this.proveedorDAO = proveedorDAO;
    }

    public List<FacProveedor> getListaProveedores() {
        return listaProveedores;
    }

    public void setListaProveedores(List<FacProveedor> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    public FacProveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(FacProveedor proveedor) {
        this.proveedor = proveedor;
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

    public CiudadDao getCiudadDAO() {
        return ciudadDAO;
    }

    public void setCiudadDAO(CiudadDao ciudadDAO) {
        this.ciudadDAO = ciudadDAO;
    }

    public List<AudCiudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<AudCiudad> ciudades) {
        this.ciudades = ciudades;
    }

   

}
