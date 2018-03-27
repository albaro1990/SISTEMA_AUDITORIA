package com.sistema.auditoria.dao.impl;

import com.sistema.auditoria.conexion.ConexionDB;
import com.sistema.auditoria.entity.AudEstructuraAsignacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.sistema.auditoria.dao.PlandeCuentasDao;
import com.sistema.auditoria.entity.AudEmpresa;
import com.sistema.auditoria.entity.AudPlanCuentas;


public class PlandeCuentasDaoImpl implements PlandeCuentasDao {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt;

    @Override
    public int save(AudPlanCuentas planCta) throws SQLException {
        int idInserted = 0;
        try {
            conn = new ConexionDB().getConexion();
           
            pstmt = conn.prepareStatement("INSERT INTO AUD_PLAN_CUENTA(PLAN_CODIGO,"
                    + "EMP_CODIGO,"
                    + "PLAN_NUMERO_CUENTA,"
                    + "PLAN_DESCRIPCION_CUENTA,"
                    + "PLAN_ESTADO) "
                    + "values (AUD_SEQ_PLAN_CTA.NEXTVAL,"+planCta.getEmpresa().getEmpCodigo()
                    + ",'" + planCta.getNumeroCta() +"',"
                    +"'"+planCta.getDescCta()+"',"+planCta.getEstado()+")", new String[]{"PLAN_CODIGO"});
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Error al crear la descripcion");
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
    public int update(AudPlanCuentas estructuraasignacion) throws SQLException {
        int nup = 0;
        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("UPDATE AUD_ESTRUCTURA_ASIGNACION SET "
                    + "ESTR_DESCRIPCION='"+""+"',"
                    + "ESTR_ESTADO="+""+""
                    + " WHERE ESTR_CODIGO = "+""+" ");
            

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
            pstmt = conn.prepareStatement("DELETE FROM AUD_ESTRUCTURA_ASIGNACION WHERE ESTR_CODIGO = "+id+"");
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
    public List<AudPlanCuentas> findAll() throws SQLException {
        List<AudPlanCuentas> estructuraasignacion = new ArrayList<AudPlanCuentas>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM AUD_ESTRUCTURA_ASIGNACION WHERE ESTR_ESTADO=1 ");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AudPlanCuentas audPlanCuentas = new AudPlanCuentas();
                
              /* audPlanCuentas.
                audPlanCuentas.setEstrDescripcion(rs.getString(2));
                audPlanCuentas.setEstrEstado(rs.getInt(3));
                estructuraasignacion.add(estructuraAsignacion);*/
              
            }
        } catch (SQLException e) {
            e.printStackTrace();
        
        }

        return estructuraasignacion;
    }

   @Override
    public boolean existePorCampo (String descripcion) throws SQLException {

        try {
            Integer rowCount = 0;
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT COUNT (*) AS CONTADOR FROM AUD_ESTRUCTURA_ASIGNACION  C WHERE C.ESTR_DESCRIPCION ='" + descripcion +"'");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                rowCount = rs.getInt("CONTADOR");
            }
            
            return rowCount > 0;
        } catch (SQLException e){
            throw e;
        }  finally{
                conn.close();
                pstmt.close();
        }
    }

    @Override
    public AudPlanCuentas find(int id) throws SQLException {
        
       AudPlanCuentas estructuraAsignacion = null;
       
       try {
            
           conn = new ConexionDB().getConexion();
           pstmt = conn.prepareStatement("SELECT * FROM AUD_PLAN_CUENTA  WHERE PLAN_CODIGO= ? AND PLAN_ESTADO=1");
           pstmt.setInt(1, id);
           rs = pstmt.executeQuery();
           
            while (rs.next()) {
                estructuraAsignacion = new AudPlanCuentas();
                estructuraAsignacion.setCodPlanCta(rs.getLong(1));
                AudEmpresa empresa = new AudEmpresa();
                empresa.setEmpCodigo(rs.getLong(2));
                estructuraAsignacion.setEmpresa(empresa);
                estructuraAsignacion.setNumeroCta(rs.getString(3));
                estructuraAsignacion.setDescCta(rs.getString(4));
           }
      
        } catch (SQLException e) {
            e.printStackTrace();
       }finally {
           conn.close();
            pstmt.close();
        }
        return estructuraAsignacion;
    
    }
    

    @Override
    public Long nuevoCodigo() throws SQLException {
 
        try {
            Long max = Long.valueOf(0);
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT MAX(C.ESTR_CODIGO)+1 FROM AUD_ESTRUCTURA_ASIGNACION C");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                max = rs.getLong(1);
            }
            return max;
        } catch (SQLException e) {
            throw e;
        }
           finally {
            conn.close();
            pstmt.close();
        }
    }
}