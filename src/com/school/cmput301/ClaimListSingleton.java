package com.school.cmput301;

import java.util.ArrayList;

public class ClaimListSingleton {
	private static ClaimList claimList; 
	
	static public ClaimList getClaimList(){
		if(claimList == null){
			claimList = new ClaimList();
		}
		return claimList;
	}

}
