package com.facturacion.dao;

import com.facturacion.entity.FacArticuloDetalle;
import java.sql.SQLException;
import java.util.List;

public interface ArticuloDetalleDao {

    public int save(FacArticuloDetalle detalleArticulo) throws SQLException;

    public FacArticuloDetalle find(int id) throws SQLException;
    
    public List<FacArticuloDetalle> findAll() throws SQLException;
    
    public List<FacArticuloDetalle> findAllXCod(int id) throws SQLException;

}
