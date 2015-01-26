package com.school.cmput301;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.renderscript.Sampler.Value;

public class Claim implements AdapterCompatible {
	private String name, category, description;
	private ClaimStatus status;
	private Date date;
	private Date startDate, endDate; //TODO
	private ArrayList<Expense> expenseList;
	
	public Claim(String name, String category,String description, Date date){
		this.name = name;
		this.category = category;
		this.description = description;
		this.date = date;
		this.status = new ClaimStatus();
		this.expenseList = new ArrayList<Expense>();
	}
	
	public HashMap<String, Float> getCurrencies(){
		HashMap<String, Float> currencies = new HashMap<String, Float>();
		String label;
		float value, total;
		
		for(Expense exp: expenseList){
			label = exp.getCost().getCurrency();
			value  = exp.getCost().getPrice();
			if(currencies.containsKey(label)){
				total = currencies.get(label);
				total += value;
				currencies.put(label,total);
			}else{
				currencies.put(label,value);
			}
		}
		
		return currencies;
	}
	
	public ArrayList<Expense> getExpenseList() {
		if(expenseList == null){
			expenseList = new ArrayList<Expense>();
		}
		return expenseList;
	}

	public void setExpenseList(ArrayList<Expense> expenseList) {
		this.expenseList = expenseList;
	}

	public void addExpense(Expense e){
		expenseList.add(e);
	}
	
	public void removeExpense(Expense e){
		expenseList.remove(e);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ClaimStatus getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status.setStatus(status);
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String getTitle() {
		return this.name;
	}

	@Override
	public String getDateText() {
		String start = this.date.getDay() +"/"+ this.date.getMonth() + "/" + this.date.getYear();
		String end = this.date.getDay() +"/"+ this.date.getMonth() + "/" + this.date.getYear(); 
		return start + " to " + end;
	}

	@Override
	public String getCostText() {
		//float value = this.cost.getPrice();
		//return Float.toString(value) + " " + this.cost.getCurrency();
		return "TEMPORARY COST";
	}
	
	
}
