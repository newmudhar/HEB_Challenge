package com.challenge.restful.util;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/*
Created to to have the EntityManager embedded in so that in can be injected and maintained by the container
 */
@Stateless
public class PersistenceManager {

//    @PersistenceContext
    private EntityManager em;

    public PersistenceManager() {
        if (null == em){
            em = Persistence.createEntityManagerFactory("challenge").createEntityManager();
        }

    }
    public EntityManager getEntityManager() {
        return em;
    }
    public void close() {
        em.close();
    }
}