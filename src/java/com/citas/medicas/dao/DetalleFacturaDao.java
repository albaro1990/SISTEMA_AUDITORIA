package com.citas.medicas.dao;

import com.citas.medicas.entity.FacDetalleFactura;
import java.sql.SQLException;
import java.util.List;

public interface DetalleFacturaDao {

    public int save(FacDetalleFactura detalleFactura) throws SQLException;

    public int update(FacDetalleFactura detalleFactura) throws SQLException;

    public int delete(int id) throws SQLException;

    public List<FacDetalleFactura> findAll() throws SQLException;

    public FacDetalleFactura find(int id) throws SQLException;
}
