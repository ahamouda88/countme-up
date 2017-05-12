package com.countme.up.dao;

import org.springframework.stereotype.Repository;

import com.countme.up.model.entity.Candidate;

/**
 * A class that extends {@link AbstMainDao}, and it defines the standard operations to be performed on a {@link Candidate}
 * entity
 * 
 * @author ahamouda
 *
 */
@Repository
public class CandidateDao extends AbstMainDao<Candidate, Long> {

	public CandidateDao() {
		super(Candidate.class);
	}

}
