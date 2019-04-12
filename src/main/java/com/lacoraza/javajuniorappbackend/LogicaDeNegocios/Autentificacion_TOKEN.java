package com.lacoraza.javajuniorappbackend.LogicaDeNegocios;

import com.lacoraza.javajuniorappbackend.bdconnect.daoPSQL.UsuarioPSQL;
import com.lacoraza.javajuniorappbackend.config.VariablesDeEntorno;
import com.lacoraza.javajuniorappbackend.modelos.Role;
import com.lacoraza.javajuniorappbackend.modelos.Usuario;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.ws.rs.core.HttpHeaders;
import java.util.*;

public class Autentificacion_TOKEN {

    SecretKey key = VariablesDeEntorno.getInstancia().getKeyJWT();

    public  Map<String,Object> verificarCredenciales(Map<String, String> credenciales) {
        Map<String,Object> respuesta = new HashMap<>();
        respuesta.put("status",false);

        if( credenciales.get("correo")==null || credenciales.get("password")== null  ){
            respuesta.put("status",1);
            return respuesta;
        }
        if( credenciales.get("correo").equalsIgnoreCase("") || credenciales.get("password").equalsIgnoreCase("")  ){
            respuesta.put("status",2);
            return respuesta;
        }


        String correo = (String) credenciales.get("correo");
        String password = (String) credenciales.get("password");

        Usuario usuario ;
        UsuarioPSQL usuarioPSQL = new UsuarioPSQL();
        usuario = usuarioPSQL.validarUsuario(correo,password);

        System.out.println(usuario.toString());
        if(usuario.getId()==0){
            respuesta.put("status",3);
            return respuesta;
        }

        respuesta.put("Usuario",usuario);
        respuesta.put("status",4);
        return respuesta;

    }

    public  String crearTOKEN(Map<String, Object> credenciales) {


        int user_id   = ((Usuario)credenciales.get("Usuario")).getId();
        String correo = ((Usuario)credenciales.get("Usuario")).getCorreo();
        Role role =     ((Usuario)credenciales.get("Usuario")).getRole();


        System.out.println(correo); System.out.println(correo); System.out.println(correo); System.out.println(correo);
        String jws = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(role.getName())
                .setExpiration(sumarRestarHorasFecha(new Date(),5))
                .claim("user_id", user_id )
                .claim("correo", correo)
                .claim("role_id", role.getId())
                .signWith(key)
                .compact();




        return jws;
    }

    public boolean verificarTOKEN(HttpHeaders httpHeaders){
        Map<String,Object> respuesta = new HashMap<>();
        try {
            String authorization = httpHeaders.getRequestHeader("Authorization").get(0);


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

        if(permisos.size() ==1 && permisos.get(0).equalsIgnoreCase("All_excepto"))
            return true;

        String authorization = httpHeaders.getRequestHeader("authorization").get(0);
        Jws<Claims> jwsRecibido =Jwts.parser().setSigningKey(key).parseClaimsJws(authorization);


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

        Object num =jwsRecibido.getBody().get("user_id");

        return jwsRecibido.getBody().getSubject();

    }
    public boolean sonIdIguales(HttpHeaders httpHeaders,int i){
        String authorization = httpHeaders.getRequestHeader("authorization").get(0);

        try {
            Jwts.parser().require("user_id",i ).setSigningKey(key).parseClaimsJws(authorization);

            return true;

        } catch(InvalidClaimException ice) {
            return false;
            // the 'myfield' field was missing or did not have a 'myRequiredValue' value
        }


    }
    public int obtenerIdUserToken(HttpHeaders httpHeaders){
        String authorization = httpHeaders.getRequestHeader("authorization").get(0);
        Jws<Claims> jwsRecibido;
        jwsRecibido=Jwts.parser().setSigningKey(key).parseClaimsJws(authorization);

        int num =(int) jwsRecibido.getBody().get("user_id");

        return num;

    }



    private   Date sumarRestarHorasFecha(Date fecha, int horas){

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(fecha); // Configuramos la fecha que se recibe

        calendar.add(Calendar.HOUR, horas);  // numero de horas a añadir, o restar en caso de horas<0


        return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas

    }



}
