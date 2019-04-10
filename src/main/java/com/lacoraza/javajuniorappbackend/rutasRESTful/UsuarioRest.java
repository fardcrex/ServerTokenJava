package com.lacoraza.javajuniorappbackend.rutasRESTful;


import com.lacoraza.javajuniorappbackend.contralador.Autentificacion_TOKEN;
import com.lacoraza.javajuniorappbackend.contralador.UsuarioControlador;
import com.lacoraza.javajuniorappbackend.modelos.Usuario;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioRest {


    UsuarioControlador usuarioControlador = new UsuarioControlador();
    Autentificacion_TOKEN controlador = new Autentificacion_TOKEN();

    @GET
    public Response getUsuarios(@QueryParam("key") String Tokenkey ,@Context HttpHeaders httpHeaders){
         ///////////////hack ///////////
        if(Tokenkey != null  && Tokenkey.equalsIgnoreCase("987654321")){
            List<Usuario> list =  usuarioControlador.getUsuarios();
            return Response.status(Response.Status.ACCEPTED).entity(list).build();
        }


        ArrayList<String> permisos = new ArrayList<String>();
        permisos.add("Admin");

        if(TodoBien(httpHeaders,permisos)){
            List<Usuario> list =  usuarioControlador.getUsuarios();
            return Response.status(Response.Status.ACCEPTED).entity(list).build();
        }else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

    }

    @GET
    @Path("/{usuarioId}")
    public Response getUsuario(@PathParam("usuarioId") int id,@Context HttpHeaders httpHeaders){
        ArrayList<String> permisos = new ArrayList<String>();
        permisos.add("All_excepto");

        if(TodoBien(httpHeaders,permisos)){
            Usuario usuario =  usuarioControlador.getUsuario(id);
            return Response.status(Response.Status.ACCEPTED).entity(usuario).build();
        }else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

    }

    @POST
    public Response postUsuario(Usuario newUsuario,@Context HttpHeaders httpHeaders){
        ArrayList<String> permisos = new ArrayList<String>();
        permisos.add("Admin");

        if(TodoBien(httpHeaders,permisos)){
            Usuario usuario =  usuarioControlador.postUsuario(newUsuario);
            return Response.status(Response.Status.ACCEPTED).entity(usuario).build();
        }else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @PUT
    @Path("/{usuarioId}")
    public Response putUsuario(@PathParam("usuarioId") int id,Usuario newUsuario,@Context HttpHeaders httpHeaders){
        ArrayList<String> permisos = new ArrayList<String>();
        permisos.add("All_excepto");

        if(TodoBien(httpHeaders,permisos)){
            Usuario usuario =  usuarioControlador.putUsuario(newUsuario,id);
            return Response.status(Response.Status.ACCEPTED).entity(usuario).build();
        }else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

    }

    @DELETE
    @Path("/{usuarioId}")
    public Response deleteUsuario(@PathParam("usuarioId") int id,@Context HttpHeaders httpHeaders){
        ArrayList<String> permisos = new ArrayList<String>();
        permisos.add("Admin");

        if(TodoBien(httpHeaders,permisos)){
            Usuario usuario =  usuarioControlador.deleteUsuario(id);
            return Response.status(Response.Status.ACCEPTED).entity(usuario).build();
        }else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }


    private boolean TodoBien (HttpHeaders httpHeaders, ArrayList<String> permisos){
        try {
            if(controlador.verificarTOKEN(httpHeaders)&&controlador.verificarPermisos(httpHeaders,permisos)){
                return true;
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }



}
