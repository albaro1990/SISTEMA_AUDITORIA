/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facturacion.controller;

import com.facturacion.dao.RolDao;
import com.facturacion.dao.UsuarioAplicacionDao;
import com.facturacion.dao.UsuarioDao;
import com.facturacion.dao.impl.RolDaoImpl;
import com.facturacion.dao.impl.UsuarioAplicacionDaoImpl;
import com.facturacion.dao.impl.UsuarioDaoImpl;
import com.facturacion.entity.FacRol;
import com.facturacion.entity.FacUsuario;
import com.facturacion.entity.FacUsuarioAplicacion;
import com.facturacion.utilitarios.ValidadorCedulaRuc;
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
@ManagedBean(name = "usuarioBean")
@ViewScoped
public class UsuarioBean extends GenericBean {

    private static final long serialVersionUID = 1L;
    private final Logger LOG = LoggerFactory.getLogger(UsuarioBean.class);
    private UsuarioDao usuarioDAO = new UsuarioDaoImpl();
    private RolDao rolDAO = new RolDaoImpl();
    private UsuarioAplicacionDao usuarioAplicacionDao = new UsuarioAplicacionDaoImpl();
    private List<FacRol> roles = new ArrayList<FacRol>();
    private List<FacUsuarioAplicacion> listaUsuarioAplicacion = new ArrayList<FacUsuarioAplicacion>();
    private FacUsuario usuario;
    private FacUsuarioAplicacion usuarioAplicacion;
    private Integer codigoRol;
    private Integer codigoEstado;
    private String confirmarClave;

    /**
     * Creates a new instance of JsfManejadorUsuarioBean
     */
    public UsuarioBean() {
        usuario = new FacUsuario();
        usuarioAplicacion = new FacUsuarioAplicacion();
        cargarCombos();
        cargarDependencias();

    }

    public void inicializar(ActionEvent actionEvent) {
        usuario = new FacUsuario();
        codigoRol = null;
        confirmarClave = null;
    }

    private void cargarCombos() {
        try {
            roles = rolDAO.findAll();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    private void cargarDependencias() {
        try {
            listaUsuarioAplicacion = usuarioAplicacionDao.findAll();
            for (FacUsuarioAplicacion item : listaUsuarioAplicacion) {
                item.setUsuCodigo(usuarioDAO.find(item.getUsuCodigo().getUsuCodigo().intValue()));
                item.setRolCodigo(rolDAO.find(item.getRolCodigo().getRolCodigo().intValue()));
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    public void create(ActionEvent actionEvent) {
        try {
            if (usuario.getUsuCodigo() == null) {
                if (!usuarioDAO.existePorCampo(usuario.getUsuLogin())) {
                    if (ValidadorCedulaRuc.isCedulaValido(usuario.getUsuIdentificacion())) {
                        if (usuario.getUsuClave().equals(confirmarClave)) {
                            int idUsuario = usuarioDAO.save(usuario);
                            usuarioAplicacion.setUsuCodigo(usuarioDAO.find(idUsuario));
                            usuarioAplicacion.setRolCodigo(rolDAO.find(codigoRol));
                            usuarioAplicacion.setUapEstado(usuario.getUsuEstado());
                            usuarioAplicacionDao.save(usuarioAplicacion);
                            cargarDependencias();
                            saveMessageInfoDetail("Usuario", "Usuario " + usuario.getUsuLogin() + " creado correctamente");
                            this.inicializar(actionEvent);
                        } else {
                            saveMessageErrorDetail("Usuario", "La clave no coinside");
                        }
                    } else {
                        saveMessageErrorDetail("Usuario", "La cedula es incorrecta");
                    }
                } else {
                    saveMessageErrorDetail("Usuario", "Usuario " + usuario.getUsuLogin() + " ya existe");
                }
            } else if (usuario.getUsuCodigo() != null) {
                usuarioDAO.update(usuario);
                usuarioAplicacion.setUsuCodigo(usuarioDAO.find(usuario.getUsuCodigo().intValue()));
                usuarioAplicacion.setRolCodigo(rolDAO.find(codigoRol));
                usuarioAplicacion.setUapEstado(usuario.getUsuEstado());
                usuarioAplicacionDao.update(usuarioAplicacion);
                cargarDependencias();
                saveMessageInfoDetail("Usuario", "Usuario " + usuario.getUsuLogin() + " modificado correctamente");
                this.inicializar(actionEvent);
            }
        } catch (SQLException e) {
            saveMessageErrorDetail("Usuario", e.getMessage());
            LOG.error(e.getMessage(), e);
        }

    }

    public void edit(ActionEvent event) {
        usuarioAplicacion = new FacUsuarioAplicacion();
        usuario = new FacUsuario();
        try {
            usuarioAplicacion = (FacUsuarioAplicacion) event.getComponent().getAttributes().get("objetoEditar");
            usuario = usuarioAplicacion.getUsuCodigo();
            codigoRol = usuarioAplicacion.getRolCodigo().getRolCodigo().intValue();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void remove(ActionEvent event) {
        usuarioAplicacion = new FacUsuarioAplicacion();
        try {
            usuarioAplicacion = (FacUsuarioAplicacion) event.getComponent().getAttributes().get("objetoEliminar");
            int ndel = usuarioAplicacionDao.delete(usuarioAplicacion.getUapCodigo().intValue());
            if (ndel > 0) {
                usuarioDAO.delete(usuarioAplicacion.getUsuCodigo().getUsuCodigo().intValue());
                cargarDependencias();
                saveMessageInfoDetail("Usuario", "Usuario " + usuarioAplicacion.getUsuCodigo().getUsuLogin() + " eliminado correctamente");
            } else {
                saveMessageErrorDetail("Usuario", "Usuario " + usuarioAplicacion.getUsuCodigo().getUsuLogin() + " error al eliminar");
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public FacUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(FacUsuario usuario) {
        this.usuario = usuario;
    }

    public RolDao getRolDAO() {
        return rolDAO;
    }

    public void setRolDAO(RolDao rolDAO) {
        this.rolDAO = rolDAO;
    }

    public Integer getCodigoRol() {
        return codigoRol;
    }

    public void setCodigoRol(Integer codigoRol) {
        this.codigoRol = codigoRol;
    }

    public Integer getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(Integer codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    public String getConfirmarClave() {
        return confirmarClave;
    }

    public void setConfirmarClave(String confirmarClave) {
        this.confirmarClave = confirmarClave;
    }

    public List<FacRol> getRoles() {
        return roles;
    }

    public void setRoles(List<FacRol> roles) {
        this.roles = roles;
    }

    public List<FacUsuarioAplicacion> getListaUsuarioAplicacion() {
        return listaUsuarioAplicacion;
    }

    public void setListaUsuarioAplicacion(List<FacUsuarioAplicacion> listaUsuarioAplicacion) {
        this.listaUsuarioAplicacion = listaUsuarioAplicacion;
    }

}
