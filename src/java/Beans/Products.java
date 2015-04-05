/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author Kuldeep
 */
public class Products {

    private int productId;
    private String name;
    private String description;
    private int quantity;

    public Products() {
    }

    public Products(int productId, String name, String description, int quantity) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.quantity = quantity;

    }

    public Products(JsonObject json) {
        productId = json.getInt("productId");
        name = json.getString("name");
        description = json.getString("description");
        quantity = json.getInt("quantity");
    }

    public JsonObject toJSON() {
     return Json.createObjectBuilder()
             .add("productId",productId)
             .add("name", name)
             .add("description", description)
             .add("quantity",quantity).build();
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
