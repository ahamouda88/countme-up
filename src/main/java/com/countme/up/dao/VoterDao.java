package com.countme.up.dao;

import org.springframework.stereotype.Repository;

import com.countme.up.model.entity.Voter;

/**
 * A class that extends {@link AbstMainDao}, and it defines the standard operations to be performed on a {@link Voter}
 * entity
 * 
 * @author ahamouda
 *
 */
@Repository
public class VoterDao extends AbstMainDao<Voter, Long> {

	public VoterDao() {
		super(Voter.class);
	}

}
