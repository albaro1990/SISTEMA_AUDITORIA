package com.citas.medicas.dao.impl;

import com.citas.medicas.conexion.ConexionDB;
import com.citas.medicas.dao.ClienteDao;
import com.citas.medicas.entity.CitPaciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDaoImpl implements ClienteDao {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt;

    @Override
    public int save(CitPaciente paciente) throws SQLException {
        int idInserted = 0;
        StringBuilder sql = new StringBuilder();
        try {
            conn = new ConexionDB().getConexion();
            sql.append("INSERT INTO CIT_PACIENTE(PAC_CODIGO,CIU_CODIGO,PAC_NOMBRES,PAC_APELLIDOS,PAC_TELEFONO,PAC_DIRECCION,PAC_CEDULA,PAC_CORREO,PAC_ESTADO, PAC_GENERO) VALUES (CIT_SEQ_PACIENTE.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt = conn.prepareStatement(sql.toString(), new String[]{"PAC_CODIGO"});
            pstmt.setBigDecimal(1, paciente.getCodigoCiudad().getCiuCodigo());
            pstmt.setString(2, paciente.getPacNombres());
            pstmt.setString(3, paciente.getPacApellidos());
            pstmt.setString(4, paciente.getPacTelefono());
            pstmt.setString(5, paciente.getPacDireccion());
            pstmt.setString(6, paciente.getPacIdentificacin());
            pstmt.setString(7, paciente.getPacCorreo());
            pstmt.setInt(8, paciente.getPacEstado());
            pstmt.setString(9, paciente.getPacGenero());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Error al crear el paciente");
            }

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                idInserted = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }
        return idInserted;
    }

    @Override
    public int update(CitPaciente paciente) throws SQLException {
        int nup = 0;
        try {

            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("UPDATE CIT_PACIENTE SET CIU_CODIGO='" + paciente.getPacNombres() + "',PAC_NOMBRES='" + paciente.getPacGenero() + "',"
                    + "PAC_APELLIDOS='" + paciente.getPacTelefono() + "',PAC_TELEFONO='" + paciente.getPacDireccion() + "',PAC_DIRECCION='" + paciente.getPacIdentificacin() + "',"
                    + "PAC_CEDULA='" + paciente.getPacCorreo() + "',PAC_CORREO=" + paciente.getPacEstado() + ",PAC_ESTADO='" + paciente.getPacApellidos() + "'"
                    + ",PAC_GENERO='"+paciente.getPacGenero()+"'  WHERE PAC_CODIGO = " + paciente.getPacCodigo() + " ");

            nup = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }
        return nup;
    }

    @Override
    public int delete(int id) throws SQLException {
        int ndel = 0;
        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("DELETE FROM CIT_PACIENTE WHERE PAC_CODIGO = " + id + "");
            ndel = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }
        return ndel;
    }

    @Override
    public List<CitPaciente> findAll() throws SQLException {
        List<CitPaciente> clientes = new ArrayList<CitPaciente>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM CIT_PACIENTE ");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CitPaciente cliente = new CitPaciente();
                cliente.setPacCodigo(rs.getLong(1));
                cliente.setPacNombres(rs.getString(2));
                cliente.setPacGenero(rs.getString(3));
                cliente.setPacTelefono(rs.getString(4));
                cliente.setPacDireccion(rs.getString(5));
                cliente.setPacIdentificacin(rs.getString(6));
                cliente.setPacCorreo(rs.getString(7));
                cliente.setPacEstado(rs.getInt(8));
                cliente.setPacApellidos(rs.getString(9));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }

        return clientes;
    }

    @Override
    public CitPaciente find(String id) throws SQLException {

        CitPaciente cliente = null;

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM CIT_PACIENTE WHERE PAC_CEDULA = ? AND PAC_ESTADO = 1");
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                cliente = new CitPaciente();
                cliente.setPacCodigo(rs.getLong(1));
                cliente.setPacNombres(rs.getString(2));
                cliente.setPacGenero(rs.getString(3));
                cliente.setPacTelefono(rs.getString(4));
                cliente.setPacDireccion(rs.getString(5));
                cliente.setPacIdentificacin(rs.getString(6));
                cliente.setPacCorreo(rs.getString(7));
                cliente.setPacEstado(rs.getInt(8));
                cliente.setPacApellidos(rs.getString(9));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }

        return cliente;
    }

    @Override
    public boolean existePorCampo(String identificacion) throws SQLException {
        try {
            Integer rowCount = 0;
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT COUNT(*) AS CONTADOR FROM CIT_PACIENTE WHERE PAC_CEDULA='" + identificacion + "'");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                rowCount = rs.getInt("CONTADOR");
            }
            return rowCount > 0;
        } catch (SQLException e) {
            throw e;
        } finally {
            conn.close();
            pstmt.close();
        }
    }
}
