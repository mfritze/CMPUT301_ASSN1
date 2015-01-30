package com.school.cmput301.Models;

import java.util.Date;

import com.school.cmput301.Controllers.AdapterCompatible;


//Currency infor http://stackoverflow.com/questions/3888991/currency-code-to-currency-symbol-mapping
// jan 17 2015
public class Expense implements AdapterCompatible{
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

	@Override
	public String getTitle() {
		return this.category;
	}

	@Override
	public String getDateText() {
		return this.date.getDay() +"/"+ this.date.getMonth() + "/" + (this.date.getYear() + 1901);
	}

	@Override
	public String getCostText() {
		float value = this.cost.getPrice();
		return Float.toString(value) + " " + this.cost.getCurrency();
	}
	
	
}
