package com.school.cmput301;

import java.util.Currency;
import java.util.Date;


//Currency infor http://stackoverflow.com/questions/3888991/currency-code-to-currency-symbol-mapping
// jan 17 2015
public class Expense {
	private Date date;
	private String category, description;
	private ExpenseCost cost;
	
	public Expense(Date date, String category, String description,
			float price, String currency){
		this.date = date;
		this.category = category;
		this.description = description;
		this.cost = new ExpenseCost(price, currency);
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public ExpenseCost getCost() {
		return cost;
	}
	public void setCost(ExpenseCost cost) {
		this.cost = cost;
	}
	
	
}
