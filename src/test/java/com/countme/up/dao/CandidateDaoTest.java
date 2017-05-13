package com.countme.up.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.countme.up.model.entity.Candidate;
import com.countme.up.spring.config.ApplicationConfig;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = { ApplicationConfig.class, CandidateDao.class })
public class CandidateDaoTest {

	@Autowired
	private CandidateDao candidateDao;

	@Before
	public void testSave() {
		Candidate candidate1 = new Candidate("Ahmed", "Hamouda", "ahmedibrahim@msn.com", null);
		assertTrue("Failed to save first candidate", candidateDao.save(candidate1));

		Candidate candidate2 = new Candidate("Omar", "Ibrahim", "omar@gmail.com", null);
		assertTrue("Failed to save second candidate", candidateDao.save(candidate2));
	}

	@Test
	public void testUpdate() {
		String newEmail = "ahmed.hamouda8888@gmail.com";
		List<Candidate> list = candidateDao.findAll();
		Candidate candidate = list.get(0);
		candidate.setEmail(newEmail);

		// Validate update method
		assertTrue("Failed to update candidate", candidateDao.update(candidate));

		// Validate find method and updated fields
		Candidate newCandidate = candidateDao.findById(candidate.getId());
		assertNotNull("Find candidate is failed, and returning null!", newCandidate);
		assertEquals("Email is not updated!", newEmail, newCandidate.getEmail());
		assertTrue("List of received votes is not empty!", newCandidate.getVotesReceived().isEmpty());
	}

	@Test
	public void testRemove() {
		List<Candidate> list = candidateDao.findAll();
		Candidate candidate = candidateDao.remove(list.get(1));

		assertNotNull("Failed to remove candidate!", candidate);
		assertEquals("Removed candidate is not correct", "Omar", candidate.getFirstname());

		assertEquals("Invalid number of candidates, after removing one candidate!", 1, candidateDao.findAll().size());
	}

	@Test
	public void testFindAll() {
		assertEquals("Invalid number of candidates!", 2, candidateDao.findAll().size());
	}

	@Test
	public void testInvalidSave() {
		assertFalse("Save method should fail when given null parameter!", candidateDao.save(null));
	}

	@Test
	public void testInvalidRemove() {
		assertNull("Remove method should fail when given null parameter!", candidateDao.remove(null));
	}

	@Test
	public void testInvalidFind() {
		assertNull("Find method should return null when given null parameter!", candidateDao.findById(null));

		assertNull("Find method should return null when given invalid Id parameter!", candidateDao.findById(1000L));
	}

	@Test
	public void testInvalidUpdate() {
		assertFalse("Update method should fail when given null parameter!", candidateDao.update(null));
	}

}
