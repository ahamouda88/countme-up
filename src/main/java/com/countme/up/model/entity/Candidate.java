package com.countme.up.model.entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * An entity that represents a candidate
 * 
 * @author ahamouda
 *
 */
@Entity
@Table(name = "candidates")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonInclude(value = Include.NON_EMPTY)
public class Candidate extends Individual implements Serializable {

	private static final long serialVersionUID = 1L;

	@Version
	private long version;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String programDesc;

	@OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value = "candidate", allowSetters = true)
	private List<Vote> votesReceived = new LinkedList<>();

	/** Constructors **/
	public Candidate() {
	}

	public Candidate(String firstname, String lastname, String email, String programDesc) {
		super(firstname, lastname, email);
		this.programDesc = programDesc;
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

	public String getProgramDesc() {
		return programDesc;
	}

	public void setProgramDesc(String programDesc) {
		this.programDesc = programDesc;
	}

	public List<Vote> getVotesReceived() {
		return votesReceived;
	}

	public void setVotesReceived(List<Vote> votesReceived) {
		this.votesReceived = votesReceived;
	}

	/** Equals and Hashcode **/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((programDesc == null) ? 0 : programDesc.hashCode());
		result = prime * result + ((votesReceived == null) ? 0 : votesReceived.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		Candidate other = (Candidate) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		if (programDesc == null) {
			if (other.programDesc != null) return false;
		} else if (!programDesc.equals(other.programDesc)) return false;
		if (votesReceived == null) {
			if (other.votesReceived != null) return false;
		} else if (!votesReceived.equals(other.votesReceived)) return false;
		return true;
	}

}
