package com.countme.up.model.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * A class that represents the status of the response
 * 
 * @author ahamouda
 */
@JsonInclude(value = Include.NON_EMPTY)
public class ResponseStatus implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int code;
	private final String message;
	private final List<String> errors;

	private ResponseStatus(Builder builder) {
		this.code = builder.code;
		this.message = builder.message;
		this.errors = builder.errors == null ? new ArrayList<>() : builder.errors;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private int code;
		private String message;
		private List<String> errors;

		public Builder code(int code) {
			this.code = code;
			return this;
		}

		public Builder errors(List<String> errors) {
			this.errors = errors;
			return this;
		}

		public Builder message(String message) {
			this.message = message;
			return this;
		}

		public ResponseStatus build() {
			return new ResponseStatus(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + code;
		result = prime * result + ((errors == null) ? 0 : errors.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ResponseStatus other = (ResponseStatus) obj;
		if (code != other.code) return false;
		if (errors == null) {
			if (other.errors != null) return false;
		} else if (!errors.equals(other.errors)) return false;
		if (message == null) {
			if (other.message != null) return false;
		} else if (!message.equals(other.message)) return false;
		return true;
	}

}
