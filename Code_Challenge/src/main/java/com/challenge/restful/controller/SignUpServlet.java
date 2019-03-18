package com.challenge.restful.controller;

import com.challenge.restful.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * This Java servlet is used to be called by JSPs related to user profile operations and maintain the JSession
 */
//@WebServlet(name = "Servlet")
public class SignUpServlet extends HttpServlet {

    private static final String HOST = "http://localhost:8080/Code_Challenge_Web_exploded";
    private static final String PATH = "/v1/rest/user/signup";
    User user;

    public void init() throws ServletException {
        if (null == user){
            user = new User();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            user.setUsername(request.getParameter("username"));
            user.setFname(request.getParameter("fname"));
            user.setLname(request.getParameter("lname"));
            user.setEmail(request.getParameter("email"));
            user.setPassword(request.getParameter("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String targetString = HOST+PATH;
        System.out.println("Service Call: " + targetString + "\n" + "user: " + user.toString());
        Client client = ClientBuilder.newClient();
        Response serviceResponse = client.target(targetString)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(user));
        client.close();
        response.sendRedirect("index.jsp");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void destroy() {
        System.gc();
    }
}
