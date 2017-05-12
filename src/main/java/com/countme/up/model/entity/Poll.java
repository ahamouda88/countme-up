package com.countme.up.model.entity;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * An entity that represents a poll, that contains a map with all participating candidates and their votes
 * 
 * @author ahamouda
 *
 */
@Entity
@Table(name = "polls")
public class Poll implements Serializable {

	private static final long serialVersionUID = 1L;

	@Version
	private long version;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;
	private String description;
	private Map<Candidate, List<Vote>> candidateVotes = Collections.emptyMap();

	/** Constructors **/
	public Poll() {
	}

	public Poll(String title, String description) {
		this.title = title;
		this.description = description;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<Candidate, List<Vote>> getCandidateVotes() {
		return candidateVotes;
	}

	public void setCandidateVotes(Map<Candidate, List<Vote>> candidateVotes) {
		this.candidateVotes = candidateVotes;
	}

	/** Equals and Hashcode **/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((candidateVotes == null) ? 0 : candidateVotes.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Poll other = (Poll) obj;
		if (candidateVotes == null) {
			if (other.candidateVotes != null) return false;
		} else if (!candidateVotes.equals(other.candidateVotes)) return false;
		if (description == null) {
			if (other.description != null) return false;
		} else if (!description.equals(other.description)) return false;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		if (title == null) {
			if (other.title != null) return false;
		} else if (!title.equals(other.title)) return false;
		return true;
	}

}
