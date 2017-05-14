package com.countme.up.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.countme.up.model.constants.PathConstants;
import com.countme.up.model.entity.Voter;
import com.countme.up.spring.config.ApplicationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ApplicationConfig.class })
@ComponentScan(basePackages = { "com.countme.up.dao", "com.countme.up.service", "com.countme.up.controller" })
public class VoterControllerTest {

	private MockMvc mockMvc;
	private ObjectMapper mapper;

	@Autowired
	private VoterController voterController;

	@Before
	public void testCreate() throws Exception {
		MockitoAnnotations.initMocks(this);

		mapper = new ObjectMapper();
		mockMvc = MockMvcBuilders.standaloneSetup(voterController).build();

		testCreateVoter("Ahmed", "Ibrahim", "aibrahim@msn.com", 1);
		testCreateVoter("Gerard", "pique", "barca@gmail.com", 2);
		testCreateVoter("Shakira", "pique", "columbia@gmail.com", 3);
	}

	@Test
	public void testRemoveAndGet() throws Exception {
		/** Test get method with valid Id **/
		//@formatter:off
		mockMvc.perform(get(PathConstants.VOTER_MAIN_PATH + "/1"))
			   		.andExpect(status().isOk())
			   		.andExpect(jsonPath("$.status.message", is("Success")))
			   		.andExpect(jsonPath("$.data.firstname", is("Ahmed")))
			   		.andExpect(jsonPath("$.data.lastname", is("Ibrahim")))
			   		.andExpect(jsonPath("$.data.email", is("aibrahim@msn.com")))
					.andExpect(jsonPath("$.data.registered", is(true)));
		//@formatter:on

		/** Test get method with invalid Id **/
		//@formatter:off
		mockMvc.perform(get(PathConstants.VOTER_MAIN_PATH + "/100"))
			   		.andExpect(status().isNoContent())
			   		.andExpect(jsonPath("$.status.message", is("Failed")))
			   		.andExpect(jsonPath("$.status.errors[0]", is("Voter with the following Id 100 doesn't exist")));
		//@formatter:on

		/** Test delete method with valid Id **/
		//@formatter:off
		mockMvc.perform(delete(PathConstants.VOTER_MAIN_PATH + "/1"))
			   		.andExpect(status().isOk())
			   		.andExpect(jsonPath("$.status.message", is("Success")))
			   		.andExpect(jsonPath("$.data.firstname", is("Ahmed")))
			   		.andExpect(jsonPath("$.data.lastname", is("Ibrahim")))
			   		.andExpect(jsonPath("$.data.email", is("aibrahim@msn.com")))
					.andExpect(jsonPath("$.data.registered", is(true)));
		//@formatter:on

		/** Test delete method with invalid Id **/
		//@formatter:off
		mockMvc.perform(delete(PathConstants.VOTER_MAIN_PATH + "/10"))
			   		.andExpect(status().isNoContent())
			   		.andExpect(jsonPath("$.status.message", is("Failed")))
			   		.andExpect(jsonPath("$.status.errors[0]", is("Voter with the following Id 10 doesn't exist")));
		//@formatter:on
	}

	@After
	public void testGetAll() throws Exception {
		//@formatter:off
		mockMvc.perform(get(PathConstants.VOTER_MAIN_PATH))
			   		.andExpect(status().isOk())
			   		.andExpect(jsonPath("$.status.message", is("Success")))
			   		.andExpect(jsonPath("$.data", hasSize(2)));
		//@formatter:on
	}

	private void testCreateVoter(String firstname, String lastname, String email, int id) throws Exception {
		//@formatter:off
		Voter voter = new Voter(firstname, lastname, email);
		mockMvc.perform(post(PathConstants.VOTER_MAIN_PATH, voter)
			   .contentType(MediaType.APPLICATION_JSON)
			   .content(mapper.writeValueAsString(voter)))
			   		.andExpect(status().isCreated())
			   		.andExpect(jsonPath("$.status.message", is("Success")))
			   		.andExpect(jsonPath("$.data.firstname", is(firstname)))
			   		.andExpect(jsonPath("$.data.lastname", is(lastname)))
			   		.andExpect(jsonPath("$.data.email", is(email)))
			   		.andExpect(jsonPath("$.data.id", is(id)))
					.andExpect(jsonPath("$.data.registered", is(true)));
		//@formatter:on
	}

}
