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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.countme.up.model.constants.PathConstants;
import com.countme.up.model.entity.Candidate;
import com.countme.up.model.entity.Voter;
import com.countme.up.spring.config.ApplicationTestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ApplicationTestConfig.class })
@ComponentScan(basePackages = { "com.countme.up.dao", "com.countme.up.service", "com.countme.up.controller" })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
public class VoteControllerTest {

	private MockMvc mockMvc;
	private ObjectMapper mapper;

	@Autowired
	private VoteController voteController;

	@Autowired
	private VoterController voterController;

	@Autowired
	private CandidateController candidateController;

	@Before
	public void testCreate() throws Exception {
		MockitoAnnotations.initMocks(this);

		mapper = new ObjectMapper();
		mockMvc = MockMvcBuilders.standaloneSetup(voteController, voterController, candidateController).build();

		testCreateVoter("Ahmed", "Ibrahim", "aibrahim@msn.com", 1);
		testCreateVoter("Gerard", "pique", "barca@gmail.com", 2);
		testCreateVoter("Shakira", "pique", "columbia@gmail.com", 3);

		testCreateCandidate("Lionel", "Messi", "leo@msn.com", 1);
		testCreateCandidate("Ronaldo", "Cris", "real@gmail.com", 2);
		testCreateCandidate("David", "Beckham", "england@gmail.com", 3);

		testCreateVote(1L, 1L);
		testCreateVote(1L, 2L);
		testCreateVote(2L, 3L);
		testCreateVote(3L, 1L);
		testCreateVote(3L, 1L);
		testCreateVote(3L, 2L);

		/** Test reaching the limit of registered votes **/
		//@formatter:off
		mockMvc.perform(get(PathConstants.VOTE_MAIN_PATH).param("cid", "2").param("vid", String.valueOf("1")))
			   		.andExpect(status().isForbidden())
			   		.andExpect(jsonPath("$.status.message", is("Failed")))
			   		.andExpect(jsonPath("$.status.errors[0]", is("Voter has reached the limit of registered votes which is 3")));
		//@formatter:on
	}

	@Test
	public void testRemoveAndGet() throws Exception {
		/** Test get method with valid Id **/
		//@formatter:off
		mockMvc.perform(get(PathConstants.VOTE_MAIN_PATH + "/3"))
			   		.andExpect(status().isOk())
			   		.andExpect(jsonPath("$.status.message", is("Success")))
			   		.andExpect(jsonPath("$.data.voter.firstname", is("Shakira")))
			   		.andExpect(jsonPath("$.data.candidate.firstname", is("Ronaldo")));
		//@formatter:on

		/** Test get method with invalid Id **/
		//@formatter:off
		mockMvc.perform(get(PathConstants.VOTE_MAIN_PATH + "/100"))
			   		.andExpect(status().isBadRequest())
			   		.andExpect(jsonPath("$.status.message", is("Failed")))
			   		.andExpect(jsonPath("$.status.errors[0]", is("Vote with the following Id 100 doesn't exist")));
		//@formatter:on

		/** Test delete method with valid Id **/
		//@formatter:off
		mockMvc.perform(delete(PathConstants.VOTE_MAIN_PATH + "/1"))
			   		.andExpect(status().isOk())
			   		.andExpect(jsonPath("$.status.message", is("Success")))
			   		.andExpect(jsonPath("$.data.voter.firstname", is("Ahmed")))
			   		.andExpect(jsonPath("$.data.candidate.firstname", is("Lionel")));
		//@formatter:on

		/** Test delete method with invalid Id **/
		//@formatter:off
		mockMvc.perform(delete(PathConstants.VOTE_MAIN_PATH + "/10"))
			   		.andExpect(status().isBadRequest())
			   		.andExpect(jsonPath("$.status.message", is("Failed")))
			   		.andExpect(jsonPath("$.status.errors[0]", is("Failed to delete vote with the following Id 10")));
		//@formatter:on
	}

	@After
	public void testGetResultsAndSearch() throws Exception {
		/** Test get results without parameters **/
		//@formatter:off
		mockMvc.perform(get(PathConstants.VOTE_MAIN_PATH + PathConstants.VOTE_RESULTS_PATH))
			   		.andExpect(status().isOk())
			   		.andExpect(jsonPath("$.status.message", is("Success")))
			   		.andExpect(jsonPath("$.data", hasSize(3)))
			   		.andExpect(jsonPath("$.data[0].votes", is(1)))
					.andExpect(jsonPath("$.data[1].votes", is(1)))
					.andExpect(jsonPath("$.data[2].votes", is(1)));
		//@formatter:on

		/** Test get results **/
		//@formatter:off
		mockMvc.perform(get(PathConstants.VOTE_MAIN_PATH + PathConstants.VOTE_RESULTS_PATH).param("cid", "3"))
			   		.andExpect(status().isOk())
			   		.andExpect(jsonPath("$.status.message", is("Success")))
			   		.andExpect(jsonPath("$.data", hasSize(1)));
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
			   		.andExpect(jsonPath("$.data.id", notNullValue()))
					.andExpect(jsonPath("$.data.registered", is(true)));
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

	private void testCreateVote(Long candidateId, Long voterId) throws Exception {
		//@formatter:off
		mockMvc.perform(get(PathConstants.VOTE_MAIN_PATH).param("cid", String.valueOf(candidateId)).param("vid", String.valueOf(voterId)))
			   		.andExpect(status().isCreated())
			   		.andExpect(jsonPath("$.status.message", is("Success")))
			   		.andExpect(jsonPath("$.data.id", notNullValue()))
			   		.andExpect(jsonPath("$.data.date", notNullValue()));
		//@formatter:on
	}

}
