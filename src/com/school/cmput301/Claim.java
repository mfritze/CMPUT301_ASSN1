package com.school.cmput301;

import java.util.ArrayList;
import java.util.Date;

public class Claim {
	private String name, category, description;
	private ClaimStatus status;
	private Date date;
	private ArrayList<Expense> expenseList;
	
	public Claim(String name, String category,String description, Date date){
		this.name = name;
		this.category = category;
		this.description = description;
		this.date = date;
		this.status = new ClaimStatus();
		this.expenseList = new ArrayList<Expense>();
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
	
	
}
