package com.sistema.auditoria.dao;

import com.sistema.auditoria.entity.AudDetEstadoFinan;
import java.sql.SQLException;
import java.util.List;

public interface ArticuloDetalleDao {

    public int save(AudDetEstadoFinan detalleArticulo) throws SQLException;

    public AudDetEstadoFinan find(int id) throws SQLException;
    
    public List<AudDetEstadoFinan> findAll() throws SQLException;
    
    public List<AudDetEstadoFinan> findAllXCod(int id) throws SQLException;

}
