package com.school.cmput301;

import java.util.ArrayList;
import java.util.Collection;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		addExpenseListeners();
		setActionBar();
	}

	private void addExpenseListeners(){
		ListView claimView = (ListView) findViewById(R.id.claimListView);
		Collection<Claim> claimCollection = ClaimListSingleton.getClaimList().getClaims();
		final ArrayList<Claim> claims = new ArrayList<Claim>(claimCollection);
		//final ArrayAdapter<Claim> claimAdapter = new ArrayAdapter<Claim>(this, android.R.layout.simple_list_item_1 , claims);
		final ClaimAdapter claimAdapter = new ClaimAdapter(this, R.layout.claim_adapter, claims);
		
		//claimAdapter.sort(comparator) TODO
		
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
	
		claimView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				Toast.makeText(getBaseContext(),"On Click!", Toast.LENGTH_SHORT).show();

			}
			
		});
		
		claimView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int position, long id) {
				Toast.makeText(getBaseContext(),"Long click!", Toast.LENGTH_SHORT).show();
				LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View popupView = inflater.inflate(R.layout.edit_claim_popup, null);

				PopupWindow window = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
				
				ImageButton deleteClaim = (ImageButton) popupView.findViewById(R.id.deleteClaim);
				ImageButton sendClaim = (ImageButton) popupView.findViewById(R.id.sendClaim);
				ImageButton approveClaim = (ImageButton) popupView.findViewById(R.id.approvedClaim);
				
				deleteClaim.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Toast.makeText(getBaseContext(), "delete", Toast.LENGTH_SHORT).show();
					}
				});
				
				sendClaim.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Toast.makeText(getBaseContext(), "send", Toast.LENGTH_SHORT).show();
						
					}
				});
				
				approveClaim.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Toast.makeText(getBaseContext(), "Approve", Toast.LENGTH_SHORT).show();						
					}
				});
				
				//TODO make background darker
				window.setBackgroundDrawable(new BitmapDrawable());
		        window.setOutsideTouchable(true);
				window.showAtLocation(popupView, Gravity.CENTER, 0, 0);
				return false;
			}
		
		});
	}
	
	private void setActionBar(){
		//Based on http://stackoverflow.com/questions/6746665/accessing-a-font-under-assets-folder-from-xml-file-in-android Jan 25 2015
		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.actionbar_layout, null);
		
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);
		
		
		//Based on http://stackoverflow.com/questions/6746665/accessing-a-font-under-assets-folder-from-xml-file-in-android Jan 25 2015
		TextView menuTitle = (TextView) findViewById(R.id.menuTitle);
		Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto/Roboto-Light.ttf");
		menuTitle.setTypeface(tf);
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
