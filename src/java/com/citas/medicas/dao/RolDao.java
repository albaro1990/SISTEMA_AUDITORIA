package com.citas.medicas.dao;

import com.citas.medicas.entity.FacRol;
import java.sql.SQLException;
import java.util.List;

public interface RolDao {

    public int save(FacRol rol) throws SQLException;

    public int update(FacRol rol) throws SQLException;

    public int delete(int id) throws SQLException;

    public List<FacRol> findAll() throws SQLException;

    public FacRol find(int id) throws SQLException;
}
