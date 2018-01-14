package com.citas.medicas.dao.impl;

import com.citas.medicas.conexion.ConexionDB;
import com.citas.medicas.dao.AntPersonalesDao;
import com.citas.medicas.entity.CitCita;
import com.citas.medicas.entity.CitPaciente;
import com.citas.medicas.entity.FacUsuarioAplicacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.citas.medicas.dao.CitaDao;
import com.citas.medicas.entity.CitAntPersonales;
import com.citas.medicas.entity.FacUsuario;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AntPersonalesDaoImpl implements AntPersonalesDao {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt;

    @Override
    public int save(CitAntPersonales antPersonales) throws SQLException {
        int idInserted = 0;
        StringBuilder sql = new StringBuilder();
        try {

            conn = new ConexionDB().getConexion();
            sql.append(" INSERT INTO CIT_ANT_PERSONALES(ANTPER_CODIGO, PAC_CODIGO, ANTPER_NUMHIJOS, ANTPER_NUMABORTOS, "
                    + " ANTPER_ENF_INFANCIA, ANTPER_QUIRURGICOS,ANTPER_ALERGIAS, ANTPER_VIH,ANTPER_MENARCA,ANTPER_RITMO_MENSTRUAL, "
                    + " ANTPER_FECHA_ULTIMA_MENSTRUACI,ANTPER_TRAUMATICOS,ANTPER_HOSPITALIZACIONES_PREVI,ANTPER_ADICCIONES,ANTPER_OTROS)"
                    + " VALUES (CIT_SEQ_ANT_PER.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
            //antPersonales.setPacCodigo(new CitPaciente());

            pstmt = conn.prepareStatement(sql.toString(), new String[]{"ANTPER_CODIGO"});
            pstmt.setLong(1, antPersonales.getPacCodigo().getPacCodigo());
            if (antPersonales.getNumHijos() != null) {
                pstmt.setInt(2, antPersonales.getNumHijos());
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }
            if (antPersonales.getNumAbortos() != null) {
                pstmt.setInt(3, antPersonales.getNumAbortos());
            } else {
                pstmt.setNull(3, Types.INTEGER);
            }
            if (antPersonales.getEnfInfancia() != null) {
                pstmt.setString(4, antPersonales.getEnfInfancia());
            } else {
                pstmt.setNull(4, Types.VARCHAR);
            }
            if (antPersonales.getQuirurgicos() != null) {
                pstmt.setString(5, antPersonales.getQuirurgicos());
            } else {
                pstmt.setNull(5, Types.VARCHAR);
            }
            if (antPersonales.getAlergias() != null) {
                pstmt.setString(6, antPersonales.getAlergias());
            } else {
                pstmt.setNull(6, Types.VARCHAR);
            }
            if (antPersonales.getVih() != null) {
                pstmt.setString(7, antPersonales.getVih());
            } else {
                pstmt.setNull(7, Types.VARCHAR);
            }
            if (antPersonales.getRitmoMenstrual() != null) {
                pstmt.setString(8, antPersonales.getRitmoMenstrual());
            } else {
                pstmt.setNull(8, Types.VARCHAR);
            }
            if (antPersonales.getEdadMenarquia() != null) {
                pstmt.setInt(9, antPersonales.getEdadMenarquia());
            } else {
                pstmt.setNull(9, Types.INTEGER);
            }
            if (antPersonales.getFechaUltMesnstruacion() != null) {
                pstmt.setDate(10, new java.sql.Date(antPersonales.getFechaUltMesnstruacion().getDate()));
            } else {
                pstmt.setNull(10, Types.DATE);
            }
            if (antPersonales.getAntTraumaticas() != null) {
                pstmt.setString(11, antPersonales.getAntTraumaticas());
            } else {
                pstmt.setNull(11, Types.VARCHAR);
            }
            if (antPersonales.getHospitalizacionAnteriores() != null) {
                pstmt.setString(12, antPersonales.getHospitalizacionAnteriores());
            } else {
                pstmt.setNull(12, Types.VARCHAR);
            }
            if (antPersonales.getAdicciones() != null) {
                pstmt.setString(13, antPersonales.getAdicciones());
            } else {
                pstmt.setNull(13, Types.VARCHAR);
            }
            if (antPersonales.getOtros() != null) {
                pstmt.setString(14, antPersonales.getOtros());
            } else {
                pstmt.setNull(14, Types.VARCHAR);
            }

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear Ant Personales");
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
    public int update(CitCita cita) throws SQLException {
        int nup = 0;
        StringBuilder sql = new StringBuilder();
        try {
            String horaMin = this.formatHoras(cita.getHoraCita(), "dd/MM/yyyy HH:mm:ss");
            conn = new ConexionDB().getConexion();
            sql.append("UPDATE CIT_CITA SET CIT_ESTADO= ?, CIT_HORA=?, CIT_FECHA=? WHERE CIT_CODIGO = ? ");
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setInt(1, cita.getCitEstado().intValue());
            pstmt.setString(2, horaMin);
            pstmt.setDate(3, new java.sql.Date(cita.getCitFechaCita().getTime()));
            pstmt.setLong(4, cita.getCitCodigo());
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
            pstmt = conn.prepareStatement("DELETE FROM CIT_CITA WHERE CAB_CODIGO = " + id + "");
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
    public List<CitCita> findAll() throws SQLException {
        List<CitCita> citas = new ArrayList<CitCita>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM CIT_CITA WHERE CIT_ESTADO NOT IN(0) ");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CitCita cita = new CitCita();
                cita.setCitCodigo(rs.getLong(1));
                cita.setUsuario(new FacUsuario());
                cita.getUsuario().setUsuCodigo(rs.getLong(2));
                cita.setCliCodigo(new CitPaciente());
                cita.getCliCodigo().setPacCodigo(rs.getLong(3));
                cita.setCitFechaCita(rs.getDate(4));
                String hora = rs.getString(5);
                this.formatDate(hora);
                cita.setHoraCita(this.formatDate(hora));
                cita.setCitEstado(rs.getInt(6));
                if (rs.getString(7) != null) {
                    cita.setCitMotivo(rs.getString(7));
                }
                citas.add(cita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }

        return citas;
    }

    @Override
    public CitAntPersonales findXIdPaciente(int id) throws SQLException {

        CitAntPersonales antPersonales = null;

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM CIT_ANT_PERSONALES WHERE PAC_CODIGO = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                antPersonales = new CitAntPersonales();
                antPersonales.setAntPerCodigo(rs.getLong(1));
                antPersonales.setPacCodigo(new CitPaciente());
                antPersonales.getPacCodigo().setPacCodigo(rs.getLong(2));
                antPersonales.setNumHijos(rs.getInt(3));
                antPersonales.setNumAbortos(rs.getInt(4));
                antPersonales.setEnfInfancia(rs.getString(5));
                antPersonales.setQuirurgicos(rs.getString(6));
                antPersonales.setAlergias(rs.getString(7));
                antPersonales.setVih(rs.getString(8)); 
                antPersonales.setEdadMenarquia(rs.getInt(9));
                antPersonales.setRitmoMenstrual(rs.getString(10));
                antPersonales.setFechaUltMesnstruacion(rs.getDate(11));
                antPersonales.setAntTraumaticas(rs.getString(12));
                antPersonales.setHospitalizacionAnteriores(rs.getString(13));
                antPersonales.setAdicciones(rs.getString(14));
                antPersonales.setOtros(rs.getString(15));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }
        return antPersonales;
    }

    public static String formatHoras(Date date, String formato) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
        String hora = simpleDateFormat.format(date);
        hora = hora.substring(11, 16);
        return hora;

    }

    /**
     * Método que retorna un objeto de tipo Date dado la fecha basado en el
     * formato dd/mm/yyyy
     *
     * @param fecha La fecha que se desea parsear.
     * @return La fecha en formato Date.
     */
    public static Date formatDate(String fecha) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        try {
            return simpleDateFormat.parse(fecha);
        } catch (ParseException e) {
            throw new RuntimeException("Error en el parseo de la fecha: " + fecha);
        }
    }

    public static Date formatFecha(String fecha, String formato) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
        try {
            return simpleDateFormat.parse(fecha);
        } catch (ParseException e) {
            throw new RuntimeException("Error en el parseo de la fecha: " + fecha);
        }
    }

    public static String formatFechaString(Date date, String formato) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
        String fecha = simpleDateFormat.format(date);
        return fecha;

    }

    @Override
    public int cacelar(int id) throws SQLException {
        int nup = 0;
        StringBuilder sql = new StringBuilder();
        try {
            conn = new ConexionDB().getConexion();
            sql.append("UPDATE CIT_CITA SET CIT_ESTADO= 0 WHERE CIT_CODIGO = ? ");
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, id);
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
    public List<CitCita> findAllXMedico() throws SQLException {
        List<CitCita> citas = new ArrayList<CitCita>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM CIT_CITA WHERE CIT_ESTADO NOT IN(0) ");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CitCita cita = new CitCita();
                cita.setCitCodigo(rs.getLong(1));
                cita.setUsuario(new FacUsuario());
                cita.getUsuario().setUsuCodigo(rs.getLong(2));
                cita.setCliCodigo(new CitPaciente());
                cita.getCliCodigo().setPacCodigo(rs.getLong(3));
                String fechaCita = this.formatFechaString(rs.getDate(4), "dd/MM/yyyy");
                String fechaHoraCita = fechaCita + " " + rs.getString(5);
                cita.setCitFechaCita(this.formatFecha(fechaHoraCita, "dd/MM/yyyy HH:mm"));
                String hora = rs.getString(5);
                this.formatDate(hora);
                cita.setHoraCita(this.formatDate(hora));
                cita.setCitEstado(rs.getInt(6));
                if (rs.getString(7) != null) {
                    cita.setCitMotivo(rs.getString(7));
                }
                citas.add(cita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }

        return citas;
    }

}
