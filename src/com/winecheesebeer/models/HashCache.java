package com.winecheesebeer.models;

import java.util.HashMap;

import android.os.AsyncTask;

public class HashCache {
	private HashMap<String, Item> map;
	private final String ITEMS_URL = "http://spencerrthomas.com:8080";
	
	/**
	 * Instantiates the map
	 */
	public HashCache() {
		this.map = new HashMap<String, Item>();
	}
	
	/**
	 * populateMap(String url)
	 * 
	 */
	public void populate() {
		//AsyncTask atask = new AsyncTask();
		if (!map.isEmpty()){
			
		}
	}
	
	
}
