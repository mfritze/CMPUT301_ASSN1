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
import java.util.Collection;

import com.school.cmput301.Controllers.Listener;

public class ClaimList {
	/*
	 * ClaimList is a wrapper class
	 * for an ArrayList<Claim>, with the added
	 * functionality of better accessing to specific
	 * claims and provides a list of listeners to 
	 * keep the date up to date between activities
	 * and fragments with methods to interface 
	 * with it.
	 */
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
