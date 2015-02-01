package com.school.cmput301.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.school.cmput301.Controllers.ClaimComparator;
import com.school.cmput301.Controllers.ExpenseComparator;

public class ClaimListSingleton {
	private static ClaimList claimList; 
	
	static public ClaimList getClaimList(){
		if(claimList == null){
			claimList = new ClaimList();
		}
		return claimList;
	}
	
	static public HashMap<String,Float> getCurrencies(){
		HashMap<String, Float> currencies = new HashMap<String, Float>();
		ArrayList<Expense> expenseList;
		String label;
		float value, total;
		
		for(Claim claim: claimList.getClaimArrayList()){
			expenseList = claim.getExpenseList();
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
		}
		return currencies;
	}
	
	static public void sortClaimList(){
		Collections.sort(getClaimList().getClaimArrayList(), new ClaimComparator());
		for(Claim claim: getClaimList().getClaims()){
			Collections.sort(claim.getExpenseList(), new ExpenseComparator());
		}
	}
	


}
