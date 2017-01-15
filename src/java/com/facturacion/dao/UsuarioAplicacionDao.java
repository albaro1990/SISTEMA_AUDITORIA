package com.facturacion.dao;

import com.facturacion.entity.FacUsuarioAplicacion;
import java.sql.SQLException;
import java.util.List;

public interface UsuarioAplicacionDao {

    public int save(FacUsuarioAplicacion usuarioAplicacion) throws SQLException;

    public int update(FacUsuarioAplicacion usuarioAplicacion) throws SQLException;

    public int delete(int id) throws SQLException;

    public List<FacUsuarioAplicacion> findAll() throws SQLException;

    public FacUsuarioAplicacion find(int id) throws SQLException;
}
