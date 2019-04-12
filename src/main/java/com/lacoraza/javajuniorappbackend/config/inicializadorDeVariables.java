package com.lacoraza.javajuniorappbackend.config;

import com.lacoraza.javajuniorappbackend.bdconnect.daoPSQL.UsuarioPSQL;
import com.lacoraza.javajuniorappbackend.modelos.Role;
import com.lacoraza.javajuniorappbackend.modelos.Usuario;
import io.jsonwebtoken.security.Keys;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class inicializadorDeVariables implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String servidor = sce.getServletContext().getInitParameter("servidor");
        String puerto = sce.getServletContext().getInitParameter("puerto");
        String basededatos = sce.getServletContext().getInitParameter("basededatos");
        String usuariobd = sce.getServletContext().getInitParameter("usuario");
        String passwordbd = sce.getServletContext().getInitParameter("password");
        String claveJWT = sce.getServletContext().getInitParameter("claveJWT");
        byte[] miClaveBytes = claveJWT.getBytes();


        creatUsuarioRoot(sce);



        Map<String,String> propiedadesConexion = new HashMap<>();
        SecretKey key = Keys.hmacShaKeyFor(miClaveBytes);


        propiedadesConexion.put("servidor", servidor);
        propiedadesConexion.put("puerto", puerto);
        propiedadesConexion.put("basededatos", basededatos);
        propiedadesConexion.put("usuariobd", usuariobd);
        propiedadesConexion.put("passwordbd", passwordbd);
        System.out.println(propiedadesConexion.toString());

        VariablesDeEntorno.getInstancia().setConfigBD(propiedadesConexion);
        VariablesDeEntorno.getInstancia().setKeyJWT(key);

    }

    private void creatUsuarioRoot(ServletContextEvent sce) {

        try {
            UsuarioPSQL usuarioPSQL = new UsuarioPSQL();
            String NameRoot = sce.getServletContext().getInitParameter("NameRoot");
            String PassRoot = sce.getServletContext().getInitParameter("PassRoot");
            Usuario usuario = new Usuario();
            usuario.setCorreo(NameRoot);
            usuario.setPassword(PassRoot);
            Role role = new Role();
            role.setId(1);
            usuario.setRole(role);
            usuario.setNombre("administrador");
            usuario.setImagen("imagenDefault.jpg");


            usuarioPSQL.insertarPOST(usuario);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }catch (Exception e){
            System.out.println("Error Usuario Root"+e);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}

