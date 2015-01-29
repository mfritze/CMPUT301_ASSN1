package com.school.cmput301;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Claim implements AdapterCompatible {
	private String name, category, description;
	private ClaimStatus status;
	private String startDate, endDate; //TODO
	private Date startTime;
	private ArrayList<Expense> expenseList;
	
	public Claim(String name, String category,String description, String startDate, String endDate, Date startTime){
		this.name = name;
		this.category = category;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
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
	public int getStatus() {
		return status.getStatus();
	}
	public void setStatus(int status) {
		this.status.setStatus(status);
	}
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String getTitle() {
		return this.name;
	}

	@Override
	public String getDateText() {
		return startDate + " - " + endDate;
	}

	@Override
	public String getCostText() {
		HashMap<String,Float> currencies = this.getCurrencies();
		String costListing = "";
		for(String key: currencies.keySet()){
			costListing += Float.toString(currencies.get(key)) + " "+ key + "\n";
		}
		if(costListing.length() == 0){
			return "";
		}
		return costListing.substring(0, costListing.length() - 1);
	}
	
	
}
