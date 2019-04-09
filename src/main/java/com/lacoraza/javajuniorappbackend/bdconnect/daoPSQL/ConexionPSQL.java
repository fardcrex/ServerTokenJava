package com.lacoraza.javajuniorappbackend.bdconnect.daoPSQL;

import com.lacoraza.javajuniorappbackend.config.VariablesDeEntorno;

import java.sql.*;
import java.util.Map;

public class ConexionPSQL {
    Map<String,String> DatosDeConexion = VariablesDeEntorno.getInstancia().getConfigBD();
    String url = "jdbc:postgresql://" +
            DatosDeConexion.get("servidor")+
            ":" + DatosDeConexion.get("puerto") +
            "/" + DatosDeConexion.get("basededatos");
    String user = DatosDeConexion.get("usuariobd");
    String clave = DatosDeConexion.get("passwordbd");

    public Connection getConnection() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, clave);
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("No se pudo generar la conexión. Mensaje: " + e.getMessage());
        }
        return conn;
    }



    public static void main(String[] args) throws ClassNotFoundException{
        ConexionPSQL conecta = new ConexionPSQL();
        Connection conn = conecta.getConnection();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM usuarios");

            while (rs.next()) {
                System.out.print("id: " + rs.getString(1));
                System.out.print(" name: " + rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println("No se pudo obtener los registros de la base de datos. Mensaje: "+ e.getMessage());
        }
    }
}
