package com.lacoraza.javajuniorappbackend.contralador;

import com.lacoraza.javajuniorappbackend.bdconnect.daoPSQL.UsuarioPSQL;
import com.lacoraza.javajuniorappbackend.config.VariablesDeEntorno;
import com.lacoraza.javajuniorappbackend.modelos.Role;
import com.lacoraza.javajuniorappbackend.modelos.Usuario;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.*;

public class Autentificacion_TOKEN {

    SecretKey key = VariablesDeEntorno.getInstancia().getKeyJWT();

    public  Map<String,Object> verificarCredenciales(Map<String, String> credenciales) {
        Map<String,Object> respuesta = new HashMap<>();
        respuesta.put("status",false);

        if( credenciales.get("correo")==null || credenciales.get("password")== null  ){

            return respuesta;
        }


        String correo = (String) credenciales.get("correo");
        String password = (String) credenciales.get("password");

        Usuario usuario ;
        UsuarioPSQL usuarioPSQL = new UsuarioPSQL();
        usuario = usuarioPSQL.validarUsuario(correo,password);
        System.out.println(usuario.toString());
        if(usuario.getId()==0){

            return respuesta;
        }


        respuesta.put("status",true);
        respuesta.put("usuario",usuario);
        return respuesta;

    }

    public  Map<String, String> crearTOKEN(Map<String, Object> credenciales) {


        int user_id   = ((Usuario)credenciales.get("usuario")).getId();
        String correo = ((Usuario)credenciales.get("usuario")).getCorreo();
        Role role =     ((Usuario)credenciales.get("usuario")).getRole();


        String jws = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(role.getName())
                .setExpiration(sumarRestarHorasFecha(new Date(),2))
                .claim("user_id ", user_id )
                .claim("role_id", role.getId())
                .claim("email",correo)
                .signWith(key)
                .compact();

        Map<String,String> respuesta = new HashMap<>();
        respuesta.put("token",jws);

        return respuesta;
    }

    public boolean verificarTOKEN(HttpHeaders httpHeaders){
        Map<String,Object> respuesta = new HashMap<>();
        try {
            String authorization = httpHeaders.getRequestHeader("authorization").get(0);


            Jws<Claims> jwsRecibido;
            jwsRecibido = Jwts.parser().setSigningKey(key).parseClaimsJws(authorization);

            //OK, we can trust this JWT

            return true;

        } catch (JwtException e) {
            System.out.println("error"+e);
            return  false;

            //don't trust the JWT!
        }catch (Exception e){
            return  false;
        }


    }

    public boolean verificarPermisos(HttpHeaders httpHeaders, ArrayList<String> permisos) {

        if(permisos.size() ==1 || permisos.get(0).equalsIgnoreCase("All_excepto"))
            return true;

        String authorization = httpHeaders.getRequestHeader("authorization").get(0);
        Jws<Claims> jwsRecibido;
        jwsRecibido=Jwts.parser().setSigningKey(key).parseClaimsJws(authorization);



        if(permisos.get(0).equalsIgnoreCase("All_excepto")){
            for (String permiso:permisos ) {
                if(permiso.equalsIgnoreCase( jwsRecibido.getBody().getSubject()))
                    return  false;
            }
            return  true;
        }


        for (String permiso:permisos ) {
            if(permiso.equalsIgnoreCase( jwsRecibido.getBody().getSubject()))
                return  true;
        }
        return  false;
    }


    public String obtenerSubject(HttpHeaders httpHeaders){
        String authorization = httpHeaders.getRequestHeader("authorization").get(0);
        Jws<Claims> jwsRecibido;
        jwsRecibido=Jwts.parser().setSigningKey(key).parseClaimsJws(authorization);
        return jwsRecibido.getBody().getSubject();
    }


    private   Date sumarRestarHorasFecha(Date fecha, int horas){

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(fecha); // Configuramos la fecha que se recibe

        calendar.add(Calendar.HOUR, horas);  // numero de horas a añadir, o restar en caso de horas<0


        return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas

    }



}
