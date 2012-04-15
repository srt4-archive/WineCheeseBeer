package com.winecheesebeer.models;

import java.util.ArrayList;

public class Item {
	public String name;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the barcode
	 */
	public String getBarcode() {
		return barcode;
	}

	/**
	 * @param barcode the barcode to set
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	/**
	 * @return the ingredients
	 */
	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}

	/**
	 * @param ingredients the ingredients to set
	 */
	public void setIngredients(ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

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
