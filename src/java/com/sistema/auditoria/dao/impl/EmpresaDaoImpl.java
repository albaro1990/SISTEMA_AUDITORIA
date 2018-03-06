package com.sistema.auditoria.dao.impl;

import com.sistema.auditoria.conexion.ConexionDB;
import com.sistema.auditoria.dao.CiudadDao;
import com.sistema.auditoria.entity.AudEmpresa;
import com.sistema.auditoria.entity.AudCiudad;
import com.sistema.auditoria.entity.FacRol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.sistema.auditoria.dao.EmpresaDao;

public class EmpresaDaoImpl implements EmpresaDao {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt;

    @Override
    public int save(AudEmpresa empresa) throws SQLException {
        int idInserted = 0;
        try {
            conn = new ConexionDB().getConexion();
           
            pstmt = conn.prepareStatement("INSERT INTO AUD_EMPRESA(EMP_CODIGO,"
                    + "CIU_CODIGO,"
                    + "EMP_NOMBRES,"
                    + "EMP_RAZON_SOCIAL,"
                    + "EMP_TELEFONO,"
                    + "EMP_DIRECCION,"
                    + "EMP_CED_RUC,"
                    + "EMP_CORREO,"
                    + "EMP_ESTADO) "
                    + "values (AUD_SEQ_EMPRESA.NEXTVAL,"+empresa.getCodigoCiudad().getCiuCodigo()+" ,'"+empresa.getEmpNombre()+"','"+empresa.getEmpRazon_Social()+"','"+empresa.getEmpTelefono()+"','"+empresa.getEmpDireccion()+"','"+empresa.getEmpCed_Ruc()+"','"+empresa.getEmpCorreo()+"',"
                            + ""+empresa.getEmpEstado()+")", new String[]{"EMP_CODIGO"});
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Error al crear la empresa");
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
    public int update(AudEmpresa empresa) throws SQLException {
        int nup = 0;
        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("UPDATE AUD_EMPRESA SET CIU_CODIGO='"+empresa.getCodigoCiudad().getCiuCodigo()+"',EMP_NOMBRES='"+empresa.getEmpNombre()+"',EMP_RAZON_SOCIAL='"+empresa.getEmpRazon_Social()+"',EMP_TELEFONO='"+empresa.getEmpTelefono()+"',EMP_DIRECCION='"+empresa.getEmpDireccion()+"',"
                    + "EMP_CED_RUC='"+empresa.getEmpCed_Ruc()+"',EMP_CORREO='"+empresa.getEmpCorreo()+"',EMP_ESTADO="+empresa.getEmpEstado()+""
                    + " WHERE EMP_CODIGO = "+empresa.getEmpCodigo()+" ");
            

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
            pstmt = conn.prepareStatement("DELETE FROM AUD_EMPRESA WHERE EMP_CODIGO = "+id+"");
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
    public List<AudEmpresa> findAll() throws SQLException {
        List<AudEmpresa> empresas = new ArrayList<AudEmpresa>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM AUD_EMPRESA ");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AudEmpresa empresa = new AudEmpresa();
                
                empresa.setEmpCodigo(rs.getLong(1));
                empresa.setCodigoCiudad(new AudCiudad());
                empresa.getCodigoCiudad().setCiuCodigo(rs.getBigDecimal(2));
                empresa.setEmpNombre(rs.getString(3));
                empresa.setEmpRazon_Social(rs.getString(4));
                empresa.setEmpTelefono(rs.getString(5));
                empresa.setEmpDireccion(rs.getString(6));
                empresa.setEmpCed_Ruc(rs.getString(7));
                empresa.setEmpCorreo(rs.getString(8));
                empresa.setEmpEstado(rs.getInt(9));
                empresas.add(empresa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empresas;
    }

    @Override
    public AudEmpresa find(int id) throws SQLException {

        AudEmpresa empresa = null;

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM AUD_EMPRESA WHERE EMP_CODIGO = ? AND EMP_ESTADO=1");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                empresa= new AudEmpresa();
                empresa.setEmpCodigo(rs.getLong(1));
                empresa.setEmpCed_Ruc(rs.getString(2));
                empresa.setEmpEstado(rs.getInt(3));
               
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            conn.close();
            pstmt.close();
        }

        return empresa;
    }
    
    @Override
    public boolean existePorCampo(String cedula) throws SQLException {
        try {
            Integer rowCount = 0;
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT COUNT(*) AS CONTADOR FROM AUD_EMPRESA C WHERE C.EMP_CED_RUC='" + cedula + "'");
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
            pstmt = conn.prepareStatement("SELECT MAX(C.EMP_CODIGO)+1 FROM EMP_CED_RUC C");
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
