package com.challenge.restful.controller;

import com.challenge.restful.model.User;
import com.challenge.restful.util.PersistenceManager;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;

/**
 * Restful resource for all user operations
 */
@Path("/user")
public class UserController {

    private PersistenceManager persistenceManager = new PersistenceManager();

    @GET
    @Path("/get/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("username") String username) {

        EntityManager em = persistenceManager.getEntityManager();
        em.getTransaction().begin();

        String queryString = "Select u\n" +
                "from User u\n" +
                "where u.username = :username";

        Query query = em.createQuery(queryString);
        query.setParameter("username", username.trim());
        User user = (query.getResultList().isEmpty())? new User():(User) query.getResultList().get(0);

        em.getTransaction().commit();
        persistenceManager.close();
        return Response
                .ok()
                .entity(user)
                .build();
    }

    /**
     * Returns an ArrayList of all the URLs of the GIFs returned by the giphy search api
     *
     * @param  User  a User object should be sent inside the http request body
     * @return      400 for invalid requests, 500 for server issues, 201 for successful db insert
     * @see         EntityManager
     */
    @PUT
    @Path("/signup")
    //@Consumes("application/x-www-form-urlencoded")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user){

        if (null == user){
            return Response
                    .status(400)
                    .entity("Invalid request")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

        System.out.println(user.toString());
        EntityManager em = persistenceManager.getEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(user);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            em.close();
            if (e.getMessage().contains("ConstraintViolationException")){
                return Response
                        .status(400)
                        .entity("SQL Constraint Violation Exception, either user exists or entry missing mandatory field")
                        .type(MediaType.TEXT_PLAIN)
                        .build();
            }
            return Response
                    .serverError()
                    .build();
        }
        em.close();
        System.out.println("User added...");
        return Response
                .status(Response.Status.CREATED)
                .entity(user.getIduser())
                .build();

    }

}
