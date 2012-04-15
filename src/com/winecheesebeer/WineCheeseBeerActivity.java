package com.winecheesebeer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.winecheesebeer.models.Ingredient;
import com.winecheesebeer.models.Item;
import com.winecheesebeer.models.ItemCollection;

public class WineCheeseBeerActivity extends Activity {
	
	private ItemCollection<String, Item> ic;
	 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ic = new ItemCollection<String, Item>();
        fillIC();
        Button b = (Button) findViewById(R.id.scan);
        b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
		        IntentIntegrator integrator = new IntentIntegrator(WineCheeseBeerActivity.this);
		        integrator.initiateScan();
			}
			
        });
        

        
        /*lv.setOnItemClickListener(new OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              // When clicked, show a toast with the TextView text
              Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
                  Toast.LENGTH_SHORT).show();
            }
          });
   	   */
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	  IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
    	  
    	  if (scanResult != null) {
    	    // handle scan result
    		TextView t = (TextView) findViewById(R.id.barCode);
    		Item item = (Item) ic.get(scanResult.getContents());
    		t.setText(item.name + " - " + item.barcode);
    		ArrayList<Ingredient> ings = item.ingredients;
    		ArrayList<String> ingredients = new ArrayList<String>(); 
    		for (Ingredient singleIng : ings) {
    			ingredients.add(singleIng.toString());
    		}
    		ListView lv = (ListView) findViewById(R.id.list);
    		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredients));
    		
    		//ItemCollection ic = new ItemCollection();
    		if (false) {
	    		try {
	    			
	    			
	    			JSONObject j = getBarcode(scanResult.getContents());
	
	    			Log.v("TIMING", "Received http data");
	    			
	    			JSONArray jarray = j.getJSONArray("ingredients");
	    			ArrayList<String> ingreds = new ArrayList<String>();
	    			for (int i = 0; i < jarray.length(); i++) {
	    				ingreds.add(((JSONObject)jarray.get(i)).getString("name"));
	    			}
	    			
	    	      //  ListView lv = (ListView) findViewById(R.id.list);
	    	      //  lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingreds));
					
				} catch (Exception e) {
					 e.printStackTrace();
				}
    	  }
    	Log.v("TIMING", "Done dislpaying JSON");
       }
    }
    
    private JSONObject getBarcode(String barcode) throws ClientProtocolException, IOException, JSONException {
    	HttpClient hc = new DefaultHttpClient();
    	
    	HttpGet g = new HttpGet("http://192.168.1.2:3000/items/" + barcode + ".json");
    	Log.v("URL", "http://192.168.1.2:3000/items/" + barcode + ".json");
    	HttpResponse hr = hc.execute(g);
    	
    	String response = EntityUtils.toString(hr.getEntity());
    	Log.v("Response", (String) new JSONObject(response).get("name"));
    	return new JSONObject(response);
    }
    
    private void fillIC() {
    	HttpClient hc = new DefaultHttpClient();
    	HttpGet g = new HttpGet("http://spencerrthomas.com:8080/items.json");
    	HttpResponse hr;
    	String response; 
    	JSONArray o; 
    	
		try {
			hr = hc.execute(g);
			response = EntityUtils.toString(hr.getEntity());
			o = new JSONArray(response);
			ArrayList<String> ingreds = new ArrayList<String>();
			for (int i = 0; i < o.length(); i++) {
				JSONObject j = o.getJSONObject(i);
				JSONArray jarray;
				// if exists ingredients
    			//ArrayList<String> ingreds = new ArrayList<String>();

				try {
					jarray = j.getJSONArray("ingredients");
					Item i1 = new Item(j.getString("name"), j.getString("barcode"));
					
	    			for (int k = 0; k < jarray.length(); k++) {
	    				ingreds.add(((JSONObject)jarray.get(k)).getString("name"));
	    				i1.addIngredient(new Ingredient(((JSONObject)jarray.get(k)).getString("name")));
	    			}
	    			ic.put(j.getString("barcode"), i1);
				} catch (Exception e) {
					
				}
    			
    	        ListView lv = (ListView) findViewById(R.id.list);
    	        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingreds));
				((TextView)findViewById(R.id.barCode)).setText(j.getString("name"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
		
    }
}