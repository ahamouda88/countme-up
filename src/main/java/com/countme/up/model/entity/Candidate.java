package com.countme.up.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * An entity that represents a candidate
 * 
 * @author ahamouda
 *
 */
@Entity
@Table(name = "candidates")
public class Candidate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Version
	private long version;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String programDesc;

}
