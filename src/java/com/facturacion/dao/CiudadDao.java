package com.facturacion.dao;

import com.facturacion.entity.FacCiudad;
import java.sql.SQLException;
import java.util.List;

public interface CiudadDao {

    public int save(FacCiudad rol) throws SQLException;

    public int update(FacCiudad rol) throws SQLException;

    public int delete(int id) throws SQLException;

    public List<FacCiudad> findAll() throws SQLException;

    public FacCiudad find(int id) throws SQLException;
    
    public boolean existePorCampo(String username) throws SQLException;
    
    public Long nuevoCodigo() throws SQLException;
}
