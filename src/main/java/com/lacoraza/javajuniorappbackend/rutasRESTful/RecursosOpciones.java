package com.lacoraza.javajuniorappbackend.rutasRESTful;

import com.lacoraza.javajuniorappbackend.LogicaDeNegocios.Autentificacion_TOKEN;
import com.lacoraza.javajuniorappbackend.LogicaDeNegocios.OpcionesControlador;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;


@Path("/v1/opciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecursosOpciones {
    OpcionesControlador opcionesControlador =new OpcionesControlador();
    Autentificacion_TOKEN controlador = new Autentificacion_TOKEN();

    @GET
    public Response getOpciones(@Context HttpHeaders httpHeaders){
        ArrayList<String> permisos = new ArrayList<String>();
        permisos.add("All_excepto");


        int id_user;

        switch (TodoBien(httpHeaders,permisos)){
            case 1 :return Response.status(200).entity("Autorizacion por Token inválido").build();

            case 2 :return Response.status(Response.Status.FORBIDDEN).entity("No tiene los permisos requeridos").build();

            case 3 :id_user =controlador.obtenerIdUserToken(httpHeaders);

                Object opciones =  opcionesControlador.getOpcionesbyId(id_user);
                return Response.status(Response.Status.ACCEPTED).entity(opciones).build();

            default:return Response.status(Response.Status.BAD_GATEWAY).entity("Su solicitud no se procesar").build();
        }

    }











    private int TodoBien (HttpHeaders httpHeaders, ArrayList<String> permisos){
        boolean permiso = false;
        try {
            if(permiso && !controlador.verificarTOKEN(httpHeaders) )
                return 1;
            if(permiso && !controlador.verificarPermisos(httpHeaders,permisos))
                return 2;
            else
                return 3;
        }catch (Exception e){
            return 4;
        }
    }
}
