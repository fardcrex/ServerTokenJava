package com.lacoraza.javajuniorappbackend.bdconnect.daoPSQL;

import com.lacoraza.javajuniorappbackend.config.VariablesDeEntorno;
import com.lacoraza.javajuniorappbackend.modelos.Usuario;

import java.util.Calendar;
import java.util.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class ConexionPSQL {
    Map<String,String> DatosDeConexion = VariablesDeEntorno.getInstancia().getConfigBD();
//    String url = "jdbc:postgresql://" +
//            DatosDeConexion.get("servidor")+
//            ":" + DatosDeConexion.get("puerto") +
//            "/" + DatosDeConexion.get("basededatos");
//    String user = DatosDeConexion.get("usuariobd");
//    String clave = DatosDeConexion.get("passwordbd");

    String url = "jdbc:postgresql://" +
            "zadness.com"+
            ":" + "5432" +
            "/" + "javajuniorbd";
    String user = "jair";
    String clave = "conislla321";


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

        UsuarioPSQL userDao = new UsuarioPSQL();
        Usuario usuario = userDao.obtenerPorIDGet(1);


        if(usuario==null){
            System.out.println("404");
        }else {
            System.out.println(usuario.toString());
        }


        usuario.setCorreo("zzz");
        try {
            usuario.setPassword("zzz");
        }catch (Exception e){
            System.out.println(e);
        }

        usuario=userDao.insertarPOST(usuario);
        if(usuario==null){
            System.out.println("401");
        }else {
            System.out.println(usuario.toString());
        }


        ArrayList<Usuario> LIST = userDao.obtenerGET();
        if(LIST==null){
            System.out.println("402");
        }else {
            System.out.println(LIST.toString());
        }


        usuario=userDao.eliminarDELETE(9);
        if(usuario==null){
            System.out.println("403");
        }else {
            System.out.println(usuario.toString());
        }

        usuario=userDao.validarUsuario("carolos","carlos");
        if(usuario==null){
            System.out.println("404");
        }else {
            System.out.println(usuario.toString());
        }




//        ConexionPSQL conecta = new ConexionPSQL();
//        Connection conn = conecta.getConnection();
//
//        try {
//            Statement st = conn.createStatement();
//            ResultSet rs = st.executeQuery("SELECT * FROM usuarios");
//
//            while (rs.next()) {
//                System.out.print("id : " + rs.getString(1));
//                System.out.print("  País: " + rs.getString(2));
//            }
//        } catch (SQLException e) {
//            System.out.println("No se pudo obtener los registros de la base de datos. Mensaje: "+ e.getMessage());
//        }
    }
}
