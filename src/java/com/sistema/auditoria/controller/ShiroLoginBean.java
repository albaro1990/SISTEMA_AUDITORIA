/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.auditoria.controller;

/**
 *
 * @author
 */
import com.sistema.auditoria.dao.RolDao;
import com.sistema.auditoria.dao.impl.RolDaoImpl;
import com.sistema.auditoria.entity.FacRol;
import com.sistema.auditoria.entity.AudUsuario;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@SessionScoped
public class ShiroLoginBean extends GenericBean {

    private static final Logger LOG = LoggerFactory.getLogger(ShiroLoginBean.class);

    private AudUsuario usuario;
    private RolDao rolDao = new RolDaoImpl();
    private boolean rememberMe = false;

    public ShiroLoginBean() {
        super();
        usuario = new AudUsuario();

    }

    public String login(ActionEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        UsernamePasswordToken token = new UsernamePasswordToken(getUsuario().getUsuLogin(), getUsuario().getUsuClave().trim(), getRememberMe());
        try {
            List<FacRol> listaRol = rolDao.findAll();
            if (!listaRol.isEmpty()) {
                Subject subject = SecurityUtils.getSubject();
                subject.login(token);
                for (FacRol rol : listaRol) {
                    if (subject.hasRole(rol.getRolNombre().trim())) {
                        LOG.info(rol.getRolNombre());
                    }
                }                
                context.getExternalContext().redirect(request.getContextPath().concat("/index.jsf"));
            } else {
                 saveMessageErrorDetail("Usuario", "No tiene asignado roles");
                return "/login.jsf";
            }
        } catch (UnknownAccountException ex) {
            saveMessageErrorDetail("Usuario", "Cuenta desconocida");
            LOG.error(ex.getMessage(), ex);
        } catch (IncorrectCredentialsException ex) {
            saveMessageErrorDetail("Usuario", "Contraseña incorrecta");
            LOG.error(ex.getMessage(), ex);
        } catch (LockedAccountException ex) {
            saveMessageErrorDetail("Usuario", "Cuenta bloqueada");
            LOG.error(ex.getMessage(), ex);
        } catch (AuthenticationException | IOException ex) {
            saveMessageErrorDetail("Usuario", "Error desconocido " + ex.getMessage());
            LOG.error(ex.getMessage(), ex);
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        } finally {
            token.clear();
        }
        return null;
    }

    public String logout(ActionEvent event) {
        Subject currentUser = SecurityUtils.getSubject();
        try {
            if (currentUser.isAuthenticated()) {
                currentUser.logout();
                inicializar(event);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return "/login.jsf";
    }

    public void inicializar(ActionEvent e) {
        usuario = new AudUsuario();
    }

    public boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean lembrar) {
        this.rememberMe = lembrar;
    }

    public AudUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(AudUsuario usuario) {
        this.usuario = usuario;
    }
}
