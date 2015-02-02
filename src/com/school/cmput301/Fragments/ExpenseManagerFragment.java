/*
 * Copyright 2015 Matthew Fritze
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import com.school.cmput301.Models.ExpenseCost;

public class ExpenseManagerFragment extends Fragment{
	/* ExpenseManagerFragment works
	 * on editing and creating expenses. It's layout
	 * is the same between editing and creating,
	 * but the fields will be filled when you edit.
	 */
	private Claim editClaim;
	private int expenseIndex;
	private Expense editExpense = null;
	private boolean isEditing;
	private EditText categoryView;
	private EditText descView;
	private EditText costView;
	private DatePicker dateView;
	private Spinner spinView;
	private Button submitButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		this.submitButton = (Button) getView().findViewById(R.id.submitExpense);
		String[] spinnerItems = {"CAD", "USD", "EUR", "GBP"};
		ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, spinnerItems);
		spinView.setAdapter(spinAdapter);
		if(isEditing){
			editExpense = editClaim.getExpenseList().get(expenseIndex);
			populateFields();
		}else{
			clearFields();
		}
	}

	public void setMode(boolean isEditing){
		this.isEditing = isEditing;
	}

	public void setClaimIndex(int i){
		this.expenseIndex = i;
	}
	
	public void createExpense(){
		String category, description, dateString = "", currencyCode;
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
				int month = dateView.getMonth() + 1, day = dateView.getDayOfMonth(), year = dateView.getYear();
				dateString = String.format("%d/%d/%d", day,month, year);
				date = sdf.parse(dateString);
			} catch (ParseException e) {
				Toast.makeText(getActivity(), "Error reading date", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			} 	
			Expense exp = new Expense(date, dateString, category, description,cost, currencyCode);
			editClaim.addExpense(exp);
			ClaimListSingleton.getClaimList().notifyListeners();
			((ExpenseManagerActivity) getActivity()).startExpenseList();
		}
	}

	public void populateFields(){
		if(editExpense != null){
			final Expense oldExpense = this.editExpense;
			final String originalStartDateValue = String.format("%d/%d/%d", 
					dateView.getDayOfMonth(), dateView.getMonth() + 1,dateView.getYear()),
					CATEGORY = editExpense.getCategory(),
					DESCRIPTION = editExpense.getDescription(),
					CURRENCY = editExpense.getCost().getCurrency();
			final float COST = editExpense.getCost().getPrice();
			int spinSelection = editExpense.getSpinPosition();

			this.categoryView.setText(CATEGORY);
			this.descView.setText(DESCRIPTION);
			this.costView.setText(Float.toString(COST));
			this.spinView.setSelection(spinSelection);
			
			submitButton.setOnClickListener(new View.OnClickListener() {							
				@Override
				public void onClick(View v) {
					int month = dateView.getMonth() + 1, day = dateView.getDayOfMonth(), year = dateView.getYear();
					String startDateString, category, description, currencyCode;
					float cost = - 1;
					Expense newExpense = oldExpense;
					Date startDate;
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					
					startDateString = String.format("%d/%d/%d", day,month, year);
					description = descView.getText().toString();
					category = categoryView.getText().toString();
					currencyCode = (String) spinView.getSelectedItem();
					try{ 
						cost = Float.valueOf(costView.getText().toString());
					}catch(NumberFormatException e){
						Toast.makeText(getActivity(), "Couldn't convert price", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}

					if(!category.equals(CATEGORY)){
						newExpense.setCategory(category);
					}
					if(!description.equals(DESCRIPTION)){
						newExpense.setDescription(description);
					}
					
					if((cost != -1 && cost != COST) || (!currencyCode.equals(CURRENCY))){
						ExpenseCost newCost = new ExpenseCost(cost, currencyCode);
						newExpense.setCost(newCost);
					}
					
					if(!startDateString.equals(originalStartDateValue)){
						newExpense.setDateString(startDateString);
						try {
							startDate = dateFormat.parse(startDateString);
							newExpense.setDate(startDate);
						} catch (ParseException e) {
							Toast.makeText(getActivity(),"Error reading date!",Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}
					}
					
					int claimIndex = ((ExpenseManagerActivity) getActivity()).getClaimIndex();
					ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex).replaceExpense(expenseIndex, newExpense);
					((ExpenseManagerActivity) getActivity()).startExpenseList();

				}
			});
		}else{
			Toast.makeText(getActivity(), "Empty Expense!", Toast.LENGTH_SHORT).show();
		}
	}

	private void clearFields() {
		this.categoryView.setText("");
		this.descView.setText("");
		this.costView.setText("");
	}
}
