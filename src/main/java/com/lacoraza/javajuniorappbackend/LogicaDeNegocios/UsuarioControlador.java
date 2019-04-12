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
        return "Su id de usuario es incorrecto";


    }

    public Object postUsuario(Usuario newUsuario, HttpHeaders httpHeaders ) {

        String subject = autentificacion_token.obtenerSubject(httpHeaders);
        if (newUsuario.getRole()==null || subject.equalsIgnoreCase("user"))        {
            newUsuario.setRole(new Role());
            newUsuario.getRole().setId(2); //default
        }


        if(!subject.equalsIgnoreCase("admin") && newUsuario.getRole().getId() ==1){
            newUsuario.getRole().setId(2);
        }


        Usuario usuario = userDao.insertarPOST(newUsuario);

        if(usuario.getId()==0)
            return usuario.getNombre();

        return  usuario;

    }



    public Usuario putUsuario(Usuario newUsuario, int i, HttpHeaders httpHeaders) {

        String subject = autentificacion_token.obtenerSubject(httpHeaders);
        if (newUsuario.getRole()==null  )
        {
            newUsuario.setRole(new Role());
            newUsuario.getRole().setId(2);
        }

        if(!subject.equalsIgnoreCase("admin")){
            newUsuario.getRole().setId(2);
        }

        Usuario usuario = userDao.modificarPUT(newUsuario,i);
        return  usuario;

    }

    public Usuario deleteUsuario(int i) {
        Usuario usuario = userDao.eliminarDELETE(i);
        return  usuario;

    }



}
