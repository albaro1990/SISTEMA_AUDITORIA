/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.auditoria.controller;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 *
 * @author 
 */
@ManagedBean
@ViewScoped
public class IndexBean implements Serializable {
    private Subject subject = SecurityUtils.getSubject();

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

  
}
