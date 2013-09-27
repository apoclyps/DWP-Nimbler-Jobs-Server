package uk.co.kyleharrison.jobseeker.tests;

import java.util.ArrayList;

import uk.co.kyleharrison.jobseeker.model.Country;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonTest {

  public static void main(final String[] args) {

    // Configure GSON
	   final GsonBuilder gsonBuilder = new GsonBuilder();
	   final Gson gson = gsonBuilder.create();

    final Country product = new Country();
    product.setName("Apple");

    final Country product2 = new Country();
    product2.setName("Orange");

    // A list of objects
    final ArrayList<Object> products = new ArrayList<>();
    products.add(product);
    products.add(product2);
    final String json2 = gson.toJson(products);
    System.out.println(json2);
  }
  
}