package com.brand.sniffy.android.service;

import java.util.ArrayList;
import java.util.List;

import com.brand.sniffy.android.model.Component;
import com.brand.sniffy.android.model.ComponentRating;
import com.brand.sniffy.android.model.Product;

public class ComponentsService {

	public List<Component> getProductComponents(Product product){

		List<Component> components = new ArrayList<Component>();
		ComponentRating rating1 = new ComponentRating();
		rating1.setColor("#FF0000");
		rating1.setTitle("Szkodliwy");
		
		Component component1 = new Component();
		component1.setName("E102 tartrazyna");
		component1.setRating(rating1);
		components.add(component1);
		

		Component component2 = new Component();
		component2.setName("E290 Dwutlenek wêgla");
		component2.setRating(rating1);
		components.add(component2);
		return components;
	}
}
