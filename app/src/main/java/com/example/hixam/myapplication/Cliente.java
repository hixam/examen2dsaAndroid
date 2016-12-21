package com.example.hixam.myapplication;

import android.util.Log;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by hixam on 20/12/16.
 */
public class Cliente {

    private final static String BASE_URI = "http://localhost:9091/etakemon";
    private static Cliente instance;
    private ClientConfig clientConfig = null;
    private Client client = null;

    private Cliente() {
        clientConfig = new ClientConfig();
        client = ClientBuilder.newClient(clientConfig);
        loadRoot();
    }

    public static Cliente getInstance() {
        if (instance == null)
            instance = new Cliente();
        return instance;
    }

    private void loadRoot() {
        WebTarget target = client.target(BASE_URI);
        Response response = target.request().get();

        String json = response.readEntity(String.class);
 //       root = (new Gson()).fromJson(json, Root.class);
    }

    public boolean login(String userid, String password) {
        String loginUri = BASE_URI + "/myresource/login";
        boolean res=false;
        WebTarget target = client.target(loginUri);
        Form form = new Form();
        form.param("login", userid);
        form.param("password",password);
        String json = target.request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);
        if(json.equals("Login ok"))
            res=true;
        return res;
    }


    public String getPokemons(String nick) {
        String uri="";
        String res ="";
        if(nick==null){
            uri = BASE_URI + "/myresource/list/{"+nick+"}";
        }
        WebTarget target = client.target(uri);
        Response response = target.request().get();
        if (response.getStatus() == Response.Status.OK.getStatusCode())
            res= response.readEntity(String.class);

    return res;




}
