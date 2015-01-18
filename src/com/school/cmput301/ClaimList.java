package com.school.cmput301;

import java.util.ArrayList;

public class ClaimList {
	// A wrapper class for an array list of claims, with the added functionality of:
	// Changing status
	private ArrayList<Claim> claimList;
	private final int EDITABLE = 0, SENT = 1, CLOSED = 2;
	
	public ClaimList(){
		claimList = new ArrayList<Claim>();
	}
	
	public void addClaim(Claim c){
		claimList.add(c);
	}
	
	public void removeClaim(Claim c){
		claimList.remove(c);
	}
	
	public ArrayList<Claim> getClaimList(){
		return this.claimList;
	}
	
	public void submitClaim(Claim c){
		c.setStatus(SENT);
	}
	
	public void closeClaim(Claim c){
		c.setStatus(CLOSED);
	}
}
