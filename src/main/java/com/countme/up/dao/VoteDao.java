package com.countme.up.dao;

import org.springframework.stereotype.Repository;

import com.countme.up.model.entity.Vote;

/**
 * A class that extends {@link AbstMainDao}, and it defines the standard operations to be performed on a {@link Vote} entity
 * 
 * @author ahamouda
 *
 */
@Repository
public class VoteDao extends AbstMainDao<Vote, Long> {

	public VoteDao() {
		super(Vote.class);
	}

}
