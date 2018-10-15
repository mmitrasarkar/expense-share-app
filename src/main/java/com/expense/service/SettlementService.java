package com.expense.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.expense.controller.GroupController;
import com.expense.model.Member;
import com.expense.model.Settlement;
import com.expense.repository.SettlementRepository;
import com.expense.utils.MemberComparator;
import com.expense.utils.SettlementConstants;
/**
 * Settlement service
 * @author USER
 *
 */
@Component
public class SettlementService {
	
	Logger logger = LoggerFactory.getLogger(GroupController.class);
	

	@Autowired
	SettlementRepository settlementRepo;
	/**
	 * Once the spend is added this service will execute asynchronously to calculate the spend
	 * We can enhance it by SMS alert to the user so thay can come and check on the system 
	 * @param members
	 * @param groupId
	 * @return
	 */
	@Async
	public void calculateSetlements(List<Member> members,String groupId)
	{
		int numberOfSettlementDone = 0;
		int totalNumerofMembers = members.size();
		List<Settlement> settlements = new ArrayList<>();
		while (numberOfSettlementDone < totalNumerofMembers)
		{
			Collections.sort(members,new MemberComparator());
			Member receiver = members.get(totalNumerofMembers-1);
			Member payer = members.get(0);
			double amount = 0.00;
			double howmuchTopay = -1*payer.getBalance();
			if (howmuchTopay>receiver.getBalance())
				amount = receiver.getBalance();
			else
				amount = payer.getBalance();
			
			if (amount <0.00)
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
		settlementRepo.insert(settlements);
		
	}
	

	
}