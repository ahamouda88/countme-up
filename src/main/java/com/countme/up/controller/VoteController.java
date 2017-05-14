package com.countme.up.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.countme.up.model.constants.PathConstants;
import com.countme.up.model.entity.Vote;
import com.countme.up.model.response.BaseResponse;
import com.countme.up.service.VoteService;

@RestController
@RequestMapping(value = PathConstants.VOTE_MAIN_PATH)
public class VoteController implements ControllerCommonMethods {

	@Autowired
	private VoteService voteService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<BaseResponse> addVote(@RequestParam(name = "cid") Long candidateId,
			@RequestParam(name = "vid") Long voterId, @RequestParam(name = "date", required = false) Date date) {

		List<String> errors = new LinkedList<>();
		Vote vote = null;
		try {
			vote = voteService.create(voterId, candidateId, date);
		} catch (Exception ex) {
			errors.add(ex.getMessage());
		}
		return createBaseResponse(HttpStatus.CREATED, HttpStatus.BAD_REQUEST, errors, vote);
	}

	@RequestMapping(method = RequestMethod.DELETE, path = PathConstants.ID_VARIABLE_PATH)
	public ResponseEntity<BaseResponse> deleteVote(@PathVariable("id") Long voteId) {

		List<String> errors = new LinkedList<>();
		Vote vote = null;
		try {
			vote = voteService.deleteByKey(voteId);
		} catch (Exception ex) {
			errors.add(ex.getMessage());
		}
		return createBaseResponse(HttpStatus.OK, HttpStatus.BAD_REQUEST, errors, vote);
	}

	@RequestMapping(method = RequestMethod.GET, path = PathConstants.ID_VARIABLE_PATH)
	public ResponseEntity<BaseResponse> getVote(@PathVariable("id") Long voteId) {

		List<String> errors = new LinkedList<>();
		Vote vote = null;
		try {
			vote = voteService.get(voteId);
		} catch (Exception ex) {
			errors.add(ex.getMessage());
		}
		return createBaseResponse(HttpStatus.OK, HttpStatus.BAD_REQUEST, errors, vote);
	}
}
