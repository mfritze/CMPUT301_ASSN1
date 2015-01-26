package com.school.cmput301;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView claimView = (ListView) findViewById(R.id.claimListView);
		Collection<Claim> claimCollection = ClaimListSingleton.getClaimList().getClaims();
		final ArrayList<Claim> claims = new ArrayList<Claim>(claimCollection);
		//final ArrayAdapter<Claim> claimAdapter = new ArrayAdapter<Claim>(this, android.R.layout.simple_list_item_1 , claims);
		final ClaimAdapter claimAdapter = new ClaimAdapter(this, R.layout.claim_adapter, claims);
		claimView.setAdapter(claimAdapter);
		
		ClaimListSingleton.getClaimList().addListener(new Listener(){
			@Override
			public void update(){
				claims.clear();
				Collection<Claim> newClaims = ClaimListSingleton.getClaimList().getClaims();
				claims.addAll(newClaims);
				claimAdapter.notifyDataSetChanged();
			}
		});
		
		ClaimListSingleton.getClaimList().addListener(new Listener(){

			@Override
			public void update() {
				String costText = "Total Costs: \n";
				TextView costView = (TextView) findViewById(R.id.claimsCostText);
				HashMap<String, Float> currencies = ClaimListSingleton.getCurrencies();
				
				for(String key: currencies.keySet()){
					costText += Float.toString(currencies.get(key)) + " "+ key + "\n";
				}
				costView.setText(costText);
				
			}			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void launchClaimEditor(View v){
		Intent intent = new Intent(this, ClaimCreatorActivity.class);
		startActivity(intent);
	}
}
