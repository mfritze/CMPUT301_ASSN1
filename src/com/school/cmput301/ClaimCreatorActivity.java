package com.school.cmput301;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class ClaimCreatorActivity extends Activity {
	
	public final String CLAIMINDEX = "com.school.cmput301.claimid";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_claim);
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
	
	public void startClaimEditor(View v){
		Intent intent = new Intent(this, ClaimEditorActivity.class);
		
		EditText nameView = (EditText) findViewById(R.id.claimNameEditText);
		EditText categoryView = (EditText) findViewById(R.id.claimCategoryEditText);
		EditText descriptionView = (EditText) findViewById(R.id.claimDescriptionEditText);
		DatePicker dateView = (DatePicker) findViewById(R.id.claimDatePicker);
		String name,category,description, dateString;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); //http://stackoverflow.com/questions/17674308/date-from-edittext Jan 18 2015
		Date date = null;
		Claim claim;
		
		//dateString = dateView.getText().toString();
		name = nameView.getText().toString();
		category = categoryView.getText().toString();
		description = descriptionView.getText().toString();
	
		
		if((name.equals("")) || (category.equals("")) || (description.equals("")) ){
			Toast.makeText(this, "Fill in all of the fields!", Toast.LENGTH_SHORT).show();
		}else{
		
			try {
				int month = dateView.getMonth(), day = dateView.getDayOfMonth(), year = dateView.getYear();
				dateString = String.format("%d-%d-%d", day,month, year);
				date = dateFormat.parse(dateString);
				//date = dateFormat.parse(dateString);
			} catch (ParseException e) {
				Toast.makeText(this, "Error reading date", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			} 
			
			claim = new Claim(name, category, description, date);
			ClaimListSingleton.getClaimList().add(claim);
	
			intent.putExtra(CLAIMINDEX, ClaimListSingleton.getClaimList().indexOf(claim));
			startActivity(intent);
		}
	}
}
