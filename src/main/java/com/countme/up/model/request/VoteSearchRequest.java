package com.countme.up.model.request;

import java.util.Date;

/**
 * A class that represents the request that needed to search or query a {@link Vote} entity
 * 
 * @author ahamouda
 *
 */
public class VoteSearchRequest {

	private final Long pollId;
	private final Long voterId;
	private final Long candidateId;
	private final Date fromDate;
	private final Date toDate;

	private VoteSearchRequest(Builder builder) {
		this.pollId = builder.pollId;
		this.voterId = builder.voterId;
		this.candidateId = builder.candidateId;
		this.fromDate = builder.fromDate;
		this.toDate = builder.toDate;
	}

	public Long getPollId() {
		return pollId;
	}

	public Long getVoterId() {
		return voterId;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long pollId;
		private Long voterId;
		private Long candidateId;
		private Date fromDate;
		private Date toDate;

		public Builder pollId(Long pollId) {
			this.pollId = pollId;
			return this;
		}

		public Builder voterId(Long voterId) {
			this.voterId = voterId;
			return this;
		}

		public Builder candidateId(Long candidateId) {
			this.candidateId = candidateId;
			return this;
		}

		public Builder fromDate(Date fromDate) {
			this.fromDate = fromDate;
			return this;
		}

		public Builder toDate(Date toDate) {
			this.toDate = toDate;
			return this;
		}

		public VoteSearchRequest build() {
			return new VoteSearchRequest(this);
		}
	}
}
