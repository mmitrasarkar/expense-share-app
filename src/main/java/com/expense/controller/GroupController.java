package com.expense.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.expense.model.Group;
import com.expense.model.Member;
import com.expense.repository.GroupRepository;
/**
 * Services for managing a group
 * @author USER
 *
 */
@RestController
public class GroupController {
	Logger logger = LoggerFactory.getLogger(GroupController.class);

	@Autowired
	private GroupRepository groupRepository;
	
	/**
	 * Returns all groups 
	 * Super admin role feature
	 * @return
	 */
	@GetMapping("/groups")
	public List<Group> retrieveAllGroups() {
		logger.info("Inside Group service");
		return groupRepository.findAll();
	}
	/**
	 * Admin can see the details of a group
	 * @param groupId
	 * @return
	 */
	@GetMapping("/groups/{groupId}")
	public Group retrieveGroup(@PathVariable String groupId) {
		logger.info("Inside Group service");
		return groupRepository.findById(groupId).get();
	}

	/**
	 * One member can create a group then that person will be admin
	 * The group feature can be extended with multiple admin
	 * @param group
	 * @return
	 */
	@PostMapping("/groups")
	public ResponseEntity<Group> createGroup(@RequestBody Group group) {
		logger.info("Inside Group create service");
		group = groupRepository.save(group);
		return new ResponseEntity<Group>(group,HttpStatus.CREATED);

	}

	/**
	 * Service to add member to a group
	 * It is individual member add
	 * @param member
	 * @param groupId
	 * @return
	 */
	@PostMapping("/groups/{groupId}/member")
	public ResponseEntity<Group> addMember(@RequestBody Member member, @PathVariable String groupId) {
		logger.info("Inside Group create service");
		Group group = groupRepository.findById(groupId).get();
		List<Member> members = group.getMembers();
		if (members == null) {
			members = new ArrayList<>();
			group.setMembers(members);
		}
		members.add(member);
		group = groupRepository.save(group);
		return new ResponseEntity<Group>(group,HttpStatus.OK);

	}
	/**
	 * Bulk loading of members
	 * @param members
	 * @param groupId
	 * @return
	 */
	@PostMapping("/groups/{groupId}/members")
	public Group addMembers(@RequestBody List<Member> members, @PathVariable String groupId) {
		logger.info("Inside Group create service");
		Group group = groupRepository.findById(groupId).get();
		group.setMembers(members);

		return groupRepository.save(group);

	}
  
   /**
    * Bulk member delete 
    * Another service interface can be provided to delete single member
    * @param groupId
    * @return
    */
	@DeleteMapping("/groups/{groupId}/members")
	public Group deleteMembers(@PathVariable String groupId) {
		logger.info("Inside Spend create service {}", groupId);
		Group group = groupRepository.findById(groupId).get();
		group.setMembers(null);

		return groupRepository.save(group);
	}
	/**
	 * Delete a group once all the settlements are closed
	 * This also should be admin feature
	 * @param groupId
	 */
	@DeleteMapping("/groups/{groupId}")
	public void deleteGroup(@PathVariable String groupId) {
		logger.info("Inside Group Delete service {}", groupId);
		/**
		 * Before deleting group need to check any relational data like unsettled amount is there or not
		 * If it is there it should not allow to close the group
		 */
		groupRepository.deleteById(groupId);
	}


	

}
