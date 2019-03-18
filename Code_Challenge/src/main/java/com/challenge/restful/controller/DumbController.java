package com.challenge.restful.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created for testing/health-checks purposes only, it can be used for keep alive
 */
@Path("/dumb")
public class DumbController {
    @GET
    @Path("/hello")
    @Produces("text/plain")
    public String sayHi(){
        return "hi";
    }
}
