/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.auditoria.controller;

import com.sistema.auditoria.dao.CiudadDao;
import com.sistema.auditoria.dao.ClienteDao;
import com.sistema.auditoria.dao.EspecialidadDao;
import com.sistema.auditoria.dao.RolDao;
import com.sistema.auditoria.dao.UsuarioAplicacionDao;
import com.sistema.auditoria.dao.UsuarioDao;
import com.sistema.auditoria.dao.impl.CiudadDaoImpl;
import com.sistema.auditoria.dao.impl.ClienteDaoImpl;
import com.sistema.auditoria.dao.impl.EspecialidadDaoImpl;
import com.sistema.auditoria.dao.impl.RolDaoImpl;
import com.sistema.auditoria.dao.impl.UsuarioAplicacionDaoImpl;
import com.sistema.auditoria.dao.impl.UsuarioDaoImpl;
import com.sistema.auditoria.entity.CitEspecialidad;
import com.sistema.auditoria.entity.CitPaciente;
import com.sistema.auditoria.entity.AudCiudad;
import com.sistema.auditoria.entity.FacRol;
import com.sistema.auditoria.entity.AudUsuario;
import com.sistema.auditoria.entity.FacUsuarioAplicacion;
import com.sistema.auditoria.utilitarios.ValidadorCedulaRuc;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rene.travez
 */
@ManagedBean(name = "pacienteBean")
@ViewScoped
public class PacienteBean extends GenericBean {

    private static final long serialVersionUID = 1L;
    private final Logger LOG = LoggerFactory.getLogger(UsuarioBean.class);
    private ClienteDao clienteDAO = new ClienteDaoImpl();
    private CiudadDao ciudadDAO = new CiudadDaoImpl();
    private List<AudCiudad> ciudades = new ArrayList<AudCiudad>();
    private List<CitPaciente> pacientes = new ArrayList<CitPaciente>();
    private CitPaciente paciente;
    private AudCiudad ciudad;
    private Integer codigoRol;
    private Integer codigoEstado;
    private String confirmarClave;
    private boolean mostrarEspecialidad=false;
    private Integer codigoEsp;
    private Integer codigoCiudad;

    /**
     * Creates a new instance of JsfManejadorUsuarioBean
     */
    public PacienteBean() {
        paciente = new CitPaciente();
        cargarCombos();
        cargarDependencias();

    }

    public void inicializar(ActionEvent actionEvent) {
        paciente = new CitPaciente();
        codigoRol = null;
        confirmarClave = null;
    }

    public void cargarCombos() {
        try {
            ciudades = ciudadDAO.findAll();
            pacientes = clienteDAO.findAll();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    private void cargarDependencias() {
        try {
            ciudades = ciudadDAO.findAll();
            pacientes = clienteDAO.findAll();
            for (CitPaciente item : pacientes) {
                item.setCodigoCiudad(ciudadDAO.find(item.getCodigoCiudad().getCiuCodigo().intValue()));
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    public void create(ActionEvent actionEvent) {
         RequestContext requestContext = RequestContext.getCurrentInstance();
        try {
            if (paciente.getPacCodigo() == null) {
                if (!clienteDAO.existePorCampo(paciente.getPacIdentificacin())) {
                    if (ValidadorCedulaRuc.isRucCedulaValido(paciente.getPacIdentificacin())) {
                        paciente.setPacEstado(1);
                        paciente.setCodigoCiudad(ciudadDAO.find(codigoCiudad));
                        int idc = clienteDAO.save(paciente);
                        if (idc > 0) {
                            saveMessageInfoDetail("Paciente", "Paciente " + paciente.getPacIdentificacin() + " creado correctamente");
                            requestContext.execute("PF('dlListaCliente').hide()");
                        }
                    } else {
                        saveMessageErrorDetail("Paciente", "La cédula o ruc es incorrecta");
                    }
                } else {
                    saveMessageErrorDetail("Paciente", "Paciente " + paciente.getPacIdentificacin() + " ya existe");
                }
            }else if (paciente.getPacCodigo() != null) {
                paciente.setCodigoCiudad(ciudadDAO.find(codigoCiudad));
                clienteDAO.update(paciente);
               /* usuarioAplicacion.setUsuCodigo(usuarioDAO.find(usuario.getUsuCodigo().intValue()));
                usuarioAplicacion.setRolCodigo(rolDAO.find(codigoRol));
                usuarioAplicacion.setUapEstado(usuario.getUsuEstado());
                usuarioAplicacionDao.update(usuarioAplicacion);*/
                cargarDependencias();
                saveMessageInfoDetail("Paciente", "Paciente " + paciente.getPacNombres() + " modificado correctamente");
                this.inicializar(actionEvent);
            }
        } catch (SQLException e) {
            saveMessageErrorDetail("Paciente", e.getMessage());
            LOG.error(e.getMessage(), e);
        }

    }

    public void edit(ActionEvent event) {
        paciente = new CitPaciente();
        try {
            paciente = (CitPaciente) event.getComponent().getAttributes().get("objetoEditar");
            ciudad = paciente.getCodigoCiudad();
            codigoCiudad = ciudad.getCiuCodigo().intValue();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void remove(ActionEvent event) {
        paciente = new CitPaciente();
        try {
            paciente = (CitPaciente) event.getComponent().getAttributes().get("objetoEliminar");
            int ndel = clienteDAO.delete(paciente.getPacCodigo().intValue());
            if (ndel > 0) {
                cargarDependencias();
                saveMessageInfoDetail("Paciente", "Paciente " + paciente.getPacNombres() + " eliminado correctamente");
            } else {
                saveMessageErrorDetail("Paciente", "Paciente " + paciente.getPacNombres() + " error al eliminar");
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public ClienteDao getClienteDAO() {
        return clienteDAO;
    }

    public void setClienteDAO(ClienteDao clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public CiudadDao getCiudadDAO() {
        return ciudadDAO;
    }

    public void setCiudadDAO(CiudadDao ciudadDAO) {
        this.ciudadDAO = ciudadDAO;
    }

    public CitPaciente getPaciente() {
        return paciente;
    }

    public void setPaciente(CitPaciente paciente) {
        this.paciente = paciente;
    }

    public AudCiudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(AudCiudad ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getCodigoCiudad() {
        return codigoCiudad;
    }

    public void setCodigoCiudad(Integer codigoCiudad) {
        this.codigoCiudad = codigoCiudad;
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

    public boolean isMostrarEspecialidad() {
        return mostrarEspecialidad;
    }

    public void setMostrarEspecialidad(boolean mostrarEspecialidad) {
        this.mostrarEspecialidad = mostrarEspecialidad;
    }

    public Integer getCodigoEsp() {
        return codigoEsp;
    }

    public void setCodigoEsp(Integer codigoEsp) {
        this.codigoEsp = codigoEsp;
    }

    public List<AudCiudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<AudCiudad> ciudades) {
        this.ciudades = ciudades;
    }
    
    public List<CitPaciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<CitPaciente> pacientes) {
        this.pacientes = pacientes;
    }

}
