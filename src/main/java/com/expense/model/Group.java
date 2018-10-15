package com.expense.model;

import java.util.List;

import org.springframework.data.annotation.Id;
/**
 * Group Entity
 * @author USER
 *
 */
public class Group {
	
	@Id
	private String groupId;
	private String groupName;
	private String groupDescription;
	private List<Member> members;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}
	
	
	

	
}
