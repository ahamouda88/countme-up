package com.countme.up.service.impl;

import static com.countme.up.utils.ParametersUtils.checkNullParameters;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.countme.up.dao.CandidateDao;
import com.countme.up.dao.VoteDao;
import com.countme.up.dao.VoterDao;
import com.countme.up.model.entity.Candidate;
import com.countme.up.model.entity.Vote;
import com.countme.up.model.entity.Voter;
import com.countme.up.model.request.VoteSearchRequest;
import com.countme.up.service.VoteService;

/**
 * A class that implements {@link VoteService}
 * 
 * @author ahamouda
 *
 */
@Service

public class VoteServiceImpl implements VoteService {

	@Autowired
	private VoteDao voteDao;

	@Autowired
	private VoterDao voterDao;

	@Autowired
	private CandidateDao candidateDao;

	/**
	 * @see VoteService#create(Long, Long)
	 */
	@Override
	public Vote create(Long voterId, Long candidateId) {
		checkNullParameters(voterId, candidateId);

		/** Get voter and candidate using the given Ids **/
		Voter voter = voterDao.findById(voterId);
		Candidate candidate = candidateDao.findById(candidateId);
		Date date = Calendar.getInstance().getTime();

		Vote vote = new Vote(voter, candidate, date);

		return this.add(vote) ? vote : null;
	}

	/**
	 * @see VoteService#add(Vote)
	 */
	@Override
	public boolean add(Vote vote) {
		checkNullParameters(vote, vote.getVoter(), vote.getCandidate(), vote.getDate());

		boolean check = voteDao.save(vote);
		// If vote is successfully added then update the list of votes in both voter, and candidate
		if (!check) return false;

		vote.getVoter().getVotes().add(vote);
		vote.getCandidate().getVotesReceived().add(vote);

		return true;
	}

	/**
	 * @see VoteService#delete(Vote)
	 */
	@Override
	public Vote delete(Vote vote) {
		checkNullParameters(vote);

		Vote removedVote = voteDao.remove(vote);
		// If removed vote is not null, then remove the vote from the list of votes in both voter, and candidate
		if (removedVote == null) return null;
		removedVote.getVoter().getVotes().remove(removedVote);
		removedVote.getCandidate().getVotesReceived().remove(removedVote);

		return removedVote;
	}

	/**
	 * @see VoteService#deleteByKey(Long)
	 */
	@Override
	public Vote deleteByKey(Long key) {
		Vote vote = get(key);

		return this.delete(vote);
	}

	/**
	 * @see VoteService#update(Vote)
	 */
	@Override
	public boolean update(Vote vote) {
		throw new UnsupportedOperationException("Update operation is invalid for votes");
	}

	/**
	 * @see VoteService#get(Long)
	 */
	@Override
	public Vote get(Long key) {
		checkNullParameters(key);

		return voteDao.findById(key);
	}

	/**
	 * @see VoteService#getAll()
	 */
	@Override
	public List<Vote> getAll() {
		return voteDao.findAll();
	}

	/**
	 * @see VoteService#search(VoteSearchRequest)
	 */
	@Override
	public List<Vote> search(VoteSearchRequest searchRequest) {
		checkNullParameters(searchRequest);

		return voteDao.findByRequest(searchRequest);
	}
}
