package com.countme.up.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.countme.up.model.CandidateCount;
import com.countme.up.model.constants.PathConstants;
import com.countme.up.model.entity.Vote;
import com.countme.up.model.exception.MaxNbrOfVotesReachedException;
import com.countme.up.model.request.VoteSearchRequest;
import com.countme.up.model.response.BaseResponse;
import com.countme.up.service.VoteService;

@RestController
@RequestMapping(value = PathConstants.VOTE_MAIN_PATH)
public class VoteController implements ControllerCommonMethods {

	private static final String DATE_FORMAT_STRING = "MM-dd-yyyy:HH:mm:ss";

	@Autowired
	private VoteService voteService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<BaseResponse> addVote(@RequestParam(name = "cid") Long candidateId,
			@RequestParam(name = "vid") Long voterId,
			@RequestParam(name = "date", required = false) @DateTimeFormat(pattern = DATE_FORMAT_STRING) Date date) {

		List<String> errors = new LinkedList<>();
		HttpStatus failHttpStatus = HttpStatus.BAD_REQUEST;
		Vote vote = null;
		try {
			vote = voteService.create(voterId, candidateId, date);
		} catch (MaxNbrOfVotesReachedException ex) {
			errors.add(ex.getMessage());
			failHttpStatus = HttpStatus.FORBIDDEN;
		} catch (Exception ex) {
			errors.add(
					String.format("Failed to add vote given candidate Id: %s and Voter Id: %s", candidateId, voterId));
		}
		return createBaseResponse(HttpStatus.CREATED, failHttpStatus, errors, vote);
	}

	@RequestMapping(method = RequestMethod.DELETE, path = PathConstants.ID_VARIABLE_PATH)
	public ResponseEntity<BaseResponse> deleteVote(@PathVariable("id") Long voteId) {

		List<String> errors = new LinkedList<>();
		Vote vote = null;
		try {
			vote = voteService.deleteByKey(voteId);
		} catch (Exception ex) {
			errors.add(String.format("Failed to delete vote with the following Id %s", voteId));
		}
		return createBaseResponse(HttpStatus.OK, HttpStatus.BAD_REQUEST, errors, vote);
	}

	@RequestMapping(method = RequestMethod.GET, path = PathConstants.ID_VARIABLE_PATH)
	public ResponseEntity<BaseResponse> getVote(@PathVariable("id") Long voteId) {

		List<String> errors = new LinkedList<>();
		Vote vote = null;
		try {
			vote = voteService.get(voteId);
			if (vote == null) {
				errors.add(String.format("Vote with the following Id %s doesn't exist", voteId));
			}
		} catch (Exception ex) {
			errors.add(String.format("Failed to get vote given vote Id: %s", voteId));
		}
		return createBaseResponse(HttpStatus.OK, HttpStatus.BAD_REQUEST, errors, vote);
	}

	@RequestMapping(method = RequestMethod.GET, path = PathConstants.VOTE_RESULTS_PATH)
	public ResponseEntity<BaseResponse> getResults(@RequestParam(name = "cid", required = false) Long candidateId,
			@RequestParam(name = "vid", required = false) Long voterId,
			@RequestParam(name = "fdate", required = false) @DateTimeFormat(pattern = DATE_FORMAT_STRING) Date fromDate,
			@RequestParam(name = "tdate", required = false) @DateTimeFormat(pattern = DATE_FORMAT_STRING) Date toDate) {

		List<String> errors = new LinkedList<>();
		CandidateCount[] candidateCount = null;
		try {
			VoteSearchRequest searchRequest = VoteSearchRequest.builder().candidateId(candidateId).voterId(voterId)
					.fromDate(fromDate).toDate(toDate).build();
			candidateCount = voteService.getResults(searchRequest);
		} catch (Exception ex) {
			errors.add(String.format("Failed to get vote results!"));
		}
		return createBaseResponse(HttpStatus.OK, HttpStatus.BAD_REQUEST, errors, candidateCount);
	}

	@RequestMapping(method = RequestMethod.GET, path = PathConstants.VOTE_SEARCH_PATH)
	public ResponseEntity<BaseResponse> searchVotes(@RequestParam(name = "cid", required = false) Long candidateId,
			@RequestParam(name = "vid", required = false) Long voterId,
			@RequestParam(name = "fdate", required = false) @DateTimeFormat(pattern = DATE_FORMAT_STRING) Date fromDate,
			@RequestParam(name = "tdate", required = false) @DateTimeFormat(pattern = DATE_FORMAT_STRING) Date toDate) {

		List<String> errors = new LinkedList<>();
		List<Vote> votes = null;
		try {
			VoteSearchRequest searchRequest = VoteSearchRequest.builder().candidateId(candidateId).voterId(voterId)
					.fromDate(fromDate).toDate(toDate).build();
			votes = voteService.search(searchRequest);
		} catch (Exception ex) {
			errors.add(String.format("Failed to do search on votes!"));
		}
		return createBaseResponse(HttpStatus.OK, HttpStatus.BAD_REQUEST, errors, votes);
	}
}
