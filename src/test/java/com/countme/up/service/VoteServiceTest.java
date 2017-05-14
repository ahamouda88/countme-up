package com.countme.up.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.countme.up.model.CandidateCount;
import com.countme.up.model.entity.Candidate;
import com.countme.up.model.entity.Vote;
import com.countme.up.model.entity.Voter;
import com.countme.up.model.exception.MaxNbrOfVotesReachedException;
import com.countme.up.model.request.VoteSearchRequest;
import com.countme.up.spring.config.ApplicationTestConfig;
import com.countme.up.utils.DateUtils;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = { ApplicationTestConfig.class })
@ComponentScan(basePackages = { "com.countme.up.dao", "com.countme.up.service" })
public class VoteServiceTest {

	@Autowired
	private VoteService voteService;

	@Autowired
	private CandidateService candidateService;

	@Autowired
	private VoterService voterService;

	private long[] voteIds;
	private long[] voterIds;
	private long[] candidateIds;

	@Before
	public void testAdd() {
		/** Create three candidates **/
		candidateIds = new long[3];
		Candidate candidate1 = new Candidate("Ahmed", "Hamouda", "ahmedibrahim@msn.com", null);
		candidateService.add(candidate1);
		candidateIds[0] = candidate1.getId();
		Candidate candidate2 = new Candidate("Lionel", "Messi", "Leo@yahoo.com", null);
		candidateService.add(candidate2);
		candidateIds[1] = candidate2.getId();
		Candidate candidate3 = new Candidate("Andres", "Iniesta", "andres@gmail.com", null);
		candidateService.add(candidate3);
		candidateIds[2] = candidate3.getId();

		/** Create voters **/
		int numOfVoters = 7;
		voterIds = new long[numOfVoters];
		Voter voter1 = new Voter("Omar", "Ibrahim", "omar@hotmail.com");
		voterService.add(voter1);
		voterIds[0] = voter1.getId();
		Voter voter2 = new Voter("Quynh", "ToTuan", "quynhNhu@gmail.com");
		voterService.add(voter2);
		voterIds[1] = voter2.getId();
		Voter voter3 = new Voter("Sara", "Darwish", "sara@hotmail.com");
		voterService.add(voter3);
		voterIds[2] = voter3.getId();
		Voter voter4 = new Voter("Diego", "Maradonna", "maro@hotmail.com");
		voterService.add(voter4);
		voterIds[3] = voter4.getId();
		Voter voter5 = new Voter("Ibrahimovic", null, "ibra@me.com");
		voterService.add(voter5);
		voterIds[4] = voter5.getId();
		Voter voter6 = new Voter("Ramadan", "Kareem", "ramo@hotmail.com");
		voterService.add(voter6);
		voterIds[5] = voter6.getId();
		Voter voter7 = new Voter("Amr", "Diab", "elhadba@hotmail.com");
		voterService.add(voter7);
		voterIds[6] = voter7.getId();

		/** Create votes **/
		voteIds = new long[11];
		Date date1 = DateUtils.getDate(2017, 5, 1);
		Vote vote1 = new Vote(voter1, candidate1, date1);
		assertTrue("Failed to save vote1 entity!", voteService.add(vote1));
		voteIds[0] = vote1.getId();

		Date date2 = DateUtils.getDate(2017, 5, 2, 1, 10);
		Vote vote2 = new Vote(voter1, candidate2, date2);
		assertTrue("Failed to save vote2 entity!", voteService.add(vote2));
		voteIds[1] = vote2.getId();

		Date date3 = DateUtils.getDate(2017, 5, 2, 1, 30);
		Vote vote3 = new Vote(voter2, candidate3, date3);
		assertTrue("Failed to save vote3 entity!", voteService.add(vote3));
		voteIds[2] = vote3.getId();

		Date date4 = DateUtils.getDate(2017, 5, 2, 2, 30);
		Vote vote4 = new Vote(voter2, candidate3, date4);
		assertTrue("Failed to save vote4 entity!", voteService.add(vote4));
		voteIds[3] = vote4.getId();

		Date date5 = DateUtils.getDate(2017, 5, 2, 10, 0);
		Vote vote5 = new Vote(voter3, candidate1, date5);
		assertTrue("Failed to save vote5 entity!", voteService.add(vote5));
		voteIds[4] = vote5.getId();

		Date date6 = DateUtils.getDate(2017, 5, 2, 22, 0);
		Vote vote6 = new Vote(voter4, candidate1, date6);
		assertTrue("Failed to save vote6 entity!", voteService.add(vote6));
		voteIds[5] = vote6.getId();

		Date date7 = DateUtils.getDate(2017, 5, 5);
		Vote vote7 = new Vote(voter5, candidate3, date7);
		assertTrue("Failed to save vote7 entity!", voteService.add(vote7));
		voteIds[6] = vote7.getId();

		Date date8 = DateUtils.getDate(2017, 5, 7);
		Vote vote8 = new Vote(voter6, candidate1, date8);
		assertTrue("Failed to save vote8 entity!", voteService.add(vote8));
		voteIds[7] = vote8.getId();

		Date date9 = DateUtils.getDate(2017, 5, 9);
		Vote vote9 = new Vote(voter7, candidate1, date9);
		assertTrue("Failed to save vote9 entity!", voteService.add(vote9));
		voteIds[8] = vote9.getId();

		Date date10 = DateUtils.getDate(2017, 5, 10);
		Vote vote10 = new Vote(voter5, candidate3, date10);
		assertTrue("Failed to save vote10 entity!", voteService.add(vote10));
		voteIds[9] = vote10.getId();

		Date date11 = DateUtils.getDate(2017, 9, 10);
		Vote vote11 = new Vote(voter6, candidate1, date11);
		assertTrue("Failed to save vote11 entity!", voteService.add(vote11));
		voteIds[10] = vote11.getId();
	}

