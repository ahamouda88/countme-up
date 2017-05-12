package com.countme.up.dao;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.countme.up.model.entity.Vote;
import com.countme.up.model.request.VoteSearchRequest;

/**
 * A class that extends {@link AbstMainDao}, and it defines the standard operations to be performed on a {@link Vote}
 * entity
 * 
 * @author ahamouda
 *
 */
@Repository
public class VoteDao extends AbstMainDao<Vote, Long> {
	private static final Logger LOGGER = LoggerFactory.getLogger(VoteDao.class);

	public VoteDao() {
		super(Vote.class);
	}

	/**
	 * This method searches through the votes given a {@link VoteSearchRequest} having all the parameters needed to
	 * perform the search
	 * 
	 * @param searchRequest
	 *            a {@link VoteSearchRequest}
	 * @return list of votes if the selection is successful, otherwise it will return <b>null</b>
	 */
	public List<Vote> findByRequest(VoteSearchRequest searchRequest) {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Vote> cq = cb.createQuery(Vote.class);
			Root<Vote> from = cq.from(Vote.class);
			cq.select(from);

			// List of predicates
			List<Predicate> predicates = new LinkedList<>();
			if (searchRequest.getCandidateId() != null) {
				predicates.add(cb.equal(from.get("candidate"), searchRequest.getCandidateId()));
			}
			if (searchRequest.getVoterId() != null) {
				predicates.add(cb.equal(from.get("voter"), searchRequest.getVoterId()));
			}
			if (searchRequest.getPollId() != null) {
				predicates.add(cb.equal(from.get("poll"), searchRequest.getPollId()));
			}
			if (searchRequest.getFromDate() != null) {
				predicates.add(cb.greaterThanOrEqualTo(from.get("date"), searchRequest.getFromDate()));
			}
			if (searchRequest.getToDate() != null) {
				predicates.add(cb.lessThanOrEqualTo(from.get("date"), searchRequest.getToDate()));
			}

			// If there are no predicates then get all votes
			if (!predicates.isEmpty()) {
				cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			}
			TypedQuery<Vote> query = entityManager.createQuery(cq);
			return query.getResultList();
		} catch (Exception ex) {
			LOGGER.error("Failed to Search Votes given the following request: " + searchRequest, ex);
			return null;
		}
	}

	/**
	 * Updating an entity is unsupported for vote entities
	 * 
	 * @throws UnsupportedOperationException
	 *             update vote an is unsupported operation
	 */
	@Override
	public boolean update(Vote vote) {
		throw new UnsupportedOperationException("");
	}
}
