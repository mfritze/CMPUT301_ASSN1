package com.school.cmput301.Models;

import java.util.ArrayList;
import java.util.Collection;

import com.school.cmput301.Controllers.Listener;

public class ClaimList {
	private ArrayList<Claim> claimList;
	private ArrayList<Listener> listeners;
	
	public ClaimList(){
		claimList = new ArrayList<Claim>();
		listeners = new ArrayList<Listener>();
	}
	
	public ClaimList(ArrayList<Claim> claims){
		claimList = claims;
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
	
	public void addClaim(Claim c){
		claimList.add(c);
	}
	
//	public void removeClaim(Claim c){
//		claimList.remove(c);
//	}
	
	public ArrayList<Claim> getClaimArrayList(){
		return this.claimList;
	}
	
	public Collection<Claim> getClaims(){
		return this.claimList;
	}
	
	public void setClaimArrayList(ArrayList<Claim> claims){
		this.claimList = claims;
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

	public void replaceClaim(int claimIndex, Claim newClaim) {
		if(!this.claimList.isEmpty() || (this.claimList.size() > claimIndex) || (claimIndex >= 0)){
			this.claimList.set(claimIndex, newClaim);
		}
	}
}
