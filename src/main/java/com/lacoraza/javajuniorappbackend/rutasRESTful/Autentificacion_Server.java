package com.lacoraza.javajuniorappbackend.rutasRESTful;

import com.lacoraza.javajuniorappbackend.contralador.Autentificacion_TOKEN;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Map;

@Path("autentificacion")
public class Autentificacion_Server {

    Autentificacion_TOKEN autentificacion_token = new Autentificacion_TOKEN();


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerJWT(Map<String,String> credenciales,@Context UriInfo uriInfo) {

        Map<String, Object> respuesta = autentificacion_token.verificarCredenciales(credenciales);


        if (((boolean)respuesta.get("status")) == true) {

            Map<String, String>  tokenjson = autentificacion_token.crearTOKEN(respuesta);
            return Response.status(Response.Status.ACCEPTED).entity(tokenjson).build();

        } else {

            return Response.status(Response.Status.UNAUTHORIZED).entity(credenciales).build();
        }
    }









}
