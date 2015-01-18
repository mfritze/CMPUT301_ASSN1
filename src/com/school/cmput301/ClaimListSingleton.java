package com.school.cmput301;

import java.util.ArrayList;

public class ClaimListSingleton {
	private static ArrayList<Claim> claimList;
	
	static public ArrayList<Claim> getClaimList(){
		if(claimList == null){
			claimList = new ArrayList<Claim>();
		}
		return claimList;
	}

}
