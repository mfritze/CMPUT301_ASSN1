package com.school.cmput301.Controllers;

import java.util.Comparator;

import com.school.cmput301.Models.Expense;

public class ExpenseComparator implements Comparator<Expense>{

	@Override
	public int compare(Expense lhs, Expense rhs) {
		return lhs.getDate().compareTo(rhs.getDate());
	}

}
