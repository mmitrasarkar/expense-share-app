package com.expense.test.controller;

import static org.junit.Assert.assertEquals;

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
import com.expense.repository.GroupRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Group operation tests
 * @author USER
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExpenseServicesApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class GroupControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	GroupRepository groupRepo;

	/**
	 * Test to create group
	 * 
	 * @throws Exception
	 */
	@Test
	public void createGroup() throws Exception {
		Group mockGroup = new Group();
		mockGroup.setGroupDescription("Munnar Trip");
		mockGroup.setGroupName("Munnar Trip");

		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(mockGroup);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/groups/").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(content);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

	}

	/**
	 * Test create bulk members
	 * @throws Exception
	 */
	@Test
	public void createMember() throws Exception {
		Group mockGroup = new Group();
		mockGroup.setGroupDescription("Munnar Trip");
		mockGroup.setGroupName("Munnar Trip");
		mockGroup = groupRepo.save(mockGroup);

		String members = "[{\"memberId\": \"p10\",\"memberName\": \"p10\",\"email\": \"p10@gmail.com\"},{\"memberId\": \"p2\",\"memberName\": \"p2\",\"email\": \"p2@gmail.com\"},{\"memberId\": \"p3\",\"memberName\": \"p3\",\"email\": \"p3@gmail.com\"},{\"memberId\": \"p1\",\"memberName\": \"p1\",\"email\": \"p1@gmail.com\"},{\"memberId\": \"p12\",\"memberName\": \"p12\",\"email\": \"p12@gmail.com\"},{\"memberId\": \"p4\",\"memberName\": \"p4\",\"email\": \"p4@gmail.com\"},{\"memberId\": \"p5\",\"memberName\": \"p5\",\"email\": \"p5@gmail.com\"},{\"memberId\": \"p6\",\"memberName\": \"p6\",\"email\": \"p6@gmail.com\"},{\"memberId\": \"p7\",\"memberName\": \"p7\",\"email\": \"p7@gmail.com\"},{\"memberId\": \"p8\",\"memberName\": \"p8\",\"email\": \"p8@gmail.com\"},{\"memberId\": \"p9\",\"memberName\": \"p9\",\"email\": \"p9@gmail.com\"}]";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/groups/" + mockGroup.getGroupId() + "/members")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(members);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
}
