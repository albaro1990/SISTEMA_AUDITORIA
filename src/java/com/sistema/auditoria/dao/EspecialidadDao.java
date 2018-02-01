package com.sistema.auditoria.dao;

import com.sistema.auditoria.entity.CitEspecialidad;
import java.sql.SQLException;
import java.util.List;

public interface EspecialidadDao {

    public int save(CitEspecialidad rol) throws SQLException;

    public int update(CitEspecialidad rol) throws SQLException;

    public int delete(int id) throws SQLException;

    public List<CitEspecialidad> findAll() throws SQLException;

    public CitEspecialidad find(int id) throws SQLException;
    
    public boolean existePorCampo(String username) throws SQLException;
    
    public Long nuevoCodigo() throws SQLException;
}
