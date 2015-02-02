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
package com.school.cmput301.Models;

import java.util.Date;

import com.school.cmput301.Controllers.AdapterCompatible;


//Currency infor http://stackoverflow.com/questions/3888991/currency-code-to-currency-symbol-mapping
// jan 17 2015
public class Expense implements AdapterCompatible{
	/*
	 * Expense models a claim's expense, and 
	 * holds descriptor fields, and most importantly,
	 * the cost of the expense.
	 */
	private Date date;
	private String category, description, dateString;
	private ExpenseCost cost;
	
	public Expense(Date date, String dateString, String category, String description,
			float price, String currency){
		this.dateString = dateString;
		this.date = date;
		this.category = category;
		this.description = description;
		this.cost = new ExpenseCost(price, currency);
	}
	
	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
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
	public String getCostText() {
		float value = this.cost.getPrice();
		return Float.toString(value) + " " + this.cost.getCurrency();
	}

	@Override
	public String getDateText() {
		return this.dateString;
	}
	
	public int getSpinPosition(){
		String currency = this.cost.getCurrency();
		if(!currency.equals(null)){
			if(currency.equals("CAD")){
				return 0;
			}else if(currency.equals("USD")){
				return 1;
			}else if(currency.equals("EUR")){
				return 2;
			}else if(currency.equals("GBP")){
				return 3;
			}
		}
		return -1;
	}
}
