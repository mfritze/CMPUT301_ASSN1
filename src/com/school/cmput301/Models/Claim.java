package com.school.cmput301.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.school.cmput301.Controllers.AdapterCompatible;

public class Claim implements AdapterCompatible {
	private String name, category, description;
	private ClaimStatus status;
	private String startDate, endDate; //TODO
	private Date startTime;
	private ArrayList<Expense> expenseList;
	
	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

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
	
	public void replaceExpense(int expenseIndex, Expense newExpense){
		if(newExpense != null && expenseIndex >= 0 && expenseIndex < this.expenseList.size()){
			this.expenseList.set(expenseIndex, newExpense);
		}else{
			throw new IndexOutOfBoundsException("Improper expense index!");
		}
	}
	
	public ArrayList<Expense> getExpenseList() {
		if(expenseList == null){
			expenseList = new ArrayList<Expense>();
		}
		return expenseList;
	}
	
	public boolean isEditable(){
		return this.status.isEditable();
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
	
	public String getEmailText(){
		String emailText = "";
		emailText += this.name + " \n(" + this.startDate + "-" + this.endDate + ")\n";
		emailText += this.category + "\n";
		int start, descLen = this.description.length(), end;
		int numLines = descLen/80;

		for(int i = 0; i < numLines; i++){
			//Add description with at most 80 chars per line
			start = i*80;
			end = Math.min((i+1)*80, descLen);
			
			emailText += this.getDescription().substring(start, end);
			emailText += "\n";
		}
		
		for(int i= 0; i < 80; i++){
			emailText += "-";
		}
		emailText+="\n";
		
		for(Expense e: this.expenseList){
			emailText += "[" + e.getCategory() + "]" + "\n";
			emailText += e.getDateString() + "\n";
			emailText += e.getCostText() + "\n";
			descLen = e.getDescription().length();
			
			numLines = descLen/80;

			for(int i = 0; i < numLines; i++){
				start = i*80;
				end = Math.min((i+1)*80, descLen);
				
				emailText += e.getDescription().substring(start, end);
				emailText += "\n";
			}
		}
		return emailText;
	}
	
	
}
