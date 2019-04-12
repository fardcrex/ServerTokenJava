package com.lacoraza.javajuniorappbackend.rutasRESTful;

import com.lacoraza.javajuniorappbackend.LogicaDeNegocios.Autentificacion_TOKEN;
import com.lacoraza.javajuniorappbackend.modelos.Usuario;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.HashMap;
import java.util.Map;

@Path("autentificacion")
public class Autentificacion_Server {

    Autentificacion_TOKEN autentificacion_token = new Autentificacion_TOKEN();


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerJWT(Map<String,String> credenciales,@Context UriInfo uriInfo) {
        try {
            Map<String, Object> respuesta = autentificacion_token.verificarCredenciales(credenciales);

            switch ((int)respuesta.get("status")){
                case 1: return Response.status(Response.Status.PARTIAL_CONTENT).entity("Falta los campos requeridos").build();

                case 2: return Response.status(Response.Status.PARTIAL_CONTENT).entity("Hay campos vacíos").build();

                case 3: return Response.status(Response.Status.OK).entity("404 : usuario inexistente").build();

                case 4: Map<String, Object>  tokenjson = new HashMap<>();
                        Map<String, Object>  usuario = new HashMap<>();

                        usuario.put("id",     ((Usuario) respuesta.get("Usuario")).getId());
                        usuario.put("Correo", ((Usuario)respuesta.get("Usuario")).getCorreo());
                        usuario.put("Nombre", ((Usuario)respuesta.get("Usuario")).getNombre());

                        tokenjson.put("Usuario",usuario);
                        tokenjson.put("Token"  ,autentificacion_token.crearTOKEN(respuesta));
                        return Response.status(Response.Status.ACCEPTED).entity(tokenjson).build();

                default:return Response.status(Response.Status.UNAUTHORIZED).entity(credenciales).build();
            }
        }catch (Exception e){
            return Response.status(Response.Status.UNAUTHORIZED).entity(credenciales).build();
        }
    }

}
