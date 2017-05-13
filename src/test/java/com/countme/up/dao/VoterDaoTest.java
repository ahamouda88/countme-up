package com.countme.up.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.countme.up.model.entity.Voter;
import com.countme.up.spring.config.ApplicationConfig;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = { ApplicationConfig.class, VoterDao.class })
public class VoterDaoTest {

	@Autowired
	private VoterDao voterDao;

	@Before
	public void testSave() {
		Voter voter1 = new Voter("Ahmed", "Hamouda", "ahmedibrahim@msn.com");
		assertTrue("Failed to save first voter!", voterDao.save(voter1));
		assertTrue("First voter is not registered by default!", voter1.getRegistered());

		Voter voter2 = new Voter("Omar", "Ibrahim", "omar@gmail.com");
		assertTrue("Failed to save second voter!", voterDao.save(voter2));
		assertTrue("Second voter is not registered by default!", voter2.getRegistered());
	}

	@Test
	public void testUpdate() {
		String newEmail = "ahmed.hamouda8888@gmail.com";
		List<Voter> list = voterDao.findAll();
		Voter voter = list.get(0);
		voter.setEmail(newEmail);

		// Validate update method
		assertEquals("Failed to update voter", true, voterDao.update(voter));

		// Validate find method and updated fields
		Voter newVoter = voterDao.findById(voter.getId());
		assertNotNull("Find voter is failed, and returning null!", newVoter);
		assertEquals("Email is not updated!", newEmail, newVoter.getEmail());
		assertTrue("List of voters is not empty!", newVoter.getVotes().isEmpty());
	}

	@Test
	public void testRemove() {
		List<Voter> list = voterDao.findAll();
		Voter voter = voterDao.remove(list.get(1));

		assertNotNull("Failed to remove voter!", voter);
		assertEquals("Removed voter is not correct", "Omar", voter.getFirstname());

		assertEquals("Invalid number of voters, after removing one voter!", 1, voterDao.findAll().size());
	}

	@Test
	public void testFindAll() {
		assertEquals("Invalid number of voters!", 2, voterDao.findAll().size());
	}

	@Test
	public void testInvalidSave() {
		assertFalse("Save method should fail when given null parameter!", voterDao.save(null));
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

	@Test
	public void testInvalidUpdate() {
		assertFalse("Update method should fail when given null parameter!", voterDao.update(null));
	}

}
