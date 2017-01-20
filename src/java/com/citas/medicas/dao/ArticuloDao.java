package com.citas.medicas.dao;

import com.citas.medicas.entity.FacArticulo;
import java.sql.SQLException;
import java.util.List;

public interface ArticuloDao {

    public int save(FacArticulo articulo) throws SQLException;

    public int update(FacArticulo articulo) throws SQLException;

    public int delete(int id) throws SQLException;

    public List<FacArticulo> findAll() throws SQLException;

    public FacArticulo find(int id) throws SQLException;

    public boolean existePorCampo(String codigo) throws SQLException;
    
    public FacArticulo findXCodUnico(String id) throws SQLException;
    
    public FacArticulo findActIna(int id) throws SQLException;
    public FacArticulo findXCodUnicoActIna(String id) throws SQLException;
}
