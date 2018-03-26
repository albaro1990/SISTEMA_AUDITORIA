package com.sistema.auditoria.dao.impl;

import com.sistema.auditoria.conexion.ConexionDB;
import com.sistema.auditoria.entity.AudEstructuraAsignacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.sistema.auditoria.dao.EstructuraAsignacionEmpresaDao;
import com.sistema.auditoria.entity.AudAsignarEstructuraEmpresa;
import com.sistema.auditoria.entity.AudPlanCuentas;


public class EstructuraAsignacionEmpresaDaoImpl implements EstructuraAsignacionEmpresaDao {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt;
    

    @Override
    public int save(AudAsignarEstructuraEmpresa estructuraEmpresa) throws SQLException {
        int idInserted = 0;
        try {
            conn = new ConexionDB().getConexion();
           
            pstmt = conn.prepareStatement("INSERT INTO AUD_ESTRUCTURA_EMPRESA(EST_CODIGO,"
                    + "PLAN_CODIGO,"
                    + "ESTR_CODIGO,"
                    + "EMP_CODIGO,"
                    + "EST_DESCRIPCION_CUENTA) "
                    + "values (AUD_SEQ_ASIG_EST_EMP.NEXTVAL, "+estructuraEmpresa.getPlanCuentas().getCodPlanCta()
                    +","+estructuraEmpresa.getEstructuraAsig().getEstrCodigo() +","
                    + " "+estructuraEmpresa.getCodigoEmpresa()+",'"+estructuraEmpresa.getDescCta()+"')", new String[]{"EST_CODIGO"});
            
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
    public int update(AudAsignarEstructuraEmpresa estructuraasignacion) throws SQLException {
        int nup = 0;
        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("UPDATE AUD_ESTRUCTURA_ASIGNACION SET "
                    + "ESTR_DESCRIPCION='"+"set"+"',"
                    + "ESTR_ESTADO="+"stet"+""
                    + " WHERE ESTR_CODIGO = "+"set"+" ");
            

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
    public List<AudAsignarEstructuraEmpresa> findAll() throws SQLException {
        List<AudAsignarEstructuraEmpresa> estructuraasignacion = new ArrayList<AudAsignarEstructuraEmpresa>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM AUD_ESTRUCTURA_ASIGNACION WHERE ESTR_ESTADO=1 ");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AudAsignarEstructuraEmpresa estructuraAsignacion = new AudAsignarEstructuraEmpresa();
                
                
                /*estructuraAsignacion.setEstrCodigo(rs.getLong(1));
                estructuraAsignacion.setEstrDescripcion(rs.getString(2));
                estructuraAsignacion.setEstrEstado(rs.getInt(3));
                estructuraasignacion.add(estructuraAsignacion);*/
              
            }
        } catch (SQLException e) {
            e.printStackTrace();
        
        }

        return estructuraasignacion;
    }
    
    
    @Override
    public List<AudEstructuraAsignacion> findByEmp(Integer codEmp) throws SQLException {
        List<AudEstructuraAsignacion> estructurasAsignacion = new ArrayList<AudEstructuraAsignacion>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT DISTINCT ASI.* "
                    + "FROM AUD_ESTRUCTURA_EMPRESA ESTEM, AUD_ESTRUCTURA_ASIGNACION ASI "
                    + "WHERE ESTEM.ESTR_CODIGO = ASI.ESTR_CODIGO AND EMP_CODIGO = "+codEmp+"");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AudEstructuraAsignacion estructuraAsignacion = new AudEstructuraAsignacion();
                
                
                estructuraAsignacion.setEstrCodigo(rs.getLong(1));
                estructuraAsignacion.setEstrDescripcion(rs.getString(2));
                estructuraAsignacion.setEstrEstado(rs.getInt(3));
                estructurasAsignacion.add(estructuraAsignacion);
              
            }
        } catch (SQLException e) {
            e.printStackTrace();
        
        }

        return estructurasAsignacion;
    }
    
     @Override
    public List<AudAsignarEstructuraEmpresa> findByEmpYEst(Integer codEmp, Long codEst) throws SQLException {
        List<AudAsignarEstructuraEmpresa> estructurasAsignacion = new ArrayList<AudAsignarEstructuraEmpresa>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT DISTINCT ESTEM.* FROM AUD_ESTRUCTURA_EMPRESA ESTEM, AUD_ESTRUCTURA_ASIGNACION ASI "
                    + "WHERE ESTEM.ESTR_CODIGO = ASI.ESTR_CODIGO "
                    + "AND EMP_CODIGO = "+codEmp+" AND ESTEM.ESTR_CODIGO = "+codEst+"");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AudAsignarEstructuraEmpresa estructuraAsignacion = new AudAsignarEstructuraEmpresa();
                estructuraAsignacion.setCodigo(rs.getInt(1));
                AudPlanCuentas planCuenta = new AudPlanCuentas();
                planCuenta.setCodPlanCta(rs.getLong(2));
                estructuraAsignacion.setPlanCuentas(planCuenta);
                AudEstructuraAsignacion estructura = new AudEstructuraAsignacion();
                estructura.setEstrCodigo(rs.getLong(3));
                estructuraAsignacion.setEstructuraAsig(estructura);
                estructuraAsignacion.setCodigoEmpresa(rs.getLong(2));
                estructuraAsignacion.setDescCta(rs.getString(3));
                
                
                estructurasAsignacion.add(estructuraAsignacion);
              
            }
        } catch (SQLException e) {
            e.printStackTrace();
        
        }

        return estructurasAsignacion;
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
    public AudAsignarEstructuraEmpresa find(int id) throws SQLException {
        
       AudAsignarEstructuraEmpresa estructuraAsignacion = null;
       
       try {
            
           conn = new ConexionDB().getConexion();
           pstmt = conn.prepareStatement("SELECT * FROM AUD_ESTRUCTURA_ASIGNACION  WHERE ESTR_CODIGO= ? AND ESTR_ESTADO=1");
           pstmt.setInt(1, id);
           rs = pstmt.executeQuery();
           
            while (rs.next()) {
                estructuraAsignacion = new AudAsignarEstructuraEmpresa();
                /*estructuraAsignacion.setEstrCodigo(rs.getLong(1));
                estructuraAsignacion.setEstrDescripcion(rs.getString(2));
                estructuraAsignacion.setEstrEstado(rs.getInt(3));*/
               
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