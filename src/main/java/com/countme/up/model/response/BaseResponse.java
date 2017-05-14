package com.countme.up.model.response;

import java.io.Serializable;

/**
 * A class that represents the response from the API. It consists of the {@link ResponseStatus} and data
 * 
 * @author ahamouda
 * 
 */
public class BaseResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private final ResponseStatus status;
	private final Object data;

	private BaseResponse(Builder builder) {
		this.status = builder.status;
		this.data = builder.data;
	}

	public ResponseStatus getStatus() {
		return status;
	}

	public Object getData() {
		return data;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private ResponseStatus status;
		private Object data;

		public Builder status(ResponseStatus status) {
			this.status = status;
			return this;
		}

		public Builder data(Object data) {
			this.data = data;
			return this;
		}

		public BaseResponse build() {
			return new BaseResponse(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		BaseResponse other = (BaseResponse) obj;
		if (data == null) {
			if (other.data != null) return false;
		} else if (!data.equals(other.data)) return false;
		if (status == null) {
			if (other.status != null) return false;
		} else if (!status.equals(other.status)) return false;
		return true;
	}

}
