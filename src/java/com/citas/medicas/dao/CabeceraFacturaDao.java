package com.citas.medicas.dao;

import com.citas.medicas.entity.FacCabeceraFactura;
import java.sql.SQLException;
import java.util.List;

public interface CabeceraFacturaDao {

    public int save(FacCabeceraFactura factura) throws SQLException;

    public int update(FacCabeceraFactura usuario) throws SQLException;

    public int delete(int id) throws SQLException;

    public List<FacCabeceraFactura> findAll() throws SQLException;

    public FacCabeceraFactura find(int id) throws SQLException;
}
