package com.school.cmput301;

import java.util.ArrayList;

public class ClaimList {
	private ArrayList<Claim> claimList;
	private ArrayList<Listener> listeners;
	
	private final int EDITABLE = 0, SENT = 1, CLOSED = 2;
	
	public ClaimList(){
		claimList = new ArrayList<Claim>();
		listeners = new ArrayList<Listener>();
	}
	
	public void notifyListeners(){
		for(Listener l : this.listeners){
			l.update();
		}
	}
	
	public void addListener(Listener l){
		this.listeners.add(l);
	}
	
	public void addClaim(Claim c){
		claimList.add(c);
	}
	
	public void removeClaim(Claim c){
		claimList.remove(c);
	}
	
	public ArrayList<Claim> getClaims(){
		return this.claimList;
	}
	
	public ArrayList<Listener> getListeners(){
		return this.listeners;
	}
	
	public void submitClaim(Claim c){
		c.setStatus(SENT);
	}
	
	public void closeClaim(Claim c){
		c.setStatus(CLOSED);
	}
	
	public void editClaim(Claim c){
		c.setStatus(EDITABLE);
	}
}
