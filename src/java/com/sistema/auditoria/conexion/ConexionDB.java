package com.sistema.auditoria.conexion;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConexionDB {

    private Connection c;

    public Connection getConexion() {
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/sis-auditoria");
            c = ds.getConnection();
            System.out.println("CONECTADO!!!!");
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        return c;
    }

//    public static void main(String[] args) throws ClassNotFoundException {
//        new ConexionDB().getConexion();
//    }
}
