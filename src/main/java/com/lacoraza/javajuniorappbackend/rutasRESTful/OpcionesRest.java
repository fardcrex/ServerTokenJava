package com.lacoraza.javajuniorappbackend.rutasRESTful;

import com.lacoraza.javajuniorappbackend.contralador.Autentificacion_TOKEN;
import com.lacoraza.javajuniorappbackend.contralador.OpcionesControlador;
import com.lacoraza.javajuniorappbackend.modelos.Opcion;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


@Path("/opciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OpcionesRest {
    OpcionesControlador opcionesControlador =new OpcionesControlador();
    Autentificacion_TOKEN controlador = new Autentificacion_TOKEN();

    @GET
    public Response getOpciones(@Context HttpHeaders httpHeaders){
        ArrayList<String> permisos = new ArrayList<String>();
        permisos.add("All_excepto");

        if(TodoBien(httpHeaders,permisos)){
            List<Opcion> list =  opcionesControlador.getOpciones();
            return Response.status(Response.Status.ACCEPTED).entity(list).build();
        }else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

    }











    private boolean TodoBien (HttpHeaders httpHeaders, ArrayList<String> permisos){
        try {
            if(controlador.verificarPermisos(httpHeaders,permisos))
                return true;
            if(controlador.verificarTOKEN(httpHeaders)){
                return true;
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }
}
