package com.lacoraza.javajuniorappbackend.contralador;

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

    public Usuario getUsuario(int i) {
        Usuario usuario = userDao.obtenerPorIDGet(i);
        return  usuario;

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
