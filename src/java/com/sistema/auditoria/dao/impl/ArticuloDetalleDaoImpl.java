package com.sistema.auditoria.dao.impl;

import com.sistema.auditoria.conexion.ConexionDB;
import com.sistema.auditoria.dao.ArticuloDao;
import com.sistema.auditoria.dao.ArticuloDetalleDao;
import com.sistema.auditoria.entity.FacArticulo;
import com.sistema.auditoria.entity.FacArticuloDetalle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDetalleDaoImpl implements ArticuloDetalleDao {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt;

    @Override
    public int save(FacArticuloDetalle articuloDetalle) throws SQLException {
        int idInserted = 0;
        StringBuilder sql = new StringBuilder();
        try {
            conn = new ConexionDB().getConexion();
            sql.append("INSERT INTO FAC_ARTICULO_DETALLE(ADE_CODIGO, ART_CODIGO, ADE_CANTIDAD_INGRESADA, ADE_CANTIDAD_SALIENTE, ADE_FECHA_TRANSACCION, ADE_VALOR_UNITARIO, ADE_SALDO,ADE_AUTORIZACION ) VALUES (FAC_SEQ_ARTICULO_DETALLE.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)");
            pstmt = conn.prepareStatement(sql.toString(), new String[]{"ADE_CODIGO"});
            pstmt.setInt(1, articuloDetalle.getFacArticulo().getArtCodigo());
            pstmt.setBigDecimal(2, articuloDetalle.getArtCantidadIgresada());
            pstmt.setBigDecimal(3, articuloDetalle.getArtCantidadSaliente());
            pstmt.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
            pstmt.setBigDecimal(5, articuloDetalle.getArtValorUnitario());
            pstmt.setBigDecimal(6, articuloDetalle.getArtSaldo());
            pstmt.setString(7, articuloDetalle.getArtAutorizacion());
            pstmt.executeUpdate();

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
    public FacArticuloDetalle find(int id) throws SQLException {
        FacArticuloDetalle articuloDetalle = null;

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM FAC_ARTICULO_DETALLE WHERE ART_CODIGO = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                articuloDetalle = new FacArticuloDetalle();
                articuloDetalle.setFacArticulo(new FacArticulo());
                articuloDetalle.setArtCodigoDetalle(rs.getInt(1));
                articuloDetalle.getFacArticulo().setArtCodigo(rs.getInt(2));
                articuloDetalle.setArtCantidadIgresada(rs.getBigDecimal(3));
                articuloDetalle.setArtCantidadSaliente(rs.getBigDecimal(4));
                articuloDetalle.setArtFechatransacccion(rs.getDate(5));
                articuloDetalle.setArtValorUnitario(rs.getBigDecimal(6));
                articuloDetalle.setArtSaldo(rs.getBigDecimal(7));
                articuloDetalle.setArtAutorizacion(rs.getString(8));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articuloDetalle;
    }
    
    @Override
    public List<FacArticuloDetalle> findAll() throws SQLException{
        List<FacArticuloDetalle> articulosDetalle = new ArrayList<FacArticuloDetalle>();
        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM FAC_ARTICULO_DETALLE ");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                FacArticuloDetalle articuloDetalle = new FacArticuloDetalle();
                articuloDetalle = new FacArticuloDetalle();
                articuloDetalle.setFacArticulo(new FacArticulo());
                articuloDetalle.setArtCodigoDetalle(rs.getInt(1));
                articuloDetalle.getFacArticulo().setArtCodigo(rs.getInt(2));
                articuloDetalle.setArtCantidadIgresada(rs.getBigDecimal(3));
                articuloDetalle.setArtCantidadSaliente(rs.getBigDecimal(4));
                articuloDetalle.setArtFechatransacccion(rs.getDate(5));
                articuloDetalle.setArtValorUnitario(rs.getBigDecimal(6));
                articuloDetalle.setArtSaldo(rs.getBigDecimal(7));
                articuloDetalle.setArtAutorizacion(rs.getString(8));
                articulosDetalle.add(articuloDetalle);
            }
            
        } catch (Exception e) {
        }
        return articulosDetalle ;
    }
    
     @Override
    public List<FacArticuloDetalle> findAllXCod(int id) throws SQLException{
        List<FacArticuloDetalle> articulosDetalle = new ArrayList<FacArticuloDetalle>();
        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM FAC_ARTICULO_DETALLE WHERE ART_CODIGO = ? ORDER BY 1");
             pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                FacArticuloDetalle articuloDetalle = new FacArticuloDetalle();
                articuloDetalle = new FacArticuloDetalle();
                articuloDetalle.setFacArticulo(new FacArticulo());
                articuloDetalle.setArtCodigoDetalle(rs.getInt(1));
                articuloDetalle.getFacArticulo().setArtCodigo(rs.getInt(2));
                articuloDetalle.setArtCantidadIgresada(rs.getBigDecimal(3));
                articuloDetalle.setArtCantidadSaliente(rs.getBigDecimal(4));
                articuloDetalle.setArtFechatransacccion(rs.getDate(5));
                articuloDetalle.setArtValorUnitario(rs.getBigDecimal(6));
                articuloDetalle.setArtSaldo(rs.getBigDecimal(7));
                articuloDetalle.setArtAutorizacion(rs.getString(8));
                articulosDetalle.add(articuloDetalle);
            }
            
        } catch (Exception e) {
        }
        return articulosDetalle ;
    }
}
