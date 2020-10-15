package ru.npetrov;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import ru.npetrov.methods.HashCacheMethods;
import ru.npetrov.rest.InitCacheRequest;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Gate class that holds HTTP methods
 */
@Path("/rest")
public class RestGate {

    @Inject
    HashCacheMethods hashCacheMethods;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }

    @GET
    @Path("/getResult/{data}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getResult(@PathParam("data") String data) {
        String result;
        try {
            result = hashCacheMethods.getResult(data);
        } catch (Exception e) {
            result = "error";
        }
        return Response.ok(result).build();
    }


    @GET
    @Path("/getCacheInfo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCacheInfo() {
        return Response.ok(hashCacheMethods.getCacheInfo()).build();
    }

    @POST
    @Path("/initCache")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response initCache(InitCacheRequest request) {
        hashCacheMethods.initCache(request);
        return Response.ok("OK").build();
    }

}