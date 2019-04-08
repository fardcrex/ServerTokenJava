package com.lacoraza.javajuniorappbackend.config;

import io.jsonwebtoken.security.Keys;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.crypto.SecretKey;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class iniciarVariables implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String servidor = sce.getServletContext().getInitParameter("servidor");
        String puerto = sce.getServletContext().getInitParameter("puerto");
        String basededatos = sce.getServletContext().getInitParameter("basededatos");
        String usuariobd = sce.getServletContext().getInitParameter("usuario");
        String passwordbd = sce.getServletContext().getInitParameter("password");
        String claveJWT = sce.getServletContext().getInitParameter("claveJWT");
        byte[] miClaveBytes = claveJWT.getBytes();

        Map<String,String> propiedadesConexion = new HashMap<>();
        SecretKey key = Keys.hmacShaKeyFor(miClaveBytes);


        propiedadesConexion.put("servidor", servidor);
        propiedadesConexion.put("puerto", puerto);
        propiedadesConexion.put("basededatos", basededatos);
        propiedadesConexion.put("usuariobd", usuariobd);
        propiedadesConexion.put("passwordbd", passwordbd);
        System.out.println(propiedadesConexion.toString());

        Utilidades.getInstancia().setConfiguraBaseDeDatos(propiedadesConexion);
        Utilidades.getInstancia().setKeyJWT(key);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}

