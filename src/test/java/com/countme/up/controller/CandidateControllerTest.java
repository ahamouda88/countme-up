package com.countme.up.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.countme.up.model.constants.PathConstants;
import com.countme.up.model.entity.Candidate;
import com.countme.up.spring.config.ApplicationTestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ApplicationTestConfig.class })
@ComponentScan(basePackages = { "com.countme.up.dao", "com.countme.up.service", "com.countme.up.controller" })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class CandidateControllerTest {

	private MockMvc mockMvc;
	private ObjectMapper mapper;

	@Autowired
	private CandidateController candidateController;

	@Before
	public void testCreate() throws Exception {
		MockitoAnnotations.initMocks(this);

		mapper = new ObjectMapper();
		mockMvc = MockMvcBuilders.standaloneSetup(candidateController).build();

		testCreateCandidate("Ahmed", "Ibrahim", "aibrahim@msn.com", 1);
		testCreateCandidate("Gerard", "pique", "barca@gmail.com", 2);
		testCreateCandidate("Shakira", "pique", "columbia@gmail.com", 3);
	}

	@Test
	public void testRemoveAndGet() throws Exception {
		/** Test get method with valid Id **/
		//@formatter:off
		mockMvc.perform(get(PathConstants.CANDIDATE_MAIN_PATH + "/1"))
			   		.andExpect(status().isOk())
			   		.andExpect(jsonPath("$.status.message", is("Success")))
			   		.andExpect(jsonPath("$.data.firstname", is("Ahmed")))
			   		.andExpect(jsonPath("$.data.lastname", is("Ibrahim")))
			   		.andExpect(jsonPath("$.data.email", is("aibrahim@msn.com")));
		//@formatter:on

		/** Test get method with invalid Id **/
		//@formatter:off
		mockMvc.perform(get(PathConstants.CANDIDATE_MAIN_PATH + "/100"))
			   		.andExpect(status().isBadRequest())
			   		.andExpect(jsonPath("$.status.message", is("Failed")))
			   		.andExpect(jsonPath("$.status.errors[0]", is("Candidate with the following Id 100 doesn't exist")));
		//@formatter:on

		/** Test delete method with valid Id **/
		//@formatter:off
		mockMvc.perform(delete(PathConstants.CANDIDATE_MAIN_PATH + "/1"))
			   		.andExpect(status().isOk())
			   		.andExpect(jsonPath("$.status.message", is("Success")))
			   		.andExpect(jsonPath("$.data.firstname", is("Ahmed")))
			   		.andExpect(jsonPath("$.data.lastname", is("Ibrahim")))
			   		.andExpect(jsonPath("$.data.email", is("aibrahim@msn.com")));
		//@formatter:on

		/** Test delete method with invalid Id **/
		//@formatter:off
		mockMvc.perform(delete(PathConstants.CANDIDATE_MAIN_PATH + "/10"))
			   		.andExpect(status().isBadRequest())
			   		.andExpect(jsonPath("$.status.message", is("Failed")))
			   		.andExpect(jsonPath("$.status.errors[0]", is("Candidate with the following Id 10 doesn't exist")));
		//@formatter:on
	}

	@After
	public void testGetAll() throws Exception {
		//@formatter:off
		mockMvc.perform(get(PathConstants.CANDIDATE_MAIN_PATH))
			   		.andExpect(status().isOk())
			   		.andExpect(jsonPath("$.status.message", is("Success")))
			   		.andExpect(jsonPath("$.data", hasSize(2)));
		//@formatter:on
	}

	private void testCreateCandidate(String firstname, String lastname, String email, int id) throws Exception {
		//@formatter:off
		Candidate candidate = new Candidate(firstname, lastname, email, null);
		mockMvc.perform(post(PathConstants.CANDIDATE_MAIN_PATH, candidate)
			   .contentType(MediaType.APPLICATION_JSON)
			   .content(mapper.writeValueAsString(candidate)))
			   		.andExpect(status().isCreated())
			   		.andExpect(jsonPath("$.status.message", is("Success")))
			   		.andExpect(jsonPath("$.data.firstname", is(firstname)))
			   		.andExpect(jsonPath("$.data.lastname", is(lastname)))
			   		.andExpect(jsonPath("$.data.email", is(email)))
			   		.andExpect(jsonPath("$.data.id", notNullValue()));
		//@formatter:on
	}

}
