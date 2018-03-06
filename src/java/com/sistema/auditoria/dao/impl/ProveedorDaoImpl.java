package com.sistema.auditoria.dao.impl;

import com.sistema.auditoria.conexion.ConexionDB;
import com.sistema.auditoria.entity.AudCiudad;
import com.sistema.auditoria.entity.FacProveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.sistema.auditoria.dao.ProveedorDao;

public class ProveedorDaoImpl implements ProveedorDao {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt; 

    @Override
    public int save(FacProveedor proveedor) throws SQLException {
        int idInserted = 0;
        try {
            conn = new ConexionDB().getConexion();

            pstmt = conn.prepareStatement("INSERT INTO FAC_PROVEEDOR(PRO_CODIGO, CIU_CODIGO,PRO_NOMBRE,PRO_IDENTIFICAION,PRO_FECHA_CREACION,PRO_RAZON_SOCIAL,PRO_ESTADO,PRO_DIRECCION,PRO_TELEFONO ) "
                    + "values (FAC_SEQ_PROVEEDOR.NEXTVAL," + proveedor.getCiuCodigo().getCiuCodigo() + ",'" + proveedor.getProNombre() + "','" + proveedor.getProIdentificaion() + "',SYSDATE,"
                    + " '" + proveedor.getProRazonSocial() + "'," + proveedor.getProEstado() + ",'" + proveedor.getProDireccion() + "', '" + proveedor.getProTelefono() + "')", new String[]{"PRO_CODIGO"});

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear el Proveedor");
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
    public int update(FacProveedor proveedor) throws SQLException {
        int nup = 0;
        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("UPDATE FAC_PROVEEDOR SET CIU_CODIGO='" + proveedor.getCiuCodigo().getCiuCodigo() + "',PRO_NOMBRE='" + proveedor.getProNombre() + "',"
                    + " PRO_IDENTIFICAION= '" + proveedor.getProIdentificaion() + "', PRO_RAZON_SOCIAL='" + proveedor.getProRazonSocial() + "', PRO_FECHA_ACTUALIZACION= SYSDATE, "
                    + " PRO_ESTADO = " + proveedor.getProEstado() + ", PRO_DIRECCION='" + proveedor.getProDireccion() + "', PRO_TELEFONO='" + proveedor.getProTelefono() + "', PRO_CORREO= '" + proveedor.getProCorreo() + "' "
                    + " WHERE PRO_CODIGO = " + proveedor.getProCodigo() + " ");

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
            pstmt = conn.prepareStatement("DELETE FROM FAC_PROVEEDOR WHERE PRO_CODIGO = " + id + "");
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
    public List<FacProveedor> findAll() throws SQLException {
        List<FacProveedor> proveedores = new ArrayList<FacProveedor>();
        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM FAC_PROVEEDOR WHERE PRO_ESTADO=1 ");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FacProveedor proveedor = new FacProveedor();
                proveedor.setCiuCodigo(new AudCiudad());
                proveedor.setProCodigo(rs.getBigDecimal(1));
                proveedor.getCiuCodigo().setCiuCodigo(rs.getBigDecimal(2));
                proveedor.setProNombre(rs.getString(3));
                proveedor.setProIdentificaion(rs.getString(4));
                proveedor.setProFechaCreacion(rs.getDate(5));
                proveedor.setProRazonSocial(rs.getString(6));
                proveedor.setProFechaActualizacion(rs.getDate(7));
                proveedor.setProEstado(rs.getInt(8));
                proveedor.setProDireccion(rs.getString(9));
                proveedor.setProTelefono(rs.getString(10));
                proveedor.setProCorreo(rs.getString(11));
                proveedores.add(proveedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }
        return proveedores;
    }

    @Override
    public FacProveedor find(int id) throws SQLException {

        FacProveedor proveedor = null;

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM FAC_PROVEEDOR WHERE PRO_CODIGO = ? AND PRO_ESTADO=1");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                proveedor = new FacProveedor();
                proveedor.setCiuCodigo(new AudCiudad());
                proveedor.setProCodigo(rs.getBigDecimal(1));
                proveedor.getCiuCodigo().setCiuCodigo(rs.getBigDecimal(2));
                proveedor.setProNombre(rs.getString(3));
                proveedor.setProIdentificaion(rs.getString(4));
                proveedor.setProFechaCreacion(rs.getDate(5));
                proveedor.setProRazonSocial(rs.getString(6));
                proveedor.setProFechaActualizacion(rs.getDate(7));
                proveedor.setProEstado(rs.getInt(8));
                proveedor.setProDireccion(rs.getString(9));
                proveedor.setProTelefono(rs.getString(10));
                proveedor.setProCorreo(rs.getString(11));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            conn.close();
            pstmt.close();
        }

        return proveedor;
    }

    @Override
    public boolean existePorCampo(String cedRuc) throws SQLException {
        try {
            Integer rowCount = 0;
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT COUNT(*) AS CONTADOR FROM FAC_PROVEEDOR P WHERE P.PRO_IDENTIFICAION='" + cedRuc + "'");
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
