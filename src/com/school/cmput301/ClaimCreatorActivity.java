package com.school.cmput301;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.school.cmput301.Activities.MainActivity;
import com.school.cmput301.Models.Claim;
import com.school.cmput301.Models.ClaimListSingleton;

public class ClaimCreatorActivity extends Activity {
	
	private final static String CLAIMINDEX = "com.school.cmput301.claimid";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claim_manager_layout);
		setFonts();
		setActionBar();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.claim_creator, menu);
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
	
	private void setFonts(){
		TextView startDate = (TextView) findViewById(R.id.startDate);
		TextView endDate = (TextView) findViewById(R.id.endDate);
		Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto/Roboto-Medium.ttf");
		startDate.setTypeface(tf);
		endDate.setTypeface(tf);
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
		
		ImageButton homeButton = (ImageButton) actionBarLayout.findViewById(R.id.homeButton);
		homeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	public void startClaimEditor(View v){
		Intent intent = new Intent(this, ExpenseManagerActivityTEMP.class);
		
		EditText nameView = (EditText) findViewById(R.id.claimNameEditText);
		EditText categoryView = (EditText) findViewById(R.id.claimCategoryEditText);
		EditText descriptionView = (EditText) findViewById(R.id.claimDescriptionEditText);
		DatePicker dateStartView = (DatePicker) findViewById(R.id.claimStartDatePicker);
		DatePicker dateEndView = (DatePicker) findViewById(R.id.claimEndDatePicker);
		String name,category,description, startDateString, endDateString;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); //http://stackoverflow.com/questions/17674308/date-from-edittext Jan 18 2015
		Date startDate = null;
		Claim claim;
		
		//dateString = dateView.getText().toString();
		name = nameView.getText().toString();
		category = categoryView.getText().toString();
		description = descriptionView.getText().toString();
	
		
		if((name.equals("")) || (category.equals("")) || (description.equals("")) ){
			Toast.makeText(this, "Fill in all of the fields!", Toast.LENGTH_SHORT).show();
		}else{
		
			try {
				//Month is the only one indexed from 0
				int smonth = dateStartView.getMonth() + 1, sday = dateStartView.getDayOfMonth(), syear = dateStartView.getYear();
				int emonth = dateEndView.getMonth() + 1, eday = dateEndView.getDayOfMonth(), eyear = dateEndView.getYear();
				startDateString = String.format("%d/%d/%d", sday,smonth, syear);
				endDateString = String.format("%d/%d/%d", eday,emonth, eyear);
				startDate = dateFormat.parse(startDateString);
				//endDate = dateFormat.parse(endDateString);
				//date = dateFormat.parse(dateString);
				
				claim = new Claim(name, category, description, startDateString, endDateString, startDate);
				ClaimListSingleton.getClaimList().addClaim(claim);
				ClaimListSingleton.sortClaimList();
				ClaimListSingleton.getClaimList().notifyListeners();
				int index = ClaimListSingleton.getClaimList().getIndex(claim);
				intent.putExtra(CLAIMINDEX, index);
				startActivity(intent);
			} catch (ParseException e) {
				Toast.makeText(this, "Error reading date", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			} 
			
		}
	}
}
