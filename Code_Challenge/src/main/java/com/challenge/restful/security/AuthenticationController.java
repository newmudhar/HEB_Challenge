package com.challenge.restful.security;

import com.challenge.restful.model.Token;
import com.challenge.restful.model.User;
import com.challenge.restful.util.PersistenceManager;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Base64;

/**
 * Created by newmudhar on 11/4/2018.
 */
@Path("/security")
public class AuthenticationController {

    PersistenceManager persistenceManager = new PersistenceManager();

    @POST
    @Path("/logon")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logon(@FormParam("username") String username, @FormParam("password") String password){

        username = (null == username ? "" : username);
        password = (null == password ? "" : password);

        if (username.trim().isEmpty() || password.trim().isEmpty()){
            return Response
                    .status(403)
                    .entity("Denied")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }


        EntityManager em = persistenceManager.getEntityManager();
        em.getTransaction().begin();

        String queryString = "Select u\n" +
                "from User u\n" +
                "where u.username = :username";

        Query query = em.createQuery(queryString);
        query.setParameter("username", username);
        User user = (query.getResultList().isEmpty())?new User():(User) query.getResultList().get(0);

        AuthResponseBody response = new AuthResponseBody();

        if (password.equals(user.getPassword())){
            try {
                String finalToken = Base64.getEncoder().encodeToString(generateToken(username).getBytes());
                Token token = new Token();
                token.setUsername(username);
                token.setToken(finalToken);
                token.setCreationDate(new Timestamp(System.currentTimeMillis()));
                em.merge(token);
                response.setToken(finalToken);
                em.getTransaction().commit();
                em.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Response
                    .ok()
                    .entity(response)
                    .build();
        }else{
            em.remove(em.find(Token.class,username));
        }

        em.getTransaction().commit();
        em.close();
        return Response
                .status(401)
                .entity("Unauthorized")
                .type(MediaType.TEXT_PLAIN)
                .build();

    }

    @POST
    @Path("/validate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateToken(AuthRequestBody request){

        String username = "";
        String token = "";

        if(null != request) {
            username = (null == request.getUsername())?"":request.getUsername();
            token = (null == request.getPassword())?"":request.getPassword();
        }

        if (username.trim().isEmpty() || token.trim().isEmpty()){
            return Response
                    .status(403)
                    .entity("Denied")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

        EntityManager em = persistenceManager.getEntityManager();
        em.getTransaction().begin();

        switch(AuthHelper.isValidToken(em,username,token)) {
            case ACCESS_GRANTED:
            em.getTransaction().commit();
            em.close();
            return Response
                    .ok()
                    .build();
            case ACCESS_DENIED:
                em.getTransaction().commit();
                em.close();
                return Response
                        .status(403)
                        .entity("Denied")
                        .type(MediaType.TEXT_PLAIN)
                        .build();
            case TOKEN_EXPIRED:
                em.getTransaction().commit();
                em.close();
                return Response
                        .status(401)
                        .entity("Session Expired")
                        .type(MediaType.TEXT_PLAIN)
                        .build();
            default:
                em.getTransaction().commit();
                em.close();
                return Response
                        .status(403)
                        .build();
        }
    }

    /**
     * This method uses the username to create a randomized/unique token that can be used for authentication purposes
     * for basic security
     * @param username as a java util String
     * @return token  as a java util String
     * @throws Exception
     * @see Math
     * @see SecureRandom
     * @see AuthHelper
     */
    public synchronized String generateToken( String username ) throws Exception {
        SecureRandom random = new SecureRandom();
        long longToken = Math.abs( random.nextLong() );
        String randomized = Long.toString( longToken, 16 );
        String token = username + randomized;
        return AuthHelper.encrypt(token, username);
    }



}
