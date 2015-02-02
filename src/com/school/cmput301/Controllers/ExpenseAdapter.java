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
package com.school.cmput301.Controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.school.cmput301.R;
import com.school.cmput301.Models.Expense;

public class ExpenseAdapter extends ArrayAdapter{
	//based on http://stackoverflow.com/questions/8166497/custom-adapter-for-list-view Jan 24th 2015
	ArrayList<Expense> expenses;
	public ExpenseAdapter(Context context, int textViewResourceId, List<Expense> expenses) {
		super(context, textViewResourceId,expenses);
		this.expenses = (ArrayList<Expense>) expenses;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		View view = convertView;
		
		if(view == null){
			LayoutInflater inflater;
			inflater = LayoutInflater.from(getContext());
			view = inflater.inflate(R.layout.expense_adapter, null);
		}
		
		Expense expense = this.expenses.get(position);
		
		if(expense != null){
			TextView titleView = (TextView) view.findViewById(R.id.titleText);
			//TextView secondView = (TextView) view.findViewById(R.id.secondaryText);
			TextView dateView = (TextView) view.findViewById(R.id.dateText);
			TextView costView = (TextView) view.findViewById(R.id.costText);
			
			titleView.setText(expense.getTitle());
			//secondView.setText(claim.getSecond());
			dateView.setText(expense.getDateText());
			costView.setText(expense.getCostText());
		}
		
		return view;
	}
}