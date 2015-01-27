package com.school.cmput301;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Claim implements AdapterCompatible {
	private String name, category, description;
	private ClaimStatus status;
	private Date startDate, endDate; //TODO
	private ArrayList<Expense> expenseList;
	
	public Claim(String name, String category,String description, Date startDate, Date endDate){
		this.name = name;
		this.category = category;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
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
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String getTitle() {
		return this.name;
	}

	@Override
	public String getDateText() {
		String start = this.startDate.getDay() +"/"+ this.startDate.getMonth() + "/" + (this.startDate.getYear()+ 1901);
		String end = this.endDate.getDay() +"/"+ this.endDate.getMonth() + "/" + (this.endDate.getYear() + 1901); 
		return start + " - " + end;
	}

	@Override
	public String getCostText() {
		String costText = "";
		HashMap<String, Float> currencies = ClaimListSingleton.getCurrencies();
		
		for(String key: currencies.keySet()){
			costText += Float.toString(currencies.get(key)) + " "+ key + "\n";
		}
		return costText;
	}
	
	
}
