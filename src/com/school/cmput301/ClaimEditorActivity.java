package com.school.cmput301;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ClaimEditorActivity extends Activity {
	private final String CLAIMINDEX = "com.school.cmput301.claimid";
	private final String EXPENSEINDEX = "com.school.cmput301.expenseid";
	private Claim claim;
	private int claimIndex;
	private Spinner spinView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claim_editor);
		
		Intent intent = getIntent();
		claimIndex = intent.getIntExtra(CLAIMINDEX, 0);
		claim = ClaimListSingleton.getClaimList().getClaimArrayList().get(claimIndex);
		
		TextView title = (TextView) findViewById(R.id.claimTitle);
		title.setText(claim.getName());
		
		ArrayList<String> spinnerItems = ExpenseCost.getCurrencyList();
		spinView = (Spinner) findViewById(R.id.currencySelector);
		ArrayAdapter spinAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, spinnerItems);
		spinView.setAdapter(spinAdapter);
		
		ListView expenseView = (ListView) findViewById(R.id.expenseList);
		Collection<Expense> expenseCollection = claim.getExpenseList();
		final ArrayList<Expense> expenses = new ArrayList<Expense>(expenseCollection);
		final ArrayAdapter<Expense> expenseAdapter = new ArrayAdapter<Expense>(this, android.R.layout.simple_list_item_1 , expenses);
		final int index = claimIndex;
		expenseView.setAdapter(expenseAdapter);
		
		ClaimListSingleton.getClaimList().addListener(new Listener(){
			@Override
			public void update(){
				expenses.clear();
				ClaimList l = ClaimListSingleton.getClaimList();
				Claim c = l.getClaimAtIndex(index);
				ArrayList<Expense> newExpenses = c.getExpenseList();
				expenses.addAll(newExpenses);
				expenseAdapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.claim_editor, menu);
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
	
	public void addExpense(View v){
        
		LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView = inflater.inflate(R.layout.create_expense, null);
		final PopupWindow window = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		
		Button submit = (Button) popupView.findViewById(R.id.submitExpense);
		submit.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText categoryView = (EditText) findViewById(R.id.expenseName);
				EditText descView = (EditText) findViewById(R.id.expenseDescription);
				EditText costView = (EditText) findViewById(R.id.expenseCost);
				DatePicker dateView = (DatePicker) findViewById(R.id.expenseDate);
				
				String category, description, dateString, currencyCode;
				float cost = -1;
				Date date = null;
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				
				category = categoryView.getText().toString();
				description = descView.getText().toString();
				currencyCode = (String) spinView.getSelectedItem();
				
				try{ //http://stackoverflow.com/questions/15864910/convert-edittext-to-float-android-eclipse jan 21 2015
					cost = Float.valueOf(costView.getText().toString());
				}catch(NumberFormatException e){
					e.printStackTrace();
				}
				
				if((category.equals("")) || (description.equals("")) || (currencyCode.equals(""))){
					Toast.makeText(getBaseContext(), "Fill in all of the fields!", Toast.LENGTH_SHORT).show();
				}else{
				
					try {
						int month = dateView.getMonth(), day = dateView.getDayOfMonth(), year = dateView.getYear();
						dateString = String.format("%d-%d-%d", day,month, year);
						date = sdf.parse(dateString);
						//date = dateFormat.parse(dateString);
					} catch (ParseException e) {
						Toast.makeText(getBaseContext(), "Error reading date", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					} 	
				}

				Expense exp = new Expense(date, category, description,cost, currencyCode);
				claim.addExpense(exp);
				window.dismiss();
			}
		});

		window.showAtLocation(popupView, Gravity.CENTER, 0, 0);
		
	}
}
