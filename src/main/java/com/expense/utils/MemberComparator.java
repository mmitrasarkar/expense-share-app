package com.expense.utils;

import java.util.Comparator;

import com.expense.model.Member;

public class MemberComparator implements Comparator<Member>{

	@Override
	public int compare(Member o1, Member o2) {
		if( o1.getBalance()>o2.getBalance())
			return 1;
		else if (o1.getBalance()<o2.getBalance())
			return -1;
		return 0;
	}

}
