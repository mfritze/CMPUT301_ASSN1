package com.school.cmput301.Fragments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.school.cmput301.R;
import com.school.cmput301.Activities.ExpenseManagerActivity;
import com.school.cmput301.Controllers.ExpenseAdapter;
import com.school.cmput301.Controllers.Listener;
import com.school.cmput301.Models.Claim;
import com.school.cmput301.Models.ClaimListSingleton;
import com.school.cmput301.Models.Expense;

public class ExpenseListFragment extends Fragment{
	Claim editClaim;
	ExpenseAdapter expenseAdapter;
	ArrayList<Expense> expenses;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		editClaim = ((ExpenseManagerActivity) getActivity()).getWorkingClaim();
		Collection<Expense> expenseCollection = editClaim.getExpenseList();
		this.expenses = new ArrayList<Expense>(expenseCollection);
		this.expenseAdapter = new ExpenseAdapter(getActivity(), R.layout.expense_adapter, expenses);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.expense_list_layout, null);
		return v;
	}

	@Override
	public void onStart() {
		super.onStart();
		addListeners();
		manageExpenseAdapter();
	}
	

	@Override
	public void onResume() {
		super.onResume();
		ClaimListSingleton.getClaimList().notifyListeners();
	}

	private void addListeners(){
		final Claim claim = this.editClaim;
		ClaimListSingleton.getClaimList().addListener(new Listener(){
			@Override
			public void update(){
				if(claim != null){
					expenses.clear();
					ArrayList<Expense> newExpenses = claim.getExpenseList();
					expenses.addAll(newExpenses);
					expenseAdapter.notifyDataSetChanged();
				}
			}
		});
		
		ClaimListSingleton.getClaimList().addListener(new Listener(){
			@Override
			public void update() {
				View v = getView();
				if(v!= null){
					TextView currencyView = (TextView) v.findViewById(R.id.currencyText);
					HashMap<String,Float> currencies = claim.getCurrencies();
					String costListing = "Total Cost:";
					for(String key: currencies.keySet()){
						costListing += "\n" + Float.toString(currencies.get(key)) + " "+ key;
					}
					currencyView.setText(costListing);
		
				}
			}
		});
	}
	
	private void manageExpenseAdapter(){
		final ListView expenseView = (ListView) getView().findViewById(R.id.expenseList);
		final Claim claim = this.editClaim;
		
		expenseView.setAdapter(expenseAdapter);
		
		expenseView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id){
				Toast.makeText(getActivity(), "EDIT ME", Toast.LENGTH_SHORT).show();
			}
		});
		
		expenseView.setOnItemLongClickListener(new OnItemLongClickListener(){
			//Based on http://www.mkyong.com/android/android-alert-dialog-example/ Jan 28 2015
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
				if(claim != null){
					final Expense expense = claim.getExpenseList().get(position);
					ad.setMessage("Delete : "+ expense.getCategory() + " ?");
					ad.setCancelable(true);
					ad.setPositiveButton("DO IT", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							claim.removeExpense(expense);
							updateCurrencyText();
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
				return true;
			}
		});
	}
	
	private void updateCurrencyText(){
		TextView currencyView = (TextView) getView().findViewById(R.id.currencyText);
		HashMap<String,Float> currencies = editClaim.getCurrencies();
		String costListing = "Total Cost:";
		for(String key: currencies.keySet()){
			costListing += "\n" + Float.toString(currencies.get(key)) + " "+ key;
		}
		currencyView.setText(costListing);
	}
}
