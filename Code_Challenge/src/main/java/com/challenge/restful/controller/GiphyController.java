package com.challenge.restful.controller;

import com.challenge.restful.model.Gif;
import com.challenge.restful.util.PersistenceManager;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * This controller is used to call any services/APIs on giphy side
 */
@Path("/giphy")
public class GiphyController {

    private PersistenceManager persistenceManager = new PersistenceManager();

    private static final String APIKEY = "UhOyl1q7w76M7dfdv4KhmRC7i3otXVsX";
    private static final String HOST = "http://api.giphy.com";
    private static final String PATH = "/v1/gifs/search";

    @GET
    @Path("/search/{keyword}")
    @Produces(MediaType.APPLICATION_JSON)
    public String search(@PathParam("keyword") String keyword){


        String targetString = HOST+PATH+"?api_key="+APIKEY+"&q="+keyword+"&rating=g"+"&limit=5";
        Client client = ClientBuilder.newClient();
        Response response = client.target(targetString)
                .request()
                .get();
        client.close();
        String result = response.readEntity(String.class);
        response.close();
        return result;
    }


}
