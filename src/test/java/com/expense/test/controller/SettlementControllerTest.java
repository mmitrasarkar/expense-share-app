package com.expense.test.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.expense.model.Settlement;
import com.expense.model.Spend;
import com.expense.repository.GroupRepository;
import com.expense.repository.PaymentHistoryRepository;
import com.expense.repository.SettlementRepository;
import com.expense.repository.SpendRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Settlement related tests
 * @author USER
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExpenseServicesApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class SettlementControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	GroupRepository groupRepo;

	@Autowired
	SpendRepository spendRepository;

	@Autowired
	SettlementRepository settlementRepo;
	
	@Autowired
	PaymentHistoryRepository paymentRepository;
	/**
	 * Partial and full payment test
	 * @throws Exception
	 */
	@Test
	public void partialPaymentTest() throws Exception {
		String members = "[{\"memberId\": \"p10\",\"memberName\": \"p10\",\"email\": \"p10@gmail.com\"},{\"memberId\": \"p2\",\"memberName\": \"p2\",\"email\": \"p2@gmail.com\"},{\"memberId\": \"p3\",\"memberName\": \"p3\",\"email\": \"p3@gmail.com\"},{\"memberId\": \"p1\",\"memberName\": \"p1\",\"email\": \"p1@gmail.com\"},{\"memberId\": \"p12\",\"memberName\": \"p12\",\"email\": \"p12@gmail.com\"},{\"memberId\": \"p4\",\"memberName\": \"p4\",\"email\": \"p4@gmail.com\"},{\"memberId\": \"p5\",\"memberName\": \"p5\",\"email\": \"p5@gmail.com\"},{\"memberId\": \"p6\",\"memberName\": \"p6\",\"email\": \"p6@gmail.com\"},{\"memberId\": \"p7\",\"memberName\": \"p7\",\"email\": \"p7@gmail.com\"},{\"memberId\": \"p8\",\"memberName\": \"p8\",\"email\": \"p8@gmail.com\"},{\"memberId\": \"p9\",\"memberName\": \"p9\",\"email\": \"p9@gmail.com\"}]";
		String spends = "[{\"spenderName\":\"p1\",\"spendAmout\":10000,\"description\":\"Hotel Cost\"},{\"spenderName\":\"p2\",\"spendAmout\":8000,\"description\":\"Lunch\"},{\"spenderName\":\"p3\",\"spendAmout\":3000,\"description\":\"breakfast\"},{\"spenderName\":\"p4\",\"spendAmout\":1000,\"description\":\"Meseum Ticket\"}]";
		String settlements = "[{ \"receiverId\" : \"p1\", \"payerId\": \"p10\", \"amountToBePaid\": 2000, \"amountPaid\": 0, \"type\": \"debt\" },{ \"receiverId\" : \"p1\", \"payerId\" : \"p12\", \"amountToBePaid\" : 2000, \"amountPaid\" : 0,  \"type\" : \"debt\"},{ \"receiverId\" : \"p2\", \"payerId\" : \"p5\", \"amountToBePaid\" : 2000, \"amountPaid\" : 0,  \"type\" : \"debt\"},{\"receiverId\" : \"p2\", \"payerId\" : \"p6\", \"amountToBePaid\" : 2000, \"amountPaid\" : 0,  \"type\" : \"debt\"},{\"receiverId\" : \"p1\", \"payerId\" : \"p7\", \"amountToBePaid\" : 2000, \"amountPaid\" : 0,  \"type\" : \"debt\"},{ \"receiverId\" : \"p1\", \"payerId\" : \"p8\", \"amountToBePaid\" : 2000, \"amountPaid\" : 0, \"type\" : \"debt\"},{ \"receiverId\" : \"p2\", \"payerId\" : \"p9\", \"amountToBePaid\" : 2000, \"amountPaid\" : 0,  \"type\" : \"debt\"},{ \"receiverId\" : \"p3\", \"payerId\" : \"p4\", \"amountToBePaid\" : 1000, \"amountPaid\" : 0, \"type\" : \"debt\"}]";
		String payment = "{ \"receiverId\": \"p2\",\"payerId\": \"p6\",\"amountToBePaid\": 2000,\"amountPaid\": 1000}";
		ObjectMapper mapper = new ObjectMapper();
		List<Member> memberList = mapper.readValue(members, List.class);
		List<Map<String, Object>> spendList = mapper.readValue(spends, List.class);
		List<Map<String, Object>> settlementList = mapper.readValue(settlements, List.class);
		Settlement paymentMade = mapper.readValue(payment, Settlement.class);
		Group mockGroup = new Group();
		mockGroup.setGroupDescription("Munnar Trip");
		mockGroup.setGroupName("Munnar Trip");
		mockGroup.setMembers(memberList);
		mockGroup = groupRepo.save(mockGroup);
		List<Spend> spendCollection = new ArrayList<>();
		List<Settlement> settlementCollection = new ArrayList<>();
		for (Map<String, Object> spend : spendList) {
			Spend s = new Spend();
			s.setGroupId(mockGroup.getGroupId());
			s.setSpenderName((String) spend.get("spenderName"));
			s.setSpendAmout((Integer) spend.get("spendAmout"));
			s.setDescription((String) spend.get("description"));
			spendCollection.add(s);
		}
		for (Map<String, Object> settlement : settlementList) {
			Settlement s = new Settlement();
			s.setGroupId(mockGroup.getGroupId());
			s.setReceiverId((String) settlement.get("receiverId"));
			s.setPayerId((String) settlement.get("payerId"));
			s.setAmountToBePaid((Integer) settlement.get("amountToBePaid"));
			s.setAmountPaid((Integer) settlement.get("amountPaid"));
			s.setType((String) settlement.get("type"));
			settlementCollection.add(s);
		}

		spendRepository.insert(spendCollection);
		settlementRepo.insert(settlementCollection);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/settlements/" + mockGroup.getGroupId())
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(payment);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());

		assertSettlementRemaining(mockGroup.getGroupId(), paymentMade);

	}
	/**
	 * Assert the remaining settlement
	 * @param groupId
	 * @param settlement
	 */
	private void assertSettlementRemaining(String groupId, Settlement settlement) {
		List<Settlement> updatedSettlement = settlementRepo.getSettlement(settlement.getPayerId(),
				settlement.getReceiverId(), groupId);
		Settlement s = updatedSettlement.get(0);
		assertEquals((settlement.getAmountToBePaid() - settlement.getAmountPaid()), s.getAmountToBePaid(),0.01);
	}
	/**
	 * Assert the payment history
	 * @param groupId
	 * @param settlement
	 */
	private void assertPaymentHistory(String groupId, Settlement settlement) {
		List<Settlement> updatedSettlement = settlementRepo.getSettlement(settlement.getPayerId(),
				settlement.getReceiverId(), groupId);
		double totalPayment = paymentRepository.getTotalPaymentByUser(settlement.getPayerId(),
				settlement.getReceiverId(), groupId);
		Settlement s = updatedSettlement.get(0);
		assertEquals(settlement.getAmountPaid(), totalPayment,0.01);
	}

}
