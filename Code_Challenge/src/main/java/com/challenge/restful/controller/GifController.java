package com.challenge.restful.controller;

import com.challenge.restful.model.Gif;
import com.challenge.restful.util.PersistenceManager;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * This resource should include all CRUD operations that will performed on the gif table
 */
@Path("/gif")
public class GifController {

    private PersistenceManager persistenceManager = new PersistenceManager();

    @GET
    @Path("/getByUserId/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Gif> getAllGifs(@PathParam("id") int id){

        EntityManager em = persistenceManager.getEntityManager();
        em.getTransaction().begin();

        String queryString = "Select g\n" +
                " from User u\n" +
                "join u.gifs g\n" +
                "where u.iduser = :id";

        Query query = em.createQuery(queryString);
        query.setParameter("id", id);
        List<Gif> gifs = (List<Gif>) query.getResultList();

        em.getTransaction().commit();
        em.close();

        return gifs;

    }

    @PUT
    @Path("/addGifToProfile")
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(Gif gif){

        if (null == gif){
            return Response
                    .status(400)
                    .entity("Invalid request")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

        System.out.println(gif.toString());
        EntityManager em = persistenceManager.getEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(gif);
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
                .entity(gif.getIdGif())
                .build();



    }

}
