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
import com.countme.up.service.CandidateService;

@RestController
@RequestMapping(value = PathConstants.CANDIDATE_MAIN_PATH)
public class CandidateController implements ControllerCommonMethods {

	@Autowired
	private CandidateService candidateService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<BaseResponse> getAllCandidates() {
		List<Candidate> candidates = candidateService.getAll();

		return createBaseResponse(HttpStatus.OK, HttpStatus.INTERNAL_SERVER_ERROR, null, candidates);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<BaseResponse> addCandidate(@RequestBody Candidate candidate) {
		List<String> errors = new LinkedList<>();
		try {
			boolean check = candidateService.add(candidate);
			if (!check) candidate = null;
		} catch (Exception ex) {
			errors.add(String.format("Failed to add Candidate!"));
		}
		return createBaseResponse(HttpStatus.CREATED, HttpStatus.BAD_REQUEST, errors, candidate);
	}

	@RequestMapping(method = RequestMethod.GET, path = PathConstants.ID_VARIABLE_PATH)
	public ResponseEntity<BaseResponse> getCandidate(@PathVariable(name = "id") Long candidateId) {
		List<String> errors = new LinkedList<>();
		Candidate candidate = null;
		try {
			candidate = candidateService.get(candidateId);
			if (candidate == null) {
				errors.add(String.format("Candidate with the following Id %s doesn't exist", candidateId));
			}
		} catch (Exception ex) {
			errors.add(String.format("Failed to get Candidate!"));
		}
		return createBaseResponse(HttpStatus.OK, HttpStatus.BAD_REQUEST, errors, candidate);
	}

	@RequestMapping(method = RequestMethod.DELETE, path = PathConstants.ID_VARIABLE_PATH)
	public ResponseEntity<BaseResponse> deleteCandidate(@PathVariable(name = "id") Long candidateId) {
		List<String> errors = new LinkedList<>();
		Candidate candidate = null;
		try {
			candidate = candidateService.deleteByKey(candidateId);
			if (candidate == null) {
				errors.add(String.format("Candidate with the following Id %s doesn't exist", candidateId));
			}
		} catch (Exception ex) {
			errors.add(String.format("Failed to delete Candidate!"));
		}
		return createBaseResponse(HttpStatus.OK, HttpStatus.BAD_REQUEST, errors, candidate);
	}
}
