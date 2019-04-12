package com.lacoraza.javajuniorappbackend.rutasRESTful;


import com.lacoraza.javajuniorappbackend.config.MensajeResponse;
import com.lacoraza.javajuniorappbackend.LogicaDeNegocios.Autentificacion_TOKEN;
import com.lacoraza.javajuniorappbackend.LogicaDeNegocios.UsuarioControlador;
import com.lacoraza.javajuniorappbackend.modelos.Usuario;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/v1/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecursosUsuario {


    private UsuarioControlador usuarioControlador = new UsuarioControlador();
    private Autentificacion_TOKEN controlador = new Autentificacion_TOKEN();
    private MensajeResponse mensajeResponse = new MensajeResponse();

    @GET
    public Response getUsuarios(@QueryParam("key") String Tokenkey ,@Context HttpHeaders httpHeaders){
        ///////////////hack ///////////
//        if(Tokenkey != null  && Tokenkey.equalsIgnoreCase("987654321")){
//            List<Usuario> list =  usuarioControlador.getUsuarios();
//            return Response.status(Response.Status.ACCEPTED).entity(list).build();
//        }

        ArrayList<String> permisos = new ArrayList<String>();
        permisos.add("Admin");

        switch (TodoBien(httpHeaders,permisos)){
            case 1 :return Response.status(Response.Status.UNAUTHORIZED).entity("Autorizacion por Token inválido").build();

            case 2 :return Response.status(Response.Status.FORBIDDEN).entity("No tiene los permisos requeridos").build();

            case 3 :List<Usuario> list =  usuarioControlador.getUsuarios();
                    return Response.status(Response.Status.ACCEPTED).entity(list).build();

            default:return Response.status(Response.Status.BAD_GATEWAY).entity("Su solicitud no se procesar").build();
        }
    }

    @GET
    @Path("/{usuarioId}")
    public Response getUsuario(@PathParam("usuarioId") int id,@Context HttpHeaders httpHeaders){
        ArrayList<String> permisos = new ArrayList<String>();
        permisos.add("All_excepto");

        switch (TodoBien(httpHeaders,permisos)){
            case 1 :return Response.status(200).entity("Autorizacion por Token inválido").build();

            case 2 :return Response.status(200).entity("No tiene los permisos requeridos").build();

            case 3 :Object usuario =  usuarioControlador.getUsuario(id,httpHeaders);
                    return Response.status(Response.Status.ACCEPTED).entity(usuario).build();

            default:return Response.status(200).entity("Su solicitud no se procesar").build();
        }
    }

    @POST
    public Response postUsuario(Usuario newUsuario,@Context HttpHeaders httpHeaders){
        ArrayList<String> permisos = new ArrayList<String>();
        permisos.add("All_Excepto");
        //permisos.add("user");

        switch (TodoBien(httpHeaders,permisos)){
            case 1 :return Response.status(Response.Status.UNAUTHORIZED).entity("Autorizacion por Token inválido").build();

            case 2 :return Response.status(Response.Status.FORBIDDEN).entity("No tiene los permisos requeridos").build();

            case 3 :Object usuario =  usuarioControlador.postUsuario(newUsuario,httpHeaders);
                    return Response.status(Response.Status.ACCEPTED).entity(usuario).build();

            default:return Response.status(Response.Status.BAD_GATEWAY).entity("Su solicitud no se procesar").build();
        }
    }

    @PUT
    @Path("/{usuarioId}")
    public Response putUsuario(@PathParam("usuarioId") int id,Usuario newUsuario,@Context HttpHeaders httpHeaders){
        ArrayList<String> permisos = new ArrayList<String>();
        permisos.add("All_excepto");

        switch (TodoBien(httpHeaders,permisos)){
            case 1 :return Response.status(Response.Status.UNAUTHORIZED).entity("Autorizacion por Token inválido").build();

            case 2 :return Response.status(Response.Status.FORBIDDEN).entity("No tiene los permisos requeridos").build();

            case 3 :Object usuario =  usuarioControlador.putUsuario(newUsuario,id,httpHeaders);
                    return Response.status(Response.Status.ACCEPTED).entity(usuario).build();

            default:return Response.status(Response.Status.BAD_GATEWAY).entity("Su solicitud no se procesar").build();
        }

    }

    @DELETE
    @Path("/{usuarioId}")
    public Response deleteUsuario(@PathParam("usuarioId") int id,@Context HttpHeaders httpHeaders){
        ArrayList<String> permisos = new ArrayList<String>();
        permisos.add("Admin");

        switch (TodoBien(httpHeaders,permisos)){
            case 1 :return Response.status(200).entity("Autorizacion por Token inválido").build();

            case 2 :return Response.status(Response.Status.FORBIDDEN).entity("No tiene los permisos requeridos").build();

            case 3 :Object usuario =  usuarioControlador.deleteUsuario(id);
                    return Response.status(Response.Status.ACCEPTED).entity(usuario).build();

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
