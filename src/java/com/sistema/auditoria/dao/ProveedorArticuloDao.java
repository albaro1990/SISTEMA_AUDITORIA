package com.sistema.auditoria.dao;

import com.sistema.auditoria.entity.FacProveedorArticulo;
import java.sql.SQLException;
import java.util.List;

public interface ProveedorArticuloDao {

    public int save(FacProveedorArticulo proveedorArticulo) throws SQLException;
    
     public int update(FacProveedorArticulo proveedorArticulo) throws SQLException;

    public int delete(int id) throws SQLException;

    public List<FacProveedorArticulo> findAll() throws SQLException;

    public FacProveedorArticulo find(int id) throws SQLException; 
    
    public boolean existePorCampo(Integer codigoArticulo, Integer codigoProveedor) throws SQLException;
}
