/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

import Servlets.Product;
import java.util.List;
import javax.json.JsonArray;

/**
 *
 * @author Kuldeep
 */
public class ProductList {
   private List<Product> productList;

    public ProductList() {
    }
   public void add(Product product) {
       productList.add(product);
   }
   public void remove(Product product) {
       productList.remove(product);
   }
   public void remove(int id) {
       
   }
   public void set(int id,Product product) {
       
   }
   public void get(int id) {
       
   }
   public JsonArray toJSON() {
      
       return null;
       
   }
}
