package com.countme.up.model;

import java.io.Serializable;

/**
 * A simple class that represents a candidate's Id and name with the number of votes received
 * 
 * @author ahamouda
 *
 */
public class CandidateCount implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long candidateId;
	private String candidateName;
	private Integer NumOfVotes;

	public CandidateCount(Long candidateId, String candidateName, Integer numOfVotes) {
		this.candidateId = candidateId;
		this.candidateName = candidateName;
		NumOfVotes = numOfVotes;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public Integer getNumOfVotes() {
		return NumOfVotes;
	}

	public void setNumOfVotes(Integer numOfVotes) {
		NumOfVotes = numOfVotes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((NumOfVotes == null) ? 0 : NumOfVotes.hashCode());
		result = prime * result + ((candidateId == null) ? 0 : candidateId.hashCode());
		result = prime * result + ((candidateName == null) ? 0 : candidateName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		CandidateCount other = (CandidateCount) obj;
		if (NumOfVotes == null) {
			if (other.NumOfVotes != null) return false;
		} else if (!NumOfVotes.equals(other.NumOfVotes)) return false;
		if (candidateId == null) {
			if (other.candidateId != null) return false;
		} else if (!candidateId.equals(other.candidateId)) return false;
		if (candidateName == null) {
			if (other.candidateName != null) return false;
		} else if (!candidateName.equals(other.candidateName)) return false;
		return true;
	}

}
