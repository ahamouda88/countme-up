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
import com.countme.up.model.entity.Candidate;
import com.countme.up.model.response.BaseResponse;
import com.countme.up.model.response.ResponseStatus;
import com.countme.up.service.CandidateService;

@RestController
@RequestMapping(value = PathConstants.CANDIDATE_MAIN_PATH)
public class CandidateController {

	@Autowired
	private CandidateService candidateService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<BaseResponse> getAllCandidates() {
		List<Candidate> candidates = candidateService.getAll();

		HttpStatus httpStatus;
		String message;
		if (candidates == null) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			message = "Failed";
		} else {
			httpStatus = HttpStatus.OK;
			message = "Success";
		}

		return createBaseResponse(httpStatus, null, message, candidates);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<BaseResponse> addCandidate(@RequestBody Candidate candidate) {
		List<String> errors = new LinkedList<>();
		HttpStatus httpStatus;
		String message;

		if (candidate == null) {
			errors.add("Candidate parameter shouldn't be null");
			httpStatus = HttpStatus.BAD_REQUEST;
			message = "Failed";
		} else {
			boolean check = candidateService.add(candidate);
			if (check) {
				httpStatus = HttpStatus.CREATED;
				message = "Success";
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
				message = "Failed";
			}
		}
		return createBaseResponse(httpStatus, errors, message, candidate);
	}

	@RequestMapping(method = RequestMethod.GET, path = PathConstants.ID_VARIABLE_PATH)
	public ResponseEntity<BaseResponse> getCandidate(@PathVariable(name = "id") Long candidateId) {
		List<String> errors = new LinkedList<>();
		HttpStatus httpStatus;
		String message;
		Candidate candidate;

		if (candidateId == null) {
			errors.add("Candidate Id parameter shouldn't be null");
			httpStatus = HttpStatus.BAD_REQUEST;
			message = "Failed";
			candidate = null;
		} else {
			candidate = candidateService.get(candidateId);
			if (candidate != null) {
				httpStatus = HttpStatus.OK;
				message = "Success";
			} else {
				errors.add(String.format("Candidate with the following Id %s doesn't exist", candidateId));
				httpStatus = HttpStatus.NO_CONTENT;
				message = "Failed";
			}
		}
		return createBaseResponse(httpStatus, errors, message, candidate);
	}

	@RequestMapping(method = RequestMethod.DELETE, path = PathConstants.ID_VARIABLE_PATH)
	public ResponseEntity<BaseResponse> deleteCandidate(@PathVariable(name = "id") Long candidateId) {
		List<String> errors = new LinkedList<>();
		HttpStatus httpStatus;
		String message;
		Candidate candidate;

		if (candidateId == null) {
			errors.add("Candidate Id parameter shouldn't be null");
			httpStatus = HttpStatus.BAD_REQUEST;
			message = "Failed";
			candidate = null;
		} else {
			candidate = candidateService.deleteByKey(candidateId);
			if (candidate != null) {
				httpStatus = HttpStatus.OK;
				message = "Success";
			} else {
				errors.add(String.format("Candidate with the following Id %s doesn't exist", candidateId));
				httpStatus = HttpStatus.NO_CONTENT;
				message = "Failed";
			}
		}
		return createBaseResponse(httpStatus, errors, message, candidate);
	}

	private ResponseEntity<BaseResponse> createBaseResponse(HttpStatus httpStatus, List<String> errors, String message,
			Object data) {
		ResponseStatus status = ResponseStatus.builder().code(httpStatus.value()).errors(errors).message(message)
				.build();
		return new ResponseEntity<>(BaseResponse.builder().data(data).status(status).build(), httpStatus);
	}
}
