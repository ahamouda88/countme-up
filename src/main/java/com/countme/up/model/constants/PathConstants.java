package com.countme.up.model.constants;

/**
 * An interface that contains all the end-points and paths of the application
 * 
 * @author ahamouda
 *
 */
public interface PathConstants {

	public static final String VOTER_MAIN_PATH = "/voters";
	public static final String CANDIDATE_MAIN_PATH = "/candidates";
	/** Votes path constants **/
	public static final String VOTE_MAIN_PATH = "/votes";
	public static final String VOTE_SEARCH_PATH = "/search";
	public static final String VOTE_RESULTS_PATH = "/search";

	public static final String ID_VARIABLE_PATH = "/{id}";
}
