package com.school.cmput301;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
			view = inflater.inflate(R.layout.claim_adapter, null);
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