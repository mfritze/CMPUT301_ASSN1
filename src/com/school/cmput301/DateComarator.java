package com.school.cmput301;

import java.util.Comparator;

public class DateComarator implements Comparator<Claim>{

	@Override
	public int compare(Claim lhs, Claim rhs) {
		return lhs.getStartTime().compareTo(rhs.getStartTime());
	}

}
