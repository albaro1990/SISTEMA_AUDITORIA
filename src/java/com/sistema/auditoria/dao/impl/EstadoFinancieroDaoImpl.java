package com.sistema.auditoria.dao.impl;

import com.sistema.auditoria.conexion.ConexionDB;
import com.sistema.auditoria.dao.EstadoFinancieroDao;
import com.sistema.auditoria.dto.AudEstadoFinancieroDTO;
import com.sistema.auditoria.entity.AudDetEstadoFinan;
import com.sistema.auditoria.entity.AudEstadoFinanciero;
import com.sistema.auditoria.entity.AudEstructuraAsignacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.omg.PortableServer.IdUniquenessPolicy;


public class EstadoFinancieroDaoImpl implements EstadoFinancieroDao {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt;

    @Override
    public int save(List<AudEstadoFinancieroDTO> audEstadoFinanciero) throws SQLException {
        int idInserted = 0;
        try {
            conn = new ConexionDB().getConexion();
           Long codEmpresa = audEstadoFinanciero.get(0).getEmpresa().getEmpCodigo();
           String nombreArchivo = audEstadoFinanciero.get(0).getNombreArchivo();
            pstmt = conn.prepareStatement("INSERT INTO AUD_ESTADO_FINANCIERO(EST_CODIGO,"
                    + "EMP_CODIGO,"
                    + "EST_NOMBRE_ARCHIVO, "
                    + "EST_FECHA_SUBIDA) "
                    + "values (AUD_SEQ_CAB_ESTADO_FINAN.NEXTVAL, "+codEmpresa
                    +",'"+nombreArchivo+"','SYSDATE()')", new String[]{"EST_CODIGO"});
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Error al crear la descripcion");
            }
            rs = pstmt.getGeneratedKeys();  
            if (rs.next()) {
                idInserted = rs.getInt(1);
            
                if(idInserted>0){
                    this.guardarDetalleEstFinan(audEstadoFinanciero, idInserted);
                }
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

    public void guardarDetalleEstFinan(List<AudEstadoFinancieroDTO> listaDetalleFinan, int idCabecera){
         int idInserted = 0;
        try {
            conn = new ConexionDB().getConexion();
           
            
            for (AudEstadoFinancieroDTO audDetEstadoFinan : listaDetalleFinan) {
                pstmt = conn.prepareStatement("INSERT INTO AUD_SEQ_DET_ESTADO_FINAN(DET_CODIGO,"
                    + "EST_CODIGO,"
                    + "DET_DEBE, "
                    + "DET_HABER, "
                    + "DET_SALDO_INICIAL, "
                    + "DET_SALDO_FINAL, "
                    + "EST_FECHA_SUBIDA) "
                    + "values (AUD_SEQ_CAB_ESTADO_FINAN.NEXTVAL, "+idCabecera+","
                    + ""+audDetEstadoFinan.getDebe()+","+audDetEstadoFinan.getHaber()+","+audDetEstadoFinan.getSaldoInicial()+","
                    + ""+audDetEstadoFinan.getSaldoFinal()+")", new String[]{"DET_CODIGO"});
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Error al crear la descripcion");
            }
            rs = pstmt.getGeneratedKeys();  
            if (rs.next()) {
                idInserted = rs.getInt(1);
            }
            }
        } catch (Exception e) {
        }
    }
    @Override
    public int update(AudEstructuraAsignacion estructuraasignacion) throws SQLException {
        int nup = 0;
        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("UPDATE AUD_ESTRUCTURA_ASIGNACION SET "
                    + "ESTR_DESCRIPCION='"+estructuraasignacion.getEstrDescripcion()+"',"
                    + "ESTR_ESTADO="+estructuraasignacion.getEstrEstado()+""
                    + " WHERE ESTR_CODIGO = "+estructuraasignacion.getEstrCodigo()+" ");
            

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
    public List<AudEstructuraAsignacion> findAll() throws SQLException {
        List<AudEstructuraAsignacion> estructuraasignacion = new ArrayList<AudEstructuraAsignacion>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM AUD_ESTRUCTURA_ASIGNACION WHERE ESTR_ESTADO=1 ");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AudEstructuraAsignacion estructuraAsignacion = new AudEstructuraAsignacion();
                
                
                estructuraAsignacion.setEstrCodigo(rs.getLong(1));
                estructuraAsignacion.setEstrDescripcion(rs.getString(2));
                estructuraAsignacion.setEstrEstado(rs.getInt(3));
                estructuraasignacion.add(estructuraAsignacion);
              
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
    public AudEstructuraAsignacion find(int id) throws SQLException {
        
       AudEstructuraAsignacion estructuraAsignacion = null;
       
       try {
            
           conn = new ConexionDB().getConexion();
           pstmt = conn.prepareStatement("SELECT * FROM AUD_ESTRUCTURA_ASIGNACION  WHERE ESTR_CODIGO= ? AND ESTR_ESTADO=1");
           pstmt.setInt(1, id);
           rs = pstmt.executeQuery();
           
            while (rs.next()) {
                estructuraAsignacion = new AudEstructuraAsignacion();
                estructuraAsignacion.setEstrCodigo(rs.getLong(1));
                estructuraAsignacion.setEstrDescripcion(rs.getString(2));
                estructuraAsignacion.setEstrEstado(rs.getInt(3));
               
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