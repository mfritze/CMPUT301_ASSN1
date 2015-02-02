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

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class ExpenseCost {
	/*
	 * Models the cost of an expense
	 * by storing both the currency
	 * and the value of the expense,
	 * in the respective currency
	 */
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
