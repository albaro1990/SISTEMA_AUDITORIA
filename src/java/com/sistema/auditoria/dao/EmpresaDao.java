package com.sistema.auditoria.dao;

import com.sistema.auditoria.entity.AudEmpresa;
import java.sql.SQLException;
import java.util.List;

public interface EmpresaDao {

    public int save(AudEmpresa rol) throws SQLException;

    public int update(AudEmpresa rol) throws SQLException;

    public int delete(int id) throws SQLException;

    public List<AudEmpresa> findAll() throws SQLException;

    public AudEmpresa find(int id) throws SQLException;
    
    public boolean existePorCampo(String username) throws SQLException;
    
    public Long nuevoCodigo() throws SQLException;
}
