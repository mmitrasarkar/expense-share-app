package com.expense.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.expense.model.Member;
import com.expense.model.Settlement;
import com.expense.model.Spend;
import com.expense.model.SpendSummary;
import com.expense.repository.GroupRepository;
import com.expense.repository.SettlementRepository;
import com.expense.repository.SpendRepository;
import com.expense.service.SettlementService;

/**
 * Spend related services
 * @author USER
 *
 */

@RestController
public class SpendController {
	
	Logger logger = LoggerFactory.getLogger(SpendController.class);
	@Autowired
	SpendRepository spendRepo;
	
	
	@Autowired
	SettlementService settlementService;
	
	@Autowired
	GroupRepository groupRepo;
	
	@Autowired
	SettlementRepository settlementRepo;
	
	/**
	 * This service will add all the spends done for a particular group 
	 * Group admin will only be allowed to add spend at one go
	 * Once the spend is added to the system automatically the settlement will be calculated and inserted
	 * The settlement calculation can be done asynchronously without blocking the main thread
	 * @param spends
	 * @param groupId
	 */
	@PostMapping("/spends/{groupId}")
	public void addSpends(@RequestBody List<Spend> spends,@PathVariable String groupId) {
		logger.info("Inside Spend create service {}", spends);
		double totalSpend = 0.00;
		Map<String, Spend> spendMap = new HashMap<>();
		for (Spend spend : spends) {
			logger.info("Spender name {} , amount {}", spend.getSpenderName(), spend.getSpendAmout());
			spend.setGroupId(groupId);
			spendMap.put(spend.getSpenderName(), spend);
			totalSpend = totalSpend + spend.getSpendAmout();
		}
		List<Member> members = groupRepo.findById(groupId).get().getMembers();
		double perPersonShare = totalSpend / members.size();
		logger.info("Total Spend {}", totalSpend);
		logger.info("Per person share {}", perPersonShare);
		members.forEach(member -> {
			Spend spend = spendMap.get(member.getMemberName());
			if (spend != null) {
				double balance = 0;
				balance = spend.getSpendAmout() - perPersonShare;
				member.setBalance(balance);
			} else
				member.setBalance(perPersonShare * -1);

		});
		
		spendRepo.insert(spends);
		settlementService.calculateSetlements(members,groupId);
		
	}
	
	/**
	 * Get the details of spend by the group
	 * @param groupId
	 * @return
	 */
	@GetMapping("/spends/{groupId}")
	public List<Spend> getSpendsByGroup(@PathVariable String groupId)
	{
		
		return spendRepo.getAllSpends(groupId);
	}
	
	
	/**
	 * Delete the spends when the group is deleted
	 * Need some validation if the group is marked for deletion and all the settlements are closed then 
	 * spends can be purged
	 * @param groupId
	 */
	@DeleteMapping("/spends/{groupId}")
	public void deleteSpends(@PathVariable String groupId) {
		logger.info("Inside Spend delete service {}", groupId);
		spendRepo.deleteSpends(groupId);
	}
	
	@GetMapping("/spends/{groupId}/summary")
	public SpendSummary getSpendSummary(@PathVariable String groupId)
	{
		double totalSpend = spendRepo.totalSpendByGroup(groupId);
		List<Member> members = groupRepo.findById(groupId).get().getMembers();
		double perPersonCost =(double) totalSpend/members.size();
		double totalPendingSettlement = settlementRepo.totalPendingSettlementByGroup(groupId);
		SpendSummary summary = new SpendSummary();
		summary.setGroupId(groupId);
		summary.setTotalCost(totalSpend);
		summary.setPerPsersonCost(perPersonCost);
		summary.setPendingSettlement(totalPendingSettlement);
		return summary;
	}

}
