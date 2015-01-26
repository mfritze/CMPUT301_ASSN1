package com.school.cmput301;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

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
	private Claim claim;
	private int claimIndex;
	private Spinner spinView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claim_editor);
		
		Intent intent = getIntent();
		claimIndex = intent.getIntExtra(CLAIMINDEX, 0);
		ArrayList<Claim> test = ClaimListSingleton.getClaimList().getClaimArrayList();
		claim = test.get(claimIndex);
		//claim = ClaimListSingleton.getClaimList().getClaimArrayList().get(claimIndex);
		
		TextView title = (TextView) findViewById(R.id.claimTitle);
		title.setText(claim.getName());
		
		addExpenseListeners();
	}
	
	private void addExpenseListeners(){
		ListView expenseView = (ListView) findViewById(R.id.expenseList);
		Collection<Expense> expenseCollection = claim.getExpenseList();
		final ArrayList<Expense> expenses = new ArrayList<Expense>(expenseCollection);
		//final ArrayAdapter<Expense> expenseAdapter = new ArrayAdapter<Expense>(this, android.R.layout.simple_list_item_1 , expenses);
		final ExpenseAdapter expenseAdapter = new ExpenseAdapter(this, R.layout.claim_adapter, expenses);
		final int index = claimIndex;
		final TextView currencyView = (TextView) findViewById(R.id.currencyText);
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
		
		ClaimListSingleton.getClaimList().addListener(new Listener(){

			@Override
			public void update() {
				HashMap<String,Float> currencies = claim.getCurrencies();
				String costListing = "Total Cost:\n";
				for(String key: currencies.keySet()){
					costListing += Float.toString(currencies.get(key)) + " "+ key + "\n";
				}
				currencyView.setText(costListing);
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
		
		final PopupWindow window = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
		final EditText categoryView = (EditText) popupView.findViewById(R.id.expenseName);
		final EditText descView = (EditText) popupView.findViewById(R.id.expenseDescription);
		final EditText costView = (EditText) popupView.findViewById(R.id.expenseCost);
		final DatePicker dateView = (DatePicker) popupView.findViewById(R.id.expenseDate);
		
		ArrayList<String> spinnerItems = ExpenseCost.getCurrencyList();
		spinView = (Spinner) popupView.findViewById(R.id.currencySelector);
		ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, spinnerItems);
		spinView.setAdapter(spinAdapter);
		
		Button submit = (Button) popupView.findViewById(R.id.submitExpense);
		submit.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
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
					Expense exp = new Expense(date, category, description,cost, currencyCode);
					claim.addExpense(exp);
					ClaimListSingleton.getClaimList().notifyListeners();
				}

			
				window.dismiss();
			}
		});

		window.showAtLocation(popupView, Gravity.CENTER, 0, 0);
		
	}
}
