package com.winecheesebeer;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.winecheesebeer.models.Ingredient;
import com.winecheesebeer.models.Item;
import com.winecheesebeer.models.ItemCollection;

public class WineCheeseBeerActivity extends ExpandableListActivity {
	
	private ItemCollection<String, Item> ic;
	public ExpandableListAdapter mAdapter;
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
        

       /*ListView lv = (ListView) findViewById(R.id.list);
        String[] ingredients = new String[] {"water", "poop", "carrots"};
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredients));
        final String[] categories = new String[]{"main ingredients","preservatives"};
        
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		            Intent i = new Intent(WineCheeseBeerActivity.this, SecondActivity.class);
		            i.putExtra("categories", categories);
		            WineCheeseBeerActivity.this.startActivity(i);               
            }
        });*/
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
    		ExpandableListView lv = (ExpandableListView) findViewById(android.R.id.list);
    		Display newDisplay = getWindowManager().getDefaultDisplay(); 
    		int width = newDisplay.getWidth();
            mAdapter = new MyExpandableListAdapter(ings);
            ((MyExpandableListAdapter) mAdapter).setActivity(this);
            setListAdapter(mAdapter);
            registerForContextMenu(getExpandableListView());

            //lv.setIndicatorBounds(width-50, width);
    		//lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.expandable_list_content, ingredients));
       }
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
    			
    	        ExpandableListView lv = (ExpandableListView) findViewById(android.R.id.list);
    	        Display newDisplay = getWindowManager().getDefaultDisplay(); 
        		int width = newDisplay.getWidth();
        		lv.setIndicatorBounds(width-50, width);
    	        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.expandable_list_content, ingreds));
				((TextView)findViewById(R.id.barCode)).setText(j.getString("name"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
		
    }
}



