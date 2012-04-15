package com.winecheesebeer.models;

import java.util.ArrayList;

public class Item {
	public String name;
	public String barcode;
	public ArrayList<Ingredient> ingredients;
	
	public Item(String name, String barcode) {
		this.name = name;
		this.barcode = barcode;
		ingredients = new ArrayList<Ingredient>();
	}
	
	public void addIngredient(Ingredient i) {
		if (!i.items.contains(this))
			i.addItem(this);
		ingredients.add(i);
	}
}
