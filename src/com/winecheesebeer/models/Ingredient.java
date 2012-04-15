package com.winecheesebeer.models;

import java.util.ArrayList;

public class Ingredient {
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the items
	 */
	public ArrayList<Item> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public String description;
	public ArrayList<Item> items;
	
	public Ingredient (String name) {
		items = new ArrayList<Item>();
		this.name = name;
	}
	
	public Ingredient (String name, String description) {
		this(name);
		this.description = description;
	}
	
	public void addItem(Item i) {
		items.add(i);
	}
	
	public String toString() {
		return this.name;
	}
}
