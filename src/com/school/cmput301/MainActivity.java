package com.school.cmput301;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView claimView = (ListView) findViewById(R.id.claimListView);
		final ArrayList<Claim> claims = ClaimListSingleton.getClaimList().getClaims();
		//claims.add(new Claim("tt","c",".",new Date()));
		final ArrayAdapter<Claim> claimAdapter = new ArrayAdapter<Claim>(this, android.R.layout.simple_list_item_1 , claims);
		claimView.setAdapter(claimAdapter);
		
		ClaimListSingleton.getClaimList().addListener(new Listener(){
			@Override
			public void update(){
				claims.clear();
				ArrayList<Claim> newClaims = ClaimListSingleton.getClaimList().getClaims();
				for(Claim c : newClaims){
					claims.add(c);
				}
				claimAdapter.notifyDataSetChanged();
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
