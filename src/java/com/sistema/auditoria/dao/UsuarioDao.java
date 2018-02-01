package com.sistema.auditoria.dao;

import com.sistema.auditoria.entity.AudUsuario;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface UsuarioDao extends Serializable{

    public int save(AudUsuario usuario) throws SQLException;

    public int update(AudUsuario usuario) throws SQLException;

    public int delete(int id) throws SQLException;

    public List<AudUsuario> findAll() throws SQLException;

    public AudUsuario find(int id) throws SQLException;
    
    public boolean existePorCampo(String username) throws SQLException;
    
    public Long nuevoCodigo() throws SQLException;
    
    public List<AudUsuario> findDoctoresXEsp(int codEspecialidad) throws SQLException; 
}
