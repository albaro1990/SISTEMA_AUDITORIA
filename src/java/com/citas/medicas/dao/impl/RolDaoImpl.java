package com.citas.medicas.dao.impl;

import com.citas.medicas.conexion.ConexionDB;
import com.citas.medicas.dao.RolDao;
import com.citas.medicas.entity.FacRol;
import com.citas.medicas.entity.FacUsuario;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RolDaoImpl implements RolDao {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt;

    @Override
    public int save(FacRol rol) throws SQLException {
        int idInserted = 0;
        try {
            conn = new ConexionDB().getConexion();
           
            pstmt = conn.prepareStatement("INSERT INTO FAC_ROL(ROL_NOMBRE,ROL_ESTADO) "
                    + "values ('"+rol.getRolNombre()+"',"+rol.getRolEstado()+")", Statement.RETURN_GENERATED_KEYS);
            
            pstmt.executeUpdate();

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
    public int update(FacRol rol) throws SQLException {
        int nup = 0;
        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("UPDATE FAC_ROL SET ROL_NOMBRE='"+rol.getRolNombre()+"',ROL_ESTADO='"+rol.getRolEstado()+"'"
                    + " WHERE USU_CODIGO = "+rol.getRolCodigo()+" ");
            

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
            pstmt = conn.prepareStatement("DELETE FROM FAC_ROL WHERE ROL_CODIGO = "+id+"");
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
    public List<FacRol> findAll() throws SQLException {
        List<FacRol> roles = new ArrayList<FacRol>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM AUD_ROL WHERE ROL_ESTADO=1");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FacRol rol = new FacRol();
                rol.setRolCodigo(rs.getBigDecimal(1));
                rol.setRolNombre(rs.getString(2));
                rol.setRolEstado(rs.getInt(3));
                roles.add(rol);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roles;
    }

    @Override
    public FacRol find(int id) throws SQLException {
        FacRol rol = null;
        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM CIT_ROL WHERE ROL_CODIGO = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                rol= new FacRol();
                rol.setRolCodigo(rs.getBigDecimal(1));
                rol.setRolNombre(rs.getString(2));
                rol.setRolEstado(rs.getInt(3));
               
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rol;
    }
}
