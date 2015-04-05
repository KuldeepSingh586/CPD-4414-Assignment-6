/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import DataBaseConnection.Credentials;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

/**
 *
 * @author Kuldeep
 */
@ApplicationScoped
public class ProductList {

    private List<Products> productList;

    public ProductList() {
        productList=new ArrayList<>();
        try (Connection conn = Credentials.getConnection()) {

            String query = "SELECT * FROM product;";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
              Products  p = new Products(
                        rs.getInt("ProductID"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("quantity"));
                productList.add(p);
            }
        } catch (Exception ex) {

        }
    }

    public JsonArray toJson() {
        JsonArrayBuilder json = Json.createArrayBuilder();
        for (Products p : productList) {
            json.add(p.toJSON());
        }
        return json.build();
    }

    public Products get(int productID) {
        Products result = null;
        for (Products p : productList) {
            if (p.getProductId() == productID) {
                result = p;
                break;
            }
        }
        return result;
    }

    public void add(Products product) throws Exception {
        int result=doUpdate("INSERT INTO product(productId, name,description, quantity) Values(?,?,?,?) ",
                String.valueOf(product.getProductId()),
                product.getName(),
                product.getDescription(),
                String.valueOf(product.getQuantity()));
        if(result>0) {
          productList.add(product);  
        }
        else throw new Exception("Error While Inserting Value");
        
    }
    

    public void remove(Products product) {
    
        productList.remove(product.getProductId());
    }

    public void remove(int productId) throws Exception {
          int result=doUpdate("DELETE FROM product WHERE productId=?",String.valueOf(productId));
          if(result>0) {
              Products original=get(productId);
              productList.remove(original);
              
          } 
          else  throw new Exception("Unable to execute Delete Statement");
          
          
          
    }  
    public void set(int productID, Products products) throws Exception {
        int result=doUpdate("Update product SET name =?, description =?, quantity=? WHERE productId=?",
        products.getName(), 
        products.getDescription(),
        String.valueOf(products.getQuantity()),
       String.valueOf(productID));
       if(result==1) {
        Products Origianl = get(productID);
        Origianl.setName(products.getName());
        Origianl.setDescription(products.getDescription());
        Origianl.setQuantity(products.getQuantity());
       }
       else throw new Exception("Error with update Statement");
    }

   
    /**
     * doUpdate Method accepts two arguments Update the entries in the table
     * 'product'
     *
     * @param query
     * @param params
     * @return numChanges
     */
    private int doUpdate(String query, String... params) {
        int numChanges = 0;
        try (Connection conn = Credentials.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            for (int i = 1; i <= params.length; i++) {
                pstmt.setString(i, params[i - 1]);
            }
            numChanges = pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("SQL EXception in doUpdate Method" + ex.getMessage());
        }
        return numChanges;
    }
    
     /**
     * resultMethod accepts two arguments It executes the Query get ProductID,
     * name, description, quantity. Used JSON object model and provides methods
     * to add name/value pairs to the object model and to return the resulting
     * object
     *
     * @param query
     * @param params
     * @throws SQLException
     * @return
     */
    private String resultMethod(String query, String... params) {
        String strJson = "";
        JsonArrayBuilder jsonArrayObj = Json.createArrayBuilder();
        try (Connection conn = Credentials.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            for (int i = 1; i <= params.length; i++) {
                pstmt.setString(i, params[i - 1]);
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                JsonObject json = Json.createObjectBuilder()
                        .add("ProductID", rs.getInt("ProductID"))
                        .add("name", rs.getString("name"))
                        .add("description", rs.getString("description"))
                        .add("quantity", rs.getInt("quantity")).build();

                jsonArrayObj.add(json);
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception Error: " + ex.getMessage());
        }

        strJson = jsonArrayObj.build().toString();
        return strJson;
    }
}
