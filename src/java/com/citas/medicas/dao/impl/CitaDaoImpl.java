package com.citas.medicas.dao.impl;

import com.citas.medicas.conexion.ConexionDB;
import com.citas.medicas.entity.CitCita;
import com.citas.medicas.entity.CitPaciente;
import com.citas.medicas.entity.FacUsuarioAplicacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.citas.medicas.dao.CitaDao;

public class CitaDaoImpl implements CitaDao {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt;

    @Override
    public int save(CitCita cita) throws SQLException {
        int idInserted = 0;
        StringBuilder sql = new StringBuilder();
        try {
            
            conn = new ConexionDB().getConexion();
            sql.append("INSERT INTO CIT_CITA(CIT_CODIGO,USU_CODIGO,PAC_CODIGO,CIT_FECHA,CIT_HORA,CIT_ESTADO, CIT_MOTIVO)VALUES (CIT_SEQ_CITA.NEXTVAL, ?, ?, ?, ?, ?, ?)");
            cita.setUapCodigo(new FacUsuarioAplicacion());

            pstmt = conn.prepareStatement(sql.toString(), new String[]{"CIT_CODIGO"});
            pstmt.setLong(1, cita.getUsuario().getUsuCodigo());
            pstmt.setLong(2, cita.getCliCodigo().getPacCodigo());
            pstmt.setDate(3, new java.sql.Date(cita.getCitFechaCita().getTime()));
            int hora = cita.getHoraCita().getHours();
            int minutos = cita.getHoraCita().getMinutes();
            String horaMin = hora +":"+minutos;
            pstmt.setString(4, horaMin);
            pstmt.setInt(5, cita.getCitEstado());
            pstmt.setString(6, cita.getCitMotivo());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear la factura");
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
    public int update(CitCita factura) throws SQLException {
        int nup = 0;
        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("UPDATE CIT_CITA SET CIT_ESTADO=" + factura.getCitEstado() + " WHERE CAB_CODIGO = " + factura.getCitCodigo() + " ");

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
            pstmt = conn.prepareStatement("DELETE FROM FAC_CABECERA_FACTURA WHERE CAB_CODIGO = " + id + "");
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
    public List<CitCita> findAll() throws SQLException {
        List<CitCita> facturas = new ArrayList<CitCita>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM FAC_CABECERA_FACTURA ");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CitCita factura = new CitCita();
//                factura.setCabCodigo(rs.getBigDecimal(1));
//                factura.getUapCodigo().setUapCodigo(rs.getBigDecimal(2));
//                factura.getCliCodigo().setPacCodigo(rs.getLong(3));
//                factura.setCabFechaCreacion(rs.getDate(4));
//                factura.setCabEstado(rs.getInt(5));
//                factura.setCabAutorizacion(rs.getString(6));
//                factura.setCabIdentificacion(rs.getString(7));//ruc de la empresa emisora no del cliente
//                factura.setCabTotal(rs.getBigDecimal(8));
//                factura.setCabIva(rs.getBigDecimal(9));
//                factura.setCabSubtotal(rs.getBigDecimal(10));
                facturas.add(factura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }

        return facturas;
    }

    @Override
    public CitCita find(int id) throws SQLException {

        CitCita factura = null;

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM FAC_CABECERA_FACTURA WHERE CAB_CODIGO = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                factura = new CitCita();
                factura.setUapCodigo(new FacUsuarioAplicacion());
                factura.setCliCodigo(new CitPaciente());
                
//                factura.setCabCodigo(rs.getBigDecimal(1));
//                factura.getUapCodigo().setUapCodigo(rs.getBigDecimal(2));
//                factura.getCliCodigo().setPacCodigo(rs.getLong(3));
//                factura.setCabFechaCreacion(rs.getDate(4));
//                factura.setCabEstado(rs.getInt(5));
//                factura.setCabAutorizacion(rs.getString(6));
//                factura.setCabIdentificacion(rs.getString(7));//ruc de la empresa emisora no del cliente
//                factura.setCabTotal(rs.getBigDecimal(8));
//                factura.setCabIva(rs.getBigDecimal(9));
//                factura.setCabSubtotal(rs.getBigDecimal(10));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }
        return factura;
    }
}
