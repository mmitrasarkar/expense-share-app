package com.expense.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.expense.model.Member;
import com.expense.model.Settlement;
import com.expense.model.Spend;
import com.expense.repository.GroupRepository;
import com.expense.repository.SettlementRepository;
import com.expense.repository.SpendRepository;
import com.expense.service.SettlementService;


@RestController
public class SpendController {
	
	Logger logger = LoggerFactory.getLogger(SpendController.class);
	@Autowired
	SpendRepository spendRepo;
	
	@Autowired
	SettlementRepository settlementRepo;
	
	@Autowired
	SettlementService settlementService;
	
	@Autowired
	GroupRepository groupRepo;
	
	@PostMapping("/spends/{groupId}")
	public void addSpends(@RequestBody List<Spend> spends,@PathVariable String groupId) {
		logger.info("Inside Spend create service {}", spends);
		int totalSpend = 0;
		Map<String, Spend> spendMap = new HashMap<>();
		for (Spend spend : spends) {
			logger.info("Spender name {} , amount {}", spend.getSpenderName(), spend.getSpendAmout());
			spend.setGroupId(groupId);
			spendMap.put(spend.getSpenderName(), spend);
			totalSpend = totalSpend + spend.getSpendAmout();
		}
		List<Member> members = groupRepo.findById(groupId).get().getMembers();
		int perPersonShare = totalSpend / members.size();
		logger.info("Total Spend {}", totalSpend);
		logger.info("Per person share {}", perPersonShare);
		members.forEach(member -> {
			Spend spend = spendMap.get(member.getMemberName());
			if (spend != null) {
				int balance = 0;
				balance = spend.getSpendAmout() - perPersonShare;
				member.setBalance(balance);
			} else
				member.setBalance(perPersonShare * -1);

		});
		List<Settlement> settlements = settlementService.calculateSetlements(members,groupId);
		
		/**
		 * TO-DO
		 * Mongodb multi collection transaction is supported in 4.0 server and lib but I am not able to install it for now
		 * Need transaction handling here
		 */
		
		spendRepo.insert(spends);
		settlementRepo.insert(settlements);
	}
	
	@DeleteMapping("/spends/{groupId}")
	public void deleteSpends(@PathVariable String groupId) {
		logger.info("Inside Spend delete service {}", groupId);
		spendRepo.deleteSpends(groupId);
	}

}
