package com.challenge.restful.controller;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.*;

/**
 * This Java servlet is used to be called by JSPs related to search and maintain the JSession
 */
//@WebServlet(name = "GiphySearchServlet")
public class GiphySearchServlet extends HttpServlet {

    private static final String HOST = "http://localhost:8080/Code_Challenge_Web_exploded";
    private static final String PATH = "/v1/rest/giphy/search";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");

        String targetString = HOST+PATH+"/"+keyword;
        Client client = ClientBuilder.newClient();
        Response serviceResponse = client.target(targetString)
                .request()
                .get();

        String jsonString = serviceResponse.readEntity(String.class);
        ArrayList<String> searchResultArray = (ArrayList) giphySearchUrlParser(jsonString);

        request.getSession().setAttribute("eList", searchResultArray);
        response.sendRedirect("profile.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Returns an ArrayList of all the URLs of the GIFs returned by the giphy search api
     *
     * @param  jsonString  a raw json string returned by the search api
     * @return      ArrayList of all URLs (as Java util String)
     * @see         Gson
     */
    private List<String> giphySearchUrlParser(String jsonString){
        List<String> searchResultArray = new ArrayList<String>();
        Map<String, Object> map = new Gson().fromJson(jsonString, new TypeToken<HashMap<String, Object>>() {}.getType());
        System.out.println("map: " + map);
        JsonElement jElement = new JsonParser().parse(jsonString);
        JsonObject jObject = jElement.getAsJsonObject();
        JsonArray jArray = jObject.getAsJsonArray("data");
        for (int i=0;i<jArray.size();i++){
            jObject = jArray.get(i).getAsJsonObject();
            jObject = jObject.getAsJsonObject("images");
            jObject = jObject.getAsJsonObject("fixed_width");
            String url = jObject.getAsJsonPrimitive("url").getAsString();
            searchResultArray.add(url);
            System.out.println("FOCUS::url: " + url);
        }

        return searchResultArray;
    }

}
