package com.countme.up.service.impl;

import static com.countme.up.utils.ParametersUtils.checkNullParameters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.countme.up.dao.VoterDao;
import com.countme.up.model.entity.Voter;
import com.countme.up.service.VoterService;

/**
 * A class that implements {@link VoterService}
 * 
 * @author ahamouda
 *
 */
@Service
@Transactional
public class VoterServiceImpl implements VoterService {

	@Autowired
	private VoterDao voterDao;

	/**
	 * @see VoterService#add(Voter)
	 */
	@Override
	public boolean add(Voter voter) {
		checkNullParameters(voter);

		return voterDao.save(voter);
	}

	/**
	 * @see VoterService#delete(Voter)
	 */
	@Override
	public Voter delete(Voter voter) {
		if (voter == null) return null;

		return voterDao.remove(voter);
	}

	/**
	 * @see VoterService#deleteByKey(Long)
	 */
	@Override
	public Voter deleteByKey(Long key) {
		Voter voter = get(key);

		return this.delete(voter);
	}

	/**
	 * @see VoterService#update(Voter)
	 */
	@Override
	public boolean update(Voter voter) {
		checkNullParameters(voter);

		return voterDao.update(voter);
	}

	/**
	 * @see VoterService#get(Long)
	 */
	@Override
	public Voter get(Long key) {
		checkNullParameters(key);

		return voterDao.findById(key);
	}

	/**
	 * @see VoterService#getAll()
	 */
	@Override
	public List<Voter> getAll() {
		return voterDao.findAll();
	}

}
