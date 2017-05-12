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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((candidateId == null) ? 0 : candidateId.hashCode());
		result = prime * result + ((fromDate == null) ? 0 : fromDate.hashCode());
		result = prime * result + ((pollId == null) ? 0 : pollId.hashCode());
		result = prime * result + ((toDate == null) ? 0 : toDate.hashCode());
		result = prime * result + ((voterId == null) ? 0 : voterId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		VoteSearchRequest other = (VoteSearchRequest) obj;
		if (candidateId == null) {
			if (other.candidateId != null) return false;
		} else if (!candidateId.equals(other.candidateId)) return false;
		if (fromDate == null) {
			if (other.fromDate != null) return false;
		} else if (!fromDate.equals(other.fromDate)) return false;
		if (pollId == null) {
			if (other.pollId != null) return false;
		} else if (!pollId.equals(other.pollId)) return false;
		if (toDate == null) {
			if (other.toDate != null) return false;
		} else if (!toDate.equals(other.toDate)) return false;
		if (voterId == null) {
			if (other.voterId != null) return false;
		} else if (!voterId.equals(other.voterId)) return false;
		return true;
	}

	@Override
	public String toString() {
		return "VoteSearchRequest [pollId=" + pollId + ", voterId=" + voterId + ", candidateId=" + candidateId
				+ ", fromDate=" + fromDate + ", toDate=" + toDate + "]";
	}

}
