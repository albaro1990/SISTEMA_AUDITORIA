package com.sistema.auditoria.dao;

import com.sistema.auditoria.entity.AudPlanCuentas;
import java.sql.SQLException;
import java.util.List;

public interface PlandeCuentasDao {

    public int save(AudPlanCuentas planCuentas) throws SQLException;

    public int update(AudPlanCuentas planCuentas) throws SQLException;

    public int delete(int id) throws SQLException;

    public List<AudPlanCuentas> findAll() throws SQLException;

    public AudPlanCuentas find(int id) throws SQLException;
    
    public boolean existePorCampo(String username) throws SQLException;
    
    public Long nuevoCodigo() throws SQLException;
}
