package services;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Beans.ProductList;
import Beans.Products;
import java.io.StringReader;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.json.Json;
import javax.json.JsonObject;
/**
 *
 * @author Kuldeep 
 */
@MessageDriven(mappedName = "jms/Queue")
public class ProductListener implements MessageListener{
    @Inject   
      ProductList productList;
   @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String jsonString = ((TextMessage) message).getText();
                JsonObject obj = Json.createReader(new StringReader(jsonString)).readObject();
                productList.add(new Products(obj));
                
            }
        } catch (JMSException ex) {
            System.err.println("Exception in JMS: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Exceoption in inserting product: " + ex.getMessage());
        }

    }
}
