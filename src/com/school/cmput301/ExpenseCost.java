package com.school.cmput301;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class ExpenseCost {
	private float price;
	private String currency;
	
	public ExpenseCost(float price, String currency){
		this.price = price;
		this.currency = currency;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public static ArrayList<String> getCurrencyList(){
		// based on http://kodejava.org/how-do-i-get-all-available-currency-codes/
		// Jan 22 2015
		ArrayList<String> currencies = new ArrayList<String>();
		Locale[] locales = Locale.getAvailableLocales();
		for(Locale loc : locales){
			try{
				currencies.add(Currency.getInstance(loc).getCurrencyCode());
			} catch(Exception e){
				//unsupported locale
				e.printStackTrace();
			}
		}
		
		return currencies;
	}

}
