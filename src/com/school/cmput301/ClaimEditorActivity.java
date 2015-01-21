package com.school.cmput301;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ClaimEditorActivity extends Activity {
	private final String CLAIMINDEX = "com.school.cmput301.claimid";
	private final String EXPENSEINDEX = "com.school.cmput301.expenseid";
	Claim claim;
	int claimIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claim_editor);
		
		Intent intent = getIntent();
		claimIndex = intent.getIntExtra(CLAIMINDEX, 0);
		claim = ClaimListSingleton.getClaimList().getClaimArrayList().get(claimIndex);
		
		TextView title = (TextView) findViewById(R.id.claimTitle);
		title.setText(claim.getName());
		
		ListView claimView = (ListView) findViewById(R.id.claimListView);
		Collection<Expense> expenseCollection = claim.getExpenseList();
		final ArrayList<Expense> expenses = new ArrayList<Expense>(expenseCollection);
		final ArrayAdapter<Expense> expenseAdapter = new ArrayAdapter<Expense>(this, android.R.layout.simple_list_item_1 , expenses);
		final int index = claimIndex;
		claimView.setAdapter(expenseAdapter);
		
		ClaimListSingleton.getClaimList().addListener(new Listener(){
			@Override
			public void update(){
				expenses.clear();
				ArrayList<Claim> 
				expenses.addAll(newClaims);
				expenseAdapter.notifyDataSetChanged();
			}
		});
	}
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
		Intent intent = new Intent(this, ExpenseCreator.class);
		intent.putExtra(EXPENSEINDEX, claimIndex);
	}
}
