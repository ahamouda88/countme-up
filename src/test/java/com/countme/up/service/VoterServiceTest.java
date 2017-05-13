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

import com.countme.up.dao.VoterDao;
import com.countme.up.model.entity.Voter;
import com.countme.up.service.impl.VoterServiceImpl;
import com.countme.up.spring.config.ApplicationConfig;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = { ApplicationConfig.class, VoterServiceImpl.class, VoterDao.class })
public class VoterServiceTest {

	@Autowired
	private VoterService voterService;

	private long voterId1;
	private long voterId2;

	@Before
	public void testAdd() {
		Voter voter1 = new Voter("Ahmed", "Hamouda", "ahmedibrahim@msn.com");
		assertTrue("Failed to add first voter!", voterService.add(voter1));
		assertTrue("First voter is not registered by default!", voter1.getRegistered());
		voterId1 = voter1.getId();

		Voter voter2 = new Voter("Omar", "Ibrahim", "omar@gmail.com");
		assertTrue("Failed to add second voter!", voterService.add(voter2));
		assertTrue("Second voter is not registered by default!", voter2.getRegistered());
		voterId2 = voter2.getId();
	}

	@Test
	public void testGetAll() {
		assertEquals("Invalid number of voters!", 2, voterService.getAll().size());
	}

	@Test
	public void testGet() {
		Voter voter = voterService.get(voterId1);
		assertNotNull("Voter object shouldn't be null!", voter);
		assertEquals("Voter firstname is incorrect!", "Ahmed", voter.getFirstname());
	}

	@Test
	public void testUpdate() {
		String newEmail = "omaribrahim@gmail.com";
		Voter voter = voterService.get(voterId2);
		voter.setEmail(newEmail);

		assertTrue("Failed to update voter!", voterService.update(voter));
		assertEquals("Voter's email isn't updated!", newEmail, voterService.get(voterId2).getEmail());
	}

	@Test
	public void testDelete() {
		Voter voter = voterService.delete(voterService.get(voterId2));

		assertNotNull("Failed to delete voter!", voter);
		assertEquals("Deleted voter is  incorrect!", "Omar", voter.getFirstname());

		assertEquals("Invalid number of voters, after deleting one voter!", 1, voterService.getAll().size());
	}

	@Test
	public void testDeleteByKey() {
		Voter voter = voterService.deleteByKey(voterId1);

		assertNotNull("Failed to delete voter!", voter);
		assertEquals("Deleted voter is  incorrect!", "Hamouda", voter.getLastname());

		assertEquals("Invalid number of voters, after deleting one voter!", 1, voterService.getAll().size());
	}

	@Test(expected = NullPointerException.class)
	public void testInvalidAdd() {
		voterService.add(null);
	}

	@Test(expected = NullPointerException.class)
	public void testInvalidDelete() {
		voterService.delete(null);
	}

	@Test(expected = NullPointerException.class)
	public void testInvalidUpdate() {
		voterService.update(null);
	}

	@Test(expected = NullPointerException.class)
	public void testInvalidGet() {
		voterService.get(null);
	}

	@Test
	public void testInvalidGetKey() {
		assertNull("Get method should return null when given invalid Id parameter!", voterService.get(1000L));
	}
}
