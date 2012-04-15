package com.winecheesebeer.models;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.winecheesebeer.R;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HashCache {
	public HashMap<String, Item> map;
	private final String ITEMS_URL = "http://spencerrthomas.com:8080/items.json";
	private PopulateMapTask pmt; 
	/**
	 * Instantiates the map
	 */
	public HashCache() {
		this.map = new HashMap<String, Item>();
		pmt = new PopulateMapTask();
		refresh();
	}
	
	public void refresh() {
		pmt.execute(new String[] { ITEMS_URL });	
	}
	
	/**
	 * populateMap(String url)
	 * 
	 */
	public class PopulateMapTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... args) {
			StringBuilder response = new StringBuilder();
			for (String url : args) {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				try {
					HttpResponse execute = client.execute(httpGet);
					response.append(EntityUtils.toString(execute.getEntity()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return response.toString();
		}
		
		@Override
		protected void onPostExecute(String result) {
			try {
				JSONArray arr = new JSONArray(result);
				if (map == null || !map.isEmpty()) {
					map = new HashMap<String, Item>();
				}
				map = fillTableWithJSONArray(arr, map);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private HashMap<String, Item> fillTableWithJSONArray(JSONArray o, HashMap<String, Item> hm) {
		ArrayList<String> ingreds = new ArrayList<String>();
		for (int i = 0; i < o.length(); i++) {
			try {
				JSONObject j = o.getJSONObject(i);
				JSONArray jarray;
				jarray = j.getJSONArray("ingredients");
				Item i1 = new Item(j.getString("name"), j.getString("barcode"));
				
    			for (int k = 0; k < jarray.length(); k++) {
    				ingreds.add(((JSONObject)jarray.get(k)).getString("name"));
    				i1.addIngredient(new Ingredient(((JSONObject)jarray.get(k)).getString("name")));
    			}
    			hm.put(j.getString("barcode"), i1);
			} catch (Exception e) {
				
			}
		}
		return hm;
	}
}
