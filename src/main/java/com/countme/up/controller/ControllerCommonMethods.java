package com.countme.up.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import com.countme.up.model.response.BaseResponse;
import com.countme.up.model.response.ResponseStatus;

/**
 * An interface that contains default common methods that might be used by several controllers
 * 
 * @author ahamouda
 *
 */
public interface ControllerCommonMethods {

	/**
	 * A default method that creates a Response entity
	 * 
	 * @param successHttpStatus
	 *            the http status in case data is not null
	 * @param failHttpStatus
	 *            the http status in case data is null
	 * @param errors
	 *            list of errors
	 * @param data
	 *            the data need to be returned
	 * @return a new {@link ResponseEntity} based on the given parameters
	 */
	public default <T> ResponseEntity<BaseResponse> createBaseResponse(HttpStatus successHttpStatus,
			HttpStatus failHttpStatus, List<String> errors, T data) {
		HttpStatus httpStatus;
		String message;
		if (CollectionUtils.isEmpty(errors)) {
			httpStatus = successHttpStatus;
			message = "Success";
		} else {
			httpStatus = failHttpStatus;
			message = "Failed";
		}

		ResponseStatus status = ResponseStatus.builder().code(httpStatus.value()).errors(errors).message(message)
				.build();
		return new ResponseEntity<>(BaseResponse.builder().data(data).status(status).build(), httpStatus);
	}
}
