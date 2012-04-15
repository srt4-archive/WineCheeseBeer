package com.winecheesebeer;
import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SecondActivity extends Activity{
	@Override 
	public void onCreate(Bundle b){
		super.onCreate(b);
		setContentView(R.layout.poop);
		Bundle extras = getIntent().getExtras();
		String[] categories = extras.getStringArray("categories");
		System.out.println(Arrays.toString(categories));
		
		ListView lv = (ListView) findViewById(R.id.listView1);
		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories));
	
	}
}