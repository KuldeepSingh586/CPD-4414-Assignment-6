/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import Beans.ProductList;
import Beans.Products;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author Kuldeep
 */
@Path("/Products")
@RequestScoped
public class ProductREST {

    @Inject
    ProductList productList;
    
    @GET
    @Produces("application/json")
    public Response getAll() {
        return Response.ok(productList.toJson()).build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getById(@PathParam("id") int id) {
        return Response.ok(productList.toJson()).build();
    }
    /**
     * set Method take two parameters id and json object
     * @param id
     * @param json
     * @return 
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Response set(@PathParam("id") int id, JsonObject json) {
        try {
            Products p = new Products(json);

            productList.set(id, p);
            return Response.ok().build();
        } catch (Exception ex) {
            System.err.print("Excpeion in Set Method in ProductRest.java");
        }
        return Response.status(500).build();
    }
    /**
     * delete method take only parameter i.e id=5 it will delete the product
     * whose id is 5
     * @param id
     * @return 
     */
    @DELETE
    @Path("{id}")
    @Consumes("application/json")
    public Response delete(@PathParam("id") int id) {
        Response response;
        try {
            productList.remove(id);
            response = Response.ok().build();
        } catch (Exception ex) {
            response = Response.status(500).build();
        }
        return response;
    }
    /**
     * add method
     * @param json
     * @return 
     */
    @POST
    @Consumes("application/json")
    public Response add(JsonObject json) {
        try {
            productList.add(new Products(json));
            return Response.ok().build();
        } catch (Exception ex) {
            System.err.print("Excpeion in add Method in ProductRest.java");
        }
        return Response.status(500).build();
    }
}
