package com.school.cmput301.Controllers;

import java.util.Comparator;

import com.school.cmput301.Models.Claim;

public class DateComparator implements Comparator<Claim>{

	@Override
	public int compare(Claim lhs, Claim rhs) {
		return lhs.getStartTime().compareTo(rhs.getStartTime());
	}

}
