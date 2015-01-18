package com.school.cmput301;

public class ClaimListSingleton {
	private static ClaimList claimList;
	
	static public ClaimList getStudentList(){
		if(claimList == null){
			claimList = new ClaimList();
		}
		return claimList;
	}

}
