package com.school.cmput301;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.school.cmput301.Activities.MainActivity;
import com.school.cmput301.Controllers.ExpenseAdapter;
import com.school.cmput301.Controllers.Listener;
import com.school.cmput301.Models.Claim;
import com.school.cmput301.Models.ClaimList;
import com.school.cmput301.Models.ClaimListSingleton;
import com.school.cmput301.Models.Expense;

public class ExpenseManagerActivityTEMP extends Activity {
	private final String CLAIMINDEX = "com.school.cmput301.claimid";
	private Claim claim;
	private int claimIndex;
	private Spinner spinView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_list_layout);
		
		Intent intent = getIntent();
		claimIndex = intent.getIntExtra(CLAIMINDEX, 0);
		ArrayList<Claim> test = ClaimListSingleton.getClaimList().getClaimArrayList();
		claim = test.get(claimIndex);
		
		setExpenseAdapter();
		setActionBar();
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
		menuTitle.setText(claim.getName());
		
		ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton);
		homeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void setExpenseAdapter(){
		final ListView expenseView = (ListView) findViewById(R.id.expenseList);
		Collection<Expense> expenseCollection = claim.getExpenseList();
		final ArrayList<Expense> expenses = new ArrayList<Expense>(expenseCollection);
		final ExpenseAdapter expenseAdapter = new ExpenseAdapter(this, R.layout.expense_adapter, expenses);
		final int index = claimIndex;
		final TextView currencyView = (TextView) findViewById(R.id.currencyText);
		expenseView.setAdapter(expenseAdapter);
		
		expenseView.setOnItemClickListener(new OnItemClickListener(){
			//Based on http://www.mkyong.com/android/android-alert-dialog-example/ Jan 28 2015
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				AlertDialog.Builder ad = new AlertDialog.Builder(ExpenseManagerActivityTEMP.this);
				final Claim claim = ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex);
				if(claim != null){
					final Expense expense = claim.getExpenseList().get(position);
					ad.setMessage("Delete : "+ expense.getCategory() + " ?");
					ad.setCancelable(true);
					ad.setPositiveButton("DO IT", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							claim.removeExpense(expense);
							ClaimListSingleton.getClaimList().notifyListeners();
						}
					});
					
					ad.setNegativeButton("Nope", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
				}
				AlertDialog dialogView = ad.create();
				try{
					dialogView.show();
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		
		ClaimListSingleton.getClaimList().addListener(new Listener(){
			@Override
			public void update(){
				expenses.clear();
				ClaimList l = ClaimListSingleton.getClaimList();
				Claim c = l.getClaimAtIndex(index);
				if(c != null){
					ArrayList<Expense> newExpenses = c.getExpenseList();
					expenses.addAll(newExpenses);
					expenseAdapter.notifyDataSetChanged();
				}
			}
		});
		
		ClaimListSingleton.getClaimList().addListener(new Listener(){

			@Override
			public void update() {
				HashMap<String,Float> currencies = claim.getCurrencies();
				String costListing = "Total Cost:";
				for(String key: currencies.keySet()){
					costListing += "\n" + Float.toString(currencies.get(key)) + " "+ key;
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
		View popupView = inflater.inflate(R.layout.expense_manager_layout, null);
		
		final PopupWindow window = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
		final EditText categoryView = (EditText) popupView.findViewById(R.id.expenseName);
		final EditText descView = (EditText) popupView.findViewById(R.id.expenseDescription);
		final EditText costView = (EditText) popupView.findViewById(R.id.expenseCost);
		final DatePicker dateView = (DatePicker) popupView.findViewById(R.id.expenseDate);
		
		//ArrayList<String> spinnerItems = ExpenseCost.getCurrencyList();
		String[] spinnerItems = {"CAD", "USD", "EUR", "GBP"};
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
