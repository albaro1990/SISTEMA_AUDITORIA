package com.citas.medicas.dao.impl;

import com.citas.medicas.conexion.ConexionDB;
import com.citas.medicas.dao.DetalleFacturaDao;
import com.citas.medicas.entity.FacDetalleFactura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetalleFacturaDaoImpl implements DetalleFacturaDao {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt;

    @Override
    public int save(FacDetalleFactura detalleFactura) throws SQLException {
        int idInserted = 0;
        StringBuilder sql = new StringBuilder();
        try {
            conn = new ConexionDB().getConexion();
            sql.append("INSERT INTO FAC_DETALLE_FACTURA(DET_CODIGO, ART_CODIGO, CAB_CODIGO, DET_CATIDAD, DET_VALOR_UNITARIO, DET_NOMBRE_ARTICULO, DET_UNIDAD, DET_SUBTOTAL, DET_APLICA_IVA) VALUES (FAC_SEQ_DET_FACTURA.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt = conn.prepareStatement(sql.toString(), new String[]{"DET_CODIGO"});
            
            pstmt.setInt(1, detalleFactura.getArtCodigo().getArtCodigo());
            pstmt.setBigDecimal(2, detalleFactura.getCabCodigo().getCabCodigo());
            pstmt.setDouble(3, detalleFactura.getDetCatidad());
            pstmt.setBigDecimal(4, detalleFactura.getDetValorUnitario());
            pstmt.setString(5, detalleFactura.getArtCodigo().getArtDescripcion());
            pstmt.setString(6, detalleFactura.getArtCodigo().getArtUnidad());
            pstmt.setBigDecimal(7, detalleFactura.getDetSubtotal());           
            pstmt.setInt(8, detalleFactura.getDetAplicaIva());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear el detalle de la factura");
            }

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                idInserted = rs.getInt(1);
            }

            //System.out.println("Se inserto : "+idInserted);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }
        return idInserted;
    }

    @Override
    public int update(FacDetalleFactura detalleFactura) throws SQLException {
        int nup = 0;
        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("");//"UPDATE FAC_DETALLE_FACTURA SET ART_CODIGO="",CAB_CODIGO="",DET_CATIDAD="", DET_VALOR_UNITARIO="", DET_NOMBRE_ARTICULO= SYSDATE, DET_UNIDAD = "", DET_SUBTOTAL="", DET_APLICA_IVA="" WHERE DET_CODIGO = "" " );

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
            pstmt = conn.prepareStatement("DELETE FROM FAC_DETALLE_FACTURA WHERE DET_CODIGO = " + id + "");
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
    public List<FacDetalleFactura> findAll() throws SQLException {
        List<FacDetalleFactura> detaleesFactura = new ArrayList<FacDetalleFactura>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM FAC_DETALLE_FACTURA ");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FacDetalleFactura detFacura = new FacDetalleFactura();
                detFacura.setDetCodigo(rs.getBigDecimal(1));
                detFacura.getArtCodigo().setArtCodigo(rs.getInt(2));
                detFacura.getCabCodigo().setCabCodigo(rs.getBigDecimal(3));
                detFacura.setDetCatidad(rs.getDouble(4));
                detFacura.setDetValorUnitario(rs.getBigDecimal(5));
                detFacura.setDetNombreArticulo(rs.getString(6));
                detFacura.setDetUnidad(rs.getString(7));
                detFacura.setDetSubtotal(rs.getBigDecimal(8));
                detFacura.setDetAplicaIva(rs.getInt(9));
                detaleesFactura.add(detFacura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return detaleesFactura;
    }

    @Override
    public FacDetalleFactura find(int id) throws SQLException {

        FacDetalleFactura detFacura = null;

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("DELECT * FROM FAC_DETALLE_FACTURA WHERE DET_CODIGO = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                detFacura = new FacDetalleFactura();
                detFacura.setDetCodigo(rs.getBigDecimal(1));
                detFacura.getArtCodigo().setArtCodigo(rs.getInt(2));
                detFacura.getCabCodigo().setCabCodigo(rs.getBigDecimal(3));
                detFacura.setDetCatidad(rs.getDouble(4));
                detFacura.setDetValorUnitario(rs.getBigDecimal(5));
                detFacura.setDetNombreArticulo(rs.getString(6));
                detFacura.setDetUnidad(rs.getString(7));
                detFacura.setDetSubtotal(rs.getBigDecimal(8));
                detFacura.setDetAplicaIva(rs.getInt(9));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return detFacura;
    }
}
