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

public class ClaimStatus {
	// 0 = editable, 1 = sent, 2 = closed
	public static final int INPROGRESS = 0, SENT = 1, APPROVED = 2;
	private int status;
	
	public ClaimStatus(int status){
		if((status < 3) && (status >= 0)){
			this.status = status;
		}else{
			this.status = INPROGRESS;
		}
	}
	public ClaimStatus(){
		this.status = INPROGRESS;
	}
	
	public boolean isEditable(){
		if(this.status < APPROVED){
			return true;
		}
		return false;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	

}
