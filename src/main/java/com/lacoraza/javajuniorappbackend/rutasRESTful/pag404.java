package com.lacoraza.javajuniorappbackend.rutasRESTful;



import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Path("/")
public class pag404 {

    @GET
    public Response Welcome(){
        List<HashMap<String,String>> list = new ArrayList<>();

        HashMap<String,String> response = new HashMap<>() ;
        response.put("Welcome To token server:","dynamo");
        list.add(response);

        HashMap<String,String> response2 = new HashMap<>() ;
        response2.put("URL ","/getToken?correo=****&pass=****");
        response2.put("Para obtener token via :","GET");
        list.add(response2);

        HashMap<String,String> response4 = new HashMap<>() ;
        response4.put("Ejemplo para ver ","opciones");
        response4.put("URL opciones ","/v1/opciones?token={token key}");
        list.add(response4);



        return Response.status(Response.Status.OK).entity(list).build();

    }

    @GET
    @Path("/{any}")
    public Response getpag404(@PathParam("any") String any ){

        return  Response.status(Response.Status.OK).entity("404 Not found:"+any).build();

    }
    @GET
    @Path("/{any}/{any}")
    public Response getpag4041(@PathParam("any") String any ){

        return  Response.status(Response.Status.OK).entity("404 Not found:"+any).build();

    }
    @GET
    @Path("/{any}/{any}/{any}")
    public Response getpag4042(@PathParam("any") String any ){

        return  Response.status(Response.Status.OK).entity("404 Not found:"+any).build();

    }

    @POST
    @Path("/{any}")
    public Response postpag404(@PathParam("any") String any){
        return  Response.status(Response.Status.OK).entity("404 Not found:"+any).build();

    }
    @POST
    @Path("/{any}/{any}")
    public Response postpag4042(@PathParam("any") String any ){
        return  Response.status(Response.Status.OK).entity("404 Not found:"+any).build();

    }


}
