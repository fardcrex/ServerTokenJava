package com.lacoraza.javajuniorappbackend.rutas;

import com.google.gson.Gson;
import com.lacoraza.javajuniorappbackend.config.VariablesDeEntorno;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("autentificacion")
public class Autentificacion_Server {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getArticulos(){
        Gson gson = new Gson();



        SecretKey key = VariablesDeEntorno.getInstancia().getKeyJWT();


        System.out.println(key.toString());
        String jws = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam(JwsHeader.KEY_ID, 100)
                .setSubject("jose")
                .claim("data", "dsdfs")
                .signWith(key)
                .compact();

        Map<String,String> tokenjson = new HashMap<>();
        tokenjson.put("token",jws);
        return  gson.toJson(tokenjson);
    }
}
