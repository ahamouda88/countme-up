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
		assertEquals("Failed to save first voter", true, voterDao.save(voter1));

		Voter voter2 = new Voter("Omar", "Ibrahim", "omar@gmail.com");
		assertEquals("Failed to save second voter", true, voterDao.save(voter2));
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
		assertNull("Find method should return null when given null parameter!", voterDao.find(null));

		assertNull("Find method should return null when given invalid Id parameter!", voterDao.find(1000L));
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
		Voter newVoter = voterDao.find(voter.getId());
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
	public void testInvalidUpdate() {
		assertEquals("Update method should fail when given null parameter!", false, voterDao.update(null));
	}

	@Test
	public void testFindAll() {
		assertEquals("Invalid number of voters!", 2, voterDao.findAll().size());
	}

}
