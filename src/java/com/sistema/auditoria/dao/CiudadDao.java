package com.sistema.auditoria.dao;

import com.sistema.auditoria.entity.AudCiudad;
import java.sql.SQLException;
import java.util.List;

public interface CiudadDao {

    public int save(AudCiudad rol) throws SQLException;

    public int update(AudCiudad rol) throws SQLException;

    public int delete(int id) throws SQLException;

    public List<AudCiudad> findAll() throws SQLException;

    public AudCiudad find(int id) throws SQLException;
    
    public boolean existePorCampo(String username) throws SQLException;
    
    public Long nuevoCodigo() throws SQLException;
}
