package com.school.cmput301.Fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.school.cmput301.R;
import com.school.cmput301.Activities.ExpenseManagerActivity;
import com.school.cmput301.Models.Claim;
import com.school.cmput301.Models.ClaimListSingleton;
import com.school.cmput301.Models.Expense;

public class ExpenseManagerFragment extends Fragment{
	private Claim editClaim;
	private int claimIndex;
	private Expense editExpense;
	private boolean isEditing;
	private EditText categoryView;
	private EditText descView;
	private EditText costView;
	private DatePicker dateView;
	private Spinner spinView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isEditing = false;
		editClaim = ((ExpenseManagerActivity) getActivity()).getWorkingClaim();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.expense_manager_layout, null);
		return v;
	}

	@Override
	public void onStart() {
		super.onStart();
		this.categoryView = (EditText) getView().findViewById(R.id.expenseName);
		this.descView = (EditText) getView().findViewById(R.id.expenseDescription);
		this.costView = (EditText) getView().findViewById(R.id.expenseCost);
		this.dateView = (DatePicker) getView().findViewById(R.id.expenseDate);
		this.spinView = (Spinner) getView().findViewById(R.id.currencySelector);		
		String[] spinnerItems = {"CAD", "USD", "EUR", "GBP"};
		ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, spinnerItems);
		spinView.setAdapter(spinAdapter);
		if(isEditing){
			populateFields();
		}else{
			clearFields();
		}
	}

	public void setMode(boolean isEditing){
		this.isEditing = isEditing;
	}

	public void setClaimIndex(int i){
		this.claimIndex = i;
	}
	
	public void createExpense(){
		String category, description, dateString, currencyCode;
		float cost = -1;
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		category = categoryView.getText().toString();
		description = descView.getText().toString();
		currencyCode = (String) spinView.getSelectedItem();
		
		try{ //http://stackoverflow.com/questions/15864910/convert-edittext-to-float-android-eclipse jan 21 2015
			cost = Float.valueOf(costView.getText().toString());
		}catch(NumberFormatException e){
			Toast.makeText(getActivity(), "Couldn't convert price", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		
		if((category.equals("")) || (description.equals("")) || (currencyCode.equals(""))){
			Toast.makeText(getActivity(), "Fill in all of the fields!", Toast.LENGTH_SHORT).show();
		}else{
			try {
				int month = dateView.getMonth(), day = dateView.getDayOfMonth(), year = dateView.getYear();
				dateString = String.format("%d/%d/%d", day,month, year);
				date = sdf.parse(dateString);
			} catch (ParseException e) {
				Toast.makeText(getActivity(), "Error reading date", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			} 	
			Expense exp = new Expense(date, category, description,cost, currencyCode);
			editClaim.addExpense(exp);
			ClaimListSingleton.getClaimList().notifyListeners();
			((ExpenseManagerActivity) getActivity()).startExpenseList();
		}
	}

	public void populateFields(){
		Toast.makeText(getActivity(), "POPULATE", Toast.LENGTH_SHORT).show();
	}

	private void clearFields() {
		this.categoryView.setText("");
		this.descView.setText("");
		this.costView.setText("");
	}
}
