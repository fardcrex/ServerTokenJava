package com.lacoraza.javajuniorappbackend.LogicaDeNegocios;

import com.lacoraza.javajuniorappbackend.bdconnect.daoPSQL.UsuarioPSQL;
import com.lacoraza.javajuniorappbackend.modelos.Role;
import com.lacoraza.javajuniorappbackend.modelos.Usuario;

import javax.ws.rs.core.HttpHeaders;
import java.util.ArrayList;
import java.util.List;

public class UsuarioControlador {

    UsuarioPSQL userDao = new UsuarioPSQL();
    Autentificacion_TOKEN autentificacion_token = new Autentificacion_TOKEN();


    public List<Usuario> getUsuarios() {
            ArrayList<Usuario> LIST = userDao.obtenerGET();
            return  LIST;

    }

    public Object getUsuario(int id, HttpHeaders httpHeaders) {


        String subject = autentificacion_token.obtenerSubject(httpHeaders);
        if(subject.equalsIgnoreCase("admin")){
            Usuario usuario = userDao.obtenerPorIDGet(id);
            return  usuario;
        }


        if ( autentificacion_token.sonIdIguales(httpHeaders,id)){
            Usuario usuario = userDao.obtenerPorIDGet(id);
            return  usuario;
        }
        return new String("Su id de usuario es incorrecto");


    }

    public Usuario postUsuario(Usuario newUsuario) {
        Usuario usuario = new Usuario();
        try {
             usuario = userDao.insertarPOST(newUsuario);
            return  usuario;
        }catch (Exception e){
            return  usuario;
        }
    }

    public Usuario putUsuario(Usuario newUsuario, int i, HttpHeaders httpHeaders) {

        String subject = autentificacion_token.obtenerSubject(httpHeaders);
        Role role = new Role();

        if(!subject.equalsIgnoreCase("admin")){
            role.setId(2);
        }


        newUsuario.setRole(role);
        Usuario usuario = userDao.modificarPUT(newUsuario,i);
        return  usuario;

    }

    public Usuario deleteUsuario(int i) {
        Usuario usuario = userDao.eliminarDELETE(i);
        return  usuario;

    }



}
