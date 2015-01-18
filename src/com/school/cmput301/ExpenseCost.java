package com.school.cmput301;

import java.util.Currency;

public class ExpenseCost {
	private float price;
	private Currency currency;
	
	public ExpenseCost(float price, Currency currency){
		this.price = price;
		this.currency = currency;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	

}
