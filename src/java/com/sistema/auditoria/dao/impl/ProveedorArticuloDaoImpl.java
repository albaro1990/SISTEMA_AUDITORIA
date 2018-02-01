package com.sistema.auditoria.dao.impl;

import com.sistema.auditoria.conexion.ConexionDB;
import com.sistema.auditoria.dao.ProveedorArticuloDao;
import com.sistema.auditoria.entity.FacArticulo;
import com.sistema.auditoria.entity.FacProveedor;
import com.sistema.auditoria.entity.FacProveedorArticulo;
import com.sistema.auditoria.entity.AudUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProveedorArticuloDaoImpl implements ProveedorArticuloDao {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt;

    @Override
    public int save(FacProveedorArticulo proveedorArticulo) throws SQLException {
        int idInserted = 0;
        StringBuilder sql = new StringBuilder();
        try {
            conn = new ConexionDB().getConexion();
            sql.append("INSERT INTO FAC_PROVEEDOR_ARTICULO (PAR_CODIGO, PRO_CODIGO, ART_CODIGO) VALUES (FAC_SEQ_PROVEEDOR_ARTICULO.NEXTVAL, ?, ?)");
            pstmt = conn.prepareStatement(sql.toString(), new String[]{"PAR_CODIGO"});
            pstmt.setBigDecimal(1, proveedorArticulo.getFacProveedor().getProCodigo());
            pstmt.setInt(2, proveedorArticulo.getFacArticulo().getArtCodigo());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear el articulo");
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
    public int update(FacProveedorArticulo proveedorArticulo) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FacProveedorArticulo> findAll() throws SQLException {
        List<FacProveedorArticulo> listaProveedorArticulos = new ArrayList<FacProveedorArticulo>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT PA.* FROM FAC_PROVEEDOR_ARTICULO PA, FAC_ARTICULO ART WHERE PA.ART_CODIGO= ART.ART_CODIGO AND ART_ESTADO=1");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FacProveedorArticulo proveedorArticulo = new FacProveedorArticulo();
                proveedorArticulo.setFacArticulo(new FacArticulo());
                proveedorArticulo.setFacProveedor(new FacProveedor());
                
                proveedorArticulo.setParCodigo(rs.getInt(1));
                proveedorArticulo.getFacProveedor().setProCodigo(rs.getBigDecimal(2));                
                proveedorArticulo.getFacArticulo().setArtCodigo(rs.getInt(3));
                listaProveedorArticulos.add(proveedorArticulo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }
        return listaProveedorArticulos;
    }

    @Override
    public FacProveedorArticulo find(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     @Override
    public boolean existePorCampo(Integer codigoArticulo, Integer codigoProveedor) throws SQLException {
        try {
            Integer rowCount = 0;
            conn = new ConexionDB().getConexion(); 
            pstmt = conn.prepareStatement("SELECT COUNT(*) AS CONTADOR FROM FAC_PROVEEDOR_ARTICULO WHERE ART_CODIGO="+codigoArticulo+" and PRO_CODIGO= "+codigoProveedor+"");
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