	@Test
	public void testVotesOfVoter() {
		Voter voter = voterService.get(voterIds[4]);

		assertEquals("Voter's number of votes is invalid!", 2, voter.getVotes().size());
	}

	@Test
	public void testReceivedVotesOfCandidate() {
		Candidate candidate = candidateService.get(candidateIds[0]);

		assertEquals("Candidate's number of votes received is invalid!", 6, candidate.getVotesReceived().size());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUpdate() {
		Vote vote = voteService.get(voteIds[4]);

		voteService.update(vote);
	}

	@Test
	public void testDelete() {
		Vote voteToBeRemoved = voteService.get(voteIds[8]);
		Vote vote = voteService.delete(voteToBeRemoved);

		assertNotNull("Failed to remove vote!", vote);
		assertEquals("The voter of the removed vote is not correct", "Amr", vote.getVoter().getFirstname());
		assertEquals("The candidate of the removed vote is not correct", "Hamouda", vote.getCandidate().getLastname());

		assertEquals("Invalid number of votes, after removing one vote!", 10, voteService.getAll().size());

		/** Test number of left votes for the voter **/
		Voter voter = voterService.get(vote.getVoter().getId());
		assertEquals("Invalied number of left votes after removing one vote from voter's list!", 0,
				voter.getVotes().size());

		/** Test number of left received votes for the candidate **/
		Candidate candidate = candidateService.get(vote.getCandidate().getId());
		assertEquals("Invalied number of left recieved votes after removing one vote from candidate's list!", 5,
				candidate.getVotesReceived().size());
	}

	@Test
	public void testDeleteByKey() {
		Vote vote = voteService.deleteByKey(voteIds[6]);

		assertNotNull("Failed to remove vote!", vote);
		assertEquals("The voter of the removed vote is not correct", "Ibrahimovic", vote.getVoter().getFirstname());
		assertEquals("The candidate of the removed vote is not correct", "Iniesta", vote.getCandidate().getLastname());

		assertEquals("Invalid number of votes, after removing one vote!", 10, voteService.getAll().size());

		/** Test number of left votes for the voter **/
		Voter voter = voterService.get(vote.getVoter().getId());
		assertEquals("Invalied number of left votes after removing one vote from voter's list!", 1,
				voter.getVotes().size());

		/** Test number of left received votes for the candidate **/
		Candidate candidate = candidateService.get(vote.getCandidate().getId());
		assertEquals("Invalied number of left recieved votes after removing one vote from candidate's list!", 3,
				candidate.getVotesReceived().size());
	}

	@Test
	public void testFindByCandidate() {
		/** Testing with valid candidate Id **/
		VoteSearchRequest request = VoteSearchRequest.builder().candidateId(candidateIds[1]).build();
		List<Vote> voteList = voteService.search(request);

		assertEquals("Invalid number of returned votes, after search!", 1, voteList.size());

		/** Testing with invalid candidate id **/
		request = VoteSearchRequest.builder().candidateId(99999L).build();
		voteList = voteService.search(request);

		assertEquals("Invalid number of returned votes, after search!", 0, voteList.size());
	}

	@Test
	public void testFindByVoter() {
		/** Testing with valid voter Id **/
		VoteSearchRequest request = VoteSearchRequest.builder().voterId(voterIds[2]).build();
		List<Vote> voteList = voteService.search(request);

		assertEquals("Invalid number of returned votes, after search!", 1, voteList.size());

		/** Testing with invalid voter id **/
		request = VoteSearchRequest.builder().voterId(99999L).build();
		voteList = voteService.search(request);

		assertEquals("Invalid number of returned votes, after search!", 0, voteList.size());
	}

	@Test
	public void testFindByDates() {
		/** Testing from and to dates **/
		VoteSearchRequest request = VoteSearchRequest.builder().fromDate(DateUtils.getDate(2017, 5, 7))
				.toDate(DateUtils.getDate(2017, 5, 10)).build();
		List<Vote> voteList = voteService.search(request);

		assertEquals("Invalid number of returned votes, after search!", 3, voteList.size());

		/** Testing with invalid dates: dates not in-range **/
		request = VoteSearchRequest.builder().fromDate(DateUtils.getDate(2018, 5, 2, 1, 11))
				.toDate(DateUtils.getDate(2018, 5, 2, 11, 11)).build();
		voteList = voteService.search(request);

		assertEquals("Invalid number of returned votes, after search!", 0, voteList.size());

		/** Testing with invalid dates: "from" date is after the "to" date **/
		request = VoteSearchRequest.builder().fromDate(DateUtils.getDate(2017, 10, 2))
				.toDate(DateUtils.getDate(2017, 5, 1)).build();
		voteList = voteService.search(request);

		assertEquals("Invalid number of returned votes, after search!", 0, voteList.size());
	}

	@Test
	public void testFindAll() {
		assertEquals("Invalid number of votes!", 11, voteService.getAll().size());
	}

	@Test
	public void testGetResults() {
		Map<Long, Long> expectedCount = new HashMap<>();
		expectedCount.put(candidateIds[0], 6L);
		expectedCount.put(candidateIds[1], 1L);
		expectedCount.put(candidateIds[2], 4L);
		CandidateCount[] resultCount = voteService.getResults(VoteSearchRequest.builder().build());
		assertEquals("Invalid number of candidates!", 3, resultCount.length);

		for (CandidateCount candidateCount: resultCount) {
			long candidateId = candidateCount.getCandidate().getId();
			long expectedValue = expectedCount.get(candidateId);
			long actualValue = candidateCount.getVotes();
			assertEquals("Invalid number of received votes for candidate: " + candidateId, expectedValue, actualValue);
		}
	}

	@Test(expected = MaxNbrOfVotesReachedException.class)
	public void testMaxNumberOfVotes() {
		Voter voter = voterService.get(voterIds[0]);
		Candidate candidate = candidateService.get(candidateIds[2]);
		Date date = DateUtils.getDate(2017, 5, 2, 1, 10);
		Vote vote = new Vote(voter, candidate, date);
		voteService.add(vote);
		// With this add the number of registered votes will be 4! which is invalid
		Vote extraVote = new Vote(voter, candidate, date);
		voteService.add(extraVote);
	}

	@Test(expected = NullPointerException.class)
	public void testInvalidAdd() {
		voteService.add(null);
	}

	@Test(expected = NullPointerException.class)
	public void testInvalidCreate() {
		voteService.create(null, 1L, null);
	}

	@Test(expected = NullPointerException.class)
	public void testInvalidDelete() {
		voteService.delete(null);
	}

	@Test(expected = NullPointerException.class)
	public void testInvalidSearch() {
		voteService.search(null);
	}

	@Test(expected = NullPointerException.class)
	public void testInvalidFind() {
		voteService.get(null);
	}
}
