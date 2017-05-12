package com.countme.up.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * An entity that represents a vote, that contains a voter, a candidate, and the associated poll
 * 
 * @author ahamouda
 *
 */
@Entity
@Table(name = "votes")
public class Vote implements Serializable {

	private static final long serialVersionUID = 1L;

	@Version
	private long version;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "voter_id", nullable = false)
	@JsonIgnoreProperties(value = "votes", allowSetters = true)
	private Voter voter;
	@ManyToOne
	@JoinColumn(name = "candidate_id", nullable = false)
	@JsonIgnoreProperties(value = "votesReceived", allowSetters = true)
	private Candidate candidate;
	@NotNull
	private Date date;

	/** Constructors **/
	public Vote() {
	}

	public Vote(Voter voter, Candidate candidate, Date date) {
		this.voter = voter;
		this.candidate = candidate;
		this.date = date;
	}

	/** Setters and Getters **/
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Voter getVoter() {
		return voter;
	}

	public void setVoter(Voter voter) {
		this.voter = voter;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/** Equals and Hashcode **/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((candidate == null) ? 0 : candidate.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((voter == null) ? 0 : voter.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Vote other = (Vote) obj;
		if (candidate == null) {
			if (other.candidate != null) return false;
		} else if (!candidate.equals(other.candidate)) return false;
		if (date == null) {
			if (other.date != null) return false;
		} else if (!date.equals(other.date)) return false;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		if (voter == null) {
			if (other.voter != null) return false;
		} else if (!voter.equals(other.voter)) return false;
		return true;
	}

}
