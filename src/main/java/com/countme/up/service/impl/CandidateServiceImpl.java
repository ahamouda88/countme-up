package com.countme.up.service.impl;

import static com.countme.up.utils.ParametersUtils.checkNullParameters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.countme.up.dao.CandidateDao;
import com.countme.up.model.entity.Candidate;
import com.countme.up.service.CandidateService;

/**
 * A class that implements {@link CandidateService}
 * 
 * @author ahamouda
 *
 */
@Service
public class CandidateServiceImpl implements CandidateService {

	@Autowired
	private CandidateDao candidateDao;

	/**
	 * @see CandidateService#add(Candidate)
	 */
	@Override
	public boolean add(Candidate candidate) {
		checkNullParameters(candidate);

		return candidateDao.save(candidate);
	}

	/**
	 * @see CandidateService#delete(Candidate)
	 */
	@Override
	public Candidate delete(Candidate candidate) {
		checkNullParameters(candidate);

		return candidateDao.remove(candidate);
	}

	/**
	 * @see CandidateService#deleteByKey(Long)
	 */
	@Override
	public Candidate deleteByKey(Long key) {
		Candidate candidate = get(key);

		return candidateDao.remove(candidate);
	}

	/**
	 * @see CandidateService#update(Candidate)
	 */
	@Override
	public boolean update(Candidate candidate) {
		checkNullParameters(candidate);

		return candidateDao.update(candidate);
	}

	/**
	 * @see CandidateService#get(Long)
	 */
	@Override
	public Candidate get(Long key) {
		checkNullParameters(key);

		return candidateDao.findById(key);
	}

	/**
	 * @see CandidateService#getAll()
	 */
	@Override
	public List<Candidate> getAll() {
		return candidateDao.findAll();
	}
}
