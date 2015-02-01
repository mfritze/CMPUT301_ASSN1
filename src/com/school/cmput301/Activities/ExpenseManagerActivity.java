package com.school.cmput301.Activities;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.school.cmput301.R;
import com.school.cmput301.Fragments.ExpenseListFragment;
import com.school.cmput301.Fragments.ExpenseManagerFragment;
import com.school.cmput301.Models.Claim;
import com.school.cmput301.Models.ClaimListSingleton;

public class ExpenseManagerActivity extends Activity {
	private FragmentManager fm;
	private FragmentTransaction ft;
	private ExpenseListFragment expenseListFragment;
	private ExpenseManagerFragment expenseManagerFragment;
	private Claim editingClaim;
	private int claimIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expense_manager_layout);
		
		Intent intent = getIntent();
		this.claimIndex = intent.getIntExtra(MainActivity.CLAIMINDEX, 0);
		ArrayList<Claim> claimArrayList = ClaimListSingleton.getClaimList().getClaimArrayList();
		this.editingClaim = claimArrayList.get(claimIndex);
		
		fm = getFragmentManager();
		expenseListFragment = new ExpenseListFragment();
		expenseManagerFragment = new ExpenseManagerFragment();
		
	}

	@Override
	protected void onStart() {
		super.onStart();
		setUpActionBar();
		startExpenseList();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		ClaimListSingleton.getClaimList().notifyListeners();
	}

	public void startExpenseList() {
		ft = fm.beginTransaction();
		ft.replace(R.id.claimFragmentHolder , this.expenseListFragment);
		ft.commit();
		
		ClaimListSingleton.getClaimList().notifyListeners();
	}

	public void createExpense(View v){
		ft = fm.beginTransaction();
		ft.replace(R.id.claimFragmentHolder , this.expenseManagerFragment);
		ft.commit();
		
		expenseManagerFragment.setMode(false);
	}
	
	public void editExpense(){
		ft = fm.beginTransaction();
		ft.replace(R.id.claimFragmentHolder , this.expenseManagerFragment);
		ft.commit();
		
		expenseManagerFragment.setMode(true);
	}
	
	public void submitExpense(View v){
		expenseManagerFragment.createExpense();
	}
	
	public Claim getWorkingClaim(){
		return this.editingClaim;
	}
	
	private void setUpActionBar(){
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
		menuTitle.setText(this.editingClaim.getName());
		
		ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton);
		homeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.expense_manager, menu);
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
}
