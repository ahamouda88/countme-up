package com.countme.up.model.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * An entity that represents a vote, that contains a voter, a candidate, and the associated poll
 * 
 * @author ahamouda
 *
 */
public class Vote implements Serializable {

	private static final long serialVersionUID = 1L;

	@Version
	private long version;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Voter voter;
	private Candidate candidate;
	private time
}
