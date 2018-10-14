package com.expense.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.expense.controller.GroupController;
import com.expense.model.Member;
import com.expense.model.Settlement;
import com.expense.utils.MemberComparator;
import com.expense.utils.SettlementConstants;

@Component
public class SettlementService {
	
	Logger logger = LoggerFactory.getLogger(GroupController.class);

	public List<Settlement> calculateSetlements(List<Member> members,String groupId)
	{
		int numberOfSettlementDone = 0;
		int totalNumerofMembers = members.size();
		List<Settlement> settlements = new ArrayList<>();
		while (numberOfSettlementDone < totalNumerofMembers)
		{
			Collections.sort(members,new MemberComparator());
			Member receiver = members.get(totalNumerofMembers-1);
			Member payer = members.get(0);
			int amount = 0;
			int howmuchTopay = -1*payer.getBalance();
			if (howmuchTopay>receiver.getBalance())
				amount = receiver.getBalance();
			else
				amount = payer.getBalance();
			
			if (amount <0)
			  amount=-1*amount;
			payer.setBalance((payer.getBalance()+(amount)));
			receiver.setBalance(receiver.getBalance()-(amount));
			Settlement settlement = new Settlement();
			settlement.setReceiverId(receiver.getMemberName());
			settlement.setPayerId(payer.getMemberName());
			settlement.setAmountToBePaid(amount);
			settlement.setType(SettlementConstants.DEBT);
			settlement.setGroupId(groupId);
			settlement.setCreateDate(new Date());
			settlements.add(settlement);
			
			
			if (payer.getBalance()==0)
				numberOfSettlementDone++;
			if(receiver.getBalance()==0)
				numberOfSettlementDone++;
		}
		settlements.forEach(s->logger.info("Settlements : from {}, to {}, amount {}",s.getPayerId(),s.getReceiverId(),s.getAmountToBePaid()));
		return settlements;
		
	}

	
}