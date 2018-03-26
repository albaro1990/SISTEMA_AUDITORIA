package com.sistema.auditoria.dao;

import com.sistema.auditoria.entity.AudAsignarEstructuraEmpresa;
import com.sistema.auditoria.entity.AudEstructuraAsignacion;
import java.sql.SQLException;
import java.util.List;

public interface EstructuraAsignacionEmpresaDao {

    public int save(AudAsignarEstructuraEmpresa rol) throws SQLException;

    public int update(AudAsignarEstructuraEmpresa rol) throws SQLException;

    public int delete(int id) throws SQLException;

    public List<AudAsignarEstructuraEmpresa> findAll() throws SQLException;

    public AudAsignarEstructuraEmpresa find(int id) throws SQLException;
    
    public List<AudEstructuraAsignacion> findByEmp(Integer codEmp) throws SQLException;
    
    public List<AudAsignarEstructuraEmpresa> findByEmpYEst(Integer codEmp, Long codEst) throws SQLException;
    
    public boolean existePorCampo(String username) throws SQLException;
    
    public Long nuevoCodigo() throws SQLException;
}
