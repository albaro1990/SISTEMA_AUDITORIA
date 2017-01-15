package com.facturacion.dao.impl;

import com.facturacion.conexion.ConexionDB;
import com.facturacion.dao.ArticuloDao;
import com.facturacion.entity.FacArticulo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDaoImpl implements ArticuloDao {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt;

    @Override
    public int save(FacArticulo articulo) throws SQLException {
        int idInserted = 0;
        StringBuilder sql = new StringBuilder();
        try {
            conn = new ConexionDB().getConexion();
            sql.append("INSERT INTO FAC_ARTICULO(ART_CODIGO, ART_DESCRIPCION, ART_CANTIDAD, ART_FECHA_INGRESO, ART_ESTADO, ART_UNIDAD, ART_VALOR_UNITARIO, ART_APLICA_IVA, ART_PORCENTAJE_IVA, ART_CODIGO_UNICO) "
                    + " VALUES (FAC_SEQ_ARTICULO.NEXTVAL,?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt = conn.prepareStatement(sql.toString(), new String[]{"ART_CODIGO"});
            pstmt.setString(1, articulo.getArtDescripcion());
            pstmt.setBigDecimal(2, articulo.getArtCantidadIngresada());
            pstmt.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
            pstmt.setInt(4, articulo.getArtEstado());
            pstmt.setString(5, articulo.getArtUnidad());
            pstmt.setBigDecimal(6, articulo.getArtValorUnitario());
            pstmt.setBoolean(7, articulo.getArtAplicaIva());
            pstmt.setInt(8, articulo.getArtPorcentajeIva());
            pstmt.setString(9, articulo.getArtCodigoUnico().toUpperCase());
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
    public int update(FacArticulo articulo) throws SQLException {
        int nup = 0;
        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("UPDATE FAC_ARTICULO SET ART_DESCRIPCION='" + articulo.getArtDescripcion() + "', "
                    + " ART_CANTIDAD=" + articulo.getArtCantidadIngresada() + ", "
                    + " ART_ESTADO=" + articulo.getArtEstado() + ", "
                    + " ART_UNIDAD='" + articulo.getArtUnidad() + "',"
                    + " ART_FECHA_ACTUALIZACION= SYSDATE, ART_VALOR_UNITARIO=" + articulo.getArtValorUnitario() + ", "
                    + " ART_APLICA_IVA=" + articulo.getArtAplicaIvaInt() + ", "
                    + " ART_PORCENTAJE_IVA=" + articulo.getArtPorcentajeIva() + " "
                    + " WHERE ART_CODIGO = " + articulo.getArtCodigo() + " ");

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
            pstmt = conn.prepareStatement("UPDATE FAC_ARTICULO SET ART_ESTADO= 0 WHERE ART_CODIGO = " + id + "");
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
    public List<FacArticulo> findAll() throws SQLException {
        List<FacArticulo> articulos = new ArrayList<>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM FAC_ARTICULO WHERE ART_ESTADO=1 ");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FacArticulo articulo = new FacArticulo();
                articulo.setArtCodigo(rs.getInt(1));
                articulo.setArtDescripcion(rs.getString(2));
                articulo.setArtFechaIngreso(rs.getDate(3));
                articulo.setArtEstado(rs.getInt(4));
                articulo.setArtUnidad(rs.getString(5));
                articulo.setArtFechaActualizacion(rs.getDate(6));
                articulo.setArtAplicaIva(rs.getBoolean(7));
                articulo.setArtPorcentajeIva(rs.getInt(8));
                articulo.setArtCodigoUnico(rs.getString(9));
                articulo.setArtCantidadIngresada(rs.getBigDecimal(10));
                articulo.setArtValorUnitario(rs.getBigDecimal(11));
                articulos.add(articulo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            conn.close();
            pstmt.close();
        }

        return articulos;
    }

    @Override
    public FacArticulo find(int id) throws SQLException {
        FacArticulo articulo = null;

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM FAC_ARTICULO WHERE ART_ESTADO = 1 AND ART_CODIGO = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                articulo = new FacArticulo();
                articulo.setArtCodigo(rs.getInt(1));
                articulo.setArtDescripcion(rs.getString(2));
                articulo.setArtFechaIngreso(rs.getDate(3));
                articulo.setArtEstado(rs.getInt(4));
                articulo.setArtUnidad(rs.getString(5));
                articulo.setArtFechaActualizacion(rs.getDate(6));
                articulo.setArtAplicaIva(rs.getBoolean(7));
                articulo.setArtPorcentajeIva(rs.getInt(8));
                articulo.setArtCodigoUnico(rs.getString(9));
                articulo.setArtCantidadIngresada(rs.getBigDecimal(10));
                articulo.setArtValorUnitario(rs.getBigDecimal(11));        
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            conn.close();
            pstmt.close();
        }
        return articulo;
    }

    @Override
    public boolean existePorCampo(String codigo) throws SQLException {
        try {
            Integer rowCount = 0;
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT COUNT(*) AS CONTADOR FROM FAC_ARTICULO WHERE UPPER (ART_CODIGO_UNICO)='" + codigo.toUpperCase() + "'");
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
    
     @Override
    public FacArticulo findXCodUnico(String id) throws SQLException {
        FacArticulo articulo = null;

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM FAC_ARTICULO WHERE ART_ESTADO = 1 AND UPPER (ART_CODIGO_UNICO) = ?");
            pstmt.setString(1, id.toUpperCase());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                articulo = new FacArticulo();
               articulo.setArtCodigo(rs.getInt(1));
                articulo.setArtDescripcion(rs.getString(2));
                articulo.setArtFechaIngreso(rs.getDate(3));
                articulo.setArtEstado(rs.getInt(4));
                articulo.setArtUnidad(rs.getString(5));
                articulo.setArtFechaActualizacion(rs.getDate(6));
                articulo.setArtAplicaIva(rs.getBoolean(7));
                articulo.setArtPorcentajeIva(rs.getInt(8));
                articulo.setArtCodigoUnico(rs.getString(9));
                articulo.setArtCantidadIngresada(rs.getBigDecimal(10));
                articulo.setArtValorUnitario(rs.getBigDecimal(11));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            conn.close();
            pstmt.close();
        }
        return articulo;
    }
@Override
    public FacArticulo findActIna(int id) throws SQLException {
        FacArticulo articulo = null;

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM FAC_ARTICULO WHERE ART_CODIGO = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                articulo = new FacArticulo();
                articulo.setArtCodigo(rs.getInt(1));
                articulo.setArtDescripcion(rs.getString(2));
                articulo.setArtFechaIngreso(rs.getDate(3));
                articulo.setArtEstado(rs.getInt(4));
                articulo.setArtUnidad(rs.getString(5));
                articulo.setArtFechaActualizacion(rs.getDate(6));
                articulo.setArtAplicaIva(rs.getBoolean(7));
                articulo.setArtPorcentajeIva(rs.getInt(8));
                articulo.setArtCodigoUnico(rs.getString(9));
                articulo.setArtCantidadIngresada(rs.getBigDecimal(10));
                articulo.setArtValorUnitario(rs.getBigDecimal(11));        
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            conn.close();
            pstmt.close();
        }
        return articulo;
    }
    
    @Override
    public FacArticulo findXCodUnicoActIna(String id) throws SQLException {
        FacArticulo articulo = null;

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM FAC_ARTICULO WHERE UPPER (ART_CODIGO_UNICO) = ?");
            pstmt.setString(1, id.toUpperCase());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                articulo = new FacArticulo();
               articulo.setArtCodigo(rs.getInt(1));
                articulo.setArtDescripcion(rs.getString(2));
                articulo.setArtFechaIngreso(rs.getDate(3));
                articulo.setArtEstado(rs.getInt(4));
                articulo.setArtUnidad(rs.getString(5));
                articulo.setArtFechaActualizacion(rs.getDate(6));
                articulo.setArtAplicaIva(rs.getBoolean(7));
                articulo.setArtPorcentajeIva(rs.getInt(8));
                articulo.setArtCodigoUnico(rs.getString(9));
                articulo.setArtCantidadIngresada(rs.getBigDecimal(10));
                articulo.setArtValorUnitario(rs.getBigDecimal(11));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            conn.close();
            pstmt.close();
        }
        return articulo;
    }
}
