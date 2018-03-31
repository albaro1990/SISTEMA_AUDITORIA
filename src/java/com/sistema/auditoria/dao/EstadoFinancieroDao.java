package com.sistema.auditoria.dao;

import com.sistema.auditoria.dto.AudEstadoFinancieroDTO;
import com.sistema.auditoria.entity.AudEstadoFinanciero;
import com.sistema.auditoria.entity.AudEstructuraAsignacion;
import java.sql.SQLException;
import java.util.List;

public interface EstadoFinancieroDao {

    public int save(List<AudEstadoFinancieroDTO> audEstadoFinanciero) throws SQLException;

    public int update(AudEstructuraAsignacion rol) throws SQLException;

    public int delete(int id) throws SQLException;

    public List<AudEstructuraAsignacion> findAll() throws SQLException;

    public AudEstructuraAsignacion find(int id) throws SQLException;
    
    public boolean existePorCampo(String username) throws SQLException;
    
    public Long nuevoCodigo() throws SQLException;
}
