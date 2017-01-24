package com.citas.medicas.dao;

import com.citas.medicas.entity.FacCitaFactura;
import java.sql.SQLException;
import java.util.List;

public interface CabeceraFacturaDao {

    public int save(FacCitaFactura factura) throws SQLException;

    public int update(FacCitaFactura usuario) throws SQLException;

    public int delete(int id) throws SQLException;

    public List<FacCitaFactura> findAll() throws SQLException;

    public FacCitaFactura find(int id) throws SQLException;
}
