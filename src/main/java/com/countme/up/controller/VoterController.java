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
import com.countme.up.model.response.ResponseStatus;
import com.countme.up.service.VoterService;

@RestController
@RequestMapping(value = PathConstants.VOTER_MAIN_PATH)
public class VoterController {

	@Autowired
	private VoterService voterService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<BaseResponse> getAllVoters() {
		List<Voter> voters = voterService.getAll();

		HttpStatus httpStatus;
		String message;
		if (voters == null) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			message = "Failed";
		} else {
			httpStatus = HttpStatus.OK;
			message = "Success";
		}

		return createBaseResponse(httpStatus, null, message, voters);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<BaseResponse> addVoter(@RequestBody Voter voter) {
		List<String> errors = new LinkedList<>();
		HttpStatus httpStatus;
		String message;

		if (voter == null) {
			errors.add("Voter parameter shouldn't be null");
			httpStatus = HttpStatus.BAD_REQUEST;
			message = "Failed";
		} else {
			boolean check = voterService.add(voter);
			if (check) {
				httpStatus = HttpStatus.CREATED;
				message = "Success";
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
				message = "Failed";
			}
		}
		return createBaseResponse(httpStatus, errors, message, voter);
	}

	@RequestMapping(method = RequestMethod.GET, path = PathConstants.ID_VARIABLE_PATH)
	public ResponseEntity<BaseResponse> getVoter(@PathVariable(name = "id") Long voterId) {
		List<String> errors = new LinkedList<>();
		HttpStatus httpStatus;
		String message;
		Voter voter;

		if (voterId == null) {
			errors.add("Voter Id parameter shouldn't be null");
			httpStatus = HttpStatus.BAD_REQUEST;
			message = "Failed";
			voter = null;
		} else {
			voter = voterService.get(voterId);
			if (voter != null) {
				httpStatus = HttpStatus.OK;
				message = "Success";
			} else {
				errors.add(String.format("Voter with the following Id %s doesn't exist", voterId));
				httpStatus = HttpStatus.NO_CONTENT;
				message = "Failed";
			}
		}
		return createBaseResponse(httpStatus, errors, message, voter);
	}

	@RequestMapping(method = RequestMethod.DELETE, path = PathConstants.ID_VARIABLE_PATH)
	public ResponseEntity<BaseResponse> deleteVoter(@PathVariable(name = "id") Long voterId) {
		List<String> errors = new LinkedList<>();
		HttpStatus httpStatus;
		String message;
		Voter voter;

		if (voterId == null) {
			errors.add("Voter Id parameter shouldn't be null");
			httpStatus = HttpStatus.BAD_REQUEST;
			message = "Failed";
			voter = null;
		} else {
			voter = voterService.deleteByKey(voterId);
			if (voter != null) {
				httpStatus = HttpStatus.OK;
				message = "Success";
			} else {
				errors.add(String.format("Voter with the following Id %s doesn't exist", voterId));
				httpStatus = HttpStatus.NO_CONTENT;
				message = "Failed";
			}
		}
		return createBaseResponse(httpStatus, errors, message, voter);
	}

	private ResponseEntity<BaseResponse> createBaseResponse(HttpStatus httpStatus, List<String> errors, String message,
			Object data) {
		ResponseStatus status = ResponseStatus.builder().code(httpStatus.value()).errors(errors).message(message)
				.build();
		return new ResponseEntity<>(BaseResponse.builder().data(data).status(status).build(), httpStatus);
	}
}
