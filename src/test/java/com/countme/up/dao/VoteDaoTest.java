package com.countme.up.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.countme.up.model.entity.Candidate;
import com.countme.up.model.entity.Vote;
import com.countme.up.model.entity.Voter;
import com.countme.up.model.request.VoteSearchRequest;
import com.countme.up.spring.config.ApplicationConfig;
import com.countme.up.utils.DateUtils;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = { ApplicationConfig.class, VoteDao.class, CandidateDao.class, VoterDao.class })
public class VoteDaoTest {

	@Autowired
	private VoteDao voteDao;

	@Autowired
	private CandidateDao candidateDao;

	@Autowired
	private VoterDao voterDao;

	@Before
	public void testSave() {
		/** Create three candidates **/
		Candidate candidate1 = new Candidate("Ahmed", "Hamouda", "ahmedibrahim@msn.com", null);
		candidateDao.save(candidate1);
		Candidate candidate2 = new Candidate("Lionel", "Messi", "Leo@yahoo.com", null);
		candidateDao.save(candidate2);
		Candidate candidate3 = new Candidate("Andres", "Iniesta", "andres@gmail.com", null);
		candidateDao.save(candidate3);

		/** Create voters **/
		Voter voter1 = new Voter("Omar", "Ibrahim", "omar@hotmail.com");
		voterDao.save(voter1);
		Voter voter2 = new Voter("Quynh", "ToTuan", "quynhNhu@gmail.com");
		voterDao.save(voter2);
		Voter voter3 = new Voter("Sara", "Darwish", "sara@hotmail.com");
		voterDao.save(voter3);
		Voter voter4 = new Voter("Diego", "Maradonna", "maro@hotmail.com");
		voterDao.save(voter4);
		Voter voter5 = new Voter("Ibrahimovic", null, "ibra@me.com");
		voterDao.save(voter5);
		Voter voter6 = new Voter("Ramadan", "Kareem", "ramo@hotmail.com");
		voterDao.save(voter6);
		Voter voter7 = new Voter("Amr", "Diab", "elhadba@hotmail.com");
		voterDao.save(voter7);

		/** Create votes **/
		Date date1 = DateUtils.getDate(2017, 5, 1);
		Vote vote1 = new Vote(voter1, candidate1, date1);
		assertTrue("Failed to save vote1 entity!", voteDao.save(vote1));

		Date date2 = DateUtils.getDate(2017, 5, 2, 1, 10);
		Vote vote2 = new Vote(voter1, candidate2, date2);
		assertTrue("Failed to save vote2 entity!", voteDao.save(vote2));

		Date date3 = DateUtils.getDate(2017, 5, 2, 1, 30);
		Vote vote3 = new Vote(voter2, candidate3, date3);
		assertTrue("Failed to save vote3 entity!", voteDao.save(vote3));

		Date date4 = DateUtils.getDate(2017, 5, 2, 2, 30);
		Vote vote4 = new Vote(voter2, candidate3, date4);
		assertTrue("Failed to save vote4 entity!", voteDao.save(vote4));

		Date date5 = DateUtils.getDate(2017, 5, 2, 10, 0);
		Vote vote5 = new Vote(voter3, candidate1, date5);
		assertTrue("Failed to save vote5 entity!", voteDao.save(vote5));

		Date date6 = DateUtils.getDate(2017, 5, 2, 22, 0);
		Vote vote6 = new Vote(voter4, candidate1, date6);
		assertTrue("Failed to save vote6 entity!", voteDao.save(vote6));

		Date date7 = DateUtils.getDate(2017, 5, 5);
		Vote vote7 = new Vote(voter5, candidate3, date7);
		assertTrue("Failed to save vote7 entity!", voteDao.save(vote7));

		Date date8 = DateUtils.getDate(2017, 5, 7);
		Vote vote8 = new Vote(voter6, candidate1, date8);
		assertTrue("Failed to save vote8 entity!", voteDao.save(vote8));

		Date date9 = DateUtils.getDate(2017, 5, 9);
		Vote vote9 = new Vote(voter7, candidate1, date9);
		assertTrue("Failed to save vote9 entity!", voteDao.save(vote9));

		Date date10 = DateUtils.getDate(2017, 5, 10);
		Vote vote10 = new Vote(voter5, candidate3, date10);
		assertTrue("Failed to save vote10 entity!", voteDao.save(vote10));

		Date date11 = DateUtils.getDate(2017, 9, 10);
		Vote vote11 = new Vote(voter6, candidate1, date11);
		assertTrue("Failed to save vote11 entity!", voteDao.save(vote11));
	}

	@Test
	public void testInvalidSave() {
		assertEquals("Save method should fail when given null parameter!", false, voterDao.save(null));
	}

	@Test
	public void testInvalidRemove() {
		assertNull("Remove method should fail when given null parameter!", voterDao.remove(null));
	}

	@Test
	public void testInvalidFind() {
		assertNull("Find method should return null when given null parameter!", voterDao.findById(null));

		assertNull("Find method should return null when given invalid Id parameter!", voterDao.findById(1000L));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUpdate() {
		List<Vote> list = voteDao.findAll();
		Vote vote = list.get(0);

		voteDao.update(vote);
	}

	@Test
	public void testRemove() {
		List<Vote> list = voteDao.findAll();
		Vote vote = voteDao.remove(list.get(3));

		assertNotNull("Failed to remove vote!", vote);
		assertEquals("The voter of the removed vote is not correct", "Quynh", vote.getVoter().getFirstname());
		assertEquals("The candidate of the removed vote is not correct", "Iniesta", vote.getCandidate().getLastname());

		assertEquals("Invalid number of votes, after removing one vote!", 10, voteDao.findAll().size());
	}

	@Test
	public void testFindByCandidate() {
		List<Candidate> candidateList = candidateDao.findAll();
		long candidateId = candidateList.get(0).getId();

		VoteSearchRequest request = VoteSearchRequest.builder().candidateId(candidateId).build();
		List<Vote> voteList = voteDao.findByRequest(request);

		assertEquals("Invalid number of returned votes, after search!", 6, voteList.size());
	}

	@Test
	public void testFindByVoter() {
		List<Voter> voterList = voterDao.findAll();
		long voterId = voterList.get(0).getId();

		VoteSearchRequest request = VoteSearchRequest.builder().voterId(voterId).build();
		List<Vote> voteList = voteDao.findByRequest(request);

		assertEquals("Invalid number of returned votes, after search!", 2, voteList.size());
	}

	@Test
	public void testFindByDates() {
		/** Testing from date **/
		VoteSearchRequest request = VoteSearchRequest.builder().fromDate(DateUtils.getDate(2017, 5, 3)).build();
		List<Vote> voteList = voteDao.findByRequest(request);

		assertEquals("Invalid number of returned votes, after search!", 5, voteList.size());

		/** Testing from and to dates **/
		request = VoteSearchRequest.builder().fromDate(DateUtils.getDate(2017, 5, 2, 1, 11))
				.toDate(DateUtils.getDate(2017, 5, 2, 11, 11)).build();
		voteList = voteDao.findByRequest(request);

		assertEquals("Invalid number of returned votes, after search!", 3, voteList.size());
		assertEquals("First vote in the returned votes is incorrect!", "Quynh",
				voteList.get(0).getVoter().getFirstname());
	}

	@Test
	public void testFindAll() {
		assertEquals("Invalid number of candidates!", 11, voteDao.findAll().size());
	}

}
