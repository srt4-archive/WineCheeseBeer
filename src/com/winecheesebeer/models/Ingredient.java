package com.winecheesebeer.models;

import java.util.ArrayList;

public class Ingredient {
	public String name;
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
