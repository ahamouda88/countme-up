package com.countme.up.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.countme.up.dao.CandidateDao;
import com.countme.up.model.entity.Candidate;
import com.countme.up.service.impl.CandidateServiceImpl;
import com.countme.up.spring.config.ApplicationConfig;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = { ApplicationConfig.class, CandidateServiceImpl.class, CandidateDao.class })
public class CandidateServiceTest {

	@Autowired
	private CandidateService candidateService;

	private long candidateId1;
	private long candidateId2;

	@Before
	public void testAdd() {
		Candidate candidate1 = new Candidate("Lionel", "Messi", "leo@hotmail.com", null);
		assertTrue("Failed to add first candidate!", candidateService.add(candidate1));
		candidateId1 = candidate1.getId();

		Candidate candidate2 = new Candidate("Andres", "Iniesta", "andres@gmail.com", null);
		assertTrue("Failed to add second candidate!", candidateService.add(candidate2));
		candidateId2 = candidate2.getId();
	}

	@Test
	public void testGetAll() {
		assertEquals("Invalid number of candidates!", 2, candidateService.getAll().size());
	}

	@Test
	public void testGet() {
		Candidate candidate = candidateService.get(candidateId1);
		assertNotNull("Candidate object shouldn't be null!", candidate);
		assertEquals("Candidate firstname is incorrect!", "Lionel", candidate.getFirstname());
	}

	@Test
	public void testUpdate() {
		String newEmail = "omaribrahim@gmail.com";
		Candidate candidate = candidateService.get(candidateId2);
		candidate.setEmail(newEmail);

		assertTrue("Failed to update candidate!", candidateService.update(candidate));
		assertEquals("Candidate's email isn't updated!", newEmail, candidateService.get(candidateId2).getEmail());
	}

	@Test
	public void testDelete() {
		Candidate candidate = candidateService.delete(candidateService.get(candidateId2));

		assertNotNull("Failed to delete candidate!", candidate);
		assertEquals("Deleted candidate is  incorrect!", "Andres", candidate.getFirstname());

		assertEquals("Invalid number of candidates, after deleting one candidate!", 1,
				candidateService.getAll().size());
	}

	@Test
	public void testDeleteByKey() {
		Candidate candidate = candidateService.deleteByKey(candidateId1);

		assertNotNull("Failed to delete candidate!", candidate);
		assertEquals("Deleted candidate is  incorrect!", "Messi", candidate.getLastname());

		assertEquals("Invalid number of candidates, after deleting one candidate!", 1,
				candidateService.getAll().size());
	}

	@Test(expected = NullPointerException.class)
	public void testInvalidAdd() {
		candidateService.add(null);
	}

	@Test(expected = NullPointerException.class)
	public void testInvalidDelete() {
		candidateService.delete(null);
	}

	@Test(expected = NullPointerException.class)
	public void testInvalidUpdate() {
		candidateService.update(null);
	}

	@Test(expected = NullPointerException.class)
	public void testInvalidGet() {
		candidateService.get(null);
	}

	@Test
	public void testInvalidGetKey() {
		assertNull("Get method should return null when given invalid Id parameter!", candidateService.get(1000L));
	}
}
