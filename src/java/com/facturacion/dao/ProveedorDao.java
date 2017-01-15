package com.facturacion.dao;

import com.facturacion.entity.FacProveedor;
import java.sql.SQLException;
import java.util.List;

public interface ProveedorDao {

    public int save(FacProveedor proveedor) throws SQLException;

    public int update(FacProveedor rol) throws SQLException;

    public int delete(int id) throws SQLException;

    public List<FacProveedor> findAll() throws SQLException;

    public FacProveedor find(int id) throws SQLException;
    
    public boolean existePorCampo(String cedRuc) throws SQLException;
}
