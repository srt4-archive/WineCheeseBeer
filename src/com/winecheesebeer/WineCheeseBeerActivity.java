package com.winecheesebeer;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class WineCheeseBeerActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button b = (Button) findViewById(R.id.scan);
        b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
		       IntentIntegrator integrator = new IntentIntegrator(WineCheeseBeerActivity.this);
		       integrator.initiateScan();
			}
			
        });
        
        ListView lv = (ListView) findViewById(R.id.list);
        String[] ingredients = new String[] {"water", "poop", "carrots"};
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredients));
        final String[] categories = new String[]{"main ingredients","preservatives"};
        
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
            Intent i = new Intent(WineCheeseBeerActivity.this, SecondActivity.class);
            i.putExtra("categories", categories);
            WineCheeseBeerActivity.this.startActivity(i);               
            }
        });
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	  IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
    	  if (scanResult != null) {
    	    // handle scan result
    		TextView t = (TextView) findViewById(R.id.barCode);
    		try {
				t.setText((String)getBarcode(scanResult.getContents()).getString("name"));
			} catch (Exception e) {
				 e.printStackTrace();
			}
    	  }
    	  // else continue with any other code you need in the method
    	  // ...
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
}