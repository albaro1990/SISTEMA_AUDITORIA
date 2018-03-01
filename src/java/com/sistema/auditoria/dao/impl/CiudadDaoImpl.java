package com.sistema.auditoria.dao.impl;

import com.sistema.auditoria.conexion.ConexionDB;
import com.sistema.auditoria.dao.CiudadDao;
import com.sistema.auditoria.entity.AudCiudad;
import com.sistema.auditoria.entity.FacRol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CiudadDaoImpl implements CiudadDao {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt;

    @Override
    public int save(AudCiudad ciudad) throws SQLException {
        int idInserted = 0;
        try {
            conn = new ConexionDB().getConexion();
           
            pstmt = conn.prepareStatement("INSERT INTO AUD_CIUDAD(CIU_CODIGO,CIU_NOMBRE,CIU_ESTADO) "
                    + "values (AUD_SEQ_CIUDAD.NEXTVAL, '"+ciudad.getCiuNombre()+"',"+ciudad.getCiuEstado()+")", new String[]{"CIU_CODIGO"});
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Error al crear el ciudad");
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
    public int update(AudCiudad ciudad) throws SQLException {
        int nup = 0;
        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("UPDATE AUD_CIUDAD SET CIU_NOMBRE='"+ciudad.getCiuNombre()+"',CIU_ESTADO="+ciudad.getCiuEstado()+""
                    + " WHERE CIU_CODIGO = "+ciudad.getCiuCodigo()+" ");
            

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
            pstmt = conn.prepareStatement("DELETE FROM AUD_CIUDAD WHERE CIU_CODIGO = "+id+"");
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
    public List<AudCiudad> findAll() throws SQLException {
        List<AudCiudad> ciudades = new ArrayList<AudCiudad>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM AUD_CIUDAD ");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AudCiudad ciudad = new AudCiudad();
                ciudad.setCiuCodigo(rs.getBigDecimal(1));
                ciudad.setCiuNombre(rs.getString(2));
                ciudad.setCiuEstado(rs.getInt(3));
                ciudades.add(ciudad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ciudades;
    }

    @Override
    public AudCiudad find(int id) throws SQLException {

        AudCiudad ciudad = null;

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM AUD_CIUDAD WHERE CIU_CODIGO = ? AND CIU_ESTADO=1");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ciudad= new AudCiudad();
                ciudad.setCiuCodigo(rs.getBigDecimal(1));
                ciudad.setCiuNombre(rs.getString(2));
                ciudad.setCiuEstado(rs.getInt(3));
               
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            conn.close();
            pstmt.close();
        }

        return ciudad;
    }
    
    @Override
    public boolean existePorCampo(String nombre) throws SQLException {
        try {
            Integer rowCount = 0;
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT COUNT(*) AS CONTADOR FROM AUD_CIUDAD C WHERE C.CIU_NOMBRE='" + nombre + "'");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                rowCount = rs.getInt("CONTADOR");
            }
            return rowCount > 0;
        } catch (SQLException e) {
            throw e;
        }finally {
            conn.close();
            pstmt.close();
        }
    }

    @Override
    public Long nuevoCodigo() throws SQLException {
        try {
            Long max = Long.valueOf(0);
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT MAX(C.CIU_CODIGO)+1 FROM AUD_CIUDAD C");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                max = rs.getLong(1);
            }
            return max;
        } catch (SQLException e) {
            throw e;
        }finally {
            conn.close();
            pstmt.close();
        }
    }
}
