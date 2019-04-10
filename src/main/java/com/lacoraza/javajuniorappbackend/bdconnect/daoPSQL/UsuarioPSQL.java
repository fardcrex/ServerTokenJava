package com.lacoraza.javajuniorappbackend.bdconnect.daoPSQL;

import com.lacoraza.javajuniorappbackend.bdconnect.i_dao.InterfaceUsuario;
import com.lacoraza.javajuniorappbackend.modelos.Role;
import com.lacoraza.javajuniorappbackend.modelos.Usuario;

import javax.validation.constraints.Null;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioPSQL  implements InterfaceUsuario {

    private final String  OBTENER_POR_ID = "SELECT usu.id,roles.id as role_id,roles.name  as role_name,usu.name,correo,imagen,usu.created_at,usu.update_at\n" +
            "FROM usuarios usu INNER JOIN roles ON usu.role_id = roles.id WHERE usu.id=?";
    private final String  OBTENER_ALL = "SELECT usu.id,roles.id as role_id,roles.name  as role_name,usu.name,correo,imagen,usu.created_at,usu.update_at\n" +
            "FROM usuarios usu INNER JOIN roles ON usu.role_id = roles.id";
    private final String  INSERTAR = "INSERT INTO usuarios (role_id,name,correo,password,imagen,created_at,update_at) VALUES (?,?,?,?,?,now(),now())";
    private final String  MODIFICAR = "UPDATE usuarios SET role_id=?,name=?, correo=?,password=?,imagen=?, update_at=now() WHERE id=?";
    private final String  ELIMINAR = "DELETE FROM usuarios WHERE id = ?";
    private final String  VALIDAR = "SELECT usu.id,roles.id as role_id,roles.name  as role_name ,correo  FROM usuarios usu INNER JOIN roles ON usu.role_id = roles.id WHERE correo = ? and password = ?";


    private Connection conexion;
    private PreparedStatement sentencia;
    private ResultSet resultados;

    @Override
    public Usuario insertarPOST(Usuario o) {

        int seInserto= 0;
        try {
            conexion = new ConexionPSQL().getConnection();
            sentencia = conexion.prepareStatement(INSERTAR);
            sentencia.setInt(1,o.getRole().getId());
            sentencia.setString(2,o.getNombre());
            sentencia.setString(3,o.getCorreo().toUpperCase());
            sentencia.setString(4,o.getPassword());
            sentencia.setString(5,o.getImagen());
            seInserto =sentencia.executeUpdate();


        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioPSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Usuario();
        }  finally {
            cerrarConexiones();
        }
        if (seInserto==1){
            try {o.setPassword("");}catch (Exception E){}
            return o;
        }else {
            return new Usuario();
        }

    }

    @Override
    public Usuario modificarPUT(Usuario o, Integer integer) {
        int seInserto= 0;
        try {
            conexion = new ConexionPSQL().getConnection();
            sentencia = conexion.prepareStatement(MODIFICAR);
            sentencia.setInt(1,o.getRole().getId());
            sentencia.setString(2,o.getNombre());
            sentencia.setString(3,o.getCorreo().toUpperCase());
            sentencia.setString(4,o.getPassword());
            sentencia.setString(5,o.getImagen());
            sentencia.setInt(6,integer);
            seInserto =sentencia.executeUpdate();


        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioPSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            cerrarConexiones();
        }
        if (seInserto==1){
            try {o.setPassword("");}catch (Exception E){}
            return o;
        }else {
            return null;
        }

    }

    @Override
    public Usuario eliminarDELETE(Integer integer) {
        Usuario usuario = obtenerPorIDGet(integer);
        if(usuario==null){
            return null;
        };
        try {
            conexion = new ConexionPSQL().getConnection();
            sentencia = conexion.prepareStatement(ELIMINAR);
            sentencia.setInt(1,integer);
            sentencia.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioPSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            cerrarConexiones();
        }
        return usuario;
    }

    @Override
    public Usuario obtenerPorIDGet(Integer integer) {
        Usuario usuario = null;
        Role role = new Role();
        try {
            conexion = new ConexionPSQL().getConnection();
            sentencia = conexion.prepareStatement(OBTENER_POR_ID);
            sentencia.setInt(1,integer);
            resultados = sentencia.executeQuery();

            if (resultados.next()){
                usuario = new Usuario();
                usuario.setId(resultados.getInt("id"));
                role.setId(resultados.getInt("role_id"));
                role.setName(resultados.getString("role_name"));
                usuario.setNombre(resultados.getString("name"));
                usuario.setCorreo(resultados.getString("correo"));
                usuario.setImagen(resultados.getString("imagen"));
                usuario.setCreated_at(resultados.getDate("created_at"));
                usuario.setUpdate_at(resultados.getDate("update_at"));
                try {usuario.setPassword("");}catch (Exception E){}
                usuario.setRole(role);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioPSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            cerrarConexiones();
        }

        return usuario;
    }

    @Override
    public ArrayList<Usuario> obtenerGET() {
        ArrayList<Usuario> usuarioList = new ArrayList<>();

        try {
            conexion = new ConexionPSQL().getConnection();
            sentencia = conexion.prepareStatement(OBTENER_ALL);
            resultados = sentencia.executeQuery();

            while (resultados.next()){
                Usuario usuario = new Usuario();
                Role role = new Role();
                usuario.setId(resultados.getInt("id"));
                role.setId(resultados.getInt("role_id"));
                role.setName(resultados.getString("role_name"));
                usuario.setNombre(resultados.getString("name"));
                usuario.setCorreo(resultados.getString("correo"));
                usuario.setImagen(resultados.getString("imagen"));
                usuario.setCreated_at(resultados.getDate("created_at"));
                usuario.setUpdate_at(resultados.getDate("update_at"));
                try {usuario.setPassword("");}catch (Exception E){}
                usuario.setRole(role);
                usuarioList.add(usuario);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioPSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            cerrarConexiones();
        }

        return usuarioList;
    }

    @Override
    public  Usuario validarUsuario(String correo, String uclave) {


        Usuario usuario = new Usuario();
        try {   usuario.setPassword(uclave);}catch (Exception E){}
        try {
            conexion = new ConexionPSQL().getConnection();
            sentencia = conexion.prepareStatement(VALIDAR);
            sentencia.setString(1,correo.toUpperCase());
            sentencia.setString(2,usuario.getPassword());
            resultados = sentencia.executeQuery();

            if (resultados.next()){
                Usuario credenciales = new Usuario();
                Role role = new Role();
                credenciales.setId(resultados.getInt("id"));
                role.setId(resultados.getInt("role_id"));
                role.setName(resultados.getString("role_name"));
                credenciales.setRole(role);
                return credenciales;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioPSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            cerrarConexiones();

        }
        return usuario;
    }
    private void cerrarConexiones(){
        try {
            if (resultados != null){
                resultados.close();
            }
            if (sentencia != null){
                sentencia.close();
            }
            if (conexion != null){
                conexion.close();
            }
        }catch(SQLException e)
        {
            System.out.println(e);
        }
    }
}
