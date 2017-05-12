package com.countme.up.model.entity;

import static org.apache.commons.lang.BooleanUtils.isTrue;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * An entity that represents a voter, with the list of votes
 * 
 * @author ahamouda
 *
 */
@Entity
@Table(name = "voters")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Voter extends Individual implements Serializable {

	private static final long serialVersionUID = 1L;

	@Version
	private long version;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToMany(mappedBy = "voter", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = "voter", allowSetters = true)
	private List<Vote> votes = Collections.emptyList();
	/** Assuming that the voter is always registered **/
	private Boolean registered = true;

	/** Constructors **/
	public Voter() {
	}

	public Voter(String firstname, String lastname, String email) {
		super(firstname, lastname, email);
	}

	/** Setters and Getters **/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Vote> getVotes() {
		return votes;
	}

	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}

	public Boolean getRegistered() {
		return registered;
	}

	public void setRegistered(Boolean registered) {
		this.registered = registered;
	}

	public Boolean canVote(int maxVotes) {
		return isTrue(registered) && (votes == null || votes.size() < maxVotes) ? true : false;
	}

	/** Equals and Hashcode **/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((registered == null) ? 0 : registered.hashCode());
		result = prime * result + (int) (version ^ (version >>> 32));
		result = prime * result + ((votes == null) ? 0 : votes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		Voter other = (Voter) obj;
		if (registered == null) {
			if (other.registered != null) return false;
		} else if (!registered.equals(other.registered)) return false;
		if (version != other.version) return false;
		if (votes == null) {
			if (other.votes != null) return false;
		} else if (!votes.equals(other.votes)) return false;
		return true;
	}

}
