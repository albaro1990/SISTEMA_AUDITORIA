package com.sistema.auditoria.dao;

import com.sistema.auditoria.entity.CitPaciente;
import java.sql.SQLException;
import java.util.List;

public interface ClienteDao {

    public int save(CitPaciente cliente) throws SQLException;

    public int update(CitPaciente usuario) throws SQLException;

    public int delete(int id) throws SQLException;

    public List<CitPaciente> findAll() throws SQLException;

    public CitPaciente find(String id) throws SQLException;
    
    public boolean existePorCampo(String identificacion) throws SQLException;

    public CitPaciente findXId(int id) throws SQLException;
}
