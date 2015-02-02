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

package com.school.cmput301.Controllers;

import java.util.Comparator;

import com.school.cmput301.Models.Expense;

public class ExpenseComparator implements Comparator<Expense>{
	/* A method for ordering expenses */

	@Override
	public int compare(Expense lhs, Expense rhs) {
		return lhs.getDate().compareTo(rhs.getDate());
	}

}
