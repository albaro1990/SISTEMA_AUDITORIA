package com.citas.medicas.dao.impl;

import com.citas.medicas.conexion.ConexionDB;
import com.citas.medicas.dao.CabeceraFacturaDao;
import com.citas.medicas.entity.FacCabeceraFactura;
import com.citas.medicas.entity.FacCliente;
import com.citas.medicas.entity.FacUsuarioAplicacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CabeceraFacturaDaoImpl implements CabeceraFacturaDao {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt;

    @Override
    public int save(FacCabeceraFactura factura) throws SQLException {
        int idInserted = 0;
        StringBuilder sql = new StringBuilder();
        try {
            conn = new ConexionDB().getConexion();
            sql.append("INSERT INTO FAC_CABECERA_FACTURA(CAB_CODIGO, UAP_CODIGO, CLI_CODIGO, CAB_FECHA_CREACION, CAB_ESTADO, CAB_AUTORIZACION, CAB_IDENTIFICACION, CAB_TOTAL, CAB_IVA, CAB_SUBTOTAL)VALUES (FAC_SEQ_CAB_FACTURA.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            factura.setUapCodigo(new FacUsuarioAplicacion());

            pstmt = conn.prepareStatement(sql.toString(), new String[]{"CAB_CODIGO"});
            pstmt.setBigDecimal(1, factura.getUapCodigo().getUapCodigo());
            pstmt.setBigDecimal(2, factura.getCliCodigo().getCliCodigo());
            pstmt.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
            pstmt.setInt(4, factura.getCabEstado());
            pstmt.setString(5, factura.getCabAutorizacion());
            pstmt.setString(6, factura.getCabIdentificacion());
            pstmt.setBigDecimal(7, factura.getCabTotal());
            pstmt.setBigDecimal(8, factura.getCabIva());
            pstmt.setBigDecimal(9, factura.getCabSubtotal());
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
    public int update(FacCabeceraFactura factura) throws SQLException {
        int nup = 0;
        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("UPDATE FAC_CABECERA_FACTURA SET CAB_ESTADO=" + factura.getCabEstado() + " WHERE CAB_CODIGO = " + factura.getCabCodigo() + " ");

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
    public List<FacCabeceraFactura> findAll() throws SQLException {
        List<FacCabeceraFactura> facturas = new ArrayList<FacCabeceraFactura>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM FAC_CABECERA_FACTURA ");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FacCabeceraFactura factura = new FacCabeceraFactura();
                factura.setCabCodigo(rs.getBigDecimal(1));
                factura.getUapCodigo().setUapCodigo(rs.getBigDecimal(2));
                factura.getCliCodigo().setCliCodigo(rs.getBigDecimal(3));
                factura.setCabFechaCreacion(rs.getDate(4));
                factura.setCabEstado(rs.getInt(5));
                factura.setCabAutorizacion(rs.getString(6));
                factura.setCabIdentificacion(rs.getString(7));//ruc de la empresa emisora no del cliente
                factura.setCabTotal(rs.getBigDecimal(8));
                factura.setCabIva(rs.getBigDecimal(9));
                factura.setCabSubtotal(rs.getBigDecimal(10));
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
    public FacCabeceraFactura find(int id) throws SQLException {

        FacCabeceraFactura factura = null;

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM FAC_CABECERA_FACTURA WHERE CAB_CODIGO = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                factura = new FacCabeceraFactura();
                factura.setUapCodigo(new FacUsuarioAplicacion());
                factura.setCliCodigo(new FacCliente());
                
                factura.setCabCodigo(rs.getBigDecimal(1));
                factura.getUapCodigo().setUapCodigo(rs.getBigDecimal(2));
                factura.getCliCodigo().setCliCodigo(rs.getBigDecimal(3));
                factura.setCabFechaCreacion(rs.getDate(4));
                factura.setCabEstado(rs.getInt(5));
                factura.setCabAutorizacion(rs.getString(6));
                factura.setCabIdentificacion(rs.getString(7));//ruc de la empresa emisora no del cliente
                factura.setCabTotal(rs.getBigDecimal(8));
                factura.setCabIva(rs.getBigDecimal(9));
                factura.setCabSubtotal(rs.getBigDecimal(10));
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
