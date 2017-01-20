package com.citas.medicas.dao.impl;

import com.citas.medicas.conexion.ConexionDB;
import com.citas.medicas.dao.ClienteDao;
import com.citas.medicas.entity.FacCliente;
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
    public int save(FacCliente cliente) throws SQLException {
        int idInserted = 0;
        StringBuilder sql = new StringBuilder();
        try {
            conn = new ConexionDB().getConexion();
            sql.append("INSERT INTO FAC_CLIENTE(CLI_CODIGO, CLI_NOMBRES, CLI_RAZON_SOCIAL, CLI_TELEFONO, CLI_DIRECCION, CLI_IDENTIFICACIN, CLI_CORREO, CLI_ESTADO, CLI_APELLIDOS) VALUES (FAC_SEQ_CLIENTE.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt = conn.prepareStatement(sql.toString(), new String[]{"CLI_CODIGO"});
            pstmt.setString(1, cliente.getCliNombres());
            pstmt.setString(2, cliente.getCliRazonSocial());
            pstmt.setString(3, cliente.getCliTelefono());
            pstmt.setString(4, cliente.getCliDireccion());
            pstmt.setString(5, cliente.getCliIdentificacin());
            pstmt.setString(6, cliente.getCliCorreo());
            pstmt.setInt(7, cliente.getCliEstado());
            pstmt.setString(8, cliente.getCliApellidos());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Error al crear el cliente");
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
    public int update(FacCliente cliente) throws SQLException {
        int nup = 0;
        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("UPDATE FAC_CLIENTE SET CLI_NOMBRES='" + cliente.getCliNombres() + "',CLI_RAZON_SOCIAL='" + cliente.getCliRazonSocial() + "',"
                    + "CLI_TELEFONO='" + cliente.getCliTelefono() + "',CLI_DIRECCION='" + cliente.getCliDireccion() + "',CLI_IDENTIFICACIN='" + cliente.getCliIdentificacin() + "',"
                    + "CLI_CORREO='" + cliente.getCliCorreo() + "',CLI_ESTADO=" + cliente.getCliEstado() + ",CLI_APELLIDOS='" + cliente.getCliApellidos() + "'"
                    + " WHERE CLI_CODIGO = " + cliente.getCliCodigo() + " ");

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
            pstmt = conn.prepareStatement("DELETE FROM FAC_CLIENTE WHERE CLI_CODIGO = " + id + "");
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
    public List<FacCliente> findAll() throws SQLException {
        List<FacCliente> clientes = new ArrayList<FacCliente>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM FAC_CLIENTE ");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FacCliente cliente = new FacCliente();
                cliente.setCliCodigo(rs.getBigDecimal(1));
                cliente.setCliNombres(rs.getString(2));
                cliente.setCliRazonSocial(rs.getString(3));
                cliente.setCliTelefono(rs.getString(4));
                cliente.setCliDireccion(rs.getString(5));
                cliente.setCliIdentificacin(rs.getString(6));
                cliente.setCliCorreo(rs.getString(7));
                cliente.setCliEstado(rs.getInt(8));
                cliente.setCliApellidos(rs.getString(9));
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
    public FacCliente find(String id) throws SQLException {

        FacCliente cliente = null;

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM FAC_CLIENTE WHERE CLI_IDENTIFICACIN = ? AND CLI_ESTADO = 1");
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                cliente = new FacCliente();
                cliente.setCliCodigo(rs.getBigDecimal(1));
                cliente.setCliNombres(rs.getString(2));
                cliente.setCliRazonSocial(rs.getString(3));
                cliente.setCliTelefono(rs.getString(4));
                cliente.setCliDireccion(rs.getString(5));
                cliente.setCliIdentificacin(rs.getString(6));
                cliente.setCliCorreo(rs.getString(7));
                cliente.setCliEstado(rs.getInt(8));
                cliente.setCliApellidos(rs.getString(9));
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
            pstmt = conn.prepareStatement("SELECT COUNT(*) AS CONTADOR FROM FAC_CLIENTE WHERE CLI_IDENTIFICACIN='" + identificacion + "'");
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
