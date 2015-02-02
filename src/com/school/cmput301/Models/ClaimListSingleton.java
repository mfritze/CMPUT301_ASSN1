package com.school.cmput301.Models;

import java.util.Collections;

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
	
	static public void sortClaimList(){
		Collections.sort(getClaimList().getClaimArrayList(), new ClaimComparator());
		for(Claim claim: getClaimList().getClaims()){
			Collections.sort(claim.getExpenseList(), new ExpenseComparator());
		}
	}
	


}
