package com.expense.test.controller;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.expense.ExpenseServicesApplication;
import com.expense.model.Group;
import com.expense.model.Member;
import com.expense.repository.GroupRepository;
import com.expense.repository.SpendRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExpenseServicesApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class SpendControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	GroupRepository groupRepo;
	
	@Autowired
	SpendRepository spendRepository;
	
	@Autowired
	SpendRepository settlementRepo;

	@Test
	public void createSpend() throws Exception {
		String members = "[{\"memberId\": \"p10\",\"memberName\": \"p10\",\"email\": \"p10@gmail.com\"},{\"memberId\": \"p2\",\"memberName\": \"p2\",\"email\": \"p2@gmail.com\"},{\"memberId\": \"p3\",\"memberName\": \"p3\",\"email\": \"p3@gmail.com\"},{\"memberId\": \"p1\",\"memberName\": \"p1\",\"email\": \"p1@gmail.com\"},{\"memberId\": \"p12\",\"memberName\": \"p12\",\"email\": \"p12@gmail.com\"},{\"memberId\": \"p4\",\"memberName\": \"p4\",\"email\": \"p4@gmail.com\"},{\"memberId\": \"p5\",\"memberName\": \"p5\",\"email\": \"p5@gmail.com\"},{\"memberId\": \"p6\",\"memberName\": \"p6\",\"email\": \"p6@gmail.com\"},{\"memberId\": \"p7\",\"memberName\": \"p7\",\"email\": \"p7@gmail.com\"},{\"memberId\": \"p8\",\"memberName\": \"p8\",\"email\": \"p8@gmail.com\"},{\"memberId\": \"p9\",\"memberName\": \"p9\",\"email\": \"p9@gmail.com\"}]";
		String spends = "[{\"spenderName\":\"p1\",\"spendAmout\":10000,\"description\":\"Hotel Cost\"},{\"spenderName\":\"p2\",\"spendAmout\":8000,\"description\":\"Lunch\"},{\"spenderName\":\"p3\",\"spendAmout\":2000,\"description\":\"breakfast\"}{\"spenderName\":\"p4\",\"spendAmout\":1000,\"description\":\"breakfast\"}]";
		String settlements = "[{ \"receiverId\" : \"p1\", \"payerId\": \"p10\", \"amountToBePaid\": 2000, \"amountPaid\": 0, \"type\": \"debt\" },{ \"receiverId\" : \"p1\", \"payerId\" : \"p12\", \"amountToBePaid\" : 2000, \"amountPaid\" : 0,  \"type\" : \"debt\"},{ \"receiverId\" : \"p2\", \"payerId\" : \"p5\", \"amountToBePaid\" : 2000, \"amountPaid\" : 0,  \"type\" : \"debt\"},{\"receiverId\" : \"p2\", \"payerId\" : \"p6\", \"amountToBePaid\" : 2000, \"amountPaid\" : 0,  \"type\" : \"debt\"},{\"receiverId\" : \"p1\", \"payerId\" : \"p7\", \"amountToBePaid\" : 2000, \"amountPaid\" : 0,  \"type\" : \"debt\"},{ \"receiverId\" : \"p1\", \"payerId\" : \"p8\", \"amountToBePaid\" : 2000, \"amountPaid\" : 0, \"type\" : \"debt\"},{ \"receiverId\" : \"p2\", \"payerId\" : \"p9\", \"amountToBePaid\" : 2000, \"amountPaid\" : 0,  \"type\" : \"debt\"},{ \"receiverId\" : \"p3\", \"payerId\" : \"p4\", \"amountToBePaid\" : 1000, \"amountPaid\" : 0, \"type\" : \"debt\"}]";
		ObjectMapper mapper = new ObjectMapper();
		List<Member> memberList = mapper.readValue(members, List.class);
		Group mockGroup = new Group();
		mockGroup.setGroupDescription("Munnar Trip");
		mockGroup.setGroupName("Munnar Trip");
		mockGroup.setMembers(memberList);
		mockGroup = groupRepo.save(mockGroup);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/spends/" + mockGroup.getGroupId())
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(spends);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		assertSpends(mockGroup,spends);
		assertSettlement(mockGroup,settlements);

	}
	
	private void assertSpends(Group group,String spends)
	{
		
	}

	private void assertSettlement(Group group,String settlements)
	{
		
	}

}
