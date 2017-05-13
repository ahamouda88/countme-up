package com.countme.up.service;

import java.util.List;

import com.countme.up.model.entity.Vote;
import com.countme.up.model.request.VoteSearchRequest;

/**
 * An interface that defines the business logic for performing main/basic operations on a {@link Vote}
 * 
 * @author ahamouda
 * 
 */
public interface VoteService extends MainService<Vote, Long> {

	/**
	 * A method that creates a {@link Vote}, given a voter's Id and a candidate's Id
	 * 
	 * @param voterId
	 *            a voter's Id
	 * @param candidateId
	 *            a candidate's Id
	 * @return the new created vote if added successfully, otherwise return <i><b>null</b></i>
	 */
	public abstract Vote create(Long voterId, Long candidateId);

	/**
	 * A method that searches through the votes given a {@link VoteSearchRequest} having all the parameters needed to
	 * perform the search
	 * 
	 * @param searchRequest
	 *            a {@link VoteSearchRequest}
	 * @return list of votes if the selection is successful, otherwise it will return <i><b>null</b></i>
	 */
	public abstract List<Vote> search(VoteSearchRequest searchRequest);
}
