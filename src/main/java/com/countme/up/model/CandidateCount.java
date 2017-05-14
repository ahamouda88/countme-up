package com.countme.up.model;

import java.io.Serializable;

import com.countme.up.model.entity.Candidate;

/**
 * A simple class that represents a {@link Candidate} with the number of votes received
 * 
 * @author ahamouda
 *
 */
public class CandidateCount implements Serializable {

	private static final long serialVersionUID = 1L;

	private Candidate candidate;
	private Long votes;

	public CandidateCount(Candidate candidate, Long votes) {
		this.candidate = candidate;
		this.votes = votes;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Long getVotes() {
		return votes;
	}

	public void setVotes(Long votes) {
		this.votes = votes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((candidate == null) ? 0 : candidate.hashCode());
		result = prime * result + ((votes == null) ? 0 : votes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		CandidateCount other = (CandidateCount) obj;
		if (candidate == null) {
			if (other.candidate != null) return false;
		} else if (!candidate.equals(other.candidate)) return false;
		if (votes == null) {
			if (other.votes != null) return false;
		} else if (!votes.equals(other.votes)) return false;
		return true;
	}

}
