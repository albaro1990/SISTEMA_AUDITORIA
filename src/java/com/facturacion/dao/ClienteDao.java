package com.facturacion.dao;

import com.facturacion.entity.FacCliente;
import java.sql.SQLException;
import java.util.List;

public interface ClienteDao {

    public int save(FacCliente cliente) throws SQLException;

    public int update(FacCliente usuario) throws SQLException;

    public int delete(int id) throws SQLException;

    public List<FacCliente> findAll() throws SQLException;

    public FacCliente find(String id) throws SQLException;
    
    public boolean existePorCampo(String identificacion) throws SQLException;
}
