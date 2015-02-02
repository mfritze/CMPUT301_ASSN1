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
