package com.countme.up.model.entity;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

/**
 * An entity that represents an individual and contains main individual information
 * 
 * @author ahamouda
 *
 */
@MappedSuperclass
public abstract class Individual implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Assuming non of these fields are mandatory **/
	private String firstname;
	private String lastname;
	private String email;

	/** Constructors **/
	public Individual() {
	}

	public Individual(String firstname, String lastname, String email) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
	}

	/** Setters and Getters **/
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/** Equals and Hashcode **/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Individual other = (Individual) obj;
		if (email == null) {
			if (other.email != null) return false;
		} else if (!email.equals(other.email)) return false;
		if (firstname == null) {
			if (other.firstname != null) return false;
		} else if (!firstname.equals(other.firstname)) return false;
		if (lastname == null) {
			if (other.lastname != null) return false;
		} else if (!lastname.equals(other.lastname)) return false;
		return true;
	}

}
