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
	 * This method searches through the votes given a {@link VoteSearchRequest} having all the parameters needed to
	 * perform the search
	 * 
	 * @param searchRequest
	 *            a {@link VoteSearchRequest}
	 * @return list of votes if the selection is successful, otherwise it will return <i><b>null</b></i>
	 */
	public abstract List<Vote> search(VoteSearchRequest searchRequest);
}
