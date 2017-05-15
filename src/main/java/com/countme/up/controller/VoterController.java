package com.countme.up.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.countme.up.model.constants.PathConstants;
import com.countme.up.model.entity.Voter;
import com.countme.up.model.response.BaseResponse;
import com.countme.up.service.VoterService;

@RestController
@RequestMapping(value = PathConstants.VOTER_MAIN_PATH)
public class VoterController implements ControllerCommonMethods {

	@Autowired
	private VoterService voterService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<BaseResponse> getAllVoters() {
		List<Voter> voters = voterService.getAll();

		return createBaseResponse(HttpStatus.OK, HttpStatus.INTERNAL_SERVER_ERROR, null, voters);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<BaseResponse> addVoter(@RequestBody Voter voter) {
		List<String> errors = new LinkedList<>();
		try {
			boolean check = voterService.add(voter);
			if (!check) voter = null;
		} catch (Exception ex) {
			errors.add(String.format("Failed to add Voter!"));
		}
		return createBaseResponse(HttpStatus.CREATED, HttpStatus.BAD_REQUEST, errors, voter);
	}

	@RequestMapping(method = RequestMethod.GET, path = PathConstants.ID_VARIABLE_PATH)
	public ResponseEntity<BaseResponse> getVoter(@PathVariable(name = "id") Long voterId) {
		List<String> errors = new LinkedList<>();
		Voter voter = null;
		try {
			voter = voterService.get(voterId);
			if (voter == null) {
				errors.add(String.format("Voter with the following Id %s doesn't exist", voterId));
			}
		} catch (Exception ex) {
			errors.add(String.format("Failed to get Voter!"));
		}
		return createBaseResponse(HttpStatus.OK, HttpStatus.BAD_REQUEST, errors, voter);
	}

	@RequestMapping(method = RequestMethod.DELETE, path = PathConstants.ID_VARIABLE_PATH)
	public ResponseEntity<BaseResponse> deleteVoter(@PathVariable(name = "id") Long voterId) {
		List<String> errors = new LinkedList<>();
		Voter voter = null;
		try {
			voter = voterService.deleteByKey(voterId);
			if (voter == null) {
				errors.add(String.format("Voter with the following Id %s doesn't exist", voterId));
			}
		} catch (Exception ex) {
			errors.add(String.format("Failed to delete Voter!"));
		}
		return createBaseResponse(HttpStatus.OK, HttpStatus.BAD_REQUEST, errors, voter);
	}
}
