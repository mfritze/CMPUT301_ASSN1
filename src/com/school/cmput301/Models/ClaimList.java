package com.school.cmput301.Models;

import java.util.ArrayList;
import java.util.Collection;

import com.school.cmput301.Controllers.Listener;

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
	
	public int getIndex(Claim c){
		if(!this.claimList.contains(c)){
			return -1;
		}
		return this.claimList.indexOf(c);
	}
	
	public void addListener(Listener l){
		this.listeners.add(l);
	}
	
	public void removeListener(int i){
		this.listeners.remove(i);
	}
	
	public void addClaim(Claim c){
		claimList.add(c);
	}
	
	public void removeClaim(Claim c){
		claimList.remove(c);
	}
	
	public ArrayList<Claim> getClaimArrayList(){
		return this.claimList;
	}
	
	public Collection<Claim> getClaims(){
		return this.claimList;
	}
	
	public ArrayList<Listener> getListeners(){
		return this.listeners;
	}
	
	public void submitClaim(Claim c){
		c.setStatus(SENT);
	}
	
	public void approveClaim(Claim c){
		c.setStatus(CLOSED);
	}
	
	public void editClaim(Claim c){
		c.setStatus(EDITABLE);
	}
	
	public Claim getClaimAtIndex(int i){
		if(this.claimList.isEmpty() || (this.claimList.size() <= i) || (i < 0)){
			return null;
		}
		return this.claimList.get(i);
	}
	
	public void removeClaimAtIndex(int i ){
		this.claimList.remove(i);
	}
}
