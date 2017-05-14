package com.countme.up.model.exception;

/**
 * An exception that will be thrown if number of votes has been reached by a voter
 * 
 * @author ahamouda
 *
 */
public class MaxNbrOfVotesReachedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final static String DEFAULT_MESSAGE = "Maximum number of votes has been reached";

	public MaxNbrOfVotesReachedException() {
		this(DEFAULT_MESSAGE);
	}

	public MaxNbrOfVotesReachedException(String message) {
		super(message);
	}

}
